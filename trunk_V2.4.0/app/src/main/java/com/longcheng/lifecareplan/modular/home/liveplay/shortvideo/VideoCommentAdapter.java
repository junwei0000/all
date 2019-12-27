package com.longcheng.lifecareplan.modular.home.liveplay.shortvideo;

import android.content.Context;
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
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoItemInfo;
import com.longcheng.lifecareplan.utils.DatesUtils;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class VideoCommentAdapter extends BaseAdapterHelper<MVideoItemInfo> {
    ViewHolder mHolder = null;
    Context context;
    Handler mHandler;
    String user_id;

    public VideoCommentAdapter(Context context, List<MVideoItemInfo> list, Handler mHandler) {
        super(context, list);
        this.context = context;
        this.mHandler = mHandler;
        user_id = UserUtils.getUserId(context);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<MVideoItemInfo> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.live_video_comment_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        MVideoItemInfo mItemBean = list.get(position);
        mItemBean.setPosition(position);
        String name = mItemBean.getUser_name();
        mHolder.item_tv_name.setText(name);
        String time = DatesUtils.getInstance().getTimeStampToDate(mItemBean.getCreate_time(), "yyyy-MM-dd");
        mHolder.item_tv_time.setText(time);
        mHolder.item_tv_deseribe.setText(mItemBean.getContent());
        mHolder.tv_dianzannum.setText(mItemBean.getFollow_number());
        int is_follow = mItemBean.getIs_follow();
        if (is_follow == 0) {
            mHolder.iv_dianzan.setBackgroundResource(R.mipmap.follow_gray);
        } else {
            mHolder.iv_dianzan.setBackgroundResource(R.mipmap.zhibo_zan_hong1);
        }
        String avatar = mItemBean.getAvatar();
        if (!TextUtils.isEmpty(avatar)) {
            GlideDownLoadImage.getInstance().loadCircleHeadImage(context, avatar, mHolder.item_iv_thumb);
        } else {
            GlideDownLoadImage.getInstance().loadCircleHeadImage(context, R.mipmap.user_default_icon, mHolder.item_iv_thumb);
        }
        mHolder.item_iv_follow.setTag(mItemBean);
        mHolder.item_iv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MVideoItemInfo mItemBean = (MVideoItemInfo) v.getTag();
                String Follow_number = mItemBean.getFollow_number();
                int is_follow = mItemBean.getIs_follow();
                String newnum;
                if (is_follow == 0) {
                    mItemBean.setIs_follow(1);
                    newnum = PriceUtils.getInstance().gteAddSumPrice(Follow_number, "1");
                } else {
                    mItemBean.setIs_follow(0);
                    newnum = PriceUtils.getInstance().gteSubtractSumPrice("1", Follow_number);
                }
                if (!TextUtils.isEmpty(newnum) && Integer.parseInt(newnum) < 0) {
                    newnum = "0";
                }
                mItemBean.setFollow_number(newnum);
                notifyDataSetChanged();

                Message message = new Message();
                message.what = TCVideoDetailActivity.followItem;
                message.arg1 = is_follow;
                message.obj = mItemBean.getShort_video_comment_id();
                mHandler.sendMessage(message);
                message = null;
            }
        });
        return convertView;
    }


    private class ViewHolder {
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_time;
        private TextView item_tv_deseribe;
        private LinearLayout item_iv_follow;
        private ImageView iv_dianzan;
        private TextView tv_dianzannum;

        public ViewHolder(View view) {
            item_iv_thumb = (ImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            item_tv_time = (TextView) view.findViewById(R.id.item_tv_time);
            item_tv_deseribe = (TextView) view.findViewById(R.id.item_tv_deseribe);
            tv_dianzannum = (TextView) view.findViewById(R.id.tv_dianzannum);
            item_iv_follow = (LinearLayout) view.findViewById(R.id.item_iv_follow);
            iv_dianzan = (ImageView) view.findViewById(R.id.iv_dianzan);
        }
    }
}
