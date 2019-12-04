package com.longcheng.lifecareplan.apiLive;


import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * get请求：在方法上使用@Get注解来标志该方法为get请求，在方法中的参数需要用@Query来注释。
 * Post请求：使用@FormUrlEncoded和@POST注解来发送表单数据。
 * 使用 @Field注解和参数来指定每个表单项的Key，value为参数的值。
 * 需要注意的是必须要使用@FormUrlEncoded来注解，因为post是以表单方式来请求的。
 */
public interface ApiLiveService {
    //****************************直播****************************
//    http://t.dock.lifecareplan.cn/dock/video/video/lists

    @FormUrlEncoded
    @POST("dock/video/video/lists")
    Observable<BasicResponse<ArrayList<VideoItemInfo>>> getVideoList(@Field("user_id") String user_id,
                                                                     @Field("page") int page,
                                                                     @Field("page_size") int page_size);

    @FormUrlEncoded
    @POST("dock/live/room/lists")
    Observable<BasicResponse<VideoDataInfo>> getLiveList(@Field("user_id") String user_id,
                                                         @Field("page") int page,
                                                         @Field("page_size") int page_size);

    @FormUrlEncoded
    @POST("dock/live/room/info")
    Observable<BasicResponse<LiveDetailInfo>> getLivePlayInfo(@Field("live_room_id") String live_room_id);

    @FormUrlEncoded
    @POST("dock/live/user/info")
    Observable<BasicResponse> getUserLiveStatus(@Field("user_id") String user_id);

}


