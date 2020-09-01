package com.longcheng.lifecareplan.modular.mine.relief;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;

import java.util.List;

public interface FinishContract {
    interface View extends BaseView<Present> {


        void getReliefListSuccess(List<ReliefItemBean.DataBean> data, String page);

        void bottomInfoSuccess(ReliefBottomInfoBean.DataBean data, String type);
    }

    abstract class Present<T> extends BasePresent<View> {
        public abstract void getLitsData(String user_id, String token, String page, String type, String keyworld);

        public abstract void getReliefBottom(String type);

        public abstract void extractEnergy();
    }

    interface Model extends BaseModel {

    }
}
