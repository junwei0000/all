package com.longcheng.lifecareplan.modular.mine.youzan;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.GetEnergyListDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.youzan.androidsdk.YouzanSDK;
import com.youzan.androidsdk.YouzanToken;
import com.youzan.androidsdk.event.AbsAuthEvent;
import com.youzan.androidsdkx5.YouzanBrowser;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 有赞激活
 */
public class YouZanActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.youzanBrowser)
    YouzanBrowser youzanBrowser;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
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
        return R.layout.mine_youzan;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
        pageTopTvName.setText("订单详情");
        String html_url = getIntent().getStringExtra("html_url");
        cookie_key = getIntent().getStringExtra("cookie_key");
        cookie_value = getIntent().getStringExtra("cookie_value");
        youzanBrowser.loadUrl(html_url);
        //认证事件, 回调表示: 需要需要新的认证信息传入
        youzanBrowser.subscribe(new AbsAuthEvent() {
            @Override
            public void call(Context context, boolean needLogin) {
                if (needLogin) {
                    getYouZanCookie();
                } else {
                    if (!TextUtils.isEmpty(cookie_key) && !TextUtils.isEmpty(cookie_value)) {
                        setCookie();
                    } else {
                        getYouZanCookie();
                    }
                }
            }
        });
    }

    String cookie_key;
    String cookie_value;

    private void setCookie() {
        YouzanToken token = new YouzanToken();
        //token.setAccessToken("接口返回的access_token")【已下线】
        token.setCookieKey(cookie_key);
        token.setCookieValue(cookie_value);
        // 这里注意调用顺序。先传递给sdk，再刷新view
        YouzanSDK.sync(getApplicationContext(), token);
        youzanBrowser.sync(token);
    }

    /**
     * 获取有赞cookie
     */
    public void getYouZanCookie() {
        Observable<GetEnergyListDataBean> observable = Api.getInstance().service.getYouZanCookie(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetEnergyListDataBean>() {
                    @Override
                    public void accept(GetEnergyListDataBean responseBean) throws Exception {
                        cookie_key = responseBean.getData().getCookie_key();
                        cookie_value = responseBean.getData().getCookie_value();
                        setCookie();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }


    private void back() {
        if (youzanBrowser.pageCanGoBack()) {
            youzanBrowser.pageGoBack();
        } else {
            doFinish();
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
