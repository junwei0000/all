package com.longcheng.lifecareplan.modular.mine.relief;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;

public interface ReliefContract {

    interface View extends BaseView<Presenter> {


        void applySuccess();

        void bottomInfoSuccess(ReliefBottomInfoBean.DataBean data);
    }

    abstract class Presenter<T> extends BasePresent<View> {


        public abstract void applyRelief();

        public abstract void getReliefBottom();
    }

    interface Model extends BaseModel {

    }

}