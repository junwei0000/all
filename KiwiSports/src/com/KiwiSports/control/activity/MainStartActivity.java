package com.KiwiSports.control.activity;

import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constans;

import android.view.View;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：日历页面
 */
public class MainStartActivity extends BaseActivity {

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.pagetop_layout_back:
		// nowFinish();
		// break;

		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		// setContentView(R.layout.user_login);
		CommonUtils.getInstance().addActivity(this);
		// CommonUtils.getInstance().addPayPageActivity(this);//
		// 为了自动注册后登录，在UserRegistSetPwActivity页面关闭
		// mUserLoginSkipUtils = new UserLoginSkipUtils(this);

	}

	@Override
	protected void findViewById() {
		// pagetop_layout_back = (LinearLayout)
		// findViewById(R.id.pagetop_layout_back);
		// pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		// pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		// pagetop_layout_you = (LinearLayout)
		// findViewById(R.id.pagetop_layout_you);
		//
		// userlogin_tv_fastlogin = (TextView)
		// findViewById(R.id.userlogin_tv_fastlogin);
		// userlogin_fastlogin_bottomline = (TextView)
		// findViewById(R.id.userlogin_fastlogin_bottomline);
		// userlogin_tv_commonlogin = (TextView)
		// findViewById(R.id.userlogin_tv_commonlogin);
		// userlogin_commonlogin_bottomline = (TextView)
		// findViewById(R.id.userlogin_commonlogin_bottomline);

	}

	@Override
	protected void setListener() {
	}

	@Override
	protected void processLogic() {
		// TODO Auto-generated method stub

	}

	/**
	 * 退出监听
	 */
	public void onBackPressed() {
		CommonUtils.getInstance().defineBackPressed(this, null,0,
				Constans.getInstance().exit);
	}

}
