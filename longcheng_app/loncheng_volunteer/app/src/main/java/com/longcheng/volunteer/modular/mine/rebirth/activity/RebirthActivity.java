package com.longcheng.volunteer.modular.mine.rebirth.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseActivityMVP;
import com.longcheng.volunteer.bean.ResponseBean;
import com.longcheng.volunteer.modular.mine.rebirth.bean.RebirthAfterBean;
import com.longcheng.volunteer.modular.mine.rebirth.bean.RebirthDataBean;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.DensityUtil;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.myview.MyDialog;
import com.longcheng.volunteer.utils.sharedpreferenceutils.UserUtils;

import butterknife.BindView;

/**
 * 重生卡
 */
public class RebirthActivity extends BaseActivityMVP<RebirthContract.View, RebirthPresenterImp<RebirthContract.View>> implements RebirthContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.iv_thumb)
    ImageView ivThumb;


    private String user_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.iv_thumb:
                if (Rebirthclick) {
                    showMyDialog();
                } else {
                    ToastUtils.showToast("您已使用过重生卡，不可重复使用！");
                }
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.my_rebirth;
    }


    @Override
    public void initView(View view) {
        pageTopTvName.setText("重生卡");
        int width = DensityUtil.screenWith(mContext) - DensityUtil.dip2px(mContext, 20);
        int height = (int) (width * 0.63);
        ivThumb.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        ivThumb.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
        user_id = UserUtils.getUserId(mContext);
        mPresent.getRenascenceInfo(user_id);
    }


    @Override
    protected RebirthPresenterImp<RebirthContract.View> createPresent() {
        return new RebirthPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    boolean Rebirthclick = false;

    @Override
    public void getRebirthInfoSuccess(RebirthDataBean responseBean) {
        firstComIn = false;
        Rebirthclick = false;
        ivThumb.setBackgroundResource(R.mipmap.my_rebirth_grey);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            RebirthAfterBean mAfterBean = responseBean.getData();
            if (mAfterBean != null && !TextUtils.isEmpty(mAfterBean.getUser_id())) {
                //重生卡状态 0：未使用 1：已使用
                int status = mAfterBean.getStatus();
                if (status == 0) {
                    Rebirthclick = true;
                    ivThumb.setBackgroundResource(R.mipmap.my_rebirth_blue);
                }
            }
        }
    }

    @Override
    public void getCodeSuccess(ResponseBean responseBean) {

    }

    @Override
    public void RebirthSuccess(ResponseBean responseBean) {

    }

    @Override
    public void ListError() {
    }

    MyDialog mDialog;

    public void showMyDialog() {
        if (mDialog == null) {
            mDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_rebirthishi);// 创建Dialog并设置样式主题
            mDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 5 / 6; //宽度设置为屏幕
            p.height = d.getHeight() * 5 / 7;
            mDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_cancel = (LinearLayout) mDialog.findViewById(R.id.layout_cancel);
            TextView btn_change = (TextView) mDialog.findViewById(R.id.btn_change);
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
            btn_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GetRECodeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                }
            });
        } else {
            mDialog.show();
        }
    }

    boolean firstComIn = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (!firstComIn) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            mPresent.getRenascenceInfo(user_id);
        }
    }

    /**
     * 重写onkeydown 用于监听返回键
     */

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
    }
}
