package com.longcheng.lifecareplan.modular.mine.record.fargment;

import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.MyOrderContract;

public class RecordPresenterImp<T> extends RecordContract.Presenter<RecordContract.View>{

    private RecordContract.View mView;
    private RecordContract.Model mModel;

    public RecordPresenterImp(RecordContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void fetch() {

    }
}
