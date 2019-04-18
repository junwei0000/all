package com.longcheng.lifecareplan.modular.mine.set.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.set.bean.PushAfterBean;
import com.longcheng.lifecareplan.modular.mine.set.bean.PushDataBean;
import com.longcheng.lifecareplan.modular.mine.set.bean.VersionAfterBean;
import com.longcheng.lifecareplan.modular.mine.set.bean.VersionDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoDataBean;
import com.longcheng.lifecareplan.modular.webView.WebAct;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 服务器升级提示
 */
public class NotServiceActivity extends WebAct {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_iv_left)
    ImageView pagetop_iv_left;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;

    /**
     * 维护状态
     */
    static boolean notService = true;

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
        return R.layout.activity_web;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("系统维护中");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetop_iv_left.setVisibility(View.GONE);
    }


    @Override
    public void initDataAfter() {
        String url = getIntent().getStringExtra("html_url");
        loadUrl(url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendRequestWithHttpClient();
    }

    private void sendRequestWithHttpClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://t.asdyf.com/home/upgrade/index";
                //第一步：创建HttpClient对象
                HttpClient httpCient = new DefaultHttpClient();
                //第二步：创建代表请求的对象,参数是访问的服务器地址
                HttpGet httpGet = new HttpGet(url);
                try {
                    //第三步：执行请求，获取服务器发还的相应对象
                    HttpResponse httpResponse = httpCient.execute(httpGet);
                    //第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
                    int code = httpResponse.getStatusLine().getStatusCode();
                    Log.e("getModifyDomainName", "code=" + code + "  notServicePage=");
                    if (code == 200) {
                        notService = true;
                    } else {
                        notService = false;
                        doFinish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();//这个start()方法不要忘记了
    }

    private void back() {
        if (!notService) {
            doFinish();
        } else {
            ActivityManager.getScreenManager().backHome(mActivity);
        }
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
