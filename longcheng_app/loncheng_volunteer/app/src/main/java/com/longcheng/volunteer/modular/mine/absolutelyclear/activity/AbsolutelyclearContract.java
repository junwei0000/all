package com.longcheng.volunteer.modular.mine.absolutelyclear.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;

/**
 * Created by Burning on 2018/8/30.
 */

public interface AbsolutelyclearContract {
    interface View extends BaseView<AbsolutelyclearContract.Presenter> {
        void onSuccess(String responseBean);

        void onError(String code);
    }

    abstract class Presenter<T> extends BasePresent<AbsolutelyclearContract.View> {
        abstract void setData(int page, int pageSize);
    }

    interface Model extends BaseModel {
    }
}
