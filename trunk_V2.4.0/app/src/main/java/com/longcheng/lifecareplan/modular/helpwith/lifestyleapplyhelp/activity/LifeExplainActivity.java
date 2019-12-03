package com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ApplyHelpContract;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ApplyHelpPresenterImp;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ExplainMoBanActivity;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataListBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ExplainDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleSearchDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedItemBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;

import java.util.List;

import butterknife.BindView;

/**
 * 互祝说明
 */
public class LifeExplainActivity extends BaseActivityMVP<LifeStyleApplyHelpContract.View, LifeStyleApplyHelpPresenterImp<LifeStyleApplyHelpContract.View>> implements LifeStyleApplyHelpContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.et_cont)
    EditText et_cont;
    @BindView(R.id.btn_save)
    TextView btnSave;
    @BindView(R.id.tv_moban)
    TextView tvMoban;
    private String goods_id, user_id;
    private String describe;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_moban:
                intent = new Intent(mContext, LifeExplainMoBanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("goods_id", goods_id);
                intent.putExtra("user_id", user_id);
                startActivityForResult(intent, ConstantManager.APPLYHELP_FORRESULT_EXPLAIN);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.btn_save:
                describe = et_cont.getText().toString().trim();
                if (!TextUtils.isEmpty(describe)) {
                    intent = new Intent();
                    intent.putExtra("describe", describe);
                    setResult(ConstantManager.APPLYHELP_FORRESULT_EXPLAIN, intent);
                    doFinish();
                } else {
                    ToastUtils.showToast("请输入您的互祝说明");
                }
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.helpwith_apply_explain;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvMoban.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("互祝说明");
        Intent intent = getIntent();
        describe = intent.getStringExtra("describe");
        goods_id = intent.getStringExtra("goods_id");
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        et_cont.setText(describe);
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


    @Override
    public void getNeedHelpNumberTaskSuccess(LifeNeedDataBean responseBean) {

    }

    @Override
    public void PeopleListSuccess(PeopleDataBean responseBean) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == ConstantManager.APPLYHELP_FORRESULT_EXPLAIN) {
                describe = data.getStringExtra("describe");
                et_cont.setText(describe);
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
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
