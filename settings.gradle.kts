rootProject.name = "MelodyProgress"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net/")
    }

    plugins {
        id("net.fabricmc.fabric-loom") version "1.17.19"
        kotlin("jvm") version "2.4.20"
    }
}
