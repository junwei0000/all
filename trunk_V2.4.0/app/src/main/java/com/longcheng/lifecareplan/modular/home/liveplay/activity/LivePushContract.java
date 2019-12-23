package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveStatusInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoGetSignatureInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;

import java.util.ArrayList;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:06
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface LivePushContract {
    interface View extends BaseView<Presenter> {
        void upLoadVideoSuccess(BasicResponse responseBean);

        void backSignSuccess(BasicResponse<VideoGetSignatureInfo> responseBean);

        void setFollowLiveSuccess(BasicResponse responseBean);

        void applyLiveSuccess(BasicResponse responseBean);

        void editAvatarSuccess(EditDataBean responseBean);

        void openRoomPaySuccess(BasicResponse<LiveStatusInfo> responseBean);

        void getUserLiveStatusSuccess(BasicResponse<LiveStatusInfo> responseBean);

        void BackLiveDetailSuccess(BasicResponse<LiveDetailInfo> responseBean);

        void BackLiveListSuccess(BasicResponse<VideoDataInfo> responseBean, int backPage);

        void BackVideoListSuccess(BasicResponse<ArrayList<VideoItemInfo>> responseBean, int backPage);

        void sendLCommentSuccess(BasicResponse responseBean);

        void giveGiftSuccess(BasicResponse responseBean);

        void showGiftDialog();

        void Error();
    }

    abstract class Presenter<T> extends BasePresent<View> {
    }

    interface Model extends BaseModel {
    }
}
