package com.longcheng.lifecareplantv.bean;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 10:04
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class Bean {

    private List<Area1Bean> Area1;
    private List<Area2Bean> Area2;
    private List<?> Area3;

    public List<Area1Bean> getArea1() {
        return Area1;
    }

    public void setArea1(List<Area1Bean> Area1) {
        this.Area1 = Area1;
    }

    public List<Area2Bean> getArea2() {
        return Area2;
    }

    public void setArea2(List<Area2Bean> Area2) {
        this.Area2 = Area2;
    }

    public List<?> getArea3() {
        return Area3;
    }

    public void setArea3(List<?> Area3) {
        this.Area3 = Area3;
    }

    public static class Area1Bean {
        /**
         * ID : 1
         * Area : 1
         * Name : 邀请好友
         * Icon : https://cdn.shihua.com/resources/app/yaoqing.png
         * ClickType : 1
         * Url : https://share.yunxuekeji.com/1
         * CreateOn : null
         * IsDelete : 0
         */

        private int ID;
        private int Area;
        private String Name;
        private String Icon;
        private int ClickType;
        private String Url;
        private Object CreateOn;
        private int IsDelete;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getArea() {
            return Area;
        }

        public void setArea(int Area) {
            this.Area = Area;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getIcon() {
            return Icon;
        }

        public void setIcon(String Icon) {
            this.Icon = Icon;
        }

        public int getClickType() {
            return ClickType;
        }

        public void setClickType(int ClickType) {
            this.ClickType = ClickType;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }

        public Object getCreateOn() {
            return CreateOn;
        }

        public void setCreateOn(Object CreateOn) {
            this.CreateOn = CreateOn;
        }

        public int getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(int IsDelete) {
            this.IsDelete = IsDelete;
        }
    }

    public static class Area2Bean {
        /**
         * ID : 2
         * Area : 2
         * Name : 签到
         * Icon : https://cdn.shihua.com/resources/app/sign.png
         * ClickType : 1
         * Url : https://share.yunxuekeji.com/2
         * CreateOn : null
         * IsDelete : 0
         */

        private int ID;
        private int Area;
        private String Name;
        private String Icon;
        private int ClickType;
        private String Url;
        private Object CreateOn;
        private int IsDelete;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getArea() {
            return Area;
        }

        public void setArea(int Area) {
            this.Area = Area;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getIcon() {
            return Icon;
        }

        public void setIcon(String Icon) {
            this.Icon = Icon;
        }

        public int getClickType() {
            return ClickType;
        }

        public void setClickType(int ClickType) {
            this.ClickType = ClickType;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }

        public Object getCreateOn() {
            return CreateOn;
        }

        public void setCreateOn(Object CreateOn) {
            this.CreateOn = CreateOn;
        }

        public int getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(int IsDelete) {
            this.IsDelete = IsDelete;
        }
    }
}
