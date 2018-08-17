package com.KiwiSports.model;

public class HobbyInfo {

	String ehobby;
	String zhobby;

	public HobbyInfo(String ehobby, String zhobby) {
		super();
		this.ehobby = ehobby;
		this.zhobby = zhobby;
	}

	public String getEhobby() {
		return ehobby;
	}

	public void setEhobby(String ehobby) {
		this.ehobby = ehobby;
	}

	public String getZhobby() {
		return zhobby;
	}

	public void setZhobby(String zhobby) {
		this.zhobby = zhobby;
	}

}
