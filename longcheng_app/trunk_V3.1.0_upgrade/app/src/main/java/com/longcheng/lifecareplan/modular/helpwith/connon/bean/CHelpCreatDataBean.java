package com.longcheng.lifecareplan.modular.helpwith.connon.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class CHelpCreatDataBean extends ResponseBean {
    @SerializedName("data")
    private CHelpCreatAfterBean data;

    public CHelpCreatAfterBean getData() {
        return data;
    }

    public void setData(CHelpCreatAfterBean data) {
        this.data = data;
    }

    public static class CHelpCreatAfterBean {
        int max_table_number;
        int base_ability;
        ArrayList<CreatItemBean> knp_team_numbers;
        CreatItemBean knp_group_item;
        CreatItemBean chatuser;

        public int getMax_table_number() {
            return max_table_number;
        }

        public void setMax_table_number(int max_table_number) {
            this.max_table_number = max_table_number;
        }

        public int getBase_ability() {
            return base_ability;
        }

        public void setBase_ability(int base_ability) {
            this.base_ability = base_ability;
        }

        public ArrayList<CreatItemBean> getKnp_team_numbers() {
            return knp_team_numbers;
        }

        public void setKnp_team_numbers(ArrayList<CreatItemBean> knp_team_numbers) {
            this.knp_team_numbers = knp_team_numbers;
        }

        public CreatItemBean getKnp_group_item() {
            return knp_group_item;
        }

        public void setKnp_group_item(CreatItemBean knp_group_item) {
            this.knp_group_item = knp_group_item;
        }

        public CreatItemBean getChatuser() {
            return chatuser;
        }

        public void setChatuser(CreatItemBean chatuser) {
            this.chatuser = chatuser;
        }

        public static class CreatItemBean {

            int super_ability_limit_number;
            String super_ability;
            String ability;

            int process_status;
            String knp_group_item_id;
            String knp_group_room_id;


            String knp_team_number_id;

            public int getProcess_status() {
                return process_status;
            }

            public void setProcess_status(int process_status) {
                this.process_status = process_status;
            }

            public int getSuper_ability_limit_number() {
                return super_ability_limit_number;
            }

            public void setSuper_ability_limit_number(int super_ability_limit_number) {
                this.super_ability_limit_number = super_ability_limit_number;
            }

            public String getSuper_ability() {
                return super_ability;
            }

            public void setSuper_ability(String super_ability) {
                this.super_ability = super_ability;
            }

            public String getAbility() {
                return ability;
            }

            public void setAbility(String ability) {
                this.ability = ability;
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

            public String getKnp_team_number_id() {
                return knp_team_number_id;
            }

            public void setKnp_team_number_id(String knp_team_number_id) {
                this.knp_team_number_id = knp_team_number_id;
            }
        }


    }
}
