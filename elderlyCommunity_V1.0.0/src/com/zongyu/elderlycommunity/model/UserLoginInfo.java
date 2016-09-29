/**
 * 
 */
package com.zongyu.elderlycommunity.model;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-9-26 下午2:56:03
 * @Description 类描述：
 */
public class UserLoginInfo {

	private static class SingletonHolder {
		private static final UserLoginInfo INSTANCE = new UserLoginInfo();
	}

	private UserLoginInfo() {
	}

	public static final UserLoginInfo getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private String tokenUse;
	String uid;
	String real_name;
	String nick_name;
	String email;
	String telephone;
	String ablum_url;
	String sex;

	public UserLoginInfo(String uid, String real_name, String nick_name,
			String email, String telephone, String ablum_url, String sex) {
		super();
		this.uid = uid;
		this.real_name = real_name;
		this.nick_name = nick_name;
		this.email = email;
		this.telephone = telephone;
		this.ablum_url = ablum_url;
		this.sex = sex;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAblum_url() {
		return ablum_url;
	}

	public void setAblum_url(String ablum_url) {
		this.ablum_url = ablum_url;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTokenUse() {
		return tokenUse;
	}

	public void setTokenUse(String tokenUse) {
		this.tokenUse = tokenUse;
	}

}
