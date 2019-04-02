package com.longcheng.lifecareplan.utils.tvrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.longcheng.lifecareplan.R;


/**
 * Created by liuyu on 17/1/6.
 */
public class TvRecyclerView2 extends RecyclerView {


    public TvRecyclerView2(Context context) {
        super(context);
    }

    public TvRecyclerView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TvRecyclerView2(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrollStateChanged(int state) {
        if (state == SCROLL_STATE_IDLE) {
            // 加载更多回调
            if (null != mOnLoadMoreListener) {
                if (getLastVisiblePosition() >= getAdapter().getItemCount() - (1 + 0)) {
                    mOnLoadMoreListener.onLoadMore();
                }
            }
        }
        super.onScrollStateChanged(state);
    }
    public int getLastVisiblePosition() {
        final int childCount = getChildCount();
        if (childCount == 0)
            return 0;
        else
            return getChildAdapterPosition(getChildAt(childCount - 1));
    }
    /***********
     * 按键加载更多 start
     **********/

    private TvRecyclerView.OnLoadMoreListener mOnLoadMoreListener;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


    public void setOnLoadMoreListener(TvRecyclerView.OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    private OnInterceptListener mInterceptLister;


    public void setOnInterceptListener(OnInterceptListener listener) {
        this.mInterceptLister = listener;
    }

}

