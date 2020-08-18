package com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.tutorExit.adapter;

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
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.tutorExit.activity.PionTutorExitBaseOrderFrag;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.tutorExit.bean.PionTutorExitItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class PionTutorExitListAdapter extends BaseAdapterHelper<PionTutorExitItemBean> {
    ViewHolder mHolder = null;
    Handler mHandler;
    Activity context;
    List<PionTutorExitItemBean> list;

    public PionTutorExitListAdapter(Activity context, List<PionTutorExitItemBean> list, Handler mHandler) {
        super(context, list);
        this.context = context;
        this.list = list;
        this.mHandler = mHandler;
    }

    public List<PionTutorExitItemBean> getList() {
        return list;
    }

    public void setList(List<PionTutorExitItemBean> list) {
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<PionTutorExitItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pionner_opengmrecord_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        PionTutorExitItemBean mOrderItemBean = list.get(position);
        mOrderItemBean.setPosition(position);

        int status = mOrderItemBean.getStatus();
        if (status == 0) {//已打款 0未处理   -1已驳回
            mHolder.item_layout_btn.setVisibility(View.VISIBLE);
            mHolder.item_tv_status.setVisibility(View.GONE);
        } else if (status == -1) {
            mHolder.item_tv_status.setText("已驳回");
            mHolder.item_tv_status.setVisibility(View.VISIBLE);
            mHolder.item_layout_btn.setVisibility(View.GONE);
        } else {
            mHolder.item_tv_status.setText("已完成");
            mHolder.item_tv_status.setVisibility(View.VISIBLE);
            mHolder.item_layout_btn.setVisibility(View.GONE);
        }
        GlideDownLoadImage.getInstance().loadCircleImage(mOrderItemBean.getUser_avatar(), mHolder.item_iv_img);
        String username = mOrderItemBean.getUser_name();
        if (!TextUtils.isEmpty(username) && username.length() > 6) {
            username = username.substring(0, 3) + "…";
        }
        mHolder.item_tv_name.setText(username);
        mHolder.item_tv_jieqi.setText(mOrderItemBean.getUser_branch_info());
        mHolder.item_tv_time.setText("申请时间：" + mOrderItemBean.getCreate_time());
        mHolder.item_tv_num.setText(mOrderItemBean.getMoney() + "元");

        mHolder.item_tv_bankusername.setText("姓\u3000\u3000名：" + mOrderItemBean.getBank_user_name());
        mHolder.item_tv_bankname.setText("开\u0020\u0020户\u0020\u0020行：" + mOrderItemBean.getBank_name());
        mHolder.bank_full_name.setText("开户支行：" + mOrderItemBean.getBank_branch());
        mHolder.item_tv_banknum.setText("卡\u3000\u3000号：" + CommonUtil.setBankNo(mOrderItemBean.getBank_account()));
        ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_tv_sure, R.color.red);
        mHolder.item_tv_sure.setTag(mOrderItemBean.getEntrepreneurs_settlement_order_id());
        mHolder.item_tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entrepreneurs_settlement_order_id = (String) v.getTag();
                Message message = Message.obtain();
                message.what = PionTutorExitBaseOrderFrag.SUREPAY;
                message.obj = entrepreneurs_settlement_order_id;
                mHandler.sendMessage(message);
            }
        });
        mHolder.item_tv_copy.setTag(mOrderItemBean);
        mHolder.item_tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PionTutorExitItemBean mOrderItemBean = (PionTutorExitItemBean) v.getTag();
                String cont = "打款银行信息\n"
                        + "姓名：" + mOrderItemBean.getBank_user_name() + "\n"
                        + "开户行：" + mOrderItemBean.getBank_name() + "\n"
                        + "开户支行：" + mOrderItemBean.getBank_branch() + "\n"
                        + "现金：" + mOrderItemBean.getMoney() + "\n"
                        + "卡号：" + mOrderItemBean.getBank_account();
                PriceUtils.getInstance().copy(context, cont);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name;
        private TextView item_tv_jieqi;
        private TextView item_tv_time;
        private TextView item_tv_num;
        private LinearLayout item_layout_btn;
        private TextView item_tv_bankusername;
        private TextView item_tv_bankname;
        private TextView item_tv_banknum;
        private TextView bank_full_name;
        private TextView item_tv_sure;
        private TextView item_tv_copy;
        private TextView item_tv_status;

        public ViewHolder(View view) {
            item_iv_img = view.findViewById(R.id.item_iv_img);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_jieqi = view.findViewById(R.id.item_tv_jieqi);
            item_tv_time = view.findViewById(R.id.item_tv_time);
            item_tv_num = view.findViewById(R.id.item_tv_num);
            item_layout_btn = view.findViewById(R.id.item_layout_btn);
            item_tv_bankusername = view.findViewById(R.id.item_tv_bankusername);
            item_tv_bankname = view.findViewById(R.id.item_tv_bankname);
            item_tv_banknum = view.findViewById(R.id.item_tv_banknum);
            item_tv_sure = view.findViewById(R.id.item_tv_sure);
            bank_full_name = view.findViewById(R.id.bank_full_name);
            item_tv_copy = view.findViewById(R.id.item_tv_copy);
            item_tv_status = view.findViewById(R.id.item_tv_status);
        }
    }
}
