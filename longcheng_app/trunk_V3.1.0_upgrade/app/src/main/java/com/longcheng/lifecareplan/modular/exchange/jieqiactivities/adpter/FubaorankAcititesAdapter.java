package com.longcheng.lifecareplan.modular.exchange.jieqiactivities.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.bean.fupackage.FuBaoRankBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.RiceActiviesListDataBean;
import com.longcheng.lifecareplan.utils.date.DatesUtils;

import java.util.List;


public class FubaorankAcititesAdapter extends BaseAdapterHelper<FuBaoRankBean.FuBaoRankAfterBean.FuBaoRankItemBean> {
    ViewHolder mHolder = null;
    Context context;
    int type = 1;

    public FubaorankAcititesAdapter(Context context, List<FuBaoRankBean.FuBaoRankAfterBean.FuBaoRankItemBean> list, int type) {
        super(context, list);
        this.context = context;
        this.type = type;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<FuBaoRankBean.FuBaoRankAfterBean.FuBaoRankItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pioneer_userzyb_ricectivies_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        FuBaoRankBean.FuBaoRankAfterBean.FuBaoRankItemBean mDaiFuItemBean = list.get(position);
        mHolder.item_tv_name.setText(mDaiFuItemBean.getUser_name());
        int time = mDaiFuItemBean.getTime();
        mHolder.item_tv_desc.setText(DatesUtils.getInstance().getTimeStampToDate(time, "MM-dd HH:mm:ss"));
        if (type == 1) {
            mHolder.item_tv_time.setText("送出" + mDaiFuItemBean.getCount() + "份福包");
        } else {
            mHolder.item_tv_time.setText("领取" + mDaiFuItemBean.getCount() + "份福包");
        }
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
