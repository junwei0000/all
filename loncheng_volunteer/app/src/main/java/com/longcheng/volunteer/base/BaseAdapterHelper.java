package com.longcheng.volunteer.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：MarkMingShuai
 * 时间 2017-8-9 11:07
 * 邮箱：mark_mingshuai@163.com
 */

public abstract class BaseAdapterHelper<T> extends android.widget.BaseAdapter {
    public Context context = null;
    public List<T> list = null;
    public LayoutInflater inflater = null;

    public BaseAdapterHelper(Context context, List<T> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //重新加载List的数据
    public void reloadListView(List<T> data, boolean isClear) {
        if (isClear) {
            list.clear();
            list = new ArrayList<>();
        }
        if (data != null && !data.isEmpty()) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    //重新加载List的数据
    public void refreshListView(List<T> data) {
        list = data;
        notifyDataSetChanged();
    }

    //清除所有数据
    public void removeAll() {
        list.clear();
        notifyDataSetChanged();
    }

    //根据list的position删除单条数据
    public void removeItem(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    //删除多条数据
    public void removeItems(List<Map<String, Object>> _list) {
        list.removeAll(_list);
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItemView(position, convertView, parent, list, inflater);
    }

    public abstract View getItemView(int position, View convertView, ViewGroup parent, List<T>
            list, LayoutInflater inflater);
//    public abstract View getItemView(int position, View convertView, ViewGroup parent , LayoutInflater inflater);
}
