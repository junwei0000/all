package com.longcheng.lifecareplan.modular.mine.relief;

import java.util.List;

public class ReliefBottomInfoBean {

    /**
     * data : {"avatar":"http://cho-health-mutual-commune.oss-cn-beijing.aliyuncs.com/user_photo/20200317/20031715455220959.jpeg","user_name":"白润江","jieqi_branch_name":"庚申","jieqi_name":"谷雨","enegry":730,"images":["http://www.huzhu.com//static/home/images/useridentity/cho.png?vi=1.0.4","http://www.huzhu.com//static/home/images/useridentity/volunteer.png?vi=1.0.4","http://www.huzhu.com//static/home/images/useridentity/leifeng.png?vi=1.0.4","http://www.huzhu.com//static/home/images/useridentity/doctor.png?vi=1.0.4","http://www.huzhu.com//static/home/images/useridentity/partymember.png?vi=1.0.4","http://www.huzhu.com//static/home/images/useridentity/commissioner.png?vi=1.0.4","http://www.huzhu.com//static/home/images/useridentity/soldier.png?vi=1.0.4"]}
     * msg : 操作成功
     * status : 200
     */

    public DataBean data;
    public String msg;
    public String status;


    public static class DataBean {
        /**
         * avatar : http://cho-health-mutual-commune.oss-cn-beijing.aliyuncs.com/user_photo/20200317/20031715455220959.jpeg
         * user_name : 白润江
         * jieqi_branch_name : 庚申
         * jieqi_name : 谷雨
         * enegry : 730
         * images : ["http://www.huzhu.com//static/home/images/useridentity/cho.png?vi=1.0.4","http://www.huzhu.com//static/home/images/useridentity/volunteer.png?vi=1.0.4","http://www.huzhu.com//static/home/images/useridentity/leifeng.png?vi=1.0.4","http://www.huzhu.com//static/home/images/useridentity/doctor.png?vi=1.0.4","http://www.huzhu.com//static/home/images/useridentity/partymember.png?vi=1.0.4","http://www.huzhu.com//static/home/images/useridentity/commissioner.png?vi=1.0.4","http://www.huzhu.com//static/home/images/useridentity/soldier.png?vi=1.0.4"]
         */

        public String avatar;
        public String user_name;
        public String jieqi_branch_name;
        public String jieqi_name;
        public String enegry;
        public String status;
        public String rank;
        public String enegryall;
        public String start_time;
        public List<String> images;


    }
}
