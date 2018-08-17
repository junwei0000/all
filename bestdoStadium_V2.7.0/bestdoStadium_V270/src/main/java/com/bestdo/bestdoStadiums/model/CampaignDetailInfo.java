/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-11-28 下午6:33:59
 * @Description 类描述：
 */
public class CampaignDetailInfo {
	String activity_id;
	String name;
	String logo;
	String time_str;
	String share_url;
	String situs;
	String rule;
	String contenttime_str;
	String max_peo;
	String is_join;
	double longitude;
	double latitude;
	String is_edit;
	String join_rate;
	String join_num;

	String footavatar;
	String footname;
	String all_nums;
	String rulestatus;
	String rulestatusname;

	String user_name;
	String user_mobile;
	String user_role;
	String club_icon;
	String club_name;

	public CampaignDetailInfo(String activity_id, String name, String logo, String time_str, String situs, String rule,
			String contenttime_str, String max_peo, String is_join, double latitude, double longitude) {
		super();
		this.activity_id = activity_id;
		this.name = name;
		this.logo = logo;
		this.time_str = time_str;
		this.situs = situs;
		this.rule = rule;
		this.contenttime_str = contenttime_str;
		this.max_peo = max_peo;
		this.is_join = is_join;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_mobile() {
		return user_mobile;
	}

	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public String getClub_icon() {
		return club_icon;
	}

	public void setClub_icon(String club_icon) {
		this.club_icon = club_icon;
	}

	public String getClub_name() {
		return club_name;
	}

	public void setClub_name(String club_name) {
		this.club_name = club_name;
	}

	public String getAll_nums() {
		return all_nums;
	}

	public void setAll_nums(String all_nums) {
		this.all_nums = all_nums;
	}

	public String getRulestatusname() {
		return rulestatusname;
	}

	public void setRulestatusname(String rulestatusname) {
		this.rulestatusname = rulestatusname;
	}

	public String getRulestatus() {
		return rulestatus;
	}

	public void setRulestatus(String rulestatus) {
		this.rulestatus = rulestatus;
	}

	public String getShare_url() {
		return share_url;
	}

	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}

	public String getJoin_rate() {
		return join_rate;
	}

	public void setJoin_rate(String join_rate) {
		this.join_rate = join_rate;
	}

	public String getJoin_num() {
		return join_num;
	}

	public void setJoin_num(String join_num) {
		this.join_num = join_num;
	}

	public String getIs_edit() {
		return is_edit;
	}

	public void setIs_edit(String is_edit) {
		this.is_edit = is_edit;
	}

	/**
	 * 
	 */
	public CampaignDetailInfo() {
		super();
	}

	public String getFootavatar() {
		return footavatar;
	}

	public void setFootavatar(String footavatar) {
		this.footavatar = footavatar;
	}

	public String getFootname() {
		return footname;
	}

	public void setFootname(String footname) {
		this.footname = footname;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getIs_join() {
		return is_join;
	}

	public void setIs_join(String is_join) {
		this.is_join = is_join;
	}

	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTime_str() {
		return time_str;
	}

	public void setTime_str(String time_str) {
		this.time_str = time_str;
	}

	public String getSitus() {
		return situs;
	}

	public void setSitus(String situs) {
		this.situs = situs;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getContenttime_str() {
		return contenttime_str;
	}

	public void setContenttime_str(String contenttime_str) {
		this.contenttime_str = contenttime_str;
	}

	public String getMax_peo() {
		return max_peo;
	}

	public void setMax_peo(String max_peo) {
		this.max_peo = max_peo;
	}

}
