enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("../node_modules/@react-native/gradle-plugin")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("com.facebook.react.settings")
}

extensions.configure<com.facebook.react.ReactSettingsExtension> { autolinkLibrariesFromCommand() }


includeBuild("../node_modules/@react-native/gradle-plugin")

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
extensions.configure<com.facebook.react.ReactSettingsExtension> {
    autolinkLibrariesFromCommand()
}
rootProject.name = "Cross_platform_benchmark"
include(":androidApp")
include(":shared")
//
//include(":react-native-config")
//project(":react-native-config").projectDir = File(rootProject.projectDir, "./node_modules/react-native-config/android")