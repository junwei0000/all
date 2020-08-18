package com.longcheng.lifecareplan.modular.helpwith.myfamily.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.adapter.MyAdapter;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.ItemBean;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.MyAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.MyFamilyDataBean;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.MyFamilyListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.RelationListDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.awordofgold.activity.AWordOfGoldAct;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的家人
 */
public class MyFamilyActivity extends BaseActivityMVP<MyContract.View, MyPresenterImp<MyContract.View>> implements MyContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.iv_thumb)
    ImageView ivThumb;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_cho)
    TextView tvCho;
    @BindView(R.id.tv_add)
    TextView tvAdd;


    private String user_id;
    private MyAdapter mAdapter;
    private List<ItemBean> mList;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_add:
                Intent intent = new Intent(mContext, AddFamilyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, this);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.helpwith_myfamily;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("我的家人");
        notDateCont.setVisibility(View.GONE);
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.wisheach_family_missing);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList != null && mList.size() > 0) {
                    selectPosition = position;
                    skipEdit();
                }
            }
        });
    }

    private final int EDIT = 1;
    private final int SkipEDIT = 2;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EDIT:
                    selectPosition = msg.arg1;
                    String Is_perfect = mList.get(selectPosition).getIs_perfect();
                    if (!TextUtils.isEmpty(Is_perfect) && Is_perfect.equals("1")) {
                        //跳转 一字千金页面
                        Intent intent = new Intent(mContext, AWordOfGoldAct.class);
                        intent.putExtra("otherId", mList.get(selectPosition).getUser_id());
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    } else {
                        if (mPerfectInfoDialog == null) {
                            mPerfectInfoDialog = new PerfectInfoDialog(mActivity, mHandler, SkipEDIT);
                        }
                        mPerfectInfoDialog.showEditDialog("完善家人信息才能查看“一字千金”哦~", "完善家人信息");
                    }
                    break;
                case SkipEDIT:
                    skipEdit();
                    break;
            }
        }
    };
    PerfectInfoDialog mPerfectInfoDialog;
    int selectPosition;


    private void skipEdit() {
        Intent intent = new Intent(mContext, EditFamilyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("family_user_id", "" + mList.get(selectPosition).getUser_id());
        startActivity(intent);
        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
    }

    @Override
    public void initDataAfter() {
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        mPresent.getList(user_id);
    }


    @Override
    protected MyPresenterImp<MyContract.View> createPresent() {
        return new MyPresenterImp<>(mContext, this);
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
    public void ListSuccess(MyFamilyListDataBean responseBean) {
        comInStatus = false;
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            MyAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                ItemBean mItemBean = mEnergyAfterBean.getUser();
                if (mItemBean != null) {
                    tvName.setText(mItemBean.getUser_name());
                }

                mList = mEnergyAfterBean.getFamilys();
                if (mList == null) {
                    mList = new ArrayList<>();
                }
                mAdapter = new MyAdapter(mContext, mList, mHandler, EDIT);
                listview.setAdapter(mAdapter);
                ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
            }
        }
    }

    @Override
    public void DelSuccess(EditDataBean responseBean) {

    }

    @Override
    public void addOrEditSuccess(EditListDataBean responseBean) {

    }

    @Override
    public void GetFamilyRelationSuccess(RelationListDataBean responseBean) {

    }

    @Override
    public void GetFamilyEditInfoSuccess(MyFamilyDataBean responseBean) {

    }


    @Override
    public void ListError() {
        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
    }


    boolean comInStatus = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (!comInStatus)
            mPresent.getList(user_id);
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
