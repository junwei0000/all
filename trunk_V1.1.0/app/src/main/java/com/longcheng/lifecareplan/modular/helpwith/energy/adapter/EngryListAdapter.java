package com.longcheng.lifecareplan.modular.helpwith.energy.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.ProgressUtils;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpItemBean;
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

public class EngryListAdapter extends BaseAdapterHelper<HelpItemBean> {
    ViewHolder mHolder = null;
    Context context;
    ProgressUtils mProgressUtils;

    public EngryListAdapter(Context context, List<HelpItemBean> list) {
        super(context, list);
        this.context = context;
        mProgressUtils = new ProgressUtils(context);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<HelpItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_home_hotpush_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        HelpItemBean mHelpItemBean = list.get(position);
        String groupimg = mHelpItemBean.getGroup_img();
        String gs_name = mHelpItemBean.getGs_name();
        int my_bless = mHelpItemBean.getMy_bless();
        int bless_me = mHelpItemBean.getBless_me();
        String action_image = mHelpItemBean.getAction_image();
        String action_name = mHelpItemBean.getAction_name();
        String h_user = mHelpItemBean.getH_user();
        String date = mHelpItemBean.getDate();
        String ability_price_action = mHelpItemBean.getAbility_price_action();

        GlideDownLoadImage.getInstance().loadCircleImageCommune(context, groupimg, mHolder.item_iv_communethumb);
        GlideDownLoadImage.getInstance().loadCircleImageRole(context, action_image, mHolder.item_iv_thumb, ConstantManager.image_angle);
        mHolder.item_iv_communename.setText(gs_name);
        if (my_bless == 0) {
            mHolder.item_iv_helpother.setVisibility(View.GONE);
            mHolder.item_tv_helpother.setVisibility(View.GONE);
        } else {
            mHolder.item_iv_helpother.setVisibility(View.VISIBLE);
            mHolder.item_tv_helpother.setVisibility(View.VISIBLE);
            mHolder.item_tv_helpother.setText("我祝福" + my_bless + "次");
        }
        if (bless_me == 0) {
            mHolder.item_iv_helpme.setVisibility(View.GONE);
            mHolder.item_tv_helpme.setVisibility(View.GONE);
        } else {
            mHolder.item_iv_helpme.setVisibility(View.VISIBLE);
            mHolder.item_tv_helpme.setVisibility(View.VISIBLE);
            mHolder.item_tv_helpme.setText("祝福我" + bless_me + "次");
        }
        mHolder.item_tv_content.setText(action_name);
        mHolder.item_tv_name.setText("接福人：" + h_user);

        int progress = mHelpItemBean.getProgress();
        mHolder.pb_lifeenergynum.setProgress(progress);
        mHolder.pb_lifeenergynum.setReachedBarColor(context.getResources().getColor(R.color.red));
        mHolder.item_pb_num.setBackgroundResource(R.drawable.corners_bg_redprogress);
        mProgressUtils.showNum(progress, mHolder.pb_lifeenergynum.getMax(), mHolder.item_pb_num);
        String showT = "已有<font color=\"#231815\">" + ability_price_action + "</font>生命能量";
        mHolder.item_tv_lifeenergynum.setText(Html.fromHtml(showT));
        mHolder.item_tv_date.setText(date);
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_communethumb;
        private TextView item_iv_communename;

        private ImageView item_iv_helpother;
        private TextView item_tv_helpother;

        private ImageView item_iv_helpme;
        private TextView item_tv_helpme;

        private ImageView item_iv_thumb;
        private TextView item_tv_content;
        private TextView item_tv_name;

        private NumberProgressBar pb_lifeenergynum;
        private TextView item_pb_num;
        private TextView item_tv_lifeenergynum;
        private TextView item_tv_date;

        public ViewHolder(View view) {

            item_iv_communethumb = (ImageView) view.findViewById(R.id.item_iv_communethumb);
            item_iv_communename = (TextView) view.findViewById(R.id.item_iv_communename);
            item_iv_helpother = (ImageView) view.findViewById(R.id.item_iv_helpother);
            item_tv_helpother = (TextView) view.findViewById(R.id.item_tv_helpother);
            item_iv_helpme = (ImageView) view.findViewById(R.id.item_iv_helpme);
            item_tv_helpme = (TextView) view.findViewById(R.id.item_tv_helpme);

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
