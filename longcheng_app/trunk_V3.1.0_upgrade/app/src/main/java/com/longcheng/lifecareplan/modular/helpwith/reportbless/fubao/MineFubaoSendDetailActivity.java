package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao;

import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.fupackage.FuListBean;
import com.longcheng.lifecareplan.bean.fupackage.MyFuListBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao.adapter.MineFubaoAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao.adapter.MineFubaoDetailAdapter;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的福包详情
 */
public class MineFubaoSendDetailActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.help_listview)
    ListView help_listview;


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
        return R.layout.fubao_minefubao_senddetail;
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
        String bless_bag_big_id = fuListBean.getBless_bag_big_id();
        pageTopTvName.setText("送出" + fuListBean.getTotal_number() + "个福包");
        getFuDetail(bless_bag_big_id);
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

    public void getFuDetail(String bless_bag_big_id) {
        showDialog();
        Observable<MyFuListBean> observable = Api.getInstance().service.getFuPackageDetail(UserUtils.getUserId(mContext), bless_bag_big_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<MyFuListBean>() {
                    @Override
                    public void accept(MyFuListBean responseBean) throws Exception {
                        dismissDialog();
                        MyFuListBean.MyFuListAfterBean data = responseBean.getData();
                        List<FuListBean> fuListBeans = data.getList();
                        MineFubaoDetailAdapter myFuListAdapter = new MineFubaoDetailAdapter(mActivity, fuListBeans);
                        help_listview.setAdapter(myFuListAdapter);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                    }
                });

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
