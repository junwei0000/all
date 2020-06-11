package com.longcheng.lifecareplan.modular.helpwith.lifestyle.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        String groupimg = mHelpItemBean.getGroup_avatar();
        String gs_name = mHelpItemBean.getReceive_group_name();
        String action_image = mHelpItemBean.getGoods_img();
        String action_name = mHelpItemBean.getGoods_name();
        String h_user = mHelpItemBean.getReceive_user_name();
        String date = mHelpItemBean.getDate();
        mHolder.item_tv_date.setText(date);
        GlideDownLoadImage.getInstance().loadCircleImageCommune(context, groupimg, mHolder.item_iv_communethumb);
        GlideDownLoadImage.getInstance().loadCircleImageRoleGoods(context, action_image, mHolder.item_iv_thumb, 0);
        mHolder.item_iv_communename.setText(gs_name);
        mHolder.item_tv_content.setText(action_name);
        mHolder.item_tv_name.setText("接福人：" + h_user);

        int super_ability_progress = mHelpItemBean.getProgress();
        mHolder.pb_lifeenergynum.setProgress(super_ability_progress);
        mProgressUtils.showNum(super_ability_progress, mHolder.pb_lifeenergynum, mHolder.item_pb_numne);
        mHolder.pb_lifeenergynum.setReachedBarColor(context.getResources().getColor(R.color.engry_btn_bg));
        ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_pb_numne, R.color.engry_btn_bg);
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_communethumb;
        private TextView item_iv_communename;
        private TextView item_tv_date;


        private ImageView item_iv_thumb;
        private TextView item_tv_content;
        private TextView item_tv_name;

        private NumberProgressBar pb_lifeenergynum;
        private TextView item_pb_numne;


        public ViewHolder(View view) {
            item_iv_communethumb = view.findViewById(R.id.item_iv_communethumb);
            item_iv_communename = view.findViewById(R.id.item_iv_communename);
            item_iv_thumb = view.findViewById(R.id.item_iv_thumb);
            item_tv_content = view.findViewById(R.id.item_tv_content);
            item_tv_name = view.findViewById(R.id.item_tv_name);

            pb_lifeenergynum = view.findViewById(R.id.item_pb_lifeenergynum);
            item_pb_numne = view.findViewById(R.id.item_pb_numne);
            item_tv_date = view.findViewById(R.id.item_tv_date);
        }
    }
}
