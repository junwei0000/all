package com.longcheng.web;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

/**
 * Author:      Sbingo
 * Date:        2016/11/20 0020
 * Time:        13:54
 */

public class FreeMoveView extends LinearLayout {

    private MyCountDownTimer countDownTimer;
    /**
     * 倒计时时间
     */
    private long millisInFuture;
    /**
     * 倒计时过程中
     * 回调{@link CountDownTimer#onTick(long)}的间隔时间
     */
    private long countDownInterval = 500;
    private float currentX;
    private float currentY;
    private int currentLeft;
    private int currentTop;
    private int parentWidth;
    private int parentHeight;
    private int viewWidth;
    private int viewHight;
    private int minLeftMargin;
    private int maxLeftMargin;
    private int rightDistance;
    private int minTopMargin;
    private int maxTopMargin;
    private int bottomDistance;
    private int leftPadding;
    private int topPadding;
    private boolean moveable;
    private boolean autoBack;
    private float toAlpha;

    public FreeMoveView(Context context) {
        this(context, null);
    }

    public FreeMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.free);
        millisInFuture = ta.getInt(R.styleable.free_millisInFuture, 3 * 1000);
        toAlpha = ta.getFloat(R.styleable.free_toAlpha, 0.2f);
        moveable = ta.getBoolean(R.styleable.free_moveable, false);
        autoBack = ta.getBoolean(R.styleable.free_autoBack, false);
        ta.recycle();
        countDownTimer = new MyCountDownTimer(millisInFuture, countDownInterval);
        countDownTimer.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (moveable) {
            if (viewWidth == 0 && viewHight == 0) {//防止转屏获取不到宽高，只初始化获取
                viewWidth = getMeasuredWidth();
                viewHight = getMeasuredHeight();
            }
            ViewGroup parentView = ((ViewGroup) getParent());
            MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
            parentWidth = parentView.getMeasuredWidth();
            parentHeight = parentView.getMeasuredHeight();
            leftPadding = parentView.getPaddingLeft();
            rightDistance = lp.rightMargin + parentView.getPaddingRight();
            maxLeftMargin = parentWidth - rightDistance - viewWidth - leftPadding;
            topPadding = parentView.getPaddingTop();
            bottomDistance = lp.bottomMargin + parentView.getPaddingBottom();
            maxTopMargin = parentHeight - bottomDistance - viewHight - topPadding;
        }
        Log.e("onSizeChanged", "onSizeChanged\n    "
                + " parentWidth " + parentWidth
                + " parentHeight " + parentHeight
                + " getRight() " + getRight()
                + " getLeft() " + getLeft()
                + " getBottom() " + getBottom()
                + " getTop() " + getTop()
                + " w " + w
                + " h " + h
                + " oldw " + oldw
                + " oldh " + oldh
                + " viewWidth " + viewWidth
                + " viewHight " + viewHight
                + " minLeftMargin " + minLeftMargin
                + " leftPadding " + leftPadding
                + " rightDistance " + rightDistance
                + " maxLeftMargin " + maxLeftMargin
                + " minTopMargin " + minTopMargin
                + " topPadding " + topPadding
                + " bottomDistance " + bottomDistance
                + " maxTopMargin " + maxTopMargin);
        initShowArea();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setAlpha(1f);
                countDownTimer.cancel();
                if (moveable) {
                    MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
                    currentX = ev.getRawX();
                    currentY = ev.getRawY();
                    currentLeft = lp.leftMargin;
                    currentTop = lp.topMargin;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (moveable) {
                    currentLeft += ev.getRawX() - currentX;
                    currentTop += ev.getRawY() - currentY;
                    //判断左边界
                    currentLeft = currentLeft < minLeftMargin ? minLeftMargin : currentLeft;
                    //判断右边界
                    currentLeft = (leftPadding + currentLeft + viewWidth + rightDistance) > parentWidth ? maxLeftMargin : currentLeft;
                    //判断上边界
                    currentTop = currentTop < minTopMargin ? minTopMargin : currentTop;
                    //判断下边界
                    currentTop = (topPadding + currentTop + viewHight + bottomDistance) > parentHeight ? maxTopMargin : currentTop;
                    MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
                    lp.leftMargin = currentLeft;
                    lp.topMargin = currentTop;
                    setLayoutParams(lp);
                    currentX = ev.getRawX();
                    currentY = ev.getRawY();
                }
                break;
            case MotionEvent.ACTION_UP:
                countDownTimer.start();
                if (moveable && autoBack) {
                    MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
                    int fromLeftMargin = lp.leftMargin;
                    if (getLeft() < (parentWidth - getLeft() - viewWidth)) {
                        lp.leftMargin = minLeftMargin;
                    } else {
                        lp.leftMargin = maxLeftMargin;
                    }
                    ObjectAnimator marginChange = ObjectAnimator.ofInt(new Wrapper(this), "leftMargin", fromLeftMargin, lp.leftMargin);
                    marginChange.setDuration(500);
                    marginChange.start();
                }
                break;
            default:
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 设置初始显示位置
     */
    public void initShowArea() {
        MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
        lp.leftMargin = maxLeftMargin;
        lp.topMargin = maxTopMargin;
        setLayoutParams(lp);
        ObjectAnimator marginChange = ObjectAnimator.ofInt(new Wrapper(this), "leftMargin", maxLeftMargin, lp.leftMargin);
        marginChange.setDuration(100);
        marginChange.start();
    }

    /**
     * 包装类
     */
    class Wrapper {
        private ViewGroup mTarget;

        public Wrapper(ViewGroup mTarget) {
            this.mTarget = mTarget;
        }

        public int getLeftMargin() {
            MarginLayoutParams lp = (MarginLayoutParams) mTarget.getLayoutParams();
            return lp.leftMargin;
        }

        public void setLeftMargin(int leftMargin) {
            MarginLayoutParams lp = (MarginLayoutParams) mTarget.getLayoutParams();
            lp.leftMargin = leftMargin;
            mTarget.requestLayout();
        }
    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            setAlpha(toAlpha);
        }
    }
}
