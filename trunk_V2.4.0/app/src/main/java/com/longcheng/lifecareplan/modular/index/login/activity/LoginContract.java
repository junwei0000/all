package com.longcheng.lifecareplan.modular.index.login.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginDataBean;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 14:07
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void loginSuccess(LoginDataBean responseBean);

        void loginFail();

        void getCodeSuccess(SendCodeBean responseBean);

        void bindPhoneSuccess(LoginDataBean responseBean);

        void updatepwSuccess(EditDataBean responseBean);
    }

    abstract class Presenter<T> extends BasePresent<View> {
        abstract void pUserAccountLogin(String phoneNum, String code);
    }

    interface Model extends BaseModel {

    }

}
