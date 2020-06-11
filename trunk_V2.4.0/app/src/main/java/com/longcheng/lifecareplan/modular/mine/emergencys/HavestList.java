package com.longcheng.lifecareplan.modular.mine.emergencys;

import java.io.Serializable;
import java.util.List;

public class HavestList implements Serializable {
    public int page;
    public int page_size;
    public int count;
    public List<Order> order;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "HavestList{" +
                "page=" + page +
                ", page_size=" + page_size +
                ", count=" + count +
                ", order=" + order +
                '}';
    }

    public static class Order {
        public String avatar;
        public String user_name;
        public String super_ability;
        public List<Img> identity_flag;

        @Override
        public String toString() {
            return "Order{" +
                    "avatar='" + avatar + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", super_ability='" + super_ability + '\'' +
                    ", identity_flag=" + identity_flag +
                    '}';
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

        public String getSuper_ability() {
            return super_ability;
        }

        public void setSuper_ability(String super_ability) {
            this.super_ability = super_ability;
        }

        public List<Img> getIdentity_flag() {
            return identity_flag;
        }

        public void setIdentity_flag(List<Img> identity_flag) {
            this.identity_flag = identity_flag;
        }

        public static class Img {
            public String name;
            public String image;
            public String type;
            public String image_html;
            public String jieqi;
            public String img_all;
            public String jieqi_name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getImage_html() {
                return image_html;
            }

            public void setImage_html(String image_html) {
                this.image_html = image_html;
            }

            public String getJieqi() {
                return jieqi;
            }

            public void setJieqi(String jieqi) {
                this.jieqi = jieqi;
            }

            public String getImg_all() {
                return img_all;
            }

            public void setImg_all(String img_all) {
                this.img_all = img_all;
            }

            public String getJieqi_name() {
                return jieqi_name;
            }

            public void setJieqi_name(String jieqi_name) {
                this.jieqi_name = jieqi_name;
            }

            @Override
            public String toString() {
                return "Img{" +
                        "name='" + name + '\'' +
                        ", image='" + image + '\'' +
                        ", type='" + type + '\'' +
                        ", image_html='" + image_html + '\'' +
                        ", jieqi='" + jieqi + '\'' +
                        ", img_all='" + img_all + '\'' +
                        ", jieqi_name='" + jieqi_name + '\'' +
                        '}';
            }
        }

    }

}
