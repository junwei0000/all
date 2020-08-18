package com.longcheng.lifecareplan.modular.helpwith.connon.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpGroupRoomDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpListDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.ZYBZuDuiItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.zudui.ZYBUserSelectActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.activity.df.ZuDuiSelectActivity;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;

import java.util.HashMap;

public class GrupUtils {


    Activity context;
    int x = 0;
    int y = 0;

    int itemChairWid;
    int wid;
    int dis;
    int chairwid;

    public GrupUtils(Activity context) {
        this.context = context;
        itemChairWid = DensityUtil.dip2px(context, 60);
        wid = DensityUtil.screenWith(context);
        dis = DensityUtil.dip2px(context, 65);
        chairwid = wid - dis * 2;
    }


    /**
     * 结缘互祝
     */
    public void showChairAllViewMrray(int groupnum, RelativeLayout relatGroup, AbsoluteLayout absolut_chair, LinearLayout layout_group_center) {
        String avatar = (String) SharedPreferencesHelper.get(context, "avatar", "");
        String user_name = (String) SharedPreferencesHelper.get(context, "user_name", "");
        layout_group_center.setLayoutParams(new LinearLayout.LayoutParams(chairwid, chairwid));
        relatGroup.setLayoutParams(new LinearLayout.LayoutParams(wid, wid));

        int skewedWid = DensityUtil.dip2px(context, 10);

        absolut_chair.removeAllViews();
        for (int i = 0; i < groupnum; i++) {
            View view = context.getLayoutInflater().inflate(R.layout.connon_create_group_item, null);
            ImageView iv_chair = view.findViewById(R.id.iv_chair);
            LinearLayout layout_group_useinfo = view.findViewById(R.id.layout_group_useinfo);
            ImageView iv_ablum = view.findViewById(R.id.iv_ablum);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_integral = view.findViewById(R.id.tv_integral);
            TextView tv_duizhangtitle = view.findViewById(R.id.tv_duizhangtitle);

            GlideDownLoadImage.getInstance().loadCircleImage(avatar, iv_ablum);
            if (!TextUtils.isEmpty(user_name) && user_name.length() >= 3) {
                user_name = user_name.substring(0, 3);
            }
            tv_name.setText(CommonUtil.setName(user_name));
            layout_group_useinfo.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            iv_chair.setVisibility(View.VISIBLE);
            layout_group_useinfo.setVisibility(View.GONE);
            if (i == 0) {
                tv_duizhangtitle.setText("队长" + groupnum + "积分");
                iv_chair.setVisibility(View.GONE);
                tv_integral.setVisibility(View.GONE);
                layout_group_useinfo.setVisibility(View.VISIBLE);
                int topwid = DensityUtil.dip2px(context, 110);
                layout_group_useinfo.setLayoutParams(new RelativeLayout.LayoutParams(topwid, RelativeLayout.LayoutParams.WRAP_CONTENT));
                x = wid / 2 - topwid / 2;
                y = 0;
                iv_chair.setRotation(180);
            } else {
                tv_integral.setVisibility(View.VISIBLE);
                iv_chair.setBackgroundResource(R.mipmap.connon_group_chair);
            }
            if (groupnum == 2) {
                if (i == 1) {
                    tv_integral.setPadding(0, 0, 0, skewedWid);
                }
            } else if (groupnum == 3) {
                if (i == 1) {
                    tv_integral.setPadding(0, 0, skewedWid, skewedWid);
                } else if (i == 2) {
                    tv_integral.setPadding(skewedWid, 0, 0, skewedWid);
                }
            } else if (groupnum == 4) {
                if (i == 1) {
                    tv_integral.setPadding(0, 0, skewedWid, 0);
                } else if (i == 2) {
                    tv_integral.setPadding(0, 0, 0, skewedWid);
                } else if (i == 3) {
                    tv_integral.setPadding(skewedWid, 0, 0, 0);
                }
            } else if (groupnum == 5) {//每个五角星的角度为36°
                if (i == 1) {
                    tv_integral.setPadding(0, 0, skewedWid, 0);
                } else if (i == 2) {
                    tv_integral.setPadding(0, 0, skewedWid, skewedWid);
                } else if (i == 3) {
                    tv_integral.setPadding(skewedWid, 0, 0, skewedWid);
                } else if (i == 4) {
                    tv_integral.setPadding(skewedWid, 0, 0, 0);
                }
            }
            setGroupView(groupnum, i, iv_chair, view, absolut_chair);
        }

    }

    /**
     * 结伴互祝
     */
    public void showChairAllViewGoWith(int groupnum, boolean inviteRoomStatus, String knp_msg_id, String knp_group_room_id,
                                       HashMap<Integer, CHelpGroupRoomDataBean.GroupRoomAfterBean.GroupRoomItemBean> mHashmap,
                                       RelativeLayout relatGroup, AbsoluteLayout absolut_chair, LinearLayout layout_group_center) {
        layout_group_center.setLayoutParams(new LinearLayout.LayoutParams(chairwid, chairwid));
        relatGroup.setLayoutParams(new LinearLayout.LayoutParams(wid, wid));

        absolut_chair.removeAllViews();
        for (int i = 0; i < groupnum; i++) {
            View view = context.getLayoutInflater().inflate(R.layout.connon_create_group_item, null);
            ImageView iv_chair = view.findViewById(R.id.iv_chair);
            LinearLayout layout_group_useinfo = view.findViewById(R.id.layout_group_useinfo);
            LinearLayout layou_info = view.findViewById(R.id.layou_info);
            ImageView iv_ablum = view.findViewById(R.id.iv_ablum);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_duizhangtitle = view.findViewById(R.id.tv_duizhangtitle);
            TextView tv_jifen = view.findViewById(R.id.tv_jifen);
            layout_group_useinfo.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            iv_chair.setVisibility(View.VISIBLE);
            layout_group_useinfo.setVisibility(View.GONE);
            tv_jifen.setVisibility(View.GONE);
            if (i == 0) {
                String avatar = (String) SharedPreferencesHelper.get(context, "avatar", "");
                String user_name = (String) SharedPreferencesHelper.get(context, "user_name", "");
                GlideDownLoadImage.getInstance().loadCircleImage(avatar, iv_ablum);
                tv_name.setText(CommonUtil.setName3(user_name));
                tv_duizhangtitle.setText("队长" + groupnum + "积分");
                iv_chair.setVisibility(View.GONE);
                layout_group_useinfo.setVisibility(View.VISIBLE);
                int topwid = DensityUtil.dip2px(context, 110);
                layout_group_useinfo.setLayoutParams(new RelativeLayout.LayoutParams(topwid, RelativeLayout.LayoutParams.WRAP_CONTENT));
                x = wid / 2 - topwid / 2;
                y = 0;
                iv_chair.setRotation(180);
            } else {
                if (mHashmap.containsKey(i)) {
                    iv_chair.setVisibility(View.GONE);
                    tv_duizhangtitle.setVisibility(View.GONE);
                    tv_jifen.setVisibility(View.VISIBLE);
                    layout_group_useinfo.setVisibility(View.VISIBLE);
                    CHelpGroupRoomDataBean.GroupRoomAfterBean.GroupRoomItemBean cHelpDetailItemBean = mHashmap.get(i);
                    GlideDownLoadImage.getInstance().loadCircleImage(cHelpDetailItemBean.getUser_avatar(), iv_ablum);
                    String user_name = cHelpDetailItemBean.getUser_name();
                    if (!TextUtils.isEmpty(user_name) && user_name.length() >= 3) {
                        user_name = user_name.substring(0, 3);
                    }
                    tv_name.setText(CommonUtil.setName(user_name));
                    layou_info.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                    tv_name.setTextColor(context.getResources().getColor(R.color.color_222222));
                    iv_chair.setVisibility(View.GONE);
                    tv_name.setGravity(Gravity.CENTER);

                    int widleft = DensityUtil.dip2px(context, 60);
                    tv_name.setWidth(widleft);
                } else {
                    if (inviteRoomStatus) {
                        iv_chair.setTag(knp_msg_id);
                        iv_chair.setBackgroundResource(R.mipmap.connon_group_chair_add);
                        iv_chair.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String knp_msg_id = (String) v.getTag();
                                if (!TextUtils.isEmpty(knp_msg_id) && Double.valueOf(knp_msg_id) > 0) {
                                    Log.e("ResponseBody", "knp_group_room_id===" + knp_group_room_id);
                                    Intent intent = new Intent(context, GoWithUserSelectActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    intent.putExtra("knp_group_room_id", knp_group_room_id);
                                    context.startActivity(intent);
                                } else {
                                    ToastUtils.showToast("请选择爱友");
                                }
                            }
                        });
                    } else {
                        iv_chair.setBackgroundResource(R.mipmap.connon_group_chair);
                    }
                }
            }
            setGroupView(groupnum, i, iv_chair, view, absolut_chair);
        }

    }

    /**
     * 大厅列表
     */
    public void showChairAllViewList(int groupnum, int already_person_number, RelativeLayout relatGroup, AbsoluteLayout absolut_chair, LinearLayout layout_group_center) {

        layout_group_center.setLayoutParams(new LinearLayout.LayoutParams(chairwid, chairwid));
        relatGroup.setLayoutParams(new LinearLayout.LayoutParams(wid, wid));
        absolut_chair.removeAllViews();
        for (int i = 0; i < groupnum; i++) {
            View view = context.getLayoutInflater().inflate(R.layout.connon_create_group_item, null);
            ImageView iv_chair = view.findViewById(R.id.iv_chair);
            LinearLayout layout_group_useinfo = view.findViewById(R.id.layout_group_useinfo);
            layout_group_useinfo.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            layout_group_useinfo.setVisibility(View.GONE);
            iv_chair.setVisibility(View.VISIBLE);
            if (already_person_number >= groupnum) {
                if (i == groupnum - 1) {
                    iv_chair.setBackgroundResource(R.mipmap.connon_group_chair);
                } else {
                    iv_chair.setBackgroundResource(R.mipmap.connon_list_chair);
                }
            } else {
                if (i < already_person_number) {
                    iv_chair.setBackgroundResource(R.mipmap.connon_list_chair);
                } else {
                    iv_chair.setBackgroundResource(R.mipmap.connon_group_chair);
                }
            }
            if (i == 0) {
                layout_group_useinfo.setVisibility(View.GONE);
                x = wid / 2 - itemChairWid / 2;
                y = 0;
                iv_chair.setRotation(180);
            }
            setGroupView(groupnum, i, iv_chair, view, absolut_chair);
        }

    }


    /**
     * 请购祝佑宝组队
     */
    public void showChairAllViewZYBZuDui(int groupnum, int process_status, int is_update_entre, int myrole, String entrepreneurs_id,
                                         String zhuyoubao_team_room_id, String zhuyoubao_team_item_id,
                                         HashMap<Integer, ZYBZuDuiItemBean> mHashmap,
                                         RelativeLayout relatGroup, AbsoluteLayout absolutChair, LinearLayout layoutGroupCenter) {
        layoutGroupCenter.setLayoutParams(new LinearLayout.LayoutParams(chairwid, chairwid));
        relatGroup.setLayoutParams(new LinearLayout.LayoutParams(wid, wid));

        absolutChair.removeAllViews();
        for (int i = 0; i < groupnum; i++) {
            View view = context.getLayoutInflater().inflate(R.layout.connon_create_group_item, null);
            ImageView iv_chair = view.findViewById(R.id.iv_chair);
            LinearLayout layout_group_useinfo = view.findViewById(R.id.layout_group_useinfo);
            layout_group_useinfo.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            ImageView iv_ablum = view.findViewById(R.id.iv_ablum);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_duizhangtitle = view.findViewById(R.id.tv_duizhangtitle);
            TextView tv_orderstatus = view.findViewById(R.id.tv_orderstatus);
            LinearLayout layou_info = view.findViewById(R.id.layou_info);
            //当前组显示数据，其他不显示
            if (mHashmap.containsKey(i + 1)) {
                ZYBZuDuiItemBean cHelpDetailItemBean = mHashmap.get(i + 1);
                GlideDownLoadImage.getInstance().loadCircleImage(cHelpDetailItemBean.getAvatar(), iv_ablum);
                String user_name = cHelpDetailItemBean.getUser_name();
                tv_name.setText(CommonUtil.setName3(user_name));
                iv_chair.setVisibility(View.GONE);
                tv_name.setGravity(Gravity.CENTER);
                layout_group_useinfo.setVisibility(View.VISIBLE);

                //显示订单状态
                tv_orderstatus.setVisibility(View.VISIBLE);
                int order_status = cHelpDetailItemBean.getOrder_status();
                if (order_status == 1) {//进行中
                    tv_orderstatus.setText("进行中");
                    tv_orderstatus.setBackgroundResource(R.drawable.corners_bg_redprogress);
                    ColorChangeByTime.getInstance().changeDrawableToClolor(context, tv_orderstatus, R.color.red);
                } else if (order_status == 2) {//已完成
                    tv_orderstatus.setText("已完成");
                    tv_orderstatus.setBackgroundResource(R.drawable.corners_bg_redprogress);
                    ColorChangeByTime.getInstance().changeDrawableToClolor(context, tv_orderstatus, R.color.lv_29e425);
                } else {
                    tv_orderstatus.setVisibility(View.GONE);
                }
            } else {
                layout_group_useinfo.setVisibility(View.GONE);
                iv_chair.setVisibility(View.VISIBLE);
                if (myrole == 1) {
                    iv_chair.setTag(entrepreneurs_id);
                    iv_chair.setBackgroundResource(R.mipmap.connon_group_chair_add);
                    iv_chair.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String entrepreneurs_id = (String) v.getTag();
                            if (!TextUtils.isEmpty(entrepreneurs_id)) {
                                Intent intents = new Intent(context, ZYBUserSelectActivity.class);
                                intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                intents.putExtra("zhuyoubao_team_room_id", zhuyoubao_team_room_id);
                                intents.putExtra("zhuyoubao_team_item_id", zhuyoubao_team_item_id);
                                context.startActivity(intents);
                            } else {
                                ToastUtils.showToast("请选择创业导师");
                            }
                        }
                    });
                } else {
                    iv_chair.setBackgroundResource(R.mipmap.connon_group_chair);
                }
            }

            if (i == 0) {
                int topwid = DensityUtil.dip2px(context, 90);
                layout_group_useinfo.setLayoutParams(new RelativeLayout.LayoutParams(topwid, RelativeLayout.LayoutParams.WRAP_CONTENT));
                y = 0;
                x = wid / 2 - topwid / 2;
                iv_chair.setRotation(180);
                tv_duizhangtitle.setVisibility(View.VISIBLE);
            } else {
                tv_duizhangtitle.setVisibility(View.GONE);

                layou_info.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                int widleft = DensityUtil.dip2px(context, 50);
                tv_name.setWidth(widleft);
            }
            setGroupView(groupnum, i, iv_chair, view, absolutChair);
        }

    }

    /**
     * 创业导师组队请购福祺宝
     */
    public void showChairAllViewFQBZuDui(int groupnum, HashMap<Integer, CHelpListDataBean.CHelpListItemBean> mHashmap,
                                         RelativeLayout relatGroup, AbsoluteLayout absolutChair, LinearLayout layoutGroupCenter) {
        layoutGroupCenter.setLayoutParams(new LinearLayout.LayoutParams(chairwid, chairwid));
        relatGroup.setLayoutParams(new LinearLayout.LayoutParams(wid, wid));

        absolutChair.removeAllViews();
        for (int i = 0; i < groupnum; i++) {
            View view = context.getLayoutInflater().inflate(R.layout.connon_create_group_item, null);
            ImageView iv_chair = view.findViewById(R.id.iv_chair);
            LinearLayout layout_group_useinfo = view.findViewById(R.id.layout_group_useinfo);
            layout_group_useinfo.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            ImageView iv_ablum = view.findViewById(R.id.iv_ablum);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_duizhangtitle = view.findViewById(R.id.tv_duizhangtitle);
            LinearLayout layou_info = view.findViewById(R.id.layou_info);
            //当前组显示数据，其他不显示
            if (mHashmap.containsKey(i + 1)) {
                CHelpListDataBean.CHelpListItemBean cHelpDetailItemBean = mHashmap.get(i + 1);
                GlideDownLoadImage.getInstance().loadCircleImage(cHelpDetailItemBean.getUser_avatar(), iv_ablum);
                String user_name = cHelpDetailItemBean.getUser_name();
                tv_name.setText(CommonUtil.setName3(user_name));
                iv_chair.setVisibility(View.GONE);
                tv_name.setGravity(Gravity.CENTER);
                layout_group_useinfo.setVisibility(View.VISIBLE);
            } else {
                iv_chair.setVisibility(View.VISIBLE);
                layout_group_useinfo.setVisibility(View.GONE);
                iv_chair.setBackgroundResource(R.mipmap.connon_group_chair_add);
                iv_chair.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intents = new Intent(context, ZuDuiSelectActivity.class);
                        intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intents);
                    }
                });
            }

            if (i == 0) {
                int topwid = DensityUtil.dip2px(context, 90);
                layout_group_useinfo.setLayoutParams(new RelativeLayout.LayoutParams(topwid, RelativeLayout.LayoutParams.WRAP_CONTENT));
                y = 0;
                x = wid / 2 - topwid / 2;
                iv_chair.setRotation(180);
                tv_duizhangtitle.setVisibility(View.VISIBLE);
            } else {
                tv_duizhangtitle.setVisibility(View.GONE);

                layou_info.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                int widleft = DensityUtil.dip2px(context, 50);
                tv_name.setWidth(widleft);
            }
            if (groupnum == 5) {//每个五角星的角度为36°
                layoutGroupCenter.setBackgroundResource(R.mipmap.connon_group_five);
            }
            setGroupView(groupnum, i, iv_chair, view, absolutChair);
        }

    }

    public void setGroupView(int groupnum, int i, ImageView iv_chair, View view, AbsoluteLayout absolut_chair) {
        if (groupnum == 2) {
            if (i == 1) {
                x = wid / 2 - itemChairWid / 2;
                y = wid - itemChairWid - DensityUtil.dip2px(context, 6);
                iv_chair.setRotation(0);
            }
        } else if (groupnum == 3) {
            if (i == 1) {
                x = wid - DensityUtil.dip2px(context, 85);
                y = (wid - itemChairWid) - chairwid / 3;
                iv_chair.setRotation(300);
            } else if (i == 2) {
                x = (wid / 2 - itemChairWid / 2) - chairwid / 3 - itemChairWid + DensityUtil.dip2px(context, 6);
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
                y = wid - itemChairWid - DensityUtil.dip2px(context, 6);
                iv_chair.setRotation(0);
            } else if (i == 3) {
                x = 0 + DensityUtil.dip2px(context, 3);
                y = wid / 2 - itemChairWid / 2;
                iv_chair.setRotation(90);
            }
        } else if (groupnum == 5) {//每个五角星的角度为36°
            if (i == 1) {
                x = wid - itemChairWid - DensityUtil.dip2px(context, 10);
                y = dis + chairwid / 3 - itemChairWid + itemChairWid / 3;
                iv_chair.setRotation(252);
            } else if (i == 2) {
                x = (wid - itemChairWid) - chairwid / 3 + itemChairWid / 3;
                y = wid - itemChairWid - itemChairWid * 2 / 3;
                iv_chair.setRotation(324);
            } else if (i == 3) {
                x = (wid / 2 - itemChairWid / 2) - chairwid * 1 / 3 - DensityUtil.dip2px(context, 12);
                y = wid - itemChairWid - itemChairWid * 2 / 3;
                iv_chair.setRotation(36);
            } else if (i == 4) {
                x = itemChairWid / 3 - DensityUtil.dip2px(context, 10);
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

