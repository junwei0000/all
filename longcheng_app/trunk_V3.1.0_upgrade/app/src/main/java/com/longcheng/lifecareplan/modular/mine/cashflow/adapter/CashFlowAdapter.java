package com.longcheng.lifecareplan.modular.mine.cashflow.adapter;

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
import com.longcheng.lifecareplan.modular.mine.cashflow.bean.CashFlowItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;


public class CashFlowAdapter extends BaseAdapterHelper<CashFlowItemBean> {
    ViewHolder mHolder = null;
    Context context;


    public CashFlowAdapter(Context context, List<CashFlowItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CashFlowItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mine_cashflow_detail_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        CashFlowItemBean mBean = list.get(position);
        mBean.setPosition(position);

        GlideDownLoadImage.getInstance().loadCircleImage(mBean.getAvatar(), mHolder.item_iv_thumb);
        String name = CommonUtil.setName(mBean.getUser_name());
        mHolder.item_tv_name.setText(name);
        mHolder.item_tv_jieeqi.setText(mBean.getJieqi_name());
        mHolder.item_tv_money.setText(mBean.getMoney());
        mHolder.item_tv_allmoney.setText("总数：" + mBean.getUserMoney());
        List<String> identity_img = mBean.getIdentity_img();
        mHolder.item_layout_shenfen.removeAllViews();
        if (identity_img != null && identity_img.size() > 0) {
            for (String url : identity_img) {
                LinearLayout linearLayout = new LinearLayout(context);
                ImageView imageView = new ImageView(context);
                int dit = DensityUtil.dip2px(context, 16);
                int jian = DensityUtil.dip2px(context, 3);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(context, url, imageView);
                linearLayout.addView(imageView);
                mHolder.item_layout_shenfen.addView(linearLayout);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_jieeqi;
        private LinearLayout item_layout_shenfen;
        private TextView item_tv_money;
        private TextView item_tv_allmoney;


        public ViewHolder(View convertView) {
            item_iv_thumb = convertView.findViewById(R.id.item_iv_thumb);
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_jieeqi = convertView.findViewById(R.id.item_tv_jieeqi);
            item_layout_shenfen = convertView.findViewById(R.id.item_layout_shenfen);
            item_tv_money = convertView.findViewById(R.id.item_tv_money);
            item_tv_allmoney = convertView.findViewById(R.id.item_tv_allmoney);
        }
    }
}
