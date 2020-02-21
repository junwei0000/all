package com.longcheng.lifecareplan.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.bean.MessageEvent;
import com.longcheng.lifecareplan.modular.bottommenu.float_view.FloatViewListener;
import com.longcheng.lifecareplan.modular.bottommenu.float_view.FloatWindowManager;
import com.longcheng.lifecareplan.modular.bottommenu.float_view.IFloatView;
import com.longcheng.lifecareplan.modular.bottommenu.float_view.OnShowMessageListener;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.push.jpush.message.EasyMessage;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.Permission.MPermissionUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.Immersive;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * 作者：MarkShuai on
 * 时间：2017/11/20 16:00
 * 邮箱：mark_mingshuai@163.com
 * 意图：BaseActivityMVP
 */
public abstract class BaseActivityMVP<V, T extends BasePresent<V>> extends RxAppCompatActivity implements View.OnClickListener {
    /**
     * P层引用
     */
    protected T mPresent;

    /**
     * 退出程序的时间间隔
     */
    private long exitTime = 0;

    /**
     * 是否沉浸状态栏
     **/
    private boolean isSetStatusBar = true;

    /**
     * 是否允许全屏,此处全屏是指将状态栏显示
     **/
    private boolean onlyShowStatusBar = false;

    /**
     * 是否允许全屏,此处全屏是指将状态栏都隐藏掉
     **/
    private boolean mAllowFullScreen = false;

    /**
     * 是否禁止旋转屏幕
     **/
    private boolean isAllowScreenRoate = false;

    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;

    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 上下文
     **/
    public Context mContext = this;
    public Activity mActivity = this;
    public RxAppCompatActivity mRxAppCompatActivity = this;
    private ActivityManager activityManager;
    private Unbinder bind;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "BaseActivityMVP-->onCreate()");
        //是否允许全屏
        if (mAllowFullScreen) {
            //隐藏状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        //判断是否允许全屏
        if (onlyShowStatusBar) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        //是否禁止屏幕旋转
        if (!isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        //设置主布局
        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        //加载主布局
        setContentView(mContextView);

        initFloatWindowManager();

        // 注册订阅者
        EventBus.getDefault().register(this);
        //Activity的管理，将Activity压入栈
        activityManager = ActivityManager.getScreenManager();
        activityManager.pushActivity(this);
        //判断是否设置了沉浸式效果
        if (isSetStatusBar) {
            steepStatusBar();
        }
        bind = ButterKnife.bind(this);
        //创建Presenter层
        mPresent = createPresent();
        //做绑定
        mPresent.attachView((V) this);
        //初始化数据之前获取一些数据的方法
        initDataBefore();
        //调用初始化控件的方法
        initView(mContextView);
        mPresent.fetch();
        //设置监听
        setListener();
        initDataAfter();
    }

    /**
     * -------------------------float window start---------------------------
     */
    FloatWindowManager floatWindowManager;

    private void initFloatWindowManager() {
        /**
         * WindowManager 应用内悬浮窗
         */
        if (floatWindowManager == null) {
            floatWindowManager = new FloatWindowManager();
        }
        floatWindowManager.setmOnShowMessageListener(new OnShowMessageListener() {
            @Override
            public void onShowMessage() {
                String loginStatus = (String) SharedPreferencesHelper.get(mActivity, "loginStatus", "");
                if (loginStatus.equals(ConstantManager.loginStatus) && EasyMessage.lastMessage != null) {
                    showFloatWindowDelay();
                }
            }
        });
    }

    /**
     * 必须等activity创建后，view展示了再addView，否则可能崩溃
     * BadTokenException: Unable to add window --token null is not valid; is your activity running?
     */
    protected void showFloatWindowDelay() {
        if (mContextView != null) {
            mContextView.removeCallbacks(floatWindowRunnable);
            mContextView.post(floatWindowRunnable);
        }
    }

    protected int floatWindowType = FloatWindowManager.FW_TYPE_ROOT_VIEW;

    private final Runnable floatWindowRunnable = new Runnable() {
        @Override
        public void run() {
            Log.e("floatWindowRunnable", "showFloatWindow");
            showFloatWindow();
        }
    };

    /**
     * 显示悬浮窗
     */
    protected void showFloatWindow() {
        if (TextUtils.isEmpty("" + EasyMessage.lastMessage)) {
            Log.d("OnMessageListener", "onClose    EasyMessage.lastMessage=" + EasyMessage.lastMessage);
            closeFloatWindow();
        } else {
            closeFloatWindow();//如果要显示多个悬浮窗，可以不关闭，这里只显示一个
            floatWindowManager.showFloatWindow(this, floatWindowType);
            addFloatWindowClickListener();
        }
    }

    /**
     * 关闭悬浮窗
     */
    protected void closeFloatWindow() {
        if (mContextView != null) {
            mContextView.removeCallbacks(floatWindowRunnable);
        }
        if (floatWindowManager != null) {
            floatWindowManager.dismissFloatWindow();
        }
    }

    /**
     * 监听悬浮窗关闭和点击事件
     */
    private void addFloatWindowClickListener() {
        IFloatView floatView = floatWindowManager.getFloatView();
        if (floatView == null) {
            return;
        }
        //说明悬浮窗view创建了，增加屏幕常亮
        floatView.setFloatViewListener(new FloatViewListener() {

            @Override
            public void onClick(String app_push_id) {
                gratefulRepay(app_push_id);
            }

        });
    }

    /**
     * 感恩回馈
     */
    public void gratefulRepay(String bless_grateful_push_queue_id) {
        Observable<EditDataBean> observable = Api.getInstance().service.gratefulRepay(UserUtils.getUserId(mContext), bless_grateful_push_queue_id
                , ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {

                        String status_ = responseBean.getStatus();
                        if (status_.equals("200")) {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
                        Log.e("Observable", "" + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Observable", "" + throwable.toString());
                    }
                });
    }

    private void destroyWindow() {
        if (floatWindowType != FloatWindowManager.FW_TYPE_ALERT_WINDOW) {
            closeFloatWindow();
        }
        //取消接收
        if (floatWindowManager != null)
            EasyMessage.unregisterMessageListener(floatWindowManager.mListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        Log.d(TAG, "onResume()");
        String loginStatus = (String) SharedPreferencesHelper.get(mActivity, "loginStatus", "");
        if (loginStatus.equals(ConstantManager.loginStatus) && EasyMessage.lastMessage != null) {
            showFloatWindowDelay();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onDestroy() {
        mPresent.detach();
        activityManager.popActivity(this);
        bind.unbind();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        destroyWindow();
    }
/**
 * ****************************************end****************************************************
 */
    /**
     * [绑定视图]
     *
     * @return
     */
    public abstract View bindView();


    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();


    private PowerManager.WakeLock mWakelock;

    @SuppressLint("InvalidWakeLockTag")
    public void setScreenWake() {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);// init powerManager
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "target");
        mWakelock.acquire();
    }

    public void setScreenRelease() {
        if (mWakelock != null) {
            mWakelock.release();
        }
    }

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        Immersive.steepStatusBar(this);
    }


    /**
     * 调用此方法设置沉浸式效果，向下兼容4.4
     *
     * @param toolbar             顶部导航
     * @param bottomNavigationBar 底部导航
     */
    public void setOrChangeTranslucentColor(Toolbar toolbar, View bottomNavigationBar) {
        Immersive.setOrChangeTranslucentColor(toolbar, bottomNavigationBar, this.getResources().getColor(R.color.white), this);
    }

    /**
     * 调用此方法设置沉浸式效果，向下兼容4.4
     *
     * @param toolbar             顶部导航
     * @param bottomNavigationBar 底部导航
     */
    public void setOrChangeTranslucentColor(Toolbar toolbar, View bottomNavigationBar, int color) {
        Immersive.setOrChangeTranslucentColor(toolbar, bottomNavigationBar, color, this);
    }

    /**
     * 调用此方法设置沉浸式效果，向下兼容4.4
     *
     * @param toolbar             顶部导航
     * @param bottomNavigationBar 底部导航
     */
    public void setOrChangeTranslucentOtherColor(Toolbar toolbar, View bottomNavigationBar, int color) {
        Immersive.setOrChangeTranslucentColorWrite(toolbar, bottomNavigationBar, color, this);
    }

    /**
     * [初始化控件之前初始化数据]
     */
    public void initDataBefore() {
    }

    /**
     * [初始化控件]
     *
     * @param view
     */
    public abstract void initView(View view);

    /**
     * [初始化控件之后初始化数据]
     */
    public abstract void initDataAfter();

    /**
     * [设置监听]
     */
    public abstract void setListener();

    /**
     * View点击
     **/
    public abstract void onClick(View view);

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }


    /**
     * @param event
     * @Subscribe注解的方法，必须是公共方法，否则会报错！
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEventBackground(MessageEvent event) {
        Log.i(TAG, "onMessageEventBackground(), current thread is " + Thread.currentThread().getName());
    }

    /*
     * @params
     * @name 子类实现具体的构建过程
     * @data 2017/11/20 15:39
     * @author :MarkShuai
     */
    protected abstract T createPresent();

    /**
     * [是否允许全屏]
     *
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /**
     * [是否设置沉浸状态栏]
     *
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    /**
     * [是否允许屏幕旋转]
     *
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

    /**
     * 描述：退出程序
     */
    protected void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            activityManager.popAllActivity();
        }
    }

    /**
     * @param
     * @Name 设置仅显示StatuBar
     * @Data 2018/2/28 18:01
     * @Author :MarkShuai
     */
    public void setOnlyShowStatusBar(boolean onlyShowStatusBar) {
        this.onlyShowStatusBar = onlyShowStatusBar;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void doFinish() {
        activityManager.popActivity(this);
        finish();
        ConfigUtils.getINSTANCE().setPageBackAnim(this);
    }
}
