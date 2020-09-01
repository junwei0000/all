package com.longcheng.lifecareplan.modular.home.liveplay.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailItemInfo;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 */

public class CommentListAdapter extends BaseAdapterHelper<LiveDetailItemInfo> {
    ViewHolder mHolder = null;
    Context context;


    public CommentListAdapter(Context context, List<LiveDetailItemInfo> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<LiveDetailItemInfo> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.live_comment_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        LiveDetailItemInfo mHelpItemBean = list.get(position);
        String con = mHelpItemBean.getUser_name() + "：" + mHelpItemBean.getContent();
        mHolder.item_tv_content.setText(Html.fromHtml(con));
        mHolder.item_tv_content.getBackground().setAlpha(50);
        int type = mHelpItemBean.getType();
        if (type == 2) {
            mHolder.item_tv_content.setTextColor(context.getResources().getColor(R.color.live_comment_bg));
        } else {
            mHolder.item_tv_content.setTextColor(context.getResources().getColor(R.color.white));
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView item_tv_content;

        public ViewHolder(View view) {
            item_tv_content = view.findViewById(R.id.item_tv_content);
        }
    }
}
