package com.longcheng.lifecareplan.modular.home.fragment;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ActionDetailActivity;
import com.longcheng.lifecareplan.modular.home.adapter.ActionAdapter;
import com.longcheng.lifecareplan.modular.home.bean.HomeDataBean;
import com.longcheng.lifecareplan.modular.home.bean.HomeItemBean;
import com.longcheng.lifecareplan.modular.home.bean.PoActionListDataBean;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 热门行动
 */
public class PopularActionActivity extends BaseActivityMVP<HomeContract.View, HomePresenterImp<HomeContract.View>> implements HomeContract.View {

    @BindView(R.id.layout_bg)
    LinearLayout layout_bg;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.action_lv)
    MyGridView action_lv;
    private String user_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.main_actionlist;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        layout_bg.setBackgroundColor(getResources().getColor(R.color.white));
        pagetopLayoutLeft.setOnClickListener(this);
        action_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList != null && mList.size() > 0) {
                    Intent intent = new Intent(mContext, ActionDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("goods_id", mList.get(position).getGoods_id());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("热门行动");
        mPresent.getReMenActioin();
    }


    @Override
    protected HomePresenterImp<HomeContract.View> createPresent() {
        return new HomePresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    List<HomeItemBean> mList;

    @Override
    public void ListSuccess(HomeDataBean mHomeDataBean) {

    }

    @Override
    public void ActionListSuccess(PoActionListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            mList = responseBean.getData();
            if (mList == null) {
                mList = new ArrayList<>();
            }
            ActionAdapter mActionListAdapter = new ActionAdapter(mContext, mList);
            action_lv.setAdapter(mActionListAdapter);
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
