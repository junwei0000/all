package com.longcheng.lifecareplan.bean.fupackage;

import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;


public class FuBaoRankBean extends ResponseBean {
    FuBaoRankAfterBean data;

    public FuBaoRankAfterBean getData() {
        return data;
    }

    public void setData(FuBaoRankAfterBean data) {
        this.data = data;
    }

    public static class FuBaoRankAfterBean {

        String total_num;
        String num;
        ArrayList<FuBaoRankItemBean> list;

        public String getTotal_num() {
            return total_num;
        }

        public void setTotal_num(String total_num) {
            this.total_num = total_num;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public ArrayList<FuBaoRankItemBean> getList() {
            return list;
        }

        public void setList(ArrayList<FuBaoRankItemBean> list) {
            this.list = list;
        }

        public static class FuBaoRankItemBean {

            String user_name;
            int time;
            String count;

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }
        }
    }
}
