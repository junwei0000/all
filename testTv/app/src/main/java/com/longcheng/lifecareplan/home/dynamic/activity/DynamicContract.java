package com.longcheng.lifecareplan.home.dynamic.activity;


import com.longcheng.lifecareplan.api.BasicResponse;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.login.bean.LoginAfterBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 14:07
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface DynamicContract {

    interface View extends BaseView<Presenter> {
        void getMenuInfoSuccess(BasicResponse<LoginAfterBean> responseBean);

        void onError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

}
