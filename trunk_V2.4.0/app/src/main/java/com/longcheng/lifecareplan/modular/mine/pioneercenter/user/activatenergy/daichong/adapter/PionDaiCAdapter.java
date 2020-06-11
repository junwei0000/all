package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity.dc.PionDaiCActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity.dc.PionDaiCBaseFrag;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.bean.PionDaiCItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.TimeCountdownUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;


public class PionDaiCAdapter extends BaseAdapterHelper<PionDaiCItemBean> {
    ViewHolder mHolder = null;
    Context context;
    Handler mHandler;
    TimeCountdownUtils mTimeCountdownUtils;

    public PionDaiCAdapter(Context context, List<PionDaiCItemBean> list, Handler mHandler) {
        super(context, list);
        this.context = context;
        this.mHandler = mHandler;
        mTimeCountdownUtils = new TimeCountdownUtils((Activity) context);
        if (timeStatus()) {
            mTimeCountdownUtils.startTime(this);
        }
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
                PionDaiCItemBean mDaiFuItemBean = list.get(i);
                int bless_user_status = mDaiFuItemBean.getBless_user_status();
                int user_status = mDaiFuItemBean.getUser_status();
                if (user_status == 0 && bless_user_status == 0) {
                    int is_bless_agree_recharge = mDaiFuItemBean.getIs_bless_agree_recharge();
                    if (is_bless_agree_recharge == 1) {
                        int agree_expire_time = mDaiFuItemBean.getAgree_expire_time();
                        long matchtime = agree_expire_time - System.currentTimeMillis() / 1000;
                        if (matchtime > 1) {
                            status = true;
                            break;
                        }
                    }
                }
            }
        }
        return status;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<PionDaiCItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.energycenter_daifu_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        PionDaiCItemBean mDaiFuItemBean = list.get(position);
        mDaiFuItemBean.setPosition(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mDaiFuItemBean.getAvatar(), mHolder.item_iv_img);
        String name = CommonUtil.setName(mDaiFuItemBean.getUser_name());
        mHolder.item_tv_name.setText(name);
        mHolder.item_tv_jieqi.setText(mDaiFuItemBean.getJieqi_name());
        mHolder.item_tv_phone.setText(mDaiFuItemBean.getPhone());
        mHolder.item_tv_date.setText(mDaiFuItemBean.getCreate_time_date());
        List<String> identity_img = mDaiFuItemBean.getImg_all();
        mHolder.item_layout_shenfen.removeAllViews();
        if (identity_img != null && identity_img.size() > 0) {
            for (String url : identity_img) {
                LinearLayout linearLayout = new LinearLayout(context);
                ImageView imageView = new ImageView(context);
                int dit = DensityUtil.dip2px(context, 16);
                int jian = DensityUtil.dip2px(context, 2);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(context, url, imageView);
                linearLayout.addView(imageView);
                mHolder.item_layout_shenfen.addView(linearLayout);
            }
        }

        int bless_user_status = mDaiFuItemBean.getBless_user_status();
        int user_status = mDaiFuItemBean.getUser_status();
        String price = mDaiFuItemBean.getPrice();
        mHolder.item_tv_price.setText(price + "祝佑宝");
        mHolder.item_tv_typetitle.setText("已完成");
        mHolder.item_layout_btn.setVisibility(View.GONE);
        ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_tv_rigth, R.color.red);
        if (user_status == 0 && bless_user_status == 0) {
            mHolder.item_tv_typetitle.setText("待处理");
            mHolder.item_tv_left.setText("拒绝申请");
            mHolder.item_tv_rigth.setText("同意申请");
            mHolder.item_layout_btn.setVisibility(View.VISIBLE);
            int is_bless_agree_recharge = mDaiFuItemBean.getIs_bless_agree_recharge();
            if (is_bless_agree_recharge == 1) {
                //倒计时
                mHolder.item_layout_btn.setVisibility(View.GONE);
                mHolder.item_tv_date.setTextColor(context.getResources().getColor(R.color.red));

                int agree_expire_time = mDaiFuItemBean.getAgree_expire_time();
                long matchtime = agree_expire_time - System.currentTimeMillis() / 1000;
                if (matchtime > 1) {
                    mTimeCountdownUtils.setTimeShow(matchtime, mHolder.item_tv_date);
                } else {
                    mDaiFuItemBean.setUser_status(-1);
                    notifyDataSetChanged();
                }
            }
        } else if (user_status == -1) {
            mHolder.item_tv_typetitle.setText("已取消");
        } else if (user_status == 1 && bless_user_status == 0) {
            mHolder.item_tv_typetitle.setText("在途");
            mHolder.item_tv_left.setText("未收到款");
            mHolder.item_tv_rigth.setText("确认已收款");
            mHolder.item_layout_btn.setVisibility(View.VISIBLE);
        } else if (user_status == 1 && bless_user_status == 1) {
            mHolder.item_tv_typetitle.setText("已完成");
            int consult_status = mDaiFuItemBean.getConsult_status();
            if (consult_status == 1) {
                mHolder.item_tv_typetitle.setText("协商完成");
            }
        } else if (user_status == 1 && bless_user_status == -1) {
            int consult_status = mDaiFuItemBean.getConsult_status();
            if (consult_status == 0) {
                mHolder.item_tv_typetitle.setText("协商");
                mHolder.item_layout_btn.setVisibility(View.VISIBLE);
                mHolder.item_tv_left.setText("撤销订单");
                mHolder.item_tv_rigth.setText("完成订单");
                int bless_consult_status = mDaiFuItemBean.getBless_consult_status();
                if (bless_consult_status != 0) {
                    mHolder.item_tv_typetitle.setText("协商");
                    mHolder.item_layout_btn.setVisibility(View.GONE);
                }
            } else if (consult_status == -1) {
                mHolder.item_tv_typetitle.setText("协商取消");
            } else if (consult_status == 1) {
                mHolder.item_tv_typetitle.setText("协商完成");
            }
        }
        mHolder.item_layout_phone.setTag(mDaiFuItemBean.getPhone());
        mHolder.item_layout_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = (String) v.getTag();
                DensityUtil.getPhoneToKey(context, phone);
            }
        });
        mHolder.item_tv_left.setTag(mDaiFuItemBean);
        mHolder.item_tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PionDaiCItemBean mDaiFuItemBean = (PionDaiCItemBean) v.getTag();
                int bless_user_status = mDaiFuItemBean.getBless_user_status();
                int user_status = mDaiFuItemBean.getUser_status();
                String user_zhufubao_order_id = mDaiFuItemBean.getUser_zhufubao_order_id();
                Message message = new Message();
                if (user_status == 0 && bless_user_status == 0) {//拒绝申请
                    message.what = PionDaiCBaseFrag.RefuseApply;
                    message.obj = user_zhufubao_order_id;
                    message.arg1 = mDaiFuItemBean.getPosition();
                    mHandler.sendMessage(message);
                } else if (user_status == 1 && bless_user_status == 0) {
                    message.what = PionDaiCBaseFrag.RefuseDaiC;
                    message.obj = user_zhufubao_order_id;
                    message.arg1 = mDaiFuItemBean.getPosition();
                    mHandler.sendMessage(message);
                } else if (user_status == 1 && bless_user_status == -1) {
                    message.what = PionDaiCBaseFrag.RefuseOrder;
                    message.obj = user_zhufubao_order_id;
                    message.arg1 = mDaiFuItemBean.getPosition();
                    mHandler.sendMessage(message);
                }
            }
        });
        mHolder.item_tv_rigth.setTag(mDaiFuItemBean);
        mHolder.item_tv_rigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PionDaiCItemBean mDaiFuItemBean = (PionDaiCItemBean) v.getTag();
                String user_zhufubao_order_id = mDaiFuItemBean.getUser_zhufubao_order_id();
                int bless_user_status = mDaiFuItemBean.getBless_user_status();
                int user_status = mDaiFuItemBean.getUser_status();
                Message message = new Message();
                if (user_status == 0 && bless_user_status == 0) {// 申请
                    message.what = PionDaiCBaseFrag.AgreeApply;
                    message.obj = user_zhufubao_order_id;
                    message.arg1 = mDaiFuItemBean.getPosition();
                    mHandler.sendMessage(message);
                } else if (user_status == 1 && bless_user_status == 0) {
                    message.what = PionDaiCBaseFrag.AgreeDaiC;
                    message.obj = user_zhufubao_order_id;
                    message.arg1 = mDaiFuItemBean.getPosition();
                    mHandler.sendMessage(message);
                } else if (user_status == 1 && bless_user_status == -1) {
                    message.what = PionDaiCBaseFrag.ConfirmOrder;
                    message.obj = user_zhufubao_order_id;
                    message.arg1 = mDaiFuItemBean.getPosition();
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

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name;
        private TextView item_tv_jieqi;
        private TextView item_tv_phone;
        private LinearLayout item_layout_shenfen;
        private TextView item_tv_price;
        private TextView item_tv_date;

        private TextView item_tv_left;
        private TextView item_tv_rigth;
        private LinearLayout item_layout_btn;
        private LinearLayout item_layout_phone;

        private TextView item_tv_typetitle;

        public ViewHolder(View convertView) {
            item_iv_img = convertView.findViewById(R.id.item_iv_img);
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_jieqi = convertView.findViewById(R.id.item_tv_jieqi);
            item_tv_phone = convertView.findViewById(R.id.item_tv_phone);
            item_layout_shenfen = convertView.findViewById(R.id.item_layout_shenfen);
            item_tv_price = convertView.findViewById(R.id.item_tv_price);
            item_tv_date = convertView.findViewById(R.id.item_tv_date);
            item_tv_left = convertView.findViewById(R.id.item_tv_left);
            item_tv_rigth = convertView.findViewById(R.id.item_tv_rigth);
            item_layout_btn = convertView.findViewById(R.id.item_layout_btn);
            item_layout_phone = convertView.findViewById(R.id.item_layout_phone);
            item_tv_typetitle = convertView.findViewById(R.id.item_tv_typetitle);
        }
    }
}
