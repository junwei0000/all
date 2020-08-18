package com.longcheng.volunteer.modular.helpwith.applyhelp.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.ActionDataBean;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.ActionDataListBean;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.ExplainDataBean;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.OtherUserInfoDataBean;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.PeopleDataBean;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.PeopleSearchDataBean;
import com.longcheng.volunteer.modular.index.login.bean.LoginDataBean;
import com.longcheng.volunteer.modular.index.login.bean.SendCodeBean;
import com.longcheng.volunteer.modular.mine.myaddress.bean.AddressListDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface ApplyHelpContract {
    interface View extends BaseView<Presenter> {
        void getNeedHelpNumberTaskSuccess(ActionDataBean responseBean);

        void ActionListSuccess(ActionDataListBean responseBean);

        void ActionDetailSuccess(ActionDataBean responseBean);

        void PeopleListSuccess(PeopleDataBean responseBean);

        void getOtherUserInfoSuccess(OtherUserInfoDataBean responseBean);

        void PeopleSearchListSuccess(PeopleSearchDataBean responseBean);

        void ExplainListSuccess(ExplainDataBean responseBean);

        void applyActionSuccess(ActionDataBean responseBean);

        void actionSafetySuccess(ActionDataBean responseBean);

        void saveUserInfo(LoginDataBean responseBean);

        void getCodeSuccess(SendCodeBean responseBean);

        void ListError();

        void GetAddressListSuccess(AddressListDataBean responseBean);
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
