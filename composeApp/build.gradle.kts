import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "2.1.10"
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.fragment.ktx)
            implementation(libs.ktor.client.android)
            implementation(libs.navigation.compose)
            implementation(libs.splashscreen.compose)
            implementation(libs.koin.android)
            implementation(libs.koin.compose.viewmodel.nav)
            implementation(libs.firebase.messaging)
            implementation(project.dependencies.platform(libs.firebase.bom))
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            implementation(libs.koin.compose.viewmodel.nav)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.java.native.access)
        }
    }
}

android {
    version = "2.5.1"
    namespace = "com.velocity.wallstreet"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.velocity.wallstreet"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 6
        versionName = project.version.toString()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt")
            )

            applicationVariants.all {
                val variant = this
                variant.outputs
                    .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                    .forEach { output ->
                        val newFileName = "WallStreet-${defaultConfig.versionName}.apk"
                        output.outputFileName = newFileName
                    }
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    version = "2.5.1"

    application {
        mainClass = "com.velocity.wallstreet.MainKt"

        run {
            jvmArgs += listOf("-Dapp.version=${project.version}")
        }

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "WallStreet"
            vendor = "Trishiraj"
            packageVersion = project.version.toString()
            description = "A compose multiplatform Wallpaper app with NeoBrutalismUI for Desktop and Mobile"
            copyright = "© 2024 - 25 Trishiraj. All rights reserved."

            val iconsRoot = project.file("src/desktopMain/desktop-icons")
            windows {
                iconFile.set(iconsRoot.resolve("launcher.ico"))
                upgradeUuid = "8cfc414b-35a1-40a7-9d94-82b8c9b47c90"
                dirChooser = true
                menu = true
            }
            macOS {
                iconFile.set(iconsRoot.resolve("launcher.icns"))
            }
            linux {
                iconFile.set(iconsRoot.resolve("launcher.png"))
                debMaintainer = "trishiraj.247@gmail.com"
            }

            buildTypes.release.proguard {
                isEnabled = true
                optimize = true
                obfuscate = true
                configurationFiles.from("src/desktopMain/proguard-rules.pro")
            }
        }
    }
}

