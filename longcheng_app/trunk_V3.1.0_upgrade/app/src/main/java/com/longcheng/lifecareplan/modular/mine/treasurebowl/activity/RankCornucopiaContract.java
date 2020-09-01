package com.longcheng.lifecareplan.modular.mine.treasurebowl.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.RankListBean;

public  interface RankCornucopiaContract {

    interface View extends BaseView<Presenter> {

        void ListSuccess(RankListBean responseBean, int pageback);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }


}
