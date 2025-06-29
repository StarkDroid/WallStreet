# ----------------------------------
# Desktop Specific Rules
# ----------------------------------
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keepclassmembers class kotlinx.serialization.json.** { *; }

# ----------------------------------
# Compose Rules
# ----------------------------------
-keep class androidx.compose.** { *; }
-keep class org.jetbrains.compose.** { *; }
-dontwarn androidx.compose.**
-dontwarn org.jetbrains.compose.**

# ----------------------------------
# Ktor Rules
# ----------------------------------
-keep class io.ktor.** { *; }
-keep class kotlinx.serialization.** { *; }
-keep class kotlinx.coroutines.** { *; }
-dontwarn io.ktor.**
-dontwarn kotlinx.serialization.**
-dontwarn kotlinx.coroutines.**

# ----------------------------------
# Coil Rules
# ----------------------------------
-keep class coil.** { *; }
-keep class coil3.** { *; }
-keep class coil.imageLoader.** { *; }
-keep class coil.request.** { *; }
-dontwarn coil.**
-dontwarn coil3.**

# ----------------------------------
# Kotlin Rules
# ----------------------------------
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }
-dontwarn kotlin.**
-dontwarn kotlinx.**

# ----------------------------------
# Koin Rules
# ----------------------------------
-keep class org.koin.** { *; }
-dontwarn org.koin.**
-keep class com.velocity.wallstreet.di.** { *; }

# ----------------------------------
# Lifecycle Rules
# ----------------------------------
-keep class androidx.lifecycle.** { *; }
-dontwarn androidx.lifecycle.**

# ----------------------------------
# Android Core Rules
# ----------------------------------
-keep class androidx.core.** { *; }
-dontwarn androidx.core.**

# ----------------------------------
# App-specific Rules
# ----------------------------------
# Keep your viewmodels
-keep class com.velocity.wallstreet.viewmodel.** { *; }

# Keep your utility classes
-keep class com.velocity.wallstreet.utils.** { *; }
-keepclasseswithmembers class com.velocity.wallstreet.utils.WallpaperUtilsDesktop { *; }
-keepclasseswithmembers class com.velocity.wallstreet.utils.PlatformUtils { *; }
# Keep JNA classes and all implementations
-keep class com.sun.jna.** { *; }
-keep class * implements com.sun.jna.** { *; }
-keepattributes InnerClasses, Signature

# Keep all utils classes and their members (including private ones)
-keep class com.velocity.wallstreet.utils.** { *; }
-keepclassmembers class com.velocity.wallstreet.utils.** { *; }

# Specifically keep the wallpaper-related methods
-keepclassmembers class * {
    boolean setWindowsWallpaper(java.lang.String);
    java.io.File downloadFile(java.lang.String);
    boolean SystemParametersInfoW(int, int, java.lang.String, int);
}

# Keep your UI components
-keep class com.velocity.wallstreet.ui.** { *; }

# Keep your navigation classes
-keep class com.velocity.wallstreet.navigation.** { *; }

# Keep your theme classes
-keep class com.velocity.wallstreet.theme.** { *; }

# Keep Compose resources
-keep class wallstreet.composeapp.generated.resources.** { *; }

# ----------------------------------
# Java Reflection Rules
# ----------------------------------
-keepclassmembers class * {
    @org.jetbrains.annotations.NotNull *;
    @org.jetbrains.annotations.Nullable *;
}

# Keep the main class
-keep class com.velocity.wallstreet.MainKt { *; }

# ----------------------------------
# General Rules
# ----------------------------------
# Ignore all warnings for unresolved classes
-ignorewarnings
