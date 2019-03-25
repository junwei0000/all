package com.longcheng.lifecareplantv.modular.test;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10755 on 2017/11/18.
 */

public class HomeModle implements HomeContract.Modle {

    private Handler mHandler = new Handler(Looper.getMainLooper());


    @Override
    public void netWork(final DataListener dataListener) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dataListener.success("asdasd");
                    }
                });

            }
        }).start();

    }

    @Override
    public void listData(final int index, final ListDataListener data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<String> list = new ArrayList<>();
                for (int i = index * 10; i < 10 * (index + 1); i++) {
                    list.add(i + "");
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        data.success(list);
                    }
                });
            }
        }).start();

    }


}
