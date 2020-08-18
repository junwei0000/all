package com.longcheng.volunteer.modular.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.modular.home.bean.HomeItemBean;
import com.longcheng.volunteer.modular.home.healthydelivery.detail.activity.HealthyDeliveryDetailAct;
import com.longcheng.volunteer.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.volunteer.utils.ConstantManager;
import com.longcheng.volunteer.utils.glide.GlideDownLoadImage;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesHelper;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/24 13:27
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class HealthAdapter extends PagerAdapter {

    private Context mContext;
    private List<HomeItemBean> list;
    private LayoutInflater mInflater;

    public HealthAdapter(Context mContext, List<HomeItemBean> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(List<HomeItemBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (list.size() != 0) {
            position %= list.size();
            if (position < 0) {
                position = list.size() + position;
            }
            View view = mInflater.inflate(R.layout.item_healthy_deliverry_list2, container, false);

            TextView tvTitle = view.findViewById(R.id.tvtitle);
            TextView tvContent = view.findViewById(R.id.tvNewContent);
            TextView tvTypeDes = view.findViewById(R.id.tvtypedes);
            TextView tvTime = view.findViewById(R.id.tvtime);
            ImageView avatar = view.findViewById(R.id.ivavatar);
            tvTypeDes.setBackgroundResource(R.drawable.corners_bg_redbian);
            tvTypeDes.setTextColor(mContext.getResources().getColor(R.color.red));
            HomeItemBean item = list.get(position);

            tvTitle.setText(item.getNew_name());
            tvContent.setText(item.getDes());
            tvTypeDes.setText(item.getType_name());
            tvTime.setText(item.getCreate_time());
            GlideDownLoadImage.getInstance().loadCircleImageRoleREf(mContext, item.getPic(), avatar);


            view.setTag(item);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeItemBean mHomeItemBean = (HomeItemBean) v.getTag();
                    if (UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToHealthyDeli)) {
                        if (!TextUtils.isEmpty(mHomeItemBean.getInfo_url())) {
                            Intent intent = new Intent(mContext, HealthyDeliveryDetailAct.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("detail_url", mHomeItemBean.getInfo_url());
                            intent.putExtra("cont", mHomeItemBean.getDes());
                            intent.putExtra("title", mHomeItemBean.getNew_name());
                            intent.putExtra("img", mHomeItemBean.getPic());
                            mContext.startActivity(intent);
                        }
                    } else {
                        SharedPreferencesHelper.put(mContext, "health_detail_url", mHomeItemBean.getInfo_url());
                        SharedPreferencesHelper.put(mContext, "health_cont", mHomeItemBean.getDes());
                        SharedPreferencesHelper.put(mContext, "health_title", mHomeItemBean.getNew_name());
                        SharedPreferencesHelper.put(mContext, "health_newimg", mHomeItemBean.getPic());
                    }
                }
            });
            container.addView(view);
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
