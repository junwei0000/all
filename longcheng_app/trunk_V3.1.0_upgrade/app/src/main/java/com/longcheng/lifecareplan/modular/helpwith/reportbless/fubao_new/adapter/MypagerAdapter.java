package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.longcheng.lifecareplan.bean.HuoDongBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.VPOnItemClick;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

public class MypagerAdapter extends PagerAdapter {
    List<HuoDongBean> huoDongBeans;
    private Activity mActivity;
    VPOnItemClick vpOnItemClick ;

    public void setVpOnItemClick(VPOnItemClick vpOnItemClick) {
        this.vpOnItemClick = vpOnItemClick;
    }

    public void setmFragmentList(List<HuoDongBean> huoDongBeans) {
        this.notifyDataSetChanged();
        this.huoDongBeans = huoDongBeans;
    }

    public MypagerAdapter(Activity mActivity,List<HuoDongBean> huoDongBeans) {
        this.mActivity = mActivity;
        this.huoDongBeans = huoDongBeans;
    }

    @Override
    public int getCount() {
        return huoDongBeans.size() > 0 ? huoDongBeans.size() : 0 ;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        WindowManager m = mActivity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = mActivity.getWindow().getAttributes(); //获取对话框当前的参数值
        int wd = d.getWidth() * 7 / 8;
        int hd = (int) (wd * 1.317);
        LogUtils.v("TAG","wd1"+wd+"hd1"+hd);
        HuoDongBean huoDongBean = huoDongBeans.get(position);
        ImageView imageView = new ImageView(mActivity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wd,hd);
        imageView.setLayoutParams(params);
        GlideDownLoadImage.getInstance().loadCircleImageRole(mActivity,huoDongBean.getImageUrl(),imageView,0);
        container.addView(imageView);
        imageView.setTag(huoDongBean);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vpOnItemClick != null){
                    HuoDongBean huoDongBean = (HuoDongBean) imageView.getTag();
                    vpOnItemClick.onItemClick(huoDongBean);
                }
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
