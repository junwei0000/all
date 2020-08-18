package com.longcheng.lifecareplan.modular.mine.treasurebowl.adapter;

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
import com.longcheng.lifecareplan.modular.mine.pioneercenter.adapter.FQBRankAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerItemBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.RankListBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.RankingBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class CornucopiaRankAdapter extends BaseAdapterHelper<RankingBean> {
    ViewHolder mHolder = null;
    Activity context;
    private ImageLoader asyncImageLoader;

    public CornucopiaRankAdapter(Activity context, List<RankingBean> list, ImageLoader asyncImageLoader) {
        super(context, list);
        this.context = context;
        this.asyncImageLoader = asyncImageLoader;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<RankingBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pion_rank_fqb_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        RankingBean mRankItemBean = list.get(position);
        String num = mRankItemBean.getRanking();
        String User_name = mRankItemBean.getUser_name();
        String engerynum = mRankItemBean.getGrades();
        String thumb = mRankItemBean.getAvatar();
        if (TextUtils.isEmpty(User_name)) {
            User_name = "";
        }
        if (TextUtils.isEmpty(engerynum)) {
            engerynum = "";
        }
        int wid =  DensityUtil.dip2px(context, 45);

        mHolder.item_img.setLayoutParams(new LinearLayout.LayoutParams(wid, wid));

        if (num != null) {
            int bangint = Integer.valueOf(num);
            if (bangint == 1) {
                mHolder.item_tv_num.setVisibility(View.GONE);
                mHolder.item_img.setVisibility(View.VISIBLE);
                mHolder.item_img.setImageResource( R.mipmap.icon_first);
            } else if (bangint == 2) {
                mHolder.item_tv_num.setVisibility(View.GONE);
                mHolder.item_img.setVisibility(View.VISIBLE);
                mHolder.item_img.setImageResource( R.mipmap.icon_second);
            } else if (bangint == 3) {
                mHolder.item_tv_num.setVisibility(View.GONE);
                mHolder.item_img.setVisibility(View.VISIBLE);
                mHolder.item_img.setImageResource( R.mipmap.icon_thr);
            } else {
                mHolder.item_tv_num.setVisibility(View.VISIBLE);
                mHolder.item_img.setVisibility(View.GONE);
            }
        }


        mHolder.item_tv_num.setText(num);
        mHolder.item_tv_name.setText(CommonUtil.setName(User_name));
        mHolder.item_tv_fqbnum.setText(new StringBuffer().append("+").append(engerynum).toString());
        asyncImageLoader.DisplayImage(thumb, mHolder.item_iv_thumb);

        mHolder.mycenter_relat_shenfen.removeAllViews();
        ArrayList<String> user_identity_imgs = (ArrayList<String>) mRankItemBean.getIdentity_img();
        if (user_identity_imgs != null && user_identity_imgs.size() > 0) {
            mHolder.mycenter_relat_shenfen.setVisibility(View.VISIBLE);
            for (String info : user_identity_imgs) {
                LinearLayout linearLayout = new LinearLayout(context);
                ImageView imageView = new ImageView(context);
                int dit = DensityUtil.dip2px(context, 16);
                int jian = DensityUtil.dip2px(context, 2);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(context, info, imageView);
                linearLayout.addView(imageView);
                mHolder.mycenter_relat_shenfen.addView(linearLayout);
            }
        } else {
            mHolder.mycenter_relat_shenfen.setVisibility(View.VISIBLE);
        }
        return convertView;
    }


    private class ViewHolder {
        private CircleImageView item_iv_thumb;
        private TextView item_tv_num;
        private TextView item_tv_name;
        private TextView item_tv_fqbnum;
        private LinearLayout mycenter_relat_shenfen;
        private ImageView item_img;

        public ViewHolder(View convertView) {
            item_img = convertView.findViewById(R.id.item_iv_thumb_img);
            item_tv_fqbnum = convertView.findViewById(R.id.item_tv_fqbnum);
            item_tv_num = convertView.findViewById(R.id.item_tv_num);
            item_iv_thumb = convertView.findViewById(R.id.item_iv_thumb);
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            mycenter_relat_shenfen = convertView.findViewById(R.id.mycenter_relat_shenfen);
        }
    }
}
