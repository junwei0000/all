package com.longcheng.lifecareplan.modular.mine.emergencys;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

public class EmergencyDetaiBean extends ResponseBean {
    @SerializedName("data")
    //
    public DetailBean data;

    public DetailBean getData() {
        return data;
    }

    public void setData(DetailBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EmergencyDetaiBean{" +
                "data=" + data +
                '}';
    }

    public static class DetailBean {
        @Override
        public String toString() {
            return "DetailBean{" +
                    "solar_term_img='" + solar_term_img + '\'' +
                    ", currentUserIdentitys=" + currentUserIdentitys +
                    ", current_jieqi=" + current_jieqi +
                    ", helpNeed=" + helpNeed +
                    '}';
        }

        public String solar_term_img;
        public CurrentUserIdentitys currentUserIdentitys;
        public Current_jieqi current_jieqi;
        public HelpNeed helpNeed;
        public int[] payAbility;

        public int[] getPayAbility() {
            return payAbility;
        }

        public void setPayAbility(int[] payAbility) {
            this.payAbility = payAbility;
        }

        public String getSolar_term_img() {
            return solar_term_img;
        }

        public void setSolar_term_img(String solar_term_img) {
            this.solar_term_img = solar_term_img;
        }

        public CurrentUserIdentitys getCurrentUserIdentitys() {
            return currentUserIdentitys;
        }

        public void setCurrentUserIdentitys(CurrentUserIdentitys currentUserIdentitys) {
            this.currentUserIdentitys = currentUserIdentitys;
        }

        public Current_jieqi getCurrent_jieqi() {
            return current_jieqi;
        }

        public void setCurrent_jieqi(Current_jieqi current_jieqi) {
            this.current_jieqi = current_jieqi;
        }

        public HelpNeed getHelpNeed() {
            return helpNeed;
        }

        public void setHelpNeed(HelpNeed helpNeed) {
            this.helpNeed = helpNeed;
        }

        public static class CurrentUserIdentitys {

            public String jieqi_name;
            public List<Img> img;

            @Override
            public String toString() {
                return "CurrentUserIdentitys{" +
                        "jieqi_name='" + jieqi_name + '\'' +
                        ", img=" + img +
                        '}';
            }

            public String getJieqi_name() {
                return jieqi_name;
            }

            public void setJieqi_name(String jieqi_name) {
                this.jieqi_name = jieqi_name;
            }

            public List<Img> getImg() {
                return img;
            }

            public void setImg(List<Img> img) {
                this.img = img;
            }


            public static class Img {
                public String name;
                public String image;
                public String type;
                public String image_html;
                public String jieqi;
                public String img_all;
                public String jieqi_name;
            }
        }

        public static class Current_jieqi {
            public String getCn() {
                return cn;
            }

            @Override
            public String toString() {
                return "Current_jieqi{" +
                        "cn='" + cn + '\'' +
                        ", time='" + time + '\'' +
                        '}';
            }

            public void setCn(String cn) {
                this.cn = cn;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String cn;
            public String time;
        }

        public static class HelpNeed {
            public String getUser_avatar() {
                return user_avatar;
            }

            @Override
            public String toString() {
                return "HelpNeed{" +
                        "user_avatar='" + user_avatar + '\'' +
                        ", user_name='" + user_name + '\'' +
                        ", obtain_super_ability='" + obtain_super_ability + '\'' +
                        ", super_ability='" + super_ability + '\'' +
                        ", cumulative_number=" + cumulative_number +
                        '}';
            }

            public void setUser_avatar(String user_avatar) {
                this.user_avatar = user_avatar;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getObtain_super_ability() {
                return obtain_super_ability;
            }

            public void setObtain_super_ability(String obtain_super_ability) {
                this.obtain_super_ability = obtain_super_ability;
            }

            public String getSuper_ability() {
                return super_ability;
            }

            public void setSuper_ability(String super_ability) {
                this.super_ability = super_ability;
            }

            public int getCumulative_number() {
                return cumulative_number;
            }

            public void setCumulative_number(int cumulative_number) {
                this.cumulative_number = cumulative_number;
            }

            public String user_avatar;
            public String user_name;
            public String obtain_super_ability;
            public String super_ability;
            public int status;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int cumulative_number;

        }
    }

}
