package com.bestdo.bestdoStadiums.business.net;

import java.util.List;

import com.bestdo.bestdoStadiums.model.UserWalkingRankInfo;

public class RankPersonResponse extends BaseResponse {
	private String total;
	private UserInfoEntity user_info;
	private String company_name;
	private String dept_name;
	private List<UserWalkingRankInfo> list;

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public UserInfoEntity getUser_info() {
		return user_info;
	}

	public void setUser_info(UserInfoEntity user_info) {
		this.user_info = user_info;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public List<UserWalkingRankInfo> getList() {
		return list;
	}

	public void setList(List<UserWalkingRankInfo> list) {
		this.list = list;
	}

	public static class UserInfoEntity {
		/**
		 * step_num : 3703 uid : 530160712115925xKE num : 3 nick_name : 雷雨霆
		 * ablum_url : http://wx.qlogo
		 * .cn/mmopen/PiajxSqBRaELnZiaoKf1dBs2ooichBibkGxvyLnqPn4o61UpLVVotaZ0icmtYhn5tWvAEtjZFj8DzUiavIx4975aptRQ/0
		 * dept_name :
		 */

		private String step_num;
		private String uid;
		private String num;
		private String nick_name;
		private String ablum_url;
		private String dept_name;

		public String getStep_num() {
			return step_num;
		}

		public void setStep_num(String step_num) {
			this.step_num = step_num;
		}

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getNick_name() {
			return nick_name;
		}

		public void setNick_name(String nick_name) {
			this.nick_name = nick_name;
		}

		public String getAblum_url() {
			return ablum_url;
		}

		public void setAblum_url(String ablum_url) {
			this.ablum_url = ablum_url;
		}

		public String getDept_name() {
			return dept_name;
		}

		public void setDept_name(String dept_name) {
			this.dept_name = dept_name;
		}
	}

}
