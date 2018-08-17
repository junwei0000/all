package com.bestdo.bestdoStadiums.control.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.net.BaseObjectResponce;
import com.bestdo.bestdoStadiums.business.net.BaseResponse;
import com.bestdo.bestdoStadiums.business.net.GsonServer;
import com.bestdo.bestdoStadiums.business.net.IBusiness;
import com.bestdo.bestdoStadiums.business.net.RankPersonResponse;
import com.bestdo.bestdoStadiums.business.net.RankPersonResponse.UserInfoEntity;
import com.bestdo.bestdoStadiums.control.adapter.RankPersonAdapter;
import com.bestdo.bestdoStadiums.model.MyWalkInfoMapper;
import com.bestdo.bestdoStadiums.model.RankPersonMapper;
import com.bestdo.bestdoStadiums.model.UserWalkingRankInfo;
import com.bestdo.bestdoStadiums.model.RankPersonMapper.RankPerson;
import com.bestdo.bestdoStadiums.utils.App;
import com.bestdo.bestdoStadiums.utils.Config;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by YuHua on 2017/5/23 16:15 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：
 */

public class RankPersonalFragment extends BaseFragment {
	private static final int ONE_PAGE_COUNT = 10;
	private View root;
	private View top, inMy, empty;
	private ListView lvPerson;
	private TextView tvOneName, tvMyName, tvMyNo, tvMySteps;
	private ImageView imgOne, imgMy;
	private RankPersonAdapter adapter;
	private List<RankPerson> datas = new ArrayList<RankPerson>();
	private boolean isLoading;
	private int curPage;
	private int lastBottomVisItem;
	private OnScrollListener lvListener = new OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			int curLastItem = firstVisibleItem + visibleItemCount;
			if (curLastItem > lastBottomVisItem) {
				onDown(totalItemCount, curLastItem);
			}
			lastBottomVisItem = curLastItem;
		}

	};

	private void onDown(int totalItemCount, int curLastItem) {
		if (isLoading)
			return;
		if (totalItemCount >= ONE_PAGE_COUNT + 1 && curLastItem > totalItemCount - 5) {
			isLoading = true;
			loadMore();
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.f_rank_personal, container, false);
		initView();
		initListView();
		loadMore();
		return root;
	}

	private void initView() {
		top = LayoutInflater.from(getActivity()).inflate(R.layout.in_rank_personal_title, null);
		inMy = top.findViewById(R.id.in_my);
		empty = root.findViewById(R.id.empty_p);
		tvOneName = (TextView) top.findViewById(R.id.tv_name_one);
		imgOne = (ImageView) top.findViewById(R.id.img_one);
		tvMyName = (TextView) inMy.findViewById(R.id.tv_my);
		tvMySteps = (TextView) inMy.findViewById(R.id.tv_count);
		tvMyNo = (TextView) inMy.findViewById(R.id.tv_ranNo);
		imgMy = (ImageView) inMy.findViewById(R.id.img_my);
	}

	private void initListView() {
		lvPerson = (ListView) root.findViewById(R.id.lv_personal);
		lvPerson.addHeaderView(top);
		lvPerson.setOnScrollListener(lvListener);
	}

	private void refreshAll() {
		refreshMyRankView();
		refreshOneView();
		refreshListView();
	}

	private void refreshOneView() {
		if (datas != null && !datas.isEmpty()) {
			RankPerson one = datas.get(0);
			tvOneName.setText(one.name + "占领了封面");
			ImageLoader.getInstance().displayImage(one.url, imgOne);
		}
	}

	@NonNull
	private void refreshMyRankView() {
		MyWalkInfoMapper.MyWalkInfo my = Config.config().getMyWalkInfo();
		tvMyName.setText(my.myName);
		tvMyNo.setText("第" + my.no + "名");
		tvMySteps.setText(my.curSteps);
		ImageLoader.getInstance().displayImage(my.myIcon, imgMy);
	}

	private void refreshListView() {
		if (adapter == null) {
			adapter = new RankPersonAdapter(getActivity(), datas);
			lvPerson.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
	}

	private void loadMore() {
		if (curPage == 0)
			showDilag();
		GsonServer.getRankPerson(curPage, new IBusiness() {

			@Override
			public void onSuccess(BaseResponse response) {
				isLoading = false;
				dismissDialog();
				BaseObjectResponce<RankPersonResponse> re = (BaseObjectResponce<RankPersonResponse>) response;
				RankPersonResponse reObject = re.getObject();
				if (reObject != null) {
					if (reObject.getUser_info() != null) {
						Config.config().updateMyWalkInfo(reObject.getUser_info());
					}
					List<UserWalkingRankInfo> infos = reObject.getList();
					if (infos != null && !infos.isEmpty()) {
						datas.addAll(RankPersonMapper.map(infos));
						if (curPage == 0) {
							refreshAll();
						} else {
							refreshListView();
						}
						curPage++;
					} else {
						if (curPage == 0)
							showEmpty();
					}
				} else {
					if (curPage == 0)
						showEmpty();
				}
			}

			@Override
			public void onError(VolleyError error) {
				isLoading = false;
				dismissDialog();
				App.t(R.string.net_tishi);
			}

		});
	}

	private void showEmpty() {
		empty.setVisibility(View.VISIBLE);
	}
}