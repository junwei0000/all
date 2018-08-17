/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-12-29 下午3:24:12
 * @Description 类描述：
 */
public class CashMyClubTypeInfo {
	int position;
	int page;
	String id;
	String name;
	String img;
	String select_img;
	int type;
	int addtime;
	int update_time;

	public CashMyClubTypeInfo(String id, String name, String img, String select_img, int type, int addtime,
			int update_time) {
		super();
		this.id = id;
		this.name = name;
		this.img = img;
		this.select_img = select_img;
		this.type = type;
		this.addtime = addtime;
		this.update_time = update_time;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSelect_img() {
		return select_img;
	}

	public void setSelect_img(String select_img) {
		this.select_img = select_img;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getAddtime() {
		return addtime;
	}

	public void setAddtime(int addtime) {
		this.addtime = addtime;
	}

	public int getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(int update_time) {
		this.update_time = update_time;
	}

}
