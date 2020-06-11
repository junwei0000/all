package com.longcheng.lifecareplan.modular.index.login.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.bottommenu.activity.BottomMenuActivity;
import com.longcheng.lifecareplan.modular.im.IMTool;
import com.longcheng.lifecareplan.modular.mine.set.activity.NotServiceActivity;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.MySharedPreferences;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.youzan.androidsdk.YouzanSDK;

import io.rong.imlib.RongIMClient;


/**
 * 唯一设备登录提示
 *
 * @author jun
 */
public class UserLoginBack403Utils {
    private static UserLoginBack403Utils mUtils;

    public synchronized static UserLoginBack403Utils getInstance() {
        if (mUtils == null) {
            mUtils = new UserLoginBack403Utils();
        }
        return mUtils;
    }

    /**
     * 是否单点登录或修改密码
     *
     * @param status
     * @return
     */
    public boolean login499Or500(String status) {
        boolean tishi = false;
        Activity mActivity = ActivityManager.getScreenManager().getCurrentActivity();
        if (status.equals("499")) {
            tishi = true;
            boolean IsLogout = MySharedPreferences.getInstance().getIsLogout();
            if (!IsLogout)
                zhuXiao();
//                showDialogPromptReLogin(mActivity);
        } else if (status.equals("500")) {
            tishi = true;
            Intent intent = new Intent(mActivity, UpdatePwActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_UPDATEPW500);
            mActivity.startActivity(intent);
        } else if (status.equals("550")) {
            tishi = true;
            Intent intent = new Intent(mActivity, NotServiceActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mActivity.startActivity(intent);
        }
        return tishi;
    }

    public void zhuXiao() {
        IMTool.getINSTANCE().imToken="";
//        RongIMClient.getInstance().logout();
        YouzanSDK.userLogout(ExampleApplication.getContext());
        BottomMenuActivity.updatedialogstatus = false;
        MySharedPreferences.getInstance().saveIsLogout(true);
        SharedPreferencesHelper.clear(ExampleApplication.getContext());
        ActivityManager.getScreenManager().popAllActivityOnlyMain();
        logout();
    }

    public void logout() {
        Intent intents = new Intent();
        intents.setAction(ConstantManager.MAINMENU_ACTION);
        intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_BACK);
        LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
    }

    /**
     * 提示设备重新登录
     */
    MyDialog selectDialog;

    public void showDialogPromptReLogin(Activity mActivity) {
        if (mActivity != null && mActivity.isFinishing()) {
            return;
        }
        if (selectDialog != null && selectDialog.isShowing()) {
            return;
        }
        Intent intents = new Intent();
        intents.setAction(ConstantManager.MAINMENU_ACTION);
        intents.putExtra("type", ConstantManager.MAIN_ACTION_UpdateVerDisAllDialog);
        LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
        selectDialog = new MyDialog(mActivity, R.style.dialog,
                R.layout.dialog_logout);
        selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        selectDialog.show();
        selectDialog.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface arg0, int arg1,
                                 KeyEvent arg2) {
                return true;
            }
        });
        TextView myexit_text_off = selectDialog
                .findViewById(R.id.myexit_text_off);
        TextView text_sure = selectDialog
                .findViewById(R.id.myexit_text_sure);
        myexit_text_off.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
                zhuXiao();
                selectDialog = null;
            }
        });
        text_sure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
                zhuXiao();
                selectDialog = null;
            }
        });
    }

}
