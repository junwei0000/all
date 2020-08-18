package com.longcheng.lifecareplanTv.utils.tvrecyclerview;

import android.view.KeyEvent;

/**
 * Created by liuyu on 16/12/27.
 * 专门用于dispatchKeyEvent事件拦截回调处理
 */
public interface OnInterceptListener {
    boolean onIntercept(KeyEvent event);
}
