package com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.adapter.LifeStyleListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.bean.LifeStyleAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.bean.LifeStyleListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.bean.LifeStyleItemBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.activity.LifeStyleDetailActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 生活方式互祝
 */
public class LifeStyleActivity extends BaseListActivity<LifeStyleContract.View, LifeStylePresenterImp<LifeStyleContract.View>> implements LifeStyleContract.View {

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
    @BindView(R.id.help_iv_select)
    ImageView help_iv_select;
    @BindView(R.id.layout_select)
    LinearLayout layout_select;
    private String user_id;
    private int page = 0;
    private int pageSize = 20;

    /**
     * 时间    2:降序 ；1：升序
     */
    private int time_sort = 2;
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
     * 状态 1未完成 2-已完成
     */
    private int status = 1;
    /**
     * 0-默认 1-祝福我 2-我祝福 3-未祝福
     */
    private int help_status = 0;
    private String count;
    List<LifeStyleItemBean> helpAllList = new ArrayList<>();
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
                    if (time_sort == 2) {//降序-->升序
                        time_sort = 1;
                        helpIvTimearrow.setBackgroundResource(R.mipmap.ic_arrow_down_red);
                    } else if (time_sort == 1) {//升序-->降序
                        time_sort = 2;
                        helpIvTimearrow.setBackgroundResource(R.mipmap.ic_arrow_up_red);
                    }
                } else {
                    //未选中时选中
                    time_sort = 1;
                    helpIvTimearrow.setBackgroundResource(R.mipmap.ic_arrow_down_red);
                    helpTvTime.setTextColor(getResources().getColor(R.color.blue));

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
                    helpTvProgress.setTextColor(getResources().getColor(R.color.blue));
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
                help_iv_select.setBackgroundResource(R.mipmap.mutuallist_arrow_redtop);
                showSelectPopupWindow(layout_select);
                break;

        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.helpwith_lifestyle;
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
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(helpEtSearch, 40);
        setOrChangeTranslucentColor(toolbar, null);
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
                if (helpAllList != null && helpAllList.size() > 0) {
                    ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                    Intent intent = new Intent(mContext, LifeStyleDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("help_goods_id", helpAllList.get(position - 1).getHelp_goods_id());
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
                if (TextUtils.isEmpty(helpEtSearch.getText())) {
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
        Log.e("tag", "onNewINtent执行了");
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
            status = 1;
            helpTvStatus.setText("进行中");
        } else {
            Search = "";
            helpEtSearch.setText(Search);
        }
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        getList(1);
    }

    private void getList(int page) {
        int timexu = 0;
        int progressxu = 0;
        if (timeSelctStatus) {
            timexu = time_sort;
        } else {
            progressxu = progress;
        }
        mPresent.setListViewData(user_id,
                timexu, Search, h_user_id, progressxu, status, help_status, page, pageSize);
    }


    @Override
    protected LifeStylePresenterImp<LifeStyleContract.View> createPresent() {
        return new LifeStylePresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {
    }


    @Override
    public void ListSuccess(LifeStyleListDataBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            LifeStyleAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                count = mEnergyAfterBean.getCount();
                if (status == 1) {
                    helpEtSearch.setHint("共有" + count + "个互祝进行中");
                } else {
                    helpEtSearch.setHint("共有" + count + "个互祝已完成");
                }
                List<LifeStyleItemBean> helpList = mEnergyAfterBean.getHelp_goods();
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
                    mHomeHotPushAdapter = new LifeStyleListAdapter(mActivity, helpList);
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
    public void ListError() {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
        ListUtils.getInstance().setNotDateViewL(mHomeHotPushAdapter, layoutNotdate);
    }

    LifeStyleListAdapter mHomeHotPushAdapter;


    //******************************************************************
    PopupWindow window;
    PopupWindow selectwindow;

    /**
     * 控件下方弹出窗口
     */
    private void showStatusPopupWindow(View view1) {
        //自定义布局，显示内容
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_helpwith_status, null);
        LinearLayout layout_bottom = (LinearLayout) view.findViewById(R.id.layout_bottom);
        layout_bottom.getBackground().setAlpha(80);
        tv_coming = (TextView) view.findViewById(R.id.tv_coming);
        TextView tv_over = (TextView) view.findViewById(R.id.tv_over);
        ImageView iv_coming = (ImageView) view.findViewById(R.id.iv_coming);
        ImageView iv_over = (ImageView) view.findViewById(R.id.iv_over);
        tv_coming.setTextColor(getResources().getColor(R.color.text_contents_color));
        iv_coming.setVisibility(View.INVISIBLE);
        tv_over.setTextColor(getResources().getColor(R.color.text_contents_color));
        iv_over.setVisibility(View.INVISIBLE);
        if (status == 1) {
            tv_coming.setTextColor(getResources().getColor(R.color.blue));
            iv_coming.setVisibility(View.VISIBLE);
        } else {
            tv_over.setTextColor(getResources().getColor(R.color.blue));
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
                status = 1;
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
        showAsDropDown(window, view1, 0, 0);
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
    public void showAsDropDown(PopupWindow window, View anchor, int xoff, int yoff) {
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

    TextView tv_blessme;
    ImageView iv_blessme;
    TextView tv_mybless;
    ImageView iv_mybless;
    TextView tv_notbless;
    ImageView iv_notbless;

    private void showSelectPopupWindow(View view1) {
        //自定义布局，显示内容
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_helpwith_lifestyle_select, null);
        LinearLayout layout_bottom = (LinearLayout) view.findViewById(R.id.layout_bottom);
        layout_bottom.getBackground().setAlpha(80);
        tv_blessme = (TextView) view.findViewById(R.id.tv_blessme);
        iv_blessme = (ImageView) view.findViewById(R.id.iv_blessme);
        tv_mybless = (TextView) view.findViewById(R.id.tv_mybless);
        iv_mybless = (ImageView) view.findViewById(R.id.iv_mybless);
        tv_notbless = (TextView) view.findViewById(R.id.tv_notbless);
        iv_notbless = (ImageView) view.findViewById(R.id.iv_notbless);
        TextView btn_reset = (TextView) view.findViewById(R.id.btn_reset);

        setGVHelpLove();

        tv_blessme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help_status = 1;
                setGVHelpLove();
                selectwindow.dismiss();
                getList(1);
            }
        });
        tv_mybless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help_status = 2;
                setGVHelpLove();
                selectwindow.dismiss();
                getList(1);
            }
        });
        tv_notbless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help_status = 3;
                setGVHelpLove();
                selectwindow.dismiss();
                getList(1);
            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help_status = 0;
                setGVHelpLove();
                selectwindow.dismiss();
                getList(1);
            }
        });
        layout_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGVHelpLove();
                selectwindow.dismiss();
            }
        });
        selectwindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        selectwindow.setTouchable(true);
        selectwindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                //这里如果返回true的话，touch事件将被拦截
                //拦截后 PoppWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        //（注意一下！！）如果不设置popupWindow的背景，无论是点击外部区域还是Back键都无法弹框
        selectwindow.setBackgroundDrawable(new BitmapDrawable());
        showAsDropDown(selectwindow, view1, 0, 0);
        selectwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setGVHelpLove();
                help_iv_select.setBackgroundResource(R.mipmap.mutuallist_arrow_greybottom);
            }
        });
    }


    private void setGVHelpLove() {
        tv_blessme.setTextColor(getResources().getColor(R.color.text_contents_color));
        iv_blessme.setVisibility(View.INVISIBLE);
        tv_mybless.setTextColor(getResources().getColor(R.color.text_contents_color));
        iv_mybless.setVisibility(View.INVISIBLE);
        tv_notbless.setTextColor(getResources().getColor(R.color.text_contents_color));
        iv_notbless.setVisibility(View.INVISIBLE);
        helpTvSelect.setTextColor(getResources().getColor(R.color.blue));
        if (help_status == 1) {
            tv_blessme.setTextColor(getResources().getColor(R.color.blue));
            iv_blessme.setVisibility(View.VISIBLE);
            helpTvSelect.setText(tv_blessme.getText().toString());
        } else if (help_status == 2) {
            tv_mybless.setTextColor(getResources().getColor(R.color.blue));
            iv_mybless.setVisibility(View.VISIBLE);
            helpTvSelect.setText(tv_mybless.getText().toString());
        } else if (help_status == 3) {
            tv_notbless.setTextColor(getResources().getColor(R.color.blue));
            iv_notbless.setVisibility(View.VISIBLE);
            helpTvSelect.setText(tv_notbless.getText().toString());
        } else {
            if (selectwindow != null && !selectwindow.isShowing()) {
                helpTvSelect.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
            }
            helpTvSelect.setText("互祝状态");
        }
    }


    //******************************************************************

    private void back() {
        if (!TextUtils.isEmpty(skiptype) && (skiptype.equals("myDeGra") ||
                skiptype.equals("Order") || skiptype.equals("LifeApplyHelp")
                || skiptype.equals("Thanks"))) {

        } else {
            Intent intents = new Intent();
            intents.setAction(ConstantManager.MAINMENU_ACTION);
            intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_HELPWITH);
            LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
            ActivityManager.getScreenManager().popAllActivityOnlyMain();
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
