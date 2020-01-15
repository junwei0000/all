package com.longcheng.lifecareplan.modular.home.liveplay.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.MineActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoItemInfo;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 */

public class MyFouseListAdapter extends BaseAdapterHelper<MVideoItemInfo> {
    ViewHolder mHolder = null;
    Context context;
    Handler mHandler;
    int mHandlerID;
    String video_user_id;

    public MyFouseListAdapter(Context context, List<MVideoItemInfo> list, Handler mHandler, int mHandlerID, String video_user_id) {
        super(context, list);
        this.context = context;
        this.mHandler = mHandler;
        this.mHandlerID = mHandlerID;
        this.video_user_id = video_user_id;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<MVideoItemInfo> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.live_mylive_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        MVideoItemInfo mHelpItemBean = list.get(position);
        mHelpItemBean.setPosition(position);
        mHolder.item_tv_name.setText(mHelpItemBean.getUser_name());
        String showtitle = mHelpItemBean.getShow_title();
        if (TextUtils.isEmpty(showtitle)) {
            showtitle = "什么也没留下";
        }
        mHolder.item_tv_num.setText("" + showtitle);
        String url = mHelpItemBean.getAvatar();
        GlideDownLoadImage.getInstance().loadCircleImage(context, url, mHolder.item_iv_thumb);

        if (!TextUtils.isEmpty(video_user_id)) {
            mHolder.item_tv_follow.setVisibility(View.VISIBLE);
            int is_follow = mHelpItemBean.getIs_follow();
            if (is_follow == 0) {
                mHolder.item_tv_follow.setText("关注");
                ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_tv_follow, R.color.red);
            } else {
                mHolder.item_tv_follow.setText("已关注");
                ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_tv_follow, R.color.text_noclick_color);
            }
        } else {
            mHolder.item_tv_follow.setVisibility(View.GONE);
        }

        mHolder.item_tv_follow.setTag(mHelpItemBean);
        mHolder.item_tv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MVideoItemInfo mHelpItemBean_ = (MVideoItemInfo) v.getTag();
                Message message = new Message();
                message.what = mHandlerID;
                message.arg1 = mHelpItemBean_.getPosition();
                message.arg2 = mHelpItemBean_.getIs_follow();
                message.obj = mHelpItemBean_.getFollow_user_id();
                mHandler.sendMessage(message);
            }
        });
        mHolder.item_layout_thumb.setTag(mHelpItemBean);
        mHolder.item_layout_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MVideoItemInfo mHelpItemBean_ = (MVideoItemInfo) v.getTag();
                String video_user_id = mHelpItemBean_.getFollow_user_id();
                Intent intent = new Intent(context, MineActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("video_user_id", video_user_id);
                context.startActivity(intent);

            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_num;
        private TextView item_tv_follow;
        private LinearLayout item_layout_thumb;


        public ViewHolder(View view) {
            item_iv_thumb = (ImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            item_tv_num = (TextView) view.findViewById(R.id.item_tv_num);
            item_tv_follow = (TextView) view.findViewById(R.id.item_tv_follow);
            item_layout_thumb = (LinearLayout) view.findViewById(R.id.item_layout_thumb);
        }
    }
}
