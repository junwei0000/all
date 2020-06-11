package com.longcheng.lifecareplan.modular.mine.myorder.detail.activity;

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
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.exchange.shopcart.activity.ShopCartContract;
import com.longcheng.lifecareplan.modular.exchange.shopcart.activity.ShopCartPresenterImp;
import com.longcheng.lifecareplan.modular.exchange.shopcart.bean.ShopCartDataBean;
import com.longcheng.lifecareplan.modular.home.fragment.PopularActionActivity;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.OrderListActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;

/**
 * 下架
 */
public class XiaJiaActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.not_date_btn)
    TextView not_date_btn;
    int type;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.not_date_btn:
                if (type == 2) {
                    Intent intent = new Intent(mContext, PopularActionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(intent);
                } else {
                    Intent intents = new Intent();
                    intents.setAction(ConstantManager.MAINMENU_ACTION);
                    intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_EXCHANGE);
                    intents.putExtra("solar_terms_id", 0);
                    intents.putExtra("solar_terms_name", "");
                    LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
                    ActivityManager.getScreenManager().popAllActivityOnlyMain();
                }
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
        return R.layout.xiajia;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        not_date_btn.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        if (type == 2) {//生命能量
            pageTopTvName.setText("行动下架");
            tv_title.setText("该行动已下架，敬请期待...");
        } else {
            pageTopTvName.setText("商品下架");
            tv_title.setText("该商品已抢购一空，敬请期待...");
        }
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
            doFinish();
        }
        return false;
    }
}
