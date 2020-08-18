package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class ZYBSelectMonRecrodListDataBean extends ResponseBean {
    @SerializedName("data")
    protected ArrayList<RiceActiviesItemBean> data;

    public ArrayList<RiceActiviesItemBean> getData() {
        return data;
    }

    public void setData(ArrayList<RiceActiviesItemBean> data) {
        this.data = data;
    }

    public static class RiceActiviesItemBean {
        String zhuyoubao_team_item_id;
        String zhuyoubao_team_room_id;
        String money;
        int role;
        int is_create_order;
        int create_time;
        String user_name;
        int process_status;

        public String getZhuyoubao_team_item_id() {
            return zhuyoubao_team_item_id;
        }

        public void setZhuyoubao_team_item_id(String zhuyoubao_team_item_id) {
            this.zhuyoubao_team_item_id = zhuyoubao_team_item_id;
        }

        public String getZhuyoubao_team_room_id() {
            return zhuyoubao_team_room_id;
        }

        public void setZhuyoubao_team_room_id(String zhuyoubao_team_room_id) {
            this.zhuyoubao_team_room_id = zhuyoubao_team_room_id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public int getIs_create_order() {
            return is_create_order;
        }

        public void setIs_create_order(int is_create_order) {
            this.is_create_order = is_create_order;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public int getProcess_status() {
            return process_status;
        }

        public void setProcess_status(int process_status) {
            this.process_status = process_status;
        }
    }

}
