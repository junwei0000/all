package com.longcheng.lifecareplan.modular.mine.relationship.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.helpwith.myDedication.activity.MyDeH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.myGratitude.activity.MyGraH5Activity;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.modular.mine.relationship.bean.RelationshipBean;
import com.longcheng.lifecareplan.modular.mine.relationship.bean.RelationshipBook;
import com.longcheng.lifecareplan.modular.mine.relationship.bean.RelationshipUser;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;

/**
 * 人情账
 * Created by Burning on 2018/9/1.
 */

public class RelationshipAccountAct extends BaseActivityMVP<RelationshipContract.View, RelationshipImp<RelationshipContract.View>> implements RelationshipContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout llBack;
    @BindView(R.id.pageTop_tv_name)
    TextView tvTitle;

    @BindView(R.id.tvname)
    TextView tvName;
    @BindView(R.id.tvdatecreate)
    TextView tvDateCreate;
    @BindView(R.id.tvdatecho)
    TextView tvDateCho;
    @BindView(R.id.tvdays)
    TextView tvDays;
    @BindView(R.id.tvhelpnum)
    TextView tvHelpNum;
    @BindView(R.id.tvenergy)
    TextView tvEnergy;
    @BindView(R.id.tvdatebehelp)
    TextView tvDateHelp;
    @BindView(R.id.tvhelpmenum)
    TextView tvHelpMeNum;
    @BindView(R.id.tvhelpmeenergy)
    TextView tvHelpMeEnergy;
    @BindView(R.id.tvmycontribution)
    TextView tvmycontribution;
    @BindView(R.id.tvmybenefactor)
    TextView tvmybenefactor;


    @Override
    protected RelationshipImp<RelationshipContract.View> createPresent() {
        return new RelationshipImp<>(mContext, this);
    }

    @Override
    public void onSuccess(RelationshipBean responseBean) {
        Log.e("aaa", "11111 onSuccess " + responseBean.getData().getBook().getName());
        RelationshipBook book = responseBean.getData().getBook();
        RelationshipUser user = responseBean.getData().getUser();
        String red = "#ff0000";
        tvName.setText(Html.fromHtml("亲爱的" + (user.isCHO() ? "CHO " : "社员 ") + CommonUtil.getHtmlContent(red, user.getUser_name()) + " 您好:"));
        tvDateCreate.setText(Html.fromHtml(CommonUtil.getHtmlContent(red, user.getRegister_time()) + "您入驻“健康互祝公社”"));
        if (user.isCHO()) {
            tvDateCho.setVisibility(View.VISIBLE);
            tvDateCho.setText(Html.fromHtml(CommonUtil.getHtmlContent(red, user.getCho_start_date()) + "正式成为了CHO"));
        } else {
            tvDateCho.setVisibility(View.GONE);
        }
        tvDays.setText(Html.fromHtml("您入驻“健康互祝公社”" + CommonUtil.getHtmlContent(red, String.valueOf(user.getRegisterDays())) + "天里"));
        if (book.getHelpNum() > 0) {
            tvHelpNum.setVisibility(View.VISIBLE);
            tvEnergy.setVisibility(View.VISIBLE);
            tvHelpNum.setText(Html.fromHtml("您帮助过" + CommonUtil.getHtmlContent(red, CommonUtil.intToStringNum(book.getHelpNum())) + "人"));
            tvEnergy.setText(Html.fromHtml("您让" + CommonUtil.getHtmlContent(red, CommonUtil.intToStringNum(book.getHelpEnergy())) + "生命能量流动起来"));
        } else {
            tvHelpNum.setVisibility(View.GONE);
            tvEnergy.setVisibility(View.GONE);
        }

        if (book.getBeHelpedNum() > 0) {
            tvDateHelp.setVisibility(View.VISIBLE);
            tvHelpMeNum.setVisibility(View.VISIBLE);
            tvHelpMeEnergy.setVisibility(View.VISIBLE);
            String df = DatesUtils.getInstance().getTimeStampToDate((int) book.getBeHelpedTime(), "yyy年MM月dd日");
            tvDateHelp.setText(Html.fromHtml(CommonUtil.getHtmlContent(red, df) + "您第一次被互祝"));
            tvHelpMeNum.setText(Html.fromHtml("共有" + CommonUtil.getHtmlContent(red, CommonUtil.intToStringNum(book.getBeHelpedNum())) + "人帮助过您"));
            tvHelpMeEnergy.setText(Html.fromHtml("获得" + CommonUtil.getHtmlContent(red, CommonUtil.intToStringNum(book.getHelpMeEnerty())) + "生命能量的祝福"));
        } else {
            tvDateHelp.setVisibility(View.GONE);
            tvHelpMeNum.setVisibility(View.GONE);
            tvHelpMeEnergy.setVisibility(View.GONE);
        }

    }

    @Override
    public void onError(String code) {
        Log.e("aaa", "onError");
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.act_mine_relationship_account;
    }

    @Override
    public void initDataBefore() {
        super.initDataBefore();
    }

    @Override
    public void initView(View view) {
        tvTitle.setText(R.string.relationship_account);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void initDataAfter() {
        mPresent.doRefresh(UserUtils.getUserId(mContext), UserUtils.getToken());
    }

    @Override
    public void setListener() {
        llBack.setOnClickListener(this);
        tvmycontribution.setOnClickListener(this);
        tvmybenefactor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tvmycontribution://我的奉献
                intent = new Intent(mContext, MyDeH5Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + HomeFragment.my_dedication_url);
                startActivity(intent);
                break;
            case R.id.tvmybenefactor://我的恩人
                intent = new Intent(mContext, MyGraH5Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + HomeFragment.my_gratitude_url);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
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
