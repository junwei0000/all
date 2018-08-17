package com.bestdo.bestdoStadiums.model;

import java.util.List;

/**
 * Created by YuHua on 2017/5/24 14:47 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：
 */

public class MyWalkInfoMapper {

	public static class MyWalkInfoBack {
		public String step_num;
		public String date;

		public MyWalkInfoBack(String step_num, String date) {
			this.step_num = step_num;
			this.date = date;
		}

	}

	public static class MyWalkInfo {

		public String company = "";
		public int pointSteps = 10000;
		public String curSteps = "";
		public String myName = "";
		public String myIcon = "";
		public float kilometers = 0f;
		public String no = "";
		public float kart = 0f;
		public String curTime = "";
		public List<MyWalkInfoBack> myWalkInfoBacks;

	}
}
