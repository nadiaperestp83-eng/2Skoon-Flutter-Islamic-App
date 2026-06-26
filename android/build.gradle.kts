plugins {
    // Utilize versões compatíveis com o Android 15 (SDK 35)
    id("com.android.application") version "8.3.2" apply false
    id("com.android.library") version "8.3.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("dev.flutter.flutter-gradle-plugin") version "1.0.0" apply false
}

rootProject.layout.buildDirectory.set(file("../build"))

subprojects {
    project.layout.buildDirectory.set(rootProject.layout.buildDirectory.dir(project.name))
}

subprojects {
    afterEvaluate {
        // Apenas force o SDK se for estritamente necessário, 
        // mas o ideal é remover este bloco e definir no build.gradle.kts do app.
        if (project.hasProperty("android")) {
            val android = project.extensions.getByName("android")
            if (android is com.android.build.gradle.BaseExtension) {
                android.compileSdkVersion(35)
            }
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
