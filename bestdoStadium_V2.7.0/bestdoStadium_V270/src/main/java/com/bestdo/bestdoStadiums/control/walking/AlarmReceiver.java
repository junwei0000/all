/**
 * 
 */
package com.bestdo.bestdoStadiums.control.walking;

import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.bestdo.bestdoStadiums.business.AddUploadDayStepBusiness;
import com.bestdo.bestdoStadiums.business.AddUploadDayStepBusiness.AddUploadDayStepCallback;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;

/**
 * @author 作者：zoc
 * @date 创建时间：2017-3-30 下午4:23:57
 * @Description 类描述：
 */
public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		SharedPreferences stepAll_INFOSharedPrefs = arg0.getSharedPreferences("stepAll_INFO", 0);
		String key = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
		int stepAll = stepAll_INFOSharedPrefs.getInt(key, 0);
		if (stepAll > 0) {
			StepUploadUtils mStepUploadUtils = new StepUploadUtils(arg0, key, stepAll_INFOSharedPrefs);
			mStepUploadUtils.isClear = true;
			mStepUploadUtils.upoadStep(stepAll);
		}
	}

}
