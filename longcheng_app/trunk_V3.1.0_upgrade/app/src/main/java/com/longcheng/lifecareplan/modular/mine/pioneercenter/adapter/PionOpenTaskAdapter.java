package com.longcheng.lifecareplan.modular.mine.pioneercenter.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.helpwith.connon.activity.CHelpListActivity;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.LoveResultActivity;
import com.longcheng.lifecareplan.modular.home.invitefriends.activity.InviteFriendsActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PionApplyAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataListBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity.PionZFBActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.activity.PionRecoverCashNewActivity;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class PionOpenTaskAdapter extends BaseAdapterHelper<PionApplyAfterBean.RecordBean> {
    ViewHolder mHolder = null;
    Activity context;

    public PionOpenTaskAdapter(Activity context, List<PionApplyAfterBean.RecordBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<PionApplyAfterBean.RecordBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pion_opentask_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        PionApplyAfterBean.RecordBean mRankItemBean = list.get(position);
        mRankItemBean.setPosition(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mRankItemBean.getIcon(), mHolder.item_iv_thumb);
        mHolder.item_tv_title.setText(mRankItemBean.getTitle());

        mHolder.mycenter_relat_shenfen.removeAllViews();
        ArrayList<String> user_identity_imgs = mRankItemBean.getItems();
        if (user_identity_imgs != null && user_identity_imgs.size() > 0) {
            mHolder.mycenter_relat_shenfen.setVisibility(View.VISIBLE);
            for (String info : user_identity_imgs) {
                View view = LayoutInflater.from(context).inflate(R.layout.pion_opentask_item_item, null);
                TextView tv_thirdmoney = view.findViewById(R.id.tv_thirdmoney);
                tv_thirdmoney.setText(Html.fromHtml(info));
                tv_thirdmoney.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
                mHolder.mycenter_relat_shenfen.addView(view);
            }
        } else {
            mHolder.mycenter_relat_shenfen.setVisibility(View.VISIBLE);
        }
        int status = mRankItemBean.getStatus();
        if (status == 0) {
            mHolder.item_tv_status.setText("去完成");
            mHolder.item_tv_status.setBackgroundResource(R.drawable.corners_bg_login_new);
        } else {
            mHolder.item_tv_status.setText("已完成");
            mHolder.item_tv_status.setBackgroundResource(R.drawable.corners_bg_logingray_new);
        }

        mHolder.item_tv_status.setTag(mRankItemBean);
        mHolder.item_tv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PionApplyAfterBean.RecordBean mRankItemBean = (PionApplyAfterBean.RecordBean) v.getTag();
                int position = mRankItemBean.getPosition();
                int status = mRankItemBean.getStatus();
                Intent intent;
                if (status == 0) {
                    String hour = DatesUtils.getInstance().getNowTime("H");
                    if (position == 0) {
                        if (Integer.valueOf(hour) < 5 || Integer.valueOf(hour) > 13) {
                            ToastUtils.showToast("天下无癌互祝大厅开放时间为5:00～13:00");
                        } else {
                            intent = new Intent(context, CHelpListActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            context.startActivity(intent);
                        }
                    } else if (position == 1) {
                        if (Integer.valueOf(hour) < 13 || Integer.valueOf(hour) > 21) {
                            ToastUtils.showToast("天下无债互祝大厅开放时间为13:00～21:00");
                        } else {
                            intent = new Intent(context, BaoZhangActitvty.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("html_url", Config.BASE_HEAD_URL + "home/knpteam/allroomlist/");
                            context.startActivity(intent);
                        }
                    } else if (position == 2) {
                        intent = new Intent(context, LoveResultActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                    } else if (position == 3) {
                        intent = new Intent(context, PionZFBActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                    } else if (position == 4) {
                        intent = new Intent(context, PionRecoverCashNewActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                    } else if (position == 5) {
                        intent = new Intent(context, InviteFriendsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                    }
                }
            }
        });

        return convertView;
    }


    private class ViewHolder {
        private ImageView item_iv_thumb;
        private TextView item_tv_title;
        private TextView item_tv_status;
        private LinearLayout mycenter_relat_shenfen;

        public ViewHolder(View convertView) {
            item_tv_title = convertView.findViewById(R.id.item_tv_title);
            item_iv_thumb = convertView.findViewById(R.id.item_iv_thumb);
            item_tv_status = convertView.findViewById(R.id.item_tv_status);
            mycenter_relat_shenfen = convertView.findViewById(R.id.mycenter_relat_shenfen);
        }
    }
}
