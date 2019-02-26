package com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.adapter.PeopleListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataListBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ExplainDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.OtherUserInfoDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleItemBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleSearchDataBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 接福人
 */
public class PeopleActivity extends BaseActivityMVP<ApplyHelpContract.View, ApplyHelpPresenterImp<ApplyHelpContract.View>> implements ApplyHelpContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.people_lv)
    ListView people_lv;
    @BindView(R.id.help_et_search)
    SupplierEditText helpEtSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.layout_search)
    LinearLayout layout_search;

    /**
     * 是否可以搜索用户 0：不允许 1：允许（大队长或者主任可以搜索本大队、公社成员）
     */
    private String searchRole;
    private String user_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_search:
                ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                String cont = helpEtSearch.getText().toString().trim();
                mPresent.getPeopleSearchList(user_id, cont);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.helpwith_apply_peoplelist;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(helpEtSearch, 20);
        pagetopLayoutLeft.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        people_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mShowList != null && mShowList.size() > 0) {
                    String allow_help = mShowList.get(position).getAllow_help();
                    //是否可以互助0：不允许 1：允许（非cho不允许）
                    if (!TextUtils.isEmpty(allow_help) && allow_help.equals("1")) {
                        Intent intent = new Intent();
                        intent.putExtra("peopleid", mShowList.get(position).getUser_id());
                        intent.putExtra("peoplename", mShowList.get(position).getUser_name());
                        intent.putExtra("avatar", mShowList.get(position).getAvatar());
                        setResult(ConstantManager.APPLYHELP_FORRESULT_PEOPLE, intent);
                        doFinish();
                    } else {
                        ToastUtils.showToast("该用户非CHO,不能发起互祝");
                    }
                }
            }
        });
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("选择接福人");
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        mPresent.getPeopleList(user_id);
    }


    @Override
    protected ApplyHelpPresenterImp<ApplyHelpContract.View> createPresent() {
        return new ApplyHelpPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    List<PeopleItemBean> mList;
    List<PeopleItemBean> mShowList = new ArrayList<>();

    @Override
    public void ActionListSuccess(ActionDataListBean responseBean) {

    }

    @Override
    public void ActionDetailSuccess(ActionDataBean responseBean) {

    }

    @Override
    public void PeopleListSuccess(PeopleDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            PeopleAfterBean mPeopleAfterBean = responseBean.getData();
            if (mPeopleAfterBean != null) {
                searchRole = mPeopleAfterBean.getSearchRole();
                if (!TextUtils.isEmpty(searchRole) && searchRole.equals("1")) {
                    layout_search.setVisibility(View.VISIBLE);
                } else {
                    layout_search.setVisibility(View.GONE);
                }
                mList = mPeopleAfterBean.getFamilyList();
                if (mList == null) {
                    mList = new ArrayList<>();
                }
                mShowList.clear();
                mShowList.addAll(mList);
                PeopleListAdapter mAdapter = new PeopleListAdapter(mContext, mList);
                people_lv.setAdapter(mAdapter);
            }
        }
    }

    @Override
    public void getOtherUserInfoSuccess(OtherUserInfoDataBean responseBean) {

    }

    @Override
    public void PeopleSearchListSuccess(PeopleSearchDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            List<PeopleItemBean> mList = responseBean.getData();
            if (mList == null || (mList != null && mList.size() == 0)) {
                ToastUtils.showToast("暂无搜索的匹配用户信息");
            } else {
                mShowList.clear();
                mShowList.addAll(mList);
                PeopleListAdapter mAdapter = new PeopleListAdapter(mContext, mList);
                people_lv.setAdapter(mAdapter);
            }
        }
    }

    @Override
    public void getNeedHelpNumberTaskSuccess(ActionDataBean responseBean) {

    }

    @Override
    public void ExplainListSuccess(ExplainDataBean responseBean) {

    }

    @Override
    public void applyActionSuccess(ActionDataBean responseBean) {

    }

    @Override
    public void actionSafetySuccess(ActionDataBean responseBean) {

    }

    @Override
    public void ListError() {

    }

    @Override
    public void GetAddressListSuccess(AddressListDataBean responseBean) {

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
