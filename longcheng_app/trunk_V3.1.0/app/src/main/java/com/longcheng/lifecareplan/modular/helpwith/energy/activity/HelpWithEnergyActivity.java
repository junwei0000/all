package com.longcheng.lifecareplan.modular.helpwith.energy.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.helpwith.energy.adapter.ActionSelectAdapter;
import com.longcheng.lifecareplan.modular.helpwith.energy.adapter.EngryListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.energy.adapter.SelectAdapter;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.ActionItemBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.ActionListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpEnergyAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpEnergyListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpItemBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 生命能量互祝
 */
public class HelpWithEnergyActivity extends BaseListActivity<EnergyContract.View, EnergyPresenterImp<EnergyContract.View>> implements EnergyContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.help_et_search)
    SupplierEditText helpEtSearch;
    @BindView(R.id.help_tv_time)
    TextView helpTvTime;
    @BindView(R.id.help_layout_time)
    LinearLayout helpLayoutTime;
    @BindView(R.id.help_tv_progress)
    TextView helpTvProgress;
    @BindView(R.id.help_layout_progress)
    LinearLayout helpLayoutProgress;
    @BindView(R.id.help_tv_status)
    TextView helpTvStatus;
    @BindView(R.id.help_layout_status)
    LinearLayout helpLayoutStatus;
    @BindView(R.id.help_tv_select)
    TextView helpTvSelect;
    @BindView(R.id.help_layout_select)
    LinearLayout helpLayoutSelect;
    @BindView(R.id.help_listview)
    PullToRefreshListView helpListview;


    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.help_iv_timearrow)
    ImageView helpIvTimearrow;
    @BindView(R.id.help_iv_progressarrow)
    ImageView helpIvProgressarrow;
    @BindView(R.id.help_iv_statusarrow)
    ImageView helpIvStatusarrow;
    @BindView(R.id.layout_select)
    LinearLayout layout_select;
    private String user_id;
    private int page = 0;
    private int pageSize = 20;

    /**
     * 时间    2:降序 ；1：升序
     */
    private int id = 2;
    /**
     * h_user_id 只我的恩人我的奉献页面跳转使用，输入框搜索时h_user_id至为0
     */
    private String Search = "", h_user_id = "0";
    /**
     * 进度 1-降序 2-升序
     */
    private int progress;

    private boolean timeSelctStatus = true;
    /**
     * 状态 0-未完成 2-已完成
     */
    private int status = 0;
    /**
     * 0-默认 1-祝福我 2-我祝福 3-未祝福
     */
    private int help_status;
    private int goods_id = 0;
    private String count;
    List<HelpItemBean> helpAllList = new ArrayList<>();
    List<ActionItemBean> actionsAllList = new ArrayList<>();
    private String skiptype;
    private TextView tv_coming;

    @Override
    public void onClick(View v) {
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.help_layout_time:
                if (timeSelctStatus) {//选中时间时的降序升序切换
                    if (id == 2) {//降序-->升序
                        id = 1;
                        helpIvTimearrow.setBackgroundResource(R.mipmap.ic_arrow_down_red);
                    } else if (id == 1) {//升序-->降序
                        id = 2;
                        helpIvTimearrow.setBackgroundResource(R.mipmap.ic_arrow_up_red);
                    }
                } else {
                    //未选中时选中
                    id = 1;
                    helpIvTimearrow.setBackgroundResource(R.mipmap.ic_arrow_down_red);
                    helpTvTime.setTextColor(getResources().getColor(R.color.red));

                    helpTvProgress.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
                    helpIvProgressarrow.setBackgroundResource(R.mipmap.ic_arrow_gray);
                }
                timeSelctStatus = true;
                getList(1);
                break;
            case R.id.help_layout_progress:
                if (!timeSelctStatus) {
                    if (progress == 2) {//升序-->降序
                        progress = 1;
                        helpIvProgressarrow.setBackgroundResource(R.mipmap.ic_arrow_down_red);
                    } else if (progress == 1) {//降序-->升序
                        progress = 2;
                        helpIvProgressarrow.setBackgroundResource(R.mipmap.ic_arrow_up_red);
                    }
                } else {
                    progress = 1;//1-降序 2-升序
                    helpTvProgress.setTextColor(getResources().getColor(R.color.red));
                    helpIvProgressarrow.setBackgroundResource(R.mipmap.ic_arrow_down_red);

                    helpIvTimearrow.setBackgroundResource(R.mipmap.ic_arrow_gray);
                    helpTvTime.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
                }
                timeSelctStatus = false;
                getList(1);
                break;
            case R.id.help_layout_status:
                helpIvStatusarrow.setBackgroundResource(R.mipmap.mutuallist_arrow_redtop);
                showStatusPopupWindow(layout_select);
                break;
            case R.id.help_layout_select:
                showPopupWindow();
                notOverHelpIndex = help_status;
                notOverActionIndex = overActionIndex;
                setGVHelpLove();
                setGVHelpAction();
                break;

        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.helpwith_engry;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        notDateCont.setText("找不到搜索的内容噢~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.not_searched_img);
        setOrChangeTranslucentColor(toolbar, null);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(helpEtSearch, 40);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        helpLayoutTime.setOnClickListener(this);
        helpLayoutProgress.setOnClickListener(this);
        helpLayoutStatus.setOnClickListener(this);
        helpLayoutSelect.setOnClickListener(this);
        helpListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        helpListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (helpAllList != null && helpAllList.size() > 0 && (position - 1) < helpAllList.size()) {
                    ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("msg_id", helpAllList.get(position - 1).getId());
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                }
            }
        });
        helpEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                    ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                    Search = helpEtSearch.getText().toString();
                    if (TextUtils.isEmpty(Search)) {
                        return true;
                    }
                    h_user_id = "0";
                    getList(1);
                    return true;
                }
                return false;
            }
        });
        helpEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(user_id) && helpEtSearch != null && TextUtils.isEmpty(helpEtSearch.getText())) {
                    ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                    Search = helpEtSearch.getText().toString();
                    h_user_id = "0";
                    getList(1);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("Observable", "onNewINtent执行了");
        setIntent(intent);
        getData(intent);
    }

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        getData(intent);
    }

    private void getData(Intent intent) {
        skiptype = intent.getStringExtra("skiptype");
        Search = intent.getStringExtra("name");
        h_user_id = intent.getStringExtra("h_user_id");
        if (!TextUtils.isEmpty(Search)) {
            helpEtSearch.setText(Search);
            helpEtSearch.setSelection(Search.length());
            status = 0;
            helpTvStatus.setText("进行中");
        } else {
            Search = "";
            helpEtSearch.setText(Search);
        }
        user_id = UserUtils.getUserId(mContext);
        Log.e("Observable", "user_id=" + user_id + " ;id= " + id);
        mPresent.setActionList(user_id);
        getList(1);
    }

    private void getList(int page) {
        int timexu = 0;
        int progressxu = 0;
        if (timeSelctStatus) {
            timexu = id;
        } else {
            progressxu = progress;
        }
        user_id = UserUtils.getUserId(mContext);
        mPresent.setListViewData(user_id,
                timexu, Search, h_user_id, progressxu, status, help_status, goods_id, page, pageSize);
    }


    @Override
    protected EnergyPresenterImp<EnergyContract.View> createPresent() {
        return new EnergyPresenterImp<>(mContext, this);
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
    public void ListSuccess(HelpEnergyListDataBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            HelpEnergyAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                count = mEnergyAfterBean.getCount();
                if (status == 0) {
                    helpEtSearch.setHint("共有" + count + "个互祝进行中");
                } else {
                    helpEtSearch.setHint("共有" + count + "个互祝已完成");
                }
                List<HelpItemBean> helpList = mEnergyAfterBean.getHelp_msg();
                int size = helpList == null ? 0 : helpList.size();
                if (backPage == 1) {
                    helpAllList.clear();
                    mHomeHotPushAdapter = null;
                    showNoMoreData(false, helpListview.getRefreshableView());
                }
                if (size > 0) {
                    helpAllList.addAll(helpList);
                }
                if (mHomeHotPushAdapter == null) {
                    mHomeHotPushAdapter = new EngryListAdapter(mContext, helpList);
                    helpListview.setAdapter(mHomeHotPushAdapter);
                } else {
                    mHomeHotPushAdapter.reloadListView(helpList, false);
                }
                page = backPage;
                checkLoadOver(size);
            }
        }
        ListUtils.getInstance().setNotDateViewL(mHomeHotPushAdapter, layoutNotdate);
    }

    private void checkLoadOver(int size) {
        Log.e("checkLoadOver", "" + mHomeHotPushAdapter.getCount() + "  " + page * pageSize);
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
    public void ActionSuccess(ActionListDataBean responseBean) {
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            List<ActionItemBean> actionsList = responseBean.getData();
            if (actionsList != null && actionsList.size() > 0) {
                actionsAllList.add(new ActionItemBean("全部", 0));
                actionsAllList.addAll(actionsList);
            }
        }
    }

    @Override
    public void ListError() {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
        ListUtils.getInstance().setNotDateViewL(mHomeHotPushAdapter, layoutNotdate);
    }

    EngryListAdapter mHomeHotPushAdapter;


    //******************************************************************
    PopupWindow window;

    /**
     * 控件下方弹出窗口
     */
    private void showStatusPopupWindow(View view1) {
        //自定义布局，显示内容
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_helpwith_status, null);
        LinearLayout layout_bottom = view.findViewById(R.id.layout_bottom);
        layout_bottom.getBackground().setAlpha(80);
        tv_coming = view.findViewById(R.id.tv_coming);
        TextView tv_over = view.findViewById(R.id.tv_over);
        ImageView iv_coming = view.findViewById(R.id.iv_coming);
        ImageView iv_over = view.findViewById(R.id.iv_over);
        tv_coming.setTextColor(getResources().getColor(R.color.text_contents_color));
        iv_coming.setVisibility(View.INVISIBLE);
        tv_over.setTextColor(getResources().getColor(R.color.text_contents_color));
        iv_over.setVisibility(View.INVISIBLE);
        iv_over.setBackgroundResource(R.mipmap.select_img_red);
        iv_coming.setBackgroundResource(R.mipmap.select_img_red);
        if (status == 0) {
            tv_coming.setTextColor(getResources().getColor(R.color.red));
            iv_coming.setVisibility(View.VISIBLE);
        } else {
            tv_over.setTextColor(getResources().getColor(R.color.red));
            iv_over.setVisibility(View.VISIBLE);
        }
        layout_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        tv_coming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                status = 0;
                helpTvStatus.setText(tv_coming.getText());
                getList(1);
            }
        });
        tv_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                status = 2;
                helpTvStatus.setText(tv_over.getText());
                getList(1);
            }
        });
        window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        window.setTouchable(true);
        window.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                //这里如果返回true的话，touch事件将被拦截
                //拦截后 PoppWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        //（注意一下！！）如果不设置popupWindow的背景，无论是点击外部区域还是Back键都无法弹框
        window.setBackgroundDrawable(new BitmapDrawable());
        showAsDropDown(view1, 0, 0);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                helpIvStatusarrow.setBackgroundResource(R.mipmap.mutuallist_arrow_greybottom);
            }
        });
    }

    /**
     * 华为手机7.0以上华为7.0上popwindow位置显示错乱，飘
     *
     * @param anchor
     * @param xoff
     * @param yoff
     */
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (window != null && window.isShowing()) {
            window.dismiss();
            return;
        } else {
            if (Build.VERSION.SDK_INT >= 24) {
                Rect visibleFrame = new Rect();
                anchor.getGlobalVisibleRect(visibleFrame);
                int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                window.setHeight(height);
                window.showAsDropDown(anchor, xoff, yoff);
            } else {
                window.showAsDropDown(anchor, xoff, yoff);
            }
        }
    }

    MyDialog selectDialog;
    MyGridView gv_help;
    MyGridView gv_action;

    private void showPopupWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_helpwith_select);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.RIGHT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
            selectDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效

            gv_help = selectDialog.findViewById(R.id.gv_help);
            gv_action = selectDialog.findViewById(R.id.gv_action);
            TextView btn_reset = selectDialog.findViewById(R.id.btn_reset);
            TextView btn_finish = selectDialog.findViewById(R.id.btn_finish);
            setGVHelpLove();
            setGVHelpAction();
            btn_reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notOverHelpIndex = 0;
                    notOverActionIndex = 0;
                    setGVHelpLove();
                    setGVHelpAction();
                }
            });
            btn_finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    help_status = notOverHelpIndex;
                    setGVHelpLove();
                    overActionIndex = notOverActionIndex;
                    if (actionsAllList != null && actionsAllList.size() > 0) {
                        goods_id = actionsAllList.get(overActionIndex).getGoods_id();
                    }
                    setGVHelpAction();
                    getList(1);
                    selectDialog.dismiss();
                }
            });
        } else {
            selectDialog.show();
        }
    }

    int notOverHelpIndex;//未点击完成的选择
    SelectAdapter mhelpAdapter;
    ArrayList<String> mhelpList;

    int overActionIndex;
    int notOverActionIndex;
    ActionSelectAdapter mActionAdapter;

    private void setGVHelpLove() {
        if (mhelpAdapter == null) {
            mhelpList = new ArrayList<>();
            mhelpList.add("全部");
            mhelpList.add("祝福我");
            mhelpList.add("我祝福");
            mhelpList.add("未祝福");
            mhelpAdapter = new SelectAdapter(mContext, mhelpList, notOverHelpIndex, "");
            gv_help.setAdapter(mhelpAdapter);
            gv_help.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    notOverHelpIndex = position;
                    mhelpAdapter.setSelectMoneyIndex(notOverHelpIndex);
                    mhelpAdapter.notifyDataSetChanged();
                }
            });
        } else {
            mhelpAdapter.setSelectMoneyIndex(notOverHelpIndex);
            mhelpAdapter.notifyDataSetChanged();
        }
    }


    private void setGVHelpAction() {
        if (mActionAdapter == null) {
            mActionAdapter = new ActionSelectAdapter(mContext, actionsAllList, notOverActionIndex, "");
            gv_action.setAdapter(mActionAdapter);
            gv_action.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    notOverActionIndex = position;
                    mActionAdapter.setSelectMoneyIndex(notOverActionIndex);
                    mActionAdapter.notifyDataSetChanged();
                }
            });
        } else {
            mActionAdapter.setSelectMoneyIndex(notOverActionIndex);
            mActionAdapter.notifyDataSetChanged();
        }
    }
    //******************************************************************

    private void back() {
        if (!TextUtils.isEmpty(skiptype) && (skiptype.equals("myDeGra")
                || skiptype.equals("HomeFragment")
                || skiptype.equals("Order")
                || skiptype.equals("lifedetalitohelp")
                || skiptype.equals("redbao")
                || skiptype.equals("qiming")
                || skiptype.equals("Thanks"))) {

        } else {
//            Intent intents = new Intent();
//            intents.setAction(ConstantManager.MAINMENU_ACTION);
//            intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_HELPWITH);
//            LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
//            ActivityManager.getScreenManager().popAllActivityOnlyMain();
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantManager.BroadcastReceiver_ENGRYLIST_ACTION);
        registerReceiver(mReceiver, filter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", 100);
            if (errCode == WXPayEntryActivity.PAYDETAIL_SHUAXIN) {
                getList(1);
            }
        }
    };
}
