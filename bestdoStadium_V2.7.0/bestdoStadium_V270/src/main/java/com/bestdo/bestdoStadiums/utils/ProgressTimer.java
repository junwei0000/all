package com.bestdo.bestdoStadiums.utils;

import java.util.Timer;
import java.util.TimerTask;

import com.bestdo.bestdoStadiums.control.adapter.UserOrderAdapter.ViewHolder;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-27 下午5:22:21
 * @Description 类描述：待付款倒计时
 */
public class ProgressTimer {

	private ViewHolder mViewHolder;
	private long mhour, mmin, msecond;// 分钟，秒

	public ProgressTimer(View view, Timer timer) {
		mViewHolder = (ViewHolder) view.getTag();
		mhour = mViewHolder.shengYuTime[0];
		mmin = mViewHolder.shengYuTime[1];
		msecond = mViewHolder.shengYuTime[2];
		timer.schedule(task, 1000);
	}

	/**
	 * 显示剩余时间
	 */

	private final int UPDATESHOWTEXT = 1;
	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATESHOWTEXT:
				try {
					String text = (String) msg.obj;
					mViewHolder.userorder_item_tv_waipaytimer.setText(text);
				} catch (Exception e) {
					Log.e("mView.progressBar_time.setText(msg.arg1", "mView.progressBar_time.setText(msg.arg1");
				}
				break;
			}

			super.handleMessage(msg);
		}
	};

	/**
	 * 倒计时计算
	 */
	private void ComputeTime() {
		if (msecond <= 0) {
			msecond = 59;
			if (mmin <= 0) {
				mmin = 59;
				if (mhour > 0) {
					mhour--;
				}
			} else {
				mmin--;
			}
		} else {
			msecond--;
		}
		mViewHolder.shengYuTime[0] = mhour;
		mViewHolder.shengYuTime[1] = mmin;
		mViewHolder.shengYuTime[2] = msecond;
		mViewHolder.time = (int) (mhour * 60 * 60 + mmin * 60 + msecond);
	}

	/**
	 * 倒计时
	 */
	TimerTask task = new TimerTask() {
		public void run() {
			while (mViewHolder.stateChange) {
				try {
					mhour = mViewHolder.shengYuTime[0];
					mmin = mViewHolder.shengYuTime[1];
					msecond = mViewHolder.shengYuTime[2];
					if (mhour <= 0 && mmin <= 0 && msecond <=1) {
						/**
						 * 倒计时为0时，到期处理
						 */
						mViewHolder.stateChange = false;
						break;
					}
					String remainingTime = DatesUtils.getInstance().getCountdownTimes(mViewHolder.shengYuTime);
					Message message = myHandler.obtainMessage();
					message.what = UPDATESHOWTEXT;
					message.obj = remainingTime;
					myHandler.sendMessage(message);
					message = null;
					ComputeTime();
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	};
}
