package com.bestdo.bestdoStadiums.model;

import java.util.ArrayList;
import java.util.List;

import com.bestdo.bestdoStadiums.business.net.RankDepartmentResponse.ListDataEntity;

import android.support.annotation.NonNull;

/**
 * Created by YuHua on 2017/5/23 13:29 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：健步走活动信息转换类
 */

public class RankDepartmentMapper {

	public static List<RankDepartment> map(@NonNull List<ListDataEntity> entities) {
		List<RankDepartment> rankCompanies = new ArrayList<RankDepartment>();
		for (ListDataEntity entity : entities) {
			RankDepartment department = new RankDepartment();
			department.department = entity.getName();
			department.steps = entity.getDep_avg_step();
			department.joinPeoples = "共" + entity.getEnroll_num() + "参与";
			department.no = entity.getNum();
			rankCompanies.add(department);
		}
		return rankCompanies;
	}

	public static class RankDepartment {
		public String department = "";
		public String steps = "";
		public String joinPeoples = "";
		public String no = "";
	}
}
