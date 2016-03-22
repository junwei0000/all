package com.zh.education.control.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zh.education.control.menu.db.ChannelDao;
import com.zh.education.control.menu.db.SQLHelper;
import com.zh.education.model.ChannelItemInfo;

import android.database.SQLException;
import android.util.Log;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-5 下午5:26:54
 * @Description 类描述：栏目默认管理
 */
public class ChannelManage {
	public static ChannelManage channelManage;
	/**
	 * 默认的用户选择频道列表
	 * */
	public static List<ChannelItemInfo> defaultUserChannels;
	/**
	 * 默认的其他频道列表
	 * */
	public static List<ChannelItemInfo> defaultOtherChannels;
	private ChannelDao channelDao;
	/** 判断数据库中是否存在用户数据 */
	private boolean userExist = false;
	static {
		defaultUserChannels = new ArrayList<ChannelItemInfo>();
		defaultOtherChannels = new ArrayList<ChannelItemInfo>();

		defaultUserChannels.add(new ChannelItemInfo(1, "课表", 1, 1));
		defaultUserChannels.add(new ChannelItemInfo(2, "博客", 2, 1));
		defaultUserChannels.add(new ChannelItemInfo(3, "消息", 3, 1));
		defaultUserChannels.add(new ChannelItemInfo(4, "日程", 4, 1));
		defaultUserChannels.add(new ChannelItemInfo(5, "云盘", 5, 1));

		defaultOtherChannels.add(new ChannelItemInfo(1, "栏目1", 1, 0));
		defaultOtherChannels.add(new ChannelItemInfo(2, "栏目2", 2, 0));
		defaultOtherChannels.add(new ChannelItemInfo(3, "栏目3", 3, 0));
		defaultOtherChannels.add(new ChannelItemInfo(4, "栏目4", 4, 0));
		defaultOtherChannels.add(new ChannelItemInfo(5, "栏目5", 5, 0));
		defaultOtherChannels.add(new ChannelItemInfo(6, "栏目6", 6, 0));
		defaultOtherChannels.add(new ChannelItemInfo(7, "栏目7", 7, 0));
		defaultOtherChannels.add(new ChannelItemInfo(8, "栏目8", 8, 0));

	}

	private ChannelManage(SQLHelper paramDBHelper) throws SQLException {
		if (channelDao == null)
			channelDao = new ChannelDao(paramDBHelper.getContext());
		// NavigateItemDao(paramDBHelper.getDao(NavigateItem.class));
		return;
	}

	/**
	 * 初始化频道管理类
	 * 
	 * @param paramDBHelper
	 * @throws SQLException
	 */
	public static ChannelManage getManage(SQLHelper dbHelper)
			throws SQLException {
		if (channelManage == null)
			channelManage = new ChannelManage(dbHelper);
		return channelManage;
	}

	/**
	 * 清除所有的频道
	 */
	public void deleteAllChannel() {
		channelDao.clearFeedTable();
	}

	/**
	 * 获取其他的频道
	 * 
	 * @return 数据库存在用户配置 ? 数据库内的用户选择频道 : 默认用户选择频道 ;
	 */
	public List<ChannelItemInfo> getUserChannel() {
		Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?",
				new String[] { "1" });
		if (cacheList != null && !((List) cacheList).isEmpty()) {
			userExist = true;
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			List<ChannelItemInfo> list = new ArrayList<ChannelItemInfo>();
			for (int i = 0; i < count; i++) {
				ChannelItemInfo navigate = new ChannelItemInfo();
				navigate.setId(Integer
						.valueOf(maplist.get(i).get(SQLHelper.ID)));
				navigate.setName(maplist.get(i).get(SQLHelper.NAME));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(
						SQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(
						SQLHelper.SELECTED)));
				list.add(navigate);
			}
			return list;
		}
		initDefaultChannel();
		return defaultUserChannels;
	}

	/**
	 * 获取其他的频道
	 * 
	 * @return 数据库存在用户配置 ? 数据库内的其它频道 : 默认其它频道 ;
	 */
	public List<ChannelItemInfo> getOtherChannel() {
		Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?",
				new String[] { "0" });
		List<ChannelItemInfo> list = new ArrayList<ChannelItemInfo>();
		if (cacheList != null && !((List) cacheList).isEmpty()) {
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			for (int i = 0; i < count; i++) {
				ChannelItemInfo navigate = new ChannelItemInfo();
				navigate.setId(Integer
						.valueOf(maplist.get(i).get(SQLHelper.ID)));
				navigate.setName(maplist.get(i).get(SQLHelper.NAME));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(
						SQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(
						SQLHelper.SELECTED)));
				list.add(navigate);
			}
			return list;
		}
		if (userExist) {
			return list;
		}
		cacheList = defaultOtherChannels;
		return (List<ChannelItemInfo>) cacheList;
	}

	/**
	 * 保存用户频道到数据库
	 * 
	 * @param userList
	 */
	public void saveUserChannel(List<ChannelItemInfo> userList) {
		for (int i = 0; i < userList.size(); i++) {
			ChannelItemInfo ChannelItemInfo = (ChannelItemInfo) userList.get(i);
			ChannelItemInfo.setOrderId(i);
			ChannelItemInfo.setSelected(Integer.valueOf(1));
			channelDao.addCache(ChannelItemInfo);
		}
	}

	/**
	 * 保存其他频道到数据库
	 * 
	 * @param otherList
	 */
	public void saveOtherChannel(List<ChannelItemInfo> otherList) {
		for (int i = 0; i < otherList.size(); i++) {
			ChannelItemInfo ChannelItemInfo = (ChannelItemInfo) otherList
					.get(i);
			ChannelItemInfo.setOrderId(i);
			ChannelItemInfo.setSelected(Integer.valueOf(0));
			channelDao.addCache(ChannelItemInfo);
		}
	}

	/**
	 * 初始化数据库内的频道数据
	 */
	private void initDefaultChannel() {
		Log.d("deleteAll", "deleteAll");
		deleteAllChannel();
		saveUserChannel(defaultUserChannels);
		saveOtherChannel(defaultOtherChannels);
	}
}
