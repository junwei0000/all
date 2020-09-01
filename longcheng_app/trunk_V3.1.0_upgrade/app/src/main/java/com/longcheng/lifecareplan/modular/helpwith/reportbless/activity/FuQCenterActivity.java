package com.longcheng.lifecareplan.modular.helpwith.reportbless.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.HuoDongBean;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.bottommenu.activity.BottomMenuActivity;
import com.longcheng.lifecareplan.modular.exchange.jieqiactivities.FuBaoActivitesActivity;
import com.longcheng.lifecareplan.modular.helpwith.connon.activity.CHelpListActivity;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.fragment.HelpWithFragmentNew;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.FQCenterDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle.MyCircleActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao.FuBaoMenuActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.HDiloagUtils;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.IntoBagFuActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.ReceiveSuccessActivity;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyHorizontalScrollView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 福券中心
 */
public class FuQCenterActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.tv_report)
    TextView tvReport;
    @BindView(R.id.tv_qifunum)
    TextView tvQifunum;
    @BindView(R.id.layout_qifunum)
    LinearLayout layoutQifunum;
    @BindView(R.id.tv_fulinum)
    TextView tvFulinum;
    @BindView(R.id.layout_fulinum)
    LinearLayout layoutFulinum;
    @BindView(R.id.tv_jieqi)
    TextView tvJieqi;
    @BindView(R.id.tv_guize)
    TextView tvGuize;
    @BindView(R.id.horizontal_layout)
    LinearLayout horizontalLayout;
    @BindView(R.id.iv_sign)
    TextView ivSign;
    @BindView(R.id.tv_wuaihelp)
    TextView tvWuaihelp;
    @BindView(R.id.tv_wuzaihelp)
    TextView tvWuzaihelp;
    @BindView(R.id.tv_engrynum)
    TextView tvEngrynum;
    @BindView(R.id.layout_zhishu)
    LinearLayout layoutZhishu;
    @BindView(R.id.layout_top)
    LinearLayout layout_top;
    @BindView(R.id.horizontal)
    MyHorizontalScrollView horizontal;
    @BindView(R.id.layout_myfuquan)
    LinearLayout layout_myfuquan;
    @BindView(R.id.flower_image)
    ImageView flowerImage;
    @BindView(R.id.layout_flower)
    LinearLayout layoutFlower;
    @BindView(R.id.layout_acupoint)
    LinearLayout layout_acupoint;
    @BindView(R.id.layout_fubao)
    LinearLayout layout_fubao;


    int helpType;
    String bless_id;
    boolean isCurrentSign;
    Animation animation;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_report:
                intent = new Intent(mActivity, ReportMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.layout_qifunum:
                intent = new Intent(mActivity, FuQListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("type", 1);
                intent.putExtra("keyword", "请祈福");
                startActivity(intent);
                break;
            case R.id.layout_fulinum:
                intent = new Intent(mActivity, FuQListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("type", 2);
                intent.putExtra("keyword", "领福利");
                startActivity(intent);
                break;
            case R.id.layout_zhishu:
                if (!TextUtils.isEmpty(bless_id)) {
                    intent = new Intent(mActivity, LoveResultActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("bless_id", bless_id);
                    startActivity(intent);
                }
                break;
            case R.id.tv_wuaihelp:
                if (helpType == 1) {
                    intent = new Intent(mActivity, CHelpListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
                break;
            case R.id.tv_wuzaihelp:
                if (helpType == 2) {
                    intent = new Intent(mActivity, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", Config.BASE_HEAD_URL + "home/knpteam/allroomlist/");
                    startActivity(intent);
                }
                break;
            case R.id.iv_sign:
                if (!isCurrentSign) {
                    fuquansign();
                }
                break;
            case R.id.tv_guize:
                showGuiZeDialog();
                break;
            case R.id.layout_myfuquan:
                intent = new Intent(mActivity, MyCircleActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.layout_fubao://福包
                intent = new Intent(mActivity, FuBaoActivitesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mActivity.startActivity(intent);
//                intent = new Intent(mActivity, IntoBagFuActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                mActivity.startActivity(intent);
                break;
            case R.id.layout_acupoint://穴位
                ToastUtils.showToast("功能开发中");
                break;
        }
    }

    public static void skipIntent(Activity m) {
        Intent intent = new Intent(m, FuBaoMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        m.startActivity(intent);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fuquan_center;
    }


    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
    }


    @Override
    public void setListener() {
        tvGuize.setOnClickListener(this);
        ivSign.setOnClickListener(this);
        tvWuaihelp.setOnClickListener(this);
        tvWuzaihelp.setOnClickListener(this);
        tvReport.setOnClickListener(this);
        layoutQifunum.setOnClickListener(this);
        layoutFulinum.setOnClickListener(this);
        layoutZhishu.setOnClickListener(this);
        layout_myfuquan.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        layout_acupoint.setOnClickListener(this);
        layout_fubao.setOnClickListener(this);
        int hei = (int) (DensityUtil.screenWith(mContext) * 0.747);
        layout_top.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hei));
    }

    @Override
    public void initDataAfter() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getInfo();
    }

    public void getInfo() {
        Observable<FQCenterDataBean> observable = Api.getInstance().service.getFuQCenterInfo(UserUtils.getUserId(mActivity), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FQCenterDataBean>() {
                    @Override
                    public void accept(FQCenterDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            FQCenterDataBean.CenterItemBean mPairBean = responseBean.getData();
                            String jieqi_pig = mPairBean.getJieqi_pig();
                            setDataView(mPairBean.getSignDetaileds(), jieqi_pig);
                            String noUserCount = mPairBean.getNoUserCount();
                            String ingCount = mPairBean.getIngCount();
                            isCurrentSign = mPairBean.isCurrentSign();
                            bless_id = mPairBean.getBless_id();
                            String complete_money = mPairBean.getComplete_money();
                            helpType = mPairBean.getHelpType();//1 天下无癌  2 天下无债
                            String wuhouName = mPairBean.getWuhouName();
                            tvJieqi.setText(wuhouName);
                            tvQifunum.setText(noUserCount);
                            tvFulinum.setText(ingCount);
                            tvEngrynum.setText(complete_money);
                            if (helpType == 1) {
                                tvWuaihelp.setBackgroundResource(R.mipmap.fuquan_zai_help);
                                tvWuzaihelp.setBackgroundResource(R.mipmap.fuquan_zai_next);
                                tvWuaihelp.setText("去祝福");
                                tvWuaihelp.setTextColor(getResources().getColor(R.color.white));
                                tvWuzaihelp.setText("明天来");
                                tvWuzaihelp.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
                            } else {
                                tvWuaihelp.setBackgroundResource(R.mipmap.fuquan_zai_next);
                                tvWuzaihelp.setBackgroundResource(R.mipmap.fuquan_zai_help);
                                tvWuaihelp.setText("明天来");
                                tvWuaihelp.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
                                tvWuzaihelp.setText("去祝福");
                                tvWuzaihelp.setTextColor(getResources().getColor(R.color.white));
                            }
                            if (isCurrentSign) {
                                ivSign.setBackgroundResource(R.mipmap.sign);
                                ivSign.setText("花神到");
                            } else {
                                ivSign.setBackgroundResource(R.mipmap.signed);
                                ivSign.setText("迎花神");
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    int todayindex = 0;

    private void setDataView(ArrayList<FQCenterDataBean.CenterItemBean> signDetaileds, String jieqi_pig) {
        if (signDetaileds != null && signDetaileds.size() > 0) {
            horizontal.smoothScrollTo(0, 0);
            horizontalLayout.removeAllViews();
            for (int i = 0; i < signDetaileds.size(); i++) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.fuquan_center_signitem, null);
                RelativeLayout relat_bg = view.findViewById(R.id.relat_bg);
                ImageView iv_avatar = view.findViewById(R.id.iv_avatar);
                LinearLayout layout_num = view.findViewById(R.id.layout_num);
                TextView tv_number = view.findViewById(R.id.tv_number);
                TextView tv_date = view.findViewById(R.id.tv_date);

                FQCenterDataBean.CenterItemBean mBean = signDetaileds.get(i);
                String date = mBean.getDate();
                String today = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
                int todaytamp = DatesUtils.getInstance().getDateToTimeStamp(today, "yyyy-MM-dd");
                int datetamp = DatesUtils.getInstance().getDateToTimeStamp(date, "yyyy-MM-dd");
                //_____________________________________________________________
                int is_sign = mBean.getIs_sign();//是否已签到
                int number = mBean.getNumber();
                if (i == 0) {//节气开始从1张开始
                    mBean.setNumber(1);
                } else {
                    if (is_sign == 1) {
                        mBean.setNumber(number);//签到设置原本的个数
                    } else {
                        int beforeissign = signDetaileds.get(i - 1).getIs_sign();
                        int beforenum = signDetaileds.get(i - 1).getNumber();
                        if (datetamp == todaytamp) {//今天未签到
                            if (beforeissign == 1) {//前天已签到，今天 beforenum+1
                                mBean.setNumber(beforenum + 1);
                            } else {//前天已签到，今天从1开始
                                mBean.setNumber(1);
                            }
                        } else {//非当天未签到 beforenum+1
                            mBean.setNumber(beforenum + 1);
                        }

                    }
                }
                number = mBean.getNumber();
                tv_number.setText(number + "张");
                //_____________________________________________________________
                GlideDownLoadImage.getInstance().loadCircleImage(jieqi_pig, iv_avatar);
                tv_date.setText(DatesUtils.getInstance().getDateGeShi(date, "yyyy-MM-dd", "M.dd"));
                tv_date.setTextColor(getResources().getColor(R.color.color_333333));
                relat_bg.setBackgroundResource(R.drawable.corners_oval_sing_yellowtran);
                iv_avatar.setVisibility(View.GONE);
                layout_num.setVisibility(View.VISIBLE);
                if (is_sign == 1) {
                    iv_avatar.setVisibility(View.VISIBLE);
                    layout_num.setVisibility(View.GONE);
                    relat_bg.setBackgroundResource(R.drawable.corners_oval_sing_yellow);
                    tv_date.setText("花神到");
                    tv_date.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
                } else {
                    if (datetamp == todaytamp) {
                        tv_date.setText("今天");
                        tv_date.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
                    } else if (datetamp < todaytamp) {
                        tv_date.setText("未迎花神");
                        relat_bg.setBackgroundResource(R.drawable.corners_oval_sing_gray);
                    }
                }
                horizontalLayout.addView(view);
                if (datetamp == todaytamp) {
                    todayindex = i;
                }
            }
            horizontalLayout.post(new Runnable() {
                @Override
                public void run() {
                    int x = DensityUtil.dip2px(mContext, 60) * todayindex;
                    horizontal.smoothScrollTo(x, 0);
                }
            });
        }

    }

    public void fuquansign() {
        Observable<ResponseBean> observable = Api.getInstance().service.FuQCenterSign(UserUtils.getUserId(mActivity), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            getInfo();
                            animStartImage();
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }


    private void animStartImage() {

//        LogUtils.v("TAG","节气"+HomeFragment.current_jieqi);
        Bitmap bitmap = DensityUtil.getAssetsNew(mActivity, "fuquan/" + HomeFragment.current_jieqi + ".png");
        BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
        layoutFlower.setVisibility(View.VISIBLE);
        flowerImage.setBackground(bd);
        animation = AnimationUtils.loadAnimation(this, R.anim.flower_open);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                layoutFlower.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        flowerImage.startAnimation(animation);

    }


    MyDialog mXuFeiDialog;

    public void showGuiZeDialog() {
        if (mXuFeiDialog == null) {
            mXuFeiDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_fuquancenter_guize);// 创建Dialog并设置样式主题
            mXuFeiDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mXuFeiDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mXuFeiDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mXuFeiDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            mXuFeiDialog.getWindow().setAttributes(p); //设置生效
            ImageView iv_cancel = mXuFeiDialog.findViewById(R.id.iv_cancel);
            iv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mXuFeiDialog.dismiss();
                }
            });
        } else {
            mXuFeiDialog.show();
        }
    }

    MyDialog mJianjieDialog;


    /***
     *  简介涂层弹框
     */
    public void showJianjieDialog() {
        if (mJianjieDialog == null) {
            mJianjieDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_fuquancenter_jianjie);// 创建Dialog并设置样式主题
            mJianjieDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mJianjieDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mJianjieDialog.show();
            WindowManager m = getWindowManager();
            int w = DensityUtil.screenWith(mContext);
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mJianjieDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = w * 4 / 5; //宽度设置为屏幕
//            p.height = (int) ((w * 4 / 5)*1.5);
            mJianjieDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout jianjie_layout = mJianjieDialog.findViewById(R.id.jianjie_layout);
            jianjie_layout.setLayoutParams(new FrameLayout.LayoutParams(w * 4 / 5, (int) ((w * 4 / 5) * 1.5)));
            ImageView iv_cancel = mJianjieDialog.findViewById(R.id.iv_cancel);
            iv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mJianjieDialog.dismiss();
                }
            });
        } else {
            mJianjieDialog.show();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
    }
}
