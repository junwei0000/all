package com.longcheng.volunteer.modular.helpwith.energy.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.helpwith.energy.bean.ActionListDataBean;
import com.longcheng.volunteer.modular.helpwith.energy.bean.HelpEnergyListDataBean;

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
