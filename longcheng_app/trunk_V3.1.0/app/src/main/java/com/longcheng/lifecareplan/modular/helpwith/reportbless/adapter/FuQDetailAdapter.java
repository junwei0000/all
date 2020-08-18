package com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter;

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
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.DetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.LoveItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.ArrayList;
import java.util.List;


public class FuQDetailAdapter extends BaseAdapterHelper<DetailAfterBean.DetailItemBean> {
    ViewHolder mHolder = null;
    Context context;
    boolean guiren;

    public FuQDetailAdapter(Context context, List<DetailAfterBean.DetailItemBean> list, boolean guiren) {
        super(context, list);
        this.context = context;
        this.guiren = guiren;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<DetailAfterBean.DetailItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fuqrep_love_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        DetailAfterBean.DetailItemBean mDaiFuItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mDaiFuItemBean.getAvatar(), mHolder.item_iv_img);
        String name = CommonUtil.setName(mDaiFuItemBean.getUser_name());
        mHolder.item_tv_name.setText(name);
        ArrayList<String> identity_img = mDaiFuItemBean.getIdentity();
        mHolder.item_layout_shenfen.removeAllViews();
        if (identity_img != null && identity_img.size() > 0) {
            for (String info : identity_img) {
                LinearLayout linearLayout = new LinearLayout(context);
                ImageView imageView = new ImageView(context);
                int dit = DensityUtil.dip2px(context, 16);
                int jian = DensityUtil.dip2px(context, 2);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(context, info, imageView);
                linearLayout.addView(imageView);
                mHolder.item_layout_shenfen.addView(linearLayout);
            }
        }
        mHolder.item_tv_price.setText("+1");
        String flora = mDaiFuItemBean.getFlora();
        String tone = mDaiFuItemBean.getTone();
        String cont = CommonUtil.getHtmlContent(tone, flora);
        if (guiren) {
            mHolder.item_tv_jieqi.setText(mDaiFuItemBean.getSolar_terms_name());
            mHolder.item_tv_typetitle.setText("");
            mHolder.item_tv_jieqititle1.setVisibility(View.VISIBLE);
            mHolder.item_tv_jieqititle2.setVisibility(View.VISIBLE);
        } else {
            mHolder.item_tv_jieqi.setText(mDaiFuItemBean.getJieqi_name());
            mHolder.item_tv_typetitle.setText("送出 ");
            mHolder.item_tv_jieqititle1.setVisibility(View.GONE);
            mHolder.item_tv_jieqititle2.setVisibility(View.GONE);
        }
        mHolder.item_tv_typetitle2.setText(Html.fromHtml(cont));
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name;
        private TextView item_tv_jieqi;
        private LinearLayout item_layout_shenfen;
        private TextView item_tv_price;
        private TextView item_tv_typetitle;
        private TextView item_tv_typetitle2;
        private TextView item_tv_jieqititle1;
        private TextView item_tv_jieqititle2;

        public ViewHolder(View convertView) {
            item_iv_img = convertView.findViewById(R.id.item_iv_img);
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_jieqi = convertView.findViewById(R.id.item_tv_jieqi);
            item_layout_shenfen = convertView.findViewById(R.id.item_layout_shenfen);
            item_tv_price = convertView.findViewById(R.id.item_tv_price);
            item_tv_typetitle = convertView.findViewById(R.id.item_tv_typetitle);
            item_tv_typetitle2 = convertView.findViewById(R.id.item_tv_typetitle2);
            item_tv_jieqititle1 = convertView.findViewById(R.id.item_tv_jieqititle1);
            item_tv_jieqititle2 = convertView.findViewById(R.id.item_tv_jieqititle2);
        }
    }
}
