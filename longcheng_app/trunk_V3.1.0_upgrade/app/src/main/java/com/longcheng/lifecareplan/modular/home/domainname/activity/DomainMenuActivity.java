package com.longcheng.lifecareplan.modular.home.domainname.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.home.domainname.adapter.ApplyAdapter;
import com.longcheng.lifecareplan.modular.home.domainname.bean.domainAfterBean;
import com.longcheng.lifecareplan.modular.home.domainname.bean.domainDataBean;
import com.longcheng.lifecareplan.modular.home.domainname.bean.domainItemBean;
import com.longcheng.lifecareplan.modular.home.domainname.mycomm.activity.CommListActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.GetEnergyListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 域名首页
 */
public class DomainMenuActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.iv_jieqi)
    ImageView ivJieqi;
    @BindView(R.id.viewflipper)
    ViewFlipper vp;
    @BindView(R.id.gv_apply)
    MyGridView gvApply;
    @BindView(R.id.tv_look)
    TextView tvLook;
    @BindView(R.id.tv_belongdadui)
    TextView tvBelongdadui;
    @BindView(R.id.tv_belongaddress)
    TextView tvBelongaddress;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.layout_eara)
    LinearLayout layout_eara;


    public static final int level_commue=4;//公社
    int is_bind;
    String address = "";

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_look:
                intent = new Intent(mActivity, CommListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("position", 0);
                startActivity(intent);
                break;
            case R.id.layout_eara:
                intent = new Intent(mActivity, BingAddressActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("is_bind", is_bind);
                intent.putExtra("address", address);
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
        return R.layout.domain_menu;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvLook.setOnClickListener(this);
        layout_eara.setOnClickListener(this);
        int hei= (int) ((DensityUtil.screenWith(mContext)-DensityUtil.dip2px(mContext,20))*0.377);
        ivJieqi.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,hei));
        ivJieqi.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("我的健康公社");
        ArrayList<String> list = new ArrayList();
        list.add("抢注公社");
        list.add("抢注大队");
        ApplyAdapter applyAdapter = new ApplyAdapter(mContext, list);
        gvApply.setAdapter(applyAdapter);
        gvApply.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, SearchCommueOrDaduiActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getInfo();
    }

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    public void getInfo() {
        showDialog();
        Observable<domainDataBean> observable = Api.getInstance().service.getYUMingInfo(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<domainDataBean>() {
                    @Override
                    public void accept(domainDataBean responseBean) throws Exception {
                        dismissDialog();
                        backSuccess(responseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        Error();
                    }
                });

    }


    public void backSuccess(domainDataBean responseBean) {
        String status_ = responseBean.getStatus();
        if (status_.equals("200")) {
            domainAfterBean domainBean = responseBean.getData();
            is_bind = domainBean.getIs_bind();
            if (is_bind == 1) {
                layout_eara.setVisibility(View.VISIBLE);
                domainItemBean commune = domainBean.getCommune();
                address = commune.getAddress();
                tvBelongdadui.setText(commune.getCommune_name() + " - " + commune.getBrigade_name());
                tvBelongaddress.setText(address);
            } else {
                layout_eara.setVisibility(View.GONE);
                showJSDialog();
            }
            fillView(domainBean.getNotice());
            String url = domainBean.getBanner().getUrl();
            if (!TextUtils.isEmpty(url))
                GlideDownLoadImage.getInstance().loadCircleImageRole(mContext, url, ivJieqi, 6);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    private void fillView(List<String> topmsg) {
        Log.e("fillView", "" + topmsg.toString());
        vp.removeAllViews();
        for (int i = 0; i < topmsg.size(); i++) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.text_item4, null);
            TextView textView = view.findViewById(R.id.tv_cont);
            textView.setText(topmsg.get(i));
            vp.addView(view);
        }
        //是否自动开始滚动
        vp.setAutoStart(true);
        //滚动时间
        vp.setFlipInterval(2600);
        //开始滚动
        vp.startFlipping();
        //出入动画
        vp.setOutAnimation(mActivity, R.anim.push_bottom_outvp);
        vp.setInAnimation(mActivity, R.anim.push_bottom_in);
    }

    public void Error() {
        ToastUtils.showToast(R.string.net_tishi);
    }

    MyDialog JSDialog;

    /**
     * 设置出生地
     */
    private void showJSDialog() {
        if (JSDialog == null) {
            JSDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_domain_address);// 创建Dialog并设置样式主题
            JSDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = JSDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            JSDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = JSDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5;
            JSDialog.getWindow().setAttributes(p); //设置生效
            ImageView iv_top = JSDialog.findViewById(R.id.iv_top);
            iv_top.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (p.width * 0.586)));
            TextView tv_bangding = JSDialog.findViewById(R.id.tv_bangding);
            tv_bangding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSDialog.dismiss();/**/
                    Intent intent = new Intent(mActivity, BingAddressActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("is_bind", is_bind);
                    intent.putExtra("address", address);
                    startActivity(intent);
                }
            });
            JSDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    doFinish();
                    return true;
                }
            });
        } else {
            JSDialog.show();
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
