package com.longcheng.lifecareplan.modular.home.liveplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.ProgressUtils;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LivePlayItemInfo;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 */

public class PlayListAdapter extends BaseAdapterHelper<LivePlayItemInfo> {
    ViewHolder mHolder = null;
    Context context;
    ProgressUtils mProgressUtils;

    public PlayListAdapter(Context context, List<LivePlayItemInfo> list) {
        super(context, list);
        this.context = context;
        mProgressUtils = new ProgressUtils(context);
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
        GlideDownLoadImage.getInstance().loadCircleImage(context, mHelpItemBean.getFlvurl(), mHolder.item_iv_thumb);
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_jieqi;
        private TextView item_tv_playstatus;
        private TextView item_tv_date;

        public ViewHolder(View view) {
            item_iv_thumb = (ImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            item_tv_jieqi = (TextView) view.findViewById(R.id.item_tv_jieqi);
            item_tv_playstatus = (TextView) view.findViewById(R.id.item_tv_playstatus);
            item_tv_date = (TextView) view.findViewById(R.id.item_tv_date/**/);
        }
    }
}
