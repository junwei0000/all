package com.longcheng.lifecareplantv.modular.login.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.longcheng.lifecareplantv.R;
import com.longcheng.lifecareplantv.base.ActivityManager;
import com.longcheng.lifecareplantv.base.ExampleApplication;
import com.longcheng.lifecareplantv.utils.sharedpreferenceutils.MySharedPreferences;
import com.longcheng.lifecareplantv.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplantv.utils.myview.MyDialog;


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
                showDialogPromptReLogin(mActivity);
        }
        return tishi;
    }

    public void zhuXiao() {
        MySharedPreferences.getInstance().saveIsLogout(true);
        SharedPreferencesHelper.clear(ExampleApplication.getContext());
        ActivityManager.getScreenManager().popAllActivityOnlyMain();
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