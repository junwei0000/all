package com.longcheng.lifecareplan.modular.mine.userinfo.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class EditThumbDataBean extends ResponseBean {
    @SerializedName("data")
    private EditThumbBackDataBean data;

    public EditThumbBackDataBean getData() {
        return data;
    }

    public void setData(EditThumbBackDataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data =" + data + " , msg = " + msg + " , status = " + status;
    }

    public class EditThumbBackDataBean {

        @SerializedName("url_path")
        private String url_path;

        public String getUrl_path() {
            return url_path;
        }

        public void setUrl_path(String url_path) {
            this.url_path = url_path;
        }
    }
}
