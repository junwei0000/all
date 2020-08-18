package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.bean.PionDaiCDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface PionDCContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(PionDaiCDataBean responseBean, int pageback);

        void RefuseSuccess(ResponseBean responseBean);

        void editAvatarSuccess(EditDataBean responseBean);

        void backBankInfoSuccess(PionDaiCDataBean responseBean);

        void BuySuccess(PayWXDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
