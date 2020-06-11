package com.longcheng.lifecareplan.modular.mine.relief;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;
import com.longcheng.lifecareplan.widget.view.FooterNoMoreData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FinishFragment extends BaseListFrag<FinishContract.View, FinishPresenterImp<FinishContract.View>> implements FinishContract.View {
    @BindView(R.id.vp)
    ViewFlipper vp;

    ProgressAdapter mAdapter;
    @BindView(R.id.help_et_search)
    SupplierEditText helpEtSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.lv_progress)
    PullToRefreshListView pullRefresh;

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

    int page = 1;
    private final String type = "2";
    List<ReliefItemBean.DataBean> allList = new ArrayList<>();

    @BindView(R.id.llserach)
    LinearLayout llserach;
    @BindView(R.id.item_tv_num)
    TextView itemTvNum;
    @BindView(R.id.item_iv_thumb)
    ImageView itemIvThumb;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.item_tv_jieeqi)
    TextView itemTvJieeqi;
    @BindView(R.id.item_tv_typetitle)
    TextView itemTvTypetitle;
    @BindView(R.id.item_layout_shenfen)
    LinearLayout itemLayoutShenfen;
    @BindView(R.id.item_pb_lifeenergynum)
    NumberProgressBar itemPbLifeenergynum;
    @BindView(R.id.item_pb_numne)
    TextView itemPbNumne;
    @BindView(R.id.relat_cn)
    RelativeLayout relatCn;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.tv_absorbed)
    TextView tvAbsorbed;
    @BindView(R.id.tv_target)
    TextView tvTarget;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.item_tv_num1)
    TextView itemTvNum1;
    @BindView(R.id.item_iv_thumb1)
    ImageView itemIvThumb1;
    @BindView(R.id.item_tv_name1)
    TextView itemTvName1;
    @BindView(R.id.item_tv_jieeqi1)
    TextView itemTvJieeqi1;
    @BindView(R.id.item_layout_shenfen1)
    LinearLayout itemLayoutShenfen1;
    @BindView(R.id.item_tv_bank)
    TextView itemTvBank;
    @BindView(R.id.item_layout_rank1)
    LinearLayout itemLayoutRank1;
    @BindView(R.id.rl_bottom1)
    RelativeLayout rlBottom1;
    Unbinder unbinder;


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
        return R.layout.fragment_relief;
    }

    @Override
    protected View getFooterView() {
        FooterNoMoreData view = new FooterNoMoreData(mContext);
        view.showDividerTop(true);
        return view;
    }

    @Override
    public void initView(View view) {
        notDateCont.setText("暂无数据~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.my_nodata_icon);

        if (mAdapter == null) mAdapter = new ProgressAdapter(mActivity);
        pullRefresh.setAdapter(mAdapter);

        pullRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getListData(page, type, "");
            }


            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.d(TAG, "onPullUpToRefresh: page" + page);
                getListData(page, type, "");
            }
        });
        pullRefresh.getRefreshableView().setOnItemClickListener((parent, view1, position, id) -> {

            if (allList != null && allList.size() > 0 && (position - 1) < allList.size()) {
                ReliefItemBean.DataBean dataBean = allList.get(position - 1);
                getActivity().startActivity(new Intent(getActivity(), ReliefDetailsActivity.class)
                        .putExtra("userid", dataBean.uid)
                );
            }
        });

        tvSearch.setOnClickListener(v -> getListData(1, type, helpEtSearch.getText().toString()));


    }

    private void getListData(int page, String type, String keyworld) {
        mPresent.getLitsData((String) SharedPreferencesHelper.get(mActivity, "user_id", ""), ExampleApplication.token, String.valueOf(page), type, keyworld);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
        getListData(1, type, "");
        //底部浮层
        mPresent.getReliefBottom(type);
    }

    @Override
    protected FinishPresenterImp<FinishContract.View> createPresent() {
        return new FinishPresenterImp<>(mContext, this);
    }


    @Override
    public void getReliefListSuccess(List<ReliefItemBean.DataBean> data, String currentPage) {
        Integer backPage = Integer.valueOf(currentPage);
        ListUtils.getInstance().RefreshCompleteL(pullRefresh);
        int size = data == null ? 0 : data.size();
        //顶部提现轮播
        fillView(data);
        if (backPage == 1) {
            page = backPage;
            allList.clear();
        }
        page++;
        allList.addAll(data);
        mAdapter.setNewData(allList);
        mAdapter.notifyDataSetChanged();

        checkLoadOver(size);

    }

    @Override
    public void bottomInfoSuccess(ReliefBottomInfoBean.DataBean data, String type) {

        if (data == null) return;
        String avatar = TextUtils.isEmpty(data.avatar) ? "" : data.avatar;
        String enegry = TextUtils.isEmpty(data.enegry) ? "0" : data.enegry;
        String jieqi_name = TextUtils.isEmpty(data.jieqi_name) ? "" : data.jieqi_name;
        String jieqi_branch_name = TextUtils.isEmpty(data.jieqi_branch_name) ? "" : data.jieqi_branch_name;
        String user_name = TextUtils.isEmpty(data.user_name) ? "" : data.user_name;
        String rank = TextUtils.isEmpty(data.rank) ? "" : data.rank;
        String enegryall = TextUtils.isEmpty(data.enegryall) ? "" : data.enegryall;
        String start_time = TextUtils.isEmpty(data.start_time) ? "" : data.start_time;

        if ("2".equals(type)) {
            rlBottom1.setVisibility(View.VISIBLE);
        } else {
            rlBottom1.setVisibility(View.GONE);
        }
        if (Integer.valueOf(rank) >= 100) {
            itemTvNum1.setText("999+");
        } else {
            itemTvNum1.setText(rank);
        }
        GlideDownLoadImage.getInstance().loadCircleImageCommune(mContext, avatar, itemIvThumb1);
        itemTvName1.setText(user_name);
        itemTvJieeqi1.setText(jieqi_branch_name + "." + jieqi_name);
        List<String> images = data.images;
        itemLayoutShenfen1.removeAllViews();
        if (images != null && images.size() > 0) {
            for (String url : images) {
                LinearLayout linearLayout = new LinearLayout(mContext);
                ImageView imageView = new ImageView(mContext);
                int dit = DensityUtil.dip2px(mContext, 16);
                int jian = DensityUtil.dip2px(mContext, 3);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(mContext, url, imageView);
                linearLayout.addView(imageView);
                itemLayoutShenfen1.addView(linearLayout);
            }
        }

        //可以提现
//        if (Integer.valueOf(enegry) >= 9000) {
        itemLayoutRank1.setVisibility(View.VISIBLE);
//        } else {
//            itemLayoutRank1.setVisibility(View.GONE);
//        }

    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.listViewDownConfig(pullRefresh);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true, pullRefresh.getRefreshableView());
            }
        } else {
            ScrowUtil.listViewConfigAll(pullRefresh);
        }
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(getActivity());
    }

    private void fillView(List<ReliefItemBean.DataBean> dataBeans) {
        vp.removeAllViews();
        if (dataBeans != null && dataBeans.size() > 0) {
            vp.setVisibility(View.VISIBLE);
            for (int i = 0; i < dataBeans.size(); i++) {
                View view = LayoutInflater.from(mActivity).inflate(R.layout.text_item3, null);
                TextView textView = view.findViewById(R.id.tv_cont);
                ImageView iv_head = view.findViewById(R.id.iv_head);
                GlideDownLoadImage.getInstance().loadCircleImageCommune(mActivity, dataBeans.get(i).avatar, iv_head);
                textView.setTextColor(mActivity.getResources().getColor(R.color.white));
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                textView.setGravity(Gravity.CENTER_VERTICAL);
                String name = TextUtils.isEmpty(dataBeans.get(i).name) ? "" : dataBeans.get(i).name;
                String number = TextUtils.isEmpty(dataBeans.get(i).number) ? "" : dataBeans.get(i).number;
                String desc = name + "<font color='#FFFFFF'>" + "\u2000" + "获得超级生命能量</font>" + "<font color='#f6fe7c'>" + "\u2000" + number + "</font>";
                textView.setText(Html.fromHtml(desc));
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

        } else {
            vp.setVisibility(View.GONE);

        }
    }

    @OnClick(R.id.item_tv_bank)
    public void onViewClicked() {
        mPresent.extractEnergy();
    }
}
