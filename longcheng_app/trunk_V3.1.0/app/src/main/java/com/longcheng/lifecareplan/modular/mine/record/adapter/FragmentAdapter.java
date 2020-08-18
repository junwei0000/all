package com.longcheng.lifecareplan.modular.mine.record.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.longcheng.lifecareplan.modular.mine.record.fargment.BaseRecordFrag;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<BaseRecordFrag> fragments;
    private String[] tabname;

    public FragmentAdapter(FragmentManager fm, List<BaseRecordFrag> fragments, String[] tabname) {
        super(fm);
        this.fragments = fragments;
        this.tabname = tabname;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (tabname == null || tabname.length== 0) return super.getPageTitle(position);
        return tabname[position];
    }
}
