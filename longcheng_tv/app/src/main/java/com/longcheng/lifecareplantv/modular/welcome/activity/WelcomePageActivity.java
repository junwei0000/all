package com.longcheng.lifecareplantv.modular.welcome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.longcheng.lifecareplantv.R;
import com.longcheng.lifecareplantv.base.BaseActivity;
import com.longcheng.lifecareplantv.modular.login.activity.LoginActivity;
import com.longcheng.lifecareplantv.utils.ConfigUtils;
import com.longcheng.lifecareplantv.utils.ConstantManager;
import com.longcheng.lifecareplantv.utils.sharedpreferenceutils.SharedPreferencesHelper;

import butterknife.BindView;

public class WelcomePageActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    public void onClick(View v) {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_welcome_page;
    }

    @Override
    public void initView(View view) {
        try {
            Thread.sleep(1000);//防止启动页消失太快
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setOrChangeTranslucentColor(toolbar, null);
        intentIndexPage();
    }

    @Override
    public void setListener() {
    }

    @Override
    public void initDataAfter() {

    }


    private void intentIndexPage() {
        String loginStatus = (String) SharedPreferencesHelper.get(getApplicationContext(), "loginStatus", "");
        if (loginStatus.equals(ConstantManager.loginStatus)) {

        } else {
            Intent intent = new Intent(mContext, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
        }
        doFinish();
    }

    /**
     * 监听返回键
     */
    public void onBackPressed() {
        exit();
    }

}
