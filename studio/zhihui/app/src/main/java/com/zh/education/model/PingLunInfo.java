package com.zh.education.model;

public class PingLunInfo {
	private int RecordCount;
	private int id;// 总数量
	private String Title;// 名称
	private String Content;// 详情
	private String CreateUser;// 创建人
	private long CreateTime;// 创建时间
	private String HeadImg;

	public String getHeadImg() {
		return HeadImg;
	}

	public void setHeadImg(String headImg) {
		HeadImg = headImg;
	}

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getCreateUser() {
		return CreateUser;
	}

	public void setCreateUser(String createUser) {
		CreateUser = createUser;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public PingLunInfo(int recordCount, int id, String title, String content,
			String createUser, long createTime, String HeadImg) {
		super();
		this.RecordCount = recordCount;
		this.id = id;
		this.Title = title;
		this.Content = content;
		this.CreateUser = createUser;
		this.CreateTime = createTime;
		this.HeadImg = HeadImg;
	}

}
