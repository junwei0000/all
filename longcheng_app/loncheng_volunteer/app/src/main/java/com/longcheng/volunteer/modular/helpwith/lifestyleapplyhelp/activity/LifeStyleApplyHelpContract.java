package com.longcheng.volunteer.modular.helpwith.lifestyleapplyhelp.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.PeopleDataBean;
import com.longcheng.volunteer.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedDataBean;
import com.longcheng.volunteer.modular.mine.myaddress.bean.AddressListDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface LifeStyleApplyHelpContract {
    interface View extends BaseView<Presenter> {
        void getNeedHelpNumberTaskSuccess(LifeNeedDataBean responseBean);

        void PeopleListSuccess(PeopleDataBean responseBean);

        void applyActionSuccess(LifeNeedDataBean responseBean);

        void ListError();

        void GetAddressListSuccess(AddressListDataBean responseBean);
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
