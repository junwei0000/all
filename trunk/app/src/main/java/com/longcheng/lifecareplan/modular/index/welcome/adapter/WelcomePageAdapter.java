package com.longcheng.lifecareplan.modular.index.welcome.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.index.welcome.bean.WelcomeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:37
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class WelcomePageAdapter extends PagerAdapter {

    private Context mContext;
    private List<WelcomeBean> list;
    private LayoutInflater mInflater;


    public WelcomePageAdapter(Context mContext, List<WelcomeBean> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (list != null && !list.isEmpty()) {
            View view = mInflater.inflate(R.layout.welcome_page_item, container, false);
            ImageView mImageViewPage = view.findViewById(R.id.iv_welcome_page);
            mImageViewPage.setBackground(list.get(position).getDrawable());
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
