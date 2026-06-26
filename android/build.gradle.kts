plugins {
    // Versões removidas para evitar conflito com o plugin do Flutter
    id("com.android.application") apply false
    id("com.android.library") apply false
    // Kotlin pode manter a versão, desde que seja compatível com a do seu Gradle
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("dev.flutter.flutter-gradle-plugin") version "1.0.0" apply false
}

rootProject.layout.buildDirectory.set(file("../build"))

subprojects {
    project.layout.buildDirectory.set(rootProject.layout.buildDirectory.dir(project.name))
}

subprojects {
    afterEvaluate {
        // Bloco de segurança para garantir o compileSdk 35
        if (project.hasProperty("android")) {
            val android = project.extensions.findByName("android")
            if (android is com.android.build.gradle.BaseExtension) {
                android.compileSdkVersion(35)
            }
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
