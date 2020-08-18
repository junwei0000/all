package com.longcheng.lifecareplan.modular.mine.tixianrecord.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.tixianrecord.bean.TXRecordItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.TimeCountdownUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class TXRecordAdapter extends BaseAdapterHelper<TXRecordItemBean> {
    ViewHolder mHolder = null;
    Handler mHandler;
    Activity context;
    List<TXRecordItemBean> list;
    TimeCountdownUtils mTimeCountdownUtils;

    public TXRecordAdapter(Activity context, List<TXRecordItemBean> list, Handler mHandler, int type) {
        super(context, list);
        this.context = context;
        this.list = list;
        this.mHandler = mHandler;
        mTimeCountdownUtils = new TimeCountdownUtils(context);
        if (type == 0 || type == 1) {
            if (timeStatus()) {
                mTimeCountdownUtils.startTime(this);
            }
        }
    }

    public List<TXRecordItemBean> getList() {
        return list;
    }

    public void setList(List<TXRecordItemBean> list) {
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<TXRecordItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_txrecord_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        TXRecordItemBean mOrderItemBean = list.get(position);
        mOrderItemBean.setPosition(position);

        GlideDownLoadImage.getInstance().loadCircleImage(mOrderItemBean.getSponsor_user_avatar(), mHolder.item_iv_sponsoravatar);
        mHolder.item_tv_sponsorname.setText(mOrderItemBean.getSponsor_user_name());
        mHolder.item_tv_sponsorjieeqi.setText(mOrderItemBean.getSponsor_user_jieqi_name());
        mHolder.item_layout_sponsorshenfen.removeAllViews();
        ArrayList<TXRecordItemBean> Sponsor_user_flag = mOrderItemBean.getSponsor_user_flag();
        if (Sponsor_user_flag != null && Sponsor_user_flag.size() > 0) {
            for (TXRecordItemBean info : Sponsor_user_flag) {
                LinearLayout linearLayout = new LinearLayout(context);
                ImageView imageView = new ImageView(context);
                int dit = DensityUtil.dip2px(context, 13);
                int jian = DensityUtil.dip2px(context, 2);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(context, info.getImage(), imageView);
                linearLayout.addView(imageView);
                mHolder.item_layout_sponsorshenfen.addView(linearLayout);
            }
        }
        mHolder.item_tv_price.setText(mOrderItemBean.getPrice() + "元");

        GlideDownLoadImage.getInstance().loadCircleImage(mOrderItemBean.getBless_user_avatar(), mHolder.item_iv_blessavatar);
        mHolder.item_tv_blessname.setText(mOrderItemBean.getBless_user_name());
        mHolder.item_tv_blessjieeqi.setText(mOrderItemBean.getBless_user_jieqi_name());
        mHolder.item_layout_blessshenfen.removeAllViews();
        ArrayList<TXRecordItemBean> Bless_user_flag = mOrderItemBean.getBless_user_flag();
        if (Bless_user_flag != null && Bless_user_flag.size() > 0) {
            for (TXRecordItemBean info : Bless_user_flag) {
                LinearLayout linearLayout = new LinearLayout(context);
                ImageView imageView = new ImageView(context);
                int dit = DensityUtil.dip2px(context, 13);
                int jian = DensityUtil.dip2px(context, 2);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(context, info.getImage(), imageView);
                linearLayout.addView(imageView);
                mHolder.item_layout_blessshenfen.addView(linearLayout);
            }
        }
        mHolder.item_tv_type.setVisibility(View.VISIBLE);
        mHolder.item_tv_rigth.setVisibility(View.GONE);
        mHolder.item_layout_rigth.setVisibility(View.VISIBLE);
        //1显示倒计时 2显示未接单 3进行中  4已完成
        int type = mOrderItemBean.getShow_type();
        if (type == 1) {
            mHolder.item_tv_rigth.setVisibility(View.VISIBLE);
            mHolder.item_layout_rigth.setVisibility(View.GONE);
            mHolder.item_tv_type.setVisibility(View.GONE);
            long Match_time = mOrderItemBean.getMatch_time();
            long Remainingtime = Match_time - System.currentTimeMillis() / 1000;
            if (Remainingtime > 1) {
                mTimeCountdownUtils.setTimeShow(Remainingtime, mHolder.item_tv_rigth);
            } else {
                mOrderItemBean.setShow_type(2);
                notifyDataSetChanged();
            }
        } else if (type == 2) {
            mHolder.item_tv_rigth.setText("未接单");
            mHolder.item_tv_rigth.setVisibility(View.VISIBLE);
            mHolder.item_layout_rigth.setVisibility(View.GONE);
            mHolder.item_tv_type.setText("接单");
            mHolder.item_tv_type.setTextColor(context.getResources().getColor(R.color.white));
            mHolder.item_tv_type.setBackgroundResource(R.drawable.corners_bg_red);
        } else if (type == 3) {
            mHolder.item_tv_type.setText("进行中");
            mHolder.item_tv_type.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            mHolder.item_tv_type.setBackgroundResource(R.drawable.corners_bg_write);
        } else {
            mHolder.item_tv_type.setText("已完成");
            mHolder.item_tv_type.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            mHolder.item_tv_type.setBackgroundResource(R.drawable.corners_bg_write);
        }
        mHolder.item_tv_type.setTag(mOrderItemBean);
        mHolder.item_tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TXRecordItemBean mOrderItemBean = (TXRecordItemBean) v.getTag();
                if (mOrderItemBean.getShow_type() == 2) {
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = mOrderItemBean.getUser_zhufubao_order_id();
                    mHandler.sendMessage(message);
                }
            }
        });
        return convertView;
    }


    /**
     * 退出时清除
     */
    public void clearTimer() {
        mTimeCountdownUtils.clearTimer();
    }

    /**
     * 判断是否有倒计时
     *
     * @return
     */
    private boolean timeStatus() {
        boolean status = false;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                int Show_type = list.get(i).getShow_type();
                if (Show_type == 1) {
                    long Match_time = list.get(i).getMatch_time();
                    long Remainingtime = Match_time - System.currentTimeMillis() / 1000;
                    if (Remainingtime > 0) {
                        status = true;
                        break;
                    }
                }
            }
        }
        return status;
    }


    private class ViewHolder {
        private ImageView item_iv_sponsoravatar;
        private TextView item_tv_sponsorname;
        private TextView item_tv_sponsorjieeqi;
        private LinearLayout item_layout_sponsorshenfen;
        private TextView item_tv_price;
        private TextView item_tv_type;
        private ImageView item_iv_blessavatar;
        private TextView item_tv_blessname;
        private TextView item_tv_blessjieeqi;
        private LinearLayout item_layout_blessshenfen;
        private TextView item_tv_rigth;
        private LinearLayout item_layout_rigth;

        public ViewHolder(View view) {
            item_iv_sponsoravatar = view.findViewById(R.id.item_iv_sponsoravatar);
            item_tv_sponsorname = view.findViewById(R.id.item_tv_sponsorname);
            item_tv_sponsorjieeqi = view.findViewById(R.id.item_tv_sponsorjieeqi);
            item_layout_sponsorshenfen = view.findViewById(R.id.item_layout_sponsorshenfen);
            item_tv_price = view.findViewById(R.id.item_tv_price);
            item_tv_type = view.findViewById(R.id.item_tv_type);
            item_iv_blessavatar = view.findViewById(R.id.item_iv_blessavatar);
            item_tv_blessname = view.findViewById(R.id.item_tv_blessname);
            item_tv_blessjieeqi = view.findViewById(R.id.item_tv_blessjieeqi);
            item_layout_blessshenfen = view.findViewById(R.id.item_layout_blessshenfen);
            item_tv_rigth = view.findViewById(R.id.item_tv_rigth);
            item_layout_rigth = view.findViewById(R.id.item_layout_rigth);
        }
    }
}
