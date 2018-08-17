/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CampaignDetailBusiness;
import com.bestdo.bestdoStadiums.business.CampaignDetailBusiness.CampaignDetailCallback;
import com.bestdo.bestdoStadiums.control.adapter.CampaignDetailBaomingAdapter;
import com.bestdo.bestdoStadiums.control.pullable.PullableScrollView;
import com.bestdo.bestdoStadiums.model.CampaignDetailInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.FileCache;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.utils.MyGridView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-11-28 下午4:23:53
 * @Descrip tion 类描述：活动详情
 */
public class CampaignDetailActivity extends BaseActivity {

	protected static final int RELOAD = 0;
	private Button click_btn;
	private LinearLayout page_top_layout;
	private LinearLayout pagetop_layout_back;
	private ImageView pagetop_iv_back;
	private TextView pagetop_tv_name;
	private LinearLayout pagetop_layout_you;
	private ImageView pagetop_iv_you;
	private PullableScrollView campagindetail_scrollview;
	private RelativeLayout campagindetail_ralat_topbanner;
	private ImageView campagindetail_iv_topbanner;
	private LinearLayout campagindetail_layout_name;
	private TextView campagindetail_tv_name;
	private TextView campagindetail_tv_time;
	protected int nowScrollY;
	private ImageView pagetop_iv_line;
	private CharSequence campagin_name;
	private HashMap<String, String> mhashmap;
	private String uid;
	private String activity_id;
	private TextView campagindetail_tv_address;
	private TextView campagindetail_tv_timeduan;
	private TextView campagindetail_tv_num;
	private TextView campagindetail_tv_content;
	// 首先在您的Activity中添加如下成员变量
	private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	private ImageLoader asyncImageLoader;
	/**
	 * 0未报名，1报名
	 */
	private String is_join = "";
	private CampaignSignupUtils mMainCampaignSignupUtils;
	private TextView campaigndetail_tv_baomingnum;
	private MyGridView campaigndetail_lv_baoming;
	private LinearLayout campagindetail_layout_foot;
	private FileCache fileCache;
	private TextView campagindetail_tv_clubname;
	private TextView campagindetail_tv_authorname;
	private TextView campagindetail_tv_authorrule;
	private TextView campagindetail_tv_tel;
	private LinearLayout campagindetail_layout_author;
	private ImageView campagindetail_iv_clubicon;

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doback();
			break;
		case R.id.pagetop_layout_you:
			String appID = "wx1d30dc3cd2adc80c";
			String appSecret = "2b129f9c81e4a75de4c32ace80f83b9a";
			// http://test.bd.app.bestdo.com/2.5.0/clubservice/activityShare?activity_id=1
			String webpageUrl = "";
			if (!TextUtils.isEmpty(share_url)) {
				webpageUrl = share_url;
			} else {
				webpageUrl = Constans.CAMPAIGNDETAILSHARE + "?activity_id=" + activity_id;
			}

			// 设置分享内容
			mController.setShareContent(rule);
			// 设置分享图片, 参数2为图片的url地址
			fileCache = new FileCache(context);
			File f = fileCache.getFile(logo);
			Bitmap bitmap = fileCache.decodeFile(f);
			mController.setShareMedia(new UMImage(context, bitmap));
			mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE);

			/**
			 * 添加微信平台
			 */
			UMWXHandler wxHandler = new UMWXHandler(context, appID, appSecret);
			wxHandler.addToSocialSDK();
			wxHandler.setTargetUrl(webpageUrl);
			wxHandler.setTitle(campagin_name + "，速来报名！");
			/**
			 * 添加微信朋友圈
			 */
			UMWXHandler wxCircleHandler = new UMWXHandler(context, appID, appSecret);
			wxCircleHandler.setToCircle(true);
			wxCircleHandler.addToSocialSDK();
			// 设置微信朋友圈分享内容
			CircleShareContent circleMedia = new CircleShareContent();
			circleMedia.setShareContent(rule);
			circleMedia.setTitle(campagin_name + "，速来报名！");
			circleMedia.setShareImage(new UMImage(context, bitmap));
			circleMedia.setTargetUrl(webpageUrl);
			mController.setShareMedia(circleMedia);
			mController.openShare(context, false);
			break;
		case R.id.click_btn:
			if (!TextUtils.isEmpty(is_edit) && !is_edit.equals("0")) {
				if (mMainCampaignSignupUtils.clickloadingoverstatus) {
					mMainCampaignSignupUtils.uid = uid;

					if (!TextUtils.isEmpty(is_join) && is_join.equals("1")) {
						// 已报名，点击取消报名
						cancelBaomingDialog();
					} else {
						mMainCampaignSignupUtils.clickloadingoverstatus = false;
						mMainCampaignSignupUtils.addCampaignSignup();
					}
				}
			} else {
				CommonUtils.getInstance().initToast(context, "报名时间段已过，不能操作了！");
			}
			break;
		case R.id.campagindetail_tv_address:
			// Intent intent = new Intent(this, StadiumDetailMapActivity.class);
			// intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			// intent.putExtra("latitude", "" + latitude);
			// intent.putExtra("longitude", "" + longitude);
			// intent.putExtra("stadium_name", campagin_name);
			// intent.putExtra("min_price", rule);
			// startActivity(intent);
			// CommonUtils.getInstance().setPageIntentAnim(intent, this);
			break;
		case R.id.campagindetail_layout_foot:
			Intent intents = new Intent(this, CampaignBaoMingUsersListActivity.class);
			intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intents.putExtra("uid", "" + uid);
			intents.putExtra("activity_id", "" + activity_id);
			intents.putExtra("intentType", "CampaignDetailActivity");
			startActivity(intents);
			CommonUtils.getInstance().setPageIntentAnim(intents, this);
			break;
		case R.id.campagindetail_layout_author:
			CommonUtils.getInstance().getPhoneToKey(context, tel);
			break;
		default:
			break;
		}
	}

	public void cancelBaomingDialog() {
		final MyDialog selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_logout);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView myexit_text = (TextView) selectDialog.findViewById(R.id.myexit_text);
		TextView myexit_text_cirt = (TextView) selectDialog.findViewById(R.id.myexit_text_cirt);
		TextView text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);// 取消
		final TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);// 确定

		myexit_text.setText("取消报名");
		myexit_text_cirt.setText("确定不参加比赛了？");
		text_off.setText("取消");
		text_sure.setText("不参加了");
		text_sure.setTextColor(getResources().getColor(R.color.btn_blue_color));
		text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}
		});
		text_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				mMainCampaignSignupUtils.clickloadingoverstatus = false;
				mMainCampaignSignupUtils.cancelCampaignSignup();
			}
		});
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.campaign_detail);
		CommonUtils.getInstance().addActivity(this);

	}

	@Override
	protected void findViewById() {
		page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		CommonUtils.getInstance().setViewTopHeigth(context, page_top_layout);
		page_top_layout.getBackground().setAlpha(0);
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_iv_back = (ImageView) findViewById(R.id.pagetop_iv_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_layout_back.setFocusable(true);
		pagetop_layout_back.setFocusableInTouchMode(true);
		pagetop_layout_back.requestFocus();
		pagetop_layout_you = (LinearLayout) findViewById(R.id.pagetop_layout_you);
		pagetop_iv_you = (ImageView) findViewById(R.id.pagetop_iv_you);
		pagetop_iv_line = (ImageView) findViewById(R.id.pagetop_iv_line);
		click_btn = (Button) findViewById(R.id.click_btn);

		campagindetail_scrollview = (PullableScrollView) findViewById(R.id.campagindetail_scrollview);
		campagindetail_ralat_topbanner = (RelativeLayout) findViewById(R.id.campagindetail_ralat_topbanner);
		campagindetail_iv_topbanner = (ImageView) findViewById(R.id.campagindetail_iv_topbanner);
		campagindetail_layout_name = (LinearLayout) findViewById(R.id.campagindetail_layout_name);
		campagindetail_tv_name = (TextView) findViewById(R.id.campagindetail_tv_name);
		campagindetail_tv_time = (TextView) findViewById(R.id.campagindetail_tv_time);
		campagindetail_ralat_topbanner.getBackground().setAlpha(51);
		pagetop_iv_you.setVisibility(View.VISIBLE);

		campagindetail_tv_address = (TextView) findViewById(R.id.campagindetail_tv_address);
		campagindetail_tv_timeduan = (TextView) findViewById(R.id.campagindetail_tv_timeduan);
		campagindetail_tv_num = (TextView) findViewById(R.id.campagindetail_tv_num);
		campagindetail_tv_content = (TextView) findViewById(R.id.campagindetail_tv_content);

		campagindetail_layout_author = (LinearLayout) findViewById(R.id.campagindetail_layout_author);
		campagindetail_tv_clubname = (TextView) findViewById(R.id.campagindetail_tv_clubname);
		campagindetail_tv_authorname = (TextView) findViewById(R.id.campagindetail_tv_authorname);
		campagindetail_tv_authorrule = (TextView) findViewById(R.id.campagindetail_tv_authorrule);
		campagindetail_tv_tel = (TextView) findViewById(R.id.campagindetail_tv_tel);
		campagindetail_iv_clubicon = (ImageView) findViewById(R.id.campagindetail_iv_clubicon);

		campagindetail_layout_foot = (LinearLayout) findViewById(R.id.campagindetail_layout_foot);
		campaigndetail_lv_baoming = (MyGridView) findViewById(R.id.campaigndetail_lv_baoming);
		campaigndetail_tv_baomingnum = (TextView) findViewById(R.id.campaigndetail_tv_baomingnum);
	}

	@Override
	protected void setListener() {
		campagindetail_layout_foot.setOnClickListener(this);
		campagindetail_tv_address.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		pagetop_layout_you.setOnClickListener(this);
		campagindetail_layout_author.setOnClickListener(this);
		click_btn.setOnClickListener(this);
		campagindetail_scrollview.setOnScrollChangedListener(new PullableScrollView.OnScrollChangedListener() {

			public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
				nowScrollY = t;
				setTopApha();
			}
		});
		Intent intent = getIntent();
		uid = intent.getStringExtra("uid");
		activity_id = intent.getStringExtra("activity_id");
		mMainCampaignSignupUtils = new CampaignSignupUtils(context, activity_id, uid, mDetailHandler, RELOAD);
		setTopApha();
	}

	Handler mDetailHandler = new Handler() {
		@SuppressLint("NewApi")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case RELOAD:
				processLogic();
				break;

			}
		}
	};

	@SuppressLint("UseValueOf")
	private void setTopApha() {
		if (campagindetail_ralat_topbanner != null) {
			int lHeight = campagindetail_ralat_topbanner.getHeight();
			int lHeight2 = page_top_layout.getHeight();
			if (nowScrollY == 0) {
				page_top_layout.getBackground().setAlpha(0);
				pagetop_iv_line.setVisibility(View.GONE);
				pagetop_tv_name.setText("");
				pagetop_iv_back.setBackgroundResource(R.drawable.back);
				pagetop_iv_you.setBackgroundResource(R.drawable.campaigndetail_img_share);
			} else if (nowScrollY < (lHeight - lHeight2)) {// =
				int progress = (int) (new Float(nowScrollY) / new Float(lHeight + 1) * 255);// 255
				// 参数a为透明度，取值范围为0-255，数值越小越透明。
				page_top_layout.getBackground().setAlpha(progress);
				pagetop_tv_name.setText(club_name);
				pagetop_iv_line.setVisibility(View.GONE);
				pagetop_iv_back.setBackgroundResource(R.drawable.back);
				pagetop_iv_you.setBackgroundResource(R.drawable.campaigndetail_img_share);
			} else {
				pagetop_tv_name.setText(club_name);
				pagetop_iv_line.setVisibility(View.VISIBLE);
				page_top_layout.getBackground().setAlpha(255);
				pagetop_iv_back.setBackgroundResource(R.drawable.back);
				pagetop_iv_you.setBackgroundResource(R.drawable.campaigndetail_img_share);
			}
		}
	}

	private ProgressDialog mDialog;
	private String rule;
	private double longitude;
	private double latitude;
	private String share_url;
	private String is_edit;
	private String logo;
	private String tel;
	private String club_name;

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(this);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void processLogic() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("activity_id", activity_id);
		new CampaignDetailBusiness(context, mhashmap, new CampaignDetailCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					if (code.equals("200")) {
						ArrayList<CampaignDetailInfo> mbaomingrenList = (ArrayList<CampaignDetailInfo>) dataMap
								.get("mbaomingrenList");
						showFoot(mbaomingrenList);
						CampaignDetailInfo mCampaignDetailInfo = (CampaignDetailInfo) dataMap
								.get("mCampaignDetailInfo");
						showContentView(mCampaignDetailInfo);
						CampaignDetailInfo mauthorInfo = (CampaignDetailInfo) dataMap.get("mauthorInfo");
						showAuthorView(mauthorInfo);
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg + "");
					}

				}
				CommonUtils.getInstance().setClearCacheDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	private void showAuthorView(CampaignDetailInfo mCampaignDetailInfo) {
		if (mCampaignDetailInfo != null) {
			campagindetail_layout_author.setVisibility(View.VISIBLE);
			club_name = mCampaignDetailInfo.getClub_name();
			campagindetail_tv_clubname.setText(club_name);
			campagindetail_tv_authorname.setText(mCampaignDetailInfo.getUser_name());
			campagindetail_tv_authorrule.setText(mCampaignDetailInfo.getUser_role());
			tel = mCampaignDetailInfo.getUser_mobile();
			campagindetail_tv_tel.setText(tel);

			String club_icon = mCampaignDetailInfo.getClub_icon();
			if (TextUtils.isEmpty(club_icon)) {
				campagindetail_iv_clubicon.setBackgroundResource(R.drawable.campaigndetail_img_manage);
			} else {
				asyncImageLoader.DisplayImage(club_icon, campagindetail_iv_clubicon);
			}

		} else {
			campagindetail_layout_author.setVisibility(View.GONE);
		}
	}

	private void showFoot(ArrayList<CampaignDetailInfo> mbaomingrenList) {
		if (mbaomingrenList != null && mbaomingrenList.size() > 0) {
			campaigndetail_tv_baomingnum.setText(mbaomingrenList.get(0).getAll_nums() + "人");
			if (mbaomingrenList.size() > 9) {
				ArrayList<CampaignDetailInfo> activityUserInfosList_ = new ArrayList<CampaignDetailInfo>();
				for (int i = 0; i < 9; i++) {
					activityUserInfosList_.add(mbaomingrenList.get(i));
				}
				CampaignDetailBaomingAdapter mAdapter = new CampaignDetailBaomingAdapter(context,
						activityUserInfosList_);
				campaigndetail_lv_baoming.setAdapter(mAdapter);

			} else {
				CampaignDetailBaomingAdapter mAdapter = new CampaignDetailBaomingAdapter(context, mbaomingrenList);
				campaigndetail_lv_baoming.setAdapter(mAdapter);
			}
		} else {
			CampaignDetailBaomingAdapter mAdapter = new CampaignDetailBaomingAdapter(context,
					new ArrayList<CampaignDetailInfo>());
			campaigndetail_lv_baoming.setAdapter(mAdapter);
			campaigndetail_tv_baomingnum.setText("0人");
		}
	}

	private void showContentView(CampaignDetailInfo mCampaignDetailInfo) {
		if (mCampaignDetailInfo != null) {
			asyncImageLoader = new ImageLoader(context, "listdetail");
			share_url = mCampaignDetailInfo.getShare_url();
			campagin_name = mCampaignDetailInfo.getName();
			String time_str = mCampaignDetailInfo.getTime_str();
			campagindetail_tv_name.setText(campagin_name);
			campagindetail_tv_time.setText(time_str);
			logo = mCampaignDetailInfo.getLogo();
			if (TextUtils.isEmpty(logo)) {
				campagindetail_iv_topbanner.setBackgroundResource(R.drawable.campign_imgbanner);
			} else {
				asyncImageLoader.DisplayImage(logo, campagindetail_iv_topbanner);
			}
			longitude = mCampaignDetailInfo.getLongitude();
			latitude = mCampaignDetailInfo.getLatitude();
			String address = mCampaignDetailInfo.getSitus();
			String timestr = mCampaignDetailInfo.getContenttime_str();
			String max_peo = mCampaignDetailInfo.getMax_peo();
			rule = mCampaignDetailInfo.getRule();
			campagindetail_tv_address.setText(address);
			campagindetail_tv_timeduan.setText(timestr);
			campagindetail_tv_num.setText(max_peo);
			campagindetail_tv_content.setText(rule);
			is_join = mCampaignDetailInfo.getIs_join();
			is_edit = mCampaignDetailInfo.getIs_edit();
			if (!TextUtils.isEmpty(is_join) && is_join.equals("1")) {
				click_btn.setText("取消报名");
				click_btn.setTextColor(getResources().getColor(R.color.white));
				click_btn.setBackgroundColor(getResources().getColor(R.color.text_hint_color));
			} else {
				click_btn.setText("报名");
				click_btn.setTextColor(getResources().getColor(R.color.white));
				click_btn.setBackgroundColor(getResources().getColor(R.color.btn_blue_color));
			}
			mCampaignDetailInfo = null;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (asyncImageLoader != null)
			asyncImageLoader.clearCache();
	}

	private void doback() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doback();
		}
		return false;
	}
}
