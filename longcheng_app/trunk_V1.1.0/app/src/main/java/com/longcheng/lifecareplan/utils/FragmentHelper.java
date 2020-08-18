package com.longcheng.lifecareplan.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * 作者：MarkMingShuai
 * 时间 2017-8-9 11:13
 * 邮箱：mark_mingshuai@163.com
 * <p>
 * 类的意图：Fragment的工具类
 */
public class FragmentHelper {

    /**
     * @param manager  Fragment 管理器
     * @param list     Fragment 集合
     * @param tabIndex Fragmet对应下表
     * @param layoutId 帧布局id
     * @param enter    进入动画
     * @param exit     推出动画
     * @name Fragment之间相互替换
     * @auhtor MarkMingShuai
     * @Data 2017-8-9 17:31
     */
    public static void replaceFragment(FragmentManager manager, List<Fragment> list, int tabIndex, int layoutId, int enter, int exit) {
        FragmentTransaction transaction = manager.beginTransaction();
        //设置碎片显示的自定义动画
        if (enter != 0 && exit != 0) {
            transaction.setCustomAnimations(enter, exit);
        }

        transaction.replace(layoutId, list.get(tabIndex));
        transaction.commit();
    }

    /**
     * @param manager  Fragment 管理器
     * @param list     Fragment 集合
     * @param tabIndex Fragmet对应下表
     * @param layoutId 帧布局id
     * @param enter    进入动画
     * @param exit     推出动画
     * @name Fragment之间的显示隐藏替换
     * @auhtor MarkMingShuai
     * @Data 2017-8-9 17:31
     */
    public static void switchFragment(FragmentManager manager, List<Fragment> list, int
            tabIndex, int layoutId, int enter, int exit) {
        FragmentTransaction transaction = manager.beginTransaction();
        //让当前显示的碎片进行隐藏
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isVisible()) {
                transaction.hide(list.get(i));
            }
        }
        //设置碎片显示的自定义动画
        if (enter != 0 && exit != 0) {
            transaction.setCustomAnimations(enter, exit);
        }
        Fragment toFragment = list.get(tabIndex);
        if (toFragment.isAdded()) {
            transaction.show(toFragment);
        } else {
            transaction.add(layoutId, toFragment);
        }
        transaction.commit();
    }
}
