package com.longcheng.lifecareplan.modular.im.activity;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class EditMoBanActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_commit)
    TextView btnCommit;
    @BindView(R.id.tv_num)
    TextView tv_num;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.btn_commit:
                String cont = etContent.getText().toString();
                if (!TextUtils.isEmpty(cont)) {
                    editMoBan(cont);
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
        return R.layout.im_editmoban;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("编辑模板");
        pagetopLayoutLeft.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etContent, 200);
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etContent != null) {
                    if (!TextUtils.isEmpty(etContent.getText().toString())) {
                        btnCommit.setBackgroundResource(R.drawable.corners_bg_login);
                        tv_num.setText(etContent.getText().length() + "/200");
                    } else {
                        tv_num.setText("0/200");
                        btnCommit.setBackgroundResource(R.drawable.corners_bg_logingray);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @Override
    public void initDataAfter() {
    }


    public void editMoBan(String cont) {
        String use_id = UserUtils.getUserId(mContext);
        Observable<ResponseBean> observable = Api.getInstance().service.addQuickMoBan(
                use_id, cont, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            ToastUtils.showToast(responseBean.getMsg());
                            doFinish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Observable", throwable.getMessage());
                    }
                });
    }


}
