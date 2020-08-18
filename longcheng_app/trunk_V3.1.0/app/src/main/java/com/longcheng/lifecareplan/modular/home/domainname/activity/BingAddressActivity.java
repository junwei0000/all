package com.longcheng.lifecareplan.modular.home.domainname.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.home.domainname.bean.domainDataBean;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.address.PopuAddressUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;


import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class BingAddressActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.layout_selectaddress)
    LinearLayout layoutSelectaddress;
    @BindView(R.id.tv_bing)
    TextView tvBing;


    int is_bind;
    String address = "";
    PopuAddressUtils popuAddressUtils;
    private String  vid, name;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.layout_selectaddress:
                if (is_bind == 0) {
                    popuAddressUtils.showAddressPopupWindow(pagetopLayoutLeft, null);
                }
                break;
            case R.id.tv_bing:
                if (is_bind == 0 && !TextUtils.isEmpty(address)) {
                    showJSDialog();
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
        return R.layout.domain_bingaddress;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvBing.setOnClickListener(this);
        layoutSelectaddress.setOnClickListener(this);
        popuAddressUtils = new PopuAddressUtils(mActivity, mHandler, SELECTADDRESS, 5);
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("我的健康公社");
        is_bind = getIntent().getIntExtra("is_bind", 0);
        address = getIntent().getStringExtra("address");
        if (is_bind == 1) {
            tvBing.setText("已绑定");
            tvAddress.setText(address);
        }
    }

    private final int SELECTADDRESS = 1;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SELECTADDRESS:
                    Bundle bundle = msg.getData();
                    vid = bundle.getString("commune_id");
                    name = bundle.getString("short_name");
                    address = bundle.getString("area");
                    tvAddress.setText(address);
                    tvBing.setBackgroundResource(R.drawable.corners_bg_login);
                    ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tvBing, R.color.yellow_E95D1B);
                    break;
            }
        }
    };

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    public void bindAddress() {
        showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.BindAddress(UserUtils.getUserId(mContext), vid, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        dismissDialog();
                        String status_ = responseBean.getStatus();
                        ToastUtils.showToast(responseBean.getMsg());
                        if (status_.equals("200")) {
                            doFinish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        Error();
                    }
                });

    }

    public void Error() {

    }

    MyDialog JSDialog;

    /**
     * 设置出生地
     */
    private void showJSDialog() {
        if (JSDialog == null) {
            JSDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_domain_address_sure);// 创建Dialog并设置样式主题
            JSDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = JSDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            JSDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = JSDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5;
            JSDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_gongshe = JSDialog.findViewById(R.id.tv_gongshe);
            tv_gongshe.setText("绑定：" + name);
            TextView tv_cancel = JSDialog.findViewById(R.id.tv_cancel);
            TextView tv_sure = JSDialog.findViewById(R.id.tv_sure);
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSDialog.dismiss();/**/
                    bindAddress();
                }
            });
        } else {
            JSDialog.show();
        }
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
