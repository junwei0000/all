package com.bestdo.bestdoStadiums.control.activity;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.R;

/**
 * 
 * @author jun
 * 
 */
public class UserOrderCancelUitls {

	/**
	 * 取消订单Dialog
	 * 
	 * @param mHandler
	 * @param context
	 * @param oid
	 * @param uid
	 * @param index
	 */
	MyDialog selectDialog;

	public void showOffOrdersDialog(final Handler mHandler, Context context, final int CANCELORDER) {
		if (selectDialog == null) {
			selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_cancelorder);
			selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
			selectDialog.show();
			TextView myexit_text_title = (TextView) selectDialog.findViewById(R.id.myexit_text_title);
			TextView text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);// 确定
			final TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);// 取消
			myexit_text_title.setText("取消订单后，您预订的场地将不予保留。如有问题可直接拨打客服电话400-684-1808");
			text_sure.setText("稍后再说");
			text_off.setText("确认取消");
			text_off.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					selectDialog.dismiss();
					mHandler.sendEmptyMessage(CANCELORDER);
				}
			});
			text_sure.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					selectDialog.dismiss();
				}
			});
		} else {
			selectDialog.show();
		}
	}
}
