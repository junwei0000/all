package com.longcheng.lifecareplan.modular.mine.activatenergy.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.activity.AutoHelpH5Activity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.activatenergy.adapter.MoneyAdapter;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyAfterBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.GetEnergyListDataBean;
import com.longcheng.lifecareplan.modular.mine.bill.activity.EngryRecordActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 激活能量---
 */
public class ActivatEnergyActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.activat_tv_num)
    TextView activatTvNum;
    @BindView(R.id.activat_tv_cont)
    TextView activatTvCont;
    @BindView(R.id.activat_gv_money)
    MyGridView activatGvMoney;
    @BindView(R.id.btn_jihuo)
    TextView btnJihuo;

    @BindView(R.id.activat_relat_jqb)
    RelativeLayout activat_relat_jqb;
    @BindView(R.id.activat_tv_jqb)
    TextView activat_tv_jqb;
    @BindView(R.id.activat_iv_jqbselect)
    ImageView activat_iv_jqbselect;


    /**
     * 充值渠道激活类型 1现金; 2 有赞 4祝福师代充   5 节气宝
     */
    private int payType = 5;
    private MoneyAdapter mMoneyAdapter;

    private String activate_ability_config_id;
    private String user_id;
    private int type;

    private String money_select = "0";


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.btn_jihuo:
                assetRecharge(user_id, money_select, activate_ability_config_id, type, "" + payType);
                break;

        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activatenergy;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("激活能量");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @SuppressLint("NewApi")
    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        btnJihuo.setOnClickListener(this);
        activatGvMoney.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList != null && mList.size() > 0) {
                    if (payType == 1 || payType == 5) {
                        for (int i = 0; i < mList.size(); i++) {
                            if (i == position) {
                                mList.get(position).setIs_default(1);
                            } else {
                                mList.get(i).setIs_default(0);
                            }
                        }
                        setAssetView(mList);
                        mMoneyAdapter.setList(mList);
                        mMoneyAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }


    @Override
    public void initDataAfter() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        user_id = UserUtils.getUserId(mContext);
        getRechargeInfo(user_id);
    }


    private void setAssetView(List<EnergyItemBean> list) {
        for (EnergyItemBean mEnergyItemBean : list) {
            if (mEnergyItemBean.getIs_default() == 1) {
                money_select = mEnergyItemBean.getMoney();
                String First_energy = mEnergyItemBean.getReward_value();
                String Presenter_energy = mEnergyItemBean.getGive_value();
                activate_ability_config_id = mEnergyItemBean.getActivate_ability_config_id();
                type = mEnergyItemBean.getType();
                if (payType == 1 || payType == 5) {
                    activatTvCont.setText("激活" + First_energy + "+赠送" + Presenter_energy);
                    activatTvNum.setText(PriceUtils.getInstance().gteAddSumPrice(First_energy, Presenter_energy));
                } else {
                    activatTvCont.setText("");
                    activatTvNum.setText(First_energy);
                }
                break;
            }
        }
    }


    /**
     * @param
     * @name 激活生命能量数据
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void getRechargeInfo(String user_id) {
        showDialog();
        Observable<GetEnergyListDataBean> observable = Api.getInstance().service.getRechargeInfo(user_id, ExampleApplication.token);
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

    /**
     * 激活生命能量
     *
     * @param user_id
     */
    public void assetRecharge(String user_id, String money, String activate_ability_config_id, int type, String payment_channel) {
        Log.e("Observable", "money=" + money + "  activate_ability_config_id=" + activate_ability_config_id + "  payment_channel=" + payment_channel);
        showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.assetRecharge(user_id, money,
                activate_ability_config_id, type, "2", payment_channel, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {
                            GetPayWXSuccess(responseBean);
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

    public void ListSuccess(GetEnergyListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            EnergyAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                mList = mEnergyAfterBean.getEnergys();
                if (mList != null && mList.size() > 0) {
                    mMoneyAdapter = new MoneyAdapter(mContext, mList);
                    activatGvMoney.setAdapter(mMoneyAdapter);
                    setAssetView(mList);
                }
                EnergyItemBean chatuser = mEnergyAfterBean.getChatuser();
                activat_tv_jqb.setText("节气宝:" + chatuser.getJieqibao());
            }
        }
    }


    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
    }

    public void GetPayWXSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            if (payType == 1 || payType == 5) {
                jihuoSuccess();
            }
        }
    }


    private void autohelpRefresh() {
        //智能互祝----刷新页面数据
        Intent intent = new Intent();
        intent.setAction(ConstantManager.BroadcastReceiver_KNP_ACTION);
        intent.putExtra("errCode", AutoHelpH5Activity.knpPaySuccessBack);
        sendBroadcast(intent);
    }

    private void jihuoSuccess() {
        autohelpRefresh();
        ToastUtils.showToast("激活成功");
        Intent intent = new Intent(mContext, EngryRecordActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
        doFinish();
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
