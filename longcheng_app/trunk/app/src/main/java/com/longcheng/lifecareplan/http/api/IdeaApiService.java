package com.longcheng.lifecareplan.http.api;

import com.longcheng.lifecareplan.bean.Bean;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.test.bean.HomeAfterBean;
import com.longcheng.lifecareplan.modular.test.bean.VersionAfterBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 *  Created by markShuai on 2017/12/7.
 */

public interface IdeaApiService {

    @GET("version/android")
    Observable<BasicResponse<VersionAfterBean>> updateVersion(@Query("version") String version);
    @POST("version/upgrade")
    Observable<BasicResponse<Bean>> getappfindmeanu(@Query("version") String version);


    @GET("index/index")
    Observable<BasicResponse<HomeAfterBean>> getHomeList(@Query("user_id") String user_id,
                                                         @Query("token") String token);
}
