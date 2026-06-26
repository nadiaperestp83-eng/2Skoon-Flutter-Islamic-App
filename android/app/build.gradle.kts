plugins {
    id("com.android.application")
    id("kotlin-android")
    // The Flutter Gradle Plugin must be applied after the Android and Kotlin Gradle plugins.
    id("dev.flutter.flutter-gradle-plugin")
}

android {
    // Use uma versão do NDK compatível ou remova a linha para usar a padrão
    ndkVersion = "27.0.12077973"  // ou remova se não for essencial

    namespace = "com.skoon.muslim.app"
    compileSdk = 35  // <-- DEFINIDO EXPLICITAMENTE

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    defaultConfig {
        applicationId = "com.skoon.muslim.app"
        minSdk = 24
        targetSdk = 35  // <-- DEFINIDO EXPLICITAMENTE
        versionCode = flutter.versionCode
        versionName = flutter.versionName
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

flutter {
    source = "../.."
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.4")
}
