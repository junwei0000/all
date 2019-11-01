package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.myview.BaseSelectPopupWindow;

/**
 * 作者：jun on
 * 时间：2018/8/30 16:05
 * 意图：
 */

public class BoEditPopupUtils {

    Handler mHandler;

    public BoEditPopupUtils(Handler mHandler) {
        this.mHandler = mHandler;
    }

    private BaseSelectPopupWindow popWiw;// 回复的 编辑框

    public void popWiw(Context mContext, View view) {
        popWiw = new BaseSelectPopupWindow(mContext, R.layout.live_edit_data);
        // popWiw.setOpenKeyboard(true);
        popWiw.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popWiw.setFocusable(true);
        popWiw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        popWiw.setShowTitle(false);
        InputMethodManager im = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        im.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        final EditText edt = (EditText) popWiw.getContentView().findViewById(
                R.id.edt_content);
        final ImageView btn_liwu = (ImageView) popWiw.getContentView().findViewById(
                R.id.btn_liwu);
        final ImageView btn_camera = (ImageView) popWiw.getContentView().findViewById(
                R.id.btn_camera);
        final ImageView btn_exit = (ImageView) popWiw.getContentView().findViewById(
                R.id.btn_exit);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(edt, 50);
        edt.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        edt.setImeOptions(EditorInfo.IME_ACTION_SEND);
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
                        message.obj = content;
                        mHandler.sendMessage(message);
                        message = null;
                    }
                    return true;
                }
                return false;
            }
        });
        btn_liwu.setOnClickListener(onClickListener);
        btn_camera.setOnClickListener(onClickListener);
        btn_exit.setOnClickListener(onClickListener);
        popWiw.setTitle("回复");
        popWiw.showAtLocation(view, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Message message = Message.obtain();
            switch (v.getId()) {
                case R.id.btn_liwu:
                    message.what = LivePushActivity.SENDLIWU;
                    mHandler.sendMessage(message);
                    message = null;
                    break;
                case R.id.btn_camera:
                    message.what = LivePushActivity.CAMERA;
                    mHandler.sendMessage(message);
                    message = null;
                    break;
                case R.id.btn_exit:
                    message.what = LivePushActivity.EXIT;
                    mHandler.sendMessage(message);
                    message = null;
                    break;
            }
        }
    };
}
