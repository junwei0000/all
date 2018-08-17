package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.control.activity.CampaignDelUtils;
import com.bestdo.bestdoStadiums.control.activity.ImagePagerActivity;
import com.bestdo.bestdoStadiums.control.activity.MainNavImgActivity;
import com.bestdo.bestdoStadiums.control.view.NoScrollGridView;
import com.bestdo.bestdoStadiums.control.view.NoScrollGridView.OnTouchInvalidPositionListener;
import com.bestdo.bestdoStadiums.model.CircleBean;
import com.bestdo.bestdoStadiums.utils.CircleImageView;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.MeasureUtils;

/**
 * @author qyy 精彩时刻列表
 */
public class CircleAdapter extends BaseAdapter {

	private List<CircleBean> mCircleBeans;
	private Context mContext;
	String privilege_id;
	private ImageLoader asyncImageLoader;
	private CampaignDelUtils mCampaignDelUtils;
	String uid;

	public CircleAdapter(List<CircleBean> beans, Context context, String privilege_id, Handler mHandler, int mHandlerID,
			String uid) {
		this.mCircleBeans = beans;
		this.privilege_id = privilege_id;
		this.mContext = context;
		this.uid = uid;
		asyncImageLoader = new ImageLoader(context, "headImg");
		mCampaignDelUtils = new CampaignDelUtils(mContext, mHandler, mHandlerID);
	}

	public void setList(List<CircleBean> mCircleBeans) {
		this.mCircleBeans = mCircleBeans;
	}

	@Override
	public int getCount() {
		return mCircleBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return mCircleBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			// convertView = UiUtils.inflate(R.layout.item_listview_circle);
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview_circle, null);
			holder = new ViewHolder();
			holder.tv_commentCount = (TextView) convertView.findViewById(R.id.tv_commentCount);
			holder.tv_dynamicDesc = (TextView) convertView.findViewById(R.id.tv_dynamicDesc);
			holder.tv_releaseTime = (TextView) convertView.findViewById(R.id.tv_releaseTime);
			holder.tv_goodCount = (TextView) convertView.findViewById(R.id.tv_goodCount);
			holder.gridView = (NoScrollGridView) convertView.findViewById(R.id.gridView);
			holder.tv_nickname = (TextView) convertView.findViewById(R.id.tv_nickname);
			holder.iv_icon = (CircleImageView) convertView.findViewById(R.id.iv_icon);
			holder.pengyou_dele_icon = (TextView) convertView.findViewById(R.id.pengyou_dele_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (!TextUtils.isEmpty(privilege_id) && privilege_id.equals("1")) {
			holder.pengyou_dele_icon.setVisibility(View.VISIBLE);
		} else {
			holder.pengyou_dele_icon.setVisibility(View.GONE);
		}

		final CircleBean bean = mCircleBeans.get(position);
		if (null != bean) {
			bean.setPosition(position);
			// 设置头像
			asyncImageLoader.DisplayImage(bean.getAvatar(), holder.iv_icon);
			// 设置昵称
			holder.tv_nickname.setText(bean.getName());
			String content = bean.getContent();
			if (!TextUtils.isEmpty(content)) {
				holder.tv_dynamicDesc.setVisibility(View.VISIBLE);
			} else {
				holder.tv_dynamicDesc.setVisibility(View.GONE);
			}
			// 设置说说
			holder.tv_dynamicDesc.setText(content);
			// 设置发布时间
			holder.tv_releaseTime.setText(bean.getCreate_time());
			// 设置点赞总数
			holder.tv_goodCount.setText(String.valueOf(bean.getShare_count()));
			// 设置评论总数
			holder.tv_commentCount.setText(String.valueOf(bean.getLaud_count()));
			// 删除
			holder.pengyou_dele_icon.setTag(bean);
			holder.pengyou_dele_icon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					CircleBean bean = (CircleBean) arg0.getTag();
					mCampaignDelUtils.showDialogProcessType(bean.getWonderful_id(), uid, bean.getPosition());
				}
			});
			// 判断如果用户是否上传图片
			if (bean.getThumb_urlList() != null && bean.getThumb_urlList().size() > 0) {// 缩略图
				// Log.e("bean.getThumb_urlList().size()",
				// bean.getThumb_urlList().size()+"");
				// 有：设置Adapter显示图片
				holder.gridView.setVisibility(View.VISIBLE);
				// 缩略图片数组转图片集合
				final String[] urls = bean.getThumb_urlList().toArray(new String[bean.getThumb_urlList().size()]);
				final String[] urls2 = bean.getImgList().toArray(new String[bean.getImgList().size()]);
				// 0 文本信息 1视频信息 2图片信息
				final String img_video_type = bean.getImg_video_type();
				if (urls.length > 0 && urls != null) {
					if (urls.length == 1) {
						ArrayList<String> wList = (ArrayList<String>) bean.getWidthList();
						ArrayList<String> hList = (ArrayList<String>) bean.getHeightList();

						if (wList != null && wList.size() == 1 && hList != null && hList.size() == 1) {
							int width = Integer.valueOf(bean.getWidthList().get(0));
							int height = Integer.valueOf(bean.getHeightList().get(0));
							RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) holder.gridView
									.getLayoutParams();
							linearParams.width = width;
							linearParams.height = height;
							holder.gridView.setLayoutParams(linearParams);
						}
						holder.gridView.setNumColumns(1);
					} else {
						int width = MeasureUtils.getWidth(mContext);
						int height = (width - 40 - 55);
						RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) holder.gridView
								.getLayoutParams();
						linearParams.width = width;
						linearParams.height = height;
						holder.gridView.setLayoutParams(linearParams);
						holder.gridView.setNumColumns(3);
					}
				}

				holder.gridView.setAdapter(new CircleGridAdapter(urls, img_video_type, mContext, bean));
				holder.gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
				holder.gridView.setOnTouchInvalidPositionListener(new OnTouchInvalidPositionListener() {

					@Override
					public boolean onTouchInvalidPosition(int motionEvent) {
						Log.e("Wondful_id", bean.getWonderful_id());
						// TODO Auto-generated method stub
						/*
						 * 当返回false的时候代表交由父级控件处理，当return true的时候表示你已经处理了该事件并不
						 * 让该事件再往上传递。为了出发listview的item点击就得返回false了
						 */
						return false;
					}
				});
				// 设置点击事件
				holder.gridView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Log.e("Wondful_id", bean.getWonderful_id());
						if (img_video_type.equals("2")) {
							enterPhotoDetailed(urls2, position);
						} else {
							CommonUtils.getInstance().toH5(mContext,bean.getWondful_show_url(), "精彩时刻", "",true);
						}
					}
				});
			} else {
				// 否：隐藏
				holder.gridView.setVisibility(View.GONE);
			}
		}
		return convertView;
	}

	/**
	 * 进入图片详情页
	 * 
	 * @param array
	 *            图片数组
	 * @param position
	 *            角标
	 */
	protected void enterPhotoDetailed(String[] urls2, int position) {
		Intent intent = new Intent(mContext, ImagePagerActivity.class);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		mContext.startActivity(intent);
	}

	private static class ViewHolder {
		CircleImageView iv_icon;
		TextView tv_nickname;
		TextView tv_dynamicDesc;
		TextView tv_releaseTime;
		TextView tv_goodCount;
		TextView tv_commentCount;
		NoScrollGridView gridView;
		TextView pengyou_dele_icon;
	}
}