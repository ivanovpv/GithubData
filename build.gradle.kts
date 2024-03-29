buildscript {
    repositories {
        google()
    }
    extra.apply {
        set("kotlinVersion", "1.9.0") //check also KSP and composeVersions!
        set("composeVersion", "1.5.2")
        set("navVersion", "2.6.0")
        set("hiltVersion", "2.48") //2.48 min version with KSP support
    }
    dependencies {
        val kotlinVersion: String by rootProject.extra
        val navVersion: String by rootProject.extra
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    }
}
plugins {
    id ("com.android.application") version "8.1.0" apply false
    id ("com.android.library") version "8.1.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id ("com.google.dagger.hilt.android") version "2.48" apply false
    id ("androidx.navigation.safeargs") version "2.6.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}
