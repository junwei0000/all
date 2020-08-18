package com.longcheng.lifecareplan.modular.mine.relief;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;

import java.util.List;

public interface ReliefDetailsContract {

    interface View extends BaseView<Presenter> {

        void getReliefDetailSuccess(ReliefDetailsBean.DataBean data);

        void getListSuccess(List<ReliefDetailsListBean.DataBean> data, String page);
    }

    abstract class Presenter<T> extends BasePresent<View> {


        public abstract void getDetailsList(String userid, String checkUid);

        public abstract void getDetailsTop(String uid);
    }

    interface Model extends BaseModel {

    }

}