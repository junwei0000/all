package com.longcheng.lifecareplan.modular.im.Plugin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.im.activity.QuickListActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;

import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * 作者：jun on
 * 时间：2020/5/29 19:57
 * 意图：
 */
public class QuickReplyPlugin implements IPluginModule {
    Conversation.ConversationType conversationType;
    String targetId;

    @Override
    public Drawable obtainDrawable(Context context) {
        //设置插件 Plugin 图标
        return ContextCompat.getDrawable(context, R.drawable.my_huifu);
    }

    @Override
    public String obtainTitle(Context context) {
        //设置插件 Plugin 展示文字
        return "快捷回复";
    }

    @Override
    public void onClick(final Fragment currentFragment, RongExtension extension) {
        Intent intent = new Intent(currentFragment.getActivity(), QuickListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        currentFragment.getActivity().startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }


}
