package com.longcheng.volunteer.modular.home.commune.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseListActivity;
import com.longcheng.volunteer.modular.helpwith.applyhelp.activity.ValueSelectUtils;
import com.longcheng.volunteer.modular.home.commune.bean.CommuneAfterBean;
import com.longcheng.volunteer.modular.home.commune.bean.CommuneDataBean;
import com.longcheng.volunteer.modular.home.commune.bean.CommuneItemBean;
import com.longcheng.volunteer.modular.home.commune.bean.CommuneListDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditThumbDataBean;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.glide.GlideDownLoadImage;
import com.longcheng.volunteer.utils.myview.SupplierEditText;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesHelper;

import java.util.List;

import butterknife.BindView;

/**
 * 创建大队
 */
public class CreateTeamActivity extends BaseListActivity<CommuneContract.View, CommunePresenterImp<CommuneContract.View>> implements CommuneContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_teamname)
    TextView tvTeamname;
    @BindView(R.id.tv_teamselect)
    TextView tvTeamselect;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.et_phone)
    SupplierEditText etPhone;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.tv_create)
    TextView tvCreate;
    @BindView(R.id.item_iv_thumb)
    ImageView itemIvThumb;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.item_tv_phone)
    TextView itemTvPhone;
    @BindView(R.id.item_tv_num)
    TextView itemTvNum;
    @BindView(R.id.layout_searchresult)
    LinearLayout layoutSearchresult;
    @BindView(R.id.item_tv_notsearch)
    TextView item_tv_notsearch;

    private String user_id;
    private int team_id;
    String type = "";
    private List<CommuneItemBean> teamNameList;
    private String team_user_id, solar_terms_name, solar_terms_en;
    private ValueSelectUtils mValueSelectUtils;
    private String[] values;
    /**
     * 是否选择大队长
     */
    boolean selectNameStatus = false;

    @Override
    public void onClick(View v) {
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_teamselect:
                if (mValueSelectUtils == null) {
                    mValueSelectUtils = new ValueSelectUtils(mActivity, mHandler, VALUE);
                }
                if (values != null && values.length > 0) {
                    mValueSelectUtils.showDialog(values, "选择大队名称");
                }
                break;
            case R.id.tv_search:
                String phone = etPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showToast("手机号不能为空");
                    break;
                }
                if (phone.length() < 11) {
                    ToastUtils.showToast("请输入正确的手机号");
                    break;
                }
                mPresent.CreateTeamSearch(user_id, phone);
                break;
            case R.id.tv_create:
                if (selectNameStatus && !TextUtils.isEmpty(solar_terms_name)) {
                    mPresent.CreateTeamSaveInfo(user_id, team_id, solar_terms_name, solar_terms_en, team_user_id);
                }
                break;
        }
    }

    private final int VALUE = 2;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case VALUE:
                    int selectindex = bundle.getInt("selectindex");
                    if (teamNameList != null && teamNameList.size() > 0) {
                        solar_terms_name = teamNameList.get(selectindex).getText();
                        solar_terms_en = teamNameList.get(selectindex).getValue();
                        tvTeamname.setText(solar_terms_name);
                        setBtn();
                    }
                    break;
            }
        }
    };

    @Override
    protected CommunePresenterImp<CommuneContract.View> createPresent() {
        return new CommunePresenterImp<>(mContext, this);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.commune_createteam;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        Intent intent = getIntent();
        team_id = intent.getIntExtra("team_id", 0);
        type = intent.getStringExtra("type");
        if (type.equals("create")) {
            pageTopTvName.setText("创建大队");
            tvCreate.setText("创建大队");
            tvCreate.setBackgroundColor(getResources().getColor(R.color.login_hint_color));
        } else {
            pageTopTvName.setText("编辑大队");
            tvCreate.setText("保存");
            tvCreate.setBackgroundColor(getResources().getColor(R.color.red));
        }
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvTeamselect.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        tvCreate.setOnClickListener(this);
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(etPhone.getText())) {
                    initBtn();
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
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        mPresent.getCreateTeamInfo(user_id, team_id);
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
    public void MineRankListSuccess(CommuneListDataBean responseBean, int backpage) {

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
    public void ClickLikeSuccess(EditListDataBean responseBean) {

    }

    @Override
    public void editAvatarSuccess(EditThumbDataBean responseBean) {

    }

    @Override
    public void GetCreateTeamInfoSuccess(CommuneListDataBean responseBean) {
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            CommuneAfterBean mCommuneAfterBean = responseBean.getData();
            if (mCommuneAfterBean != null) {
                teamNameList = mCommuneAfterBean.getTeamNameList();
                if (teamNameList != null && teamNameList.size() > 0) {
                    values = new String[teamNameList.size()];
                    for (int i = 0; i < teamNameList.size(); i++) {
                        values[i] = teamNameList.get(i).getText();
                    }
                }
                if (!type.equals("create")) {
                    CommuneItemBean teamInfo = mCommuneAfterBean.getTeamInfo();
                    if (teamInfo != null) {
                        solar_terms_name = teamInfo.getTeam_name();
                        solar_terms_en = teamInfo.getSolar_terms_en();
                        tvTeamname.setText(solar_terms_name);
                    }
                    teamLeaderUser = mCommuneAfterBean.getTeamLeaderUser();
                    if (teamLeaderUser != null && !TextUtils.isEmpty(teamLeaderUser.getUser_name())) {
                        selectNameStatus = true;
                        team_user_id = teamLeaderUser.getUser_id();
                        String user_name = teamLeaderUser.getUser_name();
                        tvUsername.setText("当前大队长：" + user_name);
                    }

                    /**
                     * 大队可以编辑名称条件：
                     1.$teamRotation < $rotation && $teamInfo['solar_terms_en'] == ''
                     2.$teamRotation >= $rotation
                     */
                    int teamRotation = mCommuneAfterBean.getTeamRotation();
                    int rotation = mCommuneAfterBean.getRotation();
                    if (teamRotation >= rotation || (teamRotation < rotation && TextUtils.isEmpty(solar_terms_en))) {
                        tvTeamselect.setVisibility(View.VISIBLE);
                    } else {
                        tvTeamselect.setVisibility(View.INVISIBLE);
                    }

                }
            }
        }
    }

    CommuneItemBean teamLeaderUser;

    @Override
    public void CreateTeamSuccess(EditListDataBean responseBean) {
        String status_ = responseBean.getStatus();
        ToastUtils.showToast(responseBean.getMsg());
        if (status_.equals("400")) {
        } else if (status_.equals("200")) {
            doFinish();
        }
    }

    @Override
    public void CreateTeamSearchSuccess(CommuneDataBean responseBean) {
        initBtn();
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            item_tv_notsearch.setText(responseBean.getMsg());
            item_tv_notsearch.setVisibility(View.VISIBLE);
        } else if (status_.equals("200")) {
            CommuneItemBean mCommuneItemBean = responseBean.getData();
            if (mCommuneItemBean != null && !TextUtils.isEmpty(mCommuneItemBean.getUser_id())) {
                GlideDownLoadImage.getInstance().loadCircleImage(mContext, mCommuneItemBean.getAvatar(), itemIvThumb);
                itemTvName.setText(mCommuneItemBean.getUser_name());
                itemTvPhone.setText(mCommuneItemBean.getPhone());
                int is_head = mCommuneItemBean.getIs_head();//是否大队长 0：否 1：是
                itemTvNum.setText("");
                if (is_head == 1) {
                    itemTvNum.setText("大队长");
                    itemTvNum.setBackgroundResource(0);
                } else {
                    itemTvNum.setText("");
                    itemTvNum.setBackgroundResource(R.mipmap.thecommune_position_grey);
                }
                layoutSearchresult.setVisibility(View.VISIBLE);
                layoutSearchresult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (is_head == 1) {
                            ToastUtils.showToast("在职大队长不能选择");
                        } else {
                            selectNameStatus = true;
                            team_user_id = mCommuneItemBean.getUser_id();
                            setBtn();
                            itemTvNum.setBackgroundResource(R.mipmap.thecommune_position_blue);
                        }
                    }
                });
            } else {
                item_tv_notsearch.setText("未搜索到用户");
                item_tv_notsearch.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initBtn() {
        layoutSearchresult.setVisibility(View.INVISIBLE);
        item_tv_notsearch.setVisibility(View.INVISIBLE);
        selectNameStatus = false;
        if (!type.equals("create")) {
            selectNameStatus = true;
            team_user_id = teamLeaderUser.getUser_id();
        }
        setBtn();
    }

    private void setBtn() {
        if (selectNameStatus && !TextUtils.isEmpty(solar_terms_name)) {
            tvCreate.setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            tvCreate.setBackgroundColor(getResources().getColor(R.color.login_hint_color));
        }
    }

    @Override
    public void ListError() {

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
