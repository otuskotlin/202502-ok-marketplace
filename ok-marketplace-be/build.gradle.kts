plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
//    alias(libs.plugins.muschko.remote) apply false
//    alias(libs.plugins.muschko.java) apply false
    alias(libs.plugins.kotlin.kapt) apply false
}

group = "ru.otus.otuskotlin.marketplace"
version = "0.0.1"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}

ext {
    val specDir = layout.projectDirectory.dir("../specs")
    set("spec-v1", specDir.file("specs-ad-v1.yaml").toString())
    set("spec-v2", specDir.file("specs-ad-v2.yaml").toString())
    set("spec-log1", specDir.file("specs-ad-log1.yaml").toString())
}

tasks {
    arrayOf("build", "clean", "check").forEach { tsk ->
        register(tsk) {
            group = "build"
            dependsOn(subprojects.map { it.getTasksByName(tsk, false) })
        }
    }
    register("buildImages") {
        dependsOn(project("ok-marketplace-app-spring").tasks.getByName("bootBuildImage"))
        dependsOn(project("ok-marketplace-app-ktor").tasks.getByName("publishImageToLocalRegistry"))
        dependsOn(project("ok-marketplace-app-ktor").tasks.getByName("docker"))
    }
}
