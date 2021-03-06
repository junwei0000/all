package com.longcheng.lifecareplanTv.welcome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;


import com.longcheng.lifecareplanTv.R;
import com.longcheng.lifecareplanTv.base.BaseActivity;
import com.longcheng.lifecareplanTv.home.menu.activity.MenuActivity;
import com.longcheng.lifecareplanTv.login.activity.LoginActivity;
import com.longcheng.lifecareplanTv.utils.ConstantManager;
import com.longcheng.lifecareplanTv.utils.SharedPreferencesHelper;


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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intentIndexPage();
            }
        }, 2000);

    }

    @Override
    public void setListener() {
    }

    @Override
    public void initDataAfter() {

    }

    @Override
    public void setDateInfo() {

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
