package com.longcheng.volunteer.modular.mine.myaddress.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface AddressContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(AddressListDataBean responseBean);

        void AddSuccess(AddressListDataBean responseBean);

        void delSuccess(EditDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
