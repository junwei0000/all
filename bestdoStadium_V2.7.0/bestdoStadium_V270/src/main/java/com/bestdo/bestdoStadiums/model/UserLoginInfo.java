package com.bestdo.bestdoStadiums.model;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:02:56
 * @Description 类描述：
 */
public class UserLoginInfo {
	/**
	 * 为1时需要绑定手机号
	 */
	private String isBind;
	private String uid;
	private String sid;
	private String mobile;
	String name;
	String department;
	String corp_id;
	String manage_office_name;
	String privilege_id;
	String corp_name;
	String corp_head_title;
	/**
	 * 头像地址
	 */
	private String ablum;
	private String loginName;
	private String nickName;
	private String realName;
	private String bid;
	private String bname;
	String deptId;
	String deptName;
	String active_url;
	/**
	 * 2显示，1不显示
	 */
	String ranked_show;
	String company_title;
	String department_title;
	/**
	 * UNKNOW(0, "未知"), MALE(1, "男"), FAMALE(2, "女")
	 */
	private String sex;
	private String qq;
	private String email;
	private String nameChangeTimes;

	public String getActive_url() {
		return active_url;
	}

	public void setActive_url(String active_url) {
		this.active_url = active_url;
	}

	public String getCompany_title() {
		return company_title;
	}

	public void setCompany_title(String company_title) {
		this.company_title = company_title;
	}

	public String getDepartment_title() {
		return department_title;
	}

	public void setDepartment_title(String department_title) {
		this.department_title = department_title;
	}

	public String getRanked_show() {
		return ranked_show;
	}

	public void setRanked_show(String ranked_show) {
		this.ranked_show = ranked_show;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCorp_head_title() {
		return corp_head_title;
	}

	public void setCorp_head_title(String corp_head_title) {
		this.corp_head_title = corp_head_title;
	}

	public String getCorp_name() {
		return corp_name;
	}

	public void setCorp_name(String corp_name) {
		this.corp_name = corp_name;
	}

	public String getIsBind() {
		return isBind;
	}

	public void setIsBind(String isBind) {
		this.isBind = isBind;
	}

	public UserLoginInfo() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCorp_id() {
		return corp_id;
	}

	public void setCorp_id(String corp_id) {
		this.corp_id = corp_id;
	}

	public String getManage_office_name() {
		return manage_office_name;
	}

	public void setManage_office_name(String manage_office_name) {
		this.manage_office_name = manage_office_name;
	}

	public String getPrivilege_id() {
		return privilege_id;
	}

	public void setPrivilege_id(String privilege_id) {
		this.privilege_id = privilege_id;
	}

	public UserLoginInfo(String uid, String sid, String mobile, String ablum, String loginName, String nickName,
			String realName, String sex, String qq, String email, String nameChangeTimes, String bid, String bname,
			String name, String department, String corp_id, String manage_office_name, String privilege_id,
			String corp_name, String deptId, String deptName) {
		super();
		this.uid = uid;
		this.sid = sid;
		this.mobile = mobile;
		this.ablum = ablum;
		this.loginName = loginName;
		this.nickName = nickName;
		this.realName = realName;
		this.sex = sex;
		this.qq = qq;
		this.email = email;
		this.nameChangeTimes = nameChangeTimes;
		this.bid = bid;
		this.bname = bname;
		this.name = name;
		this.department = department;
		this.corp_id = corp_id;
		this.manage_office_name = manage_office_name;
		this.privilege_id = privilege_id;
		this.corp_name = corp_name;
		this.deptId = deptId;
		this.deptName = deptName;
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

	public String getNameChangeTimes() {
		return nameChangeTimes;
	}

	public void setNameChangeTimes(String nameChangeTimes) {
		this.nameChangeTimes = nameChangeTimes;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAblum() {
		return ablum;
	}

	public void setAblum(String ablum) {
		this.ablum = ablum;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
