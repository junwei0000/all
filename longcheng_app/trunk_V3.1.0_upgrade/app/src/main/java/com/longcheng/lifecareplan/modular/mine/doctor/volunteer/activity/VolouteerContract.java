package com.longcheng.lifecareplan.modular.mine.doctor.volunteer.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.DocRewardListDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.RewardDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.volunteer.bean.AYRecordListDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface VolouteerContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(DocRewardListDataBean responseBean, int pageback);
        void AYRecordListSuccess(AYRecordListDataBean responseBean, int pageback);
        void GetRewardInfoSuccess(RewardDataBean responseBean);
        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
