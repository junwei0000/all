package com.zh.education.control.menu.db;

import java.util.List;
import java.util.Map;

import com.zh.education.model.ChannelItemInfo;

import android.content.ContentValues;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-5 下午5:29:12
 * @Description 类描述：数据库接口
 */
public interface ChannelDaoInface {
	public boolean addCache(ChannelItemInfo item);

	public boolean deleteCache(String whereClause, String[] whereArgs);

	public boolean updateCache(ContentValues values, String whereClause,
			String[] whereArgs);

	public Map<String, String> viewCache(String selection,
			String[] selectionArgs);

	public List<Map<String, String>> listCache(String selection,
			String[] selectionArgs);

	public void clearFeedTable();
}
