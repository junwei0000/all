package com.longcheng.lifecareplan.modular.home.liveplay.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoItemInfo;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 */

public class MyLiveListAdapter extends BaseAdapterHelper<MVideoItemInfo> {
    ViewHolder mHolder = null;
    Context context;

    public MyLiveListAdapter(Context context, List<MVideoItemInfo> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<MVideoItemInfo> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.live_mylive_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        MVideoItemInfo mHelpItemBean = list.get(position);
        mHolder.item_tv_name.setText(mHelpItemBean.getTitle());
        mHolder.item_tv_num.setText("" + mHelpItemBean.getTotal_person_number() + "人观看");
        mHolder.item_tv_follow.setVisibility(View.GONE);
        String url = mHelpItemBean.getCover_url();
        GlideDownLoadImage.getInstance().loadCircleImage(url, mHolder.item_iv_thumb);
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_num;
        private TextView item_tv_follow;

        public ViewHolder(View view) {
            item_iv_thumb = (ImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            item_tv_num = (TextView) view.findViewById(R.id.item_tv_num);
            item_tv_follow = (TextView) view.findViewById(R.id.item_tv_follow);
        }
    }
}
