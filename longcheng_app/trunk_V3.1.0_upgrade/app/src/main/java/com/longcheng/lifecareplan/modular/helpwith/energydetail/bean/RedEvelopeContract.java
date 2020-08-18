package com.longcheng.lifecareplan.modular.helpwith.energydetail.bean;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;

/**
 * Created by Burning on 2018/9/8.
 */

public interface RedEvelopeContract {
    /**
     *
     */
    interface View extends BaseView<RedEvelopeContract.Presenter> {
        void getRedEnvelopeSuccess(PayDataBean responseBean);

        void OpenRedEnvelopeSuccess(OpenRedDataBean responseBean);

        void onGetRedEnvelopeError(String msg);

        void onOpenRedEnvelopeError(String msg);
    }

    abstract class Presenter<T> extends BasePresent<RedEvelopeContract.View> {
        /**
         * 获取红包信息
         *
         * @param user_id
         * @param one_order_id
         */
        public abstract void getRedEnvelopeData(String user_id, String one_order_id);

        /**
         * 开红包
         *
         * @param user_id
         * @param one_order_id
         */
        public abstract void openRedEnvelope(String user_id, String one_order_id);
    }

    interface Model extends BaseModel {
    }
}
