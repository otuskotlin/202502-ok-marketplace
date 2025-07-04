plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
}

group = "ru.otus.otuskotlin.marketplace"
version = "0.0.1"

repositories {
    mavenCentral()
}

subprojects {
    repositories {
        mavenCentral()
    }
    group = rootProject.group
    version = rootProject.version
}

tasks {
    register("clean") {
        group = "build"
        gradle.includedBuilds.forEach {
            dependsOn(it.task(":clean"))
        }
    }
    val buildMigrations: Task by creating {
        dependsOn(gradle.includedBuild("ok-marketplace-other").task(":buildImages"))
    }

    val buildImages: Task by creating {
        dependsOn(buildMigrations)
        dependsOn(gradle.includedBuild("ok-marketplace-be").task(":buildImages"))
    }
    val e2eTests: Task by creating {
        dependsOn(buildImages)
        dependsOn(gradle.includedBuild("ok-marketplace-tests").task(":e2eTests"))
        mustRunAfter(buildImages)
    }

    register("check") {
        group = "verification"
        dependsOn(gradle.includedBuild("ok-marketplace-be").task(":check"))
        dependsOn(e2eTests)
    }
}
