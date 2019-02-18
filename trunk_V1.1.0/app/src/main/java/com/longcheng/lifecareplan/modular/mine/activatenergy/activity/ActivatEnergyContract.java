package com.longcheng.lifecareplan.modular.mine.activatenergy.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.GetEnergyListDataBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;

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
