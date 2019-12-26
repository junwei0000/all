package com.longcheng.lifecareplan.api;


import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.exchange.bean.JieQiListDataBean;
import com.longcheng.lifecareplan.modular.exchange.bean.MallGoodsListDataBean;
import com.longcheng.lifecareplan.modular.exchange.malldetail.bean.GoodsDetailDataBean;
import com.longcheng.lifecareplan.modular.exchange.shopcart.bean.ShopCartDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataListBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ExplainDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.OtherUserInfoDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleSearchDataBean;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.bean.AutoHelpDataBean;
import com.longcheng.lifecareplan.modular.helpwith.bean.HelpIndexDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.ActionListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpEnergyListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.CommentDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.EnergyDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.OpenRedDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.PayDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.bean.LifeStyleListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleCommentDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.SKBPayDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.lifestylerank.bean.LifeRankListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.medalrank.bean.MyRankListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.myDedication.bean.MyDedicationListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.myGratitude.bean.MyListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.MyFamilyDataBean;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.MyFamilyListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.RelationListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.rank.bean.RankListDataBean;
import com.longcheng.lifecareplan.modular.home.bean.HomeDataBean;
import com.longcheng.lifecareplan.modular.home.bean.PoActionListDataBean;
import com.longcheng.lifecareplan.modular.home.bean.QuickTeamDataBean;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneDataBean;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneListDataBean;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.bean.HealthyDeliveryResultBean;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginDataBean;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.GetEnergyListDataBean;
import com.longcheng.lifecareplan.modular.mine.awordofgold.bean.AWordOfGoldResponseBean;
import com.longcheng.lifecareplan.modular.mine.bill.bean.BillResultBean;
import com.longcheng.lifecareplan.modular.mine.changeinviter.bean.InviteDataBean;
import com.longcheng.lifecareplan.modular.mine.goodluck.bean.GoodLuckListDataBean;
import com.longcheng.lifecareplan.modular.mine.invitation.bean.InvitationResultBean;
import com.longcheng.lifecareplan.modular.mine.message.bean.MessageDataBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderListDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.detail.bean.DetailDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.ordertracking.bean.TrankListDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.bean.ThanksListDataBean;
import com.longcheng.lifecareplan.modular.mine.phosphor.RedirectDataBean;
import com.longcheng.lifecareplan.modular.mine.rebirth.bean.RebirthDataBean;
import com.longcheng.lifecareplan.modular.mine.recovercash.bean.AcountInfoDataBean;
import com.longcheng.lifecareplan.modular.mine.relationship.bean.RelationshipBean;
import com.longcheng.lifecareplan.modular.mine.rewardcenters.bean.RewardCentersResultBean;
import com.longcheng.lifecareplan.modular.mine.set.bean.PushDataBean;
import com.longcheng.lifecareplan.modular.mine.set.bean.VersionAfterBean;
import com.longcheng.lifecareplan.modular.mine.set.bean.VersionDataBean;
import com.longcheng.lifecareplan.modular.mine.signIn.bean.SignInDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditThumbDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetUserSETDataBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;

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
    //*******************TEST***************************
    @GET("version/android")
    Observable<BasicResponse<VersionAfterBean>> updateVersionTEST(@Query("version") String version);

    //*******************TEST*************************

    @GET("version/android")
    Observable<VersionDataBean> updateVersion(@Query("version") String version);

    //********************用户登录，个人信息***************************
    @FormUrlEncoded
    @POST(Config.VERSION + "login/account")
    Observable<LoginDataBean> userAccountLogin(@Field("phone") String phone,
                                               @Field("password") String password,
                                               @Field("login_ip") String login_ip,
                                               @Field("lat") double phone_user_latitude,
                                               @Field("lon") double phone_user_longitude,
                                               @Field("login_address") String phone_user_address,
                                               @Field("token") String token);

    @GET(Config.VERSION + "login/account")
    Observable<LoginDataBean> userAccountLoginGet(@Query("phone") String phone, @Query("password") String password, @Query("token") String token);

    @GET(Config.VERSION + "login/checkphone")
    Observable<EditDataBean> ForgetPWcheckphone(@Query("phone") String phone, @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "login/findPassword")
    Observable<EditDataBean> findPassword(@Field("phone") String phone,
                                          @Field("code") String code,
                                          @Field("password") String password,
                                          @Field("confirm_password") String confirm_password,
                                          @Field("token") String token);

    @GET(Config.VERSION + "register/checkPhone")
    Observable<EditDataBean> registerCheckphone(@Query("phone") String phone, @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "register/phone")
    Observable<LoginDataBean> register(@Field("phone") String phone,
                                       @Field("code") String code,
                                       @Field("password") String password,
                                       @Field("confirm_password") String confirm_password,
                                       @Field("token") String token);

    @GET(Config.VERSION + "code/sendCode")
    Observable<SendCodeBean> userSendCode(@Query("phone") String phone,
                                          @Query("type") String type,
                                          @Query("token") String token);

    @GET(Config.VERSION + "code/sendCode")
    Observable<SendCodeBean> userSendCode2(@Query("user_id") String user_id,
                                           @Query("phone") String phone,
                                           @Query("type") String type,
                                           @Query("token") String token);

    @GET(Config.VERSION + "code/sendCode")
    Observable<SendCodeBean> cashSendCode(@Query("user_id") String user_id, @Query("phone") String phone, @Query("token") String token);

    @GET(Config.VERSION + "login/quick")
    Observable<LoginDataBean> userPhoneLogin(@Query("phone") String phone,
                                             @Query("code") String code,
                                             @Query("login_ip") String login_ip,
                                             @Query("lat") double phone_user_latitude,
                                             @Query("lon") double phone_user_longitude,
                                             @Query("login_address") String phone_user_address,
                                             @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/bindPhone")
    Observable<LoginDataBean> bindPhone(@Field("user_id") String user_id, @Field("code") String code,
                                        @Field("phone") String phone, @Field("pwd") String pwd,
                                        @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/editPwd")
    Observable<EditDataBean> updatePW(@Field("user_id") String user_id,
                                      @Field("code") String code,
                                      @Field("phone") String phone,
                                      @Field("new_pwd") String new_pwd,
                                      @Field("conf_pwd") String conf_pwd,
                                      @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "login/weixin")
    Observable<LoginDataBean> useWXLogin(@Field("openid") String openid,
                                         @Field("unionid") String unionid,
                                         @Field("nick_name") String nick_name,
                                         @Field("headimgurl") String headimgurl,
                                         @Field("sex") String sex,
                                         @Field("province") String province,
                                         @Field("city") String city,
                                         @Field("access_token") String access_token,
                                         @Field("token") String token);

    @GET(Config.VERSION + "user/userConfig")
    Observable<GetUserSETDataBean> getUserSET(@Query("user_id") String user_id, @Query("token") String token);

    @GET(Config.VERSION + "user/home")
    Observable<GetHomeInfoDataBean> getUserHomeInfo(@Query("user_id") String user_id, @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/editUserName")
    Observable<EditDataBean> editUserName(@Field("user_id") String user_id, @Field("user_name") String user_name, @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/get_feedback")
    Observable<EditDataBean> sendIdea(@Field("user_id") String user_id, @Field("content") String content, @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/editAvatar")
    Observable<EditThumbDataBean> editAvatar(@Field("user_id") String user_id, @Field("file") String file, @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/uploadImg")
    Observable<EditDataBean> uploadImg(@Field("user_id") String user_id, @Field("file") String file, @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/editPolitical")
    Observable<EditDataBean> editPolitical(@Field("user_id") String user_id, @Field("political_status") String political_status, @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/editMilitaryService")
    Observable<EditDataBean> editMilitaryService(@Field("user_id") String user_id, @Field("is_military_service") String is_military_service, @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/editBirthday")
    Observable<EditDataBean> editBirthday(@Field("user_id") String user_id, @Field("birthday") String birthday, @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/checkUserInfo")
    Observable<EditDataBean> checkUserInfo(@Field("user_id") String user_id, @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/doStarLevelRemind")
    Observable<ResponseBean> doStarLevelRemind(@Field("user_id") String user_id, @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/editHomeplace")
    Observable<EditDataBean> editHomeplace(@Field("user_id") String user_id, @Field("pid") String pid,
                                           @Field("cid") String cid,
                                           @Field("aid") String aid, @Field("token") String token);

    //********************首页**************************
    @GET(Config.VERSION + "index/index")
    Observable<HomeDataBean> getHomeList(@Query("user_id") String user_id,
                                         @Query("version_code") int version_code,
                                         @Query("phone_user_latitude") double phone_user_latitude,
                                         @Query("phone_user_longitude") double phone_user_longitude,
                                         @Query("phone_user_address") String phone_user_address,
                                         @Query("token") String token);

    @GET(Config.VERSION + "help/index")
    Observable<HelpIndexDataBean> getHelpInfo(@Query("user_id") String user_id,
                                              @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/cancelHolidayTips")
    Observable<ResponseBean> cancelHolidayTips(@Field("user_id") String user_id, @Field("token") String token);

    @GET(Config.VERSION + "index/actionList")
    Observable<PoActionListDataBean> getReMenActioin();

    @GET(Config.VERSION + "knpteam/ajaxReciveDedicationHigh")
    Observable<QuickTeamDataBean> getQuickTeamUrl(@Query("user_id") String user_id,
                                                  @Query("token") String token);

    //********************申请互祝***************************
    @FormUrlEncoded
    @POST(Config.VERSION + "help/needHelpNumberTask")
    Observable<ActionDataBean> getNeedHelpNumberTask(@Field("user_id") String user_id,
                                                     @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "help/setTaskRead")
    Observable<EditDataBean> setTaskRead(@Field("user_id") String user_id,
                                         @Field("mutual_help_apply_id") String mutual_help_apply_id,
                                         @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "help/actionSafety")
    Observable<ActionDataBean> actionSafety(@Field("user_id") String user_id,
                                            @Field("user_name") String user_name,
                                            @Field("user_sex") int user_sex,
                                            @Field("pid") String pid,
                                            @Field("cid") String cid,
                                            @Field("aid") String aid,
                                            @Field("occupation") String occupation,
                                            @Field("birthday") String birthday,
                                            @Field("identity_number") String identity_number,
                                            @Field("phone") String phone,
                                            @Field("email") String email,
                                            @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/ajaxSaveUserInfo")
    Observable<LoginDataBean> saveUserInfo(@Field("user_id") String user_id,
                                           @Field("user_name") String user_name,
                                           @Field("phone") String phone,
                                           @Field("code") String code,
                                           @Field("password") String password,
                                           @Field("repassword") String repassword,
                                           @Field("pid") String pid,
                                           @Field("cid") String cid,
                                           @Field("aid") String aid,
                                           @Field("birthday") String birthday,
                                           @Field("token") String token);

    @GET(Config.VERSION + "help/actionList")
    Observable<ActionDataListBean> getActionList(@Query("user_id") String user_id, @Query("token") String token);

    @GET(Config.VERSION + "action/detail")
    Observable<ActionDataBean> getActionDetail(@Query("user_id") String user_id,
                                               @Query("goods_id") String goods_id,
                                               @Query("token") String token);

    @GET(Config.VERSION + "help/receiveUsers")
    Observable<PeopleDataBean> getPeopleList(@Query("user_id") String user_id, @Query("token") String token);

    @GET(Config.VERSION + "user/getOtherUserInfo")
    Observable<OtherUserInfoDataBean> getOtherUserInfo(@Query("user_id") String user_id,
                                                       @Query("other_user_id") String other_user_id,
                                                       @Query("token") String token);

    @GET(Config.VERSION + "help/searchReceiveUsers")
    Observable<PeopleSearchDataBean> getPeopleSearchList(@Query("user_id") String user_id, @Query("user_name") String user_name, @Query("token") String token);

    @GET(Config.VERSION + "help/checkActionJoinActivity")
    Observable<ExplainDataBean> getExplainList(@Query("user_id") String user_id, @Query("action_id") String action_id, @Query("token") String token);


    @GET(Config.VERSION + "useraddress/addressList")
    Observable<AddressListDataBean> getAddressList(@Query("user_id") String user_id, @Query("receive_user_id") String receive_user_id, @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "useraddress/addAddress")
    Observable<AddressListDataBean> addAddress(@Field("user_id") String user_id,
                                               @Field("order_id") String order_id,
                                               @Field("receive_user_id") String receive_user_id,
                                               @Field("consignee") String consignee,
                                               @Field("province") String province,
                                               @Field("city") String city,
                                               @Field("district") String district,
                                               @Field("address") String address,
                                               @Field("mobile") String mobile,
                                               @Field("is_default") String is_default,
                                               @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "help/applyAction")
    Observable<ActionDataBean> applyAction(@Field("user_id") String user_id,
                                           @Field("action_id") String action_id,
                                           @Field("receive_user_id") String receive_user_id,
                                           @Field("address_id") String address_id,
                                           @Field("describe") String describe,
                                           @Field("action_safety_id") String action_safety_id,
                                           @Field("extend_info") String extend_info,
                                           @Field("wealth_qiming_user_id") String qiming_user_id,
                                           @Field("life_ad_main") String life_ad_main,
                                           @Field("life_ad_minor") String life_ad_minor,
                                           @Field("life_repay_id") String life_repay_id,
                                           @Field("life_comment_id") String life_comment_id,
                                           @Field("goods_specs_id") int goods_specs_id,
                                           @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "useraddress/editAddress")
    Observable<AddressListDataBean> editAddress(@Field("user_id") String user_id,
                                                @Field("address_id") String address_id,
                                                @Field("consignee") String consignee,
                                                @Field("province") String province,
                                                @Field("city") String city,
                                                @Field("district") String district,
                                                @Field("address") String address,
                                                @Field("mobile") String mobile,
                                                @Field("is_default") String is_default,
                                                @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "useraddress/setDefaultAddress")
    Observable<AddressListDataBean> setDefaultAddress(@Field("user_id") String user_id,
                                                      @Field("address_id") String address_id,
                                                      @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "useraddress/delAddress")
    Observable<EditDataBean> delAddress(@Field("user_id") String user_id,
                                        @Field("address_id") String address_id,
                                        @Field("token") String token);


    //********************激活能量*************************
    @FormUrlEncoded
    @POST(Config.VERSION + "ability/getRechargeInfo")
    Observable<GetEnergyListDataBean> getRechargeInfo(@Field("user_id") String user_id,
                                                      @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Ability/assetRecharge")
    Observable<PayWXDataBean> assetRecharge(@Field("user_id") String user_id,
                                            @Field("money") String money,
                                            @Field("asset") String asset,
                                            @Field("pay_type") String pay_type,
                                            @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Paydeposit/paydo")
    Observable<PayWXDataBean> yaJinPay(@Field("user_id") String user_id,
                                       @Field("order_id") String order_id,
                                       @Field("money") int money,
                                       @Field("payWay") String payWay,
                                       @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Paydeposit/paydoHp")
    Observable<PayWXDataBean> yaJinPayLifeStyle(@Field("user_id") String user_id,
                                                @Field("order_id") String order_id,
                                                @Field("money") int money,
                                                @Field("payWay") String payWay,
                                                @Field("pay_source") String pay_source,
                                                @Field("token") String token);

    //********************个人中心*************************
    @GET(Config.VERSION + "help/getRedirectMsgId")
    Observable<RedirectDataBean> getRedirectMsgId(@Query("user_id") String user_id,
                                                  @Query("goods_id") String action_goods_id,
                                                  @Query("token") String token);

    @GET(Config.VERSION + "record/asset")
    Observable<BillResultBean> getBillList(@Query("user_id") String user_id,
                                           @Query("page") int page,
                                           @Query("page_size") int page_size,
                                           @Query("source_type") int source_type,
                                           @Query("month") String month,
                                           @Query("token") String token);

    @GET(Config.VERSION + "record/ability")
    Observable<BillResultBean> getEngryRecordList(@Query("user_id") String user_id,
                                                  @Query("page") int page,
                                                  @Query("page_size") int page_size,
                                                  @Query("source_type") int source_type,
                                                  @Query("month") String month,
                                                  @Query("token") String token);

    @GET(Config.VERSION + "record/superAbility")
    Observable<BillResultBean> getSleepEngryRecordList(@Query("user_id") String user_id,
                                                       @Query("page") int page,
                                                       @Query("page_size") int page_size,
                                                       @Query("source_type") int source_type,
                                                       @Query("month") String month,
                                                       @Query("token") String token);

    @GET(Config.VERSION + "record/wakeSkb")
    Observable<BillResultBean> getWakeSkbList(@Query("user_id") String user_id,
                                              @Query("page") int page,
                                              @Query("page_size") int page_size,
                                              @Query("source_type") int source_type,
                                              @Query("month") String month,
                                              @Query("token") String token);

    @GET(Config.VERSION + "record/frostSkb")
    Observable<BillResultBean> getSleepSkbList(@Query("user_id") String user_id,
                                               @Query("page") int page,
                                               @Query("page_size") int page_size,
                                               @Query("source_type") int source_type,
                                               @Query("month") String month,
                                               @Query("token") String token);

    @GET(Config.VERSION + "user/mybook")
    Observable<RelationshipBean> getRelationshipAccount(@Query("user_id") String user_id,
                                                        @Query("token") String token);

    @GET(Config.VERSION + "user/goldWord")
    Observable<AWordOfGoldResponseBean> getAWordOfGold(@Query("user_id") String user_id,
                                                       @Query("token") String token, @Query("select_user_id") String selectUserId);

    @GET(Config.VERSION + "user/reward")
    Observable<RewardCentersResultBean> getReward(@Query("user_id") String user_id,
                                                  @Query("page") int page,
                                                  @Query("page_size") int page_size,
                                                  @Query("token") String token);

    @GET(Config.VERSION + "user/invitation")
    Observable<InvitationResultBean> getInvitation(@Query("user_id") String user_id,
                                                   @Query("page") int page,
                                                   @Query("page_size") int page_size,
                                                   @Query("token") String token);


    @GET(Config.VERSION + "news/lists")
    Observable<HealthyDeliveryResultBean> getNewsList(@Query("page") int page,
                                                      @Query("page_size") int page_size,
                                                      @Query("type") int type);

    @GET(Config.VERSION + "user/withdraw")
    Observable<AcountInfoDataBean> getAccountInfo(@Query("user_id") String user_id,
                                                  @Query("token") String token);

    @GET(Config.VERSION + "push/is_send_push")
    Observable<PushDataBean> getPushStatus(@Query("user_id") String user_id,
                                           @Query("token") String token);

    @GET(Config.VERSION + "push/set_send_push")
    Observable<EditDataBean> setPushStatus(@Query("user_id") String user_id,
                                           @Query("push_need_received") int push_need_received,
                                           @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/doWithdraw")
    Observable<EditListDataBean> tiXian(@Field("user_id") String user_id,
                                        @Field("bankName") String bankName,
                                        @Field("bankBranchName") String bankBranchName,
                                        @Field("cardNum") String cardnum,
                                        @Field("accountName") String accountName,
                                        @Field("code") String code,
                                        @Field("apply_withdrawals_cash") String apply_withdrawals_cash,
                                        @Field("service_charge") String service_charge,
                                        @Field("money") String money,
                                        @Field("token") String token);


    @GET(Config.VERSION + "user/redpacket")
    Observable<GoodLuckListDataBean> getGoodLuckList(@Query("user_id") String user_id,
                                                     @Query("page") int page,
                                                     @Query("pageSize") int pageSize,
                                                     @Query("token") String token);

    @GET(Config.VERSION + "user/sign")
    Observable<SignInDataBean> getSignInfo(@Query("user_id") String user_id,
                                           @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/doSign")
    Observable<SignInDataBean> signIn(@Field("user_id") String user_id,
                                      @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "user/pushList")
    Observable<MessageDataBean> getMessageList(@Field("user_id") String user_id,
                                               @Field("page") int page,
                                               @Field("page_size") int page_size,
                                               @Field("token") String token);


    @GET(Config.VERSION + "user/userInvitationInfo")
    Observable<InviteDataBean> getInviteInfo(@Query("user_id") String user_id,
                                             @Query("token") String token);

    @GET(Config.VERSION + "user/searchInviter")
    Observable<InviteDataBean> SearchInvite(@Query("user_id") String user_id,
                                            @Query("phone") String phone,
                                            @Query("token") String token);

    @GET(Config.VERSION + "user/doChangeInvitation")
    Observable<EditListDataBean> changeInvite(@Query("user_id") String user_id,
                                              @Query("code") String code,
                                              @Query("commend_user_id") String commend_user_id,
                                              @Query("token") String token);

    @GET(Config.VERSION + "user/renascence")
    Observable<RebirthDataBean> getRenascenceInfo(@Query("user_id") String user_id,
                                                  @Query("token") String token);

    @GET(Config.VERSION + "user/useRenascenceCard")
    Observable<ResponseBean> setRenascence(@Query("user_id") String user_id,
                                           @Query("code") String code,
                                           @Query("token") String token);
    //********************订单*************************

    @FormUrlEncoded
    @POST(Config.VERSION + "order/lists")
    Observable<OrderListDataBean> getOrderList(@Field("user_id") String user_id,
                                               @Field("type") int type,
                                               @Field("page") int page,
                                               @Field("page_size") int page_size,
                                               @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "order/abilityGrateful")
    Observable<ThanksListDataBean> getabilityGrateful(@Field("user_id") String user_id,
                                                      @Field("order_id") String order_id,
                                                      @Field("page") int page,
                                                      @Field("page_size") int page_size,
                                                      @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "order/helpGoodsGrateful")
    Observable<ThanksListDataBean> gethelpGoodsGrateful(@Field("user_id") String user_id,
                                                        @Field("order_id") String order_id,
                                                        @Field("page") int page,
                                                        @Field("page_size") int page_size,
                                                        @Field("help_type") int help_type,
                                                        @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "order/shipping")
    Observable<TrankListDataBean> getOrderTrankList(@Field("user_id") String user_id,
                                                    @Field("order_id") String order_id,
                                                    @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "order/info")
    Observable<DetailDataBean> getOrderDetail(@Field("user_id") String user_id,
                                              @Field("order_id") String order_id,
                                              @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "order/cancelOrder")
    Observable<EditDataBean> cancelAction(@Field("user_id") String user_id,
                                          @Field("order_id") String order_id,
                                          @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "order/careReceive")
    Observable<EditDataBean> careReceiveOrder(@Field("user_id") String user_id,
                                              @Field("order_id") String order_id,
                                              @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "order/confirmOrder")
    Observable<EditDataBean> confirmReceipt(@Field("user_id") String user_id,
                                            @Field("order_id") String order_id,
                                            @Field("token") String token);


    //********************生活方式互祝*************************
    @GET(Config.VERSION + "Helpgoods/lists")
    Observable<LifeStyleListDataBean> getLifeStyleList(@Query("user_id") String user_id,
                                                       @Query("time_sort") int time_sort,
                                                       @Query("Search") String Search,
                                                       @Query("receive_user_id") String h_user_id,
                                                       @Query("progress") int progress,
                                                       @Query("status") int status,
                                                       @Query("help_status") int help_status,
                                                       @Query("page") int page,
                                                       @Query("page_size") int page_size,
                                                       @Query("token") String token);

    @GET(Config.VERSION + "helpgoods/info")
    Observable<LifeStyleDetailDataBean> getLifeStyleDetail(@Query("user_id") String user_id,
                                                           @Query("help_goods_id") String help_goods_id,
                                                           @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "helpgoods/comment_list")
    Observable<LifeStyleDetailDataBean> getLifeStyleCommentList(@Field("user_id") String user_id,
                                                                @Field("help_goods_id") String help_goods_id,
                                                                @Field("page") int page,
                                                                @Field("page_size") int page_size,
                                                                @Field("type") int type,
                                                                @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "helpgoods/pay")
    Observable<SKBPayDataBean> lifeStylePayHelp(@Field("user_id") String user_id,
                                                @Field("help_comment_content") String help_comment_content,
                                                @Field("help_goods_skb_money_id") String help_goods_skb_money_id,
                                                @Field("help_goods_id") String help_goods_id,
                                                @Field("skb_price") int skb_price,
                                                @Field("help_type") int help_type,
                                                @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "help/help_comment_ajax_reply")
    Observable<LifeStyleCommentDataBean> sendCommentLifeStyle(@Field("user_id") String user_id,
                                                              @Field("content") String content,
                                                              @Field("be_comment_id") int be_comment_id,
                                                              @Field("type") int type,
                                                              @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "help/help_comment_ajax_reply_del")
    Observable<ResponseBean> delComment(@Field("user_id") String user_id,
                                        @Field("mutual_help_comment_id") int mutual_help_comment_id,
                                        @Field("token") String token);

    @GET(Config.VERSION + "helpgoods/help_goods_ranking")
    Observable<LifeRankListDataBean> getLifeRankList(@Query("user_id") String user_id,
                                                     @Query("help_goods_id") String help_goods_id,
                                                     @Query("page") int page,
                                                     @Query("page_size") int pageSize,
                                                     @Query("help_type") int help_type,
                                                     @Query("token") String token);

    @GET(Config.VERSION + "Helpgoods/HelpGoodsApply")
    Observable<LifeNeedDataBean> getLifeNeedHelpNumberTask(@Query("user_id") String user_id,
                                                           @Query("goods_id") String goods_id,
                                                           @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Helpgoods/saveHelpGoodsApply")
    Observable<EditDataBean> lifeStyleApplyAction(@Field("user_id") String user_id,
                                                  @Field("shop_goods_price_id") String shop_goods_price_id,
                                                  @Field("purpose_remark") String remark,
                                                  @Field("purpose") int purpose,
                                                  @Field("address_id") String address_id,
                                                  @Field("apply_goods_number") int apply_goods_number,
                                                  @Field("receive_user_id") String receive_user_id,
                                                  @Field("goods_id") String goods_id,
                                                  @Field("remark") String purpose_remark,
                                                  @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Helpgoods/setRead")
    Observable<ResponseBean> setLifeTaskRead(@Field("user_id") String user_id,
                                             @Field("help_goods_id") String help_goods_id,
                                             @Field("token") String token);

    //********************生命能量互祝*************************
    //http://test.t.asdyf.com/api/v1_0_0/help/help_list?user_id=1335&token=8d8b7187d7b5cb55d0b82d7b6e57d978&id=2&progress=0&status=1
    @GET(Config.VERSION + "help/help_list")
    Observable<HelpEnergyListDataBean> getHelpList(@Query("user_id") String user_id,
                                                   @Query("id") int id,
                                                   @Query("Search") String Search,
                                                   @Query("h_user_id") String h_user_id,
                                                   @Query("progress") int progress,
                                                   @Query("status") int status,
                                                   @Query("help_status") int help_status,
                                                   @Query("goods_id") int goods_id,
                                                   @Query("page") int page,
                                                   @Query("page_size") int page_size,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "help/action_list")
    Observable<ActionListDataBean> getHelpActionList(@Query("user_id") String user_id,
                                                     @Query("token") String token);

    @GET(Config.VERSION + "help/lj_payment")
    Observable<EnergyDetailDataBean> getHelpDetail(@Query("user_id") String user_id,
                                                   @Query("id") String id,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "help/help_list_rank")
    Observable<RankListDataBean> getRankList(@Query("user_id") String user_id,
                                             @Query("msg_id") String msg_id,
                                             @Query("page") int page,
                                             @Query("page_size") int pageSize,
                                             @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "help/help_comment_ajax_list")
    Observable<EnergyDetailDataBean> getCommentList(@Field("user_id") String user_id,
                                                    @Field("mutual_help_id") String mutual_help_id,
                                                    @Field("page") int page,
                                                    @Field("page_size") int page_size,
                                                    @Field("type") int type,
                                                    @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "help/help_comment_ajax_reply")
    Observable<CommentDataBean> sendComment(@Field("user_id") String user_id,
                                            @Field("content") String content,
                                            @Field("be_comment_id") int be_comment_id,
                                            @Field("type") int type,
                                            @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Ability/help")
    Observable<PayWXDataBean> payHelp(@Field("user_id") String user_id,
                                      @Field("help_comment_content") String help_comment_content,
                                      @Field("pay_way") String pay_way,
                                      @Field("msg_id") String msg_id,
                                      @Field("money") int money,
                                      @Field("token") String token);

    @GET(Config.VERSION + "help/paySuccess")
    Observable<PayDataBean> getRedEnvelopeData(@Query("user_id") String user_id,
                                               @Query("one_order_id") String one_order_id,
                                               @Query("token") String token);

    @GET(Config.VERSION + "user/openRedPacket")
    Observable<OpenRedDataBean> openRedEnvelope(@Query("user_id") String user_id,
                                                @Query("one_order_id") String one_order_id,
                                                @Query("token") String token);

    @GET(Config.VERSION + "user/ajaxOpenRedPacketPk")
    Observable<OpenRedDataBean> GoodLuckopenRedEnvelope(@Query("user_id") String user_id,
                                                        @Query("mutual_help_user_red_packet_id") String mutual_help_user_red_packet_id,
                                                        @Query("token") String token);

    @GET(Config.VERSION + "user/ajaxOpenAllRedPacket")
    Observable<OpenRedDataBean> openRedEnvelopeOnekey(@Query("user_id") String user_id,
                                                      @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "help/myDedication")
    Observable<MyDedicationListDataBean> getMyDedicationList(@Field("user_id") String user_id,
                                                             @Field("page") int page,
                                                             @Field("pageSize") int pageSize,
                                                             @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "help/myHelpDedicationDetails")
    Observable<MyDedicationListDataBean> getDetail(@Field("user_id") String user_id,
                                                   @Field("h_user_id") String h_user_id,
                                                   @Field("page") int page,
                                                   @Field("pageSize") int pageSize,
                                                   @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "help/myGratitude")
    Observable<MyListDataBean> getMyGratitudeList(@Field("user_id") String user_id,
                                                  @Field("page") int page,
                                                  @Field("pageSize") int pageSize,
                                                  @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "help/helpMeDedicationDetails")
    Observable<MyListDataBean> getGratitudeDetail(@Field("user_id") String user_id,
                                                  @Field("help_me_user_id") String help_me_user_id,
                                                  @Field("page") int page,
                                                  @Field("pageSize") int pageSize,
                                                  @Field("token") String token);

    @GET(Config.VERSION + "family/index")
    Observable<MyFamilyListDataBean> getFamilyList(@Query("user_id") String user_id,
                                                   @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "family/del")
    Observable<EditDataBean> delFamily(@Field("user_id") String user_id,
                                       @Field("family_user_id") String family_user_id,
                                       @Field("token") String token);

    @GET(Config.VERSION + "family/get_myfamily")
    Observable<EditListDataBean> addOrEditFamily(@Query("user_id") String user_id,
                                                 @Query("family_user_id") String family_user_id,
                                                 @Query("user_name") String user_name,
                                                 @Query("birthday") String birthday,
                                                 @Query("pid") String pid,
                                                 @Query("cid") String cid,
                                                 @Query("aid") String aid,
                                                 @Query("phone_number") String phone_number,
                                                 @Query("call_user") String call_user,
                                                 @Query("relationship") String relationship,
                                                 @Query("token") String token);

    @GET(Config.VERSION + "family/family_relation")
    Observable<RelationListDataBean> getFamilyRelation(@Query("user_id") String user_id,
                                                       @Query("token") String token);

    @GET(Config.VERSION + "family/edit")
    Observable<MyFamilyDataBean> getFamilyEditInfo(@Query("user_id") String user_id,
                                                   @Query("family_user_id") String family_user_id,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "help/mutual_ranking")
    Observable<MyRankListDataBean> getMutualRankingList(@Query("user_id") String user_id,
                                                        @Query("type") int type,
                                                        @Query("page") int page,
                                                        @Query("page_size") int page_size,
                                                        @Query("sort") int sort,
                                                        @Query("token") String token);

    @GET(Config.VERSION + "user/automationHelpInfo")
    Observable<AutoHelpDataBean> getAutoInfo(@Query("user_id") String user_id,
                                             @Query("token") String token);

    @GET(Config.VERSION + "user/saveAutomationHelp")
    Observable<ResponseBean> saveUpdateAutoHelp(@Query("user_id") String user_id,
                                                @Query("is_automation_help") int is_automation_help,
                                                @Query("automation_help_type") int automation_help_type,
                                                @Query("mutual_help_money_id") int mutual_help_money_id,
                                                @Query("everyday_help_number") int everyday_help_number,
                                                @Query("token") String token);

    @GET(Config.VERSION + "knp/helpInfo")
    Observable<EnergyDetailDataBean> getKNPMsgDetail(@Query("user_id") String user_id,
                                                     @Query("knp_msg_id") String knp_msg_id,
                                                     @Query("token") String token);

    @GET(Config.VERSION + "knpteam/roominfo")
    Observable<EnergyDetailDataBean> getKnpTeamRoomDetail(@Query("user_id") String user_id,
                                                          @Query("knp_team_room_id") String knp_team_room_id,
                                                          @Query("token") String token);

    @GET(Config.VERSION + "life/repayInfo")
    Observable<EnergyDetailDataBean> getBaoZMsgDetail(@Query("user_id") String user_id,
                                                      @Query("life_repay_id") String life_repay_id,
                                                      @Query("token") String token);

    @GET(Config.VERSION + "Lifebasic/info")
    Observable<EnergyDetailDataBean> getLifeBasicDetail(@Query("user_id") String user_id,
                                                        @Query("life_basic_id") String life_basic_id,
                                                        @Query("token") String token);

    @GET(Config.VERSION + "Giveactivity/index")
    Observable<EnergyDetailDataBean> getGiveactivityInfo(@Query("user_id") String user_id,
                                                         @Query("token") String token);

    @GET(Config.VERSION + "life/addForward")
    Observable<ResponseBean> sendLifeDetailShareNum(@Query("user_id") String user_id,
                                                    @Query("life_repay_id") String life_repay_id,
                                                    @Query("token") String token);

    @GET(Config.VERSION + "lifebasic/addForward")
    Observable<ResponseBean> sendLifeBasicDetailShareNum(@Query("user_id") String user_id,
                                                         @Query("life_basic_id") String life_basic_id,
                                                         @Query("token") String token);

    @GET(Config.VERSION + "knp/addForward")
    Observable<ResponseBean> sendKNPDetailShareNum(@Query("user_id") String user_id,
                                                   @Query("knp_msg_id") String knp_msg_id,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "commonweal/info")
    Observable<EnergyDetailDataBean> getCommonwealMsgDetail(@Query("user_id") String user_id,
                                                            @Query("commonweal_activity_id") String commonweal_activity_id,
                                                            @Query("token") String token);

    @GET(Config.VERSION + "Paydeposit/index")
    Observable<DetailDataBean> getYaJinPayInfo(@Query("user_id") String user_id,
                                               @Query("order_id") String order_id,
                                               @Query("token") String token);

    @GET(Config.VERSION + "paydeposit/helpgoods")
    Observable<DetailDataBean> getYaJinLifeStylePayInfo(@Query("user_id") String user_id,
                                                        @Query("order_id") String order_id,
                                                        @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "knp/pay")
    Observable<PayWXDataBean> KNPPayHelp(@Field("user_id") String user_id,
                                         @Field("help_comment_content") String help_comment_content,
                                         @Field("payWay") String pay_way,
                                         @Field("id") String msg_id,
                                         @Field("money") int money,
                                         @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "life/repayToPay")
    Observable<PayWXDataBean> BaoZhangPayHelp(@Field("user_id") String user_id,
                                              @Field("help_comment_content") String help_comment_content,
                                              @Field("payment_channel") String payment_channel,
                                              @Field("life_id") String life_id,
                                              @Field("money") int money,
                                              @Field("help_number") int help_number,
                                              @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "lifebasic/helpPay")
    Observable<PayWXDataBean> LifeBasicDetailPay(@Field("user_id") String user_id,
                                                 @Field("help_comment_content") String help_comment_content,
                                                 @Field("payment_channel") String payment_channel,
                                                 @Field("life_basic_id") String life_basic_id,
                                                 @Field("money") int money,
                                                 @Field("help_number") int help_number,
                                                 @Field("is_share_help") String is_share_help,
                                                 @Field("pay_source") String pay_source,
                                                 @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Commonweal/pay")
    Observable<PayWXDataBean> GenuisPayHelp(@Field("user_id") String user_id,
                                            @Field("share_user_id") String share_user_id,
                                            @Field("commonweal_activity_id") String commonweal_activity_id,
                                            @Field("payWay") String pay_way,
                                            @Field("msg_id") String msg_id,
                                            @Field("money") int money,
                                            @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "ticket/receivePay")
    Observable<PayWXDataBean> receivePay(@Field("user_id") String user_id,
                                         @Field("receive_user_id") String receive_user_id,
                                         @Field("payment_channel") String pay_way,
                                         @Field("user_ticket_id") String user_ticket_id,
                                         @Field("pay_check_code") String pay_check_code,
                                         @Field("pay_money") String pay_money,
                                         @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "ticket/paymentCodePay")
    Observable<PayWXDataBean> MerchantPay(@Field("user_id") String user_id,
                                          @Field("payment_order_id") String payment_order_id,
                                          @Field("payment_channel") String pay_way,
                                          @Field("pay_check_code") String pay_check_code,
                                          @Field("current_time") String current_time,
                                          @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Commonpay/index")
    Observable<PayWXDataBean> allNewPay(@Field("user_id") String user_id,
                                        @Field("json_datas") String json_datas,
                                        @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Doctor/createOrder")
    Observable<PayWXDataBean> doctorPay(@Field("user_id") String user_id,
                                        @Field("payment_channel") String payment_channel,
                                        @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "life/openAutomationHelp")
    Observable<PayWXDataBean> VoluntePay(@Field("user_id") String user_id,
                                         @Field("payment_channel") String payment_channel,
                                         @Field("type") String type,
                                         @Field("pay_money") String pay_money,
                                         @Field("volunteer_debt_item_id") String volunteer_debt_item_id,
                                         @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "life/ajaxLeifengChange")
    Observable<PayWXDataBean> ChangeLeiFengPay(@Field("user_id") String user_id,
                                               @Field("payment_channel") String payment_channel,
                                               @Field("price") String price,
                                               @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "giveactivity/doApply")
    Observable<PayWXDataBean> GiveactivityApplyPay(@Field("user_id") String user_id,
                                                   @Field("payment_channel") String payment_channel,
                                                   @Field("pay_source") String pay_source,
                                                   @Field("address_id") String address_id,
                                                   @Field("money") String money,
                                                   @Field("__app_pay_token__") String __app_pay_token__,
                                                   @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "lifebasic/ajaxSaveApply")
    Observable<PayWXDataBean> LifeBasicApplyPay(@Field("user_id") String user_id,
                                                @Field("json_datas") String json_datas,
                                                @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Intelligentize/lifePayOrder")
    Observable<PayWXDataBean> RechargePay(@Field("user_id") String user_id,
                                          @Field("payment_channel") String payment_channel,
                                          @Field("price") String pay_money,
                                          @Field("token") String token);

    //********************商城*************************************************************************
    @GET(Config.VERSION + "shop/index")
    Observable<MallGoodsListDataBean> getMallGoodsList(@Query("user_id") String user_id,
                                                       @Query("category") int category,
                                                       @Query("time_sort") int time_sort,
                                                       @Query("price_sort") int price_sort,
                                                       @Query("hot_sort") int hot_sort,
                                                       @Query("solar_terms") int solar_terms,
                                                       @Query("search") String search,
                                                       @Query("page") int page,
                                                       @Query("page_size") int page_size,
                                                       @Query("token") String token);

    @GET(Config.VERSION + "shop/catetorysAndSolarTerms")
    Observable<JieQiListDataBean> getJieQiList(@Query("user_id") String user_id,
                                               @Query("token") String token);

    @GET(Config.VERSION + "shop/goodsInfo")
    Observable<GoodsDetailDataBean> getMallGoodsDetail(@Query("user_id") String user_id,
                                                       @Query("shop_goods_id") String shop_goods_id,
                                                       @Query("token") String token);

    @GET(Config.VERSION + "shop/guessYouLike")
    Observable<ShopCartDataBean> getTuiJianList(@Query("user_id") String user_id,
                                                @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "shop/createOrder")
    Observable<EditListDataBean> submitGoodsOrder(@Field("user_id") String user_id,
                                                  @Field("address_id") String address_id,
                                                  @Field("total_skb_price") String total_skb_price,
                                                  @Field("total_super_ability") String total_super_ability,
                                                  @Field("orders_datas") String orders_datas,
                                                  @Field("token") String token);

    //*******************公社******************************************************
    @GET(Config.VERSION + "group/joinGroupList")
    Observable<CommuneListDataBean> getCommuneList(@Query("user_id") String user_id,
                                                   @Query("memberNum") int memberNum,
                                                   @Query("bePraisedNum") int bePraisedNum,
                                                   @Query("energy") int energy,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "group/myGroup")
    Observable<CommuneListDataBean> getCommuneInfo(@Query("user_id") String user_id,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "group/ajaxTodayAbility")
    Observable<CommuneListDataBean> getMineCommuneTodayList(@Query("user_id") String user_id,
                                                            @Query("group_id") int group_id,
                                                            @Query("page") int page,
                                                            @Query("page_size") int page_size,
                                                            @Query("token") String token);

    @GET(Config.VERSION + "group/ajaxTotalAbility")
    Observable<CommuneListDataBean> getMineCommuneLeiJiList(@Query("user_id") String user_id,
                                                            @Query("group_id") int group_id,
                                                            @Query("page") int page,
                                                            @Query("page_size") int page_size,
                                                            @Query("token") String token);

    @GET(Config.VERSION + "group/teamList")
    Observable<CommuneListDataBean> getTeamList(@Query("user_id") String user_id,
                                                @Query("group_id") int group_id,
                                                @Query("token") String token);

    @GET(Config.VERSION + "group/ajaxTeamMember")
    Observable<CommuneListDataBean> getMemberList(@Query("user_id") String user_id,
                                                  @Query("team_id") int team_id,
                                                  @Query("search") String search,
                                                  @Query("page") int page,
                                                  @Query("pageSize") int pageSize,
                                                  @Query("token") String token);

    @GET(Config.VERSION + "group/ajaxJoinTeam")
    Observable<EditListDataBean> JoinCommune(@Query("user_id") String user_id,
                                             @Query("group_id") int group_id,
                                             @Query("team_id") int team_id,
                                             @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "group/ajaxGroupAvatar")
    Observable<EditThumbDataBean> uploadCommuneAblum(@Field("user_id") String user_id,
                                                     @Field("group_id") int group_id,
                                                     @Field("file") String file,
                                                     @Field("token") String token);

    @GET(Config.VERSION + "group/ajaxSaveNotice")
    Observable<EditListDataBean> publish(@Query("user_id") String user_id,
                                         @Query("group_id") int group_id,
                                         @Query("content") String content,
                                         @Query("token") String token);

    @GET(Config.VERSION + "group/ajaxGroupLikes")
    Observable<EditListDataBean> ClickLike(@Query("user_id") String user_id,
                                           @Query("group_id") int group_id,
                                           @Query("token") String token);

    @GET(Config.VERSION + "group/createTeam")
    Observable<CommuneListDataBean> getCreateTeamInfo(@Query("user_id") String user_id,
                                                      @Query("team_id") int team_id,
                                                      @Query("token") String token);

    @GET(Config.VERSION + "group/ajaxSearchTeamLeader")
    Observable<CommuneDataBean> CreateTeamSearch(@Query("user_id") String user_id,
                                                 @Query("phone") String phone,
                                                 @Query("custom_name") String custom_name,
                                                 @Query("token") String token);

    @GET(Config.VERSION + "group/saveTeamInfo")
    Observable<EditListDataBean> CreateTeamSaveInfo(@Query("user_id") String user_id,
                                                    @Query("team_id") int team_id,
                                                    @Query("solar_terms_name") String solar_terms_name,
                                                    @Query("solar_terms_en") String solar_terms_en,
                                                    @Query("team_user_id") String team_user_id,
                                                    @Query("token") String token);
}


