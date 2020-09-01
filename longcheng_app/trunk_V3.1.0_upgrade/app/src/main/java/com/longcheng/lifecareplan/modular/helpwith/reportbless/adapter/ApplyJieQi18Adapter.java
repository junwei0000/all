package com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ApplyItemBean;
import com.longcheng.lifecareplan.modular.home.bangdan.BangDan1824Activity;
import com.longcheng.lifecareplan.modular.home.bangdan.BlessBangDanActivity;
import com.longcheng.lifecareplan.modular.home.bean.HomeItemBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.lifecareplan.modular.mine.emergencys.EmergencysBangDanActivity;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/24 13:27
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class ApplyJieQi18Adapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<ApplyItemBean> list;
    private LayoutInflater mInflater;
    int page;
    int pageNum = 6;

    public ApplyJieQi18Adapter(Context mContext, ArrayList<ApplyItemBean> list) {
        this.mContext = mContext;
        this.list = list;
        page = (list.size() / pageNum);
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getPage() {
        return page;
    }

    @Override
    public int getCount() {
        return page;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (page != 0) {
            position %= page;
            if (position < 0) {
                position = page + position;
            }
            View view = mInflater.inflate(R.layout.fuqrep_apply_gv, container, false);
            MyGridView mMyGridView = view.findViewById(R.id.gv_bann);
            JieQi18GridAdapter mDediGridAdapter = new JieQi18GridAdapter(mContext, list, position,pageNum);
            mMyGridView.setAdapter(mDediGridAdapter);
            container.addView(view);
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /*
     * 解决viewpager在刷新调用notifyDataSetChanged不起作用 使用懒加载后没有效果?
     */
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
