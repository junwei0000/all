package com.bestdo.bestdoStadiums.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author jun
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "bestdostadium.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE_CITY = "search_city";
	public static final String TABLE_CKEYWORD = "search_keyword";

	/**
	 * 
	 * @param context
	 * @param db_Name
	 *            数据库名称路径 默认data/data/
	 * @param factory
	 * @param version
	 *            版本
	 */
	public DBHelper(Context context) {
		super(context, "/data/data/" + context.getPackageName() + "/databases/" + DBHelper.DB_NAME, null,
				DBHelper.DB_VERSION);
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