package com.longcheng.volunteer.http.api;

import com.longcheng.volunteer.bean.Bean;
import com.longcheng.volunteer.http.basebean.BasicResponse;
import com.longcheng.volunteer.modular.mine.set.bean.VersionAfterBean;

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
