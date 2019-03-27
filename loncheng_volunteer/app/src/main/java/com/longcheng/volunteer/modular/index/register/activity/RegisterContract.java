package com.longcheng.volunteer.modular.index.register.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.bean.ResponseBean;
import com.longcheng.volunteer.modular.index.login.bean.LoginDataBean;
import com.longcheng.volunteer.modular.index.login.bean.SendCodeBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 14:07
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface RegisterContract {

    interface View extends BaseView<Presenter> {
        void CheckPhoneSuccess(ResponseBean responseBean);

        void getCodeSuccess(SendCodeBean responseBean);

        void registerSuccess(LoginDataBean responseBean);

        void loginFail();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {

    }

}
