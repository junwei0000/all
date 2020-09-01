package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.CertifyBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.bean.AcountInfoDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface PionRecoverCashContract {
    interface View extends BaseView<Presenter> {
        void getCardInfoSuccess(CertifyBean responseBean);

        void getwalletSuccess(AcountInfoDataBean responseBean);

        void cancelPiPeiSuccess(ResponseBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
