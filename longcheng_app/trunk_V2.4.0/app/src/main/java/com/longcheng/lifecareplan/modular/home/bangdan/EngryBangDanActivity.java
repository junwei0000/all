package com.longcheng.lifecareplan.modular.home.bangdan;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
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
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.List;

import butterknife.BindView;

/**
 * 能量中心榜
 */
public class EngryBangDanActivity extends BaseListActivity<BangDanContract.View, BangDanPresenterImp<BangDanContract.View>> implements BangDanContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.lv_data)
    MyListView lvData;
    @BindView(R.id.exchange_sv)
    PullToRefreshScrollView exchangeSv;
    @BindView(R.id.item_tv_num)
    TextView itemTvNum;
    @BindView(R.id.item_iv_thumb)
    ImageView itemIvThumb;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.item_tv_jieeqi)
    TextView itemTvJieeqi;
    @BindView(R.id.item_layout_shenfen)
    LinearLayout itemLayoutShenfen;
    @BindView(R.id.item_tv_rank)
    TextView itemTvRank;
    @BindView(R.id.item_notbang)
    TextView item_notbang;
    @BindView(R.id.layout_item)
    LinearLayout layout_item;
    @BindView(R.id.item_tv_typetitle)
    TextView item_tv_typetitle;

    @BindView(R.id.layout_engrytype)
    LinearLayout layout_engrytype;
    @BindView(R.id.tv_today)
    TextView tv_today;
    @BindView(R.id.tv_tomorrow)
    TextView tv_tomorrow;

    private String user_id;
    private int page;
    private int pageSize = 20;
    private int type = 1;//1 前一日， type =2  前两日。


    @Override
    public void onClick(View v) {
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.tv_today:
                type = 1;
                setTopView();
                break;
            case R.id.tv_tomorrow:
                type = 2;
                setTopView();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.home_bangdan_bless;
    }


    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        layout_engrytype.setVisibility(View.VISIBLE);
        layout_item.setVisibility(View.INVISIBLE);
        pagetopLayoutLeft.setOnClickListener(this);
        tv_today.setOnClickListener(this);
        tv_tomorrow.setOnClickListener(this);
        int hei = (int) (DensityUtil.screenWith(mContext) / 1.769);
        layoutTop.setBackgroundResource(R.mipmap.bangdan_engry_bg);
        layoutTop.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, hei));
        ScrowUtil.ScrollViewConfigAll(exchangeSv);
        exchangeSv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refreshStatus = true;
                getList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refreshStatus = true;
                getList(page + 1);
            }
        });
    }

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        getData(intent);
    }

    private void getData(Intent intent) {
        String title = intent.getStringExtra("title");
        user_id = UserUtils.getUserId(mContext);
        pageTopTvName.setText(title);
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

    private void getList(int page) {
        mPresent.getEngryCenterList(user_id, page, pageSize, type);
    }


    @Override
    protected BangDanPresenterImp<BangDanContract.View> createPresent() {
        return new BangDanPresenterImp<>(mContext, this);
    }

    boolean refreshStatus = false;
    BlessAdapter mAdapter;

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
    public void ListSuccess(BangDanDataBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteS(exchangeSv);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            BangDanAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                BangDanAfterBean mBangDanAfterBean = mEnergyAfterBean.getUserSelf();
                showInfo(mBangDanAfterBean);

                List<BangDanAfterBean> helpList = mEnergyAfterBean.getBlessExponent();
                int size = helpList == null ? 0 : helpList.size();
                if (backPage == 1) {
                    mAdapter = null;
                    showNoMoreData(false, lvData);
                }
                if (size > 0) {
                }
                if (mAdapter == null) {
                    mAdapter = new BlessAdapter(mActivity, helpList, "engryCenter");
                    lvData.setAdapter(mAdapter);
                } else {
                    mAdapter.reloadListView(helpList, false);
                }
                page = backPage;
                checkLoadOver(size);
            }
        }
        lvData.setVisibility(View.VISIBLE);
    }

    private void showInfo(BangDanAfterBean mBangDanAfterBean) {
        if (mBangDanAfterBean != null && item_notbang != null) {
            item_notbang.setVisibility(View.GONE);
            layout_item.setVisibility(View.VISIBLE);
            itemTvNum.setText(mBangDanAfterBean.getRanking());
            GlideDownLoadImage.getInstance().loadCircleImage(mBangDanAfterBean.getAvatar(), itemIvThumb);
            String name = CommonUtil.setName(mBangDanAfterBean.getUser_name());
            itemTvName.setText(name);
            item_tv_typetitle.setText("分数");
            itemTvJieeqi.setText(mBangDanAfterBean.getJieqi_name());
            itemTvRank.setText(mBangDanAfterBean.getExponent());
            List<String> identity_img = mBangDanAfterBean.getIdentity_img();
            itemLayoutShenfen.removeAllViews();
            if (identity_img != null && identity_img.size() > 0) {
                for (String url : identity_img) {
                    LinearLayout linearLayout = new LinearLayout(mActivity);
                    ImageView imageView = new ImageView(mActivity);
                    int dit = DensityUtil.dip2px(mActivity, 16);
                    int jian = DensityUtil.dip2px(mActivity, 3);
                    linearLayout.setPadding(0, 2, jian, 2);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                    GlideDownLoadImage.getInstance().loadCircleImageCommune(mActivity, url, imageView);
                    linearLayout.addView(imageView);
                    itemLayoutShenfen.addView(linearLayout);
                }
            }
        } else {
            item_notbang.setVisibility(View.VISIBLE);
            layout_item.setVisibility(View.GONE);
        }
    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.ScrollViewDownConfig(exchangeSv);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true, lvData);
            }
        } else {
            ScrowUtil.ScrollViewConfigAll(exchangeSv);
        }
    }


    @Override
    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
        if (lvData != null) {
            lvData.setVisibility(View.VISIBLE);
        }
        ListUtils.getInstance().RefreshCompleteS(exchangeSv);
    }


    private void back() {
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
