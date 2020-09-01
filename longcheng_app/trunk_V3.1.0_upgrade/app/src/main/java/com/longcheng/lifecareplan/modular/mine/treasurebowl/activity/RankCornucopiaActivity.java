package com.longcheng.lifecareplan.modular.mine.treasurebowl.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.mine.fragment.MineFragment;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.adapter.CornucopiaRankAdapter;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.RankListBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.RankingBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.UserSelfBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class RankCornucopiaActivity extends BaseListActivity<RankCornucopiaContract.View, RankCornucopiaPresenterImp<RankCornucopiaContract.View>> implements RankCornucopiaContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.tv_today)
    TextView tvToday;
    @BindView(R.id.tv_tomorrow)
    TextView tvTomorrow;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.item_tv_num)
    TextView itemTvNum;
    @BindView(R.id.item_iv_thumb)
    CircleImageView itemIvThumb;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.mycenter_relat_shenfen)
    LinearLayout mycenterRelatShenfen;
    @BindView(R.id.item_tv_fqbnum)
    TextView itemTvFqbnum;
    @BindView(R.id.tv_lines)
    TextView tvLines;
    @BindView(R.id.lv_data)
    MyListView lvData;
    @BindView(R.id.rank_sv)
    PullToRefreshScrollView rankSv;
    private String user_id;
    private int page = 1;
    private int pageSize = 20;
    boolean refreshStatus = false;

    private CornucopiaRankAdapter cornucopiaRankAdapter;
    private int type = 1;//1 今日， type =2
    private List<RankingBean> rankinglist = null;
    private ImageLoader asyncImageLoader;


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_today:
                type = 1;
                setTopView();
                rankinglist.clear();
                cornucopiaRankAdapter = null;
                break;
            case R.id.tv_tomorrow:
                type = 2;
                setTopView();
                rankinglist.clear();
                cornucopiaRankAdapter = null;
                break;
        }
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
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
        return R.layout.rank_cornucop_layout;
    }


    @Override
    public void setListener() {
        ScrowUtil.ScrollViewConfigAll(rankSv);
        tvToday.setOnClickListener(this);
        tvTomorrow.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        user_id = UserUtils.getUserId(mContext);
        int hei = (int) (DensityUtil.screenWith(mContext) / 1.05);
        layoutTop.setLayoutParams(new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, hei));
        asyncImageLoader = new ImageLoader(mContext, "headImg");
        rankinglist = new ArrayList<>();

        rankSv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refreshStatus = true;
                page = 1;
                getList(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refreshStatus = true;
                page++;
                getList(page);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList(1);
    }

    private void getList(int page_) {
        mPresent.blessCardRanking(user_id, type, page_, pageSize);
    }


    @Override
    public void ListSuccess(RankListBean responseBean, int pageback) {
        ListUtils.getInstance().RefreshCompleteS(rankSv);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            RankListBean bean = responseBean.getData();
            if (bean != null) {
                showMine(bean.getUserSelf());
                rankinglist = bean.getRanking();
                LogUtils.e(rankinglist.size() + "size");
                int size = rankinglist == null ? 0 : rankinglist.size();
                if (page == 1) {
                    cornucopiaRankAdapter = null;
                    showNoMoreData(false, lvData);
                }
                if (cornucopiaRankAdapter == null) {
                    cornucopiaRankAdapter = new CornucopiaRankAdapter(mActivity, rankinglist, asyncImageLoader);
                    lvData.setAdapter(cornucopiaRankAdapter);
                } else {
                    cornucopiaRankAdapter.reloadListView(rankinglist, false);
                }
                page = pageback;
                checkLoadOver(size);
            }
        }
        lvData.setVisibility(View.VISIBLE);
    }

    private void setTopView() {
        tvToday.setTextColor(getResources().getColor(R.color.white));
        tvTomorrow.setTextColor(getResources().getColor(R.color.white));
        tvToday.setBackgroundResource(R.drawable.corners_bg_write_bangzuo_bian);
        tvTomorrow.setBackgroundResource(R.drawable.corners_bg_write_bangyou_bian);
        if (type == 1) {
            tvToday.setTextColor(getResources().getColor(R.color.yellow_fab90b));
            tvToday.setBackgroundResource(R.drawable.corners_bg_write_bangzuo);
        } else {
            tvTomorrow.setTextColor(getResources().getColor(R.color.yellow_fab90b));
            tvTomorrow.setBackgroundResource(R.drawable.corners_bg_write_bangyou);
        }
        getList(1);
    }

    private void showMine(UserSelfBean userSelf) {
        if (userSelf != null && !TextUtils.isEmpty(userSelf.getGrades())) {
            itemTvNum.setText(userSelf.getRanking());
            itemTvName.setText(userSelf.getUser_name());
            itemTvFqbnum.setText(userSelf.getGrades());
            asyncImageLoader.DisplayImage(userSelf.getAvatar(), itemIvThumb);
        } else {
            itemTvNum.setText("--");
            itemTvFqbnum.setText("您未上榜");
            String real_name = (String) SharedPreferencesHelper.get(mActivity, "real_name", "");
            itemTvName.setText(real_name);
            String avatar = (String) SharedPreferencesHelper.get(mActivity, "avatar", "");
            asyncImageLoader.DisplayImage(avatar, itemIvThumb);
        }
        mycenterRelatShenfen.removeAllViews();
        ArrayList<GetHomeInfoBean> user_identity_imgs = MineFragment.user_identity_imgs;
        if (user_identity_imgs != null && user_identity_imgs.size() > 0) {
            for (GetHomeInfoBean info : user_identity_imgs) {
                LinearLayout linearLayout = new LinearLayout(mActivity);
                ImageView imageView = new ImageView(mActivity);
                int dit = DensityUtil.dip2px(mActivity, 14);
                int jian = DensityUtil.dip2px(mActivity, 2);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(mActivity, info.getImage(), imageView);
                linearLayout.addView(imageView);
                mycenterRelatShenfen.addView(linearLayout);
            }
        }
    }

    @Override
    public void ListError() {
        lvData.setVisibility(View.VISIBLE);
        ListUtils.getInstance().RefreshCompleteS(rankSv);
    }


    @Override
    public void showDialog() {
        if (!refreshStatus)
            LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        refreshStatus = false;
        LoadingDialogAnim.dismiss(mContext);
    }

    @Override
    protected RankCornucopiaPresenterImp<RankCornucopiaContract.View> createPresent() {
        return new RankCornucopiaPresenterImp<>(mContext, this);
    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.ScrollViewDownConfig(rankSv);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true, lvData);
            }
        } else {
            ScrowUtil.ScrollViewConfigAll(rankSv);
        }
    }

}
