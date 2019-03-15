package com.longcheng.lifecareplan.modular.bottommenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseTabActivity;
import com.longcheng.lifecareplan.modular.exchange.fragment.ExChangeFragment;
import com.longcheng.lifecareplan.modular.helpwith.fragment.HelpWithFragment;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.modular.mine.fragment.MineFragment;
import com.longcheng.lifecareplan.modular.transaction.fragment.TransactionFragment;

import butterknife.BindView;

/**
 * @author MarkShuai
 * @name 首页底部导航类
 * @time 2017/11/23 17:23
 */
public class BottomMenuActivityTabHost extends BaseTabActivity implements RadioGroup.OnCheckedChangeListener {

    //主页的RadioButton
    @BindView(R.id.rb_bottom_home)
    RadioButton mButtonHome;

    //互助的RadioButton
    @BindView(R.id.rb_bottom_helpWith)
    RadioButton mButtonHelpWith;

    //兑换的RadioButton
    @BindView(R.id.rb_bottom_exchange)
    RadioButton mButtonExchange;

    //交易的RadioButton
    @BindView(R.id.rb_bottom_transaction)
    RadioButton mButtonTransaction;

    //我的页的RadioButton
    @BindView(R.id.rb_bottom_me)
    RadioButton mButtonMe;

    @BindView(R.id.rg_bottom_main)
    RadioGroup mRadioGroupMain;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Intent homeIntent;
    private Intent helpWithIntent;
    private Intent exChangeIntent;
    private Intent transactionIntent;
    private Intent mineIntent;

    // 定义标签名
    public static final String TAB_TAG_HOME = "HomeTag";
    public static final String TAB_TAG_HELP = "HelpWith";
    public static final String TAB_TAG_EXCHANGE = "ExChange";
    public static final String TAB_TAG_MINE = "MineTag";
    public static final String TAB_TAG_TRANSACTION = "transaction";
    //当前的Tab
    public int currentTab;
    public TabHost mHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        mRadioGroupMain.setOnCheckedChangeListener(this);
        setOrChangeTranslucentColor(mToolbar, null, getResources().getColor(R.color.primss));
    }

    @Override
    public void initDataAfter() {
        initDate();
    }

    /**
     * @param
     * @name 初始化数据
     * @time 2017/11/23 17:41
     * @author MarkShuai
     */
    private void initDate() {
        setContent();
        setTabs();
    }

    /**
     * 设置标签对应内容
     */
    private void setContent() {
        //主页
        homeIntent = new Intent(BottomMenuActivityTabHost.this, HomeFragment.class);
        //互助
        helpWithIntent = new Intent(BottomMenuActivityTabHost.this, HelpWithFragment.class);
        //兑换
        exChangeIntent = new Intent(BottomMenuActivityTabHost.this, ExChangeFragment.class);
        //交易
        transactionIntent = new Intent(BottomMenuActivityTabHost.this, TransactionFragment.class);
        //我的
        mineIntent = new Intent(BottomMenuActivityTabHost.this, MineFragment.class);
    }

    /**
     * @param
     * @name 设置tabHost
     * @auhtor MarkMingShuai
     * @Data 2017-8-15 9:12
     */
    private void setTabs() {
        // 获取TabHost对象
        mHost = getTabHost();
        // 添加TabSpec
        mHost.addTab(mHost.newTabSpec(TAB_TAG_HOME).setContent(homeIntent)
                .setIndicator(""));

        mHost.addTab(mHost.newTabSpec(TAB_TAG_HELP).setContent(helpWithIntent)
                .setIndicator(""));

        mHost.addTab(mHost.newTabSpec(TAB_TAG_EXCHANGE).setContent(exChangeIntent)
                .setIndicator(""));

        mHost.addTab(mHost.newTabSpec(TAB_TAG_TRANSACTION).setContent(transactionIntent)
                .setIndicator(""));

        mHost.addTab(mHost.newTabSpec(TAB_TAG_MINE).setContent(mineIntent)
                .setIndicator(""));

        // 设置默认显示标签
        mHost.setCurrentTabByTag(TAB_TAG_HOME);
        // 设置默认选中按钮
        mButtonHome.setChecked(true);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        currentTab = mHost.getCurrentTab();
        switch (checkedId) {
            //主页
            case R.id.rb_bottom_home:
                setCurrentTabWithAnim(currentTab, 0, TAB_TAG_HOME);
                break;
            //互助
            case R.id.rb_bottom_helpWith:
                setCurrentTabWithAnim(currentTab, 1, TAB_TAG_HELP);
                break;
            //兑换
            case R.id.rb_bottom_exchange:
                setCurrentTabWithAnim(currentTab, 2, TAB_TAG_EXCHANGE);
                break;
            //交易
            case R.id.rb_bottom_transaction:
                setCurrentTabWithAnim(currentTab, 3, TAB_TAG_TRANSACTION);
                break;
            //我的
            case R.id.rb_bottom_me:
                setCurrentTabWithAnim(currentTab, 4, TAB_TAG_MINE);
                break;
        }
    }


    public void setCurrentTabWithAnim(int now, int next, String tag) {
        // 这个方法是关键，用来判断动画滑动的方向
        if (now > next) {

            mHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));

            mHost.setCurrentTabByTag(tag);
            mHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
        } else {
            mHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            mHost.setCurrentTabByTag(tag);

            mHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));

        }
    }
}
