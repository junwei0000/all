package com.longcheng.volunteer.modular.mine.myorder.detail.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.ActionDataBean;
import com.longcheng.volunteer.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedDataBean;
import com.longcheng.volunteer.modular.mine.myorder.detail.bean.DetailDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface DetailContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(DetailDataBean responseBean);

        void getNeedHelpNumberTaskSuccess(ActionDataBean responseBean);

        void getLifeStyleNeedHelpNumberTaskSuccess(LifeNeedDataBean responseBean);

        void editSuccess(EditDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
