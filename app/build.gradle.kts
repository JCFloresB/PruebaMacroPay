import java.io.File
import java.io.FileInputStream
import java.util.*

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.dagger.hilt.android)
    id("com.google.gms.google-services")
}

val prop = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "Constants.properties")))
}
val baseUrl: String = prop.getProperty("API_BASE_URL")
val apiToken: String = prop.getProperty("API_READ_TOKEN")
val baseUrlImage: String = prop.getProperty("BASE_URL_IMAGE")

android {
    namespace = "com.juan.carlos.flores.bastida.movies.pruebamacropay"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.juan.carlos.flores.bastida.movies.pruebamacropay"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildFeatures {
            buildConfig = true
        }

        buildConfigField("String", "BASE_URL", baseUrl)
        buildConfigField("String", "API_READ_TOKEN", apiToken)
        buildConfigField("String", "BASE_URL_IMAGE", baseUrlImage)
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi",
            "-opt-in=androidx.paging.ExperimentalPagingApi",
            "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
        )
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.bundles.layer.ui)
    implementation(platform(libs.androidx.compose.bom))

    implementation(platform(libs.firebase.bom))
    implementation(libs.androidx.material.icons.extended.android)

    ksp(libs.bundles.compilers.kapt.hilt)
    /*implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)*/
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}