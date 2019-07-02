package com.KiwiSports.model;

/**
 * 作者：jun on
 * 时间：2018/5/31 16:41
 * 意图：
 */

public class MacthSpeedInfo {

 int distance;
 long matchSpeedTimestamp;

    public MacthSpeedInfo(int distance, long matchSpeedTimestamp) {
        this.distance = distance;
        this.matchSpeedTimestamp = matchSpeedTimestamp;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public long getMatchSpeedTimestamp() {
        return matchSpeedTimestamp;
    }

    public void setMatchSpeedTimestamp(long matchSpeedTimestamp) {
        this.matchSpeedTimestamp = matchSpeedTimestamp;
    }
}
