package com.longcheng.lifecareplan.bean.fupackage;

import com.longcheng.lifecareplan.bean.ResponseBean;


public class ReceiveFuBaoBean extends ResponseBean {
    ReceiveFuBaoAfterBean data;

    public ReceiveFuBaoAfterBean getData() {
        return data;
    }

    public void setData(ReceiveFuBaoAfterBean data) {
        this.data = data;
    }

    public static class ReceiveFuBaoAfterBean {

        int isCer;
        ReceiveFuBaoItemBean blessBag;

        public int getIsCer() {
            return isCer;
        }

        public void setIsCer(int isCer) {
            this.isCer = isCer;
        }

        public ReceiveFuBaoItemBean getBlessBag() {
            return blessBag;
        }

        public void setBlessBag(ReceiveFuBaoItemBean blessBag) {
            this.blessBag = blessBag;
        }

        public static class ReceiveFuBaoItemBean {

            String bless_bag_id;
            String sponsor_user_name;
            String receive_super_ability;

            public String getBless_bag_id() {
                return bless_bag_id;
            }

            public void setBless_bag_id(String bless_bag_id) {
                this.bless_bag_id = bless_bag_id;
            }

            public String getSponsor_user_name() {
                return sponsor_user_name;
            }

            public void setSponsor_user_name(String sponsor_user_name) {
                this.sponsor_user_name = sponsor_user_name;
            }

            public String getReceive_super_ability() {
                return receive_super_ability;
            }

            public void setReceive_super_ability(String receive_super_ability) {
                this.receive_super_ability = receive_super_ability;
            }
        }
    }
}
