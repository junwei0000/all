package com.longcheng.lifecareplan.utils.myview.address;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.myview.address.bean.SelctAeraInfo;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class SearchAeraListAdapter extends BaseAdapterHelper<SelctAeraInfo> {
    ViewHolder mHolder = null;
    Activity context;

    String topSelectname;

    public SearchAeraListAdapter(Activity context, List<SelctAeraInfo> list,String topSelectname) {
        super(context, list);
        this.context = context;
        this.topSelectname = topSelectname;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<SelctAeraInfo> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.select_aera_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        SelctAeraInfo mOrderItemBean = list.get(position);
        mHolder.tv_aera.setText(mOrderItemBean.getName());
        int wid = DensityUtil.dip2px(context, 10);
        mHolder.tv_aera.setPadding(wid, wid, wid, wid);
        if(topSelectname.equals(mOrderItemBean.getName())){
            mHolder.tv_aera.setTextColor(context.getResources().getColor(R.color.yellow_E95D1B));
        } else {
            mHolder.tv_aera.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        }
        return convertView;
    }


    private class ViewHolder {
        private TextView tv_aera;

        public ViewHolder(View view) {
            tv_aera = view.findViewById(R.id.tv_aera);
        }
    }
}
