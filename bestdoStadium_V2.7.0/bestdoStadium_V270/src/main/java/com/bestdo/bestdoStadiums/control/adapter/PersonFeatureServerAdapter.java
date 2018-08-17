package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.model.BusinessBannerInfo;
import com.bestdo.bestdoStadiums.model.ServersInfo;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.R;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-8-18 上午11:44:32
 * @Description 类描述：
 */
public class PersonFeatureServerAdapter extends BaseAdapter {

	private ArrayList<ServersInfo> navList;
	private ImageLoader asyncImageLoader;
	private Activity context;

	public PersonFeatureServerAdapter(Activity context, ArrayList<ServersInfo> navList) {
		this.context = context;
		this.navList = navList;
		asyncImageLoader = new ImageLoader(context, "listitem");
	}

	@Override
	public int getCount() {
		return navList.size();
	}

	@Override
	public Object getItem(int position) {
		return navList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.main_preson_server_item, null);
			holder.business_navitem_lay = (LinearLayout) view.findViewById(R.id.business_navitem_lay);
			holder.business_navitem_img = (ImageView) view.findViewById(R.id.business_navitem_img);
			holder.business_navitem_name = (TextView) view.findViewById(R.id.business_navitem_name);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		ServersInfo mBannerInfo = navList.get(position);

		String nameString = mBannerInfo.getName();
		holder.business_navitem_name.setText(nameString);

		String imgPath = mBannerInfo.getImg_url();
		Log.e("imgPath", imgPath);
		if (!TextUtils.isEmpty(imgPath)) {
			asyncImageLoader.DisplayImage(imgPath, holder.business_navitem_img);
			int screenW = ConfigUtils.getInstance().getPhoneWidHeigth(context).widthPixels;
			int offset = screenW / 2 - 50;

			int y = ConfigUtils.getInstance().dip2px(context, 100);
			holder.business_navitem_img.setLayoutParams(new LinearLayout.LayoutParams(offset, y));
		} else {
			holder.business_navitem_img.setImageBitmap(asyncImageLoader.bitmap_orve);
		}

		// holder.
		// .setLayoutParams(new LinearLayout.LayoutParams(
		// LinearLayout.LayoutParams.FILL_PARENT, ConfigUtils
		// .getInstance().dip2px(context, 120)));
		return view;
	}

	class ViewHolder {
		LinearLayout business_navitem_lay;
		ImageView business_navitem_img;
		TextView business_navitem_name;

	}
}
