package com.longcheng.lifecareplan.modular.im.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuAfterBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class PionImDataBean extends ResponseBean {
    @SerializedName("data")
    private PionImAfterBean data;

    public PionImAfterBean getData() {
        return data;
    }

    public void setData(PionImAfterBean data) {
        this.data = data;
    }

    public class PionImAfterBean {

        int hasCashInProgressOrder;
        PionImAfterBean solarTerms;
        String solar_terms_name;

        public PionImAfterBean getSolarTerms() {
            return solarTerms;
        }

        public void setSolarTerms(PionImAfterBean solarTerms) {
            this.solarTerms = solarTerms;
        }

        public String getSolar_terms_name() {
            return solar_terms_name;
        }

        public void setSolar_terms_name(String solar_terms_name) {
            this.solar_terms_name = solar_terms_name;
        }

        public int getHasCashInProgressOrder() {
            return hasCashInProgressOrder;
        }

        public void setHasCashInProgressOrder(int hasCashInProgressOrder) {
            this.hasCashInProgressOrder = hasCashInProgressOrder;
        }
    }
}
