package com.longcheng.volunteer.modular.index.welcome.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Burning on 2018/9/27.
 */

public abstract class GuidePageBaseFrag extends RxFragment {

    private Unbinder unbinder;
    private View mContextView;

    @BindView(R.id.bg1)
    ImageView ivBg1;
    @BindView(R.id.bg2)
    ImageView ivBg2;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = getLayoutId();
        mContextView = inflater.inflate(layout <= 0 ? R.layout.frag_guidepage_1 : layout, container, false);
        unbinder = ButterKnife.bind(this, mContextView);
        initView(mContextView);
        return mContextView;
    }

    protected abstract int getLayoutId();

    protected void initView(View view) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
