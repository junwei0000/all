package com.longcheng.lifecareplan.modular.mine.doctor.aiyou.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.bean.HealTeackDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean.AYApplyListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface AiYouContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(AYApplyListDataBean responseBean, int pageback);

        void BackDaySuccess(HealTeackDataBean responseBean);

        void editAvatarSuccess(EditDataBean responseBean);
        void AddPicSuccess(ResponseBean responseBean);
        void DocAddIdeaSuccess(ResponseBean responseBean);
        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
