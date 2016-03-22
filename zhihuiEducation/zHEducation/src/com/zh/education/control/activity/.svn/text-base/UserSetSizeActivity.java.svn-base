package com.zh.education.control.activity;

import com.zh.education.R;
import com.zh.education.control.slider.SliderRangeBar;
import com.zh.education.control.slider.SliderRangeBar.OnRangeBarChangeListener;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.TextSizeUtils;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-13 下午2:47:25
 * @Description 类描述：设置-文字大小
 */
public class UserSetSizeActivity extends BaseActivity {

	private LinearLayout top_layout_back, top_head_layout;
	private TextView top_tv_name;
	private SliderRangeBar userset_slider_rangebar;
	int rightThumbIndex;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_layout_back:
			onBackPressed();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.userset_size);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		top_layout_back = (LinearLayout) findViewById(R.id.top_layout_back);
		top_head_layout = (LinearLayout) findViewById(R.id.top_head_layout);
		top_tv_name = (TextView) findViewById(R.id.top_tv_name);
		userset_slider_rangebar = (SliderRangeBar) findViewById(R.id.userset_slider_rangebar);
	}

	@Override
	protected void setListener() {
		top_layout_back.setOnClickListener(this);
		userset_slider_rangebar
				.setOnRangeBarChangeListener(new OnRangeBarChangeListener() {
					public void onIndexChangeListener(SliderRangeBar rangeBar,
							int leftThumbIndex, int rightThumbIndex) {
						if (rightThumbIndex < 0) {
							rightThumbIndex = 0;
							rangeBar.createThumbs(rightThumbIndex);
						}
						
						TextSizeUtils.getInstance().updateSize(context, rightThumbIndex);
						userset_slider_rangebar.createBar(leftThumbIndex,
								rightThumbIndex);
					}
				});
	}

	@Override
	protected void processLogic() {
		top_head_layout.setBackgroundColor(getResources().getColor(
				R.color.transparent));
		top_tv_name.setText(getResources().getString(R.string.user_set_size));
	}

	/**
	 * 监听返回键
	 */
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}
}
