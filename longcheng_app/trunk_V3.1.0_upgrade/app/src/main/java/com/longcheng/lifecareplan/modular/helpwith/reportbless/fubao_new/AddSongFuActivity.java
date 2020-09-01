package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao.SendPayUtils;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/***
 * 添加送福人
 */
public class AddSongFuActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_iv_left)
    ImageView pagetopIvLeft;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.btn_goaction)
    TextView btnGoAction;
    int super_ability = 270;
    String username;
    String userphone;
    SendPayUtils mSendPayUtils;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.pagetop_iv_left:
            case R.id.pagetop_layout_left:
                doFinish();
                break;

            case R.id.btn_goaction:
                // 送福操作提示用户消耗多少超能
                username = etName.getText().toString().trim();
                userphone = etPhone.getText().toString().trim();
                if (username == null || username.equals("")) {
                    ToastUtils.showToast("请添加有效接福人姓名");
                    return;
                }
                if (userphone == null || userphone.equals("")) {
                    ToastUtils.showToast("请添加接福人联系电话");
                    return;
                }
                // 添加提示框
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

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.songfu_layout;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopIvLeft.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        btnGoAction.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("添加送福人");
        super_ability = HelpWithFragmentNew.blessBagVal;
    }


}
