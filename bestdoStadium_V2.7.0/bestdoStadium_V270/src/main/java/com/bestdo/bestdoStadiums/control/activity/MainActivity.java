/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.GetBusinessBannerImgBusiness;
import com.bestdo.bestdoStadiums.business.GetBusinessBannerImgBusiness.GetBusinessBannerImgCallback;
import com.bestdo.bestdoStadiums.control.activity.MainPersonActivity.MyLocationListener;
import com.bestdo.bestdoStadiums.control.adapter.AdvertImagePagerAdapter;
import com.bestdo.bestdoStadiums.control.adapter.BusinessAdvertImagePagerAdapter;
import com.bestdo.bestdoStadiums.control.adapter.BusinessNaviAdapter;
import com.bestdo.bestdoStadiums.control.adapter.MainMenuAdapter;
import com.bestdo.bestdoStadiums.control.view.AutoScrollViewPager;
import com.bestdo.bestdoStadiums.control.view.CirclePageIndicator;
import com.bestdo.bestdoStadiums.control.walking.uploadStepUtils;
import com.bestdo.bestdoStadiums.model.BannerInfo;
import com.bestdo.bestdoStadiums.model.BusinessBannerInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-8-16 下午4:50:49
 * @Description 类描述：首页
 */
public class MainActivity extends BaseActivity {

	private HomeActivity mHomeActivity;
	private AutoScrollViewPager business_viewpager_advert;
	private CirclePageIndicator business_indicator;
	private HashMap<String, String> mHashMap;
	protected ArrayList<BusinessBannerInfo> bannerList;
	private RelativeLayout business_relat_advert;
	private SharedPreferences bestDoInfoSharedPrefs;
	private Editor bestDoInfoEditor;
	private GridView main_mygridview_menu;

	@Override
	public void onClick(View arg0) {

	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.main_business);
		mHomeActivity = (HomeActivity) CommonUtils.getInstance().nHomeActivity;
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
	}

	@Override
	protected void findViewById() {
		business_relat_advert = (RelativeLayout) findViewById(R.id.business_relat_advert);
		business_viewpager_advert = (AutoScrollViewPager) findViewById(R.id.business_viewpager_advert);
		business_indicator = (CirclePageIndicator) findViewById(R.id.business_indicator);
		main_mygridview_menu = (GridView) findViewById(R.id.main_mygridview_menu);
		loadMapLocation();
	}

	// =======================为企业版添加定位提示=========================
	/**
	 * 设置定位
	 */
	private LocationClient mLocationClient = null;
	private LocationClientOption option;
	protected ArrayList<BusinessBannerInfo> menuList;
	private ImageLoader asyncImageLoader;

	private void loadMapLocation() {
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation arg0) {
				// TODO Auto-generated method stub

			}
		}); // 注册监听函数
		new Thread(new Runnable() {

			@Override
			public void run() {
				InitLocation();// 设置定位参数
			}
		}).start();

	}

	private void InitLocation() {
		option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		// LocationMode mLocationMode=LocationMode.Device_Sensors;
		// option.setLocationMode(mLocationMode);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	@Override
	protected void setListener() {
		asyncImageLoader = new ImageLoader(context, "listdetail_ball");
		uploadStepUtils.getInstance().upload(context, context.getSharedPreferences("stepAll_INFO", 0));
		main_mygridview_menu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (menuList != null && menuList.size() != 0) {

					BusinessBannerInfo mBannerInfo = menuList.get(arg2);
					String html_url = mBannerInfo.getUrl();
					String name = mBannerInfo.getName();
					if (!TextUtils.isEmpty(name) && name.equals("个人订场")) {
						Intent intent = new Intent(context, MainPersonActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						context.startActivity(intent);
						CommonUtils.getInstance().setPageIntentAnim(intent, context);
					} else if (!TextUtils.isEmpty(html_url) && !html_url.equals("null")) {
						Boolean status = true;
						CommonUtils.getInstance().toH5(context, html_url, name, "", status);
					}
				}
			}
		});

	}

	protected ArrayList<BusinessBannerInfo> news_list;

	@Override
	protected void processLogic() {
		mHashMap = new HashMap<String, String>();
		new GetBusinessBannerImgBusiness(this, mHashMap, new GetBusinessBannerImgCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {

				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						bannerList = (ArrayList<BusinessBannerInfo>) dataMap.get("bannerList");
						menuList = (ArrayList<BusinessBannerInfo>) dataMap.get("menuList");
						news_list = (ArrayList<BusinessBannerInfo>) dataMap.get("twoNavList");
						// 商城退款图片上传地址
						String shopPay_url = (String) dataMap.get("shopPay_url");
						bestDoInfoEditor.putString("shopPay_url", shopPay_url);
						String navThree_url = (String) dataMap.get("navThree_url");
						String navThree_name = (String) dataMap.get("navThree_name");
						bestDoInfoEditor.putString("navThree_url", navThree_url);
						bestDoInfoEditor.putString("navThree_name", navThree_name);
						bestDoInfoEditor.commit();
						loadBanner();
						loadMenu();
						if (news_list != null && news_list.size() > 2) {
							loadBottm();
						}
					}
					loadDataStatus = false;
				}
				CommonUtils.getInstance().setClearCacheBackDate(mHashMap, dataMap);
			}
		});
	}

	private void loadBottm() {
		addViews(news_list.get(0));
		addViews1(news_list.get(1));
		addViews2(news_list.get(2));
	}

	public void onSkip(View view) {
		String url;
		switch (view.getId()) {
		case R.id.main_bottom_layout_left:
			url = news_list.get(0).getUrl();
			if (!TextUtils.isEmpty(url))
				CommonUtils.getInstance().toH5(context, url, "", "", false);
			break;
		case R.id.main_bottom_layout_rigth1:
			if (checkLoginStatus(Constans.getInstance().loginskiptoMeber)) {
				Intent intent = new Intent(mHomeActivity, UserMeberActiyity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
			}
			break;
		case R.id.main_bottom_layout_rigth2:
			url = news_list.get(2).getUrl();
			if (!TextUtils.isEmpty(url))
				CommonUtils.getInstance().toH5(context, url, "", "", false);
			break;
		default:
			break;
		}
	}

	private boolean checkLoginStatus(String loginskiptostatus) {
		boolean loginstatus = true;
		String loginStatus = bestDoInfoSharedPrefs.getString("loginStatus", "");
		if (!loginStatus.equals(Constans.getInstance().loginStatus)) {
			bestDoInfoEditor.putString("loginskiptostatus", loginskiptostatus);
			bestDoInfoEditor.commit();
			Intent intent2 = new Intent(mHomeActivity, UserLoginActivity.class);
			intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent2);
			CommonUtils.getInstance().setPageIntentAnim(intent2, mHomeActivity);
			loginstatus = false;
		}
		return loginstatus;
	}

	private void addViews1(BusinessBannerInfo mInfo) {
		ImageView business_navitem_img = (ImageView) findViewById(R.id.business_navitem_imgrigth1);
		TextView business_navitem_name = (TextView) findViewById(R.id.business_navitem_namerigth1);
		TextView business_navitem_name2 = (TextView) findViewById(R.id.business_navitem_name2rigth1);
		business_navitem_name2.setText(mInfo.getSub_name());
		business_navitem_name.setText(mInfo.getName());
		String imgPath = mInfo.getImgPath();
		if (!TextUtils.isEmpty(imgPath)) {
			asyncImageLoader.DisplayImage(imgPath, business_navitem_img);
		} else {
			business_navitem_img.setImageBitmap(asyncImageLoader.bitmap_orve);
		}
	}

	private void addViews2(BusinessBannerInfo mInfo) {
		ImageView business_navitem_img = (ImageView) findViewById(R.id.business_navitem_imgrigth2);
		TextView business_navitem_name = (TextView) findViewById(R.id.business_navitem_namerigth2);
		TextView business_navitem_name2 = (TextView) findViewById(R.id.business_navitem_name2rigth2);
		business_navitem_name2.setText(mInfo.getSub_name());
		business_navitem_name.setText(mInfo.getName());
		String imgPath = mInfo.getImgPath();
		if (!TextUtils.isEmpty(imgPath)) {
			asyncImageLoader.DisplayImage(imgPath, business_navitem_img);
		} else {
			business_navitem_img.setImageBitmap(asyncImageLoader.bitmap_orve);
		}
	}

	private void addViews(BusinessBannerInfo mInfo) {
		ImageView business_navitem_img = (ImageView) findViewById(R.id.business_navitem_img);
		TextView business_navitem_name = (TextView) findViewById(R.id.business_navitem_name);
		TextView business_navitem_name2 = (TextView) findViewById(R.id.business_navitem_name2);
		business_navitem_name2.setText(mInfo.getSub_name());
		business_navitem_name.setText(mInfo.getName());
		// business_navitem_name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		String imgPath = mInfo.getImgPath();
		if (!TextUtils.isEmpty(imgPath)) {
			asyncImageLoader.DisplayImage(imgPath, business_navitem_img);
		} else {
			business_navitem_img.setImageBitmap(asyncImageLoader.bitmap_orve);
		}
	}

	private void loadBanner() {
		if (bannerList != null && bannerList.size() != 0) {
			business_viewpager_advert.setAdapter(new BusinessAdvertImagePagerAdapter(mHomeActivity, bannerList));
			business_indicator.setPageColor(getResources().getColor(R.color.text_noclick_color));
			business_indicator.setFillColor(getResources().getColor(R.color.white));
			business_indicator.setViewPager(business_viewpager_advert);
			business_viewpager_advert.setInterval(2000);// 设置自动滚动的间隔时间，单位为毫秒
			business_viewpager_advert.setStopScrollWhenTouch(true);
		} else {
			business_viewpager_advert.setVisibility(View.GONE);
		}
	}

	private void loadMenu() {
		if (menuList != null && menuList.size() != 0) {
			main_mygridview_menu.setVisibility(View.VISIBLE);
			MainMenuAdapter mMainMenuAdapter = new MainMenuAdapter(mHomeActivity, menuList);
			main_mygridview_menu.setAdapter(mMainMenuAdapter);
		} else {
			main_mygridview_menu.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		business_viewpager_advert.stopAutoScroll();
		MobclickAgent.onPageEnd("MainScreen");
	}

	@Override
	protected void onResume() {
		super.onResume();
		business_viewpager_advert.startAutoScroll();
		MobclickAgent.onPageStart("MainScreen");
		if (!loadDataStatus)
			processLogic();
	}

	boolean loadDataStatus = true;

	/**
	 * 退出监听
	 */
	public void onBackPressed() {
		CommonUtils.getInstance().defineBackPressed(mHomeActivity, null, Constans.getInstance().exit);
	}

}
