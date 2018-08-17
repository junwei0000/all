/**
 * 
 */
package com.bestdo.bestdoStadiums.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-9-12 上午11:29:14
 * @Description 类描述：跳转评分市场
 */
public class ScoreUtils {

	private static class SingletonHolder {
		private static final ScoreUtils INSTANCE = new ScoreUtils();
	}

	private ScoreUtils() {
	}

	public static final ScoreUtils getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * 获取已安装应用商店的包名列表
	 * 
	 * @param context
	 * @return
	 */
	private ArrayList<String> InstalledAPPs(Context context) {
		ArrayList<String> pkgs = new ArrayList<String>();
		if (context == null)
			return pkgs;
		Intent intent = new Intent();
		intent.setAction("android.intent.action.MAIN");
		// #google play
		intent.addCategory("android.intent.category.APP_MARKET");
		PackageManager pm = context.getPackageManager();
		/*
		 * 通过查询，获得所有ResolveInfo对象. flags： MATCH_DEFAULT_ONLY
		 * ：Category必须带有CATEGORY_DEFAULT的Activity，才匹配 GET_INTENT_FILTERS
		 * ：匹配Intent条件即可 GET_RESOLVED_FILTER ：匹配Intent条件即可
		 */
		List<ResolveInfo> infos = pm.queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS);
		// 调用系统排序 ， 根据name排序
		// 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
		Collections.sort(infos, new ResolveInfo.DisplayNameComparator(pm));

		if (infos == null || infos.size() == 0)
			return pkgs;
		int size = infos.size();
		for (int i = 0; i < size; i++) {
			String pkgName = "";
			try {
				ActivityInfo activityInfo = infos.get(i).activityInfo;
				pkgName = activityInfo.packageName;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!TextUtils.isEmpty(pkgName))
				pkgs.add(pkgName);

		}
		return pkgs;
	}

	/**
	 * 过滤出已经安装的包名集合
	 * 
	 * @param context
	 * @param pkgs
	 *            待过滤包名集合
	 * @return 已安装的包名集合
	 */
	private ArrayList<String> SelectedInstalledAPPs(Context context, ArrayList<String> pkgs) {
		ArrayList<String> empty = new ArrayList<String>();
		if (context == null || pkgs == null || pkgs.size() == 0)
			return empty;
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> installedPkgs = pm.getInstalledPackages(0);
		int li = installedPkgs.size();
		int lj = pkgs.size();
		for (int j = 0; j < lj; j++) {
			for (int i = 0; i < li; i++) {
				String installPkg = "";
				String checkPkg = pkgs.get(j);
				try {
					installPkg = installedPkgs.get(i).applicationInfo.packageName;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (TextUtils.isEmpty(installPkg))
					continue;
				if (installPkg.equals(checkPkg)) {
					empty.add(installPkg);
					break;
				}

			}
		}
		return empty;
	}

	/**
	 * 跳转到app详情界面
	 * 
	 * @param appPkg
	 *            App的包名
	 * @param marketPkg
	 *            应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
	 */
	private void launchAppDetail(Activity context, String appPkg, String marketPkg) {
		try {
			if (TextUtils.isEmpty(appPkg))
				return;
			Uri uri = Uri.parse("market://details?id=" + appPkg);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			if (!TextUtils.isEmpty(marketPkg))
				intent.setPackage(marketPkg);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 判断市场是否存在的方法
	public boolean isAvilible(Context context, String marketPkg) {
		PackageManager packageManager = context.getPackageManager();// 获取packagemanager
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
		List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
		// 从pinfo中将包名字逐一取出，压入pName list中
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				pName.add(pn);
			}
		}
		return pName.contains(marketPkg);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
	}

	/**
	 * user
	 */
	public boolean haveUserApp(Activity context) {
		boolean status = true;
		ArrayList<String> pkgsList = InstalledAPPs(context);
		ArrayList<String> pkgsSelectedList = SelectedInstalledAPPs(context, pkgsList);
		if ((pkgsSelectedList != null && pkgsSelectedList.size() > 0) || isAvilible(context, "com.xiaomi.market")
				|| isAvilible(context, "com.pp.assistant") || isAvilible(context, "com.huawei.appmarket")) {
			status = true;
		} else {
			status = false;
		}
		return status;

	}

	public void skipAppScore(Activity context) {
		ArrayList<String> pkgsList = InstalledAPPs(context);
		ArrayList<String> pkgsSelectedList = SelectedInstalledAPPs(context, pkgsList);

		String apppkg = "com.bestdo.bestdoStadiums";
		if (pkgsSelectedList != null && pkgsSelectedList.size() > 0) {
			Log.e("pkgsSelectedList=====", pkgsSelectedList.toString());
			String marketPkg = pkgsSelectedList.get(0);
			if (pkgsSelectedList.contains("com.tencent.android.qqdownloader")) {
				// 应用宝助手
				marketPkg = "com.tencent.android.qqdownloader";
			} else if (pkgsSelectedList.contains("com.baidu.appsearch")) {
				// 百度手机助手
				marketPkg = "com.baidu.appsearch";
			} else if (pkgsSelectedList.contains("com.qihoo.appstore")) {
				// 360助手
				marketPkg = "com.qihoo.appstore";
			} else if (pkgsSelectedList.contains("com.lenovo.leos.appstore")) {
				// 联想乐商店
				marketPkg = "com.lenovo.leos.appstore";
			} else if (pkgsSelectedList.contains("com.wandoujia.phoenix2")) {
				// 豌豆荚助手
				marketPkg = "com.wandoujia.phoenix2";
			}
			launchAppDetail(context, apppkg, marketPkg);
		} else if (isAvilible(context, "com.xiaomi.market")) {
			// 小米助手
			String marketPkg = "com.xiaomi.market";
			launchAppDetail(context, apppkg, marketPkg);
		} else if (isAvilible(context, "com.pp.assistant")) {
			// pp助手
			String marketPkg = "com.pp.assistant";
			launchAppDetail(context, apppkg, marketPkg);
		} else if (isAvilible(context, "com.huawei.appmarket")) {
			// 华为
			String marketPkg = "com.huawei.appmarket";
			launchAppDetail(context, apppkg, marketPkg);
		}
		// else if (isAvilible(context, "com.sec.android.app.samsungapps")) {
		// // 三星
		// String marketPkg = "com.sec.android.app.samsungapps";
		// launchAppDetail(context, apppkg, marketPkg);
		// }

	}
}
