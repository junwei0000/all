package com.longcheng.lifecareplan.push.jpush.message;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.emergencys.CheckNeedOrder;
import com.longcheng.lifecareplan.modular.mine.relief.HelpReliefBean;
import com.longcheng.lifecareplan.modular.mine.relief.ReliefBottomInfoBean;
import com.longcheng.lifecareplan.modular.mine.relief.ReliefCommBean;
import com.longcheng.lifecareplan.modular.mine.relief.ReliefPopupBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：jun on
 * 时间：2020/3/4 10:53
 * 意图：结对子弹层
 */
public class PairingUtils {

    private static volatile PairingUtils INSTANCE;

    public static PairingUtils getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (PairingUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PairingUtils();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 是否有弹层显示
     *
     * @return
     */
    public boolean haveShowDialogStatus() {
        if ((selectDialog != null && selectDialog.isShowing())
                || (queue_items != null && queue_items.size() > 0)
                || (blessDaichonglist != null && blessDaichonglist.size() > 0)) {
            return true;
        }
        return false;
    }

    MyDialog selectDialog;
    Activity mActivity;

    /**
     * **********************************结对子 弹窗* 全局显示************************************
     */
    public void showPopupWindow(PairBean mPairBean) {
        selectDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_pairing);// 创建Dialog并设置样式主题
        selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        Window window = selectDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        selectDialog.show();
        WindowManager m = mActivity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
        p.height = (int) (p.width * 1.56);
        selectDialog.getWindow().setAttributes(p); //设置生效

        ImageView iv_head = (ImageView) selectDialog.findViewById(R.id.iv_head);
        TextView tv_name = (TextView) selectDialog.findViewById(R.id.tv_name);
        TextView tv_jieeqi = (TextView) selectDialog.findViewById(R.id.tv_jieeqi);
        tv_jieeqi.getBackground().setAlpha(92);
        LinearLayout layout_shenfen = (LinearLayout) selectDialog.findViewById(R.id.layout_shenfen);
        LinearLayout layout_phone = (LinearLayout) selectDialog.findViewById(R.id.layout_phone);
        TextView tv_phone = (TextView) selectDialog.findViewById(R.id.tv_phone);

        TextView tv_cancel = (TextView) selectDialog.findViewById(R.id.tv_cancel);
        TextView tv_sure = (TextView) selectDialog.findViewById(R.id.tv_sure);
        tv_cancel.setOnClickListener(dialogClick);
        tv_sure.setOnClickListener(dialogClick);
        layout_phone.setOnClickListener(dialogClick);
        selectDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                setTimeCancel();
                return true;
            }
        });
        tv_cancel.setTag(mPairBean);
        tv_sure.setTag(mPairBean);
        layout_phone.setTag(mPairBean);
        GlideDownLoadImage.getInstance().loadCircleImage(mPairBean.getSponsor_user_avatar(), iv_head);
        String name = CommonUtil.setName(mPairBean.getSponsor_user_name());
        tv_name.setText(name + "请求与您结对子");
        tv_jieeqi.setText(mPairBean.getJieqi_name());
        tv_phone.setText(mPairBean.getSponsor_user_phone());
        layout_shenfen.removeAllViews();
        ArrayList<PairBean> mImg_all = mPairBean.getImg_all();
        if (mImg_all != null && mImg_all.size() > 0) {
            for (PairBean info : mImg_all) {
                LinearLayout linearLayout = new LinearLayout(mActivity);
                ImageView imageView = new ImageView(mActivity);
                int dit = DensityUtil.dip2px(mActivity, 20);
                int jian = DensityUtil.dip2px(mActivity, 4);
                linearLayout.setPadding(0, 0, jian, 0);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(mActivity, info.getImage(), imageView);
                linearLayout.addView(imageView);
                layout_shenfen.addView(linearLayout);
            }
        }

        expireTime = mPairBean.getExpireTime();
        if (expireTime - 2 > 0) {
            Message msg = new Message();
            msg.what = Daojishistart;
            mHandler.sendMessage(msg);
            msg = null;
        } else {
            selectDialog.dismiss();
        }
    }

    int expireTime;
    private TimerTask timerTask;
    private final int Daojishistart = 0;
    private final int Daojishiover = 1;
    private MyHandler mHandler = new MyHandler();

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Daojishistart:
                    daojishi();
                    break;
                default:
                    break;
            }
        }
    }

    private void daojishi() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                expireTime--;
                Message msg = new Message();
                msg.what = Daojishiover;
                msg.arg1 = expireTime;
                mHandler.sendMessage(msg);
                msg = null;
                if (expireTime <= 1) {
                    this.cancel();
                    setDismiss();
                }
            }
        };
        new Timer().schedule(timerTask, 0, 1000);
    }

    private void setDismiss() {
        if (selectDialog != null)
            selectDialog.dismiss();
    }

    private void setTimeCancel() {
        if (timerTask != null) {
            timerTask.cancel();
            mHandler.removeCallbacks(timerTask);
        }
    }

    View.OnClickListener dialogClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PairBean mPairBean = (PairBean) v.getTag();
            switch (v.getId()) {
                case R.id.tv_cancel:
                    setDismiss();
                    setTimeCancel();
                    refusePairs(mPairBean.getBlessed_teacher_pairs_id());
                    break;
                case R.id.tv_sure:
                    agreeePairs(mPairBean.getBlessed_teacher_pairs_id());
                    break;
                case R.id.layout_phone:
                    DensityUtil.getPhoneToKey(mActivity, mPairBean.getSponsor_user_phone());
                    break;
                default:
                    break;
            }
        }
    };

    public void showDialog() {
        LoadingDialogAnim.show(mActivity);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mActivity);
    }

    /**
     * 获取当前推送的结对子的信息
     *
     * @param blessed_teacher_pairs_id
     */
    public void getPairsInfo(String blessed_teacher_pairs_id) {
        showDialog();
        if (selectDialog != null && selectDialog.isShowing()) {
            selectDialog.dismiss();
        }
        mActivity = ActivityManager.getScreenManager().getCurrentActivity();
        Observable<PairDataBean> observable = Api.getInstance().service.getPairsInfo(UserUtils.getUserId(mActivity), blessed_teacher_pairs_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PairDataBean>() {
                    @Override
                    public void accept(PairDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {
                            if (responseBean.getStatus().equals("200")) {
                                PairBean mPairBean = responseBean.getData();
                                showPopupWindow(mPairBean);
                            } else {
                                ToastUtils.showToast(responseBean.getMsg());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });
    }

    /**
     * 拒绝结对子
     *
     * @param blessed_teacher_pairs_id
     */
    public void refusePairs(String blessed_teacher_pairs_id) {
        showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.refusePairs(UserUtils.getUserId(mActivity), blessed_teacher_pairs_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {
                            if (responseBean.getStatus().equals("200")) {
                                ToastUtils.showToast(responseBean.getData());
                            } else {
                                ToastUtils.showToast(responseBean.getMsg());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });
    }

    /**
     * 同意结对子
     *
     * @param blessed_teacher_pairs_id
     */
    public void agreeePairs(String blessed_teacher_pairs_id) {
        showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.agreeePairs(UserUtils.getUserId(mActivity), blessed_teacher_pairs_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {
                            if (responseBean.getStatus().equals("200")) {
                                setDismiss();
                                setTimeCancel();
                                ToastUtils.showToast(responseBean.getData());
                            } else {
                                ToastUtils.showToast(responseBean.getMsg());
                            }

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });
    }

    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
    }


    /**
     * 祝福师代付
     * **********************向祝福师发起提现弹层****start*****************只在我家弹出*****************
     */
    ArrayList<PairUBean> queue_items;
    MyDialog CashPushDialog;

    public void userCashPushLis(Activity mActivity_) {
        mActivity = mActivity_;
        Observable<PairUDataBean> observable = Api.getInstance().service.userCashPushList(UserUtils.getUserId(mActivity), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PairUDataBean>() {
                    @Override
                    public void accept(PairUDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            PairUBean mPairBean = responseBean.getData();
                            queue_items = mPairBean.getQueue_items();
                            if (queue_items != null && queue_items.size() > 0) {
                                showCashPushDialog(queue_items.get(queue_items.size() - 1));
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }


    public void showCashPushDialog(PairUBean mPairBean) {
        if (CashPushDialog != null && CashPushDialog.isShowing()) {
            CashPushDialog.dismiss();
        }
        CashPushDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_pairing_u);// 创建Dialog并设置样式主题
        CashPushDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        Window window = CashPushDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        CashPushDialog.show();
        WindowManager m = mActivity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = CashPushDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
        p.height = (int) (p.width * 1.56);
        CashPushDialog.getWindow().setAttributes(p); //设置生效

        ImageView iv_head = (ImageView) CashPushDialog.findViewById(R.id.iv_head);
        TextView tv_name = (TextView) CashPushDialog.findViewById(R.id.tv_name);
        TextView tv_jieeqi = (TextView) CashPushDialog.findViewById(R.id.tv_jieeqi);
        tv_jieeqi.getBackground().setAlpha(92);
        LinearLayout layout_shenfen = (LinearLayout) CashPushDialog.findViewById(R.id.layout_shenfen);
        LinearLayout layout_money = (LinearLayout) CashPushDialog.findViewById(R.id.layout_money);
        layout_money.getBackground().setAlpha(92);
        TextView tv_money = (TextView) CashPushDialog.findViewById(R.id.tv_money);
        TextView tv_cancel = (TextView) CashPushDialog.findViewById(R.id.tv_cancel);
        TextView tv_sure = (TextView) CashPushDialog.findViewById(R.id.tv_sure);
        tv_cancel.setTag(mPairBean);
        tv_sure.setTag(mPairBean);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CashPushDialog.dismiss();
                PairUBean mPairBean_ = (PairUBean) v.getTag();
                refusePairsU(mPairBean_.getUser_zhufubao_order_id());
                showNextTiXDialog();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CashPushDialog.dismiss();
                PairUBean mPairBean_ = (PairUBean) v.getTag();
                agreeePairsU(mPairBean_.getUser_zhufubao_order_id());
                showNextTiXDialog();
            }
        });
        GlideDownLoadImage.getInstance().loadCircleImage(mPairBean.getAvatar(), iv_head);
        String name = CommonUtil.setName(mPairBean.getUser_name());
        tv_name.setText(name + "发起提现");
        tv_jieeqi.setText(mPairBean.getJieqi_name());
        tv_money.setText(mPairBean.getMoney());
        layout_shenfen.removeAllViews();
        ArrayList<String> mImg_all = mPairBean.getImg_all();
        if (mImg_all != null && mImg_all.size() > 0) {
            for (String url : mImg_all) {
                LinearLayout linearLayout = new LinearLayout(mActivity);
                ImageView imageView = new ImageView(mActivity);
                int dit = DensityUtil.dip2px(mActivity, 20);
                int jian = DensityUtil.dip2px(mActivity, 4);
                linearLayout.setPadding(0, 0, jian, 0);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(mActivity, url, imageView);
                linearLayout.addView(imageView);
                layout_shenfen.addView(linearLayout);
            }
        }
    }

    /**
     * 显示下一个提现弹窗
     */
    private void showNextTiXDialog() {
        if (queue_items != null && queue_items.size() > 0) {
            queue_items.remove(queue_items.size() - 1);
            if (queue_items != null && queue_items.size() > 0) {
                showCashPushDialog(queue_items.get(queue_items.size() - 1));
            }
        }
    }

    /**
     * 拒绝 代付订单
     *
     * @param user_zhufubao_order_id
     */
    public void refusePairsU(String user_zhufubao_order_id) {
        showDialog();
        Observable<EditListDataBean> observable = Api.getInstance().service.teacherCancelCashOrder(
                UserUtils.getUserId(mActivity), user_zhufubao_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        dismissDialog();
                        ToastUtils.showToast(responseBean.getMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });
    }

    /**
     * 同意 接收代付订单
     *
     * @param user_zhufubao_order_id
     */
    public void agreeePairsU(String user_zhufubao_order_id) {
        showDialog();
        Observable<EditListDataBean> observable = Api.getInstance().service.teacherReceiveCashOrder(
                UserUtils.getUserId(mActivity), user_zhufubao_order_id, 0, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        dismissDialog();
                        ToastUtils.showToast(responseBean.getMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });
    }

    /**
     * *****祝福师代充***********************************************************************
     */
    ArrayList<PairUBean> blessDaichonglist;
    MyDialog blessDCDialog;

    public void blessDaichongLayer() {
        mActivity = ActivityManager.getScreenManager().getCurrentActivity();
        Observable<PairUDataBean> observable = Api.getInstance().service.blessDaichongLayer(UserUtils.getUserId(mActivity), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PairUDataBean>() {
                    @Override
                    public void accept(PairUDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            PairUBean mPairBean = responseBean.getData();
                            blessDaichonglist = mPairBean.getQueue_items();
                            if (blessDaichonglist != null && blessDaichonglist.size() > 0) {
                                showBlessDCDialog(blessDaichonglist.get(blessDaichonglist.size() - 1));
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }


    public void showBlessDCDialog(PairUBean mPairBean) {
        if (blessDCDialog != null && blessDCDialog.isShowing()) {
            blessDCDialog.dismiss();
        }
        blessDCDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_pairing_u);// 创建Dialog并设置样式主题
        blessDCDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        Window window = blessDCDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        blessDCDialog.show();
        WindowManager m = mActivity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = blessDCDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
        p.height = (int) (p.width * 1.56);
        blessDCDialog.getWindow().setAttributes(p); //设置生效


        LinearLayout layout_bg = (LinearLayout) blessDCDialog.findViewById(R.id.layout_bg);
        layout_bg.setBackgroundResource(R.mipmap.my_pairing_bless);
        ImageView iv_head = (ImageView) blessDCDialog.findViewById(R.id.iv_head);
        TextView tv_name = (TextView) blessDCDialog.findViewById(R.id.tv_name);
        TextView tv_jieeqi = (TextView) blessDCDialog.findViewById(R.id.tv_jieeqi);
        tv_jieeqi.getBackground().setAlpha(92);
        LinearLayout layout_shenfen = (LinearLayout) blessDCDialog.findViewById(R.id.layout_shenfen);
        LinearLayout layout_money = (LinearLayout) blessDCDialog.findViewById(R.id.layout_money);
        layout_money.getBackground().setAlpha(92);

        TextView tv_moneytitle = (TextView) blessDCDialog.findViewById(R.id.tv_moneytitle);
        TextView tv_money = (TextView) blessDCDialog.findViewById(R.id.tv_money);
        TextView tv_cancel = (TextView) blessDCDialog.findViewById(R.id.tv_cancel);
        TextView tv_sure = (TextView) blessDCDialog.findViewById(R.id.tv_sure);
        tv_sure.setBackgroundResource(R.drawable.corners_oval_red);
        tv_cancel.setTag(mPairBean);
        tv_sure.setTag(mPairBean);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blessDCDialog.dismiss();
                PairUBean mPairBean_ = (PairUBean) v.getTag();
                refuseBlessDC(mPairBean_.getUser_zhufubao_order_id());
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blessDCDialog.dismiss();
                PairUBean mPairBean_ = (PairUBean) v.getTag();
                agreeeBlessDC(mPairBean_.getUser_zhufubao_order_id());
            }
        });
        GlideDownLoadImage.getInstance().loadCircleImage(mPairBean.getAvatar(), iv_head);
        String name = CommonUtil.setName(mPairBean.getUser_name());
        tv_name.setText(name + "发起代充");
        tv_jieeqi.setText(mPairBean.getJieqi_name());
        tv_moneytitle.setText("代充金额");
        tv_money.setText(mPairBean.getMoney());
        layout_shenfen.removeAllViews();
        ArrayList<String> mImg_all = mPairBean.getImg_all();
        if (mImg_all != null && mImg_all.size() > 0) {
            for (String url : mImg_all) {
                LinearLayout linearLayout = new LinearLayout(mActivity);
                ImageView imageView = new ImageView(mActivity);
                int dit = DensityUtil.dip2px(mActivity, 20);
                int jian = DensityUtil.dip2px(mActivity, 4);
                linearLayout.setPadding(0, 0, jian, 0);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(mActivity, url, imageView);
                linearLayout.addView(imageView);
                layout_shenfen.addView(linearLayout);
            }
        }
    }

    /**
     * 显示下一个代充弹窗
     */
    private void showNextDCDialog() {
        if (blessDaichonglist != null && blessDaichonglist.size() > 0) {
            Log.e("showNextDCDialog", "blessDaichonglist.size()==" + blessDaichonglist.size());
            blessDaichonglist.remove(blessDaichonglist.size() - 1);
            if (blessDaichonglist != null && blessDaichonglist.size() > 0) {
                showBlessDCDialog(blessDaichonglist.get(blessDaichonglist.size() - 1));
            }
        }
    }

    /**
     * 拒绝 代充- 祝福师
     *
     * @param user_zhufubao_order_id
     */
    public void refuseBlessDC(String user_zhufubao_order_id) {
        showDialog();
        Observable<EditListDataBean> observable = Api.getInstance().service.teacherCancelRechargeOrder(
                UserUtils.getUserId(mActivity), user_zhufubao_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        dismissDialog();
                        ToastUtils.showToast(responseBean.getMsg());
                        showNextDCDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });
    }

    /**
     * 同意 代充 祝福师接单
     *
     * @param user_zhufubao_order_id
     */
    public void agreeeBlessDC(String user_zhufubao_order_id) {
        showDialog();
        Observable<EditListDataBean> observable = Api.getInstance().service.teacherReceiveRechargeOrder(
                UserUtils.getUserId(mActivity), user_zhufubao_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        dismissDialog();
                        ToastUtils.showToast(responseBean.getMsg());
                        showNextDCDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });
    }


    /**
     * *****救急弹窗帮助***********************************************************************
     */
    MyDialog helpDialog;

    public void showHelpDialog(CheckNeedOrder bean) {
        LogUtils.e(bean.getData().size() + "showHelpDialog");
        if (bean.getData() != null) {
            if (bean.getData().size() > 0) {
                for (int i = 0; i < bean.getData().size(); i++) {
                    MyDialog helpDialogNew = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_help);// 创建Dialog并设置样式主题
                    helpDialogNew.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
                    Window window = helpDialogNew.getWindow();
                    window.setGravity(Gravity.CENTER);
                    helpDialogNew.show();
                    WindowManager m = mActivity.getWindowManager();
                    Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
                    WindowManager.LayoutParams p = helpDialogNew.getWindow().getAttributes(); //获取对话框当前的参数值
                    p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
                    p.height = (int) (p.width * 1.56);
                    helpDialogNew.getWindow().setAttributes(p); //设置生效
                    ImageView iv_head = (ImageView) helpDialogNew.findViewById(R.id.iv_head);
                    TextView tv_name = (TextView) helpDialogNew.findViewById(R.id.tv_help_name);
                    TextView tv_money = (TextView) helpDialogNew.findViewById(R.id.tv_money);
                    TextView tv_ChangeCard = (TextView) helpDialogNew.findViewById(R.id.tv_ChangeCard);
                    TextView tv_sure = (TextView) helpDialogNew.findViewById(R.id.tv_sure);
                    tv_name.setText(bean.getData().get(i).getReceive_user_name());
                    tv_money.setText(bean.getData().get(i).getSuper_ability());
                    tv_ChangeCard.setTag(i);
                    GlideDownLoadImage.getInstance().loadCircleImage(bean.getData().get(i).getReceive_user_avatar(), iv_head);
                    int finalI = i;
                    tv_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            helpDialogNew.dismiss();
                            help(bean.getData().get(finalI).getHelp_need_order_id());

                        }
                    });
                    tv_ChangeCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            helpDialogNew.dismiss();
                            refuse(bean.getData().get(finalI).getHelp_need_order_id());

                        }
                    });
                }
            }
        }


    }

    /**
     * 救急弹窗
     */
    public void checkNeedOrder() {
        Observable<CheckNeedOrder> observable = Api.getInstance().service.checkNeedOrder(
                UserUtils.getUserId(mActivity), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CheckNeedOrder>() {
                    @Override
                    public void accept(CheckNeedOrder responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            ToastUtils.showToast(responseBean.getMsg());
                            showHelpDialog(responseBean);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                    }
                });
    }


    /**
     * 帮助他
     */
    public void help(int help_need_order_id) {

        Observable<ResponseBean> observable = Api.getInstance().service.receive(
                UserUtils.getUserId(mActivity), help_need_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {

                            ToastUtils.showToast(responseBean.getMsg());



                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {


                    }
                });
    }

    /**
     * 帮助他
     */
    public void refuse(int help_need_order_id) {
        Observable<ResponseBean> observable = Api.getInstance().service.refuse(
                UserUtils.getUserId(mActivity), help_need_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {


                            ToastUtils.showToast(responseBean.getMsg());



                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    /**
     * 救济推荐弹框--------------------------------------------------------------------
     */

    MyDialog reliefRecomDialog;

    public void showReliefRecomDialog() {


        reliefRecomDialog = new MyDialog(mActivity, R.style.dialog, R.layout.activity_reliefrecom);// 创建Dialog并设置样式主题
        reliefRecomDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        Window window = reliefRecomDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        reliefRecomDialog.show();
        WindowManager m = mActivity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = reliefRecomDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
//        p.height = (int) (p.width * 2);
        reliefRecomDialog.getWindow().setAttributes(p); //设置生效

        TextView tv_search = (TextView) reliefRecomDialog.findViewById(R.id.tv_search);
        TextView tv_confirm = (TextView) reliefRecomDialog.findViewById(R.id.tv_confirm);
        ImageView iv_close = (ImageView) reliefRecomDialog.findViewById(R.id.iv_close);
        TextView tv_titleprompt = (TextView) reliefRecomDialog.findViewById(R.id.tv_titleprompt);
        SupplierEditText et_phone = (SupplierEditText) reliefRecomDialog.findViewById(R.id.add_et_phone);

        SpannableString sStr = new SpannableString("前100名");
        sStr.setSpan(new AbsoluteSizeSpan(44, true), 1, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new AbsoluteSizeSpan(20, true), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new AbsoluteSizeSpan(20, true), 4, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new ForegroundColorSpan(mActivity.getResources().getColor(R.color.color_f6fe7c)), 1, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 1, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_titleprompt.setText(sStr);
        tv_titleprompt.setMovementMethod(LinkMovementMethod.getInstance());

        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_phone.getText()) || et_phone.getText().toString() == null) {
                    ToastUtils.showToast("手机号不能为空");
                } else {
                    searchPhone(et_phone.getText().toString(), reliefRecomDialog);
                }
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_phone.getText()) || et_phone.getText().toString() == null) {
                    ToastUtils.showToast("手机号不能为空");
                } else {
                    confirmRecom(et_phone.getText().toString());
                }
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", "onClick: " + "------------");
                reliefRecomDialog.cancel();
            }
        });


    }

    //是否可以推荐用户弹窗
    public void reliefRecomPopup() {
        String user_id = (String) SharedPreferencesHelper.get(mActivity, "user_id", "");
        String token = ExampleApplication.token;
        Observable<ReliefPopupBean> observable = Api.getInstance().service.isReliefPopou(user_id, token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBean -> {
                    dismissDialog();
                    if (responseBean != null) {
                        Log.d("-----------", "reliefRecomPopup: " + responseBean.status);
                        if (!TextUtils.isEmpty(responseBean.status) && responseBean.status.equals("200")) {
                            showReliefRecomDialog();
                        } else {
//
                        }
                    }
                }, throwable -> {
                    dismissDialog();
                });
    }

    //推荐---搜索手机号
    public void searchPhone(String phone, MyDialog reliefRecomDialog) {
        showDialog();
        String user_id = (String) SharedPreferencesHelper.get(mActivity, "user_id", "");
        String token = ExampleApplication.token;

        Observable<ReliefCommBean> observable = Api.getInstance().service.searchPhone(user_id, token, phone);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBean -> {
                    dismissDialog();
                    LinearLayout ll_usertab_desc = (LinearLayout) reliefRecomDialog.findViewById(R.id.ll_usertab_desc);
                    ConstraintLayout constraintLayout = (ConstraintLayout) reliefRecomDialog.findViewById(R.id.constraintLayout);
                    ImageView item_iv_thumb = (ImageView) reliefRecomDialog.findViewById(R.id.item_iv_thumb);
                    TextView item_tv_name = (TextView) reliefRecomDialog.findViewById(R.id.item_tv_name);
                    TextView tv_usertab_desc = (TextView) reliefRecomDialog.findViewById(R.id.tv_usertab_desc);
                    LinearLayout item_layout_shenfen = (LinearLayout) reliefRecomDialog.findViewById(R.id.item_layout_shenfen);
                    if (responseBean != null) {
                        if (!TextUtils.isEmpty(responseBean.status) && responseBean.status.equals("200")) {

                            if (responseBean.data != null) {
                                ll_usertab_desc.setVisibility(View.GONE);
                                constraintLayout.setVisibility(View.VISIBLE);
                                item_layout_shenfen.removeAllViews();
                                List<ReliefCommBean.DataBean.ImgsBean.ImgBean> images = responseBean.data.imgs.img;
                                if (images != null && images.size() > 0) {
                                    for (ReliefCommBean.DataBean.ImgsBean.ImgBean imgBean : images) {
                                        LinearLayout linearLayout = new LinearLayout(mActivity);
                                        ImageView imageView = new ImageView(mActivity);
                                        int dit = DensityUtil.dip2px(mActivity, 18);
                                        int jian = DensityUtil.dip2px(mActivity, 3);
                                        linearLayout.setPadding(0, 2, jian, 2);
                                        imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                                        GlideDownLoadImage.getInstance().loadCircleImageCommune(mActivity, imgBean.image, imageView);
                                        linearLayout.addView(imageView);
                                        item_layout_shenfen.addView(linearLayout);
                                    }
                                }

                                item_tv_name.setText(TextUtils.isEmpty(responseBean.data.user_name) ? "" : responseBean.data.user_name);
                                GlideDownLoadImage.getInstance().loadCircleImageCommune(mActivity, responseBean.data.avatar, item_iv_thumb);
                            } else {

                            }

                        } else {

                            constraintLayout.setVisibility(View.GONE);
                            ll_usertab_desc.setVisibility(View.VISIBLE);
                            tv_usertab_desc.setText(responseBean.msg);
                        }
                    }
                }, throwable -> {
                    dismissDialog();
                });
    }

    //推荐---确认
    public void confirmRecom(String phone) {
        showDialog();
        String user_id = (String) SharedPreferencesHelper.get(mActivity, "user_id", "");
        String token = ExampleApplication.token;

        Observable<ReliefCommBean> observable = Api.getInstance().service.confirmRecom(user_id, token, phone);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBean -> {
                    dismissDialog();
                    if (responseBean != null) {
                        if (!TextUtils.isEmpty(responseBean.status) && responseBean.status.equals("200")) {

                            reliefRecomDialog.cancel();
                        } else {

                        }
                    }
                }, throwable -> {
                    dismissDialog();
                });
    }


    public void applyRelief() {
        String user_id = (String) SharedPreferencesHelper.get(mActivity, "user_id", "");
        String token = ExampleApplication.token;

        Observable<HelpReliefBean> observable = Api.getInstance().service.helpRelief(user_id, token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBean -> {
                    dismissDialog();
                    if (responseBean != null) {
                        if (!TextUtils.isEmpty(responseBean.status) && responseBean.status.equals("200")) {
                            List<HelpReliefBean.DataBean> data = responseBean.data;
                            showHelpReliefDialog(data);
                        } else {

                        }
                    }
                }, throwable -> {
                    dismissDialog();
                });
    }


    public void showHelpReliefDialog(List<HelpReliefBean.DataBean> data) {

        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                helpDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_help);// 创建Dialog并设置样式主题
                helpDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
                Window window = helpDialog.getWindow();
                window.setGravity(Gravity.CENTER);
                helpDialog.show();
                WindowManager m = mActivity.getWindowManager();
                Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
                WindowManager.LayoutParams p = helpDialog.getWindow().getAttributes(); //获取对话框当前的参数值
                p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
                p.height = (int) (p.width * 1.56);
                helpDialog.getWindow().setAttributes(p); //设置生效
                ImageView iv_head = (ImageView) helpDialog.findViewById(R.id.iv_head);
                TextView tv_name = (TextView) helpDialog.findViewById(R.id.tv_help_name);
                TextView tv_money = (TextView) helpDialog.findViewById(R.id.tv_money);
                TextView tv_ChangeCard = (TextView) helpDialog.findViewById(R.id.tv_ChangeCard);
                TextView tv_sure = (TextView) helpDialog.findViewById(R.id.tv_sure);
                tv_name.setText(data.get(i).user_name);
                tv_money.setText(data.get(i).number);
                GlideDownLoadImage.getInstance().loadCircleImage(data.get(i).avatar, iv_head);
                int finalI = i;
                tv_sure.setOnClickListener(v -> {
                    helpRelief(data.get(finalI).id, "2");
                });
                tv_ChangeCard.setOnClickListener(v -> {
                    helpRelief(data.get(finalI).id, "1");
                });
            }
        }


    }

    private void helpRelief(String id, String result) {
        showDialog();
        String user_id = (String) SharedPreferencesHelper.get(mActivity, "user_id", "");
        String token = ExampleApplication.token;
        Observable<HelpReliefBean> observable = Api.getInstance().service.dealHelp(user_id, token, id, result);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBean -> {
                    dismissDialog();
                    if (responseBean != null) {
                        if (!TextUtils.isEmpty(responseBean.status) && responseBean.status.equals("200")) {
                            helpDialog.cancel();

                        } else {
//                            Toast.makeText(mActivity, responseBean.msg + "", Toast.LENGTH_SHORT).show();
                            helpDialog.cancel();
                        }
                    }
                }, throwable -> {
                    dismissDialog();
                });

    }


}

