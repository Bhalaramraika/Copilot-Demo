# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep JARVIS application classes
-keep class com.jarvis.assistant.** { *; }

# Kotlin and Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-dontwarn kotlinx.coroutines.**

# Retrofit and OkHttp
-keepattributes Signature
-keepattributes Exceptions
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn retrofit2.**
-dontwarn okhttp3.**

# Gson
-keep class com.google.gson.** { *; }
-keep class com.jarvis.assistant.model.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# AndroidX and Jetpack Compose
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
-keep class androidx.compose.** { *; }
-dontwarn androidx.**

# Material Design
-keep class com.google.android.material.** { *; }

# Accessibility Service
-keep class * extends android.accessibilityservice.AccessibilityService { *; }

# Device Admin
-keep class * extends android.app.admin.DeviceAdminReceiver { *; }

# Prevent obfuscation
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeVisibleTypeAnnotations

# Don't warn about missing classes
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**
