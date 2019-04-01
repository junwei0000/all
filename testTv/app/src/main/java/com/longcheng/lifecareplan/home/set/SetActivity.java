package com.longcheng.lifecareplan.home.set;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.home.menu.activity.MenuActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DatesUtils;

import butterknife.BindView;

/**
 * 设置
 */
public class SetActivity extends BaseActivity {

    @BindView(R.id.pageTop_tv_time)
    TextView pageTopTvTime;
    @BindView(R.id.pageTop_tv_date)
    TextView pageTopTvDate;
    @BindView(R.id.pageTop_tv_week)
    TextView pageTopTvWeek;
    @BindView(R.id.pagetop_layout_set)
    LinearLayout pagetopLayoutSet;
    @BindView(R.id.pageTop_iv_thumb)
    ImageView pageTopIvThumb;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pageTop_tv_phone)
    TextView pageTopTvPhone;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.set_iv_update)
    ImageView setIvUpdate;
    @BindView(R.id.set_tv_versiontile)
    TextView setTvVersiontile;
    @BindView(R.id.set_tv_version)
    TextView setTvVersion;
    @BindView(R.id.set_iv_updatearrow)
    ImageView setIvUpdatearrow;
    @BindView(R.id.set_relay_update)
    RelativeLayout setRelayUpdate;
    @BindView(R.id.set_iv_about)
    ImageView setIvAbout;
    @BindView(R.id.set_tv_about)
    TextView setTvAbout;
    @BindView(R.id.set_iv_aboutarrow)
    ImageView setIvAboutarrow;
    @BindView(R.id.set_relay_about)
    RelativeLayout setRelayAbout;
    @BindView(R.id.set_iv_clear)
    ImageView setIvClear;
    @BindView(R.id.set_tv_clear)
    TextView setTvClear;
    @BindView(R.id.set_iv_cleararrow)
    ImageView setIvCleararrow;
    @BindView(R.id.set_relay_clear)
    RelativeLayout setRelayClear;


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.set;
    }

    @Override
    public void initView(View view) {
        initTimer();
    }

    @Override
    public void setListener() {
        setRelayUpdate.setOnClickListener(this);
        setRelayAbout.setOnClickListener(this);
        setRelayClear.setOnClickListener(this);
        pageTopTvTime.setFocusable(true);
        setRelayUpdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setRelayUpdate.setBackgroundResource(R.drawable.corners_bg_setselect);
                    setTvVersiontile.setTextColor(getResources().getColor(R.color.white));
                    setTvVersion.setTextColor(getResources().getColor(R.color.white));
                    setIvUpdatearrow.setBackgroundResource(R.mipmap.set_icon_jiantoubai);
                } else {
                    setRelayUpdate.setBackgroundResource(R.drawable.corners_bg_setnotselect);
                    setTvVersiontile.setTextColor(getResources().getColor(R.color.white_9a));
                    setTvVersion.setTextColor(getResources().getColor(R.color.white_9a));
                    setIvUpdatearrow.setBackgroundResource(R.mipmap.set_icon_jiantou);
                }
            }
        });
        setRelayAbout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setRelayAbout.setBackgroundResource(R.drawable.corners_bg_setselect);
                    setTvAbout.setTextColor(getResources().getColor(R.color.white));
                    setIvAboutarrow.setBackgroundResource(R.mipmap.set_icon_jiantoubai);
                } else {
                    setRelayAbout.setBackgroundResource(R.drawable.corners_bg_setnotselect);
                    setTvAbout.setTextColor(getResources().getColor(R.color.white_9a));
                    setIvAboutarrow.setBackgroundResource(R.mipmap.set_icon_jiantou);
                }
            }
        });
        setRelayClear.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setRelayClear.setBackgroundResource(R.drawable.corners_bg_setselect);
                    setTvClear.setTextColor(getResources().getColor(R.color.white));
                    setIvCleararrow.setBackgroundResource(R.mipmap.set_icon_jiantoubai);
                } else {
                    setRelayClear.setBackgroundResource(R.drawable.corners_bg_setnotselect);
                    setTvClear.setTextColor(getResources().getColor(R.color.white_9a));
                    setIvCleararrow.setBackgroundResource(R.mipmap.set_icon_jiantou);
                }
            }
        });
    }

    @Override
    public void initDataAfter() {
        String verName = ConfigUtils.getINSTANCE().getVerCode(mContext);
        setTvVersion.setText("当前版本：v" + verName);
    }

    @Override
    public void setDateInfo() {
        String date = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
        pageTopTvDate.setText(date);
        String week = DatesUtils.getInstance().getNowTime("EE");
        pageTopTvWeek.setText(week);
        String time = DatesUtils.getInstance().getNowTime("HH:mm");
        pageTopTvTime.setText(time);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_relay_update:

                break;
            case R.id.set_relay_about:
                Intent intent = new Intent(mContext, AboutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.set_relay_clear:

                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
