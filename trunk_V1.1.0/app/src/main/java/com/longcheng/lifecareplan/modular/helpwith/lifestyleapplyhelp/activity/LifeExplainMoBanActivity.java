package com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ApplyHelpContract;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ApplyHelpPresenterImp;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.adapter.MoBanListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataListBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ExplainAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ExplainDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleSearchDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedItemBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 互祝说明模板
 */
public class LifeExplainMoBanActivity extends BaseActivityMVP<LifeStyleApplyHelpContract.View, LifeStyleApplyHelpPresenterImp<LifeStyleApplyHelpContract.View>> implements LifeStyleApplyHelpContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.moban_lv)
    ListView moban_lv;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.helpwith_apply_explainmoban;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        moban_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList != null && mList.size() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("describe", mList.get(position));
                    setResult(ConstantManager.APPLYHELP_FORRESULT_EXPLAIN, intent);
                    doFinish();
                }
            }
        });
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("使用模板");
        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        String goods_id = intent.getStringExtra("goods_id");
        mPresent.getLifeNeedHelpNumberTaskSuccess(user_id, goods_id);
    }


    @Override
    protected LifeStyleApplyHelpPresenterImp<LifeStyleApplyHelpContract.View> createPresent() {
        return new LifeStyleApplyHelpPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    List<String> mList = new ArrayList<>();


    @Override
    public void PeopleListSuccess(PeopleDataBean responseBean) {

    }

    @Override
    public void getNeedHelpNumberTaskSuccess(LifeNeedDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            LifeNeedItemBean mPeopleAfterBean = responseBean.getData();
            if (mPeopleAfterBean != null) {
                List<LifeNeedItemBean> mList_ = mPeopleAfterBean.getHelpGoodsTemp();
                if (mList_ != null && mList_.size() > 0) {
                    for (LifeNeedItemBean mLifeNeedItemBean : mList_) {
                        mList.add(mLifeNeedItemBean.getContent());
                    }
                    MoBanListAdapter mMoBanListAdapter = new MoBanListAdapter(mContext, mList);
                    moban_lv.setAdapter(mMoBanListAdapter);
                }
            }

        }
    }

    @Override
    public void applyActionSuccess(LifeNeedDataBean responseBean) {

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
