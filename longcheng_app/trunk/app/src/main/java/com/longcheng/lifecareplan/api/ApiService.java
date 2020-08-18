package com.longcheng.lifecareplan.api;



import com.longcheng.lifecareplan.bean.Bean;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.test.bean.VersionAfterBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("UserCenter/GetAppFindMenu")
    Observable<Bean> getappfindmeanu();
    @GET("version/android")
    Observable<BasicResponse<VersionAfterBean>> updateVersion(@Query("version") String version);

}


