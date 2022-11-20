plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
    id ("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

val hiltVersion = project.rootProject.ext["hiltVersion"]
val navVersion = project.rootProject.ext["navVersion"]

android {
    namespace = "co.ivanovpv.githubdata"
    compileSdk = 33

    defaultConfig {
        applicationId = namespace
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        setProperty("archivesBaseName", "githubdata-$versionName")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles (
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
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
        viewBinding = true
    }
}

dependencies {
    //navigation

    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //kotlin
    implementation ("androidx.core:core-ktx:1.9.0")

    //android
    implementation ("androidx.appcompat:appcompat:1.5.1")
    implementation ("com.google.android.material:material:1.7.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    // retrofit
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")

    // Hilt
    implementation ("com.google.dagger:hilt-android:$hiltVersion")
    kapt ("com.google.dagger:hilt-compiler:$hiltVersion")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")

    //coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    //common logging framework
    implementation ("org.slf4j:slf4j-android:1.7.30") //slf4j for Android

    //paging
    implementation ("androidx.paging:paging-runtime-ktx:3.1.1")

    //test
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.4")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.0")
}