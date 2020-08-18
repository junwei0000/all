package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.app.Activity;
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
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.ProgressUtils;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleListProgressUtils;
import com.longcheng.lifecareplan.modular.home.bangdan.BangDanAfterBean;
import com.longcheng.lifecareplan.modular.home.bangdan.BlessAdapter;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProgressAdapter extends BaseAdapterHelper<HeloneedBean.UserBean> {
    LifeStyleListProgressUtils mProgressUtils;

    private final ImageLoader asyncImageLoader;
    ProgressAdapter.ViewHolder mHolder = null;
    Activity context;
    String skip_source;
    private boolean isSerach = false;

    public ProgressAdapter(Activity context, List<HeloneedBean.UserBean> list, String skip_source, boolean isSerach) {
        super(context, list);
        this.context = context;
        this.isSerach = isSerach;
        this.skip_source = skip_source;
        asyncImageLoader = new ImageLoader(context, "headImg");
        mProgressUtils = new LifeStyleListProgressUtils(context);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<HeloneedBean.UserBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_progress_item, parent, false);
            mHolder = new ProgressAdapter.ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ProgressAdapter.ViewHolder) convertView.getTag();
        }

        HeloneedBean.UserBean mRankItemBean = list.get(position);
        if (isSerach) {
            mHolder.item_tv_num.setText("");
        } else {
            mHolder.item_tv_num.setText(mRankItemBean.getRanking());
        }

        asyncImageLoader.DisplayImage(mRankItemBean.user_avatar, mHolder.item_iv_thumb);
        String name = CommonUtil.setName(mRankItemBean.getUser_name());
        mHolder.item_tv_name.setText(name);

        mHolder.tv_absorbed.setText("已收能量:" + CommonUtil.removeTrim(mRankItemBean.obtain_super_ability));
        mHolder.tv_target.setText("目标能量:" + CommonUtil.removeTrim(mRankItemBean.super_ability));
        mHolder.item_tv_jieeqi.setText(mRankItemBean.user_branch_info);
        mHolder.item_tv_typetitle.setText(mRankItemBean.getCreate_date());
        List<Img> identity_img = mRankItemBean.user_identity_flag;
        mHolder.item_layout_shenfen.removeAllViews();
        if (identity_img != null && identity_img.size() > 0) {
            for (int i = 0; i < identity_img.size(); i++) {
                LinearLayout linearLayout = new LinearLayout(context);
                ImageView imageView = new ImageView(context);
                int dit = DensityUtil.dip2px(context, 16);
                int jian = DensityUtil.dip2px(context, 3);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(context, identity_img.get(i).image, imageView);
                linearLayout.addView(imageView);
                mHolder.item_layout_shenfen.addView(linearLayout);
            }
        }

        mHolder.item_pb_lifeenergynum.setProgress(mRankItemBean.progress);
        mProgressUtils.showNum(mRankItemBean.progress, mHolder.item_pb_lifeenergynum, mHolder.item_pb_numne);
        mHolder.item_pb_lifeenergynum.setReachedBarColor(context.getResources().getColor(R.color.red));
        ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_pb_numne, R.color.red);
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_num;
        private CircleImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_pb_numne;
        private TextView item_tv_jieeqi;
        private NumberProgressBar item_pb_lifeenergynum;
        private LinearLayout item_layout_shenfen;
        private RelativeLayout relat_cn;
        private TextView item_tv_rank;
        private TextView item_tv_typetitle;
        private TextView tv_absorbed;
        private TextView tv_target;

        public ViewHolder(View convertView) {
            item_pb_lifeenergynum = convertView.findViewById(R.id.item_pb_lifeenergynum);
            item_tv_num = convertView.findViewById(R.id.item_tv_num);
            tv_absorbed = convertView.findViewById(R.id.tv_absorbed);
            tv_target = convertView.findViewById(R.id.tv_target);
            item_iv_thumb = convertView.findViewById(R.id.item_iv_thumb);
            item_pb_numne = convertView.findViewById(R.id.item_pb_numne);
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_jieeqi = convertView.findViewById(R.id.item_tv_jieeqi);
            item_layout_shenfen = convertView.findViewById(R.id.item_layout_shenfen);
            item_tv_rank = convertView.findViewById(R.id.item_tv_rank);
            item_tv_typetitle = convertView.findViewById(R.id.item_tv_typetitle);
            relat_cn = convertView.findViewById(R.id.relat_cn);
        }
    }

}
