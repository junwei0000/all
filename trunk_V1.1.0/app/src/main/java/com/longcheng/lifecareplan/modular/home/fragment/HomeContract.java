package com.longcheng.lifecareplan.modular.home.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.IInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.helpwith.bean.HelpIndexDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.ActionListDataBean;
import com.longcheng.lifecareplan.modular.home.bean.HomeDataBean;
import com.longcheng.lifecareplan.modular.home.bean.PoActionListDataBean;
import com.longcheng.lifecareplan.modular.home.bean.QuickTeamDataBean;
import com.longcheng.lifecareplan.modular.index.welcome.bean.WelcomeBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:19
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface HomeContract {

    interface View extends BaseView<Present> {
        void ListSuccess(HomeDataBean mHomeDataBean);

        void ActionListSuccess(PoActionListDataBean mHomeDataBean);

        void QuickTeamUrlSuccess(QuickTeamDataBean mHomeDataBean);

        void ListError();
    }

    abstract class Present<T> extends BasePresent<View> {
    }


}
