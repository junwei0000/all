package com.longcheng.lifecareplan.push.hwpush;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.api.HuaweiApiClient;
import com.huawei.hms.support.api.client.PendingResult;
import com.huawei.hms.support.api.client.ResultCallback;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.huawei.hms.support.api.push.HuaweiPush;
import com.huawei.hms.support.api.push.PushException;
import com.huawei.hms.support.api.push.TokenResult;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.LogUtils;

import java.lang.ref.WeakReference;


/**
 * Created by markShuai on 2017/11/30.
 */

public final class HWPushClient implements HuaweiApiClient.ConnectionCallbacks, HuaweiApiClient.OnConnectionFailedListener {

    private String TAG = "HWPushClient";
    private WeakReference<Activity> activityWeakReference;
    private Context applicationContext;
    private Handler mHandler;
    private static HuaweiApiClient client;
    private boolean isOpenAutoHandleConnectionFailed = false;
    /**
     * 调用HuaweiApiAvailability.getInstance().resolveError传入的第三个参数
     * 作用同startactivityforresult方法中的requestcode
     **/
    private static int REQUEST_HMS_RESOLVE_ERROR = 1000;
    private HWConnectListener mHWConnectListener;
    private static volatile HWPushClient INSTANCE;

    /**
     * 处理连接失败的回调
     */
    public interface HWConnectListener {

        void onHWConnectionSuccess();

        void onHWConnectionFailed(ConnectionResult connectionResult);

        void onHWConnectionSuspended(int errorCode);

    }

    private HWPushClient(Context context) {
        applicationContext = context.getApplicationContext();
        if (context instanceof Activity) {
            activityWeakReference = new WeakReference<Activity>((Activity) context);
        }
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static HWPushClient getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (HWPushClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HWPushClient(context);
                }
            }
        }
        return INSTANCE;
    }

    private Context getContext() {
        return activityWeakReference != null
                ?
                activityWeakReference.get() != null ? activityWeakReference.get() : applicationContext
                :
                applicationContext;
    }

    public void init() {
        hwPushConnection();
//        openAutoHandleConnectionFailed(true, -1);
    }

    public HuaweiApiClient getHWPushClient() {
        return client;
    }

    /**
     * 连接推送服务器
     */
    public void hwPushConnection() {
        //创建华为移动服务client实例用以使用华为push服务
        //需要指定api为HuaweiPush.PUSH_API
        //连接回调以及连接失败监听
        client = new HuaweiApiClient.Builder(getContext())
                .addApi(HuaweiPush.PUSH_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        //建议在oncreate的时候连接华为移动服务
        //业务可以根据自己业务的形态来确定client的连接和断开的时机，但是确保connect和disconnect必须成对出现
        client.connect();
    }

    /**
     * 设置连接状态回调
     * 如果openAutoHandleConnectionFailed
     *
     * @param hwConnectListener
     */
    public void setHWConnectListener(HWConnectListener hwConnectListener) {
        this.mHWConnectListener = hwConnectListener;
    }

    /**
     * 同步请求token
     */
    public void getTokenSync() {
        if (client != null && !client.isConnected()) {
            LogUtils.i(TAG, "获取token失败，原因：HuaweiApiClient未连接");
            client.connect();
            return;
        }

        //需要在子线程中调用函数
        new Thread() {

            public void run() {
                LogUtils.i(TAG, "同步接口获取push token");
                PendingResult<TokenResult> tokenResult = HuaweiPush.HuaweiPushApi.getToken(client);
                TokenResult result = tokenResult.await();
                if (result.getTokenRes().getRetCode() == 0) {
                    //当返回值为0的时候表明获取token结果调用成功
                    LogUtils.i(TAG, "获取push token 成功，等待广播");
                }
            }

        }.start();
    }

    /**
     * 异步请求token
     */
    public void getTokenAsyn() {
        if (client != null && !client.isConnected()) {
            LogUtils.i(TAG, "获取token失败，原因：HuaweiApiClient未连接");
            client.connect();
            return;
        }

        LogUtils.i(TAG, "异步接口获取push token");
        PendingResult<TokenResult> tokenResult = HuaweiPush.HuaweiPushApi.getToken(client);
        tokenResult.setResultCallback(new ResultCallback<TokenResult>() {
            @Override
            public void onResult(TokenResult result) {
                //这边的结果只表明接口调用成功，是否能收到响应结果只在广播中接收
                LogUtils.i(TAG, "获取token 命令 发送完成");
            }
        });
    }

    @Override
    public void onConnected() {
        //华为移动服务client连接成功，在这边处理业务自己的事件
        if (mHWConnectListener != null) {
            mHWConnectListener.onHWConnectionSuccess();
        } else {
            LogUtils.i(TAG, "HuaweiApiClient 连接成功");
        }
        getTokenAsyn();
    }

    @Override
    public void onConnectionSuspended(int errorCode) {
        if (mHWConnectListener != null) {
            mHWConnectListener.onHWConnectionSuspended(errorCode);
        } else {
            //HuaweiApiClient异常断开连接, if 括号里的条件可以根据需要修改
            if (client != null && client != null) {
                client.connect();
            }
            LogUtils.i(TAG, "HuaweiApiClient 连接断开");
        }
    }

    /**
     * 是否开启自动处理华为移动服务连接错误
     * <p>
     * 结果会在onActivityResult中回调
     * 判断方式：requestCode == REQUEST_HMS_RESOLVE_ERROR
     * 启用自动处理需要在activit的onActivityResult方法中调用{@code HWPushClient.onActivityResult}
     *
     * @param isOpenAutoHandleConnectionFailed 默认为false
     * @param requestHMSResolveError           此码会在onActivityResult中回调
     */
    public void openAutoHandleConnectionFailed(boolean isOpenAutoHandleConnectionFailed, int requestHMSResolveError) {
        this.isOpenAutoHandleConnectionFailed = isOpenAutoHandleConnectionFailed;
        if (isOpenAutoHandleConnectionFailed && requestHMSResolveError != -1) {
            REQUEST_HMS_RESOLVE_ERROR = requestHMSResolveError;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        LogUtils.i(TAG, "HuaweiApiClient连接失败，错误码：" + connectionResult.getErrorCode());
        if (mHWConnectListener != null) {
            mHWConnectListener.onHWConnectionFailed(connectionResult);
        } else {
            if (isOpenAutoHandleConnectionFailed) {
                if (HuaweiApiAvailability.getInstance().isUserResolvableError(connectionResult.getErrorCode())) {
                    final int errorCode = connectionResult.getErrorCode();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 此方法必须在主线程调用
                                HuaweiApiAvailability.getInstance()
                                        .resolveError(activityWeakReference.get(), errorCode, REQUEST_HMS_RESOLVE_ERROR);
                            } catch (Exception e) {
                                // TODO: 2017/11/30 获取Activity为null，需要重新初始化
                            }
                        }
                    });
                } else {
                    //其他错误码请参见开发指南或者API文档
                    handleError(connectionResult);
                }
            } else {

            }
        }
    }

    private void handleError(ConnectionResult connectionResult) {
        switch (connectionResult.getErrorCode()) {
            case CommonCode.ErrorCode.ARGUMENTS_INVALID:
                showErrorHint(applicationContext.getString(R.string.commoncode_errorcode_arguments_invalid));
                break;
            case CommonCode.ErrorCode.INTERNAL_ERROR:
                showErrorHint(applicationContext.getString(R.string.commoncode_errorcode_internal_error));
                break;
            case CommonCode.ErrorCode.NAMING_INVALID:
                showErrorHint(applicationContext.getString(R.string.commoncode_errorcode_naming_invalid));
                break;
            case CommonCode.ErrorCode.CLIENT_API_INVALID:
                showErrorHint(applicationContext.getString(R.string.commoncode_errorcode_client_api_invalid));
                break;
            case CommonCode.ErrorCode.EXECUTE_TIMEOUT:
                showErrorHint(applicationContext.getString(R.string.commoncode_errorcode_execute_timeout));
                break;
            case CommonCode.ErrorCode.NOT_IN_SERVICE:
                showErrorHint(applicationContext.getString(R.string.commoncode_errorcode_not_in_service));
                break;
            case CommonCode.ErrorCode.SESSION_INVALID:
                showErrorHint(applicationContext.getString(R.string.commoncode_errorcode_session_invalid));
                break;
            default:
                showErrorHint(applicationContext.getString(R.string.commoncode_errorcode_other) + connectionResult.getErrorCode());
                break;
        }
    }

    private void showErrorHint(String msg) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show();
    }

    private static final String EXTRA_RESULT = "intent.extra.RESULT";

    /**
     * 当调用HuaweiApiAvailability.getInstance().resolveError方法的时候，会通过onActivityResult
     * 将实际处理结果返回给开发者。
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (isOpenAutoHandleConnectionFailed) {
            if (requestCode == REQUEST_HMS_RESOLVE_ERROR) {
                if (resultCode == Activity.RESULT_OK) {

                    int result = data.getIntExtra(EXTRA_RESULT, 0);

                    if (result == ConnectionResult.SUCCESS) {
                        LogUtils.i(TAG, "错误成功解决");
                        if (!client.isConnecting() && !client.isConnected()) {
                            client.connect();
                        }
                    } else if (result == ConnectionResult.CANCELED) {
                        LogUtils.i(TAG, "解决错误过程被用户取消");
                    } else if (result == ConnectionResult.INTERNAL_ERROR) {
                        LogUtils.i(TAG, "发生内部错误，重试可以解决");
                        //开发者可以在此处重试连接华为移动服务等操作，导致失败的原因可能是网络原因等
                        showErrorHint(applicationContext.getString(R.string.please_try_again_hw_push));
                    } else {
                        LogUtils.i(TAG, "未知返回码");
                    }
                } else {
                    LogUtils.i(TAG, "调用解决方案发生错误");
                }
            }
        }
    }

    /**
     * 断开华为client的连接
     * 官方建议connect与disconnect成对存在
     */
    public void disconnect() {
        if (client != null) {
            client.disconnect();
        }
    }

    /**
     * 注销token(此方法一般不使用)
     * <p>
     * 该接口只在华为手机并且EMUI版本号不低于5.1的版本上才起作用
     * 即只在EMUI5.1以及更高版本的华为手机上调用该接口后才不会收到PUSH消息
     *
     * @param token
     */
    public void deleteToken(final String token) {
        if (client != null && !client.isConnected()) {
            LogUtils.i(TAG, "注销token失败，原因：HuaweiApiClient未连接");
            client.connect();
            return;
        }
        //需要在子线程中执行删除token操作
        new Thread() {
            @Override
            public void run() {
                //调用删除token需要传入通过getToken接口获取到token，并且需要对token进行非空判断
                LogUtils.i(TAG, "删除Token：" + token);
                if (!TextUtils.isEmpty(token)) {
                    try {
                        HuaweiPush.HuaweiPushApi.deleteToken(client, token);
                    } catch (PushException e) {
                        LogUtils.i(TAG, "删除Token失败:" + e.getMessage());
                    }
                }
            }

        }.start();
    }

    /**
     * 查询push连接状态
     */
    public void getPushStatus() {
        if (client != null && !client.isConnected()) {
            LogUtils.i(TAG, "获取PUSH连接状态失败，原因：HuaweiApiClient未连接");
            client.connect();
            return;
        }
        //需要在子线程中调用函数
        new Thread() {
            public void run() {
                LogUtils.i(TAG, "开始获取PUSH连接状态");
                HuaweiPush.HuaweiPushApi.getPushState(client);
                // 状态结果通过广播返回
            }

        }.start();
    }

    /**
     * 设置是否接收透传消息(默认为接受状态)
     *
     * @param flag
     */
    public void setReceiveNormalMsg(boolean flag) {
        if (client != null && !client.isConnected()) {
            LogUtils.i(TAG, "设置是否接收push消息失败，原因：HuaweiApiClient未连接");
            client.connect();
            return;
        }
        if (flag) {
            LogUtils.i(TAG, "允许应用接收push透传消息");
        } else {
            LogUtils.i(TAG, "禁止应用接收push透传消息");
        }
        HuaweiPush.HuaweiPushApi.enableReceiveNormalMsg(client, flag);
    }

    /**
     * 设置是否接收通知消息
     * 请使用该接口的时候注意：
     * 当是中国大陆地区的华为手机，必须满足EMUI版本不低于EMUI5.1
     * 当是海外地区的华为手机或者非华为品牌手机，则必须满足华为移动服务的版本不低于2.5.0。
     *
     * @param flag
     */
    public void setReceiveNotifyMsg(boolean flag) {
        if (client != null && !client.isConnected()) {
            LogUtils.i(TAG, "设置是否接收push通知消息失败，原因：HuaweiApiClient未连接");
            client.connect();
            return;
        }
        if (flag) {
            LogUtils.i(TAG, "允许应用接收push通知栏消息");
        } else {
            LogUtils.i(TAG, "禁止应用接收push通知栏消息");
        }
        if (client != null) {
            HuaweiPush.HuaweiPushApi.enableReceiveNormalMsg(client, flag);
        }
    }
}
