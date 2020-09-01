package com.longcheng.lifecareplan.modular.mine.set.version;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import com.longcheng.lifecareplan.BuildConfig;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.FileCache;
import com.longcheng.lifecareplan.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 把更新条在后台状态栏
 *
 * @author qyy
 */
public class UpdateService extends Service {


    private File tempFile = null;
    private boolean cancelUpdate = false;
    private MyHandler myHandler;
    private int download_precent = 0;
    boolean states = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url;
        if (intent != null) {
            url = intent.getStringExtra("url");
            if (!TextUtils.isEmpty(url)) {
                ToastUtils.showToast("正在下载更新中...");
            }
        }
        if (!states && intent != null) {
            notifyUser(1);
            myHandler = new MyHandler(Looper.myLooper(), this);
            // 初始化下载任务内容views
            Message message = myHandler.obtainMessage(3, 0);
            myHandler.sendMessage(message);
            // 启动线程开始执行下载任务
            url = intent.getStringExtra("url");
            if (!TextUtils.isEmpty(url)) {
                downFile(url);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private NotificationManager mNotificationManager;
    private NotificationChannel mNotificationChannel;

    private void notifyUser(int progress) {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            if (mNotificationChannel == null) {
                //创建 通知通道  channelid和channelname是必须的（自己命名就好）
                mNotificationChannel = new NotificationChannel("1",
                        "huzhu", NotificationManager.IMPORTANCE_HIGH);
                mNotificationChannel.enableLights(true);//是否在桌面icon右上角展示小红点
                mNotificationChannel.setLightColor(Color.RED);//小红点颜色
                mNotificationChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                mNotificationManager.createNotificationChannel(mNotificationChannel);
            }
            int notificationId = 0x1234;
            Notification.Builder builder = new Notification.Builder(getApplicationContext(), "1");
            builder.setOnlyAlertOnce(false);
            builder.setSmallIcon(R.mipmap.app_icon)
                    .setContentText(getString(R.string.app_name) + "更新" + progress + "%")
                    .setAutoCancel(true);
            if (progress > 0 && progress <= 100) {
                builder.setProgress(100, progress, false);
            } else {
                builder.setProgress(0, 0, false);
            }
//            builder.setContentIntent(progress >= 100 ? this.getContentIntent() :
//                    PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
            Notification notification = builder.build();
            mNotificationManager.notify(notificationId, notification);
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, null);
            builder.setSmallIcon(R.mipmap.app_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon))
                    .setContentTitle(getString(R.string.app_name) + "更新" + progress + "%");
            if (progress > 0 && progress <= 100) {
                builder.setProgress(100, progress, false);
            } else {
                builder.setProgress(0, 0, false);
            }
            builder.setAutoCancel(true);
            builder.setWhen(System.currentTimeMillis());
//            builder.setContentIntent(progress >= 100 ? this.getContentIntent() :
//                    PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
            mNotificationManager.notify(0, builder.build());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 下载更新文件
     *
     * @param url
     */
    private void downFile(final String url) {
        new Thread() {
            public void run() {
                try {
//                    HttpClient client = new DefaultHttpClient();
//                    HttpGet get = new HttpGet(url);
//                    HttpResponse response = client.execute(get);
//                    HttpEntity entity = response.getEntity();
//                    long length = entity.getContentLength();
//                    InputStream is = entity.getContent();

                    URL down_url = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) down_url
                            .openConnection();
                    httpURLConnection.setConnectTimeout(20000);
                    httpURLConnection.setReadTimeout(20000);
                    long length = httpURLConnection.getContentLength();
                    InputStream is = httpURLConnection.getInputStream();
                    OutputStream outputStream;
                    if (is != null) {
                        states = true;
                        File rootFile = new File(FileCache.path);
                        if (!rootFile.exists() && !rootFile.isDirectory())
                            rootFile.mkdir();
                        tempFile = new File(FileCache.path + url.substring(url.lastIndexOf("/") + 1));
                        Log.e("downFile", "" + tempFile.getPath());
                        if (tempFile.exists())
                            tempFile.delete();
                        tempFile.createNewFile();

//                        // 已读出流作为参数创建一个带有缓冲的输出流
//                        BufferedInputStream bis = new BufferedInputStream(is);
//                        // 创建一个新的写入流，讲读取到的图像数据写入到文件中
//                        FileOutputStream fos = new FileOutputStream(tempFile);
//                        // 已写入流作为参数创建一个带有缓冲的写入流
//                        BufferedOutputStream bos = new BufferedOutputStream(fos);
                        outputStream = new FileOutputStream(tempFile, false);
                        int readsize;
                        long count = 0;
                        int precent = 0;
                        byte[] buffer = new byte[1024];
                        while ((readsize = is.read(buffer)) != -1 && !cancelUpdate) {
                            outputStream.write(buffer, 0, readsize);
                            count += readsize;
                            precent = (int) (((double) count / length) * 100);

                            // 每下载完成5%就通知任务栏进行修改下载进度
                            if (precent - download_precent >= 2) {
                                download_precent = precent;
                                Log.e("downFile", "length==" + length + ";             download_precent=" + download_precent);
                                Message message = myHandler.obtainMessage(3, precent);
                                myHandler.sendMessage(message);
                            }
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        is.close();
                        outputStream.close();
                    }

                    if (!cancelUpdate) {
                        Message message = myHandler.obtainMessage(2, tempFile);
                        myHandler.sendMessage(message);
                        states = false;
                    } else {
                        tempFile.delete();
                        states = false;
                    }
                } catch (Exception e) {
                    Message message = myHandler.obtainMessage(4, "下载更新失败");
                    myHandler.sendMessage(message);
                    states = false;
                }
            }
        }.start();
    }

    /**
     * 进入安装
     * 设置“悬浮窗”权限，否则无法在桌面安装app
     *
     * @return
     */
    private void getInstanll(File file) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//如果不加，最后安装完成，点打开，无法打开新版本应用。
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            Uri apkUri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileprovider", file);//在AndroidManifest中的android:authorities值
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            Uri apkUri = Uri.fromFile(file);
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }
        startActivity(install);
    }

    /* 事件处理类 */
    class MyHandler extends Handler {
        private Context context;

        public MyHandler(Looper looper, Context c) {
            super(looper);
            this.context = c;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 0:
                        ToastUtils.showToast(msg.obj.toString());
                        break;
                    case 1:
                        break;
                    case 2:
                        // 下载完成后清除所有下载信息，执行安装提示
                        download_precent = 0;
                        mNotificationManager.cancelAll();
                        //移除通知栏
                        if (Build.VERSION.SDK_INT >= 26) {
                            mNotificationManager.deleteNotificationChannel("1");
                        }
                        getInstanll((File) msg.obj);
                        // 停止掉当前的服务
                        stopSelf();
                        break;
                    case 3:
                        // 更新状态栏上的下载进度信息
                        notifyUser(download_precent);
                        break;
                    case 4:
                        //更新失败
                        ToastUtils.showToast("更新失败");
                        mNotificationManager.cancelAll();
                        //移除通知栏
                        if (Build.VERSION.SDK_INT >= 26) {
                            mNotificationManager.deleteNotificationChannel("1");
                        }
                        stopSelf();
                        break;
                }
            }
        }
    }

}
