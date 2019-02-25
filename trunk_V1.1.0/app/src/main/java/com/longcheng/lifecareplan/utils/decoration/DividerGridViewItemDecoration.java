package com.longcheng.lifecareplan.utils.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.State;
import android.util.Log;
import android.view.View;

import com.longcheng.lifecareplan.R;


/**
 * Created by Markshuai on 2017/3/26.
 * GridView的布局管理器的分割线
 */

public class DividerGridViewItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private int[] attrs = new int[]{
            android.R.attr.listDivider, R.drawable.item_divider
    };

    public DividerGridViewItemDecoration(Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {
        drawVertical(c, parent);
        drawHorizontal(c, parent);
    }

    // 绘制水平间隔线
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int right = child.getRight() + params.rightMargin;
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    //绘制垂直间隔线(垂直的矩形)
    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            int top = child.getTop() - params.topMargin;
            int bottom = child.getBottom() + params.bottomMargin;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    @Deprecated
    public void getItemOffsets(Rect outRect, int itemPosition,
                               RecyclerView parent) {
        // 四个方向的偏移值
        int right = mDivider.getIntrinsicWidth();
        int bottom = mDivider.getIntrinsicHeight();
        if (isLastColum(itemPosition, parent)) {//是否是最后一列
//			outRect.set(0, 0, 0, bottom);
            right = 0;
        }
        if (isLastRow(itemPosition, parent)) {//是最后一行
//			outRect.set(0, 0, right, 0);
            bottom = 0;
        }
        outRect.set(0, 0, right, bottom);

    }

    /**
     * 是否是最后一行
     *
     * @param itemPosition
     * @param parent
     * @return
     */
    private boolean isLastRow(int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        LayoutManager layoutManager = parent.getLayoutManager();
        //有多少列
        if (layoutManager instanceof GridLayoutManager) {
            double childCount = parent.getAdapter().getItemCount();
            // 总的行数
            double allCulm = Math.ceil(childCount / spanCount);
            //最后一行
            int lastSpan = (itemPosition / spanCount) + 1;
            Log.i("asd", "isLastRow: " + lastSpan);
            //最后一行的数量小于spanCount
            if (lastSpan == allCulm) {
                return true;
            }
        }
        return false;
//        int spanCount =  getSpanCount(parent);
//        LayoutManager layoutManager = parent.getLayoutManager();
//        //有多少列
//        if(layoutManager instanceof GridLayoutManager){
//            int childCount = parent.getAdapter().getItemCount();
//            int lastRowCount = childCount%spanCount;
//            //最后一行的数量小于spanCount
//            if(lastRowCount==0||lastRowCount<spanCount){
//                return true;
//            }
//        }
//        return false;
    }

    /**
     * 判断是否是最后一列
     *
     * @param itemPosition
     * @param parent
     * @return
     */
    private boolean isLastColum(int itemPosition, RecyclerView parent) {
        LayoutManager layoutManager = parent.getLayoutManager();
        //有多少列
        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = getSpanCount(parent);
            if ((itemPosition + 1) % spanCount == 0) {
                return true;
            }
        }
        return false;
    }

    private int getSpanCount(RecyclerView parent) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager lm = (GridLayoutManager) layoutManager;
            int spanCount = lm.getSpanCount();
            return spanCount;
        }
        return 0;
    }
}
