
package com.longcheng.lifecareplan.modular.bottommenu.float_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.push.jpush.message.EasyMessage;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * NonoFloatView:悬浮窗控件V2,普通的实现
 *
 * @author Nonolive-杜乾 Created on 2017/12/12 - 17:16.
 * E-mail:dusan.du@nonolive.com
 */
public class FloatView extends FrameLayout implements IFloatView {
    private FloatViewParams params = null;
    private FloatViewListener listener;
    private View floatView;
    private TextView tv_cont;
    private ImageView iv_thumb;
    private LinearLayout layout_cont;

    public FloatView(Context mContext) {
        super(mContext);
        init();
    }

    public FloatView(@NonNull Context mContext, @NonNull FloatViewParams params) {
        super(mContext);
        this.params = params;
        init();
    }

    private void init() {
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        floatView = inflater.inflate(R.layout.float_view_inner_layout, null);

        layout_cont = (LinearLayout) floatView.findViewById(R.id.layout_cont);
        tv_cont = (TextView) floatView.findViewById(R.id.tv_cont);
        iv_thumb = (ImageView) floatView.findViewById(R.id.iv_thumb);
        layout_cont.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(bless_grateful_push_queue_id);
                }
            }
        });
        addView(floatView);
        updateShowView();
    }

    String bless_grateful_push_queue_id;

    /**
     * 更新数据
     */
    public void updateShowView() {
        try {
            JSONObject jsonmessage = new JSONObject("" + EasyMessage.lastMessage);
            String sponsor_avatar = jsonmessage.optString("sponsor_avatar");
            String content = jsonmessage.optString("content");
            bless_grateful_push_queue_id = jsonmessage.optString("bless_grateful_push_queue_id");
            Log.d("OnMessageListener", "bless_grateful_push_queue_id=" + bless_grateful_push_queue_id + ",content=" + content);
            tv_cont.setText("" + content);
            GlideDownLoadImage.getInstance().loadCircleImage(sponsor_avatar, iv_thumb);
            Animation mHiddenAction = AnimationUtils.loadAnimation(getContext(), R.anim.push_bottom_in);
            tv_cont.startAnimation(mHiddenAction);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("onLayout", "left=" + left + ",top=" + top + ",right=" + right);
    }


    @Override
    public FloatViewParams getParams() {
        return params;
    }

    @Override
    public void setFloatViewListener(FloatViewListener listener) {
        this.listener = listener;
    }

}
