package com.longcheng.lifecareplan.modular.helpwith.connonEngineering.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class BaoZhangMoneyAdapter extends BaseAdapter {

    Context context;
    List<DetailItemBean> list;
    int selectMonetPostion;
    int is_applying_help;
    int mutual_help_money;
    boolean selectDefaultStatus = false;
    int mHandlerID;

    public BaoZhangMoneyAdapter(Context context, List<DetailItemBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    public int getmHandlerID() {
        return mHandlerID;
    }

    public void setmHandlerID(int mHandlerID) {
        this.mHandlerID = mHandlerID;
    }

    public int getSelectMonetPostion() {
        return selectMonetPostion;
    }

    public int getIs_applying_help() {
        return is_applying_help;
    }

    public int getMutual_help_money() {
        return mutual_help_money;
    }

    public boolean isSelectDefaultStatus() {
        return selectDefaultStatus;
    }

    public void setSelectDefaultStatus(boolean selectDefaultStatus) {
        this.selectDefaultStatus = selectDefaultStatus;
    }

    public void setMutual_help_money(int mutual_help_money) {
        this.mutual_help_money = mutual_help_money;
    }

    public void setIs_applying_help(int is_applying_help) {
        this.is_applying_help = is_applying_help;
    }

    public void setSelectMonetPostion(int selectMonetPostion) {
        this.selectMonetPostion = selectMonetPostion;
    }

    public List<DetailItemBean> getList() {
        return list;
    }

    public void setList(List<DetailItemBean> list) {
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
            mHolder.item_tv_money = (TextView) convertView.findViewById(R.id.item_tv_money);
            mHolder.item_layout_money = (LinearLayout) convertView.findViewById(R.id.item_layout_money);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        int width = DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 40);
        mHolder.item_tv_money.setWidth(width / 3);
        DetailItemBean mEnergyItemBean = list.get(position);
        int money_ = mEnergyItemBean.getMoney();
        mHolder.item_tv_money.setText("" + money_);
        if (selectMonetPostion == position && selectDefaultStatus) {//默认
            mHolder.item_tv_money.setTextColor(context.getResources().getColor(R.color.white));
            if (mHandlerID == BaoZhangActitvty.LifeBasicAppPayment) {//基础保障用绿色
                mHolder.item_layout_money.setBackgroundResource(R.drawable.corners_bg_lv);
            } else {
                mHolder.item_layout_money.setBackgroundResource(R.drawable.corners_bg_login);
            }
        } else {
            mHolder.item_tv_money.setTextColor(context.getResources().getColor(R.color.text_contents_color));
            mHolder.item_layout_money.setBackgroundResource(R.drawable.corners_bg_skbgray);
        }
        if (is_applying_help > 0 && mutual_help_money > money_) {
            mHolder.item_tv_money.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
        }
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_money;
        private LinearLayout item_layout_money;

    }
}
