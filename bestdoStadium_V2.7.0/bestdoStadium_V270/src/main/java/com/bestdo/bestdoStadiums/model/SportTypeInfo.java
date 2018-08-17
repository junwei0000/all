package com.bestdo.bestdoStadiums.model;

/**
 * 首页运动类型
 * 
 * @author qyy
 * 
 */
public class SportTypeInfo {

	String cid;// id
	String name;
	boolean check;// 选中状态
	String merid;
	String alias;
	String sport;// 运动类型名称
	String imgurl;
	String defult_price_id;
	String banlance_price_id;
	String isNew;
	String thumbnails;

	int page;

	public String getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(String thumbnails) {
		this.thumbnails = thumbnails;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMerid() {
		return merid;
	}

	public void setMerid(String merid) {
		this.merid = merid;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getDefult_price_id() {
		return defult_price_id;
	}

	public void setDefult_price_id(String defult_price_id) {
		this.defult_price_id = defult_price_id;
	}

	public String getBanlance_price_id() {
		return banlance_price_id;
	}

	public void setBanlance_price_id(String banlance_price_id) {
		this.banlance_price_id = banlance_price_id;
	}

	public SportTypeInfo() {
		super();
	}

	public SportTypeInfo(String cid, String name, String merid, boolean check) {
		super();
		this.cid = cid;
		this.name = name;
		this.merid = merid;
		this.check = check;
	}

	public SportTypeInfo(String cid, String name, String merid, String thumbnails, boolean check) {
		super();
		this.cid = cid;
		this.name = name;
		this.merid = merid;
		this.check = check;
		this.thumbnails = thumbnails;
	}

	public SportTypeInfo(String cid, String name, String merid, String alias, String sport, String imgurl,
			String defult_price_id, String banlance_price_id, String isNew) {
		super();
		this.cid = cid;
		this.name = name;
		this.merid = merid;
		this.alias = alias;
		this.sport = sport;
		this.imgurl = imgurl;
		this.defult_price_id = defult_price_id;
		this.banlance_price_id = banlance_price_id;
		this.isNew = isNew;
	}

}
