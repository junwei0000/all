package com.longcheng.lifecareplan.modular.exchange.malldetail.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/24 13:27
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class GoodsImgAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> list;
    private LayoutInflater mInflater;

    public GoodsImgAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (list.size() != 0) {
            position %= list.size();
            if (position < 0) {
                position = list.size() + position;
            }
            View view = mInflater.inflate(R.layout.exchange_detail_topimg_item, container, false);
            ImageView item_iv_pic = view.findViewById(R.id.item_iv_pic);
            GlideDownLoadImage.getInstance().loadCircleImageRoleGoodsDetail(mContext, list.get(position), item_iv_pic, 0);
            container.addView(view);
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
