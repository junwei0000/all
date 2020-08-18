package com.longcheng.volunteer.modular.mine.absolutelyclear.activity;

import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseActivityMVP;
import com.longcheng.volunteer.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;

/**
 * 一目了然
 * Created by Burning on 2018/8/30.
 */

public class AbsolutelyclearAct extends BaseActivityMVP<AbsolutelyclearContract.View, AbsolutelyclearPresenterImp<AbsolutelyclearContract.View>> implements AbsolutelyclearContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView tvTitle;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tvorganizing)
    TextView tvorganizing;


    @Override
    public void onSuccess(String responseBean) {
        tvContent.setText(getResources().getString(R.string.ymlr, responseBean));
    }

    @Override
    public void onError(String code) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_absolutely_clear2;
    }

    @Override
    public void initDataBefore() {
        super.initDataBefore();
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
//        rel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                RelativeLayout.LayoutParams lp  = (RelativeLayout.LayoutParams)envelopeView.getLayoutParams();
//                int offset = DensityUtil.dip2px(mContext,10);
//                int hight = rel.getHeight();
//                lp.width = envelopeView.getWidth()-offset*2;
//                lp.height = hight-offset*2;
//                lp.leftMargin = offset;
//                lp.rightMargin = offset;
//                lp.topMargin = offset;
//                lp.bottomMargin = offset;
//                envelopeView.setLayoutParams(lp);
//                rel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                RelativeLayout.LayoutParams lpDividerRight  = (RelativeLayout.LayoutParams)rightDivider.getLayoutParams();
//                lpDividerRight.height = hight;
//                rightDivider.setLayoutParams(lpDividerRight);
//                RelativeLayout.LayoutParams lpDividerLeft  = (RelativeLayout.LayoutParams)leftDivider.getLayoutParams();
//                lpDividerLeft.height = hight;
//                leftDivider.setLayoutParams(lpDividerLeft);
//            }
//        });
        tvTitle.setText(R.string.absolutelyclear);
    }

    @Override
    public void initDataAfter() {
        getData();
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.pagetop_layout_left) {
            doFinish();
        }
    }

    @Override
    protected AbsolutelyclearPresenterImp<AbsolutelyclearContract.View> createPresent() {
        return new AbsolutelyclearPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    /**
     * 获取数据
     */
    private void getData() {
        mPresent.setData(0, 0);
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
