package com.longcheng.lifecareplan.modular.home.liveplay.mine.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
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
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.adapter.MyFouseListAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MineItemInfo;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 作者：jun on
 * 时间：2018/9/22 15:33
 * 意图：
 */

public class MyFouseActivity extends BaseActivityMVP<MyContract.View, MyPresenterImp<MyContract.View>> implements MyContract.View {
    @BindView(R.id.date_listview)
    PullToRefreshListView dateListview;
    @BindView(R.id.layout_notlive)
    LinearLayout layout_notlive;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_left)
    LinearLayout layoutLeft;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.notdata_iv_img)
    ImageView notdataIvImg;
    @BindView(R.id.notdata_tv_cont)
    TextView notdataTvCont;


    private int page = 0;
    private int pageSize = 10;
    String video_user_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_left:
                back();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_myfollow;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
    }

    @Override
    public void setListener() {
        layoutLeft.setOnClickListener(this);
        notdataIvImg.setBackgroundResource(R.mipmap.live_quexing);
        notdataTvCont.setText("暂无关注");
        layout_notlive.setBackgroundColor(mActivity.getResources().getColor(R.color.black_live));
        dateListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        video_user_id = getIntent().getStringExtra("video_user_id");
        String user_name = getIntent().getStringExtra("user_name");
        tv_name.setText(user_name);
        getList(1);
    }


    private void getList(int page) {
        mPresent.getMineFollowList(video_user_id, page, pageSize);
    }


    @Override
    protected MyPresenterImp<MyContract.View> createPresent() {
        return new MyPresenterImp<>(mActivity, this);
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


    @Override
    public void getMineInfoSuccess(BasicResponse<MineItemInfo> responseBean) {

    }

    @Override
    public void updateShowTitleSuccess(BasicResponse responseBean) {

    }

    @Override
    public void cancelFollowSuccess(BasicResponse responseBean) {
        if (mAdapter != null) {
            mAdapter.removeItem(cancelposition);
            mAdapter.notifyDataSetChanged();
        }
        ListUtils.getInstance().setNotDateViewL(mAdapter, layout_notlive);
    }

    MyFouseListAdapter mAdapter;

    @Override
    public void BackVideoListSuccess(BasicResponse<MVideoDataInfo> responseBean, int back_page) {
        ListUtils.getInstance().RefreshCompleteL(dateListview);
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            MVideoDataInfo mVideoDataInfo = responseBean.getData();
            if (mVideoDataInfo != null) {
                ArrayList<MVideoItemInfo> mList = mVideoDataInfo.getShortVideoList();
                int size = mList == null ? 0 : mList.size();
                if (back_page == 1) {
                    mAdapter = null;
//            showNoMoreData(false);
                }
                if (mAdapter == null) {
                    mAdapter = new MyFouseListAdapter(mContext, mList, mHandler, CANCELFOLLOW, video_user_id);
                    dateListview.setAdapter(mAdapter);
                } else {
                    mAdapter.reloadListView(mList, false);
                }
                page = back_page;
                checkLoadOver(size);
            }
        } else {
            ToastUtils.showToast("" + responseBean.getMsg());
        }
        ListUtils.getInstance().setNotDateViewL(mAdapter, layout_notlive);
    }

    @Override
    public void Error() {
        RefreshComplete();
        checkLoadOver(0);
    }

    int cancelposition;
    private final int CANCELFOLLOW = 1;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CANCELFOLLOW:
                    cancelposition = msg.arg1;
                    String follow_user_id = (String) msg.obj;
                    mPresent.setCancelFollowLive(follow_user_id);
                    break;
            }
        }
    };

    private void RefreshComplete() {
        ListUtils.getInstance().RefreshCompleteL(dateListview);
        ListUtils.getInstance().setNotDateViewL(mAdapter, layout_notlive);
    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.listViewDownConfig(dateListview);
            if (size > 0 || (page > 1 && size >= 0)) {
//                showNoMoreData(true, dateListview.getRefreshableView());
            }
        } else {
            ScrowUtil.listViewConfigAll(dateListview);
        }
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
