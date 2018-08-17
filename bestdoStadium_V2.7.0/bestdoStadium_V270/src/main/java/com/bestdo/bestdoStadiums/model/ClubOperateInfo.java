/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

/**
 * @author 作者：
 * @date 创建时间：
 * @Description 类描述：运动圈运营数据
 */
public class ClubOperateInfo implements Serializable {
	String description;
	String num;
	String unit;

	public ClubOperateInfo(String description, String num, String unit) {
		super();
		this.description = description;
		this.num = num;
		this.unit = unit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
