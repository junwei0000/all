package com.bestdo.bestdoStadiums.control.activity;

import java.io.File;
import java.util.List;

import com.android.volley.VolleyError;
import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.net.BaseObjectResponce;
import com.bestdo.bestdoStadiums.business.net.BaseResponse;
import com.bestdo.bestdoStadiums.business.net.GsonServer;
import com.bestdo.bestdoStadiums.business.net.IBusiness;
import com.bestdo.bestdoStadiums.business.net.RankMyKmResponse;
import com.bestdo.bestdoStadiums.business.net.RankPersonResponse;
import com.bestdo.bestdoStadiums.control.fragment.TodayCountFragment;
import com.bestdo.bestdoStadiums.control.fragment.TodayRunFragment;
import com.bestdo.bestdoStadiums.control.map.BestDoApplication;
import com.bestdo.bestdoStadiums.control.view.ActInfoPopWindow;
import com.bestdo.bestdoStadiums.model.ActInfoMapper;
import com.bestdo.bestdoStadiums.model.ActInfoMapper.ActInfo;
import com.bestdo.bestdoStadiums.model.MyWalkInfoMapper.MyWalkInfoBack;
import com.bestdo.bestdoStadiums.utils.App;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Config;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.DpUtil;
import com.bestdo.bestdoStadiums.utils.ScreenShareUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMShareBoardListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WalkActivity extends BaseActivity {
	private LinearLayout llRoot;
	private TextView tvCount, tvRun;
	private View lineCount, lineRun;
	private ImageView imgShare;
	private FragmentManager fragmentManager;
	private boolean curPageCount;
	private boolean isBottomTouch;
	private float downY;
	private float downX;
	private String file;
	private LinearLayout ll;
	private View bottom, cUserBottom;
	private FrameLayout container;
	private boolean isBuser;
	private long lastTimeGetData;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.act_walk);
	}

	@Override
	protected void findViewById() {
		llRoot = (LinearLayout) findViewById(R.id.ll_walk);
		tvCount = (TextView) findViewById(R.id.tv_t_count);
		tvRun = (TextView) findViewById(R.id.tv_t_run);
		lineCount = findViewById(R.id.t_line1);
		lineRun = findViewById(R.id.t_line2);
		imgShare = (ImageView) findViewById(R.id.img_share);
		container = (FrameLayout) findViewById(R.id.container);
		bottom = findViewById(R.id.bottom);
		cUserBottom = findViewById(R.id.view_cuser);
	}

	@Override
	protected void setListener() {
		tvCount.setOnClickListener(this);
		tvRun.setOnClickListener(this);
	}

	/**
	 * 需对当前页面的数据进行刷新操作，最可行方案为每次页面跳转，当前Actvity finish，但是目前的back操作（跳转HomeActivity）
	 * 不支持finish；只能暂时这样处理
	 */
	@Override
	protected void onResume() {
		refreshData(true);
		super.onResume();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void processLogic() {
		lastClickTime = System.currentTimeMillis();
		lastTimeGetData = lastClickTime;
		isBuser = Config.config().isBuser();
		preShow();
		// refreshData(true);
	}

	/**
	 * 从服务器刷新所有数据
	 */
	private void refreshData(boolean refreshView) {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
		}
		if (Constans.getInstance().walkPageComInstatus) {
			showDilag();
		}
		getMyWeekSteps(refreshView);
		getActInfo();
	}

	@Override
	public void onBackPressed() {
		suBack();
	}

	private void getMyWeekSteps(final boolean refreshView) {
		GsonServer.getMyWeekWalk(new IBusiness() {

			@Override
			public void onSuccess(BaseResponse response) {
				BaseObjectResponce<List<MyWalkInfoBack>> re = (BaseObjectResponce<List<MyWalkInfoBack>>) response;
				List<MyWalkInfoBack> back = re.getObject();
				if (back != null && !back.isEmpty()) {
					Config.config().updateMyWalkInfo(back);
				}
				// 切换用户时刷新布局
				if (Constans.getInstance().walkPageComInstatus) {
					curPageCount = false;
					toCount(false);
				}
				Constans.getInstance().walkPageComInstatus = false;
				getMyOwnWalkInfo(refreshView);
			}

			@Override
			public void onError(VolleyError error) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				onGetError();
			}
		});

	}

	private void getActInfo() {
		if (!isBuser)
			return;
		GsonServer.getActsInfo(new IBusiness() {
			@Override
			public void onSuccess(BaseResponse response) {
				BaseObjectResponce<List<ActInfoMapper.ActInfoBack>> re = (BaseObjectResponce<List<ActInfoMapper.ActInfoBack>>) response;
				List<ActInfoMapper.ActInfoBack> actsInfo = re.getObject();
				if (actsInfo != null && !actsInfo.isEmpty())
					Config.config().setActInfos(ActInfoMapper.map(actsInfo));
			}

			@Override
			public void onError(VolleyError error) {
			}
		});
	}

	private void preShow() {
		bottom.setVisibility(isBuser ? View.VISIBLE : View.GONE);
		cUserBottom.setVisibility(!isBuser ? View.VISIBLE : View.GONE);
		curPageCount = true;
		fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, TodayCountFragment.newInstance(false)).commit();
	}

	private void refreshBaseFragment(boolean refreshView) {
		if (!refreshView)
			return;
		fragmentManager.beginTransaction()
				.replace(R.id.container, curPageCount ? TodayCountFragment.newInstance(true) : new TodayRunFragment())
				.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_t_count:
			toCount(true);
			break;
		case R.id.tv_t_run:
			toRun(true);
			break;
		}
	}

	private void toCount(boolean anim) {
		if (!curPageCount) {
			setTile();
			switchFragment(anim);
		}
	}

	private void toRun(boolean anim) {
		if (curPageCount) {
			setTile();
			switchFragment(anim);
		}
	}

	private void switchFragment(boolean anim) {
		Fragment fragment = curPageCount ? new TodayRunFragment() : TodayCountFragment.newInstance(true);
		FragmentTransaction tran = fragmentManager.beginTransaction();
		if (anim) {
			if (curPageCount) {
				getMyOwnWalkInfo(false);
				tran.setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.fragment_slide_left_exit);
			} else {
				tran.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_right_exit);
			}
		}
		tran.replace(R.id.container, fragment).commit();
		curPageCount = !curPageCount;
	}

	private void setTile() {
		tvCount.setTypeface(!curPageCount ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
		tvRun.setTypeface(curPageCount ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
		lineCount.setVisibility(!curPageCount ? View.VISIBLE : View.INVISIBLE);
		lineRun.setVisibility(curPageCount ? View.VISIBLE : View.INVISIBLE);
		imgShare.setImageResource(!curPageCount ? R.drawable.share : R.drawable.userwalk_run_hostory);
	}

	/**
	 * 防止分享重复点击
	 */
	private long lastClickTime;

	public void onShare(View view) {
		long curTime = System.currentTimeMillis();
		if (curTime - lastClickTime < 1000) {
			return;
		}
		lastClickTime = curTime;
		if (curPageCount) {
			screenShare();
		} else {
			Intent intent = new Intent(this, UserWalkingHistoryActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, this);
		}
	}

	public void onDescribes(View view) {
		Intent intent = new Intent(this, UserWalkingTiShiActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		CommonUtils.getInstance().setPageIntentAnim(intent, this);

	}

	private void screenShare() {
		if (!BestDoApplication.getInstance().msgApi.isWXAppInstalled()) {
			Toast.makeText(this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
			return;
		}
		file = ScreenShareUtil.shotBitmap(container);
		File f = new File(file);
		if (f != null && f.exists() && f.length() > 1000) {
			ViewGroup.LayoutParams top = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					(int) DpUtil.dpToPx(this, 400));
			ll = new LinearLayout(this);
			ll.setBackground(new ColorDrawable(0x88000000));
			ImageView imgView = new ImageView(this);
			params.setMargins(0, (int) DpUtil.dpToPx(this, 50), 0, 0);
			Bitmap bitmap = BitmapFactory.decodeFile(file);
			imgView.setImageBitmap(bitmap);
			ll.addView(imgView, params);
			addContentView(ll, top);
			UMshare(bitmap);
		}

	}

	private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

	private void UMshare(Bitmap bmp) {
		if (bmp != null) {
			String appID = "wx1d30dc3cd2adc80c";
			String appSecret = "2b129f9c81e4a75de4c32ace80f83b9a";
			mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
					SHARE_MEDIA.SINA);
			// 添加微信平台
			UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
			wxHandler.addToSocialSDK();
			// 设置分享内容,防止被重写
			mController.setShareContent(null);
			mController.setShareMedia(new UMImage(this, bmp));
			// 添加微信朋友圈
			UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
			wxCircleHandler.setToCircle(true);
			wxCircleHandler.addToSocialSDK();
			// qq
			UMQQSsoHandler qq = new UMQQSsoHandler(this, "1105290700", "yKdi86YWzF0ApOz6");
			qq.addToSocialSDK();
			QQShareContent qqShareContent = new QQShareContent();
			qqShareContent.setShareImage(new UMImage(this, bmp));
			qqShareContent.setShareContent(null);
			mController.setShareMedia(qqShareContent);
			// 关闭友盟分享的toast
			mController.getConfig().closeToast();
			mController.setShareBoardListener(new UMShareBoardListener() {

				@Override
				public void onShow() {
					Config.d("onShow");
				}

				@Override
				public void onDismiss() {
					Config.d("onDismiss");
					onShareComplete();
				}
			});
			mController.openShare(this, new SnsPostListener() {

				@Override
				public void onStart() {

				}

				@Override
				public void onComplete(SHARE_MEDIA arg0, int arg1, SocializeEntity arg2) {
					if (arg1 == 200) {
						CommonUtils.getInstance().initToast(WalkActivity.this, "分享成功");
					} else {
						CommonUtils.getInstance().initToast(WalkActivity.this, "分享失败");
					}
					onShareComplete();
					Config.d("onShareComplete");
				}

			});
		}
	}

	private void onShareComplete() {
		refreshView();
		deleteShareFile();
	}

	private void refreshView() {
		Config.d("refreshView");
		ll.removeAllViews();
		ll.setVisibility(View.GONE);
	};

	private void deleteShareFile() {
		try {
			File f = new File(file);
			f.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getMyOwnWalkInfo(final boolean refreshView) {
		GsonServer.getRankMyOwn(new IBusiness() {

			@Override
			public void onSuccess(BaseResponse response) {
				BaseObjectResponce<RankPersonResponse> re = (BaseObjectResponce<RankPersonResponse>) response;
				RankPersonResponse reObject = re.getObject();
				if (reObject != null) {
					Config.config().updateMyWalkInfo(reObject, re.timestamp);
				}
				getMyKm(refreshView);
			}

			@Override
			public void onError(VolleyError error) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				onGetError();
			}

		});
	}

	/**
	 * 获取我的walk里程
	 * 
	 * @param refreshView
	 */
	private void getMyKm(final boolean refreshView) {
		GsonServer.getMyWalkKm(new IBusiness() {

			@Override
			public void onSuccess(BaseResponse response) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				BaseObjectResponce<RankMyKmResponse> re = (BaseObjectResponce<RankMyKmResponse>) response;
				RankMyKmResponse reObject = re.getObject();
				Config.config().setMyKm(reObject);
				refreshBaseFragment(refreshView);
			}

			@Override
			public void onError(VolleyError error) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				onGetError();
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			downY = event.getY();
			downX = event.getX();
			isBottomTouch = isBottomTouch(downY);
			return isBottomTouch;
		// break;
		case MotionEvent.ACTION_UP:
			float curY = event.getY();
			float curX = event.getX();
			float min = ViewConfiguration.get(this).getScaledTouchSlop();
			if (downY - curY >= min && isBottomTouch) {
				return onUp();
			} else if (downX - curX >= min) {
				return onLeft();
			} else if (curX - downX >= min)
				return onRight();
			break;

		}
		return super.onTouchEvent(event);
	}

	private boolean onRight() {
		toCount(true);
		return true;
	}

	private boolean onLeft() {
		toRun(true);
		return true;
	}

	/**
	 * 获取服务器数据错误
	 */
	private void onGetError() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			String errorMsg = getString(R.string.net_tishi);
			CommonUtils.getInstance().initToast(this, errorMsg);
		}
		// initData();
	}

	private boolean onUp() {
		if (!isBuser)
			return false;
		SharedPreferences stepAll_INFOSharedPrefs = context.getSharedPreferences("stepAll_INFO", 0);
		int curStep = stepAll_INFOSharedPrefs.getInt(DatesUtils.getInstance().getNowTime("yyyy-MM-dd"), 0);
		GsonServer.uploadTodayStep(curStep);
		getActInfo();
		List<ActInfo> list = Config.config().getActInfos();
		showPopWindow(list);
		return true;
	}

	private void showPopWindow(List<ActInfo> list) {
		final ActInfoPopWindow popWindow = new ActInfoPopWindow(this, list);
		popWindow.showAtLocation(llRoot, Gravity.BOTTOM, 0, 0);
		popWindow.setPopListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ActInfo info = Config.config().getActInfos().get(position);
				popWindow.dismiss();
				CommonUtils.getInstance().toH5(context, info.jumpUrl, "", info.upload_url, false);
			}
		});
	}

	private boolean isBottomTouch(float curY) {
		float screenHeightPx = DpUtil.dpToPx(this, getScreenHDp());
		return curY / screenHeightPx >= 0.4;
	}

	public int getScreenHDp() {
		Configuration config = getResources().getConfiguration();
		return config.screenHeightDp;
	}

	private ProgressDialog mDialog;

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
	protected void onDestroy() {
		CommonUtils.getInstance().setClearCacheDialog(mDialog);
		super.onDestroy();
	}
}
