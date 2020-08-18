package com.longcheng.lifecareplan.modular.mine.doctor.aiyou.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.mine.bill.adapter.SelectsAdapter;
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.activity.HealTrackActivity;
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.bean.HealTeackAfterBean;
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.bean.HealTeackItemBean;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;

import java.util.ArrayList;
import java.util.List;


public class HealPicAdapter extends BaseAdapterHelper<HealTeackItemBean> {
    ViewHolder mHolder = null;
    Activity context;
    int identityFlag;
    boolean aiyou_add_status;
    boolean doctor_edit_status;
    Handler mHandler;

    int viewVISIBLE = View.GONE;

    public HealPicAdapter(Activity context, List<HealTeackItemBean> list, int identityFlag,
                          boolean aiyou_add_status, boolean doctor_edit_status, Handler mHandler) {
        super(context, list);
        this.context = context;
        this.identityFlag = identityFlag;
        this.aiyou_add_status = aiyou_add_status;
        this.doctor_edit_status = doctor_edit_status;
        this.mHandler = mHandler;
        for (HealTeackItemBean m : list) {
            if (identityFlag == 2 && doctor_edit_status &&
                    !TextUtils.isEmpty(m.getPic_url()) && m.getConfirm_status() == 0) {
                viewVISIBLE = View.INVISIBLE;
                break;
            }
        }
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<HealTeackItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.aiyou_healtrack_top, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        HealTeackItemBean mOrderItemBean = list.get(position);
        int index = mOrderItemBean.getIndex();
        if (index == 0) {
            mHolder.item_tv_title.setText("喝太赫兹水");
        } else if (index == 1) {
            mHolder.item_tv_title.setText("节气水果汁");
        } else if (index == 2) {
            mHolder.item_tv_title.setText("24节气餐");
        } else if (index == 3) {
            mHolder.item_tv_title.setText("早");
        } else if (index == 4) {
            mHolder.item_tv_title.setText("中");
        } else if (index == 5) {
            mHolder.item_tv_title.setText("晚");
        }

        String Pic_url = mOrderItemBean.getPic_url();
        if (!TextUtils.isEmpty(Pic_url)) {
            mHolder.item_tv_look.setVisibility(View.VISIBLE);
            GlideDownLoadImage.getInstance().loadCircleImageLive(Pic_url, R.mipmap.my_icon_shangchuan, mHolder.item_iv_look, 0);
            mHolder.item_tv_look.setTag(mOrderItemBean.getPic_url());
            mHolder.item_tv_look.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLookPicDialog((String) v.getTag());
                }
            });
        } else {
            mHolder.item_tv_look.setVisibility(View.GONE);
        }
        mHolder.item_tv_send.setVisibility(View.GONE);
        mHolder.item_tv_notijiao.setVisibility(View.GONE);
        mHolder.item_layout_yes.setVisibility(View.GONE);
        mHolder.item_layout_no.setVisibility(viewVISIBLE);
        if (identityFlag == 1) {//爱友
            showAiYou(mOrderItemBean);
        } else if (identityFlag == 2) {//坐堂医
            showDoctor(mOrderItemBean);
        } else {
            showVolouteer(mOrderItemBean);
        }
        return convertView;
    }

    private void showAiYou(HealTeackItemBean mOrderItemBean) {
        String Pic_url = mOrderItemBean.getPic_url();
        mHolder.item_tv_send.setVisibility(View.VISIBLE);
        if (aiyou_add_status) {
            mHolder.item_tv_send.setBackgroundResource(R.drawable.corners_bg_login);
            ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_tv_send, R.color.red);
            if (!TextUtils.isEmpty(Pic_url)) {
                boolean notsendUrlStatus = mOrderItemBean.isNotsendUrlStatus();//本地是否已发送 默认有图片已发送
                if (!notsendUrlStatus) {
                    mHolder.item_tv_send.setText("已发送");
                } else {//已选择图片，但url 未上传，可点击上传
                    mHolder.item_tv_send.setText("发送");
                    mHolder.item_tv_send.setTag(mOrderItemBean);
                    mHolder.item_tv_send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HealTeackItemBean mOrderItemBean = (HealTeackItemBean) v.getTag();
                            Message message = Message.obtain();
                            message.what = HealTrackActivity.SEND;
                            message.obj = mOrderItemBean;
                            mHandler.sendMessage(message);
                        }
                    });

                }
            } else {
                mHolder.item_tv_send.setText("发送");
                mHolder.item_tv_send.setBackgroundResource(R.drawable.corners_bg_logingray);
                mHolder.relat_img.setTag(mOrderItemBean.getIndex());
                mHolder.relat_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = (int) v.getTag();
                        Message message = Message.obtain();
                        message.what = HealTrackActivity.UPDATEPIC;
                        message.obj = index;
                        mHandler.sendMessage(message);
                    }
                });
            }
        } else {
            mHolder.item_tv_send.setText("发送");
            mHolder.item_tv_send.setBackgroundResource(R.drawable.corners_bg_logingray);
        }
    }

    private void showDoctor(HealTeackItemBean mOrderItemBean) {
        int confirm_status = mOrderItemBean.getConfirm_status();//-1 否；1 是；0 未提交
        String Pic_url = mOrderItemBean.getPic_url();
        if (doctor_edit_status && !TextUtils.isEmpty(Pic_url)) {
            mHolder.item_layout_yes.setVisibility(View.VISIBLE);
            mHolder.item_layout_no.setVisibility(View.VISIBLE);
            mHolder.item_iv_yes.setBackgroundResource(R.mipmap.check_false);
            mHolder.item_iv_no.setBackgroundResource(R.mipmap.check_false);
            if (confirm_status == 1) {
                mHolder.item_iv_yes.setBackgroundResource(R.mipmap.check_true_red2);
            } else if (confirm_status == -1) {
                mHolder.item_iv_no.setBackgroundResource(R.mipmap.check_true_red2);
            }
            mHolder.item_layout_yes.setTag(mOrderItemBean);
            mHolder.item_layout_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HealTeackItemBean mOrderItemBean = (HealTeackItemBean) v.getTag();
                    mOrderItemBean.setConfirm_status(1);
                    notifyDataSetChanged();
                    mHandler.sendEmptyMessage(HealTrackActivity.setTiJiaoBtnStatus);
                }
            });
            mHolder.item_layout_no.setTag(mOrderItemBean);
            mHolder.item_layout_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HealTeackItemBean mOrderItemBean = (HealTeackItemBean) v.getTag();
                    mOrderItemBean.setConfirm_status(-1);
                    notifyDataSetChanged();
                    mHandler.sendEmptyMessage(HealTrackActivity.setTiJiaoBtnStatus);
                }
            });
        } else {
            if (confirm_status == 0) {
                mHolder.item_tv_notijiao.setVisibility(View.VISIBLE);
            } else if (confirm_status == 1) {
                mHolder.item_layout_yes.setVisibility(View.VISIBLE);
                mHolder.item_iv_yes.setBackgroundResource(R.mipmap.check_true_red2);
            } else if (confirm_status == -1) {
                mHolder.item_layout_no.setVisibility(View.VISIBLE);
                mHolder.item_iv_no.setBackgroundResource(R.mipmap.check_true_red2);
            }
        }
    }

    private void showVolouteer(HealTeackItemBean mOrderItemBean) {
        int confirm_status = mOrderItemBean.getConfirm_status();//-1 否；1 是；0 未提交
        if (confirm_status == 0) {
            mHolder.item_tv_notijiao.setVisibility(View.VISIBLE);
        } else if (confirm_status == 1) {
            mHolder.item_layout_yes.setVisibility(View.VISIBLE);
            mHolder.item_iv_yes.setBackgroundResource(R.mipmap.check_true_red2);
        } else if (confirm_status == -1) {
            mHolder.item_layout_no.setVisibility(View.VISIBLE);
            mHolder.item_iv_no.setBackgroundResource(R.mipmap.check_true_red2);
        }
    }

    MyDialog selectDialog;
    ImageView iv_img;

    private void showLookPicDialog(String Pic_url) {
        if (selectDialog == null) {
            selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_bigpic);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectDialog.show();
            WindowManager m = context.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            p.height = d.getHeight();
            selectDialog.getWindow().setDimAmount(0.8f);
            selectDialog.getWindow().setAttributes(p); //设置生效
            iv_img = selectDialog.findViewById(R.id.iv_img);
            iv_img.setLayoutParams(new LinearLayout.LayoutParams(p.width, p.height));
            iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
        } else {
            selectDialog.show();
        }
        GlideDownLoadImage.getInstance().loadCircleImageLive(Pic_url, R.mipmap.my_icon_shangchuan, iv_img, 0);

    }

    private class ViewHolder {

        private RelativeLayout relat_img;
        private ImageView item_iv_look;
        private TextView item_tv_look;
        private TextView item_tv_title;
        private TextView item_tv_send;
        private TextView item_tv_notijiao;
        private LinearLayout item_layout_yes;
        private ImageView item_iv_yes;
        private LinearLayout item_layout_no;
        private ImageView item_iv_no;

        public ViewHolder(View view) {
            relat_img = view.findViewById(R.id.relat_img);
            item_iv_look = view.findViewById(R.id.item_iv_look);
            item_tv_look = view.findViewById(R.id.item_tv_look);
            item_tv_title = view.findViewById(R.id.item_tv_title);
            item_tv_send = view.findViewById(R.id.item_tv_send);

            item_tv_notijiao = view.findViewById(R.id.item_tv_notijiao);
            item_layout_yes = view.findViewById(R.id.item_layout_yes);
            item_iv_yes = view.findViewById(R.id.item_iv_yes);
            item_layout_no = view.findViewById(R.id.item_layout_no);
            item_iv_no = view.findViewById(R.id.item_iv_no);
        }
    }
}
