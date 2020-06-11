package com.longcheng.lifecareplan.modular.mine.signIn.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.OpenRedDataBean;
import com.longcheng.lifecareplan.modular.mine.signIn.bean.SignInDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface SignInContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(SignInDataBean responseBean);

        void signInSuccess(SignInDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
