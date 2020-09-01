package com.longcheng.lifecareplan.modular.mine.pioneercenter.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class PionApplyAfterBean extends ResponseBean {
    private ArrayList<RecordBean> openTask;
    int isPassTask;//是否需要任务

    public ArrayList<RecordBean> getOpenTask() {
        return openTask;
    }

    public void setOpenTask(ArrayList<RecordBean> openTask) {
        this.openTask = openTask;
    }

    public int getIsPassTask() {
        return isPassTask;
    }

    public void setIsPassTask(int isPassTask) {
        this.isPassTask = isPassTask;
    }

    public class  RecordBean{
        int status;
       int position;
       String icon;
       String title;
       ArrayList<String> items;

       public int getPosition() {
           return position;
       }

       public void setPosition(int position) {
           this.position = position;
       }

       public String getIcon() {
           return icon;
       }

       public void setIcon(String icon) {
           this.icon = icon;
       }

       public String getTitle() {
           return title;
       }

       public void setTitle(String title) {
           this.title = title;
       }

       public ArrayList<String> getItems() {
           return items;
       }

       public void setItems(ArrayList<String> items) {
           this.items = items;
       }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
