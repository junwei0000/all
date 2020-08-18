package com.longcheng.lifecareplan.modular.index.welcome.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.index.welcome.bean.WelcomeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class WelcomeModelImp implements WelcomeContract.Model {

    private Context mContext;

    public WelcomeModelImp(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getViewPagerData(PagerDataListener listener) {
        List<WelcomeBean> list = new ArrayList<>();
        list.add(getWelcomeBean(-1));
        list.add(getWelcomeBean(-1));
        list.add(getWelcomeBean(-1));
        listener.success(list);
    }

    @Override
    public List<ImageView> initLineLayoutDao(List<WelcomeBean> list) {
        List<ImageView> imgList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ImageView img = new ImageView(mContext); // 现在空
            if (i == 0) {
                img.setImageResource(R.drawable.corners_oval_red);
            } else {
                img.setImageResource(R.drawable.corners_oval_redfen);
            }
            imgList.add(img);
        }
        return imgList;
    }

    @Override
    public LinearLayout.LayoutParams getDotViewParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
        params.setMargins(15, 0, 15, 15);
        return params;
    }


    private WelcomeBean getWelcomeBean(int drawableID) {
        WelcomeBean bean = new WelcomeBean();
        bean.setDrawableID(drawableID);
        return bean;
    }
}
