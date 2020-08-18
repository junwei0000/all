package com.longcheng.lifecareplan.modular.helpwith.energydetail.activity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ActionDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.adapter.CommentAdapter;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.CommentDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.DetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.EnergyDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.OpenRedDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.PayDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.rank.activity.RankActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.OrderListActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DatesUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.pay.PayCallBack;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.share.ShareUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.jswebview.view.CircleInProgressBar;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 生命能量互祝-详情
 */
public class DetailActivity extends BaseListActivity<DetailContract.View, DetailPresenterImp<DetailContract.View>> implements DetailContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.detail_tv_helpname)
    TextView detailTvHelpname;
    @BindView(R.id.detail_tv_time)
    TextView detailTvTime;
    @BindView(R.id.detail_tv_helpnum)
    TextView detailTvHelpnum;
    @BindView(R.id.detail_tv_havelifeenergy)
    TextView detailTvHavelifeenergy;
    @BindView(R.id.detail_tv_lifeenergy)
    TextView detailTvLifeenergy;
    @BindView(R.id.detail_pb_lifeenergynum)
    CircleInProgressBar detailPbLifeenergynum;
    @BindView(R.id.solid_progress)
    CircleInProgressBar solid_progress;
    @BindView(R.id.frame_progessbg)
    FrameLayout frame_progessbg;
    @BindView(R.id.detail_pbtv_num)
    TextView detailPbtvNum;
    @BindView(R.id.detail_layout_rank)
    LinearLayout detailLayoutRank;
    @BindView(R.id.gv_aixin)
    LinearLayout gv_aixin;

    @BindView(R.id.layout_commune)
    LinearLayout layout_commune;
    @BindView(R.id.detail_iv_communethumb)
    ImageView detailIvCommunethumb;
    @BindView(R.id.detail_tv_communename)
    TextView detailTvCommunename;
    @BindView(R.id.detail_tv_communenum)
    TextView detailTvCommunenum;
    @BindView(R.id.detail_tv_daiyantilte)
    TextView detailTvDaiyantilte;
    @BindView(R.id.detail_tv_daiyandescribe)
    TextView detailTvDaiyandescribe;

    @BindView(R.id.main_sv)
    PullToRefreshScrollView mainSv;
    @BindView(R.id.btn_help)
    TextView btnHelp;
    @BindView(R.id.detail_iv_goodthumb)
    ImageView detailIvGoodthumb;
    @BindView(R.id.detail_tv_goodname)
    TextView detailTvGoodname;
    @BindView(R.id.layout_gooddetail)
    LinearLayout layout_gooddetail;
    @BindView(R.id.detail_tv_goodnum)
    TextView detailTvGoodnum;
    @BindView(R.id.detail_lv_comment)
    MyListView detail_lv_comment;
    @BindView(R.id.layout_comment)
    LinearLayout layout_comment;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    private int page = 0;
    private int pageSize = 20;
    private String user_id, id;


    List<DetailItemBean> commentList = new ArrayList<>();
    List<DetailItemBean> mutual_help_money_all = new ArrayList<>();
    DetailItemBean userInfo;
    int is_applying_help;
    int mutual_help_money;
    int selectCommentPosition;
    CommentAdapter mCommentAdapter;
    DetailHelpDialogUtils mDetailHelpDialogUtils;
    ShareUtils mShareUtils;
    private String goods_h5, goods_id;
    private String wx_share_url;
    List<DetailItemBean> blessings_list;

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
                    String text = "人生最大的意义，莫过于让生命能量流动起来，祝福更多的人。";
                    String title = mContext.getString(R.string.main_title);
                    mShareUtils.setShare(text, "", wx_share_url, title);
                }
                break;
            case R.id.detail_layout_rank:
                Intent intent = new Intent(mContext, RankActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("msg_id", id);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, this);
                break;
            case R.id.layout_gooddetail:
                Intent intents = new Intent(mContext, ActionDetailActivity.class);
                intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intents.putExtra("goods_id", goods_id);
                startActivity(intents);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intents, this);
                break;
            case R.id.btn_help://送上祝福
                if (mutual_help_money_all != null && mutual_help_money_all.size() > 0) {
                    if (mDetailHelpDialogUtils == null) {
                        mDetailHelpDialogUtils = new DetailHelpDialogUtils(mActivity, mHandler, BLESSING);
                    }
                    mDetailHelpDialogUtils.initData(userInfo, blessings_list, is_applying_help, mutual_help_money, mutual_help_money_all);
                    mDetailHelpDialogUtils.setIs_normal_help(is_normal_help);
                    mDetailHelpDialogUtils.showPopupWindow();
                }
                break;
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
        return R.layout.helpwith_engry_detail;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pageTopTvName.setText("互祝详情");
        pagetopIvRigth.setBackgroundResource(R.mipmap.wisheachdetails_share);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("aaa", "ShopCartActivity onResume page = " + page + " , pageSize = " + pageSize);
//        mPresent.getDetailData(user_id, id);
        //这里只是刷新了上部的数据，列表中的评论可能还有展示问题，如果用户刚好在最后一页去互祝，
        // 此时，需要重新刷新下列表所有的数据，这里需要在再次祝福页面传递参数过来进行标记，或者在OnActivityResult里面操作
        //细节等后面完善吧
    }

    @Override
    public void setListener() {
        int bmpW = BitmapFactory.decodeResource(getResources(), R.mipmap.detail_progress_round).getWidth();// 获取图片宽度
        bmpW = bmpW + DensityUtil.dip2px(mContext, 20);
        frame_progessbg.setLayoutParams(new LinearLayout.LayoutParams(bmpW, bmpW));
        int jianju = DensityUtil.dip2px(mContext, 45);
        frame_progessbg.setPadding(jianju, jianju, jianju, jianju);

        layout_gooddetail.setOnClickListener(this);
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
                mPresent.getCommentList(user_id, id, page + 1, pageSize, 1);
            }
        });

    }

    /**
     * 当调用到onNewIntent(intent)的时候，需要在onNewIntent()
     * 中使用setIntent(intent)赋值给Activity的Intent.否则，
     * 后续的getIntent()都是得到老的Intent。
     *
     * @param intent
     */
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
        id = intent.getStringExtra("msg_id");
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        mPresent.getDetailData(user_id, id);
    }

    @Override
    protected DetailPresenterImp<DetailContract.View> createPresent() {
        return new DetailPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mActivity);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mActivity);
    }

    @Override
    public void DetailSuccess(EnergyDetailDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            //http://test.t.asdyf.com/api/v1_0_0/help/lj_payment?id=1350&user_id=942&token=7e3ddf48a199421e37d17b57c7d66a1c
            DetailAfterBean mDetailAfterBean = (DetailAfterBean) responseBean.getData();
            if (mDetailAfterBean != null) {
                wx_share_url = mDetailAfterBean.getWx_share_url();
                DetailItemBean msgInfo = mDetailAfterBean.getMsg_info();
                if (msgInfo != null) {
                    showTopView(msgInfo);
                }
                List<DetailItemBean> rankings = mDetailAfterBean.getRankings();
                if (rankings != null && rankings.size() > 0) {
                    showRankView(rankings);
                }
                DetailItemBean group = mDetailAfterBean.getGroup();
                if (group != null) {
                    showcommueView(group);
                }
                DetailItemBean goodInfo = mDetailAfterBean.getGoods();
                if (goodInfo != null && msgInfo != null) {
                    DetailItemBean action = mDetailAfterBean.getAction();
                    String image = "";
                    if (action != null) {
                        image = action.getImage();
                    }
                    showGoodView(goodInfo, msgInfo.getGoods_x_name(), image);
                }
                DetailItemBean current_jieqi = mDetailAfterBean.getCurrent_jieqi();
                if (current_jieqi != null) {
                    blessings_list = current_jieqi.getBlessings_list();
                }
                is_applying_help = mDetailAfterBean.getIs_applying_help();
                mutual_help_money = mDetailAfterBean.getMutual_help_money();
                userInfo = mDetailAfterBean.getUser_info();
                mutual_help_money_all = mDetailAfterBean.getMutual_help_money_all();
                if (mutual_help_money_all != null && mutual_help_money_all.size() > 0) {
                } else {
                    mutual_help_money_all = new ArrayList<>();
                }
            }
            mPresent.getCommentList(user_id, id, 1, pageSize, 1);
        }
    }

    @Override
    public void CommentListSuccess(EnergyDetailDataBean responseBean, int page_) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            //这里可能存在bug,如果服务器返回的是200，但是没有数据的时候，页码不应该加1
            //最好是每次去判断code=200并且服务器返回的数据个数大于0时候才加1
            DetailAfterBean mDetailAfterBean = (DetailAfterBean) responseBean.getData();
            if (mDetailAfterBean != null) {
                List<DetailItemBean> commentList_ = mDetailAfterBean.getList();
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
                    showNoMoreData(true, detail_lv_comment);
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
                            if (mainSv != null) {
                                ScrollView scrollview = mainSv.getRefreshableView();
                                if (scrollview != null)
                                    scrollview.smoothScrollTo(0, 0);
                            }

                        }
                    });
        }
    }

    /**
     * @param responseBean
     */
    @Override
    public void SendCommentSuccess(CommentDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400") || status.equals("403")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            DetailItemBean mDetailItemBean = (DetailItemBean) responseBean.getData();
            if (mDetailItemBean != null) {
                List<DetailItemBean> replay_comments = commentList.get(selectCommentPosition).getReplay_comments();
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
                List<DetailItemBean> replay_comments = commentList.get(del_index).getReplay_comments();
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


    private void RefreshComplete() {
        ListUtils.getInstance().RefreshCompleteS(mainSv);
    }

    ValueAnimator animator;

    private void simulateProgress(int progress) {
        if (animator == null) {
            animator = ValueAnimator.ofInt(0, progress);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progressnow = (int) animation.getAnimatedValue();
                    if (solid_progress != null) {
                        solid_progress.setProgress(progressnow);
                        detailPbLifeenergynum.setProgress(progressnow);
                    }
                }
            });
            animator.setDuration(800);
            animator.start();
        } else {
            solid_progress.setProgress(progress);
            detailPbLifeenergynum.setProgress(progress);
        }
    }

    int progress;
    int is_normal_help = 1;

    /**
     * 判断是否使用普通能量支付
     *
     * @param msgInfo
     */
    private void setHelpStauts(DetailItemBean msgInfo) {
        int ability_type = msgInfo.getAbility_type();//互祝类型 1普通  2超级 3混合
        if (ability_type == 2) {
            is_normal_help = 0;
        } else if (ability_type == 3) {
            int normal_progress = msgInfo.getNormal_progress();
            int Ability_proportion = msgInfo.getAbility_proportion();
            if (normal_progress >= Ability_proportion) {
                is_normal_help = 0;
            }
        } else {
            is_normal_help = 1;
        }
    }

    private void showTopView(DetailItemBean msgInfo) {
        setHelpStauts(msgInfo);
        detailTvHelpname.setText("接福人：" + msgInfo.getH_user());
        int timeTamp = msgInfo.getM_time();
        String time = DatesUtils.getInstance().getTimeStampToDate(timeTamp, "yyyy-MM-dd");
        detailTvTime.setText(time);
        String cumulative_number = msgInfo.getCumulative_number();
        detailTvHelpnum.setText(cumulative_number);

        String ability_price_action = msgInfo.getAbility_price_action();
        detailTvHavelifeenergy.setText(ability_price_action);
        int ability_price = msgInfo.getAbility_price();
        detailTvLifeenergy.setText("" + ability_price);
        progress = msgInfo.getProgress();
        if (progress >= 100) {
            btnHelp.setVisibility(View.GONE);
            sendBroadcastsRefreshOrderList();
        } else {
            btnHelp.setVisibility(View.VISIBLE);
        }
        simulateProgress(progress);
        String mCurrentDrawText = String.format("%d", progress * 100 / 100);
        mCurrentDrawText = mCurrentDrawText + "%";
        detailPbtvNum.setText(mCurrentDrawText);
        Log.e("aaa", "progress = " + progress + " 已有：" + ability_price_action + " ， 共计：" + ability_price);

        String description = msgInfo.getDescription();
        detailTvDaiyandescribe.setText(description);
        //活动类型: 1 代言活动,2 邀请新用户代言活动3, 公益活动
        int activity_type = msgInfo.getActivity_type();
        if (activity_type != 1) {
            detailTvDaiyantilte.setText("互祝说明");
            detailTvDaiyantilte.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
            detailTvDaiyandescribe.setTextColor(getResources().getColor(R.color.text_contents_color));
        } else {
            detailTvDaiyantilte.setText("我为健康中国代言");
            detailTvDaiyantilte.setTextColor(getResources().getColor(R.color.red));
            detailTvDaiyandescribe.setTextColor(getResources().getColor(R.color.red));
            btnHelp.setBackgroundResource(R.mipmap.detail_img_daiyanbtn);
        }
    }

    /**
     * 排行
     *
     * @param rankings
     */
    private void showRankView(List<DetailItemBean> rankings) {
        gv_aixin.removeAllViews();
        for (int i = 0; i < rankings.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.helpwith_engry_detail_rank_item, null, false);
            ImageView iv_top = (ImageView) view.findViewById(R.id.iv_top);
            ImageView iv_thumb = (ImageView) view.findViewById(R.id.iv_thumb);
            LinearLayout layout_thumb = (LinearLayout) view.findViewById(R.id.layout_thumb);
            DetailItemBean mHelpWithInfo = rankings.get(i);
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
            gv_aixin.addView(view);
        }
    }


    /**
     * 社区
     */
    private void showcommueView(DetailItemBean group) {
        String status = group.getStatus();//是否有公社   -1没有
        if (!TextUtils.isEmpty(status) && !status.equals("-1")) {
            layout_commune.setVisibility(View.VISIBLE);
            String Group_name = group.getGroup_name();
            String name = group.getName();
            String Count = group.getCount();
            String avatar = group.getAvatar();
            Log.e("showcommueView", "" + avatar);
            GlideDownLoadImage.getInstance().loadCircleImageCommune(mContext, avatar, detailIvCommunethumb, ConstantManager.image_angle);
            detailTvCommunename.setText(Group_name);
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(Count)) {
                detailTvCommunenum.setText("主任：" + group.getName() + "     社员：" + group.getCount());
            }
        } else {
            layout_commune.setVisibility(View.GONE);
        }

    }

    /**
     * 产品
     */
    private void showGoodView(DetailItemBean goodInfo, String Goods_x_name, String image) {
        goods_h5 = goodInfo.getGoods_h5();
        goods_id = goodInfo.getGoods_id();
        Log.e(TAG, "" + goodInfo.toString());
        if (TextUtils.isEmpty(image)) {
            image = goodInfo.getOriginal_img();
        }
        GlideDownLoadImage.getInstance().loadCircleImageRole(mContext, image, detailIvGoodthumb, ConstantManager.image_angle);
        detailTvGoodname.setText(Goods_x_name);
        detailTvGoodnum.setText("生命能量：" + goodInfo.getAbility_price());
    }


    /**
     * 评论列表
     *
     * @param commentList
     */
    private void commentView(List<DetailItemBean> commentList) {
        if (detail_lv_comment != null) {
            detail_lv_comment.setFocusable(false);
        }
        if (mCommentAdapter == null) {
            mCommentAdapter = new CommentAdapter(mContext, commentList, mHandler);
            detail_lv_comment.setAdapter(mCommentAdapter);
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
                    mPresent.sendComment(user_id, content, be_comment_id, 1);
                    break;
                case BLESSING:
                    Bundle bundle = msg.getData();
                    String help_comment_content = bundle.getString("help_comment_content");
                    payType = bundle.getString("payType");
                    int selectmoney = bundle.getInt("selectmoney");
                    mPresent.payHelp(user_id, help_comment_content, payType, id, selectmoney);
                    break;
                case ConstantManager.ADDRESS_HANDLE_DEL:
                    del_mutual_help_comment_id = msg.arg1;
                    del_index = msg.arg2;
                    mPresent.delComment(user_id, del_mutual_help_comment_id);
                    break;
            }
        }
    };
    String payType;
    String one_order_id;
    int del_mutual_help_comment_id;
    int del_index;

    /**
     * 送上祝福，生成订单的回调
     *
     * @param responseBean
     */
    @Override
    public void PayHelpSuccess(PayWXDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            mPresent.getDetailData(user_id, id);
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            PayWXAfterBean payWeChatBean = (PayWXAfterBean) responseBean.getData();
            one_order_id = payWeChatBean.getOne_order_id();
            if (payType.equals("wxpay")) {
                Log.e(TAG, payWeChatBean.toString());
                PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
            } else if (payType.equals("alipay")) {
                String payInfo = payWeChatBean.getPayInfo();
                payZfb(payInfo);
            } else {
                helpSkipSuccess();
            }
        }
    }

    @Override
    public void ListError() {
        RefreshComplete();
    }

    /**
     * 支付宝2.0sdk集成时的难点在于订单参数顺序的一致性
     *
     * @param payInfo
     */
    public void payZfb(String payInfo) {
        PayUtils.Alipay(mActivity, payInfo, new PayCallBack() {
            @Override
            public void onSuccess() {
                helpSkipSuccess();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @Override
    public void getRedEnvelopeDataSuccess(PayDataBean responseBean) {

    }

    @Override
    public void OpenRedEnvelopeDataSuccess(OpenRedDataBean responseBean) {

    }

    /**
     * 祝福成功，显示红包弹层
     */

    private void helpSkipSuccess() {
        mPresent.getDetailData(user_id, id);
        Intent intent = new Intent(mActivity, RedEnvelopeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("one_order_id", one_order_id);
        startActivity(intent);
        ConfigUtils.getINSTANCE().setPageLoginIntentAnim(intent, mActivity);
        sendBroadcastRefreshList();
        sendBroadcastsRefreshOrderList();
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
        if (is_applying_help > 0 || progress > 95) {
            OrderListActivity.editOrderStatus = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        if (animator != null) {//防止内存泄漏，结束时释放动画
            animator.cancel();
            animator = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantManager.BroadcastReceiver_PAY_ACTION);
        registerReceiver(mReceiver, filter);
    }

    /**
     * 微信支付回调广播
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", 100);
            if (errCode == WXPayEntryActivity.PAY_SUCCESS) {
                helpSkipSuccess();
            } else if (errCode == WXPayEntryActivity.PAY_FAILE) {
                ToastUtils.showToast("支付失败");
            } else if (errCode == WXPayEntryActivity.PAY_CANCLE) {
                ToastUtils.showToast("支付取消");
            }
        }
    };

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
