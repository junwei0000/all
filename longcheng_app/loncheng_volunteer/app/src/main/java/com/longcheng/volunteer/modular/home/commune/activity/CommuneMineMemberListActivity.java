package com.longcheng.volunteer.modular.home.commune.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseListActivity;
import com.longcheng.volunteer.modular.home.commune.adapter.MembListAdapter;
import com.longcheng.volunteer.modular.home.commune.bean.CommuneAfterBean;
import com.longcheng.volunteer.modular.home.commune.bean.CommuneDataBean;
import com.longcheng.volunteer.modular.home.commune.bean.CommuneItemBean;
import com.longcheng.volunteer.modular.home.commune.bean.CommuneListDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditThumbDataBean;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.DensityUtil;
import com.longcheng.volunteer.utils.ListUtils;
import com.longcheng.volunteer.utils.ScrowUtil;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.myview.SupplierEditText;
import com.longcheng.volunteer.utils.sharedpreferenceutils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的公社-成员列表
 */
public class CommuneMineMemberListActivity extends BaseListActivity<CommuneContract.View, CommunePresenterImp<CommuneContract.View>> implements CommuneContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;


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

    @BindView(R.id.help_et_search)
    SupplierEditText helpEtSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.people_lv)
    PullToRefreshListView peopleLv;

    int page;
    int pageSize = 20;
    private String team_name, search;
    List<CommuneItemBean> AllList = new ArrayList<>();
    MembListAdapter mAdapter;
    String user_id;
    int team_id;
    /**
     * 是否领导 0：否 1：是 是领导的时候不允许加入公社
     */
    private int isLeader;

    @Override
    public void onClick(View v) {
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.tv_search:
                search = helpEtSearch.getText().toString().trim();
                if (TextUtils.isEmpty(search)) {
                    ToastUtils.showToast("请输入姓名/手机号");
                    break;
                }
                getList(1);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.commune_mine_memberlist;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        notDateCont.setText("暂无数据~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.not_searched_img);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        peopleLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getList(page + 1);
            }
        });
        peopleLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (AllList != null && AllList.size() > 0 && position <= AllList.size()) {
                    if (isLeader != 0) {
                        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                        String phone = AllList.get(position - 1).getPhone();
                        if (!TextUtils.isEmpty(phone))
                            DensityUtil.getPhoneToKey(mContext, phone);
                    }

                }
            }
        });
        helpEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                    ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                    search = helpEtSearch.getText().toString();
                    getList(1);
                    return true;
                }
                return false;
            }
        });
        helpEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(helpEtSearch.getText())) {
                    search = helpEtSearch.getText().toString();
                    getList(1);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void initDataAfter() {
        user_id = UserUtils.getUserId(mContext);
        Intent intent = getIntent();
        team_id = intent.getIntExtra("team_id", 0);
        team_name = intent.getStringExtra("team_name");
        pageTopTvName.setText(team_name);
        getList(1);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(helpEtSearch, 20);
    }

    private void getList(int page) {
        mPresent.getMemberList(user_id, team_id, search, page, pageSize);
    }

    @Override
    protected CommunePresenterImp<CommuneContract.View> createPresent() {
        return new CommunePresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public void editAvatarSuccess(EditThumbDataBean responseBean) {

    }

    @Override
    public void GetCreateTeamInfoSuccess(CommuneListDataBean responseBean) {

    }

    @Override
    public void CreateTeamSuccess(EditListDataBean responseBean) {

    }

    @Override
    public void CreateTeamSearchSuccess(CommuneDataBean responseBean) {

    }

    @Override
    public void ClickLikeSuccess(EditListDataBean responseBean) {

    }

    @Override
    public void JoinListSuccess(CommuneListDataBean responseBean) {

    }

    @Override
    public void MineCommuneInfoSuccess(CommuneListDataBean responseBean) {

    }

    @Override
    public void MineRankListSuccess(CommuneListDataBean responseBean, int page) {

    }

    @Override
    public void GetTeamListSuccess(CommuneListDataBean responseBean) {

    }

    @Override
    public void GetMemberListSuccess(CommuneListDataBean responseBean, int backpage) {
        ListUtils.getInstance().RefreshCompleteL(peopleLv);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            CommuneAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                isLeader = mEnergyAfterBean.getIsLeader();
                List<CommuneItemBean> teamMemberList = mEnergyAfterBean.getTeamMemberList();
                int size = teamMemberList == null ? 0 : teamMemberList.size();
                if (backpage == 1) {
                    AllList.clear();
                    mAdapter = null;
                    showNoMoreData(false, peopleLv.getRefreshableView());
                }
                if (size > 0) {
                    AllList.addAll(teamMemberList);
                }
                if (mAdapter == null) {
                    mAdapter = new MembListAdapter(mContext, teamMemberList);
                    peopleLv.setAdapter(mAdapter);
                } else {
                    mAdapter.reloadListView(teamMemberList, false);
                }
                page = backpage;
                checkLoadOver(size);
            }
            ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
        }
    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.listViewDownConfig(peopleLv);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true, peopleLv.getRefreshableView());
            }
        } else {
            ScrowUtil.listViewConfigAll(peopleLv);
        }
    }

    @Override
    public void JoinCommuneSuccess(EditListDataBean responseBean) {

    }

    @Override
    public void ListError() {
        ListUtils.getInstance().RefreshCompleteL(peopleLv);
        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
    }


    private void back() {
        doFinish();
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            back();
        }
        return false;
    }
}
