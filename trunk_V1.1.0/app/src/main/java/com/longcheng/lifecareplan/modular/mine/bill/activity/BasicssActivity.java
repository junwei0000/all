package com.longcheng.lifecareplan.modular.mine.bill.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.mine.bill.adapter.AdapterBillListAdapter;
import com.longcheng.lifecareplan.modular.mine.bill.adapter.SelectsAdapter;
import com.longcheng.lifecareplan.modular.mine.bill.bean.BillItemBean;
import com.longcheng.lifecareplan.modular.mine.bill.bean.BillResultBean;
import com.longcheng.lifecareplan.modular.mine.bill.widget.BillHeaderView;
import com.longcheng.lifecareplan.modular.mine.bill.widget.TimeSelectUtils;
import com.longcheng.lifecareplan.utils.DatesUtils;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;


public abstract class BasicssActivity extends BaseListActivity<BillContract.View, BillPresenterImp<BillContract.View>> implements BillContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout llBack;
    @BindView(R.id.pageTop_tv_name)
    TextView tvTitle;
    @BindView(R.id.ptf_bill)
    PullToRefreshListView ptrView;
    @BindView(R.id.headerview)
    BillHeaderView headerView;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;

    public static final int TYPE_BILL = 0;
    public static final int TYPE_WAKESKB = 1;
    public static final int TYPE_SLEEPSKB = 2;
    public static final int TYPE_ENGRY = 3;
    public static final int TYPE_SLEEPENGRY = 4;

    private LinkedHashMap<String, BillItemBean> monthInfoMap = new LinkedHashMap<String, BillItemBean>();

    private AdapterBillListAdapter adapter;
    private int pageIndex = 0;
    private int pageSize = 20;

    private String user_id;
    private int source_type;

    private String month;
    private TimeSelectUtils mTimeSelectUtils;
    private List<BillItemBean> source_type_allList;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.layout_month:
                if (mTimeSelectUtils == null) {
                    mTimeSelectUtils = new TimeSelectUtils(mActivity, mHandler, SELECTDATE);
                }
                mTimeSelectUtils.showDialog();
                break;
            case R.id.tv_select:
                if (source_type_allList != null && source_type_allList.size() > 0) {
                    showOpenLoadingDialog();
                }
                break;
        }
    }

    MyDialog selectDialog;

    private void showOpenLoadingDialog() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_billselect);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_cancel = selectDialog.findViewById(R.id.tv_cancel);
            GridView gv_select = selectDialog.findViewById(R.id.gv_select);
            SelectsAdapter mSelectAdapter = new SelectsAdapter(mActivity, source_type_allList);
            gv_select.setAdapter(mSelectAdapter);
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
            gv_select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (source_type_allList != null && source_type_allList.size() > 0) {
                        mSelectAdapter.setSelectMoneyIndex(position);
                        mSelectAdapter.notifyDataSetChanged();
                        source_type = source_type_allList.get(position).getSource_type();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                refresh(0);
                                selectDialog.dismiss();
                            }
                        }, 100);//秒后执行Runnable中的run方法
                    }
                }
            });
        } else {
            selectDialog.show();
        }
    }

    private final int SELECTDATE = 2;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SELECTDATE:
                    String year = msg.getData().getString("year");
                    String month_ = msg.getData().getString("month");
                    month = year + "-" + month_;
                    refresh(0);
                    Log.e(TAG, "  " + month);
                    break;
            }
        }
    };

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bill;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        tvTitle.setText(setTitle());
        setOrChangeTranslucentColor(toolbar, null);
    }

    public abstract String setTitle();

    public abstract int getType();

    @Override
    public void initDataAfter() {
        adapter = new AdapterBillListAdapter(mContext);
        ptrView.setAdapter(adapter);
        refresh(0);
    }

    @Override
    public void setListener() {
        headerView.layout_month.setOnClickListener(this);
        headerView.tv_select.setOnClickListener(this);
        headerView.setMainViewShow();
        llBack.setOnClickListener(this);
        ScrowUtil.listViewUpConfig(ptrView);
        notDateCont.setText("暂无账单信息噢~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.not_searched_img);
        ptrView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refresh(0);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refresh(pageIndex);
            }
        });

        ptrView.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > 0) {
                    BillItemBean item = adapter.getItem(firstVisibleItem - 1);
                    BillItemBean bean = null;
                    if (item.isMonthInfo()) {
                        Log.e("headerView.refresh", "  item.getTitle()  = " + item.getTitle());
                        bean = monthInfoMap.get(item.getTitle());
                        headerView.refreshMainView(bean);
                    } else {
                        if (item.getTime().contains("-") && item.getTime().length() >= 7) {
                            String date = item.getTime().substring(0, 7);
                            Log.e("headerView.refresh", "  date  = " + date);
                            bean = monthInfoMap.get(date);
                            headerView.refreshMainView(bean);
                        }
                    }
                }
            }
        });
    }

    private void refresh(int pageIndex_) {
        user_id = UserUtils.getUserId(mContext);
        mPresent.getData(user_id, pageIndex_ + 1, pageSize, source_type, month, getType());
    }

    @Override
    protected BillPresenterImp<BillContract.View> createPresent() {
        return new BillPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public void onSuccess(BillResultBean responseBean, int pageIndex_) {
        ListUtils.getInstance().RefreshCompleteL(ptrView);
        //获取到数据了
        if (responseBean.getData() != null) {
            source_type_allList = responseBean.getData().getSource_type_allList();
            int dataSize = responseBean.getData().getBills() == null ? 0 : responseBean.getData().getBills().size();
            boolean isRefresh = pageIndex_ == 1;
            List<BillItemBean> list = resetList(isRefresh, responseBean.getData().getBills());
            int listSize = list.size();
            Log.e("aaa", "dataSize = " + dataSize + " , listSize = " + listSize);
            //无数据和加载完成后
            if (listSize == 0) {
                ScrowUtil.listViewNotConfig(ptrView);
                if (isRefresh) {
                    showEmptyView(true);
                } else {
                    showNoMoreData(true, ptrView.getRefreshableView());
                }
                return;
            }
            if (isRefresh && (dataSize < pageSize)) {
                ScrowUtil.listViewNotConfig(ptrView);
                //当前加载的数据不足一页时候，提示footer
                showNoMoreData(true, ptrView.getRefreshableView());
            }

            if (pageIndex_ == 1) {//刷新
                pageIndex = pageIndex_;
                headerView.refreshMainView(list.get(0));
                adapter.refresh(list, true);
                setListViewPos(0);
                showEmptyView(false);
                if (listSize >= pageSize) {
                    ScrowUtil.listViewUpConfig(ptrView);
                    //刷新成功，可能还有下一页的时候，需要先隐藏这个提示footer
                    //非刷新操作的时候，不做任何操作
                    showNoMoreData(false, ptrView.getRefreshableView());
                }
            } else {
                pageIndex++;
                adapter.refresh(list, false);
            }
        } else if (pageIndex_ == 1) {
            ScrowUtil.listViewDownConfig(ptrView);
            showEmptyView(true);
        }
    }

    /**
     * 滚动ListView到指定位置
     *
     * @param pos
     */
    private void setListViewPos(int pos) {
//        if (android.os.Build.VERSION.SDK_INT >= 8) {
//            ptrView.getRefreshableView().smoothScrollToPosition(0);//可以滑动到当前item的顶部
//        } else {
        ptrView.getRefreshableView().setSelection(pos); //可以直接返回顶部，不过没有滑动效果
//        }
    }

    private void showEmptyView(boolean empty) {
        ptrView.setVisibility(empty ? View.GONE : View.VISIBLE);
        if (empty) {
            String mont_ = "";
            if (TextUtils.isEmpty(month)) {
                mont_ = DatesUtils.getInstance().getNowTime("yyyy-MM");
            } else {
                mont_ = month;
            }
            headerView.updateSelectNotData(mont_);
        }
        layoutNotdate.setVisibility(empty ? View.VISIBLE : View.GONE);
    }

    /**
     * 重置服务器获取下来的账单数据，去掉重复的月汇总信息
     *
     * @param list
     * @return
     */
    private List<BillItemBean> resetList(boolean isfresh, List<BillItemBean> list) {
        if (isfresh) {
            monthInfoMap.clear();
        }
        Iterator<BillItemBean> iterator = list.iterator();
        while (iterator.hasNext()) {
            BillItemBean temp = iterator.next();
            if (temp.isMonthInfo()) {
                BillItemBean existBean = monthInfoMap.get(temp.getTitle());
                if (existBean == null) {
                    monthInfoMap.put(temp.getTitle(), temp);
                    Log.e("aab", "resetList put " + temp.getTitle() + " , " + (monthInfoMap.get("2018-08") == null));
                    continue;
                } else if (temp.getTitle().equals(existBean.getTitle())) {
                    iterator.remove();
                }
            }
        }
        return list;

    }

    @Override
    public void onError(String code) {
        ListUtils.getInstance().RefreshCompleteL(ptrView);
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
