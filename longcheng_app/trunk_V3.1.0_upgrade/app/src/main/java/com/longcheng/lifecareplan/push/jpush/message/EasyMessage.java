package com.longcheng.lifecareplan.push.jpush.message;

import android.os.Handler;
import android.os.Message;

import com.longcheng.lifecareplan.push.jpush.message.bean.ListenerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类可进行对象的跨类，跨线程发送，有利于数据的快速传输。<br>
 * 有以下几个作用：<br>
 * 1.可以进行类之间数据传递<br>
 * 2.可以在不同的线程间进行数据传递<br>
 * 3.可以方便的实现应用内远程数据传递，可以少量代替使用广播发送数据
 */
public class EasyMessage {
    private static List<ListenerBean> list = new ArrayList<ListenerBean>();
    private static MHandler mHandler = new MHandler();

    public static Object lastMessage;

    private EasyMessage() {
    }

    /**
     * 是否有推送消息
     *
     * @return
     */
    public static boolean haveMessageStatus() {
        if (list.size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 发送对象
     *
     * @param key   发的key，接收时需要根据这个值进行接收
     * @param value 发送的对象
     */
    public static void sendMessage(String key, Object value) {
        if (!key.equals("") && value != null) {
            Message msg = new Message();
            ListenerBean lb = new ListenerBean();
            lb.key = key;
            lb.obj = value;
            msg.obj = lb;
            mHandler.sendMessageDelayed(msg, 2000);
        }
    }

    /**
     * 注册对象接收监听器
     *
     * @param key       接收的key值，要和发送的key匹配
     * @param onMessage 接收对象的回调接口
     */
    public static void registerMessageListener(String key, OnMessageListener onMessage) {
        add(key, onMessage);
    }

    /**
     * 取消注册对象接收监听器，这个方法一定要调用，否则会出现内存泄露
     *
     * @param onMessage 接口时注册的回调接口
     */
    public static void unregisterMessageListener(OnMessageListener onMessage) {
        remove(onMessage);
    }

    /**
     * 内部处理消息并进行分发的类
     */
    private static class MHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            ListenerBean lbean = (ListenerBean) msg.obj;
            for (ListenerBean lb : list) {
                if (lb.key.equals(lbean.key)) {
                    ((OnMessageListener) lb.obj).onMessage(lbean.obj);
                    lastMessage = lbean.obj;
                }
            }
        }
    }

    /**
     * 添加监听数据和key
     *
     * @param key
     * @param listener
     */
    private static void add(String key, OnMessageListener listener) {
        if (listener != null && !key.equals("")) {
            ListenerBean lb = new ListenerBean();
            lb.key = key;
            lb.obj = listener;
            list.add(lb);
        }
    }

    /**
     * 移除一条能匹配到监听器的数据
     *
     * @param listener
     */
    private static void remove(OnMessageListener listener) {
        if (listener != null) {
            for (ListenerBean lb : list) {
                if (lb.obj.equals(listener)) {
                    list.remove(lb);
                    break;
                }
            }
        }
    }
}
