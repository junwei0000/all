package com.longcheng.lifecareplan.modular.mine.emergencys;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.CSRecordBean;

public interface CSRecordContract {
    interface View extends BaseView<CSRecordContract.Presenter> {
        void ListSuccess(CSRecordBean responseBean, int page);


        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<CSRecordContract.View> {
    }

    interface Model extends BaseModel {
    }
}
