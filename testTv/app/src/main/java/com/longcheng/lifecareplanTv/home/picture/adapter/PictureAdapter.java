package com.longcheng.lifecareplanTv.home.picture.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplanTv.R;
import com.longcheng.lifecareplanTv.utils.DensityUtil;
import com.longcheng.lifecareplanTv.utils.ToastUtilsNew;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.BaseRecyclerAdapter;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.BaseRecyclerViewHolder;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.FocusUtil;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.SearchResultModel;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.TvRecyclerView;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.TvRecyclerViewList;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.TvRecyclerViewPic;

import java.util.List;

/**
 * Created by liuyu on 17/1/20.
 * 防止RecyclerView更新数据的时候焦点丢失:
 * (1)adapter执行setHasStableIds(true)方法
 * (2)重写getItemId()方法,让每个view都有各自的id
 * (3)RecyclerView的动画必须去掉
 */
public class PictureAdapter extends BaseRecyclerAdapter<BaseRecyclerViewHolder, SearchResultModel> {


    private static final String TAG = "GuessYouLikeAdapter";
    private final TvRecyclerViewPic mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private OnLeftEdgeListener mOnLeftEdgeListener;

    Context context;

    public interface OnLeftEdgeListener {
        void onLeftEdge();
    }

    public void setOnLeftEdgeListener(OnLeftEdgeListener listener) {
        this.mOnLeftEdgeListener = listener;
    }

    public PictureAdapter(Context context, TvRecyclerViewPic recyclerView, GridLayoutManager layoutManager, List<SearchResultModel> dataList) {
        super(context, dataList);
        this.context = context;
        mRecyclerView = recyclerView;
        mLayoutManager = layoutManager;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            case 1:
                return new GuessYouLikeViewHolder(mInflate.inflate(R.layout.picture_item, viewGroup, false), context);
        }
        return null;
    }


    @Override
    protected void onBindBaseViewHolder(final BaseRecyclerViewHolder viewHolder, final int position) {

        final GuessYouLikeViewHolder holder = (GuessYouLikeViewHolder) viewHolder;
        holder.setData(mDataList.get(position));


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
                int postion = (int) arg0.getTag();
                ToastUtilsNew.showToast("点击了  " + postion + "");
            }
        });
        /**
         * (1)这个方法内还可以做边界的焦点处理
         * mRecyclerView.setNextFocusLeftId();//如果是到了左边缘,设置左边一排的view往左的焦点
         * mRecyclerView.setNextFocusRightId();//如果是到了右边缘,设置右边一排的view网右的焦点
         */
        if (mRecyclerView.isLeftEdge(position)) {
            viewHolder.itemView.setNextFocusLeftId(R.id.layout_left);
        }
        if (mRecyclerView.isRightEdge(position)) {
            viewHolder.itemView.setNextFocusLeftId(R.id.layout_right);
        }
        //(2)通过设置onKeyListener,屏蔽边界按键事件
        viewHolder.itemView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            Log.i(TAG, "isLeftEdge = " + mRecyclerView.isLeftEdge(position));
                            if (mOnLeftEdgeListener != null) {
                                mOnLeftEdgeListener.onLeftEdge();
                                return true;
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            Log.i(TAG, "isRightEdge = " + mRecyclerView.isRightEdge(position));
                            break;
                        case KeyEvent.KEYCODE_DPAD_UP:
                            Log.i(TAG, "isTopEdge = " + mRecyclerView.isTopEdge(position));
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            Log.i(TAG, "isBottomEdge = " + mRecyclerView.isBottomEdge(position));
                            break;
                    }
                }
                return false;
            }
        });
    }


    public static class GuessYouLikeViewHolder extends BaseRecyclerViewHolder {
        public ImageView ivResultImage;
        public TextView tvResultTitle;
        public LinearLayout layout_img;

        protected GuessYouLikeViewHolder(View itemView, Context context) {
            super(itemView);
            layout_img = (LinearLayout) itemView.findViewById(R.id.layout_img);
            ivResultImage = (ImageView) itemView.findViewById(R.id.iv_result_image);
            tvResultTitle = (TextView) itemView.findViewById(R.id.tv_result_title);
//            int width = (DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 100)) / 4;
//            int heigth = (int) (width * 0.7);
//            ivResultImage.setLayoutParams(new LinearLayout.LayoutParams(width, heigth));
        }

        @Override
        public void focusIn() {
            layout_img.setBackgroundResource(R.drawable.corners_bg_textselect);
        }

        @Override
        public void focusOut() {
            layout_img.setBackgroundResource(R.drawable.corners_bg_text);
        }

        public void setData(final SearchResultModel model) {
            if (model == null) {
                return;
            }
            tvResultTitle.setText("节气 " + model.id + "");
        }
    }

}
