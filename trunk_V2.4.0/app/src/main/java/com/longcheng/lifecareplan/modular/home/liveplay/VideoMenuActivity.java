package com.longcheng.lifecareplan.modular.home.liveplay;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.TabPageAdapter;
import com.longcheng.lifecareplan.modular.helpwith.fragment.HelpWithActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.framgent.HomFramgemt;
import com.longcheng.lifecareplan.modular.home.liveplay.framgent.MineFramgemt;
import com.longcheng.lifecareplan.modular.home.liveplay.framgent.VideoFramgent;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.ShortVideoActivity;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyViewPager;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.zxing.CreateQRImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoMenuActivity extends BaseActivity {
    @BindView(R.id.vp_bt_menu)
    MyViewPager vpBtMenu;
    @BindView(R.id.bottom_tv_home)
    TextView bottomTvHome;
    @BindView(R.id.bottom_tv_scancode)
    TextView bottomTvScancode;
    @BindView(R.id.bottom_layout_publish)
    LinearLayout bottom_layout_publish;
    @BindView(R.id.bottom_tv_follow)
    TextView bottomTvFollow;
    @BindView(R.id.bottom_tv_mine)
    TextView bottomTvMine;


    public static List<Fragment> fragmentList = new ArrayList<>();
    public static int position;
    public static int beforeCodeposition;
    public static final int tab_position_home = 0;
    public static final int tab_position_Follow = 1;
    public static final int tab_position_mine = 2;

    public static Activity mMenuContext;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.video_menu;
    }


    @Override
    public void initView(View view) {
        mMenuContext = this;
    }


    String skipType;

    @Override
    public void initDataAfter() {
        skipType = getIntent().getStringExtra("skipType");
        initFragment();
        setPageAdapter();
        selectPage(tab_position_home);
    }

    @Override
    public void setListener() {
        bottomTvHome.setOnClickListener(this);
        bottomTvScancode.setOnClickListener(this);
        bottom_layout_publish.setOnClickListener(this);
        bottomTvFollow.setOnClickListener(this);
        bottomTvMine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_tv_scancode:
                VideoItemInfo mVideoItemInfo = null;
                beforeCodeposition = position;
                if (position == tab_position_Follow) {
                    mVideoItemInfo = ((VideoFramgent) fragmentList.get(tab_position_Follow)).getCurrentInfo();
                } else if (position == tab_position_home) {
                    mVideoItemInfo = ((HomFramgemt) fragmentList.get(tab_position_home)).getCurrentInfo();
                }
                if (mVideoItemInfo != null) {
                    String payurl = Config.PAY_URL + "home/live/pay/short_video_id/" + mVideoItemInfo.getVideo_id() +
                            "/publisher_user_id/" + mVideoItemInfo.getUser_id() +
                            "/forwarder_user_id/" + UserUtils.getUserId(mContext);
                    setCodeBtn();
                    setCodeDialog(payurl);
                }
                break;
            case R.id.bottom_layout_publish:
                Intent intent = new Intent(mActivity, ShortVideoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.bottom_tv_home:
                clickStatus = true;
                selectPage(tab_position_home);
                break;
            case R.id.bottom_tv_follow:
                clickStatus = true;
                selectPage(tab_position_Follow);
                break;
            case R.id.bottom_tv_mine:
                clickStatus = true;
                selectPage(tab_position_mine);
                break;
        }
    }

    MyDialog selectDialog;
    ImageView iv_code;

    public void setCodeDialog(String payurl) {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_sharehomecode);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            iv_code = (ImageView) selectDialog.findViewById(R.id.iv_code);
            ImageView iv_bottom = (ImageView) selectDialog.findViewById(R.id.iv_bottom);
            LinearLayout tv_leftwidth = (LinearLayout) selectDialog.findViewById(R.id.tv_leftwidth);
            LinearLayout pop_layout = (LinearLayout) selectDialog.findViewById(R.id.pop_layout);
            tv_leftwidth.setPadding(d.getWidth() / 5, 0, 0, 30);
            tv_leftwidth.setLayoutParams(new LinearLayout.LayoutParams(p.width, LinearLayout.LayoutParams.WRAP_CONTENT));
            iv_code.setLayoutParams(new LinearLayout.LayoutParams(p.width / 5 - 1, p.width / 5 - 5));
            iv_bottom.setLayoutParams(new LinearLayout.LayoutParams(p.width / 5, LinearLayout.LayoutParams.WRAP_CONTENT));
            selectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    clickStatus = true;
                    selectPage(beforeCodeposition);
                }
            });
            pop_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
        } else {
            selectDialog.show();
        }
        Log.e("registerHandler", "payurl=" + payurl);
        CreateQRImage.createQRImage(payurl, iv_code);
    }

    private void setCodeBtn() {
        bottomTvHome.setTextColor(getResources().getColor(R.color.text_noclick_color));
        bottomTvFollow.setTextColor(getResources().getColor(R.color.text_noclick_color));
        bottomTvMine.setTextColor(getResources().getColor(R.color.text_noclick_color));
        bottomTvScancode.setTextColor(getResources().getColor(R.color.white));
    }


    private void setBottomBtn() {
        bottomTvScancode.setTextColor(getResources().getColor(R.color.text_noclick_color));
        bottomTvHome.setTextColor(getResources().getColor(R.color.text_noclick_color));
        bottomTvFollow.setTextColor(getResources().getColor(R.color.text_noclick_color));
        bottomTvMine.setTextColor(getResources().getColor(R.color.text_noclick_color));
        if (position == tab_position_home) {
            bottomTvHome.setTextColor(getResources().getColor(R.color.white));
        } else if (position == tab_position_Follow) {
            bottomTvFollow.setTextColor(getResources().getColor(R.color.white));
        } else if (position == tab_position_mine) {
            bottomTvMine.setTextColor(getResources().getColor(R.color.white));
        }
    }

    /**
     * @name 初始化Fragment
     */
    private void initFragment() {
        HomFramgemt homFramgemt = new HomFramgemt();
        fragmentList.add(homFramgemt);

        VideoFramgent followFramgent = new VideoFramgent();
        followFramgent.showPageType = followFramgent.showPageType_follow;
        fragmentList.add(followFramgent);

        MineFramgemt mineFragment = new MineFramgemt();
        fragmentList.add(mineFragment);

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
        vpBtMenu.setOffscreenPageLimit(0);
        /**
         * 设置是否可以滑动
         */
        vpBtMenu.setScroll(false);
    }

    boolean clickStatus = false;

    /**
     * @param position
     */
    private void selectPage(int position) {
        this.position = position;
        // 切换页面
        vpBtMenu.setCurrentItem(position, false);
        setBottomBtn();
        if (clickStatus) {
            clickStatus = false;
            if (position == tab_position_home) {
                ((HomFramgemt) fragmentList.get(tab_position_home)).onResumeVideo();
            } else {
                ((HomFramgemt) fragmentList.get(tab_position_home)).onPauseVideo();
            }
            if (position == tab_position_Follow) {
                ((VideoFramgent) fragmentList.get(tab_position_Follow)).onResumeVideo();
            } else {
                ((VideoFramgent) fragmentList.get(tab_position_Follow)).onPauseVideo();
            }
            if (position == tab_position_mine) {
                ((MineFramgemt) fragmentList.get(tab_position_mine)).getMineInfo();
            }
        }
    }

    /**
     * 康农url
     * Config.BASE_HEAD_URL + "home/knpteam/allroomlist"
     */
    public void back() {
        if (!TextUtils.isEmpty(skipType) && skipType.equals("auto")) {
            Intent intent = new Intent(mActivity, HelpWithActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        doFinish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            back();
        }
        return false;
    }
}
