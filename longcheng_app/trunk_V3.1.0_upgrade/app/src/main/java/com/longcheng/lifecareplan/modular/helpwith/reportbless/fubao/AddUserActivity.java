package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao;

import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.bean.contactbean.PhoneBean;
import com.longcheng.lifecareplan.modular.helpwith.fragment.HelpWithFragmentNew;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 添加送福人
 */
public class AddUserActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.et_cont)
    EditText etCont;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_send)
    TextView tvSend;

    SendPayUtils mSendPayUtils;
    int super_ability = 270;
    String username;
    String userphone;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_send:
                username = etCont.getText().toString().trim();
                userphone = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    ToastUtils.showToast("请添加接福人的姓名");
                    return;
                }
                if (TextUtils.isEmpty(userphone)) {
                    ToastUtils.showToast("请添加接福人的手机号码");
                    return;
                }
                if (mSendPayUtils == null) {
                    mSendPayUtils = new SendPayUtils(mActivity);
                }
                ArrayList<PhoneBean> phoneBeans = new ArrayList<>();
                phoneBeans.add(new PhoneBean(username, userphone,true));
                mSendPayUtils.init(super_ability, phoneBeans);
                mSendPayUtils.setPayDialog();
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fubao_adduser;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("添加接福人");
        pagetopLayoutLeft.setOnClickListener(this);
        tvSend.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etCont, 11);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etPhone, 11);
    }

    @Override
    public void initDataAfter() {
        super_ability = HelpWithFragmentNew.blessBagVal;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    public void ListSuccess(ReportDataBean responseBean, int backPage) {

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
