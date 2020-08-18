package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.zudui;

import android.content.Intent;
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
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter.ZuDuiTutorSelectAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBSelectDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.adapter.ZuDuiSelectAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.SelectItemBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
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
 * 组队选择创业导师
 */
public class ZYBTutorSelectActivity extends BaseActivity {


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
    String entrepreneurs_id;
    String user_name;
    String avatar;
    int page = 1;
    int page_size = 1000;
    String zhuyoubao_team_room_id;
    int selectIndex = -1;

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
                if (!TextUtils.isEmpty(entrepreneurs_id)) {
                    ZYBBindEntrepreneurs();
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
                    selectIndex = position;
                    mZFBSelectAdapter.setSelectIndex(selectIndex);
                    entrepreneurs_id = entreList.get(position).getEntrepreneurs_id();
                    user_name = entreList.get(position).getUser_name();
                    avatar = entreList.get(position).getAvatar();
                    mZFBSelectAdapter.refreshListView(entreList);
                    tvBottom.setBackgroundColor(getResources().getColor(R.color.red));
                }
            }
        });
    }


    @Override
    public void initDataAfter() {
        zhuyoubao_team_room_id = getIntent().getStringExtra("zhuyoubao_team_room_id");
        getOrderTeamList();
    }


    public void getOrderTeamList() {
        entrepreneurs_id = "";
        tvBottom.setBackgroundColor(getResources().getColor(R.color.gray));
        Observable<PionZFBSelectDataBean> observable = Api.getInstance().service.getZYBTutorList(UserUtils.getUserId(mContext), keyword, page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PionZFBSelectDataBean>() {
                    @Override
                    public void accept(PionZFBSelectDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            entreList = responseBean.getData();
                            mZFBSelectAdapter = new ZuDuiTutorSelectAdapter(mActivity, entreList);
                            mZFBSelectAdapter.setSelectIndex(selectIndex);
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

    public void ZYBBindEntrepreneurs() {
        Observable<ResponseBean> observable = Api.getInstance().service.ZYBBindEntrepreneurs(
                UserUtils.getUserId(mContext), zhuyoubao_team_room_id, entrepreneurs_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            doFinish();
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
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


    ZuDuiTutorSelectAdapter mZFBSelectAdapter;
    ArrayList<PionZFBSelectDataBean.PionZFBSBean> entreList = new ArrayList<>();


    private void setBtn(PionZFBSelectDataBean responseBean) {
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
