package com.longcheng.lifecareplan.modular.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.ProgressUtils;
import com.longcheng.lifecareplan.modular.home.bean.HomeItemBean;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：热推互助
 */

public class HomeHotPushAdapter extends BaseAdapterHelper<HomeItemBean> {
    ViewHolder mHolder = null;
    Context context;
    ProgressUtils mProgressUtils;

    public HomeHotPushAdapter(Context context, List<HomeItemBean> list) {
        super(context, list);
        this.context = context;
        mProgressUtils = new ProgressUtils(context);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<HomeItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_home_hotpush_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        HomeItemBean mHelpItemBean = list.get(position);
        String groupimg = mHelpItemBean.getGroup_img();
        String gs_name = mHelpItemBean.getGs_name();
        String action_image = mHelpItemBean.getAction_image();
        String action_name = mHelpItemBean.getAction_name();
        String h_user = mHelpItemBean.getH_user();
        String date = mHelpItemBean.getDate();
        String ability_price_action = mHelpItemBean.getAbility_price_action();

        GlideDownLoadImage.getInstance().loadCircleImageCommune(context, groupimg, mHolder.item_iv_communethumb);
        GlideDownLoadImage.getInstance().loadCircleImageRole(context, action_image, mHolder.item_iv_thumb, ConstantManager.image_angle);
        mHolder.item_iv_communename.setText(gs_name);
        mHolder.item_tv_content.setText(action_name);
        mHolder.item_tv_name.setText("接福人：" + h_user);
        mHolder.item_tv_date.setText(date);
        mHolder.item_tv_lifeenergynum.setText(ability_price_action);
        ColorChangeByTime.getInstance().changeDrawable(context, mHolder.item_tv_lifeenergynum);

        //******************更新进度***************
        mHolder.item_pb_normalnumnew.setBackgroundResource(R.drawable.corners_bg_redprogress);
        mHolder.item_pb_supernumnew.setBackgroundResource(R.drawable.corners_bg_redprogress);
        ColorChangeByTime.getInstance().changeDrawable(context, mHolder.item_pb_normalnumnew);
        ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_pb_supernumnew, R.color.engry_btn_bg);
        int ability_type = mHelpItemBean.getAbility_type();//互祝类型 1普通  2超级 3混合
        int super_progress = mHelpItemBean.getSuper_progress();
        int Super_ability_proportion = mHelpItemBean.getSuper_ability_proportion();
        int normal_progress = mHelpItemBean.getNormal_progress();
        int Ability_proportion = mHelpItemBean.getAbility_proportion();
        if (super_progress > Super_ability_proportion) {
            super_progress = Super_ability_proportion;
        }
        if (normal_progress > Ability_proportion) {
            normal_progress = Ability_proportion;
        }
        if (ability_type == 2) {
            mHolder.item_pb_super.setVisibility(View.VISIBLE);
            mHolder.item_pb_supernumnew.setVisibility(View.VISIBLE);
            mHolder.item_pb_normal.setVisibility(View.GONE);
            mHolder.item_pb_normalnumnew.setVisibility(View.GONE);
            mHolder.iv_rate.setVisibility(View.GONE);
            mHolder.item_pb_super.setProgress(super_progress);
            mHolder.item_pb_super.setReachedBarColor(context.getResources().getColor(R.color.engry_btn_bg));
            mProgressUtils.showNum(super_progress, mHolder.item_pb_super.getMax(), mHolder.item_pb_supernumnew);
        } else if (ability_type == 3) {
            mHolder.item_pb_super.setVisibility(View.VISIBLE);
            mHolder.item_pb_supernumnew.setVisibility(View.VISIBLE);
            mHolder.item_pb_normal.setVisibility(View.VISIBLE);
            mHolder.item_pb_normalnumnew.setVisibility(View.VISIBLE);
            mHolder.iv_rate.setVisibility(View.VISIBLE);
            mHolder.item_pb_super.setProgress(super_progress);
            mHolder.item_pb_super.setReachedBarColor(context.getResources().getColor(R.color.engry_btn_bg));
            mProgressUtils.showNummixSuper(super_progress, Super_ability_proportion, mHolder.item_pb_super.getMax(), mHolder.item_pb_supernumnew);
            //---------------------------
            int fan_progress = 100 - normal_progress;
            mHolder.item_pb_normal.setProgress(fan_progress);
            mHolder.item_pb_normal.setUnreachedBarColor(ColorChangeByTime.getInstance().backColor(context));
            mHolder.item_pb_normal.setReachedBarColor(context.getResources().getColor(R.color.transparent));
            mProgressUtils.showNum(fan_progress, mHolder.item_pb_normal.getMax(), mHolder.item_pb_normalnumnew);
            mProgressUtils.setTextCont(normal_progress, mHolder.item_pb_normal.getMax(), mHolder.item_pb_normalnumnew);

            int progresslen = DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 46);
            float ww = progresslen * Super_ability_proportion / 100;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins((int) ww, 0, 0, DensityUtil.dip2px(context, 18));
            mHolder.iv_rate.setLayoutParams(params);
        } else {
            mHolder.item_pb_super.setVisibility(View.GONE);
            mHolder.item_pb_supernumnew.setVisibility(View.GONE);
            mHolder.item_pb_normal.setVisibility(View.VISIBLE);
            mHolder.item_pb_normalnumnew.setVisibility(View.VISIBLE);
            mHolder.iv_rate.setVisibility(View.GONE);
            mHolder.item_pb_normal.setUnreachedBarColor(context.getResources().getColor(R.color.progressbarbg));
            mHolder.item_pb_normal.setReachedBarColor(ColorChangeByTime.getInstance().backColor(context));

            mHolder.item_pb_normal.setProgress(normal_progress);
            mProgressUtils.showNum(normal_progress, mHolder.item_pb_normal.getMax(), mHolder.item_pb_normalnumnew);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_communethumb;
        private TextView item_iv_communename;

        private ImageView item_iv_thumb, iv_rate;
        private TextView item_tv_content;
        private TextView item_tv_name;

        private NumberProgressBar item_pb_normal;
        private NumberProgressBar item_pb_super;
        private TextView item_pb_normalnumnew, item_pb_supernumnew;
        private TextView item_tv_lifeenergynum;
        private TextView item_tv_date;

        public ViewHolder(View view) {
            iv_rate = (ImageView) view.findViewById(R.id.iv_rate);
            item_iv_communethumb = (ImageView) view.findViewById(R.id.item_iv_communethumb);
            item_iv_communename = (TextView) view.findViewById(R.id.item_iv_communename);

            item_iv_thumb = (ImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_content = (TextView) view.findViewById(R.id.item_tv_content);
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);

            item_pb_normal = (NumberProgressBar) view.findViewById(R.id.item_pb_normal);
            item_pb_super = (NumberProgressBar) view.findViewById(R.id.item_pb_super);
            item_pb_normalnumnew = (TextView) view.findViewById(R.id.item_pb_normalnumnew);
            item_pb_supernumnew = (TextView) view.findViewById(R.id.item_pb_supernumnew);
            item_tv_lifeenergynum = (TextView) view.findViewById(R.id.item_tv_lifeenergynum);
            item_tv_date = (TextView) view.findViewById(R.id.item_tv_date);
        }
    }
}
