package com.longcheng.lifecareplan.modular.mine.relief;

import java.util.List;

public class ReliefDetailsListBean {

    /**
     * data : [{"number":365,"user_id":1504,"user_name":"白润江","avatar":"http://cho-health-mutual-commune.oss-cn-beijing.aliyuncs.com/user_photo/20200317/20031715455220959.jpeg","images":["http://test.t.asdyf.com//static/home/images/useridentity/star_level_6.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/cho.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/volunteer.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/leifeng.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/doctor.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/partymember.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/blessed_teacher.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/center_blessed_teacher.png?vi=1.0.4"]},{"number":365,"user_id":190261,"user_name":"李淼","avatar":"http://cho-health-mutual-commune.oss-cn-beijing.aliyuncs.com/user_photo/20181123/18112317105244735.jpeg","images":["http://test.t.asdyf.com//static/home/images/useridentity/star_level_8.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/cho.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/volunteer.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/doctor.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/blessed_teacher.png?vi=1.0.4"]}]
     * msg : 操作成功
     * status : 200
     */

    public String msg;
    public String status;
    public List<DataBean> data;


    public static class DataBean {
        /**
         * number : 365
         * user_id : 1504
         * user_name : 白润江
         * avatar : http://cho-health-mutual-commune.oss-cn-beijing.aliyuncs.com/user_photo/20200317/20031715455220959.jpeg
         * images : ["http://test.t.asdyf.com//static/home/images/useridentity/star_level_6.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/cho.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/volunteer.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/leifeng.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/doctor.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/partymember.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/blessed_teacher.png?vi=1.0.4","http://test.t.asdyf.com//static/home/images/useridentity/center_blessed_teacher.png?vi=1.0.4"]
         */

        public String number;
        public String user_id;
        public String user_name;
        public String avatar;
        public List<String> images;


    }
}
