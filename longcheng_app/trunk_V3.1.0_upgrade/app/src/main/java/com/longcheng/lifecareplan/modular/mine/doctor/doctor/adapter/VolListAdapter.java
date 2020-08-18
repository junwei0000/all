package com.longcheng.lifecareplan.modular.mine.doctor.doctor.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.DocRewardListDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.VolSearchDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class VolListAdapter extends BaseAdapterHelper<DocRewardListDataBean.DocItemBean> {
    ViewHolder mHolder = null;
    Context context;
    Handler mHandler;

    public VolListAdapter(Context context, List<DocRewardListDataBean.DocItemBean> list, Handler mHandler) {
        super(context, list);
        this.context = context;
        this.mHandler = mHandler;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<DocRewardListDataBean.DocItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.doctor_vol_list_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        DocRewardListDataBean.DocItemBean mDaiFuItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImage(mDaiFuItemBean.getVolunteer_user_avatar(), mHolder.item_iv_img);
        String name = CommonUtil.setName(mDaiFuItemBean.getVolunteer_user_name());
        mHolder.item_tv_name.setText(name);
        mHolder.item_tv_jieqi.setVisibility(View.GONE);
        mHolder.item_tv_phone.setText(mDaiFuItemBean.getVolunteer_user_phone());
        mHolder.item_tv_del.setTag(mDaiFuItemBean.getDoctor_relation_volunteer_id());
        mHolder.item_tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctor_relation_volunteer_id = (String) v.getTag();
                Message message = Message.obtain();
                message.what = 1;
                message.obj = doctor_relation_volunteer_id;
                mHandler.sendMessage(message);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name;
        private TextView item_tv_jieqi;
        private TextView item_tv_phone;
        private TextView item_tv_del;

        public ViewHolder(View convertView) {
            item_iv_img = convertView.findViewById(R.id.item_iv_img);
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_jieqi = convertView.findViewById(R.id.item_tv_jieqi);
            item_tv_phone = convertView.findViewById(R.id.item_tv_phone);
            item_tv_del = convertView.findViewById(R.id.item_tv_del);
        }
    }
}
