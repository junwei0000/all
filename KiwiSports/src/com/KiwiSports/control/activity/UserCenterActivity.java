package com.KiwiSports.control.activity;

import java.util.HashMap;
import java.util.UUID;

import com.KiwiSports.R;
import com.KiwiSports.business.UserLogoutBusiness;
import com.KiwiSports.business.UserLogoutBusiness.GetLogoutCallback;
import com.KiwiSports.model.UserLoginInfo;
import com.KiwiSports.utils.CircleImageView;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constans;
import com.KiwiSports.utils.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：个人中心
 */
public class UserCenterActivity extends BaseActivity {

	private TextView usrecenter_tv_cancel;
	private TextView usrecenter_tv_name;
	private CircleImageView usrecenter_iv_head;
	private SharedPreferences bestDoInfoSharedPrefs;
	private Activity mHomeActivity;
	private String uid;
	private String token;
	private String access_token;
	private CheckBox cb_mylocation;
	private CheckBox cb_myanonlocation;
	private CheckBox cb_lowpower;
	private CheckBox cb_voice;
	private SharedPreferences welcomeSharedPreferences;
	private Editor welcomeEditor;

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.usrecenter_tv_cancel:
			CommonUtils.getInstance().defineBackPressed(mHomeActivity, mCancelHandler, CANCEL,
					Constans.getInstance().cancel);
			break;
		case R.id.usrecenter_iv_head:
			intent = new Intent(mHomeActivity, UserAccountActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivityForResult(intent, 1);
			CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_center);
		mHomeActivity = Constans.getInstance().mHomeActivity;
	}

	@Override
	protected void findViewById() {
		usrecenter_tv_cancel = (TextView) findViewById(R.id.usrecenter_tv_cancel);
		usrecenter_iv_head = (CircleImageView) findViewById(R.id.usrecenter_iv_head);
		usrecenter_tv_name = (TextView) findViewById(R.id.usrecenter_tv_name);
		cb_mylocation = (CheckBox) findViewById(R.id.cb_mylocation);
		cb_myanonlocation = (CheckBox) findViewById(R.id.cb_myanonlocation);
		cb_lowpower = (CheckBox) findViewById(R.id.cb_lowpower);
		cb_voice = (CheckBox) findViewById(R.id.cb_voice);
	}

	@Override
	protected void setListener() {
		String welcomeSPFKey = Constans.getInstance().welcomeSharedPrefsKey;
		welcomeSharedPreferences = context.getSharedPreferences(welcomeSPFKey, 0);
		welcomeEditor = welcomeSharedPreferences.edit();
		usrecenter_tv_cancel.setOnClickListener(this);
		usrecenter_iv_head.setOnClickListener(this);
		initSelectBox();
		cb_mylocation.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked && cb_myanonlocation.isChecked()) {
					cb_myanonlocation.setChecked(false);
				}
				saveSelectBox("cb_mylocationstatus", isChecked);
			}
		});
		cb_myanonlocation.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked && cb_mylocation.isChecked()) {
					cb_mylocation.setChecked(false);
				}
				saveSelectBox("cb_myanonlocationstatus", isChecked);
			}
		});

		cb_lowpower.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				saveSelectBox("cb_lowpowerstatus", isChecked);
			}
		});
		cb_voice.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				saveSelectBox("cb_voicestatus", isChecked);
			}
		});
	}

	private void initSelectBox() {
		boolean cb_mylocationstatus = welcomeSharedPreferences.getBoolean("cb_mylocationstatus", false);
		boolean cb_myanonlocationstatus = welcomeSharedPreferences.getBoolean("cb_myanonlocationstatus", true);
		boolean cb_lowpowerstatus = welcomeSharedPreferences.getBoolean("cb_lowpowerstatus", false);
		boolean cb_voicestatus = welcomeSharedPreferences.getBoolean("cb_voicestatus", false);

		if (cb_mylocationstatus) {
			cb_mylocation.setChecked(true);
		} else {
			cb_mylocation.setChecked(false);
		}
		if (cb_myanonlocationstatus) {
			cb_myanonlocation.setChecked(true);
		} else {
			cb_myanonlocation.setChecked(false);
		}
		if (cb_lowpowerstatus) {
			cb_lowpower.setChecked(true);
		} else {
			cb_lowpower.setChecked(false);
		}
		if (cb_voicestatus) {
			cb_voice.setChecked(true);
		} else {
			cb_voice.setChecked(false);
		}
	}

	private void saveSelectBox(String key, boolean value) {
		welcomeEditor.putBoolean(key, value);
		welcomeEditor.commit();
	}

	@Override
	protected void processLogic() {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		ImageLoader asyncImageLoader = new ImageLoader(context, "headImg");
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		token = bestDoInfoSharedPrefs.getString("token", "");
		access_token = bestDoInfoSharedPrefs.getString("access_token", "");
		String nick_name = bestDoInfoSharedPrefs.getString("nick_name", "");
		String album_url = bestDoInfoSharedPrefs.getString("album_url", "");
		usrecenter_tv_name.setText(nick_name);
		if (!TextUtils.isEmpty(album_url)) {
			asyncImageLoader.DisplayImage(album_url, usrecenter_iv_head);
		} else {
			Bitmap mBitmap = asyncImageLoader.readBitMap(this, R.drawable.user_default_icon);
			usrecenter_iv_head.setImageBitmap(mBitmap);
		}
	}

	private final int CANCEL = 22;
	Handler mCancelHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CANCEL:
				logout();
				break;
			}
		}
	};
	private HashMap<String, String> mhashmap;

	private void logout() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		new UserLogoutBusiness(mHomeActivity, mhashmap, new GetLogoutCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						CommonUtils.getInstance().clearAllBestDoInfoSharedPrefs(mHomeActivity);
						CommonUtils.getInstance().setLoginBack403(mHomeActivity);
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		processLogic();
	}

	/**
	 * 退出监听
	 */
	public void onBackPressed() {
		Intent intents = new Intent();
		intents.setAction(getString(R.string.action_home));
		intents.putExtra("type", getString(R.string.action_home_type_gotohome));
		sendBroadcast(intents);
	}

}
