package com.KiwiSports.utils;

/**
 * 安卓项目使用了百度地图的定位SDK，web端使用的也是百度地图， 后来发现界面显示百度地图不如高德效果好，web改用高德地图，原本的百度地图坐标是可以直接使用的，由于高德和百度地图的坐标系不一致 要如何转换呢。

 补充了下坐标系知识后发现高德使用的坐标系是“gcj02”也就是大家所说的“火星坐标”，

 百度使用的是“BD09”因为是百度所用大家习惯称之为“百度坐标”  ，如何将bd09转为gcj02呢，突然想到在百度的定位sdk里有这样一段说明（来自百度地图）


 设置返回值的坐标类型。
 public void setCoorType ( String )
 我们支持返回若干种坐标系，包括国测局坐标系、百度坐标系，需要更多坐标系请联系我们，需要深度合作。目前这些参数的代码为。因此需要在请求时指定类型，如果不指定，默认返回百度坐标系。注意当仅输入IP时，不会返回坐标。目前这些参数的代码为

 返回国测局经纬度坐标系 coor=gcj02
 返回百度墨卡托坐标系 coor=bd09
 返回百度经纬度坐标系 coor=bd09ll
 百度手机地图对外接口中的坐标系默认是bd09ll，如果配合百度地图产品的话，需要注意坐标系对应问题。
 */

/**
 * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的互转
 */
public class GPSUtil {
	/**
	 * 国测局坐标 火星坐标  GCJ-02,百度  BD-09二次加密坐标
	 */
	public static String CoorType="bd09ll"; 
	
	
	private static double pi = 3.1415926535897932384626;
	private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	private static double a = 6378245.0;
	private static double ee = 0.00669342162296594323;

	private static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
				+ 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	private static double transformLon(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
				* Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0
				* pi)) * 2.0 / 3.0;
		return ret;
	}

	public static double[] transform(double lat, double lon) {
		if (outOfChina(lat, lon)) {
			return new double[] { lat, lon };
		}
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * pi;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
		double mgLat = lat + dLat;
		double mgLon = lon + dLon;
		return new double[] { mgLat, mgLon };
	}

	private static boolean outOfChina(double lat, double lon) {
		if (lon < 72.004 || lon > 137.8347)
			return true;
		if (lat < 0.8293 || lat > 55.8271)
			return true;
		return false;
	}

	/**
	 * 84 to 火星坐标系 (GCJ-02) World Geodetic System ==> Mars Geodetic System
	 * 
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static double[] gps84_To_Gcj02(double lat, double lon) {
		if (outOfChina(lat, lon)) {
			return new double[] { lat, lon };
		}
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * pi;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
		double mgLat = lat + dLat;
		double mgLon = lon + dLon;
		return new double[] { mgLat, mgLon };
	}

	/**
	 * * 火星坐标系 (GCJ-02) to 84 * * @param lon * @param lat * @return
	 * */
	public static double[] gcj02_To_Gps84(double lat, double lon) {
		double[] gps = transform(lat, lon);
		double lontitude = lon * 2 - gps[1];
		double latitude = lat * 2 - gps[0];
		return new double[] { latitude, lontitude };
	}

	/**
	 * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
	 * 
	 * @param lat
	 * @param lon
	 */
	public static double[] gcj02_To_Bd09(double lat, double lon) {
		double x = lon, y = lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
		double tempLon = z * Math.cos(theta) + 0.0065;
		double tempLat = z * Math.sin(theta) + 0.006;
		tempLat=retain6(tempLat);
		tempLon=retain6(tempLon);
		double[] gps = { tempLat, tempLon };
		return gps;
	}

	/**
	 * * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 * * 将 BD-09 坐标转换成GCJ-02 坐标 * * @param
	 * bd_lat * @param bd_lon * @return
	 */
	public static double[] bd09_To_Gcj02(double lat, double lon) {
		double x = lon - 0.0065, y = lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		double tempLon = z * Math.cos(theta);
		double tempLat = z * Math.sin(theta);
		tempLat=retain6(tempLat);
		tempLon=retain6(tempLon);
		double[] gps = { tempLat, tempLon };
		return gps;
	}

	/**
	 * 将gps84转为bd09
	 * 
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static double[] gps84_To_bd09(double lat, double lon) {
		double[] gcj02 = gps84_To_Gcj02(lat, lon);
		double[] bd09 = gcj02_To_Bd09(gcj02[0], gcj02[1]);
		return bd09;
	}

	public static double[] bd09_To_gps84(double lat, double lon) {
		double[] gcj02 = bd09_To_Gcj02(lat, lon);
		double[] gps84 = gcj02_To_Gps84(gcj02[0], gcj02[1]);
		// 保留小数点后六位
		gps84[0] = retain6(gps84[0]);
		gps84[1] = retain6(gps84[1]);
		return gps84;
	}

	/**
	 * 保留小数点后六位
	 * 
	 * @param num
	 * @return
	 */
	private static double retain6(double num) {
		String result = String.format("%.6f", num);
		return Double.valueOf(result);
	}

}