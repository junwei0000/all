package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.home.domainname.activity.CommuDetailActivity;
import com.longcheng.lifecareplan.modular.home.domainname.activity.DomainMenuActivity;
import com.longcheng.lifecareplan.modular.home.domainname.bean.HotListDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.ZYBSelectMonRecrodListDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.zudui.ZYBZuDuiActivity;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.date.DatesUtils;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class SelectMonRecordListAdapter extends BaseAdapterHelper<ZYBSelectMonRecrodListDataBean.RiceActiviesItemBean> {
    ViewHolder mHolder = null;
    Activity context;


    public SelectMonRecordListAdapter(Activity context, List<ZYBSelectMonRecrodListDataBean.RiceActiviesItemBean> list) {
        super(context, list);
        this.context = context;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<ZYBSelectMonRecrodListDataBean.RiceActiviesItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pion_zybselectmonrecord_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        ZYBSelectMonRecrodListDataBean.RiceActiviesItemBean mOrderItemBean = list.get(position);
        String money = mOrderItemBean.getMoney();
        int role = mOrderItemBean.getRole();
        int create_time = mOrderItemBean.getCreate_time();
        String user_name = mOrderItemBean.getUser_name();
        int process_status = mOrderItemBean.getProcess_status();

        mHolder.tv_money.setText("组队请购祝佑宝 " + money);
        mHolder.tv_name.setText("队长：" + CommonUtil.setName(user_name));
        mHolder.tv_time.setText(DatesUtils.getInstance().getTimeStampToDate(create_time, "yyyy-MM-dd HH:mm:ss"));
        if (role == 1) {
            mHolder.iv_type.setBackgroundResource(R.mipmap.zyb_selectmonrecord_chuang);
        } else {
            mHolder.iv_type.setBackgroundResource(R.mipmap.zyb_selectmonrecord_can);
        }
        if (process_status <= 2) {
            mHolder.tv_status.setVisibility(View.VISIBLE);
        } else {
            mHolder.tv_status.setVisibility(View.GONE);
        }

        mHolder.item_layout.setTag(mOrderItemBean);
        mHolder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZYBSelectMonRecrodListDataBean.RiceActiviesItemBean mOrderItemBean = (ZYBSelectMonRecrodListDataBean.RiceActiviesItemBean) v.getTag();
                Intent intent = new Intent(context, ZYBZuDuiActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("zhuyoubao_team_room_id", mOrderItemBean.getZhuyoubao_team_room_id());
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    private class ViewHolder {
        private ImageView iv_type;
        private TextView tv_money;
        private TextView tv_status;
        private TextView tv_name;
        private TextView tv_time;
        private LinearLayout item_layout;

        public ViewHolder(View view) {
            iv_type = view.findViewById(R.id.iv_type);
            tv_money = view.findViewById(R.id.tv_money);
            tv_status = view.findViewById(R.id.tv_status);
            tv_name = view.findViewById(R.id.tv_name);
            tv_time = view.findViewById(R.id.tv_time);
            item_layout = view.findViewById(R.id.item_layout);
        }
    }
}
