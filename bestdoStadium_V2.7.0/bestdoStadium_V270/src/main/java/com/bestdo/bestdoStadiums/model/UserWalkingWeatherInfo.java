/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-3-7 下午5:33:40
 * @Description 类描述：
 */
public class UserWalkingWeatherInfo {

	String wendu;
	String city;
	String pm;
	String bid;
	String bname;
	int step_num;

	public int getStep_num() {
		return step_num;
	}

	public void setStep_num(int step_num) {
		this.step_num = step_num;
	}

	public String getWendu() {
		return wendu;
	}

	public void setWendu(String wendu) {
		this.wendu = wendu;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public UserWalkingWeatherInfo(String wendu, String city, String pm, String bid, String bname, int step_num) {
		super();
		this.wendu = wendu;
		this.city = city;
		this.pm = pm;
		this.bid = bid;
		this.bname = bname;
		this.step_num = step_num;
	}

	public UserWalkingWeatherInfo() {
		super();
	}

}
