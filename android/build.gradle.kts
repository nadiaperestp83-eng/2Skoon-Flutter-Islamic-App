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
    // em plugins antigos do pub.dev. Os valores precisam ser reais,
    // pois plugins como o "jni" leem flutter.ndkVersion direto no corpo do script
    // (antes de qualquer afterEvaluate ter chance de rodar).
    if (!project.extensions.extraProperties.has("flutter")) {
        project.extensions.extraProperties.set(
            "flutter",
            mapOf(
                "compileSdkVersion" to 35,
                "targetSdkVersion" to 35,
                "minSdkVersion" to 24,
                "ndkVersion" to "27.0.12077973",
                "versionCode" to 1,
                "versionName" to "1.0"
            )
        )
    }

    afterEvaluate {
        // Garantia extra de compilação com SDK 35 e NDK fixo,
        // para qualquer plugin que só valide isso depois (afterEvaluate)
        if (project.hasProperty("android")) {
            val android = project.extensions.findByName("android")
            if (android is com.android.build.gradle.BaseExtension) {
                android.compileSdkVersion(35)
                if (android.ndkVersion.isNullOrBlank()) {
                    android.ndkVersion = "27.0.12077973"
                }
            }
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
