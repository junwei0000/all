package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.home.bangdan.BangDanAfterBean;
import com.longcheng.lifecareplan.modular.home.bangdan.BangDanContract;
import com.longcheng.lifecareplan.modular.home.bangdan.BangDanDataBean;
import com.longcheng.lifecareplan.modular.home.bangdan.BangDanPresenterImp;
import com.longcheng.lifecareplan.modular.home.bangdan.BlessAdapter;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EmergencysBangDanActivity extends BaseListActivity<EmergencysBangContract.View, EmergencyPresenterBangImp<EmergencysBangContract.View>> implements EmergencysBangContract.View {
    @BindView(R.id.lv_data)
    MyListView lvData;
    @BindView(R.id.exchange_sv)
    PullToRefreshScrollView exchangeSv;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_cur)
    TextView tv_cur;
    @BindView(R.id.tv_old)
    TextView tv_old;
    @BindView(R.id.item_notbang)
    TextView item_notbang;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.item_tv_num)
    TextView itemTvNum;
    @BindView(R.id.item_tv_jieeqi)
    TextView item_tv_jieeqi;
    @BindView(R.id.item_tv_name)
    TextView item_tv_name;
    @BindView(R.id.item_iv_thumb)
    ImageView item_iv_thumb;
    @BindView(R.id.item_layout_shenfen)
    LinearLayout item_layout_shenfen;
    @BindView(R.id.layout_item)
    LinearLayout layout_item;
    @BindView(R.id.layout_bottom)
    LinearLayout layout_bottom;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    private String user_id;
    private int page = 1;
    boolean refreshStatus = false;
    private int type = 2;
    EmergencysBangAdapter mAdapter;
    private List<EmergencyBangDanList.BessCardRankings> helpList;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_emergencys_bang_dan;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
        layout_item.setVisibility(View.GONE);
        pageTopTvName.setText("救急榜");
    }

    private void back() {
        doFinish();
    }

    @Override
    public void initDataAfter() {
        user_id = UserUtils.getUserId(mContext);
        helpList = new ArrayList<>();
        mPresent.blessCardRanking(user_id, page, 2, pageSize);

        exchangeSv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refreshStatus = true;
                page = 1;
                if (type == 1) {
                    mPresent.blessCardRanking(user_id, page, 1, pageSize);
                } else if (type == 2) {
                    mPresent.blessCardRanking(user_id, page, 2, pageSize);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refreshStatus = true;
                page++;
                if (type == 1) {
                    mPresent.blessCardRanking(user_id, page, 1, pageSize);
                } else if (type == 2) {
                    mPresent.blessCardRanking(user_id, page, 2, pageSize);
                }
            }
        });
    }

    @Override
    public void setListener() {
        ScrowUtil.ScrollViewConfigAll(exchangeSv);
        tv_cur.setOnClickListener(this);
        tv_old.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cur:
                page = 1;
                helpList.clear();
                mAdapter = null;
                type = 2;
                setTopView(type);
                break;
            case R.id.tv_old:
                page = 1;
                mAdapter = null;
                helpList.clear();
                type = 1;
                setTopView(type);
                break;

            case R.id.pagetop_layout_left:
                ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                back();
                break;
        }
    }

    private void setTopView(int type) {
        tv_cur.setTextColor(getResources().getColor(R.color.white));
        tv_old.setTextColor(getResources().getColor(R.color.white));
        tv_cur.setBackgroundColor(getResources().getColor(R.color.transparent));
        tv_old.setBackgroundColor(getResources().getColor(R.color.transparent));
        if (type == 1) {
            layout_bottom.setVisibility(View.GONE);
            tv_old.setTextColor(getResources().getColor(R.color.yellow2));
            tv_old.setBackgroundResource(R.drawable.corners_bg_write_bangyou);
        } else {
            layout_bottom.setVisibility(View.VISIBLE);
            tv_cur.setTextColor(getResources().getColor(R.color.yellow2));
            tv_cur.setBackgroundResource(R.drawable.corners_bg_write_bangzuo);

        }
        LogUtils.e("type" + type);
        mPresent.blessCardRanking(user_id, 1, type, pageSize);
    }

    @Override
    protected EmergencyPresenterBangImp<EmergencysBangContract.View> createPresent() {
        return new EmergencyPresenterBangImp<>(mContext, this);
    }

    @Override
    public void ListSuccess(EmergencyBangDanBean responseBean) {
        ListUtils.getInstance().RefreshCompleteS(exchangeSv);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            EmergencyBangDanList bean = responseBean.getData();
            if (bean != null) {
                showInfo(bean.getUserSelf());
                helpList = bean.getBlessCardRanking();
                LogUtils.e(helpList.size() + "size");
                int size = helpList == null ? 0 : helpList.size();
                if (page == 1) {
                    mAdapter = null;
                    showNoMoreData(false, lvData);
                }
//
                if (mAdapter == null) {
                    mAdapter = new EmergencysBangAdapter(mActivity, helpList);
                    lvData.setAdapter(mAdapter);
                } else {
                    mAdapter.reloadListView(helpList, false);
                }
                // page = backPage;
                checkLoadOver(size);
            }
        }
        lvData.setVisibility(View.VISIBLE);

    }

    @Override
    public void ListError() {

        lvData.setVisibility(View.VISIBLE);
        ListUtils.getInstance().RefreshCompleteS(exchangeSv);
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


    private void showInfo(EmergencyBangDanList.UserSelf userSelf) {
        if (!TextUtils.isEmpty(userSelf.getUser_id())) {
            item_notbang.setVisibility(View.GONE);
            layout_item.setVisibility(View.VISIBLE);
            String rank = userSelf.getRanking();
            Log.e("showInfo", "getRanking=" + rank);
            itemTvNum.setText(rank);
            GlideDownLoadImage.getInstance().loadCircleImage(userSelf.getAvatar(), item_iv_thumb);
            String name = CommonUtil.setName(userSelf.getUser_name());
            item_tv_name.setText(name);
            item_tv_jieeqi.setText(userSelf.getJieqi_name());

            List<String> identity_img = userSelf.getIdentity_img();
            item_layout_shenfen.removeAllViews();
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
                    item_layout_shenfen.addView(linearLayout);
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
}
