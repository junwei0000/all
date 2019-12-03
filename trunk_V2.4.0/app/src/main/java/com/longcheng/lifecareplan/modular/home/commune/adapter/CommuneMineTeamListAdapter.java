package com.longcheng.lifecareplan.modular.home.commune.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.home.commune.activity.CreateTeamActivity;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneItemBean;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;

import java.util.List;


public class CommuneMineTeamListAdapter extends BaseAdapterHelper<CommuneItemBean> {
    private final ImageLoader asyncImageLoader;
    ViewHolder mHolder = null;
    Context context;
    int role;

    public CommuneMineTeamListAdapter(Context context, List<CommuneItemBean> list, int role) {
        super(context, list);
        this.context = context;
        this.role = role;
        asyncImageLoader = new ImageLoader(context, "headImg");
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CommuneItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.commune_mine_team_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        CommuneItemBean mRankItemBean = list.get(position);
        String User_name = mRankItemBean.getUser_name();
        String Team_name = mRankItemBean.getTeam_name();
        String engerynum = mRankItemBean.getCount();
        String thumb = mRankItemBean.getAvatar();
        if (TextUtils.isEmpty(User_name)) {
            User_name = "暂无大队长";
        }
        if (TextUtils.isEmpty(Team_name)) {
            Team_name = "";
        }
        if (TextUtils.isEmpty(engerynum)) {
            engerynum = "";
        }
        mHolder.item_tv_name.setText(Team_name);
        mHolder.item_tv_shequ.setText(User_name);
        mHolder.item_tv_engerynum.setText(engerynum + "成员");
        asyncImageLoader.DisplayImage(thumb, mHolder.item_iv_thumb);

        if (role > 0) {//角色 1：主任 2：副主任 0：无（>0的时候出现编辑大队按钮）
            mHolder.item_iv_edit.setVisibility(View.VISIBLE);
            mHolder.item_iv_thumb.setTag(mRankItemBean.getTeam_id());
            mHolder.item_layout_edit.setTag(mRankItemBean.getTeam_id());
            mHolder.item_iv_thumb.setOnClickListener(click);
            mHolder.item_layout_edit.setOnClickListener(click);
        } else {
            mHolder.item_iv_edit.setVisibility(View.GONE);
        }
        return convertView;
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int team_id = (int) v.getTag();
            Intent intent = new Intent(context, CreateTeamActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("type", "edit");
            intent.putExtra("team_id", team_id);
            context.startActivity(intent);
        }
    };

    private class ViewHolder {


        private CircleImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_shequ;
        private TextView item_tv_engerynum;

        private ImageView item_iv_edit;
        private LinearLayout item_layout_edit;

        public ViewHolder(View convertView) {
            item_iv_thumb = (CircleImageView) convertView.findViewById(R.id.item_iv_thumb);
            item_tv_name = (TextView) convertView.findViewById(R.id.item_tv_name);
            item_tv_shequ = (TextView) convertView.findViewById(R.id.item_tv_shequ);
            item_tv_engerynum = (TextView) convertView.findViewById(R.id.item_tv_engerynum);
            item_iv_edit = (ImageView) convertView.findViewById(R.id.item_iv_edit);
            item_layout_edit = (LinearLayout) convertView.findViewById(R.id.item_layout_edit);
        }
    }
}
