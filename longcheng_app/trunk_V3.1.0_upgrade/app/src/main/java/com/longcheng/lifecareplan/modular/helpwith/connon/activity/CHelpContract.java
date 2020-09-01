package com.longcheng.lifecareplan.modular.helpwith.connon.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpCreatDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpGroupRoomDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface CHelpContract {
    interface View extends BaseView<Presenter> {
        void ListSuccess(CHelpListDataBean responseBean, int page);

        void DataSuccess(CHelpDataBean responseBean);

        void DetailDataSuccess(CHelpDetailDataBean responseBean);


        void CreatePageDataSuccess(CHelpCreatDataBean responseBean);

        void GroupRoomDataSuccess(CHelpGroupRoomDataBean responseBean);

        void editSuccess(ResponseBean responseBean);

        void CreateTableSuccess(ResponseBean responseBean);

        void CreateRoomSuccess(EditDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
