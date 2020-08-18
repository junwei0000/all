package com.longcheng.lifecareplan.modular.mine.emergencys.facerecognition;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.mine.emergencys.EmergencyListActivity;
import com.longcheng.lifecareplan.modular.mine.emergencys.HttpRequestCallBack;
import com.longcheng.lifecareplan.modular.mine.emergencys.HttpRequestManager;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.megvii.meglive_sdk.listener.DetectCallback;
import com.megvii.meglive_sdk.listener.PreCallback;
import com.megvii.meglive_sdk.manager.MegLiveManager;

import org.json.JSONException;
import org.json.JSONObject;

import static android.os.Build.VERSION_CODES.M;

/**
 * 作者：jun on
 * 时间：2020/4/30 10:32
 * 意图：人脸识别
 */
public class FaceRecognitionUtils {
    public static final int faceSucecess = 5;
    private MegLiveManager megLiveManager;
    private Activity activity;
    private String sign = "";
    private String card = "";
    private String name = "";
    private Handler mHandler;

    public FaceRecognitionUtils(Activity activity, Handler mHandler) {
        this.activity = activity;
        this.mHandler = mHandler;
        init();
    }

    public void setCardInfo(String card, String name) {
        this.card = card;
        this.name = name;
    }

    private void init() {
        megLiveManager = MegLiveManager.getInstance();
        /**
         * 如果build.gradle中的applicationId 与 manifest中package不一致，必须将manifest中package通过
         * 下面方法传入，如果一致可以不调用此方法
         */
        megLiveManager.setManifestPack(activity, "com.longcheng.lifecareplan");
        //  mProgressDialog = new ProgressDialog(this);
        // mProgressDialog.setCancelable(false);
        sign = ExampleApplication.sign();
    }

    /**
     * 点击人脸识别
     */
    public void requestCameraPerm() {
        if (android.os.Build.VERSION.SDK_INT >= M) {
            if (activity.checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                //进行权限请求
                activity.requestPermissions(
                        new String[]{Manifest.permission.CAMERA},
                        ExampleApplication.CAMERA_PERMISSION_REQUEST_CODE);
            } else {
                requestWriteExternalPerm();
            }
        } else {
            beginDetect(1);
        }
    }


    public void requestWriteExternalPerm() {
        if (android.os.Build.VERSION.SDK_INT >= M) {
            if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //进行权限请求
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        ExampleApplication.EXTERNAL_STORAGE_REQ_WRITE_EXTERNAL_STORAGE_CODE);
            } else {
                beginDetect(1);
            }
        } else {
            beginDetect(1);
        }
    }

    public void beginDetect(int type) {
        if (type == 1) {
            Log.e("FaceRecognitionUtils", name + "," + card);
            getBizToken("meglive", 1, name, card, "", null);
        }
    }

    private void getBizToken(String livenessType,
                             int comparisonType,
                             String idcardName,
                             String idcardNum,
                             String uuid,
                             byte[] image) {
        LoadingDialogAnim.show(activity);
        HttpRequestManager.getInstance().getBizToken(activity, ExampleApplication.GET_BIZTOKEN_URL,
                sign, ExampleApplication.SIGN_VERSION, livenessType,
                comparisonType, idcardName, idcardNum, uuid, image, new HttpRequestCallBack() {

                    @Override
                    public void onSuccess(String responseBody) {
                        try {
                            Log.e("FaceRecognitionUtils", "onSuccess==" + responseBody);
                            JSONObject json = new JSONObject(responseBody);
                            //ToastUtils.showToast(name+","+card);
                            String bizToken = json.optString("biz_token");
                            megLiveManager.preDetect(activity, bizToken, "zh", "https://api.megvii.com", new PreCallback() {
                                @Override
                                public void onPreStart() {
                                    Log.e("FaceRecognitionUtils", "onPreStart==");
                                }

                                @Override
                                public void onPreFinish(String s, int i, String s1) {
                                    Log.e("FaceRecognitionUtils", "onPreFinish==" + i);
                                    if (i == 1000) {
                                        megLiveManager.setVerticalDetectionType(MegLiveManager.DETECT_VERITICAL_FRONT);
                                        megLiveManager.startDetect(new DetectCallback() {
                                            @Override
                                            public void onDetectFinish(String s, int i, String s1, String s2) {
                                                Log.e("FaceRecognitionUtils", "onDetectFinish==" + i);
                                                if (i == 1000) {
                                                    verify(s, s2.getBytes());
                                                } else {
                                                    ToastUtils.showToast("身份识别失败");
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.showToast("身份识别失败");
                            Log.e("FaceRecognitionUtils", "JSONException==");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, byte[] responseBody) {
                        Log.w("FaceRecognitionUtils", "statusCode=" + statusCode + ",responseBody" + new String(responseBody));
                        LoadingDialogAnim.dismiss(activity);
                        ToastUtils.showToast("身份识别失败");
                    }
                });
    }

    private void verify(String token, byte[] data) {
        // LoadingDialogAnim.show(this);
        HttpRequestManager.getInstance().verify(activity, ExampleApplication.VERIFY_URL, sign, ExampleApplication.SIGN_VERSION, token, data, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String responseBody) {
                Log.w("FaceRecognitionUtils", "verify==" + responseBody);
                LoadingDialogAnim.dismiss(activity);
                try {
                    Double d = 0.0;
                    Double d2 = 0.0;
                    JSONObject object = new JSONObject(responseBody);
                    if (object != null) {
                        JSONObject objectverification = object.optJSONObject("verification");
                        if (objectverification != null) {
                            JSONObject objectidcard = objectverification.optJSONObject("idcard");
                            if (objectidcard != null) {
                                String confidence = objectidcard.optString("confidence");
                                if (!TextUtils.isEmpty(confidence) && !confidence.equals("null")) {
                                    d = Double.valueOf(confidence);
                                }
                                JSONObject OBthresholds = objectidcard.optJSONObject("thresholds");
                                if (OBthresholds != null) {
                                    String e4 = OBthresholds.optString("1e-4");
                                    if (!TextUtils.isEmpty(e4) && !e4.equals("null")) {
                                        d2 = Double.valueOf(e4);
                                    }
                                }
                            }
                        }
                    }
                    Log.w("FaceRecognitionUtils", "confidence==" + d);
                    Log.w("FaceRecognitionUtils", "e4==" + d2);
                    if (d > d2) {
                        //识别通过，去提交
                        mHandler.sendEmptyMessage(faceSucecess);
                    } else {
                        ToastUtils.showToast("人脸认证未通过");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showToast("人脸认证未通过");
                }
                // progressDialogDismiss();
            }

            @Override
            public void onFailure(int statusCode, byte[] responseBody) {
                Log.w("FaceRecognitionUtils", new String(responseBody));
                LoadingDialogAnim.dismiss(activity);
                ToastUtils.showToast("人脸认证未通过");
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == ExampleApplication.CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length < 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                //拒绝了权限申请
                ToastUtils.showToast("请到“设置”-->“应用管理”-->“互祝”开通权限");
            } else {
                requestWriteExternalPerm();
            }
        } else if (requestCode == ExampleApplication.EXTERNAL_STORAGE_REQ_WRITE_EXTERNAL_STORAGE_CODE) {
            if (grantResults.length < 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                //拒绝了权限申请
                ToastUtils.showToast("请到“设置”-->“应用管理”-->“互祝”开通权限");
            } else {
                beginDetect(1);
            }
        }
    }
}
