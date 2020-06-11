package com.longcheng.lifecareplan.modular.helpwith.myfamily.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.MyFamilyDataBean;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.MyFamilyListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.RelationListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface MyContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(MyFamilyListDataBean responseBean);

        void DelSuccess(EditDataBean responseBean);

        void addOrEditSuccess(EditListDataBean responseBean);

        void GetFamilyRelationSuccess(RelationListDataBean responseBean);

        void GetFamilyEditInfoSuccess(MyFamilyDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
