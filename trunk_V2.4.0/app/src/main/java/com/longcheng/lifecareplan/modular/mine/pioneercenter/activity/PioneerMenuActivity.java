package com.longcheng.lifecareplan.modular.mine.pioneercenter.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.mine.bill.activity.PionCaiLiBillActivity;
import com.longcheng.lifecareplan.modular.mine.bill.activity.PionFQBBillActivity;
import com.longcheng.lifecareplan.modular.mine.bill.activity.PionZYBBillActivity;
import com.longcheng.lifecareplan.modular.mine.fragment.MineFragment;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.adapter.MenuTopAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerCounterDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataListBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.UserBankDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetRecordDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity.dc.PionDaiCActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.activity.df.PionDaiFuActivity;
import com.longcheng.lifecareplan.push.jpush.message.PionPairingUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 24节气创业中心
 */
public class PioneerMenuActivity extends BaseActivityMVP<PioneerContract.View, PioneerPresenterImp<PioneerContract.View>> implements PioneerContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    //    @BindView(R.id.iv_head)
//    ImageView ivHead;
//    @BindView(R.id.tv_name)
//    TextView tvName;
//    @BindView(R.id.tv_jieeqi)
//    TextView tvJieeqi;
//    @BindView(R.id.layout_shenfen)
//    LinearLayout layoutShenfen;
//    @BindView(R.id.tv_time)
//    TextView tvTime;
//    @BindView(R.id.iv_xufei)
//    ImageView ivXufei;
//@BindView(R.id.iv_business)
//ImageView iv_business;
    @BindView(R.id.tv_zfbnum)
    TextView tvZfbnum;
    @BindView(R.id.layout_zfb)
    LinearLayout layoutZfb;
    @BindView(R.id.tv_cailinum)
    TextView tvCailinum;
    @BindView(R.id.layout_caili)
    LinearLayout layoutCaili;
    @BindView(R.id.tv_fqbnum)
    TextView tvFqbnum;
    @BindView(R.id.layout_fqb)
    LinearLayout layoutFqb;
    @BindView(R.id.ll_recharge)
    RelativeLayout ll_recharge;
    @BindView(R.id.ll_help_pay)
    RelativeLayout ll_help_pay;
    @BindView(R.id.iv_rechargeimg)
    ImageView ivRechargeimg;
    @BindView(R.id.iv_help_payimg)
    ImageView ivHelpPayimg;
    @BindView(R.id.pageTop_tv_apply)
    TextView pageTop_tv_apply;
    @BindView(R.id.ll_caili)
    RelativeLayout ll_caili;
    @BindView(R.id.iv_cailiimg)
    ImageView iv_cailiimg;
    @BindView(R.id.iv_dian)
    ImageView iv_dian;
    @BindView(R.id.vp_top)
    ViewPager vp_top;
    @BindView(R.id.ll_dot)
    LinearLayout ll_dot;


    public static PioneerMenuActivity menuActivity;
    int isApplyEntrepreneursMoney = 0;//0没有   1未申请 2 累加未申请
    int receive_order_status;
    int isWxCardBag;//1 已绑卡
    int isAliCardBag;
    int diffTime = 1;
    String displayDiffTime = "";

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.pageTop_tv_apply:
                intent = new Intent(mActivity, PioneerApplyGoldActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.layout_zfb:
                intent = new Intent(mActivity, PionZYBBillActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.layout_caili:
                intent = new Intent(mActivity, PionCaiLiBillActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.layout_fqb:
                intent = new Intent(mActivity, PionFQBBillActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.ll_recharge:
                intent = new Intent(mActivity, PionDaiCActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.ll_help_pay:
                intent = new Intent(mActivity, PionDaiFuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.ll_caili:
                intent = new Intent(mActivity, PioneerCailiWithdrawActivity.class);
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
        return R.layout.pioneer_menu;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBarDark(mActivity, toolbar);
    }

    @Override
    public void setListener() {
        menuActivity = this;
        pageTopTvName.setText("24节气创业中心");
        pageTop_tv_apply.setOnClickListener(this);
        ll_help_pay.setOnClickListener(this);
        ll_recharge.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        layoutZfb.setOnClickListener(this);
        layoutCaili.setOnClickListener(this);
        layoutFqb.setOnClickListener(this);
        ll_caili.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
        mTimeTaskScroll = new TimeTaskScroll();
        //设定任务
        new Timer().schedule(mTimeTaskScroll, 0, 4000);
    }


    @Override
    protected PioneerPresenterImp<PioneerContract.View> createPresent() {
        return new PioneerPresenterImp<>(mActivity, this);
    }


    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void MenuInfoSuccess(PioneerDataBean responseBean) {
        diffTime = 1;
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            PioneerAfterBean data = responseBean.getData();
            showViewInfo(data);
            showApplyEntrepreneurs(data);
            isWxCardBag = data.getIsWxCardBag();
            isAliCardBag = data.getIsAliCardBag();
            //是否显示续费
            if (diffTime <= 1) {
                showXuFeiDialog();
            } else {
                //福祺宝额度不足弹层
                int isNewentreFuqibaoLess = data.getIsNewentreFuqibaoLess();
                boolean isNewentreFuqibaoLess_firstshow = (boolean) SharedPreferencesHelper.get(mContext, "isNewentreFuqibaoLess_firstshow", false);
                if (!isNewentreFuqibaoLess_firstshow && isNewentreFuqibaoLess == 1) {
                    showGQBDialog();
                } else {
                    mPresent.getCounterpoiseInfo();
                }
            }
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    /**
     * 显示页面信息
     *
     * @param data
     */
    private void showViewInfo(PioneerAfterBean data) {
        ArrayList<String> solarTermsEnsImg = data.getSolarTermsEnsImg();
        if (solarTermsEnsImg != null && solarTermsEnsImg.size() >= 3) {
            int width = (int) (DensityUtil.screenWith(mActivity) * 2.5 / 7);
            int imghei = (int) (width * 0.6);
            ivRechargeimg.setLayoutParams(new LinearLayout.LayoutParams(width, imghei));
            GlideDownLoadImage.getInstance().loadCircleImageHelpIndex(mActivity, solarTermsEnsImg.get(0), ivRechargeimg);
            ivHelpPayimg.setLayoutParams(new LinearLayout.LayoutParams(width, imghei));
            GlideDownLoadImage.getInstance().loadCircleImageHelpIndex(mActivity, solarTermsEnsImg.get(1), ivHelpPayimg);
            iv_cailiimg.setLayoutParams(new LinearLayout.LayoutParams(width, imghei));
            GlideDownLoadImage.getInstance().loadCircleImageHelpIndex(mActivity, solarTermsEnsImg.get(2), iv_cailiimg);
        }
        PioneerItemBean entrepreneurs = data.getEntrepreneurs();
        if (entrepreneurs != null) {
            tvZfbnum.setText(entrepreneurs.getZhufubao());
            tvCailinum.setText(entrepreneurs.getRebate());
            tvFqbnum.setText(entrepreneurs.getFuqibao());
            diffTime = entrepreneurs.getDiffTime();
            displayDiffTime = entrepreneurs.getDisplayDiffTime();
            receive_order_status = entrepreneurs.getReceive_order_status();
        }
        PioneerItemBean user = data.getUser();
        shoeZZJieQi(user, receive_order_status);
    }

    int index = 0;
    private List<ImageView> dotList = new ArrayList<>();

    private void shoeZZJieQi(PioneerItemBean user, int receive_order_status) {
        if (vp_top == null) {
            return;
        }
        index = 0;
        MenuTopAdapter adapter = new MenuTopAdapter(mActivity, mHandler, user, receive_order_status, displayDiffTime);
        vp_top.setAdapter(adapter);
        vp_top.setCurrentItem(index, false);
        vp_top.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position;
                for (int i = 0; i < 2; i++) {
                    ImageView img = dotList.get(i);
                    if (i == index % 2) {
                        img.setImageResource(R.drawable.corners_oval_blue);
                    } else {
                        img.setImageResource(R.drawable.corners_oval_write);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ll_dot.removeAllViews();
        dotList.clear();
        for (int i = 0; i < 2; i++) {
            ImageView img = new ImageView(mContext); // 现在空
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            params.setMargins(10, 0, 10, 0);
            img.setLayoutParams(params);
            if (i == index % 2) {
                img.setImageResource(R.drawable.corners_oval_blue);
            } else {
                img.setImageResource(R.drawable.corners_oval_write);
            }
            dotList.add(img);
            ll_dot.addView(img);
        }
    }

    TimeTaskScroll mTimeTaskScroll;

    boolean sartrun = true;

    @Override
    protected void onPause() {
        super.onPause();
        sartrun = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuActivity = null;
        if (mTimeTaskScroll != null) {
            mTimeTaskScroll.cancel();
            mHandler.removeCallbacks(mTimeTaskScroll);
        }
    }

    public class TimeTaskScroll extends TimerTask {

        private Handler mtimeHandler = new Handler() {
            public void handleMessage(Message msg) {
                //  控制速度
                if (sartrun && vp_top != null && vp_top.getAdapter() != null) {
                    index++;
                    vp_top.setCurrentItem(index);
                }

            }
        };

        @Override
        public void run() {
            Message msg = mtimeHandler.obtainMessage();
            mtimeHandler.sendMessage(msg);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    sartrun = false;
                    setBusinessDialog();
                    break;
            }
        }
    };

    /**
     * 申请创业金
     */
    private void showApplyEntrepreneurs(PioneerAfterBean data) {
        isApplyEntrepreneursMoney = data.getIsApplyEntrepreneursMoney();
        if (isApplyEntrepreneursMoney == 1) {
            Intent intent = new Intent(mActivity, PioneerApplyGoldActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        if (isApplyEntrepreneursMoney == 1 || isApplyEntrepreneursMoney == 2) {
            iv_dian.setVisibility(View.VISIBLE);
        } else {
            iv_dian.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void CounterInfoSuccess(PioneerCounterDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            PioneerCounterDataBean.PionCounterItemBean info = responseBean.getData();
            if (info != null) {
                int zhufubao_balance_status = info.getZhufubao_balance_status();
                int fuqibao_balance_status = info.getFuqibao_balance_status();
                if (zhufubao_balance_status == -1 || fuqibao_balance_status == -1) {
                    SharedPreferencesHelper.put(mActivity, "pioneer_balancestatus", false);
                } else {
                    //平衡
                    SharedPreferencesHelper.put(mActivity, "pioneer_balancestatus", true);
                }
                if (!counterpoise()) {
                    if (isWxCardBag == 1 && isAliCardBag == 1) {
                        showPushLis();
                    } else {
                        showPayTypeDialog();
                    }
                }
            }
        }
    }

    /**
     * 获取收付款弹层
     */
    private void showPushLis() {
        if (receive_order_status == 1) {
            PionPairingUtils.getINSTANCE().blessDaichongLayer();
//            PionPairingUtils.getINSTANCE().userCashPushLis();
        }
    }


    @Override
    public void paySuccess(PayWXDataBean responseBean) {

    }

    @Override
    public void SellFQBSuccess(ResponseBean responseBean) {
        mPresent.getMenuInfo();
    }

    @Override
    public void applyMoneyListSuccess(PioneerDataListBean responseBean) {

    }

    @Override
    public void backBankInfoSuccess(UserBankDataBean responseBean) {

    }

    @Override
    public void ListSuccess(PionOpenSetRecordDataBean responseBean, int pageback) {

    }

    @Override
    public void CZListSuccess(PioneerDataBean responseBean, int pageback) {

    }

    @Override
    public void Error() {
        ToastUtils.showToast(R.string.net_tishi);
    }

    MyDialog mXuFeiDialog;

    public void showXuFeiDialog() {
        if (mXuFeiDialog == null) {
            mXuFeiDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_pion_xufei);// 创建Dialog并设置样式主题
            mXuFeiDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mXuFeiDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mXuFeiDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mXuFeiDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
            mXuFeiDialog.getWindow().setAttributes(p); //设置生效
            ImageView iv_img = mXuFeiDialog.findViewById(R.id.iv_img);
            iv_img.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.225)));
            iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mXuFeiDialog.dismiss();
                    Intent intent = new Intent(mActivity, PioneerXuFeiActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            });
            mXuFeiDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    doFinish();
                    return true;
                }
            });
        } else {
            mXuFeiDialog.show();
        }
    }

    /**
     * 账户平衡时间
     */
    int counterpoiseTime = 17;

    /**
     * 账户平衡--每天平衡时间进入
     * 1.平衡的每天17后进一次;
     * 2.不平衡的每次打开都进;
     *
     * @return
     */
    private boolean counterpoise() {
        boolean pioneer_balancestatus = (boolean) SharedPreferencesHelper.get(mContext, "pioneer_balancestatus", false);
        if (!pioneer_balancestatus) {
            Intent intent = new Intent(mActivity, PioneerCounterpoiseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        } else {
            String hour = DatesUtils.getInstance().getNowTime("H");
            String comtime = (String) SharedPreferencesHelper.get(mContext, "pioneer_balanceComTime", "");
            if (!TextUtils.isEmpty(hour)
                    && (Integer.parseInt(hour) >= counterpoiseTime)
                    && !comtime.equals(DatesUtils.getInstance().getNowTime("yyyy-MM-dd"))) {
                Intent intent = new Intent(mActivity, PioneerCounterpoiseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            }
        }
        return false;
    }

    int select_receive_order_status;
    MyDialog selectusinessDialog;
    ImageView iv_yingye, iv_dayang;

    public void setBusinessDialog() {
        if (selectusinessDialog == null) {
            selectusinessDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_pion_business);// 创建Dialog并设置样式主题
            selectusinessDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectusinessDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectusinessDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectusinessDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectusinessDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_yingye = selectusinessDialog.findViewById(R.id.layout_yingye);
            iv_yingye = selectusinessDialog.findViewById(R.id.iv_yingye);
            LinearLayout layout_dayang = selectusinessDialog.findViewById(R.id.layout_dayang);
            iv_dayang = selectusinessDialog.findViewById(R.id.iv_dayang);
            TextView tv_off = selectusinessDialog.findViewById(R.id.tv_off);
            TextView tv_sure = selectusinessDialog.findViewById(R.id.tv_sure);
            selectusinessDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    sartrun = true;
                }
            });
            layout_yingye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_receive_order_status = 1;
                    iv_yingye.setBackgroundResource(R.mipmap.check_true_red);
                    iv_dayang.setBackgroundResource(R.mipmap.check_false);
                }
            });
            layout_dayang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_receive_order_status = 0;
                    iv_yingye.setBackgroundResource(R.mipmap.check_false);
                    iv_dayang.setBackgroundResource(R.mipmap.check_true_red);
                }
            });
            tv_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectusinessDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectusinessDialog.dismiss();
                    mPresent.receviceOrderStatusSave(select_receive_order_status);
                }
            });
        } else {
            selectusinessDialog.show();
        }
        select_receive_order_status = receive_order_status;
        if (receive_order_status == 1) {
            iv_yingye.setBackgroundResource(R.mipmap.check_true_red);
            iv_dayang.setBackgroundResource(R.mipmap.check_false);
        } else {
            iv_yingye.setBackgroundResource(R.mipmap.check_false);
            iv_dayang.setBackgroundResource(R.mipmap.check_true_red);
        }
    }

    MyDialog mGQBDialog;

    /**
     * 显示填写支付方式提示
     */
    public void showGQBDialog() {
        if (mGQBDialog == null) {
            mGQBDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_hone_coming);// 创建Dialog并设置样式主题
            mGQBDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mGQBDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mGQBDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mGQBDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
            mGQBDialog.getWindow().setAttributes(p); //设置生效
            ImageView iv_img = mGQBDialog.findViewById(R.id.iv_img);
            TextView tv_sure = mGQBDialog.findViewById(R.id.tv_sure);
            TextView tv_title = mGQBDialog.findViewById(R.id.tv_title);
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText("福祺宝额度不足");
            iv_img.setBackgroundResource(R.mipmap.my_center_fqbtc);
            iv_img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tv_sure.setText("查看详情");
            ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tv_sure, R.color.red);
            LinearLayout layout_cancel = mGQBDialog.findViewById(R.id.layout_cancel);
            TextView tv_cont = mGQBDialog.findViewById(R.id.tv_cont);
            tv_cont.setText("邀请新CHO请购祝佑宝\n提升福祺宝祝佑宝额度");
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGQBDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGQBDialog.dismiss();
                    SharedPreferencesHelper.put(mContext, "isNewentreFuqibaoLess_firstshow", true);
                    Intent intent = new Intent(mActivity, PioneerFQBTCActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            });
        } else {
            mGQBDialog.show();
        }
    }

    MyDialog mPayTypeDialog;
    TextView tv_cont;

    /**
     * 显示填写支付方式提示
     */
    public void showPayTypeDialog() {
        if (mPayTypeDialog == null) {
            mPayTypeDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_hone_coming);// 创建Dialog并设置样式主题
            mPayTypeDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mPayTypeDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mPayTypeDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mPayTypeDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
            mPayTypeDialog.getWindow().setAttributes(p); //设置生效
            ImageView iv_img = mPayTypeDialog.findViewById(R.id.iv_img);
            TextView tv_sure = mPayTypeDialog.findViewById(R.id.tv_sure);
            TextView tv_title = mPayTypeDialog.findViewById(R.id.tv_title);
            tv_title.setVisibility(View.VISIBLE);
            iv_img.setBackgroundResource(R.mipmap.my_bangka_tc);
            tv_sure.setText("立即绑定");
            ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tv_sure, R.color.red);
            LinearLayout layout_cancel = mPayTypeDialog.findViewById(R.id.layout_cancel);
            tv_cont = mPayTypeDialog.findViewById(R.id.tv_cont);
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPayTypeDialog.dismiss();
                    showPushLis();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPayTypeDialog.dismiss();
                    Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" +
                            MineFragment.ka_url);
                    startActivity(intent);
                }
            });
        } else {
            mPayTypeDialog.show();
        }
        if (isAliCardBag == 0 && isWxCardBag == 0) {
            tv_cont.setText("绑定微信/支付宝收付款账户\n可减少您的重复操作");
        } else if (isAliCardBag == 0) {
            tv_cont.setText("绑定支付宝收付款账户\n可减少您的重复操作");
        } else if (isWxCardBag == 0) {
            tv_cont.setText("绑定微信收付款账户\n可减少您的重复操作");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        sartrun = true;
        mPresent.getMenuInfo();
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
