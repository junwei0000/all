package com.longcheng.volunteer.modular.mine.myorder.tanksgiving.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.mine.myorder.tanksgiving.bean.ThanksListDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface ThanksContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(ThanksListDataBean responseBean, int back_page);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
