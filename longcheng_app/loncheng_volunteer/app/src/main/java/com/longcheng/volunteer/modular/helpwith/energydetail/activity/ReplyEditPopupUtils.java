package com.longcheng.volunteer.modular.helpwith.energydetail.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.myview.BaseSelectPopupWindow;

/**
 * 作者：jun on
 * 时间：2018/8/30 16:05
 * 意图：
 */

public class ReplyEditPopupUtils {

    Handler mHandler;
    int mHandlerID;

    public ReplyEditPopupUtils(Handler mHandler, int mHandlerID) {
        this.mHandlerID = mHandlerID;
        this.mHandler = mHandler;
    }

    private BaseSelectPopupWindow popWiw;// 回复的 编辑框

    public void popWiw(Context mContext, View view) {
        popWiw = new BaseSelectPopupWindow(mContext, R.layout.edit_data);
        // popWiw.setOpenKeyboard(true);
        popWiw.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popWiw.setFocusable(true);
        popWiw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWiw.setShowTitle(false);
        InputMethodManager im = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        im.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        final Button send = (Button) popWiw.getContentView().findViewById(
                R.id.btn_send);
        final EditText edt = (EditText) popWiw.getContentView().findViewById(
                R.id.edt_content);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(edt, 50);
        edt.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        edt.setImeOptions(EditorInfo.IME_ACTION_SEND);
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (TextUtils.isEmpty(edt.getText())) {
                    send.setEnabled(false);
                } else {
                    send.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (!TextUtils.isEmpty(edt.getText().toString().trim())) {
                        String content = edt.getText().toString().trim();
                        // 提交内容
                        Message message = new Message();
                        message.what = mHandlerID;
                        message.obj = content;
                        mHandler.sendMessage(message);
                        message = null;
                        popWiw.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edt.getText().toString().trim())) {
                    // 提交内容
                    String content = edt.getText().toString().trim();
                    Message message = new Message();
                    message.what = mHandlerID;
                    message.obj = content;
                    mHandler.sendMessage(message);
                    message = null;
                    popWiw.dismiss();
                }
            }
        });

        popWiw.setTitle("回复");
        popWiw.showAtLocation(view, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


}
