package com.bestdo.bestdoStadiums.business.net;

import java.util.List;

public class RankDepartmentResponse extends BaseResponse {
	/**
	 * head_info :
	 * {"company_name":"新赛点","enroll_num":"5","all_step_num":"20462",
	 * "avg_step_num":"4092"} my_info :
	 * {"num":"1","dep_name":"北京分公司","dep_avg_step":"4144"} list_data :
	 * [{"depid":"70","step_num":"16577","enroll_num":"4","dep_avg_step":"4144",
	 * "num":"1","name":"北京分公司"},{"depid":"71","step_num":"3885","enroll_num":"1",
	 * "dep_avg_step":"3885","num":"2","name":"上海分公司"}]
	 */

	private HeadInfoEntity head_info;
	private MyDepInfoEntity my_info;
	private List<ListDataEntity> list_data;

	public HeadInfoEntity getHead_info() {
		return head_info;
	}

	public void setHead_info(HeadInfoEntity head_info) {
		this.head_info = head_info;
	}

	public MyDepInfoEntity getMy_info() {
		return my_info;
	}

	public void setMy_info(MyDepInfoEntity my_info) {
		this.my_info = my_info;
	}

	public List<ListDataEntity> getList_data() {
		return list_data;
	}

	public void setList_data(List<ListDataEntity> list_data) {
		this.list_data = list_data;
	}

	public static class HeadInfoEntity {
		/**
		 * company_name : 新赛点 enroll_num : 5 all_step_num : 20462 avg_step_num :
		 * 4092
		 */

		private String company_name;
		private String enroll_num;
		private String all_step_num;
		private String avg_step_num;

		public String getCompany_name() {
			return company_name;
		}

		public void setCompany_name(String company_name) {
			this.company_name = company_name;
		}

		public String getEnroll_num() {
			return enroll_num;
		}

		public void setEnroll_num(String enroll_num) {
			this.enroll_num = enroll_num;
		}

		public String getAll_step_num() {
			return all_step_num;
		}

		public void setAll_step_num(String all_step_num) {
			this.all_step_num = all_step_num;
		}

		public String getAvg_step_num() {
			return avg_step_num;
		}

		public void setAvg_step_num(String avg_step_num) {
			this.avg_step_num = avg_step_num;
		}
	}

	public static class MyDepInfoEntity {
		/**
		 * num : 1 dep_name : 北京分公司 dep_avg_step : 4144
		 */

		private String num;
		private String dep_name;
		private String dep_avg_step;

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getDep_name() {
			return dep_name;
		}

		public void setDep_name(String dep_name) {
			this.dep_name = dep_name;
		}

		public String getDep_avg_step() {
			return dep_avg_step;
		}

		public void setDep_avg_step(String dep_avg_step) {
			this.dep_avg_step = dep_avg_step;
		}
	}

	public static class ListDataEntity {
		/**
		 * depid : 70 step_num : 16577 enroll_num : 4 dep_avg_step : 4144 num :
		 * 1 name : 北京分公司
		 */

		private String depid;
		private String step_num;
		private String enroll_num;
		private String dep_avg_step;
		private String num;
		private String name;

		public String getDepid() {
			return depid;
		}

		public void setDepid(String depid) {
			this.depid = depid;
		}

		public String getStep_num() {
			return step_num;
		}

		public void setStep_num(String step_num) {
			this.step_num = step_num;
		}

		public String getEnroll_num() {
			return enroll_num;
		}

		public void setEnroll_num(String enroll_num) {
			this.enroll_num = enroll_num;
		}

		public String getDep_avg_step() {
			return dep_avg_step;
		}

		public void setDep_avg_step(String dep_avg_step) {
			this.dep_avg_step = dep_avg_step;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}
