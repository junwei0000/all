package com.longcheng.lifecareplan.modular.mine.emergencys;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.CashInfoBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.CertifyBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.HeloneedIndexBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.ResultApplyBean;

public interface FinishContract {
    interface View extends BaseView<FinishContract.Present> {
        void ListSuccess(HeloneedIndexBean responseBean, int page);

        void getUserCard(CashInfoBean responseBean);

        void appSuccess(ResultApplyBean responseBean);

        void getCard(CertifyBean str);

        void CardError(String str);

        void ListError();
    }

    abstract class Present<T> extends BasePresent<FinishContract.View> {
    }

    interface Model extends BaseModel {

    }
}
