package com.longcheng.lifecareplan.push.jpush.message;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
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
import com.longcheng.lifecareplan.modular.helpwith.connon.activity.CHelpDetailAddActivity;
import com.longcheng.lifecareplan.modular.helpwith.connon.activity.CHelpGoWithActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.zudui.ZYBZuDuiActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.activity.df.PionDaiFuActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.push.jpush.message.bean.PairUBean;
import com.longcheng.lifecareplan.push.jpush.message.bean.PairUDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;

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

    //************************福祺宝**创业导师组队**************************************

    /**
     * 邀请组队弹层
     */
    public void layerTeamInfo() {
        mActivity = ActivityManager.getScreenManager().getCurrentActivity();
        Observable<PairUDataBean> observable = Api.getInstance().service.layerTeamInvite(UserUtils.getUserId(mActivity), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PairUDataBean>() {
                    @Override
                    public void accept(PairUDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            PairUBean data = responseBean.getData();
                            if (!TextUtils.isEmpty(data.getEntrepreneurs_team_invitation_id())) {
                                showInviteDialog(data);
                            } else {
                                if (mInviteDialog != null && mInviteDialog.isShowing()) {
                                    mInviteDialog.dismiss();
                                }
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    MyDialog mInviteDialog;

    public void showInviteDialog(PairUBean mPairBean) {
        if (mInviteDialog != null && mInviteDialog.isShowing()) {
            mInviteDialog.dismiss();
        }
        mInviteDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_pion_invite);// 创建Dialog并设置样式主题
        mInviteDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        Window window = mInviteDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        mInviteDialog.show();
        WindowManager m = mActivity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = mInviteDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
        mInviteDialog.getWindow().setAttributes(p); //设置生效

        TextView tv_title = mInviteDialog.findViewById(R.id.tv_title);
        TextView tv_cancel = mInviteDialog.findViewById(R.id.tv_cancel);
        TextView tv_jihuo = (TextView) mInviteDialog.findViewById(R.id.tv_jihuo);
        tv_cancel.setTag(mPairBean);
        tv_jihuo.setTag(mPairBean);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInviteDialog.dismiss();
                PairUBean mPairBean_ = (PairUBean) v.getTag();
                refuseZuDuiInvite(mPairBean_.getEntrepreneurs_team_invitation_id());
            }
        });
        tv_jihuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInviteDialog.dismiss();
                PairUBean mPairBean_ = (PairUBean) v.getTag();
                agreeeZuDuiInvite(mPairBean_.getEntrepreneurs_team_invitation_id());
            }
        });
        String name = CommonUtil.setName(mPairBean.getSponsor_user_name());
        tv_title.setText(name + "创业导师邀请您加入\n5人订单组队");
    }

    /**
     * 拒绝组队邀请
     *
     * @param entrepreneurs_team_invitation_id
     */
    public void refuseZuDuiInvite(String entrepreneurs_team_invitation_id) {
        showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.refuseZuDuiInvite(
                UserUtils.getUserId(mActivity), entrepreneurs_team_invitation_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
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
     * 同意组队邀请
     *
     * @param entrepreneurs_team_invitation_id
     */
    public void agreeeZuDuiInvite(String entrepreneurs_team_invitation_id) {
        showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.agreeeZuDuiInvite(
                UserUtils.getUserId(mActivity), entrepreneurs_team_invitation_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        dismissDialog();
                        ToastUtils.showToast(responseBean.getMsg());
                        if (responseBean.getStatus().equals("200")) {
                            Intent intent = new Intent(mActivity, PionDaiFuActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("orderShowStatus", false);
                            mActivity.startActivity(intent);
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


    //**************************祝佑宝请购组队**************************************

    /**
     * 邀请组队弹层
     */
    public void layerZybUserTeamInfo() {
        mActivity = ActivityManager.getScreenManager().getCurrentActivity();
        Observable<PairUDataBean> observable = Api.getInstance().service.layerZybUserTeamInfo(UserUtils.getUserId(mActivity), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PairUDataBean>() {
                    @Override
                    public void accept(PairUDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            PairUBean data = responseBean.getData();
                            if (!TextUtils.isEmpty(data.getEntrepreneurs_team_invitation_id())) {
                                showZybUserInviteDialog(data);
                            } else {
                                if (mZybUserInviteDialog != null && mZybUserInviteDialog.isShowing()) {
                                    mZybUserInviteDialog.dismiss();
                                }
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    MyDialog mZybUserInviteDialog;

    public void showZybUserInviteDialog(PairUBean mPairBean) {
        if (mZybUserInviteDialog != null && mZybUserInviteDialog.isShowing()) {
            mZybUserInviteDialog.dismiss();
        }
        mZybUserInviteDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_pion_invite);// 创建Dialog并设置样式主题
        mZybUserInviteDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        Window window = mZybUserInviteDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        mZybUserInviteDialog.show();
        WindowManager m = mActivity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = mZybUserInviteDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
        mZybUserInviteDialog.getWindow().setAttributes(p); //设置生效

        ImageView iv_top = mZybUserInviteDialog.findViewById(R.id.iv_top);
        TextView tv_title = mZybUserInviteDialog.findViewById(R.id.tv_title);
        TextView tv_cancel = mZybUserInviteDialog.findViewById(R.id.tv_cancel);
        TextView tv_jihuo = mZybUserInviteDialog.findViewById(R.id.tv_jihuo);
        LinearLayout layout_top = mZybUserInviteDialog.findViewById(R.id.layout_top);
        layout_top.setBackgroundResource(R.drawable.corners_bg_zybtop);
        iv_top.setVisibility(View.VISIBLE);
        int hei = (int) (p.width * 0.27);
        iv_top.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hei));
        layout_top.setPadding(0, 0, 0, DensityUtil.dip2px(mActivity, 20));
        tv_title.setTextColor(mActivity.getResources().getColor(R.color.red));
        tv_cancel.setTag(mPairBean);
        tv_jihuo.setTag(mPairBean);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mZybUserInviteDialog.dismiss();
                PairUBean mPairBean_ = (PairUBean) v.getTag();
                refuseZybUserZuDuiInvite(mPairBean_.getEntrepreneurs_team_invitation_id());
            }
        });
        tv_jihuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mZybUserInviteDialog.dismiss();
                PairUBean mPairBean_ = (PairUBean) v.getTag();
                agreeeZybUserZuDuiInvite(mPairBean_.getEntrepreneurs_team_invitation_id());
            }
        });
        String name = CommonUtil.setName(mPairBean.getSponsor_user_name());
        tv_title.setText(name + "邀请您加入\n组队请购祝佑宝" + mPairBean.getMoney());
    }

    /**
     * 拒绝组队邀请
     *
     * @param entrepreneurs_team_invitation_id
     */
    public void refuseZybUserZuDuiInvite(String entrepreneurs_team_invitation_id) {
        showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.refuseZybUserZuDuiInvite(
                UserUtils.getUserId(mActivity), entrepreneurs_team_invitation_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
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
     * 同意组队邀请
     *
     * @param entrepreneurs_team_invitation_id
     */
    public void agreeeZybUserZuDuiInvite(String entrepreneurs_team_invitation_id) {
        showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.agreeeZybUserZuDuiInvite(
                UserUtils.getUserId(mActivity), entrepreneurs_team_invitation_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        dismissDialog();
                        ToastUtils.showToast(responseBean.getMsg());
                        if (responseBean.getStatus().equals("200")) {
                            Intent intent = new Intent(mActivity, ZYBZuDuiActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("zhuyoubao_team_room_id", "" + responseBean.getData());
                            mActivity.startActivity(intent);
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
     * *****************************天下无癌 结伴互祝 成员创建组完成弹层********************************
     */
    public void wuaiGoWithCreateTeamInfo() {
        mActivity = ActivityManager.getScreenManager().getCurrentActivity();
        Observable<PairUDataBean> observable = Api.getInstance().service.wuaiGoWithTeamInfo(UserUtils.getUserId(mActivity), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PairUDataBean>() {
                    @Override
                    public void accept(PairUDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            PairUBean data = responseBean.getData();
                            if (data.getIs_show() == 1) {
                                showWuaiGoWithDialog(data);
                            } else {
                                if (wuaiGoWithDialog != null && wuaiGoWithDialog.isShowing()) {
                                    wuaiGoWithDialog.dismiss();
                                }
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    MyDialog wuaiGoWithDialog;

    public void showWuaiGoWithDialog(PairUBean mPairBean) {
        if (wuaiGoWithDialog != null && wuaiGoWithDialog.isShowing()) {
            wuaiGoWithDialog.dismiss();
        }
        wuaiGoWithDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_pion_invite);// 创建Dialog并设置样式主题
        wuaiGoWithDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        Window window = wuaiGoWithDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        wuaiGoWithDialog.show();
        WindowManager m = mActivity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = wuaiGoWithDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
        wuaiGoWithDialog.getWindow().setAttributes(p); //设置生效

        TextView tv_title = wuaiGoWithDialog.findViewById(R.id.tv_title);
        TextView tv_cancel = wuaiGoWithDialog.findViewById(R.id.tv_cancel);
        TextView tv_jihuo = (TextView) wuaiGoWithDialog.findViewById(R.id.tv_jihuo);
        tv_cancel.setBackgroundResource(R.drawable.corners_bg_yellowbian);
        tv_jihuo.setBackgroundResource(R.drawable.corners_bg_yellow);
        tv_cancel.setTextColor(mActivity.getResources().getColor(R.color.yellow_E95D1B));
        tv_jihuo.setText("前往完成");
        tv_cancel.setTag(mPairBean);
        tv_jihuo.setTag(mPairBean);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wuaiGoWithDialog.dismiss();
                PairUBean mPairBean_ = (PairUBean) v.getTag();
                addItemHelpRecord(mPairBean_.getKnp_group_room_id(), mPairBean_.getKnp_group_item_id());
            }
        });
        tv_jihuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wuaiGoWithDialog.dismiss();
                PairUBean mPairBean_ = (PairUBean) v.getTag();
                addItemHelpRecord(mPairBean_.getKnp_group_room_id(), mPairBean_.getKnp_group_item_id());
                Intent intent = new Intent(mActivity, CHelpDetailAddActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("knp_group_room_id", "" + mPairBean_.getKnp_group_room_id());
                mActivity.startActivity(intent);
            }
        });
        tv_title.setText(mPairBean.getPerson_number() + "人结伴互祝组队创建完成\n请您立即前往完成组队互祝");
    }

    /**
     * 结伴互祝弹层-修改状态方法
     */
    public void addItemHelpRecord(String knp_group_room_id, String knp_group_item_id) {
        showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.addItemHelpRecord(
                UserUtils.getUserId(mActivity), knp_group_room_id, knp_group_item_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        dismissDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                    }
                });
    }

    /**
     * *****************************天下无癌 结伴互祝 发起组队邀请弹层********************************
     */
    public void wuaiGoWithInvite() {
        mActivity = ActivityManager.getScreenManager().getCurrentActivity();
        Observable<PairUDataBean> observable = Api.getInstance().service.wuaiGoWithInviteAlert(UserUtils.getUserId(mActivity), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PairUDataBean>() {
                    @Override
                    public void accept(PairUDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            PairUBean data = responseBean.getData();
                            if (data.getIs_alert() == 1) {
                                showWuaiGoWithInviteDialog(data.getItem());
                            } else {
                                if (wuaiGoWithInviteDialog != null && wuaiGoWithInviteDialog.isShowing()) {
                                    wuaiGoWithInviteDialog.dismiss();
                                }
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("ResponseBody", "data.getIs_alert()==============" + throwable.getMessage());
                    }
                });
    }

    MyDialog wuaiGoWithInviteDialog;

    public void showWuaiGoWithInviteDialog(PairUBean mPairBean) {
        if (wuaiGoWithInviteDialog != null && wuaiGoWithInviteDialog.isShowing()) {
            wuaiGoWithInviteDialog.dismiss();
        }
        wuaiGoWithInviteDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_pion_invite);// 创建Dialog并设置样式主题
        wuaiGoWithInviteDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        Window window = wuaiGoWithInviteDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        wuaiGoWithInviteDialog.show();
        WindowManager m = mActivity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = wuaiGoWithInviteDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
        wuaiGoWithInviteDialog.getWindow().setAttributes(p); //设置生效

        TextView tv_title = wuaiGoWithInviteDialog.findViewById(R.id.tv_title);
        TextView tv_cancel = wuaiGoWithInviteDialog.findViewById(R.id.tv_cancel);
        TextView tv_jihuo = (TextView) wuaiGoWithInviteDialog.findViewById(R.id.tv_jihuo);
        tv_cancel.setBackgroundResource(R.drawable.corners_bg_yellowbian);
        tv_jihuo.setBackgroundResource(R.drawable.corners_bg_yellow);
        tv_cancel.setTextColor(mActivity.getResources().getColor(R.color.yellow_E95D1B));
        tv_jihuo.setText("立即加入");
        tv_cancel.setTag(mPairBean);
        tv_jihuo.setTag(mPairBean);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wuaiGoWithInviteDialog.dismiss();
                PairUBean mPairBean_ = (PairUBean) v.getTag();
                refuseGoWithInvite(mPairBean_.getKnp_group_invitation_id());
            }
        });
        tv_jihuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wuaiGoWithInviteDialog.dismiss();
                PairUBean mPairBean_ = (PairUBean) v.getTag();
                agreeeGoWithInvite(mPairBean_.getKnp_group_invitation_id());
            }
        });
        String name = CommonUtil.setName(mPairBean.getSponsor_user_name());
        tv_title.setText(name + "邀请您加入\n" + mPairBean.getPerson_number() + "人互祝组队");
    }

    /**
     * 拒绝组队邀请
     *
     * @param knp_group_invitation_id
     */
    public void refuseGoWithInvite(String knp_group_invitation_id) {
        showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.refuseGoWithInvite(
                UserUtils.getUserId(mActivity), knp_group_invitation_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
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
     * 同意组队邀请
     *
     * @param knp_group_invitation_id
     */
    public void agreeeGoWithInvite(String knp_group_invitation_id) {
        showDialog();
        Observable<PairUDataBean> observable = Api.getInstance().service.agreeeGoWithInvite(
                UserUtils.getUserId(mActivity), knp_group_invitation_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PairUDataBean>() {
                    @Override
                    public void accept(PairUDataBean responseBean) throws Exception {
                        dismissDialog();
                        ToastUtils.showToast(responseBean.getMsg());
                        if (responseBean.getStatus().equals("200")) {
                            Intent intent = new Intent(mActivity, CHelpGoWithActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("knp_group_room_id", responseBean.getData().getKnp_group_room_id());
                            mActivity.startActivity(intent);
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
}

