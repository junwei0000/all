package com.bestdo.bestdoStadiums.control.walking;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Administrator
 * @date 创建时间：2017年5月22日 上午11:24:22
 * @Description 倒计时开始
 */
public class MyCountTimer extends CountDownTimer {
	public static final int TIME_COUNT = 5000;// 倒计时总时间为5S，时间防止从4s开始显示（以倒计时4s为例子）
	private TextView mTextView;
	Handler mHandler;
	int mHandlerID;

	/**
	 * 参数 millisInFuture 倒计时总时间（如30s,60S，120s等） 参数 countDownInterval
	 * 渐变时间（每次倒计1s） 参数 mTextView 点击的按钮(因为Button是TextView子类，为了通用我的参数设置为TextView）
	 */
	public MyCountTimer(long millisInFuture, long countDownInterval, TextView mTextView, Handler mHandler,
			int mHandlerID) {
		super(millisInFuture, countDownInterval);
		this.mTextView = mTextView;
		this.mHandler = mHandler;
		this.mHandlerID = mHandlerID;
	}

	/**
	 * 参数上面有注释
	 */
	public MyCountTimer(TextView mTextView) {
		super(TIME_COUNT, 1000);
		this.mTextView = mTextView;
	}

	/**
	 * 计时完毕时触发
	 */
	@Override
	public void onFinish() {

	}

	/**
	 * 计时过程显示
	 */
	@Override
	public void onTick(long millisUntilFinished) {
		System.err.println("millisUntilFinished/1000====" + millisUntilFinished / 1000);
		long millis = 1000;
		// 每隔一秒修改一次UI
		if (millisUntilFinished / 1000 == 4) {
			mTextView.setText("3");
		}
		if (millisUntilFinished / 1000 == 3) {
			mTextView.setText("2");
		}
		if (millisUntilFinished / 1000 == 2) {
			mTextView.setText("1");
		}
		if (millisUntilFinished / 1000 == 1) {
			mTextView.setText("GO");
			mHandler.sendEmptyMessageDelayed(mHandlerID, 1000);
		}

		// 设置透明度渐变动画
		final AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		// 设置动画持续时间
		alphaAnimation.setDuration(millis);
		mTextView.startAnimation(alphaAnimation);

	}
}
