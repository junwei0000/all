package com.longcheng.lifecareplan.modular.mine.userinfo.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditThumbDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetUserSETDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface UserInfoContract {
    interface View extends BaseView<Presenter> {
        void editSuccess(EditDataBean responseBean);

        void saveInfoSuccess(EditDataBean responseBean);

        void editAvatarSuccess(EditThumbDataBean responseBean);

        void editBirthdaySuccess(EditDataBean responseBean);

        void getUserSetSuccess(GetUserSETDataBean responseBean);

        void editError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
