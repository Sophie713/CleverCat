// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.hiltKotlinAndroid) apply false
}

buildscript {
    repositories {
        google()
    }
    dependencies {
//       classpath("com.google.dagger:hilt-android-gradle-plugin:2:40:5")
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}