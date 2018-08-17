package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.BannerInfo;
import com.bestdo.bestdoStadiums.model.StadiumDetailInfo;
import com.bestdo.bestdoStadiums.model.StadiumVenuesInfo;
import com.bestdo.bestdoStadiums.model.StadiumVenuesPricesInfo;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * @author jun
 * 
 */
public class StadiumDetailParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("400")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("msg", data);
			} else if (status.equals("402")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("msg", data);
			} else if (status.equals("401")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("msg", data);
			} else if (status.equals("200")) {
				JSONObject jsonObj = jsonObject.getJSONObject("data");

				String is_favorite = jsonObj.optString("is_favorite", "0");
				boolean collectStatus = false;
				if (is_favorite.equals("1")) {
					// 1是收藏
					collectStatus = true;
				}
				String member_discount_note = jsonObj.optString("member_discount_note", "");

				String mer_item_id = jsonObj.optString("mer_item_id", "");
				String merid = jsonObj.optString("merid", "");
				String cid = jsonObj.optString("cid", "");
				String venue_id = jsonObj.optString("venue_id", "");
				String price_info = jsonObj.optString("price_info", "");
				String longitude = jsonObj.optString("longitude", "");
				String latitude = jsonObj.optString("latitude", "");
				String city = jsonObj.optString("city", "");
				String thumb = jsonObj.optString("thumb", "");
				String min_price = jsonObj.optString("min_price", "");
				String stadium_id = jsonObj.optString("stadium_id", "");
				String stadium_status = jsonObj.optString("stadium_status", "");
				String name = jsonObj.optString("name", "");
				String inventory_type = jsonObj.optString("inventory_type", "");
				String stadium_name = jsonObj.optString("stadium_name", "");
				JSONObject venueObj = jsonObj.optJSONObject("venue");

				String book_info = venueObj.optString("book_info", "");
				String process_type = venueObj.optString("process_type", "");
				/**
				 * 相册
				 */
				ArrayList<BannerInfo> mAlbumsList = new ArrayList<BannerInfo>();
				try {
					JSONArray mAlbumsArray = venueObj.optJSONArray("Albums");
					for (int i = 0; i < mAlbumsArray.length(); i++) {
						JSONObject mAlbumsObj = mAlbumsArray.optJSONObject(i);
						String img_url = mAlbumsObj.optString("file");
						BannerInfo mBannerInfo = new BannerInfo();
						mBannerInfo.setImg_url(img_url);
						mAlbumsList.add(mBannerInfo);
						mBannerInfo = null;
					}
				} catch (Exception e) {
				}
				if (mAlbumsList != null && mAlbumsList.size() > 0) {
				} else {
					BannerInfo mBannerInfo = new BannerInfo();
					mBannerInfo.setImg_url(thumb);
					mAlbumsList.add(mBannerInfo);
					mBannerInfo = null;
				}
				JSONObject stadiumObj = venueObj.optJSONObject("stadium");

				String abbreviation = stadiumObj.optString("abbreviation", "");
				String description = stadiumObj.optString("description", "");
				String address = stadiumObj.optString("address", "");
				String bd_phone = stadiumObj.optString("phone", "");
				int teetimejiange = venueObj.optInt("teetime", 15);
				StadiumDetailInfo mStadiumDetailInfo = new StadiumDetailInfo();

				boolean isbook = false;
				String is_book = jsonObj.optString("is_book", "0");
				String auditstatus = jsonObj.optString("status", "0");
				String venueis_book = venueObj.optString("is_book", "0");
				String venueauditstatus = venueObj.optString("status", "0");
				String stadiumObjauditstatus = stadiumObj.optString("status", "0");
				if (is_book.equals("1") && auditstatus.equals("1") && venueis_book.equals("1")
						&& venueauditstatus.equals("1") && stadiumObjauditstatus.equals("1")) {
					isbook = true;
				}
				mStadiumDetailInfo.setMember_discount_note(member_discount_note);
				mStadiumDetailInfo.setBook_info(book_info);
				mStadiumDetailInfo.setProcess_type(process_type);
				mStadiumDetailInfo.setIsbook(isbook);
				mStadiumDetailInfo.setmAlbumsList(mAlbumsList);
				mStadiumDetailInfo.setTeetimejiange(teetimejiange);
				mStadiumDetailInfo.setCollectStatus(collectStatus);
				mStadiumDetailInfo.setVenue_id(venue_id);
				mStadiumDetailInfo.setMerid(merid);
				mStadiumDetailInfo.setCity(city);
				mStadiumDetailInfo.setStadium_name(stadium_name);
				min_price = PriceUtils.getInstance().gteDividePrice(min_price, "100");
				mStadiumDetailInfo.setMin_price(min_price);
				mStadiumDetailInfo.setStadium_id(stadium_id);
				mStadiumDetailInfo.setStadium_status(stadium_status);
				mStadiumDetailInfo.setName(name);
				mStadiumDetailInfo.setMer_item_id(mer_item_id);
				mStadiumDetailInfo.setPrice_info(price_info);
				mStadiumDetailInfo.setLongitude(longitude);
				mStadiumDetailInfo.setLatitude(latitude);
				mStadiumDetailInfo.setThumb(thumb);
				mStadiumDetailInfo.setAbbreviation(abbreviation);
				mStadiumDetailInfo.setDescription(description);
				mStadiumDetailInfo.setAddress(address);
				mStadiumDetailInfo.setBd_phone(bd_phone);
				mStadiumDetailInfo.setInventory_type(inventory_type);
				mStadiumDetailInfo.setCid(cid);
				mHashMap.put("mStadiumDetailInfo", mStadiumDetailInfo);

				/**
				 * services 场馆设施
				 */
				try {
					ArrayList<StadiumDetailInfo> mservicesList = new ArrayList<StadiumDetailInfo>();
					JSONArray servicesArray = stadiumObj.optJSONArray("stadium_services");
					;
					for (int i = 0; i < servicesArray.length(); i++) {
						JSONObject servicesArrayObj = servicesArray.getJSONObject(i);
						String services_name = servicesArrayObj.getString("name");
						String services_uri = servicesArrayObj.getString("img");
						String stadium_service_id = servicesArrayObj.getString("stadium_service_id");

						StadiumDetailInfo mInfo = new StadiumDetailInfo();
						mInfo.setServices_name(services_name);
						mInfo.setServices_uri(services_uri);
						mInfo.setStadium_service_id(stadium_service_id);
						mservicesList.add(mInfo);
						mInfo = null;
					}
					mHashMap.put("mservicesList", mservicesList);
				} catch (Exception e) {
				}

				/**
				 * displayStadiumProperties 场馆属性
				 */
				try {
					ArrayList<StadiumDetailInfo> mdisplayStadiumPropertiesList = new ArrayList<StadiumDetailInfo>();
					JSONArray displaystadiumPropertiesOBJ = venueObj.getJSONArray("venue_property");
					System.err.println(displaystadiumPropertiesOBJ);
					for (int i = 0; i < displaystadiumPropertiesOBJ.length(); i++) {
						JSONObject stadiumPropertiesobj = displaystadiumPropertiesOBJ.getJSONObject(i);
						String displayStadiumProperties_name = stadiumPropertiesobj.getString("name");
						String displayStadiumProperties_value = stadiumPropertiesobj.getString("value");
						if (!TextUtils.isEmpty(displayStadiumProperties_name)
								&& !TextUtils.isEmpty(displayStadiumProperties_value)) {
							StadiumDetailInfo mInfo = new StadiumDetailInfo();
							mInfo.setDisplayStadiumProperties_name(displayStadiumProperties_name);
							mInfo.setDisplayStadiumProperties_value(displayStadiumProperties_value);
							mdisplayStadiumPropertiesList.add(mInfo);
							mInfo = null;
						}
					}
					mHashMap.put("mdisplayStadiumPropertiesList", mdisplayStadiumPropertiesList);
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
		}
		return mHashMap;
	}
}
