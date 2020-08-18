package com.longcheng.web.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/24 10:12
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class TabPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public TabPageAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
//        return list.size();
        return 4;
    }

    /**
     * 重写，不让Fragment销毁
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
