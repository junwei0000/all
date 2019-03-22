package com.longcheng.lifecareplan.modular.index.welcome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.bottommenu.activity.BottomMenuActivity;
import com.longcheng.lifecareplan.modular.index.welcome.adapter.GuidePagerAdapter;
import com.longcheng.lifecareplan.modular.index.welcome.bean.WelcomeBean;
import com.longcheng.lifecareplan.modular.index.welcome.frag.GuidePage1Frag;
import com.longcheng.lifecareplan.modular.index.welcome.frag.GuidePage2Frag;
import com.longcheng.lifecareplan.modular.index.welcome.frag.GuidePage3Frag;
import com.longcheng.lifecareplan.modular.mine.set.bean.VersionAfterBean;
import com.longcheng.lifecareplan.modular.mine.set.bean.VersionDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.MySharedPreferences;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WelcomePageActivity extends BaseActivityMVP<WelcomeContract.View, WelcomePresenterImp<WelcomeContract.View>> implements WelcomeContract.View, ViewPager.OnPageChangeListener, GuidePage3Frag.IGuidePageInListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_welcome)
    ViewPager vpWelcome;
    @BindView(R.id.ll_dot)
    LinearLayout llDot;

    private List<ImageView> dotList = new ArrayList<>();

    @Override
    public void onClick(View v) {
    }

    @Override
    public void handleButtonInClick() {
        MySharedPreferences.getInstance().saveIsFirstIn(false);
        intentIndexPage();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setAllowFullScreen(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_welcome_page;
    }

    @Override
    public void initView(View view) {
        try {
            getServerVerCode();
            Thread.sleep(1000);//防止启动页消失太快
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setOrChangeTranslucentColor(toolbar, null);
        boolean isFirstIn = MySharedPreferences.getInstance().getIsFirstIn();
        if (!isFirstIn) {
            intentIndexPage();
        }
    }

    @Override
    public void setListener() {
        vpWelcome.addOnPageChangeListener(this);
    }

    @Override
    public void initDataAfter() {

    }


    private void intentIndexPage() {
        Intent intent = new Intent(mContext, BottomMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
        doFinish();
    }

    private String getServerVerCode() {
        String appVersion = ConfigUtils.getINSTANCE().getVerCode(mContext);
        if (!TextUtils.isEmpty(appVersion) && appVersion.contains(".")) {
            Log.e("appVersion", "appVersion=" + appVersion);
            appVersion = appVersion.replace(".", "_");
        }
        Log.e("appVersion", "appVersion new=" + appVersion);
        Observable<VersionDataBean> observable = Api.getInstance().service.updateVersion(appVersion);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<VersionDataBean>() {
                    @Override
                    public void accept(VersionDataBean responseBean) throws Exception {
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
        return null;
    }

    @Override
    protected WelcomePresenterImp<WelcomeContract.View> createPresent() {
        return new WelcomePresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int size = dotList.size();
        for (int i = 0; i < size; i++) {
            ImageView img = dotList.get(i);
            if (i == position % size) {
                img.setImageResource(R.drawable.corners_oval_red);
            } else {
                img.setImageResource(R.drawable.corners_oval_redfen);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void setPageAdapter(List<WelcomeBean> list) {
        List<Fragment> fragList = new ArrayList<Fragment>();
        fragList.add(new GuidePage1Frag());
        fragList.add(new GuidePage2Frag());
        GuidePage3Frag guide3 = new GuidePage3Frag();
        guide3.setPageListener(this);
        fragList.add(guide3);
        GuidePagerAdapter adapter = new GuidePagerAdapter(getSupportFragmentManager(), fragList);
        vpWelcome.setAdapter(adapter);
    }

    @Override
    public void addLineLayoutDot(List<ImageView> list, LinearLayout.LayoutParams params) {
        dotList.clear();
        llDot.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            llDot.addView(list.get(i), params);
            dotList.add(list.get(i));
        }
        llDot.setVisibility(View.VISIBLE);
    }

    /**
     * 监听返回键
     */
    public void onBackPressed() {
        exit();
    }

}
