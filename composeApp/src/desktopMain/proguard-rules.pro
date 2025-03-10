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
# Java Reflection Rules
# ----------------------------------
-keepclassmembers class * {
    @org.jetbrains.annotations.NotNull *;
    @org.jetbrains.annotations.Nullable *;
}