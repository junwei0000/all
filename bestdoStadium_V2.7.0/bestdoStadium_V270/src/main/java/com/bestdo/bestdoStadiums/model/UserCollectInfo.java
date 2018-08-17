/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-1-19 下午5:56:38
 * @Description 类描述：用户收藏列表item
 */
public class UserCollectInfo {

	/**
	 * 商品明细ID
	 */
	private String relationNo;

	private String stadiumName;

	private String venueName;

	private String minPrice;
	private String maxPrice;

	private String thumb;

	private String region;

	private String vip_price_id;

	public UserCollectInfo() {
		super();
	}

	public UserCollectInfo(String relationNo, String stadiumName, String venueName, String minPrice, String maxPrice,
			String thumb, String region, String vip_price_id) {
		super();
		this.relationNo = relationNo;
		this.stadiumName = stadiumName;
		this.venueName = venueName;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.thumb = thumb;
		this.region = region;
		this.vip_price_id = vip_price_id;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getVip_price_id() {
		return vip_price_id;
	}

	public void setVip_price_id(String vip_price_id) {
		this.vip_price_id = vip_price_id;
	}

	public String getRelationNo() {
		return relationNo;
	}

	public void setRelationNo(String relationNo) {
		this.relationNo = relationNo;
	}

	public String getStadiumName() {
		return stadiumName;
	}

	public void setStadiumName(String stadiumName) {
		this.stadiumName = stadiumName;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
