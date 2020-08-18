package com.longcheng.volunteer.modular.helpwith.medalrank.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.modular.helpwith.medalrank.bean.MyRankListDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface MyContract {
    interface View extends BaseView<Presenter> {
        void PersonalListSuccess(MyRankListDataBean responseBean, int back_page);

        void CommuneListSuccess(MyRankListDataBean responseBean, int back_page);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
