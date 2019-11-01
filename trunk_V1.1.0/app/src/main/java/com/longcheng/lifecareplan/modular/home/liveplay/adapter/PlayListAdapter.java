package com.longcheng.lifecareplan.modular.home.liveplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LivePlayItemInfo;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 */

public class PlayListAdapter extends BaseAdapterHelper<LivePlayItemInfo> {
    ViewHolder mHolder = null;
    Context context;
    ImageLoader asyncImageLoader;

    public PlayListAdapter(Context context, List<LivePlayItemInfo> list) {
        super(context, list);
        this.context = context;
        asyncImageLoader = new ImageLoader(context, "headImg");
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<LivePlayItemInfo> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.live_play_list_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        LivePlayItemInfo mHelpItemBean = list.get(position);
        mHolder.item_tv_name.setText(mHelpItemBean.getName());
        mHolder.item_tv_jieqi.setText(mHelpItemBean.getJieqi());
        mHolder.item_tv_date.setText(mHelpItemBean.getTime());
        mHolder.item_tv_playstatus.setText("" + mHelpItemBean.getPlayTile());
        asyncImageLoader.setRoundCorner(mHolder.item_iv_thumb, asyncImageLoader.readBitMap(context, mHelpItemBean.getThumb()));
        return convertView;
    }

    private class ViewHolder {
        private CircleImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_jieqi;
        private TextView item_tv_playstatus;
        private TextView item_tv_date;

        public ViewHolder(View view) {
            item_iv_thumb = (CircleImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            item_tv_jieqi = (TextView) view.findViewById(R.id.item_tv_jieqi);
            item_tv_playstatus = (TextView) view.findViewById(R.id.item_tv_playstatus);
            item_tv_date = (TextView) view.findViewById(R.id.item_tv_date/**/);
        }
    }
}
