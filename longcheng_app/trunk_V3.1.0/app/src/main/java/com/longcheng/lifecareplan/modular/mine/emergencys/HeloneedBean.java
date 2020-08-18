package com.longcheng.lifecareplan.modular.mine.emergencys;

import java.io.Serializable;
import java.util.List;

public class HeloneedBean implements Serializable {
    public int comCount;
    public int ingCount;
    public List<UserBean> lists;
    public List<LoveBroadcasts> loveBroadcasts;
    public CurrentHelpNeed currentHelpNeed;

    public CurrentHelpNeed getCurrentHelpNeed() {
        return currentHelpNeed;
    }

    public void setCurrentHelpNeed(CurrentHelpNeed currentHelpNeed) {
        this.currentHelpNeed = currentHelpNeed;
    }

    public List<LoveBroadcasts> getLoveBroadcasts() {
        return loveBroadcasts;
    }

    public void setLoveBroadcasts(List<LoveBroadcasts> loveBroadcasts) {
        this.loveBroadcasts = loveBroadcasts;
    }

    public static class UserBean {
        public String user_avatar;
        public String user_name;
        public String create_date;
        public String user_branch_info;
        public String obtain_super_ability;
        public String super_ability;
        public String ranking;
        public int help_need_id;
        public String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getHelp_need_id() {
            return help_need_id;
        }

        public void setHelp_need_id(int help_need_id) {
            this.help_need_id = help_need_id;
        }

        public String getRanking() {
            return ranking;
        }

        public void setRanking(String ranking) {
            this.ranking = ranking;
        }

        public int progress;
        public String create_time;
        public List<Img> user_identity_flag;

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }


        public List<Img> getUser_identity_flag() {
            return user_identity_flag;
        }

        public void setUser_identity_flag(List<Img> user_identity_flag) {
            this.user_identity_flag = user_identity_flag;
        }

        public String getUser_avatar() {
            return user_avatar;
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

        public String getUser_branch_info() {
            return user_branch_info;
        }

        public void setUser_branch_info(String user_branch_info) {
            this.user_branch_info = user_branch_info;
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

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        @Override
        public String toString() {
            return "UserBean{" +
                    "user_avatar='" + user_avatar + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", user_branch_info='" + user_branch_info + '\'' +
                    ", obtain_super_ability='" + obtain_super_ability + '\'' +
                    ", super_ability='" + super_ability + '\'' +
                    ", progress=" + progress +
                    ", create_time='" + create_time + '\'' +
                    '}';
        }


    }

    public int getComCount() {
        return comCount;
    }

    public void setComCount(int comCount) {
        this.comCount = comCount;
    }

    public int getIngCount() {
        return ingCount;
    }

    public void setIngCount(int ingCount) {
        this.ingCount = ingCount;
    }

    public List<UserBean> getList() {
        return lists;
    }

    public void setList(List<UserBean> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "HeloneedBean{" +
                "comCount=" + comCount +
                ", ingCount=" + ingCount +
                ", list=" + lists +
                '}';
    }
}
