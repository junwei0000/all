package com.longcheng.lifecareplan.http.api;

import com.longcheng.lifecareplan.bean.Bean;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by markShuai on 2017/12/7.
 */

public interface IdeaApiService {


    @POST("UserCenter/GetAppFindMenu")
    Observable<BasicResponse<Bean>> getappfindmeanu();
}
