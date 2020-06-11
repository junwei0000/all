package com.longcheng.lifecareplan.modular.home.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.home.bean.HomeItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：热推互助
 */

public class ActionAdapter extends BaseAdapterHelper<HomeItemBean> {
    ViewHolder mHolder = null;
    Context context;

    public ActionAdapter(Context context, List<HomeItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<HomeItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_home_hotaction_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        HomeItemBean mHelpItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImageRoleREf(context, mHelpItemBean.getImg(), mHolder.item_iv_img, 0);
        mHolder.item_tv_name1.setText(mHelpItemBean.getName1());
        mHolder.item_tv_name2.setText(mHelpItemBean.getName2());
        mHolder.item_tv_num.setText(Html.fromHtml(mHelpItemBean.getAbility_price() + "<font color=\"#939393\">生命能量</font>"));

        int width = (DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 30)) / 2;
        int height = (int) (width * 0.55);
        mHolder.item_iv_img.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name1;
        private TextView item_tv_name2;
        private TextView item_tv_num;

        public ViewHolder(View view) {
            item_iv_img = view.findViewById(R.id.item_iv_img);
            item_tv_name1 = view.findViewById(R.id.item_tv_name1);
            item_tv_name2 = view.findViewById(R.id.item_tv_name2);
            item_tv_num = view.findViewById(R.id.item_tv_num);
        }
    }
}
