package com.longcheng.lifecareplan.modular.home.liveplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LivePlayItemInfo;
import com.longcheng.lifecareplan.utils.DensityUtil;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 */

public class PlayListAdapter extends BaseAdapterHelper<LivePlayItemInfo> {
    ViewHolder mHolder = null;
    Context context;

    boolean liveSeleStatus;

    public PlayListAdapter(Context context, List<LivePlayItemInfo> list, boolean liveSeleStatus) {
        super(context, list);
        this.context = context;
        this.liveSeleStatus = liveSeleStatus;
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
        mHolder.item_tv_playtitle.setText("" + mHelpItemBean.getPlayTile());
        int width = (DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 25)) / 2;
        int height;
        int moid;
        if (liveSeleStatus) {
            height = width;
            moid = R.mipmap.live_listnotdatebg;
        } else {
            height = (int) (width * 1.54);
            moid = R.mipmap.live_listnotdatebg2;
        }
        mHolder.item_iv_thumb.setImageDrawable(context.getResources().getDrawable(mHelpItemBean.getThumb()));
//        GlideDownLoadImage.getInstance().loadCircleImageLive("", moid, mHolder.item_iv_thumb, ConstantManager.image_angle);
        mHolder.relat_thumb.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        return convertView;
    }

    private class ViewHolder {
        private RelativeLayout relat_thumb;
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_num;
        private TextView item_tv_playtitle;
        private TextView item_tv_city;

        public ViewHolder(View view) {
            relat_thumb = (RelativeLayout) view.findViewById(R.id.relat_thumb);
            item_iv_thumb = (ImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            item_tv_num = (TextView) view.findViewById(R.id.item_tv_num);
            item_tv_playtitle = (TextView) view.findViewById(R.id.item_tv_playtitle);
            item_tv_city = (TextView) view.findViewById(R.id.item_tv_city);
        }
    }
}
