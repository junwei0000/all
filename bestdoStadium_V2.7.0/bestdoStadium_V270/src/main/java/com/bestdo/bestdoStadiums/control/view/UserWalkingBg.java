package com.bestdo.bestdoStadiums.control.view;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

public class UserWalkingBg extends View {

	private int top_fill_color;
	private int bottom_fill_color;
	private Paint paint;

	public UserWalkingBg(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.UserWalkingBg, defStyleAttr, 0);
		int i = a.getIndexCount();
		for (int j = 0; j < i; j++) {
			int att = a.getIndex(j);
			switch (att) {
			case R.styleable.UserWalkingBg_bottom_fill_color:
				bottom_fill_color = a.getColor(att, Color.WHITE);
				break;
			case R.styleable.UserWalkingBg_top_fill_color:
				top_fill_color = a.getColor(att, Color.BLUE);
				break;
			default:
				break;
			}
		}
		a.recycle();
		paint = new Paint();
	}

	public UserWalkingBg(Context context, AttributeSet attrs) {
		// super(context, attrs);
		this(context, attrs, 0);
	}

	public UserWalkingBg(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		DisplayMetrics dm = ConfigUtils.getInstance().getPhoneWidHeigth(getContext());
		int h = dm.heightPixels;
		int w = dm.widthPixels;
		paint.setAntiAlias(true); // 设置画笔为无锯齿
		paint.setColor(bottom_fill_color); // 设置画笔颜色
		paint.setStrokeWidth((float) 1.0); // 线宽
		paint.setStyle(Style.FILL);
		canvas.drawColor(top_fill_color); // 背景色
		canvas.drawCircle(w / 2, h, (float) (h / 1.5), paint);

	}

}
