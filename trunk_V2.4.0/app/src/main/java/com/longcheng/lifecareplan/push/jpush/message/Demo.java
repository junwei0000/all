package com.longcheng.lifecareplan.push.jpush.message;

public class Demo {
	public Demo(){
		//发送
		EasyMessage.sendMessage("flag", "Demo");
		
		//接收
		EasyMessage.registerMessageListener("flag", mListener);
	}
	
	//处理消息
	public OnMessageListener mListener =new OnMessageListener() {
		public void onMessage(Object msg) { 
			System.out.println(msg+"");//打印Demo
		}
	};
	
	//当不需要时一定要调用unregisterMessageListener方法
	public void Distory(){
		//取消接收
		EasyMessage.unregisterMessageListener(mListener);
	}
}
