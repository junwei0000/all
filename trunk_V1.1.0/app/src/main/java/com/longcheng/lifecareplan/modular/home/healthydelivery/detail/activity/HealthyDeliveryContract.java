package com.longcheng.lifecareplan.modular.home.healthydelivery.detail.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.bean.HealthyDeliveryResultBean;

/**
 * Created by Burning on 2018/9/13.
 */

public interface HealthyDeliveryContract {
    /**
     *
     */
    interface View extends BaseView<HealthyDeliveryContract.Presenter> {
        void onSuccess(HealthyDeliveryResultBean responseBean, int pageIndex);

        void onError(String msg);
    }

    abstract class Presenter<T> extends BasePresent<HealthyDeliveryContract.View> {
        abstract void doRefresh(int page, int pageSize, int type);
    }

    interface Model extends BaseModel {
    }
}
