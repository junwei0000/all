package com.longcheng.lifecareplan.modular.mine.relief;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.ProgressUtils;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleListProgressUtils;
import com.longcheng.lifecareplan.modular.home.bangdan.BangDanAfterBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ProgressAdapter extends BaseAdapter {
    LifeStyleListProgressUtils mProgressUtils;
    List<ReliefItemBean.DataBean> mList;
    private LayoutInflater inflater;
    ViewHolder mHolder = null;
    Activity context;


    public ProgressAdapter(Activity context) {
        this.context = context;
        mProgressUtils = new LifeStyleListProgressUtils(context);
        this.inflater = LayoutInflater.from(context);
    }


    public void setNewData(List<ReliefItemBean.DataBean> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        String number = TextUtils.isEmpty(mList.get(position).number) ? "" : mList.get(position).number;
        String numberAll = TextUtils.isEmpty(mList.get(position).numberAll) ? "" : mList.get(position).numberAll;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.relief_adapter_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.item_tv_jieeqi.setText(mList.get(position).branch + "·" + mList.get(position).jieqi);
        mHolder.item_tv_name.setText(mList.get(position).name);
        mHolder.tv_absorbed.setText("已收能量:" + number);
        mHolder.tv_target.setText("目标能量:" + numberAll);
        mHolder.item_tv_typetitle.setText(mList.get(position).start_time);
        GlideDownLoadImage.getInstance().loadCircleImageCommune(context, mList.get(position).avatar, mHolder.item_iv_thumb);
        List<String> images = mList.get(position).images;
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
        BigDecimal bi1 = new BigDecimal(number);
        BigDecimal bi2 = new BigDecimal(numberAll);
        BigDecimal divide = bi1.divide(bi2, 4, RoundingMode.HALF_UP);

        int progress = (int) (divide.doubleValue() * 100);

        mHolder.item_pb_lifeenergynum.setProgress(progress);
        mProgressUtils.showNum(progress, mHolder.item_pb_lifeenergynum, mHolder.item_pb_numne);

        if (progress == 100) {
            mHolder.iv_finish.setVisibility(View.VISIBLE);
            mHolder.item_pb_lifeenergynum.setReachedBarColor(context.getResources().getColor(R.color.color_9a9a9a));
            ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_pb_numne, R.color.color_9a9a9a);
        } else {
            mHolder.iv_finish.setVisibility(View.GONE);
            mHolder.item_pb_lifeenergynum.setReachedBarColor(context.getResources().getColor(R.color.red));
            ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_pb_numne, R.color.red);
        }

        return convertView;

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
        private ImageView iv_finish;

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
            iv_finish = convertView.findViewById(R.id.iv_finish);
        }
    }
}
