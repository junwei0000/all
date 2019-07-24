package com.longcheng.lifecareplan.modular.bottommenu.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.TabPageAdapter;
import com.longcheng.lifecareplan.modular.exchange.fragment.ExChangeFragment;
import com.longcheng.lifecareplan.modular.helpwith.fragment.HelpWithFragmentNew;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.modular.index.login.activity.LoginActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.UpdatePwActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.fragment.MineFragment;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ShortcutUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.Utils;
import com.longcheng.lifecareplan.utils.myview.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BottomMenuActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.vp_bt_menu)
    MyViewPager mViewPager;

    @BindView(R.id.rb_bottom_home)
    RadioButton mButtonHome;

    @BindView(R.id.rb_bottom_helpWith)
    RadioButton mButtonHelpWith;

    @BindView(R.id.rb_bottom_exchange)
    RadioButton mButtonExChange;

    @BindView(R.id.rb_bottom_me)
    RadioButton mButtonMine;

    @BindView(R.id.rg_bottom_main)
    RadioGroup mRadioGroup;

    public static List<Fragment> fragmentList = new ArrayList<>();
    private List<RadioButton> radioButtonList = new ArrayList<>();
    private MessageReceiver mMessageReceiver;
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static int position;
    public static String solar_terms_name = "24节气";
    public static int solar_terms_id;

    /**
     * 点击收藏登录记录之前的tab
     */
    private int before_tab_position;
    /**
     * 点击之后正常要跳转的
     */
    private int after_tab_position;

    public static final int tab_position_home = 0;
    public static final int tab_position_helpwith = 1;
    public static final int tab_position_exchange = 2;
    public static final int tab_position_mine = 3;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bottom_menu;
    }

    @Override
    public void initView(View view) {
        registerMessageReceiver();
        setOrChangeTranslucentColor(mToolbar, null);
//        ShortcutUtils.setDynamicShort(mActivity);
    }


    @Override
    public void initDataAfter() {
        initFragment();
        setPageAdapter();
        // 默认选中首页
        before_tab_position = tab_position_home;
        selectPage(tab_position_home);
    }

    @Override
    public void setListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    //主页
                    case R.id.rb_bottom_home:
                        selectPage(tab_position_home);
                        break;
                    //互助
                    case R.id.rb_bottom_helpWith:
                        after_tab_position = tab_position_helpwith;
                        chackSkipByLoginStatus();
                        break;
                    //兑换
                    case R.id.rb_bottom_exchange:
                        after_tab_position = tab_position_exchange;
                        chackSkipByLoginStatus();
                        break;
                    //我的
                    case R.id.rb_bottom_me:
                        after_tab_position = tab_position_mine;
                        chackSkipByLoginStatus();
                        break;
                }
            }
        });
    }

    /**
     * 是否登录判断
     */

    private void chackSkipByLoginStatus() {
        try {
            String loginStatus = (String) SharedPreferencesHelper.get(getApplicationContext(), "loginStatus", "");
            if (loginStatus.equals(ConstantManager.loginStatus)) {
                selectPage(after_tab_position);
            } else {
                SharedPreferencesHelper.put(mActivity, "loginSkipToStatus", ConstantManager.loginSkipToMainNext);
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
            }
        } catch (Exception e) {
        }

    }

    @Override
    public void onClick(View v) {
    }


    /**
     * @param
     * @name 初始化Fragment
     * @time 2017/11/24 10:23
     * @author MarkShuai
     */
    private void initFragment() {
        HomeFragment homeFragment = new HomeFragment();
        fragmentList.add(homeFragment);
        radioButtonList.add(mButtonHome);

        HelpWithFragmentNew helpFragment = new HelpWithFragmentNew();
        fragmentList.add(helpFragment);
        radioButtonList.add(mButtonHelpWith);

        ExChangeFragment exChangeFragment = new ExChangeFragment();
        fragmentList.add(exChangeFragment);
        radioButtonList.add(mButtonExChange);

        MineFragment mineFragment = new MineFragment();
        fragmentList.add(mineFragment);
        radioButtonList.add(mButtonMine);

    }

    /**
     * @param
     * @name 设置PagerAdapter
     * @time 2017/11/24 10:44
     * @author MarkShuai
     */
    private void setPageAdapter() {
        TabPageAdapter tabPageAdapter = new TabPageAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(tabPageAdapter);
        mViewPager.setOffscreenPageLimit(4);
//        mViewPager.addOnPageChangeListener(mOnPageChange);
        /**
         * 设置是否可以滑动
         */
        mViewPager.setScroll(false);
    }

    ViewPager.OnPageChangeListener mOnPageChange = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.e("BottomMenuActivity", "onPageScrolled=" + position);
        }

        @Override
        public void onPageSelected(int position) {
            selectPage(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 选择某页
     *
     * @param position 页面的位置
     */
    private void selectPage(int position) {
        this.position = position;
        Log.e("BottomMenuActivity", "position=" + position);
        Log.e("BottomMenuActivity", "mViewPager=" + mViewPager.getAdapter().getCount());
        Log.e("BottomMenuActivity", "mViewPager=" + mViewPager.getAdapter().toString());
        // 切换页面
        mViewPager.setCurrentItem(position, false);
        radioButtonList.get(position).setChecked(true);

        if (position == tab_position_home) {
            ((HomeFragment) fragmentList.get(position)).showCononDialog();
        } else {
            ((HomeFragment) fragmentList.get(tab_position_home)).dismissAllDialog();
        }
        if (position == tab_position_helpwith) {
            ((HelpWithFragmentNew) fragmentList.get(position)).initInfo();
        }
        if (position == tab_position_exchange) {
            ((ExChangeFragment) fragmentList.get(position)).initLoad(solar_terms_id, solar_terms_name);
        } else {
            ((ExChangeFragment) fragmentList.get(tab_position_exchange)).dismissAllDialog();
            solar_terms_name = "24节气";
            solar_terms_id = 0;
        }
        if (position == tab_position_mine) {
            ((MineFragment) fragmentList.get(position)).initUserInfo();
        } else {
            ((MineFragment) fragmentList.get(tab_position_mine)).dismissAllDialog();
        }
    }

    /**
     * 是否显示版本更新提示/通知弹层
     */
    public static boolean updatedialogstatus = false;

    /**
     * 弹出版本更新时，关闭两个页面的弹层
     */
    private void UpdatVerDisAllDialog() {
        updatedialogstatus = true;
        ((HomeFragment) fragmentList.get(tab_position_home)).dismissAllDialog();
        ((ExChangeFragment) fragmentList.get(tab_position_exchange)).dismissAllDialog();
        ((MineFragment) fragmentList.get(tab_position_mine)).dismissAllDialog();
    }


    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(ConstantManager.MAINMENU_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Log.e("onReceiveonReceive", "onReceive");
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    String type = bundle.getString("type", "");
                    if (type.equals(ConstantManager.MAIN_ACTION_TYPE_JPUSHMESSAGE)) {
                        String messge = intent.getStringExtra(KEY_MESSAGE);
                        String extras = intent.getStringExtra(KEY_EXTRAS);
                        StringBuilder showMsg = new StringBuilder();
                        showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                        if (!Utils.isEmpty(extras)) {
                            showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                        }
                        Log.e("onReceiveonReceive", "showMsg=" + showMsg.toString());
                        ((HomeFragment) fragmentList.get(tab_position_home)).haveNotReadMsg();
                    } else if (type.equals(ConstantManager.MAIN_ACTION_TYPE_HOME)) {
                        selectPage(tab_position_home);
                    } else if (type.equals(ConstantManager.MAIN_ACTION_TYPE_HELPWITH)) {
                        selectPage(tab_position_helpwith);
                    } else if (type.equals(ConstantManager.MAIN_ACTION_TYPE_CENTER)) {
                        selectPage(tab_position_mine);
                    } else if (type.equals(ConstantManager.MAIN_ACTION_TYPE_EXCHANGE)) {
                        solar_terms_id = bundle.getInt("solar_terms_id", 0);
                        solar_terms_name = bundle.getString("solar_terms_name", "24节气");
                        selectPage(tab_position_exchange);
                    } else if (type.equals(ConstantManager.MAIN_ACTION_TYPE_BACK)) {
                        selectPage(before_tab_position);
                    } else if (type.equals(ConstantManager.MAIN_ACTION_TYPE_NEXT)) {
                        selectPage(after_tab_position);
                    } else if (type.equals(ConstantManager.MAIN_ACTION_UpdateVerDisAllDialog)) {
                        UpdatVerDisAllDialog();
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    /**
     * 监听返回键
     */
    public void onBackPressed() {
        if (position != tab_position_home) {
            selectPage(tab_position_home);
        } else {
//            exit();
            ((HomeFragment) fragmentList.get(tab_position_home)).isFirstComIn = 0;
            ActivityManager.getScreenManager().backHome(mActivity);
        }
    }

}
