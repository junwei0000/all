package com.longcheng.lifecareplan.modular.home.liveplay.framgent;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.MyContract;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.MyPresenterImp;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MineItemInfo;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;

/**
 * 扫码
 */
public class CodeFramgemt extends BaseFragmentMVP<MyContract.View, MyPresenterImp<MyContract.View>> implements MyContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int bindLayout() {
        return R.layout.live_code;
    }


    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }


    @Override
    public void doBusiness(Context mContext) {
    }


    @Override
    protected MyPresenterImp<MyContract.View> createPresent() {
        return new MyPresenterImp<>(mContext, this);
    }


    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void getMineInfoSuccess(BasicResponse<MineItemInfo> responseBean) {
    }

    @Override
    public void updateShowTitleSuccess(BasicResponse responseBean) {
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
