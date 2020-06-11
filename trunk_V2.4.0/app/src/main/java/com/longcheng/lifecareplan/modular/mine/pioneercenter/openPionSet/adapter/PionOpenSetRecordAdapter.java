package com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetRecordDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;


public class PionOpenSetRecordAdapter extends BaseAdapterHelper<PionOpenSetRecordDataBean.PionOpenSetRecordItemBean> {
    ViewHolder mHolder = null;
    Context context;


    public PionOpenSetRecordAdapter(Context context, List<PionOpenSetRecordDataBean.PionOpenSetRecordItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<PionOpenSetRecordDataBean.PionOpenSetRecordItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pionner_openset_record_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        PionOpenSetRecordDataBean.PionOpenSetRecordItemBean mDaiFuItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mDaiFuItemBean.getUser_avatar(), mHolder.item_iv_img);
        String name = CommonUtil.setName(mDaiFuItemBean.getUser_name(), 8);
        mHolder.item_tv_name.setText(name);
        mHolder.item_tv_time.setText("开通时间：" + mDaiFuItemBean.getCreate_time());
        mHolder.item_tv_jieqi.setText(mDaiFuItemBean.getUser_branch_info());
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name;
        private TextView item_tv_time;
        private TextView item_tv_jieqi;

        public ViewHolder(View convertView) {
            item_iv_img = convertView.findViewById(R.id.item_iv_img);
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_time = convertView.findViewById(R.id.item_tv_time);
            item_tv_jieqi = convertView.findViewById(R.id.item_tv_jieqi);
        }
    }
}
