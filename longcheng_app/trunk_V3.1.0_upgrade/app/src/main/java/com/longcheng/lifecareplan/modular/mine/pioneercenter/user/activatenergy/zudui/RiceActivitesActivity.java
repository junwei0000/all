package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.zudui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.exchange.malldetail.activity.MallDetailActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity.PionZFBActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter.RiceAcititesAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionActiviesAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionActiviesDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.RiceActiviesDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.RiceActiviesListDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.jieqibao.activity.PionUserJQBActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.activity.PionRecoverCashNewActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.activity.PionRecoverMatchActivity;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 大米活动
 */
public class RiceActivitesActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.layout_bottom1)
    RelativeLayout layoutBottom1;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.layout_cont)
    LinearLayout layout_cont;
    @BindView(R.id.tv_create)
    TextView tvCreate;
    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.date_listview)
    MyListView date_listview;
    @BindView(R.id.iv_list)
    ImageView iv_list;


    int isOpen;
    int isJoin;//0:未领取。1:已领取
    private int processStatus;//:1未开始 2进行中 3已结束
    String shop_goods_id;

    int startHour;
    int endHour;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_open:
                if (isOpen == 0) {
                    openJieqiActivity();
                }
                break;
            case R.id.tv_create:
                if (processStatus == 2 && isJoin == 0) {
                    if (isOpen == 0) {
                        ToastUtils.showToast("请开启聚宝盆");
                    } else {
                        double nowhour = Double.valueOf(DatesUtils.getInstance().getNowTime("H"));
                        if (nowhour >= startHour && nowhour < endHour) {
                            intent = new Intent(mActivity, MallDetailActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("shop_goods_id", "" + shop_goods_id);
                            startActivity(intent);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pioneer_userzyb_ricectivies;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
    }

    @SuppressLint("NewApi")
    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvCreate.setOnClickListener(this);
        tvOpen.setOnClickListener(this);
        pagetopLayoutLeft.setFocusable(true);
        pagetopLayoutLeft.setFocusableInTouchMode(true);
        pagetopLayoutLeft.requestFocus();
        int wid = DensityUtil.screenWith(mContext) - DensityUtil.dip2px(mContext, 20);
        layoutTop.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (wid * 2.116)));
        layoutBottom1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (wid * 0.737)));
        iv_list.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (wid * 0.33)));
    }

    TimeTaskScroll mTimeTaskScroll;
    int showhei;

    @Override
    public void initDataAfter() {
        showhei = DensityUtil.dip2px(mContext, 360);
        mTimeTaskScroll = new TimeTaskScroll(this, date_listview);
        //设定任务
        new Timer().schedule(mTimeTaskScroll, 0, 50);
    }

    private Handler mHandler = new Handler();

    public class TimeTaskScroll extends TimerTask {
        private MyListView listView;

        public TimeTaskScroll(Context context, MyListView listView) {
            this.listView = listView;
        }

        int scrollPos;
        private Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (listView != null && listView.getChildCount() > 0) {
                    //  控制速度
                    scrollPos = (int) (listView.getScrollY() + 2.0);
                    listView.scrollTo(0, scrollPos);
                    View lastVisibleItemView = listView.getChildAt(listView.getChildCount() - 1);
                    if (scrollPos > lastVisibleItemView.getBottom() - showhei) {
                        Log.d("getwalletSuccess", "##### 滚动到底部 ######");
                        listView.scrollTo(0, 0);
                        mTimeTaskScroll.scrollPos = 0;
                    }
                }
            }
        };

        @Override
        public void run() {
            Message msg = handler.obtainMessage();
            handler.sendMessage(msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimeTaskScroll != null) {
            mTimeTaskScroll.cancel();
            mHandler.removeCallbacks(mTimeTaskScroll);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActivityPageInfo();
        getRiceActivityList();
    }

    public void getActivityPageInfo() {
        showDialog();
        Observable<RiceActiviesDataBean> observable = Api.getInstance().service.getRiceActivityPageInfo(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RiceActiviesDataBean>() {
                    @Override
                    public void accept(RiceActiviesDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            ListSuccess(responseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });

    }

    public void openJieqiActivity() {
        showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.Jieqiactivity(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        dismissDialog();
                        String status = responseBean.getStatus();
                        if (status.equals("200")) {
                            getActivityPageInfo();
                        }
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

    public void getRiceActivityList() {
        showDialog();
        Observable<RiceActiviesListDataBean> observable = Api.getInstance().service.getRiceActivityList(
                UserUtils.getUserId(mContext), 1, 1000, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RiceActiviesListDataBean>() {
                    @Override
                    public void accept(RiceActiviesListDataBean responseBean) throws Exception {
                        dismissDialog();
                        ArrayList<RiceActiviesListDataBean.RiceActiviesItemBean> data = responseBean.getData();
                        if (data != null && data.size() > 0) {
                            RiceAcititesAdapter mRiceAcititesAdapter = new RiceAcititesAdapter(mContext, data);
                            date_listview.setAdapter(mRiceAcititesAdapter);
                            if (data.size() > 10) {

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

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    public void ListSuccess(RiceActiviesDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            RiceActiviesDataBean.RiceActiviesAfterBean data = responseBean.getData();
            RiceActiviesDataBean.RiceActiviesAfterBean.RiceActiviesItemBean activity_info = data.getActivity_info();
            processStatus = activity_info.getProcessStatus();
            shop_goods_id = activity_info.getShop_goods_id();
            isJoin = data.getIsJoin();//0:未领取。1:已领取
            if (processStatus <= 1) {
                tvCreate.setText("活动时间 09:00~21:00");
                tvCreate.setBackgroundColor(getResources().getColor(R.color.gray));
            } else if (processStatus == 2) {
                if (isJoin == 0) {
                    startHour = data.getStartHour();
                    endHour = data.getEndHour();
                    double nowhour = Double.valueOf(DatesUtils.getInstance().getNowTime("H"));
                    if (nowhour >= startHour && nowhour < endHour) {
                        tvCreate.setText("立即换购");
                        tvCreate.setBackgroundColor(getResources().getColor(R.color.yellow_ff7119));
                    } else {
                        tvCreate.setText("活动时间 09:00~21:00");
                        tvCreate.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                } else {
                    tvCreate.setText("您已换购");
                    tvCreate.setBackgroundColor(getResources().getColor(R.color.gray));
                }
            } else if (processStatus == 3) {
                tvCreate.setText("活动已结束");
                tvCreate.setBackgroundColor(getResources().getColor(R.color.gray));
            }
            isOpen = data.getIsOpen();
            if (isOpen == 0) {
                tvOpen.setText("开启聚宝盆");
            } else {
                tvOpen.setText("已开启聚宝盆");
            }
            ArrayList<RiceActiviesDataBean.RiceActiviesAfterBean.RiceActiviesItemBean> tasks = data.getTasks();
            setCont(tasks);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }


    private void setCont(ArrayList<RiceActiviesDataBean.RiceActiviesAfterBean.RiceActiviesItemBean> tasks) {
        layout_cont.removeAllViews();
        if (tasks != null && tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.pion_phoneactivites_item_item, null);
                TextView tv_title = view.findViewById(R.id.tv_title);
                TextView tv_title2 = view.findViewById(R.id.tv_title2);
                tv_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                layout_cont.addView(view);
                tv_title.setText(Html.fromHtml(tasks.get(i).getTitle()));
                int status = tasks.get(i).getStatus();
                if (status == 0) {
                    tv_title2.setText("未开启");
                    tv_title2.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
                    tv_title2.setTag(i);
                    tv_title2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            int index = (int) v.getTag();
                            if (index == 0) {
                                intent = new Intent(mActivity, PionZFBActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                            } else if (index == 1) {
                                intent = new Intent(mActivity, PionRecoverCashNewActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                            } else if (index == 2) {
                                intent = new Intent(mActivity, PionUserJQBActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                            } else if (index == 3) {
                                intent = new Intent(mActivity, PionUserJQBActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    tv_title2.setText("开启");
                    tv_title2.setTextColor(getResources().getColor(R.color.c9));
                }
            }
        }
    }

    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
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
}
