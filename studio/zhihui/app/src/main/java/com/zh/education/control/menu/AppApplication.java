package com.zh.education.control.menu;

import com.onedrive.sdk.authentication.MSAAuthenticator;
import com.onedrive.sdk.concurrency.ICallback;
import com.onedrive.sdk.core.ClientException;
import com.onedrive.sdk.core.DefaultClientConfig;
import com.onedrive.sdk.core.IClientConfig;
import com.onedrive.sdk.extensions.IOneDriveClient;
import com.onedrive.sdk.extensions.OneDriveClient;
import com.onedrive.sdk.logger.LoggerLevel;
import com.zh.education.R;
import com.zh.education.control.menu.db.SQLHelper;
import com.zh.education.control.onedrives.ApiExplorer;
import com.zh.education.control.onedrives.DefaultCallback;
import com.zh.education.utils.RequestUtils;
import com.zh.education.utils.TextSizeUtils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.LruCache;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-5 下午5:28:21
 * @Description 类描述：AppApplication
 */
public class AppApplication extends Application {
	private static AppApplication mAppApplication;
	private SQLHelper sqlHelper;
	/**
	 * The number of thumbnails to cache
	 */
	public  static final int MAX_IMAGE_CACHE_SIZE = 300;


	/**
	 * Thumbnail cache
	 */
	public LruCache<String, Bitmap> mImageCache;

	/**
	 * The service instance
	 */
	public  final AtomicReference<IOneDriveClient> mClient = new AtomicReference<IOneDriveClient>();

	/**
	 * The system connectivity manager
	 */
	public ConnectivityManager mConnectivityManager;
	@Override
	public void onCreate() {
		super.onCreate();
		// initImageLoader(getApplicationContext());
		mAppApplication = this;
		RequestUtils.init(this);// 请求volley
		/**
		 * 初始化赋值字体大小设置
		 */
		TextSizeUtils.getInstance().setInit(this);
		mConnectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	}
	/**
	 * Create the client configuration
	 * @return the newly created configuration
	 */
	public IClientConfig createConfig() {
		final MSAAuthenticator msaAuthenticator = new MSAAuthenticator() {
			@Override
			public String getClientId() {
				return "000000004C146A60";
			}

			@Override
			public String[] getScopes() {
				return new String[] {"onedrive.readwrite", "onedrive.appfolder", "wl.offline_access"};
			}
		};

		final IClientConfig config = DefaultClientConfig.createWithAuthenticator(msaAuthenticator);
		config.getLogger().setLoggingLevel(LoggerLevel.Debug);
		return config;
	}
	/**
	 * Navigates the user to the wifi settings if there is a connection problem
	 *
	 * @return if the wifi activity was navigated to
	 */
	public synchronized boolean goToWifiSettingsIfDisconnected() {
		final NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
		if (info == null || !info.isConnected()) {
			Toast.makeText(this, getString(R.string.wifi_unavailable_error_message), Toast.LENGTH_LONG).show();
			final Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			return true;
		}
		return false;
	}
	/**
	 * Clears out the auth token from the application store
	 */
	public  void signOut() {
		if (mClient.get() == null) {
			return;
		}
		mClient.get().getAuthenticator().logout(new ICallback<Void>() {
			@Override
			public void success(final Void result) {
				mClient.set(null);
				final Intent intent = new Intent(getBaseContext(), ApiExplorer.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}

			@Override
			public void failure(final ClientException ex) {
				Toast.makeText(getBaseContext(), "Logout error " + ex, Toast.LENGTH_LONG).show();
			}
		});
	}
	/**
	 * Get an instance of the service
	 *
	 * @return The Service
	 */
	public  synchronized IOneDriveClient getOneDriveClient() {
		if (mClient.get() == null) {
			throw new UnsupportedOperationException("Unable to generate a new service object");
		}
		return mClient.get();
	}

	/**
	 * Used to setup the Services
	 * @param activity the current activity
	 * @param serviceCreated the callback
	 */
	public  synchronized void createOneDriveClient(final Activity activity, final ICallback<Void> serviceCreated) {
		final DefaultCallback<IOneDriveClient> callback = new DefaultCallback<IOneDriveClient>(activity) {
			@Override
			public void success(final IOneDriveClient result) {
				mClient.set(result);
				serviceCreated.success(null);
			}

			@Override
			public void failure(final ClientException error) {
				serviceCreated.failure(error);
			}
		};
		new OneDriveClient
				.Builder()
				.fromConfig(createConfig())
				.loginAndBuildClient(activity, callback);
	}

	/**
	 * Gets the image cache for this application
	 *
	 * @return the image loader
	 */
	public synchronized LruCache<String, Bitmap> getImageCache() {
		if (mImageCache == null) {
			mImageCache = new LruCache<String, Bitmap>(AppApplication.MAX_IMAGE_CACHE_SIZE);
		}
		return mImageCache;
	}
	/** 获取Application */
	public static AppApplication getApp() {
		return mAppApplication;
	}



	/** 获取数据库Helper */
	public SQLHelper getSQLHelper() {
		if (sqlHelper == null)
			sqlHelper = new SQLHelper(mAppApplication);
		return sqlHelper;
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (sqlHelper != null)
			sqlHelper.close();
		super.onTerminate();
		// 整体摧毁的时候调用这个方法
	}
	/** 初始化ImageLoader */
	// public static void initImageLoader(Context context) {
	// File cacheDir = StorageUtils.getOwnCacheDirectory(context,
	// "topnews/Cache");//获取到缓存的目录地址
	// Log.d("cacheDir", cacheDir.getPath());
	// //创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
	// ImageLoaderConfiguration config = new ImageLoaderConfiguration
	// .Builder(context)
	// //.memoryCacheExtraOptions(480, 800) // max width, max
	// height，即保存的每个缓存文件的最大长宽
	// //.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null) // Can
	// slow ImageLoader, use it carefully (Better don't use
	// it)设置缓存的详细信息，最好不要设置这个
	// .threadPoolSize(3)//线程池内加载的数量
	// .threadPriority(Thread.NORM_PRIORITY - 2)
	// .denyCacheImageMultipleSizesInMemory()
	// //.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You
	// can pass your own memory cache implementation你可以通过自己的内存缓存实现
	// //.memoryCacheSize(2 * 1024 * 1024)
	// ///.discCacheSize(50 * 1024 * 1024)
	// .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5
	// 加密
	// //.discCacheFileNameGenerator(new
	// HashCodeFileNameGenerator())//将保存的时候的URI名称用HASHCODE加密
	// .tasksProcessingOrder(QueueProcessingType.LIFO)
	// //.discCacheFileCount(100) //缓存的File数量
	// .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
	// //.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
	// //.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
	// // connectTimeout (5 s), readTimeout (30 s)超时时间
	// .writeDebugLogs() // Remove for release app
	// .build();
	// // Initialize ImageLoader with configuration.
	// ImageLoader.getInstance().init(config);//全局初始化此配置
	// }
}
