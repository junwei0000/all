package com.KiwiSports.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Log;

import com.KiwiSports.model.MacthSpeedInfo;
import com.KiwiSports.model.MainLocationItemInfo;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.utils.PriceUtils;
import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 所有轨迹坐标点-数据表
 *
 * @author jun
 */
public class TrackListDBOpenHelper {

    private BaseDBHelper openHelper;
    private SQLiteDatabase mSqLiteDatabase;


    /**
     * getWritableDatabase getReadableDatabase
     * 会判断指定的数据库是否存在,不存在则调SQLiteDatabase.create创建,onCreate只在数据库第一次创建时才执行
     *
     * @param context
     */
    public TrackListDBOpenHelper(Context context) {
        openHelper = new BaseDBHelper(context);
        mSqLiteDatabase = openHelper.getWritableDatabase();
        if (!hasTableSql(BaseDBHelper.TABLE_TRACKLIST)) {
            creatTableSQL();
        }
    }

    /**
     * 获得数据库
     *
     * @return
     */
    public SQLiteDatabase getSqliteDatebase() {
        return mSqLiteDatabase;
    }

    /**
     * 关闭数据库
     */
    public void close() {
        if (mSqLiteDatabase != null) {
            mSqLiteDatabase.close();
            mSqLiteDatabase = null;
            openHelper = null;
            // mDB = null;
        }
    }

    /**
     * 查看数据库中是否存在某表
     *
     * @param table_Name
     * @return
     */
    private boolean hasTableSql(String table_Name) {
        boolean result = false;
        Cursor cur = null;
        try {
            String sql_table = "select count(*) as c from Sqlite_master    where type='table' and name ='"
                    + table_Name + "'";
            cur = mSqLiteDatabase.rawQuery(sql_table, null);
            if (cur.moveToNext()) {
                int count = cur.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
            cur.close();
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    /**
     * 从数据库中删除表
     *
     * @param table_Name
     * @return
     */
    public boolean dropTableSql(String table_Name) {
        try {
            if (hasTableSql(table_Name)) {
                mSqLiteDatabase.execSQL("DROP TABLE " + table_Name);
            }
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ************************************************************************************

    /**
     * 根据id查询表中是否有该条数据
     *
     * @param uidValue
     * @param runStartTimeValue
     * @return
     */
    public boolean hasInfo(String uidValue, String runStartTimeValue) {
        Cursor cursor = null;
        try {
            String[] columns = {uid, runStartTime};//你要的数据
            String selection = "uid=? and runStartTime=?";
            String[] selectionArgs = {uidValue, runStartTimeValue};//具体的条件,注意要对应条件字段
            cursor = mSqLiteDatabase.query(true, BaseDBHelper.TABLE_TRACKLIST,
                    columns, selection, selectionArgs,
                    null, null, null, null);
            while (cursor.moveToNext()) {
                String id_Value = cursor
                        .getString(cursor.getColumnIndex(runStartTime));
                if (!TextUtils.isEmpty(id_Value) && id_Value.equals(runStartTimeValue)) {
                    return true;
                }
            }
            return false;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null)
                cursor.close();
        }

    }

    public boolean hasInfo(String uidValue, String runStartTimeValue, String durationValue) {
        Cursor cursor = null;
        try {
            String[] columns = {uid, durationStr};//你要的数据
            String selection = "uid=? and durationStr=?";
            String[] selectionArgs = {uidValue, runStartTimeValue + "_" + durationValue};//具体的条件,注意要对应条件字段
            cursor = mSqLiteDatabase.query(true, BaseDBHelper.TABLE_TRACKLIST,
                    columns, selection, selectionArgs,
                    null, null, null, null);
            while (cursor.moveToNext()) {
                String _durationStr = cursor.getString(cursor.getColumnIndex(durationStr));
                if (!TextUtils.isEmpty(_durationStr) && _durationStr.equals(durationValue)) {
                    return true;
                }
            }
            return false;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null)
                cursor.close();
        }

    }

    /**
     * 根据id删除表中一条数据
     *
     * @param idValue idValue代表的列是字符串类型的，查询语句中的字符串要用引号引上
     * @return
     */
    public boolean deleteTableInfo(String idValue) {
        long rawid = mSqLiteDatabase.delete(BaseDBHelper.TABLE_TRACKLIST, runStartTime + "='" + idValue
                + "'", null);
        if (rawid > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除表中所有数据
     *
     * @param table_Name
     * @return
     */
    public boolean deleteTableAllInfo(String table_Name) {
        try {
            if (hasTableSql(table_Name)) {
                mSqLiteDatabase.execSQL("delete from " + table_Name);
            }
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取当前的增长的主键ID
     *
     * @param table_Name
     * @return
     */
    public int getRowId(String table_Name) {
        String sql = "select last_insert_rowid() from " + table_Name;
        Cursor cursor = mSqLiteDatabase.rawQuery(sql, null);
        int a = -1;
        if (cursor.moveToFirst()) {
            a = cursor.getInt(0);
        }
        return a;
    }

    // ******************************* ***************************************

    /*
     * 表字段
     */
    private String RowId = "id";
    private String uid = "uid";
    private String runStartTime = "runStartTime";
    private String record_id = "record_id";
    private String longitude = "longitude";
    private String latitude = "latitude";
    private String speed = "speed";
    private String altitude = "altitude";
    private String accuracy = "accuracy";
    private String nStatus = "nStatus";
    private String nLapPoint = "nLapPoint";
    private String nLapTime = "nLapTime";
    private String duration = "duration";
    private String durationStr = "durationStr";//标记当前用户该条轨迹当前运动时间坐标是否缓存
    private String distance = "distance";
    private String latitudeOffset = "latitudeOffset";
    private String longitudeOffset = "longitudeOffset";
    private String latLngDashedStatus = "latLngDashedStatus";

    /**
     * 创建表
     *
     * @return
     */
    public void creatTableSQL() {
        StringBuffer sqlBuffer = new StringBuffer("CREATE TABLE IF NOT EXISTS "
                + BaseDBHelper.TABLE_TRACKLIST + "(");
        sqlBuffer.append(RowId + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        sqlBuffer.append(uid + " varchar,");
        sqlBuffer.append(runStartTime + " varchar,");
        sqlBuffer.append(record_id + " varchar,");
        sqlBuffer.append(longitude + " REAL,");
        sqlBuffer.append(latitude + " REAL,");
        sqlBuffer.append(speed + " REAL,");
        sqlBuffer.append(altitude + " INTEGER,");
        sqlBuffer.append(accuracy + " INTEGER,");
        sqlBuffer.append(nStatus + " INTEGER,");
        sqlBuffer.append(nLapPoint + " INTEGER,");
        sqlBuffer.append(nLapTime + " varchar,");
        sqlBuffer.append(duration + " INTEGER,");
        sqlBuffer.append(durationStr + " varchar,");
        sqlBuffer.append(distance + " REAL,");
        sqlBuffer.append(latitudeOffset + " varchar,");
        sqlBuffer.append(longitudeOffset + " varchar,");
        sqlBuffer.append(latLngDashedStatus + " varchar");
        sqlBuffer.append(")");
        mSqLiteDatabase.execSQL(sqlBuffer.toString());
    }

    /**
     * 添加一条信息
     *
     * @return
     */
    public boolean addTableInfo(String uid_, String runStartTime_, MainLocationItemInfo mInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(uid, uid_);
        contentValues.put(runStartTime, runStartTime_);
        contentValues.put(record_id, "");
        contentValues.put(longitude, mInfo.getLongitude());
        contentValues.put(latitude, mInfo.getLatitude());
        contentValues.put(speed, mInfo.getSpeed());
        contentValues.put(altitude, mInfo.getAltitude());
        contentValues.put(accuracy, mInfo.getAccuracy());
        contentValues.put(nStatus, mInfo.getnStatus());
        contentValues.put(nLapPoint, mInfo.getnLapPoint());
        contentValues.put(nLapTime, mInfo.getnLapTime());
        contentValues.put(duration, mInfo.getDuration());
        contentValues.put(durationStr, runStartTime_ + "_" + String.valueOf(mInfo.getDuration()));
        contentValues.put(distance, mInfo.getDistance());
        contentValues.put(latitudeOffset, mInfo.getLatitudeOffset());
        contentValues.put(longitudeOffset, mInfo.getLongitudeOffset());
        contentValues.put(latLngDashedStatus, mInfo.getLatLngDashedStatus());
        try {
            long rawid = mSqLiteDatabase.insert(BaseDBHelper.TABLE_TRACKLIST,
                    null, contentValues);
            Log.e("recordDetailDatas_", "recordDatas_-----" + mInfo.getDistance() + "    ");
            contentValues.clear();
            contentValues = null;
            if (rawid > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 按升序查询 得到v列表
     *
     * @return query(table, columns, selection, selectionArgs, groupBy, having,
     *orderBy, limit)方法各参数的含义：
     * table：表名。相当于select语句from关键字后面的部分。如果是多表联合查询，可以用逗号将两个表名分开。
     * columns：要查询出来的列名。相当于select语句select关键字后面的部分。
     * selection：查询条件子句，相当于select语句where关键字后面的部分，在条件子句允许使用占位符“?”
     * selectionArgs
     * ：对应于selection语句中占位符的值，值在数组中的位置与占位符在语句中的位置必须一致，否则就会有异常。
     * groupBy：相当于select语句group by关键字后面的部分
     * having：相当于select语句having关键字后面的部分 orderBy：相当于select语句order
     * by关键字后面的部分，如：personid desc, age asc;
     * limit：指定偏移量和获取的记录数，相当于select语句limit关键字后面的部分。
     */
    public HashMap<String, Object> getHistoryDBList(String uidValue, String runStartTimeValue) {
        HashMap<String, Object> mHashMap = null;
        int beforedistance = 0;
        long beforeDuration = 0;
        long maxmatchSpeedTimestamp = 0;
        double topSpeed=0.0;
        Cursor cursor = null;
        // 默认升序
        try {
            if (mSqLiteDatabase == null) {
                return mHashMap;
            }
            String[] columns = {uid, runStartTime, record_id, longitude, latitude
                    , speed, altitude, accuracy, nStatus, nLapPoint
                    , nLapTime, duration, distance, latitudeOffset, longitudeOffset
                    , latLngDashedStatus};//你要的数据
            String selection = "uid=? and runStartTime=?";
            String[] selectionArgs = {uidValue, runStartTimeValue};//具体的条件,注意要对应条件字段
            String orderBy = RowId + " asc";
            cursor = mSqLiteDatabase.query(BaseDBHelper.TABLE_TRACKLIST, columns, selection, selectionArgs
                    , null, null, orderBy, null);
            if (cursor != null) {
                mHashMap = new HashMap<String, Object>();
                ArrayList<MacthSpeedInfo> allmatchSpeedList = new ArrayList<MacthSpeedInfo>();
                ArrayList<MainLocationItemInfo> allpointList = new ArrayList<MainLocationItemInfo>();
                while (cursor.moveToNext()) {
                    String _uid = cursor.getString(cursor.getColumnIndex(uid));
                    String _runStartTime = cursor.getString(cursor.getColumnIndex(runStartTime));
                    String _record_id = cursor.getString(cursor.getColumnIndex(record_id));
                    double _longitude = cursor.getDouble(cursor.getColumnIndex(longitude));
                    double _latitude = cursor.getDouble(cursor.getColumnIndex(latitude));
                    double _speed = cursor.getDouble(cursor.getColumnIndex(speed));
                    if(_speed>topSpeed){
                        topSpeed=_speed;
                    }
                    int _altitude = cursor.getInt(cursor.getColumnIndex(altitude));
                    int _accuracy = cursor.getInt(cursor.getColumnIndex(accuracy));

                    int _nStatus = cursor.getInt(cursor.getColumnIndex(nStatus));
                    int _nLapPoint = cursor.getInt(cursor.getColumnIndex(nLapPoint));
                    String _nLapTime = cursor.getString(cursor.getColumnIndex(nLapTime));
                    long _duration = cursor.getLong(cursor.getColumnIndex(duration));
                    double _distance = cursor.getDouble(cursor.getColumnIndex(distance));
                    String _latitudeOffset = cursor.getString(cursor.getColumnIndex(latitudeOffset));
                    String _longitudeOffset = cursor.getString(cursor.getColumnIndex(longitudeOffset));
                    String _latLngDashedStatus = cursor.getString(cursor.getColumnIndex(latLngDashedStatus));
                    long matchSpeedTimestamp = 0;
                    if (_distance - beforedistance >= 1000) {
                        matchSpeedTimestamp = _duration - beforeDuration;
                        if (maxmatchSpeedTimestamp < matchSpeedTimestamp) {
                            maxmatchSpeedTimestamp = matchSpeedTimestamp;
                        }
                        beforeDuration = _duration;
                        beforedistance = (int) _distance;
                        MacthSpeedInfo minfo = new MacthSpeedInfo((int) _distance / 1000, matchSpeedTimestamp);
                        allmatchSpeedList.add(minfo);
                        minfo = null;
                    }

                    //isAfterLast指向查询结果的最后一条记录
                    if (cursor.isLast()) {
                        if (beforedistance != (int) _distance && _distance > 0) {
                            matchSpeedTimestamp = _duration - beforeDuration;
                            if (maxmatchSpeedTimestamp < matchSpeedTimestamp) {
                                maxmatchSpeedTimestamp = matchSpeedTimestamp;
                            }
                            MacthSpeedInfo minfo = new MacthSpeedInfo((int) ((_distance / 1000 + 1)), matchSpeedTimestamp);
                            allmatchSpeedList.add(minfo);
                            minfo = null;
                        }
                    }
                    Log.e("recordDetailDatas_", "_distance-----" + _distance + "    ");

                    LatLng mLatLng = new LatLng(_latitude, _longitude);
                    MainLocationItemInfo mMainLocationItemInfo = new MainLocationItemInfo(_latitude, _longitude,
                            _speed, _altitude, _accuracy, _nStatus,
                            _nLapPoint, _nLapTime, _latLngDashedStatus, _duration, _distance,
                            _latitudeOffset, _longitudeOffset, mLatLng);
                    allpointList.add(mMainLocationItemInfo);
                    mMainLocationItemInfo = null;
                }
                mHashMap.put("maxmatchSpeedTimestamp", maxmatchSpeedTimestamp);
                mHashMap.put("allmatchSpeedList", allmatchSpeedList);
                mHashMap.put("allpointList", allpointList);
                mHashMap.put("topSpeed", PriceUtils.getInstance().getPriceTwoDecimal(
                        Double.valueOf(topSpeed), 2));
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return mHashMap;

    }

    /**
     * 获取当前最后一个坐标点的距离
     *
     * @param uidValue
     * @param runStartTimeValue
     * @return
     */
    public Double getHistoryDBLastDis(String uidValue, String runStartTimeValue) {
        Double lastdistance = 0.0;
        Cursor cursor = null;
        // 默认升序
        try {
            if (mSqLiteDatabase == null) {
                return lastdistance;
            }
            String[] columns = {uid, runStartTime, record_id, longitude, latitude
                    , speed, altitude, accuracy, nStatus, nLapPoint
                    , nLapTime, duration, distance, latitudeOffset, longitudeOffset
                    , latLngDashedStatus};//你要的数据
            String selection = "uid=? and runStartTime=?";
            String[] selectionArgs = {uidValue, runStartTimeValue};//具体的条件,注意要对应条件字段
            String orderBy = RowId + " desc";
            cursor = mSqLiteDatabase.query(BaseDBHelper.TABLE_TRACKLIST, columns, selection, selectionArgs
                    , null, null, orderBy, "1");
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    lastdistance = cursor.getDouble(cursor.getColumnIndex(distance));
                }
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return lastdistance;

    }

    /**
     * 防止正在缓存时退出中断，获取当前缓存进度
     *
     * @param uidValue
     * @param runStartTimeValue
     * @return
     */
    public int getHistoryDBSum(String uidValue, String runStartTimeValue) {
        int sum = 0;
        Cursor cursor = null;
        // 默认升序
        try {
            if (mSqLiteDatabase == null) {
                return sum;
            }
            String[] columns = {uid, runStartTime, distance};//你要的数据
            String selection = "uid=? and runStartTime=?";
            String[] selectionArgs = {uidValue, runStartTimeValue};//具体的条件,注意要对应条件字段
            String orderBy = RowId + " asc";
            cursor = mSqLiteDatabase.query(BaseDBHelper.TABLE_TRACKLIST, columns, selection, selectionArgs
                    , null, null, orderBy, null);
            if (cursor != null) {
                sum = cursor.getCount();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return sum;

    }
}
