plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.fitzone"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.fitzone"
        minSdk = 36
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

tasks.register<Copy>("copyFitZoneApk") {
    from(layout.buildDirectory.file("outputs/apk/debug/app-debug.apk"))
    into(layout.buildDirectory.dir("fitzone"))
    rename { "FitZone.apk" }
}
tasks.configureEach { if (name == "assembleDebug") finalizedBy("copyFitZoneApk") }

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation("com.github.bumptech.glide:glide:4.16.0")
}
