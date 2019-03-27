package com.longcheng.volunteer.modular.test;


import java.util.List;

/**
 * Created by 10755 on 2017/11/18.
 */

public class HomePresenter<T> extends HomeContract.Present<HomeContract.View> {

    /**
     * 持有视图层 UI接口的引用  此时的视图层Activity
     */
    private HomeContract.View mGrilView;

    private HomeModle homeModle;


    public HomePresenter(HomeContract.View mGrilView) {
        this.mGrilView = mGrilView;
        homeModle = new HomeModle();
    }


    @Override
    public void logIn() {

        homeModle.netWork(new HomeContract.Modle.DataListener() {
            @Override
            public void success(String string) {
                mGrilView.logInOk();
                mGrilView.changeText(string);
                mGrilView.dismissDialog();
            }

            @Override
            public void error() {

            }
        });
    }

    @Override
    public void setListViewData(int index) {
        homeModle.listData(index, new HomeContract.Modle.ListDataListener() {
            @Override
            public void success(List<String> list) {
                mGrilView.onHomeListDataSuccessFul(list);
            }
        });
    }

    @Override
    public void fetch() {

    }
}
