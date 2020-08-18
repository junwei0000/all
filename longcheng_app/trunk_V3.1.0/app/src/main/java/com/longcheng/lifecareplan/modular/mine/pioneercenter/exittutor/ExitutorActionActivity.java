package com.longcheng.lifecareplan.modular.mine.pioneercenter.exittutor;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
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
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.ExitutorSearchBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.ExitutorUserItem;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ExitutorActionActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.et_cont)
    EditText etCont;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.realt_info)
    RelativeLayout realtInfo;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.btn_commit)
    TextView btnCommit;


    @BindView(R.id.item_iv_img)
    ImageView itemIvImg;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.item_tv_jieqi)
    TextView itemTvJieqi;
    @BindView(R.id.item_tv_phone)
    TextView itemTvPhone;
    @BindView(R.id.item_tv_add)
    TextView itemTvAdd;

    String entrepreneurs_id = ""; // 退出创业导师ID
    boolean isselected = false;

    @Override
    public void onClick(View view) {
        String search;
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_sure:
                search = etCont.getText().toString();
                if (!TextUtils.isEmpty(search)) {
                    getUserByPhone(search);
                } else {
                    ToastUtils.showToast("请输入正确的手机号");
                }
                break;
            case R.id.btn_commit:
            case R.id.layout_bottom:
                if (isselected && !TextUtils.isEmpty(entrepreneurs_id)) {
                    doSettlementAction();
                }
                break;
            case R.id.realt_info:
                // 选择创业导师
                isselected = true;
                selectHinit();
                break;

            default:
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.exitutor_layout;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("创业导师退出");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvSure.setOnClickListener(this);
        layoutBottom.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        realtInfo.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etCont, 11);
    }

    @Override
    public void initDataAfter() {
    }


    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
            doFinish();
        }
        return false;
    }

    public void doSettlementAction() {
        showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.doSettlementActionPost(UserUtils.getUserId(mContext), entrepreneurs_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {
                            String status = responseBean.getStatus();
                            if (status.equals("400")) {
                                ToastUtils.showToast(responseBean.getMsg());
                            } else if (status.equals("200")) {
                                ToastUtils.showToast(responseBean.getMsg());
                            }
                        } else {
                            ListError();
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


    /**
     * @param phone
     */
    public void getUserByPhone(String phone) {
        showDialog();
        Observable<ExitutorSearchBean> observable = Api.getInstance().service.getExitSearchUser(UserUtils.getUserId(mContext), phone, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ExitutorSearchBean>() {
                    @Override
                    public void accept(ExitutorSearchBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {
                            String status = responseBean.getStatus();
                            if (status.equals("400")) {
                                ToastUtils.showToast(responseBean.getMsg());
                            } else if (status.equals("200")) {
                                try {
                                    if (responseBean.getData().isEmpty()) {
                                        ToastUtils.showToast(responseBean.getMsg());
                                        entrepreneurs_id = "";
                                        realtInfo.setVisibility(View.INVISIBLE);
                                        return;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                isselected = false;
                                selectHinit();
                                ExitutorUserItem exitutorUserItem = responseBean.getData().get(0);
                                if (exitutorUserItem != null) {
                                    entrepreneurs_id = exitutorUserItem.getEntrepreneurs_id();
                                    GlideDownLoadImage.getInstance().loadCircleImage(exitutorUserItem.getAvatar(), itemIvImg);
                                    itemTvName.setText(CommonUtil.setName(exitutorUserItem.getUser_name()));
                                    itemTvPhone.setText(exitutorUserItem.getPhone());
                                    itemTvJieqi.setText(exitutorUserItem.getUser_branch_info());
                                    realtInfo.setVisibility(View.VISIBLE);
                                } else {
                                    ToastUtils.showToast("暂无该用户");
                                    entrepreneurs_id = "";
                                    realtInfo.setVisibility(View.INVISIBLE);
                                }
                            }
                        } else {
                            realtInfo.setVisibility(View.INVISIBLE);
                            ListError();
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

    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
    }


    private void selectHinit() {
        if (isselected) {
            itemTvAdd.setText("已选");
            ColorChangeByTime.getInstance().changeDrawableToClolor(mContext, itemTvAdd, R.color.red);
            btnCommit.setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            itemTvAdd.setText("未选");
            ColorChangeByTime.getInstance().changeDrawableToClolor(mContext, itemTvAdd, R.color.gray);
            btnCommit.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }
}
