# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

 -dontwarn com.alibaba.**
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
 -keep class * implements com.alibaba.android.arouter.facade.template.IProvider

 # okhttp
 -dontwarn okhttp3.**

 #retrofit
 -dontwarn retrofit2.**
 -dontwarn okio.**

 #bugly
 -dontwarn com.tencent.bugly.**
 -keep public class com.tencent.bugly.**{*;}

 # =====================fresco================

 -keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

 # Do not strip any method/class that is annotated with @DoNotStrip
 -keep @com.facebook.common.internal.DoNotStrip class *
 -keepclassmembers class * {
     @com.facebook.common.internal.DoNotStrip *;
 }

 # Keep native methods
 -keepclassmembers class * {
     native <methods>;
 }

 -dontwarn okio.**
 -dontwarn com.squareup.okhttp.**
 -dontwarn okhttp3.**
 -dontwarn javax.annotation.**
 -dontwarn com.android.volley.toolbox.**
 -dontwarn com.facebook.**

 # =====================fresco================

 #转换JSON的JavaBean，类成员名称保护，使其不被混淆
 -keepclassmembernames class com.xiaoanjujia.home.entities.** { *; }

 #auto view pager
 -keepclassmembernames class com.xiaoanjujia.common.widget.autoscrollviewpager.** { *; }

 #PictureSelector 2.0
 -keep class com.luck.picture.lib.** { *; }

 #Ucrop
 -dontwarn com.yalantis.ucrop**
 -keep class com.yalantis.ucrop** { *; }
 -keep interface com.yalantis.ucrop** { *; }

 #Okio
 -dontwarn org.codehaus.mojo.animal_sniffer.*

 #x5Webview
 -dontwarn dalvik.**
   -dontwarn com.tencent.smtt.**

   -keep class com.tencent.smtt.** {
       *;
   }

   -keep class com.tencent.tbs.** {
       *;
   }

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

   -keep public interface com.facebook.**
   -keep public interface com.tencent.**
   -keep public interface com.umeng.socialize.**
   -keep public interface com.umeng.socialize.sensor.**
   -keep public interface com.umeng.scrshot.**

   -keep public class com.umeng.socialize.* {*;}


   -keep class com.facebook.**
   -keep class com.facebook.** { *; }
   -keep class com.umeng.scrshot.**
   -keep public class com.tencent.** {*;}
   -keep class com.umeng.socialize.sensor.**
   -keep class com.umeng.socialize.handler.**
   -keep class com.umeng.socialize.handler.*
   -keep class com.umeng.weixin.handler.**
   -keep class com.umeng.weixin.handler.*
   -keep class com.umeng.qq.handler.**
   -keep class com.umeng.qq.handler.*
   -keep class UMMoreHandler{*;}
   -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
   -keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
   -keep class im.yixin.sdk.api.YXMessage {*;}
   -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
   -keep class com.tencent.mm.sdk.** {
      *;
   }
   -keep class com.tencent.mm.opensdk.** {
      *;
   }
   -keep class com.tencent.wxop.** {
      *;
   }
   -keep class com.tencent.mm.sdk.** {
      *;
   }

   -keep class com.twitter.** { *; }

   -keep class com.tencent.** {*;}
   -dontwarn com.tencent.**
   -keep class com.kakao.** {*;}
   -dontwarn com.kakao.**
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

   -keep class com.tencent.open.TDialog$*
   -keep class com.tencent.open.TDialog$* {*;}
   -keep class com.tencent.open.PKDialog
   -keep class com.tencent.open.PKDialog {*;}
   -keep class com.tencent.open.PKDialog$*
   -keep class com.tencent.open.PKDialog$* {*;}
   -keep class com.umeng.socialize.impl.ImageImpl {*;}
   -keep class com.sina.** {*;}
   -dontwarn com.sina.**
   -keep class  com.alipay.share.sdk.** {
      *;
   }

   -keepnames class * implements android.os.Parcelable {
       public static final ** CREATOR;
   }

   -keep class com.linkedin.** { *; }
   -keep class com.android.dingtalk.share.ddsharemodule.** { *; }
   -keepattributes Signature