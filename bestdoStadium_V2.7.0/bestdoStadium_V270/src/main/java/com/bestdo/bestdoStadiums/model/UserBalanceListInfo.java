package com.bestdo.bestdoStadiums.model;

import com.bestdo.bestdoStadiums.control.adapter.UserOrderAdapter.ViewHolder;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class UserBalanceListInfo {
	String id;
	String rechargeMoney;
	String inputMoney;
	String presentTitle;
	String memo;
	boolean select;
	public EditText balanceitem_ev_price;
	public UserBalanceListInfo(String id, String rechargeMoney, String presentTitle, String memo, boolean select) {
		super();
		this.id = id;
		this.rechargeMoney = rechargeMoney;
		this.presentTitle = presentTitle;
		this.memo = memo;
		this.select = select;
	}

	public String getInputMoney() {
		return inputMoney;
	}

	public void setInputMoney(String inputMoney) {
		this.inputMoney = inputMoney;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRechargeMoney() {
		return rechargeMoney;
	}

	public void setRechargeMoney(String rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}

	public String getPresentTitle() {
		return presentTitle;
	}

	public void setPresentTitle(String presentTitle) {
		this.presentTitle = presentTitle;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
