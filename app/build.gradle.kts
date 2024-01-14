
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)

    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.task.movieapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.task.movieapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"

    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.compose.navigation)

    implementation(libs.room.ktx)
    kapt(libs.room.complier)
    implementation(libs.room.paging)

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.compiler)
    implementation(libs.hilt.navigation)


    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.sqaure.logger)
    implementation(libs.okhttp3)

    debugImplementation(libs.chuckerteam)
    releaseImplementation(libs.chuckerteam.no.op)

    implementation(libs.coil)

    implementation(libs.sytem.ui.controller)
    implementation(libs.material.icon)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}
kapt {
    correctErrorTypes = true
}