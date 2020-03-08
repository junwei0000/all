package com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleDetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleDetailItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.myview.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class DetailJieQiAdapter extends BaseAdapter {

    Context context;
    List<LifeStyleDetailAfterBean> list;

    public DetailJieQiAdapter(Context context, List<LifeStyleDetailAfterBean> list) {
        super();
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.lifedetail_jieqi_item, parent, false);
            mHolder = new ViewHolder();
            mHolder.item_tv_jieqiname = (TextView) convertView.findViewById(R.id.item_tv_jieqiname);
            mHolder.item_tv_num = (TextView) convertView.findViewById(R.id.item_tv_num);
            mHolder.gv_list = (MyGridView) convertView.findViewById(R.id.gv_list);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        LifeStyleDetailAfterBean mEnergyItemBean = list.get(position);
        mHolder.item_tv_jieqiname.setText(mEnergyItemBean.getSolar_terms_name() + "节气祝福");
        mHolder.item_tv_num.setText("已收到" + mEnergyItemBean.getTotal_num() + "份祝福");
        ArrayList<LifeStyleDetailAfterBean> itemlist = mEnergyItemBean.getList();
        if(itemlist!=null&&itemlist.size()>0){
            DetailJieQiItemAdapter mDetailJieQiItemAdapter=new DetailJieQiItemAdapter(context,itemlist);
            mHolder.gv_list.setAdapter(mDetailJieQiItemAdapter);
        }
        return convertView;
    }


    private class ViewHolder {
        private MyGridView gv_list;
        private TextView item_tv_num;
        private TextView item_tv_jieqiname;
    }
}
