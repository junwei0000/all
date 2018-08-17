package com.KiwiSports.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-9-26 下午3:13:16
 * @Description 类描述：自定义弹框
 */
public class MyDialog extends AlertDialog {

	private int layout;

	protected MyDialog(Context context) {
		super(context);
	}

	public MyDialog(Context context, int theme, int layout) {
		super(context, theme);
		this.layout = layout;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout);
	}

}
