package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class ZuDuiMoneyAdapter extends BaseAdapter {

    Context context;
    List<EnergyItemBean> list;
    int selectPosition;

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public ZuDuiMoneyAdapter(Context context, List<EnergyItemBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    public List<EnergyItemBean> getList() {
        return list;
    }

    public void setList(List<EnergyItemBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activatenergy_money_item, parent, false);
            mHolder = new ViewHolder();
            mHolder.item_tv_money = convertView.findViewById(R.id.item_tv_money);
            mHolder.item_layout_money = convertView.findViewById(R.id.item_layout_money);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.item_layout_money.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context, 50)));
        EnergyItemBean mEnergyItemBean = list.get(position);
        mHolder.item_tv_money.setText("¥"+mEnergyItemBean.getMoney());
        if (selectPosition == position) {//默认
            mHolder.item_tv_money.setTextColor(context.getResources().getColor(R.color.white));
            mHolder.item_layout_money.setBackgroundResource(R.mipmap.pion_userzyb_red);
        } else {
            mHolder.item_tv_money.setTextColor(context.getResources().getColor(R.color.text_biaoTi_color));
            mHolder.item_layout_money.setBackgroundResource(R.mipmap.pion_userzyb_gray);
        }
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_money;
        private LinearLayout item_layout_money;

    }
}
