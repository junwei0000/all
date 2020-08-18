package com.longcheng.lifecareplanTv.login.activity;


import com.longcheng.lifecareplanTv.api.BasicResponse;
import com.longcheng.lifecareplanTv.base.BaseModel;
import com.longcheng.lifecareplanTv.base.BasePresent;
import com.longcheng.lifecareplanTv.base.BaseView;
import com.longcheng.lifecareplanTv.login.bean.LoginAfterBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 14:07
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void loginSuccess(BasicResponse<LoginAfterBean> responseBean);

        void getCodeSuccess(BasicResponse<LoginAfterBean> responseBean);

        void loginFail();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {

    }

}
