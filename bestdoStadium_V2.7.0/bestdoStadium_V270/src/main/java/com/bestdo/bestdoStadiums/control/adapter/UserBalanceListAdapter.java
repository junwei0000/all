package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.control.activity.UserBalanceActivity;
import com.bestdo.bestdoStadiums.model.CampaignGetClubInfo;
import com.bestdo.bestdoStadiums.model.UserBalanceListInfo;
import com.bestdo.bestdoStadiums.model.UserBuyMeberInfo;
import com.bestdo.bestdoStadiums.model.UserBuyMeberListInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.SupplierEditText;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午4:24:05
 * @Description 类描述：
 */
public class UserBalanceListAdapter extends BaseAdapter {

	Activity context;
	ArrayList<UserBalanceListInfo> aList;
	public ViewHolder viewHolder;
	Handler mHandler;
	int mHandlerID;

	public UserBalanceListAdapter(Activity context, ArrayList<UserBalanceListInfo> mList, Handler mHandler,
			int mHandlerID) {
		this.aList = mList;
		this.context = context;
		this.mHandler = mHandler;
		this.mHandlerID = mHandlerID;
	}

	public ArrayList<UserBalanceListInfo> getaList() {
		return aList;
	}

	public void setaList(ArrayList<UserBalanceListInfo> aList) {
		this.aList = aList;
	}

	@Override
	public int getCount() {
		return aList.size();
	}

	@Override
	public Object getItem(int position) {
		return aList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.user_balance_item, null);
			viewHolder.balanceitem_tv_price = (TextView) view.findViewById(R.id.balanceitem_tv_price);
			viewHolder.balanceitem_ev_price = (EditText) view.findViewById(R.id.balanceitem_ev_price);
			viewHolder.balanceitem_tv_month = (TextView) view.findViewById(R.id.balanceitem_tv_month);
			viewHolder.balanceitem_layout = (LinearLayout) view.findViewById(R.id.balanceitem_layout);
			viewHolder.balanceitem_layout_price = (LinearLayout) view.findViewById(R.id.balanceitem_layout_price);
			viewHolder.balanceitem_iv_select = (ImageView) view.findViewById(R.id.balanceitem_iv_select);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		UserBalanceListInfo mInfo = aList.get(position);
		mInfo.balanceitem_ev_price = viewHolder.balanceitem_ev_price;
		viewHolder.balanceitem_layout_price.setVisibility(View.GONE);
		viewHolder.balanceitem_iv_select.setVisibility(View.GONE);
		if (mInfo.isSelect()) {
			viewHolder.balanceitem_iv_select.setVisibility(View.VISIBLE);
			viewHolder.balanceitem_layout.setBackgroundResource(R.drawable.corners_buymeber_btnbg_blue);
		} else {
			viewHolder.balanceitem_layout.setBackgroundResource(R.drawable.corners_btnbg);
		}
		String rechargeMoney = mInfo.getRechargeMoney();
		rechargeMoney = PriceUtils.getInstance().formatFloatNumber(Double.parseDouble(rechargeMoney));
		viewHolder.balanceitem_tv_price.setText("¥" + rechargeMoney + "元");
		String memo = mInfo.getMemo();
		String presentTitle = mInfo.getPresentTitle();
		String title = "<font color='#F40A0A'>" + memo + "</font>" + presentTitle;
		if (!TextUtils.isEmpty(rechargeMoney) && Double.parseDouble(rechargeMoney) == 1) {
			viewHolder.balanceitem_tv_price.setText("其他金额");
			title = "首次充值任意金额 <font color='#F40A0A'>" + memo + presentTitle + "</font>";
			viewHolder.balanceitem_layout_price.setVisibility(View.VISIBLE);
		}
		viewHolder.balanceitem_tv_month.setText(Html.fromHtml(title));
		dataResolved(position);
		if (otherMoneystatus(position)) {
			System.err.println("4         " + otherMoneySelectNownum);

			viewHolder.balanceitem_ev_price.requestFocus();
		}
		viewHolder.balanceitem_ev_price.setTag(position);
		viewHolder.balanceitem_ev_price.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				int position = (Integer) v.getTag();
				if (hasFocus && otherMoneySelectNownum == 0) {
					otherMoneySelectNownum++;
					System.err.println("3         " + otherMoneySelectNownum);
					Message msg = new Message();
					msg.arg1 = position;
					msg.what = mHandlerID;
					mHandler.sendMessage(msg);
					msg = null;
				}

			}
		});
		viewHolder.balanceitem_layout.setTag(position);
		viewHolder.balanceitem_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.err.println("1");
				int position = (Integer) v.getTag();
				aList.get(position).setSelect(true);
				if (otherMoneystatus(position)) {
					otherMoneySelectNownum++;
					String InputMoney = aList.get(position).getInputMoney();
					System.err.println("InputMoney="+InputMoney);
				} else {
					String rechargeMoney = aList.get(position).getRechargeMoney();
					aList.get(position).setInputMoney(rechargeMoney);
					CommonUtils.getInstance().closeSoftInput(context);
					otherMoneySelectNownum = 0;
				}
				Message msg = new Message();
				msg.arg1 = position;
				msg.what = mHandlerID;
				mHandler.sendMessage(msg);
				msg = null;
			}
		});
		return view;

	}

	int otherposition;//其他金额item position
	int otherMoneySelectNownum;//选中次数
	/**
	 * 其他金额item选中状态
	 * @param position
	 * @return
	 */
	private boolean otherMoneystatus(int position) {
		otherposition=0;
		if (aList.get(position).isSelect() && !TextUtils.isEmpty(aList.get(position).getRechargeMoney())
				&& Double.parseDouble(aList.get(position).getRechargeMoney()) == 1) {
			otherposition = position;
			return true;
		}
		return false;
	}

	/**
	 * 防止数据错位;
	 * 
	 * @param mInfo
	 */
	private void dataResolved(int position) {
		if (otherMoneystatus(position)) {
			// 大部分情况下，getview中有if必须有else
			if (!TextUtils.isEmpty(aList.get(position).getInputMoney())) {
				viewHolder.balanceitem_ev_price.setText(aList.get(position).getInputMoney());
				viewHolder.balanceitem_ev_price
						.setSelection(viewHolder.balanceitem_ev_price.getText().toString().length());
			} else {
				viewHolder.balanceitem_ev_price.setText("");
			}
		} else {
			viewHolder.balanceitem_ev_price.setText("");
		}
//		viewHolder.balanceitem_ev_price.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
//		 
//		  //设置字符过滤
//		viewHolder.balanceitem_ev_price.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8) {
//		     @Override
//		     public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//	        if(source.equals(".") && dest.toString().length() == 0){
//	           return "0.";
//	         }
//		        if(dest.toString().contains(".")){
//		             int index = dest.toString().indexOf(".");
//		             int length = dest.toString().substring(index).length();
//		             if(length == 3){
//		                 return "";
//	              } 
//		         }
//		         return null;
//		      }
//		  }});
		viewHolder.balanceitem_ev_price.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if(otherMoneystatus(otherposition)){
					Message msg = new Message();
					msg.obj = s;
					msg.arg1 = otherposition;
					msg.what = UserBalanceActivity.INPUT;
					mHandler.sendMessage(msg);
					msg = null;
				}
			}

		});
	}
	class ViewHolder {
		TextView balanceitem_tv_price;
		EditText balanceitem_ev_price;
		TextView balanceitem_tv_month;
		ImageView balanceitem_iv_select;
		LinearLayout balanceitem_layout;
		LinearLayout balanceitem_layout_price;
	}
}
