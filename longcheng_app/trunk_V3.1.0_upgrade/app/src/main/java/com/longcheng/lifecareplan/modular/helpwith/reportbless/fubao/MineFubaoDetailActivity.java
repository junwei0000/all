package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.bean.fupackage.FuListBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的福包详情
 */
public class MineFubaoDetailActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.iv_thumb)
    ImageView ivThumb;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_receivename)
    TextView tvReceivename;
    @BindView(R.id.item_tv_price)
    TextView itemTvPrice;
    @BindView(R.id.item_tv_process)
    TextView itemTvProcess;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fubao_minefubao_detail;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        FuListBean fuListBean = (FuListBean) getIntent().getExtras().getSerializable("fuListBean");
        int type = getIntent().getIntExtra("type", 1);

        String sponsor_user_name = fuListBean.getSponsor_user_name();//发送人
        String receive_user_name = fuListBean.getReceive_user_name();//接收人
        String sponsor_super_ability = fuListBean.getSponsor_super_ability();
        sponsor_super_ability = PriceUtils.getInstance().seePrice(sponsor_super_ability);
        String receive_super_ability = fuListBean.getReceive_super_ability();
        receive_super_ability = PriceUtils.getInstance().seePrice(receive_super_ability);
        int status = fuListBean.getStatus();//2 已接收
        if (type == 1) {
            pageTopTvName.setText("收到" + CommonUtil.setName3(sponsor_user_name) + "的福包");
            GlideDownLoadImage.getInstance().loadCircleImage(fuListBean.getReceive_user_avatar(), ivThumb);
            tvName.setText(CommonUtil.setName3(receive_user_name));
            tvReceivename.setText("收到" + CommonUtil.setName3(sponsor_user_name) + "的福包");

            String cont = CommonUtil.getHtmlContentBig("#E95D1B", "" + receive_super_ability) + " 超级生命能量";
            itemTvPrice.setText(Html.fromHtml(cont));
            itemTvProcess.setText(new StringBuffer().append(1).append("/").append(1).toString());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    public void ListSuccess(ReportDataBean responseBean, int backPage) {

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
