package com.longcheng.lifecareplan.modular.mine.pioneercenter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.home.bean.HomeItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.activity.PioneerFQBTCActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.activity.PioneerXuFeiActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/24 13:27
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MenuTopAdapter extends PagerAdapter {

    private Context mContext;
    private PioneerItemBean user;
    private LayoutInflater mInflater;
    int receive_order_status;
    String displayDiffTime;
    int size = 2;
    Handler mHandler;

    public MenuTopAdapter(Context mContext, Handler mHandler, PioneerItemBean user, int receive_order_status, String displayDiffTime) {
        this.mContext = mContext;
        this.user = user;
        this.mHandler = mHandler;
        this.displayDiffTime = displayDiffTime;
        this.receive_order_status = receive_order_status;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @SuppressLint("ResourceType")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position %= size;
        if (position < 0) {
            position = size + position;
        }
        View view = mInflater.inflate(R.layout.pioneer_menu_topitem, container, false);
        ImageView iv_gl = view.findViewById(R.id.iv_gl);
        LinearLayout layout_info = view.findViewById(R.id.layout_info);
        ImageView iv_head = view.findViewById(R.id.iv_head);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_jieeqi = view.findViewById(R.id.tv_jieeqi);
        LinearLayout layout_shenfen = view.findViewById(R.id.layout_shenfen);
        TextView tv_time = view.findViewById(R.id.tv_time);
        ImageView iv_xufei = view.findViewById(R.id.iv_xufei);
        ImageView iv_business = view.findViewById(R.id.iv_business);

        Log.e("instantiateItem", "position==" + position);
        if (position == 0) {
            iv_gl.setVisibility(View.GONE);
            layout_info.setVisibility(View.VISIBLE);
            iv_xufei.setVisibility(View.VISIBLE);
            iv_business.setVisibility(View.VISIBLE);
        } else {
            iv_gl.setVisibility(View.VISIBLE);
            layout_info.setVisibility(View.GONE);
            iv_xufei.setVisibility(View.GONE);
            iv_business.setVisibility(View.GONE);
        }
        if (receive_order_status == 1) {
            iv_business.setBackgroundResource(R.mipmap.my_center_yingye);
        } else {
            iv_business.setBackgroundResource(R.mipmap.my_center_dayang);
        }
        tv_time.setText("有效期：" + displayDiffTime);
        if (user != null) {
            GlideDownLoadImage.getInstance().loadCircleImage(user.getAvatar(), iv_head);
            tv_name.setText(CommonUtil.setName(user.getUser_name()));
            tv_jieeqi.setText(user.getBranch_info());
            tv_jieeqi.getBackground().setAlpha(92);
            ArrayList<String> user_identity_imgs = user.getIdentity_flag();
            layout_shenfen.removeAllViews();
            if (user_identity_imgs != null && user_identity_imgs.size() > 0) {
                for (String info : user_identity_imgs) {
                    LinearLayout linearLayout = new LinearLayout(mContext);
                    ImageView imageView = new ImageView(mContext);
                    int dit = DensityUtil.dip2px(mContext, 15);
                    int jian = DensityUtil.dip2px(mContext, 1);
                    linearLayout.setPadding(0, 0, jian, 2);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                    GlideDownLoadImage.getInstance().loadCircleImageCommune(mContext, info, imageView);
                    linearLayout.addView(imageView);
                    layout_shenfen.addView(linearLayout);
                }
            }
        }
        iv_xufei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PioneerXuFeiActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);
            }
        });
        iv_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(2);
            }
        });
        iv_gl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PioneerFQBTCActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);
            }
        });
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
