package com.KiwiSports.utils;

import com.amap.api.maps.AMap;

import java.util.HashMap;


/**
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:05:32
 * @Description 类描述：属性工具类
 */
public class TrackCacheUtils {


    /**
     * ******************************************************************
     * 常量属性定义**********************************************************
     * ******************************************************************
     */
    private static class SingletonHolder {
        private static final TrackCacheUtils INSTANCE = new TrackCacheUtils();
    }

    private TrackCacheUtils() {
    }

    public static final TrackCacheUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     * 当前记录的坐标列表是否正在缓存db
     */
    public HashMap<String, Boolean> mTrackCacheDBStatusMap = new HashMap<>();

    /**
     * 获取记录的坐标列表是否正在缓存db
     * @param key
     * @return
     */
    public Boolean getTrackCacheDBStatus(String key) {
        boolean mTrackCacheDBStatus = false;
        if (mTrackCacheDBStatusMap != null && mTrackCacheDBStatusMap.containsKey(key)) {
            mTrackCacheDBStatus = mTrackCacheDBStatusMap.get(key);
        }
        return mTrackCacheDBStatus;
    }

    public void updateTrackCacheDBStatus(String key, boolean mTrackCacheDBStatus) {
        if (mTrackCacheDBStatusMap != null) {
            mTrackCacheDBStatusMap.put(key, mTrackCacheDBStatus);
        }
    }
}
