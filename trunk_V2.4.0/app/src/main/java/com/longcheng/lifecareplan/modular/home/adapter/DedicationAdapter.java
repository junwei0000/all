package com.longcheng.lifecareplan.modular.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.home.bean.HomeItemBean;
import com.longcheng.lifecareplan.utils.myview.MyGridView;

import java.util.ArrayList;

/**
 * 作者：MarkShuai
 * 时间：2017/11/24 13:27
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class DedicationAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<HomeItemBean> list;
    private LayoutInflater mInflater;
    int page;

    public DedicationAdapter(Context mContext, ArrayList<HomeItemBean> list) {
        this.mContext = mContext;
        this.list = list;
        boolean ss = (list.size() % 2) == 0;
        page = (list.size() / 2);
        if (!ss) {
            page = page + 1;
        }
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(ArrayList<HomeItemBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return page;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (page != 0) {
            position %= page;
            if (position < 0) {
                position = page + position;
            }
            View view = mInflater.inflate(R.layout.banner_griditem_adapter, container, false);
            MyGridView mMyGridView = view.findViewById(R.id.gv_bann);
            DediGridAdapter mDediGridAdapter = new DediGridAdapter(mContext, list, position);
            mMyGridView.setAdapter(mDediGridAdapter);
            container.addView(view);
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    /*
     * 解决viewpager在刷新调用notifyDataSetChanged不起作用 使用懒加载后没有效果?
     */
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
