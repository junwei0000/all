package com.longcheng.lifecareplan.modular.helpwith.lifestyle.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleListProgressUtils;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.bean.LifeStyleItemBean;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：热推互助
 */

public class LifeStyleListAdapter extends BaseAdapterHelper<LifeStyleItemBean> {
    ViewHolder mHolder = null;
    Activity context;
    LifeStyleListProgressUtils mProgressUtils;

    public LifeStyleListAdapter(Activity context, List<LifeStyleItemBean> list) {
        super(context, list);
        this.context = context;
        mProgressUtils = new LifeStyleListProgressUtils(context);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<LifeStyleItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.helpwith_lifistyle_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        LifeStyleItemBean mHelpItemBean = list.get(position);
        String groupimg = mHelpItemBean.getGroup_img();
        String gs_name = mHelpItemBean.getReceive_group_name();
        int my_bless = mHelpItemBean.getMy_bless();
        int bless_me = mHelpItemBean.getBless_me();
        String action_image = mHelpItemBean.getGoods_img();
        String action_name = mHelpItemBean.getGoods_name();
        String h_user = mHelpItemBean.getReceive_user_name();
        String skb_price_action = mHelpItemBean.getSkb_cumulative_price();
        String super_ability_cumulative_price = mHelpItemBean.getSuper_ability_cumulative_price();
        String date = mHelpItemBean.getDate();
        mHolder.item_tv_date.setText(date);
        GlideDownLoadImage.getInstance().loadCircleImageCommune(context, groupimg, mHolder.item_iv_communethumb);
        GlideDownLoadImage.getInstance().loadCircleImageRoleGoods(context, action_image, mHolder.item_iv_thumb, 0);
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


        String showcn = " | 已有" + super_ability_cumulative_price + "超能";
        mHolder.item_tv_lifeenergynum.setText(showcn);
        String showT = " | 已有" + skb_price_action + "寿康宝";
        mHolder.item_tv_lifeskbnum.setText(showT);


        int buy_type = mHelpItemBean.getBuy_type();
        if (buy_type == 2) {
            mHolder.item_tv_lifeenergynum.setVisibility(View.VISIBLE);
            mHolder.tv_jiange.setVisibility(View.GONE);
            mHolder.item_tv_lifeskbnum.setVisibility(View.GONE);
            mHolder.relat_cn.setVisibility(View.VISIBLE);
            mHolder.relat_skb.setVisibility(View.GONE);

            int super_ability_progress = mHelpItemBean.getSuper_ability_progress();
            mHolder.pb_lifeenergynum.setProgress(super_ability_progress);
            mProgressUtils.showNum(super_ability_progress, mHolder.pb_lifeenergynum, mHolder.item_pb_numne);
            mHolder.pb_lifeenergynum.setReachedBarColor(context.getResources().getColor(R.color.engry_btn_bg));
            ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_pb_numne, R.color.engry_btn_bg);
        } else if (buy_type == 3) {
            mHolder.item_tv_lifeenergynum.setVisibility(View.VISIBLE);
            mHolder.tv_jiange.setVisibility(View.VISIBLE);
            mHolder.item_tv_lifeskbnum.setVisibility(View.VISIBLE);
            mHolder.relat_cn.setVisibility(View.VISIBLE);
            mHolder.relat_skb.setVisibility(View.VISIBLE);


            int super_ability_progress = mHelpItemBean.getSuper_ability_progress();
            mHolder.pb_lifeenergynum.setProgress(super_ability_progress);
            mProgressUtils.showNum(super_ability_progress, mHolder.pb_lifeenergynum, mHolder.item_pb_numne);
            mHolder.pb_lifeenergynum.setReachedBarColor(context.getResources().getColor(R.color.engry_btn_bg));
            ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_pb_numne, R.color.engry_btn_bg);

            int progress = mHelpItemBean.getProgress();
            mHolder.item_pb_lifeskbnum.setProgress(progress);
            mProgressUtils.showNum(progress, mHolder.item_pb_lifeskbnum, mHolder.item_pb_numskb);
            mHolder.item_pb_lifeskbnum.setReachedBarColor(context.getResources().getColor(R.color.red));
            ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_pb_numskb, R.color.red);

        } else {
            mHolder.item_tv_lifeenergynum.setVisibility(View.GONE);
            mHolder.tv_jiange.setVisibility(View.GONE);
            mHolder.item_tv_lifeskbnum.setVisibility(View.VISIBLE);
            mHolder.relat_cn.setVisibility(View.GONE);
            mHolder.relat_skb.setVisibility(View.VISIBLE);

            int progress = mHelpItemBean.getProgress();
            mHolder.item_pb_lifeskbnum.setProgress(progress);
            mProgressUtils.showNum(progress, mHolder.item_pb_lifeskbnum, mHolder.item_pb_numskb);
            mHolder.item_pb_lifeskbnum.setReachedBarColor(context.getResources().getColor(R.color.red));
            ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_pb_numskb, R.color.red);
        }
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
        private TextView item_pb_numne;
        private NumberProgressBar item_pb_lifeskbnum;
        private TextView item_pb_numskb;
        private TextView item_tv_date;
        private TextView item_tv_lifeenergynum;
        private TextView item_tv_lifeskbnum;

        private RelativeLayout relat_cn;
        private RelativeLayout relat_skb;
        private TextView tv_jiange;


        public ViewHolder(View view) {
            tv_jiange = (TextView) view.findViewById(R.id.tv_jiange);
            relat_cn = (RelativeLayout) view.findViewById(R.id.relat_cn);
            relat_skb = (RelativeLayout) view.findViewById(R.id.relat_skb);
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
            item_pb_numne = (TextView) view.findViewById(R.id.item_pb_numne);
            item_pb_lifeskbnum = (NumberProgressBar) view.findViewById(R.id.item_pb_lifeskbnum);
            item_pb_numskb = (TextView) view.findViewById(R.id.item_pb_numskb);
            item_tv_lifeenergynum = (TextView) view.findViewById(R.id.item_tv_lifeenergynum);
            item_tv_lifeskbnum = (TextView) view.findViewById(R.id.item_tv_lifeskbnum);
            item_tv_date = (TextView) view.findViewById(R.id.item_tv_date);
        }
    }
}
