package com.longcheng.lifecareplan.push.jpush.message;

/**
 * 消息监听
 */
public abstract class MessageListener {

    public abstract void addNewMessage(MessageInfo mMessageInfo);

}