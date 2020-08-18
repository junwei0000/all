package com.longcheng.lifecareplan.modular.helpwith.connon.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.HashMap;

public class GrupMinUtils {


    Activity context;
    int x = 0;
    int y = 0;

    int itemChairWid;
    int wid;
    int dis;
    int chairwid;

    public GrupMinUtils(Activity context) {
        this.context = context;
        itemChairWid = DensityUtil.dip2px(context, 30);
        wid = DensityUtil.screenWith(context) / 2;
        dis = DensityUtil.dip2px(context, 35);
        chairwid = wid - dis * 2;
    }

    public void setTableView(ImageView imageView) {
        imageView.setLayoutParams(new LinearLayout.LayoutParams(chairwid - DensityUtil.dip2px(context, 4), chairwid - DensityUtil.dip2px(context, 4)));
    }

    /**
     * 小桌
     */
    public void showChairAllViewDetailAdd(int groupnum, boolean joinStatus, HashMap<Integer, CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean> mHashmap, RelativeLayout relatGroup, AbsoluteLayout absolut_chair, LinearLayout layout_group_center) {
        layout_group_center.setLayoutParams(new LinearLayout.LayoutParams(chairwid, chairwid));
        relatGroup.setLayoutParams(new LinearLayout.LayoutParams(wid, wid));
        absolut_chair.removeAllViews();
        for (int i = 0; i < groupnum; i++) {
            View view = context.getLayoutInflater().inflate(R.layout.connon_create_group_item_add, null);
            ImageView iv_chair = view.findViewById(R.id.iv_chair);
            LinearLayout layout_group_useinfo = view.findViewById(R.id.layout_group_useinfo);
            ImageView iv_ablum = view.findViewById(R.id.iv_ablum);
            TextView tv_name = view.findViewById(R.id.tv_name);
            layout_group_useinfo.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            layout_group_useinfo.setVisibility(View.GONE);
            iv_chair.setVisibility(View.VISIBLE);
            if (mHashmap.containsKey(i)) {
                layout_group_useinfo.setVisibility(View.VISIBLE);
                iv_chair.setVisibility(View.GONE);
                CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean itemBean = mHashmap.get(i);
                GlideDownLoadImage.getInstance().loadCircleImage(itemBean.getUser_avatar(), iv_ablum);
                String user_name = itemBean.getUser_name();
                if (i == 0) {
                    tv_name.setText("队长：" + CommonUtil.setName3(user_name));
                } else {
                    tv_name.setText(CommonUtil.setName3(user_name));
                }
                int widleft = DensityUtil.dip2px(context, 30);
                layout_group_useinfo.setLayoutParams(new RelativeLayout.LayoutParams(widleft, RelativeLayout.LayoutParams.WRAP_CONTENT));
            } else {
                if (joinStatus) {
                    iv_chair.setBackgroundResource(R.mipmap.connon_group_chair);
                } else {
                    iv_chair.setBackgroundResource(R.mipmap.connon_group_chair_add);
                }

            }
            if (i == 0) {
                int topwid = DensityUtil.dip2px(context, 60);
                layout_group_useinfo.setLayoutParams(new RelativeLayout.LayoutParams(topwid, RelativeLayout.LayoutParams.WRAP_CONTENT));
                x = wid / 2 - topwid / 2;
                y = 0;
                iv_chair.setRotation(180);
            }
            if (groupnum == 2) {
                layout_group_center.setBackgroundResource(R.mipmap.connon_group_two);
            } else if (groupnum == 3) {
                layout_group_center.setBackgroundResource(R.mipmap.connon_group_three);
            } else if (groupnum == 4) {
                layout_group_center.setBackgroundResource(R.mipmap.connon_group_four);
            } else if (groupnum == 5) {//每个五角星的角度为36°
                layout_group_center.setBackgroundResource(R.mipmap.connon_group_five);
            }
            setGroupView(groupnum, i, iv_chair, view, absolut_chair);
        }

    }

    public void setGroupView(int groupnum, int i, ImageView iv_chair, View view, AbsoluteLayout absolut_chair) {
        if (groupnum == 2) {
            if (i == 1) {
                x = wid / 2 - itemChairWid / 2;
                y = wid - itemChairWid - DensityUtil.dip2px(context, 3);
                iv_chair.setRotation(0);
            }
        } else if (groupnum == 3) {
            if (i == 1) {
                x = wid - itemChairWid - DensityUtil.dip2px(context, 15);
                y = (wid - itemChairWid) - chairwid / 3;
                iv_chair.setRotation(300);
            } else if (i == 2) {
                x = (wid / 2 - itemChairWid / 2) - chairwid / 3 - itemChairWid + DensityUtil.dip2px(context, 3);
                y = (wid - itemChairWid) - chairwid / 3;
                iv_chair.setRotation(60);
            }
        } else if (groupnum == 4) {
            if (i == 1) {
                x = wid - itemChairWid - DensityUtil.dip2px(context, 3);
                y = wid / 2 - itemChairWid / 2;
                iv_chair.setRotation(270);
            } else if (i == 2) {
                x = wid / 2 - itemChairWid / 2;
                y = wid - itemChairWid - DensityUtil.dip2px(context, 3);
                iv_chair.setRotation(0);
            } else if (i == 3) {
                x = 0 + DensityUtil.dip2px(context, 3);
                y = wid / 2 - itemChairWid / 2;
                iv_chair.setRotation(90);
            }
        } else if (groupnum == 5) {//每个五角星的角度为36°
            if (i == 1) {
                x = wid - itemChairWid - DensityUtil.dip2px(context, 6);
                y = dis + chairwid / 3 - itemChairWid + itemChairWid / 3;
                iv_chair.setRotation(252);
            } else if (i == 2) {
                x = (wid - itemChairWid) - chairwid / 3 + itemChairWid / 3 - DensityUtil.dip2px(context, 3);
                y = wid - itemChairWid - itemChairWid * 2 / 3;
                iv_chair.setRotation(324);
            } else if (i == 3) {
                x = (wid / 2 - itemChairWid / 2) - chairwid * 1 / 3 - DensityUtil.dip2px(context, 6);
                y = wid - itemChairWid - itemChairWid * 2 / 3;
                iv_chair.setRotation(36);
            } else if (i == 4) {
                x = itemChairWid / 3 - DensityUtil.dip2px(context, 3);
                y = dis + chairwid / 3 - itemChairWid + itemChairWid / 3;
                iv_chair.setRotation(108);
            }
        }
        AbsoluteLayout.LayoutParams absLayoutParams = new AbsoluteLayout.LayoutParams(
                AbsoluteLayout.LayoutParams.MATCH_PARENT,
                AbsoluteLayout.LayoutParams.MATCH_PARENT,
                x, y);
        absolut_chair.addView(view, absLayoutParams);
    }
}

