package com.longcheng.lifecareplan.apiLive;


import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveStatusInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MineItemInfo;

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
    @POST("dock/live/room/setBroadcastStatus")
    Observable<BasicResponse> setLiveRoomBroadcastStatus(@Field("user_id") String user_id,
                                                         @Field("live_room_id") String live_room_id,
                                                         @Field("broadcast_status") int broadcast_status);

    @FormUrlEncoded
    @POST("dock/live/room/info")
    Observable<BasicResponse<LiveDetailInfo>> getLivePlayInfo(@Field("live_room_id") String live_room_id);

    @FormUrlEncoded
    @POST("dock/live/user/info")
    Observable<BasicResponse<LiveStatusInfo>> getUserLiveStatus(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("dock/live/user/apply")
    Observable<BasicResponse> applyLive(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("dock/live/room/pay")
    Observable<BasicResponse<LiveStatusInfo>> openRoomPay(@Field("user_id") String user_id,
                                                          @Field("title") String title,
                                                          @Field("cover_url") String cover_url,
                                                          @Field("address") String address,
                                                          @Field("lon") double lon,
                                                          @Field("lat") double lat,
                                                          @Field("price") String price);


    @FormUrlEncoded
    @POST("dock/user/user/index")
    Observable<BasicResponse<MineItemInfo>> getMineInfo(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("dock/user/user/changeShowTitle")
    Observable<BasicResponse> updateShowTitle(@Field("user_id") String user_id,
                                              @Field("show_title") String show_title);

    @FormUrlEncoded
    @POST("dock/user/user/myShortVideoList")
    Observable<BasicResponse<MVideoDataInfo>> getMineVideoList(@Field("user_id") String user_id,
                                                               @Field("page") int page,
                                                               @Field("page_size") int page_size);

    @FormUrlEncoded
    @POST("dock/user/user/myLiveList")
    Observable<BasicResponse<MVideoDataInfo>> getMineLiveList(@Field("user_id") String user_id,
                                                              @Field("page") int page,
                                                              @Field("page_size") int page_size);

    @FormUrlEncoded
    @POST("dock/user/user/myFollowList")
    Observable<BasicResponse<MVideoDataInfo>> getMineFollowList(@Field("user_id") String user_id,
                                                                @Field("page") int page,
                                                                @Field("page_size") int page_size);
}


