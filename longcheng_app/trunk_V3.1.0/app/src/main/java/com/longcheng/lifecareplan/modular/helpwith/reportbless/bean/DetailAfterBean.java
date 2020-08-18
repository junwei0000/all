package com.longcheng.lifecareplan.modular.helpwith.reportbless.bean;

import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class DetailAfterBean extends ResponseBean {
    protected ArrayList<DetailItemBean> lack_user_solar_terms;
    protected DetailItemBean bless_user_statistics;
    protected ArrayList<DetailItemBean> waitFillDatas;
    protected ArrayList<DetailItemBean> sponsorDatas;
    //
    protected DetailItemBean bless;
    protected ArrayList<String> currentUserIdentitys;
    protected DetailItemBean chatuser;



    //申请页信息
    protected ApplyItemBean userInfo;
    protected ArrayList<ApplyItemBean> jieqiByUser;
    private int usable_bless_card_count;
    String bless_apply_money;


    public ApplyItemBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ApplyItemBean userInfo) {
        this.userInfo = userInfo;
    }

    public ArrayList<ApplyItemBean> getJieqiByUser() {
        return jieqiByUser;
    }

    public void setJieqiByUser(ArrayList<ApplyItemBean> jieqiByUser) {
        this.jieqiByUser = jieqiByUser;
    }

    public int getUsable_bless_card_count() {
        return usable_bless_card_count;
    }

    public void setUsable_bless_card_count(int usable_bless_card_count) {
        this.usable_bless_card_count = usable_bless_card_count;
    }

    public String getBless_apply_money() {
        return bless_apply_money;
    }

    public void setBless_apply_money(String bless_apply_money) {
        this.bless_apply_money = bless_apply_money;
    }

    public ArrayList<DetailItemBean> getLack_user_solar_terms() {
        return lack_user_solar_terms;
    }

    public void setLack_user_solar_terms(ArrayList<DetailItemBean> lack_user_solar_terms) {
        this.lack_user_solar_terms = lack_user_solar_terms;
    }

    public DetailItemBean getBless_user_statistics() {
        return bless_user_statistics;
    }

    public void setBless_user_statistics(DetailItemBean bless_user_statistics) {
        this.bless_user_statistics = bless_user_statistics;
    }

    public ArrayList<DetailItemBean> getWaitFillDatas() {
        return waitFillDatas;
    }

    public void setWaitFillDatas(ArrayList<DetailItemBean> waitFillDatas) {
        this.waitFillDatas = waitFillDatas;
    }

    public ArrayList<DetailItemBean> getSponsorDatas() {
        return sponsorDatas;
    }

    public void setSponsorDatas(ArrayList<DetailItemBean> sponsorDatas) {
        this.sponsorDatas = sponsorDatas;
    }

    public DetailItemBean getBless() {
        return bless;
    }

    public void setBless(DetailItemBean bless) {
        this.bless = bless;
    }

    public ArrayList<String> getCurrentUserIdentitys() {
        return currentUserIdentitys;
    }

    public void setCurrentUserIdentitys(ArrayList<String> currentUserIdentitys) {
        this.currentUserIdentitys = currentUserIdentitys;
    }

    public DetailItemBean getChatuser() {
        return chatuser;
    }

    public void setChatuser(DetailItemBean chatuser) {
        this.chatuser = chatuser;
    }

    public class DetailItemBean {
        String solar_terms_name;
        String solar_terms_en="";
        String solar_term_img;
        String complete_money;//福气指数

        String create_date;

        ArrayList<DetailItemBean> img;

        String user_id;
        String avatar;
        String user_name;
        private String jieqi_name;

        private String flora;
        private String tone;
        private ArrayList<String> identity;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSolar_terms_en() {
            return solar_terms_en;
        }

        public void setSolar_terms_en(String solar_terms_en) {
            this.solar_terms_en = solar_terms_en;
        }

        public String getSolar_terms_name() {
            return solar_terms_name;
        }

        public void setSolar_terms_name(String solar_terms_name) {
            this.solar_terms_name = solar_terms_name;
        }


        public String getComplete_money() {
            return complete_money;
        }

        public void setComplete_money(String complete_money) {
            this.complete_money = complete_money;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getSolar_term_img() {
            return solar_term_img;
        }

        public void setSolar_term_img(String solar_term_img) {
            this.solar_term_img = solar_term_img;
        }

        public ArrayList<DetailItemBean> getImg() {
            return img;
        }

        public void setImg(ArrayList<DetailItemBean> img) {
            this.img = img;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getJieqi_name() {
            return jieqi_name;
        }

        public void setJieqi_name(String jieqi_name) {
            this.jieqi_name = jieqi_name;
        }


        public String getFlora() {
            return flora;
        }

        public void setFlora(String flora) {
            this.flora = flora;
        }

        public String getTone() {
            return tone;
        }

        public void setTone(String tone) {
            this.tone = tone;
        }

        public ArrayList<String> getIdentity() {
            return identity;
        }

        public void setIdentity(ArrayList<String> identity) {
            this.identity = identity;
        }

    }
}
