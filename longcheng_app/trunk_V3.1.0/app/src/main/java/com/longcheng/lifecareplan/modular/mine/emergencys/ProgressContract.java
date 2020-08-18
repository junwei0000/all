package com.longcheng.lifecareplan.modular.mine.emergencys;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.home.bangdan.BangDanDataBean;

public interface ProgressContract {
    interface View extends BaseView<ProgressContract.Present> {
        void ListSuccess(HeloneedIndexBean responseBean, int page);

        void ListError();
    }

    abstract class Present<T> extends BasePresent<ProgressContract.View> {
    }

    interface Model extends BaseModel {

    }
}
