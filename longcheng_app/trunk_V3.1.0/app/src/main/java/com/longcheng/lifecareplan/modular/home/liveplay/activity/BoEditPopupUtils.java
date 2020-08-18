package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.app.Activity;
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
import android.widget.RelativeLayout;
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
    public static final int SENDLIWU = 11;
    public static final int CAMERA = 22;
    public static final int EXIT = 33;
    public static final int CONTENT = 44;
    boolean liveStatus = false;
    Handler mHandler;
    Activity mContext;

    public BoEditPopupUtils(Activity mContext, Handler mHandler, boolean liveStatus) {
        this.mContext = mContext;
        this.mHandler = mHandler;
        this.liveStatus = liveStatus;
    }

    private BaseSelectPopupWindow popWiw;// 回复的 编辑框

    public void popWiw(View view) {
        popWiw = new BaseSelectPopupWindow(mContext, R.layout.live_edit_data);
        popWiw.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popWiw.setFocusable(true);
        popWiw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWiw.setShowTitle(false);
        popWiw.getBackground().setAlpha(66);
        InputMethodManager im = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        im.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        popWiw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                ConfigUtils.getINSTANCE().closeSoftInput(mContext);
//          WindowManager.LayoutParams params = getWindow().getAttributes();
//          params.alpha = 1f;
//          getWindow().setAttributes(params);
            }
        });
        final RelativeLayout relat_pop = popWiw.getContentView().findViewById(
                R.id.relat_pop);
        relat_pop.getBackground().setAlpha(66);
        final EditText edt = popWiw.getContentView().findViewById(
                R.id.edt_content);
        final ImageView btn_liwu = popWiw.getContentView().findViewById(
                R.id.btn_liwu);
        final ImageView btn_camera = popWiw.getContentView().findViewById(
                R.id.btn_camera);
        final ImageView btn_exit = popWiw.getContentView().findViewById(
                R.id.btn_exit);
        if (liveStatus) {
            btn_liwu.setVisibility(View.GONE);
        } else {
            btn_camera.setVisibility(View.GONE);
        }
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
                        message.what = CONTENT;
                        message.obj = content;
                        mHandler.sendMessage(message);
                        message = null;
                        popWiw.dismiss();
                        ConfigUtils.getINSTANCE().closeSoftInput(mContext);
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
                    message.what = SENDLIWU;
                    mHandler.sendMessage(message);
                    message = null;
                    break;
                case R.id.btn_camera:
                    message.what = CAMERA;
                    mHandler.sendMessage(message);
                    message = null;
                    break;
                case R.id.btn_exit:
                    message.what = EXIT;
                    mHandler.sendMessage(message);
                    message = null;
                    break;
                default:
                    popWiw.dismiss();
                    ConfigUtils.getINSTANCE().closeSoftInput(mContext);
                    break;
            }
        }
    };
}
