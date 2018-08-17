package com.bestdo.bestdoStadiums.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuHua on 2017/5/23 13:29 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：健步走活动信息转换类
 */

public class ActInfoMapper {

	public static List<ActInfo> map(List<ActInfoBack> back) {
		List<ActInfo> actInfos = new ArrayList<ActInfo>();
		for (ActInfoBack b : back) {
			ActInfo info = new ActInfo(b);
			actInfos.add(info);
		}
		return actInfos;
	}

	public static class ActInfoBack {
		public String act_name;
		public String act_time;
		public String enroll_info;
		public String icon_url;
		public String status;
		public String jump_url;
		public String upload_url;
	}

	public static class ActInfo {
		public String url;
		public String name;
		public String state;
		public String time;
		public String count;
		public String jumpUrl;
		public String upload_url;

		public ActInfo(ActInfoBack back) {
			url = back.icon_url;
			name = back.act_name;
			state = back.status;
			count = back.enroll_info;
			time = back.act_time;
			jumpUrl = back.jump_url;
			upload_url = back.upload_url;
		}
	}
}
