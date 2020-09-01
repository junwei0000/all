package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.bean.fupackage.FuBaoDetailsListBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.adapter.FuBaoDetailsAdapter;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FuPacakageDetails extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_iv_left)
    ImageView pagetopIvLeft;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.lv_data)
    MyListView dateListview;
    @BindView(R.id.sv_myredbag)
    PullToRefreshScrollView pullView;


    private int page = 1;
    private int pagesize = 20;

    FuBaoDetailsAdapter fuBaoDetailsAdapter = null;
    private List<FuBaoDetailsListBean> dataBeanslist = null;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.pagetop_iv_left:
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
        return R.layout.fupacakage_details_layout;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        ScrowUtil.ScrollViewConfigAll(pullView);
        pagetopIvLeft.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);

        int hei = (int) (DensityUtil.screenHigh(mContext));
        pagetopLayoutLeft.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(hei * 0.2970)));

        pullView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getCornucopiaList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getCornucopiaList(page + 1);
            }
        });
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("送给我的福包");

        getCornucopiaList(1);

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


    public void getCornucopiaList(int page_) {

        dataBeanslist = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            FuBaoDetailsListBean fuBaoDetailsListBean = new FuBaoDetailsListBean();
            fuBaoDetailsListBean.setAllnum("5");
            fuBaoDetailsListBean.setSongnum("3");
            fuBaoDetailsListBean.setTypeline("260");
            dataBeanslist.add(fuBaoDetailsListBean);
        }
        int size = dataBeanslist == null ? 0 : dataBeanslist.size();
        if (page_ == 1) {
            fuBaoDetailsAdapter = null;
        }
        if (fuBaoDetailsAdapter == null) {
            fuBaoDetailsAdapter = new FuBaoDetailsAdapter(FuPacakageDetails.this, dataBeanslist);
            dateListview.setAdapter(fuBaoDetailsAdapter);
        } else {
            fuBaoDetailsAdapter.reloadListView(dataBeanslist, false);
        }
        if (size > 0 || (page > 1 && size >= 0)) {
            page = page_;
        } else {
            page = 1;
        }
        checkLoadOver(size);

//        Observable<FuBaoDetails> observable = Api.getInstance().service.receivedFupackageList(
//                UserUtils.getUserId(mContext), page_, pagesize, ExampleApplication.token);
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<FuBaoDetails>() {
//                    @Override
//                    public void accept(FuBaoDetails responseBean) throws Exception {
//                        ListUtils.getInstance().RefreshCompleteS(pullView);
//                        String status_ = responseBean.getStatus();
//                        if (status_.equals("400")) {
//                            ToastUtils.showToast(responseBean.getMsg());
//                        } else if (status_.equals("200")) {
//                            dataBeanslist = responseBean.getData();
//                            int size = dataBeanslist == null ? 0 : dataBeanslist.size();
//                            if (page_ == 1) {
//                                fuBaoDetailsAdapter = null;
//                            }
//                            if (fuBaoDetailsAdapter == null) {
//                                fuBaoDetailsAdapter = new FuBaoDetailsAdapter(FuPacakageDetails.this, dataBeanslist);
//                                dateListview.setAdapter(fuBaoDetailsAdapter);
//                            } else {
//                                fuBaoDetailsAdapter.reloadListView(dataBeanslist, false);
//                            }
//                            if (size > 0 || (page > 1 && size >= 0)) {
//                                page = page_;
//                            } else {
//                                page = 1;
//                            }
//                            checkLoadOver(size);
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        ToastUtils.showToast(R.string.net_tishi);
//                        ListUtils.getInstance().RefreshCompleteS(pullView);
//                    }
//                });
    }

//    private void DataSucess() {
//        userName.setText("Gerry");
//        packageSource.setText(new StringBuffer().append("送给").append("六三").append("的福包").toString());
//        hinitBack.setText(new StringBuffer().append("24小时未领取自动退回").toString());
//        GlideDownLoadImage.getInstance().loadCircleImage(mContext, R.mipmap.user_default_icon, ivHead);
//    }

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    private void checkLoadOver(int size) {
        if (size < pagesize) {
            ScrowUtil.ScrollViewConfigDismiss(pullView);
            if (size > 0 || (page > 1 && size >= 0)) {
            }
        } else {
            ScrowUtil.ScrollViewUpConfig(pullView);
        }
    }

}
