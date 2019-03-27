package com.longcheng.volunteer.modular.mine.invitation.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.mine.invitation.bean.InvitationResultBean;

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
