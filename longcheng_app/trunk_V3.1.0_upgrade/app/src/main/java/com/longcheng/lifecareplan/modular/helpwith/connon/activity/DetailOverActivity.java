package com.longcheng.lifecareplan.modular.helpwith.connon.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.helpwith.connon.adapter.CHelpApplySelectAYAdapter;
import com.longcheng.lifecareplan.modular.helpwith.connon.adapter.CHelpDetailSuccessAdapter;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 详情已完成
 */
public class DetailOverActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.tv_skip)
    TextView tvSkip;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_data)
    ListView lvData;

    String jump_url;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_skip:
                Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", jump_url);
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
        return R.layout.connon_detail_over;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("互祝成功");
        pagetopLayoutLeft.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        String knp_group_table_id = getIntent().getStringExtra("knp_group_table_id");
        getRoomInfo(knp_group_table_id);
    }

    public void getRoomInfo(String knp_team_room_id) {
        Observable<CHelpDataBean> observable = Api.getInstance().service.getOverRoomInfo(UserUtils.getUserId(mContext), knp_team_room_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CHelpDataBean>() {
                    @Override
                    public void accept(CHelpDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            CHelpAfterBean data = responseBean.getData();
                            List<CHelpDetailItemBean> knpTeamItems = data.getKnpTeamItems();
                            String patientName = data.getPatientName();
                            jump_url = data.getJump_url();
                            patientName = CommonUtil.setName(patientName);
                            tvTitle.setText("此互祝组为 " + patientName + " 送上祝福");
                            CHelpDetailSuccessAdapter mCHelpApplySelectAYAdapter = new CHelpDetailSuccessAdapter(mContext,knpTeamItems);
                            lvData.setAdapter(mCHelpApplySelectAYAdapter);
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
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
