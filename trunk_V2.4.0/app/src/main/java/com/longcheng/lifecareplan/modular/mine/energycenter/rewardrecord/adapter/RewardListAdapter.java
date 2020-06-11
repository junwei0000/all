package com.longcheng.lifecareplan.modular.mine.energycenter.rewardrecord.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.exchange.malldetail.activity.MallDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.SkipHelpUtils;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.activity.AutoHelpH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.activity.LifeStyleApplyHelpActivity;
import com.longcheng.lifecareplan.modular.mine.energycenter.rewardrecord.bean.RewardItemBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressAddActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.detail.activity.XiaJiaActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.detail.activity.YaJinActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.ordertracking.activity.TrankActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.activity.ThanksActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class RewardListAdapter extends BaseAdapterHelper<RewardItemBean> {
    ViewHolder mHolder = null;
    Handler mHandler;
    Activity context;
    List<RewardItemBean> list;

    public RewardListAdapter(Activity context, List<RewardItemBean> list, Handler mHandler) {
        super(context, list);
        this.context = context;
        this.list = list;
        this.mHandler = mHandler;
    }

    public List<RewardItemBean> getList() {
        return list;
    }

    public void setList(List<RewardItemBean> list) {
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<RewardItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_rewardrecord_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        RewardItemBean mOrderItemBean = list.get(position);
        mOrderItemBean.setPosition(position);

        int status = mOrderItemBean.getStatus();
        if (status == 1) {//已打款
            mHolder.item_layout_btn.setVisibility(View.GONE);
        } else {
            mHolder.item_layout_btn.setVisibility(View.VISIBLE);
        }
        GlideDownLoadImage.getInstance().loadCircleImage(mOrderItemBean.getAvatar(), mHolder.item_iv_img);
        String username = mOrderItemBean.getUser_name();
        if (!TextUtils.isEmpty(username) && username.length() > 6) {
            username = username.substring(0, 3) + "…";
        }
        mHolder.item_tv_name.setText(username);
        mHolder.item_tv_jieqi.setText(mOrderItemBean.getJieiqi_name());
        mHolder.item_tv_time.setText("申请时间：" + mOrderItemBean.getCreate_date());
        mHolder.item_tv_num.setText("申请奖励数量：" + mOrderItemBean.getUser_zhufubao_number() + "祝福宝");
        mHolder.item_tv_price.setText(mOrderItemBean.getTotal_price());

        mHolder.item_tv_bankusername.setText("姓\u3000\u3000名：" + mOrderItemBean.getCardholder_name());
        mHolder.item_tv_bankname.setText("开\u0020\u0020户\u0020\u0020行：" + mOrderItemBean.getBank_name());
        mHolder.item_tv_banknum.setText("卡\u3000\u3000号：" + CommonUtil.setBankNo(mOrderItemBean.getBank_no()));
        mHolder.bank_full_name.setText("开户支行：" + mOrderItemBean.getBank_full_name());
        ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_tv_sure, R.color.red);
        mHolder.item_tv_sure.setTag(mOrderItemBean.getUser_zhufubao_reward_id());
        mHolder.item_tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_zhufubao_reward_id = (String) v.getTag();
                Message message = Message.obtain();
                message.what = 1;
                message.obj = user_zhufubao_reward_id;
                mHandler.sendMessage(message);
            }
        });
        mHolder.item_tv_copy.setTag(mOrderItemBean);
        mHolder.item_tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RewardItemBean mOrderItemBean = (RewardItemBean) v.getTag();
                String cont = "打款银行信息\n"
                        + "姓名：" + mOrderItemBean.getCardholder_name() + "\n"
                        + "开户行：" + mOrderItemBean.getBank_name() + "\n"
                        + "开户支行：" + mOrderItemBean.getBank_full_name() + "\n"
                        + "金额：" + mOrderItemBean.getTotal_price() + "\n"
                        + "卡号：" + mOrderItemBean.getBank_no();
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
        private TextView item_tv_price;
        private LinearLayout item_layout_btn;
        private TextView item_tv_bankusername;
        private TextView item_tv_bankname;
        private TextView item_tv_banknum;
        private TextView bank_full_name;
        private TextView item_tv_sure;

        private TextView item_tv_copy;

        public ViewHolder(View view) {
            item_iv_img = view.findViewById(R.id.item_iv_img);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_jieqi = view.findViewById(R.id.item_tv_jieqi);
            item_tv_time = view.findViewById(R.id.item_tv_time);
            item_tv_num = view.findViewById(R.id.item_tv_num);
            item_tv_price = view.findViewById(R.id.item_tv_price);
            item_layout_btn = view.findViewById(R.id.item_layout_btn);
            item_tv_bankusername = view.findViewById(R.id.item_tv_bankusername);
            item_tv_bankname = view.findViewById(R.id.item_tv_bankname);
            item_tv_banknum = view.findViewById(R.id.item_tv_banknum);
            item_tv_sure = view.findViewById(R.id.item_tv_sure);
            bank_full_name = view.findViewById(R.id.bank_full_name);

            item_tv_copy = view.findViewById(R.id.item_tv_copy);
        }
    }
}
