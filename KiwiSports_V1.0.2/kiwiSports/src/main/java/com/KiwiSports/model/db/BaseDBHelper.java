package com.KiwiSports.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author jun
 */
public class BaseDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "kiwi.db";
    public static final int DB_VERSION = 3;
//	/**
//	 * 未上传成功的轨迹缓存-数据表
//	 */
//	public static final String TABLE_TRACK = "table_track";
    /**
     * 日历运动-数据表
     */
    public static final String TABLE_RECORDCALENDER = "table_recordCalender";
    /**
     * 记录列表-数据表
     */
    public static final String TABLE_RECORDLIST = "table_recordListss";
    /**
     * 所有轨迹坐标点-数据表
     */
    public static final String TABLE_TRACKLIST = "table_trackLists";

    /**
     * @param context
     */
    public BaseDBHelper(Context context) {
        super(context, "/data/data/" + context.getPackageName() + "/databases/"
                + BaseDBHelper.DB_NAME, null, BaseDBHelper.DB_VERSION);
    }

    /**
     * 创建数据库时调用
     */
    @Override
    public void onCreate(SQLiteDatabase _db) {
    }

    /**
     * 版本更新时调用
     */
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
//        if (2 == _oldVersion) {
//            String sql = "alter table " + TABLE_RECORDLIST + " add "
//                    + "trackStatus" + " varchar";
//            _db.execSQL(sql);
//            Log.e("db", "onUpgrade");
//        }
    }

    /**
     * 创建或打开一个只读数据库
     */
    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    /**
     * 创建或打开一个读写数据库
     */
    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

}