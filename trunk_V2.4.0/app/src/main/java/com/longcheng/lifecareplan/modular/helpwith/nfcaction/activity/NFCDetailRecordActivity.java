package com.longcheng.lifecareplan.modular.helpwith.nfcaction.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.adapter.NFCDetailRecordAdapter;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailListDataBean;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * nfc行动-记录
 */
public class NFCDetailRecordActivity extends BaseActivityMVP<NFCDetailContract.View, NFCDetailPresenterImp<NFCDetailContract.View>> implements NFCDetailContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.help_listview)
    ListView helpListview;

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
        return R.layout.nfc_detail_record;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("溯源记录");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        String order_id = intent.getStringExtra("order_id");
        String nfc_sn = intent.getStringExtra("nfc_sn");
        mPresent.getNFCRecord(order_id, nfc_sn);
    }

    @Override
    protected NFCDetailPresenterImp<NFCDetailContract.View> createPresent() {
        return new NFCDetailPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mActivity);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mActivity);
    }

    @Override
    public void DetailSuccess(NFCDetailDataBean responseBean) {

    }

    @Override
    public void DetailListSuccess(NFCDetailDataBean responseBean, int backpage) {

    }

    @Override
    public void PayHelpSuccess(ResponseBean responseBean) {

    }

    @Override
    public void DetailRecordSuccess(NFCDetailListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ArrayList<NFCDetailItemBean> list = responseBean.getData();
            if (list != null && list.size() > 0) {
                NFCDetailRecordAdapter mNAdapter = new NFCDetailRecordAdapter(mContext, list);
                helpListview.setAdapter(mNAdapter);
            }
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
