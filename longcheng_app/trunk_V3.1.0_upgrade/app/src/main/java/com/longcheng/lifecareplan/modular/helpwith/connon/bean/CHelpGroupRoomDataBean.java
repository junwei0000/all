package com.longcheng.lifecareplan.modular.helpwith.connon.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class CHelpGroupRoomDataBean extends ResponseBean {
    @SerializedName("data")
    private GroupRoomAfterBean data;

    public GroupRoomAfterBean getData() {
        return data;
    }

    public void setData(GroupRoomAfterBean data) {
        this.data = data;
    }

    public static class GroupRoomAfterBean {

        String jieqi_name;
        int last_jieqi_day;
        int type;
        GroupRoomItemBean group_room;
        ArrayList<GroupRoomItemBean> user_list;

        public String getJieqi_name() {
            return jieqi_name;
        }

        public void setJieqi_name(String jieqi_name) {
            this.jieqi_name = jieqi_name;
        }

        public int getLast_jieqi_day() {
            return last_jieqi_day;
        }

        public void setLast_jieqi_day(int last_jieqi_day) {
            this.last_jieqi_day = last_jieqi_day;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public GroupRoomItemBean getGroup_room() {
            return group_room;
        }

        public void setGroup_room(GroupRoomItemBean group_room) {
            this.group_room = group_room;
        }

        public ArrayList<GroupRoomItemBean> getUser_list() {
            return user_list;
        }

        public void setUser_list(ArrayList<GroupRoomItemBean> user_list) {
            this.user_list = user_list;
        }

        public static class GroupRoomItemBean {
            String user_id;
            String table_number;
            String card_number;
            String ability;
            int type;//1.结缘 2.结伴
            int is_super_ability;//1.超能
            int person_number;


            String user_name;
            String user_avatar;
            int is_team;//是否队长： 1是， 0不是， 只有结伴存在此字段


            int process_status;
            String knp_group_item_id;
            String knp_msg_id;
            String patient_name;
            String patient_avatar;


            public int getProcess_status() {
                return process_status;
            }

            public void setProcess_status(int process_status) {
                this.process_status = process_status;
            }

            public String getKnp_group_item_id() {
                return knp_group_item_id;
            }

            public void setKnp_group_item_id(String knp_group_item_id) {
                this.knp_group_item_id = knp_group_item_id;
            }

            public String getKnp_msg_id() {
                return knp_msg_id;
            }

            public void setKnp_msg_id(String knp_msg_id) {
                this.knp_msg_id = knp_msg_id;
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

            public int getIs_super_ability() {
                return is_super_ability;
            }

            public void setIs_super_ability(int is_super_ability) {
                this.is_super_ability = is_super_ability;
            }

            public int getPerson_number() {
                return person_number;
            }

            public void setPerson_number(int person_number) {
                this.person_number = person_number;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getTable_number() {
                return table_number;
            }

            public void setTable_number(String table_number) {
                this.table_number = table_number;
            }

            public String getCard_number() {
                return card_number;
            }

            public void setCard_number(String card_number) {
                this.card_number = card_number;
            }

            public String getAbility() {
                return ability;
            }

            public void setAbility(String ability) {
                this.ability = ability;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_avatar() {
                return user_avatar;
            }

            public void setUser_avatar(String user_avatar) {
                this.user_avatar = user_avatar;
            }

            public int getIs_team() {
                return is_team;
            }

            public void setIs_team(int is_team) {
                this.is_team = is_team;
            }
        }
    }
}
