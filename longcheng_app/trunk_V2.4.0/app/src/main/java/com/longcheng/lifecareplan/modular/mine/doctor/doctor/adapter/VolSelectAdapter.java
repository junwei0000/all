package com.longcheng.lifecareplan.modular.mine.doctor.doctor.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.DocRewardListDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;


public class VolSelectAdapter extends BaseAdapterHelper<DocRewardListDataBean.DocItemBean> {
    ViewHolder mHolder = null;
    Context context;

    public VolSelectAdapter(Context context, List<DocRewardListDataBean.DocItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<DocRewardListDataBean.DocItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.doctor_volselect_list_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        DocRewardListDataBean.DocItemBean mDaiFuItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mDaiFuItemBean.getVolunteer_user_avatar(), mHolder.item_iv_img);
        String name = CommonUtil.setName(mDaiFuItemBean.getVolunteer_user_name());
        mHolder.item_tv_name.setText(name);
        mHolder.item_tv_jieqi.setVisibility(View.GONE);
        mHolder.item_tv_phone.setText(mDaiFuItemBean.getVolunteer_user_phone());
        int selectstatus = mDaiFuItemBean.getSelectstatus();
        if (selectstatus == 1) {
            mHolder.item_iv_selecticon.setBackgroundResource(R.mipmap.check_true_red);
        } else {
            mHolder.item_iv_selecticon.setBackgroundResource(R.mipmap.check_false);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name;
        private TextView item_tv_jieqi;
        private TextView item_tv_phone;
        private ImageView item_iv_selecticon;

        public ViewHolder(View convertView) {
            item_iv_img = convertView.findViewById(R.id.item_iv_img);
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_jieqi = convertView.findViewById(R.id.item_tv_jieqi);
            item_tv_phone = convertView.findViewById(R.id.item_tv_phone);
            item_iv_selecticon = convertView.findViewById(R.id.item_iv_selecticon);
        }
    }
}
