package com.KiwiSports.control.locationService;


import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/6/20 15:20
 * 意图：
 */

public class GradientHelper {
    private int[] colorList = {0xFFFBE01C, 0xFFE1E618, 0xFF7DFF00, 0xffDE2C00};// 颜色值
    private HashMap<Integer, Integer> agrSpeerColorHashMap;

    private List<Integer> trackColorList = new ArrayList<Integer>();

    public List<Integer> getTrackColorList() {
        return trackColorList;
    }

    public GradientHelper() {
        super();
        getAgrSpeerColorHashMap();
    }

    private void getAgrSpeerColorHashMap() {
        colorList[0] = Color.rgb(255, 0, 0);
        colorList[1] = Color.rgb(200, 200, 0);
        colorList[2] = Color.rgb(200, 200, 0);
        colorList[3] = Color.rgb(0, 255, 0);

        agrSpeerColorHashMap = new HashMap<Integer, Integer>();
        agrSpeerColorHashMap.put(5, colorList[0]);
        agrSpeerColorHashMap.put(6, colorList[1]);
        agrSpeerColorHashMap.put(8, colorList[2]);
        agrSpeerColorHashMap.put(9, colorList[3]);
    }

    /**
     * 当前坐标速度获取色值
     *
     * @param speed
     */
    public int speedColor(double speed) {
        int speedColor;
        if (speed < 6) {
            speedColor = 5;
        } else if (speed < 7 && speed >= 6) {
            speedColor = 6;
        } else if (speed < 9 && speed >= 7) {
            speedColor = 8;
        } else {
            speedColor = 9;
        }
        int color;
        if (agrSpeerColorHashMap.containsKey(speedColor)) {
            color = agrSpeerColorHashMap.get(speedColor);
        } else {
            color = colorList[1];
        }
        trackColorList.add(trackColorList.size(), color);
        return color;
    }


}
