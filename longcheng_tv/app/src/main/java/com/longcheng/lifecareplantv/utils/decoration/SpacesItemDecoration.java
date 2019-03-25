package com.longcheng.lifecareplantv.utils.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者：MarkMingShuai
 * 时间 2017-8-9 11:16
 * 邮箱：mark_mingshuai@163.com
 * 类的意图： 设置item之间的分割空间 为RecyclerView封装
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        if (parent.getChildPosition(view) == 0 || parent.getChildPosition(view) == 1 || parent.getChildPosition(view) == 2 || parent.getChildPosition(view) == 3) {
            outRect.top = space;
        }
    }
}
