package com.longcheng.lifecareplan.modular.mine.pioneercenter.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerCounterDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataListBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.UserBankDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetRecordDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface PioneerContract {
    interface View extends BaseView<Presenter> {

        void MenuInfoSuccess(PioneerDataBean responseBean);

        void CounterInfoSuccess(PioneerCounterDataBean responseBean);

        void paySuccess(PayWXDataBean responseBean);

        void SellFQBSuccess(ResponseBean responseBean);

        void applyMoneyListSuccess(PioneerDataListBean responseBean);

        void ListSuccess(PionOpenSetRecordDataBean responseBean, int pageback);

        void CZListSuccess(PioneerDataBean responseBean, int pageback);

        void backBankInfoSuccess(UserBankDataBean responseBean);

        void Error();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
