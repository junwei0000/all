package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.model.BusinessBannerInfo;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
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
public class BusinessNaviAdapter extends BaseAdapter {

	private ArrayList<BusinessBannerInfo> navList;
	public ImageLoader asyncImageLoader;
	private Activity context;

	public BusinessNaviAdapter(Activity context, ArrayList<BusinessBannerInfo> navList) {
		this.context = context;
		this.navList = navList;
		asyncImageLoader = new ImageLoader(context, "listdetail_ball");
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
			view = LayoutInflater.from(context).inflate(R.layout.main_business_nav_item, null);
			holder.business_navitem_lay = (LinearLayout) view.findViewById(R.id.business_navitem_lay);
			holder.business_navitem_img = (ImageView) view.findViewById(R.id.business_navitem_img);
			holder.business_navitem_name = (TextView) view.findViewById(R.id.business_navitem_name);
			holder.business_navitem_name2 = (TextView) view.findViewById(R.id.business_navitem_name2);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		BusinessBannerInfo mBannerInfo = navList.get(position);

		String nameString = mBannerInfo.getName();
		holder.business_navitem_name2.setText(mBannerInfo.getSub_name());
		holder.business_navitem_name.setText(nameString);
		holder.business_navitem_name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		String imgPath = mBannerInfo.getImgPath();
		// if (!TextUtils.isEmpty(imgPath)) {
		// asyncImageLoader.DisplayImage(imgPath, holder.business_navitem_img);
		// } else {
		// holder.business_navitem_img
		// .setImageBitmap(asyncImageLoader.bitmap_orve);
		// }
		holder.business_navitem_lay.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				ConfigUtils.getInstance().dip2px(context, 90)));
		// holder.business_navitem_lay
		// .setLayoutParams(new LinearLayout.LayoutParams(
		// ConfigUtils
		// .getInstance().dip2px(context, 140), ConfigUtils
		// .getInstance().dip2px(context, 75)));
		return view;
	}

	class ViewHolder {
		LinearLayout business_navitem_lay;
		ImageView business_navitem_img;
		TextView business_navitem_name, business_navitem_name2;

	}
}
