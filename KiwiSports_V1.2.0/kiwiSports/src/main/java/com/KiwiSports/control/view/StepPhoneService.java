package com.KiwiSports.control.view;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.RemoteException;
import android.util.Log;

import com.KiwiSports.control.locationService.Utils;
import com.KiwiSports.utils.CommonUtils;

/**
 * Created by fySpring
 * Date : 2017/3/24
 * To do :调用手机自带计步
 */

public class StepPhoneService extends Service {
    //传感器
    private SensorManager sensorManager;

    private StepPhoneDetector detector;

    //    public static final int MSG_FROM_CLIENT = 0;
//    public static final int MSG_FROM_SERVER = 1;
    @Override
    public void onCreate() {
        super.onCreate();
        if (sensorManager != null) {
            sensorManager = null;
        }
        // 获取传感器管理器的实例
        sensorManager = (SensorManager) this
                .getSystemService(SENSOR_SERVICE);
        //android4.4以后可以使用计步传感器
        int VERSION_CODES = Build.VERSION.SDK_INT;
        if (VERSION_CODES >= 19) {
            // 创建监听器类，实例化监听对象
            detector = new StepPhoneDetector(this);
            Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            if (countSensor != null) {
                detector.setStepSensor(0);
                sensorManager.registerListener(detector, countSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else if (detectorSensor != null) {
                detector.setStepSensor(1);
                sensorManager.registerListener(detector, detectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
            wl.acquire();
        }
    }

    //发送消息，用来和Service之间传递步数
//    private Messenger messenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    /**
//     * 自定义handler
//     */
//    private class MessengerHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case MSG_FROM_CLIENT:
//                    try {
//                        SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance()
//                                .getBestDoInfoSharedPrefs(getBaseContext());
//                        Log.e("stepssteps", "MessengerHandler   s=" + bestDoInfoSharedPrefs.getString("stepStatus", ""));
//                        String stepStatus = bestDoInfoSharedPrefs.getString("stepStatus", "");
//                        if(stepStatus.equals("end")){
//                            detector.CURRENT_STEP=0;
//                        }
//                        //这里负责将当前的步数发送出去，可以在界面或者其他地方获取，我这里是在MainActivity中获取来更新界面
//                        Messenger messenger = msg.replyTo;
//                        Message replyMsg = Message.obtain(null, MSG_FROM_SERVER);
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("steps", detector.CURRENT_STEP);
//                        replyMsg.setData(bundle);
//                        messenger.send(replyMsg);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                default:
//                    super.handleMessage(msg);
//            }
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (detector != null) {
            sensorManager.unregisterListener(detector);
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

}
