package com.longcheng.lifecareplan.modular.home.liveplay.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2019/10/29 15:12
 * 意图：
 */
public class VideoDataInfo implements Serializable {
    private ArrayList<VideoItemInfo> liveRoomList;

    public ArrayList<VideoItemInfo> getLiveRoomList() {
        return liveRoomList;
    }

    public void setLiveRoomList(ArrayList<VideoItemInfo> liveRoomList) {
        this.liveRoomList = liveRoomList;
    }
}
