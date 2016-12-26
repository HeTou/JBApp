# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\install\Android\androidStudio2.0\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


-optimizationpasses 5   #优化数量 n
-dontusemixedcaseclassnames  #不使用大小写混合类名
-dontskipnonpubliclibraryclasses  #不跳过jars中的非public classes。在proguard4.5时，是默认选项
-dontpreverify   #不预校验
#-ignorewarnings     #忽略警告，继续执行
-verbose            #打印详细
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#-dontoptimize  #不优化

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(Java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#------------------------以上是基础配置----------------------------------------------------------------------------

# TODO 极光推送
#-dontoptimize      上文已有这里注释掉
#-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

# TODO PLDroidPlayer
-keep class com.pili.pldroid.player.** { *; }
-keep class tv.danmaku.ijk.media.player.** {*;}
