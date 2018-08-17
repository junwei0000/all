package com.KiwiSports.control.view;

import com.KiwiSports.control.locationService.LocationForegoundService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 作者：jun on
 * 时间：2018/4/23 15:57
 * 意图：
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LocationForegoundService.class);
        context.startService(i);
    }
}