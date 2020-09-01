package com.longcheng.lifecareplan.modular.mine.treasurebowl.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.home.domainname.activity.GrabSuccessActivity;
import com.longcheng.lifecareplan.modular.mine.emergencys.EmergencysBangAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity.PionZFBActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.adapter.ZuDuiSelectAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.SelectItemBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.adapter.CornucopiaAdapter;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.CornucopiaBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.CornucopiaListBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.UserBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.UserCornConfigListBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.UserCornucopiaBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.LongClickButton;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CornucopiaPageActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.top_center_title)
    TextView topCenterTitle;
    @BindView(R.id.tv_shoukangnum)
    TextView tvShoukangnum;
    @BindView(R.id.super_shoukangbao)
    TextView superShoukangbao;
    @BindView(R.id.tv_shoukang)
    TextView tvShoukang;
    @BindView(R.id.tv_fuqi)
    TextView tvFuqi;
    @BindView(R.id.tv_zhuyou)
    TextView tvZhuyou;
    @BindView(R.id.tv_jieqi)
    TextView tvJieqi;
    @BindView(R.id.tv_get_integral)
    TextView tvGetIntegral;
    @BindView(R.id.tv_life)
    TextView tvLife;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    @BindView(R.id.date_listview)
    MyListView dateListview;
    @BindView(R.id.pull_view)
    PullToRefreshScrollView pullView;
    @BindView(R.id.tv_btn)
    TextView tvBtn;
    @BindView(R.id.layout_top)
    LinearLayout layout_top;


    CornucopiaAdapter cornucopiaAdapter = null;
    private String[] strdata = new String[]{"全部", "24节气", "48节气", "72节气"};
    MyDialog JSDialog;
    private int isopend = 0;
    LayoutInflater mInflater = null;
    List<UserCornConfigListBean> userCornConfigListBean = null; // 选择存储期限
    UserCornucopiaBean userCornucopiaBean = null;
    UserBean userBean = null;

    private int cornucopiaconfigid = 0;
    private int listtype = 0;// 默认全部 0、全部1、24节气、2、48节气3、72节气
    private int page = 1;
    private int pagesize = 20;
    private List<CornucopiaListBean.DataBean> dataBeanslist = null;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_btn:
                // 开启聚宝盆 - 存聚宝盆
                if (userCornucopiaBean != null) {
                    showFunengDialog(userCornucopiaBean);
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
        return R.layout.cornucopia_layout;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);

    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvBtn.setOnClickListener(this);
        idFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                listtype = position;
                idFlowlayout.getAdapter().setSelectedList(listtype);//默认选中第一个
                getCornucopiaList(1);
                return true;
            }
        });
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
        int wid = DensityUtil.screenWith(mContext);
        layout_top.setLayoutParams(new RelativeLayout.LayoutParams(wid, (int) (wid * 0.654)));
        pagetopLayoutLeft.setFocusable(true);
        pagetopLayoutLeft.setFocusableInTouchMode(true);
        pagetopLayoutLeft.requestFocus();
        topCenterTitle.setText("聚宝盆");
        try {
            isopend = this.getIntent().getIntExtra("opened", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mInflater = LayoutInflater.from(this);
        idFlowlayout.setAdapter(new TagAdapter<String>(strdata) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.dialog_view_textview,
                        idFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
        idFlowlayout.getAdapter().setSelectedList(listtype);//默认选中第一个
        if (isopend == 0) {
            showJBPNoDialog();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCornucopiaInfo();
        getCornucopiaList(1);
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (jbpnoDialog != null && jbpnoDialog.isShowing()) {
                jbpnoDialog.dismiss();
                doFinish();
            }
            doFinish();
        }
        return false;
    }


    private TextView dtv_shoukang = null;
    private TextView dtv_fuqi = null;
    private TextView dtv_zhuyou = null;
    private TextView dtv_jieqi = null;
    private TextView dbao_num = null;
    private EditText det_sellm = null;
    private TextView ditem_tv_num = null;
    private TagFlowLayout dtagflow = null;
    private LongClickButton leftbtn = null;
    private LongClickButton rightbtn = null;
    private TextView dbtn_sure = null;

    private LinearLayout edit_layout = null;
    private TextView hinit_tv = null;

    int num = 1; // 默认开通赋能组数
    int maxNum = 10000;
    String funen = "24";

    private void setEditext(String numstr) {
        funen = numstr;
        dbao_num.setText(PriceUtils.getInstance().gteMultiplySumPrice("" + num, funen));
    }

    /**
     * 打开赋能页面
     */
    private void showFunengDialog(UserCornucopiaBean userCornucopiaBean) {
        if (JSDialog == null) {
            JSDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_domain_cornucopia);// 创建Dialog并设置样式主题
            Window window = JSDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            JSDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = JSDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth();
            JSDialog.getWindow().setAttributes(p); //设置生效

            dtv_shoukang = JSDialog.findViewById(R.id.tv_shoukang);
            dtv_fuqi = JSDialog.findViewById(R.id.tv_fuqi);
            dtv_zhuyou = JSDialog.findViewById(R.id.tv_zhuyou);
            dtv_jieqi = JSDialog.findViewById(R.id.tv_jieqi);
            dbao_num = JSDialog.findViewById(R.id.bao_num);
            ditem_tv_num = JSDialog.findViewById(R.id.item_tv_num);
            dtagflow = JSDialog.findViewById(R.id.diloag_flowlayout);
            leftbtn = JSDialog.findViewById(R.id.item_layout_sub);
            rightbtn = JSDialog.findViewById(R.id.item_tv_add);
            dbtn_sure = JSDialog.findViewById(R.id.btn_sure);
            det_sellm = JSDialog.findViewById(R.id.et_sell);
            edit_layout = JSDialog.findViewById(R.id.edit_layout);
            hinit_tv = JSDialog.findViewById(R.id.hihit_tv);
            det_sellm.setCursorVisible(false);// 内容清空后将编辑框1的光标隐藏，提升用户的体验度
            setEditext(funen);
            JSDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (isopend == 0) {
                        doFinish();
                    }
                }
            });
            det_sellm.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().equals("")) {
                        setEditext(charSequence.toString());
                    } else {
                        funen = "0";
                        setEditext(funen);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            if (userCornConfigListBean != null) {
                TagAdapter tagAdapter = new TagAdapter<UserCornConfigListBean>(userCornConfigListBean) {
                    @Override
                    public View getView(FlowLayout parent, int position, UserCornConfigListBean useritem) {
                        TextView tv = (TextView) mInflater.inflate(R.layout.dialog_view_textview,
                                dtagflow, false);
                        tv.setText(useritem.getName());
                        return tv;
                    }
                };
                dtagflow.setAdapter(tagAdapter);
                // 设置默认选中
                tagAdapter.setSelectedList(0);
                cornucopiaconfigid = userCornConfigListBean.get(0).getCornucopia_config_id();
                dtagflow.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        cornucopiaconfigid = userCornConfigListBean.get(position).getCornucopia_config_id();
                        dtagflow.getAdapter().setSelectedList(position);
                        return true;
                    }
                });
            }
            //连续减
            leftbtn.setLongClickRepeatListener(new LongClickButton.LongClickRepeatListener() {
                @Override
                public void repeatAction() {
                    dialogClick.onClick(leftbtn);
                }
            }, 50);
            //连续加
            rightbtn.setLongClickRepeatListener(new LongClickButton.LongClickRepeatListener() {
                @Override
                public void repeatAction() {
                    dialogClick.onClick(rightbtn);
                }
            }, 50);

            edit_layout.setOnClickListener(dialogClick);
            hinit_tv.setOnClickListener(dialogClick);
            dbtn_sure.setOnClickListener(dialogClick);
            leftbtn.setOnClickListener(dialogClick);
            rightbtn.setOnClickListener(dialogClick);
        } else {
            JSDialog.show();
        }
        if (isopend == 0) {
            JSDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        } else {
            JSDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
        }
        if (userBean != null) {
            dtv_fuqi.setText(userBean.getFuqibao());
            dtv_zhuyou.setText(userBean.getUzhufubao());
            dtv_jieqi.setText(userBean.getJieqibao());
            dtv_shoukang.setText(userBean.getShoukangyuan());
        }
    }

    View.OnClickListener dialogClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.edit_layout:
                case R.id.hihit_tv:
                    det_sellm.requestFocus();
                    det_sellm.setSelection(det_sellm.getText().length());
                    ConfigUtils.getINSTANCE().openSoftInput(mActivity, det_sellm);
                    break;
                case R.id.item_layout_sub:
                    if (num > 1) {
                        num--;
                    }
                    dbao_num.setText(PriceUtils.getInstance().gteMultiplySumPrice("" + num, funen));
                    ditem_tv_num.setText("" + num);
                    break;
                case R.id.item_tv_add:
                    if (num < maxNum) {
                        num++;
                    }
                    dbao_num.setText(PriceUtils.getInstance().gteMultiplySumPrice("" + num, funen));
                    ditem_tv_num.setText("" + num);
                    break;
                case R.id.btn_sure://立即互祝
                    funen = det_sellm.getText().toString().trim();
                    if (TextUtils.isEmpty(funen) || (!TextUtils.isEmpty(funen) && Double.valueOf(funen) < 24)) {
                        ToastUtils.showToast("赋能数量每组最低24");
                        return;
                    }
                    /// 开启执行
                    if (userCornucopiaBean != null) {
                        String cont = "";
                        btype = 0;

                        String pri = PriceUtils.getInstance().gteMultiplySumPrice("" + num, funen);
                        if (Double.valueOf(pri) > Double.valueOf((!userBean.getFuqibao().equals("")) ? userBean.getFuqibao() : "0").doubleValue()) {
                            cont = "福祺宝";
                        }
                        if (Double.valueOf(pri) > Double.valueOf((!userBean.getJieqibao().equals("")) ? userBean.getJieqibao() : "0").doubleValue()) {
                            if (TextUtils.isEmpty(cont)) {
                                cont = "节气宝";
                            } else {
                                cont = cont + "、节气宝";
                            }
                        }
                        if (Double.valueOf(pri) > Double.valueOf((!userBean.getShoukangyuan().equals("")) ? userBean.getShoukangyuan() : "0").doubleValue()) {
                            if (TextUtils.isEmpty(cont)) {
                                cont = "寿康宝";
                            } else {
                                cont = cont + "、寿康宝";
                            }
                        }
                        if (Double.valueOf(pri) > Double.valueOf((!userBean.getUzhufubao().equals("")) ? userBean.getUzhufubao() : "0").doubleValue()) {
                            if (TextUtils.isEmpty(cont)) {
                                cont = "祝佑宝";
                            } else {
                                cont = cont + "、祝佑宝";
                            }
                            btype = 4;
                        }
                        if (!TextUtils.isEmpty(cont)) {
                            dialogtype = 3;
                            showJBPDialog(cont);
                        } else {
                            dialogtype = 2;
                            showJBPDialog("");
                        }

                    }
                    break;
                default:

                    break;
            }
        }
    };

    private MyDialog jbpDialog;
    int dialogtype;// dialogtype 2、开启赋能提示 3、某宝不足提示
    int btype;//4 祝佑宝

    /**
     * 聚宝盆提示对话框 dialogtype 2、开启赋能提示 3、某宝不足提示
     * moubao 类别名称
     * btype 类别判断
     */
    private void showJBPDialog(String moubao) {
        if (jbpDialog == null) {
            jbpDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_jbp_sure);// 创建Dialog并设置样式主题
            jbpDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = jbpDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            jbpDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = jbpDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5;
            jbpDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_hinit = jbpDialog.findViewById(R.id.tv_hinit);
            TextView tv_cancel = jbpDialog.findViewById(R.id.tv_cancel);
            TextView tv_sure = jbpDialog.findViewById(R.id.tv_sure);
            if (dialogtype == 2) {
                tv_hinit.setText(String.format(mActivity.getResources().getString(R.string.hinit_diloag_start_sucess), num + ""));
                tv_sure.setText("马上赋能");
            } else if (dialogtype == 3) {
                tv_hinit.setText(String.format(mActivity.getResources().getString(R.string.hinit_diloag_not_sucess), num + "", moubao, moubao));
                tv_sure.setText("去添加");
            }
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jbpDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jbpDialog.dismiss();/**/
                    if (dialogtype == 2) {
                        // 开启赋能
                        getCornucopiaStart();
                    } else if (dialogtype == 3) {
                        // 去添加
                        if (btype == 4) {
                            //跳转到祝佑宝添加
                            Intent intent = new Intent(mActivity, PionZFBActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        } else {
                            doFinish();
                        }
                    }
                }
            });
        } else {
            jbpDialog.show();
        }
    }

    /**
     * 聚宝盆提示对话框  1、未开启提示 2、开启赋能提示 3、某宝不足提示
     */

    private MyDialog jbpnoDialog;

    private void showJBPNoDialog() {
        if (jbpnoDialog == null) {
            jbpnoDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_jbpno_sure);// 创建Dialog并设置样式主题
            jbpnoDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = jbpnoDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            jbpnoDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = jbpnoDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5;
            jbpnoDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_sure = jbpnoDialog.findViewById(R.id.tv_sure);
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jbpnoDialog.dismiss();/**/
                    showFunengDialog(userCornucopiaBean);
                }
            });
            jbpnoDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    doFinish();
                    return true;
                }
            });
        } else {
            jbpnoDialog.show();
        }
    }


    public void getCornucopiaInfo() {
        Observable<CornucopiaBean> observable = Api.getInstance().service.getCornucopiaInfo(
                UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CornucopiaBean>() {
                    @Override
                    public void accept(CornucopiaBean responseBean) throws Exception {

                        String status_ = responseBean.getStatus();
                        if (status_.equals("400")) {
                            ToastUtils.showToast(responseBean.getMsg());
                        } else if (status_.equals("200")) {
                            CornucopiaBean data = responseBean.getData();
                            if (data != null) {
                                userCornucopiaBean = data.getUserCornucopia();
                                userCornConfigListBean = data.getUserCornConfigList();
                                userBean = data.getUser();
                                DataSucess(userCornucopiaBean);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showToast(R.string.net_tishi);
                    }
                });
    }

    public void getCornucopiaList(int page_) {
        Observable<CornucopiaListBean> observable = Api.getInstance().service.getRankingList(
                UserUtils.getUserId(mContext), listtype, page_, pagesize, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CornucopiaListBean>() {
                    @Override
                    public void accept(CornucopiaListBean responseBean) throws Exception {
                        ListUtils.getInstance().RefreshCompleteS(pullView);
                        String status_ = responseBean.getStatus();
                        if (status_.equals("400")) {
                            ToastUtils.showToast(responseBean.getMsg());
                        } else if (status_.equals("200")) {
                            dataBeanslist = responseBean.getData();
                            int size = dataBeanslist == null ? 0 : dataBeanslist.size();
                            if (page_ == 1) {
                                cornucopiaAdapter = null;
                            }
                            if (cornucopiaAdapter == null) {
                                cornucopiaAdapter = new CornucopiaAdapter(CornucopiaPageActivity.this, dataBeanslist);
                                dateListview.setAdapter(cornucopiaAdapter);
                            } else {
                                cornucopiaAdapter.reloadListView(dataBeanslist, false);
                            }
                            if (size > 0 || (page > 1 && size >= 0)) {
                                page = page_;
                            } else {
                                page = 1;
                            }
                            checkLoadOver(size);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showToast(R.string.net_tishi);
                        ListUtils.getInstance().RefreshCompleteS(pullView);
                    }
                });
    }

    public void getCornucopiaStart() {
        String total_money = PriceUtils.getInstance().gteMultiplySumPrice("" + num, funen);
        Observable<ResponseBean> observable = Api.getInstance().service.getOpenCor(
                UserUtils.getUserId(mContext), cornucopiaconfigid + "", funen + "", num + "", total_money, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        String status_ = responseBean.getStatus();
                        if (status_.equals("400")) {
                            ToastUtils.showToast(responseBean.getMsg());
                        } else if (status_.equals("200")) {
                            grabSuccess();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.v("TAG", "throwable" + throwable.getMessage());
                        ToastUtils.showToast(R.string.net_tishi);
                    }
                });
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

    private void grabSuccess() {
        isopend = 1;
        JSDialog.dismiss();
        Intent intent = new Intent(this, CornucopiaSucessActicity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("groupnum", "" + num);
        startActivity(intent);
    }

    private void DataSucess(UserCornucopiaBean itemuserinfo) {
        tvShoukangnum.setText(new StringBuffer().append(itemuserinfo.getSuper_shoukangyuan()).toString());
        tvFuqi.setText(new StringBuffer().append(itemuserinfo.getFuqibao()).toString());
        tvZhuyou.setText(new StringBuffer().append(itemuserinfo.getUzhufubao()).toString());
        tvJieqi.setText(new StringBuffer().append(itemuserinfo.getJieqibao()).toString());
        tvShoukang.setText(new StringBuffer().append(itemuserinfo.getShoukangyuan()).toString());
        tvGetIntegral.setText(new StringBuffer().append(itemuserinfo.getTotal_score()).toString());
        tvLife.setText(new StringBuffer().append(itemuserinfo.getAbility()).toString());
    }


}
