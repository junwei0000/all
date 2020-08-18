package com.longcheng.lifecareplan.modular.im.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.im.adapter.MoBanAdapter;
import com.longcheng.lifecareplan.modular.im.bean.PionImDataBean;
import com.longcheng.lifecareplan.modular.im.bean.PionImMoBanDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class QuickListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_bottom)
    TextView tv_bottom;
    @BindView(R.id.lv_data)
    ListView lvData;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_bottom:
                Intent intent = new Intent(mActivity, EditMoBanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
        return R.layout.im_quicklist;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("快捷回复");
        pagetopLayoutLeft.setOnClickListener(this);
        tv_bottom.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getQuickList();
    }

    public void getQuickList() {
        String use_id = UserUtils.getUserId(mContext);
        Observable<PionImMoBanDataBean> observable = Api.getInstance().service.getQuickList(
                use_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PionImMoBanDataBean>() {
                    @Override
                    public void accept(PionImMoBanDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            ArrayList<PionImMoBanDataBean.PionImMoBanItemBean> data = responseBean.getData();
                            MoBanAdapter moBanAdapter = new MoBanAdapter(mContext, data, mHandler);
                            lvData.setAdapter(moBanAdapter);
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Observable", throwable.getMessage());
                    }
                });
    }

    public void delMoBan(String im_user_temp_id) {
        String use_id = UserUtils.getUserId(mContext);
        Observable<ResponseBean> observable = Api.getInstance().service.DelMoBan(
                use_id, im_user_temp_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            ToastUtils.showToast(responseBean.getMsg());
                            getQuickList();
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Observable", throwable.getMessage());
                    }
                });
    }

    public static final int SkipEDIT = 2;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SkipEDIT:
                    String im_user_temp_id = (String) msg.obj;
                    delMoBan(im_user_temp_id);
                    break;
            }
        }
    };

}
