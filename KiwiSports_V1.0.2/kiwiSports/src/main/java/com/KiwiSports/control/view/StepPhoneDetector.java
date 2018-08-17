package com.KiwiSports.control.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.KiwiSports.utils.App;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constants;

/**
 * 作者：jun on
 * 时间：2018/5/24 11:05
 * 意图：
 */

public class StepPhoneDetector implements SensorEventListener {
    private final SharedPreferences bestDoInfoSharedPrefs;
    Context context;
    //计步传感器类型 0-counter 1-detector
    private int stepSensor = -1;
    //是否有当天的记录
    private boolean hasRecord;
    //未记录之前的步数
    private int hasStepCount;
    //下次记录之前的步数
    private int previousStepCount;
    //当前步数
    public   int CURRENT_STEP = 0;

    public void setStepSensor(int stepSensor) {
        this.stepSensor = stepSensor;
    }

    public StepPhoneDetector(Context context) {
        super();
        this.context = context;
        bestDoInfoSharedPrefs = CommonUtils.getInstance()
                .getBestDoInfoSharedPrefs(context);
        Log.e("stepssteps", "Constants.getInstance().stepStatus=" + bestDoInfoSharedPrefs.getString("stepStatus", ""));
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        boolean haveCachingDBAndLoad = bestDoInfoSharedPrefs.getBoolean("haveCachingDBAndLoad", false);
        if(haveCachingDBAndLoad){
            CURRENT_STEP=bestDoInfoSharedPrefs.getInt("step",0);
            Log.e("stepssteps", "haveCachingDBAndLoad=" + CURRENT_STEP);

            SharedPreferences.Editor medit = bestDoInfoSharedPrefs.edit();
            medit.putBoolean("haveCachingDBAndLoad", false);
            medit.commit();
        }
        mStepHandler.sendEmptyMessage(11);
        String stepStatus = bestDoInfoSharedPrefs.getString("stepStatus", "");
//        Log.e("stepssteps", "stepStatus stepStatus=" + stepStatus);
        if (stepSensor == 0) {
            int tempStep = (int) event.values[0];
            if (stepStatus.equals("pause")) {
                hasRecord = true;
                hasStepCount = tempStep;
                previousStepCount = tempStep - hasStepCount;
            } else if (stepStatus.equals("end")) {
                hasRecord = true;
                hasStepCount = tempStep;
                previousStepCount = tempStep - hasStepCount;
                CURRENT_STEP = 0;
            } else if (stepStatus.equals("start")) {
            }
            if (!hasRecord) {
                hasRecord = true;
                hasStepCount = tempStep;
            } else {
                int thisStepCount = tempStep - hasStepCount;
                CURRENT_STEP += (thisStepCount - previousStepCount);
                previousStepCount = thisStepCount;
            }
            App.getInstance().savaInfoToSD("CURRENT_STEP==" + CURRENT_STEP);
//            Log.e("stepssteps", "CURRENT_STEP==" + CURRENT_STEP);
//            Log.e("stepssteps", "stepSensor == 0；；tempStep=" + tempStep + ";;hasStepCount==" + hasStepCount + "  " + previousStepCount);
        } else if (stepSensor == 1) {
            if (event.values[0] == 1.0) {
                if (stepStatus.equals("pause")) {
                } else if (stepStatus.equals("end")) {
                    CURRENT_STEP = 0;
                } else if (stepStatus.equals("start")) {
                    CURRENT_STEP++;
                }
            }
//            Log.e("stepssteps", "stepSensor == 1");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @SuppressLint("HandlerLeak")
    Handler mStepHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //这里用来获取到Service发来的数据
                case 11:
                    String stepStatus = bestDoInfoSharedPrefs.getString("stepStatus", "");
//                    Log.e("stepssteps", "mStepHandler mStepHandler========" + stepStatus);
                    if(stepStatus.equals("end")){
                       CURRENT_STEP=0;
                    }
                    Intent intent = new Intent(Constants.RECEIVER_ACTION_STEP);
                    intent.putExtra("CURRENT_STEP_NOW", CURRENT_STEP);
                    context.sendBroadcast(intent);
                    break;
            }
        }
    };

}
