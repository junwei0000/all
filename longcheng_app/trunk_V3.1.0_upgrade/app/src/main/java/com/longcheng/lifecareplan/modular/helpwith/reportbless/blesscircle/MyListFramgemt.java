package com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.base.BaseListFrag;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.FragmentAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.ReportContract;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.ReportPresenterImp;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.fuqrep.FuQRepListActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter.FuQuanListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter.MyMessageAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.DetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.FQDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.MessageListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.MessageListItemBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportItemBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.fuqi.DevoteFrag;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.fuqi.RewardFrag;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.view.FooterNoMoreData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 */
public class MyListFramgemt extends BaseFragmentMVP<MyCircleContract.View, MyCirclePresenterImp<MyCircleContract.View>> implements MyCircleContract.View {
    @BindView(R.id.date_listview)
    PullToRefreshListView dateListview;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;

    @Override
    public int bindLayout() {
        return R.layout.my_order_fragment;
    }


    @Override
    public void initView(View view) {
        notDateCont.setText("暂无数据噢~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.my_nodata_icon);
        ScrowUtil.listViewDownConfig(dateListview);
        dateListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getList();
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        getList();
    }


    public void getList() {
        Log.e("MyCircleActivity", "getList------==");
        mPresent.getMyMessageList(1);
    }

    @Override
    public void widgetClick(View v) {
    }

    @Override
    protected MyCirclePresenterImp<MyCircleContract.View> createPresent() {
        return new MyCirclePresenterImp<>(mContext, this);
    }


    @Override
    public void showDialog() {
//            LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
//        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void ListSuccess(ReportDataBean responseBean, int back_page) {

    }

    @Override
    public void MessageListSuccess(MessageListDataBean responseBean) {
        ListUtils.getInstance().RefreshCompleteL(dateListview);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            ArrayList<MessageListItemBean> mList = responseBean.getData();
            if (mAdapter == null) {
                mAdapter = new MyMessageAdapter(getActivity(), mList);
                dateListview.setAdapter(mAdapter);
            } else {
                mAdapter.refreshListView(mList);
            }
            ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
        }
    }

    MyMessageAdapter mAdapter;


    @Override
    public void Error() {
        ListUtils.getInstance().RefreshCompleteL(dateListview);
        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
    }
}
