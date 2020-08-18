package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBSelectDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;


public class ZuDuiTutorSelectAdapter extends BaseAdapterHelper<PionZFBSelectDataBean.PionZFBSBean> {
    ViewHolder mHolder = null;
    Context context;
    int selectIndex;

    public ZuDuiTutorSelectAdapter(Context context, List<PionZFBSelectDataBean.PionZFBSBean> list) {
        super(context, list);
        this.context = context;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<PionZFBSelectDataBean.PionZFBSBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pioneer_userzyb_select_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        PionZFBSelectDataBean.PionZFBSBean mDaiFuItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mDaiFuItemBean.getAvatar(), mHolder.item_iv_img);
        String name = CommonUtil.setName(mDaiFuItemBean.getUser_name());
        mHolder.item_tv_name.setText(name);
        mHolder.item_tv_jieqi.setText(mDaiFuItemBean.getJieqi_name());
        mHolder.item_tv_phone.setText(mDaiFuItemBean.getPhone());
        mHolder.item_tv_phone.setTag(mDaiFuItemBean.getPhone());
        mHolder.item_tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = (String) v.getTag();
                DensityUtil.getPhoneToKey(context, phone);
            }
        });
        if (selectIndex == position) {
            mHolder.item_tv_add.setText("已选");
            ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_tv_add, R.color.red);
        } else {
            mHolder.item_tv_add.setText("未选");
            ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_tv_add, R.color.gray);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name;
        private TextView item_tv_jieqi;
        private TextView item_tv_phone;
        private TextView item_tv_add;

        public ViewHolder(View convertView) {
            item_iv_img = convertView.findViewById(R.id.item_iv_img);
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_jieqi = convertView.findViewById(R.id.item_tv_jieqi);
            item_tv_phone = convertView.findViewById(R.id.item_tv_phone);
            item_tv_add = convertView.findViewById(R.id.item_tv_add);
        }
    }
}
