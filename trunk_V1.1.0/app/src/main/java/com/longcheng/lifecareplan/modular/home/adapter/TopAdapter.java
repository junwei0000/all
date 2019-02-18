package com.longcheng.lifecareplan.modular.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.home.bean.HomeItemBean;
import com.longcheng.lifecareplan.modular.home.healthydelivery.detail.activity.HealthyDeliveryDetailAct;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/24 13:27
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class TopAdapter extends PagerAdapter {

    private Context mContext;
    private List<HomeItemBean> list;
    private LayoutInflater mInflater;

    public TopAdapter(Context mContext, List<HomeItemBean> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(List<HomeItemBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (list.size() != 0) {
            position %= list.size();
            if (position < 0) {
                position = list.size() + position;
            }
            View view = mInflater.inflate(R.layout.bannertop_item_adapter, container, false);
            ImageView item_iv_pic = view.findViewById(R.id.item_iv_pic);
            TextView item_tv_jieqiday = view.findViewById(R.id.item_tv_jieqiday);
            LinearLayout item_layout_type1 = view.findViewById(R.id.item_layout_type1);
            TextView item_tv_jieqititle = view.findViewById(R.id.item_tv_jieqititle);
            TextView item_tv_givehelp = view.findViewById(R.id.item_tv_givehelp);
            TextView item_tv_helpme = view.findViewById(R.id.item_tv_helpme);

            HomeItemBean item = list.get(position);
            GlideDownLoadImage.getInstance().loadCircleImageRoleREf(mContext, item.getPic(), item_iv_pic);
            item_tv_jieqiday.setText(item.getDesc());
            item_tv_jieqititle.setText("越互祝越健康，" + item.getPre_jieqi_name() + "感恩您的祝福");
            String showT = "<font color=\"#ff4d5b\">" + item.getSponsor_help_number() +
                    "</font>次，生命能量<font color=\"#ff4d5b\">" + item.getSponsor_ability() + "</font>";
            item_tv_givehelp.setText(Html.fromHtml(showT));
            String showT2 = "<font color=\"#ff4d5b\">" + item.getReceive_help_number() +
                    "</font>次，生命能量<font color=\"#ff4d5b\">" + item.getReceive_ability() + "</font>";
            item_tv_helpme.setText(Html.fromHtml(showT2));
            String color = item.getColor();
            if (!TextUtils.isEmpty(color)) {
                item_tv_jieqiday.setTextColor(Color.parseColor(color));
            }
            int type = item.getType();
            if (type == 1) {
                item_layout_type1.setVisibility(View.VISIBLE);
                item_tv_jieqiday.setVisibility(View.GONE);
            } else {
                item_layout_type1.setVisibility(View.GONE);
                item_tv_jieqiday.setVisibility(View.VISIBLE);
            }


            container.addView(view);
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
