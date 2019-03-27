package com.longcheng.volunteer.modular.mine.signIn.activity;

import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseActivityMVP;
import com.longcheng.volunteer.modular.mine.signIn.bean.SignInAfterBean;
import com.longcheng.volunteer.modular.mine.signIn.bean.SignInDataBean;
import com.longcheng.volunteer.utils.DensityUtil;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.sharedpreferenceutils.UserUtils;

import butterknife.BindView;

/**
 * 签到
 */
public class SignInActivity extends BaseActivityMVP<SignInContract.View, SignInPresenterImp<SignInContract.View>> implements SignInContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_jieqi)
    TextView tvJieqi;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_open)
    ImageView iv_open;
    @BindView(R.id.tv_skb)
    TextView tv_skb;
    @BindView(R.id.iv_closed)
    ImageView ivClosed;
    @BindView(R.id.fl_rede)
    FrameLayout flRede;
    private String user_id, rewardSkb;
    /**
     * 是否签到 0：未签到 1：已签到
     */
    private int isSign;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.iv_closed:
                if (isSign == 0) {
                    mPresent.signIn(user_id);
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
        return R.layout.dialog_signin;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("节气签到");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        ivClosed.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        user_id = UserUtils.getUserId(mContext);
        mPresent.getShowInfo(user_id);
        int width = DensityUtil.screenWith(mContext);
        int height = (int) (width * 1.074);
        iv_open.setLayoutParams(new FrameLayout.LayoutParams(width, height));

        int heights = (int) (width * 1.02);
        ivClosed.setLayoutParams(new FrameLayout.LayoutParams(width, heights));
    }

    @Override
    protected SignInPresenterImp<SignInContract.View> createPresent() {
        return new SignInPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }


    @Override
    public void ListSuccess(SignInDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            SignInAfterBean mSignInAfterBean = responseBean.getData();
            if (mSignInAfterBean != null) {
                tvJieqi.setText(mSignInAfterBean.getJieqi() + "节气");
                tvTime.setText(mSignInAfterBean.getStartDay() + "至" + mSignInAfterBean.getEndDay());
                isSign = mSignInAfterBean.getIsSign();
                rewardSkb = mSignInAfterBean.getRewardSkb();
                if (isSign == 0) {//是否签到 0：未签到 1：已签到
                    ivClosed.setVisibility(View.VISIBLE);
                    flRede.setVisibility(View.INVISIBLE);
                } else {
                    ivClosed.setVisibility(View.INVISIBLE);
                    flRede.setVisibility(View.VISIBLE);
                    tv_skb.setText(rewardSkb);
                }
            }
        }
    }

    @Override
    public void signInSuccess(SignInDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ivClosed.setVisibility(View.INVISIBLE);
            flRede.setVisibility(View.VISIBLE);
            tv_skb.setText(rewardSkb);
        }
    }

    @Override
    public void ListError() {

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
