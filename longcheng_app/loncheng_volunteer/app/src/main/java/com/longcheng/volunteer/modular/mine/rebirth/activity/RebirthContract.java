package com.longcheng.volunteer.modular.mine.rebirth.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.bean.ResponseBean;
import com.longcheng.volunteer.modular.mine.rebirth.bean.RebirthDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface RebirthContract {
    interface View extends BaseView<Presenter> {
        void getRebirthInfoSuccess(RebirthDataBean responseBean);

        void getCodeSuccess(ResponseBean responseBean);

        void RebirthSuccess(ResponseBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
