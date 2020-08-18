package com.longcheng.lifecareplan.modular.mine.relief;

import java.util.List;

public class ReliefCommBean {

    /**
     * data : {"user_id":877,"user_name":"刘明明232","avatar":"http://cho-health-mutual-commune.oss-cn-beijing.aliyuncs.com/user_photo/20190802/1908021858529594.jpeg","star_level":5,"jieqi_name":"小寒","create_time":0,"imgs":{"img":[{"name":"成就勋章","image":"http://test.t.asdyf.com//static/home/images/useridentity/star_level_5.png?vi=1.0.4","type":"1","image_html":"<img src=\"http://test.t.asdyf.com//static/home/images/useridentity/star_level_5.png?vi=1.0.4\" alt=\"成就勋章\">"},{"name":"党员","image":"http://test.t.asdyf.com//static/home/images/useridentity/partymember.png?vi=1.0.4","type":"1","image_html":"<img src=\"http://test.t.asdyf.com//static/home/images/useridentity/partymember.png?vi=1.0.4\" alt=\"党员\">"}],"jieqi":{"name":"节气","image":"己亥·小寒","type":"2"},"img_all":"<img src=\"http://test.t.asdyf.com//static/home/images/useridentity/star_level_5.png?vi=1.0.4\" alt=\"成就勋章\"><img src=\"http://test.t.asdyf.com//static/home/images/useridentity/cho.png?vi=1.0.4\" alt=\"CHO\"><img src=\"http://test.t.asdyf.com//static/home/images/useridentity/volunteer.png?vi=1.0.4\" alt=\"志愿者\"><img src=\"http://test.t.asdyf.com//static/home/images/useridentity/doctor.png?vi=1.0.4\" alt=\"坐堂医\"><img src=\"http://test.t.asdyf.com//static/home/images/useridentity/partymember.png?vi=1.0.4\" alt=\"党员\">","jieqi_name":"己亥·小寒"}}
     * msg : 符合条件，可以推荐
     * status : 200
     */

    public DataBean data;
    public String msg;
    public String status;


    public static class DataBean {
        /**
         * user_id : 877
         * user_name : 刘明明232
         * avatar : http://cho-health-mutual-commune.oss-cn-beijing.aliyuncs.com/user_photo/20190802/1908021858529594.jpeg
         * star_level : 5
         * jieqi_name : 小寒
         * create_time : 0
         * imgs : {"img":[{"name":"成就勋章","image":"http://test.t.asdyf.com//static/home/images/useridentity/star_level_5.png?vi=1.0.4","type":"1","image_html":"<img src=\"http://test.t.asdyf.com//static/home/images/useridentity/star_level_5.png?vi=1.0.4\" alt=\"成就勋章\">"},{"name":"党员","image":"http://test.t.asdyf.com//static/home/images/useridentity/partymember.png?vi=1.0.4","type":"1","image_html":"<img src=\"http://test.t.asdyf.com//static/home/images/useridentity/partymember.png?vi=1.0.4\" alt=\"党员\">"}],"jieqi":{"name":"节气","image":"己亥·小寒","type":"2"},"img_all":"<img src=\"http://test.t.asdyf.com//static/home/images/useridentity/star_level_5.png?vi=1.0.4\" alt=\"成就勋章\"><img src=\"http://test.t.asdyf.com//static/home/images/useridentity/cho.png?vi=1.0.4\" alt=\"CHO\"><img src=\"http://test.t.asdyf.com//static/home/images/useridentity/volunteer.png?vi=1.0.4\" alt=\"志愿者\"><img src=\"http://test.t.asdyf.com//static/home/images/useridentity/doctor.png?vi=1.0.4\" alt=\"坐堂医\"><img src=\"http://test.t.asdyf.com//static/home/images/useridentity/partymember.png?vi=1.0.4\" alt=\"党员\">","jieqi_name":"己亥·小寒"}
         */

        public String user_id;
        public String user_name;
        public String avatar;
        public String star_level;
        public String jieqi_name;
        public String create_time;
        public ImgsBean imgs;


        public static class ImgsBean {
            /**
             * img : [{"name":"成就勋章","image":"http://test.t.asdyf.com//static/home/images/useridentity/star_level_5.png?vi=1.0.4","type":"1","image_html":"<img src=\"http://test.t.asdyf.com//static/home/images/useridentity/star_level_5.png?vi=1.0.4\" alt=\"成就勋章\">"},{"name":"党员","image":"http://test.t.asdyf.com//static/home/images/useridentity/partymember.png?vi=1.0.4","type":"1","image_html":"<img src=\"http://test.t.asdyf.com//static/home/images/useridentity/partymember.png?vi=1.0.4\" alt=\"党员\">"}]
             * jieqi : {"name":"节气","image":"己亥·小寒","type":"2"}
             * img_all : <img src="http://test.t.asdyf.com//static/home/images/useridentity/star_level_5.png?vi=1.0.4" alt="成就勋章"><img src="http://test.t.asdyf.com//static/home/images/useridentity/cho.png?vi=1.0.4" alt="CHO"><img src="http://test.t.asdyf.com//static/home/images/useridentity/volunteer.png?vi=1.0.4" alt="志愿者"><img src="http://test.t.asdyf.com//static/home/images/useridentity/doctor.png?vi=1.0.4" alt="坐堂医"><img src="http://test.t.asdyf.com//static/home/images/useridentity/partymember.png?vi=1.0.4" alt="党员">
             * jieqi_name : 己亥·小寒
             */

            public JieqiBean jieqi;
            public String img_all;
            public String jieqi_name;
            public List<ImgBean> img;


            public static class JieqiBean {
                /**
                 * name : 节气
                 * image : 己亥·小寒
                 * type : 2
                 */

                public String name;
                public String image;
                public String type;


            }

            public static class ImgBean {
                /**
                 * name : 成就勋章
                 * image : http://test.t.asdyf.com//static/home/images/useridentity/star_level_5.png?vi=1.0.4
                 * type : 1
                 * image_html : <img src="http://test.t.asdyf.com//static/home/images/useridentity/star_level_5.png?vi=1.0.4" alt="成就勋章">
                 */

                public String name;
                public String image;
                public String type;
                public String image_html;


            }
        }
    }
}
