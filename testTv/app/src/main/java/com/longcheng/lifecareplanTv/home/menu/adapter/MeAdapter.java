package com.longcheng.lifecareplanTv.home.menu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplanTv.R;
import com.longcheng.lifecareplanTv.home.dynamic.activity.DynamicActivity;
import com.longcheng.lifecareplanTv.home.help.HelpActivity;
import com.longcheng.lifecareplanTv.home.menu.bean.MenuInfo;
import com.longcheng.lifecareplanTv.home.picture.activity.PictureActivity;
import com.longcheng.lifecareplanTv.home.vedio.activity.VediosActivity;
import com.longcheng.lifecareplanTv.utils.FontUtils;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.BaseRecyclerAdapter;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.BaseRecyclerViewHolder;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.FocusUtil;

import java.util.List;

/**
 * Created by liuyu on 17/1/20.
 * 防止RecyclerView更新数据的时候焦点丢失:
 * (1)adapter执行setHasStableIds(true)方法
 * (2)重写getItemId()方法,让每个view都有各自的id
 * (3)RecyclerView的动画必须去掉
 */
public class MeAdapter extends BaseRecyclerAdapter<BaseRecyclerViewHolder, MenuInfo> {

    static Context context;

    public MeAdapter(Context context, List<MenuInfo> dataList) {
        super(context, dataList);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
//        if(position == 0 || position == 1){
//            return 2;
//        }
        return 1;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            case 1:
                return new GuessYouLikeViewHolder(mInflate.inflate(R.layout.menu_item, viewGroup, false));
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
                    if (position == 0) {
                        Intent intent = new Intent(context, VediosActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                    } else if (position == 1) {
                        Intent intent = new Intent(context, PictureActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                    } else if (position == 2) {
                        Intent intent = new Intent(context, HelpActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                    } else if (position == 3) {
                        Intent intent = new Intent(context, DynamicActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                    } else if (position == 4) {
                        Intent intent = new Intent(context, DynamicActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                    }
                }
            });
        }

    }

    public static class GuessYouLikeViewHolder extends BaseRecyclerViewHolder {
        private ImageView item_iv_icon;
        private TextView item_tv_dayname, item_tv_jieeqiname, item_tv_huaname, item_tv_iconname;
        private RelativeLayout imgbg;

        protected GuessYouLikeViewHolder(View itemView) {
            super(itemView);
            imgbg = (RelativeLayout) itemView.findViewById(R.id.imgbg);
            item_tv_dayname = (TextView) itemView.findViewById(R.id.item_tv_dayname);
            item_tv_jieeqiname = (TextView) itemView.findViewById(R.id.item_tv_jieeqiname);
            item_tv_huaname = (TextView) itemView.findViewById(R.id.item_tv_huaname);
            item_iv_icon = (ImageView) itemView.findViewById(R.id.item_iv_icon);
            item_tv_iconname = (TextView) itemView.findViewById(R.id.item_tv_iconname);
        }

        @Override
        public void focusIn() {
        }

        @Override
        public void focusOut() {
        }

        public void setData(final MenuInfo mInfo, int position) {
            if (mInfo == null) {
                return;
            }
            FontUtils.setFontKaiTi(context, item_tv_dayname);
            FontUtils.setFontKaiTi(context, item_tv_jieeqiname);
            FontUtils.setFontKaiTi(context, item_tv_huaname);
            FontUtils.setFontKaiTi(context, item_tv_iconname);
            imgbg.setBackgroundResource(mInfo.getBgImgId());
            item_tv_dayname.setText(mInfo.getNextday());
            item_tv_jieeqiname.setText(mInfo.getJieqiname());
            item_tv_huaname.setText(mInfo.getHuaname());
            item_tv_iconname.setText(mInfo.getIconname());
            item_iv_icon.setBackgroundResource(mInfo.getIconId());
            if (position == 4) {
                item_tv_dayname.setVisibility(View.INVISIBLE);
                item_tv_jieeqiname.setVisibility(View.INVISIBLE);
                item_tv_huaname.setVisibility(View.INVISIBLE);
                item_tv_iconname.setVisibility(View.INVISIBLE);
                item_iv_icon.setVisibility(View.INVISIBLE);
            } else {
                item_tv_dayname.setVisibility(View.VISIBLE);
                item_tv_jieeqiname.setVisibility(View.VISIBLE);
                item_tv_huaname.setVisibility(View.VISIBLE);
                item_tv_iconname.setVisibility(View.VISIBLE);
                item_iv_icon.setVisibility(View.VISIBLE);
            }
        }
    }
}
