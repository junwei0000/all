package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.ContactListActivity;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.fulist.MyFuBaoList;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;

/**
 * 福包首页
 */
public class FuBaoMenuNewActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.layout_myfubao)
    LinearLayout layoutMyfubao;
    @BindView(R.id.fubao_topbg)
    LinearLayout fubaoTopbg;

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.pagetop_layout_left:
                doFinish();
                break;
//            case R.id.tv_sendletter:
//                intent = new Intent(mActivity, ContactListActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent);
//                break;
//            case R.id.tv_addpeople:
//                intent = new Intent(mActivity, AddSongFuActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent);
//                break;
            case R.id.layout_myfubao:
                intent = new Intent(mActivity, MyFuBaoList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fubao_main;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("福包");
        pagetopLayoutLeft.setOnClickListener(this);
//        tvSendletter.setOnClickListener(this);
//        tvAddpeople.setOnClickListener(this);
        layoutMyfubao.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        int sw = DensityUtil.screenWith(mContext);
        fubaoTopbg.setLayoutParams(new LinearLayout.LayoutParams(sw, (int) (sw * 0.382)));
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
