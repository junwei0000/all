package com.longcheng.lifecareplan.modular.exchange.shopcart.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.exchange.shopcart.bean.ShopCartDataBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.OrderListActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;

/**
 * 商品-兑换成功
 */
public class SubmitSuccessActivity extends BaseActivityMVP<ShopCartContract.View, ShopCartPresenterImp<ShopCartContract.View>> implements ShopCartContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;

    @BindView(R.id.btn_look)
    TextView btn_look;
    @BindView(R.id.btn_backmall)
    TextView btn_backmall;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_look:
                back();
                Intent intent = new Intent(mContext, OrderListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.pagetop_layout_left:
            case R.id.btn_backmall:
                back();
                break;
            default:
                doFinish();
                break;
        }
    }

    private void back() {
        Intent intents = new Intent();
        intents.setAction(ConstantManager.MAINMENU_ACTION);
        intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_EXCHANGE);
        intents.putExtra("solar_terms_id", 0);
        intents.putExtra("solar_terms_name", "");
        LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
        ActivityManager.getScreenManager().popAllActivityOnlyMain();
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.exchange_submitsuccess;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("兑换成功");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        btn_look.setOnClickListener(this);
        btn_backmall.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
    }


    @Override
    protected ShopCartPresenterImp<ShopCartContract.View> createPresent() {
        return new ShopCartPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mActivity);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mActivity);
    }

    @Override
    public void GetTuiJianListSuccess(ShopCartDataBean responseBean) {

    }

    @Override
    public void GetAddressListSuccess(AddressListDataBean responseBean) {

    }

    @Override
    public void SubmitGoodsOrder(EditListDataBean responseBean) {

    }

    @Override
    public void ListError() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 重写onkeydown 用于监听返回键
     */

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            back();
            doFinish();
        }
        return false;
    }
}
