package com.longcheng.lifecareplan.modular.helpwith.fragment;

import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ApplyHelpActivity;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.activity.AutoHelpActivity;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.adapter.HelpWithBottomAdapter;
import com.longcheng.lifecareplan.modular.helpwith.adapter.HelpWithTopAdapter;
import com.longcheng.lifecareplan.modular.helpwith.bean.HelpWithInfo;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleActivity;
import com.longcheng.lifecareplan.modular.helpwith.medalrank.activity.MedalRankActivity;
import com.longcheng.lifecareplan.modular.helpwith.myDedication.activity.MyDedicationActivity;
import com.longcheng.lifecareplan.modular.helpwith.myGratitude.activity.MyGratitudeActivity;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.activity.MyFamilyActivity;
import com.longcheng.lifecareplan.modular.mine.activatenergy.activity.ActivatEnergyActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.myview.MyScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:19
 * 邮箱：MarkShuai@163.com
 * 意图：互助页面
 */

public class HelpWithFragment extends BaseFragmentMVP<HelpWithContract.View, HelpWithPresenterImp<HelpWithContract.View>> implements HelpWithContract.View {

    @BindView(R.id.pagetop_iv_left)
    ImageView pagetop_iv_left;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.helpWith_gvtop)
    MyGridView helpWithGvtop;
    @BindView(R.id.helpWith_gvbottom)
    MyGridView helpWithGvbottom;
    @BindView(R.id.helpWith_sv)
    MyScrollView helpWithSv;

    @Override
    public int bindLayout() {
        return R.layout.fragment_helpwith;
    }

    @Override
    public void initView(View view) {
        pagetop_iv_left.setVisibility(View.GONE);
        pageTopTvName.setText(getString(R.string.bottom_helpwith));
        helpWithGvtop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (position == 0) {
                    //生命能量互祝
                    intent = new Intent(mContext, HelpWithEnergyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                } else if (position == 1) {
                    //生活方式互祝
                    intent = new Intent(mContext, LifeStyleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                } else if (position == 2) {
                    //申请互祝
                    String is_cho = (String) SharedPreferencesHelper.get(mContext, "is_cho", "");
                    //1:是  ；0：不是
                    if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
                        intent = new Intent(mContext, ApplyHelpActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    } else {
                        ToastUtils.showToast("升级为CHO可用");
                    }
                } else if (position == 3) {
                    //智能互祝
                    intent = new Intent(mContext, AutoHelpActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                } else if (position == 4) {
                    //成为CHO
                    intent = new Intent(mContext, UserInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
            }
        });
        helpWithGvbottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (position == 0) {
                    //我的奉献
                    intent = new Intent(mContext, MyDedicationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                } else if (position == 1) {
                    //我的恩人
                    intent = new Intent(mContext, MyGratitudeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                } else if (position == 2) {
                    //激活能量
                    intent = new Intent(mContext, ActivatEnergyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                } else if (position == 3) {
                    //奖牌榜
                    intent = new Intent(mContext, MedalRankActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                } else if (position == 4) {
                    //我的家人
                    intent = new Intent(mContext, MyFamilyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        initInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume", "onResume");
        initInfo();
    }

    public void initInfo() {
        List<HelpWithInfo> mList = new ArrayList();
        mList.add(new HelpWithInfo("生命能量互祝", R.mipmap.wisheach_icon_energy));
        mList.add(new HelpWithInfo("生活方式互祝", R.mipmap.wisheach_icon_life));
        mList.add(new HelpWithInfo("申请互祝", R.mipmap.wisheach_icon_toapplyfor));
        mList.add(new HelpWithInfo("智能互祝", R.mipmap.wisheach_icon_smartr));
        String is_cho = (String) SharedPreferencesHelper.get(mContext, "is_cho", "");
        //1:是  ；0：不是
        if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
        } else {
            mList.add(new HelpWithInfo("成为CHO", R.mipmap.wisheach_icon_cho));
        }
        HelpWithTopAdapter mHelpWithTopAdapter = new HelpWithTopAdapter(mContext, mList);
        helpWithGvtop.setAdapter(mHelpWithTopAdapter);

        List<HelpWithInfo> mBottomList = new ArrayList();
        mBottomList.add(new HelpWithInfo("我的奉献", R.mipmap.wisheach_icon_dedication));
        mBottomList.add(new HelpWithInfo("我的恩人", R.mipmap.wisheach_icon_benefactor));
        mBottomList.add(new HelpWithInfo("激活能量", R.mipmap.wisheach_icon_theactivation));
        mBottomList.add(new HelpWithInfo("奖牌榜", R.mipmap.wisheach_icon_listn));
        mBottomList.add(new HelpWithInfo("我的家人", R.mipmap.wisheach_icon_family));
        HelpWithBottomAdapter mHelpWithBottomAdapter = new HelpWithBottomAdapter(mContext, mBottomList);
        helpWithGvbottom.setAdapter(mHelpWithBottomAdapter);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    protected HelpWithPresenterImp<HelpWithContract.View> createPresent() {
        return new HelpWithPresenterImp<>(this);
    }


    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }


}
