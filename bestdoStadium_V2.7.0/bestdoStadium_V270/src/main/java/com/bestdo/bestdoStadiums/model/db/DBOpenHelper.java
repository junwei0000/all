package com.bestdo.bestdoStadiums.model.db;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.model.SearchCityInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Log;

/**
 * 城市/关键字历史
 * 
 * @author jun
 * 
 */
public class DBOpenHelper {

	private DBHelper openHelper;
	private SQLiteDatabase mSqLiteDatabase;
	private static DBOpenHelper mCityDB;

	/*
	 * 城市表字段
	 */
	private String RowId = "id";
	private String cityId = "cityid";
	private String cityName = "cityname";
	private String cityNameShow = "citynameShow";
	private String cityLongitude = "longitude";
	private String cityLatitude = "latitude";
	private String citySortLetters = "sortLetters";
	private String citySortLettersShow = "sortLettersShow";

	/*
	 * 关键字表字段
	 */
	private String keyWordName = "name";

	public synchronized static DBOpenHelper getInstance(Context context) {
		if (mCityDB == null) {
			mCityDB = new DBOpenHelper(context);
		}
		return mCityDB;
	}

	/**
	 * getWritableDatabase getReadableDatabase
	 * 会判断指定的数据库是否存在,不存在则调SQLiteDatabase.create创建,onCreate只在数据库第一次创建时才执行
	 * 
	 * @param context
	 */
	public DBOpenHelper(Context context) {
		openHelper = new DBHelper(context);
		mSqLiteDatabase = openHelper.getWritableDatabase();
		if (!hasTableSql(DBHelper.TABLE_CITY)) {
			creatTableCitySQL();
		}
		if (!hasTableSql(DBHelper.TABLE_CKEYWORD)) {
			creatTableKeyWordSQL();
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
			mCityDB = null;
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

	// ********************************city_history*********************************************
	/**
	 * city---创建表
	 * 
	 * @return
	 */
	public void creatTableCitySQL() {
		StringBuffer sqlBuffer = new StringBuffer("CREATE TABLE IF NOT EXISTS " + DBHelper.TABLE_CITY + "(");
		sqlBuffer.append(RowId + " INTEGER PRIMARY KEY AUTOINCREMENT,");
		sqlBuffer.append(cityId + " varchar,");
		sqlBuffer.append(cityName + " varchar,");
		sqlBuffer.append(cityNameShow + " varchar,");
		sqlBuffer.append(cityLongitude + " varchar,");
		sqlBuffer.append(cityLatitude + " varchar,");
		sqlBuffer.append(citySortLetters + " varchar,");
		sqlBuffer.append(citySortLettersShow + " varchar");
		sqlBuffer.append(")");
		mSqLiteDatabase.execSQL(sqlBuffer.toString());
	}

	/**
	 * city---添加一条信息
	 * 
	 * @param mInfo
	 * @return
	 */
	public boolean addTableCityInfo(SearchCityInfo mInfo) {
		ContentValues contentValues = new ContentValues();
		String cityid = mInfo.getCityid();
		contentValues.put(cityId, cityid);
		contentValues.put(cityName, mInfo.getCityname());
		contentValues.put(cityNameShow, mInfo.getCitynameShow());
		contentValues.put(cityLongitude, mInfo.getLongitude());
		contentValues.put(cityLatitude, mInfo.getLatitude());
		contentValues.put(citySortLetters, mInfo.getSortLetters());
		contentValues.put(citySortLettersShow, mInfo.getSortLettersShow());
		try {
			if (hasInfo(cityId, cityid, DBHelper.TABLE_CITY)) {
				deleteTableInfo(cityId, cityid, DBHelper.TABLE_CITY);
			}
			long rawid = mSqLiteDatabase.insert(DBHelper.TABLE_CITY, null, contentValues);
			Log.e("----------addTableInfo-------------",
					"" + rawid + "----;cityid=" + cityid + ";;cityname=" + mInfo.getCityname() + ";;getCitynameShow="
							+ mInfo.getCitynameShow() + ";;getSortLetters=" + mInfo.getSortLetters()
							+ ";;sortLettersShow=" + mInfo.getSortLettersShow());
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
	 * 按升序查询 city---得到城市历史列表
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
	public ArrayList<SearchCityInfo> getHistoryCityList() {

		ArrayList<SearchCityInfo> mList = new ArrayList<SearchCityInfo>();
		Cursor cursor = null;
		String orderBy = RowId + " desc";
		// 默认升序
		try {
			cursor = mSqLiteDatabase
					.query(true,
							DBHelper.TABLE_CITY, new String[] { cityId, cityName, cityNameShow, cityLongitude,
									cityLatitude, citySortLetters, citySortLettersShow },
							null, null, null, null, orderBy, "3");
			while (cursor.moveToNext()) {
				String cityid = cursor.getString(cursor.getColumnIndex(cityId));
				String cityname = cursor.getString(cursor.getColumnIndex(cityName));
				String citynameShow = cursor.getString(cursor.getColumnIndex(cityNameShow));
				double longitude = cursor.getDouble(cursor.getColumnIndex(cityLongitude));
				double latitude = cursor.getDouble(cursor.getColumnIndex(cityLatitude));
				String sortLetters = cursor.getString(cursor.getColumnIndex(citySortLetters));
				String sortLettersShow = cursor.getString(cursor.getColumnIndex(citySortLettersShow));
				SearchCityInfo mInfo = new SearchCityInfo();
				mInfo.setCityid(cityid);
				mInfo.setCityname(cityname);
				mInfo.setCitynameShow(citynameShow);
				mInfo.setLongitude(longitude);
				mInfo.setLatitude(latitude);
				mInfo.setSortLetters(sortLetters);
				mInfo.setSortLettersShow(sortLettersShow);
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

	// ********************************keyword*********************************************
	/**
	 * keyword---创建表
	 * 
	 * @return
	 */
	public void creatTableKeyWordSQL() {
		StringBuffer sqlBuffer = new StringBuffer("CREATE TABLE IF NOT EXISTS " + DBHelper.TABLE_CKEYWORD + "(");
		sqlBuffer.append(RowId + " INTEGER PRIMARY KEY AUTOINCREMENT,");
		sqlBuffer.append(keyWordName + " varchar");
		sqlBuffer.append(")");
		mSqLiteDatabase.execSQL(sqlBuffer.toString());
	}

	/**
	 * keyword---添加一条信息
	 * 
	 * @param mInfo
	 * @return
	 */
	public boolean addTableKeyWordInfo(String keyName) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(keyWordName, keyName);
		try {
			if (hasInfo(keyWordName, keyName, DBHelper.TABLE_CKEYWORD)) {
				deleteTableInfo(keyWordName, keyName, DBHelper.TABLE_CKEYWORD);
			}
			long rawid = mSqLiteDatabase.insert(DBHelper.TABLE_CKEYWORD, null, contentValues);
			Log.e("----------addTableKeyWordInfo-------------", "" + rawid + "----;keyWordName=" + keyName);
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
	 * keyword---得到历史列表
	 */
	public ArrayList<StadiumInfo> getKeyWordList() {

		ArrayList<StadiumInfo> mList = new ArrayList<StadiumInfo>();
		Cursor cursor = null;
		String orderBy = RowId + " desc";
		// 默认升序
		try {
			cursor = mSqLiteDatabase.query(true, DBHelper.TABLE_CKEYWORD, new String[] { keyWordName }, null, null,
					null, null, orderBy, "10");
			while (cursor.moveToNext()) {
				String name = cursor.getString(cursor.getColumnIndex(keyWordName));
				StadiumInfo mStadiumInfo = new StadiumInfo();
				mStadiumInfo.setName(name);
				mList.add(mStadiumInfo);
				mStadiumInfo = null;
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
	 * keyword---得到模糊查询的历史列表
	 */
	public ArrayList<StadiumInfo> getKeyWordSameList(String keyword) {

		ArrayList<StadiumInfo> mList = new ArrayList<StadiumInfo>();
		Cursor cursor = null;
		String orderBy = RowId + " desc";
		String selection = keyWordName + " like '%" + keyword + "%'";
		// 默认升序
		try {
			cursor = mSqLiteDatabase.query(true, DBHelper.TABLE_CKEYWORD, new String[] { keyWordName }, selection, null,
					null, null, orderBy, "10");
			while (cursor.moveToNext()) {
				String name = cursor.getString(cursor.getColumnIndex(keyWordName));
				StadiumInfo mStadiumInfo = new StadiumInfo();
				mStadiumInfo.setName(name);
				mList.add(mStadiumInfo);
				mStadiumInfo = null;
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
