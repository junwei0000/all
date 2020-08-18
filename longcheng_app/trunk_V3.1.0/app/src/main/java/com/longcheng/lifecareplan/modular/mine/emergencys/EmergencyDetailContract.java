package com.longcheng.lifecareplan.modular.mine.emergencys;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;

public interface EmergencyDetailContract {
    interface View extends BaseView<EmergencyDetailContract.Presenter> {
        void ListSuccess(EmergencyDetaiBean responseBean);

        void pay(EmergencysPayBean responseBean);

        void order(HarvestBean responseBean, int backPage);


        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<EmergencyDetailContract.View> {
    }

    interface Model extends BaseModel {
    }
}
