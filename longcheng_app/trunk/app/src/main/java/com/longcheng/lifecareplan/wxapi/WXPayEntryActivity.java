package com.longcheng.lifecareplan.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "shuaishuai";
    private IWXAPI api;
    public static final String BROAD_CAST_PAY_SUCCESS = "com.finish.activity.success";
    public static final String BROAD_CAST_PAY_FAILE = "com.finish.activity.faile";
    public static final String BROAD_CAST_PAY_CANCLE = "com.finish.activity.cancle";
    private int contentCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        api = WXAPIFactory.createWXAPI(this, ConstantManager.WECHATAPPID);
        api.handleIntent(getIntent(), this);
        Log.i(TAG, "onCreate: ");
        if (ConstantManager.isHtmlPayMethod == 1) {
            ConstantManager.isHtmlPayMethod = 0;
            Log.i(TAG, "onCreate: finish");
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.i(TAG, "onNewIntent: ");
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Log.i(TAG, "onReq: ");
    }

    //微信支付成功回调
    @Override
    public void onResp(BaseResp resp) {
        Log.i(TAG, "onResp: ");
        PayResp response = (PayResp) resp;
        contentCode = response.errCode;
        switch (response.errCode) {
            case 0:
                Intent intent = new Intent();
                intent.setAction(BROAD_CAST_PAY_SUCCESS);
                intent.putExtra("errCode", response.errCode);
                Log.i(TAG, "onResp: BROAD_CAST_PAY_SUCCESS");
                sendBroadcast(intent);//发送普通广播
                break;
            case -1:
                Intent intent2 = new Intent();
                intent2.setAction(BROAD_CAST_PAY_FAILE);
                intent2.putExtra("errCode", response.errCode);
                Log.i(TAG, "onResp:BROAD_CAST_PAY_FAILE ");
                sendBroadcast(intent2);
                finish();
                break;
            case -2:
                Intent intent3 = new Intent();
                intent3.setAction(BROAD_CAST_PAY_CANCLE);
                intent3.putExtra("errCode", response.errCode);
                Log.i(TAG, "onResp: BROAD_CAST_PAY_CANCLE");
                sendBroadcast(intent3);
                finish();
                break;
        }
    }
}