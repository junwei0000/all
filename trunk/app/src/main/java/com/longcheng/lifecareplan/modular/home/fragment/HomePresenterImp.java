package com.longcheng.lifecareplan.modular.home.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:23
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class HomePresenterImp<T> extends HomeContract.Present<HomeContract.View> {

    private HomeContract.View view;
    private HomeContract.Model model;
    private Context mContext;

    public HomePresenterImp(Context mContext, HomeContract.View view) {
        this.view = view;
        model = new HomeModelImp();
        this.mContext = mContext;
    }

    @Override
    public void fetch() {
        //获取Banner的数据
        model.BannerListData(mContext, new HomeContract.Model.BannerListListener() {
            @Override
            public void dataSuccess(List<Drawable> list) {
                view.BannerListSuccess(list);
            }

            @Override
            public void dataError(String code) {
                view.BannerListError(code);
            }
        });
    }
}
