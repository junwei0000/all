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
#融云 sdk
-keepattributes EnclosingMethod
-keepattributes Exceptions,InnerClasses
-keepattributes Signature
-keep class io.rong.app.DemoNotificationReceiver {*;}
-keep class io.rong.** {*;}
-keep class cn.rongcloud.** {*;}
-keep class * implements io.rong.imlib.model.MessageContent {*;}
-dontwarn io.rong.push.**
-dontnote com.xiaomi.**
-dontnote com.google.android.gms.gcm.**
-dontnote io.rong.**
# Location
-keep class com.amap.api.**{*;}
-keep class com.amap.api.services.**{*;}
-ignorewarnings

# 有赞 SDK
-dontwarn com.youzan.**
-keep class com.youzan.jsbridge.** { *; }
-keep class com.youzan.spiderman.** { *; }
-keep class com.youzan.androidsdk.** { *; }
-keep class com.youzan.x5web.** { *; }
-keep class com.youzan.androidsdkx5.** { *; }
-keep class dalvik.system.VMStack { *; }
-keep class com.tencent.smtt.** { *; }
-dontwarn  com.tencent.smtt.**
# OkHttp
-dontwarn com.squareup.okhttp.**
-keep class okio.**{*;}
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

# IM
-keep class com.youzan.mobile.zanim.model.** { *; }

-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

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

# 实体类不混淆（注意xxx是你项目的路径）
-keep class com.longcheng.lifecareplan.bean.**{*;}
-keep class com.longcheng.lifecareplan.modular.home.bean.**{*;}

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
-ignorewarning
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
    -keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
    }

    -keep class com.linkedin.**{*;}
    -keepattributes Signature

























