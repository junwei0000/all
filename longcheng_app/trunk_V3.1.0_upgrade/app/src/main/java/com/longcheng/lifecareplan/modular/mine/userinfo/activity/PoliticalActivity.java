package com.longcheng.lifecareplan.modular.mine.userinfo.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.mine.userinfo.adapter.BingYiAdapter;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditThumbDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetUserSETDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.UserSetBean;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 政治面貌
 */
public class PoliticalActivity extends BaseActivityMVP<UserInfoContract.View, UserInfoPresenterImp<UserInfoContract.View>> implements UserInfoContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.bingyi_lv)
    MyListView bingyi_lv;
    private String political_status;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;

        }
    }

    @Override
    protected UserInfoPresenterImp<UserInfoContract.View> createPresent() {
        return new UserInfoPresenterImp<>(mContext, this);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.user_account_bingyi;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("政治面貌");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        bingyi_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position_ = position;
                String user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
                mPresent.editPolitical(user_id, mList.get(position).getId());
            }
        });
    }

    int position_;
    ArrayList<UserSetBean.UserSetInfoBean> mList;

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        political_status = intent.getStringExtra("political_status");
        mPresent.getUserSET(user_id);
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    @Override
    public void editSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getData());
            Intent intent = new Intent();
            intent.putExtra("political_status", mList.get(position_).getId());
            intent.putExtra("political_status_name", mList.get(position_).getName());
            setResult(ConstantManager.USERINFO_FORRESULT_POLITICAL, intent);
            doFinish();
        }
    }

    @Override
    public void saveInfoSuccess(EditDataBean responseBean) {

    }

    @Override
    public void editAvatarSuccess(EditThumbDataBean responseBean) {

    }

    @Override
    public void editBirthdaySuccess(EditDataBean responseBean) {
    }

    @Override
    public void getUserSetSuccess(GetUserSETDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            UserSetBean mUserSetBean = responseBean.getData();
            if (mUserSetBean != null) {
                mList = mUserSetBean.getPolitical_status_conf();
                if (mList == null) {
                    mList = new ArrayList<>();
                }
                BingYiAdapter mBingYiAdapter = new BingYiAdapter(this, mList, political_status);
                bingyi_lv.setAdapter(mBingYiAdapter);
            }
        }
    }

    @Override
    public void editError() {

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
