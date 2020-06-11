package com.longcheng.lifecareplan.modular.mine.changeinviter.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.OpenRedDataBean;
import com.longcheng.lifecareplan.modular.mine.changeinviter.bean.InviteDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;

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
