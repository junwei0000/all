package com.zh.education.control.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import com.zh.education.R;
import com.zh.education.control.menu.AppApplication;
import com.zh.education.control.onedrives.DisplayItem;
import com.zh.education.control.onedrives.ItemFragment;
import com.zh.education.model.BoKeInfo;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.DatesUtils;
import com.zh.education.utils.ImageLoader;
import com.zh.education.utils.MImageGetter;
import com.zh.education.utils.MTagHandler;
import com.zh.education.utils.NewTextView;
import com.zh.education.utils.TextSizeUtils;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import java.lang.ref.WeakReference;
import android.content.BroadcastReceiver;
public class YunPanDetailActivity extends FragmentActivity implements ItemFragment.OnFragmentInteractionListener,
		View.OnClickListener{

	private LinearLayout top_layout_back;
	private LinearLayout top_head_layout;
	private TextView top_tv_name;
	private Activity activity;
	private String mItemId;
	private Fragment itemFragment;
//	private ItemFragment itemFragment;
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.top_layout_back:
			onBackPressed();
			break;
		case R.id.boke_detai_pinglun_layout:

			break;
		default:
			break;
		}
	}


	@Override
	public void onCreate( Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		CommonUtils.getInstance().addActivity(this);
		Intent in = getIntent();
		mItemId=in.getStringExtra("mItemId");
		Bundle args = new Bundle();
		args.putString("itemId", mItemId);

        FragmentManager fragmentManager =getSupportFragmentManager();
//        FragmentTransaction tx = fragmentManager.beginTransaction();

        itemFragment=fragmentManager.findFragmentById(R.id.fragment222);
        if(itemFragment!=null){
//            ItemFragment itemFragments=new ItemFragment();
//            itemFragments.newInstance(mItemId);
//
//            tx.add(R.id.fragment222,itemFragments,"one").commit();
//            tx.add();
            itemFragment.setArguments(args);
        }
//		Log.e("itemFragment", itemFragment + "itemFragment");
////		itemFragment.newInstance(mItemId);
//        itemFragment.setArguments(args);
        setContentView(R.layout.yunpan_detail_activity);


//		text_content = boKeInfo.getContent();
//		name = boKeInfo.getCreateUser();
//		Title = boKeInfo.getTitle();

		activity = this;
		findViewById();
		setListener();
		processLogic();
	}
//@Override
//public void onCreate(Bundle savedInstanceState) {
//	super.onCreate(savedInstanceState);
//	Bundle args = getArguments();
//	Log.e("channel_id", args.getInt("id", 0) + "云盘");
//
//
//}

	protected void findViewById() {
		top_layout_back = (LinearLayout) findViewById(R.id.top_layout_back);
		top_tv_name = (TextView) findViewById(R.id.top_tv_name);
        top_tv_name.setText("我的云盘");

	}


	protected void setListener() {
		top_layout_back.setOnClickListener(this);

	}

	@Override
	public void onFragmentInteraction(final DisplayItem item) {
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.fragment222, ItemFragment.newInstance(item.getId()))
				.addToBackStack(null)
				.commit();
	}


	protected void processLogic() {

	}

    private BroadcastReceiver dynamicReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context contexts, Intent intent) {
            Bundle extras = intent.getExtras();
            long doneDownloadId = extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID);
            DownloadManager manager = (DownloadManager) activity.getSystemService(Context
                    .DOWNLOAD_SERVICE);
//            if(doneDownloadId != downloadId)
//                return;
//            showInfo("Download with id " + doneDownloadId + " is finished.");
//            showInfo("Download file uri is " + manager.getUriForDownloadedFile(doneDownloadId));
            try {
                manager.openDownloadedFile(doneDownloadId);
            } catch (Exception exc) {

            }

        }


    };

//	/**
//	 * 监听返回键
//	 */
//	public void onBackPressed() {
//		super.onBackPressed();
//		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//		finish();
//	}

	@Override
	public void onBackPressed() {
		if (getFragmentManager().getBackStackEntryCount() == 0) {
			super.onBackPressed();
			return;
		}
		getFragmentManager().popBackStack();
	}
}
