package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.bean.fupackage.FuListBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.AddSongFuActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.FuPacakageDetails;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;

import java.util.List;

public class MyFuListAdapter extends BaseAdapterHelper<FuListBean> {
    private Context context;
    ViewHolder mHolder = null;
    int type;

    public MyFuListAdapter(Context context, List<FuListBean> list, int type) {
        super(context, list);
        this.context = context;
        this.type = type;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<FuListBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.myfulist_adapter_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        FuListBean fuListBean = list.get(position);
        String sponsor_user_name = fuListBean.getSponsor_user_name();//发送人
        String receive_user_name = fuListBean.getReceive_user_name();//接收人
        String sponsor_super_ability = fuListBean.getSponsor_super_ability();
        sponsor_super_ability = PriceUtils.getInstance().seePrice(sponsor_super_ability);
        String receive_super_ability = fuListBean.getReceive_super_ability();
        receive_super_ability = PriceUtils.getInstance().seePrice(receive_super_ability);
        int create_time = fuListBean.getCreate_time();
        int open_time = fuListBean.getOpen_time();//收到的时间
        int status = fuListBean.getStatus();//2 已接收

        if (type == 1) {
            mHolder.item_tv_source.setText("收到" + CommonUtil.setName3(sponsor_user_name) + "的福包");
            if (open_time > 0) {
                mHolder.item_tv_time.setText(DatesUtils.getInstance().getTimeStampToDate(open_time, "yyyy-MM-dd HH:mm:ss"));
            } else {
                mHolder.item_tv_time.setText("");
            }
            mHolder.item_tv_live.setText(sponsor_super_ability);
            mHolder.item_tv_over.setText(new StringBuffer().append(1).append("/").append(1).toString());
        } else {
            mHolder.item_tv_source.setText("送给" + CommonUtil.setName3(receive_user_name) + "的福包");
            mHolder.item_tv_time.setText(DatesUtils.getInstance().getTimeStampToDate(create_time, "yyyy-MM-dd HH:mm:ss"));
            mHolder.item_tv_live.setText(receive_super_ability);
            if (status >= 2) {
                mHolder.item_tv_over.setText(new StringBuffer().append(1).append("/").append(1).toString());
            } else {
                mHolder.item_tv_over.setText(new StringBuffer().append(0).append("/").append(1).toString());
            }
        }
        mHolder.layout_item.setTag(fuListBean);
        mHolder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FuListBean fuListBean = (FuListBean) view.getTag();
                Intent intent = new Intent(context, FuPacakageDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("fuListBean", fuListBean);
                intent.putExtra("type", type);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView item_tv_source;
        private TextView item_tv_time;
        private TextView item_tv_live;
        private TextView item_tv_over;
        private LinearLayout layout_item;

        public ViewHolder(View convertView) {
            item_tv_source = convertView.findViewById(R.id.tv_fromuser);
            item_tv_time = convertView.findViewById(R.id.tv_start_time);
            item_tv_live = convertView.findViewById(R.id.tv_live_num);
            item_tv_over = convertView.findViewById(R.id.tv_over_all);
            layout_item = convertView.findViewById(R.id.layout_item);
        }
    }
}
