package com.longcheng.lifecareplan.modular.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/23 17:44
 * 意图：
 */

public class HomeItemBean implements Serializable {
    private int group_id;
    private int team_id;
    //current_jieqi
    private String id;
    private String year;
    private String en;
    private String time;
    private String day;
    private String date;
    private String cn;
    private String date_desc;
    private String week;
    private String pic;

    //banners
    private String pre_jieqi_name;
    private String sponsor_help_number;
    private String sponsor_ability;
    private String receive_help_number;
    private String receive_ability;
    private String desc;
    private String color;
    private int sort;
    //newpu
    private String new_id;
    private String new_name;
    private String des;
    private String type_name;
    private String add_time;
    private String new_num;
    private String new_zan;
    private String info_url;
    //actions
    private String ability_price;
    private String name;
    private String num_limit;
    private String img;
    private String goods_id;
    private String name1;
    private String name2;
    //msg
    private String h_user_id;
    private String h_user;//接福人
    private String gs_name;//公社名称
    private int progress;//进度
    private String ability_price_action;//生命能量
    private String action_image;
    private String group_img;//公社头像
    private String action_name;

    //rankingData
    private String title;
    private String subtitle;
    private String note;
    private String start_time;
    private String end_time;
    private String url;
    private int type;
    private String skip_source;


    private List<HomeItemBean> extend_info;
    private String user_invitation_ability_ranking_id;
    private String user_name;
    private String group_name;
    private String help_number;
    private String ability;
    private int status;
    private String avatar;


    private String href;
    private int isLiveBroadcast;//是否有直播权限

    private String msg;
    private int skip_type;//0 跳转提现记录     1  跳转代付列表
    private String skip_url;


    private String content;
    private String video_url;

    private int is_alert;
    private String activity_date;

    public int getIs_alert() {
        return is_alert;
    }

    public void setIs_alert(int is_alert) {
        this.is_alert = is_alert;
    }

    public String getActivity_date() {
        return activity_date;
    }

    public void setActivity_date(String activity_date) {
        this.activity_date = activity_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getSkip_url() {
        return skip_url;
    }

    public void setSkip_url(String skip_url) {
        this.skip_url = skip_url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getSkip_type() {
        return skip_type;
    }

    public void setSkip_type(int skip_type) {
        this.skip_type = skip_type;
    }

    public String getSkip_source() {
        return skip_source;
    }

    public void setSkip_source(String skip_source) {
        this.skip_source = skip_source;
    }

    public String getNew_num() {
        return new_num;
    }

    public void setNew_num(String new_num) {
        this.new_num = new_num;
    }

    public String getNew_zan() {
        return new_zan;
    }

    public void setNew_zan(String new_zan) {
        this.new_zan = new_zan;
    }

    public int getIsLiveBroadcast() {
        return isLiveBroadcast;
    }

    public void setIsLiveBroadcast(int isLiveBroadcast) {
        this.isLiveBroadcast = isLiveBroadcast;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPre_jieqi_name() {
        return pre_jieqi_name;
    }

    public void setPre_jieqi_name(String pre_jieqi_name) {
        this.pre_jieqi_name = pre_jieqi_name;
    }

    public String getSponsor_help_number() {
        return sponsor_help_number;
    }

    public void setSponsor_help_number(String sponsor_help_number) {
        this.sponsor_help_number = sponsor_help_number;
    }

    public String getSponsor_ability() {
        return sponsor_ability;
    }

    public void setSponsor_ability(String sponsor_ability) {
        this.sponsor_ability = sponsor_ability;
    }

    public String getReceive_help_number() {
        return receive_help_number;
    }

    public void setReceive_help_number(String receive_help_number) {
        this.receive_help_number = receive_help_number;
    }

    public String getReceive_ability() {
        return receive_ability;
    }

    public void setReceive_ability(String receive_ability) {
        this.receive_ability = receive_ability;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getCreate_time() {
        return add_time;
    }

    public void setCreate_time(String create_time) {
        this.add_time = create_time;
    }

    public String getInfo_url() {
        return info_url;
    }

    public void setInfo_url(String info_url) {
        this.info_url = info_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getDate_desc() {
        return date_desc;
    }

    public void setDate_desc(String date_desc) {
        this.date_desc = date_desc;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getNew_id() {
        return new_id;
    }

    public void setNew_id(String new_id) {
        this.new_id = new_id;
    }

    public String getNew_name() {
        return new_name;
    }

    public void setNew_name(String new_name) {
        this.new_name = new_name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAbility_price() {
        return ability_price;
    }

    public void setAbility_price(String ability_price) {
        this.ability_price = ability_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum_limit() {
        return num_limit;
    }

    public void setNum_limit(String num_limit) {
        this.num_limit = num_limit;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getH_user_id() {
        return h_user_id;
    }

    public void setH_user_id(String h_user_id) {
        this.h_user_id = h_user_id;
    }

    public String getH_user() {
        return h_user;
    }

    public void setH_user(String h_user) {
        this.h_user = h_user;
    }

    public String getGs_name() {
        return gs_name;
    }

    public void setGs_name(String gs_name) {
        this.gs_name = gs_name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getAbility_price_action() {
        return ability_price_action;
    }

    public void setAbility_price_action(String ability_price_action) {
        this.ability_price_action = ability_price_action;
    }

    public String getAction_image() {
        return action_image;
    }

    public void setAction_image(String action_image) {
        this.action_image = action_image;
    }

    public String getGroup_img() {
        return group_img;
    }

    public void setGroup_img(String group_img) {
        this.group_img = group_img;
    }

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<HomeItemBean> getExtend_info() {
        return extend_info;
    }

    public void setExtend_info(List<HomeItemBean> extend_info) {
        this.extend_info = extend_info;
    }

    public String getUser_invitation_ability_ranking_id() {
        return user_invitation_ability_ranking_id;
    }

    public void setUser_invitation_ability_ranking_id(String user_invitation_ability_ranking_id) {
        this.user_invitation_ability_ranking_id = user_invitation_ability_ranking_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getHelp_number() {
        return help_number;
    }

    public void setHelp_number(String help_number) {
        this.help_number = help_number;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }
}