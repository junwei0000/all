package com.longcheng.lifecareplan.modular.im;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.im.Plugin.SampleExtensionModule;
import com.longcheng.lifecareplan.modular.im.bean.PionImListInfoDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;
import io.rong.push.pushconfig.PushConfig;

/**
 * 作者：jun on
 * 时间：2020/5/28 10:51
 * 意图：
 */
public class IMTool {
    private static volatile IMTool INSTANCE;
    public String imToken;


    public static IMTool getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (IMTool.class) {
                if (INSTANCE == null) {
                    INSTANCE = new IMTool();
                }
            }
        }
        return INSTANCE;
    }

    public void initRongYun() {
        PushConfig config = new PushConfig.Builder()
                .build();
        RongPushClient.setPushConfig(config);

        // 初始化. 建议在 Application 中进行初始化.
        String appKey = Config.RONGYUN_APPKEY;
        RongIM.init(ExampleApplication.getContext(), appKey);
        /**
         * 设置消息体内是否携带用户信息。
         * @param state 是否携带用户信息，true 携带，false 不携带。
         */
        RongIM.getInstance().setMessageAttachedUserInfo(true);
        RongIM.getInstance().enableNewComingMessageIcon(true);//显示新消息提醒
        RongIM.getInstance().enableUnreadMessageIcon(true);//显示未读消息数目
    }

    /**
     * 设置用户信息，
     */
    public void setCurrentUserInfo(String user_id, String user_name, String avatar, boolean reflshother) {
        if (!TextUtils.isEmpty(user_id)) {
            getImToken(user_id, reflshother);
            RongIM.getInstance().refreshUserInfoCache(new UserInfo(user_id, user_name, Uri.parse(avatar)));
            RongIM.getInstance().setCurrentUserInfo(new UserInfo(user_id, user_name, Uri.parse(avatar)));
            if (!reflshother) {
                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                    /**
                     * 获取设置用户信息. 通过返回的 userId 来封装生产用户信息.
                     *
                     * @param userId 用户 ID
                     */
                    @Override
                    public UserInfo getUserInfo(String userId) {
                        UserInfo userInfo = new UserInfo(userId, user_name, Uri.parse(avatar));
                        return userInfo;
                    }
                }, true);
            }
        }
    }

    /**
     * 用户首页信息
     */
    private void getImToken(String user_id, boolean reflshother) {
        Observable<GetHomeInfoDataBean> observable = Api.getInstance().service.getImToken(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<GetHomeInfoDataBean>() {
                    @Override
                    public void accept(GetHomeInfoDataBean responseBean) throws Exception {
                        imToken = responseBean.getData().getImToken();
                        Log.e("imConnect", "imToken=" + imToken);
                        if (!reflshother)
                            imConnect();
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    private void imConnect() {
        RongIM.connect(imToken, new RongIMClient.ConnectCallbackEx() {

            /**
             * 数据库回调.
             * @param code 数据库打开状态. DATABASE_OPEN_SUCCESS 数据库打开成功; DATABASE_OPEN_ERROR 数据库打开失败
             */
            @Override
            public void OnDatabaseOpened(RongIMClient.DatabaseOpenStatus code) {

            }

            /**
             * token 无效
             */
            @Override
            public void onTokenIncorrect() {
                Log.e("imConnect", "onTokenIncorrect=");
            }

            /**
             * 成功回调
             * @param userId 当前用户 ID
             */
            @Override
            public void onSuccess(String userId) {
                Log.e("imConnect", "onSuccess=" + userId);
                setMyExtensionModule();
            }

            /**
             * 错误回调
             * @param errorCode 错误码
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("imConnect", "onError=" + errorCode.getMessage());
            }
        });
    }

    public void startConversation(Context context, String targetId, String title) {
        RongIM.getInstance().startConversation(context, Conversation.ConversationType.PRIVATE, targetId, title);
    }

    public void startConversationList(Context context) {
        RongIM.getInstance().startConversationList(context);
    }


    /**
     * 获取所有未读数
     *
     * @param textView
     */
    public void setUnReadNum(TextView textView) {
        if (textView == null) {
            return;
        }
        RongIM.getInstance().addUnReadMessageCountChangedObserver(i -> {
            // i 是未读数量
            if (i < 1) {
                textView.setVisibility(View.GONE);
            } else if (i < 100) {
                textView.setVisibility(View.VISIBLE);
                textView.setText("" + i);
            } else {
                textView.setVisibility(View.VISIBLE);
                textView.setText("99+");
            }
        }, Conversation.ConversationType.PRIVATE);
    }

    /**
     * 获取指定用户未读数
     *
     * @param targetId
     */
    public void getUnreadCountById(String targetId, TextView textView) {
        RongIMClient.getInstance().getUnreadCount(Conversation.ConversationType.PRIVATE, targetId,
                new RongIMClient.ResultCallback<Integer>() {
                    /**
                     * 成功回调
                     * @param unReadCount 未读数
                     */
                    @Override
                    public void onSuccess(Integer unReadCount) {
                        if (unReadCount < 1) {
                            textView.setVisibility(View.GONE);
                        } else if (unReadCount < 100) {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText("" + unReadCount);
                        } else {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText("99+");
                        }
                        Log.e("onGotUserInfo", "onSuccess=" + unReadCount);
                    }

                    /**
                     * 错误回调
                     * @param errorCode 错误码
                     */
                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
    }


    /**
     * 在初始化之后，取消 SDK 默认的 ExtensionModule，注册自定义的 ExtensionModule
     */
    public void setMyExtensionModule() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new SampleExtensionModule());
            }
        }
    }


    public void refrshDateList(String user_id) {
        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                String userid = "";
                if (conversations != null && conversations.size() > 0) {
                    for (int i = 0; i < conversations.size(); i++) {
                        if (i == conversations.size() - 1) {
                            userid = userid + conversations.get(0).getTargetId();
                        } else {
                            userid = userid + conversations.get(0).getTargetId() + ",";
                        }
                    }
                    getUserInfos(user_id, userid);
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    /**
     * 用户信息
     */
    private void getUserInfos(String user_id, String user_ids) {
        Observable<PionImListInfoDataBean> observable = Api.getInstance().service.getIMUserInfos(user_id, user_ids, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PionImListInfoDataBean>() {
                    @Override
                    public void accept(PionImListInfoDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            ArrayList<PionImListInfoDataBean.PionImInfoItemBean> data = responseBean.getData();
                            if (data != null && data.size() > 0) {
                                for (PionImListInfoDataBean.PionImInfoItemBean pionImInfoItemBean : data) {
                                    setCurrentUserInfo(pionImInfoItemBean.getUser_id(), pionImInfoItemBean.getUser_name(), pionImInfoItemBean.getAvatar(), true);
                                }
                            }
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }
}
