package com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.activity.HealTrackActivity;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean.AYApplyListAfterBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class AYApplyListAdapter extends BaseAdapterHelper<AYApplyListAfterBean.BasicItemBean> {
    ViewHolder mHolder = null;
    Activity context;
    List<AYApplyListAfterBean.BasicItemBean> list;

    public AYApplyListAdapter(Activity context, List<AYApplyListAfterBean.BasicItemBean> list) {
        super(context, list);
        this.context = context;
        this.list = list;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<AYApplyListAfterBean.BasicItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.doctor_atapply_list_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        AYApplyListAfterBean.BasicItemBean mOrderItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mOrderItemBean.getAvatar(), mHolder.item_iv_img);
        mHolder.item_tv_name.setText(CommonUtil.setName(mOrderItemBean.getPatient_name()));
        mHolder.item_tv_jieqi.setText(mOrderItemBean.getJieqi_name());
        mHolder.item_tv_phone.setText(mOrderItemBean.getPatient_phone());
        mHolder.item_tv_time.setText(DatesUtils.getInstance().getTimeStampToDate(mOrderItemBean.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
        ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_tv_status, R.color.red);
        mHolder.item_tv_status.setTag(mOrderItemBean);
        mHolder.item_tv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AYApplyListAfterBean.BasicItemBean mOrderItemBean = (AYApplyListAfterBean.BasicItemBean) v.getTag();
                String user_name = mOrderItemBean.getPatient_name();
                Intent intent = new Intent(context, HealTrackActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("user_name", "" + user_name);
                intent.putExtra("knp_msg_id", mOrderItemBean.getKnp_msg_id());
                intent.putExtra("identityFlag", 2);
                String nowtime = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
                String sign_start_day = mOrderItemBean.getSign_start_day();
                String sign_end_day = mOrderItemBean.getSign_end_day();
                boolean stauts = DatesUtils.getInstance().doCheckDate(nowtime, sign_start_day, sign_end_day);
                if (stauts) {
                    intent.putExtra("day", nowtime);
                } else {
                    if(TextUtils.isEmpty(sign_start_day)||(!TextUtils.isEmpty(sign_start_day)&&sign_start_day.equals("0000-00-00"))){
                        intent.putExtra("day", nowtime);
                    }else{
                        intent.putExtra("day", sign_start_day);
                    }
                }

                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name;
        private TextView item_tv_jieqi;
        private TextView item_tv_time;
        private TextView item_tv_phone;
        private TextView item_tv_status;

        public ViewHolder(View view) {
            item_iv_img = view.findViewById(R.id.item_iv_img);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_jieqi = view.findViewById(R.id.item_tv_jieqi);
            item_tv_time = view.findViewById(R.id.item_tv_time);
            item_tv_phone = view.findViewById(R.id.item_tv_phone);
            item_tv_status = view.findViewById(R.id.item_tv_status);
        }
    }
}
