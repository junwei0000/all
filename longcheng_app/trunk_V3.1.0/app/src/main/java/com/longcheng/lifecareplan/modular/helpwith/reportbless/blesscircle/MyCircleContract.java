package com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.FQDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.MessageListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface MyCircleContract {
    interface View extends BaseView<Presenter> {

        void ListSuccess(ReportDataBean responseBean, int backPage);

        void MessageListSuccess(MessageListDataBean responseBean);

        void Error();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
