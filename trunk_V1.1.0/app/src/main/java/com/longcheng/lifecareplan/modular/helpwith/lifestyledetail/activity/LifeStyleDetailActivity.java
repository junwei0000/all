package com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.exchange.malldetail.activity.MallDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.ReplyEditPopupUtils;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.adapter.CommentAdapter;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleCommentDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleDetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleDetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.SKBPayAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.SKBPayDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.lifestylerank.activity.LifeRankActivity;
import com.longcheng.lifecareplan.modular.mine.activatenergy.activity.ActivatEnergyActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.OrderListActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.detail.activity.XiaJiaActivity;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.share.ShareUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.detail_tv_havelifeenergy)
    TextView detailTvHavelifeenergy;
    @BindView(R.id.detail_tv_helpnum)
    TextView detailTvHelpnum;
    @BindView(R.id.detail_tv_lifeenergy)
    TextView detailTvLifeenergy;
    @BindView(R.id.pb_lifeprogressnum)
    NumberProgressBar pbLifeprogressnum;
    @BindView(R.id.pb_num)
    TextView pbNum;
    @BindView(R.id.gv_aixin)
    LinearLayout gvAixin;
    @BindView(R.id.detail_layout_rank)
    LinearLayout detailLayoutRank;
    @BindView(R.id.detail_iv_communethumb)
    ImageView detailIvCommunethumb;
    @BindView(R.id.detail_tv_communename)
    TextView detailTvCommunename;
    @BindView(R.id.detail_tv_communenum)
    TextView detailTvCommunenum;
    @BindView(R.id.layout_commune)
    LinearLayout layoutCommune;
    @BindView(R.id.detail_tv_daiyantilte)
    TextView detailTvDaiyantilte;
    @BindView(R.id.detail_tv_daiyandescribe)
    TextView detailTvDaiyandescribe;
    @BindView(R.id.detail_iv_goodthumb)
    ImageView detailIvGoodthumb;
    @BindView(R.id.detail_tv_goodname)
    TextView detailTvGoodname;
    @BindView(R.id.detail_tv_goodgeshu)
    TextView detail_tv_goodgeshu;
    @BindView(R.id.detail_tv_goodnum)
    TextView detailTvGoodnum;
    @BindView(R.id.layout_gooddetail)
    LinearLayout layoutGooddetail;
    @BindView(R.id.layout_comment)
    LinearLayout layout_comment;
    @BindView(R.id.detail_lv_comment)
    MyListView detailLvComment;
    @BindView(R.id.main_sv)
    PullToRefreshScrollView mainSv;
    @BindView(R.id.btn_help)
    TextView btnHelp;


    private int page = 0;
    private int pageSize = 20;
    private String user_id, help_goods_id;


    List<LifeStyleDetailItemBean> commentList = new ArrayList<>();
    List<LifeStyleDetailItemBean> mutual_help_money_all = new ArrayList<>();
    int is_applying_help;
    int mutual_help_money;
    int selectCommentPosition;
    CommentAdapter mCommentAdapter;
    LifeStyleDetailHelpDialogUtils mDetailHelpDialogUtils;
    ShareUtils mShareUtils;
    private String wx_share_url;
    private int shop_goods_id;
    private String receive_user_name;
    /**
     * 商品是否下架
     */
    int action_status = 1;
    private String skb = "0";
    List<LifeStyleDetailItemBean> blessings_list;

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
                    mShareUtils.setShare(text, "", wx_share_url, title);
                }
                break;
            case R.id.detail_layout_rank:
                if (!TextUtils.isEmpty(help_goods_id)) {
                    Intent intent = new Intent(mContext, LifeRankActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("help_goods_id", help_goods_id);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, this);
                }
                break;
            case R.id.layout_gooddetail:
                if (!TextUtils.isEmpty(wx_share_url)) {
                    RedeemAgain();
                }
                break;
            case R.id.btn_help://送上祝福
                if (mutual_help_money_all != null && mutual_help_money_all.size() > 0) {
                    if (mDetailHelpDialogUtils == null) {
                        mDetailHelpDialogUtils = new LifeStyleDetailHelpDialogUtils(mActivity, mHandler, BLESSING);
                    }
                    mDetailHelpDialogUtils.initData(skb, blessings_list, is_applying_help, mutual_help_money, mutual_help_money_all);
                    mDetailHelpDialogUtils.showPopupWindow();
                }
                break;
        }
    }

    private void RedeemAgain() {
        if (xiajia()) {
            return;
        }
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

    /**
     * 是否下架
     *
     * @return
     */
    private boolean xiajia() {
        boolean xiajiaStatus = false;
        if (action_status == 0) {
            xiajiaStatus = true;
            Intent intent = new Intent(mContext, XiaJiaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("type", "3");
            mContext.startActivity(intent);
        }
        return xiajiaStatus;
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
        return R.layout.helpwith_lifestyle_detail;
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
            mPresent.getDetailData(user_id, help_goods_id);
    }

    @Override
    public void setListener() {
        layoutGooddetail.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        detailLayoutRank.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        ScrowUtil.ScrollViewUpConfig(mainSv);
        mainSv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                mPresent.getCommentList(user_id, help_goods_id, page + 1, pageSize, 2);
            }
        });

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
        help_goods_id = intent.getStringExtra("help_goods_id");
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        mPresent.getDetailData(user_id, help_goods_id);
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
            LifeStyleDetailAfterBean mDetailAfterBean = (LifeStyleDetailAfterBean) responseBean.getData();
            if (mDetailAfterBean != null) {
                wx_share_url = mDetailAfterBean.getWx_share_url();
                LifeStyleDetailItemBean userInfo = mDetailAfterBean.getUser();
                if (userInfo != null) {
                    skb = userInfo.getShoukangyuan();
                }
                LifeStyleDetailItemBean help_goodsInfo = mDetailAfterBean.getHelp_goods();
                if (help_goodsInfo != null) {
                    showTopView(help_goodsInfo);
                    String remark = help_goodsInfo.getRemark();
                    detailTvDaiyandescribe.setText(remark);
                    detailTvDaiyantilte.setText("互祝说明");
                }
                LifeStyleDetailItemBean goodsInfo = mDetailAfterBean.getGoods();
                action_status = goodsInfo.getStatus();

                List<LifeStyleDetailItemBean> rankings = mDetailAfterBean.getRankings();
                if (rankings != null && rankings.size() > 0) {
                    showRankView(rankings);
                }

                LifeStyleDetailItemBean group = mDetailAfterBean.getGroup();
                showcommueView(group);
                if (help_goodsInfo != null) {
                    showGoodView(help_goodsInfo);
                }
                blessings_list = mDetailAfterBean.getTemps();

                is_applying_help = mDetailAfterBean.getIs_apply();
                mutual_help_money = mDetailAfterBean.getApply_money();

                mutual_help_money_all = mDetailAfterBean.getSkb_moneys();
                if (mutual_help_money_all != null && mutual_help_money_all.size() > 0) {
                } else {
                    mutual_help_money_all = new ArrayList<>();
                }
            }
            mPresent.getCommentList(user_id, help_goods_id, 1, pageSize, 2);
        }
        firstComIn = false;
    }


    @Override
    public void CommentListSuccess(LifeStyleDetailDataBean responseBean, int page_) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            //这里可能存在bug,如果服务器返回的是200，但是没有数据的时候，页码不应该加1
            //最好是每次去判断code=200并且服务器返回的数据个数大于0时候才加1
            LifeStyleDetailAfterBean mDetailAfterBean = (LifeStyleDetailAfterBean) responseBean.getData();
            if (mDetailAfterBean != null) {
                List<LifeStyleDetailItemBean> commentList_ = mDetailAfterBean.getList();
                int size = commentList_ == null ? 0 : commentList_.size();
                if (page_ == 1) {
                    commentList.clear();
                    if (mCommentAdapter != null) {
                        mCommentAdapter.reloadListView(commentList, true);
                    }
                }
                if (size > 0) {
                    commentList.addAll(commentList_);
                    commentView(commentList_);
                }
                if (commentList != null && commentList.size() > 0) {
                    layout_comment.setVisibility(View.VISIBLE);
                } else {
                    layout_comment.setVisibility(View.GONE);
                }
                if (size < pageSize) {
                    ScrowUtil.ScrollViewConfigDismiss(mainSv, false);
                    showNoMoreData(true, detailLvComment);
                } else {
                    ScrowUtil.ScrollViewUpConfig(mainSv, false);
                }
                page = page_;
                setFocuse();
            }
        }
        RefreshComplete();
    }

    private void setFocuse() {
        if (page == 1) {
            mainSv.post(
                    new Runnable() {
                        public void run() {
                            /**
                             * 从本质上来讲，pulltorefreshscrollview 是 LinearLayout，那么要想让它能滚动到顶部，我们就需要将它转为 ScrollView
                             */
                            ScrollView scrollview = mainSv.getRefreshableView();
                            scrollview.smoothScrollTo(0, 0);
                        }
                    });
        }
    }

    /**
     * @param responseBean
     */
    @Override
    public void SendCommentSuccess(LifeStyleCommentDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400") || status.equals("403")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            LifeStyleDetailItemBean mDetailItemBean = (LifeStyleDetailItemBean) responseBean.getData();
            if (mDetailItemBean != null) {
                List<LifeStyleDetailItemBean> replay_comments = commentList.get(selectCommentPosition).getReplay_comments();
                if (replay_comments == null) {
                    replay_comments = new ArrayList<>();
                }
                replay_comments.add(mDetailItemBean);
                mCommentAdapter.refreshListView(commentList);
            }
        }
    }

    @Override
    public void delCommentSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400") || status.equals("403")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            if (commentList != null && commentList.size() > 0) {
                List<LifeStyleDetailItemBean> replay_comments = commentList.get(del_index).getReplay_comments();
                if (replay_comments != null && replay_comments.size() > 0) {
                    for (int i = 0; i < replay_comments.size(); i++) {
                        if (del_mutual_help_comment_id == replay_comments.get(i).getMutual_help_comment_id()) {
                            replay_comments.remove(i);
                            break;
                        }
                    }
                }
                mCommentAdapter.refreshListView(commentList);
            }
        }
    }


    @Override
    public void ListError() {
        RefreshComplete();
        ToastUtils.showToast(R.string.net_tishi);
    }

    private void RefreshComplete() {
        ListUtils.getInstance().RefreshCompleteS(mainSv);
    }


    private void showTopView(LifeStyleDetailItemBean help_goodsInfo) {
        shop_goods_id = help_goodsInfo.getGoods_id();
        receive_user_name = help_goodsInfo.getReceive_user_name();
        detailTvHelpname.setText("接福人：" + receive_user_name);
        String time = help_goodsInfo.getDate();
        detailTvTime.setText(time);
        String cumulative_number = help_goodsInfo.getCumulative_number();
        detailTvHavelifeenergy.setText(cumulative_number);
        String skb_cumulative_price = help_goodsInfo.getSkb_cumulative_price();
        detailTvHelpnum.setText(skb_cumulative_price);
        String skb_total_price = help_goodsInfo.getSkb_total_price();
        detailTvLifeenergy.setText("" + skb_total_price);
        progress = help_goodsInfo.getProgress();
        if (progress >= 100) {
            btnHelp.setVisibility(View.GONE);
            sendBroadcastsRefreshOrderList();
        } else {
            btnHelp.setVisibility(View.VISIBLE);
        }
        pbLifeprogressnum.setProgress(progress);
        DetailProgressUtils mProgressUtils = new DetailProgressUtils(mActivity);
        mProgressUtils.showNum(progress, pbLifeprogressnum.getMax(), pbNum);
    }

    int progress;

    /**
     * 排行
     *
     * @param rankings
     */
    private void showRankView(List<LifeStyleDetailItemBean> rankings) {
        gvAixin.removeAllViews();
        for (int i = 0; i < rankings.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.helpwith_engry_detail_rank_item, null, false);
            ImageView iv_top = (ImageView) view.findViewById(R.id.iv_top);
            ImageView iv_thumb = (ImageView) view.findViewById(R.id.iv_thumb);
            LinearLayout layout_thumb = (LinearLayout) view.findViewById(R.id.layout_thumb);
            LifeStyleDetailItemBean mHelpWithInfo = rankings.get(i);
            if (i == 0) {
                iv_top.setBackgroundResource(R.mipmap.wisheachdetails_champion);
                layout_thumb.setBackgroundResource(R.drawable.corners_bg_rank1);
            } else if (i == 1) {
                iv_top.setBackgroundResource(R.mipmap.wisheachdetails_firstrunner);
                layout_thumb.setBackgroundResource(R.drawable.corners_bg_rank2);
            } else if (i == 2) {
                iv_top.setBackgroundResource(R.mipmap.wisheachdetails_thebronze);
                layout_thumb.setBackgroundResource(R.drawable.corners_bg_rank3);
            }
            String avatar = mHelpWithInfo.getAvatar();
            if (!TextUtils.isEmpty(avatar)) {
                GlideDownLoadImage.getInstance().loadCircleHeadImage(mContext, avatar, iv_thumb);
            } else {
                GlideDownLoadImage.getInstance().loadCircleHeadImage(mContext, R.mipmap.user_default_icon, iv_thumb);
            }
            gvAixin.addView(view);
        }
    }


    /**
     * 社区
     */
    private void showcommueView(LifeStyleDetailItemBean group) {
        if (group != null
                && !TextUtils.isEmpty(group.getName())
                && !TextUtils.isEmpty(group.getCount())) {
            layoutCommune.setVisibility(View.VISIBLE);
            String Group_name = group.getGroup_name();
            String name = group.getName();
            String Count = group.getCount();
            GlideDownLoadImage.getInstance().loadCircleImageCommune(mContext, group.getAvatar(), detailIvCommunethumb, ConstantManager.image_angle);
            detailTvCommunename.setText(Group_name);
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(Count)) {
                detailTvCommunenum.setText("主任：" + group.getName() + "     社员：" + group.getCount());
            }
        } else {
            layoutCommune.setVisibility(View.GONE);
        }

    }

    /**
     * 产品
     */
    private void showGoodView(LifeStyleDetailItemBean help_goodsInfo) {
        GlideDownLoadImage.getInstance().loadCircleImageRole(mContext, help_goodsInfo.getGoods_img(), detailIvGoodthumb, 0);
        detailTvGoodname.setText(help_goodsInfo.getGoods_name());
        detail_tv_goodgeshu.setText("  x  " + help_goodsInfo.getApply_goods_number());
        detailTvGoodnum.setText("寿康宝：" + help_goodsInfo.getSkb_unit_price());
    }


    /**
     * 评论列表
     *
     * @param commentList
     */
    private void commentView(List<LifeStyleDetailItemBean> commentList) {
        if (detailLvComment != null) {
            detailLvComment.setFocusable(false);
        }
        if (mCommentAdapter == null) {
            mCommentAdapter = new CommentAdapter(mContext, commentList, mHandler);
            detailLvComment.setAdapter(mCommentAdapter);
        } else {
            mCommentAdapter.reloadListView(commentList, false);
        }
    }

    ReplyEditPopupUtils mReplyEditUtils;
    int be_comment_id;
    public static final int REPLY = 11;
    public static final int BLESSING = 22;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ConstantManager.ADDRESS_HANDLE_SETMOREN:
                    be_comment_id = msg.arg1;
                    selectCommentPosition = msg.arg2;
                    if (mReplyEditUtils == null) {
                        mReplyEditUtils = new ReplyEditPopupUtils(mHandler, REPLY);
                    }
                    mReplyEditUtils.popWiw(mActivity, btnHelp);
                    break;
                case REPLY:
                    String content = (String) msg.obj;
                    content = content.replace(" ", "");
                    mPresent.sendComment(user_id, content, be_comment_id, 2);
                    break;
                case BLESSING:
                    Bundle bundle = msg.getData();
                    String help_comment_content = bundle.getString("help_comment_content");
                    String help_goods_skb_money_id = bundle.getString("help_goods_skb_money_id");
                    int skb_price = bundle.getInt("skb_price");
                    if (Double.valueOf(skb) >= skb_price) {
                        mPresent.lifeStylePayHelp(user_id, help_comment_content, help_goods_skb_money_id, help_goods_id, skb_price);
                    } else {
                        showSKBDialog();
                    }
                    break;
                case ConstantManager.ADDRESS_HANDLE_DEL:
                    del_mutual_help_comment_id = msg.arg1;
                    del_index = msg.arg2;
                    mPresent.delComment(user_id, del_mutual_help_comment_id);
                    break;
            }
        }
    };
    MyDialog redBaoDialog;
    int del_mutual_help_comment_id;
    int del_index;

    /**
     * 寿康宝不足提示
     */
    public void showSKBDialog() {
        if (redBaoDialog == null) {
            redBaoDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_lifestyledetail_notskb);// 创建Dialog并设置样式主题
            redBaoDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = redBaoDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            redBaoDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = redBaoDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 5 / 6; //宽度设置为屏幕
            redBaoDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_cancel = (LinearLayout) redBaoDialog.findViewById(R.id.layout_cancel);
            TextView btn_gohelp = (TextView) redBaoDialog.findViewById(R.id.btn_gohelp);
            TextView btn_jihuo = (TextView) redBaoDialog.findViewById(R.id.btn_jihuo);
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redBaoDialog.dismiss();
                }
            });

            btn_gohelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redBaoDialog.dismiss();
                    Intent intent = new Intent(mContext, HelpWithEnergyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("skiptype", "lifedetalitohelp");
                    startActivity(intent);
                }
            });
            btn_jihuo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redBaoDialog.dismiss();
                    Intent intent = new Intent(mContext, ActivatEnergyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            });
        } else {
            redBaoDialog.show();
        }
    }

    /**
     * 送上祝福，生成订单的回调
     *
     * @param responseBean
     */
    @Override
    public void PayHelpSuccess(SKBPayDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            mPresent.getDetailData(user_id, help_goods_id);
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getMsg());
            SKBPayAfterBean mSKBPayAfterBean = responseBean.getData();
            helpSkipSuccess(mSKBPayAfterBean);
        }
    }

    /**
     * 祝福成功，显示红包弹层
     */
    private void helpSkipSuccess(SKBPayAfterBean mSKBPayAfterBean) {
        sendBroadcastRefreshList();
        sendBroadcastsRefreshOrderList();

        Intent intent = new Intent(mActivity, LifeStylePaySuccessActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (mSKBPayAfterBean != null) {
            intent.putExtra("sponsor_user_name", mSKBPayAfterBean.getSponsor_user_name());
            intent.putExtra("receive_user_name", mSKBPayAfterBean.getReceive_user_name());
            intent.putExtra("skb_price", mSKBPayAfterBean.getSkb_price());
            intent.putExtra("help_goods_id", help_goods_id);
        } else {
            intent.putExtra("sponsor_user_name", "");
            intent.putExtra("receive_user_name", "");
            intent.putExtra("skb_price", "");
            intent.putExtra("help_goods_id", help_goods_id);
        }
        startActivity(intent);
        doFinish();
    }

    /**
     * 祝福完成后刷新互祝列表
     */
    private void sendBroadcastRefreshList() {
        Intent intent = new Intent();
        intent.setAction(ConstantManager.BroadcastReceiver_ENGRYLIST_ACTION);
        intent.putExtra("errCode", WXPayEntryActivity.PAYDETAIL_SHUAXIN);
        sendBroadcast(intent);//发送普通广播
    }

    /**
     * 祝福完成后刷新订单列表
     */
    private void sendBroadcastsRefreshOrderList() {
        if (is_applying_help > 0 || progress > 90) {
            OrderListActivity.editOrderStatus=true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * android.view.WindowLeaked的解决办法
         * 解决方法：
         关闭(finish)某个Activity前，要确保附属在上面的Dialog或PopupWindow已经关闭(dismiss)了。
         */
        if (mDetailHelpDialogUtils != null && mDetailHelpDialogUtils.selectDialog != null) {
            mDetailHelpDialogUtils.selectDialog.dismiss();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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
