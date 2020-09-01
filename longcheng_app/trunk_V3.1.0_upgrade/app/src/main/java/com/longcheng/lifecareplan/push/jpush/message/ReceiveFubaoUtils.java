package com.longcheng.lifecareplan.push.jpush.message;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.bean.fupackage.ReceiveFuBaoBean;
import com.longcheng.lifecareplan.modular.exchange.jieqiactivities.FuBaoActivitesActivity;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.exchange.jieqiactivities.MyAnimationUtils;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.FuQCenterActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao.FuBaoMenuActivity;
import com.longcheng.lifecareplan.modular.mine.fragment.MineFragment;
import com.longcheng.lifecareplan.push.jpush.message.bean.PairUBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：jun on
 * 时间：2020/3/4 10:53
 * 意图：领取福包
 */
public class ReceiveFubaoUtils {

    private static volatile ReceiveFubaoUtils INSTANCE;

    public static ReceiveFubaoUtils getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (ReceiveFubaoUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ReceiveFubaoUtils();
                }
            }
        }
        return INSTANCE;
    }

    Activity mActivity;
    MyAnimationUtils mMyAnimationUtils;

    public void showDialog() {
        LoadingDialogAnim.show(mActivity);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mActivity);
    }


    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
    }

    int isCer;
    ReceiveFuBaoBean.ReceiveFuBaoAfterBean.ReceiveFuBaoItemBean queue_items;
    MyDialog myFubaoDialog;

    public void getFubaoList() {
        mActivity = ActivityManager.getScreenManager().getCurrentActivity();
        Observable<ReceiveFuBaoBean> observable = Api.getInstance().service.getFubaoNotReceiveList(UserUtils.getUserId(mActivity), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ReceiveFuBaoBean>() {
                    @Override
                    public void accept(ReceiveFuBaoBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            ReceiveFuBaoBean.ReceiveFuBaoAfterBean mPairBean = responseBean.getData();
                            queue_items = mPairBean.getBlessBag();
                            isCer = mPairBean.getIsCer();
                            if (queue_items != null && !TextUtils.isEmpty(queue_items.getBless_bag_id())) {
                                showmyFubaoDialog();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("ResponseBody", "getFubaoNotReceiveList==---------------------------------------" + throwable.getMessage());
                    }
                });
    }

    RelativeLayout layout_nopen;
    RelativeLayout layout_opend;
    LinearLayout layout_cancel;

    private void showopend() {
        layout_nopen.setVisibility(View.GONE);
        layout_opend.setVisibility(View.VISIBLE);
        layout_cancel.setVisibility(View.VISIBLE);

        WindowManager m = mActivity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = myFubaoDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
        myFubaoDialog.getWindow().setAttributes(p); //设置生效
        layout_nopen.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.37)));
        layout_opend.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.313)));

    }


    public void showmyFubaoDialog() {
        if (myFubaoDialog != null && myFubaoDialog.isShowing()) {
            myFubaoDialog.dismiss();
        }
        myFubaoDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_fubao);// 创建Dialog并设置样式主题
        myFubaoDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        Window window = myFubaoDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        myFubaoDialog.show();
        WindowManager m = mActivity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = myFubaoDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth() * 8 / 9; //宽度设置为屏幕
        myFubaoDialog.getWindow().setAttributes(p); //设置生效


        ImageView iv_fubao_zi = myFubaoDialog.findViewById(R.id.iv_fubao_zi);
        TextView tv_receivename = myFubaoDialog.findViewById(R.id.tv_receivename);
        layout_nopen = myFubaoDialog.findViewById(R.id.layout_nopen);
        TextView number_nengliang = myFubaoDialog.findViewById(R.id.number_nengliang);
        layout_opend = myFubaoDialog.findViewById(R.id.layout_opend);
        layout_cancel = myFubaoDialog.findViewById(R.id.layout_cancel);
        layout_cancel.setVisibility(View.GONE);

        layout_nopen.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.37)));
        layout_opend.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.312)));
        String ability = queue_items.getReceive_super_ability();
        ability = PriceUtils.getInstance().seePrice(ability);
        tv_receivename.setText(CommonUtil.setName3(queue_items.getSponsor_user_name()) + "送给您的福包");
        number_nengliang.setText(Html.fromHtml(CommonUtil.getHtmlContent("#ed4401", ability) + " 超级生命能量"));
        layout_cancel.setTag(queue_items);
        layout_nopen.setTag(queue_items);
        if (mMyAnimationUtils == null) {
            mMyAnimationUtils = new MyAnimationUtils();
        }
        mMyAnimationUtils.ScaleAnimationNew(iv_fubao_zi);
        layout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFubaoDialog.dismiss();
            }
        });
        layout_nopen.setTag(queue_items.getBless_bag_id());
        layout_nopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCer == 0) {//未认证
                    ToastUtils.showToast("实名认证后开启福包");
                    Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + MineFragment.Certification_url);
                    mActivity.startActivity(intent);
                } else {
                    String bless_bag_id = (String) v.getTag();
                    agreeePairsU(bless_bag_id);
                }
            }
        });
        layout_opend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFubaoDialog.dismiss();/**/
                Intent intent = new Intent(mActivity, FuBaoActivitesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mActivity.startActivity(intent);
            }
        });
    }


    /**
     * 开福包
     *
     * @param bless_bag_id
     */
    public void agreeePairsU(String bless_bag_id) {
        showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.receiveBag(
                UserUtils.getUserId(mActivity), bless_bag_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        dismissDialog();
                        if (responseBean.getStatus().equals("200")) {
                            showopend();
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
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

