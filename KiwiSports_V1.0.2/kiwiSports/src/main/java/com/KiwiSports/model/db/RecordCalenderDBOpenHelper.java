package com.KiwiSports.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

/**
 * 轨迹数据无网络时
 * 
 * @author jun
 * 
 */
public class RecordCalenderDBOpenHelper {

	private BaseDBHelper openHelper;
	private SQLiteDatabase mSqLiteDatabase;


	/**
	 * getWritableDatabase getReadableDatabase
	 * 会判断指定的数据库是否存在,不存在则调SQLiteDatabase.create创建,onCreate只在数据库第一次创建时才执行
	 *
	 * @param context
	 */
	public RecordCalenderDBOpenHelper(Context context) {
		openHelper = new BaseDBHelper(context);
		mSqLiteDatabase = openHelper.getWritableDatabase();
		if (!hasTableSql(BaseDBHelper.TABLE_RECORDCALENDER)) {
			creatTableCalenderSQL();
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
	 * @param idKey
	 *            --"cityid";
	 * @param idValue
	 *            --cityid;
	 * @return
	 */
	public boolean hasInfo(String idKey, String idValue, String table_name) {
		Cursor cursor = null;
		try {
			cursor = mSqLiteDatabase.query(true, table_name,
					new String[] { idKey }, idKey + "='" + idValue + "'", null,
					null, null, null, null);
			while (cursor.moveToNext()) {
				String id_Value = cursor
						.getString(cursor.getColumnIndex(idKey));
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
	public boolean deleteTableInfo(String idKey, String idValue,
			String table_name) {
		long rawid = mSqLiteDatabase.delete(table_name, idKey + "='" + idValue
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
	private String uid  = "uid";
	public String uidyearmonth = "uidyearmonth";
	public String recordDatas = "recordDatas";

	/**
	 * 创建表
	 * 
	 * @return
	 */
	public void creatTableCalenderSQL() {
		StringBuffer sqlBuffer = new StringBuffer("CREATE TABLE IF NOT EXISTS "
				+ BaseDBHelper.TABLE_RECORDCALENDER + "(");
		sqlBuffer.append(RowId + " INTEGER PRIMARY KEY AUTOINCREMENT,");
		sqlBuffer.append(uid + " varchar,");
		sqlBuffer.append(uidyearmonth + " varchar,");
		sqlBuffer.append(recordDatas + " varchar");
		sqlBuffer.append(")");
		mSqLiteDatabase.execSQL(sqlBuffer.toString());
	}

	/**
	 * 修改数据
	 * @return
	 */
	public boolean updateTableCalenderInfo(String recordDatas_,
			String uidyearmonth_) {
		String iKey="recordDatas";
		try {
			if (hasTableSql(BaseDBHelper.TABLE_RECORDCALENDER)) {
				mSqLiteDatabase.execSQL("update " + BaseDBHelper.TABLE_RECORDCALENDER
						+ " set " + iKey + "=? where uidyearmonth=?",
						new Object[] { recordDatas_, uidyearmonth_ });
				Log.e("___db_____", "updateTableInfo-----" + iKey);
			}
			return true;
		} catch (SQLiteException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 添加一条信息
	 *
	 * @return
	 */
	public boolean addTableCalenderInfo(String uid_ ,String uidyearmonth_ , String recordDatas_) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(uid, uid_);
		contentValues.put(uidyearmonth, uidyearmonth_);
		contentValues.put(recordDatas, recordDatas_);
		try {
			long rawid = mSqLiteDatabase.insert(BaseDBHelper.TABLE_RECORDCALENDER,
					null, contentValues);
			Log.e("___db_____", "addTableTrackInfo-----" + rawid + "    "
					+ contentValues.toString());
			contentValues.clear();
			contentValues=null;
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
	 *         orderBy, limit)方法各参数的含义：
	 *         table：表名。相当于select语句from关键字后面的部分。如果是多表联合查询，可以用逗号将两个表名分开。
	 *         columns：要查询出来的列名。相当于select语句select关键字后面的部分。
	 *         selection：查询条件子句，相当于select语句where关键字后面的部分，在条件子句允许使用占位符“?”
	 *         selectionArgs
	 *         ：对应于selection语句中占位符的值，值在数组中的位置与占位符在语句中的位置必须一致，否则就会有异常。
	 *         groupBy：相当于select语句group by关键字后面的部分
	 *         having：相当于select语句having关键字后面的部分 orderBy：相当于select语句order
	 *         by关键字后面的部分，如：personid desc, age asc;desc-降序 asc-升序
	 *         limit：指定偏移量和获取的记录数，相当于select语句limit关键字后面的部分。
	 */
	public ArrayList<String> getHistoryDBList(
			String uidvalue) {

		ArrayList<String> mList = null;
		Cursor cursor = null;
		String orderBy = RowId + " desc";
		// 默认升序
		try {
			if(mSqLiteDatabase==null){
				return mList;
			}
			cursor = mSqLiteDatabase.query(true, BaseDBHelper.TABLE_RECORDCALENDER,
					new String[] {uid, uidyearmonth, recordDatas},
					uid + "=?",
					new String[] { uidvalue }, null, null,
					orderBy, "6");
			if (cursor != null) {
				mList = new ArrayList<String>();
				while (cursor.moveToNext()) {
					String uid_ = cursor.getString(cursor.getColumnIndex(uid));
					String uidyearmonth_ = cursor.getString(cursor
							.getColumnIndex(uidyearmonth));
					String recordDatas_ = cursor.getString(cursor
							.getColumnIndex(recordDatas));
					mList.add(recordDatas_);
				}
				Log.e("___db_____", "------------getHistoryList------------"
						+ mList);
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
