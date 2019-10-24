package com.longcheng.lifecareplan.utils.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import java.util.HashMap;
import java.util.Map;

/**
 * 作者：zhangjinqi
 * 时间 2017/10/17 15:10
 * 邮箱：mengchong.55@163.com
 * 类的意图：调起支付
 */

public class PayUtils {
    private static final int SDK_PAY_FLAG = 1;
    static Handler mpayHandler;

    /**
     * 支付宝2.0sdk集成时的难点在于订单参数顺序的一致性
     */
    public static void Alipay(Activity context, String alipayPayInfo, PayCallBack payCallBack) {
        getAlipayInfo(context, alipayPayInfo, payCallBack);
    }


    @SuppressLint("HandlerLeak")
    private static void getAlipayInfo(final Activity context, final String alipayPayInfo, final PayCallBack payCallBack) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                Map<String, String> result = alipay.payV2(alipayPayInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mpayHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
        mpayHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                        //对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                            ToastUtils.showToast("支付成功");
                            payCallBack.onSuccess();
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            ToastUtils.showToast("支付失败");
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        };

    }

    public static void Pay(Context context, PayBeforeBean payBeforeBean, PayEnum payEnum, PayCallBack payCallBack) {
        LoadingDialogAnim.show(context);
        if (payEnum.equals(PayEnum.WECHAT)) {
            getWeChatInfo(context, payBeforeBean, payCallBack);
        } else if (payEnum.equals(PayEnum.ALIPAY)) {
            getAlipayInfo(context, payBeforeBean, payCallBack);
        }
    }

    /**
     * 获取支付宝订单
     *
     * @param context
     * @param payBeforeBean
     * @param payCallBack
     */
    private static void getAlipayInfo(final Context context, final PayBeforeBean payBeforeBean, final PayCallBack payCallBack) {
        final PayWXAfterBean payAfterBean = new PayWXAfterBean();
        @SuppressLint("HandlerLeak") final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                        //对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            ToastUtils.showToast("支付成功");
                            payCallBack.onSuccess();
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            ToastUtils.showToast("支付失败");
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        };

        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("userid", payBeforeBean.getUserid());
        stringObjectHashMap.put("money", payBeforeBean.getMoney());
        stringObjectHashMap.put("productid", payBeforeBean.getProductid());
        //支付结果查询支付状态
//        HttpXUtils3Manager.postHttpRequest(InterfaceUrl.rechargeAliPayOrder(), stringObjectHashMap, new XUtils3Callback() {
//            @Override
//            public void onSuccess(String result) {
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    JSONObject result1 = jsonObject.getJSONObject("result");
//                    String ordernum = result1.getString("ordernum");
//                    payAfterBean.setOrdernum(ordernum);
//                    final String ordersign = result1.getString("ordersign");
//                    Runnable payRunnable = new Runnable() {
//
//                        @Override
//                        public void run() {
//                            PayTask alipay = new PayTask((Activity) context);
//                            Map<String, String> result = alipay.payV2(ordersign, true);
//                            Message msg = new Message();
//                            msg.what = SDK_PAY_FLAG;
//                            msg.obj = result;
//                            mHandler.sendMessage(msg);
//                        }
//                    };
//                    new Thread(payRunnable).start();
//                } catch (JSONException e) {
//                    payCallBack.onFailure("Json解析错误");
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(int code, String message) {
//                payCallBack.onFailure(code + "__" + message);
//            }
//
//            @Override
//            public void onFinished() {
//                LoadingDialogAnim.dismiss(context);
//            }
//        });
    }

    /**
     * 获取微信支付订单
     *
     * @param context
     * @param payBeforeBean
     * @param payCallBack
     */
    private static void getWeChatInfo(final Context context, PayBeforeBean payBeforeBean, final PayCallBack payCallBack) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("userid", payBeforeBean.getUserid());
        stringObjectHashMap.put("money", payBeforeBean.getMoney());
        stringObjectHashMap.put("ip", payBeforeBean.getIp());
        stringObjectHashMap.put("productid", payBeforeBean.getProductid());
//        HttpXUtils3Manager.postHttpRequest(InterfaceUrl.rechargeWeChatOrder(), stringObjectHashMap, new XUtils3Callback() {
//            @Override
//            public void onSuccess(String result) {
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    JSONObject result1 = jsonObject.getJSONObject("result");
//                    SKBPayAfterBean payAfterBean = new SKBPayAfterBean();
//                    payAfterBean.setPackages(result1.getString("package"));
//                    payAfterBean.setAppid(result1.getString("appid"));
//                    payAfterBean.setSign(result1.getString("sign"));
//                    payAfterBean.setPartnerid(result1.getString("partnerid"));
//                    payAfterBean.setPrepayid(result1.getString("prepayid"));
//                    payAfterBean.setNoncestr(result1.getString("noncestr"));
//                    payAfterBean.setTimestamp(result1.getString("timestamp"));
//                    payAfterBean.setOrdernum(result1.getString("partnerid"));
//                    getWeChatPay(context, payAfterBean);
//                    payCallBack.onSuccess(payAfterBean);
//                } catch (JSONException e) {
//                    payCallBack.onFailure("Json解析错误");
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(int code, String message) {
//                payCallBack.onFailure(code + "__" + message);
//            }
//
//            @Override
//            public void onFinished() {
//                LoadingDialogAnim.dismiss(context);
//            }
//        });
    }

    /**
     * 吊起微信支付
     */
//    private static void getWeChatPay(Context context, PayWXAfterBean payWeChatBean) {
//        PayReq req = new PayReq();
//        req.appId = payWeChatBean.getAppid();
//        req.partnerId = payWeChatBean.getPartnerid();
//        req.prepayId = payWeChatBean.getPrepayid();
//        req.nonceStr = payWeChatBean.getNoncestr();
//        req.timeStamp = payWeChatBean.getTimestamp();
//        req.packageValue = payWeChatBean.getPackages();
//        req.sign = payWeChatBean.getSign();
//        IWXAPI wxapi = WXAPIFactory.createWXAPI(context, null);
//        wxapi.registerApp(ConstantManager.WECHATAPPID);
//        wxapi.sendReq(req);
//    }

    /**
     * 吊起微信支付
     */
    public static void getWeChatPayHtml(Context context, PayWXAfterBean payWeChatBean) {
        PayReq req = new PayReq();
        req.appId = payWeChatBean.getAppid();
        req.partnerId = payWeChatBean.getPartnerid();
        req.prepayId = payWeChatBean.getPrepayid();
        req.nonceStr = payWeChatBean.getNoncestr();
        req.timeStamp = payWeChatBean.getTimestamp();
        req.packageValue = payWeChatBean.getPackages();
        req.sign = payWeChatBean.getSign();

        /**
         * 每次调起时判断一下 支付商户类别
         */
        ConstantManager.setWeChatAppId(payWeChatBean.getAppid());
        IWXAPI wxapi = WXAPIFactory.createWXAPI(context, null);
        wxapi.registerApp(ConstantManager.getWeChatAppId());
        wxapi.sendReq(req);
    }

}
