package com.KiwiSports.control.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.KiwiSports.R;
import com.KiwiSports.model.VenuesListInfo;
import com.KiwiSports.model.VenuesUsersInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ImageLoader;
import com.KiwiSports.utils.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;

public class MapListAdapter extends BaseAdapter {

    private ArrayList<VenuesUsersInfo> list;
    private Activity context;
    private HashMap<String, String> mUserListHideMap;
    ImageLoader asyncImageLoader;

    public MapListAdapter(Activity context, ArrayList<VenuesUsersInfo> list) {
        super();
        this.context = context;
        this.list = list;
        asyncImageLoader = new ImageLoader(context, "headImg");
    }

    public HashMap<String, String> getmUserListHideMap() {
        return mUserListHideMap;
    }

    public void setmUserListHideMap(HashMap<String, String> mUserListHideMap) {
        this.mUserListHideMap = mUserListHideMap;
    }

    public ArrayList<VenuesUsersInfo> getList() {
        return list;
    }

    public void setList(ArrayList<VenuesUsersInfo> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VenuesUsersInfo stadiumObj = (VenuesUsersInfo) list.get(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.map_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.listitem_tv_name = (TextView) convertView
                    .findViewById(R.id.listitem_tv_name);
            viewHolder.listitem_iv_thumb = (ImageView) convertView
                    .findViewById(R.id.listitem_iv_thumb);
            viewHolder.listitem_tv_status = (TextView) convertView
                    .findViewById(R.id.listitem_tv_status);
            viewHolder.listitem_layout_status = (LinearLayout) convertView
                    .findViewById(R.id.listitem_layout_status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 默认价格
        viewHolder.listitem_tv_name.setText(stadiumObj.getNick_name());
        asyncImageLoader.DisplayImage(stadiumObj.getAlbum_url(), viewHolder.listitem_iv_thumb);


        if (mUserListHideMap != null && mUserListHideMap.containsKey(stadiumObj.getUid())) {
            viewHolder.listitem_layout_status.setBackgroundResource(R.drawable.corners_bg_qing);
            viewHolder.listitem_tv_status.setText("显示");
        } else {
            viewHolder.listitem_layout_status.setBackgroundResource(R.drawable.corners_bg_gray2);
            viewHolder.listitem_tv_status.setText("隐藏");
        }
        viewHolder.listitem_layout_status.setTag(stadiumObj.getUid());
        viewHolder.listitem_layout_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = (String) v.getTag();
                if (mUserListHideMap != null) {
                    if (!mUserListHideMap.containsKey(uid)) {
                        mUserListHideMap.put(uid, uid);
                    } else {
                        mUserListHideMap.remove(uid);
                    }
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    class ViewHolder {
        public TextView listitem_tv_name;
        public ImageView listitem_iv_thumb;
        public TextView listitem_tv_status;
        public LinearLayout listitem_layout_status;

    }
}
