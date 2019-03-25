package com.longcheng.lifecareplantv.modular.login.activity;

import com.longcheng.lifecareplantv.base.BaseModel;
import com.longcheng.lifecareplantv.base.BasePresent;
import com.longcheng.lifecareplantv.base.BaseView;
import com.longcheng.lifecareplantv.bean.Bean;
import com.longcheng.lifecareplantv.http.basebean.BasicResponse;
import com.longcheng.lifecareplantv.modular.login.bean.LoginAfterBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 14:07
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void loginSuccess(BasicResponse<LoginAfterBean> responseBean);

        void getCodeSuccess(BasicResponse<Bean> responseBean);

        void loginFail();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {

    }

}
