package com.longcheng.lifecareplan.modular.im.Plugin;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.emoticon.IEmoticonTab;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

/**
 * 作者：jun on
 * 时间：2020/5/29 19:58
 * 意图：调用 QuickReplyPlugin ：
 */
public class SampleExtensionModule extends DefaultExtensionModule {
    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModuleList = new ArrayList<>();
        pluginModuleList.add(new QuickReplyPlugin());
//        pluginModuleList.add(new ImagePlugin());
        return pluginModuleList;
    }

    @Override
    public List<IEmoticonTab> getEmoticonTabs() {
        return super.getEmoticonTabs();
    }
}
