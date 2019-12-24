package com.longcheng.lifecareplan.modular.home.liveplay.mine.adapter;

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
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoItemInfo;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 */

public class MyVideoListAdapter extends BaseAdapterHelper<MVideoItemInfo> {
    ViewHolder mHolder = null;
    Context context;


    public MyVideoListAdapter(Context context, List<MVideoItemInfo> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<MVideoItemInfo> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.live_play_list_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        MVideoItemInfo mHelpItemBean = list.get(position);
        mHolder.item_tv_playtitle.setText("" + mHelpItemBean.getTitle());
        mHolder.item_tv_name.setText(mHelpItemBean.getUser_name());
        mHolder.item_tv_num.setText("" + mHelpItemBean.getFollow_number());
        mHolder.item_tv_city.setText("" + mHelpItemBean.getAddress());
        int width = (DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 26)) / 3;
        int height;
        int moid;
        height = (int) (width * 1.54);
        moid = R.mipmap.live_listnotdatebg2;
        String url = mHelpItemBean.getCover_url();
        GlideDownLoadImage.getInstance().loadCircleImageLive(url, moid, mHolder.item_iv_thumb, 0);
        mHolder.relat_thumb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
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
