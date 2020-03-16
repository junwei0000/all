package com.longcheng.lifecareplan.modular.mine.energycenter.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface EnergyCenterContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(DaiFuDataBean responseBean, int pageback);

        void RefuseSuccess(EditListDataBean responseBean);

        void editAvatarSuccess(EditDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
