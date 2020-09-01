package com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.LoveItemBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.MessageListItemBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle.ToChatH5Actitvty;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MyMessageAdapter extends BaseAdapterHelper<MessageListItemBean> {
    ViewHolder mHolder = null;
    Activity context;

    public MyMessageAdapter(Activity context, List<MessageListItemBean> list) {
        super(context, list);
        this.context = context;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<MessageListItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fuqrep_mymessage_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        MessageListItemBean mItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mItemBean.getAvatar_url(), mHolder.item_iv_img);
        mHolder.item_tv_name.setText(CommonUtil.setName(mItemBean.getName(), 5));
        String date = DatesUtils.getInstance().TodayOrYesterday(mItemBean.getLast_at(), "yyyy-MM-dd HH:mm:ss");
        mHolder.item_tv_time.setText(date);
        int unread_count = mItemBean.getUnread_count();
        mHolder.top_tv_num.setText("" + unread_count);
        if (unread_count == 0) {
            mHolder.top_tv_num.setVisibility(View.GONE);
        } else {
            mHolder.top_tv_num.setVisibility(View.VISIBLE);
        }
        int chat_type = mItemBean.getChat_type();
        if (chat_type == 1) {
            mHolder.item_tv_cont.setText(mItemBean.getLast_message().getText());
        } else {
            mHolder.item_tv_cont.setText("[图片]");
        }
        mHolder.item_relat_message.setTag(mItemBean);
        mHolder.item_relat_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageListItemBean mItemBean = (MessageListItemBean) v.getTag();
                int type = mItemBean.getType();
                Intent intent = new Intent(context, ToChatH5Actitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", Config.BASE_HEAD_URL + "/home/im?device=android&type=" + type + "&to=" + mItemBean.getTo());
                intent.putExtra("title", mItemBean.getName());
                intent.putExtra("group_id", mItemBean.getTo());
                intent.putExtra("type", type);
                context.startActivityForResult(intent, ConstantManager.REFRESHDATA);
            }
        });
        return convertView;
    }


    private class ViewHolder {
        private TextView top_tv_num;
        private TextView item_tv_name;
        private TextView item_tv_time;
        private TextView item_tv_cont;
        private ImageView item_iv_img;
        private RelativeLayout item_relat_message;


        public ViewHolder(View view) {
            top_tv_num = view.findViewById(R.id.top_tv_num);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_time = view.findViewById(R.id.item_tv_time);
            item_tv_cont = view.findViewById(R.id.item_tv_cont);
            item_iv_img = view.findViewById(R.id.item_iv_img);
            item_relat_message = view.findViewById(R.id.item_relat_message);
        }
    }
}
