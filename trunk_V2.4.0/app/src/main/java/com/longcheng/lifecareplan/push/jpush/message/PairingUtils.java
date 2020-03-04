package com.longcheng.lifecareplan.push.jpush.message;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
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

    MyDialog selectDialog;
    private ImageView iv_head;
    private TextView tv_name;
    private TextView tv_jieeqi;
    private LinearLayout layout_shenfen;
    private LinearLayout layout_phone;
    private TextView tv_phone;
    private TextView tv_cancel;
    private TextView tv_sure;
    Activity mActivity;
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

    public void showPopupWindow(PairBean mPairBean) {
        if (selectDialog != null && selectDialog.isShowing()) {
            return;
        }
        if (selectDialog == null) {
            selectDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_pairing);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
            p.height = (int) (p.width * 1.56);
            selectDialog.getWindow().setAttributes(p); //设置生效

            iv_head = (ImageView) selectDialog.findViewById(R.id.iv_head);
            tv_name = (TextView) selectDialog.findViewById(R.id.tv_name);
            tv_jieeqi = (TextView) selectDialog.findViewById(R.id.tv_jieeqi);
            tv_jieeqi.getBackground().setAlpha(92);
            layout_shenfen = (LinearLayout) selectDialog.findViewById(R.id.layout_shenfen);
            layout_phone = (LinearLayout) selectDialog.findViewById(R.id.layout_phone);
            tv_phone = (TextView) selectDialog.findViewById(R.id.tv_phone);

            tv_cancel = (TextView) selectDialog.findViewById(R.id.tv_cancel);
            tv_sure = (TextView) selectDialog.findViewById(R.id.tv_sure);
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
        } else {
            selectDialog.show();
        }
        tv_cancel.setTag(mPairBean);
        tv_sure.setTag(mPairBean);
        layout_phone.setTag(mPairBean);
        GlideDownLoadImage.getInstance().loadCircleImage(mPairBean.getSponsor_user_avatar(), iv_head);
        String name = mPairBean.getSponsor_user_name();
        if (!TextUtils.isEmpty(name) && name.length() > 4) {
            name = name.substring(0, 3) + "…";
        }
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
        mActivity = ActivityManager.getScreenManager().getCurrentActivity();
        showDialog();
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
}
