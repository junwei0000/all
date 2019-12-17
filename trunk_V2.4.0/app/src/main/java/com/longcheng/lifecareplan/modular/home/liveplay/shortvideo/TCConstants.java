package com.longcheng.lifecareplan.modular.home.liveplay.shortvideo;

/**
 * 静态函数
 */
public class TCConstants {

    /**
     * UGC小视频录制信息
     */
    public static final String VIDEO_RECORD_TYPE = "type";
    public static final String VIDEO_RECORD_RESULT = "result";
    public static final String VIDEO_RECORD_DESCMSG = "descmsg";
    public static final String VIDEO_RECORD_VIDEPATH = "path";
    public static final String VIDEO_RECORD_COVERPATH = "coverpath";
    public static final String VIDEO_RECORD_DURATION = "duration";
    public static final String VIDEO_RECORD_RESOLUTION = "resolution";


    public static final int VIDEO_RECORD_TYPE_PUBLISH = 1;   // 推流端录制
    public static final int VIDEO_RECORD_TYPE_PLAY = 2;   // 播放端录制
    public static final int VIDEO_RECORD_TYPE_UGC_RECORD = 3;   // 短视频录制
    public static final int VIDEO_RECORD_TYPE_EDIT = 4;   // 短视频编辑
}
