package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.fulist;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.fupackage.MyFuListBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.RankListBean;

public  interface FuListContract {

    interface View extends BaseView<Presenter> {

        void ListSuccess(MyFuListBean responseBean, int pageback);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {


    }


}
