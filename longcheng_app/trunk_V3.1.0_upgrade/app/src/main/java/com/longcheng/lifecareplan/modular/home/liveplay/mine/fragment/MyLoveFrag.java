package com.longcheng.lifecareplan.modular.home.liveplay.mine.fragment;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.MyContract;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.MyPresenterImp;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.MyVideoDetailNewActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.adapter.MyVideoListAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MineItemInfo;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 作者：jun on
 * 时间：2018/9/22 15:33
 * 意图：
 */

public class MyLoveFrag extends BaseFragmentMVP<MyContract.View, MyPresenterImp<MyContract.View>> implements MyContract.View {
    @BindView(R.id.date_listview)
    PullToRefreshGridView dateListview;
    @BindView(R.id.layout_notlive)
    LinearLayout layout_notlive;
    @BindView(R.id.notdata_iv_img)
    ImageView notdata_iv_img;
    private int page = 0;
    private int pageSize = 20;
    String video_user_id;

    public void setVideo_user_id(String video_user_id) {
        this.video_user_id = video_user_id;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_myvideo_fragment;
    }

    @Override
    public void initView(View view) {
        notdata_iv_img.setBackgroundResource(R.mipmap.live_quexing);

        layout_notlive.setBackgroundColor(mActivity.getResources().getColor(R.color.black_live));
        dateListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                getList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                getList(page + 1);
            }
        });
        dateListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAllList != null && mAllList.size() > 0) {
                    MyVideoDetailNewActivity.skipVideoDetail(mActivity, mAllList, position, page, "mylove");

                }
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        getList(1);
    }

    @Override
    public void widgetClick(View v) {

    }


    private void getList(int page) {
        mPresent.getMineLoveList(video_user_id, page, pageSize);
    }


    @Override
    protected MyPresenterImp<MyContract.View> createPresent() {
        return new MyPresenterImp<>(getActivity(), this);
    }


    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public void getMineInfoSuccess(BasicResponse<MineItemInfo> responseBean) {

    }

    @Override
    public void updateShowTitleSuccess(BasicResponse responseBean) {

    }

    @Override
    public void cancelFollowSuccess(BasicResponse responseBean) {

    }

    MyVideoListAdapter mAdapter;
    ArrayList<MVideoItemInfo> mAllList = new ArrayList<>();

    @Override
    public void BackVideoListSuccess(BasicResponse<MVideoDataInfo> responseBean, int back_page) {
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            MVideoDataInfo mVideoDataInfo = responseBean.getData();
            if (mVideoDataInfo != null) {
                ArrayList<MVideoItemInfo> mList = mVideoDataInfo.getShortVideoList();
                int size = mList == null ? 0 : mList.size();
                if (back_page == 1) {
                    mAllList.clear();
                    mAdapter = null;
//            showNoMoreData(false);
                }
                if (size > 0) {
                    mAllList.addAll(mList);
                }
                if (mAdapter == null) {
                    mAdapter = new MyVideoListAdapter(mContext, mList);
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
        RefreshComplete();
    }

    @Override
    public void Error() {
        RefreshComplete();
        checkLoadOver(0);
    }

    private void RefreshComplete() {
        ListUtils.getInstance().RefreshCompleteG(dateListview);
        ListUtils.getInstance().setNotDateViewL(mAdapter, layout_notlive);
    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.gridViewDownConfig(dateListview);
            if (size > 0 || (page > 1 && size >= 0)) {
//                showNoMoreData(true, dateListview.getRefreshableView());
            }
        } else {
            ScrowUtil.gridViewConfigAll(dateListview);
        }
    }

}
