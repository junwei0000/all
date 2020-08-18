package com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean.BasicInfoListDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class BasicInfoListAdapter extends BaseAdapterHelper<BasicInfoListDataBean.BasicItemBean> {
    ViewHolder mHolder = null;
    Activity context;
    List<BasicInfoListDataBean.BasicItemBean> list;

    public BasicInfoListAdapter(Activity context, List<BasicInfoListDataBean.BasicItemBean> list) {
        super(context, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<BasicInfoListDataBean.BasicItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.doctor_basic_list_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        BasicInfoListDataBean.BasicItemBean mOrderItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mOrderItemBean.getAvatar(), mHolder.item_iv_img);
        mHolder.item_tv_name.setText(CommonUtil.setName(mOrderItemBean.getPatient_name()));
        mHolder.item_tv_jieqi.setText(mOrderItemBean.getJieqi_name());
        mHolder.item_tv_phone.setText(mOrderItemBean.getPatient_phone());
        mHolder.item_tv_time.setText(mOrderItemBean.getCreate_time());
        mHolder.item_tv_status.setBackgroundResource(R.mipmap.my_icon_sign);
        mHolder.item_tv_status.setPadding(0, 0, 0, 0);
        if (mOrderItemBean.getPatient_type() == 1) {
            mHolder.item_tv_status.setVisibility(View.VISIBLE);
        } else {
            mHolder.item_tv_status.setVisibility(View.GONE);
        }
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
