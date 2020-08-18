package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter.ZFBHistoryAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter.ZFBSelectAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBHistoryDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBSelectDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity.cz.PionCZRecordActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;

import butterknife.BindView;

/**
 *
 */
public class PionZFBSelectActivity extends BaseActivityMVP<PionZFBContract.View, PionZFBPresenterImp<PionZFBContract.View>> implements PionZFBContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.et_phone)
    SupplierEditText etPhone;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_bottom)
    TextView tvBottom;
    @BindView(R.id.date_listview)
    MyListView dateListview;
    @BindView(R.id.date_history)
    MyListView dateHistory;
    @BindView(R.id.layout_user)
    ScrollView layoutUser;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont_title)
    TextView notDateContTitle;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.tv_histitle)
    TextView tv_histitle;


    String inputmoney;
    int pay_method;
    String phone;
    String entrepreneurs_id;

    int topindex = -1;
    int historyindex = -1;

    @Override
    public void onClick(View v) {
        Intent intent;
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.item_tv_phone:
                DensityUtil.getPhoneToKey(mContext, phone);
                break;
            case R.id.tv_search:
                String phone = etPhone.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    searchclick = true;
                    serach(phone);
                }
                break;
            case R.id.tv_bottom:
                if (topindex != -1 || historyindex != -1)
                    mPresent.entrepreneursuserRecharge(inputmoney, pay_method, entrepreneurs_id);
                break;
            default:
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pioneer_userzyb_select;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("查找创业导师");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        tvBottom.setOnClickListener(this);
        notDateImg.setBackgroundResource(R.mipmap.zyb_search_bg);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etPhone, 11);
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPhone != null) {
                    String phone = etPhone.getText().toString();
                    if (TextUtils.isEmpty(phone)) {
                        topindex = -1;
                        layoutNotdate.setVisibility(View.GONE);
                        layoutUser.setVisibility(View.VISIBLE);
                        tvBottom.setBackgroundColor(getResources().getColor(R.color.gray));
                        if (mZFBSelectAdapter != null) {
                            entreList.clear();
                            mZFBSelectAdapter.refreshListView(entreList);
                        }
                    } else {
                        serach(phone);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dateListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (entreList != null && entreList.size() > 0) {
                    ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                    entrepreneurs_id = entreList.get(position).getEntrepreneurs_id();
                    topindex = position;
                    historyindex = -1;
                    mZFBSelectAdapter.setTopindex(topindex);
                    mZFBSelectAdapter.refreshListView(entreList);
                    if (historyList != null && historyList.size() > 0) {
                        historyAdapter.setHistoryindex(historyindex);
                        historyAdapter.refreshListView(historyList);
                    }
                    tvBottom.setBackgroundColor(getResources().getColor(R.color.red));
                }
            }
        });
        dateHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (historyList != null && historyList.size() > 0) {
                    ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                    entrepreneurs_id = historyList.get(position).getEntrepreneurs_id();
                    historyindex = position;
                    topindex = -1;
                    historyAdapter.setHistoryindex(historyindex);
                    historyAdapter.refreshListView(historyList);
                    if (entreList != null && entreList.size() > 0) {
                        mZFBSelectAdapter.setTopindex(topindex);
                        mZFBSelectAdapter.refreshListView(entreList);
                    }
                    tvBottom.setBackgroundColor(getResources().getColor(R.color.red));
                }
            }
        });
    }


    @Override
    public void initDataAfter() {
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        inputmoney = getIntent().getStringExtra("inputmoney");
        pay_method = getIntent().getIntExtra("pay_method", 4);
        mPresent.getRechargeHistory();
    }


    private void serach(String keyword) {
        mPresent.getRechargeList(keyword);
    }

    @Override
    protected PionZFBPresenterImp<PionZFBContract.View> createPresent() {
        return new PionZFBPresenterImp<>(mContext, this);
    }

    boolean searchclick = false;

    public void showDialog() {
        if (searchclick)
            LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void getwalletSuccess(PionZFBDataBean responseBean) {

    }

    ZFBSelectAdapter mZFBSelectAdapter;
    ArrayList<PionZFBSelectDataBean.PionZFBSBean> entreList = new ArrayList<>();

    ZFBHistoryAdapter historyAdapter;
    ArrayList<PionZFBHistoryDataBean.PionZFBSBean> historyList;

    @Override
    public void SelectSuccess(PionZFBSelectDataBean responseBean) {
        searchclick = false;
        topindex = -1;
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            entreList = responseBean.getData();
            mZFBSelectAdapter = new ZFBSelectAdapter(mActivity, entreList);
            mZFBSelectAdapter.setTopindex(topindex);
            dateListview.setAdapter(mZFBSelectAdapter);
        }
        setBtn(responseBean);
    }

    @Override
    public void historySuccess(PionZFBHistoryDataBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            layoutUser.setVisibility(View.VISIBLE);
            PionZFBHistoryDataBean.PionZFBSBean mEnergyAfterBean = responseBean.getData();
            int listType = mEnergyAfterBean.getListType();
            if (listType == 2) {
                tv_histitle.setText("推荐导师");
            } else {
                tv_histitle.setText("历史充值");
            }
            historyList = mEnergyAfterBean.getEntreList();
            historyAdapter = new ZFBHistoryAdapter(mActivity, historyList);
            historyAdapter.setHistoryindex(historyindex);
            dateHistory.setAdapter(historyAdapter);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }


    private void setBtn(PionZFBSelectDataBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            layoutNotdate.setVisibility(View.GONE);
            layoutUser.setVisibility(View.VISIBLE);
        } else {
            notDateCont.setText(responseBean.getMsg());
            layoutNotdate.setVisibility(View.VISIBLE);
            layoutUser.setVisibility(View.GONE);

            historyindex = -1;
            topindex = -1;
            if (entreList != null && entreList.size() > 0) {
                entreList.clear();
                mZFBSelectAdapter.setTopindex(topindex);
                mZFBSelectAdapter.refreshListView(entreList);
            }
            if (historyList != null && historyList.size() > 0) {
                historyAdapter.setHistoryindex(historyindex);
                historyAdapter.refreshListView(historyList);
            }
            tvBottom.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }

    @Override
    public void cancelPiPeiSuccess(ResponseBean responseBean) {

    }

    @Override
    public void PaySuccess(PayWXDataBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            showPayTypeDialog();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    MyDialog mPayTypeDialog;

    /**
     * 显示填写支付方式提示
     */
    public void showPayTypeDialog() {
        if (mPayTypeDialog == null) {
            mPayTypeDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_pion_ordertishi);// 创建Dialog并设置样式主题
            mPayTypeDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mPayTypeDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mPayTypeDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mPayTypeDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
            mPayTypeDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_ok = mPayTypeDialog.findViewById(R.id.tv_ok);
            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPayTypeDialog.dismiss();
                }
            });
            mPayTypeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Intent  intent = new Intent(mActivity, PionCZRecordActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    doFinish();
                }
            });
        } else {
            mPayTypeDialog.show();
        }
    }


    @Override
    public void ListError() {
    }


    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
            doFinish();
        }
        return false;
    }
}
