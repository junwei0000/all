package com.longcheng.lifecareplan.modular.mine.invitation.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.mine.invitation.bean.InvitationResultBean;

/**
 * Created by Burning on 2018/9/5.
 */

public interface InvitationContract {

    /**
     *
     */
    interface View extends BaseView<InvitationContract.Presenter> {
        void onSuccess(InvitationResultBean responseBean, int pageIndex_);

        void onError(String code);
    }

    abstract class Presenter<T> extends BasePresent<InvitationContract.View> {
        abstract void doRefresh(int page, int pageSize, String userId, String token);
    }

    interface Model extends BaseModel {
    }
}
