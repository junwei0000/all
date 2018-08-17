package com.bestdo.bestdoStadiums.utils;

import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bestdo.bestdoStadiums.business.CreateOrdersUsreBalance0Business;
import com.bestdo.bestdoStadiums.business.CreateOrdersUsreBalance0Business.GetCreateOrdersCallback;
import com.bestdo.bestdoStadiums.business.PayBusiness;
import com.bestdo.bestdoStadiums.business.PayWXBusiness;
import com.bestdo.bestdoStadiums.business.UserOrderDetailsBusiness;
import com.bestdo.bestdoStadiums.business.PayBusiness.GetPayCallback;
import com.bestdo.bestdoStadiums.business.PayUnionBusiness;
import com.bestdo.bestdoStadiums.business.PayUnionBusiness.GetPayUnionCallback;
import com.bestdo.bestdoStadiums.business.PayUnionHuaweiBusiness;
import com.bestdo.bestdoStadiums.business.PayUnionHuaweiBusiness.GetPayUnionHuaweiCallback;
import com.bestdo.bestdoStadiums.business.PayWXBusiness.GetPayWXCallback;
import com.bestdo.bestdoStadiums.business.UserOrderDetailsBusiness.GetUserOrderDetailsCallback;
import com.bestdo.bestdoStadiums.control.activity.Base64;
import com.bestdo.bestdoStadiums.control.activity.CardSuccess_Pay;
import com.bestdo.bestdoStadiums.control.activity.PayResult;
import com.bestdo.bestdoStadiums.control.activity.PayUnionActivity;
import com.bestdo.bestdoStadiums.control.activity.Success_Balanceresult;
import com.bestdo.bestdoStadiums.control.activity.Success_Faliure;
import com.bestdo.bestdoStadiums.control.activity.Success_Off;
import com.bestdo.bestdoStadiums.control.activity.Success_Pay;
import com.bestdo.bestdoStadiums.control.activity.Success_YuDing;
import com.bestdo.bestdoStadiums.control.activity.UserMeberResultActiyity;
import com.bestdo.bestdoStadiums.control.map.BestDoApplication;
import com.bestdo.bestdoStadiums.model.PayInfo;
import com.bestdo.bestdoStadiums.model.UserOrderDetailsInfo;
import com.bestdo.bestdoStadiums.R;
import com.tencent.mm.sdk.modelpay.PayReq;

public class PayUtils {

	private HashMap<String, String> mhashmap;
	private ProgressDialog mDialog;
	private Activity activity;
	private String mername;
	private String play_time;
	private String selectNum;
	private PayInfo mPayInfo;
	private SharedPreferences bestDoInfoSharedPrefs;
	private Editor bestDoInfoEditor;
	private Intent payintents;
	boolean skipresultstatus = false;
	private String skipToFinal;
	private String orderstatus = "0";
	private String is_set_supplier = "";
	private String process_type = "";

	public String skiptIntent = "";
	private String remind;
	private String num = "";
	private String oid;
	private String uid;
	/**
	 * 银联支付-渠道类型（android ios web）
	 */
	public String mid = "";

	public PayUtils(Activity activity, String oid, String uid, String remind, String num, String mid,
			String skiptIntent) {
		super();
		this.activity = activity;
		this.oid = oid;
		this.uid = uid;
		this.remind = remind;
		this.num = num;
		this.mid = mid;
		this.skiptIntent = skiptIntent;
		init();
	}

	public void init() {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(activity);
		bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
		// 注册返回支付成功的广播(从 订单完成列表、订单详情)
		payintents = new Intent();
		payintents.setAction(activity.getString(R.string.action_Success_Faliure));
		payintents.putExtra("type", "payover");
	}

	/**
	 * 华为支付
	 */
	public void processLogicHuaWei() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(activity)) {
			CommonUtils.getInstance().initToast(activity, activity.getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("oid", oid);
		mhashmap.put("uid", uid);

		mhashmap.put("isBalance", isBalance);
		mhashmap.put("pay_money", pay_money);
		mhashmap.put("balance_reduce_money", balance_reduce_money);
		if (skiptIntent.equals(Constans.showPayDialogByBuyMember)) {
			url = Constans.PAYHUAWEIUNIONBUYMEBER;
		}
		new PayUnionHuaweiBusiness(activity, mhashmap, url, new GetPayUnionHuaweiCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {

				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						String tn = (String) dataMap.get("tn");
						if (TextUtils.isEmpty(tn)) {
							CommonUtils.getInstance().initToast(activity, activity.getString(R.string.net_tishi));
							return;
						}
						Intent intent;
						intent = new Intent(activity, PayUnionActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						intent.putExtra("PAYTYPE", "PHONEPAY");
						intent.putExtra("oid", oid);
						intent.putExtra("uid", uid);
						intent.putExtra("tn", tn);
						intent.putExtra("mid", mid);
						intent.putExtra("num", num);
						intent.putExtra("skiptIntent", skiptIntent);
						intent.putExtra("remind", remind);
						intent.putExtra("ordermoney", ordermoney);
						intent.putExtra("isBalance", isBalance);
						intent.putExtra("pay_money", pay_money);
						intent.putExtra("balance_reduce_money", balance_reduce_money);
						activity.startActivity(intent);
						if (skiptIntent.equals(Constans.showPayDialogByBuyStadium)) {
							activity.finish();
						}
						CommonUtils.getInstance().setPageIntentAnim(intent, activity);

					} else {
						String msg = (String) dataMap.get("data");
						setFailureSendFunction(msg);
					}
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	/**
	 * 银联支付
	 */
	public void processLogicYinLian() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(activity)) {
			CommonUtils.getInstance().initToast(activity, activity.getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("oid", oid);
		mhashmap.put("uid", uid);

		mhashmap.put("isBalance", isBalance);
		mhashmap.put("pay_money", pay_money);
		mhashmap.put("balance_reduce_money", balance_reduce_money);

		if (skiptIntent.equals(Constans.showPayDialogByNavImg)) {
			if (TextUtils.isEmpty(mid)) {
				mhashmap.put("mtype", "shop");// 商城订单支付必填（mtype为shop）
			}
			mhashmap.put("mid", "" + mid);
			url = Constans.PAYUNION;
		} else if (skiptIntent.equals(Constans.showPayDialogByBuyCard)) {
			mhashmap.put("num", num);
			mhashmap.put("card_batch_id", card_batch_id);
			url = Constans.PAYUNIONBUYCARD;
		} else if (skiptIntent.equals(Constans.showPayDialogByBuyMember)) {
			url = Constans.PAYUNIONBUYMEBER;
		} else if (skiptIntent.equals(Constans.showPayDialogByBuyBalance)) {
			mhashmap.put("mtype", "balance");
			url = Constans.PAYUNION;
		} else {
			url = Constans.PAYUNION;
		}
		new PayUnionBusiness(activity, mhashmap, url, new GetPayUnionCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {

				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						String tn = (String) dataMap.get("tn");
						if (TextUtils.isEmpty(tn)) {
							CommonUtils.getInstance().initToast(activity, activity.getString(R.string.net_tishi));
							return;
						}
						Intent intent;
						intent = new Intent(activity, PayUnionActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						intent.putExtra("PAYTYPE", "UNIONPAY");
						intent.putExtra("oid", oid);
						intent.putExtra("uid", uid);
						intent.putExtra("tn", tn);
						intent.putExtra("mid", mid);
						intent.putExtra("num", num);
						intent.putExtra("skiptIntent", skiptIntent);
						intent.putExtra("remind", remind);
						intent.putExtra("ordermoney", ordermoney);
						intent.putExtra("isBalance", isBalance);
						intent.putExtra("pay_money", pay_money);
						intent.putExtra("balance_reduce_money", balance_reduce_money);
						activity.startActivity(intent);
						if (skiptIntent.equals(Constans.showPayDialogByBuyStadium)) {
							activity.finish();
						}
						CommonUtils.getInstance().setPageIntentAnim(intent, activity);

					} else {
						String msg = (String) dataMap.get("data");
						setFailureSendFunction(msg);
					}
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});
	}

	String url;
	public String card_batch_id;
	/**
	 * 1:使用余额
	 */
	public String isBalance = "0";
	/**
	 * 实际支付的金额
	 */
	public String pay_money = "";
	public String balance_reduce_money = "";

	public String ordermoney = "";

	public void processLogicZFB() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(activity)) {
			CommonUtils.getInstance().initToast(activity, activity.getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("oid", oid);
		mhashmap.put("uid", uid);

		mhashmap.put("isBalance", isBalance);
		mhashmap.put("pay_money", pay_money);
		mhashmap.put("balance_reduce_money", balance_reduce_money);

		if (skiptIntent.equals(Constans.showPayDialogByNavImg)) {
			mhashmap.put("mtype", "shop");// 商城订单支付必填（mtype为shop）
			url = Constans.PAYZFB;
		} else if (skiptIntent.equals(Constans.showPayDialogByBuyCard)) {
			mhashmap.put("num", num);
			mhashmap.put("card_batch_id", card_batch_id);
			url = Constans.CARDPAYZFB;
		} else if (skiptIntent.equals(Constans.showPayDialogByBuyMember)) {
			url = Constans.MEBERPAYZFB;
		} else if (skiptIntent.equals(Constans.showPayDialogByBuyBalance)) {
			mhashmap.put("mtype", "balance");
			url = Constans.PAYZFB;
		} else {
			url = Constans.PAYZFB;
		}
		System.err.println(mhashmap);
		new PayBusiness(activity, mhashmap, url, new GetPayCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						String sign = (String) dataMap.get("sign");
						String body = (String) dataMap.get("body");
						String _input_charset = (String) dataMap.get("_input_charset");
						String subject = (String) dataMap.get("subject");
						String total_fee = (String) dataMap.get("total_fee");
						String sign_type = (String) dataMap.get("sign_type");
						String service = (String) dataMap.get("service");
						String notify_url = (String) dataMap.get("notify_url");
						String partner = (String) dataMap.get("partner");
						String seller_id = (String) dataMap.get("seller_id");
						String out_trade_no = (String) dataMap.get("out_trade_no");
						String payment_type = (String) dataMap.get("payment_type");
						String it_b_pay = (String) dataMap.get("it_b_pay");
						if (TextUtils.isEmpty(partner) || TextUtils.isEmpty(sign) || TextUtils.isEmpty(seller_id)) {
							new AlertDialog.Builder(activity).setTitle("警告")
									.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
									.setPositiveButton("确定", new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialoginterface, int i) {
											activity.finish();
										}
									}).show();
							return;
						}
						// 订单
						String orderInfo = getOrderInfo(_input_charset, body, notify_url, out_trade_no, partner,
								payment_type, seller_id, service, subject, total_fee, it_b_pay);
						Log.e("orderInfo", orderInfo);
						// 完整的符合支付宝参数规范的订单信息
						String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + "sign_type=" + "\"" + sign_type + "\"";
						Log.e("payInfo", payInfo);
						payZfb(payInfo);

					} else {
						String msg = (String) dataMap.get("data");
						setFailureSendFunction(msg);
					}
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	/**
	 * create the order info. 创建订单信息
	 */
	public String getOrderInfo(String _input_charset, String body, String notify_url, String out_trade_no,
			String partner, String payment_type, String seller_id, String service, String subject, String total_fee,
			String it_b_pay) {

		// 参数编码， 固定值
		String orderInfo = "_input_charset=" + "\"" + _input_charset + "\"";
		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";
		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
//		orderInfo += "&it_b_pay=" + "\""+it_b_pay + "\"";
		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + notify_url + "\"";
		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + out_trade_no + "\"";
		// 签约合作者身份ID
		orderInfo += "&partner=" + "\"" + partner + "\"";
		// 支付类型， 固定值
		orderInfo += "&payment_type=" + "\"" + payment_type + "\"";
		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + seller_id + "\"";
		// 服务接口名称， 固定值
		orderInfo += "&service=" + "\"" + service + "\"";
		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";
		// 商品金额
		orderInfo += "&total_fee=" + "\"" + total_fee + "\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		// orderInfo += "&return_url=\"m.alipay.com\"";
		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";
		Log.e("map", "orderInfo------" + orderInfo);
		return orderInfo;

	}

	/**
	 * call alipay sdk pay. 调用支付宝SDK支付
	 * 
	 */
	public void payZfb(final String payInfo) {
		Runnable payRunnable = new Runnable() {
			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(activity);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);
				Log.e("payInfo", "result-----" + result);
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * 微信支付
	 * 
	 * @param remind
	 */
	public void processLogicWX() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(activity)) {
			CommonUtils.getInstance().initToast(activity, activity.getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("oid", oid);
		mhashmap.put("uid", uid);
		mhashmap.put("isBalance", isBalance);
		mhashmap.put("pay_money", pay_money);
		mhashmap.put("balance_reduce_money", balance_reduce_money);
		if (skiptIntent.equals(Constans.showPayDialogByNavImg)) {
			mhashmap.put("mtype", "shop");// 商城订单支付必填（mtype为shop）
			url = Constans.PAYWEIXIN;
		} else if (skiptIntent.equals(Constans.showPayDialogByBuyCard)) {
			mhashmap.put("num", num);
			mhashmap.put("card_batch_id", card_batch_id);
			url = Constans.CARDPAYWEIXIN;
		} else if (skiptIntent.equals(Constans.showPayDialogByBuyMember)) {
			url = Constans.MEMBERPAYWEIXIN;
		} else if (skiptIntent.equals(Constans.showPayDialogByBuyBalance)) {
			mhashmap.put("mtype", "balance");
			url = Constans.PAYWEIXIN;
		} else {
			url = Constans.PAYWEIXIN;
		}
		CommonUtils.getInstance().paywxMoney = ordermoney;
		CommonUtils.getInstance().setPayWXStatus(skiptIntent);
		new PayWXBusiness(activity, mhashmap, url, new GetPayWXCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);

				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						mPayInfo = (PayInfo) dataMap.get("mPayInfo");
						bestDoInfoEditor.putString("remind", remind);
						bestDoInfoEditor.putString("oid", oid);
						bestDoInfoEditor.commit();
						if (skiptIntent.equals(Constans.showPayDialogByBuyStadium)) {
							mHandler.sendEmptyMessage(WX);
						} else {
							mHandler.sendEmptyMessage(SHANGCENGWX);
							return;
						}

					} else {
						String msg = (String) dataMap.get("data");
						setFailureSendFunction(msg);
					}
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	/**
	 * 微信支付
	 * 
	 * @param mPayInfo
	 */
	private void payEX(PayInfo mPayInfo) {
		PayReq req = new PayReq();
		req.appId = mPayInfo.getAppid();
		req.partnerId = mPayInfo.getMch_id();
		req.prepayId = mPayInfo.getPrepay_id();
		req.nonceStr = mPayInfo.getNonce_str();
		req.packageValue = mPayInfo.getPackages();
		req.timeStamp = mPayInfo.getTimestamp();
		req.sign = mPayInfo.getSign();
		// List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		// signParams.add(new BasicNameValuePair("appid", req.appId));
		// signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		// signParams.add(new BasicNameValuePair("package", req.packageValue));
		// signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		// signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		// signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
		// req.sign = genAppSign(signParams);
		BestDoApplication.getInstance().msgApi.sendReq(req);

	}

	public void userBalance0Pay() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("oid", oid);
		mhashmap.put("uid", uid);
		mhashmap.put("pay_money", pay_money);
		mhashmap.put("balance_reduce_money", balance_reduce_money);
		if (skiptIntent.equals(Constans.showPayDialogByNavImg)) {
			mhashmap.put("mtype", "shop");
		} else if (skiptIntent.equals(Constans.showPayDialogByBuyBalance)) {
			mhashmap.put("mtype", "balance");
		}
		System.err.println(mhashmap);
		new CreateOrdersUsreBalance0Business(activity, mhashmap, new GetCreateOrdersCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("code");
					if (status.equals("200")) {
						seccessSkip();
					} else {
						failureSkip();
					}
				} else {
					CommonUtils.getInstance().initToast(activity, activity.getString(R.string.net_tishi));
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	public void seccessSkip() {
		mHandler.sendEmptyMessage(SUCCESSSTATUS);
	}

	public void failureSkip() {
		mHandler.sendEmptyMessage(FAILURESTATUS);
	}

	/**
	 * 支付宝回调
	 */
	private static final int SDK_PAY_FLAG = 4;

	/**
	 * 跳转微信第三方支付
	 */
	private static final int WX = 3;
	/**
	 * 商城跳转微信第三方支付
	 */
	private static final int SHANGCENGWX = 9;

	/**
	 * 成功跳转
	 */
	private static final int SUCCESSSTATUS = 5;
	/**
	 * 失败跳转
	 */
	private static final int FAILURESTATUS = 6;
	/**
	 * 支付成功向订单列表发送广播
	 */
	private static final int SENDBRODCAST = 7;
	/**
	 * 根据订单状态判断跳转方向
	 */
	private static final int UPDATESKIP = 8;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WX:
				payEX(mPayInfo);
				mHandler.sendEmptyMessageDelayed(-1, 2000);
				break;
			case SHANGCENGWX:
				payEX(mPayInfo);
				break;
			case -1:
				activity.finish();
				break;
			case SDK_PAY_FLAG:
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();
				Log.e("payInfo", "msg.obj--" + msg.obj);
				Log.e("payInfo", "resultInfo--" + resultInfo);
				String resultStatus = payResult.getResultStatus();
				Log.e("payInfo", "resultStatus--" + resultStatus);
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					seccessSkip();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						mHandler.sendEmptyMessage(FAILURESTATUS);
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						mHandler.sendEmptyMessage(FAILURESTATUS);
					}
				}
				break;
			case SUCCESSSTATUS:
				if (skiptIntent.equals(Constans.showPayDialogByBuyCard)) {
					Intent intent;
					intent = new Intent(activity, CardSuccess_Pay.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					activity.startActivity(intent);
					activity.finish();
					CommonUtils.getInstance().setPageIntentAnim(intent, activity);
					break;
				} else if (skiptIntent.equals(Constans.showPayDialogByNavImg)) {
					JSONObject oo = new JSONObject();
					try {
						oo.put("code", "200");
						oo.put("order_sn", oid);
						oo.put("mid", mid);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					String jsonresult = Base64.encode(oo.toString().getBytes());
					Constans.getInstance().function.onCallBack(jsonresult);
					break;
				} else if (skiptIntent.equals(Constans.showPayDialogByBuyMember)) {
					Intent intent = new Intent(activity, UserMeberResultActiyity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					activity.startActivity(intent);
					activity.finish();
					CommonUtils.getInstance().setPageIntentAnim(intent, activity);
					break;
				} else if (skiptIntent.equals(Constans.showPayDialogByBuyBalance)) {
					Intent intent = new Intent(activity, Success_Balanceresult.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("resultStatus", "success");
					intent.putExtra("payMoney", ordermoney);
					activity.startActivity(intent);
					activity.finish();
					CommonUtils.getInstance().setPageIntentAnim(intent, activity);
					break;
				} else {
					skipresultstatus = true;
					checkOrderStatus("successUrl");
				}
				break;
			case FAILURESTATUS:
				if (skiptIntent.equals(Constans.showPayDialogByBuyCard)) {
					break;
				} else if (skiptIntent.equals(Constans.showPayDialogByNavImg)) {
					setFailureSendFunction("");
					break;
				} else if (skiptIntent.equals(Constans.showPayDialogByBuyMember)) {
					Toast.makeText(activity, "购买会员失败", 0).show();
					break;
				} else if (skiptIntent.equals(Constans.showPayDialogByBuyBalance)) {
					Intent intent = new Intent(activity, Success_Balanceresult.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("resultStatus", "failure");
					activity.startActivity(intent);
					activity.finish();
					CommonUtils.getInstance().setPageIntentAnim(intent, activity);
					break;
				} else {
					skipresultstatus = true;
					checkOrderStatus("failUrl");
				}
				break;
			case SENDBRODCAST:
				activity.sendBroadcast(payintents);
				break;
			case UPDATESKIP:
				// 清除缓存
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (!TextUtils.isEmpty(skipToFinal) && skipToFinal.equals("successUrl")) {
					mHandler.sendEmptyMessage(SENDBRODCAST);
					if (!TextUtils.isEmpty(orderstatus)
							&& orderstatus.equals(activity.getResources().getString(R.string.order_canceled))) {
						// 支付成功但订单取消状态
						skipOffView();
					} else {
						skipSuccessView();
					}
				} else if (!TextUtils.isEmpty(skipToFinal) && skipToFinal.equals("finishStatus")) {
					if (!TextUtils.isEmpty(orderstatus)
							&& orderstatus.equals(activity.getResources().getString(R.string.order_canceled))) {
					}
				} else {
					skipFaliureView();
				}
				break;
			}
		}
	};

	private void setFailureSendFunction(String msg) {
		if (skiptIntent.equals(Constans.showPayDialogByNavImg)) {
			JSONObject oo = new JSONObject();
			try {
				oo.put("code", "400");
				oo.put("order_sn", oid);
				oo.put("mid", mid);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			String jsonresult = Base64.encode(oo.toString().getBytes());
			Constans.getInstance().function.onCallBack(jsonresult);
		} else {
			CommonUtils.getInstance().initToast(activity, msg);
		}
	}

	private UserOrderDetailsInfo userOrderDetailsInfo;
	private String cid;
	private int servicecurrenttimestamp;
	private int createtimestamp;
	protected String book_day;

	/**
	 * 超时支付成功页面
	 * 
	 */
	private void skipOffView() {
		Intent intent = new Intent(activity, Success_Off.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("oid", oid);
		activity.startActivity(intent);
		activity.finish();
		CommonUtils.getInstance().setPageIntentAnim(intent, activity);
	}

	/**
	 * 跳转成功页面
	 */
	private void skipSuccessView() {
		Intent intent;
		if (toYuding()) {
			intent = new Intent(activity, Success_YuDing.class);
			intent.putExtra("mername", mername);
			intent.putExtra("play_time", play_time);
			intent.putExtra("selectNum", "" + selectNum);
			intent.putExtra("remind", "" + remind);
			intent.putExtra("book_day", "" + book_day);
		} else {
			intent = new Intent(activity, Success_Pay.class);
		}
		intent.putExtra("cid", cid);
		intent.putExtra("oid", oid);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		activity.startActivity(intent);
		activity.finish();
		CommonUtils.getInstance().setPageIntentAnim(intent, activity);

	}

	/**
	 * 跳转失败页面
	 */
	private void skipFaliureView() {
		Intent intent = new Intent(activity, Success_Faliure.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("servicecurrenttimestamp", servicecurrenttimestamp);
		intent.putExtra("createtimestamp", createtimestamp);
		intent.putExtra("orderstatus", orderstatus);
		intent.putExtra("oid", oid);
		activity.startActivity(intent);
		CommonUtils.getInstance().setPageIntentAnim(intent, activity);
		activity.finish();
	}

	/**
	 * 获取订单状态
	 * 
	 * @param skipTo
	 */
	public void checkOrderStatus(final String skipTo) {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		mhashmap.put("oid", oid);
		System.err.println(mhashmap);
		new UserOrderDetailsBusiness(activity, mhashmap, new GetUserOrderDetailsCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("400")) {
						String msg = (String) dataMap.get("data");
						CommonUtils.getInstance().initToast(activity, msg);
					} else if (status.equals("200")) {
						userOrderDetailsInfo = (UserOrderDetailsInfo) dataMap.get("userOrderDetailsInfo");
						if (userOrderDetailsInfo != null) {
							orderstatus = userOrderDetailsInfo.getStats();
							cid = userOrderDetailsInfo.getCid();
							mername = userOrderDetailsInfo.getMer_item_name();
							play_time = userOrderDetailsInfo.getPlay_time();
							selectNum = userOrderDetailsInfo.getNumber();
							book_day = userOrderDetailsInfo.getDay();
							is_set_supplier = userOrderDetailsInfo.getIs_set_supplier();
							process_type = userOrderDetailsInfo.getProcess_type();
							orderstatus = userOrderDetailsInfo.getStats();
							servicecurrenttimestamp = userOrderDetailsInfo.getCurrent_time();
							createtimestamp = userOrderDetailsInfo.getCreate_time();

							skipToFinal = skipTo;
							mHandler.sendEmptyMessage(UPDATESKIP);
						}
					}
				} else {
					CommonUtils.getInstance().initToast(activity, activity.getString(R.string.net_tishi));
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	/**
	 * status-人工状态-供应商匹配状态 ; 1-0-1预订成功； 1-0-0支付成功； 1-1-0支付成功； 3-0-1预订成功；
	 * 3-0-0支付成功； 3-1-0支付成功；
	 * 
	 * @return
	 */
	private boolean toYuding() {
		boolean toYudingStatus = true;
		System.err.println(orderstatus + "   " + process_type + "     " + is_set_supplier);
		if (orderstatus.equals(activity.getString(R.string.order_putParameters_confirming))
				&& process_type.equals(Constans.getInstance().stadiumUsedManage)
				&& is_set_supplier.equals(Constans.getInstance().stadiumStockType)) {
			// 1-0-1预订成功；
			toYudingStatus = true;
		} else if (orderstatus.equals(activity.getString(R.string.order_putParameters_confirming))
				&& process_type.equals(Constans.getInstance().stadiumUsedManage)
				&& !is_set_supplier.equals(Constans.getInstance().stadiumStockType)) {
			// 1-0-0支付成功；
			toYudingStatus = false;
		} else if (orderstatus.equals(activity.getString(R.string.order_putParameters_confirming))
				&& !process_type.equals(Constans.getInstance().stadiumUsedManage)
				&& !is_set_supplier.equals(Constans.getInstance().stadiumStockType)) {
			// 1-1-0支付成功；
			toYudingStatus = false;
		} else if (orderstatus.equals(activity.getString(R.string.order_putParameters_stayoff))
				&& process_type.equals(Constans.getInstance().stadiumUsedManage)
				&& is_set_supplier.equals(Constans.getInstance().stadiumStockType)) {
			// 3-0-1预订成功；
			toYudingStatus = true;
		} else if (orderstatus.equals(activity.getString(R.string.order_putParameters_stayoff))
				&& process_type.equals(Constans.getInstance().stadiumUsedManage)
				&& !is_set_supplier.equals(Constans.getInstance().stadiumStockType)) {
			// 3-0-0支付成功；
			toYudingStatus = false;
		} else if (orderstatus.equals(activity.getString(R.string.order_putParameters_stayoff))
				&& !process_type.equals(Constans.getInstance().stadiumUsedManage)
				&& !is_set_supplier.equals(Constans.getInstance().stadiumStockType)) {
			// 3-1-0支付成功；
			toYudingStatus = false;
		}
		return toYudingStatus;
	}

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(activity);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
