package com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionItemBean;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class ActionListAdapter extends BaseAdapterHelper<ActionItemBean> {
    ViewHolder mHolder = null;

    Context context;

    public ActionListAdapter(Context context, List<ActionItemBean> list) {
        super(context, list);
        this.context = context;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<ActionItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.helpwith_apply_actionlist_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        ActionItemBean mHelpWithInfo = list.get(position);

        mHolder.item_tv_actionname1.setText(mHelpWithInfo.getName1());
        mHolder.item_tv_actionname2.setText(mHelpWithInfo.getName2());

        String img = mHelpWithInfo.getImg();
        GlideDownLoadImage.getInstance().loadCircleImageRole(context, img, mHolder.item_iv_img, ConstantManager.image_angle);
        mHolder.item_tv_abilityprice.setText(mHelpWithInfo.getAbility_price());

        String activity_id = mHelpWithInfo.getActivity_id();
        if (!TextUtils.isEmpty(activity_id) && activity_id.equals("1")) {
            mHolder.item_iv_daiyan.setVisibility(View.VISIBLE);
        } else {
            mHolder.item_iv_daiyan.setVisibility(View.GONE);
        }
        //690 380
        int width = (DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 70)) / 2;
        int height = (int) (width * 0.55) + DensityUtil.dip2px(context, 10);
        mHolder.relat_img.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        return convertView;
    }


    private class ViewHolder {
        private RelativeLayout relat_img;
        private TextView item_tv_actionname1;
        private TextView item_tv_actionname2;
        private ImageView item_iv_img;
        private TextView item_tv_abilityprice;
        private ImageView item_iv_daiyan;

        public ViewHolder(View view) {
            relat_img = view.findViewById(R.id.relat_img);
            item_tv_actionname1 = view.findViewById(R.id.item_tv_actionname1);
            item_tv_actionname2 = view.findViewById(R.id.item_tv_actionname2);
            item_iv_img = view.findViewById(R.id.item_iv_img);
            item_tv_abilityprice = view.findViewById(R.id.item_tv_abilityprice);
            item_iv_daiyan = view.findViewById(R.id.item_iv_daiyan);
        }
    }
}
