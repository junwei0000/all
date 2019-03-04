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
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.MySharedPreferences;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.myview.MyDialog;


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
        if (status.equals("499")) {
            tishi = true;
            boolean IsLogout = MySharedPreferences.getInstance().getIsLogout();
            if (!IsLogout)
                UserLoginBack403Utils.getInstance().sendBroadcastLoginBack403();
        } else if (status.equals("500")) {
            tishi = true;
            UserLoginBack403Utils.getInstance().sendBroadcastUpdatePw500();
        }
        return tishi;
    }

    /**
     * 单点登录弹层
     */
    public void sendBroadcastLoginBack403() {
        Intent intents = new Intent();
        intents.setAction(ConstantManager.MAINMENU_ACTION);
        intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_LOGIN403);
        LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
    }

    /**
     * 无密码跳转设置密码
     */
    public void sendBroadcastUpdatePw500() {
        Intent intents = new Intent();
        intents.setAction(ConstantManager.MAINMENU_ACTION);
        intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_UPDATEPW500);
        LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
    }

    public void zhuXiao() {
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
        if (selectDialog == null || (selectDialog != null && !selectDialog.isShowing())) {
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
            TextView myexit_text_off = (TextView) selectDialog
                    .findViewById(R.id.myexit_text_off);
            TextView text_sure = (TextView) selectDialog
                    .findViewById(R.id.myexit_text_sure);
            myexit_text_off.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    zhuXiao();
                    selectDialog.dismiss();
                    selectDialog = null;
                }
            });
            text_sure.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    zhuXiao();
                    selectDialog.dismiss();
                    selectDialog = null;
                }
            });
        }
    }
}
