package com.longcheng.lifecareplan.modular.home.commune.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.home.commune.adapter.CommuneMineEngryRankAdapter;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneAfterBean;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneDataBean;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneItemBean;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneListDataBean;
import com.longcheng.lifecareplan.modular.home.healthydelivery.detail.activity.HealthyDeliveryDetailAct;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditThumbDataBean;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.AblumUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.like.GoodImgView;
import com.longcheng.lifecareplan.utils.like.GoodView;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的公社
 */
public class CommuneMineActivity extends BaseListActivity<CommuneContract.View, CommunePresenterImp<CommuneContract.View>> implements CommuneContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.iv_communethumb)
    ImageView ivCommunethumb;
    @BindView(R.id.tv_changethumb)
    TextView tvChangethumb;
    @BindView(R.id.iv_changethumb)
    ImageView ivChangethumb;
    @BindView(R.id.layout_changethumb)
    LinearLayout layoutChangethumb;
    @BindView(R.id.frame_thumb)
    FrameLayout frame_thumb;

    @BindView(R.id.tv_bang)
    TextView tv_bang;
    @BindView(R.id.tv_communename)
    TextView tvCommunename;
    @BindView(R.id.tv_zhuren)
    TextView tvZhuren;
    @BindView(R.id.tv_zhixing)
    TextView tvZhixing;
    @BindView(R.id.tv_likenum)
    TextView tvLikenum;
    @BindView(R.id.layout_like)
    LinearLayout layoutLike;
    @BindView(R.id.tv_membernum)
    TextView tvMembernum;
    @BindView(R.id.tv_chonum)
    TextView tvChonum;
    @BindView(R.id.tv_engrynum)
    TextView tvEngrynum;
    @BindView(R.id.tv_dadui)
    TextView tvDadui;
    @BindView(R.id.detail_layout_dadui)
    LinearLayout detailLayoutDadui;
    @BindView(R.id.detail_layout_comm)
    LinearLayout detailLayoutComm;
    @BindView(R.id.tv_gonggaotime)
    TextView tvGonggaotime;
    @BindView(R.id.tv_gonggaocont)
    TextView tvGonggaocont;
    @BindView(R.id.tv_today)
    TextView tvToday;
    @BindView(R.id.tv_leiji)
    TextView tvLeiji;
    @BindView(R.id.detail_lv_rank)
    MyListView detailLvRank;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont_title)
    TextView notDateContTitle;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.main_sv)
    PullToRefreshScrollView mainSv;
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    @BindView(R.id.tv_createdadui)
    TextView tvCreatedadui;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.item_iv_head3)
    ImageView itemIvHead3;
    @BindView(R.id.item_layout_head3)
    LinearLayout itemLayoutHead3;
    @BindView(R.id.item_iv_head2)
    ImageView itemIvHead2;
    @BindView(R.id.item_layout_head2)
    LinearLayout itemLayoutHead2;
    @BindView(R.id.item_iv_head1)
    ImageView itemIvHead1;
    @BindView(R.id.item_layout_head1)
    LinearLayout itemLayoutHead1;


    private String user_id;
    private int page = 0;
    private int pageSize = 20;
    private int isGroupLikes;
    private int group_id;
    private int team_id;
    private int role;//角色 1：主任 2：执行主任
    private int is_head;
    private String team_name;
    private AblumUtils mAblumUtils;
    private GoodImgView mGoodView;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.tv_bang:
                intent = new Intent(mContext, CommuneJoinListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.frame_thumb:
                if (role == 0) {//社员
                    intent = new Intent(mContext, CommuneJoinListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else {//更换头像
                    mAblumUtils.onAddAblumClick();
                }
                break;
            case R.id.layout_like://点赞
                mPresent.ClickLike(user_id, group_id);
                break;
            case R.id.tv_today:
                todayShowStatus = true;
                checkRankType();
                break;
            case R.id.tv_leiji:
                todayShowStatus = false;
                checkRankType();
                break;
            case R.id.detail_layout_dadui:
                intent = new Intent(mContext, CommuneMineMemberListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("team_id", team_id);
                intent.putExtra("team_name", team_name);
                startActivity(intent);
                break;
            case R.id.detail_layout_comm:
                intent = new Intent(mContext, CommuneMineTeamListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("group_id", group_id);
                startActivity(intent);
                break;
            case R.id.tv_publish:
                intent = new Intent(mContext, PublishActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("group_id", group_id);
                startActivity(intent);
                break;
            case R.id.tv_createdadui:
                intent = new Intent(mContext, CreateTeamActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("type", "create");
                intent.putExtra("team_id", 0);
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
        return R.layout.commune_mine;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        pageTopTvName.setText("我的公社");
        notDateCont.setText("暂无数据~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.my_nodata_icon);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        tv_bang.setOnClickListener(this);
        detailLvRank.setFocusable(false);
        pagetopLayoutLeft.setOnClickListener(this);
        frame_thumb.setOnClickListener(this);
        layoutLike.setOnClickListener(this);
        layoutLike.setFocusable(true);
        tvToday.setOnClickListener(this);
        tvLeiji.setOnClickListener(this);
        detailLayoutDadui.setOnClickListener(this);
        detailLayoutComm.setOnClickListener(this);
        tvPublish.setOnClickListener(this);
        tvCreatedadui.setOnClickListener(this);
        ScrowUtil.ScrollViewUpConfig(mainSv);
        mGoodView = new GoodImgView(this);
        mAblumUtils = new AblumUtils(this, mHandler, UPDATEABLUM);
        mainSv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (todayShowStatus) {
                    mPresent.getMineCommuneTodayList(user_id, group_id, page + 1, pageSize);
                } else {
                    mPresent.getMineCommuneLeiJiList(user_id, group_id, page + 1, pageSize);
                }
            }
        });
    }

    boolean firstComIn = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (!firstComIn)
            initDataAfter();
    }

    @Override
    public void initDataAfter() {
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        mPresent.getMineCommuneInfo(user_id);
    }

    @Override
    protected CommunePresenterImp<CommuneContract.View> createPresent() {
        return new CommunePresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public void JoinListSuccess(CommuneListDataBean responseBean) {
    }

    @Override
    public void MineCommuneInfoSuccess(CommuneListDataBean responseBean) {
        firstComIn = false;
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            CommuneAfterBean mCommuneAfterBean = responseBean.getData();
            if (mCommuneAfterBean != null) {
                String zhuren = mCommuneAfterBean.getGroupDirector();
                String zhixingzhuren = mCommuneAfterBean.getGroupDeputyDirector();
                String groupChoUserNum = mCommuneAfterBean.getGroupChoUserNum();
                tvChonum.setText(groupChoUserNum);
                tvZhuren.setText("主任：" + zhuren);
                tvZhixing.setText("执行主任：" + zhixingzhuren);
                CommuneItemBean userInfo = mCommuneAfterBean.getUserInfo();
                isGroupLikes = mCommuneAfterBean.getIsGroupLikes();
                if (userInfo != null) {
                    group_id = userInfo.getGroup_id();
                    team_id = userInfo.getTeam_id();
                    role = userInfo.getRole();
                    is_head = userInfo.getIs_head();
                }
                if (role == 0) {
                    tvChangethumb.setText("换公社");
                    ivChangethumb.setVisibility(View.VISIBLE);
                    layoutBottom.setVisibility(View.GONE);
                    tv_bang.setVisibility(View.GONE);
                } else {
                    tvChangethumb.setText("更换头像");
                    ivChangethumb.setVisibility(View.GONE);
                    layoutBottom.setVisibility(View.VISIBLE);
                    tv_bang.setVisibility(View.VISIBLE);
                }
                CommuneItemBean groupInfo = mCommuneAfterBean.getGroupInfo();
                if (groupInfo != null) {
                    String group_name = groupInfo.getGroup_name();
                    String avatar = groupInfo.getAvatar();
                    String likes_number = groupInfo.getLikes_number();
                    String count = groupInfo.getCount();
                    GlideDownLoadImage.getInstance().loadCircleImageCommune(mContext, avatar, ivCommunethumb);
                    tvCommunename.setText(group_name);
                    tvLikenum.setText(likes_number);
                    tvMembernum.setText(count);
                }
                CommuneItemBean groupAbilityInfo = mCommuneAfterBean.getGroupAbilityInfo();
                if (groupAbilityInfo != null) {
                    String ability = groupAbilityInfo.getAbility();
                    tvEngrynum.setText(ability);
                }
                CommuneItemBean teamInfo = mCommuneAfterBean.getTeamInfo();
                if (teamInfo != null) {
                    team_name = teamInfo.getTeam_name();
                    tvDadui.setText(team_name);
                }
                List<CommuneItemBean> rankings = mCommuneAfterBean.getGroupTeamUsers();
                showRankView(rankings);
                CommuneItemBean noticeInfo = mCommuneAfterBean.getNoticeInfo();
                String content = "";
                if (noticeInfo != null) {
                    String user_name = noticeInfo.getUser_name();
                    String create_time = noticeInfo.getCreate_time();
                    content = noticeInfo.getContent();
                    //1：主任 2：执行主任
                    int role = noticeInfo.getRole();
                    String typr = "";
                    if (role == 1) {
                        typr = "主任";
                    } else if (role == 2) {
                        typr = "执行主任";
                    }
                    tvGonggaotime.setText(create_time + "  " + typr + " " + user_name);

                } else {

                }
                if (TextUtils.isEmpty(content)) {
                    content = "暂无公告";
                    tvGonggaotime.setVisibility(View.GONE);
                } else {
                    tvGonggaotime.setVisibility(View.VISIBLE);
                }
                tvGonggaocont.setText(content);
            }
            checkRankType();
        } else if (status_.equals("499")) {
            UserLoginBack403Utils.getInstance().sendBroadcastLoginBack403();
        }
    }

    boolean todayShowStatus = true;

    private void checkRankType() {
        if (todayShowStatus) {
            tvToday.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
            tvLeiji.setTextColor(getResources().getColor(R.color.text_noclick_color));
            tvToday.setBackgroundResource(R.drawable.corners_bg_communeengryselect);
            tvLeiji.setBackgroundColor(getResources().getColor(R.color.transparent));
            mPresent.getMineCommuneTodayList(user_id, group_id, 1, pageSize);
        } else {
            tvToday.setTextColor(getResources().getColor(R.color.text_noclick_color));
            tvLeiji.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
            tvLeiji.setBackgroundResource(R.drawable.corners_bg_communeengryselect);
            tvToday.setBackgroundColor(getResources().getColor(R.color.transparent));
            mPresent.getMineCommuneLeiJiList(user_id, group_id, 1, pageSize);
        }
    }

    /**
     * 排行
     *
     * @param rankings
     */
    private void showRankView(List<CommuneItemBean> rankings) {
        itemLayoutHead1.setVisibility(View.GONE);
        itemLayoutHead2.setVisibility(View.GONE);
        itemLayoutHead3.setVisibility(View.GONE);
        if (rankings != null && rankings.size() > 0) {
            if (rankings.size() >= 1) {
                itemLayoutHead1.setVisibility(View.VISIBLE);
                GlideDownLoadImage.getInstance().loadCircleHeadImage(mContext, rankings.get(0).getAvatar(), itemIvHead1);
            }
            if (rankings.size() >= 2) {
                itemLayoutHead2.setVisibility(View.VISIBLE);
                GlideDownLoadImage.getInstance().loadCircleHeadImage(mContext, rankings.get(1).getAvatar(), itemIvHead2);
            }
            if (rankings.size() >= 3) {
                itemLayoutHead3.setVisibility(View.VISIBLE);
                GlideDownLoadImage.getInstance().loadCircleHeadImage(mContext, rankings.get(2).getAvatar(), itemIvHead3);
            }
        }
    }

    @Override
    public void MineRankListSuccess(CommuneListDataBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteS(mainSv);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            CommuneAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                List<CommuneItemBean> rankList = mEnergyAfterBean.getRanking();
                int size = rankList == null ? 0 : rankList.size();
                if (backPage == 1) {
                    mHomeHotPushAdapter = null;
                    showNoMoreData(false, detailLvRank);
                }
                if (mHomeHotPushAdapter == null) {
                    mHomeHotPushAdapter = new CommuneMineEngryRankAdapter(mActivity, rankList, todayShowStatus);
                    detailLvRank.setAdapter(mHomeHotPushAdapter);
                } else {
                    mHomeHotPushAdapter.reloadListView(rankList, false);
                }
                page = backPage;
                checkLoadOver(size);
            }
        }
        ListUtils.getInstance().setNotDateViewL(mHomeHotPushAdapter, layoutNotdate);
    }

    @Override
    public void GetTeamListSuccess(CommuneListDataBean responseBean) {

    }

    @Override
    public void GetMemberListSuccess(CommuneListDataBean responseBean, int backpage) {

    }

    @Override
    public void JoinCommuneSuccess(EditListDataBean responseBean) {

    }

    @Override
    public void ClickLikeSuccess(EditListDataBean responseBean) {
        String status_ = responseBean.getStatus();
        ToastUtils.showToast(responseBean.getMsg());
        if (status_.equals("200")) {
            ClickLikeShowAn(layoutLike);
            initDataAfter();
        }
    }

    /**
     * 点赞效果动画
     *
     * @param view
     */
    public void ClickLikeShowAn(View view) {
        mGoodView.setImage(getResources().getDrawable(R.mipmap.good_checked));
        mGoodView.show(view);
    }

    @Override
    public void editAvatarSuccess(EditThumbDataBean responseBean) {
        String status = responseBean.getStatus();
        ToastUtils.showToast(responseBean.getMsg());
        if (status.equals("400")) {
        } else if (status.equals("200")) {
            initDataAfter();
        }
    }

    @Override
    public void GetCreateTeamInfoSuccess(CommuneListDataBean responseBean) {

    }

    @Override
    public void CreateTeamSuccess(EditListDataBean responseBean) {

    }

    @Override
    public void CreateTeamSearchSuccess(CommuneDataBean responseBean) {

    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.ScrollViewConfigDismiss(mainSv, false);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true, detailLvRank);
            }
        } else {
            ScrowUtil.ScrollViewUpConfig(mainSv, false);
        }
    }

    @Override
    public void ListError() {
        firstComIn = false;
        ListUtils.getInstance().RefreshCompleteS(mainSv);
        ListUtils.getInstance().setNotDateViewL(mHomeHotPushAdapter, layoutNotdate);
    }

    CommuneMineEngryRankAdapter mHomeHotPushAdapter;
    /**
     * 调用相册
     */
    protected static final int UPDATEABLUM = 3;
    protected static final int SETRESULT = 4;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATEABLUM:
                    Bitmap mBitmap = (Bitmap) msg.obj;
                    //上传头像
                    String file = mAblumUtils.Bitmap2StrByBase64(mBitmap);
                    mPresent.uploadCommuneAblum(user_id, group_id, file);
                    break;
                case SETRESULT:
                    int requestCode = msg.arg1;
                    int resultCode = msg.arg2;
                    Intent data = (Intent) msg.obj;
                    mAblumUtils.setResult(requestCode, resultCode, data);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == mAblumUtils.RESULTCAMERA || requestCode == mAblumUtils.RESULTGALLERY
                    || requestCode == mAblumUtils.RESULTCROP) {
                Message msgMessage = new Message();
                msgMessage.arg1 = requestCode;
                msgMessage.arg2 = resultCode;
                msgMessage.obj = data;
                msgMessage.what = SETRESULT;
                mHandler.sendMessage(msgMessage);
                msgMessage = null;
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void back() {
        Intent intents = new Intent();
        intents.setAction(ConstantManager.MAINMENU_ACTION);
        intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_HOME);
        LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
        ActivityManager.getScreenManager().popAllActivityOnlyMain();
        doFinish();
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            back();
        }
        return false;
    }

}
