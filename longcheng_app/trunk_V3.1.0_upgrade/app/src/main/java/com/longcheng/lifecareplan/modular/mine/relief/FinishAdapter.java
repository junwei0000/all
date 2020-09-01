package com.longcheng.lifecareplan.modular.mine.relief;

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
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleListProgressUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;

import java.util.List;

public class FinishAdapter extends BaseAdapterHelper<ReliefItemBean.DataBean> {
    LifeStyleListProgressUtils mProgressUtils;

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<ReliefItemBean.DataBean> list, LayoutInflater inflater) {
        String number = TextUtils.isEmpty(list.get(position).number) ? "" : list.get(position).number;
        String numberAll = TextUtils.isEmpty(list.get(position).numberAll) ? "" : list.get(position).numberAll;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.relief_adapter_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.item_tv_jieeqi.setText(list.get(position).branch + "." + list.get(position).jieqi);
        mHolder.item_tv_name.setText(list.get(position).name);
        mHolder.tv_absorbed.setText("已收能量:" + number);
        mHolder.tv_target.setText("目标能量:" + numberAll);
        GlideDownLoadImage.getInstance().loadCircleImageCommune(context, list.get(position).avatar, mHolder.item_iv_thumb);
        List<String> images = list.get(position).images;

        mHolder.item_layout_shenfen.removeAllViews();
        if (images != null && images.size() > 0) {
            for (String url : images) {
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

        int progress = 9000 / (Integer.valueOf(number) + 240);

        mHolder.item_pb_lifeenergynum.setProgress(progress);
        mProgressUtils.showNum(progress, mHolder.item_pb_lifeenergynum, mHolder.item_pb_numne);
        mHolder.item_pb_lifeenergynum.setReachedBarColor(context.getResources().getColor(R.color.red));
        ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_pb_numne, R.color.red);
        return convertView;
    }

    ViewHolder mHolder = null;
    Activity context;

    public FinishAdapter(Activity context, List<ReliefItemBean.DataBean> list) {
        super(context, list);
        this.context = context;
        this.list = list;
        mProgressUtils = new LifeStyleListProgressUtils(context);
    }

    private class ViewHolder {
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_pb_numne;
        private TextView item_tv_jieeqi;
        private NumberProgressBar item_pb_lifeenergynum;
        private LinearLayout item_layout_shenfen;

        private TextView item_tv_typetitle;
        private TextView tv_absorbed;
        private TextView tv_target;

        public ViewHolder(View convertView) {
            item_pb_lifeenergynum = convertView.findViewById(R.id.item_pb_lifeenergynum);

            item_iv_thumb = convertView.findViewById(R.id.item_iv_thumb);
            item_pb_numne = convertView.findViewById(R.id.item_pb_numne);
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_jieeqi = convertView.findViewById(R.id.item_tv_jieeqi);
            item_layout_shenfen = convertView.findViewById(R.id.item_layout_shenfen);
            tv_absorbed = convertView.findViewById(R.id.tv_absorbed);
            item_tv_typetitle = convertView.findViewById(R.id.item_tv_typetitle);
            tv_target = convertView.findViewById(R.id.tv_target);
        }
    }
}
