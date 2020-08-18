package com.longcheng.lifecareplan.modular.home.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ExampleApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:28
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class HomeModelImp implements HomeContract.Model {

    @Override
    public void BannerListData(Context mContext, BannerListListener listListener) {
        List<Drawable> list = new ArrayList<>();
        list.add(mContext.getResources().getDrawable(R.drawable.tuijianhei));
        listListener.dataSuccess(list);
    }
}
