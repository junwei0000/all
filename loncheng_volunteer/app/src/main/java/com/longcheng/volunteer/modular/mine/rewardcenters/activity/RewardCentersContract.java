package com.longcheng.volunteer.modular.mine.rewardcenters.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.mine.rewardcenters.bean.RewardCentersResultBean;

/**
 * Created by Burning on 2018/9/4.
 */

public interface RewardCentersContract {
    /**
     *
     */
    interface View extends BaseView<RewardCentersContract.Presenter> {
        void onSuccess(RewardCentersResultBean responseBean, int pageIndex);

        void onError(String code);
    }

    abstract class Presenter<T> extends BasePresent<RewardCentersContract.View> {
        abstract void doRefresh(int page, int pageSize, String userId, String token);
    }

    interface Model extends BaseModel {
    }
}
