package com.longcheng.lifecareplan.modular.helpwith.medalrank.activity;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.helpwith.medalrank.adapter.MyAdapter;
import com.longcheng.lifecareplan.modular.helpwith.medalrank.bean.ItemBean;
import com.longcheng.lifecareplan.modular.helpwith.medalrank.bean.MyAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.medalrank.bean.MyRankListDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 奖牌榜
 */
public class MedalRankActivity extends BaseListActivity<MyContract.View, MyPresenterImp<MyContract.View>> implements MyContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.tv_personal)
    TextView tvPersonal;
    @BindView(R.id.tv_commune)
    TextView tvCommune;
    @BindView(R.id.userorder_iv_cursor)
    ImageView userorderIvCursor;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_engry)
    TextView tvEngry;
    @BindView(R.id.listview_personal)
    PullToRefreshListView listviewPersonal;
    @BindView(R.id.listview_commune)
    PullToRefreshListView listviewCommune;
    @BindView(R.id.item_tv_sortNum)
    TextView itemTvSortNum;
    @BindView(R.id.item_iv_thumb)
    ImageView itemIvThumb;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.item_tv_shequ)
    TextView itemTvShequ;
    @BindView(R.id.item_tv_renci)
    TextView itemTvRenci;
    @BindView(R.id.item_tv_engerynum)
    TextView itemTvEngerynum;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    private String user_id;
    int type = 1;//type=2公社榜;type=1个人榜
    int personalpage = 1;
    int communepage = 1;
    int page_size = 20;
    int personalsort = 2;//sort=1按总能量倒序，sort=2按总互助次数倒序
    int communesort = 2;//sort=1按总能量倒序，sort=2按总互助次数倒序
    private MyAdapter mPersonalAdapter;
    private MyAdapter mCommuneAdapter;
    ItemBean mCommuneBean;
    ItemBean mPersonalBean;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_personal:
                type = 1;
                initTopSelect(type);
                showBottom(mPersonalBean);
                break;
            case R.id.tv_commune:
                type = 2;
                initTopSelect(type);
                showBottom(mCommuneBean);
                break;
            case R.id.tv_num:
                if (type == 1) {
                    personalsort = 2;
                    getPersonalList(1);
                } else {
                    communesort = 2;
                    getCommuneList(1);
                }
                showSelView(type);
                break;
            case R.id.tv_engry:
                if (type == 1) {
                    personalsort = 1;
                    getPersonalList(1);
                } else {
                    communesort = 1;
                    getCommuneList(1);
                }
                showSelView(type);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.helpwith_medalrank;
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
        pagetopLayoutLeft.setOnClickListener(this);
        tvPersonal.setOnClickListener(this);
        tvCommune.setOnClickListener(this);

        tvNum.setOnClickListener(this);
        tvEngry.setOnClickListener(this);
        ScrowUtil.listViewConfigAll(listviewPersonal);
        ScrowUtil.listViewConfigAll(listviewCommune);
        listviewPersonal.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getPersonalList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getPersonalList(personalpage + 1);
            }
        });
        listviewCommune.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getCommuneList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getCommuneList(communepage + 1);
            }
        });
    }

    @Override
    public void initDataAfter() {
        int bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();// 获取图片宽度
        userorderIvCursor.setLayoutParams(new LinearLayout.LayoutParams(bmpW, DensityUtil.dip2px(mContext, 2)));
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        initTopSelect(type);
        getPersonalList(1);
        getCommuneList(1);
    }


    private void getPersonalList(int personalpage) {
        mPresent.getPersonalList(user_id, 1, personalpage, page_size, personalsort);

    }

    private void getCommuneList(int communepage) {
        mPresent.getCommuneList(user_id, 2, communepage, page_size, communesort);

    }

    @Override
    protected MyPresenterImp<MyContract.View> createPresent() {
        return new MyPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    private void RefreshCompletePersonal() {
        ListUtils.getInstance().RefreshCompleteL(listviewPersonal);
    }

    private void RefreshCompleteCommune() {
        ListUtils.getInstance().RefreshCompleteL(listviewCommune);
    }

    @Override
    public void PersonalListSuccess(MyRankListDataBean responseBean, int back_page) {
        RefreshCompletePersonal();
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            MyAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                mPersonalBean = mEnergyAfterBean.getPersonal_ranking();
                showBottom(mPersonalBean);
                List<ItemBean> mPersonalList = mEnergyAfterBean.getRankings();
                int size = mPersonalList == null ? 0 : mPersonalList.size();
                if (back_page == 1) {
                    mPersonalAdapter = null;
                    showNoMoreData(false, listviewPersonal.getRefreshableView());
                }
                if (size == 0) {
                    mPersonalList = new ArrayList<>();
                }
                if (mPersonalAdapter == null) {
                    mPersonalAdapter = new MyAdapter(mContext, mPersonalList);
                    mPersonalAdapter.setType(1);
                    listviewPersonal.setAdapter(mPersonalAdapter);
                } else {
                    mPersonalAdapter.reloadListView(mPersonalList, false);
                }
                checkLoadOver(size, listviewPersonal);
                personalpage = back_page;
            }
        } else if (status_.equals("499")) {
            UserLoginBack403Utils.getInstance().sendBroadcastLoginBack403();
        }
    }

    private void showBottom(ItemBean mItemBean) {
        if (mItemBean != null) {
            layoutBottom.setVisibility(View.VISIBLE);
            if (type == 1) {
                String id = mItemBean.getId();
                if (TextUtils.isEmpty(id)) {
                    id = "暂无";
                }
                itemTvSortNum.setText(id);
                itemTvName.setText(mItemBean.getUser_name());
                itemTvShequ.setText(mItemBean.getGroup_name());
                itemTvRenci.setText("互祝人次：" + mItemBean.getCount());
                itemTvEngerynum.setText("互祝能量：" + mItemBean.getAbility());
                GlideDownLoadImage.getInstance().loadCircleHeadImage(mContext, mItemBean.getAvatar(), itemIvThumb);
                itemTvName.setVisibility(View.VISIBLE);
                itemTvShequ.setTextColor(getResources().getColor(R.color.text_noclick_color));
            } else {
                String status = mItemBean.getStatus();
                if (!TextUtils.isEmpty(status) && !status.equals("0")) {
                    itemTvName.setVisibility(View.GONE);
                    itemTvShequ.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
                    String id = mItemBean.getId();
                    if (TextUtils.isEmpty(id)) {
                        id = "暂无";
                    }
                    itemTvSortNum.setText(id);
                    itemTvName.setText(mItemBean.getUser_name());
                    itemTvShequ.setText(mItemBean.getGroup_name());
                    itemTvRenci.setText("互祝人次：" + mItemBean.getCount());
                    itemTvEngerynum.setText("互祝能量：" + mItemBean.getAbility());
                    GlideDownLoadImage.getInstance().loadCircleHeadImage(mContext, mItemBean.getAvatar(), itemIvThumb);
                } else {
                    layoutBottom.setVisibility(View.GONE);
                }
            }
        } else {
            layoutBottom.setVisibility(View.GONE);
        }
    }

    @Override
    public void CommuneListSuccess(MyRankListDataBean responseBean, int back_page) {
        RefreshCompleteCommune();
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            MyAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                mCommuneBean = mEnergyAfterBean.getPersonal_ranking();
                List<ItemBean> mCommuneList = mEnergyAfterBean.getRankings();
                int size = mCommuneList == null ? 0 : mCommuneList.size();
                if (back_page == 1) {
                    mCommuneAdapter = null;
                    showNoMoreData(false, listviewCommune.getRefreshableView());
                }
                if (size == 0) {
                    mCommuneList = new ArrayList<>();
                }
                if (mCommuneAdapter == null) {
                    mCommuneAdapter = new MyAdapter(mContext, mCommuneList);
                    mCommuneAdapter.setType(2);
                    listviewCommune.setAdapter(mCommuneAdapter);
                } else {
                    mCommuneAdapter.reloadListView(mCommuneList, false);
                }
                checkLoadOver(size, listviewCommune);
                communepage = back_page;
            }
        } else if (status_.equals("499")) {
            UserLoginBack403Utils.getInstance().sendBroadcastLoginBack403();
        }
    }

    private void checkLoadOver(int size, PullToRefreshListView mListview) {
        if (size < page_size) {
            ScrowUtil.listViewDownConfig(mListview);
            if (size > 0) {
                showNoMoreData(true, mListview.getRefreshableView());
            }
        } else {
            ScrowUtil.listViewConfigAll(mListview);
        }
    }

    @Override
    public void ListError() {
        RefreshCompletePersonal();
        RefreshCompleteCommune();
    }


    private void showSelView(int selectArg0) {
        if (selectArg0 == 1) {
            tvNum.setTextColor(getResources().getColor(R.color.text_contents_color));
            tvNum.setText("互祝人次");
            tvNum.setBackgroundResource(R.drawable.corners_bg_write);
            tvEngry.setTextColor(getResources().getColor(R.color.text_contents_color));
            tvEngry.setText("互祝能量");
            tvEngry.setBackgroundResource(R.drawable.corners_bg_write);
            if (personalsort == 2) {
                tvNum.setTextColor(getResources().getColor(R.color.white));
                tvNum.setText("互祝人次");
                tvNum.setBackgroundResource(R.drawable.corners_bg_redprogress);
            } else {
                tvEngry.setTextColor(getResources().getColor(R.color.white));
                tvEngry.setText("互祝能量");
                tvEngry.setBackgroundResource(R.drawable.corners_bg_redprogress);
            }
        } else if (selectArg0 == 2) {
            //公社
            tvNum.setTextColor(getResources().getColor(R.color.text_contents_color));
            tvNum.setText("互祝人次");
            tvNum.setBackgroundResource(R.drawable.corners_bg_write);
            tvEngry.setTextColor(getResources().getColor(R.color.text_contents_color));
            tvEngry.setText("互祝能量");
            tvEngry.setBackgroundResource(R.drawable.corners_bg_write);
            if (communesort == 2) {
                tvNum.setTextColor(getResources().getColor(R.color.white));
                tvNum.setText("互祝人次");
                tvNum.setBackgroundResource(R.drawable.corners_bg_redprogress);
            } else {
                tvEngry.setTextColor(getResources().getColor(R.color.white));
                tvEngry.setText("互祝能量");
                tvEngry.setBackgroundResource(R.drawable.corners_bg_redprogress);
            }
        }
    }


    private void initTopSelect(int selectArg0) {
        if (selectArg0 == 1) {
            tvPersonal.setTextColor(getResources().getColor(R.color.blue));
            tvCommune.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
            listviewPersonal.setVisibility(View.VISIBLE);
            listviewCommune.setVisibility(View.GONE);
        } else if (selectArg0 == 2) {
            tvPersonal.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
            tvCommune.setTextColor(getResources().getColor(R.color.blue));
            listviewPersonal.setVisibility(View.GONE);
            listviewCommune.setVisibility(View.VISIBLE);
        }
        InitImageView(selectArg0);
        showSelView(selectArg0);
    }

    /**
     * 初始化动画
     */
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度

    private void InitImageView(int selectArg0) {
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();// 获取图片宽度
        int screenW = DensityUtil.getPhoneWidHeigth(this).widthPixels;
        offset = (screenW / 4 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        userorderIvCursor.setImageMatrix(matrix);// 设置动画初始位置
        setLineFollowSlide(selectArg0);
    }

    /**
     * 设置跟随滑动
     */
    private void setLineFollowSlide(int selectArg0) {
        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        Animation animation = new TranslateAnimation(offset + one * currIndex, offset + one * selectArg0, 0, 0);// 显然这个比较简洁，只有一行代码。
        currIndex = selectArg0;
        animation.setFillAfter(true);// True:图片停在动画结束位置
        animation.setDuration(300);
        userorderIvCursor.startAnimation(animation);
    }

    @Override

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
