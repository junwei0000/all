package com.longcheng.web;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.longcheng.web.adapter.TabPageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseTabActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    TabHost mTabHost;
    @BindView(R.id.rb_bottom_home)
    RadioButton mButtonHome;

    @BindView(R.id.rb_bottom_helpWith)
    RadioButton mButtonHelpWith;

    @BindView(R.id.rg_bottom_main)
    RadioGroup mRadioGroup;
    public static final String TAB_MAIN = "MAIN_ACTIVITY";
    public static final String TAB_SPORTQURAT = "SPORTQURAT_ACTIVITY";
    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case  R.id.rb_bottom_home:

                break;
            case  R.id.rb_bottom_helpWith:

                break;
        }
    }
    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(mToolbar, null);
    }

    @Override
    public void initDataAfter() {
        initView();
    }

    @Override
    public void setListener() {
    }

    private void initView() {
        mTabHost = getTabHost();
        Intent i_main = new Intent(this, MyDeH5Activity.class);
        Intent i_sportqurat = new Intent(this, MyDe2H5Activity.class);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN).setContent(i_main));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_SPORTQURAT).setIndicator(TAB_SPORTQURAT).setContent(i_sportqurat));

        mTabHost.setCurrentTabByTag(TAB_MAIN);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_bottom_home:
                        mTabHost.setCurrentTabByTag(TAB_MAIN);
                        break;
                    case R.id.rb_bottom_helpWith:
                        mTabHost.setCurrentTabByTag(TAB_SPORTQURAT);
                        break;
                    default:
                        break;
                }
            }
        });
    }

}
