package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao;

import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.fupackage.FuListBean;
import com.longcheng.lifecareplan.bean.fupackage.MyFuListBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao.adapter.MineFubaoAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.adapter.MyFuListAdapter;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的福包
 */
public class MineFubaoActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_receive)
    TextView tvReceive;
    @BindView(R.id.tv_sended)
    TextView tvSended;
    @BindView(R.id.iv_thumb)
    ImageView ivThumb;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.help_listview)
    PullToRefreshListView helpListview;

    private List<FuListBean> fuListBeans = null;
    MineFubaoAdapter myFuListAdapter = null;

    private int type = 1;//type =1 收到    2 发出，
    private int page;
    private int pageSize = 20;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_receive:
                type = 1;
                SwitchTopView();
                break;
            case R.id.tv_sended:
                type = 2;
                SwitchTopView();
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fubao_minefubao;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("我的福包");
        pagetopLayoutLeft.setOnClickListener(this);
        tvReceive.setOnClickListener(this);
        tvSended.setOnClickListener(this);
        helpListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                getList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                getList(page + 1);
            }
        });
    }

    @Override
    public void initDataAfter() {
        SwitchTopView();
    }

    private void SwitchTopView() {
        tvReceive.setTextColor(getResources().getColor(R.color.color_C63F00));
        tvReceive.setBackgroundColor(getResources().getColor(R.color.transparent));
        int top = DensityUtil.dip2px(mContext, 4);
        int left = DensityUtil.dip2px(mContext, 15);
        tvReceive.setPadding(left, top, top * 2, top);
        tvSended.setPadding(top * 2, top, left, top);
        tvSended.setTextColor(getResources().getColor(R.color.color_C63F00));
        tvSended.setBackgroundColor(getResources().getColor(R.color.transparent));
        if (type == 1) {
            tvReceive.setPadding(left, top, left, top);
            tvReceive.setTextColor(getResources().getColor(R.color.white));
            tvReceive.setBackgroundResource(R.drawable.corners_bg_redshen);
        } else {
            tvSended.setPadding(left, top, left, top);
            tvSended.setTextColor(getResources().getColor(R.color.white));
            tvSended.setBackgroundResource(R.drawable.corners_bg_redshen);
        }
        getList(1);
    }

    private void getList(int page_) {
        getFuList(type, page_, pageSize);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    boolean refreshStatus = false;

    public void showDialog() {
        if (!refreshStatus)
            LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        refreshStatus = false;
        LoadingDialogAnim.dismiss(mContext);
    }


    public void getFuList(int type,
                          int page,
                          int page_size) {
        showDialog();
        Observable<MyFuListBean> observable = Api.getInstance().service.getFuPackageList(UserUtils.getUserId(mContext), type,
                page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<MyFuListBean>() {
                    @Override
                    public void accept(MyFuListBean responseBean) throws Exception {
                        dismissDialog();
                        ListSuccess(responseBean, page);

                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });

    }

    public void ListSuccess(MyFuListBean responseBean, int pageback) {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            MyFuListBean.MyFuListAfterBean data = responseBean.getData();
            String avatar = data.getAvatar();
            String count = data.getCount();
            GlideDownLoadImage.getInstance().loadCircleImage(avatar, ivThumb);
            if (type == 1) {
                tvNum.setText(Html.fromHtml("共收到" + CommonUtil.getHtmlContentBig("#ffffff", count) + "个福包"));
            } else {
                tvNum.setText(Html.fromHtml("共发出" + CommonUtil.getHtmlContentBig("#ffffff", count) + "个福包"));
            }
            fuListBeans = data.getList();
            int size = fuListBeans == null ? 0 : fuListBeans.size();
            Log.v("ListSuccess", "size:" + size + "page" + pageback);
            if (pageback == 1) {
                myFuListAdapter = null;
            }
            if (myFuListAdapter == null) {
                myFuListAdapter = new MineFubaoAdapter(mActivity, fuListBeans, type);
                helpListview.setAdapter(myFuListAdapter);
            } else {
                myFuListAdapter.reloadListView(fuListBeans, false);
            }
            page = pageback;
            checkLoadOver(size);
        }
    }

    public void ListError() {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.listViewDownConfig(helpListview);
            if (size > 0 || (page > 1 && size >= 0)) {
            }
        } else {
            ScrowUtil.listViewConfigAll(helpListview);
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
