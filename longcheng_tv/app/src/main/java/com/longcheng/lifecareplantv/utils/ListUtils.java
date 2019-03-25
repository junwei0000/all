package com.longcheng.lifecareplantv.utils;

import android.view.View;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.lifecareplantv.base.BaseAdapterHelper;

/**
 * 作者：jun on
 * 时间：2018/11/2 10:19
 * 意图：
 */

public class ListUtils {
    private static ListUtils instance;

    private ListUtils() {
    }

    public static synchronized ListUtils getInstance() {
        if (instance == null) {
            instance = new ListUtils();
        }
        return instance;
    }

    public void RefreshCompleteL(PullToRefreshListView Listview) {
        if (Listview != null) {
            Listview.onRefreshComplete();
        }
    }

    public void RefreshCompleteS(PullToRefreshScrollView ScrollView) {
        if (ScrollView != null) {
            ScrollView.onRefreshComplete();
        }
    }

    public void setNotDateViewL(BaseAdapterHelper mAdapter, LinearLayout layoutNotdate) {
        if (layoutNotdate != null) {
            if (mAdapter == null || (mAdapter != null && mAdapter.getCount() == 0)) {
                layoutNotdate.setVisibility(View.VISIBLE);
            } else {
                layoutNotdate.setVisibility(View.GONE);
            }
        }
    }
}
