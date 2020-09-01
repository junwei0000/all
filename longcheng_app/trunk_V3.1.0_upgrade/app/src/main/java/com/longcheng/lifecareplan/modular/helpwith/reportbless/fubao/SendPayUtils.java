package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.bean.contactbean.PhoneBean;
import com.longcheng.lifecareplan.bean.contactbean.SelectPhone;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.ContactListActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.IntoBagFuActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity.PionZFBActivity;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SendPayUtils {


    Activity mActivity;
    MyDialog selectPayDialog;
    TextView tv_title;


    int super_ability;
    List<PhoneBean> phoneBeans;

    public void init(int super_ability, List<PhoneBean> phoneBeans) {
        this.super_ability = super_ability;
        this.phoneBeans = phoneBeans;
    }


    public SendPayUtils(Activity mActivity) {
        this.mActivity = mActivity;
    }

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
                    fuquansign(getPhoneMsg(phoneBeans));
                }
            });
        } else {
            selectPayDialog.show();
        }
        String cont = "需要分享超级生命能量" + CommonUtil.getHtmlContentBig("#E95D1B", "" + super_ability);
        tv_title.setText(Html.fromHtml(cont));
    }

    public void fuquansign(String bless_json) {
        Observable<ResponseBean> observable = Api.getInstance().service.sendFubaoPackage(UserUtils.getUserId(mActivity), bless_json, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        String status = responseBean.getStatus();
                        if (status.equals("200")) {
                            Intent intent = new Intent(mActivity, IntoBagFuActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("selectUser", getSelectPhoneMsg(phoneBeans));
                            intent.putExtras(bundle);
                            mActivity.startActivity(intent);
                        } else if (status.equals("405")) {
                            setAbilityDialog();
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    /***
     * 获取选中用户手机号用户名
     * @return
     */

    private ArrayList<Map<String, String>> selectphone = new ArrayList<>();

    /***
     * 获取选中用户手机号用户名
     * @return
     */
    private String getPhoneMsg(List<PhoneBean> phoneBeans) {
        String usephone = "";
        if (phoneBeans != null && !phoneBeans.isEmpty()) {
            selectphone.clear();
            for (PhoneBean phoneBean : phoneBeans) {
                if (phoneBean.isIsadd()) {
                    Map<String, String> maps = new HashMap<>();
                    maps.put("name", phoneBean.getName());
                    maps.put("phone", phoneBean.getPhone().replace(" ", ""));
                    selectphone.add(maps);
                }
            }
        }
        usephone = new Gson().toJson(selectphone).toString();
        Log.v("TAG", usephone);
        return usephone;
    }


    MyDialog selectabiltyDialog;

    public void setAbilityDialog() {
        if (selectabiltyDialog == null) {
            selectabiltyDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_tixiandel);// 创建Dialog并设置样式主题
            selectabiltyDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectabiltyDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectabiltyDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectabiltyDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectabiltyDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_title = selectabiltyDialog.findViewById(R.id.tv_title);
            TextView tv_off = selectabiltyDialog.findViewById(R.id.tv_off);
            TextView tv_sure = selectabiltyDialog.findViewById(R.id.tv_sure);
            tv_sure.setText("激活超能");
            tv_title.setText("您的超级生命能量不足");
            tv_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectabiltyDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectabiltyDialog.dismiss();
                    Intent intent = new Intent(mActivity, PionZFBActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mActivity.startActivity(intent);
                }
            });
        } else {
            selectabiltyDialog.show();
        }
    }
    private List<PhoneBean> selectphoneBeans = new ArrayList<>();
    private SelectPhone select = null;
    private SelectPhone getSelectPhoneMsg(List<PhoneBean> phoneBeans) {

        if (phoneBeans != null && !phoneBeans.isEmpty()) {
            selectphoneBeans.clear();
            for (PhoneBean phoneBean : phoneBeans) {
                if (phoneBean.isIsadd()) {
                    selectphoneBeans.add(phoneBean);
                }
            }
        }
        if (selectphoneBeans.size() > 0) {
            select = new SelectPhone();
            select.setData(selectphoneBeans);
        }
        return select;
    }

}
