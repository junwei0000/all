package com.longcheng.lifecareplan.modular.mine.energycenter.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.energycenter.activity.TiXianRecordActivity;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;


public class TiXianRecordAdapter extends BaseAdapterHelper<DaiFuItemBean> {
    ViewHolder mHolder = null;
    Context context;

    Handler mHandler;

    public TiXianRecordAdapter(Context context, List<DaiFuItemBean> list, Handler mHandler) {
        super(context, list);
        this.context = context;
        this.mHandler = mHandler;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<DaiFuItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.energycenter_daifu_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        DaiFuItemBean mDaiFuItemBean = list.get(position);
        mDaiFuItemBean.setPosition(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mDaiFuItemBean.getUser_avatar(), mHolder.item_iv_img);
        String name = mDaiFuItemBean.getUser_name();
        if (!TextUtils.isEmpty(name) && name.length() > 4) {
            name = name.substring(0, 3) + "…";
        }
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

        int bless_user_status = mDaiFuItemBean.getBless_user_status();
        int user_status = mDaiFuItemBean.getUser_status();
        String price = mDaiFuItemBean.getPrice();
        mHolder.item_tv_price.setText(price);
        mHolder.item_tv_typetitle.setText("现金");
        mHolder.item_layout_btn.setVisibility(View.GONE);
        if (user_status == 0 && bless_user_status == 0) {
            mHolder.item_tv_price.setText(price);
            mHolder.item_tv_typetitle.setText("待处理");
        } else if (user_status == 0 && bless_user_status == 1) {
            mHolder.item_tv_price.setText(price);
            mHolder.item_tv_left.setText("撤回");
            mHolder.item_tv_rigth.setText("已收款");
        } else if (user_status == 0 && bless_user_status == -1) {
            mHolder.item_tv_price.setText(price);
            mHolder.item_tv_typetitle.setText("已驳回");
        } else if ((user_status == -1 && bless_user_status == 1) && (user_status == 1 && bless_user_status == -1)) {
            mHolder.item_tv_price.setText(price);
            mHolder.item_tv_typetitle.setText("协商");
            mHolder.item_layout_btn.setVisibility(View.VISIBLE);
            mHolder.item_tv_left.setText("撤销提现");
            mHolder.item_tv_rigth.setText("完成提现");

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
                DaiFuItemBean mDaiFuItemBean = (DaiFuItemBean) v.getTag();
                int bless_user_status = mDaiFuItemBean.getBless_user_status();
                int user_status = mDaiFuItemBean.getUser_status();
                String user_zhufubao_order_id = mDaiFuItemBean.getUser_zhufubao_order_id();
                if (user_status == 0 && bless_user_status == 1) {//撤回
                    Message message = new Message();
                    message.what = TiXianRecordActivity.Refuse;
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
                DaiFuItemBean mDaiFuItemBean = (DaiFuItemBean) v.getTag();
                String user_zhufubao_order_id = mDaiFuItemBean.getUser_zhufubao_order_id();
                if (user_status == 0 && bless_user_status == 1) {// 已收款
                    Message message = new Message();
                    message.what = TiXianRecordActivity.blessPaymentConfirm;
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

        public ViewHolder(View convertView) {
            item_iv_img = (ImageView) convertView.findViewById(R.id.item_iv_img);
            item_tv_name = (TextView) convertView.findViewById(R.id.item_tv_name);
            item_tv_jieqi = (TextView) convertView.findViewById(R.id.item_tv_jieqi);
            item_tv_phone = (TextView) convertView.findViewById(R.id.item_tv_phone);
            item_layout_shenfen = (LinearLayout) convertView.findViewById(R.id.item_layout_shenfen);
            item_tv_price = (TextView) convertView.findViewById(R.id.item_tv_price);
            item_tv_date = (TextView) convertView.findViewById(R.id.item_tv_date);
            item_tv_left = (TextView) convertView.findViewById(R.id.item_tv_left);
            item_tv_rigth = (TextView) convertView.findViewById(R.id.item_tv_rigth);
            item_layout_btn = (LinearLayout) convertView.findViewById(R.id.item_layout_btn);
            item_layout_phone = (LinearLayout) convertView.findViewById(R.id.item_layout_phone);
            item_tv_typetitle = (TextView) convertView.findViewById(R.id.item_tv_typetitle);
        }
    }
}
