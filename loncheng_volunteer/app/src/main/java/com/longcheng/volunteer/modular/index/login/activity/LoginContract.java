package com.longcheng.volunteer.modular.index.login.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.index.login.bean.LoginDataBean;
import com.longcheng.volunteer.modular.index.login.bean.SendCodeBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditDataBean;

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
