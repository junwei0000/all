package com.KiwiSports.control.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.KiwiSports.R;
import com.KiwiSports.model.VenuesListInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ImageLoader;
import com.KiwiSports.utils.MyGridView;

import java.util.ArrayList;

public class VenuesListUserAdapter extends BaseAdapter {

    private final ImageLoader asyncImageLoader;
    private ArrayList<String> list;
    private Activity context;

    public VenuesListUserAdapter(Activity context, ArrayList<String> list) {
        super();
        this.context = context;
        this.list = list;
        asyncImageLoader = new ImageLoader(context, "headCircularImg");
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
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
        String url = (String) list.get(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.venues_list_item_useritem, null);
            viewHolder = new ViewHolder();
            viewHolder.item_thumb = (ImageView) convertView
                    .findViewById(R.id.item_thumb);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(url)) {
            asyncImageLoader.DisplayImage(url, viewHolder.item_thumb);
        }
        return convertView;
    }

    class ViewHolder {
        public ImageView item_thumb;
    }
}
