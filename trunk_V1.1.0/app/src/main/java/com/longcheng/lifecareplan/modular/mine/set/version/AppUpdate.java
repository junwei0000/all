package com.longcheng.lifecareplan.modular.mine.set.version;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.modular.mine.set.bean.VersionAfterBean;
import com.longcheng.lifecareplan.modular.mine.set.bean.VersionDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * app更新
 */
public class AppUpdate {
    private Activity context;
    private MyDialog selectDialog;
    String dialogstatus = "";

    public AppUpdate(Activity context) {
        this.context = context;
    }

    /**
     * 方法说明：启动异步任务
     *
     * @param updateDirection
     */
    public void startUpdateAsy(String updateDirection) {
        String verName = ConfigUtils.getINSTANCE().getVerCode(context);
        if (!TextUtils.isEmpty(verName) && !dialogstatus.equals("show")) {
            getServerVerCode(verName, updateDirection);
            dialogstatus = "show";
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
        Log.e("appVersion", "appVersion new=" + appVersion);
        Observable<VersionDataBean> observable = Api.getInstance().service.updateVersion(appVersion);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<VersionDataBean>() {
                    @Override
                    public void accept(VersionDataBean responseBean) throws Exception {
                        String status = responseBean.getStatus();
                        if (status.equals("200")) {
                            VersionAfterBean mVersionAfterBean = responseBean.getData();
                            if (mVersionAfterBean != null) {
                                String url = mVersionAfterBean.getUrl();
                                String version = mVersionAfterBean.getVersion();
                                level = mVersionAfterBean.getLevel();
                                if (level.equals("0")) {
                                    if (TextUtils.isEmpty(updateDirection)) {
                                        ToastUtils.showToast("当前已为最新版本");
                                    }
                                } else {// 更新
                                    defineUpdated(version, url, level, "");
                                }
                            }
                        }
                        getIsOpenNotification(updateDirection);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Observable", "" + throwable.toString());
                        getIsOpenNotification(updateDirection);
                    }
                });
        return null;
    }

    private void getIsOpenNotification(String updateDirection) {
        //首页 最新版本时提示
        if (!TextUtils.isEmpty(updateDirection) && level.equals("0")) {
            NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            boolean isOpened = manager.areNotificationsEnabled();
            Log.e("getIsOpenNotification", "isOpened=" + isOpened);
            if (!isOpened) {
                showOpenNotificationWindow();
            }
        }
    }

    MyDialog OpenNotificationDialog;

    private void showOpenNotificationWindow() {
        if (OpenNotificationDialog == null) {
            OpenNotificationDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_openotification);// 创建Dialog并设置样式主题
            OpenNotificationDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = OpenNotificationDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            OpenNotificationDialog.show();
            WindowManager m = context.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = OpenNotificationDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 5 / 6; //宽度设置为屏幕
            OpenNotificationDialog.getWindow().setAttributes(p); //设置生效

            LinearLayout layout_cancel = (LinearLayout) OpenNotificationDialog.findViewById(R.id.layout_cancel);
            TextView btn_ok = (TextView) OpenNotificationDialog.findViewById(R.id.btn_ok);
            layout_cancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    OpenNotificationDialog.dismiss();
                }
            });
            btn_ok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    OpenNotificationDialog.dismiss();
                    String pkg = context.getApplicationContext().getPackageName();
                    // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", pkg, null);
                    intent.setData(uri);
                    context.startActivity(intent);
                }
            });

        } else {
            OpenNotificationDialog.show();
        }
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
        p.height = (int) (p.width * 1.36);
        selectDialog.getWindow().setAttributes(p); //设置生效
        selectDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface arg0) {
                dialogstatus = "";
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
        TextView tv_cont = (TextView) selectDialog.findViewById(R.id.tv_cont);
        TextView tv_update = (TextView) selectDialog.findViewById(R.id.tv_update);
        TextView tv_xicai = (TextView) selectDialog.findViewById(R.id.tv_xicai);
        if (!TextUtils.isEmpty(androidVersion) && androidVersion.contains("_")) {
            androidVersion = androidVersion.replace("_", ".");
        }
        tv_cont.setText("发现新版本：V" + androidVersion);
        tv_update.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent updateIntent = new Intent(context, UpdateService.class);
                updateIntent.putExtra("url", url);
                context.startService(updateIntent);
                if (level.equals("1")) {
                    // 必须更新
                } else {
                    selectDialog.dismiss();
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
