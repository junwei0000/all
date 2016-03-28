package com.zh.education.model;

import java.io.Serializable;

public class BoKeInfo implements Serializable {

	private int RecordCount;
	private int id;// 总数量
	private String Title;// 名称
	private String Content;// 详情
	private String CreateUser;// 创建人
	private long CreateTime;// 创建时间
	private String Categroy;// 文字的分类
	private String CommentCount;// 评论数目
	private String LikeCount;// 点赞的数目
	private String HeadImg;
	private String Cover;

	public String getCover() {
		return Cover;
	}

	public void setCover(String cover) {
		Cover = cover;
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

	public String getCategroy() {
		return Categroy;
	}

	public void setCategroy(String categroy) {
		Categroy = categroy;
	}

	public String getCommentCount() {
		return CommentCount;
	}

	public void setCommentCount(String commentCount) {
		CommentCount = commentCount;
	}

	public String getLikeCount() {
		return LikeCount;
	}

	public void setLikeCount(String likeCount) {
		LikeCount = likeCount;
	}

	public String getHeadImg() {
		return HeadImg;
	}

	public void setHeadImg(String headImg) {
		HeadImg = headImg;
	}

	public BoKeInfo(int recordCount, int id, String title, String content,
			String createUser, long createTime, String categroy,
			String commentCount, String likeCount, String headImg, String Cover) {
		super();
		RecordCount = recordCount;
		this.id = id;
		this.Title = title;
		this.Content = content;
		this.CreateUser = createUser;
		this.CreateTime = createTime;
		this.Categroy = categroy;
		this.CommentCount = commentCount;
		this.LikeCount = likeCount;
		this.HeadImg = headImg;
		this.Cover = Cover;
	}

}
