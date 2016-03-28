package com.zh.education.model;

import org.json.JSONArray;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:02:56
 * @Description 类描述：
 */
public class UserLoginInfo {
	private String username;
	private String mobile;
	private String uid;
	private String sid;
	private String sex;

	String LoginName;
	String Name;
	String Email;
	String PictureUrl;
	String TokenStr;
	Boolean AllowBrowseUserInfo;
	String groupsStringArray[];

	Boolean IsApplicationPrincipal;
	Boolean IsDomainGroup;
	Boolean IsSiteAdmin;
	Boolean IsSiteAuditor;
	String Notes;

	String OwnedGroups;
	String RawSid;
	String Roles;
	String UserToken;
	String SchoolUrl;
	String PersonalSiteUrl;
	String TeacherSpaceUrl;
	String StudentSpaceUrl;
	String BlogUrl;
	String ClassSpaceList;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLoginName() {
		return LoginName;
	}

	public void setLoginName(String loginName) {
		LoginName = loginName;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPictureUrl() {
		return PictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		PictureUrl = pictureUrl;
	}

	public String getTokenStr() {
		return TokenStr;
	}

	public void setTokenStr(String tokenStr) {
		TokenStr = tokenStr;
	}

	public Boolean getAllowBrowseUserInfo() {
		return AllowBrowseUserInfo;
	}

	public void setAllowBrowseUserInfo(Boolean allowBrowseUserInfo) {
		AllowBrowseUserInfo = allowBrowseUserInfo;
	}

	public String[] getGroupsStringArray() {
		return groupsStringArray;
	}

	public void setGroupsStringArray(String[] groupsStringArray) {
		this.groupsStringArray = groupsStringArray;
	}

	public Boolean getIsApplicationPrincipal() {
		return IsApplicationPrincipal;
	}

	public void setIsApplicationPrincipal(Boolean isApplicationPrincipal) {
		IsApplicationPrincipal = isApplicationPrincipal;
	}

	public Boolean getIsDomainGroup() {
		return IsDomainGroup;
	}

	public void setIsDomainGroup(Boolean isDomainGroup) {
		IsDomainGroup = isDomainGroup;
	}

	public Boolean getIsSiteAdmin() {
		return IsSiteAdmin;
	}

	public void setIsSiteAdmin(Boolean isSiteAdmin) {
		IsSiteAdmin = isSiteAdmin;
	}

	public Boolean getIsSiteAuditor() {
		return IsSiteAuditor;
	}

	public void setIsSiteAuditor(Boolean isSiteAuditor) {
		IsSiteAuditor = isSiteAuditor;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String notes) {
		Notes = notes;
	}

	public String getOwnedGroups() {
		return OwnedGroups;
	}

	public void setOwnedGroups(String ownedGroups) {
		OwnedGroups = ownedGroups;
	}

	public String getRawSid() {
		return RawSid;
	}

	public void setRawSid(String rawSid) {
		RawSid = rawSid;
	}

	public String getRoles() {
		return Roles;
	}

	public void setRoles(String roles) {
		Roles = roles;
	}

	public String getUserToken() {
		return UserToken;
	}

	public void setUserToken(String userToken) {
		UserToken = userToken;
	}

	public String getSchoolUrl() {
		return SchoolUrl;
	}

	public void setSchoolUrl(String schoolUrl) {
		SchoolUrl = schoolUrl;
	}

	public String getPersonalSiteUrl() {
		return PersonalSiteUrl;
	}

	public void setPersonalSiteUrl(String personalSiteUrl) {
		PersonalSiteUrl = personalSiteUrl;
	}

	public String getTeacherSpaceUrl() {
		return TeacherSpaceUrl;
	}

	public void setTeacherSpaceUrl(String teacherSpaceUrl) {
		TeacherSpaceUrl = teacherSpaceUrl;
	}

	public String getStudentSpaceUrl() {
		return StudentSpaceUrl;
	}

	public void setStudentSpaceUrl(String studentSpaceUrl) {
		StudentSpaceUrl = studentSpaceUrl;
	}

	public String getBlogUrl() {
		return BlogUrl;
	}

	public void setBlogUrl(String blogUrl) {
		BlogUrl = blogUrl;
	}

	public String getClassSpaceList() {
		return ClassSpaceList;
	}

	public void setClassSpaceList(String classSpaceList) {
		ClassSpaceList = classSpaceList;
	}

}
