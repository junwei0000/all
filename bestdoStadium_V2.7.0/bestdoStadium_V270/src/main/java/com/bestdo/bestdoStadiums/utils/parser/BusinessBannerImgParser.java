package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.BannerInfo;
import com.bestdo.bestdoStadiums.model.BusinessBannerInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-8-18 上午10:49:48
 * @Description 类描述：
 */
public class BusinessBannerImgParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("400")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			} else if (status.equals("403")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			} else if (status.equals("401")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			} else if (status.equals("200")) {
				JSONObject objdata = jsonObject.optJSONObject("data");

				ArrayList<BusinessBannerInfo> bannerList = new ArrayList<BusinessBannerInfo>();
				try {
					JSONArray bannerArray = objdata.optJSONArray("banner");
					for (int i = 0; i < bannerArray.length(); i++) {
						JSONObject bannerO = bannerArray.optJSONObject(i);
						String name = bannerO.optString("name", "");
						String imgPath = bannerO.optString("url", "");
						String url = bannerO.optString("html_url", "");
						BusinessBannerInfo mBannerInfo = new BusinessBannerInfo(name, imgPath, url);
						bannerList.add(mBannerInfo);
						mBannerInfo = null;
					}
				} catch (Exception e) {
				}
				mHashMap.put("bannerList", bannerList);

				ArrayList<BusinessBannerInfo> menuList = new ArrayList<BusinessBannerInfo>();
				try {
					JSONArray navArray = objdata.optJSONArray("nav");
					for (int i = 0; i < navArray.length(); i++) {
						JSONObject navO = navArray.optJSONObject(i);
						String name = navO.optString("name", "");
						String imgPath = navO.optString("imgPath", "");
						String url = navO.optString("url", "");
						String sub_name = navO.optString("sub_name", "");

						BusinessBannerInfo mBannerInfo = new BusinessBannerInfo(name, imgPath, url, sub_name);
						menuList.add(mBannerInfo);
						mBannerInfo = null;
					}
				} catch (Exception e) {
				}
				mHashMap.put("menuList", menuList);

				ArrayList<BusinessBannerInfo> navList = new ArrayList<BusinessBannerInfo>();
				try {
					JSONArray navArray = objdata.optJSONArray("twoNav");
					for (int i = 0; i < navArray.length(); i++) {
						JSONObject navO = navArray.optJSONObject(i);
						String name = navO.optString("name", "");
						String imgPath = navO.optString("img", "");
						String url = navO.optString("url", "");
						String describe = navO.optString("describe", "");
						BusinessBannerInfo mBannerInfo = new BusinessBannerInfo(name, imgPath, url, describe);
						navList.add(mBannerInfo);
						mBannerInfo = null;
					}
				} catch (Exception e) {
				}
				mHashMap.put("twoNavList", navList);

				JSONObject jo = objdata.optJSONObject("shopPay");
				String shopPay_url = jo.optString("url");
				mHashMap.put("shopPay_url", shopPay_url);
				String shopPay_name = jo.optString("name");
				mHashMap.put("shopPay_name", shopPay_name);

				String navThree_url = "";
				String navThree_name = "";
				try {
					JSONObject jo2 = objdata.optJSONObject("navThree");
					navThree_url = jo2.optString("url");
					navThree_name = jo2.optString("name");
				} catch (Exception e) {
					// TODO: handle exception
				}
				mHashMap.put("navThree_url", navThree_url);
				mHashMap.put("navThree_name", navThree_name);

			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
