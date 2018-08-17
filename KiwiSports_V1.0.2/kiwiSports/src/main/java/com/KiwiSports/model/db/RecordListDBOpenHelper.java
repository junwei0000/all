package com.KiwiSports.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Log;

import com.KiwiSports.model.RecordInfo;

import java.util.ArrayList;

/**
 * 轨迹记录列表-数据表
 *
 * @author jun
 */
public class RecordListDBOpenHelper {

    private BaseDBHelper openHelper;
    private SQLiteDatabase mSqLiteDatabase;


    /**
     * getWritableDatabase getReadableDatabase
     * 会判断指定的数据库是否存在,不存在则调SQLiteDatabase.create创建,onCreate只在数据库第一次创建时才执行
     *
     * @param context
     */
    public RecordListDBOpenHelper(Context context) {
        openHelper = new BaseDBHelper(context);
        mSqLiteDatabase = openHelper.getWritableDatabase();
        if (!hasTableSql(BaseDBHelper.TABLE_RECORDLIST)) {
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
    public boolean hasTableSql(String table_Name) {
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
     * 根据id查询表中是否有该用户数据
     *
     * @param uidValue
     * @return
     */
    public boolean hasRecordListInfo(String uidValue) {
        Cursor cursor = null;
        try {
            String[] columns = {uid};//你要的数据
            String selection = "uid=?";
            String[] selectionArgs = {uidValue};//具体的条件,注意要对应条件字段
            cursor = mSqLiteDatabase.query(BaseDBHelper.TABLE_RECORDLIST, columns, selection, selectionArgs, null, null, null, null);
            while (cursor.moveToNext()) {
                String id_Value = cursor
                        .getString(cursor.getColumnIndex(uid));
                if (!TextUtils.isEmpty(id_Value) && id_Value.equals(uidValue)) {
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
     * 根据id查询表中是否有该条数据
     *
     * @param uidValue
     * @param runStartTimeTampValue
     * @return
     */
    public boolean hasrunStartTimeTampInfo(String uidValue, String runStartTimeTampValue) {
        Cursor cursor = null;
        try {
            String[] columns = {uid, runStartTimeTamp};//你要的数据
            String selection = "uid=? and runStartTimeTamp=?";
            String[] selectionArgs = {uidValue, runStartTimeTampValue};//具体的条件,注意要对应条件字段
            cursor = mSqLiteDatabase.query(BaseDBHelper.TABLE_RECORDLIST, columns, selection, selectionArgs, null, null, null, null);
            while (cursor.moveToNext()) {
                String _runStartTime = cursor.getString(cursor.getColumnIndex(runStartTimeTamp));
                if (!TextUtils.isEmpty(_runStartTime) && _runStartTime.equals(runStartTimeTampValue)) {
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
     * @param runStartTimeTampValue idValue代表的列是字符串类型的，查询语句中的字符串要用引号引上
     * @return
     */
    public boolean deleteTableInfo(String runStartTimeTampValue) {
        long rawid = mSqLiteDatabase.delete(BaseDBHelper.TABLE_RECORDLIST, runStartTimeTamp + "='" + runStartTimeTampValue
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
    private String record_id = "record_id";
    private String uid = "uid";
    public String posid = "posid";
    public String date_time = "date_time";
    public String distanceTraveled = "distanceTraveled";
    public String duration = "duration";
    public String verticalDistance = "verticalDistance";
    public String topSpeed = "topSpeed";
    public String dropTraveled = "dropTraveled";
    public String nSteps = "nSteps";
    public String matchSpeed = "matchSpeed";
    public String maxMatchSpeed = "maxMatchSpeed";
    public String maxSlope = "maxSlope";
    public String maxAltitude = "maxAltitude";
    public String currentAltitude = "currentAltitude";
    public String averageMatchSpeed = "averageMatchSpeed";
    public String averageSpeed = "averageSpeed";
    public String freezeDuration = "freezeDuration";
    public String maxHoverDuration = "maxHoverDuration";

    public String totalHoverDuration = "totalHoverDuration";
    public String lapCount = "lapCount";
    public String wrestlingCount = "wrestlingCount";
    public String cableCarQueuingDuration = "cableCarQueuingDuration";
    public String address = "address";
    public String minAltidue = "minAltidue";
    public String calorie = "calorie";
    public String sportsType = "sportsType";
    public String latitudeOffset = "latitudeOffset";
    public String longitudeOffset = "longitudeOffset";

    public String upHillDistance = "upHillDistance";
    public String downHillDistance = "downHillDistance";
    public String extendedField1 = "extendedField1";
    public String extendedField2 = "extendedField2";
    public String extendedField3 = "extendedField3";
    public String runStartTime = "runStartTime";
    public String runStartTimeTamp = "runStartTimeTamp";

    public String minMatchSpeed = "minMatchSpeed";
    public String status = "status";
    public String create_time = "create_time";
    public String pos_name = "pos_name";
    /**
     * //Coming  OVER   NotUpLoad
     */
    public String currentTrackStatus = "currentTrackStatus";
    /**
     * //Coming  OVER
     */
    public String trackStatus = "trackStatus";

    /**
     * Coming ：正在缓冲的
     */
    public static String currentTrackComing = "Coming";
    /**
     * OVER：已经缓冲完成的并已上传
     */
    public static String currentTrackBOVER = "OVER";
    /**
     * NotUpLoad：已经缓冲完成的，未上传
     */
    public static String currentTrackNotUpLoad = "NotUpLoad";

    /**
     * 创建表
     * INTEGER –整数，对应Java 的byte、short、int 和long。
     * REAL – 小数，对应Java 的float 和double。
     * TEXT – 字串，对应Java 的String。
     *
     * @return
     */
    public void creatTableSQL() {
        StringBuffer sqlBuffer = new StringBuffer("CREATE TABLE IF NOT EXISTS "
                + BaseDBHelper.TABLE_RECORDLIST + "(");
        sqlBuffer.append(RowId + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        sqlBuffer.append(record_id + " varchar,");
        sqlBuffer.append(uid + " varchar,");
        sqlBuffer.append(posid + " varchar,");
        sqlBuffer.append(date_time + " varchar,");
        sqlBuffer.append(distanceTraveled + " REAL,");
        sqlBuffer.append(duration + " INTEGER,");
        sqlBuffer.append(verticalDistance + " varchar,");
        sqlBuffer.append(topSpeed + " varchar,");
        sqlBuffer.append(dropTraveled + " varchar,");
        sqlBuffer.append(nSteps + " varchar,");
        sqlBuffer.append(matchSpeed + " varchar,");
        sqlBuffer.append(maxMatchSpeed + " varchar,");
        sqlBuffer.append(maxSlope + " varchar,");
        sqlBuffer.append(maxAltitude + " varchar,");
        sqlBuffer.append(currentAltitude + " varchar,");
        sqlBuffer.append(averageMatchSpeed + " varchar,");
        sqlBuffer.append(averageSpeed + " varchar,");
        sqlBuffer.append(freezeDuration + " INTEGER,");
        sqlBuffer.append(maxHoverDuration + " varchar,");
        sqlBuffer.append(totalHoverDuration + " varchar,");
        sqlBuffer.append(lapCount + " varchar,");
        sqlBuffer.append(wrestlingCount + " varchar,");
        sqlBuffer.append(cableCarQueuingDuration + " varchar,");
        sqlBuffer.append(address + " varchar,");

        sqlBuffer.append(minAltidue + " varchar,");
        sqlBuffer.append(calorie + " varchar,");
        sqlBuffer.append(sportsType + " varchar,");
        sqlBuffer.append(latitudeOffset + " varchar,");
        sqlBuffer.append(longitudeOffset + " varchar,");
        sqlBuffer.append(upHillDistance + " varchar,");
        sqlBuffer.append(downHillDistance + " varchar,");

        sqlBuffer.append(extendedField1 + " varchar,");
        sqlBuffer.append(extendedField2 + " varchar,");
        sqlBuffer.append(extendedField3 + " varchar,");
        sqlBuffer.append(runStartTime + " varchar,");
        sqlBuffer.append(runStartTimeTamp + " varchar,");
        sqlBuffer.append(minMatchSpeed + " varchar,");
        sqlBuffer.append(create_time + " varchar,");
        sqlBuffer.append(status + " varchar,");
        sqlBuffer.append(currentTrackStatus + " varchar,");
        sqlBuffer.append(trackStatus + " varchar,");
        sqlBuffer.append(pos_name + " varchar");
        sqlBuffer.append(")");
        mSqLiteDatabase.execSQL(sqlBuffer.toString());
    }


    /**
     * 添加一条信息
     *
     * @return
     */
    public boolean addTableInfo(RecordInfo mRecordInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(record_id, mRecordInfo.getRecord_id());
        contentValues.put(uid, mRecordInfo.getUid());
        contentValues.put(posid, mRecordInfo.getPosid());
        contentValues.put(date_time, mRecordInfo.getDate_time());
        contentValues.put(distanceTraveled, mRecordInfo.getDistanceTraveled());
        contentValues.put(duration, mRecordInfo.getDuration());
        contentValues.put(verticalDistance, mRecordInfo.getVerticalDistance());
        contentValues.put(topSpeed, mRecordInfo.getTopSpeed());
        contentValues.put(dropTraveled, mRecordInfo.getDropTraveled());
        contentValues.put(nSteps, mRecordInfo.getnSteps());
        contentValues.put(matchSpeed, mRecordInfo.getMatchSpeed());
        contentValues.put(maxMatchSpeed, mRecordInfo.getMaxMatchSpeed());
        contentValues.put(maxSlope, mRecordInfo.getMaxSlope());
        contentValues.put(maxAltitude, mRecordInfo.getMaxAltitude());
        contentValues.put(currentAltitude, mRecordInfo.getCurrentAltitude());
        contentValues.put(averageMatchSpeed, mRecordInfo.getAverageMatchSpeed());
        contentValues.put(averageSpeed, mRecordInfo.getAverageSpeed());
        contentValues.put(freezeDuration, mRecordInfo.getFreezeDuration());
        contentValues.put(maxHoverDuration, mRecordInfo.getMaxHoverDuration());
        contentValues.put(totalHoverDuration, mRecordInfo.getTotalHoverDuration());
        contentValues.put(lapCount, mRecordInfo.getLapCount());
        contentValues.put(wrestlingCount, mRecordInfo.getWrestlingCount());
        contentValues.put(cableCarQueuingDuration, mRecordInfo.getCableCarQueuingDuration());
        contentValues.put(address, mRecordInfo.getAddress());

        contentValues.put(minAltidue, mRecordInfo.getMinAltidue());
        contentValues.put(calorie, mRecordInfo.getCalorie());
        contentValues.put(sportsType, mRecordInfo.getSportsType());
        contentValues.put(latitudeOffset, mRecordInfo.getLatitudeOffset());
        contentValues.put(longitudeOffset, mRecordInfo.getLongitudeOffset());
        contentValues.put(upHillDistance, mRecordInfo.getUpHillDistance());
        contentValues.put(downHillDistance, mRecordInfo.getDownHillDistance());

        contentValues.put(extendedField1, mRecordInfo.getExtendedField1());
        contentValues.put(extendedField2, mRecordInfo.getExtendedField2());
        contentValues.put(extendedField3, mRecordInfo.getExtendedField3());
        contentValues.put(runStartTime, mRecordInfo.getRunStartTime());
        contentValues.put(runStartTimeTamp, mRecordInfo.getRunStartTimeTamp());

        contentValues.put(minMatchSpeed, mRecordInfo.getMinMatchSpeed());
        contentValues.put(create_time, mRecordInfo.getCreate_time());
        contentValues.put(status, mRecordInfo.getCstatus());
        String currentTrackStatus_ = mRecordInfo.getCurrentTrackStatus();
        contentValues.put(currentTrackStatus, currentTrackStatus_);
        if (currentTrackStatus_.equals(currentTrackComing)) {
            contentValues.put(trackStatus, currentTrackComing);
        } else {
            contentValues.put(trackStatus, currentTrackBOVER);
        }
        contentValues.put(pos_name, mRecordInfo.getPos_name());
        try {
            long rawid = mSqLiteDatabase.insert(BaseDBHelper.TABLE_RECORDLIST,
                    null, contentValues);
            Log.e("___listdb___", "addTableTrackInfo-----" + rawid + "   Record_id "
                    + mRecordInfo.getRecord_id() + "   create_time   " + mRecordInfo.getCreate_time());
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
     * 修改数据
     *
     * @return
     */
    public boolean updateTableInfo(RecordInfo mRecordInfo) {
        try {
            if (hasTableSql(BaseDBHelper.TABLE_RECORDLIST)) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(record_id, mRecordInfo.getRecord_id());
                contentValues.put(uid, mRecordInfo.getUid());
                contentValues.put(posid, mRecordInfo.getPosid());
                contentValues.put(date_time, mRecordInfo.getDate_time());
                contentValues.put(distanceTraveled, mRecordInfo.getDistanceTraveled());
                contentValues.put(duration, mRecordInfo.getDuration());
                contentValues.put(verticalDistance, mRecordInfo.getVerticalDistance());
                contentValues.put(topSpeed, mRecordInfo.getTopSpeed());
                contentValues.put(dropTraveled, mRecordInfo.getDropTraveled());
                contentValues.put(nSteps, mRecordInfo.getnSteps());
                contentValues.put(matchSpeed, mRecordInfo.getMatchSpeed());
                contentValues.put(maxMatchSpeed, mRecordInfo.getMaxMatchSpeed());
                contentValues.put(maxSlope, mRecordInfo.getMaxSlope());
                contentValues.put(maxAltitude, mRecordInfo.getMaxAltitude());
                contentValues.put(currentAltitude, mRecordInfo.getCurrentAltitude());
                contentValues.put(averageMatchSpeed, mRecordInfo.getAverageMatchSpeed());
                contentValues.put(averageSpeed, mRecordInfo.getAverageSpeed());
                contentValues.put(freezeDuration, mRecordInfo.getFreezeDuration());
                contentValues.put(maxHoverDuration, mRecordInfo.getMaxHoverDuration());
                contentValues.put(totalHoverDuration, mRecordInfo.getTotalHoverDuration());
                contentValues.put(lapCount, mRecordInfo.getLapCount());
                contentValues.put(wrestlingCount, mRecordInfo.getWrestlingCount());
                contentValues.put(cableCarQueuingDuration, mRecordInfo.getCableCarQueuingDuration());
                contentValues.put(address, mRecordInfo.getAddress());

                contentValues.put(minAltidue, mRecordInfo.getMinAltidue());
                contentValues.put(calorie, mRecordInfo.getCalorie());
                contentValues.put(sportsType, mRecordInfo.getSportsType());
                contentValues.put(latitudeOffset, mRecordInfo.getLatitudeOffset());
                contentValues.put(longitudeOffset, mRecordInfo.getLongitudeOffset());
                contentValues.put(upHillDistance, mRecordInfo.getUpHillDistance());
                contentValues.put(downHillDistance, mRecordInfo.getDownHillDistance());

                contentValues.put(extendedField1, mRecordInfo.getExtendedField1());
                contentValues.put(extendedField2, mRecordInfo.getExtendedField2());
                contentValues.put(extendedField3, mRecordInfo.getExtendedField3());
                contentValues.put(runStartTime, mRecordInfo.getRunStartTime());
                contentValues.put(runStartTimeTamp, mRecordInfo.getRunStartTimeTamp());
                contentValues.put(minMatchSpeed, mRecordInfo.getMinMatchSpeed());
                contentValues.put(create_time, mRecordInfo.getCreate_time());
                contentValues.put(status, mRecordInfo.getCstatus());
                String currentTrackStatus_ = mRecordInfo.getCurrentTrackStatus();
                contentValues.put(currentTrackStatus, currentTrackStatus_);
                if (currentTrackStatus_.equals(currentTrackComing)) {
                    contentValues.put(trackStatus, currentTrackComing);
                } else {
                    contentValues.put(trackStatus, currentTrackBOVER);
                }
                contentValues.put(pos_name, mRecordInfo.getPos_name());
                mSqLiteDatabase.update(BaseDBHelper.TABLE_RECORDLIST, contentValues, runStartTimeTamp + " = ?", new String[]{mRecordInfo.getRunStartTimeTamp()});

//                mSqLiteDatabase.execSQL("update " + BaseDBHelper.TABLE_RECORDLIST
//                                + " set " + iKey + "=? where uidyearmonth=?",
//                        new Object[]{recordDatas_, uidyearmonth_});
                Log.e("___listdb___", "updateTableInfo-----record_id=" + mRecordInfo.getRecord_id());
            }
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取当前用户的distanceTraveled 总数
     *
     * @param uidvalue
     * @return
     */
    private double getDistanceSum(String uidvalue) {
        Cursor cursor = null;
        try {
            String sql = "SELECT sum(distanceTraveled) FROM " + BaseDBHelper.TABLE_RECORDLIST + " WHERE uid = ?";
            cursor = mSqLiteDatabase.rawQuery(sql, new String[]{uidvalue});
            if (cursor != null)
                cursor.moveToFirst();
            return cursor.getDouble(0) / 1000;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (cursor != null)
                cursor.close();
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
    public ArrayList<RecordInfo> getHistoryDBList(
            String uidValue, String trackStatusValue, int startIndex, int page_size) {

        ArrayList<RecordInfo> mList = null;
        Cursor cursor = null;
        // 默认升序
        try {
            if (mSqLiteDatabase == null) {
                return mList;
            }
            String[] columns = {record_id, uid, posid, date_time, distanceTraveled, duration, verticalDistance
                    , topSpeed, dropTraveled, nSteps, matchSpeed, maxMatchSpeed, maxSlope, maxAltitude
                    , currentAltitude, averageMatchSpeed, averageSpeed, freezeDuration, maxHoverDuration, totalHoverDuration
                    , lapCount, wrestlingCount, cableCarQueuingDuration, address, minAltidue, calorie, sportsType, latitudeOffset
                    , longitudeOffset, upHillDistance, downHillDistance, extendedField1, extendedField2, extendedField3
                    , runStartTime, runStartTimeTamp, minMatchSpeed, create_time, status, currentTrackStatus, pos_name};//你要的数据
            String selection = "uid=? and trackStatus=?";
            String[] selectionArgs = {uidValue, trackStatusValue};//具体的条件,注意要对应条件字段
            String orderBy = " runStartTimeTamp desc";
            //"0,20",第1行开始,返回20行数据
            String limit = startIndex + "," + page_size;
            Log.e("___listdb___", "------------limit------------"
                    + limit);
            cursor = mSqLiteDatabase.query(BaseDBHelper.TABLE_RECORDLIST, columns, selection, selectionArgs
                    , null, null, orderBy, limit);
            if (cursor != null) {
                mList = new ArrayList<RecordInfo>();
                while (cursor.moveToNext()) {
                    String _record_id = cursor.getString(cursor.getColumnIndex(record_id));
                    String _uid = cursor.getString(cursor.getColumnIndex(uid));
                    String _posid = cursor.getString(cursor.getColumnIndex(posid));
                    String _date_time = cursor.getString(cursor.getColumnIndex(date_time));
                    double _distanceTraveled = cursor.getDouble(cursor.getColumnIndex(distanceTraveled));
                    long _duration = cursor.getLong(cursor.getColumnIndex(duration));
                    String _verticalDistance = cursor.getString(cursor.getColumnIndex(verticalDistance));

                    String _topSpeed = cursor.getString(cursor.getColumnIndex(topSpeed));
                    String _dropTraveled = cursor.getString(cursor.getColumnIndex(dropTraveled));
                    String _nSteps = cursor.getString(cursor.getColumnIndex(nSteps));
                    String _matchSpeed = cursor.getString(cursor.getColumnIndex(matchSpeed));
                    String _maxMatchSpeed = cursor.getString(cursor.getColumnIndex(maxMatchSpeed));
                    String _maxSlope = cursor.getString(cursor.getColumnIndex(maxSlope));
                    String _maxAltitude = cursor.getString(cursor.getColumnIndex(maxAltitude));
                    String _currentAltitude = cursor.getString(cursor.getColumnIndex(currentAltitude));

                    String _averageMatchSpeed = cursor.getString(cursor.getColumnIndex(averageMatchSpeed));
                    String _averageSpeed = cursor.getString(cursor.getColumnIndex(averageSpeed));
                    long _freezeDuration = cursor.getLong(cursor.getColumnIndex(freezeDuration));
                    String _maxHoverDuration = cursor.getString(cursor.getColumnIndex(maxHoverDuration));
                    String _totalHoverDuration = cursor.getString(cursor.getColumnIndex(totalHoverDuration));
                    String _lapCount = cursor.getString(cursor.getColumnIndex(lapCount));
                    String _wrestlingCount = cursor.getString(cursor.getColumnIndex(wrestlingCount));
                    String _cableCarQueuingDuration = cursor.getString(cursor.getColumnIndex(cableCarQueuingDuration));
                    String _address = cursor.getString(cursor.getColumnIndex(address));
                    String _minAltidue = cursor.getString(cursor.getColumnIndex(minAltidue));
                    String _calorie = cursor.getString(cursor.getColumnIndex(calorie));
                    String _sportsType = cursor.getString(cursor.getColumnIndex(sportsType));
                    String _latitudeOffset = cursor.getString(cursor.getColumnIndex(latitudeOffset));
                    String _longitudeOffset = cursor.getString(cursor.getColumnIndex(longitudeOffset));
                    String _upHillDistance = cursor.getString(cursor.getColumnIndex(upHillDistance));
                    String _downHillDistance = cursor.getString(cursor.getColumnIndex(downHillDistance));
                    String _extendedField1 = cursor.getString(cursor.getColumnIndex(extendedField1));
                    String _extendedField2 = cursor.getString(cursor.getColumnIndex(extendedField2));
                    String _extendedField3 = cursor.getString(cursor.getColumnIndex(extendedField3));
                    String _runStartTime = cursor.getString(cursor.getColumnIndex(runStartTime));
                    String _runStartTimeTamp = cursor.getString(cursor.getColumnIndex(runStartTimeTamp));
                    String _minMatchSpeed = cursor.getString(cursor.getColumnIndex(minMatchSpeed));
                    String _create_time = cursor.getString(cursor.getColumnIndex(create_time));
                    String _status = cursor.getString(cursor.getColumnIndex(status));
                    String _currentTrackStatus = cursor.getString(cursor.getColumnIndex(currentTrackStatus));
                    String _pos_name = cursor.getString(cursor.getColumnIndex(pos_name));
                    RecordInfo mRecordInfo = new RecordInfo(_record_id, _uid,
                            _posid, _date_time, _distanceTraveled, _duration,
                            _verticalDistance, _topSpeed, _dropTraveled, _nSteps,
                            _matchSpeed, _maxMatchSpeed, _maxSlope, _maxAltitude,
                            _currentAltitude, _averageMatchSpeed, _averageSpeed,
                            _freezeDuration, _maxHoverDuration,
                            _totalHoverDuration, _lapCount, _wrestlingCount,
                            _cableCarQueuingDuration, _address, _minAltidue,
                            _calorie, _sportsType, _latitudeOffset,
                            _longitudeOffset, _upHillDistance, _downHillDistance,
                            _extendedField1, _extendedField2, _extendedField3,
                            _create_time, _status, _pos_name, _runStartTime,
                            _runStartTimeTamp, _minMatchSpeed, _currentTrackStatus);
                    mList.add(mRecordInfo);
                    mRecordInfo = null;
                    Log.e("___listdb___", "------------getHistoryTrackList------------"
                            + _record_id + "   create_time   " + _create_time);
                }
                Log.e("___listdb___", "------------getHistoryTrackList------------"
                        + mList.size());
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return mList;

    }

    /**
     * 根据轨迹状态获取数据
     *
     * @param uidValue
     * @param currentTrackStatusValue
     * @return
     */
    public ArrayList<RecordInfo> getTrackDBList(
            String uidValue, String currentTrackStatusValue) {

        ArrayList<RecordInfo> mList = null;
        Cursor cursor = null;
        // 默认升序
        try {
            if (mSqLiteDatabase == null) {
                return mList;
            }
            String[] columns = {record_id, uid, posid, date_time, distanceTraveled, duration, verticalDistance
                    , topSpeed, dropTraveled, nSteps, matchSpeed, maxMatchSpeed, maxSlope, maxAltitude
                    , currentAltitude, averageMatchSpeed, averageSpeed, freezeDuration, maxHoverDuration, totalHoverDuration
                    , lapCount, wrestlingCount, cableCarQueuingDuration, address, minAltidue, calorie, sportsType, latitudeOffset
                    , longitudeOffset, upHillDistance, downHillDistance, extendedField1, extendedField2, extendedField3
                    , runStartTime, runStartTimeTamp, minMatchSpeed, create_time, status, currentTrackStatus, pos_name};//你要的数据
            String selection = "uid=? and currentTrackStatus=?";
            String[] selectionArgs = {uidValue, currentTrackStatusValue};//具体的条件,注意要对应条件字段
            String orderBy = RowId + " desc";
            cursor = mSqLiteDatabase.query(BaseDBHelper.TABLE_RECORDLIST, columns, selection, selectionArgs
                    , null, null, orderBy, null);
            if (cursor != null) {
                mList = new ArrayList<RecordInfo>();
                while (cursor.moveToNext()) {
                    String _record_id = cursor.getString(cursor.getColumnIndex(record_id));
                    String _uid = cursor.getString(cursor.getColumnIndex(uid));
                    String _posid = cursor.getString(cursor.getColumnIndex(posid));
                    String _date_time = cursor.getString(cursor.getColumnIndex(date_time));
                    double _distanceTraveled = cursor.getDouble(cursor.getColumnIndex(distanceTraveled));
                    long _duration = cursor.getLong(cursor.getColumnIndex(duration));
                    String _verticalDistance = cursor.getString(cursor.getColumnIndex(verticalDistance));

                    String _topSpeed = cursor.getString(cursor.getColumnIndex(topSpeed));
                    String _dropTraveled = cursor.getString(cursor.getColumnIndex(dropTraveled));
                    String _nSteps = cursor.getString(cursor.getColumnIndex(nSteps));
                    String _matchSpeed = cursor.getString(cursor.getColumnIndex(matchSpeed));
                    String _maxMatchSpeed = cursor.getString(cursor.getColumnIndex(maxMatchSpeed));
                    String _maxSlope = cursor.getString(cursor.getColumnIndex(maxSlope));
                    String _maxAltitude = cursor.getString(cursor.getColumnIndex(maxAltitude));
                    String _currentAltitude = cursor.getString(cursor.getColumnIndex(currentAltitude));

                    String _averageMatchSpeed = cursor.getString(cursor.getColumnIndex(averageMatchSpeed));
                    String _averageSpeed = cursor.getString(cursor.getColumnIndex(averageSpeed));
                    long _freezeDuration = cursor.getLong(cursor.getColumnIndex(freezeDuration));
                    String _maxHoverDuration = cursor.getString(cursor.getColumnIndex(maxHoverDuration));
                    String _totalHoverDuration = cursor.getString(cursor.getColumnIndex(totalHoverDuration));
                    String _lapCount = cursor.getString(cursor.getColumnIndex(lapCount));
                    String _wrestlingCount = cursor.getString(cursor.getColumnIndex(wrestlingCount));
                    String _cableCarQueuingDuration = cursor.getString(cursor.getColumnIndex(cableCarQueuingDuration));
                    String _address = cursor.getString(cursor.getColumnIndex(address));
                    String _minAltidue = cursor.getString(cursor.getColumnIndex(minAltidue));
                    String _calorie = cursor.getString(cursor.getColumnIndex(calorie));
                    String _sportsType = cursor.getString(cursor.getColumnIndex(sportsType));
                    String _latitudeOffset = cursor.getString(cursor.getColumnIndex(latitudeOffset));
                    String _longitudeOffset = cursor.getString(cursor.getColumnIndex(longitudeOffset));
                    String _upHillDistance = cursor.getString(cursor.getColumnIndex(upHillDistance));
                    String _downHillDistance = cursor.getString(cursor.getColumnIndex(downHillDistance));
                    String _extendedField1 = cursor.getString(cursor.getColumnIndex(extendedField1));
                    String _extendedField2 = cursor.getString(cursor.getColumnIndex(extendedField2));
                    String _extendedField3 = cursor.getString(cursor.getColumnIndex(extendedField3));
                    String _runStartTime = cursor.getString(cursor.getColumnIndex(runStartTime));
                    String _runStartTimeTamp = cursor.getString(cursor.getColumnIndex(runStartTimeTamp));
                    String _minMatchSpeed = cursor.getString(cursor.getColumnIndex(minMatchSpeed));
                    String _create_time = cursor.getString(cursor.getColumnIndex(create_time));
                    String _status = cursor.getString(cursor.getColumnIndex(status));
                    String _currentTrackStatus = cursor.getString(cursor.getColumnIndex(currentTrackStatus));
                    String _pos_name = cursor.getString(cursor.getColumnIndex(pos_name));
                    RecordInfo mRecordInfo = new RecordInfo(_record_id, _uid,
                            _posid, _date_time, _distanceTraveled, _duration,
                            _verticalDistance, _topSpeed, _dropTraveled, _nSteps,
                            _matchSpeed, _maxMatchSpeed, _maxSlope, _maxAltitude,
                            _currentAltitude, _averageMatchSpeed, _averageSpeed,
                            _freezeDuration, _maxHoverDuration,
                            _totalHoverDuration, _lapCount, _wrestlingCount,
                            _cableCarQueuingDuration, _address, _minAltidue,
                            _calorie, _sportsType, _latitudeOffset,
                            _longitudeOffset, _upHillDistance, _downHillDistance,
                            _extendedField1, _extendedField2, _extendedField3,
                            _create_time, _status, _pos_name, _runStartTime,
                            _runStartTimeTamp, _minMatchSpeed, _currentTrackStatus);
                    mList.add(mRecordInfo);
                    mRecordInfo = null;
                    Log.e("___listdb___", "------------getHistoryTrackList------------"
                            + _record_id + "   create_time   " + _create_time);
                }
                Log.e("___listdb___", "------------getHistoryTrackList------------"
                        + mList.size());
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return mList;

    }

    /**
     * 获取一条数据
     *
     * @param uidValue
     * @param runStartTimeValue
     * @return
     */
    public RecordInfo getRecordInfoByDB(
            String uidValue, String runStartTimeValue) {

        RecordInfo mRecordInfo = null;
        Cursor cursor = null;
        // 默认升序
        try {
            if (mSqLiteDatabase == null) {
                return mRecordInfo;
            }
            String[] columns = {record_id, uid, posid, date_time, distanceTraveled, duration, verticalDistance
                    , topSpeed, dropTraveled, nSteps, matchSpeed, maxMatchSpeed, maxSlope, maxAltitude
                    , currentAltitude, averageMatchSpeed, averageSpeed, freezeDuration, maxHoverDuration, totalHoverDuration
                    , lapCount, wrestlingCount, cableCarQueuingDuration, address, minAltidue, calorie, sportsType, latitudeOffset
                    , longitudeOffset, upHillDistance, downHillDistance, extendedField1, extendedField2, extendedField3
                    , runStartTime, runStartTimeTamp, minMatchSpeed, create_time, status, currentTrackStatus, pos_name};//你要的数据
            String selection = "uid=? and runStartTime=?";
            String[] selectionArgs = {uidValue, runStartTimeValue};//具体的条件,注意要对应条件字段
            String orderBy = RowId + " desc";
            cursor = mSqLiteDatabase.query(BaseDBHelper.TABLE_RECORDLIST, columns, selection, selectionArgs
                    , null, null, orderBy, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String _record_id = cursor.getString(cursor.getColumnIndex(record_id));
                    String _uid = cursor.getString(cursor.getColumnIndex(uid));
                    String _posid = cursor.getString(cursor.getColumnIndex(posid));
                    String _date_time = cursor.getString(cursor.getColumnIndex(date_time));
                    double _distanceTraveled = cursor.getDouble(cursor.getColumnIndex(distanceTraveled));
                    long _duration = cursor.getLong(cursor.getColumnIndex(duration));
                    String _verticalDistance = cursor.getString(cursor.getColumnIndex(verticalDistance));

                    String _topSpeed = cursor.getString(cursor.getColumnIndex(topSpeed));
                    String _dropTraveled = cursor.getString(cursor.getColumnIndex(dropTraveled));
                    String _nSteps = cursor.getString(cursor.getColumnIndex(nSteps));
                    String _matchSpeed = cursor.getString(cursor.getColumnIndex(matchSpeed));
                    String _maxMatchSpeed = cursor.getString(cursor.getColumnIndex(maxMatchSpeed));
                    String _maxSlope = cursor.getString(cursor.getColumnIndex(maxSlope));
                    String _maxAltitude = cursor.getString(cursor.getColumnIndex(maxAltitude));
                    String _currentAltitude = cursor.getString(cursor.getColumnIndex(currentAltitude));

                    String _averageMatchSpeed = cursor.getString(cursor.getColumnIndex(averageMatchSpeed));
                    String _averageSpeed = cursor.getString(cursor.getColumnIndex(averageSpeed));
                    long _freezeDuration = cursor.getLong(cursor.getColumnIndex(freezeDuration));
                    String _maxHoverDuration = cursor.getString(cursor.getColumnIndex(maxHoverDuration));
                    String _totalHoverDuration = cursor.getString(cursor.getColumnIndex(totalHoverDuration));
                    String _lapCount = cursor.getString(cursor.getColumnIndex(lapCount));
                    String _wrestlingCount = cursor.getString(cursor.getColumnIndex(wrestlingCount));
                    String _cableCarQueuingDuration = cursor.getString(cursor.getColumnIndex(cableCarQueuingDuration));
                    String _address = cursor.getString(cursor.getColumnIndex(address));
                    String _minAltidue = cursor.getString(cursor.getColumnIndex(minAltidue));
                    String _calorie = cursor.getString(cursor.getColumnIndex(calorie));
                    String _sportsType = cursor.getString(cursor.getColumnIndex(sportsType));
                    String _latitudeOffset = cursor.getString(cursor.getColumnIndex(latitudeOffset));
                    String _longitudeOffset = cursor.getString(cursor.getColumnIndex(longitudeOffset));
                    String _upHillDistance = cursor.getString(cursor.getColumnIndex(upHillDistance));
                    String _downHillDistance = cursor.getString(cursor.getColumnIndex(downHillDistance));
                    String _extendedField1 = cursor.getString(cursor.getColumnIndex(extendedField1));
                    String _extendedField2 = cursor.getString(cursor.getColumnIndex(extendedField2));
                    String _extendedField3 = cursor.getString(cursor.getColumnIndex(extendedField3));
                    String _runStartTime = cursor.getString(cursor.getColumnIndex(runStartTime));
                    String _runStartTimeTamp = cursor.getString(cursor.getColumnIndex(runStartTimeTamp));
                    String _minMatchSpeed = cursor.getString(cursor.getColumnIndex(minMatchSpeed));
                    String _create_time = cursor.getString(cursor.getColumnIndex(create_time));
                    String _status = cursor.getString(cursor.getColumnIndex(status));
                    String _currentTrackStatus = cursor.getString(cursor.getColumnIndex(currentTrackStatus));
                    String _pos_name = cursor.getString(cursor.getColumnIndex(pos_name));
                    mRecordInfo = new RecordInfo(_record_id, _uid,
                            _posid, _date_time, _distanceTraveled, _duration,
                            _verticalDistance, _topSpeed, _dropTraveled, _nSteps,
                            _matchSpeed, _maxMatchSpeed, _maxSlope, _maxAltitude,
                            _currentAltitude, _averageMatchSpeed, _averageSpeed,
                            _freezeDuration, _maxHoverDuration,
                            _totalHoverDuration, _lapCount, _wrestlingCount,
                            _cableCarQueuingDuration, _address, _minAltidue,
                            _calorie, _sportsType, _latitudeOffset,
                            _longitudeOffset, _upHillDistance, _downHillDistance,
                            _extendedField1, _extendedField2, _extendedField3,
                            _create_time, _status, _pos_name, _runStartTime,
                            _runStartTimeTamp, _minMatchSpeed, _currentTrackStatus);
                    Log.e("___listdb___", "------------getHistoryTrackList------------"
                            + _record_id + "   create_time   " + _create_time);
                }
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return mRecordInfo;

    }

    public RecordInfo getRecordInfoByDB(String record_idValue) {

        RecordInfo mRecordInfo = null;
        Cursor cursor = null;
        // 默认升序
        try {
            if (mSqLiteDatabase == null) {
                return mRecordInfo;
            }
            String[] columns = {runStartTime, distanceTraveled};//你要的数据
            String selection = "record_id=?";
            String[] selectionArgs = {record_idValue};//具体的条件,注意要对应条件字段
            String orderBy = RowId + " desc";
            cursor = mSqLiteDatabase.query(BaseDBHelper.TABLE_RECORDLIST, columns, selection, selectionArgs
                    , null, null, orderBy, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String _runStartTime = cursor.getString(cursor.getColumnIndex(runStartTime));
                    double _distanceTraveled = cursor.getDouble(cursor.getColumnIndex(distanceTraveled));
                    mRecordInfo = new RecordInfo();
                    mRecordInfo.setRunStartTime(_runStartTime);
                    mRecordInfo.setDistanceTraveled(_distanceTraveled);
                }
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return mRecordInfo;

    }

    /**
     * 获取当前用户下列表数据个数
     *
     * @param uidValue
     * @return
     */
    public int getSize(String uidValue, String trackStatusValue) {
        int size = 0;
        Cursor cursor = null;
        // 默认升序
        try {
            if (mSqLiteDatabase == null) {
                return 0;
            }
            String[] columns = {record_id, uid, posid, date_time, distanceTraveled, duration, verticalDistance
                    , topSpeed, dropTraveled, nSteps, matchSpeed, maxMatchSpeed, maxSlope, maxAltitude
                    , currentAltitude, averageMatchSpeed, averageSpeed, freezeDuration, maxHoverDuration, totalHoverDuration
                    , lapCount, wrestlingCount, cableCarQueuingDuration, address, minAltidue, calorie, sportsType, latitudeOffset
                    , longitudeOffset, upHillDistance, downHillDistance, extendedField1, extendedField2, extendedField3
                    , runStartTime, runStartTimeTamp, minMatchSpeed, create_time, status, currentTrackStatus, pos_name};//你要的数据
            String selection = "uid=? and trackStatus=?";
            String[] selectionArgs = {uidValue, trackStatusValue};//具体的条件,注意要对应条件字段
            cursor = mSqLiteDatabase.query(BaseDBHelper.TABLE_RECORDLIST, columns, selection, selectionArgs
                    , null, null, null, null);
            if (cursor != null) {
                size = cursor.getCount();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return size;

    }
}
