package com.longcheng.lifecareplan.modular.exchange.jieqiactivities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.bean.fupackage.FuBaoRankBean;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.exchange.bean.FuBaoDetailsBeans;
import com.longcheng.lifecareplan.modular.exchange.bean.FuBaoItems;
import com.longcheng.lifecareplan.modular.exchange.jieqiactivities.adpter.FubaorankAcititesAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.FuQCenterActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.ReceiveSuccessActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressListActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity.PionZFBActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter.RiceAcititesAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.RiceActiviesListDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.jieqibao.activity.PionUserJQBActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.activity.PionRecoverCashNewActivity;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 处暑-福包包活动
 */
public class FuBaoActivitesActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.layout_cont)
    LinearLayout layout_cont;
    @BindView(R.id.tv_create)
    LinearLayout tvCreate;
    @BindView(R.id.guize_layout)
    LinearLayout guizeLayout;
    @BindView(R.id.tv_open)
    TextView tvOpen;

    @BindView(R.id.ing_show_icon)
    ImageView ingShowIcon;
    @BindView(R.id.get_btn)
    ImageView getBtn;

    @BindView(R.id.song)
    TextView song;
    @BindView(R.id.tv_fu_num)
    TextView tvFuNum;
    @BindView(R.id.tv_fu_num_hinit)
    TextView tvFuNumHinit;
    @BindView(R.id.btn_img)
    ImageView btnImg;
    @BindView(R.id.one_layout)
    LinearLayout oneLayout;
    @BindView(R.id.layout_new_user)
    RelativeLayout layoutNewUser;
    @BindView(R.id.iv_send)
    ImageView iv_send;
    @BindView(R.id.iv_receive)
    ImageView iv_receive;
    @BindView(R.id.date_listview)
    MyListView date_listview;
    @BindView(R.id.tv_count)
    TextView tv_count;


    //    private int processStatus;//:1未开始 2进行中 3已结束
    String shop_goods_id;
    int isOpen;
    int isJoin;//0:未领取。1:已领取
    int startHour;
    int endHour;
    private int select_type = 0; //默认已抢购
    int is_new = 0;//1 是新用户
    LayoutInflater inflater = null;
    private int is_activity_end = 0; //活动是否已结束
    int bless_count = 0; // 已送出多少个福包
    int isadress = 0; // 是否有地址
    String adress_str = "ImageView 设置图片缩放有两种方法_rongbinjava的博客-CSDN博客";
    FuBaoItems.AddressBean addressBean = null;
    int is_stock_over = 0; //  1可领取， 0没有货

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
            case R.id.btn_img:
                //去送红包
                if (is_new == 0 && isOpen == 0) {
                    ToastUtils.showToast("请开启聚宝盆");
                    break;
                }
                FuQCenterActivity.skipIntent(mActivity);
                break;

            case R.id.tv_create:
                if (is_activity_end <= 1) {
                    ToastUtils.showToast("活动未开始");
                } else if (is_activity_end == 2) {
                    if (bless_count != 5) {
//                        ToastUtils.showToast("您的福包能量还不够哦！");
                        break;
                    }
                    if (is_new == 1) {
                        // 新用户
                    } else {
                        // 老用户
                        if (isOpen == 0) {
                            ToastUtils.showToast("请开启聚宝盆");
                            break;
                        }
                    }
                    if (isadress == 0) {
                        // 显示添加地址
                        showAdressDialog();
                    } else {
                        // 显示默认地址
                        showAddAdressDialog();
                    }

                } else if (is_activity_end == 3) {
                    ToastUtils.showToast("活动已结束");
                }
                break;
            case R.id.iv_send:
                type = 1;
                iv_send.setBackgroundResource(R.mipmap.fubao_chanpin_send_select);
                iv_receive.setBackgroundResource(R.mipmap.fubao_chanpin_receive_notselect);
                getRankActivityList();
                break;
            case R.id.iv_receive:
                type = 2;
                iv_send.setBackgroundResource(R.mipmap.fubao_chanpin_send_notselect);
                iv_receive.setBackgroundResource(R.mipmap.fubao_chanpin_receive_select);
                getRankActivityList();
                break;

        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pioneer_userzyb_fubao;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressLint("NewApi")
    @Override
    public void setListener() {
        iv_send.setOnClickListener(this);
        iv_receive.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        tvOpen.setOnClickListener(this);
        tvCreate.setOnClickListener(this);
        pagetopLayoutLeft.setFocusable(true);
        pagetopLayoutLeft.setFocusableInTouchMode(true);
        pagetopLayoutLeft.requestFocus();
        btnImg.setOnClickListener(this);
        int wid = DensityUtil.screenWith(mContext);
        layoutTop.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (wid * 1.463)));
        int widimg = DensityUtil.screenWith(mContext) - DensityUtil.dip2px(mContext, 60);
        ingShowIcon.setLayoutParams(new LinearLayout.LayoutParams(widimg / 2, (int) (widimg / 2 * 1.12)));
    }

    TimeTaskScroll mTimeTaskScroll;
    int showhei;

    @Override
    public void initDataAfter() {
        showhei = DensityUtil.dip2px(mContext, 360);
        mTimeTaskScroll = new TimeTaskScroll(this, date_listview);
        //设定任务
        new Timer().schedule(mTimeTaskScroll, 0, 50);
        getGuiZe();
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
        getRankActivityList();
    }

    public void getActivityPageInfo() {
        showDialog();
        Observable<FuBaoDetailsBeans> observable = Api.getInstance().service.getHDFubaoLiveAction(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FuBaoDetailsBeans>() {
                    @Override
                    public void accept(FuBaoDetailsBeans responseBean) throws Exception {
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
        Observable<ResponseBean> observable = Api.getInstance().service.openFubaoJuBaopen(UserUtils.getUserId(mContext), ExampleApplication.token);
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

    public void getFuliActivity(String adress_id) {
        showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.getFubaoFuli(UserUtils.getUserId(mContext), adress_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        dismissDialog();
                        String status = responseBean.getStatus();
                        if (status.equals("200")) {
//                            getActivityPageInfo();
                            String dataurl = Config.BASE_HEAD_URL + "home/app/index";
                            Intent intent = new Intent(mActivity, ReceiveSuccessActivity.class);
                            intent.putExtra("pagetype", 2);
                            intent.putExtra("dataurl", dataurl);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            mActivity.startActivity(intent);
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

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    public void ListSuccess(FuBaoDetailsBeans responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            FuBaoItems data = responseBean.getData();

            is_activity_end = data.getIs_activity_end();
            isOpen = data.getIsOpen();
            if (isOpen == 0) {
                tvOpen.setBackgroundResource(R.mipmap.open_jubaopen_btn);
            } else {
                /// 已开启
                tvOpen.setBackgroundResource(R.mipmap.openend_jubaopen_btn);
            }
            bless_count = data.getBless_bag_count();
            int total_bless_count = data.getBless_bag_total_count();

            tvFuNum.setText("" + total_bless_count + "个");

            tvFuNumHinit.setText(new StringBuffer().append("已送出：").append(bless_count).append("/").append(total_bless_count).toString());
            // 新用户不展示聚宝盆展示页面
            is_new = data.getIs_new_user();
            if (is_new == 1) {
                layoutNewUser.setVisibility(View.GONE);
            } else {
                layoutNewUser.setVisibility(View.VISIBLE);
            }

            addressBean = data.getAddress();

            if (addressBean != null && !TextUtils.isEmpty(addressBean.getAddress())) {
                isadress = 1;
                adress_str = addressBean.getAddress();
            } else {
                isadress = 0;
                adress_str = "";
            }
            is_stock_over = data.getIs_stock_over();

            if (bless_count == 5) {
                getBtn.setBackgroundResource(R.mipmap.btn_li_get);
            } else {
                getBtn.setBackgroundResource(R.mipmap.btn_li_get_end);
            }
            if (is_stock_over == 0) {
                tvCreate.setEnabled(false);
                getBtn.setBackgroundResource(R.mipmap.btn_li_cant_click);
            } else {
                tvCreate.setEnabled(true);
            }
            // 开启聚宝盆
            List<FuBaoItems.TasksBean> tasks = data.getTasks();
            setCont(tasks);
            // 规则展示
            List<String> guizelist = data.getRule();
            if (guizelist != null && !guizelist.isEmpty()) {
                guizeLayout.removeAllViews();
                for (int i = 0; i < guizelist.size(); i++) {
                    guizeLayout.addView(addGuize(guizelist.get(i)));
                }
            }


        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    private void setCont(List<FuBaoItems.TasksBean> tasks) {
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

    private void getGuiZe() {
        String[] liststr = mActivity.getResources().getStringArray(R.array.guize_list);
        if (liststr != null && liststr.length > 0) {

            for (int i = 0; i < liststr.length; i++) {
                guizeLayout.addView(addGuize(liststr[i]));
            }
        }

    }

    TextView guize_content = null;

    private LinearLayout addGuize(String items) {
        LinearLayout content_layout = null;
        if (items != null && !items.equals("")) {
            content_layout = (LinearLayout) inflater.inflate(R.layout.hdfb_item_guize, null);
            guize_content = content_layout.findViewById(R.id.item_content);
            guize_content.setText(items);
        }
        return content_layout;
    }

    int type = 1;//1 已送出  2 已领取

    public void getRankActivityList() {
        showDialog();
        Observable<FuBaoRankBean> observable = Api.getInstance().service.getFuBaoRankActivityList(
                UserUtils.getUserId(mContext), type, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FuBaoRankBean>() {
                    @Override
                    public void accept(FuBaoRankBean responseBean) throws Exception {
                        dismissDialog();
                        FuBaoRankBean.FuBaoRankAfterBean data = responseBean.getData();
                        tv_count.setText("共" + data.getTotal_num() + "份大米， 已成功领取" + data.getNum() + "份");
                        ArrayList<FuBaoRankBean.FuBaoRankAfterBean.FuBaoRankItemBean> list = data.getList();
                        if (list != null && list.size() > 0) {
                        } else {
                            list = new ArrayList<>();
                        }
                        FubaorankAcititesAdapter mRiceAcititesAdapter = new FubaorankAcititesAdapter(mContext, list, type);
                        date_listview.setAdapter(mRiceAcititesAdapter);
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
     * 聚宝盆提示对话框  1、未开启提示 2、开启赋能提示 3、某宝不足提示
     */

    private MyDialog adressDialog;

    private void showAdressDialog() {
        if (adressDialog == null) {
            adressDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_jbpno_add);// 创建Dialog并设置样式主题
            adressDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = adressDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            adressDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = adressDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5;
            adressDialog.getWindow().setAttributes(p); //设置生效

            TextView tv_sure = adressDialog.findViewById(R.id.tv_sure);
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adressDialog.dismiss();/**/
                    // 去添加地吱
                    Intent intent = new Intent(mContext, AddressListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("skiptype", "FuBaoActivitesActivity");
                    intent.putExtra("receive_user_id", UserUtils.getUserId(mContext));
                    startActivity(intent);
                }
            });

        } else {
            adressDialog.show();
        }
    }

    private MyDialog addAdressDialog;
    private TextView addresshinit;

    private void showAddAdressDialog() {
        if (addAdressDialog == null) {
            addAdressDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_jbpno_adress);// 创建Dialog并设置样式主题
            addAdressDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = addAdressDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            addAdressDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = addAdressDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5;
            addAdressDialog.getWindow().setAttributes(p); //设置生效
            addresshinit = addAdressDialog.findViewById(R.id.tv_hinit);
            TextView tv_sure = addAdressDialog.findViewById(R.id.tv_sure);
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addAdressDialog.dismiss();/**/
                    // 去领取奖励
                    if (addressBean != null) {
                        getFuliActivity(addressBean.getAddress_id());
                    }

                }
            });
        } else {
            addAdressDialog.show();
        }
        if (addressBean != null) {
            addresshinit.setText(addressBean.getAddress());
        }
    }
}
