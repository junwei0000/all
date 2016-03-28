package com.zh.education.utils;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2015-12-31 下午5:07:29
 * @Description 类描述：属性工具类
 */
public class Constans {
	private static Constans mConstans;

	public synchronized static Constans getInstance() {
		if (mConstans == null) {
			mConstans = new Constans();
		}
		return mConstans;
	}

	/**
	 * 软引用
	 */
	public String textSizeSharedPrefsKey = "zhedu_spftextsize";
	public String bestDoInfoSharedPrefsKey = "zhedu_spf";
	public boolean refreshOrLoadMoreLoading = false;
	public static final String WEB_ROOT = "http://60.28.182.24:9080";// 测试
	public static final String LOGIN = WEB_ROOT + "/appApi/Login";// 登录
	public static final String CLASSSCHEDULE = WEB_ROOT
			+ "/appApi/GetCourseSchedules";// 课表
	public static final String NOTICES = WEB_ROOT + "/appApi/GetNotices";// 通知消息
	public static final String BOKE = WEB_ROOT + "/appApi/GetPosts";// 博客
	public static final String EVENTS = WEB_ROOT + "/appApi/GetCalendar";// 日程
	public static final String GETCOMMENTS = WEB_ROOT + "/appApi/GetComments";// 博客
	public static final String POSTCOMMENT = WEB_ROOT + "/appApi/PostComment";// 发评论
	public static final String FILE_ROOT = "http://file.zh.com/f/";

}
