package com.longcheng.volunteer.modular.index.forgetPw.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.index.login.bean.SendCodeBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 14:07
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface ForgetPWContract {

    interface View extends BaseView<Presenter> {
        void checkPhoneSuccess(EditDataBean responseBean);

        void getCodeSuccess(SendCodeBean responseBean);

        void loginFail();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {

    }

}
