package com.longcheng.volunteer.modular.home.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseAdapterHelper;
import com.longcheng.volunteer.modular.helpwith.energy.activity.ProgressUtils;
import com.longcheng.volunteer.modular.home.bean.HomeItemBean;
import com.longcheng.volunteer.utils.ConstantManager;
import com.longcheng.volunteer.utils.glide.GlideDownLoadImage;
import com.longcheng.volunteer.widget.jswebview.view.NumberProgressBar;

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
        mHolder.item_layout_right.setVisibility(View.GONE);
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

        int progress = mHelpItemBean.getProgress();
        mHolder.pb_lifeenergynum.setProgress(progress);
        mProgressUtils.showNum(progress, mHolder.pb_lifeenergynum.getMax(), mHolder.item_pb_num);

        String showT = "已有<font color=\"#231815\">" + ability_price_action + "</font>生命能量";
        mHolder.item_tv_lifeenergynum.setText(Html.fromHtml(showT));
        mHolder.item_tv_date.setText(date);
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_communethumb;
        private TextView item_iv_communename;

        private ImageView item_iv_thumb;
        private TextView item_tv_content;
        private TextView item_tv_name;

        private NumberProgressBar pb_lifeenergynum;
        private TextView item_pb_num;
        private TextView item_tv_lifeenergynum;
        private TextView item_tv_date;
        private LinearLayout item_layout_right;

        public ViewHolder(View view) {
            item_layout_right = (LinearLayout) view.findViewById(R.id.item_layout_right);
            item_iv_communethumb = (ImageView) view.findViewById(R.id.item_iv_communethumb);
            item_iv_communename = (TextView) view.findViewById(R.id.item_iv_communename);

            item_iv_thumb = (ImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_content = (TextView) view.findViewById(R.id.item_tv_content);
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);

            pb_lifeenergynum = (NumberProgressBar) view.findViewById(R.id.item_pb_lifeenergynum);
            item_pb_num = (TextView) view.findViewById(R.id.item_pb_num);
            item_tv_lifeenergynum = (TextView) view.findViewById(R.id.item_tv_lifeenergynum);
            item_tv_date = (TextView) view.findViewById(R.id.item_tv_date);
        }
    }
}
