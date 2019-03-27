package com.longcheng.volunteer.modular.exchange.shopcart.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.exchange.shopcart.bean.ShopCartDataBean;
import com.longcheng.volunteer.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditListDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface ShopCartContract {
    interface View extends BaseView<Presenter> {
        void GetTuiJianListSuccess(ShopCartDataBean responseBean);

        void GetAddressListSuccess(AddressListDataBean responseBean);

        void SubmitGoodsOrder(EditListDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
