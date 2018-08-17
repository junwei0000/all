package com.KiwiSports.utils;

import android.view.View;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * 作者：MarkMingShuai
 * 时间 2017/9/1 11:56
 * 邮箱：mengchong.55@163.com
 * 类的意图：
 */

public class ScrowUtil {
    /////////////////////上下拉的字段
    public static final String PullDownLabel = "下拉刷新...";
    public static final String RefreshingDownLabel = "正在刷新...";
    public static final String ReleaseDownLabel = "下拉加载更多...";
    public static final String PullUpLabel = "上拉加载...";
    public static final String RefreshingUpLabel = "正在加载...";
    public static final String ReleaseUpLabel = "上拉加载更多...";
    public static final String REFRESH_RELEASE = "松开可刷新";
    /////////////////////上下拉的字段
    /**
     *  上拉下拉
     */
    public static void ScrollViewConfigAll(PullToRefreshScrollView mScrollView) {
        mScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mScrollView.getLoadingLayoutProxy(true, false).setPullLabel(ScrowUtil.PullDownLabel);
        mScrollView.getLoadingLayoutProxy(true, false).setRefreshingLabel(ScrowUtil.RefreshingDownLabel);
        mScrollView.getLoadingLayoutProxy(true, false).setReleaseLabel(ScrowUtil.ReleaseDownLabel);

        mScrollView.getLoadingLayoutProxy(false, true).setPullLabel(ScrowUtil.PullUpLabel);
        mScrollView.getLoadingLayoutProxy(false, true).setRefreshingLabel(ScrowUtil.RefreshingUpLabel);
        mScrollView.getLoadingLayoutProxy(false, true).setReleaseLabel(ScrowUtil.ReleaseUpLabel);

        final ScrollView refreshableView = mScrollView.getRefreshableView();
        refreshableView.post(new Runnable() {
            @Override
            public void run() {
                refreshableView.fullScroll(View.FOCUS_UP);
            }
        });
        refreshableView.smoothScrollTo(0, 20);//ScrollView
    }

    /**
     * 仅有下拉
     */
    public static void ScrollViewDownConfig(PullToRefreshScrollView mScrollView) {
//        mScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mScrollView.getLoadingLayoutProxy(true, false).setPullLabel(ScrowUtil.PullDownLabel);
        mScrollView.getLoadingLayoutProxy(true, false).setRefreshingLabel(ScrowUtil.RefreshingDownLabel);
        mScrollView.getLoadingLayoutProxy(true, false).setReleaseLabel(ScrowUtil.ReleaseDownLabel);

        mScrollView.getLoadingLayoutProxy(false, true).setPullLabel(ScrowUtil.PullUpLabel);
        mScrollView.getLoadingLayoutProxy(false, true).setRefreshingLabel(ScrowUtil.RefreshingUpLabel);
        mScrollView.getLoadingLayoutProxy(false, true).setReleaseLabel(ScrowUtil.ReleaseUpLabel);

        final ScrollView refreshableView = mScrollView.getRefreshableView();
        refreshableView.post(new Runnable() {
            @Override
            public void run() {
                refreshableView.fullScroll(View.FOCUS_UP);
            }
        });
        refreshableView.smoothScrollTo(0, 20);//ScrollView
    }

    /**
     * @param
     * @name 上拉下拉
     * @time 2017/11/24 16:26
     * @author MarkShuai
     */
    public static void listViewConfigAll(PullToRefreshListView mlistview) {
        mlistview.setMode(PullToRefreshBase.Mode.BOTH);
        mlistview.getLoadingLayoutProxy(true, false).setPullLabel(ScrowUtil.PullDownLabel);
        mlistview.getLoadingLayoutProxy(true, false).setRefreshingLabel(ScrowUtil.RefreshingDownLabel);
        mlistview.getLoadingLayoutProxy(true, false).setReleaseLabel(ScrowUtil.ReleaseDownLabel);

        mlistview.getLoadingLayoutProxy(false, true).setPullLabel(ScrowUtil.PullUpLabel);
        mlistview.getLoadingLayoutProxy(false, true).setRefreshingLabel(ScrowUtil.RefreshingUpLabel);
        mlistview.getLoadingLayoutProxy(false, true).setReleaseLabel(ScrowUtil.ReleaseUpLabel);
    }
    /**
     * 禁用
     * @param mlistview
     */
    public static void listViewNotConfig(PullToRefreshListView mlistview) {
        mlistview.setMode(PullToRefreshBase.Mode.DISABLED);
        mlistview.getLoadingLayoutProxy(true, false).setPullLabel(ScrowUtil.PullDownLabel);
        mlistview.getLoadingLayoutProxy(true, false).setRefreshingLabel(ScrowUtil.RefreshingDownLabel);
        mlistview.getLoadingLayoutProxy(true, false).setReleaseLabel(ScrowUtil.ReleaseDownLabel);

        mlistview.getLoadingLayoutProxy(false, true).setPullLabel(ScrowUtil.PullUpLabel);
        mlistview.getLoadingLayoutProxy(false, true).setRefreshingLabel(ScrowUtil.RefreshingUpLabel);
        mlistview.getLoadingLayoutProxy(false, true).setReleaseLabel(ScrowUtil.ReleaseUpLabel);
    }
    /**
     * 仅有上拉加载更多
     * @param mlistview
     */
    public static void listViewUpConfig(PullToRefreshListView mlistview) {
        mlistview.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mlistview.getLoadingLayoutProxy(true, false).setPullLabel(ScrowUtil.PullDownLabel);
        mlistview.getLoadingLayoutProxy(true, false).setRefreshingLabel(ScrowUtil.RefreshingDownLabel);
        mlistview.getLoadingLayoutProxy(true, false).setReleaseLabel(ScrowUtil.ReleaseDownLabel);

        mlistview.getLoadingLayoutProxy(false, true).setPullLabel(ScrowUtil.PullUpLabel);
        mlistview.getLoadingLayoutProxy(false, true).setRefreshingLabel(ScrowUtil.RefreshingUpLabel);
        mlistview.getLoadingLayoutProxy(false, true).setReleaseLabel(ScrowUtil.ReleaseUpLabel);
    }
    /**
     * 仅有下拉刷新
     * @param mlistview
     */
    public static void listViewDownConfig(PullToRefreshListView mlistview) {
        mlistview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mlistview.getLoadingLayoutProxy(true, false).setPullLabel(ScrowUtil.PullDownLabel);
        mlistview.getLoadingLayoutProxy(true, false).setRefreshingLabel(ScrowUtil.RefreshingDownLabel);
        mlistview.getLoadingLayoutProxy(true, false).setReleaseLabel(ScrowUtil.ReleaseDownLabel);

        mlistview.getLoadingLayoutProxy(false, true).setPullLabel(ScrowUtil.PullUpLabel);
        mlistview.getLoadingLayoutProxy(false, true).setRefreshingLabel(ScrowUtil.RefreshingUpLabel);
        mlistview.getLoadingLayoutProxy(false, true).setReleaseLabel(ScrowUtil.ReleaseUpLabel);
    }
}
