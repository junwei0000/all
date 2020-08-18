package com.longcheng.volunteer.modular.test;

import com.longcheng.volunteer.base.BaseModel;
import com.longcheng.volunteer.base.BasePresent;
import com.longcheng.volunteer.base.BaseView;

import java.util.List;

/**
 * Created by 10755 on 2017/11/18.
 */

public interface HomeContract {

    interface View extends BaseView<Present> {
        void changeText(String title);

        void logInOk();

        void logInError();

        void onHomeListDataSuccessFul(List<String> list);

        void onHomeListDataFail();

    }

    abstract class Present<T> extends BasePresent<View> {
        public abstract void logIn();

        public abstract void setListViewData(int index);

    }

    interface Modle extends BaseModel {

        void netWork(DataListener dataListener);


        interface DataListener {
            void success(String string);

            void error();
        }

        void listData(int index, ListDataListener data);

        interface ListDataListener {
            void success(List<String> list);
        }
    }
}
