package com.longcheng.lifecareplan.modular.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.home.bangdan.BlessBangDanActivity;
import com.longcheng.lifecareplan.modular.home.bangdan.EngryBangDanActivity;
import com.longcheng.lifecareplan.modular.home.bean.HomeItemBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：热推互助
 */

public class DediGridAdapter extends BaseAdapter {
    private final ArrayList<HomeItemBean> mList;
    ViewHolder mHolder = null;
    Context mContext;
    int page;

    public DediGridAdapter(Context context, ArrayList<HomeItemBean> list, int page) {
        this.page = page;
        this.mContext = context;
        mList = new ArrayList<>();
        int i = page * 2;
        int iEnd = i + 2;
        System.out.println("i=" + i);
        System.out.println("iEnd=" + iEnd);
        while ((i < list.size()) && (i < iEnd)) {
            mList.add(list.get(i));
            i++;
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View view, ViewGroup arg2) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.banner_item_adapter, null);
            mHolder = new ViewHolder();
            mHolder.item_tv_title = view.findViewById(R.id.item_tv_title);
            mHolder.item_tv_time = view.findViewById(R.id.item_tv_time);
            mHolder.frame_pic = view.findViewById(R.id.frame_pic);
            mHolder.item_iv_pic = view.findViewById(R.id.item_iv_pic);
            mHolder.layout_right = view.findViewById(R.id.layout_right);
            mHolder.item_iv_head1 = view.findViewById(R.id.item_iv_head1);
            mHolder.item_iv_head2 = view.findViewById(R.id.item_iv_head2);
            mHolder.item_iv_head3 = view.findViewById(R.id.item_iv_head3);
            view.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) view.getTag();
        }
        int width = (DensityUtil.screenWith(mContext) - DensityUtil.dip2px(mContext, 32)) / 2;
        mHolder.item_iv_pic.setLayoutParams(new FrameLayout.LayoutParams(width, width));
        mHolder.item_iv_pic.setScaleType(ImageView.ScaleType.FIT_XY);
        HomeItemBean mHomeItemBean = mList.get(position);
        mHolder.item_tv_title.setText(mHomeItemBean.getTitle());
        List<HomeItemBean> extend_info = mHomeItemBean.getExtend_info();
        if (extend_info != null && extend_info.size() > 0) {
            Log.i("BannerListSuccess", "position: " + position);
            if (extend_info.size() >= 1) {
                Log.i("BannerListSuccess", "BannerListSuccess: " + extend_info.get(0).getAvatar());
                GlideDownLoadImage.getInstance().loadCircleImage(extend_info.get(0).getAvatar(), mHolder.item_iv_head1);
            } else {
                mHolder.item_iv_head1.setBackgroundResource(R.mipmap.user_default_icon);
            }
            if (extend_info.size() >= 2) {
                Log.i("BannerListSuccess", "BannerListSuccess: " + extend_info.get(1).getAvatar());
                GlideDownLoadImage.getInstance().loadCircleImage(extend_info.get(1).getAvatar(), mHolder.item_iv_head2);
            } else {
                mHolder.item_iv_head2.setBackgroundResource(R.mipmap.user_default_icon);
            }
            if (extend_info.size() >= 3) {
                Log.i("BannerListSuccess", "BannerListSuccess: " + extend_info.get(2).getAvatar());
                GlideDownLoadImage.getInstance().loadCircleImage(extend_info.get(2).getAvatar(), mHolder.item_iv_head3);
            } else {
                mHolder.item_iv_head3.setBackgroundResource(R.mipmap.user_default_icon);
            }
        } else {
            mHolder.item_iv_head1.setBackgroundResource(R.mipmap.user_default_icon);
            mHolder.item_iv_head2.setBackgroundResource(R.mipmap.user_default_icon);
            mHolder.item_iv_head3.setBackgroundResource(R.mipmap.user_default_icon);
        }
        mHolder.frame_pic.setTag(mHomeItemBean);
        mHolder.frame_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeItemBean mHomeItemBean = (HomeItemBean) v.getTag();
                String url = mHomeItemBean.getUrl();
                int type = mHomeItemBean.getType();
                String skip_source = mHomeItemBean.getSkip_source();
                String title = "";
                if (type == 1) {
                    title = mHomeItemBean.getSubtitle();
                } else {
                    title = mHomeItemBean.getTitle();
                }
                Log.e("onItemClick", "url=" + url + "    skip_source=" + skip_source);
                if (!TextUtils.isEmpty(url)) {
                    url = url + "/is_app_Android/1";
                    SharedPreferencesHelper.put(mContext, "starturl", url);
                    SharedPreferencesHelper.put(mContext, "title", mHomeItemBean.getTitle());
                    if (UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToBangDan)) {
                        Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                        intent.putExtra("html_url", url);
                        intent.putExtra("title", title);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        mContext.startActivity(intent);
                    }
                } else {
                    if (UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToBangDan)) {
                        if (!TextUtils.isEmpty(skip_source) && skip_source.equals("bless_exponent")) {
                            Intent intent = new Intent(mContext, BlessBangDanActivity.class);
                            intent.putExtra("title", title);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            mContext.startActivity(intent);
                        } else if (!TextUtils.isEmpty(skip_source) && skip_source.equals("energy_center")) {
                            Intent intent = new Intent(mContext, EngryBangDanActivity.class);
                            intent.putExtra("title", title);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            mContext.startActivity(intent);
                        }
                    }
                }
            }
        });
        int type = mHomeItemBean.getType();
        if (type == 1) {
            mHolder.layout_right.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mHomeItemBean.getStart_time()) && !TextUtils.isEmpty(mHomeItemBean.getEnd_time())) {
                mHolder.item_tv_time.setText(mHomeItemBean.getStart_time() + "至" + mHomeItemBean.getEnd_time());
                mHolder.item_tv_time.setVisibility(View.VISIBLE);
            } else {
                mHolder.item_tv_time.setVisibility(View.INVISIBLE);
            }
        } else {
            if (!TextUtils.isEmpty(mHomeItemBean.getStart_time()) && !TextUtils.isEmpty(mHomeItemBean.getEnd_time())) {
                mHolder.item_tv_time.setText(mHomeItemBean.getStart_time() + "至" + mHomeItemBean.getEnd_time());
                mHolder.item_tv_time.setVisibility(View.VISIBLE);
            } else {
                mHolder.item_tv_time.setVisibility(View.INVISIBLE);
                if (!TextUtils.isEmpty(mHomeItemBean.getSubtitle())) {
                    mHolder.item_tv_time.setText(mHomeItemBean.getSubtitle());
                    mHolder.item_tv_time.setVisibility(View.VISIBLE);
                }
            }
            mHolder.layout_right.setVisibility(View.INVISIBLE);
        }
        String color = mHomeItemBean.getColor();
        if (!TextUtils.isEmpty(color)) {
            mHolder.item_tv_title.setTextColor(Color.parseColor(mHomeItemBean.getColor()));
            mHolder.item_tv_time.setTextColor(Color.parseColor(mHomeItemBean.getColor()));
        }
        Log.i("BannerListSuccess", "BannerListSuccess: " + mHomeItemBean.getPic());
        GlideDownLoadImage.getInstance().loadCircleImageLive(mHomeItemBean.getPic(), R.mipmap.moren_new, mHolder.item_iv_pic, ConstantManager.image_angle);

        return view;
    }

    private class ViewHolder {
        TextView item_tv_title;
        TextView item_tv_time;
        FrameLayout frame_pic;
        ImageView item_iv_pic;
        RelativeLayout layout_right;
        ImageView item_iv_head1;
        ImageView item_iv_head2;
        ImageView item_iv_head3;
    }
}
