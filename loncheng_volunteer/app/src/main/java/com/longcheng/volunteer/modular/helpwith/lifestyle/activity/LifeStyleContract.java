package com.longcheng.volunteer.modular.helpwith.lifestyle.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.helpwith.lifestyle.bean.LifeStyleListDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface LifeStyleContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(LifeStyleListDataBean responseBean, int page);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
