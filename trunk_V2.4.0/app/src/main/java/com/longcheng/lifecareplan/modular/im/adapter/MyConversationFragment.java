package com.longcheng.lifecareplan.modular.im.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.widget.adapter.MessageListAdapter;

/**
 * 作者：jun on
 * 时间：2020/6/2 10:44
 * 意图：
 */
public class MyConversationFragment extends ConversationFragment {
    public MessageListAdapter onResolveAdapter(Context context) {
        return new MyMessageListAdapter(context);
    }
}
