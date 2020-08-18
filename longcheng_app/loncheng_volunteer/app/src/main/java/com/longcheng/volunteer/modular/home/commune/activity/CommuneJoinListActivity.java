package com.longcheng.volunteer.modular.home.commune.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseListActivity;
import com.longcheng.volunteer.modular.home.commune.adapter.CommuneJoinListAdapter;
import com.longcheng.volunteer.modular.home.commune.bean.CommuneAfterBean;
import com.longcheng.volunteer.modular.home.commune.bean.CommuneDataBean;
import com.longcheng.volunteer.modular.home.commune.bean.CommuneListDataBean;
import com.longcheng.volunteer.modular.home.commune.bean.CommuneItemBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditThumbDataBean;
import com.longcheng.volunteer.utils.PriceUtils;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.sharedpreferenceutils.UserUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 加入公社
 */
public class CommuneJoinListActivity extends BaseListActivity<CommuneContract.View, CommunePresenterImp<CommuneContract.View>> implements CommuneContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_member)
    TextView tvMember;
    @BindView(R.id.iv_memberarrow)
    ImageView ivMemberarrow;
    @BindView(R.id.layout_member)
    LinearLayout layoutMember;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.iv_likearrow)
    ImageView ivLikearrow;
    @BindView(R.id.layout_like)
    LinearLayout layoutLike;
    @BindView(R.id.tv_engry)
    TextView tvEngry;
    @BindView(R.id.iv_engryarrow)
    ImageView ivEngryarrow;
    @BindView(R.id.layout_engry)
    LinearLayout layoutEngry;
    @BindView(R.id.join_listview)
    ListView joinListview;


    private String user_id;

    /**
     * 社员数    1：由低到高 2：由高到最低
     */
    private int member = 0;
    /**
     * 被赞数 2-降序 1-升序
     */
    private int like = 0;

    /**
     * 能量 2-降序 1-升序
     */
    private int engry = 2;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.layout_member:
                if (member != 0) {
                    if (member == 2) {//降序-->升序
                        member = 1;
                        ivMemberarrow.setBackgroundResource(R.mipmap.ic_arrow_up_red);
                    } else if (member == 1) {//升序-->降序
                        member = 2;
                        ivMemberarrow.setBackgroundResource(R.mipmap.ic_arrow_down_red);
                    }
                } else {
                    //未选中时选中
                    member = 1;
                    ivMemberarrow.setBackgroundResource(R.mipmap.ic_arrow_up_red);
                    tvMember.setTextColor(getResources().getColor(R.color.red));

                    tvLike.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
                    ivLikearrow.setBackgroundResource(R.mipmap.ic_arrow_gray);
                    tvEngry.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
                    ivEngryarrow.setBackgroundResource(R.mipmap.ic_arrow_gray);
                }
                like = 0;
                engry = 0;
                initDataAfter();
                break;
            case R.id.layout_like:
                if (like != 0) {
                    if (like == 2) {//升序-->降序
                        like = 1;
                        ivLikearrow.setBackgroundResource(R.mipmap.ic_arrow_up_red);
                    } else if (like == 1) {//降序-->升序
                        like = 2;
                        ivLikearrow.setBackgroundResource(R.mipmap.ic_arrow_down_red);
                    }
                } else {
                    like = 1;//1-降序 2-升序
                    tvLike.setTextColor(getResources().getColor(R.color.red));
                    ivLikearrow.setBackgroundResource(R.mipmap.ic_arrow_up_red);

                    ivMemberarrow.setBackgroundResource(R.mipmap.ic_arrow_gray);
                    tvMember.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
                    tvEngry.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
                    ivEngryarrow.setBackgroundResource(R.mipmap.ic_arrow_gray);
                }
                member = 0;
                engry = 0;
                initDataAfter();
                break;
            case R.id.layout_engry:
                if (engry != 0) {
                    if (engry == 2) {//升序-->降序
                        engry = 1;
                        ivEngryarrow.setBackgroundResource(R.mipmap.ic_arrow_up_red);
                    } else if (engry == 1) {//降序-->升序
                        engry = 2;
                        ivEngryarrow.setBackgroundResource(R.mipmap.ic_arrow_down_red);
                    }
                } else {
                    engry = 1;//1-降序 2-升序
                    tvEngry.setTextColor(getResources().getColor(R.color.red));
                    ivEngryarrow.setBackgroundResource(R.mipmap.ic_arrow_up_red);

                    tvLike.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
                    ivLikearrow.setBackgroundResource(R.mipmap.ic_arrow_gray);
                    ivMemberarrow.setBackgroundResource(R.mipmap.ic_arrow_gray);
                    tvMember.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
                }
                member = 0;
                like = 0;
                initDataAfter();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.commune_joinlist;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        pageTopTvName.setText("加入公社");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        layoutMember.setOnClickListener(this);
        layoutLike.setOnClickListener(this);
        layoutEngry.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        user_id = UserUtils.getUserId(mContext);
        mPresent.getJoinList(user_id, member, like, engry);
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
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            CommuneAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                groupList = mEnergyAfterBean.getGroupList();
                int isLeader = mEnergyAfterBean.getIsLeader();
                mAdapter = new CommuneJoinListAdapter(mContext, groupList, isLeader, mHandler, CLCIKLIKE);
                joinListview.setAdapter(mAdapter);
            }
        }
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

    }

    @Override
    public void JoinCommuneSuccess(EditListDataBean responseBean) {

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
        String status_ = responseBean.getStatus();
        ToastUtils.showToast(responseBean.getMsg());
        if (status_.equals("200")) {
            if (groupList != null && groupList.size() > 0) {
                String num = groupList.get(position).getLikes_number();
                num = PriceUtils.getInstance().gteAddSumPrice(num, "1");
                groupList.get(position).setLikes_number(num);
                mAdapter.setUpdateClickLikeStatus(true);
                mAdapter.setClickPo(position);
                mAdapter.refreshListView(groupList);
            }
        }
    }


    @Override
    public void ListError() {
    }

    List<CommuneItemBean> groupList;
    CommuneJoinListAdapter mAdapter;
    int position;
    protected static final int CLCIKLIKE = 1;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLCIKLIKE:
                    int group_id = msg.arg1;
                    position = msg.arg2;

                    mPresent.ClickLike(user_id, group_id);
                    break;
            }
        }
    };

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
