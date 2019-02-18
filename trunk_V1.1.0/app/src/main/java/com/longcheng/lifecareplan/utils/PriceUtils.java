package com.longcheng.lifecareplan.utils;

import android.text.Editable;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字价格计算类
 *
 * @author zoc
 */
public class PriceUtils {
    private static PriceUtils mUtils;

    public synchronized static PriceUtils getInstance() {
        if (mUtils == null) {
            mUtils = new PriceUtils();
        }
        return mUtils;
    }

    /**
     * 判断字符串中某个字符的数量
     *
     * @param str
     * @param substr
     * @return
     */
    public int getNumByStr(String str, char substr) {
        int num = 0;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (substr == chars[i]) {
                num++;
            }
        }
        return num;
    }

    /**
     * 控制输入框小数点前后位数
     *
     * @param beforeDot -1 不限制
     * @param afterDot
     * @param editable
     */
    public void judge(int beforeDot, int afterDot, Editable editable) {
        String temp = editable.toString();
        int posDot = temp.indexOf(".");
        // 直接输入小数点的情况
        if (posDot == 0) {
            editable.insert(0, "0");
            return;
        }
        // 连续输入0
        if (temp.equals("00")) {
            editable.delete(1, 2);
            return;
        }
        // 输入"08" 等类似情况
        if (temp.startsWith("0") && temp.length() > 1
                && (posDot == -1 || posDot > 1)) {
            editable.delete(0, 1);
            return;
        }

        // 不包含小数点 不限制小数点前位数
        if (posDot < 0 && beforeDot == -1) {
            // do nothing 仅仅为了理解逻辑而已
            return;
        } else if (posDot < 0 && beforeDot != -1) {
            // 不包含小数点 限制小数点前位数
            if (temp.length() <= beforeDot) {
                // do nothing 仅仅为了理解逻辑而已
            } else {
                editable.delete(beforeDot, beforeDot + 1);
            }
            return;
        }

        // 如果包含小数点 限制小数点后位数
        if (temp.length() - posDot - 1 > afterDot && afterDot != -1) {
            editable.delete(posDot + afterDot + 1, posDot + afterDot + 2);// 删除小数点后多余位数
        }
    }

    /**
     * 获取两位小数点   向下截取
     *
     * @param str
     * @return
     */
    public String getStrWeiShuTwo(String str) {
        String neStr = str;
        if (str.contains(".") && !str.endsWith(".")) {
            String sd = str.substring(str.indexOf(".") + 1);
            if (sd.length() >= 2) {
                neStr = str.substring(0, str.indexOf(".") + 1 + 2);
            } else {
                neStr = neStr + "0";
            }
        }
        //例：“500”---》“500.00” ，  “50.256”----》"50.25"
        Double cny = Double.parseDouble(neStr);//转换成Double
        DecimalFormat df = new DecimalFormat("0.00");//格式化
        String CNY = df.format(cny);
        return CNY;
    }

    /**
     * 向下截取
     *
     * @param str
     * @return
     */
    public String getStrWeiShu0(String str) {
        String neStr = str;
        if (str.contains(".")) {
            neStr = str.substring(0, str.indexOf("."));
        }
        return neStr;
    }

    /**
     * 价格判断小数点后是否为0 小数点后wei0则去掉，否则不去
     *
     * @param price
     */
    public String seePrice(String price) {
        String reprice = "0";
        try {
            if (!TextUtils.isEmpty(price)) {
                if (price.contains(".")) {
                    String sd = price.substring(price.indexOf(".") + 1);
                    int dw = Integer.parseInt(sd);
                    if (dw > 0) {
                        reprice = price;
                    } else {
                        reprice = price.substring(0, price.indexOf("."));
                    }
                } else {
                    reprice = price;
                }
            }
        } catch (Exception e) {
        }
        return reprice;
    }

    /**
     * 当浮点型数据位数超过10位之后，数据变成科学计数法显示。用此方法可以使其正常显示。
     *
     * @param value
     * @return Sting
     */
    public String formatFloatNumber(double value) {
        if (value != 0.00) {
            java.text.DecimalFormat df = new java.text.DecimalFormat(
                    "#######0.00");
            return df.format(value);
        } else {
            return "0.00";
        }

    }

    /**
     * 保留小数 =2 4舍5入
     *
     * @param price
     * @param weishu
     * @return
     */
    public String getPriceTwoDecimal(double price, int weishu) {
        return String.format("%." + weishu + "f", price);
    }

    public double getPriceTwoDecimalDouble(double price, int weishu) {
        return Double.valueOf(String.format("%." + weishu + "f", price));
    }

    /**
     * BigDecimal.ROUND_DOWN-位数后舍去
     *
     * @param price
     * @param weishu
     * @return
     */
    public String getPriceTwoDecimalDown(double price, int weishu) {
        BigDecimal bg = new BigDecimal(String.valueOf(price));
        double f1 = bg.setScale(weishu, BigDecimal.ROUND_DOWN).doubleValue();
        System.out.println(f1);
        return String.valueOf(f1);
    }

    /**
     * BigDecimal.ROUND_DOWN-位数后舍去
     *
     * @param price
     * @param weishu
     * @return
     */
    public double getPriceTwoDecimalDownDouble(double price, int weishu) {
        BigDecimal bg = new BigDecimal(String.valueOf(price));
        double f1 = bg.setScale(weishu, BigDecimal.ROUND_DOWN).doubleValue();
        System.out.println(f1);
        return f1;
    }

    /**
     * 计算总价格，防止长度超出范围 乘
     *
     * @param price
     * @param startsum
     * @return
     */
    public String gteMultiplySumPrice(String price, String startsum) {
        String sum = "";
        try {
            BigDecimal mprice = new BigDecimal(price);
            BigDecimal mstartsum = new BigDecimal(startsum);
            BigDecimal dosum = mprice.multiply(mstartsum);
            sum = dosum.toString();
        } catch (Exception e) {
        }
        return sum;
    }

    /**
     * 计算总价格，防止长度超出范围 加
     *
     * @param price
     * @param startsum
     * @return
     */
    public String gteAddSumPrice(String price, String startsum) {
        String sum = "";
        try {
            BigDecimal mprice = new BigDecimal(price);
            BigDecimal mstartsum = new BigDecimal(startsum);
            BigDecimal dosum = mprice.add(mstartsum);
            sum = dosum.toString();
        } catch (Exception e) {
        }
        return sum;
    }

    /**
     * 计算总价格，防止长度超出范围 减
     *
     * @param price
     * @param startsum subtract 减法 add 加法 multiply 乘法 divide 除法
     * @return
     */
    public String gteSubtractSumPrice(String price, String startsum) {
        String sum = "0";
        try {
            BigDecimal mprice = new BigDecimal(price);
            BigDecimal mstartsum = new BigDecimal(startsum);
            if (mstartsum.compareTo(mprice) != -1) {
                BigDecimal dosum = mstartsum.subtract(mprice);
                sum = dosum.toString();
            } else if (mstartsum.compareTo(mprice) == -1) {
                BigDecimal dosum = mprice.subtract(mstartsum);
                sum = "-" + dosum.toString();
            }
        } catch (Exception e) {
        }
        return sum;
    }

    /**
     * 除法
     *
     * @param price
     * @param startsum
     * @return
     */
    public String gteDividePrice(String price, String startsum) {
        String sum = "0";
        try {
            BigDecimal mprice = new BigDecimal(price);
            BigDecimal mstartsum = new BigDecimal(startsum);
            BigDecimal dosum = mprice.divide(mstartsum);
            sum = dosum.toString();
        } catch (Exception e) {
        }
        return sum;
    }

    /**
     * 计算两个价格大小,计算两个字符数的大小 result大于0，则firstPrice>sencondPrice-->false;
     * result等于0，则firstPrice=sencondPrice; result小于0，则firstPrice<sencondPrice;
     *
     * @param firstPrice
     * @param sencondPrice
     * @return
     */
    public boolean compareToPrice(String firstPrice, String sencondPrice) {
        if (TextUtils.isEmpty(firstPrice)) {
            firstPrice = "0";
        }
        if (TextUtils.isEmpty(sencondPrice)) {
            sencondPrice = "0";
        }
        BigDecimal mprice = new BigDecimal(firstPrice);
        BigDecimal mstartsum = new BigDecimal(sencondPrice);
        int result = mprice.compareTo(mstartsum);
        if (result <= 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 整数转字符串
     *
     * @param value
     * @return
     */
    public String toString(int value) {
        return String.valueOf(value);
    }

}
