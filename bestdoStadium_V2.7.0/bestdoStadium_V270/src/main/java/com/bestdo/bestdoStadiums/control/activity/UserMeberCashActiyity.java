package com.bestdo.bestdoStadiums.control.activity;

import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.GetBuySuccessMemberBusiness;
import com.bestdo.bestdoStadiums.business.GetBuySuccessMemberBusiness.GetBuySuccessMemberCallback;
import com.bestdo.bestdoStadiums.business.UserMeberCashBusiness;
import com.bestdo.bestdoStadiums.business.UserMeberCashBusiness.GetMeberCashCallback;
import com.bestdo.bestdoStadiums.control.view.HuitanCustomView;
import com.bestdo.bestdoStadiums.model.UserMemberSuccessInfo;
import com.bestdo.bestdoStadiums.utils.CircleImageView;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.SupplierEditText;
import com.bestdo.bestdoStadiums.R;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午4:26:41
 * @Description 类描述：会员兑换
 */
public class UserMeberCashActiyity extends BaseActivity {

	private LinearLayout pagetop_layout_back;
	private TextView user_mebercash_tv_title;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String mobile;
	private Button click_blue_btn;
	private SupplierEditText et_content;

	@Override
	public void onClick(View v) {
		CommonUtils.getInstance().closeSoftInput(this);
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			toFinish();
			break;
		case R.id.click_blue_btn:
			String codeCdk = et_content.getText().toString();
			if (!TextUtils.isEmpty(codeCdk)) {
				processLogicLo(codeCdk.trim());
			} else {
				CommonUtils.getInstance().initToast(context, "请输入百动会员兑换码");
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_mebercash);
		CommonUtils.getInstance().addActivity(UserMeberCashActiyity.this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(context);
	}

	@Override
	protected void findViewById() {
		LinearLayout page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		CommonUtils.getInstance().setViewTopHeigth(context, page_top_layout);
		page_top_layout.setBackgroundColor(getResources().getColor(R.color.btn_blue_color));
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		TextView pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_name.setText("会员兑换");
		pagetop_tv_name.setTextColor(getResources().getColor(R.color.white));
		ImageView pagetop_iv_back = (ImageView) findViewById(R.id.pagetop_iv_back);
		pagetop_iv_back.setBackgroundResource(R.drawable.back_moren);
		user_mebercash_tv_title = (TextView) findViewById(R.id.user_mebercash_tv_title);
		et_content = (SupplierEditText) findViewById(R.id.et_content);
		click_blue_btn = (Button) findViewById(R.id.click_blue_btn);
		click_blue_btn.setText("确认兑换");
		click_blue_btn.setTextColor(getResources().getColor(R.color.white));
	}

	@Override
	protected void setListener() {
		click_blue_btn.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		mobile = bestDoInfoSharedPrefs.getString("mobile", "");
		String aa = mobile;
		aa = aa.substring(0, 3) + "****" + aa.substring(7, 11);
		String strTime = "<font color='#666666'>兑换成功后，百动会员将绑定至</font>账户" + aa;
		user_mebercash_tv_title.setText(Html.fromHtml(strTime));
	}

	@Override
	protected void processLogic() {

	}

	protected void processLogicLo(String codeCdk) {
		HashMap<String, String> mhashmap;
		mhashmap = new HashMap<String, String>();
		mhashmap.put("codeCdk", "" + codeCdk);
		mhashmap.put("uid", "" + bestDoInfoSharedPrefs.getString("uid", ""));
		mhashmap.put("telphone", "" + mobile);
		mhashmap.put("userName", "" + bestDoInfoSharedPrefs.getString("realName", ""));
		new UserMeberCashBusiness(this, mhashmap, new GetMeberCashCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						Intent intent;
						intent = new Intent(context, UserMeberResultActiyity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						CommonUtils.getInstance().setPageIntentAnim(intent, context);
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
					}
				}
			}
		});
	}

	private void toFinish() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			toFinish();
		}
		return false;
	}
}