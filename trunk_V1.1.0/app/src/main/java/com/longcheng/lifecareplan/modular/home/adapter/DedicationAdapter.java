package com.longcheng.lifecareplan.modular.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.home.bangdan.BangDanActitvty;
import com.longcheng.lifecareplan.modular.home.bean.HomeItemBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/24 13:27
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class DedicationAdapter extends PagerAdapter {

    private Context mContext;
    private List<HomeItemBean> list;
    private LayoutInflater mInflater;

    public DedicationAdapter(Context mContext, List<HomeItemBean> list) {
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
            View view = mInflater.inflate(R.layout.banner_item_adapter, container, false);
            TextView item_tv_title = view.findViewById(R.id.item_tv_title);
            TextView item_tv_time = view.findViewById(R.id.item_tv_time);
            ImageView item_iv_pic = view.findViewById(R.id.item_iv_pic);
            RelativeLayout layout_right = view.findViewById(R.id.layout_right);
            ImageView item_iv_head1 = view.findViewById(R.id.item_iv_head1);
            ImageView item_iv_head2 = view.findViewById(R.id.item_iv_head2);
            ImageView item_iv_head3 = view.findViewById(R.id.item_iv_head3);

            HomeItemBean mHomeItemBean = list.get(position);
            item_tv_title.setText(mHomeItemBean.getTitle());
            List<HomeItemBean> extend_info = mHomeItemBean.getExtend_info();
            if (extend_info != null && extend_info.size() > 0) {
                Log.i("BannerListSuccess", "position: " + position);
                if (extend_info.size() >= 1) {
                    Log.i("BannerListSuccess", "BannerListSuccess: " + extend_info.get(0).getAvatar());
                    GlideDownLoadImage.getInstance().loadCircleHeadImage(mContext, extend_info.get(0).getAvatar(), item_iv_head1);
                }
                if (extend_info.size() >= 2) {
                    Log.i("BannerListSuccess", "BannerListSuccess: " + extend_info.get(1).getAvatar());
                    GlideDownLoadImage.getInstance().loadCircleHeadImage(mContext, extend_info.get(1).getAvatar(), item_iv_head2);
                }
                if (extend_info.size() >= 3) {
                    Log.i("BannerListSuccess", "BannerListSuccess: " + extend_info.get(2).getAvatar());
                    GlideDownLoadImage.getInstance().loadCircleHeadImage(mContext, extend_info.get(2).getAvatar(), item_iv_head3);
                }
            }
            view.setTag(mHomeItemBean);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeItemBean mHomeItemBean = (HomeItemBean) v.getTag();
                    String url = mHomeItemBean.getUrl();
                    int type = mHomeItemBean.getType();
                    if (!TextUtils.isEmpty(url)) {
                        url = url + "/is_app_Android/1";
                        Log.e("onItemClick", "url=" + url);
                        SharedPreferencesHelper.put(mContext, "starturl", url);
                        SharedPreferencesHelper.put(mContext, "title", mHomeItemBean.getTitle());
                        if (type == 1 || UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToBangDan)) {
                            String title = "";
                            if (type == 1) {
                                title = mHomeItemBean.getSubtitle();
                            } else {
                                title = mHomeItemBean.getTitle();
                            }
                            Intent intent = new Intent(mContext, BangDanActitvty.class);
                            intent.putExtra("starturl", url);
                            intent.putExtra("title", title);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            mContext.startActivity(intent);
                        }
                    }
                }
            });
            int type = mHomeItemBean.getType();
            if (type == 1) {
                layout_right.setVisibility(View.VISIBLE);
                item_tv_time.setText(mHomeItemBean.getStart_time() + "至" + mHomeItemBean.getEnd_time());
            } else {
                layout_right.setVisibility(View.INVISIBLE);
                item_tv_time.setVisibility(View.INVISIBLE);
            }
            Log.i("BannerListSuccess", "BannerListSuccess: " + mHomeItemBean.getPic());
            GlideDownLoadImage.getInstance().loadCircleImageRoleREf(mContext, mHomeItemBean.getPic(), item_iv_pic, 0);
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
