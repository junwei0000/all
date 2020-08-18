package com.longcheng.lifecareplan.modular.home.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.longcheng.lifecareplan.R;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/24 13:27
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class BannerPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Drawable> list;
    private LayoutInflater mInflater;

    public BannerPagerAdapter(Context mContext, List<Drawable> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if(list.size()!=0){
            position %= list.size();
            if (position < 0) {
                position = list.size() + position;
            }

            View view = mInflater.inflate(R.layout.banner_item_adapter, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageview_banner_item);
            imageView.setBackground(list.get(position));
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
