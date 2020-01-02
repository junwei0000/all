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

    public static final String PLAYER_DEFAULT_VIDEO = "play_default_video";
    public static final String VIDEO_EDITER_PATH = "key_video_editer_path"; // 路径的key
    public static final String VIDEO_EDITER_IMPORT = "key_video_editer_import";
    public static final String INTENT_KEY_MULTI_CHOOSE = "multi_video";
    public static final String INTENT_KEY_MULTI_PIC_CHOOSE = "multi_pic";
    public static final String INTENT_KEY_MULTI_PIC_LIST = "pic_list"; // 图片列表


    public static final String TCLIVE_INFO_LIST = "txlive_info_list";
    public static final String TCLIVE_INFO_POSITION = "txlive_info_position";
}
