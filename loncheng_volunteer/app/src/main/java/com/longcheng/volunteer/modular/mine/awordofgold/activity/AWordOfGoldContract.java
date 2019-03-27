package com.longcheng.volunteer.modular.mine.awordofgold.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.mine.awordofgold.bean.AWordOfGoldResponseBean;

/**
 * Created by Burning on 2018/9/4.
 */

public interface AWordOfGoldContract {

    /**
     *
     */
    interface View extends BaseView<AWordOfGoldContract.Presenter> {
        void onSuccess(AWordOfGoldResponseBean responseBean);

        void onError(String code);
    }

    abstract class Presenter<T> extends BasePresent<AWordOfGoldContract.View> {
        abstract void doRefresh(String userId, String token, String selectUserId);
    }

    interface Model extends BaseModel {
    }
}
