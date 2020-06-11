package com.longcheng.lifecareplan.modular.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.im.bean.PionImDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.utils.RongDateUtils;
import io.rong.imkit.widget.adapter.MessageListAdapter;

/**
 * 作者：jun on
 * 时间：2020/6/2 10:33
 * 意图：
 */
public class MyMessageListAdapter extends MessageListAdapter {
    Context context;

    public MyMessageListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void bindView(View v, int position, UIMessage data) {
        super.bindView(v, position, data);
        if (data != null) {
            final MessageListAdapter.ViewHolder holder = (MessageListAdapter.ViewHolder) v.getTag();
            if (holder != null) {
                getSolarTermsInfo(data.getSentTime(), holder.time);
//                String time = RongDateUtils.getConversationFormatDate(data.getSentTime(), v.getContext());
//                holder.time.setText("立夏  " + time);
            }
        }
    }

    public void getSolarTermsInfo(long date_time, TextView textView) {
        String use_id = UserUtils.getUserId(context);
        Observable<PionImDataBean> observable = Api.getInstance().service.getSolarTermsInfo(
                use_id, date_time / 1000, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PionImDataBean>() {
                    @Override
                    public void accept(PionImDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            String time = RongDateUtils.getConversationFormatDate(date_time, context);
                            String jieqi = responseBean.getData().getSolarTerms().getSolar_terms_name();
                            textView.setText(jieqi + "  -  " + time);
                            Log.e("bindView", "getSentTime==" + date_time + "   " + jieqi + "   " + time);
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Observable", throwable.getMessage());
                    }
                });
    }
}