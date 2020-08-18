package com.longcheng.lifecareplan.modular.helpwith.nfcaction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class NFCDetailMoneyAdapter extends BaseAdapter {

    Context context;
    List<NFCDetailItemBean> list;

    int selectMonetPostion;

    public NFCDetailMoneyAdapter(Context context, List<NFCDetailItemBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    public int getSelectMonetPostion() {
        return selectMonetPostion;
    }

    public void setSelectMonetPostion(int selectMonetPostion) {
        this.selectMonetPostion = selectMonetPostion;
    }

    public List<NFCDetailItemBean> getList() {
        return list;
    }

    public void setList(List<NFCDetailItemBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.helpwith_engry_detail_money_item, parent, false);
            mHolder = new ViewHolder();
            mHolder.item_tv_moneyimg = convertView.findViewById(R.id.item_tv_moneyimg);
            mHolder.item_tv_money = convertView.findViewById(R.id.item_tv_money);
            mHolder.item_layout_money = convertView.findViewById(R.id.item_layout_money);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        int width = DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 80);
        NFCDetailItemBean mEnergyItemBean = list.get(position);
        mHolder.item_layout_money.setLayoutParams(new LinearLayout.LayoutParams(width / 3, width / 3));
        GlideDownLoadImage.getInstance().loadCircleImageRoleGoods(context, mEnergyItemBean.getImage(), mHolder.item_tv_moneyimg, 0);
        String Ability = mEnergyItemBean.getAbility();
        mHolder.item_tv_money.setText(Ability + "");
        if (selectMonetPostion == position) {//默认
            mHolder.item_layout_money.setBackgroundResource(R.drawable.corners_bg_redmoney);
            mHolder.item_tv_money.setTextColor(context.getResources().getColor(R.color.text_biaoTi_color));
        } else {
            mHolder.item_layout_money.setBackgroundResource(R.drawable.corners_bg_write);
            mHolder.item_tv_money.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
        }
        return convertView;
    }


    private class ViewHolder {
        private ImageView item_tv_moneyimg;
        private TextView item_tv_money;
        private LinearLayout item_layout_money;

    }
}
