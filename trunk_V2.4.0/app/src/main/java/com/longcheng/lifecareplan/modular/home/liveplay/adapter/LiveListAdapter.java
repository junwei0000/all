package com.longcheng.lifecareplan.modular.home.liveplay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseRecyclerAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.ArrayList;
import java.util.List;

/**
 * 小视频列表的Adapter
 * 列表项布局格式: R.layout.listview_ugc_item
 * 列表项数据格式: TCLiveInfo
 */

public class LiveListAdapter extends BaseRecyclerAdapter<LiveListAdapter.VideoVideoHolder> {
    private List<VideoItemInfo> mList;
    private Context mContext;

    //重新加载List的数据
    public void reloadListView(List<VideoItemInfo> data, boolean isClear) {
        if (isClear) {
            mList = new ArrayList<>();
        }
        if (data != null && data.size() > 0) {
            mList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public LiveListAdapter(Context context, List<VideoItemInfo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public void onBindVH(VideoVideoHolder holder, int position) {
        VideoItemInfo mHelpItemBean = mList.get(position);
        holder.item_tv_name.setText(mHelpItemBean.getUser_name());
        holder.item_tv_playtitle.setText("" + mHelpItemBean.getTitle());
        holder.item_tv_num.setText("" + mHelpItemBean.getTotal_number());
        holder.item_tv_city.setText("" + mHelpItemBean.getAddress());
        int width = (DensityUtil.screenWith(mContext) - DensityUtil.dip2px(mContext, 23)) / 2;
        int height;
        int moid;
        height = (int) (width * 1.54);
        moid = R.mipmap.live_listnotdatebg2;
        String url = mHelpItemBean.getCover_url();
        GlideDownLoadImage.getInstance().loadCircleImageLive(url, moid, holder.item_iv_thumb, 0);
        holder.relat_thumb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
    }


    @Override
    public VideoVideoHolder onCreateVH(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_play_list_item, null);
        return new VideoVideoHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class VideoVideoHolder extends RecyclerView.ViewHolder {
        private RelativeLayout relat_thumb;
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_num;
        private TextView item_tv_playtitle;
        private TextView item_tv_city;

        public VideoVideoHolder(View view) {
            super(view);
            relat_thumb = view.findViewById(R.id.relat_thumb);
            item_iv_thumb = view.findViewById(R.id.item_iv_thumb);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_num = view.findViewById(R.id.item_tv_num);
            item_tv_playtitle = view.findViewById(R.id.item_tv_playtitle);
            item_tv_city = view.findViewById(R.id.item_tv_city);
        }
    }
}


