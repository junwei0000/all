package com.longcheng.lifecareplan.modular.mine.treasurebowl.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerItemBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.CornucopiaBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.CornucopiaListBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class CornucopiaAdapter extends BaseAdapterHelper<CornucopiaListBean.DataBean> {

    ViewHolder mHolder = null;
    Activity context;

    public CornucopiaAdapter(Activity context, List<CornucopiaListBean.DataBean> list) {
        super(context, list);
        this.context= context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CornucopiaListBean.DataBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cornucopia_adapter_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        CornucopiaListBean.DataBean dataBean = list.get(position);

        mHolder.item_order_id.setText(dataBean.getCornucopia_item_no());
        mHolder.item_tv_time.setText(new StringBuffer().append("距离到期还有").append(dataBean.getJieqi_num()).append("节气").toString());
        mHolder.item_tv_baobei.setText(dataBean.getTotal_number());
        String styp = dataBean.getConfig_value();
        mHolder.item_tv_jieqi.setText(styp);
//        if (styp != null && !styp.equals("")){
//            int stypint = Integer.valueOf(styp);
//            if (stypint == 1) {
//                mHolder.item_tv_jieqi.setText("24");
//            }else if (stypint == 2) {
//                mHolder.item_tv_jieqi.setText("48");
//            }if (stypint == 3) {
//                mHolder.item_tv_jieqi.setText("72");
//            }
//        }
        return convertView;
    }


    private class ViewHolder {
        private TextView item_order_id;
        private TextView item_tv_time;
        private TextView item_tv_baobei;
        private TextView item_tv_jieqi;

        public ViewHolder(View convertView) {
            item_order_id = convertView.findViewById(R.id.tv_order_id);
            item_tv_time = convertView.findViewById(R.id.tv_time_status);
            item_tv_baobei = convertView.findViewById(R.id.tv_baobei);
            item_tv_jieqi = convertView.findViewById(R.id.tv_jieqi_num);
        }
    }
}
