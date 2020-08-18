package com.longcheng.lifecareplan.modular.helpwith.nfcaction.activity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ActionDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailListDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.LoginActivity;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * nfc行动-详情
 */
public class NFCDetailActivity extends BaseActivityMVP<NFCDetailContract.View, NFCDetailPresenterImp<NFCDetailContract.View>> implements NFCDetailContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.iv_thumb)
    ImageView ivThumb;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_jieeqi)
    TextView tvJieeqi;
    @BindView(R.id.layout_shenfen)
    LinearLayout layoutShenfen;


    @BindView(R.id.tv_starttime)
    TextView tvStarttime;
    @BindView(R.id.tv_overtime)
    TextView tvOvertime;
    @BindView(R.id.tv_getenergynum)
    TextView tvGetenergynum;
    @BindView(R.id.tv_blessnum)
    TextView tvBlessnum;
    @BindView(R.id.tv_way)
    TextView tv_way;
    @BindView(R.id.tv_jianiyi)
    TextView tv_jianiyi;
    @BindView(R.id.btn_engry)
    TextView btnEngry;
    @BindView(R.id.tv_apply)
    TextView tv_apply;
    @BindView(R.id.pageTop_tv_rigth)
    TextView pageTopTvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.tv_applyname)
    TextView tvApplyname;
    @BindView(R.id.tv_receivename)
    TextView tvReceivename;
    @BindView(R.id.tv_actionname)
    TextView tvActionname;
    private String order_id, goods_id, nfc_sn;
    String applytime, User_name, Avatar;
    int diffRecordNum, totalRecordNum = 25;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.pagetop_layout_rigth:
                intent = new Intent(mContext, NFCDetailRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("order_id", order_id);
                intent.putExtra("nfc_sn", nfc_sn);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.btn_engry:
                if (!nfcScan) {
                    setDialog(Avatar, User_name, applytime, true);
                } else {
                    intent = new Intent(mContext, NFCDetailListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("nfc_sn", nfc_sn);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                }
                break;
            case R.id.tv_apply:
                intent = new Intent(mContext, ActionDetailActivity.class);
                intent.putExtra("goods_id", goods_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.nfc_detail;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("信息溯源");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvRigth.setText("溯源记录");
        pageTopTvRigth.setVisibility(View.VISIBLE);
        pageTopTvRigth.setTextColor(getResources().getColor(R.color.text_healthcontents_color));
        tv_apply.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        btnEngry.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        tvJieeqi.getBackground().setAlpha(92);
    }

    @Override
    public void initDataAfter() {
        resolveIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        resolveIntent(intent);
    }

    @Override
    protected NFCDetailPresenterImp<NFCDetailContract.View> createPresent() {
        return new NFCDetailPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }


    @Override
    public void DetailSuccess(NFCDetailDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            NFCDetailAfterBean mNFCDetailAfterBean = responseBean.getData();
            NFCDetailItemBean userinfo = mNFCDetailAfterBean.getUser();
            diffRecordNum = mNFCDetailAfterBean.getDiffRecordNum();
            totalRecordNum = mNFCDetailAfterBean.getTotalRecordNum();
            if (userinfo != null) {
                showUserInfo(userinfo);
                User_name = userinfo.getUser_name();
                Avatar = userinfo.getAvatar();
                String cont = "<font color=\"#858585\">接福人：</font>" + User_name;
                tvReceivename.setText(Html.fromHtml(cont));
            }
            NFCDetailItemBean actioninfo = mNFCDetailAfterBean.getMsg();
            if (actioninfo != null) {
                applytime = DatesUtils.getInstance().getTimeStampToDate(actioninfo.getM_time(), "yyyy年MM月dd日HH:mm:ss");
                String applytime_ = "<font color=\"#858585\">申请时间: </font>" + applytime;
                goods_id = actioninfo.getGoods_id();
                String F_user = "<font color=\"#858585\">申请人: </font>" + actioninfo.getF_user();
                tvApplyname.setText(Html.fromHtml(F_user));
                String Action_name = "<font color=\"#858585\">行动名称: </font>" + actioninfo.getAction_name();
                tvActionname.setText(Html.fromHtml(Action_name));
                tv_apply.setText("申请" + actioninfo.getAction_name());
                tvStarttime.setText(Html.fromHtml(applytime_));
                String Over_time = DatesUtils.getInstance().getTimeStampToDate(actioninfo.getOver_time(), "yyyy年MM月dd日HH:mm:ss");
                Over_time = "<font color=\"#858585\">完成时间: </font>" + Over_time;
                tvOvertime.setText(Html.fromHtml(Over_time));


                int isComplete = mNFCDetailAfterBean.getIsComplete();
                if (isComplete == 1) {//该产品已使用完成
                    nfcScan = true;
                }
                String price_action;
                String ulative_number;
                String way_;
                String NotStr_;
                if (!nfcScan) {
                    price_action = "*****";
                    ulative_number = "*****";
                    way_ = "*****";
                    NotStr_ = "*****";
                    if (diffRecordNum == totalRecordNum) {
                        btnEngry.setText("导入生命能量  " + totalRecordNum);
                    } else {
                        btnEngry.setText("导入生命能量  " + diffRecordNum + "/" + totalRecordNum);
                    }

                } else {
                    price_action = PriceUtils.getInstance().seePrice(actioninfo.getAbility_price_action());
                    ulative_number = actioninfo.getCumulative_number() + "人";
                    way_ = mNFCDetailAfterBean.getUsageMethod();
                    NotStr_ = mNFCDetailAfterBean.getNotStr();
                    btnEngry.setText("祝福能量列表");
                }
                String Ability_price_action = "<font color=\"#858585\">收到生命能量: </font>" + price_action;
                String Cumulative_number = "<font color=\"#858585\">祝福人数: </font>" + ulative_number;
                String way = "<font color=\"#858585\">食用方法: </font>" + way_;
                String NotStr = "<font color=\"#858585\">建议食用量: </font>" + NotStr_;
                tvGetenergynum.setText(Html.fromHtml(Ability_price_action));
                tvBlessnum.setText(Html.fromHtml(Cumulative_number));
                tv_jianiyi.setText(Html.fromHtml(NotStr));
                tv_way.setText(Html.fromHtml(way));


                if (isComplete == 1) {//该产品已使用完成
                    setOverDialog();
                } else {
                    if (!nfcScan) {
                        setDialog(Avatar, User_name, applytime, false);
                    }
                }
            }
        }
    }


    @Override
    public void DetailListSuccess(NFCDetailDataBean responseBean, int backPage) {

    }

    private void showUserInfo(NFCDetailItemBean userinfo) {
        GlideDownLoadImage.getInstance().loadCircleImage(userinfo.getAvatar(), ivThumb);
        tvName.setText(CommonUtil.setName(userinfo.getUser_name()));
        tvJieeqi.setText(userinfo.getJieqi_name());
        List<String> identity_img = userinfo.getImg_all();
        layoutShenfen.removeAllViews();
        if (identity_img != null && identity_img.size() > 0) {
            for (String url : identity_img) {
                LinearLayout linearLayout = new LinearLayout(mContext);
                ImageView imageView = new ImageView(mContext);
                int dit = DensityUtil.dip2px(mContext, 16);
                int jian = DensityUtil.dip2px(mContext, 3);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(mContext, url, imageView);
                linearLayout.addView(imageView);
                layoutShenfen.addView(linearLayout);
            }
        }
    }


    @Override
    public void PayHelpSuccess(ResponseBean responseBean) {

    }

    @Override
    public void DetailRecordSuccess(NFCDetailListDataBean responseBean) {

    }

    @Override
    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
    }

    MyDialog selectDialog;
    ImageView iv_thumb;
    TextView tv_name, tv_time, tv_off, tv_surenum, tv_title;

    public void setDialog(String avatar, String name, String time, boolean clickBottomBtn) {
        if (selectOverDialog != null && selectOverDialog.isShowing()) {
            selectOverDialog.dismiss();
        }
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_nfctishi);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            iv_thumb = selectDialog.findViewById(R.id.iv_thumb);
            tv_name = selectDialog.findViewById(R.id.tv_name);
            tv_time = selectDialog.findViewById(R.id.tv_time);
            tv_off = selectDialog.findViewById(R.id.tv_off);
            tv_title = selectDialog.findViewById(R.id.tv_title);
            tv_surenum = selectDialog.findViewById(R.id.tv_surenum);
            tv_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
            tv_surenum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                    nfcScan = true;
                    Intent intent = new Intent(mContext, NFCEnergyZhuRuActivityNew.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("nfc_sn", nfc_sn);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                }
            });
        } else {
            selectDialog.show();
        }
        GlideDownLoadImage.getInstance().loadCircleImage(avatar, iv_thumb);
        tv_name.setText(name);
        tv_time.setText("申请时间: " + time);
        tv_title.setText("为了您能获得更好的生命能量，请您\n点击“导入生命能量”，快速补充\n缺失的生命元素");
        if (diffRecordNum == totalRecordNum) {
            tv_surenum.setText("导入生命能量  " + totalRecordNum);
        } else {
            tv_surenum.setText("导入生命能量  " + diffRecordNum + "/" + totalRecordNum);
        }
        if (clickBottomBtn) {
            tv_off.setVisibility(View.GONE);
            tv_surenum.setText("导入");
        } else {
            tv_off.setVisibility(View.VISIBLE);
        }
    }


    MyDialog selectOverDialog;

    public void setOverDialog() {
        if (selectDialog != null && selectDialog.isShowing()) {
            selectDialog.dismiss();
        }
        if (selectOverDialog == null) {
            selectOverDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_nfc_over);// 创建Dialog并设置样式主题
            selectOverDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectOverDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectOverDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectOverDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectOverDialog.getWindow().setAttributes(p); //设置生效
            selectOverDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    doFinish();
                    return true;
                }
            });
            TextView tv_off = selectOverDialog.findViewById(R.id.tv_off);
            TextView tv_sure = selectOverDialog.findViewById(R.id.tv_sure);
            tv_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doFinish();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentd = new Intent(mContext, ActionDetailActivity.class);
                    intentd.putExtra("goods_id", goods_id);
                    intentd.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intentd);
                    doFinish();
                }
            });
        } else {
            selectOverDialog.show();
        }
    }


    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
    }

    boolean nfcScan = false;

    protected NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;

    /**
     * onCreat->onStart->onResume->onPause->onStop->onDestroy
     * 启动Activity，界面可见时.
     */
    @Override
    protected void onStart() {
        super.onStart();
        //此处adapter需要重新获取，否则无法获取message
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //一旦截获NFC消息，就会通过PendingIntent调用窗口
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);
    }

    /**
     * 获得焦点，按钮可以点击
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();
        if (nfcScan) {
            mPresent.getDetail(order_id, nfc_sn);
        }
        //设置处理优于所有其他NFC的处理
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    /**
     * 暂停Activity，界面获取焦点，按钮可以点击
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onPause() {
        super.onPause();
        //恢复默认状态
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    //初次判断是什么类型的NFC卡
    private void resolveIntent(Intent intent) {
        String loginStatus = (String) SharedPreferencesHelper.get(mActivity, "loginStatus", "");
        if (loginStatus.equals(ConstantManager.loginStatus)) {
            Log.e("Payload", "resolveIntent=");
            NdefMessage[] msgs = getNdefMsg(intent); //重点功能，解析nfc标签中的数据
            if (msgs == null) {
                Toast.makeText(this, "非NFC启动", Toast.LENGTH_SHORT).show();
            } else {
                setNFCMsgView(msgs);
            }
        } else {
            SharedPreferencesHelper.put(mContext, "loginSkipToStatus", ConstantManager.loginSkipToHome);
            Intent intents = new Intent(mContext, LoginActivity.class);
            intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mContext.startActivity(intents);
            doFinish();
        }
    }

    /**
     * 显示扫描后的信息
     *
     * @param ndefMessages ndef数据
     */
    private void setNFCMsgView(NdefMessage[] ndefMessages) {
        if (ndefMessages == null || ndefMessages.length == 0) {
            return;
        }
        String Payload = new String(ndefMessages[0].getRecords()[0].getPayload());
        if (!TextUtils.isEmpty(Payload)) {
            try {
                JSONObject jsonObject = new JSONObject(Payload);
                order_id = jsonObject.optString("order_id");
                nfc_sn = jsonObject.optString("nfc_sn");
                Log.e("Payload", "order_id=" + order_id + ",nfc_sn=" + nfc_sn);
                nfcScan = false;
                mPresent.getDetail(order_id, nfc_sn);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showToast("非NFC启动");
            doFinish();
        }
    }


    //初次判断是什么类型的NFC卡
    public static NdefMessage[] getNdefMsg(Intent intent) {
        if (intent == null)
            return null;
        //nfc卡支持的格式
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String[] temp = tag.getTechList();
        for (String s : temp) {
            Log.i("getNdefMsg", "resolveIntent tag: " + s);
        }
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            Parcelable[] rawMessage = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] ndefMessages = null;
            // 判断是哪种类型的数据 默认为NDEF格式
            if (rawMessage != null) {
                Log.i("getNdefMsg", "getNdefMsg: ndef格式 ");
                ndefMessages = new NdefMessage[rawMessage.length];
                for (int i = 0; i < rawMessage.length; i++) {
                    ndefMessages[i] = (NdefMessage) rawMessage[i];
                }
            } else {
                //未知类型 (公交卡类型)
                Log.i("getNdefMsg", "getNdefMsg: 未知类型");
                //对应的解析操作，在Github上有
            }
            return ndefMessages;
        }
        return null;
    }
}
