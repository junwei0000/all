package com.longcheng.lifecareplan.utils.keyboard;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 作者:MarkMingShuai
 * 时间：2017/8/9 16:58
 * 邮箱:18610922052@163.com
 * 类的意图:软键盘管理
 */

public class AppKeyBoardMgr {

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     * @Author :MarkMingShuai
     * @DATE :2017/8/9 16:59
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     * @Author :MarkMingShuai
     * @DATE :2017/8/9 17:00
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        if (KeyBoard(mEditText)){
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        }
    }

    /**
     * 输入法是否显示
     *
     * @Author :MarkMingShuai
     * @DATE :2017/8/9 17:00
     * @Params
     */
    public static boolean KeyBoard(EditText edittext) {
        boolean bool = false;
        InputMethodManager imm = (InputMethodManager) edittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            bool = true;
        }
        return bool;
    }


    /**
     * 切换软键盘的状态
     * 如当前为收起变为弹出,若当前为弹出变为收起
     *
     * @Author :MarkMingShuai
     * @DATE :2017/8/9 17:01
     * @Params
     */
    public static void toggleKeybord(EditText edittext) {
        InputMethodManager inputMethodManager = (InputMethodManager)
                edittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 强制隐藏输入法键盘
     *
     * @Author :MarkMingShuai
     * @DATE :2017/8/9 17:01
     * @Params
     */
    public static void hideKeybord(EditText edittext) {
        InputMethodManager inputMethodManager = (InputMethodManager)
                edittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
        }
    }

    /**
     * 强制显示输入法键盘
     *
     * @Author :MarkMingShuai
     * @DATE :2017/8/9 17:02
     * @Params
     */
    public static void showKeybord(EditText edittext) {
        InputMethodManager inputMethodManager = (InputMethodManager)
                edittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(edittext, InputMethodManager.SHOW_FORCED);
    }

    /**
     * @Author :MarkMingShuai
     * @DATE :2017/8/16 15:09
     * @Params 设置EditText hint值
     */
    public static void sethint(String str,EditText editText){
        SpannableString ss = new SpannableString(str);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12,true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }

}
