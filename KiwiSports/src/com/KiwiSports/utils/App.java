package com.KiwiSports.utils;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class App {
	private static Application sApp;
	private static Handler mainHandler;
	private static Thread mainThread;
	public static final boolean DEBUG = false;

	public static Handler getMainHandler() {
		return mainHandler;
	}

	public static void init(Application app) {
		sApp = app;
		mainHandler = new Handler(app.getMainLooper());
		mainThread = mainHandler.getLooper().getThread();

	}

	public static Context getContext() {
		if (sApp == null) {
			throw new IllegalThreadStateException("必须在onCreat方法之前调用init方法，且在init方法之后调用发送请求");
		}
		return sApp;
	}

	public static void runInMainThread(Runnable run) {
		if (Thread.currentThread() == mainThread) {
			run.run();
		} else {
			mainHandler.post(run);
		}
	}

	public static void runInMainThread(Runnable run, int delayMillis) {
		mainHandler.postDelayed(run, delayMillis);
	}

	public static void t(final int resId) {
		t(getContext().getString(resId));
	}

	public static void t(final String msg) {
		runInMainThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
			}
		});

	}

}
