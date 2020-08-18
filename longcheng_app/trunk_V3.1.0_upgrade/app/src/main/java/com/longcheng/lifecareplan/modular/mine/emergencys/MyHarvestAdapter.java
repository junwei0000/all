package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;

import java.util.List;

public class MyHarvestAdapter extends BaseAdapterHelper<HavestList.Order> {
    private final ImageLoader asyncImageLoader;
    ViewHolder mHolder = null;
    Context context;

    public MyHarvestAdapter(Context context, List<HavestList.Order> list) {
        super(context, list);
        this.context = context;
        asyncImageLoader = new ImageLoader(context, "headImg");
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<HavestList.Order> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.harves_item, parent, false);
            mHolder = new MyHarvestAdapter.ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (MyHarvestAdapter.ViewHolder) convertView.getTag();
        }
        asyncImageLoader.DisplayImage(list.get(position).getAvatar(), mHolder.item_iv_thumb);
        String name = list.get(position).getUser_name();
        if (!TextUtils.isEmpty(name) && name.length() > 9) {
            name = name.substring(0, 7) + "…";
        }
        mHolder.item_tv_name.setText(name);
        String power = "送来" + CommonUtil.removeTrim(list.get(position).getSuper_ability()) + "个节气能量";
        SpannableString spannableString = new SpannableString(power);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#e60c0c")), 2, power.length() - 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mHolder.item_tv_typetitle.setText(spannableString);


        List<HavestList.Order.Img> identity_img = list.get(position).getIdentity_flag();
        mHolder.item_layout_shenfen.removeAllViews();
        if (identity_img != null && identity_img.size() > 0) {
            for (int i = 0; i < identity_img.size(); i++) {
                LinearLayout linearLayout = new LinearLayout(context);
                ImageView imageView = new ImageView(context);
                int dit = DensityUtil.dip2px(context, 16);
                int jian = DensityUtil.dip2px(context, 3);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(context, identity_img.get(i).getImage(), imageView);
                linearLayout.addView(imageView);
                mHolder.item_layout_shenfen.addView(linearLayout);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_typetitle;

        private LinearLayout item_layout_shenfen;


        public ViewHolder(View convertView) {
            item_tv_typetitle = convertView.findViewById(R.id.item_tv_typetitle);
            item_iv_thumb = convertView.findViewById(R.id.item_iv_thumb);
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_layout_shenfen = convertView.findViewById(R.id.item_layout_shenfen);

        }
    }
}
