package com.longcheng.lifecareplan.modular.home.bangdan;

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
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;

import java.util.List;

/**
 *
 */

public class BlessAdapter extends BaseAdapterHelper<BangDanAfterBean> {
    private final ImageLoader asyncImageLoader;
    ViewHolder mHolder = null;
    Activity context;
    String skip_source;

    public BlessAdapter(Activity context, List<BangDanAfterBean> list, String skip_source) {
        super(context, list);
        this.context = context;
        this.skip_source = skip_source;
        asyncImageLoader = new ImageLoader(context, "headImg");
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<BangDanAfterBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.home_bangdan_bless_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        BangDanAfterBean mRankItemBean = list.get(position);
        String num = mRankItemBean.getRanking();
        mHolder.item_tv_num.setText(num);
        asyncImageLoader.DisplayImage(mRankItemBean.getAvatar(), mHolder.item_iv_thumb);
        String name = CommonUtil.setName(mRankItemBean.getUser_name());
        mHolder.item_tv_name.setText(name);
        mHolder.item_tv_jieeqi.setText(mRankItemBean.getJieqi_name());
        mHolder.item_tv_rank.setText(mRankItemBean.getExponent());
        List<String> identity_img = mRankItemBean.getIdentity_img();
        mHolder.item_layout_shenfen.removeAllViews();
        if (identity_img != null && identity_img.size() > 0) {
            for (String url : identity_img) {
                LinearLayout linearLayout = new LinearLayout(context);
                ImageView imageView = new ImageView(context);
                int dit = DensityUtil.dip2px(context, 16);
                int jian = DensityUtil.dip2px(context, 3);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(context, url, imageView);
                linearLayout.addView(imageView);
                mHolder.item_layout_shenfen.addView(linearLayout);
            }
        }
        if (skip_source.equals("engryCenter")) {
            mHolder.item_tv_typetitle.setText("分数");
        } else if (skip_source.equals("bless_1824")) {
            mHolder.item_tv_typetitle.setText("分数");
        } else {
            mHolder.item_tv_typetitle.setText("福气值");
        }
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_num;
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_jieeqi;

        private LinearLayout item_layout_shenfen;
        private TextView item_tv_rank;
        private TextView item_tv_typetitle;

        public ViewHolder(View convertView) {
            item_tv_num = convertView.findViewById(R.id.item_tv_num);
            item_iv_thumb = convertView.findViewById(R.id.item_iv_thumb);
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_jieeqi = convertView.findViewById(R.id.item_tv_jieeqi);
            item_layout_shenfen = convertView.findViewById(R.id.item_layout_shenfen);
            item_tv_rank = convertView.findViewById(R.id.item_tv_rank);
            item_tv_typetitle = convertView.findViewById(R.id.item_tv_typetitle);
        }
    }
}
