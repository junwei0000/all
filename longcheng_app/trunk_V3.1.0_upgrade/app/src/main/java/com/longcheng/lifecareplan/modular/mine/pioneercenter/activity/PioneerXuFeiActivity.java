package com.longcheng.lifecareplan.modular.mine.pioneercenter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.adapter.XuFeiAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerXuFeiDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.pay.PayCallBack;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 24节气创业中心---续费
 */
public class PioneerXuFeiActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.btn_open)
    TextView btn_open;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.activat_iv_cardselect)
    ImageView activatIvCardselect;
    @BindView(R.id.activat_relat_card)
    RelativeLayout activatRelatCard;
    @BindView(R.id.gv_data)
    GridView gv_data;
    private int payType = 3;

    int selectindex;
    String entrepreneurs_renew_config_id, money;
    XuFeiAdapter mXuFeiAdapter;
    ArrayList<PioneerXuFeiDataBean.PioneerXuFeiItemBean> data;

    public  static  PioneerXuFeiActivity mPioneerXuFeiActivity;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.activat_relat_card:
                payType = 3;
                selectPayTypeView();
                break;
            case R.id.btn_open:
                Intent intent = new Intent(mContext, PioneerXuFeiNextActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("entrepreneurs_renew_config_id",entrepreneurs_renew_config_id);
                intent.putExtra("money",money);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pioneer_xufei;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        mPioneerXuFeiActivity=this;
        pageTopTvName.setText("24节气创业中心续费");
        pagetopLayoutLeft.setOnClickListener(this);
        btn_open.setOnClickListener(this);
        activatRelatCard.setOnClickListener(this);
        gv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (data != null && data.size() > 0) {
                    selectindex = position;
                    money = data.get(selectindex).getMoney();
                    entrepreneurs_renew_config_id = data.get(selectindex).getEntrepreneurs_renew_config_id();
                    mXuFeiAdapter.setSelectindex(position);
                    mXuFeiAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public void initDataAfter() {
        selectPayTypeView();
        getInfo();
    }

    /**
     * 切换充值渠道
     */
    private void selectPayTypeView() {
        tvCard.setTextColor(getResources().getColor(R.color.text_contents_color));
        activatRelatCard.setBackgroundResource(R.drawable.corners_bg_graybian);
        activatIvCardselect.setVisibility(View.GONE);
        if (payType == 3) {
            tvCard.setTextColor(getResources().getColor(R.color.red));
            activatIvCardselect.setVisibility(View.VISIBLE);
            activatRelatCard.setBackgroundResource(R.drawable.corners_bg_redbian);
            activatRelatCard.setPadding(0, 0, 0, 0);
        }
    }

    public void showDialog() {
        RequestDataStatus = true;
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        RequestDataStatus = false;
        LoadingDialogAnim.dismiss(mContext);
    }

    private void getInfo() {
        if (RequestDataStatus) {
            return;
        }
        showDialog();
        Observable<PioneerXuFeiDataBean> observable = Api.getInstance().service.PionGetXuFeiInfo(UserUtils.getUserId(mContext),
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PioneerXuFeiDataBean>() {
                    @Override
                    public void accept(PioneerXuFeiDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {
                            String status = responseBean.getStatus();
                            if (status.equals("200")) {
                                data = responseBean.getData();
                                if (data != null && data.size() > 0) {
                                    money = data.get(selectindex).getMoney();
                                    entrepreneurs_renew_config_id = data.get(selectindex).getEntrepreneurs_renew_config_id();
                                    mXuFeiAdapter = new XuFeiAdapter(mActivity, data);
                                    gv_data.setAdapter(mXuFeiAdapter);
                                }
                            } else {
                                ToastUtils.showToast(responseBean.getMsg());
                            }
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showToast(R.string.net_tishi);
                        Log.e("Observable", "" + throwable.toString());
                        dismissDialog();
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

