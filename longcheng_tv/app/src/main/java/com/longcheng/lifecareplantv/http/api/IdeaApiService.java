package com.longcheng.lifecareplantv.http.api;

import com.longcheng.lifecareplantv.bean.Bean;
import com.longcheng.lifecareplantv.http.basebean.BasicResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by markShuai on 2017/12/7.
 */

public interface IdeaApiService {

    @GET("version/upgrade")
    Observable<BasicResponse<Bean>> getappfindmeanu(@Query("version") String version);

    @GET("merchandise/index")
    Observable<BasicResponse<Bean>> getaa();
}
