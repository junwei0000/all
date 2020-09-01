package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class RiceActiviesDataBean extends ResponseBean {
    @SerializedName("data")
    protected RiceActiviesAfterBean data;

    public RiceActiviesAfterBean getData() {
        return data;
    }

    public void setData(RiceActiviesAfterBean data) {
        this.data = data;
    }

    public static class RiceActiviesAfterBean {

        int isJoin;//0:未领取。1:已领取
        int isOpen;
        int startHour;
        int endHour;
        RiceActiviesItemBean activity_info;
        ArrayList<RiceActiviesItemBean> tasks;

        public int getStartHour() {
            return startHour;
        }

        public void setStartHour(int startHour) {
            this.startHour = startHour;
        }

        public int getEndHour() {
            return endHour;
        }

        public void setEndHour(int endHour) {
            this.endHour = endHour;
        }

        public int getIsJoin() {
            return isJoin;
        }

        public void setIsJoin(int isJoin) {
            this.isJoin = isJoin;
        }

        public int getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(int isOpen) {
            this.isOpen = isOpen;
        }

        public RiceActiviesItemBean getActivity_info() {
            return activity_info;
        }

        public void setActivity_info(RiceActiviesItemBean activity_info) {
            this.activity_info = activity_info;
        }

        public ArrayList<RiceActiviesItemBean> getTasks() {
            return tasks;
        }

        public void setTasks(ArrayList<RiceActiviesItemBean> tasks) {
            this.tasks = tasks;
        }

        public static class RiceActiviesItemBean {

            String title;
            int status;

            String shop_goods_id;
            int processStatus;//processStatus:1未开始 2进行中 3已结束
            String goods_img;
            String skb_price;

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public String getSkb_price() {
                return skb_price;
            }

            public void setSkb_price(String skb_price) {
                this.skb_price = skb_price;
            }

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

            public String getShop_goods_id() {
                return shop_goods_id;
            }

            public void setShop_goods_id(String shop_goods_id) {
                this.shop_goods_id = shop_goods_id;
            }

            public int getProcessStatus() {
                return processStatus;
            }

            public void setProcessStatus(int processStatus) {
                this.processStatus = processStatus;
            }
        }
    }

}
