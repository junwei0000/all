package com.longcheng.lifecareplan.bean.fupackage;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.FQCenterDataBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.CornucopiaListBean;

import java.util.List;

public class MyFuListBean extends ResponseBean {

    @SerializedName("data")
    private MyFuListAfterBean data;

    public MyFuListAfterBean getData() {
        return data;
    }

    public void setData(MyFuListAfterBean data) {
        this.data = data;
    }

    public static class MyFuListAfterBean {
        private List<FuListBean> list;
        String avatar;
        String count;

        public List<FuListBean> getList() {
            return list;
        }

        public void setList(List<FuListBean> list) {
            this.list = list;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
