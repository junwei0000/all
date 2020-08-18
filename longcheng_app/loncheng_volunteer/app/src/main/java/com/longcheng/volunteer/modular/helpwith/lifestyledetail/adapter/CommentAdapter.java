package com.longcheng.volunteer.modular.helpwith.lifestyledetail.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseAdapterHelper;
import com.longcheng.volunteer.modular.helpwith.lifestyledetail.bean.LifeStyleDetailItemBean;
import com.longcheng.volunteer.utils.ConstantManager;
import com.longcheng.volunteer.utils.glide.GlideDownLoadImage;
import com.longcheng.volunteer.utils.sharedpreferenceutils.UserUtils;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class CommentAdapter extends BaseAdapterHelper<LifeStyleDetailItemBean> {
    ViewHolder mHolder = null;
    Context context;
    Handler mHandler;

    String user_id;

    public CommentAdapter(Context context, List<LifeStyleDetailItemBean> list, Handler mHandler) {
        super(context, list);
        this.context = context;
        this.mHandler = mHandler;
        user_id = UserUtils.getUserId(context);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<LifeStyleDetailItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.helpwith_comment_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        LifeStyleDetailItemBean mItemBean = list.get(position);
        mItemBean.setPosition(position);
        String name = mItemBean.getUser_name();
        mHolder.item_tv_name.setText(name);
        String showT = "奉献了 <font color=\"#ff443b\">" + mItemBean.getPrice() + "</font> 寿康宝";
        mHolder.item_tv_num.setText(Html.fromHtml(showT));
        mHolder.item_tv_deseribe.setText(mItemBean.getContent());
        mHolder.item_tv_time.setText(mItemBean.getComment_date());
        String avatar = mItemBean.getAvatar();
        if (!TextUtils.isEmpty(avatar)) {
            GlideDownLoadImage.getInstance().loadCircleHeadImage(context, avatar, mHolder.item_iv_thumb);
        } else {
            GlideDownLoadImage.getInstance().loadCircleHeadImage(context, R.mipmap.user_default_icon, mHolder.item_iv_thumb);
        }
        mHolder.item_iv_pinglun.setTag(mItemBean);
        mHolder.item_iv_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LifeStyleDetailItemBean mItemBean = (LifeStyleDetailItemBean) v.getTag();
                Message message = new Message();
                message.what = ConstantManager.ADDRESS_HANDLE_SETMOREN;
                message.arg1 = mItemBean.getMutual_help_comment_id();
                message.arg2 = mItemBean.getPosition();
                mHandler.sendMessage(message);
                message = null;
            }
        });
        List<LifeStyleDetailItemBean> replay_comments = mItemBean.getReplay_comments();
        mHolder.item_layout_huifu.removeAllViews();
        if (replay_comments != null && replay_comments.size() > 0) {
            mHolder.item_layout_huifu.setVisibility(View.VISIBLE);
            mHolder.item_iv_arrow.setVisibility(View.VISIBLE);
            for (LifeStyleDetailItemBean mDetailItemBean : replay_comments) {
                View view = LayoutInflater.from(context).inflate(R.layout.text_item, null);
                TextView mT = view.findViewById(R.id.tv_cont);
                TextView tv_del = view.findViewById(R.id.tv_del);
                String uid = mDetailItemBean.getUser_id();
                if (uid.equals(user_id)) {
                    tv_del.setVisibility(View.VISIBLE);
                } else {
                    tv_del.setVisibility(View.GONE);
                }
                mT.setTextColor(context.getResources().getColor(R.color.text_contents_color));
                String showTs = "<font color=\"#b2b2b2\">" + mDetailItemBean.getUser_name() + "：</font>" + mDetailItemBean.getContent();
                mT.setText(Html.fromHtml(showTs));
                mHolder.item_layout_huifu.addView(view);
                int mutual_help_comment_id = mDetailItemBean.getMutual_help_comment_id();
                int[] sd = new int[]{position, mutual_help_comment_id};
                tv_del.setTag(sd);
                tv_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int[] sd = (int[]) v.getTag();
                        Message message = new Message();
                        message.what = ConstantManager.ADDRESS_HANDLE_DEL;
                        message.arg1 = sd[1];
                        message.arg2 = sd[0];
                        mHandler.sendMessage(message);
                        message = null;
                    }
                });
            }
        } else {
            mHolder.item_iv_arrow.setVisibility(View.GONE);
            mHolder.item_layout_huifu.setVisibility(View.GONE);
        }

        return convertView;
    }


    private class ViewHolder {
        private ImageView item_iv_thumb;
        private ImageView item_iv_arrow;
        private TextView item_tv_name;
        private TextView item_tv_num;
        private TextView item_tv_deseribe;
        private TextView item_tv_time;
        private LinearLayout item_iv_pinglun;
        private LinearLayout item_layout_huifu;

        public ViewHolder(View view) {
            item_iv_arrow = (ImageView) view.findViewById(R.id.item_iv_arrow);
            item_iv_thumb = (ImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            item_tv_num = (TextView) view.findViewById(R.id.item_tv_num);
            item_tv_deseribe = (TextView) view.findViewById(R.id.item_tv_deseribe);
            item_tv_time = (TextView) view.findViewById(R.id.item_tv_time);
            item_iv_pinglun = (LinearLayout) view.findViewById(R.id.item_iv_pinglun);
            item_layout_huifu = (LinearLayout) view.findViewById(R.id.item_layout_huifu);
        }
    }
}
