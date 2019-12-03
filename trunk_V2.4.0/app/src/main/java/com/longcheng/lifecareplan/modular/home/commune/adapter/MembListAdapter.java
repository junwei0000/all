package com.longcheng.lifecareplan.modular.home.commune.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleItemBean;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneItemBean;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MembListAdapter extends BaseAdapterHelper<CommuneItemBean> {
    private final ImageLoader asyncImageLoader;
    ViewHolder mHolder = null;

    Context context;

    public MembListAdapter(Context context, List<CommuneItemBean> list) {
        super(context, list);
        this.context = context;
        asyncImageLoader = new ImageLoader(context, "headImg");
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CommuneItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.commune_mine_member_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        CommuneItemBean mHelpWithInfo = list.get(position);

        mHolder.item_tv_name.setText(mHelpWithInfo.getUser_name());
        String phone = mHelpWithInfo.getPhone();
        if (TextUtils.isEmpty(phone)) {
            mHolder.item_tv_phone.setText("暂无");
        } else {
            mHolder.item_tv_phone.setText(phone);
        }
        //是否CHO 0：否（展示位社员） 1：是
        int is_cho = mHelpWithInfo.getIs_cho();
        int star_level = mHelpWithInfo.getStar_level();
        if (is_cho == 0) {
            mHolder.item_tv_num.setText("社员");
            mHolder.item_tv_num.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        } else {
            mHolder.item_tv_num.setText(star_level + "星");
            mHolder.item_tv_num.setTextColor(context.getResources().getColor(R.color.red));
        }
        asyncImageLoader.DisplayImage(mHelpWithInfo.getAvatar(), mHolder.item_iv_thumb);
        return convertView;
    }


    private class ViewHolder {
        private CircleImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_phone;
        private TextView item_tv_num;

        public ViewHolder(View view) {
            item_iv_thumb = (CircleImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            item_tv_phone = (TextView) view.findViewById(R.id.item_tv_phone);
            item_tv_num = (TextView) view.findViewById(R.id.item_tv_num);
        }
    }
}
