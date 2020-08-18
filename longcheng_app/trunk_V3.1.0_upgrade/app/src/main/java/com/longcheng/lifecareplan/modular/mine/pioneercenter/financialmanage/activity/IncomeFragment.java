package com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.adapter.IncomeAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.pionopen.activity.PionOpenListActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.zyblist.activity.ZYBRecordListActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.myview.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class IncomeFragment extends BaseFragmentMVP<FinanManageContract.View, FinanManagePresenterImp<FinanManageContract.View>> implements FinanManageContract.View {

    @BindView(R.id.gv_cont)
    MyGridView gvCont;

    @Override
    public int bindLayout() {
        return R.layout.pion_finanmanage_frame;
    }


    @Override
    public void initView(View view) {
    }

    @Override
    public void doBusiness(Context mContext) {
        List<String> mList = new ArrayList();
        mList.add("创业中心开通");
        mList.add("创业中心升级");
        mList.add("创业中心续费");
        mList.add("祝佑宝平衡");
        IncomeAdapter mHelpWithTopAdapter = new IncomeAdapter(mActivity, mList);
        gvCont.setAdapter(mHelpWithTopAdapter);
        gvCont.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (position == 0) {
                    intent = new Intent(mActivity, PionOpenListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("type", PionOpenListActivity.type_open);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                } else if (position == 1) {
                    intent = new Intent(mActivity, PionOpenListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("type", PionOpenListActivity.type_upgrade);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                } else if (position == 2) {
                    intent = new Intent(mActivity, PionOpenListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("type", PionOpenListActivity.type_xufei);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                } else if (position == 3) {
                    intent = new Intent(mActivity, ZYBRecordListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
            }
        });
    }


    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    protected FinanManagePresenterImp<FinanManageContract.View> createPresent() {
        return new FinanManagePresenterImp<>(this);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }
}
