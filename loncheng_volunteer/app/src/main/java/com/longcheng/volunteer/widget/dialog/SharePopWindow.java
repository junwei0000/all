package com.longcheng.volunteer.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.longcheng.volunteer.R;

/**
 * 作者：MarkShuai on
 * 时间：2018/2/5 16:13
 * 邮箱：mark_mingshuai@163.com
 * 意图：
 */

public class SharePopWindow extends PopupWindow {

    public SharePopWindow(Context context, View.OnClickListener onItemClickListener) {
        super(context);
        initView(context, onItemClickListener);
    }

    /**
     * @param
     * @Name 初始化View
     * @Data 2018/2/5 16:19
     * @Author :MarkShuai
     */
    private void initView(Context context, View.OnClickListener onItemClickListener) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.share_layout, null);
        RelativeLayout rlWeChat = view.findViewById(R.id.rl_wechat);
        RelativeLayout rlFriends = view.findViewById(R.id.rl_friends);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(v -> {
            dismiss();
            backgroundAlpha(context, 1f);
        });

        rlWeChat.setOnClickListener(onItemClickListener);
        rlFriends.setOnClickListener(onItemClickListener);

        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置PopupWindow可触摸
        this.setTouchable(true);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        backgroundAlpha(context, 0.5f);//0.0-1.0
        this.setOnDismissListener(() -> backgroundAlpha(context, 1f));
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Context context, float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) context).getWindow().setAttributes(lp);
    }

}
