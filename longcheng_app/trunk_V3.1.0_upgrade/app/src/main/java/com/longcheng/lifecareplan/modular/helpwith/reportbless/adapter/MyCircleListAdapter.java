package com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.FuQApplyActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.FuQDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ListItemBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.LoveItemBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportItemBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle.ToChatH5Actitvty;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.TimeCountdownUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MyCircleListAdapter extends BaseAdapterHelper<LoveItemBean> {
    ViewHolder mHolder = null;
    Activity context;

    public MyCircleListAdapter(Activity context, List<LoveItemBean> list) {
        super(context, list);
        this.context = context;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<LoveItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fuqrep_mycircle_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        LoveItemBean mItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mItemBean.getAvatar(), mHolder.item_iv_img);
        mHolder.item_tv_name.setText(CommonUtil.setName(mItemBean.getUser_name(), 5));
        mHolder.item_tv_jieqi.setText(mItemBean.getJieqi());
        mHolder.item_tv_price.setText(Html.fromHtml("祝福  " + CommonUtil.getHtmlContent("#232323", "+" + mItemBean.getAbilities())));
        mHolder.item_layout_shenfen.removeAllViews();
        List<String> identity_img = mItemBean.getIdentity();
        if (identity_img != null && identity_img.size() > 0) {
            for (String url : identity_img) {
                LinearLayout linearLayout = new LinearLayout(context);
                ImageView imageView = new ImageView(context);
                int dit = DensityUtil.dip2px(context, 16);
                int jian = DensityUtil.dip2px(context, 2);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(context, url, imageView);
                linearLayout.addView(imageView);
                mHolder.item_layout_shenfen.addView(linearLayout);
            }
        }
        mHolder.item_relat_circle.setTag(mItemBean);
        mHolder.item_relat_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoveItemBean mItemBean = (LoveItemBean) v.getTag();
                Intent intent = new Intent(context, ToChatH5Actitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", Config.BASE_HEAD_URL + "/home/im?device=android&type=1&to=" + mItemBean.getUser_id());
                intent.putExtra("title", mItemBean.getUser_name());
                intent.putExtra("type", 1);
                context.startActivityForResult(intent, ConstantManager.REFRESHDATA);
            }
        });
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_jieqi;
        private TextView item_tv_name;
        private LinearLayout item_layout_shenfen;
        private TextView item_tv_price;
        private ImageView item_iv_img;
        private RelativeLayout item_relat_circle;


        public ViewHolder(View view) {
            item_tv_jieqi = view.findViewById(R.id.item_tv_jieqi);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_layout_shenfen = view.findViewById(R.id.item_layout_shenfen);
            item_tv_price = view.findViewById(R.id.item_tv_price);
            item_iv_img = view.findViewById(R.id.item_iv_img);
            item_relat_circle = view.findViewById(R.id.item_relat_circle);
        }
    }
}
