package com.longcheng.lifecareplan.modular.mine.set.activity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.webView.WebAct;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import butterknife.BindView;

/**
 * 功能优化升级中
 */
public class UpgradeActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetop_layout_left;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_upgrade;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("功能优化升级中");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetop_layout_left.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void back() {
        doFinish();
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            back();
        }
        return false;
    }
}
