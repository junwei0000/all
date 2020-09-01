package com.longcheng.lifecareplan.modular.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.home.bangdan.BangDan1824Activity;
import com.longcheng.lifecareplan.modular.home.bangdan.BlessBangDanActivity;
import com.longcheng.lifecareplan.modular.home.bean.HomeItemBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.lifecareplan.modular.mine.emergencys.EmergencysBangDanActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.activity.RankPionZYBActivity;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.activity.RankCornucopiaActivity;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/24 13:27
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class DedicationAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<HomeItemBean> list;
    private LayoutInflater mInflater;

    public DedicationAdapter(Context mContext, ArrayList<HomeItemBean> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(ArrayList<HomeItemBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 页面宽度所占ViewPager测量宽度的权重比例，默认为1
     */
    @Override
    public float getPageWidth(int position) {
        if (position == list.size() - 1) {
            int left = DensityUtil.dip2px(mContext, 15);
            int wi = DensityUtil.screenWith(mContext);
            float ff = (float) ((wi * 0.45 + left) / wi);
            return ff;
        } else {
            return (float) 0.45;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.banner_item_adapter, container, false);
        TextView item_tv_title = view.findViewById(R.id.item_tv_title);
        TextView item_tv_time = view.findViewById(R.id.item_tv_time);
        FrameLayout frame_pic = view.findViewById(R.id.frame_pic);
        RoundedImageView item_iv_pic = view.findViewById(R.id.item_iv_pic);
        RelativeLayout layout_right = view.findViewById(R.id.layout_right);
        ImageView item_iv_head1 = view.findViewById(R.id.item_iv_head1);
        ImageView item_iv_head2 = view.findViewById(R.id.item_iv_head2);
        ImageView item_iv_head3 = view.findViewById(R.id.item_iv_head3);

        int left = DensityUtil.dip2px(mContext, 15);
        if (position == list.size() - 1) {
            frame_pic.setPadding(left, 0, left, 0);
        } else {
            frame_pic.setPadding(left, 0, 0, 0);
        }
        int wi = (int) (DensityUtil.screenWith(mContext) * 0.45) - left;
//        frame_pic.setLayoutParams(new LinearLayout.LayoutParams((wi - left), (int) (wi * 0.5333)));
        item_iv_pic.setLayoutParams(new FrameLayout.LayoutParams(wi, (int) (wi * 0.5333)));
        HomeItemBean mHomeItemBean = list.get(position);
        item_tv_title.setText(mHomeItemBean.getTitle());
        item_tv_title.setVisibility(View.INVISIBLE);
        List<HomeItemBean> extend_info = mHomeItemBean.getExtend_info();
        if (extend_info != null && extend_info.size() > 0) {
            Log.i("BannerListSuccess", "position: " + position);
            if (extend_info.size() >= 1) {
                Log.i("BannerListSuccess", "BannerListSuccess: " + extend_info.get(0).getAvatar());
                GlideDownLoadImage.getInstance().loadCircleImage(extend_info.get(0).getAvatar(), item_iv_head1);
            } else {
                item_iv_head1.setBackgroundResource(R.mipmap.user_default_icon);
            }
            if (extend_info.size() >= 2) {
                Log.i("BannerListSuccess", "BannerListSuccess: " + extend_info.get(1).getAvatar());
                GlideDownLoadImage.getInstance().loadCircleImage(extend_info.get(1).getAvatar(), item_iv_head2);
            } else {
                item_iv_head2.setBackgroundResource(R.mipmap.user_default_icon);
            }
            if (extend_info.size() >= 3) {
                Log.i("BannerListSuccess", "BannerListSuccess: " + extend_info.get(2).getAvatar());
                GlideDownLoadImage.getInstance().loadCircleImage(extend_info.get(2).getAvatar(), item_iv_head3);
            } else {
                item_iv_head3.setBackgroundResource(R.mipmap.user_default_icon);
            }
        } else {
            item_iv_head1.setBackgroundResource(R.mipmap.user_default_icon);
            item_iv_head2.setBackgroundResource(R.mipmap.user_default_icon);
            item_iv_head3.setBackgroundResource(R.mipmap.user_default_icon);
        }
        frame_pic.setTag(mHomeItemBean);
        frame_pic.setOnClickListener(new View.OnClickListener() {
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
                        } else if (!TextUtils.isEmpty(skip_source) && skip_source.equals("bless_ranking")) {
                            Intent intent = new Intent(mContext, BangDan1824Activity.class);
                            intent.putExtra("title", title);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            mContext.startActivity(intent);
                        } else if (!TextUtils.isEmpty(skip_source) && skip_source.equals("bless_card_ranking")) {
                            Intent intent = new Intent(mContext, EmergencysBangDanActivity.class);
                            intent.putExtra("title", title);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            mContext.startActivity(intent);
                        } else if (!TextUtils.isEmpty(skip_source) && skip_source.equals("zhuyoubao_exponent")) {
                            Intent intent = new Intent(mContext, RankPionZYBActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            mContext.startActivity(intent);
                        }else if (!TextUtils.isEmpty(skip_source) && skip_source.equals("jubaopen_ranking")) {
                            Intent intent = new Intent(mContext, RankCornucopiaActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            mContext.startActivity(intent);
                        }
                    }
                }
            }
        });
        int type = mHomeItemBean.getType();
        if (type == 1) {
            layout_right.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mHomeItemBean.getStart_time()) && !TextUtils.isEmpty(mHomeItemBean.getEnd_time())) {
                item_tv_time.setText(mHomeItemBean.getStart_time() + "至" + mHomeItemBean.getEnd_time());
                item_tv_time.setVisibility(View.VISIBLE);
            } else {
                item_tv_time.setVisibility(View.INVISIBLE);
            }
        } else {
            if (!TextUtils.isEmpty(mHomeItemBean.getStart_time()) && !TextUtils.isEmpty(mHomeItemBean.getEnd_time())) {
                item_tv_time.setText(mHomeItemBean.getStart_time() + "至" + mHomeItemBean.getEnd_time());
                item_tv_time.setVisibility(View.VISIBLE);
            } else {
                item_tv_time.setVisibility(View.INVISIBLE);
                if (!TextUtils.isEmpty(mHomeItemBean.getSubtitle())) {
                    item_tv_time.setText(mHomeItemBean.getSubtitle());
                    item_tv_time.setVisibility(View.VISIBLE);
                }
            }
            layout_right.setVisibility(View.INVISIBLE);
        }
        String color = mHomeItemBean.getColor();
        if (!TextUtils.isEmpty(color)) {
            item_tv_title.setTextColor(Color.parseColor(mHomeItemBean.getColor()));
            item_tv_time.setTextColor(Color.parseColor(mHomeItemBean.getColor()));
        }
        GlideDownLoadImage.getInstance().loadCircleImageRoleREf2(mHomeItemBean.getPic(), item_iv_pic, 0);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /*
     * 解决viewpager在刷新调用notifyDataSetChanged不起作用 使用懒加载后没有效果?
     */
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
