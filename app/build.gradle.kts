plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainsKotlinKsp)
    alias(libs.plugins.hiltPlugin)
    alias(libs.plugins.kotlinSerialization)
}


android {
    namespace = "com.example.swiftcart"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.swiftcart"
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //Splash
    implementation(libs.splash.screen)

    //Lottie
    implementation(libs.lottie.animation)

    //Navigation
    implementation(libs.navigation.compose)

    //Datastore
    implementation(libs.dataStore.preferences)

    // Pager and Pager Indicators
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.pager)

    //Hilt
    implementation (libs.hilt.android)
    ksp(libs.hilt.ext.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation)

    //Coroutines
    implementation(libs.kotlinx.coroutines)

    //Google Fonts
    implementation(libs.google.fonts)

    //Compose Lifecycle
    implementation(libs.runtime.livedata)
    implementation(libs.lifecycle.compose)

    //Kotlinx-serialization
    implementation(libs.kotlinx.serialization)

    //ktor
    implementation(libs.client.android)
    implementation(libs.client.logging)
    implementation(libs.client.auth)
    implementation(libs.client.serialization)
    implementation(libs.client.content.negotiation)
    implementation(libs.client.cio)
    implementation(libs.ktor.serialization)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}