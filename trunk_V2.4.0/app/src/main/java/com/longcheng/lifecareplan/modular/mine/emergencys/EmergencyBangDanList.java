package com.longcheng.lifecareplan.modular.mine.emergencys;

import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

//
public class EmergencyBangDanList extends ResponseBean {
    //
    public List<BessCardRankings> blessCardRanking;
    public UserSelf userSelf;

    public List<BessCardRankings> getBlessCardRanking() {
        return blessCardRanking;
    }

    public void setBlessCardRanking(List<BessCardRankings> blessCardRanking) {
        this.blessCardRanking = blessCardRanking;
    }

    public UserSelf getUserSelf() {
        return userSelf;
    }

    public void setUserSelf(UserSelf userSelf) {
        this.userSelf = userSelf;
    }

    public class UserSelf {
        String user_id;
        public String avatar;
        public String user_name;
        public String jieqi_name;
        public String ranking;
        public List<String> identity_img;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getRanking() {
            return ranking;
        }

        public void setRanking(String ranking) {
            this.ranking = ranking;
        }

        public List<String> getIdentity_img() {
            return identity_img;
        }

        public void setIdentity_img(List<String> identity_img) {
            this.identity_img = identity_img;
        }

        @Override
        public String toString() {
            return "UserSelf{" +
                    "avatar='" + avatar + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", jieqi_name='" + jieqi_name + '\'' +
                    ", ranking='" + ranking + '\'' +
                    ", identity_imgs=" + identity_img +
                    '}';
        }
    }


    public static class BessCardRankings {
        public String avatar;
        public String user_name;
        public String jieqi_name;
        public String ranking;
        public List<String> identity_img;

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

        public String getRanking() {
            return ranking;
        }

        public void setRanking(String ranking) {
            this.ranking = ranking;
        }

        public List<String> getIdentity_img() {
            return identity_img;
        }

        public void setIdentity_imgs(List<String> identity_imgs) {
            this.identity_img = identity_imgs;
        }

        @Override
        public String toString() {
            return "BessCardRankings{" +
                    "avatar='" + avatar + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", jieqi_name='" + jieqi_name + '\'' +
                    ", ranking='" + ranking + '\'' +
                    ", identity_imgs=" + identity_img +
                    '}';
        }
    }


}
