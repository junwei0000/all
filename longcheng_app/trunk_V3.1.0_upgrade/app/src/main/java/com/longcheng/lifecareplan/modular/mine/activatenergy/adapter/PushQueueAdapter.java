package com.longcheng.lifecareplan.modular.mine.activatenergy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;


public class PushQueueAdapter extends BaseAdapter {

    Context context;
    List<EnergyItemBean> list;

    public PushQueueAdapter(Context context, List<EnergyItemBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    public List<EnergyItemBean> getList() {
        return list;
    }

    public void setList(List<EnergyItemBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activatenergy_pushqueue_item, parent, false);
            mHolder = new ViewHolder();
            mHolder.iv_thumb = convertView.findViewById(R.id.iv_thumb);
            mHolder.item_tv_time = convertView.findViewById(R.id.item_tv_time);
            mHolder.item_tv_status = convertView.findViewById(R.id.item_tv_status);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        EnergyItemBean mEnergyItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mEnergyItemBean.getB_avatar(), mHolder.iv_thumb);

        String runtime = DatesUtils.getInstance().getTimeStampToDate(mEnergyItemBean.getRun_time(), "yyyy-MM-dd HH:mm:ss");
        mHolder.item_tv_time.setText(runtime + " " + mEnergyItemBean.getB_user_name());
        int status = mEnergyItemBean.getStatus();
        if (status == -1) {
            mHolder.item_tv_status.setText("狠心飞单");
            mHolder.item_tv_status.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
        } else if (status == -4) {
            mHolder.item_tv_status.setText("额度不足");
            mHolder.item_tv_status.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
        } else {
            mHolder.item_tv_status.setText("等待接单");
            mHolder.item_tv_status.setTextColor(context.getResources().getColor(R.color.red));
        }
        return convertView;
    }


    private class ViewHolder {
        private ImageView iv_thumb;
        private TextView item_tv_time;
        private TextView item_tv_status;

    }
}
