package com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter.MyGroupListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.MessageListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.MessageListItemBean;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;


import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class GroupListActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.gv_data)
    GridView gvData;


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
        return R.layout.fuqrep_mygroup_list;
    }


    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBarDark(mActivity, toolbar);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        gvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list != null && list.size() > 0) {
                    Intent intent = new Intent(mContext, ToChatH5Actitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", Config.BASE_HEAD_URL + "/home/im?device=android&type=1&to=" + list.get(position).getUnique_id());
                    intent.putExtra("title", list.get(position).getName());
                    intent.putExtra("type", 1);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public void initDataAfter() {
        String title = getIntent().getStringExtra("title");
        String group_id = getIntent().getStringExtra("group_id");
        pageTopTvName.setText(title);
        getMyGroupList(group_id);
    }

    ArrayList<MessageListItemBean> list;

    /**
     *
     */
    public void getMyGroupList(String group_id) {
        Observable<MessageListDataBean> observable = Api.getInstance().service.getMyGroupList(UserUtils.getUserId(mContext),
                group_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<MessageListDataBean>() {
                    @Override
                    public void accept(MessageListDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            list = responseBean.getData();
                            MyGroupListAdapter mJieQiAdapter = new MyGroupListAdapter(mActivity, list);
                            gvData.setAdapter(mJieQiAdapter);
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
    }


}
