buildscript {
    ext.kotlin_version = '1.9.22'  // Versão estável do Kotlin
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        // Gradle plugin (versão compatível com o Flutter)
        classpath 'com.android.tools.build:gradle:7.4.2'  // ou 8.1.0, mas 7.4.2 é estável
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

// Configuração do diretório de build (opcional, mas mantida)
val newBuildDir: Directory = rootProject.layout.buildDirectory.dir("../../build").get()
rootProject.layout.buildDirectory.value(newBuildDir)

subprojects {
    val newSubprojectBuildDir: Directory = newBuildDir.dir(project.name)
    project.layout.buildDirectory.value(newSubprojectBuildDir)
}

// Remova a lógica abaixo se não for necessária (pode causar problemas)
// subprojects {
//     afterEvaluate {
//         if (hasProperty("android")) {
//             configure<com.android.build.gradle.BaseExtension> {
//                 if (namespace == null) {
//                     namespace = project.group.toString()
//                 }
//                 // ... lógica para remover package do manifest
//             }
//         }
//     }
// }

// Se precisar forçar a avaliação do app
subprojects {
    project.evaluationDependsOn(":app")
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
