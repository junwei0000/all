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
        list.add(getWelcomeBean(mContext.getResources().getDrawable(R.mipmap.appa_guide_page)));
        list.add(getWelcomeBean(mContext.getResources().getDrawable(R.mipmap.appb_guide_page)));
        list.add(getWelcomeBean(mContext.getResources().getDrawable(R.mipmap.appc_guide_page)));
        listener.success(list);
    }

    @Override
    public List<ImageView> initLineLayoutDao(List<WelcomeBean> list) {
        List<ImageView> imgList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ImageView img = new ImageView(mContext); // 现在空
            if (i == 0) {
                img.setImageResource(R.mipmap.lunbo_chose);
            } else {
                img.setImageResource(R.mipmap.lunbo_nochose);
            }
            imgList.add(img);
        }
        return imgList;
    }

    @Override
    public LinearLayout.LayoutParams getDotViewParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
        params.setMargins(5, 0, 5, 5);
        return params;
    }


    private WelcomeBean getWelcomeBean(Drawable drawable) {
        WelcomeBean bean = new WelcomeBean();
        bean.setDrawable(drawable);
        return bean;
    }
}
