-ignorewarnings
#关闭某个运营商功能时编译通不过可以加上下面的代码
#-dontpreverify
# for SecVerify
-keep class com.mob.**{*;}
# for CTCC
-keep class cn.com.chinatelecom.account.**{*;}
-keep class cn.com.chinatelecom.account.api.ClientUtils{*;}
# for CUCC
-keep class com.sdk.**{*;}
# for CMCC
-keep class com.cmic.sso.sdk.**{*;}
-keep class com.unicom.xiaowo.account.shield.**{*;}

-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class com.mob.**{*;}
-keep class com.bytedance.**{*;}
-dontwarn cn.sharesdk.**
-dontwarn com.sina.**
-dontwarn com.mob.**