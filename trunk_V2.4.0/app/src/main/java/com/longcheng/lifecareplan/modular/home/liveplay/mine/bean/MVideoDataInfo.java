package com.longcheng.lifecareplan.modular.home.liveplay.mine.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2019/10/29 15:12
 * 意图：
 */
public class MVideoDataInfo implements Serializable {
    @SerializedName(value = "shortVideoList", alternate = {"liveRoomList", "userFollowList"})
    private ArrayList<MVideoItemInfo> shortVideoList;

    public ArrayList<MVideoItemInfo> getShortVideoList() {
        return shortVideoList;
    }

    public void setShortVideoList(ArrayList<MVideoItemInfo> shortVideoList) {
        this.shortVideoList = shortVideoList;
    }
}
