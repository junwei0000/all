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

public abstract class DefaultObserver<T extends BasicResponse> implements Observer<T> {
    /**
     * 请求成功
     */
    public final static int REQUEST_SUCCESS = 200;
    /**
     * token错误
     */
    public final static int TOKEN_INCORRECT = 201;
    /**
     * token过期
     */
    public final static int TOKEN_EXPIRED = 202;

    /**
     * refresh_token过期
     */
    public final static int REFRESH_TOKEN_EXPIRED = 203;

    private Activity activity;


    public DefaultObserver(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
//        if (!response.isError()) {
//            onSuccess(response);
//        } else {
//            onFail(response, response.getCode());
//        }
        if (response.getCode() == 0) {
            onSuccess(response);
        } else {
            onFail(response, response.getCode());
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e("Retrofit", e.getMessage());
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
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

    abstract public void onErrorUser();

    /**
     * 服务器返回数据，但响应码不为200
     *
     * @param response 服务器返回的数据
     */
    public void onFail(T response, int code) {
//        String message = response.getMessage();
        String message = "asdasd";
        switch (code) {
            case TOKEN_EXPIRED: //  token过期 刷新token
                refreshToken();
                break;
            case REFRESH_TOKEN_EXPIRED:// refresh_token过期
            case TOKEN_INCORRECT:// token错误重新登录
//                SharedPreferencesHelper.put(activity, "isLogin", false);
//                ToastUtils.show("token错误，请重新登录，跳转到登录页面...");
                break;
            default:
                ToastUtils.showToast(message);
                break;
        }

        onErrorUser();
    }

    /**
     * @param
     * @name 刷新Token
     * @time 2017/12/1 14:04
     * @author MarkShuai
     */
    private void refreshToken() {

    }

    /**
     * @param
     * @name 获取Token的成功回调
     * @time 2017/12/1 14:04
     * @author MarkShuai
     */
    public void onTokenUpdateSuccess() {
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
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
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
