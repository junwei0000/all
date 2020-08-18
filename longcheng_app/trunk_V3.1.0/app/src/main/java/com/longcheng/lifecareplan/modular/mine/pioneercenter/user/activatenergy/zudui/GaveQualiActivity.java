package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.zudui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity.PionZFBContract;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity.PionZFBPresenterImp;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter.ZFBHistoryAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter.ZFBSelectAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionActiviesDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBHistoryDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBSelectDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity.cz.PionCZRecordActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 给与推荐资格
 */
public class GaveQualiActivity extends BaseActivity {


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
    @BindView(R.id.date_listview)
    MyListView dateListview;
    @BindView(R.id.layout_user)
    ScrollView layoutUser;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont_title)
    TextView notDateContTitle;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.tv_bottom)
    TextView tvBottom;
    @BindView(R.id.tv_quli)
    TextView tv_quli;


    String phone;
    String seniority_user_id;
    int topindex = -1;

    @Override
    public void onClick(View v) {
        Intent intent;
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.item_tv_phone:
                DensityUtil.getPhoneToKey(mContext, phone);
                break;
            case R.id.tv_search:
                String phone = etPhone.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    searchSeniorityUser(phone);
                }
                break;
            case R.id.tv_quli:
                intent = new Intent(mActivity, GaveQualiRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.tv_bottom:
                if (topindex != -1)
                    addSeniorityUser(seniority_user_id);
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
        return R.layout.pioneer_userzyb_gavequli;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("添加活动资格");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        tvBottom.setOnClickListener(this);
        tv_quli.setOnClickListener(this);
        notDateImg.setBackgroundResource(R.mipmap.zyb_search_bg);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etPhone, 11);
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPhone != null) {
                    String phone = etPhone.getText().toString();
                    if (TextUtils.isEmpty(phone)) {
                        topindex = -1;
                        layoutNotdate.setVisibility(View.GONE);
                        layoutUser.setVisibility(View.VISIBLE);
                        tvBottom.setBackgroundColor(getResources().getColor(R.color.gray));
                        if (mZFBSelectAdapter != null) {
                            entreList.clear();
                            mZFBSelectAdapter.refreshListView(entreList);
                        }
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
                    seniority_user_id = entreList.get(position).getUser_id();
                    topindex = position;
                    mZFBSelectAdapter.setTopindex(topindex);
                    mZFBSelectAdapter.refreshListView(entreList);
                    tvBottom.setBackgroundColor(getResources().getColor(R.color.red));
                }
            }
        });
    }


    @Override
    public void initDataAfter() {
    }


    public void searchSeniorityUser(String keyword) {
        showDialog();
        Observable<PionZFBSelectDataBean> observable = Api.getInstance().service.searchSeniorityUser(UserUtils.getUserId(mContext), keyword, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PionZFBSelectDataBean>() {
                    @Override
                    public void accept(PionZFBSelectDataBean responseBean) throws Exception {
                        dismissDialog();
                        SelectSuccess(responseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                    }
                });

    }

    public void addSeniorityUser(String seniority_user_id) {
        showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.addSeniorityUser(UserUtils.getUserId(mContext), seniority_user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        dismissDialog();
                        String status = responseBean.getStatus();
                        if (status.equals("200")) {

                        }
                        ToastUtils.showToast(responseBean.getMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                    }
                });

    }

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    ZFBSelectAdapter mZFBSelectAdapter;
    ArrayList<PionZFBSelectDataBean.PionZFBSBean> entreList = new ArrayList<>();


    public void SelectSuccess(PionZFBSelectDataBean responseBean) {
        topindex = -1;
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            entreList = responseBean.getData();
            mZFBSelectAdapter = new ZFBSelectAdapter(mActivity, entreList);
            mZFBSelectAdapter.setTopindex(topindex);
            dateListview.setAdapter(mZFBSelectAdapter);
        }
        setBtn(responseBean);
    }


    private void setBtn(PionZFBSelectDataBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            layoutNotdate.setVisibility(View.GONE);
            layoutUser.setVisibility(View.VISIBLE);
        } else {
            notDateCont.setText(responseBean.getMsg());
            layoutNotdate.setVisibility(View.VISIBLE);
            layoutUser.setVisibility(View.GONE);

            topindex = -1;
            if (entreList != null && entreList.size() > 0) {
                entreList.clear();
                mZFBSelectAdapter.setTopindex(topindex);
                mZFBSelectAdapter.refreshListView(entreList);
            }
            tvBottom.setBackgroundColor(getResources().getColor(R.color.gray));
        }
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
