package com.longcheng.lifecareplan.modular.index.register.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginDataBean;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;

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
