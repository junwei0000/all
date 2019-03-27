package com.longcheng.volunteer.modular.exchange.fragment;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.exchange.bean.JieQiListDataBean;
import com.longcheng.volunteer.modular.exchange.bean.MallGoodsListDataBean;

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
