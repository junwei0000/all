package com.KiwiSports.model.db;

import java.util.ArrayList;

import com.KiwiSports.model.TrackSaveInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Log;

/**
 * 轨迹数据无网络时
 * 
 * @author jun
 * 
 */
public class TrackDBOpenHelper {

	private TrackDBHelper openHelper;
	private SQLiteDatabase mSqLiteDatabase;
	// private static TrackDBOpenHelper mDB;
	//
	// public synchronized static TrackDBOpenHelper getInstance(Context context)
	// {
	// if (mDB == null) {
	// mDB = new TrackDBOpenHelper(context);
	// }
	// return mDB;
	// }

	/**
	 * getWritableDatabase getReadableDatabase
	 * 会判断指定的数据库是否存在,不存在则调SQLiteDatabase.create创建,onCreate只在数据库第一次创建时才执行
	 * 
	 * @param context
	 */
	public TrackDBOpenHelper(Context context) {
		openHelper = new TrackDBHelper(context);
		mSqLiteDatabase = openHelper.getWritableDatabase();
		if (!hasTableSql(TrackDBHelper.TABLE_TRACK)) {
			creatTableTrackSQL();
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
			String sql_table = "select count(*) as c from Sqlite_master    where type='table' and name ='" + table_Name
					+ "'";
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

	/**
	 * 根据id查询表中是否有该条数据
	 * 
	 * @param idKey
	 *            --"cityid";
	 * @param idValue
	 *            --cityid;
	 * @return
	 */
	public boolean hasInfo(String idKey, String idValue, String table_name) {
		Cursor cursor = null;
		try {
			Log.e("hasInfo------",
					idKey + "    " + idValue + "    " + table_name + "  mSqLiteDatabase=" + mSqLiteDatabase);
			cursor = mSqLiteDatabase.query(true, table_name, new String[] { idKey }, idKey + "='" + idValue + "'", null,
					null, null, null, null);
			while (cursor.moveToNext()) {
				String id_Value = cursor.getString(cursor.getColumnIndex(idKey));
				if (!TextUtils.isEmpty(id_Value) && id_Value.equals(idValue)) {
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
	 * @param idKey
	 * @param idValue
	 *            idValue代表的列是字符串类型的，查询语句中的字符串要用引号引上
	 * @return
	 */
	public boolean deleteTableInfo(String idKey, String idValue, String table_name) {
		long rawid = mSqLiteDatabase.delete(table_name, idKey + "='" + idValue + "'", null);
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

	// ********************************track_history*********************************************

	/*
	 * 表字段
	 */
	private String RowId = "id";
	private String uid = "uid";
	private String token = "token";
	private String access_token = "access_token";
	private String recordDatas = "recordDatas";

	/**
	 * 创建表
	 * 
	 * @return
	 */
	public void creatTableTrackSQL() {
		StringBuffer sqlBuffer = new StringBuffer("CREATE TABLE IF NOT EXISTS " + TrackDBHelper.TABLE_TRACK + "(");
		sqlBuffer.append(RowId + " INTEGER PRIMARY KEY AUTOINCREMENT,");
		sqlBuffer.append(uid + " varchar,");
		sqlBuffer.append(token + " varchar,");
		sqlBuffer.append(access_token + " varchar,");
		sqlBuffer.append(recordDatas + " varchar");
		sqlBuffer.append(")");
		mSqLiteDatabase.execSQL(sqlBuffer.toString());
	}

	/**
	 * 添加一条信息
	 * 
	 * @param mInfo
	 * @return
	 */
	public boolean addTableTrackInfo(String uid_, String token_, String access_token_, String recordDatas_) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(uid, uid_);
		contentValues.put(token, token_);
		contentValues.put(access_token, access_token_);
		contentValues.put(recordDatas, recordDatas_);
		try {
			long rawid = mSqLiteDatabase.insert(TrackDBHelper.TABLE_TRACK, null, contentValues);
			Log.e("----------addTableInfo-------------", contentValues.toString());
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
	 * 按升序查询 得到城市历史列表
	 * 
	 * @return query(table, columns, selection, selectionArgs, groupBy, having,
	 *         orderBy, limit)方法各参数的含义：
	 *         table：表名。相当于select语句from关键字后面的部分。如果是多表联合查询，可以用逗号将两个表名分开。
	 *         columns：要查询出来的列名。相当于select语句select关键字后面的部分。
	 *         selection：查询条件子句，相当于select语句where关键字后面的部分，在条件子句允许使用占位符“?”
	 *         selectionArgs
	 *         ：对应于selection语句中占位符的值，值在数组中的位置与占位符在语句中的位置必须一致，否则就会有异常。
	 *         groupBy：相当于select语句group by关键字后面的部分
	 *         having：相当于select语句having关键字后面的部分 orderBy：相当于select语句order
	 *         by关键字后面的部分，如：personid desc, age asc;
	 *         limit：指定偏移量和获取的记录数，相当于select语句limit关键字后面的部分。
	 */
	public ArrayList<TrackSaveInfo> getHistoryTrackList() {

		ArrayList<TrackSaveInfo> mList = new ArrayList<TrackSaveInfo>();
		Cursor cursor = null;
		String orderBy = RowId + " desc";
		// 默认升序
		try {
			cursor = mSqLiteDatabase.query(true, TrackDBHelper.TABLE_TRACK,
					new String[] { uid, token, access_token, recordDatas }, null, null, null, null, orderBy, "20");
			while (cursor.moveToNext()) {
				String uid_ = cursor.getString(cursor.getColumnIndex(uid));
				String token_ = cursor.getString(cursor.getColumnIndex(token));
				String access_token_ = cursor.getString(cursor.getColumnIndex(access_token));
				String recordDatas_ = cursor.getString(cursor.getColumnIndex(recordDatas));
				TrackSaveInfo mInfo = new TrackSaveInfo(uid_, token_, access_token_, recordDatas_);
				mList.add(mInfo);
			}
		} catch (SQLiteException e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return mList;

	}

}
