package com.longcheng.lifecareplan.http.api;

import com.longcheng.lifecareplan.bean.allBean.Bean;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.mine.set.bean.VersionAfterBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by markShuai on 2017/12/7.
 */

public interface IdeaApiService {
    @GET("version/android")
    Observable<BasicResponse<VersionAfterBean>> updateVersionTEST(@Query("version") String version);

    @GET("version/upgrade")
    Observable<BasicResponse<Bean>> getappfindmeanu(@Query("version") String version);

    @GET("merchandise/index")
    Observable<BasicResponse<Bean>> getaa();
}
