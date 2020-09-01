package com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.TabPageAdapter;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.myview.MyViewPager;
import com.longcheng.lifecareplan.utils.network.LocationUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * 我的福圈
 */
public class MyCircleActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.layout_left)
    LinearLayout layoutLeft;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.vp_bt_menu)
    MyViewPager vpBtMenu;
    @BindView(R.id.bottom_iv_myquan)
    ImageView bottomIvMyquan;
    @BindView(R.id.bottom_tv_myquan)
    TextView bottomTvMyquan;
    @BindView(R.id.bottom_layout_myquan)
    LinearLayout bottomLayoutMyquan;
    @BindView(R.id.bottom_iv_list)
    ImageView bottomIvList;
    @BindView(R.id.bottom_tv_list)
    TextView bottomTvList;
    @BindView(R.id.bottom_layout_list)
    LinearLayout bottomLayoutList;
    @BindView(R.id.bottom_iv_mygroup)
    ImageView bottomIvMygroup;
    @BindView(R.id.bottom_tv_mygroup)
    TextView bottomTvMygroup;
    @BindView(R.id.bottom_layout_mygroup)
    LinearLayout bottomLayoutMygroup;


    public List<Fragment> fragmentList = new ArrayList<>();
    public static int position;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_left:
                doFinish();
                break;
            case R.id.bottom_layout_myquan:
                selectPage(0);
                break;
            case R.id.bottom_layout_list:
                selectPage(1);
                break;
            case R.id.bottom_layout_mygroup:
                selectPage(2);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fuqrep_mycircle;
    }


    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
    }


    @Override
    public void initDataAfter() {
        initFragment();
        setPageAdapter();
        selectPage(0);
        setSocketConnect();
    }

    @Override
    public void setListener() {
        layoutLeft.setOnClickListener(this);
        bottomLayoutMyquan.setOnClickListener(this);
        bottomLayoutList.setOnClickListener(this);
        bottomLayoutMygroup.setOnClickListener(this);
        LocationUtils mLocationUtils = new LocationUtils();
        double[] mLngAndLat = mLocationUtils.getLngAndLatWithNetwork(mContext);
        double phone_user_latitude = mLngAndLat[0];
        double phone_user_longitude = mLngAndLat[1];
        String city = mLocationUtils.getAddress(mContext, phone_user_latitude, phone_user_longitude);
        tvAddress.setText(city);
    }


    /**
     * @name 初始化Fragment
     */
    private void initFragment() {
        fragmentList.clear();
        MyCircleFramgemt myCircleFramgemt = new MyCircleFramgemt();
        fragmentList.add(myCircleFramgemt);

        MyListFramgemt myListFramgemt = new MyListFramgemt();
        fragmentList.add(myListFramgemt);

        MyGroupFramgemt myGroupFramgemt = new MyGroupFramgemt();
        fragmentList.add(myGroupFramgemt);
    }

    private void refreshData() {
        if (fragmentList != null && fragmentList.size() == 3) {
            ((MyListFramgemt) fragmentList.get(1)).getList();
            ((MyGroupFramgemt) fragmentList.get(2)).getList();
        }
    }

    /**
     * @param
     * @name 设置PagerAdapter
     * @time 2017/11/24 10:44
     * @author MarkShuai
     */
    private void setPageAdapter() {
        TabPageAdapter tabPageAdapter = new TabPageAdapter(getSupportFragmentManager(), fragmentList);
        vpBtMenu.setAdapter(tabPageAdapter);
        vpBtMenu.setOffscreenPageLimit(3);
        /**
         * 设置是否可以滑动
         */
        vpBtMenu.setScroll(true);
        vpBtMenu.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * @param position
     */
    private void selectPage(int position) {
        MyCircleActivity.position = position;
        // 切换页面
        vpBtMenu.setCurrentItem(position, false);
        setMenuBtn();
    }

    private void setMenuBtn() {
        bottomTvMyquan.setTextColor(getResources().getColor(R.color.main_tab_text_color_select));
        bottomTvList.setTextColor(getResources().getColor(R.color.main_tab_text_color_select));
        bottomTvMygroup.setTextColor(getResources().getColor(R.color.main_tab_text_color_select));
        bottomIvMyquan.setBackgroundResource(R.mipmap.fuquan_icon_1);
        bottomIvList.setBackgroundResource(R.mipmap.fuquan_icon_3);
        bottomIvMygroup.setBackgroundResource(R.mipmap.fuquan_icon_5);
        if (position == 0) {
            tvName.setText("我的福圈");
            bottomIvMyquan.setBackgroundResource(R.mipmap.fuquan_icon_2);
            bottomTvMyquan.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
        } else if (position == 1) {
            tvName.setText("消息列表");
            bottomIvList.setBackgroundResource(R.mipmap.fuquan_icon_4);
            bottomTvList.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
        } else if (position == 2) {
            tvName.setText("我的群聊");
            bottomIvMygroup.setBackgroundResource(R.mipmap.fuquan_icon_6);
            bottomTvMygroup.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == ConstantManager.REFRESHDATA) {
                Log.e(TAG, "*********************onActivityResult***************************");
                layoutLeft.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                    }
                }, 10);
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
    }

    private Boolean isConnected = true;
    private Socket mSocket;

    private void setSocketConnect() {
        ExampleApplication app = (ExampleApplication) getApplication();
        app.setOptsQuery(UserUtils.getUserId(mContext));
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("chat_msg", onNewMessage);
        mSocket.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("chat_msg", onNewMessage);
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "*****************************connect*******************************");
                    if (!isConnected) {
                        isConnected = true;
                    }
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "*****************************diconnected************************************");
                    isConnected = false;
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "********************************************Error connecting");
                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "onNewMessage==" + args[0]);
                    layoutLeft.post(new Runnable() {
                        @Override
                        public void run() {
                            refreshData();
                            Intent intent = new Intent();
                            intent.setAction(ConstantManager.BroadcastReceiver_CHAT);
                            intent.putExtra("type", 1);
                            intent.putExtra("chatmsg", "" + args[0]);
                            sendBroadcast(intent);//发送普通广播
                        }
                    });
                }
            });
        }
    };
}
