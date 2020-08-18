package com.longcheng.volunteer.modular.transaction.fragment;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 17:23
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface TransactionContract {
    interface View extends BaseView<Present> {

    }

    abstract class Present<T> extends BasePresent<View> {

    }

    interface Model extends BaseModel {

    }
}
