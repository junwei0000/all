package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBHistoryDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBSelectDataBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface PionZFBContract {
    interface View extends BaseView<Presenter> {
        void getwalletSuccess(PionZFBDataBean responseBean);

        void SelectSuccess(PionZFBSelectDataBean responseBean);
        void historySuccess(PionZFBHistoryDataBean responseBean);
        void cancelPiPeiSuccess(ResponseBean responseBean);

        void PaySuccess(PayWXDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
