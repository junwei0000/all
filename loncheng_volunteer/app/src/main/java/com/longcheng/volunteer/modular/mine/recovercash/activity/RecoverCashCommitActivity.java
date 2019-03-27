package com.longcheng.volunteer.modular.mine.recovercash.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.ActivityManager;
import com.longcheng.volunteer.base.BaseActivityMVP;
import com.longcheng.volunteer.modular.index.login.bean.SendCodeBean;
import com.longcheng.volunteer.modular.mine.recovercash.bean.AcountInfoDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.myview.SupplierEditText;
import com.longcheng.volunteer.widget.dialog.LoadingDialogAnim;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;


/**
 * 提现-银行卡信息
 */
public class RecoverCashCommitActivity extends BaseActivityMVP<RecoverCashContract.View, RecoverCashPresenterImp<RecoverCashContract.View>> implements RecoverCashContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;

    @BindView(R.id.cash_tv_banknametitle)
    TextView cashTvBanknametitle;
    @BindView(R.id.cash_et_bankname)
    SupplierEditText cashEtBankname;
    @BindView(R.id.cash_et_bankfullname)
    SupplierEditText cashEtBankfullname;
    @BindView(R.id.cash_tv_kanumtitle)
    TextView cashTvKanumtitle;
    @BindView(R.id.cash_et_kanum)
    SupplierEditText cashEtKanum;
    @BindView(R.id.cash_et_name)
    SupplierEditText cashEtName;
    @BindView(R.id.cash_et_code)
    SupplierEditText cashEtCode;
    @BindView(R.id.cash_tv_getcode)
    TextView cashTvGetcode;
    @BindView(R.id.btn_commit)
    TextView btnCommit;

    private String service_charge, apply_withdrawals_cash, money;
    private String phone, user_id;
    private String bankName;
    private String bankBranchName;
    private String cardnum;
    private String accountName;
    private String code;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.cash_tv_getcode:
                if (!codeSendingStatus) {
                    codeSendingStatus = true;
                    mPresent.pCashSendCode(user_id, phone);
                }
                break;
            case R.id.btn_commit:
                bankName = cashEtBankname.getText().toString().trim();
                bankBranchName = cashEtBankfullname.getText().toString().trim();
                cardnum = cashEtKanum.getText().toString().trim();
                accountName = cashEtName.getText().toString().trim();
                code = cashEtCode.getText().toString().trim();
                if (!TextUtils.isEmpty(bankName) && !TextUtils.isEmpty(bankBranchName)
                        && !TextUtils.isEmpty(cardnum) && !TextUtils.isEmpty(accountName) && !TextUtils.isEmpty(code)) {
                    mPresent.tiXian(user_id,
                            bankName, bankBranchName, cardnum, accountName, code,
                            apply_withdrawals_cash, service_charge, money);
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
        return R.layout.recovercash_commit;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView(View view) {
        pageTopTvName.setText(getString(R.string.recovercash_tilte));
        setOrChangeTranslucentColor(toolbar, null);
        cashTvBanknametitle.setLetterSpacing(0.3f);
        cashTvKanumtitle.setLetterSpacing(0.3f);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        cashTvGetcode.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        cashEtBankname.addTextChangedListener(mTextWatcher);
        cashEtBankfullname.addTextChangedListener(mTextWatcher);
        cashEtKanum.addTextChangedListener(mTextWatcher);
        cashEtName.addTextChangedListener(mTextWatcher);
        cashEtCode.addTextChangedListener(mTextWatcher);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(cashEtBankname, 50);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(cashEtBankfullname, 50);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(cashEtKanum, 50);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(cashEtName, 20);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(cashEtCode, 6);
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence source, int start, int before, int count) {
            bankName = cashEtBankname.getText().toString().trim();
            bankBranchName = cashEtBankfullname.getText().toString().trim();
            cardnum = cashEtKanum.getText().toString().trim();
            accountName = cashEtName.getText().toString().trim();
            code = cashEtCode.getText().toString().trim();
            if (!TextUtils.isEmpty(bankName) && !TextUtils.isEmpty(bankBranchName)
                    && !TextUtils.isEmpty(cardnum) && !TextUtils.isEmpty(accountName) && !TextUtils.isEmpty(code)) {
                btnCommit.setBackgroundResource(R.drawable.corners_bg_login);
            } else {
                btnCommit.setBackgroundResource(R.drawable.corners_bg_logingray);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public void initDataAfter() {
        btnCommit.setBackgroundResource(R.drawable.corners_bg_logingray);
        phone = (String) SharedPreferencesHelper.get(mContext, "phone", "");
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        Intent intent = getIntent();
        service_charge = intent.getStringExtra("service_charge");
        apply_withdrawals_cash = intent.getStringExtra("apply_withdrawals_cash");
        money = intent.getStringExtra("money");
    }


    @Override
    protected RecoverCashPresenterImp<RecoverCashContract.View> createPresent() {
        return new RecoverCashPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    @Override
    public void ListSuccess(AcountInfoDataBean responseBean) {

    }

    @Override
    public void getCodeSuccess(SendCodeBean responseBean) {
        codeSendingStatus = false;
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            Message msg = new Message();
            msg.what = Daojishistart;
            mHandler.sendMessage(msg);
            msg = null;
        }
    }

    @Override
    public void TiXianSuccess(EditListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getMsg());
            ActivityManager.getScreenManager().popAllActivityOnlyMain();
            doFinish();
        }
    }

    @Override
    public void ListError() {
    }

    /**
     * 是否正在请求发送验证码，防止多次重发
     */
    boolean codeSendingStatus = false;
    private MyHandler mHandler = new MyHandler();
    private TimerTask timerTask;
    private final int Daojishistart = 0;
    private final int Daojishiover = 1;
    private int count;

    private void daojishi() {
        final long nowTime = System.currentTimeMillis();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                count--;
                Message msg = new Message();
                msg.what = Daojishiover;
                msg.arg1 = count;
                msg.obj = nowTime;
                mHandler.sendMessage(msg);
                if (count <= 0) {
                    this.cancel();
                }
                msg = null;
            }
        };
        new Timer().schedule(timerTask, 0, 1000);
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Daojishistart:
                    cashTvGetcode.setEnabled(false);
                    count = 60;
                    daojishi();
                    break;
                case Daojishiover:
                    if (msg.arg1 < 10) {
                        cashTvGetcode.setText("0" + msg.arg1 + getString(R.string.tv_codeunit));
                    } else {
                        cashTvGetcode.setText(msg.arg1 + getString(R.string.tv_codeunit));
                    }
                    if (msg.arg1 <= 0) {
                        cashTvGetcode.setTextColor(getResources().getColor(R.color.blue));
                        cashTvGetcode.setEnabled(true);
                        cashTvGetcode.setText(getString(R.string.code_get));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerTask != null) {
            timerTask.cancel();
            mHandler.removeCallbacks(timerTask);
        }
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
