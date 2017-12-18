package com.KiwiSports.control.activity;

import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.UserAccountUpdateBusiness;
import com.KiwiSports.business.VenuesAddBusiness.GetVenuesAddCallback;
import com.KiwiSports.business.VenuesAddBusiness;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.GPSUtil;
import com.KiwiSports.utils.SupplierEditText;
import com.baidu.mapapi.map.MapView;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VenuesAddNextActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private TextView tv_left;
	private TextView tv_right;
	private TextView tv_next;
	private String uid;
	private String token;
	private String access_token;
	private String sportsType;
	private String field_name;
	private String address;
	private ImageView map_iv_cutreslut;
	private String top_left_x;
	private String top_left_y;
	private String bottom_right_x;
	private String bottom_right_y;
	private SupplierEditText tv_name;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.tv_next:
			field_name = tv_name.getText().toString();
			if (!TextUtils.isEmpty(field_name)) {
				processUpdateInfo();
			} else {
				CommonUtils.getInstance().initToast(context, getString(R.string.venues_add_nametishi));
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.venues_addnext);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		map_iv_cutreslut = (ImageView) findViewById(R.id.map_iv_cutreslut);
		tv_left = (TextView) findViewById(R.id.tv_left);
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_next = (TextView) findViewById(R.id.tv_next);
		tv_name = (SupplierEditText) findViewById(R.id.tv_name);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		tv_next.setOnClickListener(this);
		pagetop_tv_name.setText(getString(R.string.venues_title));
	}

	@SuppressLint("NewApi")
	@Override
	protected void processLogic() {
		SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		token = bestDoInfoSharedPrefs.getString("token", "");
		access_token = bestDoInfoSharedPrefs.getString("access_token", "");
		Intent intent = getIntent();
		sportsType = intent.getExtras().getString("sportsType", "");
		field_name = "";
		address = intent.getExtras().getString("address", "");
		try {
			byte[] bis = intent.getByteArrayExtra("bitmap");
			Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
			map_iv_cutreslut.setImageBitmap(bitmap);
		} catch (Exception e) {
		}

		top_left_x = intent.getExtras().getString("top_left_x", "");
		top_left_y = intent.getExtras().getString("top_left_y", "");
		bottom_right_x = intent.getExtras().getString("bottom_right_x", "");
		bottom_right_y = intent.getExtras().getString("bottom_right_y", "");
		tv_left.setText(top_left_y + "," + top_left_x);
		tv_right.setText(bottom_right_y + "," + bottom_right_x);
	}

	private HashMap<String, String> mhashmap;
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

	private void processUpdateInfo() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("field_name", field_name);
		mhashmap.put("sportsType", sportsType);
		mhashmap.put("address", address);

		double[] latlngx = GPSUtil.bd09_To_Gcj02(Double.valueOf(top_left_y), Double.valueOf(top_left_x));
		double[] latlngy = GPSUtil.bd09_To_Gcj02(Double.valueOf(bottom_right_y), Double.valueOf(bottom_right_x));

		mhashmap.put("top_left_x", latlngx[1] + "");
		mhashmap.put("top_left_y", latlngx[0] + "");
		mhashmap.put("bottom_right_x", latlngy[1] + "");
		mhashmap.put("bottom_right_y", latlngy[0] + "");
		Log.e("decrypt----", mhashmap.toString());
		new VenuesAddBusiness(this, mhashmap, new GetVenuesAddCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					String msg = (String) dataMap.get("msg");
					CommonUtils.getInstance().initToast(context, msg);
					if (status.equals("200")) {
						doBack();
					}
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				// 清除缓存
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
