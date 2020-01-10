package com.longcheng.lifecareplan.modular.mine.set.version;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.bottommenu.activity.BottomMenuActivity;
import com.longcheng.lifecareplan.modular.mine.set.bean.VersionAfterBean;
import com.longcheng.lifecareplan.modular.mine.set.bean.VersionDataBean;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * app更新
 */
public class AppUpdate {
    private Activity context;
    private MyDialog selectDialog;
    /**
     * 是否已经显示更新弹层
     */
    private boolean dialogstatus = false;

    /**
     * 数据正在加载中
     */
    private boolean loadDataStatus = false;


    /**
     * 方法说明：启动异步任务
     *
     * @param updateDirection
     */
    public void startUpdateAsy(Activity context, String updateDirection) {
        this.context = context;
        String verName = ConfigUtils.getINSTANCE().getVersionName(context);
        if (!TextUtils.isEmpty(verName) && !loadDataStatus && !dialogstatus) {
            loadDataStatus = true;
            getServerVerCode(verName, updateDirection);
        }
    }

    String level = "0";

    /**
     * 方法说明：与服务器交互获取当前程序版本号
     *
     * @param appVersion
     * @param updateDirection 请求路径
     * @return
     */
    private String getServerVerCode(String appVersion, String updateDirection) {
        if (!TextUtils.isEmpty(appVersion) && appVersion.contains(".")) {
            Log.e("appVersion", "appVersion=" + appVersion);
            appVersion = appVersion.replace(".", "_");
        }
        Log.e("showUpdaDialog", "appVersion new=" + appVersion);
        Observable<VersionDataBean> observable = Api.getInstance().service.updateVersion(appVersion);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<VersionDataBean>() {
                    @Override
                    public void accept(VersionDataBean responseBean) throws Exception {
                        String status = responseBean.getStatus();
                        loadDataStatus = false;
                        if (status.equals("200")) {
                            VersionAfterBean mVersionAfterBean = responseBean.getData();
                            if (mVersionAfterBean != null) {
                                String url = mVersionAfterBean.getUrl();
                                String version = mVersionAfterBean.getVersion();
                                level = mVersionAfterBean.getLevel();
                                Log.e("showUpdaDialog", "level=" + level);
                                if (level.equals("0")) {
                                    BottomMenuActivity.updatedialogstatus = false;
                                    if (TextUtils.isEmpty(updateDirection)) {
                                        ToastUtils.showToast("当前已为最新版本");
                                    }
                                } else {// 更新
                                    BottomMenuActivity.updatedialogstatus = true;
                                    dialogstatus = true;
                                    Intent intents = new Intent();
                                    intents.setAction(ConstantManager.MAINMENU_ACTION);
                                    intents.putExtra("type", ConstantManager.MAIN_ACTION_UpdateVerDisAllDialog);
                                    LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);

                                    ArrayList<String> descriptionList = mVersionAfterBean.getDescription();
                                    StringBuffer description = new StringBuffer();
                                    if (descriptionList != null && descriptionList.size() > 0) {
                                        for (String str : descriptionList) {
                                            description.append(str + "\n");
                                        }
                                    }
                                    defineUpdated(version, url, level, description.toString());
                                }
                            }
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Observable", "" + throwable.toString());
                        loadDataStatus = false;
                    }
                });
        return null;
    }


    /**
     * 方法说明：更新对话框提示 后台更新
     *
     * @param androidVersion
     * @param url
     * @param level          0：不用更新 1：必须更新 2：非必须更新
     * @param description
     */
    public void defineUpdated(String androidVersion, String url, String level, String description) {
        selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_updateversion);// 创建Dialog并设置样式主题
        selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        selectDialog.show();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
        selectDialog.getWindow().setAttributes(p); //设置生效
        selectDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface arg0) {
                dialogstatus = false;
            }
        });
        selectDialog.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (level.equals("1") && selectDialog.isShowing()) {
                    // 必须更新
                    return true;
                } else {
                    return false;
                }
            }
        });
        ImageView iv_top = (ImageView) selectDialog.findViewById(R.id.iv_top);
        iv_top.setLayoutParams(new LinearLayout.LayoutParams(d.getWidth() * 3 / 4, (int) (p.width * 0.64)));
        TextView tv_version = (TextView) selectDialog.findViewById(R.id.tv_version);
        TextView tv_cont = (TextView) selectDialog.findViewById(R.id.tv_cont);
        TextView tv_update = (TextView) selectDialog.findViewById(R.id.tv_update);
        TextView tv_xicai = (TextView) selectDialog.findViewById(R.id.tv_xicai);
        if (!TextUtils.isEmpty(androidVersion) && androidVersion.contains("_")) {
            androidVersion = androidVersion.replace("_", ".");
        }
        tv_version.setText("版本号：V" + androidVersion);
        tv_cont.setText(description);
        tv_update.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    Intent updateIntent = new Intent(context, UpdateService.class);
                    updateIntent.putExtra("url", url);
                    context.startService(updateIntent);
                    if (level.equals("1")) {
                        // 必须更新
                    } else {
                        selectDialog.dismiss();
                    }
                } catch (Exception e) {

                }

            }
        });
        tv_xicai.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (level.equals("1")) {
                    // 必须更新时点击取消退出程序
                    ActivityManager.getScreenManager().backHome(context);
                } else {
                    selectDialog.dismiss();
                }
            }
        });

    }
}
