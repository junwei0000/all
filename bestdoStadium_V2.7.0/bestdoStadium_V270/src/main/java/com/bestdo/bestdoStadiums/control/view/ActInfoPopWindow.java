package com.bestdo.bestdoStadiums.control.view;

import java.util.List;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.control.adapter.ActsPopAdapter;
import com.bestdo.bestdoStadiums.model.ActInfoMapper.ActInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * Created by YuHua on 2017/5/23 11:59 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：
 */

public class ActInfoPopWindow extends PopupWindow {
	private Activity activity;
	private Window window;
	private List<ActInfo> actInfos;
	private View mRoot;
	private View cancel;
	private ListView lv;
	private View.OnClickListener oncancel = new View.OnClickListener() {
		public void onClick(View v) {
			dismiss();
		}
	};

	private void setRootBg(float alpha) {
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = alpha;
		window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		window.setAttributes(lp);
	}

	public ActInfoPopWindow(Activity context, List<ActInfo> actInfos) {
		super(context);
		this.actInfos = actInfos;
		this.activity = context;
		window = CommonUtils.getInstance().nHomeActivity.getWindow();
		initView();
	}

	private void initView() {
		LayoutInflater inflater = LayoutInflater.from(activity);
		mRoot = inflater.inflate(R.layout.popw_act_info, null);
		setContentView(mRoot);
		setBackgroundDrawable(new ColorDrawable(0x00000000));
		cancel = mRoot.findViewById(R.id.img_cancel);
		lv = (ListView) mRoot.findViewById(R.id.lv);
		lv.setEmptyView(mRoot.findViewById(R.id.empty));
		setAnimationStyle(R.style.AnimBottom);
		setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

		setFocusable(true);
		cancel.setOnClickListener(oncancel);
		setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				setRootBg(1);
			}
		});
		lv.setAdapter(new ActsPopAdapter(mRoot.getContext(), actInfos));
		setRootBg(0.5f);
	}

	public void setPopListener(AdapterView.OnItemClickListener iPopListener) {
		lv.setOnItemClickListener(iPopListener);
	}

}
