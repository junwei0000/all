package com.longcheng.lifecareplan.home.menu.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.BasicResponse;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.home.help.HelpActivity;
import com.longcheng.lifecareplan.home.menu.adapter.MenusAdapter;
import com.longcheng.lifecareplan.home.menu.bean.MenuInfo;
import com.longcheng.lifecareplan.home.set.SetActivity;
import com.longcheng.lifecareplan.login.bean.LoginAfterBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DatesUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 菜单首页
 */
public class MenuActivity extends BaseActivityMVP<MenuContract.View, MenuPresenterImp<MenuContract.View>> implements MenuContract.View {

    @BindView(R.id.pageTop_tv_time)
    TextView pageTopTvTime;
    @BindView(R.id.pageTop_tv_date)
    TextView pageTopTvDate;
    @BindView(R.id.pageTop_tv_week)
    TextView pageTopTvWeek;
    @BindView(R.id.pagetop_layout_set)
    LinearLayout pagetopLayoutSet;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pageTop_tv_phone)
    TextView pageTopTvPhone;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.pageTop_iv_thumb)
    ImageView pageTopIvThumb;
    @BindView(R.id.gvbottom)
    GridView gvbottom;

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.menu;
    }

    @Override
    public void initView(View view) {
        initTimer();
    }

    @Override
    public void setListener() {
        pagetopLayoutRigth.setVisibility(View.VISIBLE);
        pagetopLayoutSet.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setSelectFouseText(pagetopLayoutSet);
        pageTopTvTime.setFocusable(true);//设置无用的view焦点，其他可点击view默认无焦点
        gvbottom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mAdapter.setSelectItem(-1);
                } else {
                    pageTopTvTime.setFocusable(false);//防止点击上下键还有焦点
                }
            }
        });
        gvbottom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setSelectItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mAdapter.setSelectItem(-1);
            }
        });
        gvbottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else if (position == 1) {

                } else if (position == 2) {
                    Intent intent = new Intent(mContext, HelpActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else if (position == 3) {

                } else if (position == 4) {

                }
            }
        });
    }

    @Override
    public void initDataAfter() {
        initD();
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
            case R.id.pagetop_layout_set:
                Intent intent = new Intent(this, SetActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected MenuPresenterImp<MenuContract.View> createPresent() {
        return new MenuPresenterImp<>(mActivity, this);
    }


    @Override
    public void getMenuInfoSuccess(BasicResponse<LoginAfterBean> responseBean) {

    }

    MenusAdapter mAdapter;

    private void initD() {
        List<MenuInfo> mBottomList = new ArrayList();
        mBottomList.add(new MenuInfo("春分", "玉兰花", "春分第七天",
                R.mipmap.lichun, R.mipmap.login_icon_phone, "视频"));
        mBottomList.add(new MenuInfo("清明", "杜鸥花", "距离清明第14天",
                R.mipmap.qingming, R.mipmap.login_icon_phone, "图库"));
        mBottomList.add(new MenuInfo("谷雨", "玉兰花", "春分第七天",
                R.mipmap.guyu, R.mipmap.login_icon_phone, "互祝"));
        mBottomList.add(new MenuInfo("立夏", "玉兰花", "春分第七天",
                R.mipmap.lixia, R.mipmap.login_icon_phone, "动态"));
        mBottomList.add(new MenuInfo("", "", "",
                R.mipmap.jieqiyangsheng, R.mipmap.login_icon_phone, ""));
        mAdapter = new MenusAdapter(mContext, mBottomList);
        gvbottom.setAdapter(mAdapter);
        mAdapter.setSelectItem(-1);
    }

    @Override
    public void onError() {
        ToastUtils.showToast(getString(R.string.net_tishi));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 监听返回键
     */
    public void onBackPressed() {
        exit();
    }
}
