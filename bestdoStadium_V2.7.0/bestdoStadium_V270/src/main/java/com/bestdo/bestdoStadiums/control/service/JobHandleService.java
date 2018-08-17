package com.bestdo.bestdoStadiums.control.service;

import java.util.List;

import com.bestdo.bestdoStadiums.control.walking.StepAllCounterService;
import com.bestdo.bestdoStadiums.control.walking.StepCounterService;
import com.bestdo.bestdoStadiums.utils.CommonUtils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("NewApi")
public class JobHandleService extends JobService {
	private int kJobId = 0;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("INFO", "jobService create");

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("INFO", "jobService start");
		scheduleJob(getJobInfo());
		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 你上面那个onStartJob最好也返回false，，理论上应该不会杀掉进程 除非你这个service在单独一个进程
	 * 一般你在这个回调开了线程处理耗时操作才需要返回true 你这里工作都干完了 没必要返回true
	 * 返回true官方介绍是说明你工作还没干完，cpu没办法进入休眠状态，应该比较耗电。。
	 */
	@Override
	public boolean onStartJob(JobParameters params) {
		Log.i("INFO", "job start");
		startServices(this);
		return true;
	}

	public static void startServices(Context mContext) {
		boolean isLocalServiceWork = isServiceWork(mContext,
				"com.bestdoEnterprise.generalCitic.control.service.LocalService");
		boolean isRemoteServiceWork = isServiceWork(mContext,
				"com.bestdoEnterprise.generalCitic.control.service.RemoteService");
		boolean isStepAllCounterWork = isServiceWork(mContext,
				"com.bestdoEnterprise.generalCitic.control.walking.StepAllCounterService");
		if (!isLocalServiceWork || !isRemoteServiceWork) {
			mContext.startService(new Intent(mContext, LocalService.class));
			mContext.startService(new Intent(mContext, RemoteService.class));
		}
		if (!isStepAllCounterWork) {
			mContext.startService(new Intent(mContext, StepAllCounterService.class));
		}
	}

	/**
	 * 这个值你返回true他会重新使用 返回false就把这个任务干掉了, 你返回true应该旧的一直在 新的就加进来了
	 */
	@Override
	public boolean onStopJob(JobParameters params) {
		Log.i("INFO", "job stop");
		scheduleJob(getJobInfo());
		return true;
	}

	public void scheduleJob(JobInfo t) {
		Log.i("INFO", "Scheduling job");
		try {
			JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
			if (t != null && tm != null)
				tm.schedule(t);
		} catch (Exception e) {
		}
	}

	/**
	 * 为了应对CONNECTIVITY_ACTION的变化所带来的影响,官方给出了两种缓解方案.
	 * 方案一:使用JobScheduler在无计量网络下调度网络任务.
	 * 
	 * @return
	 */
	JobInfo.Builder builder;

	public JobInfo getJobInfo() {
		if (builder == null) {
			builder = new JobInfo.Builder(kJobId++, new ComponentName(this, JobHandleService.class));
			builder.setOverrideDeadline(3 * 1000);// 设置JobService执行的最晚时间
			builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
			builder.setRequiresCharging(false);// 是否要设备为充电状态
			builder.setRequiresDeviceIdle(false);// 是否要求设备为idle状态
		}
		return builder.build();
	}

	public static boolean isServiceWork(Context mContext, String serviceName) {
		boolean isWork = false;
		ActivityManager myAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> myList = myAM.getRunningServices(100);
		if (myList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < myList.size(); i++) {
			String mName = myList.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}
}
