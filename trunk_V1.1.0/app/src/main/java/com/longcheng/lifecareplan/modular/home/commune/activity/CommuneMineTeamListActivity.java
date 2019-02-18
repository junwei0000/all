package com.longcheng.lifecareplan.modular.home.commune.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.home.commune.adapter.CommuneJoinTeamListAdapter;
import com.longcheng.lifecareplan.modular.home.commune.adapter.CommuneMineTeamListAdapter;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneAfterBean;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneDataBean;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneItemBean;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneListDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditThumbDataBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 我的公社-大队列表
 */
public class CommuneMineTeamListActivity extends BaseListActivity<CommuneContract.View, CommunePresenterImp<CommuneContract.View>> implements CommuneContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;

    @BindView(R.id.join_listview)
    ListView join_listview;

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


    List<CommuneItemBean> teamList;
    CommuneMineTeamListAdapter mAdapter;
    String user_id;
    int group_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.commune_join_teamlist;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        pageTopTvName.setText("公社大队");
        notDateCont.setText("暂无数据~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.my_nodata_icon);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        join_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (teamList != null && teamList.size() > 0) {
                    Intent intent = new Intent(mContext, CommuneMineMemberListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("team_id", teamList.get(position).getTeam_id());
                    intent.putExtra("team_name", teamList.get(position).getTeam_name());
                    startActivity(intent);
                }
            }
        });
    }

    boolean firstComIn = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (!firstComIn) {
            mPresent.getTeamList(user_id, group_id);
        }
    }

    @Override
    public void initDataAfter() {
        user_id = UserUtils.getUserId(mContext);
        Intent intent = getIntent();
        group_id = intent.getIntExtra("group_id", 0);
        mPresent.getTeamList(user_id, group_id);
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
    public void JoinListSuccess(CommuneListDataBean responseBean) {

    }

    @Override
    public void MineCommuneInfoSuccess(CommuneListDataBean responseBean) {

    }

    @Override
    public void MineRankListSuccess(CommuneListDataBean responseBean, int page) {

    }

    @Override
    public void ClickLikeSuccess(EditListDataBean responseBean) {

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
    public void GetTeamListSuccess(CommuneListDataBean responseBean) {
        firstComIn = false;
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            CommuneAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                CommuneItemBean UserInfo = mEnergyAfterBean.getUserInfo();
                int role = 0;
                if (UserInfo != null) {
                    role = UserInfo.getRole();
                }
                teamList = mEnergyAfterBean.getTeamList();
                if (mAdapter == null) {
                    mAdapter = new CommuneMineTeamListAdapter(mContext, teamList, role);
                    join_listview.setAdapter(mAdapter);
                } else {
                    mAdapter.setRole(role);
                    mAdapter.refreshListView(teamList);
                }
            }
            ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
        } else if (status_.equals("499")) {
            UserLoginBack403Utils.getInstance().sendBroadcastLoginBack403();
        }
    }

    @Override
    public void GetMemberListSuccess(CommuneListDataBean responseBean, int backpage) {

    }

    @Override
    public void JoinCommuneSuccess(EditListDataBean responseBean) {

    }

    @Override
    public void ListError() {
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
