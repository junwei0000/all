package com.longcheng.lifecareplan.modular.mine.doctor.doctor.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.adapter.AiYouSelectAdapter;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.adapter.VolSelectAdapter;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.DocRewardListDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.VolSearchDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.activity.BasicContract;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.activity.BasicListActivity;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.activity.BasicPresenterImp;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean.AYApplyListDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean.BasicInfoListDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 爱友选择
 */
public class AiYouSelectActivity extends BaseListActivity<BasicContract.View, BasicPresenterImp<BasicContract.View>> implements BasicContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.lv_data)
    PullToRefreshListView lvData;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont_title)
    TextView notDateContTitle;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.tv_bottom)
    TextView tvBottom;
    private int page = 0;
    private int pageSize = 20;


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.not_date_btn:
                intent = new Intent(mContext, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + Config.BASE_HEAD_URL + "home/knp/patientadd");
                startActivity(intent);
                break;
            case R.id.tv_bottom:
                if (allhelpList != null && allhelpList.size() > 0) {
                    BasicInfoListDataBean.BasicItemBean info = allhelpList.get(selectposition);
                    String userid = info.getUser_id();
                    if (TextUtils.isEmpty(userid) || (!TextUtils.isEmpty(userid) && userid.equals("0"))) {
                        setDialog(info.getKnp_msg_patient_id());
                    } else {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("knp_msg_patient_id", info.getKnp_msg_patient_id());
                            jsonObject.put("user_name", info.getPatient_name());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("registerHandler", "" + jsonObject);
                        ConfigUtils.getINSTANCE().function.onCallBack(jsonObject.toString());
                        doFinish();
                    }
                }
                break;
        }
    }

    MyDialog selectDialog;

    public void setDialog(String knp_msg_patient_id) {
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
            TextView tv_title = selectDialog.findViewById(R.id.tv_title);
            tv_title.setText("请完善爱友资料后在选择");
            TextView tv_off = selectDialog.findViewById(R.id.tv_off);
            TextView tv_sure = selectDialog.findViewById(R.id.tv_sure);
            tv_sure.setText("编辑资料");
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
                    Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + Config.BASE_HEAD_URL + "home/knp/patientedit/knp_msg_patient_id/" + knp_msg_patient_id);
                    startActivity(intent);
                }
            });
        } else {
            selectDialog.show();
        }


    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.doctor_volout_list;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("爱友选择");
        tvBottom.setText("确认");
        pagetopLayoutLeft.setOnClickListener(this);
        tvBottom.setOnClickListener(this);
        notDateBtn.setOnClickListener(this);
        notDateImg.setBackgroundResource(R.mipmap.not_volout_img);
        notDateCont.setText("您还未添加爱友~");
        notDateBtn.setVisibility(View.VISIBLE);
        notDateBtn.setText("添加爱友");
        lvData.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (allhelpList != null && allhelpList.size() > 0 && position <= allhelpList.size()) {
                    selectposition = position - 1;
                    for (BasicInfoListDataBean.BasicItemBean info : allhelpList) {
                        info.setSelectstatus(0);
                    }
                    allhelpList.get(selectposition).setSelectstatus(1);
                    mDaiFuAdapter.refreshListView(allhelpList);
                }
            }
        });
    }

    int selectposition;

    @Override
    public void initDataAfter() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList(1);
    }

    private void getList(int page) {
        mPresent.getBasicInfoList(UserUtils.getUserId(mContext), page, pageSize);
    }


    @Override
    protected BasicPresenterImp<BasicContract.View> createPresent() {
        return new BasicPresenterImp<>(this);
    }

    boolean refreshStatus = false;

    @Override
    public void showDialog() {
        if (!refreshStatus)
            LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        refreshStatus = false;
        LoadingDialogAnim.dismiss(mContext);
    }

    AiYouSelectAdapter mDaiFuAdapter;
    ArrayList<BasicInfoListDataBean.BasicItemBean> allhelpList = new ArrayList<>();


    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.listViewDownConfig(lvData);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true, lvData.getRefreshableView());
            }
        } else {
            ScrowUtil.listViewConfigAll(lvData);
        }
    }

    @Override
    public void ListSuccess(BasicInfoListDataBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteL(lvData);
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            List<BasicInfoListDataBean.BasicItemBean> helpList = responseBean.getData();
            int size = helpList == null ? 0 : helpList.size();
            if (backPage == 1) {
                mDaiFuAdapter = null;
                allhelpList.clear();
                showNoMoreData(false, lvData.getRefreshableView());
            }
            if (size > 0) {
                allhelpList.addAll(helpList);
            }
            if (mDaiFuAdapter == null) {
                mDaiFuAdapter = new AiYouSelectAdapter(mContext, helpList);
                lvData.setAdapter(mDaiFuAdapter);
            } else {
                mDaiFuAdapter.reloadListView(helpList, false);
            }
            page = backPage;
            checkLoadOver(size);
        }
        ListUtils.getInstance().setNotDateViewL(mDaiFuAdapter, layoutNotdate);
    }

    @Override
    public void ayapplySuccess(AYApplyListDataBean responseBean, int back_page) {

    }

    public void ListError() {
        ListUtils.getInstance().RefreshCompleteL(lvData);
        ListUtils.getInstance().setNotDateViewL(mDaiFuAdapter, layoutNotdate);
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
