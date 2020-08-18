package com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter;

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
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
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

public class FuQiListAdapter extends BaseAdapterHelper<ReportItemBean> {
    ViewHolder mHolder = null;
    Activity context;
    List<ReportItemBean> list;

    public FuQiListAdapter(Activity context, List<ReportItemBean> list) {
        super(context, list);
        this.context = context;
        this.list = list;
    }

    public List<ReportItemBean> getList() {
        return list;
    }

    public void setList(List<ReportItemBean> list) {
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<ReportItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.report_fuqi_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        ReportItemBean mOrderItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mOrderItemBean.getAvatar(), mHolder.item_iv_thumb);
        String name = CommonUtil.setName(mOrderItemBean.getUser_name());
        mHolder.item_tv_name.setText(name);
        mHolder.item_tv_jieeqi.setText(mOrderItemBean.getBranch_info());
        mHolder.item_tv_ability.setText(mOrderItemBean.getSuper_ability() + "能量");

        ArrayList<ReportItemBean> identity_img = mOrderItemBean.getIdentity_flag();
        mHolder.item_layout_shenfen.removeAllViews();
        if (identity_img != null && identity_img.size() > 0) {
            for (ReportItemBean info : identity_img) {
                LinearLayout linearLayout = new LinearLayout(context);
                ImageView imageView = new ImageView(context);
                int dit = DensityUtil.dip2px(context, 16);
                int jian = DensityUtil.dip2px(context, 3);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(context, info.getImage(), imageView);
                linearLayout.addView(imageView);
                mHolder.item_layout_shenfen.addView(linearLayout);
            }
        }

        return convertView;
    }


    private class ViewHolder {
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_jieeqi;
        private LinearLayout item_layout_shenfen;
        private TextView item_tv_ability;

        public ViewHolder(View view) {
            item_iv_thumb = view.findViewById(R.id.item_iv_thumb);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_jieeqi = view.findViewById(R.id.item_tv_jieeqi);
            item_layout_shenfen = view.findViewById(R.id.item_layout_shenfen);
            item_tv_ability = view.findViewById(R.id.item_tv_ability);
        }
    }
}
