package com.bestdo.bestdoStadiums.control.walking;

import com.bestdo.bestdoStadiums.utils.DatesUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;

public class uploadStepUtils {
	private static class SingletonHolder {
		private static final uploadStepUtils INSTANCE = new uploadStepUtils();
	}

	private uploadStepUtils() {
	}

	public static final uploadStepUtils getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private SharedPreferences stepAllInfoSharedPrefs;
	Context context;

	public void upload(Context context, SharedPreferences stepAllInfoSharedPrefs) {
		this.context = context;
		this.stepAllInfoSharedPrefs = stepAllInfoSharedPrefs;
		mHandler.sendEmptyMessage(uploadDayBeforeStep);
		mHandler.sendEmptyMessage(uploadDayTwoBeforeStep);
	}

	private final int uploadDayBeforeStep = 32;
	private final int uploadDayTwoBeforeStep = 22;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case uploadDayBeforeStep:
				uploadDayBeforeStep(0);
				break;
			case uploadDayTwoBeforeStep:
				uploadDayBeforeStep(-1);
				uploadDayBeforeStep(-2);
				uploadDayBeforeStep(-3);
				uploadDayBeforeStep(-4);
				uploadDayBeforeStep(-5);
				break;
			}
		}
	};
	private StepUploadUtils mStepUploadUtils;

	/**
	 * 每次重启服务判断上传前一天的数据
	 */
	private void uploadDayBeforeStep(int dayindex) {
		String key = DatesUtils.getInstance().getEDate_y(dayindex, "yyyy-MM-dd");
		int stepAll = stepAllInfoSharedPrefs.getInt(key, 0);
		if (stepAll > 0) {
			if (mStepUploadUtils == null) {
				mStepUploadUtils = new StepUploadUtils(context, key, stepAllInfoSharedPrefs);
			}
			mStepUploadUtils.setKey(key);
			mStepUploadUtils.upoadStep(stepAll);
		}
	}

}
