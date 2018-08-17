/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-3-7 下午5:33:40
 * @Description 类描述：
 */
public class BusinessBannerInfo {

	String name;
	String imgPath;
	String url;
	String sub_name;

	public BusinessBannerInfo() {
		super();
	}

	public BusinessBannerInfo(String name, String imgPath, String url, String sub_name) {
		super();
		this.name = name;
		this.imgPath = imgPath;
		this.url = url;
		this.sub_name = sub_name;
	}

	public BusinessBannerInfo(String name, String imgPath, String url) {
		super();
		this.name = name;
		this.imgPath = imgPath;
		this.url = url;
	}

	public String getSub_name() {
		return sub_name;
	}

	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
