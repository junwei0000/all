package com.longcheng.volunteer.modular.mine.relationship.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.mine.relationship.bean.RelationshipBean;

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
