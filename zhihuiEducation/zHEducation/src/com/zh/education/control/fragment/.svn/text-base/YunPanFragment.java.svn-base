package com.zh.education.control.fragment;

import java.util.ArrayList;

import org.w3c.dom.Text;

import com.microsoft.onedrivesdk.picker.IPicker;
import com.microsoft.onedrivesdk.picker.IPickerResult;
import com.microsoft.onedrivesdk.picker.LinkType;
import com.microsoft.onedrivesdk.picker.Picker;
import com.zh.education.R;
import com.zh.education.model.NoticesInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class YunPanFragment extends Fragment {
	Activity activity;
	 private static final String ONEDRIVE_APP_ID = "000000004418061F";
	public YunPanFragment() {
		super();
	}

	public YunPanFragment(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		Log.e("channel_id", args.getInt("id", 0)+"云盘");
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	/** 此方法意思为fragment是否可见 ,可见时候加载数据 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_yunpan, null);
		
		((Button)view.findViewById(R.id.startPickerButton)).setOnClickListener(mStartPickingListener);
		mPicker = Picker.createPicker(ONEDRIVE_APP_ID);
		return view;
	}
	 /**
     * The OneDrive picker instance used by this activity
     */
    private IPicker mPicker;
	/**
     * The onClickListener that will start the OneDrive Picker
     */
    private final OnClickListener mStartPickingListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {

            // Determine the link type that was selected
            LinkType linkType;
                linkType = LinkType.WebViewLink;

            // Start the picker
            mPicker.startPicking((Activity)v.getContext(), linkType);
        }
    };
    @Override
	public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        // Get the results from the from the picker
        final IPickerResult result = mPicker.getPickerResult(requestCode, resultCode, data);

        // Handle the case if nothing was picked
        if (result == null) {
//            Toast.makeText(this, "Did not get a file from the picker!", Toast.LENGTH_LONG).show();
            return;
        }

        // Update the UI with the picker results
//        updateResultTable(result);
    }
	/* 摧毁视图 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	/* 摧毁该Fragment，一般是FragmentActivity 被摧毁的时候伴随着摧毁 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
