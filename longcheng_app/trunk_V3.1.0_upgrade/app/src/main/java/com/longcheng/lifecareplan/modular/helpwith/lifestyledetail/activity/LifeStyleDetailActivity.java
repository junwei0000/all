package com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.exchange.malldetail.activity.MallDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleListProgressUtils;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.adapter.DetailJieQiAdapter;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleCommentDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleDetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.SKBPayDataBean;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.myview.MyScrollView;
import com.longcheng.lifecareplan.utils.share.ShareUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * 生活方式互祝-详情
 */
public class LifeStyleDetailActivity extends BaseListActivity<LifeStyleDetailContract.View, LifeStyleDetailPresenterImp<LifeStyleDetailContract.View>> implements LifeStyleDetailContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.detail_tv_helpname)
    TextView detailTvHelpname;
    @BindView(R.id.detail_tv_time)
    TextView detailTvTime;
    @BindView(R.id.iv_jieqi)
    ImageView iv_jieqi;
    @BindView(R.id.detail_tv_jieqi)
    TextView detail_tv_jieqi;
    @BindView(R.id.layout_actiondetail)
    LinearLayout layout_actiondetail;
    @BindView(R.id.item_iv_thumb)
    ImageView item_iv_thumb;
    @BindView(R.id.item_tv_content)
    TextView item_tv_content;
    @BindView(R.id.item_tv_helpnum)
    TextView item_tv_helpnum;
    @BindView(R.id.item_pb_lifeenergynum)
    NumberProgressBar item_pb_lifeenergynum;
    @BindView(R.id.item_pb_numne)
    TextView item_pb_numne;
    @BindView(R.id.detail_tv_daiyantilte)
    TextView detail_tv_daiyantilte;
    @BindView(R.id.detail_tv_daiyandescribe)
    TextView detail_tv_daiyandescribe;
    @BindView(R.id.lv_jieqi)
    MyListView lv_jieqi;
    @BindView(R.id.main_sv)
    MyScrollView main_sv;


    LifeStyleListProgressUtils mProgressUtils;
    private String user_id, help_wares_id;
    ShareUtils mShareUtils;
    private String wx_share_url;
    private int shop_goods_id;
    private String receive_user_name;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.pagetop_layout_rigth:
                //分享
                if (mShareUtils == null) {
                    mShareUtils = new ShareUtils(mActivity);
                }
                if (!TextUtils.isEmpty(wx_share_url)) {
                    String text = receive_user_name + "申请了互祝，让我们行动起来，一起给TA送上祝福。";
                    String title = "生活方式互祝";
                    mShareUtils.setShare(text, "", R.mipmap.share_icon, wx_share_url, title);
                }
                break;
            case R.id.layout_actiondetail:
                RedeemAgain();
                break;
        }
    }

    private void RedeemAgain() {
        Log.e("btnClick", "再次兑换");
        if (shop_goods_id >= 10000) {
            Intent intent = new Intent(mContext, MallDetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("shop_goods_id", "" + shop_goods_id);
            startActivity(intent);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
        } else {
            //老商品
            Intent intents = new Intent();
            intents.setAction(ConstantManager.MAINMENU_ACTION);
            intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_EXCHANGE);
            intents.putExtra("solar_terms_id", 0);
            intents.putExtra("solar_terms_name", "");
            LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
            ActivityManager.getScreenManager().popAllActivityOnlyMain();
            doFinish();
        }
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.helpwith_lifestyle_detailnew;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        pageTopTvName.setText("互祝详情");
        pagetopIvRigth.setBackgroundResource(R.mipmap.wisheachdetails_share);
        setOrChangeTranslucentColor(toolbar, null);
    }

    boolean firstComIn = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (!firstComIn)
            mPresent.getDetailData(user_id, help_wares_id);
    }

    @Override
    public void setListener() {
        layout_actiondetail.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopIvRigth.setVisibility(View.GONE);
        detail_tv_jieqi.getBackground().setAlpha(92);
        int hei = (int) (DensityUtil.screenWith(mContext) / 2.344);
        iv_jieqi.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, hei));
        pagetopLayoutRigth.setFocusable(true);
        pagetopLayoutRigth.setFocusableInTouchMode(true);
        pagetopLayoutRigth.requestFocus();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initLoad(intent);
    }

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        initLoad(intent);
    }

    private void initLoad(Intent intent) {
        help_wares_id = intent.getStringExtra("help_wares_id");
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        mPresent.getDetailData(user_id, help_wares_id);
    }


    @Override
    protected LifeStyleDetailPresenterImp<LifeStyleDetailContract.View> createPresent() {
        return new LifeStyleDetailPresenterImp<>(mContext, this);
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
    public void DetailSuccess(LifeStyleDetailDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            //http://test.t.asdyf.com/api/v1_0_0/help/lj_payment?id=1350&user_id=942&token=7e3ddf48a199421e37d17b57c7d66a1c
            LifeStyleDetailAfterBean mDetailAfterBean = responseBean.getData();
            if (mDetailAfterBean != null) {
                wx_share_url = mDetailAfterBean.getWx_share_url();
                LifeStyleDetailAfterBean help_goodsInfo = mDetailAfterBean.getHelp_wares();
                if (help_goodsInfo != null) {
                    showTopView(help_goodsInfo);
                }
                ArrayList<LifeStyleDetailAfterBean> orders = mDetailAfterBean.getOrders();
                DetailJieQiAdapter mJieQiAdapter = new DetailJieQiAdapter(mContext, orders);
                lv_jieqi.setAdapter(mJieQiAdapter);
            }
        }
        lv_jieqi.setVisibility(View.VISIBLE);
        setFocuse();
    }


    private void setFocuse() {
        if (firstComIn) {
            firstComIn = false;
            main_sv.post(
                    new Runnable() {
                        public void run() {
                            /**
                             * 从本质上来讲，pulltorefreshscrollview 是 LinearLayout，那么要想让它能滚动到顶部，我们就需要将它转为 ScrollView
                             */
                            if (main_sv != null) {
                                main_sv.smoothScrollTo(0, 0);
                            }

                        }
                    });
        }
    }

    @Override
    public void CommentListSuccess(LifeStyleDetailDataBean responseBean, int page_) {
    }


    /**
     * @param responseBean
     */
    @Override
    public void SendCommentSuccess(LifeStyleCommentDataBean responseBean) {
    }

    @Override
    public void delCommentSuccess(ResponseBean responseBean) {
    }


    @Override
    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
    }


    private void showTopView(LifeStyleDetailAfterBean help_goodsInfo) {
        shop_goods_id = help_goodsInfo.getShop_goods_id();
        receive_user_name = help_goodsInfo.getReceive_user_name();
        detailTvHelpname.setText("接福人：" + receive_user_name);
        String time = help_goodsInfo.getDate();
        detailTvTime.setText(time);
        detail_tv_jieqi.setText(help_goodsInfo.getSolar_term_cn() + "  " + help_goodsInfo.getStart_time());
        String remark = help_goodsInfo.getDes_content();
        detail_tv_daiyandescribe.setText(remark);
        detail_tv_daiyantilte.setText("互祝说明");

        int super_ability_progress = help_goodsInfo.getProgress();
        item_pb_lifeenergynum.setProgress(super_ability_progress);
        if (mProgressUtils == null) {
            mProgressUtils = new LifeStyleListProgressUtils(mActivity);
        }
        mProgressUtils.showNum(super_ability_progress, item_pb_lifeenergynum, item_pb_numne);
        item_pb_lifeenergynum.setReachedBarColor(getResources().getColor(R.color.engry_btn_bg));
        ColorChangeByTime.getInstance().changeDrawableToClolor(mContext, item_pb_numne, R.color.engry_btn_bg);

        GlideDownLoadImage.getInstance().loadCircleImageRoleREf(mContext, help_goodsInfo.getSolar_term_img(), iv_jieqi, 0);
        GlideDownLoadImage.getInstance().loadCircleImageRole(mContext, help_goodsInfo.getShop_goods_img(), item_iv_thumb, 0);
        item_tv_content.setText(help_goodsInfo.getShop_goods_name() + "  " + help_goodsInfo.getShop_goods_price_name());
        Log.e("ResponseBody", "help_goodsInfo.getCumulative_number()==" + help_goodsInfo.getCumulative_number());
        item_tv_helpnum.setText("" + help_goodsInfo.getCumulative_number());
    }


    /**
     * 送上祝福，生成订单的回调
     *
     * @param responseBean
     */
    @Override
    public void PayHelpSuccess(SKBPayDataBean responseBean) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
