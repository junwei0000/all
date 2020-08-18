package com.longcheng.lifecareplan.modular.mine.relief;

import java.util.List;

public class ReliefDetailsBean {

    /**
     * data : {"number":730,"bless_me":2,"airm":"9000","user_name":"柔yyyyggg","user_avatar":"http://test.t.asdyf.com/http://cho-health-mutual-commune.oss-cn-beijing.aliyuncs.com/upload/202002/12/484c073c0e3442c8964bb0ca4e452693.jpg","user_jieqi_name":"芒种","user_jieqi_branch_name":"戊寅","now_jieqi":{"id":81036,"year":2020,"en":"chunfen","time":"2020-03-20 11:49:29","day":"2020-03-20","date":"2020-03-20","cn":"春分","date_desc":"第七天","diff_days":7},"now_jieqi_img":"http://test.t.asdyf.com//static/home/images/240730_jq_img/240730_chunfen.png","images":["http://test.t.asdyf.com//static/home/images/useridentity/cho.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/partymember.png?vi=1.0.4"]}
     * msg : 操作成功
     * status : 200
     */

    public DataBean data;
    public String msg;
    public String status;


    public static class DataBean {
        /**
         * number : 730
         * bless_me : 2
         * airm : 9000
         * user_name : 柔yyyyggg
         * user_avatar : http://test.t.asdyf.com/http://cho-health-mutual-commune.oss-cn-beijing.aliyuncs.com/upload/202002/12/484c073c0e3442c8964bb0ca4e452693.jpg
         * user_jieqi_name : 芒种
         * user_jieqi_branch_name : 戊寅
         * now_jieqi : {"id":81036,"year":2020,"en":"chunfen","time":"2020-03-20 11:49:29","day":"2020-03-20","date":"2020-03-20","cn":"春分","date_desc":"第七天","diff_days":7}
         * now_jieqi_img : http://test.t.asdyf.com//static/home/images/240730_jq_img/240730_chunfen.png
         * images : ["http://test.t.asdyf.com//static/home/images/useridentity/cho.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/partymember.png?vi=1.0.4"]
         */

        public String number;
        public String bless_me;
        public String airm;
        public String user_name;
        public String user_avatar;
        public String user_jieqi_name;
        public String user_jieqi_branch_name;
        public NowJieqiBean now_jieqi;
        public String now_jieqi_img;
        public List<String> images;


        public static class NowJieqiBean {
            /**
             * id : 81036
             * year : 2020
             * en : chunfen
             * time : 2020-03-20 11:49:29
             * day : 2020-03-20
             * date : 2020-03-20
             * cn : 春分
             * date_desc : 第七天
             * diff_days : 7
             */

            public String id;
            public String year;
            public String en;
            public String time;
            public String day;
            public String date;
            public String cn;
            public String date_desc;
            public String diff_days;


        }
    }
}
