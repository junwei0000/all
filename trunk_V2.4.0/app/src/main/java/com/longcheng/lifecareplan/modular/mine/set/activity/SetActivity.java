package com.longcheng.lifecareplan.modular.mine.set.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.set.bean.PushAfterBean;
import com.longcheng.lifecareplan.modular.mine.set.bean.PushDataBean;
import com.longcheng.lifecareplan.modular.mine.set.version.AppUpdate;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.SendIdeaActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
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
    @BindView(R.id.set_relay_aftersales)
    RelativeLayout setRelayAftersales;
    @BindView(R.id.set_relay_opinion)
    RelativeLayout setRelayOpinion;
    @BindView(R.id.set_relay_about)
    RelativeLayout setRelayAbout;
    @BindView(R.id.set_tv_showversion)
    TextView setTvShowversion;
    @BindView(R.id.set_tv_logout)
    TextView setTvLogout;
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
            case R.id.set_relay_opinion:
                intent = new Intent(mContext, SendIdeaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
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
        setRelayOpinion.setOnClickListener(this);
        setRelayAbout.setOnClickListener(this);
        setRelayUpdateapp.setOnClickListener(this);
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
