plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-android")
    id("com.google.devtools.ksp")
    id ("kotlin-parcelize")
    id ("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlinx-serialization")
}

val kotlinVersion: String by rootProject.extra
val composeVersion: String by rootProject.extra
var hiltVersion: String by rootProject.extra
val navVersion: String by rootProject.extra

android {
    namespace = "co.ivanovpv.githubdata"
    compileSdk = 34

    defaultConfig {
        applicationId = namespace
        minSdk = 23
        targetSdk = 34
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
    lint {
        baseline = file("lint-baseline.xml")
        abortOnError = false
    }
}

dependencies {
    //navigation

    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //kotlin
    implementation ("androidx.core:core-ktx:1.13.1")

    //android
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("com.google.android.material:material:1.12.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")

    //ktor
    var ktorVersion = "2.3.12"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")

    // Hilt
    implementation ("com.google.dagger:hilt-android:$hiltVersion")
    ksp("com.google.dagger:hilt-compiler:$hiltVersion")
    ksp ("androidx.hilt:hilt-compiler:1.2.0")

    //coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    //common logging framework
    implementation ("org.slf4j:slf4j-android:1.7.30") //slf4j for Android

    //paging
    implementation ("androidx.paging:paging-runtime-ktx:3.3.2")

    // Kotlin Json
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")

    //test
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.2.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1")
}