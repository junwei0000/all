package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.RecordDelBusiness;
import com.KiwiSports.business.RecordDetailBusiness;
import com.KiwiSports.business.RecordListBusiness;
import com.KiwiSports.business.RecordListBusiness.GetRecordListCallback;
import com.KiwiSports.control.adapter.RecordListAdapter;
import com.KiwiSports.model.DistanceCountInfo;
import com.KiwiSports.model.MainLocationItemInfo;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.db.RecordCalenderDBOpenHelper;
import com.KiwiSports.model.db.RecordListDBOpenHelper;
import com.KiwiSports.model.db.TrackListDBOpenHelper;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.Constants;
import com.KiwiSports.utils.DatesUtils;
import com.KiwiSports.utils.MobileInfoUtils;
import com.KiwiSports.utils.MyDialog;
import com.KiwiSports.utils.PriceUtils;
import com.KiwiSports.utils.PullDownListView;
import com.KiwiSports.utils.PullDownListView.OnRefreshListioner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Administrator 运动记录
 */
public class MainRecordActivity extends BaseActivity implements
        OnRefreshListioner {

    private Activity mHomeActivity;
    private PullDownListView mPullDownView;
    private ListView mListView;
    private SharedPreferences bestDoInfoSharedPrefs;
    private String uid;
    private String token;
    private String access_token;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        CommonUtils.getInstance().mCurrentActivity = CommonUtils.getInstance().mCurrentActivity;
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.main_record);
        mHomeActivity = CommonUtils.getInstance().mHomeActivity;
    }

    @Override
    protected void findViewById() {
        LinearLayout pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
        pagetop_layout_back.setVisibility(View.INVISIBLE);
        TextView pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
        LinearLayout page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
        pagetop_tv_name.setText(getString(R.string.record_title));
        CommonUtils.getInstance().setViewTopHeigth(context, page_top_layout);
        mPullDownView = (PullDownListView) findViewById(R.id.refreshable_view);
        mListView = (ListView) findViewById(R.id.list_date);
    }

    @Override
    protected void setListener() {
        mPullDownView.setRefreshListioner(this);
        mPullDownView.setOrderBottomMoreLine("list");
        bestDoInfoSharedPrefs = CommonUtils.getInstance()
                .getBestDoInfoSharedPrefs(this);
        uid = bestDoInfoSharedPrefs.getString("uid", "");
        token = bestDoInfoSharedPrefs.getString("token", "");
        access_token = bestDoInfoSharedPrefs.getString("access_token", "");
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Log.e("mapsize", "-- mList.size() --" + mList.size()
                        + "--arg2--" + arg2);
                if (mList != null && mList.size() > 0 && arg2 <= mList.size() && arg2 >= 2) {
                    String posid = mList.get(arg2 - 1).getPosid();
                    String record_id = mList.get(arg2 - 1).getRecord_id();
                    String sporttype = mList.get(arg2 - 1).getSportsType();
                    Intent intent;
                    intent = new Intent(mHomeActivity,
                            RecordDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("sporttype", sporttype);
                    intent.putExtra("posid", posid);
                    intent.putExtra("record_id", record_id);
                    intent.putExtra("runStartTime", mList.get(arg2 - 1).getRunStartTime());
                    intent.putExtra("distanceTraveled", mList.get(arg2 - 1).getDistanceTraveled());
                    startActivity(intent);
                    CommonUtils.getInstance().setPageIntentAnim(intent,
                            mHomeActivity);
                }
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList != null && mList.size() > 0 && position <= mList.size() && position >= 2) {
                    del_index = position - 1;
                    String posid = mList.get(position - 1).getPosid();
                    String record_id = mList.get(position - 1).getRecord_id();
                    showDialog(record_id);
                }
                return true;
            }
        });
        if (mRecordCalenderDB == null) {
            mRecordCalenderDB = new RecordCalenderDBOpenHelper(context);
        }
        if (mListDB == null) {
            mListDB = new RecordListDBOpenHelper(this);
        }
        mPullDownViewHandler.sendEmptyMessage(LOADCalender);
    }

    RecordListDBOpenHelper mListDB;


    private ProgressDialog mDialog;
    private HashMap<String, String> mhashmap;
    protected ArrayList<RecordInfo> mList;
    protected ArrayList<RecordInfo> mNewList;
    DistanceCountInfo mDistanceCountInfo;
    protected int total;
    private int page = 1;
    private int page_size;
    private RecordListAdapter adapter;
    int del_index;
    String totalDistances = "";

    private void showDilag() {
        try {
            if (mDialog == null) {
                mDialog = CommonUtils.getInstance().createLoadingDialog(this);
            } else {
                mDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        adapter = null;
        mList = new ArrayList<RecordInfo>();
        mList.add(new RecordInfo());
        page = 1;
        page_size = 20;
    }

    private void initResume() {
        mList = new ArrayList<RecordInfo>();
        mList.add(new RecordInfo());
        page = 1;
        page_size = 20;
    }


    @Override
    protected void processLogic() {
        mPullDownViewHandler.sendEmptyMessage(REFLESH);
        mPullDownViewHandler.sendEmptyMessage(LOADDATA);
    }

    private void showData() {
        int startIndex = (0 + page_size * (page - 1));
        total = mListDB.getSize(uid, mListDB.currentTrackBOVER);
        Log.e("___listdb___", "------------page------------"
                + page + "   " + total);
        ArrayList mList_ = mListDB.getHistoryDBList(uid, mListDB.currentTrackBOVER, startIndex, page_size);
        if (mList_ != null && mList_.size() != 0) {
            mList.addAll(mList_);
            updateList();
        }
        mPullDownViewHandler.sendEmptyMessage(DATAUPDATEOVER);
    }


    private void getList() {
        mhashmap = new HashMap<String, String>();
        mhashmap.put("uid", uid);
        mhashmap.put("token", token);
        mhashmap.put("access_token", access_token);
        mhashmap.put("page", 1 + "");
        mhashmap.put("page_size", 60 + "");
        Log.e("TESTLOG", "------------mhashmap------------" + mhashmap);
        mNewList = new ArrayList<>();
        new RecordListBusiness(mHomeActivity, mNewList, mhashmap,
                new GetRecordListCallback() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void afterDataGet(HashMap<String, Object> dataMap) {
                        if (dataMap != null) {
                            String status = (String) dataMap.get("status");
                            if (status.equals("200")) {
                                total = (Integer) dataMap.get("count");
                                mDistanceCountInfo = (DistanceCountInfo) dataMap
                                        .get("mDistanceCountInfo");
                                if (mDistanceCountInfo != null) {
                                    totalDistances = mDistanceCountInfo.getTotalDistances();
                                }
                                updateDB();
                                mPullDownViewHandler.sendEmptyMessage(REFLESHRESUME);
                            }
                        }
                        CommonUtils.getInstance().setClearCacheBackDate(
                                mhashmap, dataMap);
                    }
                });
    }

    private void updateDB() {
        if (mNewList != null && mNewList.size() > 0) {
            for (int i = 0; i < mNewList.size(); i++) {
                RecordInfo mRecordInfo = mNewList.get(i);
                addDB(mRecordInfo);
            }
        }
    }

    public void addDB(RecordInfo mRecordInfo) {
        if (!mListDB.hasrunStartTimeTampInfo(mRecordInfo.getUid(), mRecordInfo.getRunStartTimeTamp())) {
            mListDB.addTableInfo(mRecordInfo);
        } else {
            mListDB.updateTableInfo(mRecordInfo);
        }
    }

    private void delRecordFromDB(String runStartTimeTamp) {
        mListDB.deleteTableInfo(runStartTimeTamp);
    }

    /**
     */
    public void showDialog(final String record_id) {
        final MyDialog selectDialog = new MyDialog(this, R.style.dialog,
                R.layout.dialog_myexit);// 创建Dialog并设置样式主题
        selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        selectDialog.show();
        TextView text_off = (TextView) selectDialog
                .findViewById(R.id.myexit_text_off);// 取消
        final TextView text_sure = (TextView) selectDialog
                .findViewById(R.id.myexit_text_sure);// 确定
        TextView myexit_text_title = (TextView) selectDialog
                .findViewById(R.id.myexit_text_title);
        myexit_text_title.setText("是否确定删除该条记录？删除后无法恢复。");
        text_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
            }
        });
        text_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
                if (TextUtils.isEmpty(record_id)) {
                    delRecordFromDB(mList.get(del_index).getRunStartTimeTamp());
                    mList.remove(del_index);
                    updateList();
                    mPullDownViewHandler.sendEmptyMessage(REFLESHRESUME);
                } else {
                    del(record_id);
                }
            }
        });
    }

    protected void del(String record_id) {
        showDilag();
        mhashmap = new HashMap<String, String>();
        mhashmap.put("uid", uid);
        mhashmap.put("token", token);
        mhashmap.put("access_token", access_token);
        mhashmap.put("record_id", record_id + "");
        new RecordDelBusiness(this, mhashmap, new RecordDelBusiness.GetRecordDelCallback() {
            @Override
            public void afterDataGet(HashMap<String, Object> dataMap) {
                if (dataMap != null) {
                    String status = (String) dataMap.get("status");
                    String msg = (String) dataMap.get("msg");
                    if (status.equals("200")) {
                        CommonUtils.getInstance().initToast(msg);
                        if (mList != null && mList.size() > 0 && del_index < mList.size()) {
                            delRecordFromDB(mList.get(del_index).getRunStartTimeTamp());
                            mList.remove(del_index);
                            updateList();
                            mPullDownViewHandler.sendEmptyMessage(REFLESHRESUME);
                            mPullDownViewHandler.sendEmptyMessage(LOADDATA);
                        }
                    } else {
                        CommonUtils.getInstance().initToast(msg);
                    }
                } else {
                    CommonUtils.getInstance().initToast(
                            getString(R.string.net_tishi));
                }
                CommonUtils.getInstance().setOnDismissDialog(mDialog);
                CommonUtils.getInstance().setClearCacheBackDate(
                        mhashmap, dataMap);
            }
        });

    }


    private void updateList() {
        if (mList != null && mList.size() != 0) {
        } else {
            mList = new ArrayList<RecordInfo>();
        }
        if (adapter == null) {
            adapter = new RecordListAdapter(this, mList, uid, mRecordCalenderDB);
            adapter.setTotalDistances(totalDistances);
            adapter.setmDistanceCountInfo(mDistanceCountInfo);
            mListView.setAdapter(adapter);
        } else {
            adapter.setmDistanceCountInfo(mDistanceCountInfo);
            adapter.setTotalDistances(totalDistances);
            adapter.setList(mList);
            adapter.notifyDataSetChanged();
        }
        addNotDateImg();
    }

    /**
     * 没有数据时加载默认图
     */
    private void addNotDateImg() {
        LinearLayout not_date = (LinearLayout) findViewById(R.id.not_date);
        if (adapter == null || (adapter != null && adapter.getCount() == 0)) {
            not_date.setVisibility(View.VISIBLE);
        } else {
            not_date.setVisibility(View.GONE);
        }
    }

    /**
     * 当返回数据小于总数时，不让显示“加载更多”
     */
    private void nideBottom() {
        if (!checkDataLoadStatus()) {
            mPullDownView.onLoadMoreComplete(
                    getResources().getString(R.string.seen_more), "");// 这里表示加载更多处理完成后把下面的加载更多界面（隐藏或者设置字样更多）
            mPullDownView.setMore(true);// 这里设置true表示还有更多加载，设置为false底部将不显示更多
        } else {
            mPullDownView.onLoadMoreComplete(getString(R.string.notmore_info),
                    "over");
            mPullDownView.setMore(true);
        }
    }

    /**
     * 判断数据是否加载完
     *
     * @return true为加载完
     */
    private boolean checkDataLoadStatus() {
        boolean loadStatus = true;
        if (adapter != null && total + 1 > adapter.getCount()) {
            loadStatus = false;
        }
        return loadStatus;
    }

    /**
     * 数据加载完后，判断底部“加载更多”显示状态
     */
    private final int DATAUPDATEOVER = 0;

    /**
     * 下拉刷新
     */
    private final int REFLESH = 1;
    /**
     * 加载更多
     */
    private final int LOADMORE = 2;
    private final int LOADDATA = 3;
    private final int LOADCalender = 5;
    private final int REFLESHRESUME = 6;
    @SuppressLint("HandlerLeak")
    Handler mPullDownViewHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DATAUPDATEOVER:
                    mPullDownView.onRefreshComplete();
                    Constants.getInstance().refreshOrLoadMoreLoading = false;
                    nideBottom();
                    break;
                case REFLESH:
                    if (!Constants.getInstance().refreshOrLoadMoreLoading) {
                        Constants.getInstance().refreshOrLoadMoreLoading = true;
                        init();
                        showData();
                    }
                    break;
                case REFLESHRESUME:
                    // 返回页面自动静默刷新数据
                    if (!Constants.getInstance().refreshOrLoadMoreLoading) {
                        Constants.getInstance().refreshOrLoadMoreLoading = true;
                        initResume();
                        showData();
                    }
                    break;

                case LOADDATA:
                    getList();
                    break;
                case LOADMORE:
                    System.err.println("LOADMORELOADMORELOADMORELOADMORELOADMORE");
                    Log.e("___listdb___", "------------LOADMORE------------"
                            + total);
                    if (!Constants.getInstance().refreshOrLoadMoreLoading) {
                        Constants.getInstance().refreshOrLoadMoreLoading = true;
                        if (total + 1 > mList.size()) {
                            page++;
                            showData();
                        } else {
                            mPullDownViewHandler.sendEmptyMessage(DATAUPDATEOVER);
                        }
                    }
                    break;
                case LOADCalender:
                    if (mRecordCalendarUtils == null) {
                        mRecordCalendarUtils = new RecordCalendarUtils(context, mRecordCalenderDB, uid, token, access_token);
                    }
                    mRecordCalendarUtils.getAllCadenrdats();
                    break;
            }
        }
    };
    RecordCalenderDBOpenHelper mRecordCalenderDB;
    RecordCalendarUtils mRecordCalendarUtils;
    private Handler mHandler = new Handler();

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            public void run() {
                mPullDownViewHandler.sendEmptyMessage(REFLESH);
            }
        }, 1500);
    }

    @Override
    public void onLoadMore() {
        System.err.println("onLoadMoreonLoadMoreonLoadMoreonLoadMore");
        mHandler.postDelayed(new Runnable() {
            public void run() {
                mPullDownViewHandler.sendEmptyMessage(LOADMORE);
            }
        }, 1500);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.getInstance().addTrackStatus) {
            mPullDownViewHandler.sendEmptyMessage(LOADCalender);
            mPullDownViewHandler.sendEmptyMessage(REFLESHRESUME);
            mPullDownViewHandler.sendEmptyMessage(LOADDATA);
            Constants.getInstance().addTrackStatus = false;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mListDB != null) {
            mListDB.close();
        }
        if (mRecordCalenderDB != null) {
            mRecordCalenderDB.close();
        }

    }

    /**
     * 退出监听
     */
    public void onBackPressed() {
        Intent intents = new Intent();
        intents.setAction(getString(R.string.action_home));
        intents.putExtra("type", getString(R.string.action_home_type_gotohome));
        sendBroadcast(intents);
    }

}
