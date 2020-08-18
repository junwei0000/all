package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBHistoryDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.RiceActiviesListDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;


public class RiceAcititesAdapter extends BaseAdapterHelper<RiceActiviesListDataBean.RiceActiviesItemBean> {
    ViewHolder mHolder = null;
    Context context;

    public RiceAcititesAdapter(Context context, List<RiceActiviesListDataBean.RiceActiviesItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<RiceActiviesListDataBean.RiceActiviesItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pioneer_userzyb_ricectivies_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        RiceActiviesListDataBean.RiceActiviesItemBean mDaiFuItemBean = list.get(position);
        mHolder.item_tv_name.setText(mDaiFuItemBean.getUser_name());
        mHolder.item_tv_desc.setText(mDaiFuItemBean.getDesc());
        int time = mDaiFuItemBean.getCreate_time();

        mHolder.item_tv_time.setText(DatesUtils.getInstance().getTimeStampToDate(time, "MM-dd HH:mm:ss"));
        return convertView;
    }

    private class ViewHolder {
        private TextView item_tv_name;
        private TextView item_tv_desc;
        private TextView item_tv_time;

        public ViewHolder(View convertView) {
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_desc = convertView.findViewById(R.id.item_tv_desc);
            item_tv_time = convertView.findViewById(R.id.item_tv_time);
        }
    }
}
