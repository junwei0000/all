package com.bestdo.bestdoStadiums.utils;

import com.bestdo.bestdoStadiums.control.activity.CampaignManagementActivity;
import com.bestdo.bestdoStadiums.control.activity.ClubActivity;
import com.bestdo.bestdoStadiums.control.activity.UserOrderActivity;
import com.example.androidbridge.CallBackFunction;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:05:32
 * @Description 类描述：属性工具类
 */
public class Constans {

	/**
	 * ******************************************************************
	 * 接口定义*************************************************************
	 * ******************************************************************
	 */
//	public static final String WEB_ROOT ="http://test.bd.app.bestdo.com/2.7.0";// 线上测试
	public static final String WEB_ROOT = "https://www.bestdo.com/new-bd-app/2.7.0";// 线上正式

	/**
	 * ******************************************************************
	 * v2.0.0改版新接口**********************************************************
	 * ******************************************************************
	 */
	public static final String USERSENDID = WEB_ROOT + "/user/addFeedback";// 意见反馈
	public static final String CAMPAIGNDETAILSHARE = WEB_ROOT + "/clubservice/activityShare";// 活动详情分享链接
	public static final String CITYLIST = WEB_ROOT + "/region/cities";// 城市列表
	public static final String CITYLISTAREA = WEB_ROOT + "/region/regions";// 城市区域
	// **********登录、注册***********
	public static final String FASTLOGINGETCODE = WEB_ROOT + "/user/quickLoginSendCode";// 快速登录获取验证码
	public static final String FASTLOGIN = WEB_ROOT + "/user/quickLogin";// 快速登录
	public static final String LOGIN = WEB_ROOT + "/user/login";// 登录
	public static final String UMENGLOGIN = WEB_ROOT + "/user/thirdpartyLogin";// 第三方登录
	public static final String USERMEBERCASH = WEB_ROOT + "/memberpoint/ExchangeMember";// 会员兑换
	public static final String FINDPASSWORDGETCODE = WEB_ROOT + "/user/mobileFindPasswordSendCode";// 找回密码获取验证码
	public static final String BINDMOBILEGETCODE = WEB_ROOT + "/user/thirdBindMobileSendCode";// 三方绑定手机号发送验证码

	public static final String LODEPERSONDATE = WEB_ROOT + "/merchandise/index";// 首页数据
	public static final String FINDPWBYPHONE = WEB_ROOT + "/user/findPasswordByMobile";// 找回密码
	public static final String REGISTGETCODE = WEB_ROOT + "/user/mobileRegisterSendCode";// 注册获取验证码
	public static final String REGISTCHECKCODE = WEB_ROOT + "/user/mobileRegisterCheckCode";// 注册时检查验证码
	public static final String REGISTSETPASSWORD = WEB_ROOT + "/user/register";// 注册设置密码
	public static final String FINDPASSWORDGETCODECHECKCODE = WEB_ROOT + "/user/mobileFindPasswordCheckCode";// 找回密码检查验证码
	public static final String BINDMOBILECODE = WEB_ROOT + "/user/thirdBindMobile";// 三方绑定手机号
	public static final String BINDMOBILEGETCODEBYOLD = WEB_ROOT + "/user/thirdOldUserBindMobileSendCode";// 三方绑定手机号发送验证码老用户
	public static final String BINDMOBILECODEBYOLD = WEB_ROOT + "/user/thirdValideCodeAndBindMobile";// 三方绑定手机号老用户
	public static final String UPDATEPW = WEB_ROOT + "/user/editPassword";// 修改密码
	// **********场馆***********
	public static final String STADIUMCANCELFAVORITE = WEB_ROOT + "/merchandise/item/cancelFavorite";// 取消收藏
	public static final String STADIUMADDFAVORITE = WEB_ROOT + "/merchandise/item/addFavorite";// 添加收藏
	public static final String USERCOLLECTLIST = WEB_ROOT + "/merchandise/item/favoriteList";// 我的收藏列表
	public static final String GETRECOMMENDSTADIUMLIST = WEB_ROOT + "/merchandise/item/getRecommendMerItem";// 首页推荐列表
	public static final String GETSTADIUMLIST = WEB_ROOT + "/merchandise/item/search";// 场馆列表
	public static final String SEARCHKEYWORD = WEB_ROOT + "/merchandise/item/keyWordSearch";// 关键字搜索

	public static final String PAYZFB = WEB_ROOT + "/payment/alipay/toPay";// 支付宝支付
	public static final String MEBERPAYZFB = WEB_ROOT + "/payment/alipay/toMemberAliPay";// 购买会员支付宝支付
	public static final String CARDPAYZFB = WEB_ROOT + "/payment/alipay/toBaidongCardPay";// 百动卡支付宝支付

	public static final String PAYUSERBALANCE0 = WEB_ROOT + "/payment/alipay/toBalancePay";// 使用余额0元支付
	
	public static final String PAYWEIXIN = WEB_ROOT + "/payment/alipay/toWxPay";// 微信支付
	public static final String MEMBERPAYWEIXIN = WEB_ROOT + "/payment/alipay/toMemberWxPay";// 微信支付
	public static final String CARDPAYWEIXIN = WEB_ROOT + "/payment/alipay/toBaidongCardWxPay";// 百动卡微信支付
	
	
	public static final String PAYHUAWEIUNIONBUYMEBER = WEB_ROOT + "/payment/alipay/toMemberHuaWeiPay";// 华为银联支付
	
	public static final String PAYUNION = WEB_ROOT + "/payment/alipay/toUnionPay";// 银联订场支付
	public static final String PAYUNIONBUYCARD = WEB_ROOT + "/payment/alipay/toBaidongCardUnionPay";// 银联买卡支付
	public static final String PAYUNIONBUYMEBER = WEB_ROOT + "/payment/alipay/toMemberUnionPay";// 银联购买会员支付

	public static final String GETMEMBERLIST = WEB_ROOT + "/memberpoint/getMemberDataList";// 获取可购买会员列表
	public static final String GETMEMBERINFO = WEB_ROOT + "/memberpoint/getMemberInfo";// 获取当前用户身份信息
	public static final String GETMEMBERSUCCESS = WEB_ROOT + "/memberpoint/buyMemberSuccess";// 购买会员成功后获取会员信息

	public static final String UPLOADVEDIO = WEB_ROOT + "/cinda/upload_mv";// 上传视频地址
	public static final String STADIUMDETAILS = WEB_ROOT + "/merchandise/item/detail";// 商品详情
	public static final String MERITEMPRICE = WEB_ROOT + "/merchandise/item/getMerItemPriceAndInventorySummary" + "";// 商品明细七天价格和库存汇总
	public static final String MERITEMPRICEGOLF = WEB_ROOT + "/merchandise/item/getGolfPriceAndInventorySummary";// 商品明细七天价格和库存汇总(golf
																													// golfange)
	public static final String GETONEDAYMERITEMPRICE = WEB_ROOT + "/merchandise/item/getOneDayMerItemPrice";// 商品明细一天的报价API（乒羽篮网）
	public static final String GETONEDAYHOURSMERITEMPRICE = WEB_ROOT + "/merchandise/item/getOneDayHoursMerItemPrice";// 某天指定小时商品明细的价格
	public static final String CREATEORDERSGETDEFAOUTCARD = WEB_ROOT + "/card/getAccount";// 获取默认的卡券
	public static final String CREATEORDERMERTEMPRICE = WEB_ROOT
			+ "/merchandise/item/getOneDayHoursCreateOrderMerItemPrice";// 创建订单获取商品明细的价格API

	public static final String CREATEORDERGETPRICE = WEB_ROOT + "/merchandise/item/getCreateOrderInfo";// 创建订单获取价格API
	public static final String CREATEORDERSBUYBALANCE = WEB_ROOT + "/order/createBalancePayOrder";// 充值余额创建订单
	public static final String CREATEORDERSFITNESS = WEB_ROOT + "/order/createFitnessOrder";// 健身创建订单
	public static final String CREATEORDERSSWIMMING = WEB_ROOT + "/order/createSwimOrder";// 游泳创建订单
	public static final String CREATEORDERSPRACTICE = WEB_ROOT + "/order/createGolfPracticeOrder";// 练习场创建订单
	public static final String CREATEORDERSSWIM = WEB_ROOT + "/order/createTennisOrder";// 网球创建订单
	public static final String CREATEORDERBADMINTON = WEB_ROOT + "/order/createBadmintonOrder";// 羽毛球创建订单
	public static final String CREATEORDERSGOLF = WEB_ROOT + "/order/createGolfOrder";// golf创建订单
	public static final String CREATEORDERSSKI = WEB_ROOT + "/order/CreateSkiOrder";// 滑雪创建订单
	public static final String CREATEORDERSBILLIAR = WEB_ROOT + "/order/CreateBilliardsOrder";// 台球创建订单
	public static final String CREATEORDERSOUTDOORS = WEB_ROOT + "/order/CreateOutdoorsOrder";// 户外创建订单
	public static final String CREATEORDERSSWIMBOD = WEB_ROOT + "/order/CreateSwimbodOrder";// 高端游泳创建订单
	public static final String CREATETABLETENNISORDER = WEB_ROOT + "/order/createTableTennisOrder";// 乒乓球创建订单
	public static final String CREATEBASKETBALLORDER = WEB_ROOT + "/order/createBasketballOrder";// 创建篮球创建订单
	public static final String CREATEWOMENORDER = WEB_ROOT + "/order/CreateWomenOrder";// 女性专区创建订单
	public static final String CREATETATICKETORDER = WEB_ROOT + "/order/CreateTicketOrder";// 创建票务订单
	public static final String CREATETEQUIPMENTORDER = WEB_ROOT + "/order/CreateEquipmentOrder";// 创建运动装备订单
	public static final String CREATEORDERSBUYCARD = WEB_ROOT + "/order/createBaidongCardOrder";// 创建订单-=-购买百动卡
	public static final String CREATEMEMBERORDER = WEB_ROOT + "/order/createMemberOrder";// 创建购买会员订单API

	// **********个人中心***********
	public static final String GETVERSON = WEB_ROOT + "/version/androidVersion";// 版本更新
	public static final String USERGETINFO = WEB_ROOT + "/user/row";// 个人中心---得到个人信息
	public static final String USEORDERLIST = WEB_ROOT + "/order/list";// 订单列表
	public static final String USERORDERDETAILS = WEB_ROOT + "/order/detail";// 订单详情
	public static final String USERORDERSHENGUODANGAO = WEB_ROOT + "/card/userlife"; // 生活-蛋糕
	public static final String GETORDERNUM = WEB_ROOT + "/order/getOrderStatusNums";// 获取订单数量
	public static final String USERORDERDETAILUNSUBSCRIB = WEB_ROOT + "/order/unsubscribe";// 订单退订
	public static final String USERUPDATEINFO = WEB_ROOT + "/user/edit";// 编辑用户信息
	public static final String USERUPDATEUSERNAME = WEB_ROOT + "/user/editUsername";// 编辑用户名
	public static final String USERUPDATEABLUM = WEB_ROOT + "/user/uploadHeadPortraits";// 编辑头像
	public static final String USERCARDSLIST = WEB_ROOT + "/card/getValidCards";// 卡列表
	public static final String USERCARDSMILIST = WEB_ROOT + "/card/getCamiList";// 卡密包列表
	public static final String USERBALANCELIST = WEB_ROOT + "/user/balance_list";// 余额-充值列表
	public static final String USERBALANCEDETAIL = WEB_ROOT + "/user/user_balance_detail";// 余额-充值列表明细
	public static final String GETCOMPANYCARDS = WEB_ROOT + "/card/getCompanyCards";// 企业卡列表

	public static final String USERCARDSBUY = WEB_ROOT + "/card/getBaidongCards";// 百动卡购买
	public static final String CARDJIHUO = WEB_ROOT + "/card/activateCard";// 卡激活
	public static final String CARDDETAIL = WEB_ROOT + "/card/getCard";// 卡详情
	public static final String CARDDETAILLOADSHOUZHI = WEB_ROOT + "/card/getCradAccountList";// 卡详情加载收支记录
	public static final String GETBANNERIMG = WEB_ROOT + "/merchandise/publicity";// 首页Banner
	public static final String GETBUSINESSBANNERIMG = WEB_ROOT + "/merchandise/item/HomePage";// 企业版首页Banner
	public static final String GETSPORTSLIST = WEB_ROOT + "/merchandise/sports";// 个人中心运动类型切换
	public static final String GETWEATHER = WEB_ROOT + "/appservice/GetWeather";// 健步走获取天气，pm25
	public static final String GETMAINSPORTS = WEB_ROOT + "/merchandise/list";// 首页运动类型
	public static final String USERWALKINGHISTORY = WEB_ROOT + "/appservice/HistoryData";// 健步走历史记录
	public static final String USERWALKINGGETTRCKE = WEB_ROOT + "/appservice/getHistoryLog";// 健步走获取轨迹
	public static final String USERWALKINPOSTDATA = WEB_ROOT + "/appservice/StepUpload";// 健步走上传数据
	public static final String USERWALKINGRANK = WEB_ROOT + "/appservice/TopData";// 企业计步排行
	public static final String USERWALKINGRANKBUSINESS = WEB_ROOT + "/appservice/TopBusData";// 部门计步排行
	public static final String USERWALKINGLOADSTEP = WEB_ROOT + "/appservice/TopDataUpload";// 上传总步数
	public static final String CREATELIFEORDER = WEB_ROOT + "/order/CreateLifeOrder";// 生活
	// **********活动***********
	public static final String CAMPAIGNGETCLUB = WEB_ROOT + "/clubservice/getManageClubList";// 活动-获取俱乐部
	public static final String CAMPAIGNPUBLISH = WEB_ROOT + "/clubservice/createActivity";// 活动-发布
	public static final String CAMPAIGNEDIT = WEB_ROOT + "/clubservice/editActivity";// 活动-编辑
	public static final String CAMPAIGNREGULAREDIT = WEB_ROOT + "/clubservice/changeRegularActivityStatus";// 活动-固定活动编辑
	public static final String CAMPAIGNCANCEL = WEB_ROOT + "/clubservice/cancelActivity";// 活动-取消
	public static final String CAMPAIGNLIST = WEB_ROOT + "/clubservice/getActivityList";// 活动-列表
	public static final String CAMPAIGNREGULARLIST = WEB_ROOT + "/clubservice/getRegularActivityList";// 活动-固定活动列表
	public static final String CAMPAIGNDETAIL = WEB_ROOT + "/clubservice/getActivityInfo";// 活动-详情
	public static final String CAMPAIGNADDSIGNUP = WEB_ROOT + "/clubservice/activityApply";// 活动-报名
	public static final String CAMPAIGNDELSIGNUP = WEB_ROOT + "/clubservice/cancelActivityApply";// 活动-取消报名
	public static final String CAMPAIGNBAOMINGLIST = WEB_ROOT + "/clubservice/getActivityMemberList";// 活动-报名成员
	public static final String CLUBACTIVITYLIST = WEB_ROOT + "/clubservice/getClubActivityList";// 俱乐部
																								// 活动列表
	public static final String CLUCLUBMENBERLIST = WEB_ROOT + "/clubservice/getclubMemberList";// 俱乐部成员列表
	public static final String CAMPAIGNQIANDAO = WEB_ROOT + "/clubservice/activitySign";// 签到

	public static final String CLUBSERVICE = WEB_ROOT + "/clubservice/clubIndex";// 俱乐部首页API
	public static final String MESSAGE = WEB_ROOT + "/clubservice/messageList";// 消息首页列表API
	public static final String MESSAGELIST = WEB_ROOT + "/clubservice/getMessage";// 获取消息列表API
	public static final String MESSAGESETREAD = WEB_ROOT + "/clubservice/editMessage";// 将消息修改为已读API
	public static final String MESSAGESETALLREAD = WEB_ROOT + "/clubservice/allReadMessage";// 将某一类型下的消息全变成已读API

	public static final String CASHMYCLUB = WEB_ROOT + "/clubservice/getManageClubList";// 获取管理员管理的俱乐部API
	public static final String GETCASHBOOKLIST = WEB_ROOT + "/clubservice/getClubNotepad";// 获取俱乐部记账本记录信息API
	public static final String GETCLUBTEARBUDGETLIST = WEB_ROOT + "/clubservice/getClubYearBudget";// 获取俱乐部预算信息listAPI
	public static final String SETYEARBUDGETLIST = WEB_ROOT + "/clubservice/addClubYearBudget";// 设置年度预算API
	public static final String DELECTCLUBNOTPAD = WEB_ROOT + "/clubservice/delectClubNotpad";// 删除记账本信息API
	public static final String CLUBNOTPADCLASS = WEB_ROOT + "/clubservice/getNotepadClass";// 获取记账本分类API
	public static final String CLUBADD = WEB_ROOT + "/clubservice/addClubNotepad";// 添加记账本API
	public static final String CLUBEDIT = WEB_ROOT + "/clubservice/editClubNotepad";// 修改记账本API
	public static final String GETWONDERFUL = WEB_ROOT + "/wondfulTime/getWonderfulCorpList";// 朋友圈列表
	public static final String CAMPAIGNGMDEL = WEB_ROOT + "/wondfulTime/delWonderful";// 活动-精彩时刻删除
	public static final String CLUBGETMANEGE = WEB_ROOT + "/clubservice/getManageClubList";// 活动-精彩时刻删除
	public static final String GETQINIUTOKEN = WEB_ROOT + "/qiniu/getQiniuToken";// 获取七牛token
	public static final String ADDWONDERFUL = WEB_ROOT + "/wondfulTime/addWonderful";// 发布精彩时刻
	public static final String ADDUPLOADDAYSTEP = WEB_ROOT + "/appservice/uploadDayStep";// 上传昨日步数

	/**
	 * ******************************************************************
	 * 常量属性定义**********************************************************
	 * ******************************************************************
	 */
	private static Constans mConstans;

	public synchronized static Constans getInstance() {
		if (mConstans == null) {
			mConstans = new Constans();
		}
		return mConstans;
	}

	public int STARTYEAR = 2017;
	/**
	 * welcomeSharedPreferences
	 */
	public String welcomeSharedPrefsKey = "firstLaunch";
	public String welcomeSharedPrefs_LaunchStatusKey = "launchStatus";
	public String welcomeSharedPrefs_Permission = "launchPermission";
	// invokingType
	public String invokingTypeByFastLogin = "FastLogin";
	public String invokingTypeByRegist = "regist";
	public String invokingTypeByFindphone = "findpmbyphone";
	public String invokingTypeByBindMobile = "bindMobile";
	public String invokingTypeByBindMobileByOldUser = "bindMobileByOldUser";
	/**
	 * 登录后跳转方向
	 */
	public String loginskiptoinmain = "loginskiptoinmain";
	public String loginskiptoaddfavorite = "addfavorite";//
//	public String loginskiptocreateorder = "createorder";
//	public String loginskiptoPYLWyuding = "PYLWyuding";
	public String loginskiptoPersonImgActivity = "PersonImgActivity";
	public String loginskiptologin403 = "login403";
	public String loginrefreshDetail = "loginrefreshDetail";
	public String loginskiptoMeber = "loginskiptoMeber";
	/**
	 * 普通用户ID
	 */
	public String identityCommomUser = "0";
	/**
	 * 登录状态
	 */
	public String loginStatus = "loging";
	/**
	 * 收藏列表-是否取消回调监听
	 */
	public int collectForResultByStaDetailPage = 4;

	/**
	 * 调用版本更新方向
	 */
	public String updateVersionFromMainPage = "HomeActivity";
	public String updateVersionFromSetPage = "UserSetActivity";
	public String cityFileName = "city.json";
	/**
	 * ************************正式Merid*****************************************
	 * 
	 */
	/**
	 * {"Merid":1020320,"name":"户外"}
	 */
	public String sportMeridHuWai = "1020292";
	/**
	 * {"Merid":1020319,"name":"台球"}
	 */
	public String sportMeridBilliards = "1020270";
	/**
	 * {"Merid":1020318,"name":"高端泳健"}
	 */
	public String sportMeridHighswimming = "1020294";
	/**
	 * {测试：1020317，正式1020293:,"name":"滑雪"}
	 */
	public String sportMeridSkiing = "1020293";
	/**
	 * {测试：1020434，正式1020360"Merid":1020434,"name":"运动装备"}
	 */
	public String sportEquipment = "1020360";

	/**
	 * {"Merid":1020317,"name":"女性专区"} 正式1020359 测试1020435
	 */
	public String sportWomen = "1020359";
	/**
	 * {"Merid":1020317,"name":"票务"} 正式1020358 测试1020436
	 */
	public String sportTicket = "1020358";

	/**
	 * {"Merid":1020317,"name":生活"} 生活测试：1020457 生活线上：1020343
	 */
	public String shenghuo = "1020343";
	/**
	 * ************************测试Merid*****************************************
	 * 
	 */
	// /**
	// * {"Merid":1020320,"name":"户外"}
	// */
	// public String sportMeridHuWai = "1020320";
	// /**
	// * {"Merid":1020319,"name":"台球"}
	// */
	// public String sportMeridBilliards = "1020319";
	// /**
	// * {"Merid":1020318,"name":"高端泳健"}
	// */
	// public String sportMeridHighswimming = "1020318";
	// /**
	// * {测试：1020317，正式1020293:,"name":"滑雪"}
	// */
	// public String sportMeridSkiing = "1020317";
	// /**
	// * {测试：1020434，正式1020360"Merid":1020434,"name":"运动装备"}
	// */
	// public String sportEquipment = "1020434";
	//
	// /**
	// * {"Merid":1020317,"name":"女性专区"} 正式1020359 测试1020435
	// */
	// public String sportWomen = "1020435";
	// /**
	// * {"Merid":1020317,"name":"票务"} 正式1020358 测试1020436
	// */
	// public String sportTicket = "1020436";
	//
	// /**
	// * {"Merid":1020317,"name":生活"} 生活测试：1020457 生活线上：1020343
	// */
	// public String shenghuo = "1020457";

	// ------------------------------------------------------------------------------------
	/**
	 * {"cid":103,"name":"高尔夫"}
	 */
	public String sportCidGolf = "103";
	/**
	 * {"cid":107,"name":"练习场"}
	 */
	public String sportCidGolfrange = "107";
	/**
	 * {"cid":101,"name":"网球"}
	 */
	public String sportCidTennis = "101";
	/**
	 * {"cid":102,"name":"羽毛球"}
	 */
	public String sportCidBadminton = "102";
	/**
	 * {"cid":109,"name":"游泳"}
	 */
	public String sportCidSwim = "109";
	/**
	 * {"cid":108,"name":"健身"}
	 */
	public String sportCidFitness = "108";
	/**
	 * {"cid":1020317,"name":"乒乓球"}
	 */
	public String sportTableTennis = "106";
	/**
	 * {"cid":1020317,"name":"篮球"}
	 */
	public String sportBasketball = "104";

	/**
	 * 性别
	 */
	public String SEX_UNKNOW = "UNKNOW";
	public String SEX_MALE = "MALE";
	public String SEX_FAMALE = "FAMALE";
	/**
	 * 修改-用户名
	 */
	public String UPDATE_LOGINNAME = "loginName";
	public String UPDATE_NICKNAME = "nickName";
	public String UPDATE_REALNAME = "realName";
	public String UPDATE_SEX = "sex";
	public String UPDATE_ABLUM = "ablum";
	/**
	 * 软引用
	 */
	public String bestDoInfoSharedPrefsKey = "bestdo_info";
	public String exit = "exit";
	public String cancel = "cancel";
	public String exit_pay = "exit_pay";
	public String orderSharedPrefsKey = "order_info";
	/**
	 * 跳转场馆方向
	 */
	public String skipToPageByMainPage = "MainActivity";
	public String skipToPageBySearchPage = "SearchActivity";
	public String skipToPageByStadiumPage = "StadiumListMapActivity";
	/**
	 * 跳转场馆详情方向
	 */
	public String skipToDetailByStadiumPage = "StadiumListMapActivity";
	public String skipToDetailByCollectPage = "userCollectActivity";
	public String skipToDetailBySreachKWOrOrderPage = "";

	/**
	 * 支付--区别跳转渠道（购买卡、场馆下单、商城购买、会员购买）
	 */
	public static String showPayDialogByNavImg = "NavImg";//商城
	public static String showPayDialogByBuyCard = "BuyCard";//购买卡
	public static String showPayDialogByBuyMember = "BuyMember";//会员购买
	public static String showPayDialogByBuyStadium = "BuyStadium";//场馆下单
	public static String showPayDialogByBuyBalance = "BuyBalance";//余额充值
	public static int showPayDialogByBuyBalanceResult = 111;//余额充值
	/**
	 * 搜索页-城市回调、关键字回调
	 */
	public int searchForResultByCityPage = 1;
	public int searchForResultBySearchKWPage = 2;
	public int ForResult3 = 3;
	public int ForResult4 = 4;
	public int ForResult5 = 5;
	/**
	 * true下拉或加载更多正在加载;false 加载完成
	 */
	public boolean refreshOrLoadMoreLoading = false;
	public boolean walkPageComInstatus = true;
	/**
	 * 选择日期
	 */
	public String selectDTBySearchPage = "Search";
	public String selectDTByDetailPage = "Detail";
	public String selectDTByDate = "select_date";
	public String selectDTByTeetime = "select_teetime";

	/**
	 * 供应商设置匹配 1已设置，0没有；；； 供应商设置匹配：预订成功页。 人工：支付成功页；非人工：预订成功页；
	 */
	public String stadiumStockType = "1";
	/**
	 * 人工 0 非人工, 1人工
	 */
	public String stadiumUsedManage = "0";

	/**
	 * 支付类型
	 */
	public String PAY_TYPE_ZFB = "ZFB";
	public String PAY_TYPE_WX = "WX";

	/**
	 * 激活类型
	 */
	public String cardAbstractTypeKa = "1";
	public String cardAbstractTypeQuan = "2";

	public static class ShKey {
		public static final String MY_WALK_POINT = "MY_WALK_POINT";
	}

	/**
	 * 跳转卡详情
	 */
	public String skipToCardDetailByAbstractPage = "UserCardsActivateActivity";
	public String skipToCardDetailByListPage = "UserCardsActivity";
	public String skipToCardDetailByQiYEPage = "UserCardsQiYeActivity";
	/**
	 * 获取卡详情数据
	 */
	public String getCardDetailInfoByCardDePage = "cardDetail";
	public String getCardDetailInfoByAbstractPage = "jiHuo";
	/**
	 * 1 IOS 2 Android
	 */
	public String source = "2";

	public UserOrderActivity mCampaingActivity;
	public CampaignManagementActivity mCampaignManagementActivity;
	public ClubActivity mClubActivity;

	/**
	 * 订单倒计时
	 */
	public int TIMES = 120;
	public CallBackFunction function;
}
