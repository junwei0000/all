package com.longcheng.lifecareplan.modular.mine.pioneercenter.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetRecordDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class PionCZRecordListAdapter extends BaseAdapterHelper<PioneerItemBean> {
    ViewHolder mHolder = null;
    Activity context;
    List<PioneerItemBean> list;

    public PionCZRecordListAdapter(Activity context, List<PioneerItemBean> list) {
        super(context, list);
        this.context = context;
        this.list = list;
    }

    public List<PioneerItemBean> getList() {
        return list;
    }

    public void setList(List<PioneerItemBean> list) {
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<PioneerItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pionner_czrecord_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        PioneerItemBean mOrderItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mOrderItemBean.getAvatar(), mHolder.item_iv_img);
        String username = mOrderItemBean.getUser_name();
        mHolder.item_tv_name.setText(username);
        mHolder.item_tv_time.setText(mOrderItemBean.getCreate_time_detail());
        mHolder.item_tv_num.setText(mOrderItemBean.getMoney());
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name;
        private TextView item_tv_time;
        private TextView item_tv_num;


        public ViewHolder(View view) {
            item_iv_img = view.findViewById(R.id.item_iv_img);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_time = view.findViewById(R.id.item_tv_time);
            item_tv_num = view.findViewById(R.id.item_tv_num);
        }
    }
}
