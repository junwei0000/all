package com.longcheng.lifecareplanTv.home.dynamic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplanTv.R;
import com.longcheng.lifecareplanTv.base.MyApplication;
import com.longcheng.lifecareplanTv.home.dynamic.activity.DynamicActivity;
import com.longcheng.lifecareplanTv.home.help.HelpActivity;
import com.longcheng.lifecareplanTv.home.menu.bean.MenuInfo;
import com.longcheng.lifecareplanTv.home.picture.activity.PictureActivity;
import com.longcheng.lifecareplanTv.home.vedio.activity.VediosActivity;
import com.longcheng.lifecareplanTv.utils.ToastUtilsNew;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.BaseRecyclerAdapter;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.BaseRecyclerViewHolder;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.FocusUtil;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.SearchResultModel;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.TvRecyclerViewList;

import java.util.List;

/**
 * Created by liuyu on 17/1/20.
 * 防止RecyclerView更新数据的时候焦点丢失:
 * (1)adapter执行setHasStableIds(true)方法
 * (2)重写getItemId()方法,让每个view都有各自的id
 * (3)RecyclerView的动画必须去掉
 */
public class DynamicsAdapter extends BaseRecyclerAdapter<BaseRecyclerViewHolder, SearchResultModel> {

    static Context context;

    public DynamicsAdapter(Context context, List<SearchResultModel> dataList) {
        super(context, dataList);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            case 1:
                return new GuessYouLikeViewHolder(mInflate.inflate(R.layout.dynamic_item, viewGroup, false));
        }
        return null;
    }


    @Override
    protected void onBindBaseViewHolder(final BaseRecyclerViewHolder viewHolder, final int position) {

        if (getItemCount() > 0) {
            if (viewHolder instanceof GuessYouLikeViewHolder) {
                final GuessYouLikeViewHolder holder = (GuessYouLikeViewHolder) viewHolder;
                holder.setData(mDataList.get(position), position);
            }
            viewHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    /**
                     * 设置选中item 大小
                     */
                    if (hasFocus) {
                        view.getBackground().setAlpha(35);
                        viewHolder.focusIn();
                        FocusUtil.onFocusIn(view, 1.00f);
                    } else {
                        viewHolder.focusOut();
                        FocusUtil.onFocusOut(view, 1.00f);
                    }

                }
            });
            viewHolder.itemView.setTag(position);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    // 点击事件
                    int position = (int) arg0.getTag();
                    ToastUtilsNew.showToast("点击了   " + position);
                }
            });
        }

    }

    public static class GuessYouLikeViewHolder extends BaseRecyclerViewHolder {
        private ImageView iv_avatar;

        protected GuessYouLikeViewHolder(View itemView) {
            super(itemView);
            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
        }

        @Override
        public void focusIn() {
        }

        @Override
        public void focusOut() {
        }

        public void setData(final SearchResultModel mInfo, int position) {
            if (mInfo == null) {
                return;
            }
        }
    }
}
