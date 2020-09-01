package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleListProgressUtils;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.CurrentHelpNeed;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.HeloneedBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.HeloneedIndexBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.Img;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.LoveBroadcasts;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;
import com.longcheng.lifecareplan.widget.view.FooterNoMoreData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProgressFragment extends BaseListFrag<ProgressContract.View, ProgressPresenterImp<ProgressContract.View>> implements ProgressContract.View {
    @BindView(R.id.vp)
    ViewFlipper vp;
    @BindView(R.id.lv_progress)
    PullToRefreshListView lv_progress;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.help_et_search)
    SupplierEditText help_et_search;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
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
    @BindView(R.id.rl_bootom)
    RelativeLayout rl_bootom;

    @BindView(R.id.layout_item_energy)
    LinearLayout layout_item_energy;
    @BindView(R.id.item_pb_lifeenergynum)
    NumberProgressBar item_pb_lifeenergynum;
    @BindView(R.id.item_pb_numne)
    TextView item_pb_numne;
    @BindView(R.id.tv_absorbed)
    TextView tv_absorbed;
    @BindView(R.id.tv_target)
    TextView tv_target;
    @BindView(R.id.item_tv_typetitle)
    TextView item_tv_typetitle;
    private String user_id;
    private HeloneedBean mEnergyAfterBean;
    private int page = 1;
    private List<LoveBroadcasts> topmsg;
    private List<HeloneedBean.UserBean> mAllList = new ArrayList<>();
    private ImageLoader asyncImageLoader;
    private LifeStyleListProgressUtils mProgressUtils;
    private boolean isSearch = false;

    public static ProgressFragment newInstance() {
        ProgressFragment fragment = new ProgressFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_progress;
    }

    @Override
    protected View getFooterView() {
        FooterNoMoreData view = new FooterNoMoreData(mContext);
        view.showContJiJu(true);
        return view;
    }

    @Override
    public void initView(View view) {
        mProgressUtils = new LifeStyleListProgressUtils(getActivity());
        topmsg = new ArrayList<>();
        tv_search.setOnClickListener(this);
        rl_bootom.setOnClickListener(this);
        layout_item_energy.setVisibility(View.GONE);
        asyncImageLoader = new ImageLoader(mContext, "headImg");
        user_id = UserUtils.getUserId(mContext);

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
                    LogUtils.e("mAllList" + mAllList.get(position - 1).getHelp_need_id());
                    Intent intent = new Intent(mActivity, EmergencyDetailActivity.class);
                    intent.putExtra("Help_need_id", mAllList.get(position - 1).getHelp_need_id());
                    intent.putExtra("head", mAllList.get(position - 1).getUser_avatar());
                    intent.putExtra("status", mAllList.get(position - 1).getStatus());
                    intent.putExtra("jieqi", mAllList.get(position - 1).getUser_branch_info());
                    intent.putExtra("where", "1");
                    intent.putExtra("img", (Serializable) mAllList.get(position - 1).getUser_identity_flag());
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);


                }
            }
        });
    }

    private void getData(int p) {
        isSearch = !help_et_search.getText().toString().equals("");

        mPresent.helpneed_index(user_id, 1, help_et_search.getText().toString(), p, pageSize);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData(1);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                isSearch = !help_et_search.getText().toString().equals("");

                mPresent.helpneed_index(user_id, 1, help_et_search.getText().toString(), 1, pageSize);
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
    protected ProgressPresenterImp<ProgressContract.View> createPresent() {
        return new ProgressPresenterImp<>(mContext, this);
    }

    boolean refreshStatus = false;
    ProgressAdapter mAdapter;

    @Override
    public void ListSuccess(HeloneedIndexBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteL(lv_progress);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                help_et_search.setHint("进行中" + mEnergyAfterBean.getIngCount() + "," + "已完成" + mEnergyAfterBean.getComCount());
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
                    mAdapter = new ProgressAdapter(mActivity, mEnergyAfterBean.getList(), "bless_1824", isSearch);
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
    public void ListError() {

    }


    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        refreshStatus = false;
        LoadingDialogAnim.dismiss(mContext);
    }

    private void fillView(List<LoveBroadcasts> topmsg) {
        vp.removeAllViews();
        for (int i = 0; i < topmsg.size(); i++) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.text_item3, null);
            TextView textView = view.findViewById(R.id.tv_cont);
            ImageView iv_head = view.findViewById(R.id.iv_head);
            TextView tv_cont2 = view.findViewById(R.id.tv_cont2);
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

        if (need.getUser_name() == null || need.getUser_name().equals("null") || need.getUser_name().equals("")) {
            layout_item_energy.setVisibility(View.GONE);
        } else {
            layout_item_energy.setVisibility(View.VISIBLE);
            if (isSearch) {
                item_tv_num.setText("");
            } else {
                item_tv_num.setText(need.getRanking());
            }

            String name = CommonUtil.setName(need.getUser_name());
            item_tv_name.setText(name);
            item_tv_jieeqi.setText(need.getUser_branch_info());
            tv_absorbed.setText("已收能量" + CommonUtil.removeTrim(need.getObtain_super_ability()));
            tv_target.setText("目标能量" + CommonUtil.removeTrim(need.getSuper_ability()));
            item_tv_typetitle.setText(need.getCreate_time());
            asyncImageLoader.DisplayImage(need.getUser_avatar(), item_iv_thumb);
            List<Img> identity_img = need.getUser_identity_flag();
            LogUtils.e("22222" + need.getObtain_super_ability());
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
        item_pb_lifeenergynum.setProgress(need.getProgress());
        mProgressUtils.showNum(need.getProgress(), item_pb_lifeenergynum, item_pb_numne);
        item_pb_lifeenergynum.setReachedBarColor(getResources().getColor(R.color.red));
        ColorChangeByTime.getInstance().changeDrawableToClolor(getActivity(), item_pb_numne, R.color.red);

    }
}
