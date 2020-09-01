package com.longcheng.lifecareplan.modular.mine.treasurebowl.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

public class RankListBean extends ResponseBean {
    @SerializedName("data")
    private RankListBean data;

    public RankListBean getData() {
        return data;
    }

    public void setData(RankListBean data) {
        this.data = data;
    }

    /**
     * ranking : [{"user_cornucopia_ranking_id":63,"user_id":234032,"user_name":"18510770350","avatar":"http://test.t.asdyf.com//static/home/images/icon_pretermission.png?v=1.0.0","points":1000,"date":"2020-08-10","start_date":"2020-08-10 21:00:00","end_date":"2020-08-11 21:00:00","type":2,"create_time":1597129545,"update_time":1597129545,"jieqi_name":"丁丑·大暑","identity_img":["http://test.t.asdyf.com//static/home/images/useridentity/cho.png?vi=1.0.4"],"ranking":1,"grades":1000},{"user_cornucopia_ranking_id":61,"user_id":905,"user_name":"郭永进","avatar":"http://cho-health-mutual-commune.img-cn-beijing.aliyuncs.com/user_photo/20190706/19070616500421067.jpeg?x-oss-process=image/resize,m_fill,h_100,w_100","points":76,"date":"2020-08-10","start_date":"2020-08-10 21:00:00","end_date":"2020-08-11 21:00:00","type":2,"create_time":1597129545,"update_time":1597129545,"jieqi_name":"庚午·秋分","identity_img":["http://test.t.asdyf.com//static/home/images/useridentity/star_level_5.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/cho.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/volunteer.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/leifeng.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/doctor.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/commissioner.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/partymember.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/blessed_teacher.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/center_blessed_teacher.png?vi=1.0.4"],"ranking":2,"grades":76}]
     * userSelf : {"user_cornucopia_ranking_id":63,"user_id":234032,"user_name":"18510770350","avatar":"http://test.t.asdyf.com//static/home/images/icon_pretermission.png?v=1.0.0","points":1000,"date":"2020-08-10","start_date":"2020-08-10 21:00:00","end_date":"2020-08-11 21:00:00","type":2,"create_time":1597129545,"update_time":1597129545,"ranking":1,"jieqi_name":"丁丑·大暑","identity_img":["http://test.t.asdyf.com//static/home/images/useridentity/cho.png?vi=1.0.4"],"grades":1000}
     */

    private UserSelfBean userSelf;
    private List<RankingBean> ranking;

    public UserSelfBean getUserSelf() {
        return userSelf;
    }

    public void setUserSelf(UserSelfBean userSelf) {
        this.userSelf = userSelf;
    }

    public List<RankingBean> getRanking() {
        return ranking;
    }

    public void setRanking(List<RankingBean> ranking) {
        this.ranking = ranking;
    }

}
