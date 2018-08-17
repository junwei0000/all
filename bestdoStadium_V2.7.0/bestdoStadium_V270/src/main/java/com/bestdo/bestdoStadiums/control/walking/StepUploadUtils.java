/**
 * 
 */
package com.bestdo.bestdoStadiums.control.walking;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;

import com.bestdo.bestdoStadiums.business.AddUploadDayStepBusiness;
import com.bestdo.bestdoStadiums.business.AddUploadDayStepBusiness.AddUploadDayStepCallback;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.DatesUtils;

/**
 * @author 作者：zoc
 * @date 创建时间：2017-4-12 下午1:21:16
 * @Description 类描述：
 */
public class StepUploadUtils {
	private SharedPreferences bestDoInfoSharedPrefs;
	private float km_num;
	private String key;
	private SharedPreferences stepAllInfoSharedPrefs;
	Context mContext;
	public boolean isClear = false;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public StepUploadUtils(Context mContext, String key, SharedPreferences stepAllInfoSharedPrefs) {
		super();
		this.mContext = mContext;
		this.key = key;
		this.stepAllInfoSharedPrefs = stepAllInfoSharedPrefs;
	}

	public void upoadStep(int stepAll) {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(mContext);
		// uid 用户ID string 是
		// bid 企业ID string 是
		// depid 部门D string 是
		// step_num 步数 string 是
		// km_num 公里数 string 是
		// step_num 步数 string 是
		// data_time 上传数据的日期 string 是 例：2017-03-28
		// app_device 手机的唯一标识 string 是

		if (stepAll > 0) {
			HashMap<String, String> mhashmap = new HashMap<String, String>();
			mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
			String bid = bestDoInfoSharedPrefs.getString("bid", "");
			if (bid.equals("nobid")) {
				mhashmap.put("bid", "" + 0);
			} else {
				mhashmap.put("bid", bid);
			}
			mhashmap.put("depid", bestDoInfoSharedPrefs.getString("deptId", ""));
			mhashmap.put("step_num", stepAll + "");
			mhashmap.put("km_num", step2Mileage(stepAll) + "");
			mhashmap.put("data_time", key + "");
			mhashmap.put("app_device", bestDoInfoSharedPrefs.getString("device_token", ""));
			System.err.println(mhashmap);
			new AddUploadDayStepBusiness(mContext, mhashmap, new AddUploadDayStepCallback() {

				@Override
				public void afterDataGet(HashMap<String, Object> dataMap) {
					if (dataMap != null)
						mHandler.sendEmptyMessage(UPDATE);
				}
			});
		}
	}

	private final int UPDATE = 2;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE:

				if (isClear) {
					/**
					 * 服务没重启的情况--防止软引用数据清理但（CURRENT_SETP没重新赋值问题），CURRENT_SETP
					 * 每日11.59从0开始计步
					 */
					StepAllDetector.CURRENT_SETP = 0;
					isClear = false;
					Map dd = stepAllInfoSharedPrefs.getAll();
					if (dd != null && dd.size() > 500) {
						Editor e = stepAllInfoSharedPrefs.edit();
						e.clear();
					}
				}
				break;

			}
		}
	};

	private float step2Mileage(int paramInt) {
		this.km_num = (paramInt * 70 / 100000.0F);
		return this.km_num;
	}
}
