package com.longcheng.lifecareplan.modular.home.liveplay.mine.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
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

    public MyFouseListAdapter(Context context, List<MVideoItemInfo> list, Handler mHandler, int mHandlerID) {
        super(context, list);
        this.context = context;
        this.mHandler = mHandler;
        this.mHandlerID = mHandlerID;
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
            showtitle = "主播很懒，什么也没留下";
        }
        mHolder.item_tv_num.setText("" + showtitle);
        String url = mHelpItemBean.getAvatar();
        GlideDownLoadImage.getInstance().loadCircleImage(context, url, mHolder.item_iv_thumb);
        mHolder.item_tv_follow.setTag(mHelpItemBean);
        mHolder.item_tv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MVideoItemInfo mHelpItemBean_ = (MVideoItemInfo) v.getTag();
                Message message = new Message();
                message.what = mHandlerID;
                message.arg1 = mHelpItemBean_.getPosition();
                message.obj = mHelpItemBean_.getFollow_user_id();
                mHandler.sendMessage(message);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_num;
        private TextView item_tv_follow;

        public ViewHolder(View view) {
            item_iv_thumb = (ImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            item_tv_num = (TextView) view.findViewById(R.id.item_tv_num);
            item_tv_follow = (TextView) view.findViewById(R.id.item_tv_follow);
        }
    }
}
