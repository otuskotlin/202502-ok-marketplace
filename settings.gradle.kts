pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "otuskotlin-marketplace-202502"

include("m1l1-first", "m1l2-basic")
include("m1l3-func")
include("m1l4-oop")
