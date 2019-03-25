package com.longcheng.lifecareplantv.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Dell on 2017/11/21.
 */

public abstract class BaseRecyclerViewAdapterHelper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context mContext = null;
    public List<T> list = null;
    public LayoutInflater mInflater = null;

    public BaseRecyclerViewAdapterHelper(Context mContext, List<T> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateMyViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindMyViewHolder(holder, position);
    }

    public abstract RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindMyViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    /**
     * 全局刷新
     *
     * @param _list
     * @param isClear
     */
    public void reloadRecyclerView(List<T> _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }

    /**
     * 适配器中添加单条数据，刷新UI
     *
     * @param position 要添加的数据所在适配器中的位置
     * @param data     要添加的数据
     */
    public void addItem(int position, T data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * 适配器中批量添加数据，刷新UI
     *
     * @param _list         批量添加的集合
     * @param positionStart 添加到适配器中的起始位置
     */
    public void addItems(List<T> _list, int positionStart) {
        list.addAll(_list);
        int itemCount = list.size();
        notifyItemRangeInserted(positionStart, itemCount);
    }

    /**
     * 适配器中删除单条数据，刷新UI
     *
     * @param position 要删除的数据所在适配器中的位置
     */
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 适配器中批量删除多条数据，刷新UI
     *
     * @param _list         要删除的数据集合
     * @param positionStart 删除的数据在适配器中的起始位置
     */
    public void removeItems(List<T> _list, int positionStart) {
        list.removeAll(_list);
        int itemCount = list.size();
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    /**
     * 修改适配器中单条数据，刷新UI
     *
     * @param position 更新的数据所在适配器中的位置
     * @param data     更新后的数据集合
     */
    public void updateItem(int position, T data) {
        list.remove(position);
        list.add(position, data);
        notifyItemChanged(position);
    }
}
