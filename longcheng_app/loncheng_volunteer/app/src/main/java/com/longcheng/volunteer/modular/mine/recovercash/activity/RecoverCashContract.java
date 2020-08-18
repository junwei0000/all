package com.longcheng.volunteer.modular.mine.recovercash.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.index.login.bean.SendCodeBean;
import com.longcheng.volunteer.modular.mine.recovercash.bean.AcountInfoDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditListDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface RecoverCashContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(AcountInfoDataBean responseBean);

        void getCodeSuccess(SendCodeBean responseBean);

        void TiXianSuccess(EditListDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
