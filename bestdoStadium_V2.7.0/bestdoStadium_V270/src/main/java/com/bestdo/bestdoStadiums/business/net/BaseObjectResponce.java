package com.bestdo.bestdoStadiums.business.net;

public class BaseObjectResponce<T> extends BaseResponse {
	public T getObject() {
		return data;
	}

	public void setObject(T object) {
		this.data = object;
	}

	public T data;

	@Override
	public String toString() {
		return "BaseObjectResponce{" + "data=" + data + '}';
	}
}
