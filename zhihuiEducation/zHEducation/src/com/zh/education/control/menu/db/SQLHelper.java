package com.zh.education.control.menu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-5 下午5:28:37
 * @Description 类描述：数据库
 */
public class SQLHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "databases.db";// 数据库名称
	public static final int VERSION = 1;

	public static final String TABLE_CHANNEL = "channell";// 数据表

	public static final String ID = "id";//
	public static final String NAME = "name";
	public static final String ORDERID = "orderId";
	public static final String SELECTED = "selected";
	private Context context;

	public SQLHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 创建数据库后，对数据库的操作
		String sql = "create table if not exists " + TABLE_CHANNEL
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + ID
				+ " INTEGER , " + NAME + " TEXT , " + ORDERID + " INTEGER , "
				+ SELECTED + " SELECTED)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 更改数据库版本的操作
		onCreate(db);
	}

}
