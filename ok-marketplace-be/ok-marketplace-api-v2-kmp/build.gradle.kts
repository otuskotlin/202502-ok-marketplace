import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    id("build-kmp")
    alias(libs.plugins.crowdproj.generator)
    alias(libs.plugins.kotlinx.serialization)
}

crowdprojGenerate {
    packageName.set("${project.group}.api.v2")
    inputSpec.set(rootProject.ext["spec-v2"] as String)
}

kotlin {
    sourceSets {
        val serializationVersion: String by project
        val commonMain by getting {
            kotlin.srcDirs(layout.buildDirectory.dir("generate-resources/src/commonMain/kotlin"))
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(projects.okMarketplaceCommon)

                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(projects.okMarketplaceStubs)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

tasks {
    val openApiGenerateTask: GenerateTask = getByName("openApiGenerate", GenerateTask::class) {
        outputDir.set(layout.buildDirectory.file("generate-resources").get().toString())
        finalizedBy("compileCommonMainKotlinMetadata")
    }
    filter { it.name.startsWith("compile") }.forEach {
        it.dependsOn(openApiGenerateTask)
    }
}
