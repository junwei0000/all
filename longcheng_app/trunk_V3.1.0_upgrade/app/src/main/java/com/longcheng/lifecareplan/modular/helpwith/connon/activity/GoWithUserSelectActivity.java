package com.longcheng.lifecareplan.modular.helpwith.connon.activity;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.adapter.ZuDuiSelectAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.SelectItemBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 组队选择邀请用户
 */
public class GoWithUserSelectActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_bottom)
    TextView tvBottom;
    @BindView(R.id.date_listview)
    PullToRefreshListView dateListview;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.layout_zudui)
    LinearLayout layout_zudui;


    String keyword;
    int surplus_number;
    int selectnum;
    String receive_user_id = "";
    String knp_group_room_id;
    int page;
    int page_size = 20;

    @Override
    public void onClick(View v) {
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_search:
                keyword = etPhone.getText().toString();
                if (!TextUtils.isEmpty(keyword)) {
                    getOrderTeamList(1);
                }
                break;
            case R.id.tv_bottom:
                if (selectnum > 0) {
                    receive_user_id = "";
                    if (mAllList != null && mAllList.size() > 0) {
                        for (SelectItemBean mSelectItemBean : mAllList) {
                            int showSelect = mSelectItemBean.getShowSelect();
                            if (showSelect == 1) {
                                if (TextUtils.isEmpty(receive_user_id)) {
                                    receive_user_id = mSelectItemBean.getUser_id();
                                } else {
                                    receive_user_id = receive_user_id + "," + mSelectItemBean.getUser_id();
                                }
                            }
                        }
                    }
                    launchInvitation();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pion_user_zudui_select;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("选择互祝组成员");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        tvBottom.setOnClickListener(this);
        notDateImg.setBackgroundResource(R.mipmap.zyb_search_bg);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etPhone, 11);
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPhone != null) {
                    keyword = etPhone.getText().toString();
                    if (TextUtils.isEmpty(keyword)) {
                        wuaiRecordUserList();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dateListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAllList != null && mAllList.size() > 0 && position <= mAllList.size()) {
                    ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                    if (mAllList.get(position - 1).getShowSelect() == 0) {
                        Log.e("onItemClick", "" + selectnum);
                        if (selectnum < surplus_number) {
                            selectnum++;
                            mAllList.get(position - 1).setShowSelect(1);
                        } else
                            ToastUtils.showToast("最多可邀请" + surplus_number + "个组队成员");
                    } else {
                        mAllList.get(position - 1).setShowSelect(0);
                        selectnum--;
                    }
                    mZFBSelectAdapter.refreshListView(mAllList);
                    if (selectnum > 0) {
                        tvBottom.setBackgroundColor(getResources().getColor(R.color.red));
                    } else {
                        tvBottom.setBackgroundColor(getResources().getColor(R.color.gray));
                    }

                }
            }
        });
        dateListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                getOrderTeamList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                getOrderTeamList(page + 1);
            }
        });
    }


    @Override
    public void initDataAfter() {
        knp_group_room_id = getIntent().getStringExtra("knp_group_room_id");
        wuaiRecordUserList();
    }

    ZuDuiSelectAdapter mZFBSelectAdapter;
    List<SelectItemBean> mAllList = new ArrayList<>();
    List<SelectItemBean> record;

    public void wuaiRecordUserList() {
        Log.e("ResponseBody", "knp_group_room_idknp_group_room_id===" + knp_group_room_id);
        Observable<PionDaiFuDataBean> observable = Api.getInstance().service.wuaiRecordUserList(
                UserUtils.getUserId(mContext), knp_group_room_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PionDaiFuDataBean>() {
                    @Override
                    public void accept(PionDaiFuDataBean responseBean) throws Exception {
                        ListUtils.getInstance().RefreshCompleteL(dateListview);
                        String status_ = responseBean.getStatus();
                        if (status_.equals("400")) {
                            ToastUtils.showToast(responseBean.getMsg());
                        } else if (status_.equals("200")) {
                            PionDaiFuAfterBean data = responseBean.getData();
                            record = data.getRecord();
                            surplus_number = data.getNum();

                            layout_zudui.setVisibility(View.VISIBLE);
                            mAllList.clear();
                            mAllList.addAll(record);
                            ScrowUtil.listViewNotConfig(dateListview);
                            mZFBSelectAdapter = null;
                            selectnum = 0;
                            receive_user_id = "";

                            tvBottom.setBackgroundColor(getResources().getColor(R.color.gray));
                            mZFBSelectAdapter = new ZuDuiSelectAdapter(mActivity, record);
                            dateListview.setAdapter(mZFBSelectAdapter);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    public void getOrderTeamList(int page_) {
        Observable<PionDaiFuDataBean> observable = Api.getInstance().service.wuaiInviteUserInfo(
                UserUtils.getUserId(mContext), keyword, knp_group_room_id, page_, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PionDaiFuDataBean>() {
                    @Override
                    public void accept(PionDaiFuDataBean responseBean) throws Exception {
                        ListUtils.getInstance().RefreshCompleteL(dateListview);
                        String status_ = responseBean.getStatus();
                        if (status_.equals("400")) {
                            ToastUtils.showToast(responseBean.getMsg());
                        } else if (status_.equals("200")) {
                            PionDaiFuAfterBean data = responseBean.getData();
                            surplus_number = data.getNum();
                            List<SelectItemBean> mList = data.getUsers();
                            if (TextUtils.isEmpty(keyword)) {
                                mList = record;
                                layout_zudui.setVisibility(View.VISIBLE);
                            } else {
                                layout_zudui.setVisibility(View.GONE);
                            }
                            int size = mList == null ? 0 : mList.size();
                            if (page_ == 1) {
                                mAllList.clear();
                                mZFBSelectAdapter = null;

                                selectnum = 0;
                                receive_user_id = "";
                                tvBottom.setBackgroundColor(getResources().getColor(R.color.gray));
                            }
                            if (size > 0) {
                                mAllList.addAll(mList);
                            }
                            if (mZFBSelectAdapter == null) {
                                mZFBSelectAdapter = new ZuDuiSelectAdapter(mActivity, mList);
                                dateListview.setAdapter(mZFBSelectAdapter);
                            } else {
                                mZFBSelectAdapter.reloadListView(mList, false);
                            }
                            page = page_;
                            checkLoadOver(size);
                            ListUtils.getInstance().setNotDateViewL(mZFBSelectAdapter, layoutNotdate);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    private void checkLoadOver(int size) {
        if (size < page_size) {
            ScrowUtil.listViewDownConfig(dateListview);
            if (size > 0 || (page > 1 && size >= 0)) {
            }
        } else {
            ScrowUtil.listViewConfigAll(dateListview);
        }
    }

    public void launchInvitation() {
        Log.e("ResponseBody", "receive_user_id==" + receive_user_id);
        Observable<ResponseBean> observable = Api.getInstance().service.wuaiUserlaunchInvitation(
                UserUtils.getUserId(mContext), receive_user_id, knp_group_room_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            doFinish();
                        }
                        ToastUtils.showToast(responseBean.getMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    boolean refreshStatus = false;

    public void showDialog() {
        if (!refreshStatus)
            LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        refreshStatus = false;
        LoadingDialogAnim.dismiss(mContext);
    }


    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
            doFinish();
        }
        return false;
    }
}
