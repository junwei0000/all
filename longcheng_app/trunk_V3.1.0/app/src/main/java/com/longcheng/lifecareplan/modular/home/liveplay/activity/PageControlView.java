package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.longcheng.lifecareplan.R;


public class PageControlView extends LinearLayout {
    private Context context;

    private int count;

    public void bindScrollViewGroup(ScrollLayout scrollViewGroup) {
        this.count = scrollViewGroup.getChildCount();
        System.out.println("count=" + count);
        generatePageControl(scrollViewGroup.getCurrentScreenIndex());

        scrollViewGroup.setOnScreenChangeListener(new ScrollLayout.OnScreenChangeListener() {

            public void onScreenChange(int currentIndex) {
                // TODO Auto-generated method stub
                generatePageControl(currentIndex);
            }
        });
    }

    public PageControlView(Context context) {
        super(context);
        this.init(context);
    }

    public PageControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    private void init(Context context) {
        this.context = context;
    }

    private void generatePageControl(int currentIndex) {
        this.removeAllViews();
        int pageNo = currentIndex + 1; // 第几页
        int pageSum = this.count; // 总共多少页
        if (pageSum > 1) {
            for (int i = 0; i < pageSum; i++) {
                ImageView imageView = new ImageView(context);
                LayoutParams params_linear = new LayoutParams(20, 20);
                params_linear.setMargins(20, 20, 20, 10);
                imageView.setLayoutParams(params_linear);
                if (i + 1 == pageNo) {
                    imageView.setImageResource(R.drawable.corners_circular_anliang);
                } else {
                    imageView.setImageResource(R.drawable.corners_circular_an);
                }
                this.addView(imageView);
            }
        }

    }
}