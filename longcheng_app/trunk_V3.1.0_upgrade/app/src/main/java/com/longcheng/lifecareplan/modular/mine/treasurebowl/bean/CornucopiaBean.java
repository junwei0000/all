package com.longcheng.lifecareplan.modular.mine.treasurebowl.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuAfterBean;

import java.util.List;

public class CornucopiaBean extends ResponseBean {

    @SerializedName("data")
    private CornucopiaBean data;

    public CornucopiaBean getData() {
        return data;
    }

    public void setData(CornucopiaBean data) {
        this.data = data;
    }
    /**
     * userCornucopia : {"user_id":"834","user_name":"张阳","shoukangyuan":0,"jieqibao":0,"fuqibao":0,"uzhufubao":0,"score":0,"total_score":0,"limit":0,"total_limit":0,"super_shoukangyuan":0,"ability":0,"status":1,"create_time":1597121552,"update_time":1597121552}
     * userCornConfigList : [{"cornucopia_config_id":1,"type":1,"name":"24节气","value":24,"rate":0,"status":1,"create_time":0,"update_time":0},{"cornucopia_config_id":2,"type":2,"name":"48节气","value":48,"rate":0,"status":1,"create_time":0,"update_time":0},{"cornucopia_config_id":3,"type":3,"name":"72节气","value":72,"rate":0,"status":1,"create_time":0,"update_time":0}]
     */
    private UserCornucopiaBean userCornucopia;
    private List<UserCornConfigListBean> userCornConfigList;
    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public UserCornucopiaBean getUserCornucopia() {
        return userCornucopia;
    }

    public void setUserCornucopia(UserCornucopiaBean userCornucopia) {
        this.userCornucopia = userCornucopia;
    }

    public List<UserCornConfigListBean> getUserCornConfigList() {
        return userCornConfigList;
    }

    public void setUserCornConfigList(List<UserCornConfigListBean> userCornConfigList) {
        this.userCornConfigList = userCornConfigList;
    }

}
