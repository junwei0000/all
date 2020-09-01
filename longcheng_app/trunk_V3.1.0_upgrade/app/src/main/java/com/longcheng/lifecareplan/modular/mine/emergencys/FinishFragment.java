package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListFrag;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleListProgressUtils;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.CashInfoBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.CertifyBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.CurrentHelpNeed;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.HeloneedBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.HeloneedIndexBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.Img;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.LoveBroadcasts;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.ResultApplyBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;
import com.longcheng.lifecareplan.widget.view.FooterNoMoreData;
import com.megvii.meglive_sdk.listener.DetectCallback;
import com.megvii.meglive_sdk.listener.PreCallback;
import com.megvii.meglive_sdk.manager.MegLiveManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.os.Build.VERSION_CODES.M;

public class FinishFragment extends BaseListFrag<FinishContract.View, FinishPresenterImp<FinishContract.View>> implements FinishContract.View {
    @BindView(R.id.vp)
    ViewFlipper vp;
    @BindView(R.id.lv_progress)
    PullToRefreshListView lv_progress;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.help_et_search)
    SupplierEditText help_et_search;
    @BindView(R.id.layout_item_energy)
    LinearLayout layout_item_energy;
    @BindView(R.id.item_tv_num)
    TextView item_tv_num;
    @BindView(R.id.item_tv_name)
    TextView item_tv_name;
    @BindView(R.id.item_tv_jieeqi)
    TextView item_tv_jieeqi;
    @BindView(R.id.item_iv_thumb)
    ImageView item_iv_thumb;
    @BindView(R.id.item_layout_shenfen)
    LinearLayout item_layout_shenfen;
    @BindView(R.id.item_layout_rank)
    LinearLayout item_layout_rank;
    @BindView(R.id.item_pb_lifeenergynum)
    NumberProgressBar item_pb_lifeenergynum;
    @BindView(R.id.item_pb_numne)
    TextView item_pb_numne;
    @BindView(R.id.relat_cn)
    RelativeLayout relat_cn;
    @BindView(R.id.rl_power)
    RelativeLayout rl_power;
    @BindView(R.id.rl_bootom)
    RelativeLayout rl_bootom;
    private String user_id;
    private String url;
    private int page;
    private int help_need_id;
    private String card = "";
    private String name = "";
    private boolean refreshStatus = false;
    private FinishAdapter mAdapter;
    private ImageLoader asyncImageLoader;
    private List<LoveBroadcasts> topmsg;
    private MyDialog mUpdaDialog;
    private CashInfoBean.CashBean cash;
    private List<HeloneedBean.UserBean> mAllList = new ArrayList<>();
    private LifeStyleListProgressUtils mProgressUtils;
    private HeloneedBean mEnergyAfterBean;
    private String msg = "";
    private boolean isClick = true;

    private String sign = "";


    private MegLiveManager megLiveManager;

    public static FinishFragment newInstance() {
        FinishFragment fragment = new FinishFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_finish;
    }

    @Override
    protected View getFooterView() {
        FooterNoMoreData view = new FooterNoMoreData(mContext);
        view.showContJiJu(true);
        return view;
    }

    private void init() {
        megLiveManager = MegLiveManager.getInstance();
        /**
         * 如果build.gradle中的applicationId 与 manifest中package不一致，必须将manifest中package通过
         * 下面方法传入，如果一致可以不调用此方法
         */
        megLiveManager.setManifestPack(getActivity(), "com.longcheng.lifecareplan");


        //  mProgressDialog = new ProgressDialog(this);
        // mProgressDialog.setCancelable(false);


        sign = ExampleApplication.sign();
    }

    @Override
    public void initView(View view) {
        cash = new CashInfoBean.CashBean();
        asyncImageLoader = new ImageLoader(mContext, "headImg");
        topmsg = new ArrayList<>();
        user_id = UserUtils.getUserId(mContext);
        tv_search.setOnClickListener(this);
        item_layout_rank.setOnClickListener(this);
        rl_bootom.setOnClickListener(this);
        layout_item_energy.setVisibility(View.GONE);
        relat_cn.setVisibility(View.GONE);

        mProgressUtils = new LifeStyleListProgressUtils(getActivity());
        lv_progress.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                getData(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                page++;
                getData(page);
            }
        });

        lv_progress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAllList != null && mAllList.size() > 0) {

                    Intent intent = new Intent(mActivity, EmergencyDetailActivity.class);
                    intent.putExtra("Help_need_id", mAllList.get(position - 1).getHelp_need_id());
                    intent.putExtra("status", mAllList.get(position - 1).getStatus());
                    intent.putExtra("where", " ");
                    intent.putExtra("head", mAllList.get(position - 1).getUser_avatar());
                    intent.putExtra("jieqi", mAllList.get(position - 1).getUser_branch_info());
                    intent.putExtra("img", (Serializable) mAllList.get(position - 1).getUser_identity_flag());
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);


                }
            }
        });

    }

    private void getData(int p) {
        mPresent.helpneed_index(user_id, 2, "", p, pageSize);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                mPresent.helpneed_index(user_id, 2, help_et_search.getText().toString(), 1, pageSize);
                break;
            case R.id.item_layout_rank:
                if (isClick) {
                    mPresent.cashInfo(user_id, help_need_id);
                } else {
                    ToastUtils.showToast(msg);
                }

                break;
            case R.id.rl_bootom:
                Intent intent = new Intent(mActivity, EmergencyDetailActivity.class);
                intent.putExtra("Help_need_id", mEnergyAfterBean.getCurrentHelpNeed().getHelp_need_id());
                intent.putExtra("head", mEnergyAfterBean.getCurrentHelpNeed().getUser_avatar());
                intent.putExtra("status", mEnergyAfterBean.getCurrentHelpNeed().getStatus());
                intent.putExtra("jieqi", mEnergyAfterBean.getCurrentHelpNeed().getUser_branch_info());
                intent.putExtra("where", "1");
                intent.putExtra("img", (Serializable) mEnergyAfterBean.getCurrentHelpNeed().getUser_identity_flag());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected FinishPresenterImp<FinishContract.View> createPresent() {
        return new FinishPresenterImp<>(mContext, this);
    }

    @Override
    public void ListSuccess(HeloneedIndexBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteL(lv_progress);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {

            mEnergyAfterBean = responseBean.getData();
            LogUtils.e("cash_info" + mEnergyAfterBean.getCurrentHelpNeed().getCash_info());

            if (mEnergyAfterBean != null) {
                help_et_search.setHint("已完成" + mEnergyAfterBean.getComCount() + "," + "进行中" + mEnergyAfterBean.getIngCount());
                showUserInfo(mEnergyAfterBean.currentHelpNeed);
                for (int i = 0; i < mEnergyAfterBean.getLoveBroadcasts().size(); i++) {
                    topmsg.add(mEnergyAfterBean.getLoveBroadcasts().get(i));
                }


                fillView(topmsg);

                int size = mEnergyAfterBean.getList() == null ? 0 : mEnergyAfterBean.getList().size();

                if (backPage == 1) {
                    mAllList.clear();
                    mAdapter = null;
                    showNoMoreData(false, lv_progress.getRefreshableView());
                }
                if (size > 0) {
                    mAllList.addAll(mEnergyAfterBean.getList());
                }
                if (mAdapter == null) {
                    mAdapter = new FinishAdapter(mActivity, mEnergyAfterBean.getList(), "bless_1824");
                    lv_progress.setAdapter(mAdapter);

                } else {
                    mAdapter.reloadListView(mEnergyAfterBean.getList(), false);
                }
                page = backPage;
                checkLoadOver(size);
                ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
            }
        }
    }

    @Override
    public void getUserCard(CashInfoBean responseBean) {
        cash = responseBean.getData();
        LogUtils.e("responseBean" + cash.getUrl());
        showUpdaDialog();
    }

    private void requestCameraPerm() {
        if (android.os.Build.VERSION.SDK_INT >= M) {
            if (mContext.checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                //进行权限请求
                requestPermissions(
                        new String[]{Manifest.permission.CAMERA},
                        ExampleApplication.CAMERA_PERMISSION_REQUEST_CODE);

            } else {

                requestWriteExternalPerm();
            }
        } else {


            beginDetect(1);
        }
    }

    @Override
    public void appSuccess(ResultApplyBean responseBean) {
        LogUtils.e(responseBean.getStatus() + "");
        if (responseBean.getStatus().equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (responseBean.getStatus().equals("200")) {
            item_layout_rank.setVisibility(View.GONE);
            Intent it = new Intent(getActivity(), ResultActivity.class);
            it.putExtra("result", "提现成功");
            startActivity(it);
        }

    }

    @Override
    public void getCard(CertifyBean str) {
        isClick = true;
        card = str.getData().getIdNo();
        name = str.getData().getName();
    }

    @Override
    public void CardError(String str) {
        isClick = false;
        msg = str;
        ToastUtils.showToast(str);
    }

    private void verify(String token, byte[] data) {
        LoadingDialogAnim.show(mContext);
        HttpRequestManager.getInstance().verify(mContext, ExampleApplication.VERIFY_URL, sign, ExampleApplication.SIGN_VERSION, token, data, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String responseBody) {
                Log.w("result", responseBody);
                LoadingDialogAnim.dismiss(mContext);
                try {
                    JSONObject object = new JSONObject(responseBody);
                    JSONObject objectverification = object.getJSONObject("verification");
                    JSONObject objectidcard = objectverification.getJSONObject("idcard");
                    String confidence = objectidcard.getString("confidence");
                    JSONObject OBthresholds = objectidcard.getJSONObject("thresholds");
                    String e4 = OBthresholds.getString("1e-4");
                    Double d = 0.0;
                    Double d2 = 0.0;
                    if (confidence != null && !confidence.equals("") && !confidence.equals("null")) {
                        d = Double.valueOf(confidence);
                    }
                    if (e4 != null && !e4.equals("") && !e4.equals("null")) {
                        d2 = Double.valueOf(e4);
                    }

                    if (d > d2) {

                        gotoFinish();

                    } else {
                        ToastUtils.showToast("人脸认证未通过");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // progressDialogDismiss();
            }

            @Override
            public void onFailure(int statusCode, byte[] responseBody) {
                Log.w("result", new String(responseBody));
                LoadingDialogAnim.dismiss(mContext);
                ToastUtils.showToast("人脸认证未通过");
            }
        });
    }

    @Override
    public void ListError() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getData(1);
        mPresent.isCertify(user_id);
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    private void fillView(List<LoveBroadcasts> topmsg) {
        vp.removeAllViews();
        for (int i = 0; i < topmsg.size(); i++) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.text_item3, null);
            TextView textView = view.findViewById(R.id.tv_cont);
            TextView tv_cont2 = view.findViewById(R.id.tv_cont2);
            ImageView iv_head = view.findViewById(R.id.iv_head);
            asyncImageLoader.DisplayImage(topmsg.get(i).getAvatar(), iv_head);
            textView.setTextColor(mActivity.getResources().getColor(R.color.white));
            tv_cont2.setTextColor(mActivity.getResources().getColor(R.color.white));
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
            textView.setGravity(Gravity.CENTER_VERTICAL);

            String s1 = topmsg.get(i).getDisplay_msg().split("[{]")[0];
            String s2 = topmsg.get(i).getDisplay_msg().split("[{]")[1];
            String s3 = s2.replace("}", "");
            SpannableString spannableString = new SpannableString(s3);
            int len = 0;
            if (s3.contains("元")) {
                len = s3.length() - 1;
            } else if (s3.contains("能量")) {
                len = s3.length() - 2;
            }
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ffff00")), 0, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(s1);
            tv_cont2.setText(spannableString);
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

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.listViewDownConfig(lv_progress);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true, lv_progress.getRefreshableView());
            }
        } else {
            ScrowUtil.listViewConfigAll(lv_progress);
        }
    }

    private void showUserInfo(CurrentHelpNeed need) {
        rl_power.setVisibility(View.GONE);
        if (need.getUser_name() == null || need.getUser_name().equals("null") || need.getUser_name().equals("")) {
            layout_item_energy.setVisibility(View.GONE);
            LogUtils.e("showUserInfo" + "99999");
        } else {
            help_need_id = need.getHelp_need_id();
            layout_item_energy.setVisibility(View.VISIBLE);
            LogUtils.e("getStatus" + need.getStatus());
            LogUtils.e("getStatus" + need.toString());
            if (need.getStatus().equals("2") && (need.getCash_info() == null
                    || need.getCash_info().equals("") || need.getCash_info().equals("null"))) {
                init();
                item_layout_rank.setVisibility(View.VISIBLE);
            } else {
                item_layout_rank.setVisibility(View.GONE);
            }
            //    item_tv_num.setText(need.getRanking());
            String name = CommonUtil.setName(need.getUser_name());
            item_tv_name.setText(name);
            item_tv_jieeqi.setText(need.getUser_branch_info());

            asyncImageLoader.DisplayImage(need.getUser_avatar(), item_iv_thumb);
            List<Img> identity_img = need.getUser_identity_flag();
            item_layout_shenfen.removeAllViews();
            if (identity_img != null && identity_img.size() > 0) {
                for (int i = 0; i < identity_img.size(); i++) {
                    LinearLayout linearLayout = new LinearLayout(getActivity());
                    ImageView imageView = new ImageView(getActivity());
                    int dit = DensityUtil.dip2px(getActivity(), 16);
                    int jian = DensityUtil.dip2px(getActivity(), 3);
                    linearLayout.setPadding(0, 2, jian, 2);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                    GlideDownLoadImage.getInstance().loadCircleImageCommune(getActivity(), identity_img.get(i).image, imageView);
                    linearLayout.addView(imageView);
                    item_layout_shenfen.addView(linearLayout);
                }
            }
        }
//        item_pb_lifeenergynum.setProgress(need.getProgress());
//        mProgressUtils.showNum(need.getProgress(), item_pb_lifeenergynum, item_pb_numne);
//        item_pb_lifeenergynum.setReachedBarColor(getResources().getColor(R.color.red));
//        ColorChangeByTime.getInstance().changeDrawableToClolor(getActivity(), item_pb_numne, R.color.red);

    }

    public void showUpdaDialog() {
        if (mContext == null) {
            return;
        }

        mUpdaDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_bank);// 创建Dialog并设置样式主题
        mUpdaDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
        Window window = mUpdaDialog.getWindow();
        window.setGravity(Gravity.CENTER);

        WindowManager m = getActivity().getWindowManager();

        mUpdaDialog.show();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = mUpdaDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth() * 3 / 4;
        mUpdaDialog.getWindow().setAttributes(p); //设置生效
        RelativeLayout rl_hava = mUpdaDialog.findViewById(R.id.rl_hava);
        RelativeLayout rl_no = mUpdaDialog.findViewById(R.id.rl_no_card);
        TextView tv_ChangeCard = mUpdaDialog.findViewById(R.id.tv_ChangeCard);
        TextView tv_sure = mUpdaDialog.findViewById(R.id.tv_sure);
        ImageView iv_bank = mUpdaDialog.findViewById(R.id.iv_bank);
        ImageView iv_bank_add = mUpdaDialog.findViewById(R.id.iv_bank_add);
        TextView tv_cardName = mUpdaDialog.findViewById(R.id.tv_cardName);
        TextView tv_cardNum = mUpdaDialog.findViewById(R.id.tv_cardNum);


        rl_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + cash.getUrl());
                mUpdaDialog.dismiss();
                startActivity(intent);
                getActivity().finish();

            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cash.getCard().getBank_name() != null) {
                    requestCameraPerm();

                    mUpdaDialog.dismiss();
                } else {
                    ToastUtils.showToast("请先绑定银行卡");
                }

            }
        });
        tv_ChangeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + cash.getUrl());
                mUpdaDialog.dismiss();
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                getActivity().finish();

            }
        });

        if (!cash.getCard().getBank_no().equals("")) {
            rl_hava.setVisibility(View.VISIBLE);
            rl_no.setVisibility(View.GONE);
            asyncImageLoader.DisplayImage(cash.getImg_url(), iv_bank);
        } else {
            rl_hava.setVisibility(View.GONE);
            rl_no.setVisibility(View.VISIBLE);
        }

        tv_cardName.setText(cash.getCard().getBank_name());
        tv_cardNum.setText(cash.getCard().getBank_no());


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == ExampleApplication.CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length < 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                //拒绝了权限申请
            } else {
                requestWriteExternalPerm();
            }
        } else if (requestCode == ExampleApplication.EXTERNAL_STORAGE_REQ_WRITE_EXTERNAL_STORAGE_CODE) {
            if (grantResults.length < 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                //拒绝了权限申请
            } else {
                beginDetect(1);
            }
        }
    }

    private void beginDetect(int type) {
        if (type == 1) {
            getBizToken("meglive", 1, name, card, "", null);
        }


    }

    private void requestWriteExternalPerm() {
        if (android.os.Build.VERSION.SDK_INT >= M) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //进行权限请求
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        ExampleApplication.EXTERNAL_STORAGE_REQ_WRITE_EXTERNAL_STORAGE_CODE);

            } else {

                beginDetect(1);
            }
        } else {

            beginDetect(1);
        }
    }

    private void getBizToken(String livenessType, int comparisonType, String idcardName, String idcardNum, String uuid, byte[] image) {
        LoadingDialogAnim.show(mContext);
        HttpRequestManager.getInstance().getBizToken(getActivity(), ExampleApplication.GET_BIZTOKEN_URL, sign, ExampleApplication.SIGN_VERSION, livenessType, comparisonType, idcardName, idcardNum, uuid, image, new HttpRequestCallBack() {

            @Override
            public void onSuccess(String responseBody) {
                try {
                    JSONObject json = new JSONObject(responseBody);
                    String bizToken = json.optString("biz_token");
                    LoadingDialogAnim.dismiss(getActivity());
                    megLiveManager.preDetect(getActivity(), bizToken, "zh", "https://api.megvii.com", new PreCallback() {
                        @Override
                        public void onPreStart() {

                        }

                        @Override
                        public void onPreFinish(String s, int i, String s1) {
                            if (i == 1000) {
                                megLiveManager.setVerticalDetectionType(MegLiveManager.DETECT_VERITICAL_FRONT);
                                megLiveManager.startDetect(new DetectCallback() {
                                    @Override
                                    public void onDetectFinish(String s, int i, String s1, String s2) {
                                        if (i == 1000) {

                                            verify(s, s2.getBytes());
                                        }
                                    }
                                });
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, byte[] responseBody) {
                Log.w("onFailure", "statusCode=" + statusCode + ",responseBody" + new String(responseBody));
                LoadingDialogAnim.dismiss(getActivity());
                ToastUtils.showToast("身份证错误");
            }
        });
    }

    private void gotoFinish() {
        mPresent.ajaxCashMoney(user_id, help_need_id);
    }
}
