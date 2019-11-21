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
     * 原来FragmentPagerAdapter里在根据getItemId(int position)来判断当前position里Fragment是否存在，
     * 如果存在，则不会创建亦不会更新，那么要让FragmentPagerAdapter的更新生效，那在getItemId(int)里根据数据返回一个唯一的数据ID，
     * 当FragmentPagerAdapter更新时，数据ID改变了，那么Fragment就会调用getItem(int)去获取新Fragment，达到更新效果
     * 好了，我们的解决方案就是通过重新getItemId()方法，返回唯一的id。
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return fgs.get(position).hashCode();
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
     * onSaveInstanceState方法是在该Activity即将被销毁前调用，来保存Activity数据的，如果在保存玩状态后
     * 再给它添加Fragment就会出错。解决办法就是把commit（）方法替换成 commitAllowingStateLoss()就行
     *
     * @param items
     */
    public void setPagerItems(List<Fragment> items) {
        if (items != null) {
            for (int i = 0; i < fgs.size(); i++) {
                mFragmentManager.beginTransaction().remove(fgs.get(i)).commitAllowingStateLoss();
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
