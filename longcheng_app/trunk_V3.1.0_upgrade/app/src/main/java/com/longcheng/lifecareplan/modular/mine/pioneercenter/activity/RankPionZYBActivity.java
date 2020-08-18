package com.longcheng.lifecareplan.modular.mine.pioneercenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.fragment.MineFragment;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.adapter.FQBRankAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerCounterDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataListBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.UserBankDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetRecordDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.view.FooterNoMoreData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 祝佑宝-排行
 */
public class RankPionZYBActivity extends BaseActivityMVP<PioneerContract.View, PioneerPresenterImp<PioneerContract.View>> implements PioneerContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.help_listview)
    PullToRefreshListView helpListview;
    @BindView(R.id.top_iv_head2)
    CircleImageView topIvHead2;
    @BindView(R.id.top_tv_name2)
    TextView topTvName2;
    @BindView(R.id.top_tv_fqbnum2)
    TextView topTvFqbnum2;
    @BindView(R.id.top_iv_head1)
    CircleImageView topIvHead1;
    @BindView(R.id.top_tv_name1)
    TextView topTvName1;
    @BindView(R.id.top_tv_fqbnum1)
    TextView topTvFqbnum1;
    @BindView(R.id.top_iv_head3)
    CircleImageView topIvHead3;
    @BindView(R.id.top_tv_name3)
    TextView topTvName3;
    @BindView(R.id.top_tv_fqbnum3)
    TextView topTvFqbnum3;
    @BindView(R.id.item_tv_num)
    TextView itemTvNum;
    @BindView(R.id.item_iv_thumb)
    CircleImageView itemIvThumb;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.item_tv_fqbnum)
    TextView itemTvFqbnum;
    @BindView(R.id.tv_today)
    TextView tv_today;
    @BindView(R.id.tv_tomorrow)
    TextView tv_tomorrow;
    @BindView(R.id.btn_open)
    TextView btnOpen;
    @BindView(R.id.mycenter_relat_shenfen)
    LinearLayout mycenter_relat_shenfen;


    private int page = 0;
    private int pageSize = 20;
    FQBRankAdapter mRankAdapter;
    private ImageLoader asyncImageLoader;

    private int type = 1;//1 今日， type =2

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_today:
                type = 1;
                setTopView();
                break;
            case R.id.tv_tomorrow:
                type = 2;
                setTopView();
                break;
            case R.id.btn_open:
                Intent intent = new Intent(mActivity, PioneerOpenZoreActivity.class);
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
        return R.layout.pion_rank_zyb;
    }


    @Override
    public void initView(View view) {
        pageTopTvName.setText("创业导师排行榜");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        asyncImageLoader = new ImageLoader(mContext, "headImg");
        pagetopLayoutLeft.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        tv_today.setOnClickListener(this);
        tv_tomorrow.setOnClickListener(this);
        helpListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                getList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                getList(page + 1);
            }
        });
    }

    @Override
    public void initDataAfter() {
        setTopView();
    }

    private void setTopView() {
        tv_today.setTextColor(getResources().getColor(R.color.white));
        tv_tomorrow.setTextColor(getResources().getColor(R.color.white));
        tv_today.setBackgroundColor(getResources().getColor(R.color.transparent));
        tv_tomorrow.setBackgroundColor(getResources().getColor(R.color.transparent));
        if (type == 1) {
            tv_today.setTextColor(getResources().getColor(R.color.blue_bang));
            tv_today.setBackgroundResource(R.drawable.corners_bg_write_bangzuo);
        } else {
            tv_tomorrow.setTextColor(getResources().getColor(R.color.blue_bang));
            tv_tomorrow.setBackgroundResource(R.drawable.corners_bg_write_bangyou);
        }
        getList(1);
    }

    private void getList(int page_) {
        mPresent.getZYBRankList(type, page_, pageSize);
    }

    @Override
    protected PioneerPresenterImp<PioneerContract.View> createPresent() {
        return new PioneerPresenterImp<>(mActivity, this);
    }

    boolean refreshStatus = false;

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


    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.listViewDownConfig(helpListview);
            if (size > 0 || (page > 1 && size >= 0)) {
            }
        } else {
            ScrowUtil.listViewConfigAll(helpListview);
        }
    }

    List<PioneerItemBean> rankingsTop = new ArrayList<>();
    List<PioneerItemBean> mHotPushList = new ArrayList<>();

    private void showData(List<PioneerItemBean> rankings, int backpage) {
        if (backpage == 1) {
            if (rankings.size() == 0) {
                rankings.add(new PioneerItemBean());
                rankings.add(new PioneerItemBean());
                rankings.add(new PioneerItemBean());
            } else if (rankings.size() == 1) {
                rankings.add(new PioneerItemBean());
                rankings.add(new PioneerItemBean());
            } else if (rankings.size() == 2) {
                rankings.add(new PioneerItemBean());
            }
            for (int i = 0; i < rankings.size(); i++) {
                if (i < 3) {
                    rankingsTop.add(rankings.get(i));
                } else {
                    mHotPushList.add(rankings.get(i));
                }
            }
        } else {
            mHotPushList.addAll(rankings);
        }
        showTop();
        if (mRankAdapter == null) {
            mRankAdapter = new FQBRankAdapter(mActivity, mHotPushList, asyncImageLoader);
            helpListview.setAdapter(mRankAdapter);
        } else {
            mRankAdapter.refreshListView(mHotPushList);
        }
    }

    private void showTop() {
        asyncImageLoader.DisplayImage(rankingsTop.get(0).getAvatar(), topIvHead1);
        asyncImageLoader.DisplayImage(rankingsTop.get(1).getAvatar(), topIvHead2);
        asyncImageLoader.DisplayImage(rankingsTop.get(2).getAvatar(), topIvHead3);
        String User_name1 = rankingsTop.get(0).getUser_name();
        String grades1 = rankingsTop.get(0).getGrades();
        if (TextUtils.isEmpty(User_name1)) {
            User_name1 = "";
        }
        if (TextUtils.isEmpty(grades1)) {
            grades1 = "";
        }
        topTvName1.setText(CommonUtil.setName(User_name1));
        topTvFqbnum1.setText(grades1);

        String User_name2 = rankingsTop.get(1).getUser_name();
        String grades2 = rankingsTop.get(1).getGrades();
        if (TextUtils.isEmpty(User_name2)) {
            User_name2 = "";
        }
        if (TextUtils.isEmpty(grades2)) {
            grades2 = "";
        }
        topTvName2.setText(CommonUtil.setName(User_name2));
        topTvFqbnum2.setText(grades2);

        String User_name3 = rankingsTop.get(2).getUser_name();
        String grades3 = rankingsTop.get(2).getGrades();
        if (TextUtils.isEmpty(User_name3)) {
            User_name3 = "";
        }
        if (TextUtils.isEmpty(grades3)) {
            grades3 = "";
        }
        topTvName3.setText(CommonUtil.setName(User_name3));
        topTvFqbnum3.setText(grades3);
    }

    private void showMine(PioneerItemBean userSelf) {
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
        mycenter_relat_shenfen.removeAllViews();
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
                mycenter_relat_shenfen.addView(linearLayout);
            }
        }
    }

    @Override
    public void MenuInfoSuccess(PioneerDataBean responseBean) {

    }

    @Override
    public void CounterInfoSuccess(PioneerCounterDataBean responseBean) {

    }

    @Override
    public void paySuccess(PayWXDataBean responseBean) {

    }

    @Override
    public void SellFQBSuccess(ResponseBean responseBean) {

    }

    @Override
    public void applyMoneyListSuccess(PioneerDataListBean responseBean) {

    }

    @Override
    public void ListSuccess(PionOpenSetRecordDataBean responseBean, int pageback) {

    }

    @Override
    public void CZListSuccess(PioneerDataBean responseBean, int backpage) {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            PioneerAfterBean mDetailAfterBean = responseBean.getData();
            if (mDetailAfterBean != null) {
                int isOpenEntre = mDetailAfterBean.getIsOpenEntre();//是否开通创业中心
                if (isOpenEntre == 0) {
                    btnOpen.setVisibility(View.VISIBLE);
                } else {
                    btnOpen.setVisibility(View.GONE);
                }
                showMine(mDetailAfterBean.getUserSelf());
                ArrayList<PioneerItemBean> rankings = mDetailAfterBean.getBlessRanking();
                int size = rankings == null ? 0 : rankings.size();
                if (backpage == 1) {
                    mRankAdapter = null;
                    rankingsTop.clear();
                    mHotPushList.clear();
                }
                if (size == 0) {
                    rankings = new ArrayList<>();
                }
                page = backpage;
                showData(rankings, backpage);
                checkLoadOver(size);
            }
        }
    }

    @Override
    public void backBankInfoSuccess(UserBankDataBean responseBean) {

    }

    @Override
    public void Error() {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
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
