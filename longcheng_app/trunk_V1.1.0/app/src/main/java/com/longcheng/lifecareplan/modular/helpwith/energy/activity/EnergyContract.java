package com.longcheng.lifecareplan.modular.helpwith.energy.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.ActionListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpEnergyListDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface EnergyContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(HelpEnergyListDataBean responseBean, int page);

        void ActionSuccess(ActionListDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
