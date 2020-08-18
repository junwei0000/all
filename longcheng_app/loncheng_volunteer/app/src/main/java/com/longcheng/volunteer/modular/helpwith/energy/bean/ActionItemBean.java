package com.longcheng.volunteer.modular.helpwith.energy.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class ActionItemBean implements Serializable {
    private String id;
    private String name;
    private String sort;
    private String image;
    private String content;
    private int goods_id;
    private String robot_sort;
    private String robot_ratio;
    private String automation_robot_ratio;
    private String num_limit;
    private String mutual_help_money_id;
    private String status;
    private String ing_count;

    public ActionItemBean(String name, int goods_id) {
        this.name = name;
        this.goods_id = goods_id;
    }

    public String getIng_count() {
        return ing_count;
    }

    public void setIng_count(String ing_count) {
        this.ing_count = ing_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getRobot_sort() {
        return robot_sort;
    }

    public void setRobot_sort(String robot_sort) {
        this.robot_sort = robot_sort;
    }

    public String getRobot_ratio() {
        return robot_ratio;
    }

    public void setRobot_ratio(String robot_ratio) {
        this.robot_ratio = robot_ratio;
    }

    public String getAutomation_robot_ratio() {
        return automation_robot_ratio;
    }

    public void setAutomation_robot_ratio(String automation_robot_ratio) {
        this.automation_robot_ratio = automation_robot_ratio;
    }

    public String getNum_limit() {
        return num_limit;
    }

    public void setNum_limit(String num_limit) {
        this.num_limit = num_limit;
    }

    public String getMutual_help_money_id() {
        return mutual_help_money_id;
    }

    public void setMutual_help_money_id(String mutual_help_money_id) {
        this.mutual_help_money_id = mutual_help_money_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
