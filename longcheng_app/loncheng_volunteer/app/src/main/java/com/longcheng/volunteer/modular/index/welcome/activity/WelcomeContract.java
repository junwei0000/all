package com.longcheng.volunteer.modular.index.welcome.activity;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.index.welcome.bean.WelcomeBean;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface WelcomeContract {
    interface View extends BaseView<Presenter> {
        //设置Viewpager的适配器
        void setPageAdapter(List<WelcomeBean> list);

        //初始化小圆点
        void addLineLayoutDot(List<ImageView> list, LinearLayout.LayoutParams params);

    }

    abstract class Presenter<T> extends BasePresent<View> {

    }

    interface Model extends BaseModel {
        //获取ViewPager的数据
        void getViewPagerData(PagerDataListener listener);

        //初始化小圆点
        List<ImageView> initLineLayoutDao(List<WelcomeBean> list);

        //获取小圆点布局的LayoutParams
        LinearLayout.LayoutParams getDotViewParams();

        interface PagerDataListener {
            void success(List<WelcomeBean> list);

            void error(String string);
        }

    }
}
