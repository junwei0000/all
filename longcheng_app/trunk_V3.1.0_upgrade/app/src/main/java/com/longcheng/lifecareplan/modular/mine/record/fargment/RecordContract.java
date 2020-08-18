package com.longcheng.lifecareplan.modular.mine.record.fargment;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.MyOrderContract;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;

public interface RecordContract {

    interface View extends BaseView<Presenter> {
//        void ListSuccess(OrderListDataBean responseBean, int back_page);
//
//        void getNeedHelpNumberTaskSuccess(ActionDataBean responseBean);
//
//        void editSuccess(EditDataBean responseBean);
//
//        void careReceiveOrderSuccess(EditDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
