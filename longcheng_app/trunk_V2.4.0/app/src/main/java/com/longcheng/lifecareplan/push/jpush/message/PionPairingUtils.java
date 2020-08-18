package com.longcheng.lifecareplan.push.jpush.message;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.emergencys.CheckNeedOrder;
import com.longcheng.lifecareplan.modular.mine.relief.HelpReliefBean;
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
public class PionPairingUtils {

    private static volatile PionPairingUtils INSTANCE;

    public static PionPairingUtils getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (PionPairingUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PionPairingUtils();
                }
            }
        }
        return INSTANCE;
    }

    Activity mActivity;


    public void showDialog() {
        LoadingDialogAnim.show(mActivity);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mActivity);
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

    double Cashmoney_limit;

    public void userCashPushLis() {
        mActivity = ActivityManager.getScreenManager().getCurrentActivity();
        Observable<PairUDataBean> observable = Api.getInstance().service.PionuserCashPushList(UserUtils.getUserId(mActivity), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PairUDataBean>() {
                    @Override
                    public void accept(PairUDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            PairUBean mPairBean = responseBean.getData();
                            queue_items = mPairBean.getQueue_items();
                            Cashmoney_limit = mPairBean.getMoney_limit();
                            if (queue_items != null && queue_items.size() > 0) {
                                showCashPushDialog(queue_items.get(0));
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
        //余额不足不弹提示
        if (Cashmoney_limit < Double.parseDouble(mPairBean.getMoney())) {
            return;
        }
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
        TextView tv_moneytitle = (TextView) CashPushDialog.findViewById(R.id.tv_moneytitle);
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
                agreeePairsU(mPairBean_.getUser_zhufubao_order_id(), mPairBean_.getMoney());
            }
        });
        GlideDownLoadImage.getInstance().loadCircleImage(mPairBean.getAvatar(), iv_head);
        String name = CommonUtil.setName(mPairBean.getUser_name());
        tv_name.setText(name + "正在敬售福祺宝");
        tv_moneytitle.setText("敬售数量 ");
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
            queue_items.remove(0);
            if (queue_items != null && queue_items.size() > 0) {
                showCashPushDialog(queue_items.get(0));
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
        Observable<EditListDataBean> observable = Api.getInstance().service.PionteacherCancelCashOrder(
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
    public void agreeePairsU(String user_zhufubao_order_id, String money) {
        showDialog();
        Observable<EditListDataBean> observable = Api.getInstance().service.PionteacherReceiveCashOrder(
                UserUtils.getUserId(mActivity), user_zhufubao_order_id, 0, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        dismissDialog();
                        ToastUtils.showToast(responseBean.getMsg());
                        Cashmoney_limit = Cashmoney_limit - Double.parseDouble(money);
                        showNextTiXDialog();
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
    double Daichongmoney_limit;

    public void blessDaichongLayer() {
        mActivity = ActivityManager.getScreenManager().getCurrentActivity();
        Observable<PairUDataBean> observable = Api.getInstance().service.PionblessDaichongLayer(UserUtils.getUserId(mActivity), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PairUDataBean>() {
                    @Override
                    public void accept(PairUDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            PairUBean mPairBean = responseBean.getData();
                            blessDaichonglist = mPairBean.getQueue_items();
                            Daichongmoney_limit = mPairBean.getMoney_limit();
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

        //余额不足不弹提示
        if (Daichongmoney_limit < Double.parseDouble(mPairBean.getMoney())) {
            return;
        }
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
                agreeeBlessDC(mPairBean_.getUser_zhufubao_order_id(), mPairBean_.getMoney());
            }
        });
        GlideDownLoadImage.getInstance().loadCircleImage(mPairBean.getAvatar(), iv_head);
        String name = CommonUtil.setName(mPairBean.getUser_name());
        tv_name.setText(name + "发起祝佑宝请购");
        tv_jieeqi.setText(mPairBean.getJieqi_name());
        tv_moneytitle.setText("请购金额 ");
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
        Observable<EditListDataBean> observable = Api.getInstance().service.PionteacherCancelRechargeOrder(
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
    public void agreeeBlessDC(String user_zhufubao_order_id, String money) {
        showDialog();
        Observable<EditListDataBean> observable = Api.getInstance().service.PionteacherReceiveRechargeOrder(
                UserUtils.getUserId(mActivity), user_zhufubao_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        dismissDialog();
                        ToastUtils.showToast(responseBean.getMsg());
                        Daichongmoney_limit = Daichongmoney_limit - Double.parseDouble(money);
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


}

