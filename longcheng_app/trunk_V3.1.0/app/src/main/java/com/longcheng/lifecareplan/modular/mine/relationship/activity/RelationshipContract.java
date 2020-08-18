package com.longcheng.lifecareplan.modular.mine.relationship.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.mine.relationship.bean.RelationshipBean;

/**
 * Created by Burning on 2018/9/1.
 */

public interface RelationshipContract {
    /**
     *
     */
    interface View extends BaseView<RelationshipContract.Presenter> {
        void onSuccess(RelationshipBean responseBean);

        void onError(String code);
    }

    abstract class Presenter<T> extends BasePresent<RelationshipContract.View> {
        abstract void doRefresh(String userId, String token);
    }

    interface Model extends BaseModel {
    }
}
