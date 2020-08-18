package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.activity.df;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpListDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter.ZFBSelectAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBSelectDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.adapter.ZuDuiSelectAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.SelectItemBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
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
 * 组队选择邀请导师
 */
public class ZuDuiSelectActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.et_phone)
    SupplierEditText etPhone;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_bottom)
    TextView tvBottom;
    @BindView(R.id.date_listview)
    ListView dateListview;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;


    String keyword;
    int surplus_number;
    int selectnum;
    String receive_user_id = "";

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
                    getOrderTeamList();
                }
                break;
            case R.id.tv_bottom:
                if (selectnum > 0) {
                    receive_user_id="";
                    if (entreList != null && entreList.size() > 0) {
                        for (SelectItemBean mSelectItemBean : entreList) {
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
        return R.layout.pioneer_zudui_select;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("选择创业导师");
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
                        getOrderTeamList();
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
                if (entreList != null && entreList.size() > 0) {
                    ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                    if (entreList.get(position).getShowSelect() == 0) {
                        Log.e("onItemClick",""+selectnum);
                        if (selectnum < surplus_number) {
                            selectnum++;
                            entreList.get(position).setShowSelect(1);
                        } else
                            ToastUtils.showToast("最多可邀请" + surplus_number + "个创业导师");
                    } else {
                        entreList.get(position).setShowSelect(0);
                        selectnum--;
                    }
                    mZFBSelectAdapter.refreshListView(entreList);
                    if (selectnum > 0) {
                        tvBottom.setBackgroundColor(getResources().getColor(R.color.red));
                    } else {
                        tvBottom.setBackgroundColor(getResources().getColor(R.color.gray));
                    }

                }
            }
        });
    }


    @Override
    public void initDataAfter() {
        getOrderTeamList();
    }


    public void getOrderTeamList() {
        selectnum = 0;
        receive_user_id = "";
        if (selectnum > 0) {
            tvBottom.setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            tvBottom.setBackgroundColor(getResources().getColor(R.color.gray));
        }
        Observable<PionDaiFuDataBean> observable = Api.getInstance().service.getOrderTeamList(UserUtils.getUserId(mContext), keyword, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PionDaiFuDataBean>() {
                    @Override
                    public void accept(PionDaiFuDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            PionDaiFuAfterBean data = responseBean.getData();
                            SelectItemBean room = data.getRoom();
                            surplus_number = room.getSurplus_number();
                            entreList = data.getUsers();
                            mZFBSelectAdapter = new ZuDuiSelectAdapter(mActivity, entreList);
                            dateListview.setAdapter(mZFBSelectAdapter);
                            setBtn(responseBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    public void launchInvitation() {
        Observable<ResponseBean> observable = Api.getInstance().service.launchInvitation(UserUtils.getUserId(mContext), receive_user_id, ExampleApplication.token);
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

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    ZuDuiSelectAdapter mZFBSelectAdapter;
    List<SelectItemBean> entreList = new ArrayList<>();


    private void setBtn(PionDaiFuDataBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            layoutNotdate.setVisibility(View.GONE);
        } else {
            layoutNotdate.setVisibility(View.VISIBLE);
            if (entreList != null && entreList.size() > 0) {
                entreList.clear();
                mZFBSelectAdapter.refreshListView(entreList);
            }
            tvBottom.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }


    public void PaySuccess(PayWXDataBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            doFinish();
        }
        ToastUtils.showToast(responseBean.getMsg());
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
