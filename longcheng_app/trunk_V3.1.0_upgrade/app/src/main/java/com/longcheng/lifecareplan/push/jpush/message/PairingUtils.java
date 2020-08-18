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
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.network.LocationUtils;
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
        mActivity = ActivityManager.getScreenManager().getCurrentActivity();
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

    LocationUtils mLocationUtils;

    /**
     * 帮助他
     */
    public void help(int help_need_order_id) {
        if (mLocationUtils == null) {
            mLocationUtils = new LocationUtils();
        }
        double[] mLngAndLat = mLocationUtils.getLngAndLatWithNetwork(mActivity);
        double phone_user_latitude = mLngAndLat[0];
        double phone_user_longitude = mLngAndLat[1];
        Observable<ResponseBean> observable = Api.getInstance().service.receive(
                UserUtils.getUserId(mActivity), help_need_order_id, phone_user_latitude, phone_user_longitude, ExampleApplication.token);
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

