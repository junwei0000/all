package com.longcheng.lifecareplan.modular.mine.emergencys;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.EmergencyBangDanBean;

public interface EmergencysBangContract {
    interface View extends BaseView<EmergencysBangContract.Presenter> {
        void ListSuccess(EmergencyBangDanBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<EmergencysBangContract.View> {
    }

    interface Model extends BaseModel {
    }
}
