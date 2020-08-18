package com.longcheng.volunteer.modular.mine.bill.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.mine.bill.bean.BillResultBean;

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
