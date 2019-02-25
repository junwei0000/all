package com.longcheng.lifecareplan.modular.bottommenu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-1-28 下午2:41:53
 * @Description 类描述：
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    /**
     * 页面内容集合
     */
    private List<Fragment> fgs = null;
    private FragmentManager mFragmentManager;
    /**
     * 当数据发生改变时的回调接口
     */
    private OnReloadListener mListener;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fgs) {
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

    /**
     * 2.当页面数据发生改变时你可以调用此方法 重新载入数据，具体载入信息由回调函数实现
     */
    public void reLoad() {
        if (mListener != null) {
            mListener.onReload();
        }
        this.notifyDataSetChanged();
    }

    public void setOnReloadListener(OnReloadListener listener) {
        this.mListener = listener;
    }

    /**
     * 回调接口
     */
    public interface OnReloadListener {
        public void onReload();
    }
}
