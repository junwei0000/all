package com.longcheng.volunteer.modular.index.welcome.activity;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.longcheng.volunteer.modular.index.welcome.bean.WelcomeBean;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class WelcomePresenterImp<T> extends WelcomeContract.Presenter<WelcomeContract.View> {

    private Context mContext;
    private WelcomeContract.View mView;
    private WelcomeContract.Model mModel;

    public WelcomePresenterImp(Context mContext, WelcomeContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
        mModel = new WelcomeModelImp(mContext);
    }

    @Override
    public void fetch() {
        getPageData();
    }

    /**
     * @param
     * @name 获取ViewPager的数据
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    private void getPageData() {
        mModel.getViewPagerData(new WelcomeContract.Model.PagerDataListener() {
            @Override
            public void success(List<WelcomeBean> list) {
                mView.setPageAdapter(list);
                List<ImageView> imgList = mModel.initLineLayoutDao(list);
                LinearLayout.LayoutParams params = mModel.getDotViewParams();
                mView.addLineLayoutDot(imgList, params);
            }

            @Override
            public void error(String string) {

            }
        });

    }
}
