plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)

}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
        dependencies {
            classpath("com.android.tools.build:gradle:7.3.1")
            classpath("com.facebook.react:react-native-gradle-plugin")

        }

}

