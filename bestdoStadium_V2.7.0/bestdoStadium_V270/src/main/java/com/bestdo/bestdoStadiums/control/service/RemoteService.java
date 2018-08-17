package com.bestdo.bestdoStadiums.control.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class RemoteService extends Service {

	public static final String TAG = "remoteKeepLive";
	private MyBinder binder;
	private MyServiceConnection conn;

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (binder == null) {
			binder = new MyBinder();
		}
		conn = new MyServiceConnection();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			RemoteService.this.bindService(new Intent(RemoteService.this, LocalService.class), conn,
					Context.BIND_IMPORTANT);
			if (intent != null) {
				PendingIntent contentIntent = PendingIntent.getService(this, 0, intent, 0);
				NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
				builder.setTicker("百动运动管家").setContentIntent(contentIntent).setContentTitle("百动运动管家")
						.setAutoCancel(true).setContentText("").setWhen(System.currentTimeMillis());

				startForeground(startId, builder.build());
			}
		} catch (Exception e) {
		}
		return START_STICKY;
	}

	class MyBinder extends RemoteConnection.Stub {

		@Override
		public String getProcessName() throws RemoteException {
			return "LocalService";
		}

	}

	class MyServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(TAG, "MyServiceConnection  onServiceConnected");

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			RemoteService.this.startService(new Intent(RemoteService.this, LocalService.class));
			RemoteService.this.bindService(new Intent(RemoteService.this, LocalService.class), conn,
					Context.BIND_IMPORTANT);
		}

	}

}
