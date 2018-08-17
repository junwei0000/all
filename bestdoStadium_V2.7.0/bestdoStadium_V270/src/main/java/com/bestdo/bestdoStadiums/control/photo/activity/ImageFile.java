package com.bestdo.bestdoStadiums.control.photo.activity;

import java.util.List;

import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.control.activity.BaseActivity;
import com.bestdo.bestdoStadiums.control.photo.adapter.FolderAdapter;
import com.bestdo.bestdoStadiums.control.photo.util.AlbumHelper;
import com.bestdo.bestdoStadiums.control.photo.util.ImageBucket;
import com.bestdo.bestdoStadiums.utils.CommonUtils;

/**
 * 这个类主要是用来进行显示包含图片的文件夹
 * 
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日 下午11:48:06
 */
public class ImageFile extends BaseActivity {

	private FolderAdapter folderAdapter;
	private LinearLayout pagetop_layout_back;
	private AlbumHelper helper;
	public static List<ImageBucket> contentList;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;

		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.plugin_camera_image_file);
		CommonUtils.getInstance().addPhotoActivity(this);
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
		contentList = helper.getImagesBucketList(false);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);

		GridView gridView = (GridView) findViewById(R.id.fileGridView);
		TextView pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_name.setText("相册胶卷");
		folderAdapter = new FolderAdapter(this);
		gridView.setAdapter(folderAdapter);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {

	}

	private void doBack() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doBack();
		}
		return false;
	}
}
