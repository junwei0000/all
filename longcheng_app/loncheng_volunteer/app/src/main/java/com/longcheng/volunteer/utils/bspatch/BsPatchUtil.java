package com.longcheng.volunteer.utils.bspatch;

/**
 * 作者：MarkShuai
 * 时间：2017/12/27 11:01
 * 邮箱：MarkShuai@163.com
 * 意图：增量更新工具类
 * 使用步骤：http://blog.csdn.net/baoolong/article/details/54378806
 */

public class BsPatchUtil {
    /**
     * 合并
     *
     * @param oldfile
     * @param newfile
     * @param patchfile
     */
    public native static void patch(String oldfile, String newfile, String patchfile);

    static {
        System.loadLibrary("bspatch");
    }

}
