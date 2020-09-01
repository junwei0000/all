package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity.cz.PionCZBaseFrag;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity.cz.PionCZRecordActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.bean.PionDaiCItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.TimeCountdownUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.share.ShareHelper;

import java.util.List;


public class PionCZRecordAdapter extends BaseAdapterHelper<PionDaiCItemBean> {
    ViewHolder mHolder = null;
    Context context;

    Handler mHandler;
    TimeCountdownUtils mTimeCountdownUtils;
    int type;

    public PionCZRecordAdapter(Context context, List<PionDaiCItemBean> list, Handler mHandler, int type) {
        super(context, list);
        this.context = context;
        this.mHandler = mHandler;
        this.type = type;
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

        int is_group_order = mDaiFuItemBean.getIs_group_order();//是否组队订单：1 是, 0 否；=1的时候祝福师和用户取消按钮去掉
        if (is_group_order == 1) {
            mHolder.item_tv_grouptype.setVisibility(View.VISIBLE);
        } else {
            mHolder.item_tv_grouptype.setVisibility(View.GONE);
        }

        mHolder.item_tv_date.setText(mDaiFuItemBean.getCreate_time_date());
        mHolder.item_tv_date.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
        int bless_user_status = mDaiFuItemBean.getBless_user_status();
        int user_status = mDaiFuItemBean.getUser_status();
        String price = mDaiFuItemBean.getPrice();
        mHolder.item_tv_price.setText(price + "祝佑宝");
        mHolder.item_tv_typetitle.setText("已完成");
        mHolder.item_layout_btn.setVisibility(View.GONE);
        ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_tv_rigth, R.color.red);
        if (user_status == 0 && bless_user_status == 0) {
            mHolder.item_tv_typetitle.setText("匹配成功");
            mHolder.item_tv_left.setText("取消");
            mHolder.item_tv_rigth.setVisibility(View.GONE);
            mHolder.item_layout_btn.setVisibility(View.VISIBLE);

            int is_bless_agree_recharge = mDaiFuItemBean.getIs_bless_agree_recharge();
            if (is_bless_agree_recharge == 1) {
                mHolder.item_tv_rigth.setVisibility(View.VISIBLE);
                mHolder.item_tv_rigth.setText("已打款");
                //倒计时
                mHolder.item_tv_date.setText("等待对方确认");
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
            if (is_group_order == 1) {
                mHolder.item_tv_left.setVisibility(View.GONE);
                mHolder.item_tv_rigth.getLayoutParams().width = DensityUtil.dip2px(context, 200);
            } else {
                mHolder.item_tv_left.setVisibility(View.VISIBLE);
                mHolder.item_tv_rigth.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            }
        } else if (user_status == -1) {
            mHolder.item_tv_typetitle.setText("已取消");
        } else if (user_status == 1 && bless_user_status == 0) {
            mHolder.item_tv_typetitle.setText("等待充值");
        } else if (user_status == 1 && bless_user_status == 1) {
            mHolder.item_tv_typetitle.setText("充值成功");
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
                int user_consult_status = mDaiFuItemBean.getUser_consult_status();
                if (user_consult_status != 0) {
                    mHolder.item_tv_typetitle.setText("协商");
                    mHolder.item_layout_btn.setVisibility(View.GONE);
                }
            } else if (consult_status == -1) {
                mHolder.item_tv_typetitle.setText("协商取消");
            }
        }
        showPayView(mDaiFuItemBean);
        mHolder.item_layout_phone.setTag(mDaiFuItemBean.getPhone());
        mHolder.item_layout_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = (String) v.getTag();
                DensityUtil.getPhoneToKey(context, phone);
            }
        });
        Log.e("item_tv_left", "bless_user_status=" + bless_user_status + "  user_status=" + user_status);
        mHolder.item_tv_left.setTag(mDaiFuItemBean);
        mHolder.item_tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PionDaiCItemBean mDaiFuItemBean = (PionDaiCItemBean) v.getTag();
                int bless_user_status = mDaiFuItemBean.getBless_user_status();
                int user_status = mDaiFuItemBean.getUser_status();
                String user_zhufubao_order_id = mDaiFuItemBean.getUser_zhufubao_order_id();
                Log.e("item_tv_left", "bless_user_status=" + bless_user_status + "  user_status=" + user_status);
                Message message = new Message();
                if (user_status == 0 && bless_user_status == 0) {//取消
                    message.what = PionCZBaseFrag.Refuse;
                    message.obj = user_zhufubao_order_id;
                    message.arg1 = mDaiFuItemBean.getPosition();
                    mHandler.sendMessage(message);
                } else if (user_status == 1 && bless_user_status == -1) {
                    message.what = PionCZBaseFrag.RefuseTiXian;
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
                if (user_status == 0 && bless_user_status == 0) {// 已打款
                    message.what = PionCZBaseFrag.blessPaymentConfirm;
                    message.obj = user_zhufubao_order_id;
                    message.arg1 = mDaiFuItemBean.getPosition();
                    mHandler.sendMessage(message);
                } else if (user_status == 1 && bless_user_status == -1) {
                    message.what = PionCZBaseFrag.ConfirmTiXian;
                    message.obj = user_zhufubao_order_id;
                    message.arg1 = mDaiFuItemBean.getPosition();
                    mHandler.sendMessage(message);
                }
            }
        });
        return convertView;
    }

    /**
     * 显示支付方式
     *
     * @param mDaiFuItemBean
     */
    private void showPayView(PionDaiCItemBean mDaiFuItemBean) {
        mHolder.item_layout_payshow.removeAllViews();
        View payView = LayoutInflater.from(context).inflate(R.layout.energycenter_daifu_paytype_item, null);
        PionDaiFuItemBean pay_lists = mDaiFuItemBean.getPay_lists();
        showItemView(payView, pay_lists);
    }

    private void showItemView(View payView, PionDaiFuItemBean pay_lists) {
        LinearLayout item_layout_wx = payView.findViewById(R.id.item_layout_wx);
        LinearLayout item_layout_zfb = payView.findViewById(R.id.item_layout_zfb);
        TextView item_tv_wxaccount = payView.findViewById(R.id.item_tv_wxaccount);
        TextView item_tv_wxdrigth = payView.findViewById(R.id.item_tv_wxdrigth);
        TextView item_tv_zfbaccount = payView.findViewById(R.id.item_tv_zfbaccount);
        TextView item_tv_zfbrigth = payView.findViewById(R.id.item_tv_zfbrigth);
        item_tv_wxdrigth.getPaint().setAntiAlias(true);//抗锯齿
        item_tv_wxdrigth.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        item_tv_zfbrigth.getPaint().setAntiAlias(true);//抗锯齿
        item_tv_zfbrigth.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        item_tv_wxaccount.getBackground().setAlpha(20);
        item_tv_zfbaccount.getBackground().setAlpha(20);
        String wxaccount = "";
        String zfbaccount = "";
        if (pay_lists != null && type == 1) {
            mHolder.item_layout_payinfo.setVisibility(View.VISIBLE);
            PionDaiFuItemBean weixin_pay = pay_lists.getWeixin_pay();
            PionDaiFuItemBean alipay_pay = pay_lists.getAlipay_pay();
            if (weixin_pay != null) {
                wxaccount = weixin_pay.getAccount();
            }
            if (alipay_pay != null) {
                zfbaccount = alipay_pay.getAccount();
            }

            Log.e("PionDaiFuItemBean", "wxaccount=" + wxaccount + "  zfbaccount=" + zfbaccount);
            if (TextUtils.isEmpty(wxaccount) && TextUtils.isEmpty(zfbaccount)) {
                mHolder.item_layout_payinfo.setVisibility(View.GONE);
            } else {
                if (!TextUtils.isEmpty(wxaccount)) {
                    item_tv_wxaccount.setText(wxaccount);
                    item_layout_wx.setVisibility(View.VISIBLE);
                } else {
                    item_layout_wx.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(zfbaccount)) {
                    item_tv_zfbaccount.setText(zfbaccount);
                    item_layout_zfb.setVisibility(View.VISIBLE);
                } else {
                    item_layout_zfb.setVisibility(View.GONE);
                }
            }
        } else {
            mHolder.item_layout_payinfo.setVisibility(View.GONE);
        }
        mHolder.item_layout_payshow.addView(payView);
        item_tv_wxdrigth.setTag(wxaccount);
        item_tv_wxdrigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wxaccount = (String) v.getTag();
                PriceUtils.getInstance().copy((Activity) context, wxaccount);
                ShareHelper.getINSTANCE().skipPackNameApp(context, ShareHelper.packName_WX);
            }
        });
        item_tv_zfbrigth.setTag(zfbaccount);
        item_tv_zfbrigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zfbaccount = (String) v.getTag();
                PriceUtils.getInstance().copy((Activity) context, zfbaccount);
                ShareHelper.getINSTANCE().skipPackNameApp(context, ShareHelper.packName_ZFB);
            }
        });
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

        private LinearLayout item_layout_payinfo;
        private LinearLayout item_layout_payshow;

        private TextView item_tv_grouptype;

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

            item_layout_payinfo = convertView.findViewById(R.id.item_layout_payinfo);
            item_layout_payshow = convertView.findViewById(R.id.item_layout_payshow);
            item_tv_grouptype = convertView.findViewById(R.id.item_tv_grouptype);
        }
    }
}
