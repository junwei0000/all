package com.longcheng.lifecareplan.modular.mine.set.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UpdatePwActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressListActivity;
import com.longcheng.lifecareplan.modular.mine.set.bean.PushAfterBean;
import com.longcheng.lifecareplan.modular.mine.set.bean.PushDataBean;
import com.longcheng.lifecareplan.modular.mine.set.version.AppUpdate;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.SendIdeaActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 设置
 */
public class SetActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.set_relay_updateapp)
    RelativeLayout setRelayUpdateapp;
    @BindView(R.id.cb_mylocation)
    CheckBox cbMylocation;
    @BindView(R.id.set_relay_about)
    RelativeLayout setRelayAbout;
    @BindView(R.id.set_tv_showversion)
    TextView setTvShowversion;
    @BindView(R.id.set_tv_logout)
    TextView setTvLogout;
    @BindView(R.id.usercenter_relay_address)
    RelativeLayout usercenterRelayAddress;
    @BindView(R.id.usercenter_relay_updatepw)
    RelativeLayout usercenterRelayupdatepw;
    @BindView(R.id.usercenter_layout_tel)
    RelativeLayout usercenter_layout_tel;


    private String about_me_url;
    private String user_id;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.set_tv_logout:
                UserLoginBack403Utils.getInstance().zhuXiao();
                doFinish();
                break;
            case R.id.set_relay_updateapp:
                AppUpdate appUpdate = new AppUpdate();
                appUpdate.startUpdateAsy(this, "");
                break;
            case R.id.set_relay_about:
                intent = new Intent(mContext, AboutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("about_me_url", about_me_url);
                startActivity(intent);
                break;
            case R.id.usercenter_layout_tel:
                onTelClick();
                break;
            case R.id.usercenter_relay_updatepw://修改密码
                intent = new Intent(mActivity, UpdatePwActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.usercenter_relay_address://地址管理
                intent = new Intent(mActivity, AddressListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("receive_user_id", user_id);
                intent.putExtra("skiptype", "MineFragment");
                startActivity(intent);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.set;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText(getString(R.string.set));
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        setTvLogout.setOnClickListener(this);
        setRelayAbout.setOnClickListener(this);
        setRelayUpdateapp.setOnClickListener(this);
        usercenterRelayAddress.setOnClickListener(this);
        usercenterRelayupdatepw.setOnClickListener(this);
        usercenter_layout_tel.setOnClickListener(this);
        cbMylocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ((push_need_received == 1 && !isChecked) || (push_need_received != 1 && isChecked)) {
                    if (isChecked) {
                        setPushStatus(1);
                    } else {
                        setPushStatus(0);
                    }
                }
            }
        });
    }

    /**
     * //1:开启
     */
    int push_need_received = 1;

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        about_me_url = intent.getStringExtra("about_me_url");
        user_id = UserUtils.getUserId(mContext);
        getPushStatus();
    }

    private void getPushStatus() {
        Observable<PushDataBean> observable = Api.getInstance().service.getPushStatus(user_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PushDataBean>() {
                    @Override
                    public void accept(PushDataBean responseBean) throws Exception {
                        String status = responseBean.getStatus();
                        if (status.equals("400")) {
                        } else if (status.equals("200")) {
                            PushAfterBean mPushAfterBean = responseBean.getData();
                            if (mPushAfterBean != null) {
                                push_need_received = mPushAfterBean.getPush_need_received();
                                if (push_need_received == 1) {
                                    cbMylocation.setChecked(true);
                                } else {
                                    cbMylocation.setChecked(false);
                                }
                            }
                        }

                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }

    private void setPushStatus(int push_need_received_) {
        Observable<EditDataBean> observable = Api.getInstance().service.setPushStatus(user_id, push_need_received_,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        String status = responseBean.getStatus();
                        if (status.equals("400")) {
                            if (push_need_received_ == 1) {//开启失败，重置FALSE
                                cbMylocation.setChecked(false);
                            } else {
                                cbMylocation.setChecked(true);
                            }
                            ToastUtils.showToast(responseBean.getMsg());
                        } else if (status.equals("200")) {
                            push_need_received = push_need_received_;
                        }

                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }

    MyDialog telDialog;
    TextView tv_tel;

    public void onTelClick() {
        if (telDialog == null) {
            telDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_tel);// 创建Dialog并设置样式主题
            telDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = telDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            telDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = telDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            telDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_tel = telDialog.findViewById(R.id.layout_tel);
            TextView tv_cancel = telDialog.findViewById(R.id.tv_cancel);
            tv_tel = telDialog.findViewById(R.id.tv_tel);

            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    telDialog.dismiss();
                }
            });
            layout_tel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    telDialog.dismiss();
                    DensityUtil.getPhoneToKey(mContext, "400-6124365");
                }
            });
        } else {
            telDialog.show();
        }
        if (tv_tel != null) {
            tv_tel.setText("400-6124365");
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
