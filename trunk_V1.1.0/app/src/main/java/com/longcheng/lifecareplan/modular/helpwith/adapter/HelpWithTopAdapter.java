package com.longcheng.lifecareplan.modular.helpwith.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ApplyHelpActivity;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.activity.AutoHelpH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.bean.HelpWithInfo;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.ConnonH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleActivity;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.CommonUtilImage;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class HelpWithTopAdapter extends BaseAdapterHelper<HelpWithInfo> {
    ViewHolder mHolder = null;

    Context context;
    List<String> solarTermsEnsImg;

    public HelpWithTopAdapter(Context context, List<HelpWithInfo> list, List<String> solarTermsEnsImg) {
        super(context, list);
        this.context = context;
        this.solarTermsEnsImg = solarTermsEnsImg;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<HelpWithInfo> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_helpwith_topitem, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        HelpWithInfo mHelpWithInfo = list.get(position);
        String cont = mHelpWithInfo.getName2();
        mHolder.item_tv_cont.setText(mHelpWithInfo.getName());
        mHolder.item_tv_cont.setTextColor(context.getResources().getColor(mHelpWithInfo.getTextColorId()));
        mHolder.item_tv_cont2.setText(cont);
        GlideDownLoadImage.getInstance().loadCircleImageRole(mHelpWithInfo.getBgColorId(), mHolder.item_iv_applyselect, 0);
        GlideDownLoadImage.getInstance().loadCircleImageRole(mHelpWithInfo.getBgColorId(), mHolder.item_iv_select, 0);
        int width = (DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 30)) / 2;
        int height = (int) (width * 0.476);
        mHolder.item_iv_select.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height));
        mHolder.item_iv_applyselect.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height));
        mHolder.layout_apply.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height));
        int imgwidth = (int) ((height - 10) * 1.68);
        mHolder.item_iv_img.setLayoutParams(new RelativeLayout.LayoutParams(imgwidth, height - 10));
        if (solarTermsEnsImg != null && solarTermsEnsImg.size() - 1 >= position) {
            GlideDownLoadImage.getInstance().loadCircleImageHelpIndex(context, solarTermsEnsImg.get(position), mHolder.item_iv_img);
        }

        if (TextUtils.isEmpty(cont)) {
            mHolder.relat_apply.setVisibility(View.GONE);
        } else {
            mHolder.relat_apply.setVisibility(View.VISIBLE);
            mHolder.item_iv_apply.setBackgroundResource(R.drawable.apply_loading);
            AnimationDrawable animaition = (AnimationDrawable) mHolder.item_iv_apply.getBackground();
            animaition.start();
        }
        mHolder.relat_help.setTag(position);
        mHolder.relat_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Intent intent;
                if (position == 0) {
                    //生命能量互祝
                    intent = new Intent(context, HelpWithEnergyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(intent);
                } else if (position == 1) {
                    //生活方式互祝
                    intent = new Intent(context, LifeStyleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(intent);
                } else if (position == 2) {
                    //智能互祝
                    intent = new Intent(context, AutoHelpH5Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + list.get(position).getSkipurl());
                    context.startActivity(intent);
                } else if (position == 3) {
                    //康农工程互祝
                    intent = new Intent(context, ConnonH5Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("kn_url", "" + list.get(position).getSkipurl());
                    context.startActivity(intent);
                } else if (position == 4) {
                    //生活保障互祝
                    intent = new Intent(context, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + list.get(position).getSkipurl());
                    context.startActivity(intent);
                } else if (position == 5) {
                    //天下无债
                    intent = new Intent(context, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + list.get(position).getSkipurl());
                    context.startActivity(intent);
                }
            }
        });
        mHolder.relat_apply.setTag(position);
        mHolder.relat_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Intent intent;
                if (position == 0) {
                    //生命能量 申请互祝
                    intent = new Intent(context, ApplyHelpActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(intent);
                } else if (position == 1) {
                    //生活方式 申请互祝
                    Intent intents = new Intent();
                    intents.setAction(ConstantManager.MAINMENU_ACTION);
                    intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_EXCHANGE);
                    LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
                    ActivityManager.getScreenManager().popAllActivityOnlyMain();
                }
            }
        });
        return convertView;
    }


    private class ViewHolder {
        private RelativeLayout relat_help;
        private TextView item_tv_cont;
        private TextView item_tv_cont2;
        private ImageView item_iv_select;
        private ImageView item_iv_img;

        private RelativeLayout relat_apply;
        private ImageView item_iv_applyselect;
        private LinearLayout layout_apply;
        private ImageView item_iv_apply;

        public ViewHolder(View view) {
            relat_help = (RelativeLayout) view.findViewById(R.id.relat_help);
            item_tv_cont = (TextView) view.findViewById(R.id.item_tv_cont);
            item_tv_cont2 = (TextView) view.findViewById(R.id.item_tv_cont2);
            item_iv_select = (ImageView) view.findViewById(R.id.item_iv_select);
            item_iv_img = (ImageView) view.findViewById(R.id.item_iv_img);
            relat_apply = (RelativeLayout) view.findViewById(R.id.relat_apply);
            item_iv_applyselect = (ImageView) view.findViewById(R.id.item_iv_applyselect);
            layout_apply = (LinearLayout) view.findViewById(R.id.layout_apply);
            item_iv_apply = (ImageView) view.findViewById(R.id.item_iv_apply);
        }
    }
}
