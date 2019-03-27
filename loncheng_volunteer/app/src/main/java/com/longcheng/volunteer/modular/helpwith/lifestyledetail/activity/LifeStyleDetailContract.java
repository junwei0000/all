package com.longcheng.volunteer.modular.helpwith.lifestyledetail.activity;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;
import com.longcheng.volunteer.bean.ResponseBean;
import com.longcheng.volunteer.modular.helpwith.lifestyledetail.bean.LifeStyleCommentDataBean;
import com.longcheng.volunteer.modular.helpwith.lifestyledetail.bean.LifeStyleDetailDataBean;
import com.longcheng.volunteer.modular.helpwith.lifestyledetail.bean.SKBPayDataBean;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface LifeStyleDetailContract {
    interface View extends BaseView<Presenter> {
        void DetailSuccess(LifeStyleDetailDataBean responseBean);

        void CommentListSuccess(LifeStyleDetailDataBean responseBean, int page);

        void SendCommentSuccess(LifeStyleCommentDataBean responseBean);

        void delCommentSuccess(ResponseBean responseBean);

        void PayHelpSuccess(SKBPayDataBean responseBean);

        void ListError();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
