package com.longcheng.lifecareplan.modular.mine.absolutelyclear.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.mine.absolutelyclear.mode.PointF;
import com.longcheng.lifecareplan.modular.mine.absolutelyclear.mode.RectPoint;

/**
 * Created by Burning on 2018/8/29.
 */

public class DiyEnvelope extends View {
    int width;
    int hight;

    int x = 50;
    int y = 10;
    int offset = 20;

    private Paint paintRed;
    private Paint paintBlue;
    private int[] countLineItem = {0, 0, 0, 0};//top,right,bottom,left
    private boolean[] flag = {false, false, false, false};


    public DiyEnvelope(Context context) {
        super(context);
        init();
    }

    public DiyEnvelope(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiyEnvelope(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paintRed = new Paint();
        paintRed.setAntiAlias(true);
        paintRed.setColor(getResources().getColor(R.color.red));
        paintRed.setStyle(Paint.Style.FILL);

        paintBlue = new Paint();
        paintBlue.setAntiAlias(true);
        paintBlue.setColor(getResources().getColor(R.color.blue));
        paintBlue.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int temp_width = getMeasuredWidth();
        int temp_hight = getMeasuredHeight();
        if (temp_width != 0) {
            width = temp_width;
        }
        if (temp_hight != 0) {
            hight = temp_hight;
        }
        resetItemCount();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLineTop(canvas, countLineItem[0], x + offset, 0, flag[0]);
        drawLineLeft(canvas, countLineItem[3], x + offset, countLineItem[2], flag[3]);
        drawLineRight(canvas, countLineItem[1], x + offset, countLineItem[0], flag[1]);
        drawLineBottom(canvas, countLineItem[2], x + offset, countLineItem[1], flag[2]);
    }

    private void resetItemCount() {
        if (hight <= 0 || width <= 0) {
            return;
        }
        int one = x + offset;
        int countH = width / one;
        if (width % one != 0) {
            countH++;
        }
        countLineItem[0] = countH;
        countLineItem[2] = countH;

        int countV = hight / one;
        if (hight % one != 0) {
            countV++;
        }
        countLineItem[1] = countV;
        countLineItem[3] = countV;

        flag[0] = true;
        flag[1] = countLineItem[0] % 2 == 0;
        flag[1] = (countLineItem[0] + countLineItem[1] - 1) % 2 == 0;
        flag[2] = (countLineItem[0] + countLineItem[1] + countLineItem[2] - 2) % 2 == 0;
    }

    private void drawLineTop(Canvas canvas, int count, int one, int preCount, boolean flag) {
        int xWidth = x;
        int yHight = y;
        RectPoint rectStart = new RectPoint(new PointF(0, 0), new PointF(xWidth, 0), new PointF(offset, yHight), new PointF(xWidth + offset, yHight));
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            int offset = one * i;
            path.reset();
            path.moveTo(rectStart.getPointLeftTop().getX() + offset, rectStart.getPointLeftTop().getY());
            path.lineTo(rectStart.getPointRightTop().getX() + offset, rectStart.getPointRightTop().getY());
            path.lineTo(rectStart.getPointRightButtom().getX() + offset, rectStart.getPointRightButtom().getY());
            path.lineTo(rectStart.getPointLeftButtom().getX() + offset, rectStart.getPointLeftButtom().getY());
            path.close();
            canvas.drawPath(path, i % 2 == 0 ? paintRed : paintBlue);
        }

        canvas.drawRect(new RectF(0, 0, offset, yHight), flag ? paintRed : paintBlue);
    }

    private void drawLineBottom2(Canvas canvas, int count, int one, int preCount, boolean flag) {
        int xWidth = x;
        int yHight = y;
        RectPoint rectStart = new RectPoint(new PointF(0, hight - yHight), new PointF(xWidth, hight - yHight), new PointF(offset, hight), new PointF(xWidth + offset, hight));
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            int offset = one * i;
            path.reset();
            path.moveTo(rectStart.getPointLeftTop().getX() + offset, rectStart.getPointLeftTop().getY());
            path.lineTo(rectStart.getPointRightTop().getX() + offset, rectStart.getPointRightTop().getY());
            path.lineTo(rectStart.getPointRightButtom().getX() + offset, rectStart.getPointRightButtom().getY());
            path.lineTo(rectStart.getPointLeftButtom().getX() + offset, rectStart.getPointLeftButtom().getY());
            path.close();
            canvas.drawPath(path, (i % 2 == 0) == flag ? paintRed : paintBlue);
        }
//        canvas.drawRect(new RectF(0,hight-y,offset,hight),flag?paintRed:paintBlue);
    }

    private void drawLineBottom(Canvas canvas, int count, int one, int preCount, boolean flag) {
        int xWidth = x;
        int yHight = y;
        RectPoint rectStart = new RectPoint(new PointF(width - xWidth, hight - yHight), new PointF(width, hight - yHight), new PointF(width - xWidth - offset, hight), new PointF(width - offset, hight));
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            int offset = one * i;
            path.reset();
            path.moveTo(rectStart.getPointLeftTop().getX() - offset, rectStart.getPointLeftTop().getY());
            path.lineTo(rectStart.getPointRightTop().getX() - offset, rectStart.getPointRightTop().getY());
            path.lineTo(rectStart.getPointRightButtom().getX() - offset, rectStart.getPointRightButtom().getY());
            path.lineTo(rectStart.getPointLeftButtom().getX() - offset, rectStart.getPointLeftButtom().getY());
            path.close();
            canvas.drawPath(path, (i % 2 == 0) == flag ? paintRed : paintBlue);
        }
        canvas.drawRect(new RectF(width - offset, hight - yHight, width, hight), flag ? paintRed : paintBlue);
    }

    private void drawLineLeft(Canvas canvas, int count, int one, int preCount, boolean flag) {
        int xWidth = y;
        int yHight = x;

        RectPoint rectStart = new RectPoint(new PointF(0, hight - offset - yHight), new PointF(xWidth, hight - yHight), new PointF(0, hight - offset), new PointF(xWidth, hight));
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            int offset = one * i;
            path.reset();
            path.moveTo(rectStart.getPointLeftTop().getX(), rectStart.getPointLeftTop().getY() - offset);
            path.lineTo(rectStart.getPointRightTop().getX(), rectStart.getPointRightTop().getY() - offset);
            path.lineTo(rectStart.getPointRightButtom().getX(), rectStart.getPointRightButtom().getY() - offset);
            path.lineTo(rectStart.getPointLeftButtom().getX(), rectStart.getPointLeftButtom().getY() - offset);
            path.close();
            canvas.drawPath(path, (i % 2 == 0) == flag ? paintRed : paintBlue);
        }
        canvas.drawRect(new RectF(0, hight - offset, xWidth, hight), flag ? paintRed : paintBlue);
    }

    private void drawLineRight(Canvas canvas, int count, int one, int preCount, boolean flag) {
        int xWidth = y;
        int yHight = x;

        RectPoint rectStart = new RectPoint(new PointF(width - xWidth, offset), new PointF(width, 0), new PointF(width - xWidth, yHight + offset), new PointF(width, yHight));
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            int offset = one * i;
            path.reset();
            path.moveTo(rectStart.getPointLeftTop().getX(), rectStart.getPointLeftTop().getY() + offset);
            path.lineTo(rectStart.getPointRightTop().getX(), rectStart.getPointRightTop().getY() + offset);
            path.lineTo(rectStart.getPointRightButtom().getX(), rectStart.getPointRightButtom().getY() + offset);
            path.lineTo(rectStart.getPointLeftButtom().getX(), rectStart.getPointLeftButtom().getY() + offset);
            path.close();
            canvas.drawPath(path, (i % 2 == 0) == flag ? paintRed : paintBlue);
        }
        canvas.drawRect(new RectF(width - xWidth, 0, width, offset), flag ? paintRed : paintBlue);
    }
}
