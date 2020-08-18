package com.longcheng.lifecareplan.modular.home.domainname.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.home.domainname.adapter.ApplyAdapter;
import com.longcheng.lifecareplan.modular.home.domainname.bean.CommuDetailDataBean;
import com.longcheng.lifecareplan.modular.home.domainname.bean.domainAfterBean;
import com.longcheng.lifecareplan.modular.home.domainname.bean.domainDataBean;
import com.longcheng.lifecareplan.modular.home.domainname.bean.domainItemBean;
import com.longcheng.lifecareplan.modular.home.domainname.mycomm.activity.CommListActivity;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 公社大队详情
 */
public class CommuDetailActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.tv_addresstitle)
    TextView tvAddresstitle;
    @BindView(R.id.tv_commune)
    TextView tvCommune;
    @BindView(R.id.tv_infotitle)
    TextView tvInfotitle;
    @BindView(R.id.tv_cont1)
    TextView tvCont1;
    @BindView(R.id.tv_cont2)
    TextView tvCont2;
    @BindView(R.id.tv_cont3)
    TextView tvCont3;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_commname)
    TextView tv_commname;
    @BindView(R.id.tv_btn)
    TextView tvBtn;
    MapView map_view;


    String commune_id = "", show_content;
    int level;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_btn:
                if (is_own == 1) {
                    if (is_sold != 1) {
                    }
                } else {
                    if (is_sold != 1) {
                        showPayDialog();
                    }
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
        return R.layout.domain_commu_detail;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvBtn.setOnClickListener(this);
        initMapView();
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("我的健康公社");

        commune_id = getIntent().getStringExtra("commune_id");
        level = getIntent().getIntExtra("level", DomainMenuActivity.level_commue);
        if (level == DomainMenuActivity.level_commue) {
            tvAddresstitle.setText("公社地址");
            tvInfotitle.setText("公社信息");
            tvCont1.setText("大队数：0");
            tvCont2.setText("公社CHO人数：0");
            tvCont3.setVisibility(View.GONE);
            tvBtn.setText("抢注公社");
        } else {
            tvAddresstitle.setText("大队地址");
            tvInfotitle.setText("大队信息");
            tvCont1.setText("大队长：无");
            tvCont1.setVisibility(View.GONE);
            tvCont2.setText("大队CHO人数：0");
            tvCont3.setText("所属公社：");
            tvCont3.setVisibility(View.VISIBLE);
            tvBtn.setText("抢注大队");
        }
    }


    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    public void getDetailInfo() {
        showDialog();
        Observable<CommuDetailDataBean> observable = Api.getInstance().service.getCommuDetail(UserUtils.getUserId(mContext), commune_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CommuDetailDataBean>() {
                    @Override
                    public void accept(CommuDetailDataBean responseBean) throws Exception {
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

    int is_sold;
    int is_own;

    public void backSuccess(CommuDetailDataBean responseBean) {
        String status_ = responseBean.getStatus();
        if (status_.equals("200")) {
            CommuDetailDataBean.DeItemInfo domainBean = responseBean.getData();
            if (level == DomainMenuActivity.level_commue) {
                tvCont1.setText("大队数：" + domainBean.getBrigade_count());
                tvCont2.setText("公社CHO人数：" + domainBean.getCho_count());
                tv_commname.setVisibility(View.VISIBLE);
            } else {
                tvCont1.setText("大队长：" + domainBean.getBrigade_user_name());
                tvCont2.setText("大队CHO人数：" + domainBean.getCho_count());
                tvCont3.setText("所属公社：" + domainBean.getCommune_name());
                tv_commname.setVisibility(View.GONE);
            }
            tvBtn.setVisibility(View.VISIBLE);
            tvCommune.setText(domainBean.getAddress());
            show_content = domainBean.getShort_name();
            pageTopTvName.setText(show_content);
            is_sold = domainBean.getIs_sold();
            is_own = domainBean.getIs_own();//是否是自己的；1：是
            if (is_own == 1) {
                if (is_sold == 1) {
                    tvBtn.setText("已持有");
                    tvBtn.setBackgroundColor(getResources().getColor(R.color.color_c8c8c8));
//                    if (level == DomainMenuActivity.level_commue) {
//                        tvBtn.setText("敬售公社");
//                        tvBtn.setBackgroundColor(getResources().getColor(R.color.color_c8c8c8));
//                    } else {
//                        tvBtn.setText("敬售大队");
//                        tvBtn.setBackgroundColor(getResources().getColor(R.color.color_c8c8c8));
//                    }
                }
                tv_commname.setText("公社主任：" + domainBean.getCommune_user_name());
                tv_date.setVisibility(View.VISIBLE);
                tvCont1.setVisibility(View.VISIBLE);
                tv_date.setText("有效期至：" + DatesUtils.getInstance().getTimeStampToDate(domainBean.getEnd_time(), "yyyy年MM月dd日"));
            } else {
                if (is_sold == 1) {
                    tvBtn.setText("已抢注");
                    tvBtn.setBackgroundColor(getResources().getColor(R.color.color_c8c8c8));
                }
                if (level == DomainMenuActivity.level_commue) {
                    tvCont1.setVisibility(View.VISIBLE);
                } else {
                    tvCont1.setVisibility(View.GONE);
                }
            }
            moveToCenter(domainBean.getLat(), domainBean.getLng());
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    CommuDetailDataBean.DeItemInfo PriceData;

    private void getCommuDetailPrice() {
        Observable<CommuDetailDataBean> observable;
        if (level == DomainMenuActivity.level_commue) {
            observable = Api.getInstance().service.getBuyCommue(UserUtils.getUserId(mContext), commune_id, ExampleApplication.token);
        } else {
            observable = Api.getInstance().service.getBuyBrigade(UserUtils.getUserId(mContext), commune_id, ExampleApplication.token);
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CommuDetailDataBean>() {
                    @Override
                    public void accept(CommuDetailDataBean responseBean) throws Exception {
                        dismissDialog();
                        String status_ = responseBean.getStatus();
                        if (status_.equals("200")) {
                            PriceData = responseBean.getData();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                    }
                });
    }

    private void rushCommune() {
        Observable<PayWXDataBean> observable = Api.getInstance().service.rushCommune(
                UserUtils.getUserId(mContext), commune_id, selectprice, payment_channel, 2, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        dismissDialog();
                        String status_ = responseBean.getStatus();
                        ToastUtils.showToast(responseBean.getMsg());
                        if (status_.equals("200")) {
                            if (payment_channel == 3) {
                                PayWXAfterBean payWeChatBean = responseBean.getData();
                                Log.e(TAG, payWeChatBean.toString());
                                PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
                            } else {
                                grabSuccess();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        Error();
                    }
                });
    }

    private void grabSuccess() {
        Intent intent = new Intent(mActivity, GrabSuccessActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("level", level);
        intent.putExtra("show_content", show_content);
        startActivity(intent);
    }

    public void Error() {
        ToastUtils.showToast(R.string.net_tishi);
    }

    MyDialog JSDialog;
    TextView tv_showprice;

    RelativeLayout relat_skb;
    TextView tv_skbtitle;
    TextView tv_skb;
    ImageView iv_skbselect;

    RelativeLayout relat_fqb;
    TextView tv_fqbtitle;
    TextView tv_fqb;
    ImageView iv_fqbselect;

    RelativeLayout relat_wx;
    TextView tv_wxtitle;
    ImageView iv_wxselect;

    int payment_channel = 1;//支付类型 1：寿康宝， 2：福祺宝， 3：微信
    String selectprice;

    /**
     * 设置出生地
     */
    private void showPayDialog() {
        if (JSDialog == null) {
            JSDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_domain_commue_pay);// 创建Dialog并设置样式主题
            JSDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = JSDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            JSDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = JSDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth();
            JSDialog.getWindow().setAttributes(p); //设置生效

            LinearLayout layout_cancel = JSDialog.findViewById(R.id.layout_cancel);
            tv_showprice = JSDialog.findViewById(R.id.tv_showprice);

            relat_skb = JSDialog.findViewById(R.id.relat_skb);
            tv_skbtitle = JSDialog.findViewById(R.id.tv_skbtitle);
            tv_skb = JSDialog.findViewById(R.id.tv_skb);
            iv_skbselect = JSDialog.findViewById(R.id.iv_skbselect);

            relat_fqb = JSDialog.findViewById(R.id.relat_fqb);
            tv_fqbtitle = JSDialog.findViewById(R.id.tv_fqbtitle);
            tv_fqb = JSDialog.findViewById(R.id.tv_fqb);
            iv_fqbselect = JSDialog.findViewById(R.id.iv_fqbselect);

            relat_wx = JSDialog.findViewById(R.id.relat_wx);
            tv_wxtitle = JSDialog.findViewById(R.id.tv_wxtitle);
            iv_wxselect = JSDialog.findViewById(R.id.iv_wxselect);

            TextView btn_sure = JSDialog.findViewById(R.id.btn_sure);
            layout_cancel.setOnClickListener(dialogClick);
            btn_sure.setOnClickListener(dialogClick);
            relat_skb.setOnClickListener(dialogClick);
            relat_fqb.setOnClickListener(dialogClick);
            relat_wx.setOnClickListener(dialogClick);
        } else {
            JSDialog.show();
        }
        selectPayTypeView();
    }

    View.OnClickListener dialogClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.layout_cancel:
                    JSDialog.dismiss();
                    break;
                case R.id.relat_skb:
                    payment_channel = 1;
                    selectPayTypeView();
                    break;
                case R.id.relat_fqb:
                    payment_channel = 2;
                    selectPayTypeView();
                    break;
                case R.id.relat_wx:
                    payment_channel = 3;
                    selectPayTypeView();
                    break;
                case R.id.btn_sure://立即互祝
                    JSDialog.dismiss();
                    rushCommune();
                    break;
                default:

                    break;
            }
        }
    };

    private void selectPayTypeView() {
        showSelect();
        relat_skb.setBackgroundResource(R.drawable.corners_bg_black);
        tv_skbtitle.setTextColor(getResources().getColor(R.color.follow_color));
        tv_skb.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        iv_skbselect.setVisibility(View.GONE);

        relat_fqb.setBackgroundResource(R.drawable.corners_bg_black);
        tv_fqbtitle.setTextColor(getResources().getColor(R.color.follow_color));
        tv_fqb.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        iv_fqbselect.setVisibility(View.GONE);

        relat_wx.setBackgroundResource(R.drawable.corners_bg_black);
        tv_wxtitle.setTextColor(getResources().getColor(R.color.follow_color));
        iv_wxselect.setVisibility(View.GONE);
        //设置默认

        if (payment_channel == 1) {
            relat_skb.setBackgroundResource(R.drawable.corners_bg_yellowbian);
            tv_skbtitle.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
            tv_skb.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
            iv_skbselect.setVisibility(View.VISIBLE);
            relat_skb.setPadding(0, 0, 0, 0);
        } else if (payment_channel == 2) {
            relat_fqb.setBackgroundResource(R.drawable.corners_bg_yellowbian);
            tv_fqbtitle.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
            tv_fqb.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
            iv_fqbselect.setVisibility(View.VISIBLE);
            relat_fqb.setPadding(0, 0, 0, 0);
        } else if (payment_channel == 3) {
            relat_wx.setBackgroundResource(R.drawable.corners_bg_yellowbian);
            tv_wxtitle.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
            iv_wxselect.setVisibility(View.VISIBLE);
            relat_wx.setPadding(0, 0, 0, 0);
        }
    }

    private void showSelect() {
        if (PriceData != null) {
            CommuDetailDataBean.DeItemInfo wechat = PriceData.getWechat();
            CommuDetailDataBean.DeItemInfo shoukangyuan = PriceData.getShoukangyuan();
            CommuDetailDataBean.DeItemInfo fuqibao = PriceData.getFuqibao();
            tv_skb.setText(shoukangyuan.getBalance());
            tv_fqb.setText(fuqibao.getBalance());
            if (payment_channel == 1) {
                selectprice = shoukangyuan.getPrice();
            } else if (payment_channel == 2) {
                selectprice = fuqibao.getPrice();
            } else if (payment_channel == 3) {
                selectprice = wechat.getPrice();
            }
            tv_showprice.setText(selectprice);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantManager.BroadcastReceiver_PAY_ACTION);
        filter.addAction(ConstantManager.BroadcastReceiver_KNP_ACTION);
        registerReceiver(mReceiver, filter);
    }


    /**
     * 微信支付回调广播
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", 100);
            if (errCode == WXPayEntryActivity.PAY_SUCCESS) {
                grabSuccess();
            } else if (errCode == WXPayEntryActivity.PAY_FAILE) {
                ToastUtils.showToast("支付失败");
            } else if (errCode == WXPayEntryActivity.PAY_CANCLE) {
                ToastUtils.showToast("支付取消");
            }
        }
    };

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
    }


    private AMap mBaiduMap;
    private BitmapDescriptor realtimeBitmap;
    private Marker overlay;

    /**
     * 初始化定位的SDK
     */
    private void initMapView() {
        map_view = (MapView) findViewById(R.id.map_view);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        map_view.onCreate(paramBundle);
        mBaiduMap = map_view.getMap();
        /*
         * 百度地图 UI 控制器
         */
        UiSettings mUiSettings = mBaiduMap.getUiSettings();
        mUiSettings.setCompassEnabled(false);// 隐藏指南针
        mUiSettings.setRotateGesturesEnabled(false);// 阻止旋转
        mUiSettings.setZoomControlsEnabled(false);// 隐藏的缩放按钮
    }

    /**
     * 设置中心点的焦点
     * new LatLng(33.789925, 104.838326);
     *
     * @param latitude_me
     * @param longitude_me
     */
    private void moveToCenter(String latitude_me, String longitude_me) {
        if (TextUtils.isEmpty(latitude_me) && TextUtils.isEmpty(longitude_me)) {
            return;
        }
        LatLng mypoint = new LatLng(Double.valueOf(longitude_me), Double.valueOf(latitude_me));
        Log.e("moveToCenter", "latitude_me= " + mypoint.latitude + "   longitude_me" + mypoint.longitude);
        // 自定义图标
        if (null == realtimeBitmap) {
            realtimeBitmap = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(),
                            R.mipmap.icon_myposition_map));
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mypoint);
        markerOptions.icon(realtimeBitmap);
        if (null != overlay) {
            overlay.remove();
        }
        overlay = mBaiduMap.addMarker(markerOptions);

        CameraUpdate u = CameraUpdateFactory.newLatLngZoom(mypoint, 12.0f);
        if (u != null && mBaiduMap != null) {
            mBaiduMap.moveCamera(u);// 以动画方式更新地图状态，动画耗时 300 ms
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        try {
            if (map_view != null) {
                map_view.onPause();
            }
            super.onPause();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onResume() {
        if (map_view != null) {
            map_view.onResume();
        }
        super.onResume();
        getDetailInfo();
        getCommuDetailPrice();
    }


    @Override
    protected void onDestroy() {
        try {
            // 关闭定位图层
            mBaiduMap.setMyLocationEnabled(false);
            mBaiduMap.clear();
            mBaiduMap = null;
            map_view.removeAllViews();
            map_view.onDestroy();
            map_view = null;
        } catch (Exception e) {
        }
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

}
