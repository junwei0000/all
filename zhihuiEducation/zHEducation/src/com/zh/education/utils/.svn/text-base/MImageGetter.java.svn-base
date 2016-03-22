package com.zh.education.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.widget.TextView;

public class MImageGetter implements ImageGetter {
	Context c;

	public MImageGetter(TextView text, Context c) {
		this.c = c;
	}

	public Drawable getDrawable(String source) {
		Drawable drawable = null;
		URL url;
		try {
			url = new URL(source);
			drawable = Drawable.createFromStream(url.openStream(), null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			TypedValue typedValue = new TypedValue();
			typedValue.density = TypedValue.DENSITY_DEFAULT;
			DisplayMetrics dm = c.getResources().getDisplayMetrics();
			int dwidth = dm.widthPixels - 10;// padding left + padding right
			float dheight = (float) drawable.getIntrinsicHeight()
					* (float) dwidth / (float) drawable.getIntrinsicWidth();
			int dh = (int) (dheight + 0.5);
			int wid = dwidth;
			int hei = dh;
			/*
			 * int wid = drawable.getIntrinsicWidth() > dwidth? dwidth:
			 * drawable.getIntrinsicWidth(); int hei =
			 * drawable.getIntrinsicHeight() > dh ? dh:
			 * drawable.getIntrinsicHeight();
			 */
			drawable.setBounds(0, 0, wid, hei);
			return drawable;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

}
