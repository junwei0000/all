package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.zudui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.exchange.malldetail.activity.MallDetailActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.GetEnergyListDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter.ZuDuiMoneyAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionActiviesAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionActiviesDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 手机定制活动
 */
public class ZYBZuActivitesActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.layout_bottom1)
    LinearLayout layoutBottom1;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.tv_create)
    TextView tvCreate;
    @BindView(R.id.tv_gave)
    TextView tv_gave;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.layout_cont)
    LinearLayout layout_cont;


    int is_activity_finish;//是否有报名资格 1 有报名资格
    int user_role;//是否有身份 1有（创业导师或坐堂医）
    int is_get_phone;//是否已领取手机  1已领取
    int is_enroll;//1 已报名
    int is_receive;//是否可以领取手机
    int is_activity_end;//活动是否已结束
    int room_id;
    String shop_goods_id;
    String invite_user_name = "";

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_gave:
                intent = new Intent(mActivity, GaveQualiActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.tv_create:
                if (is_get_phone == 0 && is_activity_end == 1) {
                    if (is_receive == 1) {
                        ToastUtils.showToast("请完成以上任务");
                    } else {
                        intent = new Intent(mActivity, MallDetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("shop_goods_id", "" + shop_goods_id);
                        startActivity(intent);
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
        return R.layout.pioneer_userzyb_phonectivies;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
    }

    @SuppressLint("NewApi")
    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvCreate.setOnClickListener(this);
        tv_gave.setOnClickListener(this);
        int wid = DensityUtil.screenWith(mContext);
        layoutTop.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (wid * 0.42)));
        layoutBottom1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (wid * 1.2482)));
    }


    @Override
    public void initDataAfter() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getActivityPageInfo();
    }

    public void getActivityPageInfo() {
        showDialog();
        Observable<PionActiviesDataBean> observable = Api.getInstance().service.getActivityPageInfo(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PionActiviesDataBean>() {
                    @Override
                    public void accept(PionActiviesDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            ListSuccess(responseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });

    }

    public void SignUpZFJ() {
        showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.SignUpZFJ(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        dismissDialog();
                        String status = responseBean.getStatus();
                        if (status.equals("200")) {
                            if (selectPayDialog != null && selectPayDialog.isShowing()) {
                                selectPayDialog.dismiss();
                            }
                            getActivityPageInfo();
                        }
                        ToastUtils.showToast(responseBean.getMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });

    }

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    public void ListSuccess(PionActiviesDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            PionActiviesAfterBean data = responseBean.getData();
            is_receive = data.getIs_receive();
            is_activity_end = data.getIs_activity_end();//活动是否结束： 1未结束， 0已结束
            is_enroll = data.getIs_enroll();//1 已报名
            is_activity_finish = data.getIs_activity_finish();
            user_role = data.getUser_role();
            is_get_phone = data.getIs_get_phone();
            invite_user_name = data.getInvite_user_name();
            room_id = data.getRoom_id();
            String activity_date = data.getActivity_date();
            tvTime.setText(activity_date);

            showQualifications();

            if (user_role == 1) {
                tv_gave.setVisibility(View.VISIBLE);
            } else {
                tv_gave.setVisibility(View.GONE);
            }
            if (is_activity_end == 0) {
                tvCreate.setText("活动已结束");
                tvCreate.setTextColor(getResources().getColor(R.color.white));
                tvCreate.setBackgroundColor(getResources().getColor(R.color.gray));
            } else {
                if (is_get_phone == 0) {
                    tvCreate.setText("点击马上获取");
                    tvCreate.setTextColor(getResources().getColor(R.color.color_eb3f00));
                    tvCreate.setBackgroundColor(getResources().getColor(R.color.color_ffd434));
                } else {
                    tvCreate.setText("已领取");
                    tvCreate.setTextColor(getResources().getColor(R.color.white));
                    tvCreate.setBackgroundColor(getResources().getColor(R.color.gray));
                }

            }
            showCont(data);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    /**
     * 是否有资格
     */
    private void showQualifications() {
        if (is_activity_finish == 1) {
            if (is_enroll == 0 && user_role == 0) {
                setPayDialog();
            }
        } else {
            setPayDialog();
        }
    }


    private void showCont(PionActiviesAfterBean data) {
        layout_cont.removeAllViews();
        ArrayList<PionActiviesAfterBean.PionActiviesItemBean> mList = new ArrayList<>();
        mList.add(data.getTask_one());
        mList.add(data.getTask_two());
        for (int i = 0; i < mList.size(); i++) {
            View convertView = LayoutInflater.from(mContext).inflate(R.layout.pion_phoneactivites_item, null);
            TextView item_tv_title = convertView.findViewById(R.id.item_tv_title);
            TextView item_tv_title2 = convertView.findViewById(R.id.item_tv_title2);
            ImageView item_iv_thumb = convertView.findViewById(R.id.item_iv_thumb);
            TextView item_tv_status = convertView.findViewById(R.id.item_tv_status);
            LinearLayout mycenter_relat_shenfen = convertView.findViewById(R.id.mycenter_relat_shenfen);
            layout_cont.addView(convertView);
            PionActiviesAfterBean.PionActiviesItemBean mInfo = mList.get(i);
            GlideDownLoadImage.getInstance().loadCircleImage(mInfo.getIcon(), item_iv_thumb);
            item_tv_title.setText(mInfo.getTitle());
            item_tv_title2.setText(mInfo.getSmall_title());

            mycenter_relat_shenfen.removeAllViews();
            ArrayList<PionActiviesAfterBean.PionActiviesItemBean> items_inner = mInfo.getItems_inner();
            if (items_inner != null && items_inner.size() > 0) {
                for (PionActiviesAfterBean.PionActiviesItemBean info : items_inner) {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.pion_phoneactivites_item_item, null);
                    TextView tv_title = view.findViewById(R.id.tv_title);
                    TextView tv_title2 = view.findViewById(R.id.tv_title2);

                    ArrayList<String> items = info.getItems();
                    tv_title.setText(Html.fromHtml(items.get(0)));
                    String cont1 = items.get(1);
                    if (!TextUtils.isEmpty(cont1) && cont1.length() > 3) {
                        tv_title2.setTextColor(mContext.getResources().getColor(R.color.color_d93e3f));
                    }
                    tv_title2.setText(cont1);
                    mycenter_relat_shenfen.addView(view);

                    int status = info.getStatus();
                    if (i == 0) {
                        if (status == 1) {
                            shop_goods_id = info.getShop_goods_id();
                        }
                    }
                }
            }


            int status = mInfo.getStatus();
            if (i == 0) {
                item_tv_status.setVisibility(View.VISIBLE);
                if (status == 0) {
                    item_tv_status.setText("去完成");
                    item_tv_status.setBackgroundResource(R.drawable.corners_bg_login_new);
                    item_tv_status.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mActivity, ZYBZuDuiSelectMoneyActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
                    });
                } else {
                    item_tv_status.setText("已完成");
                    item_tv_status.setBackgroundResource(R.drawable.corners_bg_logingray_new);
                }
            } else if (i == 1) {
                if (status == 1) {
                    item_tv_status.setText("已锁定");
                    item_tv_status.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
                    item_tv_status.setBackgroundColor(getResources().getColor(R.color.transparent));
                    item_tv_status.setVisibility(View.VISIBLE);
                } else {
                    if (user_role == 1) {
                        item_tv_status.setVisibility(View.VISIBLE);
                        item_tv_status.setText("锁定");
                        item_tv_status.setBackgroundResource(R.drawable.corners_bg_login_new);
                        item_tv_status.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SignUpZFJ();
                            }
                        });
                    } else {
                        item_tv_status.setVisibility(View.GONE);
                    }

                }
            }
        }


    }

    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
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

    MyDialog selectPayDialog;
    TextView tv_title, tv_title2;
    TextView tv_off;

    public void setPayDialog() {
        if (selectPayDialog == null) {
            selectPayDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_tixiandel);// 创建Dialog并设置样式主题
            selectPayDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectPayDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectPayDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectPayDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectPayDialog.getWindow().setAttributes(p); //设置生效
            tv_title = selectPayDialog.findViewById(R.id.tv_title);
            tv_title2 = selectPayDialog.findViewById(R.id.tv_title2);
            tv_title2.setVisibility(View.VISIBLE);
            tv_title2.setTextColor(getResources().getColor(R.color.text_contents_color));
            tv_off = selectPayDialog.findViewById(R.id.tv_off);
            TextView tv_sure = selectPayDialog.findViewById(R.id.tv_sure);
            tv_sure.setVisibility(View.GONE);
            View view_suline = selectPayDialog.findViewById(R.id.view_suline);
            view_suline.setVisibility(View.GONE);
            tv_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (is_activity_finish == 1) {
                        SignUpZFJ();
                    } else {
                        selectPayDialog.dismiss();
                        doFinish();
                    }
                }
            });
            selectPayDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    doFinish();
                    return true;
                }
            });
        } else {
            selectPayDialog.show();
        }
        String cont = "";
        if (is_activity_finish == 1) {
            cont = CommonUtil.setName(invite_user_name) + "已为您开通“双促”活动资格";
            tv_title2.setText("请配置财富资产");
            tv_title.setTextColor(getResources().getColor(R.color.red));
            tv_off.setText("确定");
        } else {
            cont = "你还未获得双促活动资格";
            tv_title2.setText("请咨询创业导师或坐堂医");
            tv_title.setTextColor(getResources().getColor(R.color.red));
            tv_off.setText("退出");
        }
        tv_title.setText(cont);
    }
}
