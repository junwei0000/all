package com.longcheng.volunteer.modular.test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.volunteer.base.BaseActivityMVP;
import com.longcheng.volunteer.R;
import com.longcheng.volunteer.bean.Bean;
import com.longcheng.volunteer.http.api.DefaultObserver;
import com.longcheng.volunteer.http.api.IdeaApi;
import com.longcheng.volunteer.http.api.IdeaApiTest;
import com.longcheng.volunteer.http.api.IdeaApiTest2;
import com.longcheng.volunteer.http.basebean.BasicResponse;
import com.longcheng.volunteer.modular.mine.set.bean.VersionAfterBean;
import com.longcheng.volunteer.modular.test.adapter.HomeAdapter;
import com.longcheng.volunteer.modular.webView.WebProjectActivityBridge;
import com.longcheng.volunteer.utils.ConstantManager;
import com.longcheng.volunteer.utils.ListUtils;
import com.longcheng.volunteer.utils.SDCardHelper;
import com.longcheng.volunteer.utils.ScrowUtil;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.apkupload.service.DownloadService;
import com.longcheng.volunteer.utils.apkupload.utils.InstallUtil;
import com.longcheng.volunteer.utils.bspatch.BsPatchUtil;
import com.longcheng.volunteer.utils.myview.MyListView;
import com.longcheng.volunteer.utils.share.UMLoginUtils;
import com.longcheng.volunteer.widget.dialog.LoadingDialogAnim;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityMVP extends BaseActivityMVP<HomeContract.View, HomePresenter<HomeContract.View>> implements HomeContract.View {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.lv)
    MyListView lv;
    @BindView(R.id.ssd)
    PullToRefreshScrollView ssd;


    private ProgressBar progressBar;
    private HomeAdapter adapter;
    private int index = 0;
    private List<String> listString = new ArrayList<>();
    private long exitTime = 0;
    private DownloadService.DownloadBinder mDownloadBinder;
    private Disposable mDisposable;//可以取消观察者
    private ProgressBar mProgressBar;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mDownloadBinder = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        bindService();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        progressBar = view.findViewById(R.id.prg);
        mProgressBar = (ProgressBar) findViewById(R.id.down_progress);

        Button buttonRecharge = findViewById(R.id.but_recharge);
        buttonRecharge.setOnClickListener(this);

        Button buttonActivation = findViewById(R.id.btn_activation);
        buttonActivation.setOnClickListener(this);

        Button buttonHelp = findViewById(R.id.btn_help);
        buttonHelp.setOnClickListener(this);

        adapter = new HomeAdapter(mContext, listString);
        lv.setAdapter(adapter);
    }

    @Override
    public void initDataAfter() {
        mPresent.setListViewData(index);
    }


    @Override
    public void setListener() {
        ScrowUtil.ScrollViewDownConfig(ssd);
        lv.setOnItemClickListener((parent, view, position, id) -> {

            //二维码
//            Intent intent = new Intent(mContext, MipcaCaptureActivity.class);
            Intent intent = new Intent(mContext, WebProjectActivityBridge.class);
            intent.putExtra("name", "asd");
            //欢迎引导页
//            Intent intent = new Intent(mContext, WelcomePageActivity.class);
            String item = adapter.getItem(position - 1);
            startActivity(intent);

//            PayUtils.getWeCharOpenId(mContext);
        });

        ssd.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                index++;
                mPresent.setListViewData(index);
            }
        });
        testLoadData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_recharge:
                Intent intent1 = new Intent(mContext, WebProjectActivityBridge.class);
                intent1.putExtra("name", "recharge");
                startActivity(intent1);
                break;
            case R.id.btn_activation:
                Intent intent2 = new Intent(mContext, WebProjectActivityBridge.class);
                intent2.putExtra("name", "activation");
                startActivity(intent2);
                break;
            case R.id.btn_help:
                testLoadData();
                break;
        }
    }

    /**
     * 增量更新并安装apk
     */
    private void doBsPatch() {
        LoadingDialogAnim.show(mContext);
        String oldfile = InstallUtil.getSourceApkPath(mContext, getPackageName());

        String newfile = ConstantManager.NEW_APK_PATH;
        String patchfile = ConstantManager.SD_CARD + "patch.patch";

        BsPatchUtil.patch(oldfile, newfile, patchfile);
        ToastUtils.showToast("开始了");
        LoadingDialogAnim.dismiss(mContext);
        InstallUtil.installApk(mContext, ConstantManager.NEW_APK_PATH);
    }

    private void testLoadData() {
        IdeaApi.getApiService()
                .updateVersionTEST("1_6_0")
                .compose(this.<BasicResponse<VersionAfterBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<VersionAfterBean>>(this) {
                    @Override
                    public void onSuccess(BasicResponse<VersionAfterBean> response) {
                        VersionAfterBean results = response.getData();
                        ToastUtils.showToast(results.toString());
                        Log.e("Observable", "https://t.asdyf.com/api/============" + results.toString());
                    }

                    @Override
                    public void onError() {
                        Log.e("Observable", "onErrorUser");
                    }
                });
        IdeaApiTest.getApiService()
                .getappfindmeanu("1.6.0")
                .compose(this.<BasicResponse<Bean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<Bean>>(this) {
                    @Override
                    public void onSuccess(BasicResponse<Bean> response) {
                        Bean results = response.getData();
                        ToastUtils.showToast(results.toString());
                        Log.e("Observable", "http://www.kiwiloc.com/api/============" + results.toString());
                    }

                    @Override
                    public void onError() {
                        Log.e("Observable", "onErrorUser");
                    }
                });
        IdeaApiTest2.getApiService()
                .getaa()
                .compose(this.<BasicResponse<Bean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<Bean>>(this) {
                    @Override
                    public void onSuccess(BasicResponse<Bean> response) {
                        Bean results = response.getData();
                        ToastUtils.showToast(results.toString());
                        Log.e("Observable", "https://www.bestdo.com/new-bd-app/2.7.0/============" + results.toString());
                    }

                    @Override
                    public void onError() {
                        Log.e("Observable", "onErrorUser");
                    }
                });
//        IdeaApi.getApiService()
//                .getHomeList("", "fgjlkdjkl")
//                .compose(this.<BasicResponse<HomeAfterBean>>bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DefaultObserver<BasicResponse<HomeAfterBean>>(this) {
//                    @Override
//                    public void onSuccess(BasicResponse<HomeAfterBean> response) {
//                        HomeAfterBean results = response.getData();
//                        ToastUtils.showToast(results.toString());
//                        Log.e("Observable", "" + results.toString());
//                    }
//
//                    @Override
//                    public void onError() {
//                        Log.e("Observable", "onErrorUser");
//                    }
//                });
    }


    /**
     * 绑定到一个service
     */
    private void bindService() {
        Intent intent = new Intent(this, DownloadService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 解绑绑定到一个service
     */
    private void unBindService() {
        unbindService(mConnection);
    }


    public void longIn(View view) {
//        showDialog();
//        mPresent.logIn();
//        Intent intent = new Intent(mContext, BottomMenuActivity.class);
//        startActivity(intent);
//        分享
//        String url = "http://image.baidu.com/search/detail?ct=503316480&z=0&tn=baiduimagedetail&ipn=d&cl=2&cm=1&sc=0&lm=-1&ie=gbk&pn=0&rn=1&di=113219030100&ln=30&word=%CD%BC%C6%AC&os=2394225117,7942915&cs=594559231,2167829292&objurl=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F120727%2F201995-120HG1030762.jpg&bdtype=0&simid=3436308227,304878115&pi=0&adpicid=0&timingneed=0&spn=0&is=0,0&fr=ala&ala=1&alatpl=others&pos=1";
//        ShareHelper.shareActionAll(this, SHARE_MEDIA.QQ, url, "asdasdasdasdasdasd", "http://www.baidu.com", "asdasdasdasd");
//        登录
//        SnsPlatform snsPlatform = SHARE_MEDIA.QQ.toSnsPlatform();
//        boolean isauth = UMShareAPI.get(mContext).isAuthorize(this, snsPlatform.mPlatform);
//        if(isauth){
//            UMLoginUtils.getInstance().deleteOauth(this,SHARE_MEDIA.QQ, authListener);
//        }else {
//            UMLoginUtils.getInstance().threadLogin(this, SHARE_MEDIA.QQ, authListener);
//        }
        //选取照片
//        CameraPhotoDialog.show(mContext, FileImage.getFilePath(FileImage.path, "123.png"));
    }

    /**
     * 更新APK
     */
    private void DownloadApk() {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "test.apk");
        boolean isHave = SDCardHelper.fileIsHave(file.getPath());
        if (isHave) {
            ToastUtils.showToast("存在了");
        } else {
            if (mDownloadBinder != null) {
                long downloadId = mDownloadBinder.startDownload(ConstantManager.UPLOAD_APK_NAME);
                startCheckProgress(downloadId);
            }
        }
    }

    //开始监听进度
    private void startCheckProgress(long downloadId) {
        Observable
                .interval(100, 200, TimeUnit.MILLISECONDS, Schedulers.io())//无限轮询,准备查询进度,在io线程执行
                .map(i -> mDownloadBinder.getProgress(downloadId))//获得下载进度
                .filter(time -> mDownloadBinder != null)
                .takeUntil(progress -> progress >= 100)//返回true就停止了,当进度>=100就是下载完成了
                .distinct()//去重复
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressObserver());
    }

    //观察者
    private class ProgressObserver implements Observer<Integer> {

        @Override
        public void onSubscribe(Disposable d) {
            mDisposable = d;
        }

        @Override
        public void onNext(Integer progress) {
            mProgressBar.setProgress(progress);//设置进度
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
            ToastUtils.showToast("出错");
        }

        @Override
        public void onComplete() {
            mProgressBar.setProgress(100);
            ToastUtils.showToast("下载完成");
        }
    }

    UMLoginUtils.UMLoginListener authListener = new UMLoginUtils.UMLoginListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.i(TAG, "onStart: " + share_media.toSnsPlatform().mPlatform);
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            Log.i(TAG, "onComplete: " + map);
            ToastUtils.showToast("成功了");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Log.i(TAG, "onError: ");
            ToastUtils.showToast("onError");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Log.i(TAG, "onCancel: ");
            ToastUtils.showToast("onCancel");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void changeText(String title) {
        button.setText(title);
    }

    @Override
    public void logInOk() {
    }

    @Override
    public void logInError() {
    }

    @Override
    public void onHomeListDataSuccessFul(List<String> list) {

        if (index == 0) {
            adapter.reloadListView(list, true);
        } else {
            adapter.reloadListView(list, false);
        }
        ListUtils.getInstance().RefreshCompleteS(ssd);
    }


    @Override
    public void onHomeListDataFail() {
        ListUtils.getInstance().RefreshCompleteS(ssd);
        ToastUtils.showToast(R.string.net_tishi);
    }


    @Override
    public void showDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissDialog() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        mPresent.detach();
        if (mDisposable != null) {
            //取消监听
            mDisposable.dispose();
        }
        unBindService();
        super.onDestroy();
    }

    @Override
    protected HomePresenter<HomeContract.View> createPresent() {
        return new HomePresenter<>(this);
    }


    public void jump(View view) {

    }

    /**
     * 再按一次退出
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
