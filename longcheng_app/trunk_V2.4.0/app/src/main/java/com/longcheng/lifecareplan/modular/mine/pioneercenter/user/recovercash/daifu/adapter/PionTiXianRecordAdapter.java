package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
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
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.bean.PionDaiCItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.activity.PionRecoverMatchActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.activity.tx.PionTiXianBaseFrag;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.activity.tx.PionTiXianRecordActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.TimeCountdownUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;


public class PionTiXianRecordAdapter extends BaseAdapterHelper<PionDaiFuItemBean> {
    ViewHolder mHolder = null;
    Context context;

    Handler mHandler;
    int type;
    TimeCountdownUtils mTimeCountdownUtils;

    public PionTiXianRecordAdapter(Context context, List<PionDaiFuItemBean> list, Handler mHandler, int type) {
        super(context, list);
        this.context = context;
        this.mHandler = mHandler;
        this.type = type;
        if (timeStatus()) {
            mTimeCountdownUtils = new TimeCountdownUtils((Activity) context);
            mTimeCountdownUtils.startTime(this);
        }
    }

    /**
     * 退出时清除
     */
    public void clearTimer() {
        if (mTimeCountdownUtils != null)
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
                PionDaiFuItemBean mDaiFuItemBean = list.get(i);
                int match_status = mDaiFuItemBean.getMatch_status();
                if (match_status == 0) {
                    status = true;
                    break;
                }
            }
        }
        return status;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<PionDaiFuItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.energycenter_daifu_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        PionDaiFuItemBean mDaiFuItemBean = list.get(position);
        mDaiFuItemBean.setPosition(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mDaiFuItemBean.getUser_avatar(), mHolder.item_iv_img);
        String name = CommonUtil.setName(mDaiFuItemBean.getUser_name());
        String price = mDaiFuItemBean.getPrice();
        mHolder.item_tv_name.setText(name);
        mHolder.item_tv_price.setText(price);
        mHolder.item_tv_jieqi.setText(mDaiFuItemBean.getJieqi_name());
        mHolder.item_tv_phone.setText(mDaiFuItemBean.getPhone());
        mHolder.item_tv_date.setText(mDaiFuItemBean.getCreate_time());
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
        int match_status = mDaiFuItemBean.getMatch_status();
        if (match_status == 0) {
            String title = mHolder.item_tv_typetitle.getText().toString();

            mHolder.item_layout_btn2.setVisibility(View.GONE);
            mHolder.item_tv_detail.setVisibility(View.VISIBLE);
            mHolder.item_layout_btn.setVisibility(View.VISIBLE);
            mHolder.item_tv_typetitle2.setText("");
            Log.e("WaveView", "" + title);
            if (TextUtils.isEmpty(title)) {
                mHolder.item_tv_typetitle.setText("匹配中...");
            } else if (!TextUtils.isEmpty(title) && title.equals("匹配中...")) {
                mHolder.item_tv_typetitle.setText("匹配中.");
                mHolder.item_tv_typetitle2.setText("..");
            } else if (!TextUtils.isEmpty(title) && title.equals("匹配中.")) {
                mHolder.item_tv_typetitle.setText("匹配中..");
                mHolder.item_tv_typetitle2.setText(".");
            } else if (!TextUtils.isEmpty(title) && title.equals("匹配中..")) {
                mHolder.item_tv_typetitle.setText("匹配中...");
            }
        } else {
            mHolder.item_tv_typetitle2.setText("");
            mHolder.item_layout_btn2.setVisibility(View.VISIBLE);
            mHolder.item_tv_detail.setVisibility(View.GONE);
            mHolder.item_layout_btn.setVisibility(View.GONE);
            int bless_user_status = mDaiFuItemBean.getBless_user_status();
            int user_status = mDaiFuItemBean.getUser_status();
            mHolder.item_tv_typetitle.setText("已完成");
            ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_tv_rigth, R.color.red);
            if (user_status == 0 && bless_user_status == 0) {
                mHolder.item_tv_typetitle.setText("待处理");
            } else if (user_status == 0 && bless_user_status == 1) {
                mHolder.item_tv_left.setText("撤回");
                mHolder.item_tv_rigth.setText("已收款");
                mHolder.item_tv_typetitle.setText("待处理");
                mHolder.item_layout_btn.setVisibility(View.VISIBLE);
            } else if (user_status == 0 && bless_user_status == -1) {
                mHolder.item_tv_typetitle.setText("已驳回");
            } else if (user_status == -1) {
                mHolder.item_tv_typetitle.setText("协商");
                mHolder.item_layout_btn.setVisibility(View.VISIBLE);
                mHolder.item_tv_left.setText("撤销订单");
                mHolder.item_tv_rigth.setText("完成订单");
                int user_consult_status = mDaiFuItemBean.getUser_consult_status();
                int consult_status = mDaiFuItemBean.getConsult_status();
                if (consult_status == 0 && user_consult_status != 0) {
                    mHolder.item_tv_typetitle.setText("协商中待对方处理");
                    mHolder.item_layout_btn.setVisibility(View.GONE);
                } else if (consult_status == -1) {
                    mHolder.item_tv_typetitle.setText("协商取消");
                    mHolder.item_layout_btn.setVisibility(View.GONE);
                } else if (consult_status == 1) {
                    mHolder.item_tv_typetitle.setText("已完成");
                    mHolder.item_layout_btn.setVisibility(View.GONE);
                }
            }
            showPayView(mDaiFuItemBean);
        }
        mHolder.item_tv_detail.setTag(mDaiFuItemBean.getPhone());
        mHolder.item_tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PionRecoverMatchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        });
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
                PionDaiFuItemBean mDaiFuItemBean = (PionDaiFuItemBean) v.getTag();
                int bless_user_status = mDaiFuItemBean.getBless_user_status();
                int user_status = mDaiFuItemBean.getUser_status();
                String user_zhufubao_order_id = mDaiFuItemBean.getUser_zhufubao_order_id();
                Message message = new Message();
                if (user_status == 0 && bless_user_status == 1) {//撤回
                    message.what = PionTiXianBaseFrag.Refuse;
                    message.obj = user_zhufubao_order_id;
                    message.arg1 = mDaiFuItemBean.getPosition();
                    mHandler.sendMessage(message);
                } else if (user_status == -1) {
                    message.what = PionTiXianBaseFrag.RefuseTiXian;
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
                PionDaiFuItemBean mDaiFuItemBean = (PionDaiFuItemBean) v.getTag();
                String user_zhufubao_order_id = mDaiFuItemBean.getUser_zhufubao_order_id();
                int bless_user_status = mDaiFuItemBean.getBless_user_status();
                int user_status = mDaiFuItemBean.getUser_status();
                Message message = new Message();
                if (user_status == 0 && bless_user_status == 1) {// 已收款
                    message.what = PionTiXianBaseFrag.blessPaymentConfirm;
                    message.obj = user_zhufubao_order_id;
                    message.arg1 = mDaiFuItemBean.getPosition();
                    mHandler.sendMessage(message);
                } else if (user_status == -1) {
                    message.what = PionTiXianBaseFrag.ConfirmTiXian;
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
    private void showPayView(PionDaiFuItemBean mDaiFuItemBean) {
        mHolder.item_tv_wxdrigth.getPaint().setAntiAlias(true);//抗锯齿
        mHolder.item_tv_wxdrigth.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mHolder.item_tv_zfbrigth.getPaint().setAntiAlias(true);//抗锯齿
        mHolder.item_tv_zfbrigth.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mHolder.item_tv_wxaccount.getBackground().setAlpha(20);
        mHolder.item_tv_zfbaccount.getBackground().setAlpha(20);
        String wxaccount = "";
        String zfbaccount = "";
        PionDaiFuItemBean pay_lists = mDaiFuItemBean.getPay_lists();
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
                    mHolder.item_tv_wxaccount.setText(wxaccount);
                    mHolder.item_layout_wx.setVisibility(View.VISIBLE);
                } else {
                    mHolder.item_layout_wx.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(zfbaccount)) {
                    mHolder.item_tv_zfbaccount.setText(zfbaccount);
                    mHolder.item_layout_zfb.setVisibility(View.VISIBLE);
                } else {
                    mHolder.item_layout_zfb.setVisibility(View.GONE);
                }
            }
        } else {
            mHolder.item_layout_payinfo.setVisibility(View.GONE);
        }
        mHolder.item_tv_wxdrigth.setTag(wxaccount);
        mHolder.item_tv_wxdrigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wxaccount = (String) v.getTag();
                PriceUtils.getInstance().copy((Activity) context, wxaccount);
                Intent lan = context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(lan.getComponent());
                context.startActivity(intent);
            }
        });
        mHolder.item_tv_zfbrigth.setTag(zfbaccount);
        mHolder.item_tv_zfbrigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zfbaccount = (String) v.getTag();
                PriceUtils.getInstance().copy((Activity) context, zfbaccount);
                Intent lan = context.getPackageManager().getLaunchIntentForPackage("com.eg.android.AlipayGphone");
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(lan.getComponent());
                context.startActivity(intent);
            }
        });
    }

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name;
        private TextView item_tv_jieqi;
        private TextView item_tv_phone;
        private LinearLayout item_layout_shenfen;
        private TextView item_tv_price;
        private TextView item_tv_date;


        private TextView item_tv_detail;
        private LinearLayout item_layout_btn2;
        private TextView item_tv_left;
        private TextView item_tv_rigth;
        private LinearLayout item_layout_btn;
        private LinearLayout item_layout_phone;
        private TextView item_tv_typetitle;
        private TextView item_tv_typetitle2;

        private LinearLayout item_layout_payinfo;
        private LinearLayout item_layout_wx;
        private LinearLayout item_layout_zfb;
        private TextView item_tv_wxaccount;
        private TextView item_tv_wxdrigth;
        private TextView item_tv_zfbaccount;
        private TextView item_tv_zfbrigth;

        public ViewHolder(View convertView) {
            item_iv_img = convertView.findViewById(R.id.item_iv_img);
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_jieqi = convertView.findViewById(R.id.item_tv_jieqi);
            item_tv_phone = convertView.findViewById(R.id.item_tv_phone);
            item_layout_shenfen = convertView.findViewById(R.id.item_layout_shenfen);
            item_tv_price = convertView.findViewById(R.id.item_tv_price);
            item_tv_date = convertView.findViewById(R.id.item_tv_date);

            item_tv_detail = convertView.findViewById(R.id.item_tv_detail);
            item_layout_btn2 = convertView.findViewById(R.id.item_layout_btn2);

            item_tv_left = convertView.findViewById(R.id.item_tv_left);
            item_tv_rigth = convertView.findViewById(R.id.item_tv_rigth);
            item_layout_btn = convertView.findViewById(R.id.item_layout_btn);
            item_layout_phone = convertView.findViewById(R.id.item_layout_phone);
            item_tv_typetitle = convertView.findViewById(R.id.item_tv_typetitle);
            item_tv_typetitle2 = convertView.findViewById(R.id.item_tv_typetitle2);
            item_layout_payinfo = convertView.findViewById(R.id.item_layout_payinfo);
            item_layout_wx = convertView.findViewById(R.id.item_layout_wx);
            item_layout_zfb = convertView.findViewById(R.id.item_layout_zfb);
            item_tv_wxaccount = convertView.findViewById(R.id.item_tv_wxaccount);
            item_tv_wxdrigth = convertView.findViewById(R.id.item_tv_wxdrigth);
            item_tv_zfbaccount = convertView.findViewById(R.id.item_tv_zfbaccount);
            item_tv_zfbrigth = convertView.findViewById(R.id.item_tv_zfbrigth);
        }
    }
}
