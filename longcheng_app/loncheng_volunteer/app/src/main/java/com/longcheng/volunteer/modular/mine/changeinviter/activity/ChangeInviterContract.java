package com.longcheng.volunteer.modular.mine.changeinviter.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.bean.ResponseBean;
import com.longcheng.volunteer.modular.mine.changeinviter.bean.InviteDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditListDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface ChangeInviterContract {
    interface View extends BaseView<Presenter> {
        void getInviteInfoSuccess(InviteDataBean responseBean);

        void SearchInviteSuccess(InviteDataBean responseBean);

        void getCodeSuccess(ResponseBean responseBean);

        void ChangeInviteSuccess(EditListDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
