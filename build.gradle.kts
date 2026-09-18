import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("net.fabricmc.fabric-loom")
    kotlin("jvm")
    java
}

val minecraft_version = project.property("minecraft_version") as String
val loader_version = project.property("loader_version") as String
val fabric_version = project.property("fabric_version") as String
val fabric_kotlin_version = project.property("fabric_kotlin_version") as String
val mod_version = project.property("mod_version") as String
val mod_name = project.property("mod_name") as String

version = mod_version
group = "cc.me0wo"

base { archivesName.set(mod_name) }

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
    maven("https://api.modrinth.com/maven")
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")
    implementation("net.fabricmc:fabric-loader:$loader_version")
    implementation("net.fabricmc.fabric-api:fabric-api:$fabric_version")
    implementation("net.fabricmc:fabric-language-kotlin:$fabric_kotlin_version")

    // bundled via Jar-in-Jar (MIT, bundling officially encouraged)
    implementation("maven.modrinth:midnightlib:1.9.3+26.1-fabric")
    include("maven.modrinth:midnightlib:1.9.3+26.1-fabric")
}

tasks.withType<JavaCompile>().configureEach { options.release.set(25) }
tasks.withType<KotlinCompile>().configureEach { compilerOptions { jvmTarget.set(JvmTarget.JVM_25) } }

tasks.jar {
    // MelodyProgress-<mod_version>-<minecraft_version>.jar
    archiveVersion.set("$mod_version-$minecraft_version")
}

tasks.processResources {
    inputs.property("version", mod_version)
    filesMatching("fabric.mod.json") {
        expand("version" to mod_version)
    }
}
