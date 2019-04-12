package com.longcheng.lifecareplanTv.api;


import com.longcheng.lifecareplanTv.config.Config;
import com.longcheng.lifecareplanTv.login.bean.LoginAfterBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * get请求：在方法上使用@Get注解来标志该方法为get请求，在方法中的参数需要用@Query来注释。
 * Post请求：使用@FormUrlEncoded和@POST注解来发送表单数据。
 * 使用 @Field注解和参数来指定每个表单项的Key，value为参数的值。
 * 需要注意的是必须要使用@FormUrlEncoded来注解，因为post是以表单方式来请求的。
 */
public interface ApiService {

    //********************用户登录，个人信息***************************

    @GET(Config.VERSION + "code/sendCode")
    Observable<BasicResponse<LoginAfterBean>> userSendCode(@Query("phone") String phone,
                                                           @Query("type") String type,
                                                           @Query("token") String token);

    @GET(Config.VERSION + "login/quick")
    Observable<BasicResponse<LoginAfterBean>> userPhoneLogin(@Query("phone") String phone, @Query("code") String code, @Query("token") String token);

}


