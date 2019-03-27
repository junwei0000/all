package com.longcheng.volunteer.modular.index.welcome.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Burning on 2018/9/27.
 */

public class GuidePagerAdapter extends FragmentPagerAdapter {
    /**
     * 页面内容集合
     */
    private List<Fragment> fgs = null;
    private FragmentManager mFragmentManager;


    public GuidePagerAdapter(FragmentManager fm, List<Fragment> fgs) {
        super(fm);
        this.fgs = fgs;
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int arg0) {
        return fgs.get(arg0);
    }

    @Override
    public int getCount() {
        return fgs.size();
    }

    /**
     * 1.最重要的是要return POSITION_NONE，这个表示需要刷新，另一个参数是POSITION_UNCHANGED,这个表示不需要刷新，
     * 所以这里必须返回return POSITION_NONE,少了这句就不会刷新了。
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    /**
     * 重新设置页面内容
     *
     * @param items
     */
    public void setPagerItems(List<Fragment> items) {
        if (items != null) {
            for (int i = 0; i < fgs.size(); i++) {
                mFragmentManager.beginTransaction().remove(fgs.get(i)).commit();
            }
            fgs = items;
        }
    }
}
