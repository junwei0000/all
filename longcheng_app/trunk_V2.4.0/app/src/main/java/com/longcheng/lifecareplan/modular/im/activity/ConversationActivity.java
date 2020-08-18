package com.longcheng.lifecareplan.modular.im.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.im.IMTool;
import com.longcheng.lifecareplan.modular.im.adapter.MoBanAdapter;
import com.longcheng.lifecareplan.modular.im.adapter.MoBanHuiAdapter;
import com.longcheng.lifecareplan.modular.im.adapter.MyConversationFragment;
import com.longcheng.lifecareplan.modular.im.bean.PionImDataBean;
import com.longcheng.lifecareplan.modular.im.bean.PionImMoBanDataBean;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.utils.RongDateUtils;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.TextMessage;

/**
 * 会话页面
 */
public class ConversationActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_bottom)
    TextView tv_bottom;

    @BindView(R.id.layout_moban)
    LinearLayout layout_moban;
    @BindView(R.id.tv_mobn)
    TextView tv_mobn;
    @BindView(R.id.lv_data)
    ListView lv_data;


    private String mTargetId, title;


    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_mobn:
                layout_moban.setVisibility(View.GONE);
                break;
            case R.id.tv_bottom:

                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.im_conversation;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tv_bottom.setOnClickListener(this);
        tv_mobn.setOnClickListener(this);
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (data != null && data.size() > 0) {
                    layout_moban.setVisibility(View.GONE);
                    huiFu(data.get(position).getMain());
                }
            }
        });


    }


    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        getIntentDate(intent);
        isReconnect(intent);
        checkCashInProgressOrder();
    }

    ArrayList<PionImMoBanDataBean.PionImMoBanItemBean> data;

    public void getQuickList() {
        String use_id = UserUtils.getUserId(mContext);
        Observable<PionImMoBanDataBean> observable = Api.getInstance().service.getQuickList(
                use_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PionImMoBanDataBean>() {
                    @Override
                    public void accept(PionImMoBanDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            data = responseBean.getData();
                            if (data != null && data.size() > 0) {
                                MoBanHuiAdapter moBanAdapter = new MoBanHuiAdapter(mContext, data);
                                lv_data.setAdapter(moBanAdapter);
                                layout_moban.setVisibility(View.VISIBLE);
                            } else {
                                layout_moban.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Observable", throwable.getMessage());
                    }
                });
    }

    boolean haveCommOrder = true;

    /**
     * 普通状态下   和祝福师有进行中
     */
    public void checkCashInProgressOrder() {
        String use_id = UserUtils.getUserId(mContext);
        Observable<PionImDataBean> observable = Api.getInstance().service.checkCashInProgressOrder(
                use_id, use_id, mTargetId, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PionImDataBean>() {
                    @Override
                    public void accept(PionImDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            if (responseBean.getData().getHasCashInProgressOrder() == 0) {
                                haveCommOrder = false;
                            }
                        }
                        checkBlessInProgressOrder();
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Observable", throwable.getMessage());
                    }
                });
    }

    boolean haveBlessOrder = true;

    /**
     * 祝福师状态下  和对方有进行中
     */
    public void checkBlessInProgressOrder() {
        String use_id = UserUtils.getUserId(mContext);
        Observable<PionImDataBean> observable = Api.getInstance().service.checkCashInProgressOrder(
                use_id, mTargetId, use_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PionImDataBean>() {
                    @Override
                    public void accept(PionImDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            if (responseBean.getData().getHasCashInProgressOrder() == 0) {
                                haveBlessOrder = false;
                            }
                            setBottom();
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Observable", throwable.getMessage());
                    }
                });
    }

    private void setBottom() {
        if (!haveBlessOrder && !haveCommOrder) {
            tv_bottom.setVisibility(View.VISIBLE);
        } else {
            getQuickList();
        }
    }

    /**
     * //示例获取 会话类型、targetId、Context,此处可根据产品需求自定义逻辑，如:开启新的 Activity 等。
     *
     * @param cont
     */
    private void huiFu(String cont) {
        Message message = Message.obtain(mTargetId, mConversationType, TextMessage.obtain(cont));
        RongIM.getInstance().sendMessage(message, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
            }

            @Override
            public void onSuccess(Message message) {
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
            }
        });
    }


    /**
     * 小灰条
     */
    private void setNotificationMessage(String time) {
        //        RongDateUtils.formatDate();
        MessageContent content = InformationNotificationMessage.obtain(time);
        RongIM.getInstance().insertIncomingMessage(
                Conversation.ConversationType.PRIVATE,
                mTargetId,
                UserUtils.getUserId(mContext),
                new Message.ReceivedStatus(1),
                content,
                new RongIMClient.ResultCallback<Message>() {
                    @Override
                    public void onSuccess(Message message) {

                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                }
        );
    }

    /**
     * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
     */
    private void getIntentDate(Intent intent) {

        mTargetId = intent.getData().getQueryParameter("targetId");
        title = intent.getData().getQueryParameter("title");
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));

        enterFragment(mConversationType, mTargetId);
        setActionBarTitle(title);
    }


    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType
     * @param mTargetId
     */
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {
        MyConversationFragment fragment = (MyConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();
        fragment.setUri(uri);
    }


    /**
     * 判断消息是否是 push 消息
     */
    private void isReconnect(Intent intent) {
        String token = IMTool.getINSTANCE().imToken;
        //push或通知过来
        if (intent != null && intent.getData() != null && intent.getData().getScheme().equals("rong")) {
            //通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息
            if (intent.getData().getQueryParameter("push") != null
                    && intent.getData().getQueryParameter("push").equals("true")) {
                reconnect(token);
            } else {
                //程序切到后台，收到消息后点击进入,会执行这里
                if (RongIM.getInstance() == null || RongIM.getInstance().getRongIMClient() == null) {
                    reconnect(token);
                } else {
                    enterFragment(mConversationType, mTargetId);
                }
            }
        }
    }


    /**
     * 设置 actionbar title
     */
    private void setActionBarTitle(String targetid) {
        pageTopTvName.setText(targetid);
    }

    /**
     * 重连
     *
     * @param token
     */
    private void reconnect(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
                enterFragment(mConversationType, mTargetId);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }
}
