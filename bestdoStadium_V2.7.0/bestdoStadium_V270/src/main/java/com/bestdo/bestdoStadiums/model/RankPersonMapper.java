package com.bestdo.bestdoStadiums.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuHua on 2017/5/23 13:29 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：健步走活动信息转换类
 */

public class RankPersonMapper {

	public static List<RankPerson> map(List<UserWalkingRankInfo> back) {
		List<RankPerson> actInfos = new ArrayList<RankPerson>();
		for (UserWalkingRankInfo b : back) {
			RankPerson info = new RankPerson(b);
			actInfos.add(info);
		}
		return actInfos;
	}

	public static class RankPerson implements Comparable<RankPerson> {
		public String url = "";
		public String name = "";
		public String department = "";
		public String steps = "";
		public int no = 0;

		public RankPerson() {
			super();
		}

		public RankPerson(UserWalkingRankInfo info) {
			url = info.getAblum_url();
			name = info.getNick_name();
			department = info.dept_name;
			steps = info.getStep_num();
			no = info.getNum();
		}

		@Override
		public int compareTo(RankPerson o) {
			return no - o.no;
		}
	}
}
