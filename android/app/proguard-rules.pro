# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep JARVIS application classes
-keep class com.jarvis.assistant.** { *; }

# Keep WebView JavaScript Interface methods
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Keep WebView related classes
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebChromeClient {
    public void *(android.webkit.WebView, java.lang.String);
}

# AndroidX and Material Design
-keep class com.google.android.material.** { *; }
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Prevent obfuscation of WebView assets
-keepattributes JavascriptInterface
-keepattributes *Annotation*

# Don't warn about missing classes
-dontwarn android.webkit.**
-dontwarn androidx.**
