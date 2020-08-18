package com.longcheng.lifecareplan.modular.exchange.fragment;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.exchange.bean.JieQiListDataBean;
import com.longcheng.lifecareplan.modular.exchange.bean.MallGoodsListDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderListDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:19
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface ExChangeContract {

    interface View extends BaseView<Present> {
        void ListSuccess(MallGoodsListDataBean responseBean, int back_page);

        void JieQiListSuccess(JieQiListDataBean responseBean);

        void ListError();
    }

    abstract class Present<T> extends BasePresent<View> {

    }

    interface Model extends BaseModel {

    }


}
