package com.longcheng.lifecareplan.modular.mine.recovercash.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.mine.recovercash.bean.AcountItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;


public class ApplyCashAdapter extends BaseAdapter {

    Context context;
    List<AcountItemBean> list;

    public ApplyCashAdapter(Context context, List<AcountItemBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.tixian_applycash_item, parent, false);
            mHolder = new ViewHolder();
            mHolder.iv_thumb = convertView.findViewById(R.id.iv_thumb);
            mHolder.item_tv_time = convertView.findViewById(R.id.item_tv_time);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        AcountItemBean mEnergyItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mEnergyItemBean.getAvatar(), mHolder.iv_thumb);

        String name = CommonUtil.setName(mEnergyItemBean.getUser_name());
        String cont = name + "&nbsp;&nbsp;&nbsp;&nbsp;成功提现<font color=\"#e60c0c\">" + mEnergyItemBean.getMoney() + "</font>元";
        mHolder.item_tv_time.setText(Html.fromHtml(cont));
        return convertView;
    }


    private class ViewHolder {
        private ImageView iv_thumb;
        private TextView item_tv_time;

    }
}
