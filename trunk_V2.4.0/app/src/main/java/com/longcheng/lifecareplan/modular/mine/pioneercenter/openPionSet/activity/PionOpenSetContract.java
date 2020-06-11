package com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetRecordDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.recovercash.bean.AcountInfoDataBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface PionOpenSetContract {
    interface View extends BaseView<Presenter> {
        void getUserByPhoneSuccess(PionOpenSetDataBean responseBean);

        void commitSuccess(ResponseBean responseBean);

        void ListSuccess(PionOpenSetRecordDataBean responseBean, int pageback);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
