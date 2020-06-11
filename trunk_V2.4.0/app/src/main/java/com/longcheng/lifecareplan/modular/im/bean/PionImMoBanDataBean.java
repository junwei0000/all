package com.longcheng.lifecareplan.modular.im.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class PionImMoBanDataBean extends ResponseBean {
    @SerializedName("data")
    private ArrayList<PionImMoBanItemBean> data;

    public ArrayList<PionImMoBanItemBean> getData() {
        return data;
    }

    public void setData(ArrayList<PionImMoBanItemBean> data) {
        this.data = data;
    }

    public class PionImMoBanItemBean {

        String im_user_temp_id;
        String main;

        public String getIm_user_temp_id() {
            return im_user_temp_id;
        }

        public void setIm_user_temp_id(String im_user_temp_id) {
            this.im_user_temp_id = im_user_temp_id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }
    }
}
