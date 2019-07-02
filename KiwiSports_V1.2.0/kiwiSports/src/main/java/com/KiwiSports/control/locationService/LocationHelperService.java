package com.KiwiSports.control.locationService;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

/**
 */

public class LocationHelperService extends Service {


    private Utils.CloseServiceReceiver mCloseReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        startBind();
        mCloseReceiver = new Utils.CloseServiceReceiver(this);
        registerReceiver(mCloseReceiver, Utils.getCloseServiceFilter());
    }



    @Override
    public void onDestroy() {
        if (mInnerConnection != null) {
            unbindService(mInnerConnection);
            mInnerConnection = null;
        }

        if (mCloseReceiver != null) {
            unregisterReceiver(mCloseReceiver);
            mCloseReceiver = null;
        }

        super.onDestroy();
    }

    private ServiceConnection mInnerConnection;
    private void startBind() {
        final String locationServiceName = "com.KiwiSports.control.locationService.LocationService";
        mInnerConnection = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Intent intent = new Intent();
                intent.setAction(locationServiceName);
                startService(Utils.getExplicitIntent(getApplicationContext(), intent));
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ILocationServiceAIDL l = ILocationServiceAIDL.Stub.asInterface(service);
                try {
                    l.onFinishBind();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };

        Intent intent = new Intent();
        intent.setAction(locationServiceName);
        bindService(Utils.getExplicitIntent(getApplicationContext(), intent), mInnerConnection, Service.BIND_AUTO_CREATE);
    }



    private HelperBinder mBinder;

    private class HelperBinder extends ILocationHelperServiceAIDL.Stub{
        @Override
        public void onFinishBind(int notiId) throws RemoteException {
            startForeground(notiId, Utils.buildNotification(LocationHelperService.this.getApplicationContext()));
            stopForeground(true);
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        if (mBinder == null) {
            mBinder = new HelperBinder();
        }

        return mBinder;
    }
}
