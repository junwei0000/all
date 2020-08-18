package com.longcheng.lifecareplan.modular.mine.pioneercenter.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.adapter.PionOpenTaskAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PionApplyAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PionApplyDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataListBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.ZYBBDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.AblumUtils;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.pay.PayCallBack;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 24节气创业中心---开通0
 */
public class PioneerOpenZoreActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.lv_data)
    ListView lvData;
    @BindView(R.id.btn_open)
    TextView btnOpen;


    boolean isHaveAuth = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.btn_open:
                if (isHaveAuth) {
                    Intent intent = new Intent(mActivity, PioneerOpenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pioneer_open_zore;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getOpenTask();
    }

    public void showDialog() {
        RequestDataStatus = true;
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        RequestDataStatus = false;
        LoadingDialogAnim.dismiss(mContext);
    }

    private void setBtn() {
        if (isHaveAuth) {
            btnOpen.setBackgroundColor(getResources().getColor(R.color.yellow_E95D1B));
        } else {
            btnOpen.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }

    public void getOpenTask() {
        showDialog();
        isHaveAuth = false;
        setBtn();
        Observable<PionApplyDataBean> observable = Api.getInstance().service.openTask(
                UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PionApplyDataBean>() {
                    @Override
                    public void accept(PionApplyDataBean responseBean) throws Exception {
                        dismissDialog();
                        String status = responseBean.getStatus();
                        if (status.equals("200")) {
                            PionApplyAfterBean data = responseBean.getData();
                            int isPassTask = data.getIsPassTask();
                            if (isPassTask == 1) {
                                Intent intent = new Intent(mActivity, PioneerOpenActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                doFinish();
                            }
                            ArrayList<PionApplyAfterBean.RecordBean> openTask = data.getOpenTask();
                            if (openTask != null && openTask.size() > 0) {
                                int undonenum = 0;
                                for (PionApplyAfterBean.RecordBean recordBean : openTask) {
                                    if (recordBean.getStatus() == 0) {
                                        isHaveAuth = false;
                                        setBtn();
                                        undonenum++;
                                        break;
                                    }
                                }
                                if (undonenum == 0) {
                                    isHaveAuth = true;
                                    setBtn();
                                }
                                PionOpenTaskAdapter pionOpenTaskAdapter = new PionOpenTaskAdapter(mActivity, openTask);
                                lvData.setAdapter(pionOpenTaskAdapter);
                            }
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        Log.e("Observable", throwable.toString());
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

