package com.longcheng.lifecareplan.modular.home.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.IInterface;

import com.longcheng.lifecareplan.base.BaseModel;
import com.longcheng.lifecareplan.base.BasePresent;
import com.longcheng.lifecareplan.base.BaseView;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:19
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface HomeContract {

    interface View extends BaseView<Present> {
        void BannerListSuccess(List<Drawable> list);

        void BannerListError(String code);
    }

    abstract class Present<T> extends BasePresent<View> {

    }

    interface Model extends BaseModel {
        //请求Banner图的
        void BannerListData(Context mContext, BannerListListener listListener);

        interface BannerListListener {
            void dataSuccess(List<Drawable> list);

            void dataError(String code);
        }
    }


}
