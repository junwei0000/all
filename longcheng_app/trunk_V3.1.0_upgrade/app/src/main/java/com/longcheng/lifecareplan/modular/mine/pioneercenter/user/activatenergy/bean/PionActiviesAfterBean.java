package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class PionActiviesAfterBean extends ResponseBean {
    protected int is_receive;
    protected int is_activity_end;
    int is_enroll;//1 已报名
    protected int room_id;
    int is_activity_finish;//是否有报名资格 1 有报名资格
    int user_role;//是否有身份 1有（创业导师或坐堂医）
    int is_get_phone;//是否已领取手机  1已领取
    protected String activity_date;
    String invite_user_name;
    PionActiviesItemBean task_one;
    PionActiviesItemBean task_two;

    public String getInvite_user_name() {
        return invite_user_name;
    }

    public void setInvite_user_name(String invite_user_name) {
        this.invite_user_name = invite_user_name;
    }

    public int getIs_activity_finish() {
        return is_activity_finish;
    }

    public void setIs_activity_finish(int is_activity_finish) {
        this.is_activity_finish = is_activity_finish;
    }

    public int getUser_role() {
        return user_role;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }

    public int getIs_get_phone() {
        return is_get_phone;
    }

    public void setIs_get_phone(int is_get_phone) {
        this.is_get_phone = is_get_phone;
    }

    public int getIs_enroll() {
        return is_enroll;
    }

    public void setIs_enroll(int is_enroll) {
        this.is_enroll = is_enroll;
    }

    public int getIs_receive() {
        return is_receive;
    }

    public void setIs_receive(int is_receive) {
        this.is_receive = is_receive;
    }

    public int getIs_activity_end() {
        return is_activity_end;
    }

    public void setIs_activity_end(int is_activity_end) {
        this.is_activity_end = is_activity_end;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getActivity_date() {
        return activity_date;
    }

    public void setActivity_date(String activity_date) {
        this.activity_date = activity_date;
    }

    public PionActiviesItemBean getTask_one() {
        return task_one;
    }

    public void setTask_one(PionActiviesItemBean task_one) {
        this.task_one = task_one;
    }

    public PionActiviesItemBean getTask_two() {
        return task_two;
    }

    public void setTask_two(PionActiviesItemBean task_two) {
        this.task_two = task_two;
    }

    public class PionActiviesItemBean {
        protected String title;
        protected String small_title;
        protected String icon;
        protected int status;

        protected ArrayList<String> items;
        protected String shop_goods_id;

        protected ArrayList<PionActiviesItemBean> items_inner;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSmall_title() {
            return small_title;
        }

        public void setSmall_title(String small_title) {
            this.small_title = small_title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public ArrayList<String> getItems() {
            return items;
        }

        public void setItems(ArrayList<String> items) {
            this.items = items;
        }

        public ArrayList<PionActiviesItemBean> getItems_inner() {
            return items_inner;
        }

        public void setItems_inner(ArrayList<PionActiviesItemBean> items_inner) {
            this.items_inner = items_inner;
        }

        public String getShop_goods_id() {
            return shop_goods_id;
        }

        public void setShop_goods_id(String shop_goods_id) {
            this.shop_goods_id = shop_goods_id;
        }

    }
}
