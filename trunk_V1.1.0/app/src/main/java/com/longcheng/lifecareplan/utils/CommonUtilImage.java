package com.longcheng.lifecareplan.utils;

import android.os.Environment;


/**
 * 作者：MarkShuai on
 * 时间：2017/11/21 16:15
 * 邮箱：mark_mingshuai@163.com
 * 意图：SD卡工具类
 */

public class CommonUtilImage {

    public static String getPath() {
        if (hasSDCard()) {
            //获取SDCard卡目录
            return Environment.getExternalStorageDirectory().toString()
                    + "/dianping/data/";// filePath:/sdcard/
        } else {
            //获取缓存目录
            return Environment.getDataDirectory().toString()
                    + "/dianping/data/"; // filePath: /data/data/
        }
    }

    //判断SDCard是否存在
    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        return true;
    }

}
