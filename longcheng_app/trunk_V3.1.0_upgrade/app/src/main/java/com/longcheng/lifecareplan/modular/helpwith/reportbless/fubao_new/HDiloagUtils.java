package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.model.GlideUrl;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.bean.HuoDongBean;
import com.longcheng.lifecareplan.bean.contactbean.PhoneBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.adapter.MypagerAdapter;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideCacheUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;

import java.util.ArrayList;
import java.util.List;

public class HDiloagUtils {
    public HDiloagUtils() {

    }

    Activity mActivity;
    MyDialog mobDialog;

    List<HuoDongBean> huoDongBeans;


    ViewPager viewPager = null;
    MypagerAdapter mypagerAdapter = null;

//    LayoutInflater inflater = null;

    int curponsition = -1;

    public void init(Activity mActivity, List<HuoDongBean> huoDongBeans) {

        this.huoDongBeans = huoDongBeans;
        this.mActivity = mActivity;
//        inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setPayDialog() {
        if (mobDialog == null) {
            mobDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_hdiloag);// 创建Dialog并设置样式主题
            mobDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mobDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mobDialog.show();
            LinearLayout layoutCannel = mobDialog.findViewById(R.id.layout_cancel);
            layoutCannel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mobDialog.dismiss();
                }
            });

        } else {
            mobDialog.show();
        }
        initView(mobDialog);
    }

    private ImageView[] ivs = null;

    private void initView(MyDialog modialog) {
        WindowManager m = mActivity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = modialog.getWindow().getAttributes(); //获取对话框当前的参数值
        int wd = d.getWidth() * 7 / 8;
        int hd = (int) (wd * 1.317);
        p.width = wd;
        modialog.getWindow().setAttributes(p); //设置生效
       /* ivs = new ImageView[3];
        ivs[0] = (ImageView) modialog.findViewById(R.id.iv_1);//小点
        ivs[1] = (ImageView) modialog.findViewById(R.id.iv_2);
        ivs[2] = (ImageView) modialog.findViewById(R.id.iv_3);
        ivs[0].setSelected(true);*/
        getText();
        LogUtils.v("TAG","wd"+wd+"hd"+hd);
        viewPager = modialog.findViewById(R.id.vp);
        viewPager.setLayoutParams(new RelativeLayout.LayoutParams(wd,hd));
        if (mypagerAdapter == null) {
            mypagerAdapter = new MypagerAdapter(mActivity,huoDongBeans);
        } else {
            mypagerAdapter.setmFragmentList(huoDongBeans);
        }
        mypagerAdapter.setVpOnItemClick(new VPOnItemClick() {
            @Override
            public void onItemClick(HuoDongBean itemclick) {
                ToastUtils.showToast("Name:"+itemclick.getName());
            }
        });
        viewPager.setAdapter(mypagerAdapter);
        viewPager.setCurrentItem(1, false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curponsition = position;//记录当前显示的索引
                switch (position) {
                    case 0:
                    case 3:
                        setSelect(2);
                        break;
                    case 1:
                    case 4:
                        setSelect(0);
                        break;
                    case 2:
                        setSelect(1);
                        break;
                }
            }

            private void setSelect(int index) {
                for (int i = 0; i < ivs.length; i++) {
//                    ivs[i].setSelected(i == index);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //验证当前的滑动是否结束
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (curponsition == 0) {
                        viewPager.setCurrentItem(3, false);//切换，不要动画效果
                    } else if (curponsition == 4) {
                        viewPager.setCurrentItem(1, false);//切换，不要动画效果
                    }
                }
            }
        });

    }

    private void getText() {
        huoDongBeans = new ArrayList<>();
        List<String> urls1 = new ArrayList<>();
        urls1.add("http://img.ivsky.com/img/tupian/pre/201707/30/xingganyoumeilidemeinvtupian-005.jpg");
        urls1.add("http://img.ivsky.com/img/tupian/pre/201707/30/xingganyoumeilidemeinvtupian-007.jpg");
        urls1.add("http://img.ivsky.com/img/tupian/pre/201801/16/qinwen_lianren-006.jpg");
        urls1.add("http://img.ivsky.com/img/tupian/pre/201803/24/qinwen_lianren-001.jpg");
        urls1.add("http://img.ivsky.com/img/tupian/pre/201803/24/qinwen_lianren-001.jpg");
        for (int i = 0; i < urls1.size(); i++) {
            HuoDongBean huoDongBean = new HuoDongBean();
            huoDongBean.setImageUrl(urls1.get(i));
            huoDongBean.setHdid("hdid_" + i);
            huoDongBean.setName("hdid_" + i);
            huoDongBeans.add(huoDongBean);
        }
    }

}
