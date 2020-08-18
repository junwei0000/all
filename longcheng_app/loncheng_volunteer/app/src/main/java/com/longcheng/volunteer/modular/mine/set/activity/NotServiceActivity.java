package com.longcheng.volunteer.modular.mine.set.activity;

import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.api.Api;
import com.longcheng.volunteer.base.ActivityManager;
import com.longcheng.volunteer.base.BaseActivity;
import com.longcheng.volunteer.base.ExampleApplication;
import com.longcheng.volunteer.modular.mine.userinfo.bean.GetHomeInfoDataBean;
import com.longcheng.volunteer.utils.sharedpreferenceutils.UserUtils;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 服务器升级提示
 */
public class NotServiceActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;


    /**
     * 维护状态
     */
    boolean notService = true;

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
        return R.layout.set_notservice;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("系统维护");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDate();
    }

    private void getDate() {
        Observable<GetHomeInfoDataBean> observable = Api.getInstance().service.getUserHomeInfo(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<GetHomeInfoDataBean>() {
                    @Override
                    public void accept(GetHomeInfoDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("550")) {
                            notDateCont.setText("" + responseBean.getMsg());
                            notService = true;
                        } else {
                            notService = false;
                            doFinish();
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
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
