/**
 * 
 */
package com.zh.education.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * @author      作者：zoc
 * @date        创建时间：2016-3-2 下午1:29:01
 * @Description 类描述：
 */
public class NewTextView extends EditText {
    public NewTextView(Context context) {
         super(context);
         // TODO Auto-generated constructor stub
    }

    public NewTextView(Context context, AttributeSet attrs) {
         super(context, attrs);
         // TODO Auto-generated constructor stub
    }

    public NewTextView(Context context, AttributeSet attrs, int defStyle) {
         super(context, attrs, defStyle);
         // TODO Auto-generated constructor stub
    }

    @Override
    protected boolean getDefaultEditable() {//禁止EditText被编辑
           return false;
    }
}