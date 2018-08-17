package com.bestdo.bestdoStadiums.control.photo.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.control.activity.CampaignGMPublicActivity;
import com.bestdo.bestdoStadiums.control.photo.util.Bimp;
import com.bestdo.bestdoStadiums.control.photo.util.ImageItem;
import com.bestdo.bestdoStadiums.control.photo.util.PublicWay;
import com.bestdo.bestdoStadiums.control.photo.util.TaskParamInfo;
import com.bestdo.bestdoStadiums.control.photo.vedio.AblumUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-15 下午7:18:58
 * @Description 类描述：上传图片显示
 */
public class SelectPhotoAdapter extends BaseAdapter {
	private ArrayList<ImageItem> selectbimp;
	private ViewHolder viewHolder;
	private Activity mContext;
	ArrayList<TaskParamInfo> image_selectstate;
	Handler mHandler;
	AblumUtils mAblumUtils;

	public SelectPhotoAdapter(Activity mContext, AblumUtils mAblumUtils, ArrayList<ImageItem> selectbimp,
			Handler mHandler) {
		this.mContext = mContext;
		this.mAblumUtils = mAblumUtils;
		this.selectbimp = selectbimp;
		this.mHandler = mHandler;
	}

	public ArrayList<ImageItem> getSelectbimp() {
		return selectbimp;
	}

	public void setSelectbimp(ArrayList<ImageItem> selectbimp) {
		this.selectbimp = selectbimp;
	}

	public int getCount() {
		return selectbimp.size();
	}

	public Object getItem(int position) {
		return selectbimp.get(position);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int position, View view, ViewGroup arg2) {
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.main_campaign_publish_ablumitem, null);
			viewHolder.campaignablumadd_iv_ablum = (ImageView) view.findViewById(R.id.campaignablumadd_iv_ablum);
			viewHolder.campaignablumadd_iv_del = (TextView) view.findViewById(R.id.campaignablumadd_iv_del);
			viewHolder.campaignablumadd_layout_vedioinfo = (LinearLayout) view
					.findViewById(R.id.campaignablumadd_layout_vedioinfo);
			viewHolder.campaignablumadd_iv_vediosize = (TextView) view.findViewById(R.id.campaignablumadd_iv_vediosize);
			viewHolder.campaignablumadd_iv_vediodis = (TextView) view.findViewById(R.id.campaignablumadd_iv_vediodis);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		ImageItem mImageItem = selectbimp.get(position);
		mImageItem.setPosition(position);
		if (position == selectbimp.size() - 1) {
			if (mAblumUtils.selectVedioStatus || Bimp.tempSelectBitmap.size() - 1 == PublicWay.num) {
				// 当选择视频和9张图片的时候隐藏添加按钮
				viewHolder.campaignablumadd_iv_ablum.setVisibility(View.INVISIBLE);
			}
			viewHolder.campaignablumadd_layout_vedioinfo.setVisibility(View.GONE);
			viewHolder.campaignablumadd_iv_del.setVisibility(View.INVISIBLE);
			viewHolder.campaignablumadd_iv_ablum.setBackgroundResource(R.drawable.campaignpublishedit_img_addablum);
			viewHolder.campaignablumadd_iv_ablum.setTag(mImageItem);
			viewHolder.campaignablumadd_iv_ablum.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Message msg = new Message();
					msg.what = CampaignGMPublicActivity.ADDPHOTO;
					mHandler.sendMessage(msg);
					msg = null;
				}
			});
		} else {
			if (mAblumUtils.selectVedioStatus) {
				// 视频的时候，显示时长和大小
				viewHolder.campaignablumadd_layout_vedioinfo.setVisibility(View.VISIBLE);
				viewHolder.campaignablumadd_layout_vedioinfo.getBackground().setAlpha(40);
				String vediosize = PriceUtils.getInstance().gteDividePrice(mImageItem.getVediosize() + "",
						1024 * 1024 + "");
				String vediosize_ = PriceUtils.getInstance().getPriceTwoDecimal(Double.valueOf(vediosize), 2);
				String vedioduration = mImageItem.getVedioduration();
				String timeString = formatTime(Integer.parseInt(vedioduration));
				viewHolder.campaignablumadd_iv_vediosize.setText(vediosize_ + "M");
				viewHolder.campaignablumadd_iv_vediodis.setText(timeString);
			} else {
				viewHolder.campaignablumadd_layout_vedioinfo.setVisibility(View.GONE);
			}
			viewHolder.campaignablumadd_iv_del.setTag(mImageItem);
			viewHolder.campaignablumadd_iv_del.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ImageItem mInfo = (ImageItem) v.getTag();
					Message msg = new Message();
					msg.what = CampaignGMPublicActivity.DEL;
					msg.arg1 = mInfo.getPosition();
					mHandler.sendMessage(msg);
					msg = null;
				}
			});
			Bitmap bitmap = mImageItem.getBitmap();
			viewHolder.campaignablumadd_iv_ablum.setImageBitmap(bitmap);
			viewHolder.campaignablumadd_iv_ablum.setTag(mImageItem);
			viewHolder.campaignablumadd_iv_ablum.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ImageItem mInfo = (ImageItem) v.getTag();
					Message msg = new Message();
					msg.what = CampaignGMPublicActivity.LOOK;
					msg.obj = mInfo.getPosition();
					mHandler.sendMessage(msg);
					msg = null;
				}
			});
		}

		return view;
	}

	private String formatTime(int milliseconds) {
		String format = "HH:mm:ss";
		// TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));// Beijing
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		return sdf.format(milliseconds);
	}

	class ViewHolder {
		ImageView campaignablumadd_iv_ablum;
		TextView campaignablumadd_iv_del;
		LinearLayout campaignablumadd_layout_vedioinfo;
		TextView campaignablumadd_iv_vediosize;
		TextView campaignablumadd_iv_vediodis;
	}

}
