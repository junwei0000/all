package com.longcheng.lifecareplan.modular.mine.message.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
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
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.OpenRedDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.activity.LifeStyleDetailActivity;
import com.longcheng.lifecareplan.modular.mine.message.adapter.MessageAdapter;
import com.longcheng.lifecareplan.modular.mine.message.bean.MessageAfterBean;
import com.longcheng.lifecareplan.modular.mine.message.bean.MessageItemBean;
import com.longcheng.lifecareplan.modular.mine.message.bean.MessageDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.view.FooterNoMoreData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 消息列表
 */
public class MessageActivity extends BaseListActivity<MessageContract.View, MessagePresenterImp<MessageContract.View>> implements MessageContract.View {

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

    private String user_id;
    private int page = 0;
    private int pageSize = 20;
    MessageAdapter mHomeHotPushAdapter;
    List<MessageItemBean> helpAllList = new ArrayList<>();
    private int count;

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
        return R.layout.my_message;
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
        pageTopTvName.setText("消息");
        notDateCont.setText("暂无数据~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.my_nodata_icon);
        setOrChangeTranslucentColor(toolbar, null);

        SharedPreferencesHelper.put(mContext, "haveNotReadMsgStatus", false);
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
                    Log.e("Observable", "position=" + position);
                    int help_type = helpAllList.get(position - 1).getHelp_type();
                    if (help_type == 1) {
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("msg_id", helpAllList.get(position - 1).getHelp_action_id());
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    } else if (help_type == 2) {
                        Intent intent = new Intent(mContext, LifeStyleDetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("help_goods_id", helpAllList.get(position - 1).getHelp_action_id());
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    }else if (help_type == 3) {
                        Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("html_url", "" + helpAllList.get(position - 1).getInfo_url());
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    }

                }
            }
        });
    }


    @Override
    public void initDataAfter() {
        user_id = UserUtils.getUserId(mContext);
        getList(1);
    }


    private void getList(int page) {
        Log.e("ResponseBody", "user_id=" + user_id + "  ;page=" + page + "  ;pageSize=" + pageSize);
        mPresent.getMessageList(user_id, page, pageSize);
    }


    @Override
    protected MessagePresenterImp<MessageContract.View> createPresent() {
        return new MessagePresenterImp<>(mContext, this);
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
    public void ListSuccess(MessageDataBean responseBean, int back_page) {
        RefreshComplete();
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            MessageAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                count = mEnergyAfterBean.getCount();
                List<MessageItemBean> pushsList = mEnergyAfterBean.getPushs();
                int size = pushsList == null ? 0 : pushsList.size();
                if (back_page == 1) {
                    helpAllList.clear();
                    mHomeHotPushAdapter = null;
                    showNoMoreData(false, helpListview.getRefreshableView());
                }
                if (size > 0) {
                    helpAllList.addAll(pushsList);
                }
                if (mHomeHotPushAdapter == null) {
                    mHomeHotPushAdapter = new MessageAdapter(mActivity, pushsList);
                    helpListview.getRefreshableView().setAdapter(mHomeHotPushAdapter);
                } else {
                    mHomeHotPushAdapter.reloadListView(pushsList, false);
                }
                page = back_page;
                ListUtils.getInstance().setNotDateViewL(mHomeHotPushAdapter, layoutNotdate);
                checkLoadOver(size);
            }
        }
    }

    @Override
    public void OpenRedEnvelopeSuccess(OpenRedDataBean responseBean) {

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

    @Override
    public void onOpenRedEnvelopeError(String msg) {

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
