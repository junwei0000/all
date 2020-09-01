package com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.pionopen.adapter;

import android.app.Activity;
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
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.activity.LookPicUtils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.pionopen.activity.PionOpenListActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.pionopen.activity.PionOpenListBaseOrderFrag;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.pionopen.bean.PionOpenListItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class PionOpenListAdapter extends BaseAdapterHelper<PionOpenListItemBean> {
    ViewHolder mHolder = null;
    Handler mHandler;
    Activity context;
    List<PionOpenListItemBean> list;

    public PionOpenListAdapter(Activity context, List<PionOpenListItemBean> list, Handler mHandler) {
        super(context, list);
        this.context = context;
        this.list = list;
        this.mHandler = mHandler;
    }

    public List<PionOpenListItemBean> getList() {
        return list;
    }

    public void setList(List<PionOpenListItemBean> list) {
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<PionOpenListItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pionner_zybrecord_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        PionOpenListItemBean mOrderItemBean = list.get(position);
        mOrderItemBean.setPosition(position);
        if(PionOpenListActivity.type==PionOpenListActivity.type_open){
            mHolder.tv_numtitle.setText("开通金额");
        }else if(PionOpenListActivity.type==PionOpenListActivity.type_xufei){
            mHolder.tv_numtitle.setText("续费金额");
        }else if(PionOpenListActivity.type==PionOpenListActivity.type_upgrade){
            mHolder.tv_numtitle.setText("升级金额");
        }
        int status = mOrderItemBean.getStatus();
        if (status == 0) {//已打款 0未处理   -1已驳回
            mHolder.item_layout_btn.setVisibility(View.VISIBLE);
            mHolder.item_tv_status.setVisibility(View.GONE);
        } else {
            mHolder.item_tv_status.setText("已处理");
            mHolder.item_tv_status.setVisibility(View.VISIBLE);
            mHolder.item_layout_btn.setVisibility(View.GONE);
        }
        GlideDownLoadImage.getInstance().loadCircleImage(mOrderItemBean.getUser_avatar(), mHolder.item_iv_img);
        String username = mOrderItemBean.getUser_name();
        if (!TextUtils.isEmpty(username) && username.length() > 6) {
            username = username.substring(0, 3) + "…";
        }
        mHolder.item_tv_name.setText(username);
        mHolder.item_tv_phone.setText(mOrderItemBean.getUser_phone());
        mHolder.item_tv_jieqi.setText(mOrderItemBean.getUser_branch_info());
        mHolder.item_tv_time.setText(mOrderItemBean.getCreate_time());
        mHolder.item_tv_num.setText(mOrderItemBean.getPrice() + "元");

        ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_tv_sure, R.color.red);
        mHolder.item_tv_sure.setTag(mOrderItemBean.getEntrepreneurs_order_id());
        mHolder.item_tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entrepreneurs_order_id = (String) v.getTag();
                Message message = Message.obtain();
                message.what = PionOpenListBaseOrderFrag.SUREPAY;
                message.obj = entrepreneurs_order_id;
                mHandler.sendMessage(message);
            }
        });
        mHolder.item_tv_phone.setTag(mOrderItemBean.getUser_phone());
        mHolder.item_tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = (String) v.getTag();
                DensityUtil.getPhoneToKey(context, phone);
            }
        });
        mHolder.item_tv_copy.setTag(mOrderItemBean.getPayment_img());
        mHolder.item_tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLookPicUtils == null) {
                    mLookPicUtils = new LookPicUtils(context);
                }
                mLookPicUtils.showLookPicDialog((String) v.getTag());
            }
        });
        return convertView;
    }

    LookPicUtils mLookPicUtils;

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name;
        private TextView item_tv_jieqi;
        private TextView item_tv_phone;
        private TextView item_tv_time;
        private TextView item_tv_num;
        private TextView tv_numtitle;

        private LinearLayout item_layout_btn;
        private TextView item_tv_sure;
        private TextView item_tv_copy;
        private TextView item_tv_status;

        public ViewHolder(View view) {
            item_iv_img = view.findViewById(R.id.item_iv_img);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_jieqi = view.findViewById(R.id.item_tv_jieqi);
            item_tv_time = view.findViewById(R.id.item_tv_time);
            item_tv_num = view.findViewById(R.id.item_tv_num);
            tv_numtitle= view.findViewById(R.id.tv_numtitle);
            item_layout_btn = view.findViewById(R.id.item_layout_btn);
            item_tv_phone = view.findViewById(R.id.item_tv_phone);
            item_tv_sure = view.findViewById(R.id.item_tv_sure);
            item_tv_copy = view.findViewById(R.id.item_tv_copy);
            item_tv_status = view.findViewById(R.id.item_tv_status);
        }
    }
}
