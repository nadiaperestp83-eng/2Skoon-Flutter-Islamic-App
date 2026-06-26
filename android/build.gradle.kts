buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

val newBuildDir = rootProject.layout.buildDirectory.dir("../../build").get()
rootProject.layout.buildDirectory.set(newBuildDir)

subprojects {
    val newSubprojectBuildDir = newBuildDir.dir(project.name)
    project.layout.buildDirectory.set(newSubprojectBuildDir)
}

// ==========================================
// CORREÇÃO GLOBAL PARA TODOS OS PLUGINS
// ==========================================
subprojects {
    afterEvaluate {
        // Define compileSdk = 35 para todos os módulos Android
        project.extensions.findByName("android")?.let { ext ->
            when (ext) {
                is com.android.build.gradle.LibraryExtension -> {
                    ext.compileSdk = 35
                }
                is com.android.build.gradle.AppExtension -> {
                    ext.compileSdk = 35
                }
            }
        }
        // Cria a propriedade 'flutter' para evitar erro "unknown property"
        if (!project.ext.has("flutter")) {
            project.ext.set("flutter", mapOf<String, Any>())
        }
    }
}

subprojects {
    project.evaluationDependsOn(":app")
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
