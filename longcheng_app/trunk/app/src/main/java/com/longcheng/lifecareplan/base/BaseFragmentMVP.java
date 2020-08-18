package com.longcheng.lifecareplan.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：MarkMingShuai
 * 时间 2017-8-9 14:19
 * 邮箱：mark_mingshuai@163.com
 * 类的意图：Fragment 的基础类
 */

public abstract class BaseFragmentMVP<V, T extends BasePresent<V>> extends RxFragment implements View.OnClickListener {

    /**
     * P层引用
     */
    protected T mPresent;

    protected final String TAG = this.getClass().getSimpleName();
    private View mContextView = null;
    protected Context mContext = null;
    //
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        //创建Presenter层
        mPresent = createPresent();
        //做绑定
        mPresent.attachView((V) this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContextView = inflater.inflate(bindLayout(), container, false);
        unbinder = ButterKnife.bind(this, mContextView);

        initView(mContextView);
        return mContextView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        doBusiness(getActivity());
        mPresent.fetch();
    }


    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [初始化控件]
     *
     * @param view
     */
    public abstract void initView(final View view);

    /**
     * [业务操作]
     *
     * @param mContext
     */
    public abstract void doBusiness(Context mContext);

    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    /*
     * @params
     * @name 子类实现具体的构建过程
     * @data 2017/11/20 15:39
     * @author :MarkShuai
     */
    protected abstract T createPresent();

    @Override
    public void onDestroy() {
        //解绑(防止内存泄漏)
        mPresent.detach();
        unbinder.unbind();
        super.onDestroy();
    }
}
