package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.fragment.HelpWithFragmentNew;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.ContactListActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.IntoBagFuActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.ReceiveSuccessActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;


import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 福包首页
 */
public class FuBaoMenuActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_superablity)
    TextView tv_superablity;
    @BindView(R.id.phonetype_et_num)
    EditText phonetype_et_num;
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.layout_myfubao)
    LinearLayout layoutMyfubao;

    SendPayUtils mSendPayUtils;
    int super_ability = 270;
    String bag_num;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_send:
                bag_num = phonetype_et_num.getText().toString();
                if (!TextUtils.isEmpty(bag_num)) {
                    setPayDialog();
                }
                break;
            case R.id.layout_myfubao:
                intent = new Intent(mActivity, MineFubaoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
        return R.layout.fubao_main;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("福包");
        pagetopLayoutLeft.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        layoutMyfubao.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        super_ability = HelpWithFragmentNew.blessBagVal;
        tv_superablity.setText("超级生命能量   " + super_ability);
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

    MyDialog selectPayDialog;
    TextView tv_title;

    public void setPayDialog() {
        if (selectPayDialog == null) {
            selectPayDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_payfubao);// 创建Dialog并设置样式主题
            selectPayDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectPayDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectPayDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectPayDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectPayDialog.getWindow().setAttributes(p); //设置生效
            tv_title = selectPayDialog.findViewById(R.id.tv_title);
            TextView tv_off = selectPayDialog.findViewById(R.id.tv_off);
            TextView tv_sure = selectPayDialog.findViewById(R.id.tv_sure);
            tv_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPayDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPayDialog.dismiss();
                    sendFuBao();
                    ;
                }
            });
        } else {
            selectPayDialog.show();
        }
        String cont = "需要分享超级生命能量" + CommonUtil.getHtmlContentBig("#E95D1B", "" + super_ability * Integer.valueOf(bag_num));
        tv_title.setText(Html.fromHtml(cont));
    }

    public void sendFuBao() {
        showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.sendFubaoPackageNew(UserUtils.getUserId(mActivity), bag_num, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        dismissDialog();
                        String status = responseBean.getStatus();
                        if (status.equals("200")) {
                            Intent intent = new Intent(mActivity, ReceiveSuccessActivity.class);
                            intent.putExtra("pagetype", 1);
                            intent.putExtra("dataurl", responseBean.getData());
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            mActivity.startActivity(intent);
                        } else if (status.equals("405")) {
                            if (mSendPayUtils == null) {
                                mSendPayUtils = new SendPayUtils(mActivity);
                            }
                            mSendPayUtils.setAbilityDialog();
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
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
