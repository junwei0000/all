package com.longcheng.lifecareplan.modular.mine.relief;

import java.util.List;

public class HelpReliefBean {

    /**
     * data : [{"id":791,"recive_uid":1504,"send_uid":1506,"send_time":1585133216,"number":365,"is_valid":1,"is_recive":0},{"id":791,"recive_uid":1504,"send_uid":1506,"send_time":1585133216,"number":365,"is_valid":1,"is_recive":0}]
     * msg : 已经处理完成
     * status : 200
     */

    public String msg;
    public String status;
    public List<DataBean> data;


    public static class DataBean {
        /**
         * id : 791
         * recive_uid : 1504
         * send_uid : 1506
         * send_time : 1585133216
         * number : 365
         * is_valid : 1
         * is_recive : 0
         */

        public String id;
        public String recive_uid;
        public String send_uid;
        public String send_time;
        public String number;
        public String is_valid;
        public String is_recive;
        public String user_name;
        public String avatar;


    }
}
