package com.longcheng.lifecareplan.modular.helpwith.connon.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.connon.activity.CHelpDetailAddActivity;
import com.longcheng.lifecareplan.modular.helpwith.connon.activity.CHelpGoWithActivity;
import com.longcheng.lifecareplan.modular.helpwith.connon.activity.CHelpMrrayActivity;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpListDataBean;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：热推互助
 */

public class MyCHelpListAdapter extends BaseAdapterHelper<CHelpListDataBean.CHelpListItemBean> {
    ViewHolder mHolder = null;
    Activity context;

    public MyCHelpListAdapter(Activity context, List<CHelpListDataBean.CHelpListItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CHelpListDataBean.CHelpListItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.connon_myhelplist_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        CHelpListDataBean.CHelpListItemBean mHelpItemBean = list.get(position);
        int person_number = mHelpItemBean.getPerson_number();
        int type = mHelpItemBean.getType();
        if (type == 1) {
            mHolder.item_tv_content.setText(person_number + "人结缘组队互祝");
        } else if (type == 2) {
            mHolder.item_tv_content.setText(person_number + "人结伴组队互祝");
        } else {
            mHolder.item_tv_content.setText(person_number + "人组队互祝");
        }

        mHolder.item_tv_name.setText(mHelpItemBean.getTeam_number_name());
        mHolder.item_tv_time.setText(mHelpItemBean.getCreate_time());
        mHolder.item_tv_header.setText("队长：" + mHelpItemBean.getUser_name());
        int grant_status = mHelpItemBean.getGrant_status();
        if (grant_status == 1) {
            mHolder.item_iv_status.setVisibility(View.VISIBLE);
        } else {
            mHolder.item_iv_status.setVisibility(View.GONE);
        }
        mHolder.item_layout.setTag(mHelpItemBean);
        mHolder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHelpListDataBean.CHelpListItemBean mHelpItemBean = (CHelpListDataBean.CHelpListItemBean) v.getTag();
                int process_status = mHelpItemBean.getProcess_status();
                int process_status_item = mHelpItemBean.getProcess_status_item();//流程状态：0 人未满,1 人已满 2 已开桌 3 已完成
                String knp_group_room_id = mHelpItemBean.getKnp_group_room_id();
                int role = mHelpItemBean.getRole();//1队长 ；2队员
                if (process_status >= 2 || process_status_item >= 2) {
                    if (role == 1 && process_status == 0) {
                        //队长时，并且人未满
                        Intent intent = new Intent(context, CHelpGoWithActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("knp_group_room_id", knp_group_room_id);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, CHelpDetailAddActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("knp_group_room_id", knp_group_room_id);
                        context.startActivity(intent);
                    }
                } else {
                    Intent intent;
                    if (mHelpItemBean.getType() == 1) {
                        intent = new Intent(context, CHelpMrrayActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("knp_group_room_id", knp_group_room_id);
                        context.startActivity(intent);
                    } else {
                        intent = new Intent(context, CHelpGoWithActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("knp_group_room_id", knp_group_room_id);
                        context.startActivity(intent);
                    }
                }
            }
        });
        return convertView;
    }


    private class ViewHolder {

        private LinearLayout item_layout;
        private TextView item_tv_content;
        private TextView item_tv_name;
        private TextView item_tv_time;
        private TextView item_tv_header;
        private ImageView item_iv_status;


        public ViewHolder(View view) {
            item_layout = view.findViewById(R.id.item_layout);
            item_tv_content = view.findViewById(R.id.item_tv_content);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_time = view.findViewById(R.id.item_tv_time);
            item_tv_header = view.findViewById(R.id.item_tv_header);
            item_iv_status = view.findViewById(R.id.item_iv_status);
        }
    }
}
