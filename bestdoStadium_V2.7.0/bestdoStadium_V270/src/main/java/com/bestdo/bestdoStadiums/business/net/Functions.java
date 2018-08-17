package com.bestdo.bestdoStadiums.business.net;

import java.util.List;

import com.bestdo.bestdoStadiums.model.ActInfoMapper.ActInfoBack;
import com.bestdo.bestdoStadiums.model.MyWalkInfoMapper.MyWalkInfo;
import com.bestdo.bestdoStadiums.model.MyWalkInfoMapper.MyWalkInfoBack;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.google.gson.reflect.TypeToken;

public enum Functions implements Function {

	GET_ACTS_INFO("/appservice/stepActiveList", new TypeToken<BaseObjectResponce<List<ActInfoBack>>>() {
	}),

	GET_WALK_COMP_RANK("/appservice/TopData", new TypeToken<BaseObjectResponce<RankPersonResponse>>() {
	}),

	GET_WALK_DEP_RANK("/appservice/newTopBusData", new TypeToken<BaseObjectResponce<RankDepartmentResponse>>() {
	}),

	GET_MY_WALK_KM("/appservice/getUserTodayStep", new TypeToken<BaseObjectResponce<RankMyKmResponse>>() {
	}),

	GET_MY_WEEK_WALK("/appservice/getStep7Days", new TypeToken<BaseObjectResponce<List<MyWalkInfoBack>>>() {
	}),

	UP_MY_WALK_STEPS("/appservice/TopDataUpload", new TypeToken<BaseObjectResponce>() {
	});

	private final String url;
	private final TypeToken typeToken;

	private Functions(String url_path, TypeToken typeToken) {
		this.url = url_path;
		this.typeToken = typeToken;
	}

	@Override
	public TypeToken getResponceTypeToken() {
		return typeToken;
	}

	@Override
	public String getUrl() {
		return Constans.WEB_ROOT + url;
	}

}