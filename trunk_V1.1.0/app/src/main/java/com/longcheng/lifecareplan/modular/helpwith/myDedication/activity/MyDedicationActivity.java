package com.longcheng.lifecareplan.modular.helpwith.myDedication.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.helpwith.myDedication.adapter.MyAdapter;
import com.longcheng.lifecareplan.modular.helpwith.myDedication.bean.ItemBean;
import com.longcheng.lifecareplan.modular.helpwith.myDedication.bean.MyDedicationAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.myDedication.bean.MyDedicationListDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的奉献
 */
public class MyDedicationActivity extends BaseListActivity<MyDedicationContract.View, MyDedicationPresenterImp<MyDedicationContract.View>> implements MyDedicationContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;

    @BindView(R.id.help_listview)
    PullToRefreshListView helpListview;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_sum)
    TextView tv_sum;

    private String user_id;
    private int page = 0;
    private int pageSize = 20;
    private String count;
    MyAdapter mHomeHotPushAdapter;
    List<ItemBean> helpAllList = new ArrayList<>();

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
        return R.layout.helpwith_mydedication;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        pageTopTvName.setText("我的奉献");
        notDateCont.setText("暂无数据~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.my_nodata_icon);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        helpListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getList(page + 1);
            }
        });
        helpListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (helpAllList != null && helpAllList.size() > 0 && (position - 1) < helpAllList.size()) {
                    Intent intent = new Intent(mContext, MyDedicationDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("h_user_id", helpAllList.get(position - 1).getReceive_user_id());
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                }
            }
        });
    }


    @Override
    public void initDataAfter() {
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        getList(1);
    }

    private void getList(int page) {
        Log.e("checkLoadOver", "" + page);
        mPresent.setListViewData(user_id, page, pageSize);
    }


    @Override
    protected MyDedicationPresenterImp<MyDedicationContract.View> createPresent() {
        return new MyDedicationPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    private void RefreshComplete() {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
    }

    @Override
    public void ListSuccess(MyDedicationListDataBean responseBean, int back_page) {
        RefreshComplete();
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            MyDedicationAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                count = mEnergyAfterBean.getCount();
                if (TextUtils.isEmpty(count)) {
                    count = "0";
                }
                String mutual_help_num = mEnergyAfterBean.getMutual_help_num();
                if (TextUtils.isEmpty(mutual_help_num)) {
                    mutual_help_num = "0";
                }
                String Ability = mEnergyAfterBean.getAbility();
                if (TextUtils.isEmpty(Ability)) {
                    Ability = "0";
                }
                String showT = "我祝福<font color=\"#e60c0c\">" + count + "</font>人，互祝<font color=\"#e60c0c\">" + mutual_help_num + "</font>次";
                tv_num.setText(Html.fromHtml(showT));
                tv_sum.setText("共计：" + Ability);
                List<ItemBean> helpList = mEnergyAfterBean.getMutualHelpMyBlessLists();
                int size = helpList == null ? 0 : helpList.size();
                if (back_page == 1) {
                    helpAllList.clear();
                    mHomeHotPushAdapter = null;
                    showNoMoreData(false, helpListview.getRefreshableView());
                }
                if (size > 0) {
                    helpAllList.addAll(helpList);
                }
                if (mHomeHotPushAdapter == null) {
                    mHomeHotPushAdapter = new MyAdapter(mContext, helpList);
                    helpListview.setAdapter(mHomeHotPushAdapter);
                } else {
                    mHomeHotPushAdapter.reloadListView(helpList, false);
                }
                page = back_page;
                checkLoadOver(size);
                ListUtils.getInstance().setNotDateViewL(mHomeHotPushAdapter, layoutNotdate);
            }
        }
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

    @Override
    public void ListError() {
        RefreshComplete();
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
