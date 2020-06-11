package com.longcheng.lifecareplan.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.longcheng.lifecareplan.modular.bottommenu.activity.BottomMenuActivity;
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
    protected Activity mActivity = null;
    //
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mActivity = getActivity();
        //创建Presenter层
        mPresent = createPresent();
        //做绑定
        mPresent.attachView((V) this);
    }

    public void initContext() {
        if (mActivity == null) {
            mActivity = BottomMenuActivity.mMenuContext;
        }
    }

    /**
     * 解决java.lang.IllegalStateException: Bindings already cleared.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContextView != null) {
            ViewGroup parent = (ViewGroup) mContextView.getParent();
            if (parent != null) {
                parent.removeView(mContextView);
            }
        } else {
            mContextView = inflater.inflate(bindLayout(), container, false);
        }
        unbinder = ButterKnife.bind(this, mContextView);
        initView(mContextView);
        return mContextView;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //列表类型的fragment,不要Override doBisiness（），需要Override onActivityCreated（）,在这里面加入新的判断再自定义一个加载数据的方法
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
