package com.longcheng.volunteer.modular.mine.activatenergy.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.mine.activatenergy.bean.GetEnergyListDataBean;
import com.longcheng.volunteer.utils.pay.PayWXDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface ActivatEnergyContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(GetEnergyListDataBean responseBean);

        void ListError();

        void GetPayWXSuccess(PayWXDataBean responseBean);
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
