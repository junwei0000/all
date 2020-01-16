package com.longcheng.lifecareplan.modular.home.liveplay.mine.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MineItemInfo;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;

import butterknife.BindView;

/**
 *
 */
public class UpdateInfoActivity extends BaseActivityMVP<MyContract.View, MyPresenterImp<MyContract.View>> implements MyContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pageTop_tv_rigth)
    TextView pageTopTvrigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRight;
    @BindView(R.id.updatename_et_name)
    SupplierEditText updatename_et_name;
    private String content;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.pagetop_layout_rigth:
                content = updatename_et_name.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    ConfigUtils.getINSTANCE().closeSoftInput(updatename_et_name);
                    mPresent.updateShowTitle(content);
                } else {
                    ToastUtils.showToast("说点什么吧~");
                }
                break;
        }
    }

    @Override
    protected MyPresenterImp<MyContract.View> createPresent() {
        return new MyPresenterImp<>(mContext, this);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.user_account_updatename;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("签名编辑");
        pageTopTvrigth.setText("确定");
        pageTopTvrigth.setVisibility(View.VISIBLE);
        setOrChangeTranslucentColor(toolbar, null);
        content = getIntent().getStringExtra("show_title");
        updatename_et_name.setText(content);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(updatename_et_name, 40);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopLayoutRight.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

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


    @Override
    public void getMineInfoSuccess(BasicResponse<MineItemInfo> responseBean) {

    }

    @Override
    public void updateShowTitleSuccess(BasicResponse responseBean) {
        doFinish();
    }

    @Override
    public void cancelFollowSuccess(BasicResponse responseBean) {

    }

    @Override
    public void BackVideoListSuccess(BasicResponse<MVideoDataInfo> responseBean, int back_page) {

    }

    @Override
    public void Error() {

    }
}
