package com.longcheng.lifecareplan.modular.helpwith.fragment;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.bean.HelpIndexDataBean;
import com.longcheng.lifecareplan.modular.home.bean.HomeDataBean;
import com.longcheng.lifecareplan.modular.home.bean.PoActionListDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:19
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface HelpWithContract {

    interface View extends BaseView<Present> {
        void getHelpIndexSuccess(HelpIndexDataBean mHomeDataBean);

        void ListError();
    }

    abstract class Present<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {

    }


}
