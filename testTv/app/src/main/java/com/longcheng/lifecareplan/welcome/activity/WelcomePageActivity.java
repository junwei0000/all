package com.longcheng.lifecareplan.welcome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.home.activity.MenuActivity;
import com.longcheng.lifecareplan.login.activity.LoginActivity;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.SharedPreferencesHelper;


public class WelcomePageActivity extends BaseActivity {


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
            Intent intent = new Intent(mContext, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        finish();
    }

    /**
     * 监听返回键
     */
    public void onBackPressed() {
        exit();
    }

}
