package com.longcheng.lifecareplan.modular.mine.relief;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;

import java.util.List;

public interface ProgressContract {
    interface View extends BaseView<Present> {

        void bottomInfoSuccess(ReliefBottomInfoBean.DataBean data, String type);

        void getReliefListSuccess(List<ReliefItemBean.DataBean> data, String page, String type);
    }

    abstract class Present<T> extends BasePresent<View> {
        public abstract void getLitsData(String user_id, String token, String s, String s1, String keyworld);

        public abstract void getReliefBottom(String type);
    }

    interface Model extends BaseModel {

    }
}
