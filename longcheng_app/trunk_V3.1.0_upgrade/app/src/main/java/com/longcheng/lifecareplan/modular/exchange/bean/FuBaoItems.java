package com.longcheng.lifecareplan.modular.exchange.bean;

import java.util.List;

public class FuBaoItems {


    /**
     * bless_bag_total_count : 5
     * bless_bag_count : 0
     * is_activity_end : 1
     * is_new_user : 0
     * tasks : [{"title":"祝佑宝：<span style=\"color:#E95D1B;\">24<\/span>","status":0,"items":[]},{"title":"福祺宝：<span style=\"color:#E95D1B;\">24<\/span>","status":0,"items":[]},{"title":"节气宝：<span style=\"color:#E95D1B;\">24<\/span>","status":0,"items":[]},{"title":"寿康宝：<span style=\"color:#E95D1B;\">24<\/span>","status":0,"items":[]}]
     * rule : ["1、 活动时间:08月24日早22:23分开始, 08月26日22:23分结束.","2、向亲友送出5个福包,5个亲友收到福包接福成功后,参与排名.","3、如果亲友24小时未收到福包,系统将福包自动退回,可以重新发放.","4、福包有效期以排名结束时间为准.","5、活动结束3天后统一发放活动物品."]
     */

    private int bless_bag_total_count;
    private int bless_bag_count;
    private int is_activity_end;
    private int is_new_user;
    private List<TasksBean> tasks;
    private List<String> rule;
    int is_open;
    private AddressBean address;
    int is_stock_over;

    public int getIs_stock_over() {
        return is_stock_over;
    }

    public void setIs_stock_over(int is_stock_over) {
        this.is_stock_over = is_stock_over;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public int getIsOpen() {
        return is_open;
    }

    public void setIsOpen(int is_open) {
        this.is_open = is_open;
    }

    public int getBless_bag_total_count() {
        return bless_bag_total_count;
    }

    public void setBless_bag_total_count(int bless_bag_total_count) {
        this.bless_bag_total_count = bless_bag_total_count;
    }

    public int getBless_bag_count() {
        return bless_bag_count;
    }

    public void setBless_bag_count(int bless_bag_count) {
        this.bless_bag_count = bless_bag_count;
    }

    public int getIs_activity_end() {
        return is_activity_end;
    }

    public void setIs_activity_end(int is_activity_end) {
        this.is_activity_end = is_activity_end;
    }

    public int getIs_new_user() {
        return is_new_user;
    }

    public void setIs_new_user(int is_new_user) {
        this.is_new_user = is_new_user;
    }

    public List<TasksBean> getTasks() {
        return tasks;
    }

    public void setTasks(List<TasksBean> tasks) {
        this.tasks = tasks;
    }

    public List<String> getRule() {
        return rule;
    }

    public void setRule(List<String> rule) {
        this.rule = rule;
    }

    public static class TasksBean {
        /**
         * title : 祝佑宝：<span style="color:#E95D1B;">24</span>
         * status : 0
         * items : []
         */

        private String title;
        private int status;
        private List<?> items;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<?> getItems() {
            return items;
        }

        public void setItems(List<?> items) {
            this.items = items;
        }
    }

    public static class AddressBean {
        String address_id;
        String user_id;
        String consignee;
        String province;
        String city;
        String district;
        String twon;
        String address;
        String mobile;
        String is_default;
        String is_gift;


        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getTwon() {
            return twon;
        }

        public void setTwon(String twon) {
            this.twon = twon;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public String getIs_gift() {
            return is_gift;
        }

        public void setIs_gift(String is_gift) {
            this.is_gift = is_gift;
        }
    }
}
