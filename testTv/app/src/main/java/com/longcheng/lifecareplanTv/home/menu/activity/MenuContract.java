package com.longcheng.lifecareplanTv.home.menu.activity;


import com.longcheng.lifecareplanTv.api.BasicResponse;
import com.longcheng.lifecareplanTv.base.BasePresent;
import com.longcheng.lifecareplanTv.base.BaseView;
import com.longcheng.lifecareplanTv.login.bean.LoginAfterBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 14:07
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface MenuContract {

    interface View extends BaseView<Presenter> {
        void getMenuInfoSuccess(BasicResponse<LoginAfterBean> responseBean);

        void onError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

}
