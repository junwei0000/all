package com.bestdo.bestdoStadiums.control.fragment;

import java.util.List;

import com.android.volley.VolleyError;
import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.net.BaseObjectResponce;
import com.bestdo.bestdoStadiums.business.net.BaseResponse;
import com.bestdo.bestdoStadiums.business.net.GsonServer;
import com.bestdo.bestdoStadiums.business.net.IBusiness;
import com.bestdo.bestdoStadiums.business.net.RankDepartmentResponse;
import com.bestdo.bestdoStadiums.business.net.RankDepartmentResponse.HeadInfoEntity;
import com.bestdo.bestdoStadiums.business.net.RankDepartmentResponse.ListDataEntity;
import com.bestdo.bestdoStadiums.business.net.RankDepartmentResponse.MyDepInfoEntity;
import com.bestdo.bestdoStadiums.control.adapter.RankDepartmentAdapter;
import com.bestdo.bestdoStadiums.model.RankDepartmentMapper;
import com.bestdo.bestdoStadiums.model.RankDepartmentMapper.RankDepartment;
import com.bestdo.bestdoStadiums.utils.App;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by YuHua on 2017/5/23 16:15 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：
 */

public class RankDepFragment extends BaseFragment {
	private View root, top, myDep, empty;
	private TextView tvCompany, tvCompanyJoins, tvCompanySteps, tvMyDepartment, tvMyDepartMentNo, tvMyDepartmentSteps;
	private ListView lvDepartment;
	private MyDepInfoEntity myDepInfo;
	private HeadInfoEntity headInfo;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.f_rank_department, container, false);
		initView();
		initData();
		return root;
	}

	private void initView() {
		lvDepartment = (ListView) root.findViewById(R.id.lv_company);
		empty = root.findViewById(R.id.empty_p);
		top = LayoutInflater.from(getActivity()).inflate(R.layout.in_rank_company_title, null);
		lvDepartment.addHeaderView(top);
		myDep = top.findViewById(R.id.in_my_dep);
		tvCompany = (TextView) top.findViewById(R.id.tv_company_name);
		tvCompanyJoins = (TextView) top.findViewById(R.id.tv_count);
		tvCompanySteps = (TextView) top.findViewById(R.id.tv_company_steps);
		tvMyDepartment = (TextView) myDep.findViewById(R.id.tv_my);
		tvMyDepartmentSteps = (TextView) myDep.findViewById(R.id.tv_count);
		tvMyDepartMentNo = (TextView) myDep.findViewById(R.id.tv_ranNo);
	}

	private void initData() {
		showDilag();
		GsonServer.getRankDep(new IBusiness() {

			@Override
			public void onSuccess(BaseResponse response) {
				dismissDialog();
				BaseObjectResponce<RankDepartmentResponse> re = (BaseObjectResponce<RankDepartmentResponse>) response;
				RankDepartmentResponse reObject = re.getObject();
				if (reObject != null) {
					List<ListDataEntity> infos = reObject.getList_data();
					headInfo = reObject.getHead_info();
					myDepInfo = reObject.getMy_info();
					refreshLv(infos);
					refreshMydepView();
					refreshHeadView();
				} else {
					showEmpty();
				}
			}

			@Override
			public void onError(VolleyError error) {
				dismissDialog();
				App.t(R.string.net_tishi);
			}
		});

	}

	private void refreshLv(List<ListDataEntity> infos) {
		if (infos != null && !infos.isEmpty()) {
			List<RankDepartment> departments = RankDepartmentMapper.map(infos);
			lvDepartment.setAdapter(new RankDepartmentAdapter(getActivity(), departments));
		} else {
			showEmpty();
		}
	}

	private void refreshHeadView() {
		if (headInfo != null) {
			tvCompany.setText(headInfo.getCompany_name());
			tvCompanyJoins.setText(getString(R.string.walk_total_join_numbser, headInfo.getEnroll_num()));
			tvCompanySteps.setText(headInfo.getAvg_step_num());
		}
	}

	private void refreshMydepView() {
		if (myDepInfo != null) {
			tvMyDepartment.setText(myDepInfo.getDep_name());
			tvMyDepartMentNo.setText("第" + myDepInfo.getNum() + "名");
			tvMyDepartmentSteps.setText(myDepInfo.getDep_avg_step());
		}
	}

	private void showEmpty() {
		empty.setVisibility(View.VISIBLE);
	}

}