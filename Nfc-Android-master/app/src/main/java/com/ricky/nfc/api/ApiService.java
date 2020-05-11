package com.ricky.nfc.api;


import com.ricky.nfc.bean.OrderDataBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * get请求：在方法上使用@Get注解来标志该方法为get请求，在方法中的参数需要用@Query来注释。
 * Post请求：使用@FormUrlEncoded和@POST注解来发送表单数据。
 * 使用 @Field注解和参数来指定每个表单项的Key，value为参数的值。
 * 需要注意的是必须要使用@FormUrlEncoded来注解，因为post是以表单方式来请求的。
 */
public interface ApiService {
    @FormUrlEncoded
    @POST(Config.VERSION + "Nfcwrite/HelpInfo")
    Observable<OrderDataBean> getOrderInfo(@Field("order_id") String order_id);
    @FormUrlEncoded
    @POST(Config.VERSION + "nfcwrite/bindNfcSn")
    Observable<ResponseBean> setOrderSN(@Field("order_id") String order_id,
                                         @Field("nfc_sn") String nfc_sn);
}


