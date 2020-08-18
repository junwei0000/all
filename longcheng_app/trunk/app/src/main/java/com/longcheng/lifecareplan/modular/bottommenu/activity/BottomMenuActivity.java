package com.longcheng.lifecareplan.modular.bottommenu.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.TabPageAdapter;
import com.longcheng.lifecareplan.modular.exchange.fragment.ExChangeFragment;
import com.longcheng.lifecareplan.modular.helpwith.fragment.HelpWithFragment;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.modular.mine.fragment.MineFragment;
import com.longcheng.lifecareplan.modular.transaction.fragment.TransactionFragment;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.Utils;
import com.longcheng.lifecareplan.widget.view.XRadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BottomMenuActivity extends BaseActivity implements XRadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.vp_bt_menu)
    ViewPager mViewPager;

    @BindView(R.id.rb_bottom_home)
    RadioButton mButtonHome;

    @BindView(R.id.rb_bottom_helpWith)
    RadioButton mButtonHelpWith;

    @BindView(R.id.rb_bottom_exchange)
    RadioButton mButtonExChange;

    @BindView(R.id.rb_bottom_transaction)
    RadioButton mButtonTransaction;

    @BindView(R.id.rb_bottom_me)
    RadioButton mButtonMine;

    @BindView(R.id.rg_bottom_main)
    XRadioGroup mRadioGroup;

    @BindView(R.id.tv_bottom_home)
    TextView mTextViewHome;

    @BindView(R.id.rl_bottom_home)
    RelativeLayout mRelativeLayoutHome;

    @BindView(R.id.tv_bottom_helpWith)
    TextView mTextViewHelpWith;

    @BindView(R.id.rl_bottom_helpWith)
    RelativeLayout mRelativeLayoutHelpWith;

    @BindView(R.id.tv_bottom_exchange)
    TextView mTextViewExchange;

    @BindView(R.id.rl_bottom_exchange)
    RelativeLayout mRelativeLayoutExchange;

    @BindView(R.id.tv_bottom_transaction)
    TextView mTextViewTransaction;

    @BindView(R.id.rl_bottom_transaction)
    RelativeLayout mRelativeLayoutTransaction;

    @BindView(R.id.tv_bottom_me)
    TextView mTextViewMe;

    @BindView(R.id.rl_bottom_me)
    RelativeLayout mRelativeLayoutMe;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<RadioButton> radioButtonList = new ArrayList<>();
    private List<TextView> textViewBottomList = new ArrayList<>();
    private List<RelativeLayout> relativeLayoutBottomList = new ArrayList<>();
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bottom_menu2;
    }

    @Override
    public void initView(View view) {
        registerMessageReceiver();
        setOrChangeTranslucentColor(mToolbar, null, getResources().getColor(R.color.primss));
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }


    @Override
    public void initDataAfter() {
        initFragment();

        setPageAdapter();

        // 默认选中首页
        selectPage(0);
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
        mViewPager.setOffscreenPageLimit(fragmentList.size());
        mViewPager.addOnPageChangeListener(this);
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
        textViewBottomList.add(mTextViewHome);
        relativeLayoutBottomList.add(mRelativeLayoutHome);

        HelpWithFragment helpFragment = new HelpWithFragment();
        fragmentList.add(helpFragment);
        radioButtonList.add(mButtonHelpWith);
        textViewBottomList.add(mTextViewHelpWith);
        relativeLayoutBottomList.add(mRelativeLayoutHelpWith);

        ExChangeFragment exChangeFragment = new ExChangeFragment();
        fragmentList.add(exChangeFragment);
        radioButtonList.add(mButtonExChange);
        textViewBottomList.add(mTextViewExchange);
        relativeLayoutBottomList.add(mRelativeLayoutExchange);

        TransactionFragment transactionFragment = new TransactionFragment();
        fragmentList.add(transactionFragment);
        radioButtonList.add(mButtonTransaction);
        textViewBottomList.add(mTextViewTransaction);
        relativeLayoutBottomList.add(mRelativeLayoutTransaction);

        MineFragment mineFragment = new MineFragment();
        fragmentList.add(mineFragment);
        radioButtonList.add(mButtonMine);
        textViewBottomList.add(mTextViewMe);
        relativeLayoutBottomList.add(mRelativeLayoutMe);

    }

    @Override
    public void setListener() {
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void widgetClick(View v) {
    }

    @Override
    public void onCheckedChanged(XRadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            //主页
            case R.id.rb_bottom_home:
                selectPage(0);
                break;
            //互助
            case R.id.rb_bottom_helpWith:
                selectPage(1);
                break;
            //兑换
            case R.id.rb_bottom_exchange:
                selectPage(2);
                break;
            //交易
            case R.id.rb_bottom_transaction:
                selectPage(3);
                break;
            //我的
            case R.id.rb_bottom_me:
                selectPage(4);
                break;
        }
    }

    //滑动中
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //选中状态
    @Override
    public void onPageSelected(int position) {
        selectPage(position);
    }

    //改变
    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * 选择某页
     *
     * @param position 页面的位置
     */
    private void selectPage(int position) {
        // 切换页面
        mViewPager.setCurrentItem(position, false);
        radioButtonList.get(position).setChecked(true);
        for (int i = 0; i < textViewBottomList.size(); i++) {
            if (i == position) {
                textViewBottomList.get(i).setTextColor(mContext.getResources().getColor(R.color.white));
                relativeLayoutBottomList.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.grey));
            } else {
                relativeLayoutBottomList.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.black));
                textViewBottomList.get(i).setTextColor(mContext.getResources().getColor(R.color.grey));
            }
        }


    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!Utils.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
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
}
