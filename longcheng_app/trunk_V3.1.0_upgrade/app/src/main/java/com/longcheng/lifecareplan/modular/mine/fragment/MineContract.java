package com.longcheng.lifecareplan.modular.mine.fragment;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 17:07
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface MineContract {

    interface View extends BaseView<Present> {
        void getHomeInfoSuccess(GetHomeInfoDataBean responseBean);

        void checkUserInfoSuccess(EditDataBean responseBean);

        void doStarLevelRemindSuccess(ResponseBean responseBean);

        void error();
    }

    abstract class Present<T> extends BasePresent<View> {

    }

    interface Model extends BaseModel {

    }
}
