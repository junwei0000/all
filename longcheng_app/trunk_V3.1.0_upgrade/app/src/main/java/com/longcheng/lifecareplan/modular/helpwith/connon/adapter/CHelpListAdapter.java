package com.longcheng.lifecareplan.modular.helpwith.connon.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.connon.activity.GrupUtils;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpItemBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpListDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：热推互助
 */

public class CHelpListAdapter extends BaseAdapterHelper<CHelpListDataBean.CHelpListItemBean> {
    ViewHolder mHolder = null;
    Activity context;

    public CHelpListAdapter(Activity context, List<CHelpListDataBean.CHelpListItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CHelpListDataBean.CHelpListItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.connon_list_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        CHelpListDataBean.CHelpListItemBean mHelpItemBean = list.get(position);


        GlideDownLoadImage.getInstance().loadCircleImage(mHelpItemBean.getUser_avatar(), mHolder.item_iv_thumb);
        mHolder.item_tv_content.setText(CommonUtil.setName(mHelpItemBean.getUser_name()) + "为爱友");
        mHolder.item_tv_name.setText(CommonUtil.setName(mHelpItemBean.getPatient_name()));
        mHolder.item_tv_time.setText(mHelpItemBean.getCreate_time());

        GlideDownLoadImage.getInstance().loadCircleImage(mHelpItemBean.getPatient_avatar(), mHolder.aiyou_iv_thumb);
        mHolder.aiyou_tv_name.setText(CommonUtil.setName(mHelpItemBean.getPatient_name()));
        mHolder.aiyou_tv_name.setTextColor(context.getResources().getColor(R.color.color_333333));
        mHolder.aiyou_tv_zhufu.setVisibility(View.VISIBLE);
        int person_number = mHelpItemBean.getPerson_number();
        int already_person_number = mHelpItemBean.getAlready_person_number();
        if (mGrupUtils == null) {
            mGrupUtils = new GrupUtils(context);
        }
        mGrupUtils.showChairAllViewList(person_number, already_person_number, mHolder.relatGroup, mHolder.absolut_chair, mHolder.layout_group_center);
        return convertView;
    }

    GrupUtils mGrupUtils;


    private class ViewHolder {
        private ImageView item_iv_thumb;
        private TextView item_tv_content;
        private TextView item_tv_name;
        private TextView item_tv_time;

        private RelativeLayout relatGroup;
        private AbsoluteLayout absolut_chair;
        private LinearLayout layout_group_center;
        private ImageView aiyou_iv_thumb;
        private TextView aiyou_tv_name;
        private TextView aiyou_tv_zhufu;


        public ViewHolder(View view) {
            item_iv_thumb = view.findViewById(R.id.item_iv_thumb);
            item_tv_content = view.findViewById(R.id.item_tv_content);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_time = view.findViewById(R.id.item_tv_time);
            relatGroup = view.findViewById(R.id.relat_group);
            absolut_chair = view.findViewById(R.id.absolut_chair);
            layout_group_center = view.findViewById(R.id.layout_group_center);
            aiyou_iv_thumb = view.findViewById(R.id.aiyou_iv_thumb);
            aiyou_tv_name = view.findViewById(R.id.aiyou_tv_name);
            aiyou_tv_zhufu = view.findViewById(R.id.aiyou_tv_zhufu);
        }
    }
}
