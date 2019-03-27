package com.longcheng.volunteer.modular.home.fragment;

import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.home.bean.HomeDataBean;
import com.longcheng.volunteer.modular.home.bean.PoActionListDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:19
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface HomeContract {

    interface View extends BaseView<Present> {
        void ListSuccess(HomeDataBean mHomeDataBean);

        void ActionListSuccess(PoActionListDataBean mHomeDataBean);


        void ListError();
    }

    abstract class Present<T> extends BasePresent<View> {
    }


}
