plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.kotlin.android") apply false
    id("dev.flutter.flutter-gradle-plugin") apply false
}

rootProject.layout.buildDirectory.set(file("../build"))

subprojects {
    project.layout.buildDirectory.set(rootProject.layout.buildDirectory.dir(project.name))
}

subprojects {
    // ESSENCIAL: Isso resolve o erro de "Could not get unknown property 'flutter'"
    // em plugins antigos do pub.dev
    if (!project.extensions.extraProperties.has("flutter")) {
        project.extensions.extraProperties.set("flutter", mapOf<String, Any>())
    }

    afterEvaluate {
        // Garantia de compilação com SDK 35
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
