import org.gradle.jvm.tasks.Jar
import org.jetbrains.dokka.gradle.GradleExternalDocumentationLinkBuilder
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.kryptonmc.krypton.Versions
import org.kryptonmc.krypton.adventure
import org.kryptonmc.krypton.dependsOn
import org.kryptonmc.krypton.junit
import org.kryptonmc.krypton.kotlinx
import org.kryptonmc.krypton.log4j

plugins {
    kotlin("jvm") version "1.5.0"
    kotlin("plugin.serialization") version "1.5.0"
    id("org.jetbrains.dokka") version "1.4.30"
    id("info.solidsoft.pitest") version "1.6.0"
    id("org.cadixdev.licenser") version "0.6.0" apply false
    `maven-publish`
    signing
    id("io.github.slimjar") version "1.1.0"
}

allprojects {
    group = "org.kryptonmc"
    version = "0.19.3"

    repositories {
        mavenCentral()
        maven("https://libraries.minecraft.net")
        jcenter()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "info.solidsoft.pitest")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")
    apply(plugin = "io.github.slimjar")

    dependencies {
        slim(kotlin("stdlib"))
        slim("com.google.guava:guava:30.1.1-jre")
        slim("org.apache.commons:commons-lang3:3.12.0")
        slim("org.apache.commons:commons-text:1.9")

        slim(kotlinx("coroutines-core", Versions.COROUTINES))
        slim(kotlinx("serialization-json", Versions.SERIALIZATION))
        slim(kotlinx("serialization-hocon", Versions.SERIALIZATION))

        slim(adventure("api"))
        slim(adventure("extra-kotlin"))

        slim("com.mojang:brigadier:1.0.17")

        slim(log4j("api"))

        // Testing
        testImplementation(junit("jupiter", "api"))
        testRuntimeOnly(junit("jupiter", "engine"))
        testImplementation(junit("platform", "runner", "1.7.1"))
        testImplementation(kotlin("test-junit5"))
        testImplementation("io.mockk:mockk:1.10.6")
    }

    configurations.all {
        exclude("org.checkerframework", "checker-qual")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "11"
                freeCompilerArgs = listOf(
                    "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi",
                    "-Xjvm-default=all"
                )
            }
        }
        withType<Test> {
            useJUnitPlatform()
        }
    }

    pitest {
        junit5PluginVersion.set("0.12")
        avoidCallsTo.set(setOf(
            "kotlin.jvm.internal",
            "java.util.logging",
            "org.slf4j",
            "org.apache.logging.log4j"
        ))
        mutators.set(setOf("STRONGER"))
        targetClasses.set(setOf("org.kryptonmc.*"))
        targetTests.set(setOf("org.kryptonmc.*"))
        threads.set(Runtime.getRuntime().availableProcessors())
        outputFormats.set(setOf("XML", "HTML"))
        excludedMethods.set(setOf("equals", "hashCode", "toString", "emptyList"))
    }

    tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
        dokkaSourceSets {
            named("main") {
                fun adventure(module: String) = Action<GradleExternalDocumentationLinkBuilder> {
                    val baseURL = "https://jd.adventure.kyori.net/$module/${Versions.ADVENTURE}/"
                    url.set(uri(baseURL).toURL())
                    packageListUrl.set(uri("${baseURL}element-list").toURL())
                }

                externalDocumentationLink(adventure("api"))
                externalDocumentationLink(adventure("key"))
                externalDocumentationLink(adventure("nbt"))
                externalDocumentationLink(adventure("text-serializer-gson"))
                externalDocumentationLink(adventure("text-serializer-legacy"))
                externalDocumentationLink(adventure("text-serializer-plain"))
            }
        }
    }

    task<Jar>("sourcesJar") {
        from(sourceSets.main.get().allSource)
        archiveClassifier.set("sources")
    }

    task<Jar>("javadocJar") {
        from(tasks["dokkaJavadoc"])
        archiveClassifier.set("javadoc")
    }

    tasks["build"] dependsOn tasks["test"]
}
