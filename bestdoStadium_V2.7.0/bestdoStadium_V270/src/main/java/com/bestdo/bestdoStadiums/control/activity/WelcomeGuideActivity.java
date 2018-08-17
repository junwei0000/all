package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.control.adapter.WelcomeGuideAdapter;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.R;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午7:17:45
 * @Description 类描述：引导页
 */
public class WelcomeGuideActivity extends BaseActivity {

	private ArrayList<View> viewList;

	@Override
	public void onClick(View v) {
		toLogin();
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.welcome_guide);
		CommonUtils.getInstance().addActivity(WelcomeGuideActivity.this);
	}

	@Override
	protected void findViewById() {
		ViewPager welcomViewPager = (ViewPager) findViewById(R.id.welcome_guide_viewpager);
		LayoutInflater lf = LayoutInflater.from(this);
		View view1 = lf.inflate(R.layout.welcome_guide_v1, null);
		View view2 = lf.inflate(R.layout.welcome_guide_v2, null);
		View view3 = lf.inflate(R.layout.welcome_guide_v3, null);
		View view4 = lf.inflate(R.layout.welcome_guide_v4, null);
		TextView welcome_iv_start = (TextView) view4.findViewById(R.id.welcome_iv_start);
		welcome_iv_start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonUtils.getInstance().skipMainActivity(WelcomeGuideActivity.this);
			}
		});
		viewList = new ArrayList<View>();
		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);
		viewList.add(view4);
		WelcomeGuideAdapter mWelcomeGuideAdapter = new WelcomeGuideAdapter(viewList);
		welcomViewPager.setAdapter(mWelcomeGuideAdapter);

	}

	@Override
	protected void setListener() {
	}

	@Override
	protected void processLogic() {
	}

	private void toLogin() {
		CommonUtils.getInstance().skipMainActivity(this);
	}

	@Override
	protected void onDestroy() {
		if (viewList != null && viewList.size() != 0) {
			for (View iterable_element : viewList) {
				iterable_element = null;
			}
			viewList.clear();
			viewList = null;
		}
		super.onDestroy();
	}

	/**
	 * 退出监听
	 */
	public void onBackPressed() {
		CommonUtils.getInstance().defineBackPressed(this, null, Constans.getInstance().exit);
	}
}
