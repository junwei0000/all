package com.longcheng.lifecareplan.modular.helpwith.connon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;

import java.util.List;


public class CHelpApplySelectAYAdapter extends BaseAdapterHelper<CHelpItemBean> {
    ViewHolder mHolder = null;
    Context context;

    int selectIndex;

    public CHelpApplySelectAYAdapter(Context context, List<CHelpItemBean> list, int selectIndex) {
        super(context, list);
        this.context = context;
        this.selectIndex = selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CHelpItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.connon_apply_selectay_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        CHelpItemBean mHelpItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mHelpItemBean.getAvatar(), mHolder.item_iv_head);
        mHolder.item_tv_name.setText(CommonUtil.setName(mHelpItemBean.getName()));
        mHolder.item_tv_jieeqi.setText(mHelpItemBean.getJieqi_name());
        if (position == selectIndex) {
            mHolder.item_iv_select.setBackgroundResource(R.mipmap.connon_zudui_aiyou_xuanzhong);
        } else {
            mHolder.item_iv_select.setBackgroundResource(R.mipmap.connon_zudui_aiyou_weixuan);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_head;
        private TextView item_tv_name;
        private TextView item_tv_jieeqi;
        private ImageView item_iv_select;


        public ViewHolder(View view) {
            item_iv_head = view.findViewById(R.id.item_iv_head);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_jieeqi = view.findViewById(R.id.item_tv_jieeqi);
            item_iv_select = view.findViewById(R.id.item_iv_select);
        }
    }
}
