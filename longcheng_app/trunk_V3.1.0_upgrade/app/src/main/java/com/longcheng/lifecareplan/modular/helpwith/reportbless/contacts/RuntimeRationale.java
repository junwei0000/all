/*
 * Copyright © Zhenjie Yan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

/**
 * Created by Zhenjie Yan on 2018/1/1.
 */
public final class RuntimeRationale implements Rationale<List<String>> {
    MyDialog permissionRuntimeDialog;

    TextView tv_sure, tv_title;

    @Override
    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_rationale,
                TextUtils.join("\n", permissionNames));
        if (permissionRuntimeDialog == null) {
            permissionRuntimeDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_tixiandel);// 创建Dialog并设置样式主题
            permissionRuntimeDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = permissionRuntimeDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            permissionRuntimeDialog.show();
//            WindowManager m = context.getApplicationContext().getWindowManager();
            WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = permissionRuntimeDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            permissionRuntimeDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_off = permissionRuntimeDialog.findViewById(R.id.tv_off);
            tv_title = permissionRuntimeDialog.findViewById(R.id.tv_title);
            tv_sure = permissionRuntimeDialog.findViewById(R.id.tv_sure);
            tv_title.setText(message);
            tv_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    permissionRuntimeDialog.dismiss();
                    executor.execute();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    permissionRuntimeDialog.dismiss();
                    executor.cancel();
                }
            });
        } else {
            permissionRuntimeDialog.show();
        }
//        new AlertDialog.Builder(context).setCancelable(false)
//            .setTitle(R.string.title_dialog)
//            .setMessage(message)
//            .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    executor.execute();
//                }
//            })
//            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    executor.cancel();
//                }
//            })
//            .show();
    }
}