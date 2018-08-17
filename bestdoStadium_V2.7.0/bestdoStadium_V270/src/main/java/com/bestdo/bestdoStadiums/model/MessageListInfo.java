/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-12-2 上午11:04:01
 * @Description 类描述：
 */
public class MessageListInfo {

	String id;
	String bestdo_uid;
	String type;
	String title;
	String content;
	String sport_id;
	String source;
	String is_read;
	int add_time;
	String icon;
	String activity_id;
	String activity_name;
	String activity_status;

	public MessageListInfo(String id, String bestdo_uid, String type, String title, String content, String sport_id,
			String source, String is_read, int add_time, String icon, String activity_id, String activity_name,
			String activity_status) {
		super();
		this.id = id;
		this.bestdo_uid = bestdo_uid;
		this.type = type;
		this.title = title;
		this.content = content;
		this.sport_id = sport_id;
		this.source = source;
		this.is_read = is_read;
		this.add_time = add_time;
		this.icon = icon;
		this.activity_id = activity_id;
		this.activity_name = activity_name;
		this.activity_status = activity_status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBestdo_uid() {
		return bestdo_uid;
	}

	public void setBestdo_uid(String bestdo_uid) {
		this.bestdo_uid = bestdo_uid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSport_id() {
		return sport_id;
	}

	public void setSport_id(String sport_id) {
		this.sport_id = sport_id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getIs_read() {
		return is_read;
	}

	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}

	public int getAdd_time() {
		return add_time;
	}

	public void setAdd_time(int add_time) {
		this.add_time = add_time;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

	public String getActivity_name() {
		return activity_name;
	}

	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}

	public String getActivity_status() {
		return activity_status;
	}

	public void setActivity_status(String activity_status) {
		this.activity_status = activity_status;
	}

}
