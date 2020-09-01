package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.bean.fupackage.FuListBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao.MineFubaoDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao.MineFubaoSendDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.FuPacakageDetails;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.ReceiveSuccessActivity;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;

import java.util.List;

public class MineFubaoAdapter extends BaseAdapterHelper<FuListBean> {
    private Context context;
    ViewHolder mHolder = null;
    int type;

    public MineFubaoAdapter(Context context, List<FuListBean> list, int type) {
        super(context, list);
        this.context = context;
        this.type = type;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<FuListBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fubao_minefubao_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        FuListBean fuListBean = list.get(position);
        String sponsor_user_name = fuListBean.getSponsor_user_name();//发送人
        String sponsor_super_ability = fuListBean.getSponsor_super_ability();
        sponsor_super_ability = PriceUtils.getInstance().seePrice(sponsor_super_ability);
        String Receive_super_ability = fuListBean.getReceive_super_ability();
        Receive_super_ability = PriceUtils.getInstance().seePrice(Receive_super_ability);
        int create_time = fuListBean.getCreate_time();
        int open_time = fuListBean.getOpen_time();//收到的时间

        int total_number = fuListBean.getTotal_number();
        int finsh_count = fuListBean.getFinsh_count();

        if (type == 1) {
            mHolder.item_tv_name.setText("收到" + CommonUtil.setName3(sponsor_user_name) + "的福包");
            if (open_time > 0) {
                mHolder.item_tv_time.setText(DatesUtils.getInstance().getTimeStampToDate(open_time, "yyyy-MM-dd HH:mm:ss"));
            } else {
                mHolder.item_tv_time.setText("");
            }
            String cont = CommonUtil.getHtmlContentBig("#E95D1B", "" + Receive_super_ability) + " 超级生命能量";
            mHolder.item_tv_price.setText(Html.fromHtml(cont));
            mHolder.item_tv_process.setText(new StringBuffer().append(1).append("/").append(1).toString());
            mHolder.tv_share.setVisibility(View.GONE);
        } else {
            mHolder.item_tv_name.setText("送出" + total_number + "个福包");
            mHolder.item_tv_time.setText(DatesUtils.getInstance().getTimeStampToDate(create_time, "yyyy-MM-dd HH:mm:ss"));
            String cont = CommonUtil.getHtmlContentBig("#E95D1B", "" + sponsor_super_ability) + " 超级生命能量";
            mHolder.item_tv_price.setText(Html.fromHtml(cont));
            mHolder.item_tv_process.setText(new StringBuffer().append(finsh_count).append("/").append(total_number).toString() + "个");
            if (finsh_count < total_number) {
                mHolder.tv_share.setVisibility(View.VISIBLE);
            } else {
                mHolder.tv_share.setVisibility(View.GONE);
            }
        }
        mHolder.tv_share.setTag(fuListBean);
        mHolder.tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FuListBean fuListBean = (FuListBean) view.getTag();
                Intent intent = new Intent(context, ReceiveSuccessActivity.class);
                intent.putExtra("pagetype", 1);
                intent.putExtra("dataurl", fuListBean.getUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        });
        mHolder.layout_item.setTag(fuListBean);
        mHolder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FuListBean fuListBean = (FuListBean) view.getTag();
                if (type == 1) {
                    Intent intent = new Intent(context, MineFubaoDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("fuListBean", fuListBean);
                    intent.putExtra("type", type);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, MineFubaoSendDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("fuListBean", fuListBean);
                    intent.putExtra("type", type);
                    context.startActivity(intent);
                }

            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView item_tv_name;
        private TextView item_tv_time;
        private TextView item_tv_price;
        private TextView item_tv_process;
        private TextView tv_share;
        private RelativeLayout layout_item;

        public ViewHolder(View convertView) {
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_time = convertView.findViewById(R.id.item_tv_time);
            item_tv_price = convertView.findViewById(R.id.item_tv_price);
            item_tv_process = convertView.findViewById(R.id.item_tv_process);
            layout_item = convertView.findViewById(R.id.layout_item);
            tv_share = convertView.findViewById(R.id.tv_share);
        }
    }
}
