package com.longcheng.lifecareplan.modular.helpwith.connon.activity;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.connon.adapter.CHelpListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.connon.adapter.CHelpSelectAllAdapter;
import com.longcheng.lifecareplan.modular.helpwith.connon.adapter.CHelpSelectJieQiAdapter;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpCreatDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpGroupRoomDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpItemBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.mine.fragment.MineFragment;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.push.jpush.message.PionPairingUtils;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 天下无癌互祝组
 */
public class CHelpListActivity extends BaseActivityMVP<CHelpContract.View, CHelpPresenterImp<CHelpContract.View>> implements CHelpContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.help_tv_all)
    TextView helpTvAll;
    @BindView(R.id.help_iv_timearrow)
    ImageView helpIvTimearrow;
    @BindView(R.id.help_layout_all)
    LinearLayout helpLayoutAll;
    @BindView(R.id.help_tv_jieqi)
    TextView helpTvJieqi;
    @BindView(R.id.help_iv_statusarrow)
    ImageView helpIvStatusarrow;
    @BindView(R.id.help_layout_jieqi)
    LinearLayout helpLayoutJieqi;
    @BindView(R.id.help_et_search)
    EditText helpEtSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.help_listview)
    PullToRefreshListView helpListview;
    @BindView(R.id.help_tv_create)
    TextView helpTvCreate;
    @BindView(R.id.help_tv_my)
    TextView helpTvMy;
    @BindView(R.id.layout_old)
    LinearLayout layout_old;


    private int page = 0;
    private int pageSize = 10;
    private String keyword = "";
    List<CHelpListDataBean.CHelpListItemBean> helpAllList = new ArrayList<>();


    List<CHelpItemBean> jieqis;
    List<CHelpItemBean> knp_team_numbers;
    String knp_team_number_id = "0";
    String jieqi_en = "all";

    /**
     * 全部弹层   节气弹层
     */
    boolean showJieQi = false;
    int allIndex = 0;
    int jieqiIndex;

    @Override
    public void onClick(View v) {
        Intent intent;
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.help_layout_all:
                showJieQi = false;
                showStatusPopupWindow(helpLayoutJieqi, helpIvTimearrow, helpTvAll);
                break;
            case R.id.help_layout_jieqi:
                showJieQi = true;
                showStatusPopupWindow(helpLayoutJieqi, helpIvStatusarrow, helpTvJieqi);
                break;
            case R.id.tv_search:
                ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                keyword = helpEtSearch.getText().toString();
                getList(1);
                break;
            case R.id.help_tv_create:
                intent = new Intent(mActivity, CHelpCreateActivityNew.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.help_tv_my:
                intent = new Intent(mActivity, MyHelpListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.layout_old:
                String hour = DatesUtils.getInstance().getNowTime("H");
                if (Integer.valueOf(hour) < 5 || Integer.valueOf(hour) > 13) {
                    ToastUtils.showToast("天下无癌互祝大厅开放时间为5:00～13:00");
                    break;
                }
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", Config.BASE_HEAD_URL + "home/knpteam/allroomlist/");
                startActivity(intent);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.connon_helplist;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("天下无癌互祝组");
        pagetopLayoutLeft.setOnClickListener(this);
        helpLayoutJieqi.setOnClickListener(this);
        helpLayoutAll.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        helpTvCreate.setOnClickListener(this);
        helpTvMy.setOnClickListener(this);
        layout_old.setOnClickListener(this);
        ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, layout_old, R.color.red);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(helpEtSearch, 40);
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
                    Intent intent = new Intent(mContext, CHelpDetailAddActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("knp_group_room_id", helpAllList.get(position - 1).getKnp_group_room_id());
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
                    keyword = helpEtSearch.getText().toString();
                    getList(1);
                    return true;
                }
                return false;
            }
        });
        helpEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (helpEtSearch != null) {
                    keyword = helpEtSearch.getText().toString();
                    if (TextUtils.isEmpty(keyword)) {
                        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                        getList(1);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void initDataAfter() {

    }

    private void getList(int page) {
        PionPairingUtils.getINSTANCE().wuaiGoWithInvite();
        PionPairingUtils.getINSTANCE().wuaiGoWithCreateTeamInfo();
        mPresent.getListIndexConfig();
        mPresent.getHelpList(keyword, knp_team_number_id, jieqi_en, page, pageSize);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList(1);
    }

    @Override
    protected CHelpPresenterImp<CHelpContract.View> createPresent() {
        return new CHelpPresenterImp<>(mContext, this);
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

    CHelpListAdapter mAdapter;

    @Override
    public void ListSuccess(CHelpListDataBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
        String status_ = responseBean.getStatus();
        if (status_.equals("421")) {
            doFinish();
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            ArrayList<CHelpListDataBean.CHelpListItemBean> helpList = responseBean.getData();
            int size = helpList == null ? 0 : helpList.size();
            if (backPage == 1) {
                helpAllList.clear();
                mAdapter = null;
            }
            if (size > 0) {
                helpAllList.addAll(helpList);
            }
            if (mAdapter == null) {
                mAdapter = new CHelpListAdapter(mActivity, helpList);
                helpListview.setAdapter(mAdapter);
            } else {
                mAdapter.reloadListView(helpList, false);
            }
            page = backPage;
            checkLoadOver(size);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.listViewDownConfig(helpListview);
            if (size > 0 || (page > 1 && size >= 0)) {
            }
        } else {
            ScrowUtil.listViewConfigAll(helpListview);
        }
    }

    @Override
    public void DataSuccess(CHelpDataBean responseBean) {
        String status_ = responseBean.getStatus();
        if (status_.equals("421")) {
            doFinish();
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            CHelpAfterBean data = responseBean.getData();
            jieqis = data.getJieqis();
            knp_team_numbers = data.getKnp_team_numbers();
            int ing_num = data.getIng_num();//进行中
            int com_num = data.getCom_num();//已完成
            helpEtSearch.setHint("已完成 " + com_num + " 个  进行中 " + ing_num + " 个");
            int isUserCertificationCommon = data.getIsUserCertificationCommon();//是否需要实名  1需要
            if (isUserCertificationCommon == 1 && !showStatus) {
                showStatus = true;
                showMyKaDialog();
            }
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    boolean showStatus = false;

    @Override
    public void DetailDataSuccess(CHelpDetailDataBean responseBean) {

    }

    @Override
    public void CreatePageDataSuccess(CHelpCreatDataBean responseBean) {

    }

    @Override
    public void GroupRoomDataSuccess(CHelpGroupRoomDataBean responseBean) {

    }

    @Override
    public void editSuccess(ResponseBean responseBean) {

    }

    @Override
    public void CreateTableSuccess(ResponseBean responseBean) {

    }

    @Override
    public void CreateRoomSuccess(EditDataBean responseBean) {

    }


    @Override
    public void ListError() {
    }


    //******************************************************************
    PopupWindow window;

    /**
     * 控件下方弹出窗口
     */
    private void showStatusPopupWindow(View view1, ImageView imageView, TextView textView) {
        //自定义布局，显示内容
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_conhelp_gv, null);
        LinearLayout layout_bottom = view.findViewById(R.id.layout_bottom);
        layout_bottom.getBackground().setAlpha(99);
        GridView gv_date = view.findViewById(R.id.gv_date);
        layout_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
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
                showJieQi = false;
                imageView.setBackgroundResource(R.mipmap.gongshe_jiantou1_icon);
                textView.setTextColor(getResources().getColor(R.color.color_333333));
            }
        });

        imageView.setBackgroundResource(R.mipmap.gongshe_jiantou2_icon);
        textView.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
        if (showJieQi) {
            CHelpSelectJieQiAdapter mActionSelectAdapter = new CHelpSelectJieQiAdapter(mContext, jieqis, jieqi_en);
            mActionSelectAdapter.setJieqi_en(jieqi_en);
            gv_date.setNumColumns(5);
            gv_date.setAdapter(mActionSelectAdapter);
        } else {
            CHelpSelectAllAdapter mCHelpSelectAllAdapter = new CHelpSelectAllAdapter(mContext, knp_team_numbers, allIndex);
            mCHelpSelectAllAdapter.setSelectIndex(allIndex);
            gv_date.setNumColumns(1);
            gv_date.setAdapter(mCHelpSelectAllAdapter);
        }
        gv_date.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (showJieQi) {
                    jieqiIndex = position;
                    if (jieqis != null && jieqis.size() > 0) {
                        jieqi_en = jieqis.get(jieqiIndex).getSolar_terms_en();
                        textView.setText(jieqis.get(jieqiIndex).getSolar_terms_name());
                    }
                } else {
                    allIndex = position;
                    if (knp_team_numbers != null && knp_team_numbers.size() > 0) {
                        knp_team_number_id = knp_team_numbers.get(allIndex).getKnp_team_number_id();
                        textView.setText(knp_team_numbers.get(allIndex).getName());
                    }
                }
                getList(1);
                window.dismiss();
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


    MyDialog myKaDialog;

    /**
     * 实名认证
     */
    public void showMyKaDialog() {
        if (myKaDialog == null) {
            myKaDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_identity);// 创建Dialog并设置样式主题
            myKaDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = myKaDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            myKaDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = myKaDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4;
            myKaDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_bg = myKaDialog.findViewById(R.id.layout_bg);
            layout_bg.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.08)));
            TextView tv_cancel = myKaDialog.findViewById(R.id.tv_cancel);
            TextView tv_jihuo = myKaDialog.findViewById(R.id.tv_jihuo);
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myKaDialog.dismiss();
                }
            });
            tv_jihuo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myKaDialog.dismiss();/**/
                    Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + MineFragment.Certification_url);
                    startActivity(intent);
                }
            });
        } else {
            myKaDialog.show();
        }
    }
}
