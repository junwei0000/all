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
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpCreatDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpGroupRoomDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.ActionListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpEnergyListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.CommentDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.EnergyDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.OpenRedDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.PayDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.rank.bean.RankListDataBean;
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
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.FQCenterDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.FQDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.MessageListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.home.bangdan.BangDanDataBean;
import com.longcheng.lifecareplan.modular.home.bean.HomeDataBean;
import com.longcheng.lifecareplan.modular.home.bean.PoActionListDataBean;
import com.longcheng.lifecareplan.modular.home.bean.QuickTeamDataBean;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneDataBean;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneListDataBean;
import com.longcheng.lifecareplan.modular.home.domainname.bean.CommuDetailDataBean;
import com.longcheng.lifecareplan.modular.home.domainname.bean.HotListDataBean;
import com.longcheng.lifecareplan.modular.home.domainname.bean.domainDataBean;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.bean.HealthyDeliveryResultBean;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginDataBean;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.GetEnergyListDataBean;
import com.longcheng.lifecareplan.modular.mine.awordofgold.bean.AWordOfGoldResponseBean;
import com.longcheng.lifecareplan.modular.mine.bill.bean.BillResultBean;
import com.longcheng.lifecareplan.modular.mine.cashflow.bean.CashFlowDataBean;
import com.longcheng.lifecareplan.modular.mine.changeinviter.bean.InviteDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.bean.HealTeackDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.DocRewardListDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.RewardDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.VolSearchDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean.AYApplyListDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean.BasicInfoListDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.volunteer.bean.AYRecordListDataBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.ApplyEmergencyBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.CSRecordBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.CashInfoBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.CertifyBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.CheckNeedOrder;
import com.longcheng.lifecareplan.modular.mine.emergencys.EmergencyBangDanBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.EmergencyDetaiBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.EmergencyListBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.EmergencysPayBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.HarvestBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.HeloneedIndexBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.ResultApplyBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.SubmitMoneyBean;
import com.longcheng.lifecareplan.modular.mine.goodluck.bean.GoodLuckListDataBean;
import com.longcheng.lifecareplan.modular.mine.invitation.bean.InvitationResultBean;
import com.longcheng.lifecareplan.modular.mine.message.bean.MessageDataBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderListDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.detail.bean.DetailDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.ordertracking.bean.TrankListDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.bean.ThanksListDataBean;
import com.longcheng.lifecareplan.modular.mine.phosphor.RedirectDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.ExitutorSearchBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PionApplyDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerCounterDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataListBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerXuFeiDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.UserBankDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.ZYBBDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.caililist.bean.PionCailiListDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.pionopen.bean.PionOpenListDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.tutorExit.bean.PionTutorExitListDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetRecordDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.openorder.bean.PionOpenGMListDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.fqborder.bean.PionRewardListDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionActiviesDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBHistoryDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBSelectDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.RiceActiviesDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.RiceActiviesListDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.ZYBSelectMonRecrodListDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.ZYBZuDuiDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.bean.PionDaiCDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.bean.AcountInfoDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.zyblist.bean.ZYBRecordListDataBean;
import com.longcheng.lifecareplan.modular.mine.relationship.bean.RelationshipBean;
import com.longcheng.lifecareplan.modular.mine.relief.ApplyReliefBean;
import com.longcheng.lifecareplan.modular.mine.relief.HelpReliefBean;
import com.longcheng.lifecareplan.modular.mine.relief.ReliefBottomInfoBean;
import com.longcheng.lifecareplan.modular.mine.relief.ReliefCommBean;
import com.longcheng.lifecareplan.modular.mine.relief.ReliefDetailsBean;
import com.longcheng.lifecareplan.modular.mine.relief.ReliefDetailsListBean;
import com.longcheng.lifecareplan.modular.mine.relief.ReliefItemBean;
import com.longcheng.lifecareplan.modular.mine.relief.ReliefPopupBean;
import com.longcheng.lifecareplan.modular.mine.rewardcenters.bean.RewardCentersResultBean;
import com.longcheng.lifecareplan.modular.mine.set.bean.PushDataBean;
import com.longcheng.lifecareplan.modular.mine.set.bean.VersionAfterBean;
import com.longcheng.lifecareplan.modular.mine.set.bean.VersionDataBean;
import com.longcheng.lifecareplan.modular.mine.signIn.bean.SignInDataBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.CornucopiaBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.CornucopiaListBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.RankListBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditThumbDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetUserSETDataBean;
import com.longcheng.lifecareplan.push.jpush.message.PairUDataBean;
import com.longcheng.lifecareplan.utils.myview.address.SelctAeraDataInfo;
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
    @POST(Config.VERSION + "Files/ajaxUploadPhoto")
    Observable<EditDataBean> uploadCertificateImg(@Field("user_id") String user_id,
                                                  @Field("file") String file,
                                                  @Field("type") int type,
                                                  @Field("token") String token);

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
    @POST(Config.VERSION + "lifebasic/recieive_festival_card")
    Observable<EditDataBean> recieiveFestivalCard(@Field("user_id") String user_id,
                                                  @Field("life_basic_festival_config_id") String life_basic_festival_config_id,
                                                  @Field("token") String token);

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

    @GET(Config.VERSION + "user/updateChoAbilityLayer")
    Observable<ResponseBean> updateChoAbilityLayer(@Query("user_id") String user_id,
                                                   @Query("token") String token);

    //********************申请互祝***************************
    @FormUrlEncoded
    @POST(Config.VERSION + "help/needHelpNumberTask")
    Observable<ActionDataBean> getNeedHelpNumberTask(@Field("user_id") String user_id,
                                                     @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "blesscard/gratefulRepay")
    Observable<EditDataBean> gratefulRepay(@Field("user_id") String user_id,
                                           @Field(" bless_grateful_push_queue_id") String bless_grateful_push_queue_id,
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
    @POST(Config.VERSION + "relieve/isCertify")
    Observable<CertifyBean> isCertify(@Field("user_id") String user_id,
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


    @GET(Config.VERSION + "Companystatistics/statisticsInfo")
    Observable<CashFlowDataBean> userStatisticsInfo(@Query("user_id") String user_id,
                                                    @Query("date") String date,
                                                    @Query("token") String token);

    @GET(Config.VERSION + "Companystatistics/userStatisticsInfo")
    Observable<CashFlowDataBean> getFlowDetail(@Query("user_id") String user_id,
                                               @Query("type") int type,
                                               @Query("date") String date,
                                               @Query("page") int page,
                                               @Query("pageSize") int pageSize,
                                               @Query("token") String token);

    //********************激活能量*************************
    @GET(Config.VERSION + "ability/getRechargeInfoNew")
    Observable<GetEnergyListDataBean> getRechargeInfo(@Query("user_id") String user_id,
                                                      @Query("token") String token);

    @GET(Config.VERSION + "zhuyoubaoteam/rules")
    Observable<GetEnergyListDataBean> getZYZBMoneyInfo(@Query("user_id") String user_id,
                                                       @Query("token") String token);

    @GET(Config.VERSION + "zhuyoubaoteam/teamItemList")
    Observable<ZYBSelectMonRecrodListDataBean> getTeamItemList(@Query("user_id") String user_id,
                                                               @Query("token") String token);

    @GET(Config.VERSION + "zhuyoubaoteam/activityPage")
    Observable<PionActiviesDataBean> getActivityPageInfo(@Query("user_id") String user_id,
                                                         @Query("token") String token);

    @GET(Config.VERSION + "Jieqiactivity/index")
    Observable<RiceActiviesDataBean> getRiceActivityPageInfo(@Query("user_id") String user_id,
                                                             @Query("token") String token);

    @GET(Config.VERSION + "Jieqiactivity/activityUser")
    Observable<RiceActiviesListDataBean> getRiceActivityList(@Query("user_id") String user_id,
                                                             @Query("page") int page,
                                                             @Query("page_size") int page_size,
                                                             @Query("token") String token);

    @GET(Config.VERSION + "zhuyoubaoteam/searchSeniorityUser")
    Observable<PionZFBSelectDataBean> searchSeniorityUser(@Query("user_id") String user_id,
                                                          @Query("keyword") String keyword,
                                                          @Query("token") String token);

    @GET(Config.VERSION + "Jieqiactivity/sign")
    Observable<ResponseBean> Jieqiactivity(@Query("user_id") String user_id,
                                           @Query("token") String token);

    @GET(Config.VERSION + "zhuyoubaoteam/addSeniorityUser")
    Observable<ResponseBean> addSeniorityUser(@Query("user_id") String user_id,
                                              @Query("seniority_user_id") String seniority_user_id,
                                              @Query("token") String token);

    @GET(Config.VERSION + "zhuyoubaoteam/seniorityUserList")
    Observable<PionZFBSelectDataBean> seniorityUserList(@Query("user_id") String user_id,
                                                        @Query("page") int page,
                                                        @Query("page_size") int page_size,
                                                        @Query("token") String token);

    @GET(Config.VERSION + "zhuyoubaoteam/sign")
    Observable<ResponseBean> SignUpZFJ(@Query("user_id") String user_id,
                                       @Query("token") String token);

    @GET(Config.VERSION + "zhuyoubaoteam/teamInfo")
    Observable<PionZFBDataBean> ZYBTeamRoomInfo(@Query("user_id") String user_id,
                                                @Query("zhuyoubao_team_room_id") String zhuyoubao_team_room_id,
                                                @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "zhuyoubaoteam/createTeamRoom")
    Observable<EditDataBean> ZYBCreateTeamRoom(@Field("user_id") String user_id,
                                               @Field("zhuyoubao_team_rule_id") String zhuyoubao_team_rule_id,
                                               @Field("token") String token);

    @GET(Config.VERSION + "zhufubao/userCancelRechargeMatch")
    Observable<ResponseBean> cancelPiPeiDaiChong(@Query("user_id") String user_id,
                                                 @Query("user_zhufubao_order_id") String user_zhufubao_order_id,
                                                 @Query("token") String token);

    @GET(Config.VERSION + "zhufubao/userCancelMatch")
    Observable<ResponseBean> cancelPiPeiTiXian(@Query("user_id") String user_id,
                                               @Query("user_zhufubao_order_id") String user_zhufubao_order_id,
                                               @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Ability/userBlessRecharge")
    Observable<GetEnergyListDataBean> userBlessRecharge(@Field("user_id") String user_id,
                                                        @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Ability/assetRechargeNew")
    Observable<EditDataBean> assetRecharge(@Field("user_id") String user_id,
                                           @Field("money") String money,
                                           @Field("activate_ability_config_id") String activate_ability_config_id,
                                           @Field("type") int type,
                                           @Field("pay_source") String pay_source,
                                           @Field("payment_channel") String payment_channel,
                                           @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "zhufubao/userRecharge")
    Observable<ResponseBean> userRecharge(@Field("user_id") String user_id,
                                          @Field("recharge_money") String recharge_money,
                                          @Field("pay_source") String pay_source,
                                          @Field("pay_method") String pay_method,
                                          @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Ability/SubmitActivation")
    Observable<EditDataBean> SubmitActivation(@Field("user_id") String user_id,
                                              @Field("recharge_money") String recharge_money,
                                              @Field("pay_source") String pay_source,
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


    @GET(Config.VERSION + "record/entrepreneursZhufubao")
    Observable<BillResultBean> entrepreneursZhufubaoList(@Query("user_id") String user_id,
                                                         @Query("page") int page,
                                                         @Query("page_size") int page_size,
                                                         @Query("source_type") int source_type,
                                                         @Query("month") String month,
                                                         @Query("token") String token);

    @GET(Config.VERSION + "record/entrepreneursRebate")
    Observable<BillResultBean> entrepreneursRebateList(@Query("user_id") String user_id,
                                                       @Query("page") int page,
                                                       @Query("page_size") int page_size,
                                                       @Query("source_type") int source_type,
                                                       @Query("month") String month,
                                                       @Query("token") String token);

    @GET(Config.VERSION + "record/entrepreneursFuqibao")
    Observable<BillResultBean> entrepreneursFuqibaoList(@Query("user_id") String user_id,
                                                        @Query("page") int page,
                                                        @Query("page_size") int page_size,
                                                        @Query("source_type") int source_type,
                                                        @Query("month") String month,
                                                        @Query("token") String token);

    @GET(Config.VERSION + "record/userFuqibaoLimit")
    Observable<BillResultBean> entrepreneuserFuqibaoLimitList(@Query("user_id") String user_id,
                                                              @Query("page") int page,
                                                              @Query("page_size") int page_size,
                                                              @Query("source_type") int source_type,
                                                              @Query("month") String month,
                                                              @Query("token") String token);

    @GET(Config.VERSION + "record/userFuqibao")
    Observable<BillResultBean> userFuqibaoList(@Query("user_id") String user_id,
                                               @Query("page") int page,
                                               @Query("page_size") int page_size,
                                               @Query("source_type") int source_type,
                                               @Query("month") String month,
                                               @Query("token") String token);

    @GET(Config.VERSION + "record/userJieqibao")
    Observable<BillResultBean> userJieqibaoList(@Query("user_id") String user_id,
                                                @Query("page") int page,
                                                @Query("page_size") int page_size,
                                                @Query("source_type") int source_type,
                                                @Query("month") String month,
                                                @Query("token") String token);

    @GET(Config.VERSION + "record/userUzhufubao")
    Observable<BillResultBean> userUzhufubaoList(@Query("user_id") String user_id,
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
                                                       @Query("token") String token,
                                                       @Query("select_user_id") String selectUserId);

    @GET(Config.VERSION + "user/newGoldWord")
    Observable<AWordOfGoldResponseBean> getAWordOfGoldOther(@Query("user_id") String user_id,
                                                            @Query("token") String token,
                                                            @Query("user_name") String user_name,
                                                            @Query("phone") String phone,
                                                            @Query("area_simple") String area_simple,
                                                            @Query("birthday") String birthday,
                                                            @Query("pid") String pid,
                                                            @Query("cid") String cid,
                                                            @Query("aid") String aid);

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

    @GET(Config.VERSION + "promoter/wallet")
    Observable<AcountInfoDataBean> getwalletInfo(@Query("user_id") String user_id,
                                                 @Query("token") String token);


    @GET(Config.VERSION + "promoter/withdraw")
    Observable<AcountInfoDataBean> getAccountInfo(@Query("user_id") String user_id,
                                                  @Query("token") String token);

    @GET(Config.VERSION + "promoter/doWithdraw")
    Observable<ResponseBean> doWithdraw(@Query("user_id") String user_id,
                                        @Query("money") String money,
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


    @GET(Config.VERSION + "user/useRenascenceCard")
    Observable<ResponseBean> setRenascence(@Query("user_id") String user_id,
                                           @Query("code") String code,
                                           @Query("token") String token);

    //********************订单*************************
    @GET(Config.VERSION + "zhufubaoreward/customerConfirmPayment")
    Observable<ResponseBean> customerConfirmPayment(@Query("user_id") String user_id,
                                                    @Query("user_zhufubao_reward_id") String user_zhufubao_reward_id,
                                                    @Query("token") String token);

    @GET(Config.VERSION + "order/lists")
    Observable<OrderListDataBean> getOrderList(@Query("user_id") String user_id,
                                               @Query("type") int type,
                                               @Query("page") int page,
                                               @Query("page_size") int page_size,
                                               @Query("token") String token);

    @GET(Config.VERSION + "blesscard/solarTermSummary")
    Observable<ReportDataBean> getFuQuanByJieQi(@Query("user_id") String user_id,
                                                @Query("page") int page,
                                                @Query("page_size") int page_size,
                                                @Query("token") String token);

    @GET(Config.VERSION + "blesscard/daySummary")
    Observable<ReportDataBean> getFuQuanByDay(@Query("user_id") String user_id,
                                              @Query("page") int page,
                                              @Query("page_size") int page_size,
                                              @Query("token") String token);

    @GET(Config.VERSION + "blesscard/myReceive")
    Observable<ReportDataBean> getFuQiByReceive(@Query("user_id") String user_id,
                                                @Query("page") int page,
                                                @Query("page_size") int page_size,
                                                @Query("token") String token);

    @GET(Config.VERSION + "blesscard/mySponsor")
    Observable<ReportDataBean> getFuQiBySponsor(@Query("user_id") String user_id,
                                                @Query("page") int page,
                                                @Query("page_size") int page_size,
                                                @Query("token") String token);

    @GET(Config.VERSION + "blesscard/cardList")
    Observable<ReportDataBean> getFuLiList(@Query("user_id") String user_id,
                                           @Query("page") int page,
                                           @Query("pageSize") int page_size,
                                           @Query("type") int type,
                                           @Query("token") String token);

    @GET(Config.VERSION + "blesscard/myAchievement")
    Observable<ReportDataBean> getLoveResultList(@Query("user_id") String user_id,
                                                 @Query("token") String token);

    @GET(Config.VERSION + "Blesscard/apply")
    Observable<FQDetailDataBean> getBlesscardApply(@Query("user_id") String user_id,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "blesscard/info")
    Observable<FQDetailDataBean> getFuQDetailList(@Query("user_id") String user_id,
                                                  @Query("bless_id") String bless_id,
                                                  @Query("token") String token);

    @GET(Config.VERSION + "blesscard/lists")
    Observable<ReportDataBean> getFuQRepList(@Query("user_id") String user_id,
                                             @Query("type") int type,
                                             @Query("keyword") String keyword,
                                             @Query("page") int page,
                                             @Query("page_size") int page_size,
                                             @Query("token") String token);

    @GET(Config.VERSION + "blesscard/report")
    Observable<ReportDataBean> getFuQReportList(@Query("user_id") String user_id,
                                                @Query("type") int type,
                                                @Query("keyword") String keyword,
                                                @Query("select_status") int select_status,
                                                @Query("page") int page,
                                                @Query("page_size") int page_size,
                                                @Query("token") String token);

    @GET(Config.VERSION + "blesscard/doCash")
    Observable<EditDataBean> getLingQu(@Query("user_id") String user_id,
                                       @Query("bless_id") String bless_id,
                                       @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Blesscard/doApply")
    Observable<EditDataBean> BlesscardDoApply(@Field("user_id") String user_id,
                                              @Field("payment_channel") int payment_channel,
                                              @Field("pay_source") int pay_source,
                                              @Field("apply_number") int apply_number,
                                              @Field("super_ability") String super_ability,
                                              @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "im/userList")
    Observable<ReportDataBean> getMyCircleList(@Field("user_id") String user_id,
                                               @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "im/groupUsers")
    Observable<MessageListDataBean> getMyGroupList(@Field("user_id") String user_id,
                                                   @Field("group_id") String group_id,
                                                   @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "im/message")
    Observable<MessageListDataBean> getMyMessageCircleList(@Field("user_id") String user_id,
                                                           @Field("type") int type,
                                                           @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "blesscard/ThinkEnterQueue")
    Observable<ResponseBean> ThinkEnterQueue(@Field("user_id") String user_id,
                                             @Field("bless_id") String bless_id,
                                             @Field("think_user_id") String think_user_id,
                                             @Field("type") int type,
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
    @GET(Config.VERSION + "Wares/index")
    Observable<LifeStyleListDataBean> getLifeStyleList(@Query("user_id") String user_id,
                                                       @Query("time_sort") int time_sort,
                                                       @Query("Search") String Search,
                                                       @Query("receive_user_id") String h_user_id,
                                                       @Query("status") int status,
                                                       @Query("help_status") int help_status,
                                                       @Query("page") int page,
                                                       @Query("page_size") int page_size,
                                                       @Query("token") String token);

    @GET(Config.VERSION + "helpgoods/info")
    Observable<LifeStyleDetailDataBean> getLifeStyleDetail(@Query("user_id") String user_id,
                                                           @Query("help_goods_id") String help_goods_id,
                                                           @Query("token") String token);

    @GET(Config.VERSION + "wares/info")
    Observable<LifeStyleDetailDataBean> getLifeStyleDetailWares(@Query("user_id") String user_id,
                                                                @Query("help_wares_id") String help_wares_id,
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

    @GET(Config.VERSION + "Wares/apply")
    Observable<LifeNeedDataBean> getLifeApplyInfo(@Query("user_id") String user_id,
                                                  @Query("shop_goods_id") String shop_goods_id,
                                                  @Query("shop_goods_price_id") String shop_goods_price_id,
                                                  @Query("token") String token);


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
    @POST(Config.VERSION + "Wares/doApply")
    Observable<EditDataBean> lifeStyleApplyAction(@Field("user_id") String user_id,
                                                  @Field("shop_goods_id") String shop_goods_id,
                                                  @Field("shop_goods_price_id") String shop_goods_price_id,
                                                  @Field("receive_user_id") String receive_user_id,
                                                  @Field("addressId") String addressId,
                                                  @Field("remark") String remark,
                                                  @Field("pay_source") String pay_source,
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

    @GET(Config.VERSION + "blesscard/userBlessRank")
    Observable<BangDanDataBean> getBlessList(@Query("user_id") String user_id,
                                             @Query("page") int page,
                                             @Query("page_size") int page_size,
                                             @Query("token") String token);

    @GET(Config.VERSION + "ranking/blessRanking")
    Observable<BangDanDataBean> blessRanking(@Query("user_id") String user_id,
                                             @Query("page") int page,
                                             @Query("page_size") int page_size,
                                             @Query("token") String token);

    @GET(Config.VERSION + "ranking/centerRanking")
    Observable<BangDanDataBean> getEngryCenterList(@Query("user_id") String user_id,
                                                   @Query("page") int page,
                                                   @Query("page_size") int page_size,
                                                   @Query("type") int type,
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
                                      @Field("lat") double lat,
                                      @Field("lon") double lon,
                                      @Field("token") String token);

    @GET(Config.VERSION + "help/paySuccess")
    Observable<PayDataBean> getRedEnvelopeData(@Query("user_id") String user_id,
                                               @Query("one_order_id") String one_order_id,
                                               @Query("token") String token);

    @GET(Config.VERSION + "nfc/scan")
    Observable<EditDataBean> addRecord(@Query("user_id") String user_id,
                                       @Query("order_id") String order_id,
                                       @Query("lon") double lon,
                                       @Query("lat") double lat,
                                       @Query("lao_address") String lao_address,
                                       @Query("nfc_sn") String nfc_sn,
                                       @Query("token") String token);

    @GET(Config.VERSION + "Nfc/HelpInfo")
    Observable<NFCDetailDataBean> getNFCDetail(@Query("user_id") String user_id,
                                               @Query("order_id") String order_id,
                                               @Query("nfc_sn") String nfc_sn,
                                               @Query("token") String token);

    @GET(Config.VERSION + "Nfc/help_list")
    Observable<NFCDetailDataBean> getNFCDetailList(@Query("user_id") String user_id,
                                                   @Query("order_id") String order_id,
                                                   @Query("page") int page,
                                                   @Query("page_size") int page_size,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "nfc/scanRecord")
    Observable<NFCDetailListDataBean> getNFCRecord(@Query("user_id") String user_id,
                                                   @Query("order_id") String order_id,
                                                   @Query("nfc_sn") String nfc_sn,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "nfc/energyImport")
    Observable<NFCDetailDataBean> getNFCEnergyImport(@Query("user_id") String user_id,
                                                     @Query("order_id") String order_id,
                                                     @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "nfc/pay")
    Observable<ResponseBean> payNFCHelp(@Field("user_id") String user_id,
                                        @Field("msg_id") String msg_id,
                                        @Field("order_id") String order_id,
                                        @Field("pay_source") String pay_source,
                                        @Field("payment_channel") int payment_channel,
                                        @Field("mutual_help_money_id") String mutual_help_money_id,
                                        @Field("nfc_sn") String nfc_sn,
                                        @Field("token") String token);

    @GET(Config.VERSION + "user/openRedPacket")
    Observable<OpenRedDataBean> openRedEnvelope(@Query("user_id") String user_id,
                                                @Query("one_order_id") String one_order_id,
                                                @Query("token") String token);

    @GET(Config.VERSION + "user/setReadPush")
    Observable<EditDataBean> setReadPush(@Query("user_id") String user_id,
                                         @Query("app_push_id") String app_push_id,
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
                                                  @Field("carving") String carving,
                                                  @Field("token") String token);

    @GET(Config.VERSION + "zhuyoubaoteam/getHighestIdentity")
    Observable<ShopCartDataBean> getHighestIdentity(@Query("user_id") String user_id,
                                                    @Query("token") String token);

    @GET(Config.VERSION + "zhuyoubaoteam/getJieqiName")
    Observable<ShopCartDataBean> getJieqiName(@Query("user_id") String user_id,
                                              @Query("birthday") String birthday,
                                              @Query("token") String token);

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


    //申请救急
    @GET(Config.VERSION + "helpneed/apply")
    Observable<ApplyEmergencyBean> applyEmergency(@Query("user_id") String user_id,
                                                  @Query("token") String token);

    @GET(Config.VERSION + "helpneed/checkNeedOrder")
    Observable<CheckNeedOrder> checkNeedOrder(@Query("user_id") String user_id,
                                              @Query("token") String token);

    //帮助他
    @GET(Config.VERSION + "helpneed/receive")
    Observable<ResponseBean> receive(@Query("user_id") String user_id,
                                     @Query("help_need_order_id") int help_need_order_id,
                                     @Query("lat") double lat,
                                     @Query("lon") double lon,
                                     @Query("token") String token);

    //拒绝他
    @GET(Config.VERSION + "helpneed/refuse")
    Observable<ResponseBean> refuse(@Query("user_id") String user_id,
                                    @Query("help_need_order_id") int help_need_order_id,
                                    @Query("token") String token);

    //救济推荐弹框
    @FormUrlEncoded
    @POST(Config.VERSION + "relieve/isTop")
    Observable<ReliefPopupBean> isReliefPopou(@Field("user_id") String user_id,
                                              @Field("token") String token);

    //救济列表
    @FormUrlEncoded
    @POST(Config.VERSION + "relieve/getEnegryList")
    Observable<ReliefItemBean> getReliefList(@Field("user_id") String user_id,
                                             @Field("token") String token,
                                             @Field("page") String page,
                                             @Field("type") String type,
                                             @Field("keyworld") String keyworld);

    @GET(Config.VERSION + "ranking/blessCardRanking")
    Observable<EmergencyBangDanBean> blessCardRanking(@Query("user_id") String user_id,
                                                      @Query("page") int page,
                                                      @Query("page_size") int page_size,
                                                      @Query("type") int type,
                                                      @Query("token") String token);

    @GET(Config.VERSION + "helpneed/indexExtend")
    Observable<EmergencyListBean> indexExtend(@Query("user_id") String user_id,
                                              @Query("search_type") int search_type,
                                              @Query("token") String token);

    @GET(Config.VERSION + "helpneed/info")
    Observable<EmergencyDetaiBean> helpneed_info(@Query("user_id") String user_id,
                                                 @Query("help_need_id") int help_need_id,
                                                 @Query("token") String token);

    @GET(Config.VERSION + "helpneed/index")
    Observable<HeloneedIndexBean> helpneed_index(@Query("user_id") String user_id,
                                                 @Query("search_type") int search_type,
                                                 @Query("page") int page,
                                                 @Query("page_size") int page_size,
                                                 @Query("keyword") String keyword,
                                                 @Query("token") String token);

    @GET(Config.VERSION + "helpneed/pay")
    Observable<EmergencysPayBean> helpneed_pay(@Query("user_id") String user_id,
                                               @Query("help_need_id") int help_need_id,
                                               @Query("super_ability") int super_ability,
                                               @Query("token") String token);

    @GET(Config.VERSION + "helpneed/order")
    Observable<HarvestBean> helpneed_order(@Query("user_id") String user_id,
                                           @Query("help_need_id") int help_need_id,
                                           @Query("page") int page,
                                           @Query("page_size") int page_size,
                                           @Query("token") String token);

    @GET(Config.VERSION + "helpneed/customerList")
    Observable<CSRecordBean> helpneed_customerList(@Query("user_id") String user_id,
                                                   @Query("search_type") int search_type,
                                                   @Query("page") int page,
                                                   @Query("page_size") int page_size,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "helpneed/ajaxCashMoney")
    Observable<ResultApplyBean> helpneed_ajaxCashMoney(@Query("user_id") String user_id,
                                                       @Query("help_need_id") int help_need_id,
                                                       @Query("token") String token);

    @GET(Config.VERSION + "helpneed/confirmMakeMoney")
    Observable<SubmitMoneyBean> helpneed_confirmMakeMoney(@Query("user_id") String user_id,
                                                          @Query("help_need_id") int help_need_id,
                                                          @Query("token") String token);

    //救济列表详情页头部数据
    @FormUrlEncoded
    @POST(Config.VERSION + "relieve/getUserById")
    Observable<ReliefDetailsBean> getReliefDetailsTop(@Field("user_id") String user_id,
                                                      @Field("token") String token,
                                                      @Field("checkUid") String checkUid);

    //救济列表详情页list
    @FormUrlEncoded
    @POST(Config.VERSION + "relieve/getEnegryDetailById")
    Observable<ReliefDetailsListBean> getReliefListDetails(@Field("user_id") String user_id,
                                                           @Field("token") String token,
                                                           @Field("checkUid") String checkUid,
                                                           @Field("page") String page);

    //申请救济
    @FormUrlEncoded
    @POST(Config.VERSION + "relieve/apply")
    Observable<ApplyReliefBean> applyRelief(@Field("user_id") String user_id,
                                            @Field("token") String token);


    //救济底部个人信息
    @FormUrlEncoded
    @POST(Config.VERSION + "relieve/getMeEnegry")
    Observable<ReliefBottomInfoBean> getReliefBottom(@Field("user_id") String user_id,
                                                     @Field("token") String token);

    //救急银行卡
    @FormUrlEncoded
    @POST(Config.VERSION + "helpneed/cashInfo")
    Observable<CashInfoBean> cashInfo(@Field("user_id") String user_id,
                                      @Field("help_need_id") int help_need_id,
                                      @Field("token") String token);

    //救济弹框搜索推荐人手机号
    @FormUrlEncoded
    @POST(Config.VERSION + "relieve/getUserByPhone")
    Observable<ReliefCommBean> searchPhone(@Field("user_id") String user_id,
                                           @Field("token") String token,
                                           @Field("phone") String phone);

    //救济弹框确认推荐
    @FormUrlEncoded
    @POST(Config.VERSION + "relieve/recommendUser")
    Observable<ReliefCommBean> confirmRecom(@Field("user_id") String user_id,
                                            @Field("token") String token,
                                            @Field("phone") String phone);

    //救济帮助弹框显示
    @FormUrlEncoded
    @POST(Config.VERSION + "relieve/myHelp")
    Observable<HelpReliefBean> helpRelief(@Field("user_id") String user_id,
                                          @Field("token") String token);

    //处理救济帮助弹框
    @FormUrlEncoded
    @POST(Config.VERSION + "relieve/dealHelp")
    Observable<HelpReliefBean> dealHelp(@Field("user_id") String user_id,
                                        @Field("token") String token,
                                        @Field("hid") String hid,
                                        @Field("result") String result);

    //救济提取能量
    @FormUrlEncoded
    @POST(Config.VERSION + "relieve/cash")
    Observable<ReliefBottomInfoBean> extractEnergy(@Field("user_id") String user_id,
                                                   @Field("token") String token);


    /**
     * *************************创业中心***************************************************
     */
    @GET(Config.VERSION + "entrepreneurs/userCancelRechargeMatch")
    Observable<ResponseBean> PioncancelPiPeiDaiChong(@Query("user_id") String user_id,
                                                     @Query("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                                     @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/userRechargeShowEntreList")
    Observable<PionZFBHistoryDataBean> getRechargeHistory(@Query("user_id") String user_id,
                                                          @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/getEntresInfo")
    Observable<PionZFBSelectDataBean> getRechargeList(@Query("user_id") String user_id,
                                                      @Query("keyword") String keyword,
                                                      @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/userCancelMatch")
    Observable<ResponseBean> PioncancelPiPeiTiXian(@Query("user_id") String user_id,
                                                   @Query("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/blessPayment")
    Observable<PionDaiFuDataBean> PiongetDaiFuList(@Query("user_id") String user_id,
                                                   @Query("page") int page,
                                                   @Query("page_size") int page_size,
                                                   @Query("type") int type,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/blessPaymentRefuse")
    Observable<EditListDataBean> PionblessPaymentRefuse(@Query("user_id") String user_id,
                                                        @Query("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                                        @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/blessPaymentConfirm")
    Observable<EditListDataBean> PionblessPaymentConfirm(@Query("user_id") String user_id,
                                                         @Query("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                                         @Query("bless_payment_channel") int bless_payment_channel,
                                                         @Query("bless_payment_photo") String bless_payment_photo,
                                                         @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/userCashList")
    Observable<PionDaiFuDataBean> PiongetTiXianList(@Query("user_id") String user_id,
                                                    @Query("page") int page,
                                                    @Query("page_size") int page_size,
                                                    @Query("type") int type,
                                                    @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/userWithdrawCash")
    Observable<EditListDataBean> PionuserPaymentRefuse(@Query("user_id") String user_id,
                                                       @Query("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                                       @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/userConfirmCash")
    Observable<EditListDataBean> PionuserConfirmCash(@Query("user_id") String user_id,
                                                     @Query("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                                     @Query("bless_payment_channel") int bless_payment_channel,
                                                     @Query("bless_payment_photo") String bless_payment_photo,
                                                     @Query("token") String token);


    @GET(Config.VERSION + "entrepreneurs/userRechargeList")
    Observable<PionDaiCDataBean> PiongetCZList(@Query("user_id") String user_id,
                                               @Query("page") int page,
                                               @Query("page_size") int page_size,
                                               @Query("type") int type,
                                               @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/updateOrderStatus")
    Observable<ResponseBean> PionCZUpdateOrderStatus(@Query("user_id") String user_id,
                                                     @Query("order_id") String order_id,
                                                     @Query("status") int status,
                                                     @Query("user_payment_channel") int user_payment_channel,
                                                     @Query("user_payment_photo") String user_payment_photo,
                                                     @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/rechargeOrderConsultStatusUser")
    Observable<ResponseBean> PionCZuserConsult(@Query("user_id") String user_id,
                                               @Query("entrepreneurs_zhufubao_order_id") String entrepreneurs_zhufubao_order_id,
                                               @Query("user_consult_status") int user_consult_status,
                                               @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/rechargeOrderConsultStatusBless")
    Observable<ResponseBean> PionCZBlessConsult(@Query("user_id") String user_id,
                                                @Query("entrepreneurs_zhufubao_order_id") String entrepreneurs_zhufubao_order_id,
                                                @Query("bless_consult_status") int bless_consult_status,
                                                @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/blessTeacherRecharge")
    Observable<PionDaiCDataBean> PiongetDaiCList(@Query("user_id") String user_id,
                                                 @Query("page") int page,
                                                 @Query("page_size") int page_size,
                                                 @Query("type") int type,
                                                 @Query("token") String token);


    @GET(Config.VERSION + "entrepreneurs/blessTeacherCancel")
    Observable<ResponseBean> PiondaiCRefuse(@Query("user_id") String user_id,
                                            @Query("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                            @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/blesseTeacherAgree")
    Observable<ResponseBean> PiondaiCAgree(@Query("user_id") String user_id,
                                           @Query("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                           @Query("bless_payment_channel") int bless_payment_channel,
                                           @Query("bless_payment_photo") String bless_payment_photo,
                                           @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/blessDisagreeRecharge")
    Observable<ResponseBean> RefuseApply(@Query("user_id") String user_id,
                                         @Query("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                         @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/blessAgreeRecharge")
    Observable<ResponseBean> AgreeApply(@Query("user_id") String user_id,
                                        @Query("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                        @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/blesseTeacherAgree")
    Observable<EditListDataBean> PionblesseTeacherAgree(@Query("user_id") String user_id,
                                                        @Query("entrepreneurs_zhufubao_order_id") String entrepreneurs_zhufubao_order_id,
                                                        @Query("bless_payment_channel") int bless_payment_channel,
                                                        @Query("bless_payment_photo") String bless_payment_photo,
                                                        @Query("token") String token);


    @GET(Config.VERSION + "entrepreneurs/cashOrderConsultStatusUser")
    Observable<EditListDataBean> PionuserConsult(@Query("user_id") String user_id,
                                                 @Query("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                                 @Query("user_consult_status") int user_consult_status,
                                                 @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/cashOrderConsultStatusBless")
    Observable<EditListDataBean> PioncashOrderConsultStatusBless(@Query("user_id") String user_id,
                                                                 @Query("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                                                 @Query("bless_consult_status") int bless_consult_status,
                                                                 @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/componyBankInfo")
    Observable<PionDaiFuDataBean> PioncomponyBankInfo(@Query("user_id") String user_id,
                                                      @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/buyZhufubaoByWeixin")
    Observable<PayWXDataBean> PionbuyZhufubaoByWeixin(@Query("user_id") String user_id,
                                                      @Query("pay_source") int pay_source,
                                                      @Query("price") String price,
                                                      @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/buyZhufubaoByCash")
    Observable<EditDataBean> PionbuyZhufubaoByCash(@Query("user_id") String user_id,
                                                   @Query("pay_source") int pay_source,
                                                   @Query("price") String price,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "entre/getUserBankInfo")
    Observable<UserBankDataBean> getUserBankInfo(@Query("user_id") String user_id,
                                                 @Query("token") String token);

    @GET(Config.VERSION + "entre/saveApplyEntrepreneursMoney")
    Observable<ResponseBean> saveApplyEntrepreneursMoney(@Query("user_id") String user_id,
                                                         @Query("knp_team_bind_card_id") String knp_team_bind_card_id,
                                                         @Query("money") String money,
                                                         @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/zhufubaoRewardSubmit")
    Observable<EditListDataBean> PionzhufubaoRewardSubmit(@Query("user_id") String user_id,
                                                          @Query("knp_team_bind_card_id") String knp_team_bind_card_id,
                                                          @Query("zhufubao_number") String zhufubao_number,
                                                          @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/rewardList")
    Observable<PionDaiFuDataBean> PiongetRewardList(@Query("user_id") String user_id,
                                                    @Query("page") int page,
                                                    @Query("page_size") int page_size,
                                                    @Query("type") int type,
                                                    @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "entrepreneurs/userCashPushList")
    Observable<PairUDataBean> PionuserCashPushList(@Field("user_id") String user_id,
                                                   @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "entrepreneurs/teacherCancelCashOrder")
    Observable<EditListDataBean> PionteacherCancelCashOrder(@Field("user_id") String user_id,
                                                            @Field("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                                            @Field("token") String token);

    @GET(Config.VERSION + "entrepreneurs/teacherReceiveCashOrder")
    Observable<EditListDataBean> PionteacherReceiveCashOrder(@Query("user_id") String user_id,
                                                             @Query("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                                             @Query("is_customer") int is_customer,
                                                             @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "entrepreneurs/blessDaichongLayer")
    Observable<PairUDataBean> PionblessDaichongLayer(@Field("user_id") String user_id,
                                                     @Field("token") String token);

    @GET(Config.VERSION + "blesscard/index")
    Observable<FQCenterDataBean> getFuQCenterInfo(@Query("user_id") String user_id,
                                                  @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "blesscard/ajaxDoSign")
    Observable<ResponseBean> FuQCenterSign(@Field("user_id") String user_id,
                                           @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "entrepreneurs/teacherCancelRechargeOrder")
    Observable<EditListDataBean> PionteacherCancelRechargeOrder(@Field("user_id") String user_id,
                                                                @Field("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                                                @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "entrepreneurs/teacherReceiveRechargeOrder")
    Observable<EditListDataBean> PionteacherReceiveRechargeOrder(@Field("user_id") String user_id,
                                                                 @Field("entrepreneurs_zhufubao_order_id") String user_zhufubao_order_id,
                                                                 @Field("token") String token);

    @GET(Config.VERSION + "entrepreneurs/wallet")
    Observable<AcountInfoDataBean> PiongetwalletInfo(@Query("user_id") String user_id,
                                                     @Query("token") String token);

    @GET(Config.VERSION + "Entrepreneurs/userCashInfo")
    Observable<AcountInfoDataBean> getwalletOrderInfo(@Query("user_id") String user_id,
                                                      @Query("token") String token);

    @GET(Config.VERSION + "user/jieqibao")
    Observable<AcountInfoDataBean> PiongetUserJQBInfo(@Query("user_id") String user_id,
                                                      @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Entre/openPay")
    Observable<PayWXDataBean> PionopenPay(@Field("user_id") String user_id,
                                          @Field("entrepreneurs_level_config_id") String entrepreneurs_level_config_id,
                                          @Field("payment_channel") int payment_channel,
                                          @Field("pay_source") String pay_source,
                                          @Field("payment_img") String payment_img,
                                          @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "entre/doUpgrade")
    Observable<PayWXDataBean> PiondoUpgradePay(@Field("user_id") String user_id,
                                               @Field("entrepreneurs_level_config_id") String entrepreneurs_level_config_id,
                                               @Field("payment_channel") int payment_channel,
                                               @Field("pay_source") String pay_source,
                                               @Field("payment_img") String payment_img,
                                               @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Entre/renew")
    Observable<PioneerXuFeiDataBean> PionGetXuFeiInfo(@Field("user_id") String user_id,
                                                      @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Entre/saveRenew")
    Observable<PayWXDataBean> PionXuFeiPay(@Field("user_id") String user_id,
                                           @Field("entrepreneurs_renew_config_id") String entrepreneurs_renew_config_id,
                                           @Field("money") String money,
                                           @Field("payment_channel") int payment_channel,
                                           @Field("pay_source") String pay_source,
                                           @Field("payment_img") String payment_img,
                                           @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Entre/center")
    Observable<PioneerDataBean> PionGetMenuInfo(@Field("user_id") String user_id,
                                                @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Entre/applyMoneyInfo")
    Observable<PioneerDataBean> applyMoneyInfo(@Field("user_id") String user_id,
                                               @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Entre/applyMoneyList")
    Observable<PioneerDataListBean> applyMoneyList(@Field("user_id") String user_id,
                                                   @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Entre/receviceOrderStatusSave")
    Observable<ResponseBean> receviceOrderStatusSave(@Field("user_id") String user_id,
                                                     @Field("receive_order_status") int receive_order_status,
                                                     @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Entre/sellFuqibao")
    Observable<PioneerCounterDataBean> getSellBankInfo(@Field("user_id") String user_id,
                                                       @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Entre/balance")
    Observable<PioneerCounterDataBean> getCounterpoiseInfo(@Field("user_id") String user_id,
                                                           @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "entre/myRebate")
    Observable<PioneerCounterDataBean> getCailiInfo(@Field("user_id") String user_id,
                                                    @Field("token") String token);

    @GET(Config.VERSION + "entre/doRebate")
    Observable<ResponseBean> doRebateWithdraw(@Query("user_id") String user_id,
                                              @Query("knp_team_bind_card_id") String knp_team_bind_card_id,
                                              @Query("money") String money,
                                              @Query("token") String token);

    @GET(Config.VERSION + "entre/buyZhufubao")
    Observable<ZYBBDataBean> getZYBBalanceInfo(@Query("user_id") String user_id,
                                               @Query("token") String token);

    @GET(Config.VERSION + "entre/openBank")
    Observable<ZYBBDataBean> getopenBankInfo(@Query("user_id") String user_id,
                                             @Query("token") String token);

    @GET(Config.VERSION + "entre/openTask")
    Observable<PionApplyDataBean> openTask(@Query("user_id") String user_id,
                                           @Query("token") String token);

    @GET(Config.VERSION + "entre/renewBank")
    Observable<ZYBBDataBean> getrenewBankInfo(@Query("user_id") String user_id,
                                              @Query("token") String token);

    @GET(Config.VERSION + "entre/upgradeBank")
    Observable<ZYBBDataBean> getupgradeBankInfo(@Query("user_id") String user_id,
                                                @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Entre/saveZhufubao")
    Observable<PayWXDataBean> counterpoiseBuyZFb(@Field("user_id") String user_id,
                                                 @Field("money") String money,
                                                 @Field("payment_channel") int payment_channel,
                                                 @Field("pay_source") String pay_source,
                                                 @Field("payment_img") String payment_img,
                                                 @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Entre/doSellFuqibao")
    Observable<ResponseBean> counterpoiseSellFQB(@Field("user_id") String user_id,
                                                 @Field("fuqibao") String fuqibao,
                                                 @Field("bank_user_name") String bank_user_name,
                                                 @Field("bank_name") String bank_name,
                                                 @Field("bank_branch") String bank_branch,
                                                 @Field("bank_account") String bank_account,
                                                 @Field("token") String token);

    @GET(Config.VERSION + "user/jieqiTofuqi")
    Observable<ResponseBean> ConvertJQB(@Query("user_id") String user_id,
                                        @Query("jieqibao") String jieqibao,
                                        @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/userCashSave")
    Observable<ResponseBean> UserFQBSell(@Query("user_id") String user_id,
                                         @Query("total_money") String total_money,
                                         @Query("cloud_service_charge") String cloud_service_charge,
                                         @Query("money") String money,
                                         @Query("is_face_pay") int is_face_pay,
                                         @Query("token") String token);

    @GET(Config.VERSION + "Entre/customerList")
    Observable<PionRewardListDataBean> PionrewardCustomer(@Query("user_id") String user_id,
                                                          @Query("search_type") int search_type,
                                                          @Query("page") int page,
                                                          @Query("pageSize") int page_size,
                                                          @Query("token") String token);

    @GET(Config.VERSION + "Entre/customerConfirm")
    Observable<ResponseBean> PioncustomerConfirmPayment(@Query("user_id") String user_id,
                                                        @Query("entrepreneurs_fuqibao_sell_order_id") String entrepreneurs_fuqibao_sell_order_id,
                                                        @Query("token") String token);

    @GET(Config.VERSION + "Entre/customerReject")
    Observable<ResponseBean> PioncustomerBoHui(@Query("user_id") String user_id,
                                               @Query("entrepreneurs_fuqibao_sell_order_id") String entrepreneurs_fuqibao_sell_order_id,
                                               @Query("token") String token);

    @GET(Config.VERSION + "Entre/customerApplyMoneyList")
    Observable<PionOpenGMListDataBean> PioncustomerApplyMoneyList(@Query("user_id") String user_id,
                                                                  @Query("search_type") int search_type,
                                                                  @Query("page") int page,
                                                                  @Query("page_size") int page_size,
                                                                  @Query("token") String token);

    @GET(Config.VERSION + "Entre/customerApplyRebateList")
    Observable<PionCailiListDataBean> PionRebateList(@Query("user_id") String user_id,
                                                     @Query("search_type") int search_type,
                                                     @Query("page") int page,
                                                     @Query("page_size") int page_size,
                                                     @Query("token") String token);

    @GET(Config.VERSION + "entre/customerSettlementList")
    Observable<PionTutorExitListDataBean> PionTutorExitList(@Query("user_id") String user_id,
                                                            @Query("search_type") int search_type,
                                                            @Query("page") int page,
                                                            @Query("page_size") int page_size,
                                                            @Query("token") String token);

    @GET(Config.VERSION + "entre/clearSettlement")
    Observable<ResponseBean> PionTutorExitConfirmPayApplyMoney(@Query("user_id") String user_id,
                                                               @Query("entrepreneurs_settlement_order_id") String entrepreneurs_settlement_order_id,
                                                               @Query("token") String token);

    @GET(Config.VERSION + "Entre/customerConfirmZhuyoubaoList")
    Observable<ZYBRecordListDataBean> PionZYBZDList(@Query("user_id") String user_id,
                                                    @Query("search_type") int search_type,
                                                    @Query("page") int page,
                                                    @Query("page_size") int page_size,
                                                    @Query("token") String token);

    @GET(Config.VERSION + "Entre/customerConfirmModelList")
    Observable<PionOpenListDataBean> PionOpenXuFeiList(@Query("user_id") String user_id,
                                                       @Query("type") int type,
                                                       @Query("search_type") int search_type,
                                                       @Query("page") int page,
                                                       @Query("page_size") int page_size,
                                                       @Query("token") String token);

    @GET(Config.VERSION + "Entre/confirmModel")
    Observable<ResponseBean> PionOpenXuFeiConfirmGetMoney(@Query("user_id") String user_id,
                                                          @Query("entrepreneurs_order_id") String entrepreneurs_order_id,
                                                          @Query("token") String token);

    @GET(Config.VERSION + "Entre/confirmZhuyoubao")
    Observable<ResponseBean> PionZYBZDConfirmGetMoney(@Query("user_id") String user_id,
                                                      @Query("entrepreneurs_zhufubao_buy_order_id") String entrepreneurs_zhufubao_buy_order_id,
                                                      @Query("token") String token);

    @GET(Config.VERSION + "Entre/customerConfirmPayApplyRebate")
    Observable<ResponseBean> PionRebateConfirmPayApplyMoney(@Query("user_id") String user_id,
                                                            @Query("entrepreneurs_apply_rebate_id") String entrepreneurs_apply_rebate_id,
                                                            @Query("token") String token);

    @GET(Config.VERSION + "entre/customerConfirmPayApplyMoney")
    Observable<ResponseBean> PioncustomerConfirmPayApplyMoney(@Query("user_id") String user_id,
                                                              @Query("entrepreneurs_apply_money_id") String entrepreneurs_apply_money_id,
                                                              @Query("token") String token);

    @GET(Config.VERSION + "entrepreneurs/getRechargeInfoNew")
    Observable<PionZFBDataBean> PiongetRechargeInfoNew(@Query("user_id") String user_id,
                                                       @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "entrepreneurs/userRecharge")
    Observable<PayWXDataBean> entrepreneursuserRecharge(@Field("user_id") String user_id,
                                                        @Field("recharge_money") String recharge_money,
                                                        @Field("pay_method") int pay_method,
                                                        @Field("pay_source") String pay_source,
                                                        @Field("entrepreneurs_id") String entrepreneurs_id,
                                                        @Field("token") String token);

    @GET(Config.VERSION + "entrepreneurs/activateSuperAbility")
    Observable<ResponseBean> activateSuperAbility(@Query("user_id") String user_id,
                                                  @Query("uzhufubao_number") String uzhufubao_number,
                                                  @Query("token") String token);


    @GET(Config.VERSION + "user/getUserByPhone")
    Observable<PionOpenSetDataBean> getUserByPhone(@Query("user_id") String user_id,
                                                   @Query("search") String search,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "entre/addEntrepreneursUser")
    Observable<ResponseBean> addEntrepreneursUser(@Query("user_id") String user_id,
                                                  @Query("add_user_id") String add_user_id,
                                                  @Query("token") String token);

    @GET(Config.VERSION + "Entre/confirmUser")
    Observable<PionOpenSetRecordDataBean> getOpenRecordList(@Query("user_id") String user_id,
                                                            @Query("page") int page,
                                                            @Query("page_size") int page_size,
                                                            @Query("token") String token);

    @GET(Config.VERSION + "entre/applyRebate")
    Observable<PionOpenSetRecordDataBean> getCailiRecordList(@Query("user_id") String user_id,
                                                             @Query("page") int page,
                                                             @Query("page_size") int page_size,
                                                             @Query("token") String token);

    @GET(Config.VERSION + "ranking/entreFuqibaoLimitRanking")
    Observable<PioneerDataBean> getFQBRankList(@Query("user_id") String user_id,
                                               @Query("page") int page,
                                               @Query("page_size") int page_size,
                                               @Query("token") String token);

    @GET(Config.VERSION + "ranking/entreZhufubaoSellRanking")
    Observable<PioneerDataBean> getZYBRankList(@Query("user_id") String user_id,
                                               @Query("type") int type,
                                               @Query("page") int page,
                                               @Query("page_size") int page_size,
                                               @Query("token") String token);

    @GET(Config.VERSION + "entre/rewardRechargeMoney")
    Observable<PioneerDataBean> rewardRechargeMoneyRecordList(@Query("user_id") String user_id,
                                                              @Query("page") int page,
                                                              @Query("page_size") int page_size,
                                                              @Query("token") String token);


    @GET(Config.VERSION + "Knpmsg/patientIndex")
    Observable<AYApplyListDataBean> getAiYouDocList(@Query("user_id") String user_id,
                                                    @Query("page") int page,
                                                    @Query("page_size") int page_size,
                                                    @Query("token") String token);

    @GET(Config.VERSION + "knpmsg/scanFollowItemInfo")
    Observable<HealTeackDataBean> scanFollowItemInfo(@Query("user_id") String user_id,
                                                     @Query("knp_msg_id") String knp_msg_id,
                                                     @Query("day") String day,
                                                     @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "knpmsg/patientAddPic")
    Observable<ResponseBean> patientAddPic(@Field("user_id") String user_id,
                                           @Field("pic_url") String pic_url,
                                           @Field("knp_msg_id") String knp_msg_id,
                                           @Field("type") int type,
                                           @Field("token") String token);

    @GET(Config.VERSION + "knpmsg/dactoreExaminePatientPic")
    Observable<ResponseBean> dactoreExaminePatientPic(@Query("user_id") String user_id,
                                                      @Query("knp_msg_follow_item_id") String knp_msg_follow_item_id,
                                                      @Query("item_info_jsons") String item_info_jsons,
                                                      @Query("proposal") String proposal,
                                                      @Query("token") String token);

    @GET(Config.VERSION + "Knpmsg/volunteerMsgList")
    Observable<AYRecordListDataBean> getAYRecordList(@Query("user_id") String user_id,
                                                     @Query("page") int page,
                                                     @Query("page_size") int page_size,
                                                     @Query("token") String token);

    @GET(Config.VERSION + "knpmsg/volunteerAssetList")
    Observable<DocRewardListDataBean> getVolRewardList(@Query("user_id") String user_id,
                                                       @Query("page") int page,
                                                       @Query("page_size") int page_size,
                                                       @Query("token") String token);

    @GET(Config.VERSION + "knpmsg/doctorAssetList")
    Observable<DocRewardListDataBean> getDocRewardList(@Query("user_id") String user_id,
                                                       @Query("page") int page,
                                                       @Query("page_size") int page_size,
                                                       @Query("token") String token);

    @GET(Config.VERSION + "Knpmsg/listVolunteer")
    Observable<DocRewardListDataBean> getVolList(@Query("user_id") String user_id,
                                                 @Query("page") int page,
                                                 @Query("page_size") int page_size,
                                                 @Query("token") String token);

    @GET(Config.VERSION + "Knpmsg/searchVolunteer")
    Observable<VolSearchDataBean> searchVolunteer(@Query("user_id") String user_id,
                                                  @Query("phone") String phone,
                                                  @Query("token") String token);

    @GET(Config.VERSION + "Knpmsg/addVolunteer")
    Observable<VolSearchDataBean> addVolunteer(@Query("user_id") String user_id,
                                               @Query("volunteer_user_id") String volunteer_user_id,
                                               @Query("token") String token);

    @GET(Config.VERSION + "Knpmsg/delVolunteer")
    Observable<ResponseBean> delVolunteer(@Query("user_id") String user_id,
                                          @Query("doctor_relation_volunteer_id") String doctor_relation_volunteer_id,
                                          @Query("token") String token);

    @GET(Config.VERSION + "knpmsg/doctorAsset")
    Observable<RewardDataBean> doctorAsset(@Query("user_id") String user_id,
                                           @Query("token") String token);

    @GET(Config.VERSION + "knpmsg/volunteerAsset")
    Observable<RewardDataBean> volunteerAsset(@Query("user_id") String user_id,
                                              @Query("token") String token);

    @GET(Config.VERSION + "knpmsg/assetChange")
    Observable<ResponseBean> assetChange(@Query("user_id") String user_id,
                                         @Query("source") String source,
                                         @Query("number") String number,
                                         @Query("token") String token);

    @GET(Config.VERSION + "Knpmsg/patientList")
    Observable<BasicInfoListDataBean> getBasicInfoList(@Query("user_id") String user_id,
                                                       @Query("page") int page,
                                                       @Query("page_size") int page_size,
                                                       @Query("token") String token);

    @GET(Config.VERSION + "Knpmsg/doctorGetPatientMsgList")
    Observable<AYApplyListDataBean> getAYApplyList(@Query("user_id") String user_id,
                                                   @Query("page") int page,
                                                   @Query("page_size") int page_size,
                                                   @Query("token") String token);


    //------------------------------域名-----------------------------------
    @GET(Config.VERSION + "userBindCommune/verifyIsBind")
    Observable<domainDataBean> getYUMingInfo(@Query("user_id") String user_id,
                                             @Query("token") String token);

    @GET(Config.VERSION + "commune/hotCommune")
    Observable<HotListDataBean> gethotCommuList(@Query("user_id") String user_id,
                                                @Query("level") int level,
                                                @Query("token") String token);

    @GET(Config.VERSION + "userBindCommune/areaSelect")
    Observable<SelctAeraDataInfo> getCityList(@Query("user_id") String user_id,
                                              @Query("parent_id") int parent_id,
                                              @Query("token") String token);

    @GET(Config.VERSION + "commune/communeSearch")
    Observable<HotListDataBean> getSearchCommuList(@Query("user_id") String user_id,
                                                   @Query("keyword") String keyword,
                                                   @Query("level") int level,
                                                   @Query("page") int page,
                                                   @Query("page_size") int page_size,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "userBindCommune/bindBrigade")
    Observable<ResponseBean> BindAddress(@Query("user_id") String user_id,
                                         @Query("commune_id") String commune_id,
                                         @Query("token") String token);

    @GET(Config.VERSION + "commune/communeDetail")
    Observable<CommuDetailDataBean> getCommuDetail(@Query("user_id") String user_id,
                                                   @Query("commune_id") String commune_id,
                                                   @Query("token") String token);

    @GET(Config.VERSION + "commune/buyBrigade")
    Observable<CommuDetailDataBean> getBuyBrigade(@Query("user_id") String user_id,
                                                  @Query("commune_id") String commune_id,
                                                  @Query("token") String token);

    @GET(Config.VERSION + "commune/buyCommune")
    Observable<CommuDetailDataBean> getBuyCommue(@Query("user_id") String user_id,
                                                 @Query("commune_id") String commune_id,
                                                 @Query("token") String token);

    @GET(Config.VERSION + "commune/rushCommune")
    Observable<PayWXDataBean> rushCommune(@Query("user_id") String user_id,
                                          @Query("commune_id") String commune_id,
                                          @Query("price") String price,
                                          @Query("payment_channel") int payment_channel,
                                          @Query("pay_source") int pay_source,
                                          @Query("token") String token);

    @GET(Config.VERSION + "commune/myCommune")
    Observable<HotListDataBean> getMyCommuList(@Query("user_id") String user_id,
                                               @Query("level") int level,
                                               @Query("page") int page,
                                               @Query("page_size") int page_size,
                                               @Query("token") String token);

    @GET(Config.VERSION + "Knpgroup/indexConfig")
    Observable<CHelpDataBean> getListIndexConfig(@Query("user_id") String user_id,
                                                 @Query("token") String token);

    @GET(Config.VERSION + "knpgroup/createRoomPage")
    Observable<CHelpCreatDataBean> createRoomPage(@Query("user_id") String user_id,
                                                  @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "knpgroup/bindKnpMsg")
    Observable<ResponseBean> wuwaiBindKnpAiYouMsg(@Field("user_id") String user_id,
                                                  @Field("knp_group_item_id") String knp_group_item_id,
                                                  @Field("knp_msg_id") String knp_msg_id,
                                                  @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "knpgroup/openTable")
    Observable<ResponseBean> wuwaiOpenTable(@Field("user_id") String user_id,
                                            @Field("knp_group_item_id") String knp_group_item_id,
                                            @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "knpgroup/createRoom")
    Observable<EditDataBean> CreateRoom(@Field("user_id") String user_id,
                                        @Field("type") int type,
                                        @Field("is_super_ability") int is_super_ability,
                                        @Field("knp_team_number_id") String knp_team_number_id,
                                        @Field("table_number") int table_number,
                                        @Field("ability") int ability,
                                        @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "knpgroup/joinTableItem")
    Observable<ResponseBean> JoinHelpRoom(@Field("user_id") String user_id,
                                          @Field("knp_group_room_id") String knp_group_room_id,
                                          @Field("ability") int ability,
                                          @Field("number") int number,
                                          @Field("knp_group_table_ids") String knp_group_table_ids,
                                          @Field("token") String token);

    @GET(Config.VERSION + "Knpgroup/index")
    Observable<CHelpListDataBean> getHelpList(@Query("user_id") String user_id,
                                              @Query("keyword") String keyword,
                                              @Query("knp_team_number_id") String knp_team_number_id,
                                              @Query("jieqi_en") String jieqi_en,
                                              @Query("page") int page,
                                              @Query("page_size") int page_size,
                                              @Query("token") String token);


    @FormUrlEncoded
    @POST(Config.VERSION + "knpgroup/inviteAlert")
    Observable<PairUDataBean> wuaiGoWithInviteAlert(@Field("user_id") String user_id,
                                                    @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "knpgroup/refuseInvite")
    Observable<ResponseBean> refuseGoWithInvite(@Field("user_id") String user_id,
                                                @Field("knp_group_invitation_id") String knp_group_invitation_id,
                                                @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "knpgroup/acceptInvite")
    Observable<PairUDataBean> agreeeGoWithInvite(@Field("user_id") String user_id,
                                                 @Field("knp_group_invitation_id") String knp_group_invitation_id,
                                                 @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Knpgroup/partnershipHelpGroupLayer")
    Observable<PairUDataBean> wuaiGoWithTeamInfo(@Field("user_id") String user_id,
                                                 @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "Knpgroup/addItemHelpRecord")
    Observable<ResponseBean> addItemHelpRecord(@Field("user_id") String user_id,
                                               @Field("knp_group_room_id") String knp_group_room_id,
                                               @Field("knp_group_item_id") String knp_group_item_id,
                                               @Field("token") String token);

    @GET(Config.VERSION + "knpgroup/groupRoomInfo")
    Observable<CHelpGroupRoomDataBean> groupRoomInfo(@Query("user_id") String user_id,
                                                     @Query("knp_group_room_id") String knp_group_room_id,
                                                     @Query("token") String token);

    @GET(Config.VERSION + "knpgroup/knpMsgList")
    Observable<CHelpDataBean> knpAiYouMsgList(@Query("user_id") String user_id,
                                              @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "knpgroup/myRoomList")
    Observable<CHelpListDataBean> getMyHelpList(@Field("user_id") String user_id,
                                                @Field("type") int type,
                                                @Field("page") int page,
                                                @Field("page_size") int page_size,
                                                @Field("token") String token);

    @GET(Config.VERSION + "knpgroup/grantSuccess")
    Observable<CHelpDataBean> getOverRoomInfo(@Query("user_id") String user_id,
                                              @Query("knp_group_table_id") String knp_group_table_id,
                                              @Query("token") String token);

    @GET(Config.VERSION + "Knpgroup/groupTableList")
    Observable<CHelpDetailDataBean> getRoomInfo(@Query("user_id") String user_id,
                                                @Query("knp_group_room_id") String knp_group_room_id,
                                                @Query("knp_group_item_id") String knp_group_item_id,
                                                @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "knpgroup/dissolution")
    Observable<ResponseBean> wuaiDissolution(@Field("user_id") String user_id,
                                             @Field("knp_group_room_id") String knp_group_room_id,
                                             @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "knpgroup/signOut")
    Observable<ResponseBean> wuaiSignOut(@Field("user_id") String user_id,
                                         @Field("knp_group_room_id") String knp_group_room_id,
                                         @Field("token") String token);

    @GET(Config.VERSION + "entre/teamInfo")
    Observable<CHelpListDataBean> getOrderTeamInfo(@Query("user_id") String user_id,
                                                   @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "entre/quitTeam")
    Observable<ResponseBean> cancelOrderTeam(@Field("user_id") String user_id,
                                             @Field("entrepreneurs_team_item_id") String entrepreneurs_team_item_id,
                                             @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "zhuyoubaoteam/quitTeam")
    Observable<ResponseBean> ZYBQuitTeam(@Field("user_id") String user_id,
                                         @Field("zhuyoubao_team_item_id") String zhuyoubao_team_item_id,
                                         @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "zhuyoubaoteam/createUserRecharge")
    Observable<ZYBZuDuiDataBean> createUserRecharge(@Field("user_id") String user_id,
                                                    @Field("entrepreneurs_id") String entrepreneurs_id,
                                                    @Field("zhuyoubao_team_room_id") String zhuyoubao_team_room_id,
                                                    @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "entre/createTeamRoom")
    Observable<ResponseBean> createOrderTeam(@Field("user_id") String user_id,
                                             @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "entre/teamList")
    Observable<PionDaiFuDataBean> getOrderTeamList(@Field("user_id") String user_id,
                                                   @Field("keyword") String keyword,
                                                   @Field("token") String token);

    @GET(Config.VERSION + "zhuyoubaoteam/userList")
    Observable<PionDaiFuDataBean> getZybUserList(@Query("user_id") String user_id,
                                                 @Query("keyword") String keyword,
                                                 @Query("zhuyoubao_team_room_id") String zhuyoubao_team_room_id,
                                                 @Query("page") int page,
                                                 @Query("page_size") int page_size,
                                                 @Query("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "knpgroup/userList")
    Observable<PionDaiFuDataBean> wuaiInviteUserInfo(@Field("user_id") String user_id,
                                                     @Field("keyword") String keyword,
                                                     @Field("knp_group_room_id") String knp_group_room_id,
                                                     @Field("page") int page,
                                                     @Field("page_size") int page_size,
                                                     @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "knpgroup/recordUserList")
    Observable<PionDaiFuDataBean> wuaiRecordUserList(@Field("user_id") String user_id,
                                                     @Field("knp_group_room_id") String knp_group_room_id,
                                                     @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "zhuyoubaoteam/teamList")
    Observable<PionZFBSelectDataBean> getZYBTutorList(@Field("user_id") String user_id,
                                                      @Field("keyword") String keyword,
                                                      @Field("page") int page,
                                                      @Field("page_size") int page_size,
                                                      @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "zhuyoubaoteam/bindEntrepreneurs")
    Observable<ResponseBean> ZYBBindEntrepreneurs(@Field("user_id") String user_id,
                                                  @Field("zhuyoubao_team_room_id") String zhuyoubao_team_room_id,
                                                  @Field("entrepreneurs_id") String entrepreneurs_id,
                                                  @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "entre/launchInvitation")
    Observable<ResponseBean> launchInvitation(@Field("user_id") String user_id,
                                              @Field("receive_user_id") String receive_user_id,
                                              @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "zhuyoubaoteam/launchInvitation")
    Observable<ResponseBean> ZybUserlaunchInvitation(@Field("user_id") String user_id,
                                                     @Field("receive_user_id") String receive_user_id,
                                                     @Field("zhuyoubao_team_item_id") String zhuyoubao_team_item_id,
                                                     @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "knpgroup/sendInvite")
    Observable<ResponseBean> wuaiUserlaunchInvitation(@Field("user_id") String user_id,
                                                      @Field("user_ids") String user_ids,
                                                      @Field("knp_group_room_id") String knp_group_room_id,
                                                      @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "entre/layerTeam")
    Observable<PairUDataBean> layerTeamInvite(@Field("user_id") String user_id,
                                              @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "entre/refuseTeam")
    Observable<ResponseBean> refuseZuDuiInvite(@Field("user_id") String user_id,
                                               @Field("entrepreneurs_team_invitation_id") String entrepreneurs_team_invitation_id,
                                               @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "entre/joinTeam")
    Observable<ResponseBean> agreeeZuDuiInvite(@Field("user_id") String user_id,
                                               @Field("entrepreneurs_team_invitation_id") String entrepreneurs_team_invitation_id,
                                               @Field("token") String token);


    @FormUrlEncoded
    @POST(Config.VERSION + "zhuyoubaoteam/layerTeam")
    Observable<PairUDataBean> layerZybUserTeamInfo(@Field("user_id") String user_id,
                                                   @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "zhuyoubaoteam/refuseTeam")
    Observable<ResponseBean> refuseZybUserZuDuiInvite(@Field("user_id") String user_id,
                                                      @Field("zhuyoubao_team_invitation_id") String zhuyoubao_team_invitation_id,
                                                      @Field("token") String token);

    @FormUrlEncoded
    @POST(Config.VERSION + "zhuyoubaoteam/joinTeam")
    Observable<EditDataBean> agreeeZybUserZuDuiInvite(@Field("user_id") String user_id,
                                                      @Field("zhuyoubao_team_invitation_id") String zhuyoubao_team_invitation_id,
                                                      @Field("token") String token);


    //聚宝盆个人页面接口
    @GET(Config.VERSION + "cornucopia/info")
    Observable<CornucopiaBean> getCornucopiaInfo(@Query("user_id") String user_id,
                                                 @Query("token") String token);


    //聚宝盆个已开启组列表接口
    @GET(Config.VERSION + "cornucopia/cornucopiaGroupList")
    Observable<CornucopiaListBean> getRankingList(@Query("user_id") String user_id,
                                                  @Query("type") int type,
                                                  @Query("page") int page,
                                                  @Query("page_size") int page_size,
                                                  @Query("token") String token);

    //聚宝盆个人用户排名接口
    @GET(Config.VERSION + "ranking/userCornScoreRanking")
    Observable<RankListBean> getRankingUser(@Query("user_id") String user_id,
                                            @Query("type") int type,
                                            @Query("page") int page,
                                            @Query("page_size") int page_size,
                                            @Query("token") String token);

    //开启存入聚宝盆
    @GET(Config.VERSION + "Cornucopia/open")
    Observable<ResponseBean> getOpenCor(@Query("user_id") String user_id,
                                        @Query("config_id") String config_id,
                                        @Query("money") String money,
                                        @Query("number") String number,
                                        @Query("total_money") String total_money,
                                        @Query("token") String token);

    //创业导师退出搜索接口
    @GET(Config.VERSION + "entre/entreList")
    Observable<ExitutorSearchBean> getExitSearchUser(@Query("user_id") String user_id,
                                                     @Query("phone") String phone,
                                                     @Query("token") String token);


    //创业导师退出确认接口
    @GET(Config.VERSION + "entre/doSettlement")
    Observable<ResponseBean> doSettlementActionGet(@Query("user_id") String user_id,
                                                @Query("entrepreneurs_id") String entrepreneursid,
                                                @Query("token") String token);

    //创业导师退出确认接口
    @FormUrlEncoded
    @POST(Config.VERSION + "entre/doSettlement")
    Observable<ResponseBean> doSettlementActionPost(@Field("user_id") String user_id,
                                                    @Field("entrepreneurs_id") String entrepreneursid,
                                                    @Field("token") String token);

}


