pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "ok-marketplace-202502"

//includeBuild("lessons")
includeBuild("ok-marketplace-be")
includeBuild("ok-marketplace-libs")

includeBuild("ok-marketplace-tests")
