-optimizationpasses 5               # 指定代码的压缩级别
-dontusemixedcaseclassnames         # 是否使用大小写混合
-dontskipnonpubliclibraryclasses
-dontpreverify                      # 混淆时是否做预校验
-verbose                            # 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法
# 不被混淆的
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v7.**

-keepattributes EnclosingMethod
-keepattributes InnerClasses
-dontoptimize
-dontwarn # 不警告


-keep class * implements android.os.Parcelable {# 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}

-keepclasseswithmembernames class * {# 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembers class * {#自定义控件不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#某一变量不混淆
-keepclasseswithmembers class com.longcheng.lifecareplan.utils.ConstantManager {
    private java.io.FileDescriptor mFd;
}

#某一方法不混淆
#注意参数和返回值如果不是基本类型，是类类型都必须写包名；
#-keepclasseswithmembers class com.xxx.xxx {
#    void m1();
#    boolean m2(android.content.Context);
#    com.xxx.xxx.Temp m3(com.xxx.xxx.Temp);
#}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#-ignorewarnings
# Location
-keep class com.amap.api.**{*;}
#3D 地图 V5.0.0之后：
-keep class com.amap.api.maps.**{*;}
-keep class com.autonavi.**{*;}
-keep class com.amap.api.trace.**{*;}
#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
#搜索
-keep class com.amap.api.services.**{*;}

-keep class com.tencent.smtt.** { *; }
-dontwarn  com.tencent.smtt.**
# OkHttp
-dontwarn com.squareup.okhttp.**
-keep class okio.**{*;}
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

###  美恰客服
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.** { *; }
-dontwarn com.squareup.okhttp3.**

-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# talking data
-dontwarn com.tendcloud.tenddata.**
-keep class com.tendcloud.** {*;}
-keep public class com.tendcloud.tenddata.** { public protected *;}
-keepclassmembers class com.tendcloud.tenddata.**{
public void *(***);
}
-keep class com.talkingdata.sdk.TalkingDataSDK {public *;}
-keep class com.apptalkingdata.** {*;}

######################短视频混淆配置#########################
-keep class com.aliyun.**{*;}
-keep class com.duanqu.**{*;}
-keep class com.qu.**{*;}
-keep class com.alibaba.**{*;}
-keep class component.alivc.**{*;}
# 直播
-keep class com.alibaba.livecloud.**{*;}
-keep class com.alivc.**{*;}
-keep class com.aliyun.clientinforeport.**{*;}
-dontwarn com.alivc.player.**
# 第三方接口不混淆
-keep class com.tencent.android.tpush.**{*;}
-keep class com.tencent.mid.**{*;}
-keep class com.jg.**{*;}
-keep class com.qq.**{*;}
-keep class src.com.qq.**{*;}
-keep class com.nineoldandroids.**{*;}
-keep class com.aps.**{*;}
-keep class com.amap.api.**{*;}
-keep class com.google.protobuf.micro.**{*;}

# http client
-keep class org.apache.http.**{*;}

#zxing二维码混淆配置
-dontwarn com.google.zxing.**
-keep class com.google.zxing.**{*;}

#GLide混淆
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$**{
  **[] $VALUES;
  public *;
}
# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

#Retrofit混淆
-dontwarn okio.**
-dontwarn javax.annotation.**

#Gson
##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe {*;}
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
#自己的Gson Bean类
-keep class com.google.gson.examples.android.model.**{*;}

##---------------End: proguard configuration for Gson  ----------

#Serializable 的配置
# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * implements java.io.Serializable {*;}

#OkHttp3
-dontwarn okhttp3.**
#-dontwarn okio.**   Retrofit已混淆
-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase


#RXJava
# rx
-dontwarn rx.**
-keepclassmembers class rx.**{*;}
# retrolambda
-dontwarn java.lang.invoke.*

#butterknife
-keep class butterknife.**{*;}
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder{*;}
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#JPush
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.**{*;}
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver {*;}

-dontwarn cn.jiguang.**
-keep class cn.jiguang.**{*;}

#华为推送
-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}

#小米推送
-keep class com.longcheng.lifecareplan.push.xmpush.XMPushReceiver {*;}
#可以防止一个误报的 warning 导致无法成功编译，如果编译使用的 Android 版本是 23。
-dontwarn com.xiaomi.push.**

#友盟统计
-keep class com.umeng.commonsdk.**{*;}
#友盟
 -dontusemixedcaseclassnames
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-keep public class javax.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**
-keep class com.android.dingtalk.share.ddsharemodule.**{*;}
-keep public class com.umeng.socialize.*{*;}


-keep class com.umeng.scrshot.**
-keep public class com.tencent.**{*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.umeng.weixin.handler.**
-keep class com.umeng.weixin.handler.*
-keep class com.umeng.qq.handler.**
-keep class com.umeng.qq.handler.*
-keep class UMMoreHandler{*;}
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements   com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep class com.tencent.mm.sdk.** {
 *;
}
-keep class com.tencent.mm.opensdk.** {
*;
}
-dontwarn twitter4j.**
-keep class twitter4j.**{*;}

-keep class com.tencent.**{*;}
-dontwarn com.tencent.**
-keep public class com.umeng.com.umeng.soexample.R$*{
public static final int *;
}
-keep public class com.linkedin.android.mobilesdk.R$*{
public static final int *;
    }
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}
-keep class com.tencent.** { *; }
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
   *;
}
## 注解支持
-keepclassmembers class *{
   void *(android.view.View);
}

#保护注解
-keepattributes *Annotation*

# 支付宝 支付
-libraryjars libs/alipaySdk-20180601.jar

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}



-keepnames class * implements android.os.Parcelable {
public static final ** CREATOR;
}

#noinspection ShrinkerUnresolvedReference
-keep class com.linkedin.**{*;}
-keepattributes Signature

#### webView js 调用本地
-keepclassmembers class * extends android.webkit.WebChromeClient{
public void openFileChooser(...);
}
-keep public class * extends android.view.View{
*** get*();
void set*(***);
public <init>(android.content.Context);
public <init>(android.content.Context, android.util.AttributeSet);
public <init>(android.content.Context, android.util.AttributeSet, int);
}
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }

-keepattributes Signature
# 实体类不混淆（注意xxx是你项目的路径）
-keep class com.longcheng.lifecareplan.bean.**{*;}
-keep class com.longcheng.lifecareplan.http.basebean.**{*;}

-keep class com.longcheng.lifecareplan.modular.exchange.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.exchange.malldetail.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.exchange.shopcart.bean.**{*;}

-keep class com.longcheng.lifecareplan.modular.helpwith.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.autohelp.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.connon.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.connonEngineering.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.energy.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.energydetail.rank.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.lifestyle.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.lifestylerank.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.medalrank.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.myDedication.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.myGratitude.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.home.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.home.commune.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.home.domainname.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.home.healthydelivery.list.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.home.liveplay.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.index.login.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.index.welcome.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.activatenergy.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.awordofgold.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.bill.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.cashflow.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.changeinviter.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.doctor.aiyou.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.doctor.volunteer.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.goodluck.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.invitation.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.message.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.myaddress.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.myorder.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.myorder.detail.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.myorder.ordertracking.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.caililist.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.fqborder.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.openorder.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.pionopen.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.tutorExit.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.zyblist.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.record.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.relationship.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.rewardcenters.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.set.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.signIn.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.userinfo.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.mine.emergencys.bean.**{*;}

-keep class com.longcheng.lifecareplan.utils.pay.**{*;}
-keep class com.longcheng.lifecareplan.utils.myview.address.bean.**{*;}
-keep class com.longcheng.lifecareplan.utils.date.**{*;}
-keep class com.longcheng.lifecareplan.push.jpush.message.bean.**{*;}
-keep class com.longcheng.lifecareplan.widget.jswebview.browse.bean.**{*;}
#-keep class app.gds.one.entity.**{*;}
#-keep class app.gds.one.entity.**{*;}
#-keep class app.gds.one.entity.**{*;}

































