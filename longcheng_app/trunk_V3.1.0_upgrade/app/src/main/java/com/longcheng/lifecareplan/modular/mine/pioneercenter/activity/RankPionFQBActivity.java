package com.longcheng.lifecareplan.modular.mine.pioneercenter.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.adapter.FQBRankAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerCounterDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataListBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.UserBankDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetRecordDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
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

/**
 * 福祺宝-排行
 */
public class RankPionFQBActivity extends BaseListActivity<PioneerContract.View, PioneerPresenterImp<PioneerContract.View>> implements PioneerContract.View {


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

    private int page = 0;
    private int pageSize = 20;
    FQBRankAdapter mRankAdapter;
    private ImageLoader asyncImageLoader;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pion_rank_fqb;
    }

    @Override
    protected View getFooterView() {
        FooterNoMoreData view = new FooterNoMoreData(mContext);
        view.showContJiJu(true);
        return view;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        pageTopTvName.setText("福祺宝额度实时动态榜");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        asyncImageLoader = new ImageLoader(mContext, "headImg");
        pagetopLayoutLeft.setOnClickListener(this);
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
        getList(1);
    }


    private void getList(int page_) {
        mPresent.getFQBRankList(page_, pageSize);
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
                showNoMoreData(true, helpListview.getRefreshableView());
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
        if (userSelf != null && !TextUtils.isEmpty(userSelf.getEntrepreneurs_ranking_id())) {
            itemTvNum.setText(userSelf.getRanking());
            itemTvName.setText(userSelf.getUser_name());
            itemTvFqbnum.setText(userSelf.getGrades());
            asyncImageLoader.DisplayImage(userSelf.getAvatar(), itemIvThumb);
        } else {
            itemTvNum.setText("");
            itemTvFqbnum.setText("");
            itemTvName.setText(UserUtils.getUserName(mContext));
            String avatar = (String) SharedPreferencesHelper.get(mActivity, "avatar", "");
            asyncImageLoader.DisplayImage(avatar, itemIvThumb);
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
                showMine(mDetailAfterBean.getUserSelf());
                ArrayList<PioneerItemBean> rankings = mDetailAfterBean.getBlessRanking();
                int size = rankings == null ? 0 : rankings.size();
                if (backpage == 1) {
                    mRankAdapter = null;
                    rankingsTop.clear();
                    mHotPushList.clear();
                    showNoMoreData(false, helpListview.getRefreshableView());
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
