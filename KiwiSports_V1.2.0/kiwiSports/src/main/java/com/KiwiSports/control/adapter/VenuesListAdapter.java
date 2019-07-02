package com.KiwiSports.control.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.model.VenuesListInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.MyGridView;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VenuesListAdapter extends BaseAdapter {

    private ArrayList<VenuesListInfo> list;
    private Activity context;
    private HashMap<String,ArrayList<String>> mUserMap;

    public VenuesListAdapter(Activity context, ArrayList<VenuesListInfo> list) {
        super();
        this.context = context;
        this.list = list;
    }
    public HashMap<String, ArrayList<String>> getmUserMap() {
        return mUserMap;
    }

    public void setmUserMap(HashMap<String, ArrayList<String>> mUserMap) {
        this.mUserMap = mUserMap;
    }
    public ArrayList<VenuesListInfo> getList() {
        return list;
    }

    public void setList(ArrayList<VenuesListInfo> list) {
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
        VenuesListInfo stadiumObj = (VenuesListInfo) list.get(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.venues_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.listitem_tv_name = (TextView) convertView
                    .findViewById(R.id.listitem_tv_name);
            viewHolder.listitem_tv_dian = (TextView) convertView
                    .findViewById(R.id.listitem_tv_dian);
            viewHolder.listitem_tv_status = (TextView) convertView
                    .findViewById(R.id.listitem_tv_status);
            viewHolder.listitem_gv_userthumb = (MyGridView) convertView
                    .findViewById(R.id.listitem_gv_userthumb);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String name = stadiumObj.getField_name();
        String Venuestatus = stadiumObj.getVenuestatus();
        String Audit_status = stadiumObj.getAudit_status();
        // 默认价格
        viewHolder.listitem_tv_name.setText(name);
        String tiltle = "";
        viewHolder.listitem_tv_dian.setVisibility(View.GONE);
        viewHolder.listitem_tv_status.setVisibility(View.GONE);
        if (Venuestatus.equals("0")) {
            tiltle = CommonUtils.getInstance().getString(context,
                    R.string.venues_status_closed);
            viewHolder.listitem_tv_dian.setVisibility(View.VISIBLE);
            viewHolder.listitem_tv_status.setVisibility(View.VISIBLE);
        }
        if (Audit_status.equals("0")) {
            tiltle = CommonUtils.getInstance().getString(context,
                    R.string.venues_status_verify);
            viewHolder.listitem_tv_dian.setVisibility(View.VISIBLE);
            viewHolder.listitem_tv_status.setVisibility(View.VISIBLE);
        }
        if (Audit_status.equals("-1")) {
            tiltle = CommonUtils.getInstance().getString(context,
                    R.string.venues_status_fali);
            viewHolder.listitem_tv_dian.setVisibility(View.VISIBLE);
            viewHolder.listitem_tv_status.setVisibility(View.VISIBLE);
        }
        viewHolder.listitem_tv_status.setText(tiltle);

        viewHolder.listitem_gv_userthumb.setVisibility(View.GONE);
        String posid=stadiumObj.getPosid();
        if(mUserMap!=null&&mUserMap.containsKey(posid)){
            Log.e("posid==","posid=----------"+posid);
            ArrayList<String> mUserList = mUserMap.get(posid);
            if (mUserList != null && mUserList.size() > 0) {
                viewHolder.listitem_gv_userthumb.setVisibility(View.VISIBLE);
                VenuesListUserAdapter mVenuesListUserAdapter = new VenuesListUserAdapter(context, mUserList);
                viewHolder.listitem_gv_userthumb.setAdapter(mVenuesListUserAdapter);
            }
        }
        //ListView 嵌套 GridView ，会导致ListView的item中,GridView的那部分位置，点击对ListView的OnItemClickListener的行为无效
        //原因是gridview抢了listlview的焦点
        //在自己定义的item的布局最外层的layout 加入 这个标签 android:descendantFocusability="blocksDescendants"，就可以点击
        viewHolder.listitem_gv_userthumb.setClickable(false);
        viewHolder.listitem_gv_userthumb.setPressed(false);
        viewHolder.listitem_gv_userthumb.setEnabled(false);
        return convertView;
    }

    class ViewHolder {
        public TextView listitem_tv_name;
        public TextView listitem_tv_dian;
        public TextView listitem_tv_status;
        public MyGridView listitem_gv_userthumb;

    }
}
