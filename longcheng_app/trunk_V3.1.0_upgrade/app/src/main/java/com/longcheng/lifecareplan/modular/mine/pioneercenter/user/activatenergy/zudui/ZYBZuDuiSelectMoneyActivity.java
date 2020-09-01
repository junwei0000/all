package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.zudui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.activity.AutoHelpH5Activity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.activatenergy.adapter.MoneyAdapter;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyAfterBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.GetEnergyListDataBean;
import com.longcheng.lifecareplan.modular.mine.bill.activity.EngryRecordActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter.SelectMonRecordListAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter.ZuDuiMoneyAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.ZYBSelectMonRecrodListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 组队选择金额
 */
public class ZYBZuDuiSelectMoneyActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.gv_money)
    MyGridView gvMoney;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.date_listview)
    ListView date_listview;


    String zhuyoubao_team_rule_id;
    int selectIndex;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_next:
                if (!TextUtils.isEmpty(zhuyoubao_team_rule_id)) {
                    ZYBCreateTeamRoom();
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
        return R.layout.pioneer_userzyb_zudui_selectmoney;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("组队请购祝佑宝");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @SuppressLint("NewApi")
    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        gvMoney.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList != null && mList.size() > 0) {
                    selectIndex = position;
                    zhuyoubao_team_rule_id = mList.get(selectIndex).getZhuyoubao_team_rule_id();
                    mMoneyAdapter.setSelectPosition(selectIndex);
                    updatTitle(mList.get(selectIndex).getMoney());
                }
            }
        });
    }


    @Override
    public void initDataAfter() {
        selectIndex = (int) SharedPreferencesHelper.get(mContext, "selectIndex", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMoneyInfo();
        getTeamItemList();
    }

    private void updatTitle(String money) {
        tv_title.setText("(每位成员请购" + money + ")");
    }

    public void getMoneyInfo() {
        showDialog();
        Observable<GetEnergyListDataBean> observable = Api.getInstance().service.getZYZBMoneyInfo(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetEnergyListDataBean>() {
                    @Override
                    public void accept(GetEnergyListDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            ListSuccess(responseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });

    }

    public void ZYBCreateTeamRoom() {
        showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.ZYBCreateTeamRoom(
                UserUtils.getUserId(mContext), zhuyoubao_team_rule_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (responseBean.getStatus().equals("200")) {
                            SharedPreferencesHelper.put(mContext, "selectIndex", selectIndex);
                            Intent intent = new Intent(mActivity, ZYBZuDuiActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("zhuyoubao_team_room_id", "" + responseBean.getData());
                            startActivity(intent);
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });

    }

    public void getTeamItemList() {
        showDialog();
        Observable<ZYBSelectMonRecrodListDataBean> observable = Api.getInstance().service.getTeamItemList(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ZYBSelectMonRecrodListDataBean>() {
                    @Override
                    public void accept(ZYBSelectMonRecrodListDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (responseBean.getStatus().equals("200")) {
                            ArrayList<ZYBSelectMonRecrodListDataBean.RiceActiviesItemBean> data = responseBean.getData();
                            SelectMonRecordListAdapter mSelectMonRecordListAdapter = new SelectMonRecordListAdapter(mActivity, data);
                            date_listview.setAdapter(mSelectMonRecordListAdapter);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });

    }

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    List<EnergyItemBean> mList;
    ZuDuiMoneyAdapter mMoneyAdapter;

    public void ListSuccess(GetEnergyListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            EnergyAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                mList = mEnergyAfterBean.getRules();
                if (mList != null && mList.size() > 0) {
                    if (selectIndex >= mList.size()) {
                        selectIndex = 0;
                    }
                    zhuyoubao_team_rule_id = mList.get(selectIndex).getZhuyoubao_team_rule_id();
                    updatTitle(mList.get(selectIndex).getMoney());
                    mMoneyAdapter = new ZuDuiMoneyAdapter(mContext, mList);
                    mMoneyAdapter.setSelectPosition(selectIndex);
                    gvMoney.setAdapter(mMoneyAdapter);
                }
            }
        }
    }


    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
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
