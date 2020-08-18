package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.adapter;

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
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.activity.df.PionDaiFuBaseFrag;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;


public class PionDaiFusAdapter extends BaseAdapterHelper<PionDaiFuItemBean> {
    ViewHolder mHolder = null;
    Context context;

    Handler mHandler;
    int type;

    public PionDaiFusAdapter(Context context, List<PionDaiFuItemBean> list, Handler mHandler, int type) {
        super(context, list);
        this.context = context;
        this.mHandler = mHandler;
        this.type = type;
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
        mHolder.item_tv_name.setText(name);
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
        int is_group_order = mDaiFuItemBean.getIs_group_order();//是否组队订单：1 是, 0 否；=1的时候祝福师和用户取消按钮去掉
        if (is_group_order == 1) {
            mHolder.item_tv_grouptype.setVisibility(View.VISIBLE);
        } else {
            mHolder.item_tv_grouptype.setVisibility(View.GONE);
        }
        int bless_user_status = mDaiFuItemBean.getBless_user_status();
        int user_status = mDaiFuItemBean.getUser_status();
        String price = mDaiFuItemBean.getPrice();
        mHolder.item_tv_price.setText(price);
        mHolder.item_tv_typetitle.setText("已完成");
        mHolder.item_layout_btn.setVisibility(View.GONE);
        ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_tv_rigth, R.color.red);
        if (user_status == 0 && bless_user_status == 0) {
            mHolder.item_layout_btn.setVisibility(View.VISIBLE);
            mHolder.item_tv_typetitle.setText("待处理");
            if (is_group_order == 1) {
                mHolder.item_tv_left.setVisibility(View.GONE);
                mHolder.item_tv_rigth.setText("已付款");
                mHolder.item_tv_rigth.getLayoutParams().width = DensityUtil.dip2px(context, 200);
            } else {
                mHolder.item_tv_left.setVisibility(View.VISIBLE);
                mHolder.item_tv_left.setText("驳回");
                mHolder.item_tv_rigth.setText("已打款");
                mHolder.item_tv_rigth.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            }
        } else if (user_status == 0 && bless_user_status == 1) {
            mHolder.item_tv_typetitle.setText("在途");
        } else if (user_status == 0 && bless_user_status == -1) {
            mHolder.item_tv_typetitle.setText("取消");
        } else if (user_status == -1) {
            mHolder.item_tv_typetitle.setText("协商");
            mHolder.item_layout_btn.setVisibility(View.VISIBLE);
            mHolder.item_tv_left.setText("撤销订单");
            mHolder.item_tv_rigth.setText("完成订单");
            int bless_consult_status = mDaiFuItemBean.getBless_consult_status();
            int consult_status = mDaiFuItemBean.getConsult_status();
            if (consult_status == 0 && bless_consult_status != 0) {
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
                if (user_status == 0 && bless_user_status == 0) {//驳回
                    message.what = PionDaiFuBaseFrag.Refuse;
                    message.obj = user_zhufubao_order_id;
                    message.arg1 = mDaiFuItemBean.getPosition();
                    mHandler.sendMessage(message);
                } else if (user_status == -1) {
                    message.what = PionDaiFuBaseFrag.RefuseTiXian;
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
                Message message = new Message();
                if (user_status == 0 && bless_user_status == 0) {// 已打款
                    message.what = PionDaiFuBaseFrag.blessPaymentConfirm;
                    message.obj = user_zhufubao_order_id;
                    message.arg1 = mDaiFuItemBean.getPosition();
                    mHandler.sendMessage(message);
                } else if (user_status == -1) {
                    message.what = PionDaiFuBaseFrag.ConfirmTiXian;
                    message.obj = user_zhufubao_order_id;
                    message.arg1 = mDaiFuItemBean.getPosition();
                    mHandler.sendMessage(message);
                }
            }
        });
        return convertView;
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
            item_tv_grouptype = convertView.findViewById(R.id.item_tv_grouptype);
        }
    }
}
