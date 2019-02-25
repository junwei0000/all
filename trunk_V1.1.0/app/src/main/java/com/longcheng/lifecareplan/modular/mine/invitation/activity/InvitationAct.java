package com.longcheng.lifecareplan.modular.mine.invitation.activity;

import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.mine.invitation.adapter.AdapterInvitation;
import com.longcheng.lifecareplan.modular.mine.invitation.bean.InvitationResultBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import butterknife.BindView;

/**
 * Created by Burning on 2018/9/5.
 */

public class InvitationAct extends BaseListActivity<InvitationContract.View, InvitationImp<InvitationContract.View>> implements InvitationContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout llBack;
    @BindView(R.id.pageTop_tv_name)
    TextView tvTitle;
    @BindView(R.id.ptf_invitation)
    PullToRefreshListView ptrView;
    @BindView(R.id.chocount)
    TextView tvChoCount;
    @BindView(R.id.invitationcount)
    TextView tvInvitationCount;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;

    private int pageIndex = 0;
    private int pageSize = 10;
    private AdapterInvitation adapter;

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.act_invitation;
    }

    @Override
    public void initDataBefore() {
        super.initDataBefore();
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        tvTitle.setText(R.string.invitation_recording);
        notDateCont.setText("暂无邀请记录噢~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.my_nodata_icon);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void initDataAfter() {
        adapter = new AdapterInvitation(mContext);
        ptrView.setAdapter(adapter);
        refresh(0);
    }

    @Override
    public void setListener() {
        llBack.setOnClickListener(this);
        ScrowUtil.listViewConfigAll(ptrView);
        ptrView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refresh(0);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refresh(pageIndex);
            }
        });
    }

    private void refresh(int pageIndex_) {
        mPresent.doRefresh(pageIndex_ + 1, pageSize, UserUtils.getUserId(mContext), UserUtils.getToken());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
        }
    }

    @Override
    protected InvitationImp<InvitationContract.View> createPresent() {
        return new InvitationImp<>(mContext, this);
    }

    @Override
    public void onSuccess(InvitationResultBean responseBean, int pageIndex_) {
        ListUtils.getInstance().RefreshCompleteL(ptrView);
        //获取到数据了
        if (responseBean != null && responseBean.getData() != null) {
            int listSize = responseBean.getData().getList() == null ? 0 : responseBean.getData().getList().size();
            refreshText(responseBean.getData().getInvitationCount(), responseBean.getData().getChoCount());
            boolean isRefresh = pageIndex_ == 1;
            if (listSize == 0) {
                ScrowUtil.listViewDownConfig(ptrView);
                if (isRefresh) {
                    showEmptyView(true);
                } else {
                    showNoMoreData(true, ptrView.getRefreshableView());
                }
                return;
            }
            if (listSize < pageSize) {
                ScrowUtil.listViewDownConfig(ptrView);
                //当前加载的数据不足一页时候，提示footer
                showNoMoreData(true, ptrView.getRefreshableView());
            }

            if (pageIndex_ == 1) {//刷新
                pageIndex = pageIndex_;
                adapter.refresh(responseBean.getData().getList(), true);
                showEmptyView(false);
                if (listSize == pageSize) {
                    ScrowUtil.listViewConfigAll(ptrView);
                    //刷新成功，可能还有下一页的时候，需要先隐藏这个提示footer
                    //非刷新操作的时候，不做任何操作
                    showNoMoreData(false, ptrView.getRefreshableView());
                }
            } else {
                pageIndex++;
                adapter.refresh(responseBean.getData().getList(), false);
            }
        } else if (pageIndex_ == 1) {
            ScrowUtil.listViewDownConfig(ptrView);
            showEmptyView(true);
        }
    }

    private void refreshText(int invitationCount, int choCount) {
        String invitationStr = CommonUtil.intToStringNum(invitationCount);

        SpannableStringBuilder ssbInvitation = new SpannableStringBuilder();
        ssbInvitation.append(getString(R.string.invitation_count, invitationStr));
        ssbInvitation.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), 3, 3 + invitationStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvInvitationCount.setText(ssbInvitation);

        String choStr = CommonUtil.intToStringNum(choCount);
        SpannableStringBuilder ssbCho = new SpannableStringBuilder();
        ssbCho.append(getString(R.string.invitation_cho_count, choStr));
        ssbCho.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), 5, 5 + choStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvChoCount.setText(ssbCho);
    }

    private void showEmptyView(boolean empty) {
        ptrView.setVisibility(empty ? View.GONE : View.VISIBLE);
        layoutNotdate.setVisibility(empty ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onError(String code) {
        ListUtils.getInstance().RefreshCompleteL(ptrView);
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
