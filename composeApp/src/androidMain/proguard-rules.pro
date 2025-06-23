# ----------------------------------
# Android Specific Rules
# ----------------------------------
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keepclassmembers class kotlinx.serialization.json.** { *; }

# ----------------------------------
# Compose Rules
# ----------------------------------
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**
-keep class androidx.lifecycle.** { *; }
-dontwarn androidx.lifecycle.**

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
# Firebase Rules
# ----------------------------------
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

# ----------------------------------
# Koin Rules
# ----------------------------------
-keep class org.koin.** { *; }
-keep class com.velocity.wallstreet.di.** { *; }

# ----------------------------------
# App-specific Rules
# ----------------------------------
# Keep your viewmodels
-keep class com.velocity.wallstreet.viewmodel.** { *; }

# Keep your utility classes
-keep class com.velocity.wallstreet.utils.** { *; }

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

# Keep the application class
-keep class com.velocity.wallstreet.WallStreetApplication { *; }
-keep class com.velocity.wallstreet.MainActivity { *; }