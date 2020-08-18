package com.longcheng.lifecareplan.modular.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.ProgressUtils;
import com.longcheng.lifecareplan.modular.home.bean.HomeItemBean;
import com.longcheng.lifecareplan.utils.ConstantManager;
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

        mHolder.item_pb_normalnumnew.setBackgroundResource(R.drawable.corners_bg_redprogress);
        ColorChangeByTime.getInstance().changeDrawable(context, mHolder.item_pb_normalnumnew);
        int progress = mHelpItemBean.getProgress();
        mHolder.item_pb_normal.setUnreachedBarColor(context.getResources().getColor(R.color.progressbarbg));
        mHolder.item_pb_normal.setReachedBarColor(ColorChangeByTime.getInstance().backColor(context));
        mHolder.item_pb_normal.setProgress(progress);
        mProgressUtils.showNum(progress, mHolder.item_pb_normal.getMax(), mHolder.item_pb_normalnumnew);
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_communethumb;
        private TextView item_iv_communename;

        private ImageView item_iv_thumb;
        private TextView item_tv_content;
        private TextView item_tv_name;

        private NumberProgressBar item_pb_normal;
        private TextView item_pb_normalnumnew;
        private TextView item_tv_lifeenergynum;
        private TextView item_tv_date;

        public ViewHolder(View view) {
            item_iv_communethumb = view.findViewById(R.id.item_iv_communethumb);
            item_iv_communename = view.findViewById(R.id.item_iv_communename);

            item_iv_thumb = view.findViewById(R.id.item_iv_thumb);
            item_tv_content = view.findViewById(R.id.item_tv_content);
            item_tv_name = view.findViewById(R.id.item_tv_name);

            item_pb_normal = view.findViewById(R.id.item_pb_normal);
            item_pb_normalnumnew = view.findViewById(R.id.item_pb_normalnumnew);
            item_tv_lifeenergynum = view.findViewById(R.id.item_tv_lifeenergynum);
            item_tv_date = view.findViewById(R.id.item_tv_date);
        }
    }
}
