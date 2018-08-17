package com.bestdo.bestdoStadiums.business.net;

import java.io.Serializable;

/**
 * Created by YuHua on 2017/5/24 19:08 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：
 */

public class BaseResponse implements Serializable {
	public String execute_time;
	public int code;
	public String msg;
	public boolean encrypt;
	public String state;
	public double timestamp;

	@Override
	public String toString() {
		return "BaseResponse{" + "execute_time='" + execute_time + '\'' + ", code=" + code + ", msg='" + msg + '\''
				+ ", encrypt=" + encrypt + ", state='" + state + '\'' + ", timestamp=" + timestamp + '}';
	}
}
