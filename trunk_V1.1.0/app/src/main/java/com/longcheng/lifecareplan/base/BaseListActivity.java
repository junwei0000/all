package com.longcheng.lifecareplan.base;

import android.view.View;
import android.widget.ListView;

import com.longcheng.lifecareplan.widget.view.FooterNoMoreData;

/**
 * Created by Burning on 2018/9/21.
 */

public abstract class BaseListActivity<V, T extends BasePresent<V>> extends BaseActivityMVP<V, T> {
    View footerView;
    protected int pageIndex = 0;
    protected int pageSize = 10;

    /**
     *
     */
    private boolean hasFooter = false;


    /**
     * 获取footer控件，可以自定义内容，也可以传null
     *
     * @return
     */
    protected abstract View getFooterView();

    /**
     * 移除footer
     *
     * @param lv 被移除footer的ListView
     */
    private void removeFooterView(ListView lv) {
        if (hasFooter && lv != null && footerView != null && lv.getFooterViewsCount() > 0) {
//            hasFooter = lv.removeFooterView(footerView);
            hasFooter = false;
            lv.removeFooterView(footerView);
        }
    }

    /**
     * 添加footer
     *
     * @param lv 被添加footer的ListView
     */
    private void addFooterView(ListView lv) {
        if (!hasFooter && lv != null && footerView != null) {
            lv.addFooterView(footerView);
            hasFooter = true;
        }
    }

    /**
     * @param flag true:显示footer；false 不显示footer
     * @param lv   被添加footerde ListView
     */
    public void showNoMoreData(boolean flag, ListView lv) {
        if (flag) {
            addFooterView(lv);
        } else {
            removeFooterView(lv);
        }
    }


    @Override
    public void initView(View view) {
        //Todo 子类Override后，一定要写上super.initView(view);
        footerView = getFooterView();
        if (footerView == null) {
            footerView = new FooterNoMoreData(mContext);
        }
    }

}
