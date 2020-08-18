package com.longcheng.lifecareplan.modular.mine.doctor.doctor.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.DocRewardListDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;


public class DoclRewardListAdapter extends BaseAdapterHelper<DocRewardListDataBean.DocItemBean> {
    ViewHolder mHolder = null;
    Context context;


    public DoclRewardListAdapter(Context context, List<DocRewardListDataBean.DocItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<DocRewardListDataBean.DocItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.aiyou_list_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        DocRewardListDataBean.DocItemBean mDaiFuItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mDaiFuItemBean.getAvatar(), mHolder.item_iv_img);
        String name = CommonUtil.setName(mDaiFuItemBean.getPatient_name());
        mHolder.item_tv_name.setText(name);
        mHolder.item_tv_jieqi.setText(mDaiFuItemBean.getJieqi_name());
        mHolder.item_tv_phone.setText(mDaiFuItemBean.getPatient_phone());
        mHolder.item_tv_time.setText(DatesUtils.getInstance().getTimeStampToDate(mDaiFuItemBean.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
        mHolder.item_tv_time.setVisibility(View.VISIBLE);
        mHolder.item_tv_status.setVisibility(View.VISIBLE);
        String money = mDaiFuItemBean.getMoney();
        if (!TextUtils.isEmpty(money) && Double.valueOf(money) > 0) {
            mHolder.item_tv_money.setText("+" + money + "元");
            mHolder.item_tv_money.setVisibility(View.VISIBLE);
        } else {
            mHolder.item_tv_money.setVisibility(View.GONE);
        }

        int finish=mDaiFuItemBean.getFinish();
        if(finish==1){
            mHolder.item_tv_status.setText("已完成");
            mHolder.item_tv_status.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
        }else{
            mHolder.item_tv_status.setText("进行中");
            mHolder.item_tv_status.setTextColor(context.getResources().getColor(R.color.red));
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name;
        private TextView item_tv_jieqi;
        private TextView item_tv_phone;
        private TextView item_tv_time;
        private TextView item_tv_money;
        private TextView item_tv_status;

        public ViewHolder(View convertView) {
            item_iv_img = convertView.findViewById(R.id.item_iv_img);
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_jieqi = convertView.findViewById(R.id.item_tv_jieqi);
            item_tv_phone = convertView.findViewById(R.id.item_tv_phone);
            item_tv_time = convertView.findViewById(R.id.item_tv_time);
            item_tv_money = convertView.findViewById(R.id.item_tv_money);
            item_tv_status = convertView.findViewById(R.id.item_tv_status);
        }
    }
}
