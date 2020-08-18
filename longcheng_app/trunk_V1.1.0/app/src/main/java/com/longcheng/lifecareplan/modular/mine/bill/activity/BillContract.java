package com.longcheng.lifecareplan.modular.mine.bill.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.bill.bean.BillResultBean;

/**
 * Created by Burning on 2018/8/30.
 */

public interface BillContract {
    /**
     *
     */
    interface View extends BaseView<BillContract.Presenter> {
        void onSuccess(BillResultBean responseBean, int pageIndex);

        void onError(String msg);
    }

    abstract class Presenter<T> extends BasePresent<BillContract.View> {
    }

    interface Model extends BaseModel {
    }
}
