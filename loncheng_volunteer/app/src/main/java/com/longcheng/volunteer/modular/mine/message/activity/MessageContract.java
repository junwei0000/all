package com.longcheng.volunteer.modular.mine.message.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.helpwith.energydetail.bean.OpenRedDataBean;
import com.longcheng.volunteer.modular.mine.message.bean.MessageDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface MessageContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(MessageDataBean responseBean, int back_page);

        void OpenRedEnvelopeSuccess(OpenRedDataBean responseBean);

        void ListError();

        void onOpenRedEnvelopeError(String msg);
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
