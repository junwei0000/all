package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CircleBean implements Serializable {
	private static final long serialVersionUID = -8546131748993549867L;

	public String iconUri;// 头像uri
	public String nickName;// 昵称
	public int goodCount;// 赞数
	public int commentCount;// 评论数
	public String releaseTime;// 发布时间
	public String dynamicDesc;// 动态详情

	public boolean isClickGood;// 是否点过赞
	public List<String> imgList;// 图片集合
	public List<String> thumb_urlList;// 缩略图集合
	public String wonderful_id;// 精彩时刻id
	public String theme;// 标题
	public String content;// 内容
	public String laud_count;// 点赞数量
	public String create_time;// 发布时间
	public String share_count;// 分享数量

	public String bestdo_uid;// 发布用户uid
	public String name;// 用户名
	public ArrayList<String> widthList;// 视频缩略图宽度
	public ArrayList<String> heightList;// 视频缩略图高度
	public String img_video_type;// 0 文本信息 1视频信息 2图片信息
	public String avatar;// 头像uri
	public String wondful_show_url;

	public int position;

	public CircleBean() {
		super();
	}

	public CircleBean(boolean isClickGood, List<String> imgList, List<String> thumb_urlList, String wonderful_id,
			String theme, String content, String laud_count, String create_time, String share_count, String bestdo_uid,
			String name, ArrayList<String> widthList, ArrayList<String> heightList, String img_video_type,
			String avatar, String wondful_show_url) {
		super();
		this.isClickGood = isClickGood;
		this.imgList = imgList;
		this.thumb_urlList = thumb_urlList;
		this.wonderful_id = wonderful_id;
		this.theme = theme;
		this.content = content;
		this.laud_count = laud_count;
		this.create_time = create_time;
		this.share_count = share_count;
		this.bestdo_uid = bestdo_uid;
		this.name = name;
		this.widthList = widthList;
		this.heightList = heightList;
		this.img_video_type = img_video_type;
		this.avatar = avatar;
		this.wondful_show_url = wondful_show_url;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getWondful_show_url() {
		return wondful_show_url;
	}

	public void setWondful_show_url(String wondful_show_url) {
		this.wondful_show_url = wondful_show_url;
	}

	public boolean isClickGood() {
		return isClickGood;
	}

	public void setClickGood(boolean isClickGood) {
		this.isClickGood = isClickGood;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	public List<String> getThumb_urlList() {
		return thumb_urlList;
	}

	public void setThumb_urlList(List<String> thumb_urlList) {
		this.thumb_urlList = thumb_urlList;
	}

	public String getWonderful_id() {
		return wonderful_id;
	}

	public void setWonderful_id(String wonderful_id) {
		this.wonderful_id = wonderful_id;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLaud_count() {
		return laud_count;
	}

	public void setLaud_count(String laud_count) {
		this.laud_count = laud_count;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getShare_count() {
		return share_count;
	}

	public void setShare_count(String share_count) {
		this.share_count = share_count;
	}

	public String getBestdo_uid() {
		return bestdo_uid;
	}

	public void setBestdo_uid(String bestdo_uid) {
		this.bestdo_uid = bestdo_uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getWidthList() {
		return widthList;
	}

	public void setWidthList(ArrayList<String> widthList) {
		this.widthList = widthList;
	}

	public ArrayList<String> getHeightList() {
		return heightList;
	}

	public void setHeightList(ArrayList<String> heightList) {
		this.heightList = heightList;
	}

	public String getImg_video_type() {
		return img_video_type;
	}

	public void setImg_video_type(String img_video_type) {
		this.img_video_type = img_video_type;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
