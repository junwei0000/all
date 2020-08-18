package com.longcheng.lifecareplan.modular.helpwith.nfcaction.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailListDataBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface NFCDetailContract {
    interface View extends BaseView<Presenter> {
        void DetailSuccess(NFCDetailDataBean responseBean);

        void DetailListSuccess(NFCDetailDataBean responseBean, int backpage);

        void PayHelpSuccess(ResponseBean responseBean);

        void DetailRecordSuccess(NFCDetailListDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
