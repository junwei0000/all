package com.bestdo.bestdoStadiums.control.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.control.activity.WalkActivity;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Config;

/**
 * Created by YuHua on 2017/5/24 20:57 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：
 */

public class BaseFragment extends Fragment {

	protected ProgressDialog mDialog;
	private String clazzName = getClass().getSimpleName();

	protected void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(activity);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void dismissDialog() {
		CommonUtils.getInstance().setOnDismissDialog(mDialog);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Config.d(clazzName + "  onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Config.d(clazzName + "  onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroy() {
		Config.d(clazzName + "  onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		Config.d(clazzName + "  onDestroyView");
		super.onDestroyView();
	}

	Activity activity;

	@Override
	public void onAttach(Activity activity) {
		Config.d(clazzName + "  onAttach");
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public void onDetach() {
		Config.d(clazzName + "  onDetach");
		CommonUtils.getInstance().setClearCacheDialog(mDialog);
		super.onDetach();
	}

	@Override
	public void onStart() {
		Config.d(clazzName + "  onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		Config.d(clazzName + "  onStop");
		super.onStop();
	}

	protected void toast(@StringRes int resId) {
		CommonUtils.getInstance().initToast(activity, getString(resId));
	}

	protected void toast(String msg) {
		CommonUtils.getInstance().initToast(activity, msg);
	}

}
