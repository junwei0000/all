package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.zudui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.connon.activity.GrupUtils;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpListDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyAfterBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.GetEnergyListDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.ZYBZuDuiDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.ZYBZuDuiItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.activity.df.ZuDuiSelectActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.adapter.ZuDuiSelectAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.SelectItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 组队
 */
public class ZYBZuDuiActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.absolut_chair)
    AbsoluteLayout absolutChair;
    @BindView(R.id.aiyou_tv_zhufu)
    TextView aiyouTvZhufu;
    @BindView(R.id.aiyou_iv_thumb)
    ImageView aiyouIvThumb;
    @BindView(R.id.aiyou_tv_name)
    TextView aiyouTvName;
    @BindView(R.id.layout_inner)
    LinearLayout layoutInner;
    @BindView(R.id.layout_group_center)
    LinearLayout layoutGroupCenter;
    @BindView(R.id.iv_tutorimg)
    ImageView ivTutorimg;
    @BindView(R.id.tv_tutorname)
    TextView tvTutorname;
    @BindView(R.id.layout_tutor)
    LinearLayout layoutTutor;
    @BindView(R.id.relat_group)
    RelativeLayout relatGroup;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_create)
    TextView tvCreate;


    /**
     * 自己是否是队长 1 是
     */
    int myrole;
    /**
     * 流程状态：0 人未满,1 人已满 2 生成订单 3 充值完成;
     */
    int process_status;
    int is_update_entre;// 1可以修改导师
    String zhuyoubao_team_item_id;
    String zhuyoubao_team_room_id;
    HashMap<Integer, ZYBZuDuiItemBean> mHashmap = new HashMap<>();

    String entrepreneurs_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.layout_tutor:
            case R.id.layout_inner:
                if (myrole == 1 && is_update_entre == 1) {
                    Intent intent = new Intent(mActivity, ZYBTutorSelectActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("zhuyoubao_team_room_id", zhuyoubao_team_room_id);
                    startActivity(intent);
                }
                break;
            case R.id.tv_edit:
                isEdit = true;
                if (myrole == 1) {
                    setDialog();
                } else {
                    setDialog();
                }
                break;
            case R.id.tv_create:
                if (!TextUtils.isEmpty(entrepreneurs_id) && myrole == 1 && (process_status == 1 || process_status == 0)) {
                    isEdit = false;
                    setDialog();
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
        return R.layout.pioneer_userzyb_zudui;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        layoutTutor.setOnClickListener(this);
        layoutInner.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
        tvCreate.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
        aiyouTvName.setText("选择创业导师");
        zhuyoubao_team_room_id = getIntent().getStringExtra("zhuyoubao_team_room_id");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRechargeInfo();
    }


    public void getRechargeInfo() {
        showDialog();
        Observable<PionZFBDataBean> observable = Api.getInstance().service.ZYBTeamRoomInfo(
                UserUtils.getUserId(mContext), zhuyoubao_team_room_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PionZFBDataBean>() {
                    @Override
                    public void accept(PionZFBDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (responseBean.getStatus().equals("200")) {
                            PionZFBAfterBean data = responseBean.getData();
                            ZYBZuDuiItemBean room = data.getRoom();
                            process_status = room.getProcess_status();
                            is_update_entre = room.getIs_update_entre();
                            pageTopTvName.setText("组队请购祝佑宝 每位队员" + room.getMoney());

                            ZYBZuDuiItemBean entre = data.getEntre();
                            if (entre != null && !TextUtils.isEmpty(entre.getUser_id())) {
                                entrepreneurs_id = entre.getEntrepreneurs_id();
                                layoutTutor.setVisibility(View.VISIBLE);
                                layoutInner.setVisibility(View.GONE);
                                GlideDownLoadImage.getInstance().loadCircleImage(entre.getAvatar(), ivTutorimg);
                                tvTutorname.setText(CommonUtil.setName(entre.getUser_name()));
                            } else {
                                layoutTutor.setVisibility(View.GONE);
                                layoutInner.setVisibility(View.VISIBLE);
                            }
                            List<ZYBZuDuiItemBean> usersList = data.getItems();
                            mHashmap.clear();
                            showZudInfo(usersList);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });

    }

    public void ZYBQuitTeam() {
        Observable<ResponseBean> observable = Api.getInstance().service.ZYBQuitTeam(UserUtils.getUserId(mContext), zhuyoubao_team_item_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        ToastUtils.showToast(responseBean.getMsg());
                        if (responseBean.getStatus().equals("200")) {
                            doFinish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    public void createUserRecharge() {
        Observable<ZYBZuDuiDataBean> observable = Api.getInstance().service.createUserRecharge(
                UserUtils.getUserId(mContext), entrepreneurs_id, zhuyoubao_team_room_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ZYBZuDuiDataBean>() {
                    @Override
                    public void accept(ZYBZuDuiDataBean responseBean) throws Exception {
                        ToastUtils.showToast(responseBean.getMsg());
                        if (responseBean.getStatus().equals("200")) {
                            getRechargeInfo();
                            ArrayList<ZYBZuDuiDataBean.RiceActiviesAfterBean.RiceActiviesItemBean> userCreateInfos = responseBean.getData().getUserCreateInfos();
                            if (userCreateInfos != null && userCreateInfos.size() > 0) {
                                String createinfo = "";
                                String notcreateinfo = "";
                                for (int i = 0; i < userCreateInfos.size(); i++) {
                                    int status = userCreateInfos.get(i).getStatus();
                                    String username = userCreateInfos.get(i).getUser_name();
                                    if (status == 1) {
                                        if (TextUtils.isEmpty(createinfo)) {
                                            createinfo = createinfo + username;
                                        } else {
                                            createinfo = createinfo + "、" + username;
                                        }
                                    } else {
                                        if (TextUtils.isEmpty(notcreateinfo)) {
                                            notcreateinfo = notcreateinfo + username;
                                        } else {
                                            notcreateinfo = notcreateinfo + "、" + username;
                                        }
                                    }
                                }
                                setPayDialog(createinfo, notcreateinfo);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    private void showZudInfo(List<ZYBZuDuiItemBean> usersList) {
        mHashmap.clear();
        if (usersList != null && usersList.size() > 0) {
            for (int i = 0; i < usersList.size(); i++) {
                mHashmap.put(i + 1, usersList.get(i));
                if (UserUtils.getUserId(mContext).equals(usersList.get(i).getUser_id())) {
                    myrole = usersList.get(i).getRole();
                    zhuyoubao_team_item_id = usersList.get(i).getZhuyoubao_team_item_id();
                }
            }
            if (process_status == 2 || process_status == 3) {
                tvEdit.setVisibility(View.GONE);
                tvCreate.setVisibility(View.GONE);
            } else {
                if (myrole == 1) {
                    tvEdit.setText("解散当前组队");
                    tvCreate.setVisibility(View.VISIBLE);
                    tvCreate.setBackgroundColor(getResources().getColor(R.color.color_ffd536));
                    tvCreate.setTextColor(getResources().getColor(R.color.color_674d16));
                } else {
                    tvEdit.setText("退出当前组队");
                    tvCreate.setVisibility(View.GONE);
                }
                tvEdit.setVisibility(View.VISIBLE);
            }
        }
        if (mGrupUtils == null) {
            mGrupUtils = new GrupUtils(mActivity);
        }
        mGrupUtils.showChairAllViewZYBZuDui(groupnum, process_status, is_update_entre, myrole,entrepreneurs_id,
                zhuyoubao_team_room_id, zhuyoubao_team_item_id,
                mHashmap, relatGroup, absolutChair, layoutGroupCenter);
    }

    int groupnum = 5;
    GrupUtils mGrupUtils;
    MyDialog selectDialog;
    TextView tv_title, tv_title2;
    boolean isEdit;

    public void setDialog() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_tixiandel);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            tv_title = selectDialog.findViewById(R.id.tv_title);
            tv_title2 = selectDialog.findViewById(R.id.tv_title2);
            TextView tv_off = selectDialog.findViewById(R.id.tv_off);
            TextView tv_sure = selectDialog.findViewById(R.id.tv_sure);
            tv_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                    if (isEdit) {
                        ZYBQuitTeam();
                    } else {
                        createUserRecharge();
                    }
                }
            });
        } else {
            selectDialog.show();
        }
        if (isEdit) {
            tv_title2.setVisibility(View.GONE);
        } else {
            tv_title2.setText("组队订单生成后将不可取消");
            tv_title2.setVisibility(View.VISIBLE);
        }
    }

    MyDialog selectPayDialog;
    TextView tv_titlePay, tv_title2Pay;

    public void setPayDialog(String createinfo, String notcreateinfo) {
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
            tv_titlePay = selectPayDialog.findViewById(R.id.tv_title);
            tv_title2Pay = selectPayDialog.findViewById(R.id.tv_title2);
            tv_title2Pay.setVisibility(View.VISIBLE);
            TextView tv_off = selectPayDialog.findViewById(R.id.tv_off);
            TextView tv_sure = selectPayDialog.findViewById(R.id.tv_sure);
            tv_sure.setVisibility(View.GONE);
            tv_off.setText("知道了");
            View view_suline = selectPayDialog.findViewById(R.id.view_suline);
            view_suline.setVisibility(View.GONE);
            tv_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPayDialog.dismiss();
                }
            });
        } else {
            selectPayDialog.show();
        }
        if (TextUtils.isEmpty(createinfo)) {
            tv_titlePay.setVisibility(View.GONE);
        } else {
            tv_titlePay.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(notcreateinfo)) {
            tv_title2Pay.setVisibility(View.GONE);
        } else {
            tv_title2Pay.setVisibility(View.VISIBLE);
        }
        tv_titlePay.setText(createinfo + " 生成订单成功");
        tv_title2Pay.setText(notcreateinfo + " 有进行中的组队请购祝佑宝，未生成订单");
    }

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
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

}
