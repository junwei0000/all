package com.longcheng.lifecareplan.modular.home.domainname.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.exchange.adapter.GoodsListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailActivity;
import com.longcheng.lifecareplan.modular.home.domainname.adapter.HotListAdapter;
import com.longcheng.lifecareplan.modular.home.domainname.adapter.SearchListAdapter;
import com.longcheng.lifecareplan.modular.home.domainname.bean.HotListDataBean;
import com.longcheng.lifecareplan.modular.home.domainname.bean.domainAfterBean;
import com.longcheng.lifecareplan.modular.home.domainname.bean.domainDataBean;
import com.longcheng.lifecareplan.modular.home.domainname.bean.domainItemBean;
import com.longcheng.lifecareplan.modular.home.domainname.flowlayout.FlowLayout;
import com.longcheng.lifecareplan.modular.home.domainname.flowlayout.TagAdapter;
import com.longcheng.lifecareplan.modular.home.domainname.flowlayout.TagFlowLayout;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.myview.MyScrollView;
import com.longcheng.lifecareplan.utils.myview.address.PopuAddressUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.MySharedPreferences;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class SearchCommueOrDaduiActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_left)
    LinearLayout layout_left;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_earaarrow)
    ImageView ivEaraarrow;
    @BindView(R.id.layout_area)
    LinearLayout layoutArea;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.flowlayout)
    TagFlowLayout flowlayout;
    @BindView(R.id.home_sv)
    PullToRefreshScrollView homeSv;
    @BindView(R.id.layout_search)
    LinearLayout layout_search;
    @BindView(R.id.layout_hot)
    LinearLayout layout_hot;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.lv_hot)
    MyListView lv_hot;
    @BindView(R.id.search_listview)
    MyListView search_listview;
    @BindView(R.id.layout_tuijian)
    LinearLayout layout_tuijian;


    PopuAddressUtils popuAddressUtils;
    int level;
    boolean isEdit = false;
    private int page = 0;
    private int page_size = 20;
    String keyword;
    private List<String> searchList = new ArrayList<>();
    //布局管理器
    private LayoutInflater mInflater;
    //流式布局的子布局
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    flowlayout.removeAllViews();
                    flowlayout.setAdapter(new TagAdapter<String>(searchList) {
                        @Override
                        public View getView(FlowLayout parent, int position, String s) {
                            View view = mInflater.inflate(R.layout.domain_search_tagitem,
                                    flowlayout, false);
                            TextView tv_cont = view.findViewById(R.id.tv_cont);
                            tv_cont.setText(s);
                            ImageView et_del = view.findViewById(R.id.et_del);
                            if (isEdit) {
                                et_del.setVisibility(View.VISIBLE);
                            } else {
                                et_del.setVisibility(View.GONE);
                            }
                            return view;
                        }
                    });
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onClick(View v) {
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.layout_left:
                doFinish();
                break;
            case R.id.tv_edit:
                if (isEdit) {
                    isEdit = false;
                    tvEdit.setText("");
                    tvEdit.setBackgroundResource(R.mipmap.mall_icon_delete);
                } else {
                    isEdit = true;
                    tvEdit.setText("完成");
                    tvEdit.setBackgroundColor(Color.TRANSPARENT);
                }
                //通知handler更新UI
                handler.sendEmptyMessageDelayed(1, 0);
                break;

            case R.id.layout_area:
                ivEaraarrow.setBackgroundResource(R.mipmap.gongshe_jiantou2_icon);
                popuAddressUtils.showAddressPopupWindow(layout_left, ivEaraarrow);
                break;

        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.domain_search;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        mInflater = LayoutInflater.from(this);
        layout_left.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
        layoutArea.setOnClickListener(this);
        popuAddressUtils = new PopuAddressUtils(mActivity, mHandler, SELECTADDRESS, level);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etSearch, 20);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etSearch != null) {
                    keyword = etSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(keyword)) {
                        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                        search_listview.setVisibility(View.GONE);
                        layout_hot.setVisibility(View.VISIBLE);
                        ScrowUtil.ScrollViewConfigDismiss(homeSv);
                        changeSearchView();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                    if (etSearch != null) {
                        keyword = etSearch.getText().toString().trim();
                        if (!TextUtils.isEmpty(keyword)) {
                            add();
                            getList(1);
                        }
                        changeSearchView();
                    }
                    return true;
                }
                return false;
            }
        });
        //流式布局tag的点击方法
        flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (isEdit) {
                    searchList.remove(position);
                    changeSearchView();
                } else {
                    ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                    keyword = searchList.get(position);
                    etSearch.setText(keyword);
                    etSearch.setSelection(keyword.length());
                    getList(1);
                }
                return true;
            }
        });
        ScrowUtil.ScrollViewConfigDismiss(homeSv);
        homeSv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refreshStatus = true;
                getList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refreshStatus = true;
                getList(page + 1);
            }
        });
    }

    private void getList(int page) {
        search_listview.setVisibility(View.VISIBLE);
        layout_hot.setVisibility(View.GONE);
        getCommuneSearch(keyword, page, page_size);
    }

    @Override
    public void initDataAfter() {
        int position = getIntent().getIntExtra("position", 0);
        level = position + DomainMenuActivity.level_commue;
        if (level == DomainMenuActivity.level_commue) {
            etSearch.setHint("请输入公社名称");
            tv_title.setText("热门公社");
        } else {
            etSearch.setHint("请输入大队名称");
            tv_title.setText("热门大队");
        }
        getHotInfo();
        initSearch();
        changeSearchView();
    }

    private void initSearch() {
        String contlist = "";
        if (level == DomainMenuActivity.level_commue) {
            contlist = MySharedPreferences.getInstance().getSearchCommue();
        } else {
            contlist = MySharedPreferences.getInstance().getSearchDaDui();
        }
        try {
            JSONArray jsonElements = new JSONArray(contlist);
            if (jsonElements != null && jsonElements.length() > 0) {
                for (int i = 0; i < jsonElements.length(); i++) {
                    searchList.add(jsonElements.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void changeSearchView() {
        if (searchList != null && searchList.size() > 0) {
            layout_search.setVisibility(View.VISIBLE);
        } else {
            layout_search.setVisibility(View.GONE);
            isEdit = false;
            tvEdit.setText("");
            tvEdit.setBackgroundResource(R.mipmap.mall_icon_delete);
        }
        edit();
    }

    private void add() {
        boolean have = false;
        for (String cont : searchList) {
            if (cont.equals(keyword)) {
                have = true;
            }
        }
        if (!have) {
            searchList.add(0, keyword);
        }
        if (searchList.size() > 8) {
            searchList.remove(searchList.size() - 1);
        }
    }

    /**
     * 最新的显示在前面
     */
    private void edit() {
        JSONArray jsonElements = new JSONArray();
        if (searchList != null && searchList.size() > 0) {
            for (int i = 0; i < searchList.size(); i++) {
                jsonElements.put(searchList.get(i));
            }
        }
        if (level == DomainMenuActivity.level_commue) {
            MySharedPreferences.getInstance().setSearchCommue(jsonElements.toString());
        } else {
            MySharedPreferences.getInstance().setSearchDaDui(jsonElements.toString());
        }
        //通知handler更新UI
        handler.sendEmptyMessageDelayed(1, 0);
    }

    private final int SELECTADDRESS = 2;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    keyword = (String) msg.obj;
                    add();
                    edit();
                    break;
                case SELECTADDRESS:
                    Bundle bundle = msg.getData();
                    keyword = bundle.getString("short_name");
                    etSearch.setText(keyword);
                    etSearch.setSelection(keyword.length());
                    add();
                    getList(1);
                    break;
            }
        }
    };
    boolean refreshStatus = false;

    public void showDialog() {
        if (!refreshStatus)
            LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        refreshStatus = false;
        LoadingDialogAnim.dismiss(mContext);
    }

    public void getHotInfo() {
        showDialog();
        Observable<HotListDataBean> observable = Api.getInstance().service.gethotCommuList(UserUtils.getUserId(mContext), level, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HotListDataBean>() {
                    @Override
                    public void accept(HotListDataBean responseBean) throws Exception {
                        dismissDialog();
                        String status_ = responseBean.getStatus();
                        if (status_.equals("200")) {
                            ArrayList<HotListDataBean.HotItemInfo> list = responseBean.getData();
                            HotListAdapter hotListAdapter = new HotListAdapter(mActivity, list, level, mHandler);
                            lv_hot.setAdapter(hotListAdapter);
                            if (list != null && list.size() > 0) {
                                layout_tuijian.setVisibility(View.VISIBLE);
                            } else {
                                layout_tuijian.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        Error();
                    }
                });

    }

    SearchListAdapter hotListAdapter;

    public void getCommuneSearch(String keyword, int backPage, int page_size) {
        showDialog();
        Observable<HotListDataBean> observable = Api.getInstance().service.getSearchCommuList(
                UserUtils.getUserId(mContext), keyword, level, backPage, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HotListDataBean>() {
                    @Override
                    public void accept(HotListDataBean responseBean) throws Exception {
                        dismissDialog();
                        ListUtils.getInstance().RefreshCompleteS(homeSv);
                        String status_ = responseBean.getStatus();
                        if (status_.equals("200")) {
                            ArrayList<HotListDataBean.HotItemInfo> list = responseBean.getData();
                            int size = list == null ? 0 : list.size();
                            if (backPage == 1) {
                                hotListAdapter = null;
                            }
                            if (size > 0) {
                            }
                            if (hotListAdapter == null) {
                                hotListAdapter = new SearchListAdapter(mActivity, list, level, mHandler);
                                search_listview.setAdapter(hotListAdapter);
                            } else {
                                hotListAdapter.reloadListView(list, false);
                            }
                            page = backPage;
                            checkLoadOver(size);

                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListUtils.getInstance().RefreshCompleteS(homeSv);
                    }
                });

    }

    private void checkLoadOver(int size) {
        if (size < page_size) {
            ScrowUtil.ScrollViewDownConfig(homeSv);
        } else {
            ScrowUtil.ScrollViewConfigAll(homeSv);
        }
    }

    public void Error() {
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
