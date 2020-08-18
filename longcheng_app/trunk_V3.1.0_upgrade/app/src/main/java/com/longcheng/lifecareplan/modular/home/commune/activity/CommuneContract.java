package com.longcheng.lifecareplan.modular.home.commune.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneDataBean;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditThumbDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface CommuneContract {
    interface View extends BaseView<Presenter> {
        void JoinListSuccess(CommuneListDataBean responseBean);

        void MineCommuneInfoSuccess(CommuneListDataBean responseBean);

        void MineRankListSuccess(CommuneListDataBean responseBean, int backpage);

        void GetTeamListSuccess(CommuneListDataBean responseBean);

        void GetMemberListSuccess(CommuneListDataBean responseBean, int backpage);

        void JoinCommuneSuccess(EditListDataBean responseBean);

        void ClickLikeSuccess(EditListDataBean responseBean);

        void editAvatarSuccess(EditThumbDataBean responseBean);

        void GetCreateTeamInfoSuccess(CommuneListDataBean responseBean);

        void CreateTeamSuccess(EditListDataBean responseBean);

        void CreateTeamSearchSuccess(CommuneDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
