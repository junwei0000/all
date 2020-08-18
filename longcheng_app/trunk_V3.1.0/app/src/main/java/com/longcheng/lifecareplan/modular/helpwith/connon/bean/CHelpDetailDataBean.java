package com.longcheng.lifecareplan.modular.helpwith.connon.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class CHelpDetailDataBean extends ResponseBean {
    @SerializedName("data")
    private CHelpDetailAfterBean data;

    public CHelpDetailAfterBean getData() {
        return data;
    }

    public void setData(CHelpDetailAfterBean data) {
        this.data = data;
    }

    public static class CHelpDetailAfterBean {
        String jieqi_name;
        String last_jieqi_day;
        int type;
        DetailItemBean knpGroupItem;

        ArrayList<DetailItemBean> knpGroupItemList;
        ArrayList<DetailItemBean> notJoinTables;
        ArrayList<DetailItemBean> joinTables;
        ArrayList<String> lastSixSolarTerms;

        public String getJieqi_name() {
            return jieqi_name;
        }

        public void setJieqi_name(String jieqi_name) {
            this.jieqi_name = jieqi_name;
        }

        public String getLast_jieqi_day() {
            return last_jieqi_day;
        }

        public void setLast_jieqi_day(String last_jieqi_day) {
            this.last_jieqi_day = last_jieqi_day;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public DetailItemBean getKnpGroupItem() {
            return knpGroupItem;
        }

        public void setKnpGroupItem(DetailItemBean knpGroupItem) {
            this.knpGroupItem = knpGroupItem;
        }

        public ArrayList<DetailItemBean> getKnpGroupItemList() {
            return knpGroupItemList;
        }

        public void setKnpGroupItemList(ArrayList<DetailItemBean> knpGroupItemList) {
            this.knpGroupItemList = knpGroupItemList;
        }

        public ArrayList<DetailItemBean> getNotJoinTables() {
            return notJoinTables;
        }

        public void setNotJoinTables(ArrayList<DetailItemBean> notJoinTables) {
            this.notJoinTables = notJoinTables;
        }

        public ArrayList<DetailItemBean> getJoinTables() {
            return joinTables;
        }

        public void setJoinTables(ArrayList<DetailItemBean> joinTables) {
            this.joinTables = joinTables;
        }

        public ArrayList<String> getLastSixSolarTerms() {
            return lastSixSolarTerms;
        }

        public void setLastSixSolarTerms(ArrayList<String> lastSixSolarTerms) {
            this.lastSixSolarTerms = lastSixSolarTerms;
        }

        public static class DetailItemBean {

            int status;//状态：0 未处理,1 匹配互祝进行中,2 匹配完成, 3 互祝完成
            boolean joinStatus = false;

            boolean check=false;
            String user_id;
            String user_name;
            String knp_group_item_id;
            String knp_group_room_id;
            String table_number;
            int is_super_ability;
            String ability;

            String patient_name;
            String patient_avatar;
            String patient_jieqi_name;


            //joinTables

            String knp_group_table_id;
            int person_number;
            ArrayList<DetailItemBean> knp_group_table_item;

            String user_avatar;


            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public boolean isJoinStatus() {
                return joinStatus;
            }

            public void setJoinStatus(boolean joinStatus) {
                this.joinStatus = joinStatus;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getKnp_group_item_id() {
                return knp_group_item_id;
            }

            public void setKnp_group_item_id(String knp_group_item_id) {
                this.knp_group_item_id = knp_group_item_id;
            }

            public String getKnp_group_room_id() {
                return knp_group_room_id;
            }

            public void setKnp_group_room_id(String knp_group_room_id) {
                this.knp_group_room_id = knp_group_room_id;
            }

            public String getTable_number() {
                return table_number;
            }

            public void setTable_number(String table_number) {
                this.table_number = table_number;
            }

            public int getIs_super_ability() {
                return is_super_ability;
            }

            public void setIs_super_ability(int is_super_ability) {
                this.is_super_ability = is_super_ability;
            }

            public String getAbility() {
                return ability;
            }

            public void setAbility(String ability) {
                this.ability = ability;
            }

            public String getPatient_name() {
                return patient_name;
            }

            public void setPatient_name(String patient_name) {
                this.patient_name = patient_name;
            }

            public String getPatient_avatar() {
                return patient_avatar;
            }

            public void setPatient_avatar(String patient_avatar) {
                this.patient_avatar = patient_avatar;
            }

            public String getPatient_jieqi_name() {
                return patient_jieqi_name;
            }

            public void setPatient_jieqi_name(String patient_jieqi_name) {
                this.patient_jieqi_name = patient_jieqi_name;
            }

            public String getKnp_group_table_id() {
                return knp_group_table_id;
            }

            public void setKnp_group_table_id(String knp_group_table_id) {
                this.knp_group_table_id = knp_group_table_id;
            }

            public int getPerson_number() {
                return person_number;
            }

            public void setPerson_number(int person_number) {
                this.person_number = person_number;
            }

            public ArrayList<DetailItemBean> getKnp_group_table_item() {
                return knp_group_table_item;
            }

            public void setKnp_group_table_item(ArrayList<DetailItemBean> knp_group_table_item) {
                this.knp_group_table_item = knp_group_table_item;
            }

            public String getUser_avatar() {
                return user_avatar;
            }

            public void setUser_avatar(String user_avatar) {
                this.user_avatar = user_avatar;
            }
        }


    }
}
