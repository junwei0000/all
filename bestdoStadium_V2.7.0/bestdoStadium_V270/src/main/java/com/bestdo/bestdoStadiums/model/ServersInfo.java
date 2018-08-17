/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-3-7 下午5:33:40
 * @Description 类描述：
 */
public class ServersInfo {

	String name;
	String url;
	String img_url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public ServersInfo(String name, String url, String img_url) {
		super();
		this.name = name;
		this.url = url;
		this.img_url = img_url;
	}

}
