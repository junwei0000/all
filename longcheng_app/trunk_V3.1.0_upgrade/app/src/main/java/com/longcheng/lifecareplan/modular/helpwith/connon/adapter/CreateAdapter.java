package com.longcheng.lifecareplan.modular.helpwith.connon.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CreateBean;

import java.util.List;


public class CreateAdapter extends BaseAdapterHelper<CreateBean> {
    ViewHolder mHolder = null;
    Activity context;

    int selectPosition;

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public CreateAdapter(Activity context, List<CreateBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CreateBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.connon_createnew_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        CreateBean mHelpItemBean = list.get(position);

        mHolder.item_tv_title.setText(mHelpItemBean.getTitle());
        mHolder.item_tv_ablity.setText(mHelpItemBean.getAblity());
        mHolder.item_tv_subtitle.setText(mHelpItemBean.getSubtitle());
        if (selectPosition == position) {
            mHolder.item_layout.setBackgroundResource(R.mipmap.create_select);
            mHolder.item_tv_title.setTextColor(context.getResources().getColor(R.color.white));
            mHolder.item_tv_ablity.setTextColor(context.getResources().getColor(R.color.white_tran));
            mHolder.item_tv_subtitle.setTextColor(context.getResources().getColor(R.color.white_tran));
        } else {
            mHolder.item_layout.setBackgroundResource(R.mipmap.create_selectnot);
            mHolder.item_tv_title.setTextColor(context.getResources().getColor(R.color.color_222222));
            mHolder.item_tv_ablity.setTextColor(context.getResources().getColor(R.color.c9));
            mHolder.item_tv_subtitle.setTextColor(context.getResources().getColor(R.color.c9));
        }
        return convertView;
    }


    private class ViewHolder {

        private LinearLayout item_layout;
        private TextView item_tv_title;
        private TextView item_tv_ablity;
        private TextView item_tv_subtitle;


        public ViewHolder(View view) {
            item_layout = view.findViewById(R.id.item_layout);
            item_tv_title = view.findViewById(R.id.item_tv_title);
            item_tv_ablity = view.findViewById(R.id.item_tv_ablity);
            item_tv_subtitle = view.findViewById(R.id.item_tv_subtitle);
        }
    }
}
