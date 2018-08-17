/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-12-2 上午11:04:01
 * @Description 类描述：
 */
public class MessageInfo {

	String title;
	String thumb;
	/**
	 * （1活动消息2系统消息）
	 */
	String type;
	/**
	 * 是否存在未读消息（yes存在no不存在）
	 */
	String is_read;
	String message_title;

	public MessageInfo(String title, String thumb, String type, String is_read, String message_title) {
		super();
		this.title = title;
		this.thumb = thumb;
		this.type = type;
		this.is_read = is_read;
		this.message_title = message_title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIs_read() {
		return is_read;
	}

	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}

	public String getMessage_title() {
		return message_title;
	}

	public void setMessage_title(String message_title) {
		this.message_title = message_title;
	}

}
