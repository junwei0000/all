package com.longcheng.lifecareplan.modular.mine.doctor.doctor.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.DocRewardListDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.RewardDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.VolSearchDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface DoctorContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(DocRewardListDataBean responseBean, int pageback);

        void VolSearchSuccess(VolSearchDataBean responseBean);

        void GetRewardInfoSuccess(RewardDataBean responseBean);

        void VolAddSuccess(ResponseBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
