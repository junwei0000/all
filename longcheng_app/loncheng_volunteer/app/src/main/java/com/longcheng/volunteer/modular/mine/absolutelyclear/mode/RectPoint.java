package com.longcheng.volunteer.modular.mine.absolutelyclear.mode;

/**
 * Created by Burning on 2018/8/29.
 */

public class RectPoint {
    private PointF pointLeftTop;
    private PointF pointRightTop;
    private PointF pointLeftButtom;
    private PointF pointRightButtom;

    public RectPoint() {
        super();
    }

    public RectPoint(PointF pointLeftTop, PointF pointRightTop, PointF pointLeftButtom, PointF pointRightButtom) {
        super();
        this.pointLeftTop = pointLeftTop;
        this.pointRightTop = pointRightTop;
        this.pointLeftButtom = pointLeftButtom;
        this.pointRightButtom = pointRightButtom;
    }

    public PointF getPointLeftTop() {
        return pointLeftTop;
    }

    public void setPointLeftTop(PointF pointLeftTop) {
        this.pointLeftTop = pointLeftTop;
    }

    public PointF getPointRightTop() {
        return pointRightTop;
    }

    public void setPointRightTop(PointF pointRightTop) {
        this.pointRightTop = pointRightTop;
    }

    public PointF getPointLeftButtom() {
        return pointLeftButtom;
    }

    public void setPointLeftButtom(PointF pointLeftButtom) {
        this.pointLeftButtom = pointLeftButtom;
    }

    public PointF getPointRightButtom() {
        return pointRightButtom;
    }

    public void setPointRightButtom(PointF pointRightButtom) {
        this.pointRightButtom = pointRightButtom;
    }
}
