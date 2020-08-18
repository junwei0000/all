package com.longcheng.lifecareplan.modular.helpwith.connon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;


public class CHelpDetailSuccessAdapter extends BaseAdapterHelper<CHelpDetailItemBean> {
    ViewHolder mHolder = null;
    Context context;


    public CHelpDetailSuccessAdapter(Context context, List<CHelpDetailItemBean> list) {
        super(context, list);
        this.context = context;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CHelpDetailItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.connon_detailsuccess_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        CHelpDetailItemBean mHelpItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mHelpItemBean.getUser_avatar(), mHolder.item_iv_head);
        mHolder.item_tv_name.setText(CommonUtil.setName(mHelpItemBean.getUser_name()));
        mHolder.item_tv_fen.setText(mHelpItemBean.getGrant_points());
        int role_type = mHelpItemBean.getRole_type();
        if (role_type == 1) {
            mHolder.item_tv_type.setText("队长");
            mHolder.item_tv_type.setBackgroundResource(R.drawable.corners_bg_red);
            ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_tv_type, R.color.red);
        } else {
            mHolder.item_tv_type.setText("组员");
            mHolder.item_tv_type.setBackgroundResource(R.drawable.corners_bg_lv);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_head;
        private TextView item_tv_type;
        private TextView item_tv_name;
        private TextView item_tv_fen;


        public ViewHolder(View view) {
            item_iv_head = view.findViewById(R.id.item_iv_head);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_type = view.findViewById(R.id.item_tv_type);
            item_tv_fen = view.findViewById(R.id.item_tv_fen);
        }
    }
}
