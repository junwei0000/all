package com.longcheng.volunteer.modular.helpwith.energydetail.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.bean.ResponseBean;
import com.longcheng.volunteer.modular.helpwith.energydetail.bean.CommentDataBean;
import com.longcheng.volunteer.modular.helpwith.energydetail.bean.EnergyDetailDataBean;
import com.longcheng.volunteer.modular.helpwith.energydetail.bean.OpenRedDataBean;
import com.longcheng.volunteer.modular.helpwith.energydetail.bean.PayDataBean;
import com.longcheng.volunteer.utils.pay.PayWXDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface DetailContract {
    interface View extends BaseView<Presenter> {
        void DetailSuccess(EnergyDetailDataBean responseBean);

        void CommentListSuccess(EnergyDetailDataBean responseBean, int page);

        void SendCommentSuccess(CommentDataBean responseBean);

        void delCommentSuccess(ResponseBean responseBean);

        void PayHelpSuccess(PayWXDataBean responseBean);

        void getRedEnvelopeDataSuccess(PayDataBean responseBean);

        void OpenRedEnvelopeDataSuccess(OpenRedDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
