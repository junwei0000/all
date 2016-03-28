package com.zh.education.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zh.education.model.BoKeInfo;
import com.zh.education.model.EventsInfo;
import com.zh.education.model.UserLoginInfo;

/**
 * 
 * @author 作者：qyy
 * @date 
 * @Description 类描述：
 */
public class EventsParser extends BaseParser<Object> {

	private ArrayList<EventsInfo>  mList;
	public EventsParser(ArrayList<EventsInfo> mList) {
		super();
		this.mList = mList;
	}

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONArray jSONArray=jsonObject.optJSONArray("data");
				ArrayList<EventsInfo> EventsInfoList=new ArrayList<EventsInfo>();
				
				for (int i = 0; i < jSONArray.length(); i++) {
					JSONObject obj=jSONArray.optJSONObject(i);
					long DayStamp=obj.optLong("DayStamp");
					EventsInfo eventsInfo=new EventsInfo();
					eventsInfo.setDayStamp(DayStamp);
					
					
					JSONArray array=obj.optJSONArray("Calendars");
					if(array!=null){
						ArrayList<EventsInfo> eventsDetailInfoList=
								new ArrayList<EventsInfo>();
					for (int j = 0; j < array.length(); j++) {
						EventsInfo eventsDetailInfo=new EventsInfo();
						
						JSONObject oo=array.optJSONObject(j);
						String Description=oo.optString("Description","");
						long BeginTime=oo.optLong("BeginTime");
						String Type=oo.optString("Type","");
						String Address=oo.optString("Address","");
						String AppointmentType=oo.optString("AppointmentType","");
						String CalendarId=oo.optString("CalendarId","");
						long EndTime=oo.optLong("EndTime");
						String CalendarName=oo.optString("CalendarName","");
						
						eventsDetailInfo.setDescription(Description);
						eventsDetailInfo.setBeginTime(BeginTime);
						eventsDetailInfo.setType(Type);
						eventsDetailInfo.setAddress(Address);
						eventsDetailInfo.setAppointmentType(AppointmentType);
						eventsDetailInfo.setCalendarId(CalendarId);
						eventsDetailInfo.setEndTime(EndTime);
						eventsDetailInfo.setCalendarName(CalendarName);
						eventsDetailInfoList.add(eventsDetailInfo);
						
						}
						eventsInfo.setEventsDetailInfoList(eventsDetailInfoList);
					}
					EventsInfoList.add(eventsInfo);
				}
				mHashMap.put("EventsInfoList",EventsInfoList);
			} else {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
