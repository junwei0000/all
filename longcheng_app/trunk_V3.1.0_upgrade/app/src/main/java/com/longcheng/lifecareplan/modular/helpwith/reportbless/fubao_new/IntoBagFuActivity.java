package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.bean.contactbean.PhoneBean;
import com.longcheng.lifecareplan.bean.contactbean.SelectPhone;
import com.longcheng.lifecareplan.modular.helpwith.fragment.HelpWithFragmentNew;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.adapter.IntoBagFuAdapter;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.myview.MyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * 塞进福包
 */
public class IntoBagFuActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_iv_left)
    ImageView pagetopIvLeft;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;

    @BindView(R.id.all_chaonen)
    TextView allChaonen;
    @BindView(R.id.btn_goaction)
    TextView btnGoaction;

    @BindView(R.id.lv_data)
    MyListView lvData;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;

    private SelectPhone select = null;

    IntoBagFuAdapter intoBagFuAdapter = null;
    String bodystr = "";
    String bodynohttp = "";
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.pagetop_iv_left:
            case R.id.pagetop_layout_left:
                doFinish();
                break;

            case R.id.btn_goaction:
                // 送福操作提示用户消耗多少超能

                PriceUtils.getInstance().copy(IntoBagFuActivity.this,bodystr);

                break;


        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.intobag_layout;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
        bodystr = this.getResources().getString(R.string.duanxin_moban);
        bodynohttp = this.getResources().getString(R.string.daunxin_moban_nohttp);
    }

    @Override
    public void setListener() {
        pagetopIvLeft.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        btnGoaction.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("快速送福包");

        try {
            select = this.getIntent().getExtras().getParcelable("selectUser");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (select != null) {
            intoBagFuAdapter = new IntoBagFuAdapter(mActivity, select.getData(),bodynohttp);
            lvData.setAdapter(intoBagFuAdapter);
        }
    }


}
