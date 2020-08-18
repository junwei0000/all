package com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.FuQApplyActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.FuQDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.FuQListActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ListItemBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.bean.PionDaiCItemBean;
import com.longcheng.lifecareplan.utils.TimeCountdownUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class FuQRepListAdapter extends BaseAdapterHelper<ListItemBean> {
    ViewHolder mHolder = null;
    Activity context;
    TimeCountdownUtils mTimeCountdownUtils;
    int type;
    Handler mHandler;
    int hei;

    public FuQRepListAdapter(Activity context, List<ListItemBean> list, int type, Handler mHandler) {
        super(context, list);
        this.context = context;
        this.type = type;
        this.mHandler = mHandler;
        mTimeCountdownUtils = new TimeCountdownUtils(context);
        if (timeStatus()) {
            mTimeCountdownUtils.startTime(this);
        }
        hei = BitmapFactory.decodeResource(context.getResources(), R.mipmap.fq_item_rigth).getHeight() * 3 / 4;// 获取图片宽度

    }

    /**
     * 判断是否有倒计时
     *
     * @return
     */
    private boolean timeStatus() {
        boolean status = false;
        if (type == 1 && list != null && list.size() > 0) {
            status = true;
        }
        return status;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<ListItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fuqrep_list_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        ListItemBean mOrderItemBean = list.get(position);
        mHolder.tv_jieqi.setText(mOrderItemBean.getSolar_terms_name() + " . 福券");
        String color = mOrderItemBean.getSolar_terms_en_rgb();
        ColorChangeByTime.getInstance().changeDrawableToClolorNew(context, mHolder.tv_jieqi, Color.parseColor(color));
        mHolder.tv_bianma.setText(mOrderItemBean.getBless_card_number());
        mHolder.tv_desc.setText(mOrderItemBean.getDesc());
        mHolder.iv_jieqihua.setLayoutParams(new LinearLayout.LayoutParams((int) (hei * 1.2857), hei));
        GlideDownLoadImage.getInstance().loadCircleImageHelpIndex(context, mOrderItemBean.getSolar_terms_en_pic(), mHolder.iv_jieqihua);
        mHolder.tv_status2.setVisibility(View.VISIBLE);
        if (type == 1) {
            mHolder.tv_status.setText("立 即\n使 用");
            int over_time = mOrderItemBean.getOver_time();
            long matchtime = over_time - System.currentTimeMillis() / 1000;
            if (matchtime > 1) {
                mTimeCountdownUtils.setTimeShow(matchtime, mHolder.tv_status2);
            } else {
                list.remove(position);
                notifyDataSetChanged();
            }
        } else {
            int status = mOrderItemBean.getStatus();//1 进行中 ；2 已完成
            if (status == 1) {
                mHolder.tv_status.setText("查 看\n详 情");
                mHolder.tv_status2.setText("集福中");
            } else if (status == 2) {
                mHolder.tv_status.setText("提 到\n福 祺 宝");
                mHolder.tv_status2.setText("");
                mHolder.tv_status2.setVisibility(View.GONE);
            } else {
                mHolder.tv_status.setText("福 气\n满 满");
                mHolder.tv_status2.setText("");
                mHolder.tv_status2.setVisibility(View.GONE);
            }
        }

        mHolder.tv_status.setTag(mOrderItemBean);
        mHolder.tv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListItemBean mOrderItemBean = (ListItemBean) v.getTag();
                if (type == 1) {
                    Intent intent = new Intent(context, FuQApplyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(intent);
                } else {
                    int status = mOrderItemBean.getStatus();//1 进行中 ；2 已完成
                    if (status == 2) {
                        Message message = Message.obtain();
                        message.what = 1;
                        message.obj = mOrderItemBean.getBless_id();
                        mHandler.sendMessage(message);
                    } else if (status == 1) {
                        Intent intent = new Intent(context, FuQDetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("bless_id", mOrderItemBean.getBless_id());
                        context.startActivity(intent);
                    }
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

    private class ViewHolder {
        private TextView tv_jieqi;
        private TextView tv_bianma;
        private TextView tv_desc;
        private TextView tv_status;
        private TextView tv_status2;
        private ImageView iv_jieqihua;

        public ViewHolder(View view) {
            tv_jieqi = view.findViewById(R.id.tv_jieqi);
            tv_bianma = view.findViewById(R.id.tv_bianma);
            tv_desc = view.findViewById(R.id.tv_desc);
            tv_status = view.findViewById(R.id.tv_status);
            tv_status2 = view.findViewById(R.id.tv_status2);
            iv_jieqihua = view.findViewById(R.id.iv_jieqihua);
        }
    }
}
