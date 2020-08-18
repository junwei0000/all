package com.longcheng.lifecareplan.modular.mine.pioneercenter.adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.message.bean.MessageItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerXuFeiDataBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class XuFeiAdapter extends BaseAdapterHelper<PioneerXuFeiDataBean.PioneerXuFeiItemBean> {
    ViewHolder mHolder = null;
    Activity context;

    int selectindex;

    public XuFeiAdapter(Activity context, List<PioneerXuFeiDataBean.PioneerXuFeiItemBean> list) {
        super(context, list);
        this.context = context;
    }

    public int getSelectindex() {
        return selectindex;
    }

    public void setSelectindex(int selectindex) {
        this.selectindex = selectindex;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<PioneerXuFeiDataBean.PioneerXuFeiItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pioneer_xufei_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        PioneerXuFeiDataBean.PioneerXuFeiItemBean mActionItemBean = list.get(position);
        mHolder.tv_price.setText("" + mActionItemBean.getMoney());
        mHolder.tv_cont.setText(mActionItemBean.getName());
        int width = (DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 55)) / 3;
        mHolder.layout_price.setLayoutParams(new FrameLayout.LayoutParams(width, (int) (width * 1.273)));
        mHolder.layout_price.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        if (position == selectindex) {
            mHolder.fram_price.setBackgroundResource(R.drawable.pioneer_yellow_gradient_shape_bian);
        } else {
            mHolder.fram_price.setBackgroundResource(R.drawable.pioneer_tran_gradient_shape_bian);
        }
        mHolder.tv_yuanprice.getPaint().setAntiAlias(true);//抗锯齿
        mHolder.tv_yuanprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        mHolder.tv_yuanprice.setText("原价" + PriceUtils
                .getInstance().gteAddSumPrice(mActionItemBean.getMoney(), mActionItemBean.getSave_money()) + "元");
        if (position == 0) {
            mHolder.tv_yuanprice.setVisibility(View.GONE);
        } else {
            mHolder.tv_yuanprice.setVisibility(View.VISIBLE);
        }
        return convertView;
    }


    private class ViewHolder {
        private FrameLayout fram_price;
        private LinearLayout layout_price;
        private TextView tv_price;
        private TextView tv_cont;
        private TextView tv_yuanprice;

        public ViewHolder(View view) {
            fram_price = view.findViewById(R.id.fram_price);
            layout_price = view.findViewById(R.id.layout_price);
            tv_price = view.findViewById(R.id.tv_price);
            tv_cont = view.findViewById(R.id.tv_cont);
            tv_yuanprice = view.findViewById(R.id.tv_yuanprice);
        }
    }
}
