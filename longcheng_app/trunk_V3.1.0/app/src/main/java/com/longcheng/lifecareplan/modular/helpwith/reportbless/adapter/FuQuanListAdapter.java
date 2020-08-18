package com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.longcheng.lifecareplan.modular.exchange.malldetail.activity.MallDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.SkipHelpUtils;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.activity.AutoHelpH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.activity.LifeStyleApplyHelpActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportItemBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressAddActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderItemBean;
import com.longcheng.lifecareplan.modular.mine.myorder.detail.activity.XiaJiaActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.detail.activity.YaJinActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.ordertracking.activity.TrankActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.activity.ThanksActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
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

public class FuQuanListAdapter extends BaseAdapterHelper<ReportItemBean> {
    ViewHolder mHolder = null;
    Activity context;
    List<ReportItemBean> list;

    public FuQuanListAdapter(Activity context, List<ReportItemBean> list) {
        super(context, list);
        this.context = context;
        this.list = list;
    }

    public List<ReportItemBean> getList() {
        return list;
    }

    public void setList(List<ReportItemBean> list) {
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<ReportItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.report_fuquan_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        ReportItemBean mOrderItemBean = list.get(position);
        mHolder.item_tv_date.setText(mOrderItemBean.getShow_name());
        mHolder.item_tv_num.setText(mOrderItemBean.getShow_number() + "张");
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_date;
        private TextView item_tv_num;

        public ViewHolder(View view) {
            item_tv_date = view.findViewById(R.id.item_tv_date);
            item_tv_num = view.findViewById(R.id.item_tv_num);
        }
    }
}
