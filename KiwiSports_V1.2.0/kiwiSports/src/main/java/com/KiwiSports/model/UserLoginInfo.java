/**
 * 
 */
package com.KiwiSports.model;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-9-26 下午2:56:03
 * @Description 类描述：
 */
public class UserLoginInfo {
	String uid;
	String nick_name;
	String album_url;
	String hobby;
	String sex;
	String token;
	String access_token;

	public UserLoginInfo(String uid, String nick_name, String album_url,
			String hobby, String sex, String token, String access_token) {
		super();
		this.uid = uid;
		this.nick_name = nick_name;
		this.album_url = album_url;
		this.hobby = hobby;
		this.sex = sex;
		this.token = token;
		this.access_token = access_token;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getAlbum_url() {
		return album_url;
	}

	public void setAlbum_url(String album_url) {
		this.album_url = album_url;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

}
