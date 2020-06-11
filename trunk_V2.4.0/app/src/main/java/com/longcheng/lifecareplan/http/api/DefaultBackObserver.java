package com.longcheng.lifecareplan.http.api;

import android.app.Activity;

import com.google.gson.JsonParseException;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

import static com.longcheng.lifecareplan.http.api.DefaultObserver.ExceptionReason.CONNECT_ERROR;
import static com.longcheng.lifecareplan.http.api.DefaultObserver.ExceptionReason.CONNECT_TIMEOUT;
import static com.longcheng.lifecareplan.http.api.DefaultObserver.ExceptionReason.PARSE_ERROR;
import static com.longcheng.lifecareplan.http.api.DefaultObserver.ExceptionReason.UNKNOWN_ERROR;


/**
 * Created by markShuai on 2017/12/7.
 */

public abstract class DefaultBackObserver<T extends BasicResponse> implements Observer<T> {
    /**
     * 请求成功
     */
    public final static int REQUEST_SUCCESS = 0;

    private Activity activity;


    public DefaultBackObserver(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        onSuccess(response);
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e("Retrofit", e.getMessage());
        if (e instanceof HttpException) {     //   HTTP错误
            onException(DefaultObserver.ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {   //   连接错误
            onException(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(PARSE_ERROR);
        } else {
            onException(UNKNOWN_ERROR);
        }
        onError();
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    abstract public void onError();


    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(DefaultObserver.ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.showToast(R.string.connect_error);
                break;
            case CONNECT_TIMEOUT:
                ToastUtils.showToast(R.string.connect_timeout);
                break;
            case BAD_NETWORK:
                ToastUtils.showToast(R.string.bad_network);
                break;
            case PARSE_ERROR:
                ToastUtils.showToast(R.string.parse_error);
                break;
            case UNKNOWN_ERROR:
            default:
                ToastUtils.showToast(R.string.unknown_error);
                break;
        }
        onError();
    }

}
