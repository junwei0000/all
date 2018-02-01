package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.VenuesRankAllBusiness;
import com.KiwiSports.business.VenuesRankAllBusiness.GetVenuesRankAllCallback;
import com.KiwiSports.business.VenuesRankTodayBusiness;
import com.KiwiSports.business.VenuesRankTodayBusiness.GetVenuesRankTodayCallback;
import com.KiwiSports.business.VenuesTypeBusiness;
import com.KiwiSports.business.VenuesTypeBusiness.GetVenuesTypeCallback;
import com.KiwiSports.control.adapter.VenuesHobbyAdapter;
import com.KiwiSports.control.adapter.VenuesRankAdapter;
import com.KiwiSports.model.HobbyInfo;
import com.KiwiSports.model.VenuesRankTodayInfo;
import com.KiwiSports.utils.CircleImageView;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constans;
import com.KiwiSports.utils.ImageLoader;
import com.KiwiSports.utils.PriceUtils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：
 */
public class VenuesRankActivity extends BaseActivity {

	private ListView mListView;
	private HashMap<String, String> mhashmap;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String uid;
	private String token;
	private String access_token;
	private LinearLayout pagetop_layout_back;
	private String posid;
	private TextView pagetop_tv_name;
	private TextView tv_today;
	private TextView tv_todayline;
	private TextView tv_all;
	private TextView tv_allline;
	private View convertView;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.tv_today:
			todayStatus = true;
			initBottom();
			processLogic();
			break;
		case R.id.tv_all:
			todayStatus = false;
			initBottom();
			getRankAll();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.venues_rank);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		mListView = (ListView) findViewById(R.id.list_date);

		tv_today = (TextView) findViewById(R.id.tv_today);
		tv_todayline = (TextView) findViewById(R.id.tv_todayline);
		tv_all = (TextView) findViewById(R.id.tv_all);
		tv_allline = (TextView) findViewById(R.id.tv_allline);
		initBottom();
	}

	@SuppressLint("NewApi")
	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		tv_today.setOnClickListener(this);
		tv_all.setOnClickListener(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		token = bestDoInfoSharedPrefs.getString("token", "");
		access_token = bestDoInfoSharedPrefs.getString("access_token", "");

		Intent intent = getIntent();
		String name = intent.getExtras().getString("name", "");
		posid = intent.getExtras().getString("posid", "");
		pagetop_tv_name.setText(name);

		initHeadView();
	}

	LinearLayout rankitemtop_layout2;
	private CircleImageView rankitemtop_iv_head2;
	private TextView rankitemtop_tv_name2;
	private TextView rankitemtop_tv_num2;
	private LinearLayout rankitemtop_layout1;
	private CircleImageView rankitemtop_iv_head1;
	private TextView rankitemtop_tv_name1;
	private TextView rankitemtop_tv_num1;
	private LinearLayout rankitemtop_layout3;
	private CircleImageView rankitemtop_iv_head3;
	private TextView rankitemtop_tv_name3;
	private TextView rankitemtop_tv_num3;
	private ImageLoader asyncImageLoader;

	private void initHeadView() {
		asyncImageLoader = new ImageLoader(context, "headImg");
		convertView = LayoutInflater.from(context).inflate(R.layout.venues_rank_item_top, null);
		rankitemtop_layout2 = (LinearLayout) convertView.findViewById(R.id.rankitemtop_layout2);
		rankitemtop_iv_head2 = (CircleImageView) convertView.findViewById(R.id.rankitemtop_iv_head2);
		rankitemtop_tv_name2 = (TextView) convertView.findViewById(R.id.rankitemtop_tv_name2);
		rankitemtop_tv_num2 = (TextView) convertView.findViewById(R.id.rankitemtop_tv_num2);

		rankitemtop_layout1 = (LinearLayout) convertView.findViewById(R.id.rankitemtop_layout1);
		rankitemtop_iv_head1 = (CircleImageView) convertView.findViewById(R.id.rankitemtop_iv_head1);
		rankitemtop_tv_name1 = (TextView) convertView.findViewById(R.id.rankitemtop_tv_name1);
		rankitemtop_tv_num1 = (TextView) convertView.findViewById(R.id.rankitemtop_tv_num1);

		rankitemtop_layout3 = (LinearLayout) convertView.findViewById(R.id.rankitemtop_layout3);
		rankitemtop_iv_head3 = (CircleImageView) convertView.findViewById(R.id.rankitemtop_iv_head3);
		rankitemtop_tv_name3 = (TextView) convertView.findViewById(R.id.rankitemtop_tv_name3);
		rankitemtop_tv_num3 = (TextView) convertView.findViewById(R.id.rankitemtop_tv_num3);
	}

	boolean todayStatus = true;

	private void initBottom() {
		tv_today.setTextColor(getResources().getColor(R.color.white_light));
		tv_all.setTextColor(getResources().getColor(R.color.white_light));
		tv_todayline.setVisibility(View.INVISIBLE);
		tv_allline.setVisibility(View.INVISIBLE);
		if (todayStatus) {
			tv_today.setTextColor(getResources().getColor(R.color.white));
			tv_todayline.setVisibility(View.VISIBLE);
		} else {
			tv_all.setTextColor(getResources().getColor(R.color.white));
			tv_allline.setVisibility(View.VISIBLE);
		}
	}

	private ProgressDialog mDialog;
	protected ArrayList<VenuesRankTodayInfo> mList;

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

	protected VenuesHobbyAdapter adapter;
	protected ArrayList<VenuesRankTodayInfo> mtopList;
	private int max;

	@Override
	protected void processLogic() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("posid", posid);
		new VenuesRankTodayBusiness(this, mhashmap, new GetVenuesRankTodayCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						mtopList = (ArrayList<VenuesRankTodayInfo>) dataMap.get("mtopList");
						mList = (ArrayList<VenuesRankTodayInfo>) dataMap.get("mList");
						showAdapter();
					}
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	private void showAdapter() {
		mListView.removeHeaderView(convertView);
		if (mtopList != null && mtopList.size() > 0) {
			mListView.addHeaderView(convertView);
			rankitemtop_layout1.setVisibility(View.INVISIBLE);
			rankitemtop_layout2.setVisibility(View.INVISIBLE);
			rankitemtop_layout3.setVisibility(View.INVISIBLE);
			rankitemtop_tv_name1.setText("");
			rankitemtop_tv_num1.setText("");
			rankitemtop_tv_name2.setText("");
			rankitemtop_tv_num2.setText("");
			rankitemtop_tv_name3.setText("");
			rankitemtop_tv_num3.setText("");
			if (mtopList.size() >= 1) {
				rankitemtop_layout1.setVisibility(View.VISIBLE);
				VenuesRankTodayInfo mInfo = mtopList.get(0);
				String album_url = mInfo.getAlbum_url();
				String name = mInfo.getNick_name();
				double diatance = mInfo.getDistanceTraveled();
				diatance = diatance / 1000;
				diatance = PriceUtils.getInstance().getPriceTwoDecimalDouble(diatance, 2);
				/**
				 * 1：匿名； 0：实名
				 */
				int is_anonymous = mInfo.getIs_anonymous();
				if (is_anonymous == 1) {
					album_url="";
					name="匿名雪友";
				}
				if (!TextUtils.isEmpty(album_url)) {
					asyncImageLoader.DisplayImage(album_url, rankitemtop_iv_head1);
				} else {
					Bitmap mBitmap = asyncImageLoader.readBitMap(context, R.drawable.ic_launcher);
					rankitemtop_iv_head1.setImageBitmap(mBitmap);
				}
				rankitemtop_layout1.setBackgroundResource(R.drawable.corners_circular_yellow);
				rankitemtop_tv_name1.setText(name);
				rankitemtop_tv_num1.setText(diatance + "km");
			}
			if (mtopList.size() >= 2) {
				rankitemtop_layout2.setVisibility(View.VISIBLE);
				VenuesRankTodayInfo mInfo = mtopList.get(1);
				String album_url = mInfo.getAlbum_url();
				String name = mInfo.getNick_name();
				double diatance = mInfo.getDistanceTraveled();
				diatance = diatance / 1000;
				diatance = PriceUtils.getInstance().getPriceTwoDecimalDouble(diatance, 2);
				max = (int) (diatance * 1000);
				/**
				 * 1：匿名； 0：实名
				 */
				int is_anonymous = mInfo.getIs_anonymous();
				if (is_anonymous == 1) {
					album_url="";
					name="匿名雪友";
				}
				if (!TextUtils.isEmpty(album_url)) {
					asyncImageLoader.DisplayImage(album_url, rankitemtop_iv_head2);
				} else {
					Bitmap mBitmap = asyncImageLoader.readBitMap(context, R.drawable.ic_launcher);
					rankitemtop_iv_head2.setImageBitmap(mBitmap);
				}
				rankitemtop_layout2.setBackgroundResource(R.drawable.corners_circular_gray);
				rankitemtop_tv_name2.setText(name);
				rankitemtop_tv_num2.setText(diatance + "km");
			}
			if (mtopList.size() >= 3) {
				rankitemtop_layout3.setVisibility(View.VISIBLE);
				VenuesRankTodayInfo mInfo = mtopList.get(2);
				String album_url = mInfo.getAlbum_url();
				String name = mInfo.getNick_name();
				double diatance = mInfo.getDistanceTraveled();
				diatance = diatance / 1000;
				diatance = PriceUtils.getInstance().getPriceTwoDecimalDouble(diatance, 2);
				/**
				 * 1：匿名； 0：实名
				 */
				int is_anonymous = mInfo.getIs_anonymous();
				if (is_anonymous == 1) {
					album_url="";
					name="匿名雪友";
				}
				if (!TextUtils.isEmpty(album_url)) {
					asyncImageLoader.DisplayImage(album_url, rankitemtop_iv_head3);
				} else {
					Bitmap mBitmap = asyncImageLoader.readBitMap(context, R.drawable.ic_launcher);
					rankitemtop_iv_head3.setImageBitmap(mBitmap);
				}
				rankitemtop_layout3.setBackgroundResource(R.drawable.corners_circular_dian);
				rankitemtop_tv_name3.setText(name);
				rankitemtop_tv_num3.setText(diatance + "km");
			}
		}
		if (mList != null && mList.size() > 0) {
		} else {
			mList = new ArrayList<VenuesRankTodayInfo>();
		}
		VenuesRankAdapter mVenuesRankAdapter = new VenuesRankAdapter(context, mList, max);
		mListView.setAdapter(mVenuesRankAdapter);
	}

	protected void getRankAll() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("posid", posid);
		new VenuesRankAllBusiness(this, mhashmap, new GetVenuesRankAllCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						mtopList = (ArrayList<VenuesRankTodayInfo>) dataMap.get("mtopList");
						mList = (ArrayList<VenuesRankTodayInfo>) dataMap.get("mList");
						showAdapter();
					}
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	private void doBack() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doBack();
		}
		return false;
	}
}
